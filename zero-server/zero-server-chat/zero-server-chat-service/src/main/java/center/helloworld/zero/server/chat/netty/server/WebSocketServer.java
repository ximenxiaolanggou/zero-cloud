package center.helloworld.zero.server.chat.netty.server;

import center.helloworld.zero.server.chat.netty.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note ws 服务
 */
@Slf4j
@Component
public class WebSocketServer implements IServer {

    private volatile boolean isStart;

    private Channel serverChannel;

    private int port = 20000;

    private String ip;

    private NioEventLoopGroup boss;

    private NioEventLoopGroup worker;

    @Resource
    private ServerChannelInitializer serverChannelInitializer;


    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public void start() {
        try {
            if (isStart()) {
                log.error("重复启动 {}", this);
                return;
            }
            boss = new NioEventLoopGroup(1);
            worker = new NioEventLoopGroup(1);
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverChannelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            ChannelFuture channelFuture = b.bind(port).sync();
            serverChannel = channelFuture.channel();
            this.ip = InetAddress.getLocalHost().getHostAddress();
            log.info("服务器 {}:{} 启动", getIp(), getPort());
            isStart = true;
            serverChannel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    shutdown();
                    log.error("服务器关闭...");
                    System.exit(1);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void shutdown() {
        if (boss != null) {
            boss.shutdownGracefully();
        }

        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    @Override
    public boolean isStart() {
        return isStart;
    }
}
