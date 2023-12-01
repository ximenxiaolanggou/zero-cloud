package center.helloworld.zero.gateway.filter;

import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.common.code.ResCode;
import center.helloworld.zero.common.utils.JwtUtil;
import center.helloworld.zero.gateway.properties.GatewayProperty;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

/**
 * JWT 拦截器
 */
@Component
public class JwtFilter implements GlobalFilter {

    @Autowired
    private GatewayProperty gatewayProperty;

    @PostConstruct
    public void init() {
        if(!CollectionUtils.isEmpty(gatewayProperty.getAnonUris())) {
            for (String anonUris : gatewayProperty.getAnonUris()) {
                if(anonUris.contains("/*")) {
                    anonUrisPattern.add(anonUris);
                }else {
                    anonUrisCommon.add(anonUris);
                }
            }
        }
    }

    // 普通路径
    List<String> anonUrisCommon = new ArrayList();
    // 携带/* 或者/**
    List<String> anonUrisPattern = new ArrayList();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LinkedHashSet<URI> uris = exchange.getAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originUri = null;
        if (uris != null) {
            originUri = uris.stream().findFirst().orElse(null);
        }
        String path = originUri.getPath();
        // 判断放行路径
        if(anonUrisCommon.contains(path)) {
            return chain.filter(exchange);
        }
        if(!CollectionUtils.isEmpty(anonUrisPattern)) {
            for (String anonUri : anonUrisPattern) {
                if(anonUri.endsWith("/*")) {
                    // 路径头部相同，并且层级一样
                    if(path.startsWith(anonUri) && anonUri.split("/").length == path.split("/").length)
                        return chain.filter(exchange);
                }else if(anonUri.endsWith("/**")) {
                    // 路径头部相同，并且层级大于等于
                    if(path.startsWith(anonUri) && anonUri.split("/").length >= path.split("/").length)
                        return chain.filter(exchange);
                }
            }
        }
        // 下面代表需要验证token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        // 1. 令牌为空判断
        if(StringUtils.isBlank(token) || !token.startsWith("bearer ")) {
            //如果不符合就不给访问
            ServerHttpResponse response = exchange.getResponse();
            DataBuffer wrap = makeErrResponse(exchange, ResCode.ERROR_NO_TOKEN);
            return response.writeWith(Mono.just(wrap));
        }
        // 2. 令牌合法性判断
        token = token.replace("bearer ","");
        ResCode resCode = null;
        try {
            DecodedJWT decodedJWT = JwtUtil.verify(gatewayProperty.getJwtSecret(), token);
            ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
            // ID
            mutate.header("id", decodedJWT.getId());
            // 获取载体信息
            for (String key : decodedJWT.getClaims().keySet()) {
                // 防止中文（请求头携带中不可以携带中文，这里用base64进行加密）
                mutate.header(key, Base64.encode(decodedJWT.getClaim(key).asString(), CharsetUtil.UTF_8));
            }
            ServerWebExchange serverWebExchange = exchange.mutate().request(mutate.build()).build();
            return chain.filter(serverWebExchange);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            resCode = ResCode.ERROR_NOT_MATCH_SIGNATURE;
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            resCode = ResCode.ERROR_TOKEN_EXPIRE;
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            resCode = ResCode.ERROR_NOT_MATCH_ALGORITHN;
        } catch (InvalidClaimException e) {
            e.printStackTrace();
            resCode = ResCode.ERROR_INVALID_CLAIM;
        } catch (Exception e) {
            e.printStackTrace();
            resCode = ResCode.ERROR_INVALID_TOKEN;
        }

        //如果不符合就不给访问
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer wrap = makeErrResponse(exchange, resCode);
        return response.writeWith(Mono.just(wrap));
    }

    /**
     * 创建错误返回对象
     * @param exchange
     * @param resCode
     * @return
     */
    public DataBuffer makeErrResponse(ServerWebExchange exchange, ResCode resCode) {
        Result result = new Result(resCode);
        //如果不符合就不给访问
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type","application/json;charset=utf-8");
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        ObjectMapper objectMapper = new ObjectMapper();
        byte [] bytes = new byte[0];
        try {
            bytes=objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.bufferFactory().wrap(bytes);
    }
}
