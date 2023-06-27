package center.helloworld.zero.server.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhishun.cai
 * @create 2023/6/27
 * @note
 */

@SpringBootApplication
@EnableDiscoveryClient
public class SystemApp {
    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class);
    }
}
