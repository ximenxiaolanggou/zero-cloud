package center.helloworld.zero.server.chat.netty;

import center.helloworld.zero.server.chat.netty.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@Component
public class WsServerStart implements CommandLineRunner {

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.start();
    }
}
