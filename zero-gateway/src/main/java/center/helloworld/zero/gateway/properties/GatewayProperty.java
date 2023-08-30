package center.helloworld.zero.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "zero")
public class GatewayProperty {

    private String jwtSecret;

    private List<String> anonUris;
}
