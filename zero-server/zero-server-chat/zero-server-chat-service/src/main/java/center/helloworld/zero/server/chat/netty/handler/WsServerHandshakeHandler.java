package center.helloworld.zero.server.chat.netty.handler;


import center.helloworld.zero.common.code.ResCode;
import center.helloworld.zero.common.utils.JwtUtil;
import center.helloworld.zero.server.chat.api.model.entity.Session;
import center.helloworld.zero.server.chat.api.model.entity.message.send.SendBaseMsg;
import center.helloworld.zero.server.chat.api.model.entity.message.send.SendMsgType;
import center.helloworld.zero.server.chat.netty.channel.ChannelManager;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        log.info("WsServerHandshakeHandler~~~~~~~~~~~~~~~");
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            CharSequence token = urlBuilder.getQuery().get("token");
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
                // 令牌合法
                // 防止重复会话
                Session alreadySession = sessionService.getSessionByUserId(Long.valueOf(userId));
                // TODO : 一个账号在不同浏览器中是相互顶掉的，但是当一个浏览器中登录成功后，
                // 再打开一个浏览器就会存在，挤掉的问题（需要判断token信息，同一个浏览器上的token信息是一样的）
                if(alreadySession != null) {
                    // 会话已存在, 获取会话对应的channel
                    Channel channel = channelManager.getChannel(alreadySession.getSessionId());
                    // 客户端断开但服务端还保留通道链接（概率比较小，当客户端浏览器断电时会出现情况 之一）
                    if(StringUtils.isNotBlank(alreadySession.getToken()) && token.equals(alreadySession.getToken())) {
                        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(new SendBaseMsg<String>(SendMsgType.CLOSE_AFTER_SHOW_MSG_SAME_CLIENT, "您当前客户端被挤下线！"))));
                    }else {
                        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(new SendBaseMsg<String>(SendMsgType.CLOSE_AFTER_SHOW_MSG_OTHER_CLIENT, "您被别的客户端挤下线！"))));
                    }
                    channel.close();
                    sessionService.remove(alreadySession.getSessionId());
                    channelManager.remove(alreadySession.getSessionId());
                }
                // 创建会话
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
