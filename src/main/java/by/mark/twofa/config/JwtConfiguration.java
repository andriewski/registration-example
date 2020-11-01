package by.mark.twofa.config;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.component.jwt.TotpManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(JwtConfig.class)
public class JwtConfiguration {

    @Bean
    public TotpManager totpManager() {
        return new TotpManager();
    }

    @Bean
    public JwtProvider jwtProvider(JwtConfig jwtConfig) {
        return new JwtProvider(jwtConfig);
    }
}
