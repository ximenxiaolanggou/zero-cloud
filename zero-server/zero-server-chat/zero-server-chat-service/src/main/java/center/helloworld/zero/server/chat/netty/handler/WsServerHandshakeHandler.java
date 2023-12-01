package center.helloworld.zero.server.chat.netty.handler;


import center.helloworld.zero.common.code.ResCode;
import center.helloworld.zero.common.utils.JwtUtil;
import center.helloworld.zero.server.chat.api.model.entity.Session;
import center.helloworld.zero.server.chat.netty.channel.ChannelManager;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import cn.hutool.core.net.url.UrlBuilder;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 握手处理
 */

@Slf4j
@Component
@ChannelHandler.Sharable
public class WsServerHandshakeHandler extends ChannelInboundHandlerAdapter {

    @Value("${zero.jwtSecret}")
    private String jwtSecret;

    @Autowired
    private SessionService sessionService;

    @Resource
    private ChannelManager channelManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            CharSequence token = urlBuilder.getQuery().get("token");

            log.info("WsServerHandshakeHandler~~~~~~~~~~~~~~~");
            ResCode resCode = null;
            String userId = null;
            try {
                DecodedJWT decodedJWT = JwtUtil.verify(jwtSecret, token.toString());
                userId = decodedJWT.getId();
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
            if(resCode != null) {
                ctx.close();
                return;
            }else {
                // 令牌合法 创建会话
                // TODO 防止重复会话
                Session session = sessionService.createSession(Long.valueOf(userId), null);
                ctx.channel().attr(AttributeKey.valueOf("sessionId")).set(session.getSessionId());
                channelManager.save(session.getSessionId(), ctx.channel());
            }
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("服务端连接发生错误 {} {}", ctx.channel(), cause.getMessage());
    }
}
