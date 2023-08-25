package center.helloworld.zero.server.chat.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class WsServerCommonHandler extends ChannelInboundHandlerAdapter {

    /**
     * 关闭连接
     *
     * @param ctx 通道上下文
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        try {
            log.info("断开连接 {}", ctx.channel());
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
