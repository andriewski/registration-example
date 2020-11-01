package by.mark.twofa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jwt")
public class JwtConfig {

    private String secret;
    private Long expiration;
}
