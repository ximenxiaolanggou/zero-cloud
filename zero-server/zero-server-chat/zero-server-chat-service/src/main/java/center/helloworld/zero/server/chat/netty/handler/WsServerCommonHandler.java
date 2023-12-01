package center.helloworld.zero.server.chat.netty.handler;

import center.helloworld.zero.server.chat.netty.channel.ChannelManager;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class WsServerCommonHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private ChannelManager channelManager;

    @Autowired
    private SessionService sessionService;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        try {
            log.info("断开连接11 {}", ctx.channel());
            Object sessionId = ctx.channel().attr(AttributeKey.valueOf("sessionId")).get();
            channelManager.remove(String.valueOf(sessionId));
            sessionService.remove(String.valueOf(sessionId));
            ctx.channel().close();
        } catch (Exception e) {
            log.error("==== {}", e.getStackTrace());
            log.error("端口连接发生错误 {}", e.getMessage());
            ctx.close();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
