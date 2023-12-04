package center.helloworld.zero.server.chat.netty.handler;

import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class WsServerMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        System.out.println("msg");
        HashMap map = new HashMap();
        map.put("msgType", 0);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(map)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
