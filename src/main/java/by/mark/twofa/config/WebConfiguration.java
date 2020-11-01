package by.mark.twofa.config;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.service.UserService;
import by.mark.twofa.web.filter.JwtFilter;
import org.springframework.context.annotation.Bean;

public class WebConfiguration {

    @Bean
    public JwtFilter jwtFilter(JwtProvider jwtProvider, UserService userService) {
        return new JwtFilter(jwtProvider, userService);
    }
}
