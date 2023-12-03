package center.helloworld.zero.server.chat.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private WsServerHeartHandler wsServerHeartHandler;

    @Resource
    private WsServerCommonHandler wsServerCommonHandler;

    @Resource
    private WsServerHandshakeHandler wsServerHandshakeHandler;
    
    @Resource
    private WsServerMessageHandler wsServerMessageHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // server读空闲时间 超过该时间的channel关闭
        pipeline.addLast(new IdleStateHandler(20, 0, 0));
        // 心跳处理
        pipeline.addLast(wsServerHeartHandler);
        // 关闭或者异常处理
        pipeline.addLast(wsServerCommonHandler);
        //以下三个是Http的支持
        //http解码器
        pipeline.addLast(new HttpServerCodec());
        //支持写大数据流
        pipeline.addLast(new ChunkedWriteHandler());
        //http聚合器
        pipeline.addLast(new HttpObjectAggregator(1024 * 4)); // 4KB
        // 握手处理
        pipeline.addLast(wsServerHandshakeHandler);
        // TODO
        // WebSocket数据压缩 TODO 暂时关闭数据压缩，会导致客户端发送消息报错issule
//        pipeline.addLast(new WebSocketServerCompressionHandler());
        //websocket支持,设置路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws/chat", true));
        pipeline.addLast(wsServerMessageHandler);
    }
}
