package cener.helloworld.zero.starter.openfeign.configure;


import cener.helloworld.zero.starter.openfeign.interceptors.FeignInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "center.helloworld.**.api")
public class ZeroOpenfeignAutoConfigure {

    @Bean
    public FeignInterceptor requestHeaderForwordInterceptor() {
        return new FeignInterceptor();
    }
}
