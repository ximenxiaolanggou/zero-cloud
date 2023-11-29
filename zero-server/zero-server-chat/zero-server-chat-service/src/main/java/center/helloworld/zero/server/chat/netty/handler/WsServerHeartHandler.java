package center.helloworld.zero.server.chat.netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
public class WsServerHeartHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 删除无用通道
                log.info("未及时发送心跳 关闭连接 {}", ctx.channel());
                ctx.channel().close();
                return;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
