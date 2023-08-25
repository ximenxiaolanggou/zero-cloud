package center.helloworld.zero.server.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ChatApp {
    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class);
    }
}
