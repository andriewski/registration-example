package by.mark.twofa.autoconfigure;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.component.jwt.TotpManager;
import by.mark.twofa.config.JwtConfiguration;
import by.mark.twofa.config.SecurityConfiguration;
import by.mark.twofa.config.WebConfiguration;
import by.mark.twofa.repo.MessageRepository;
import by.mark.twofa.repo.UserRepository;
import by.mark.twofa.service.MessageService;
import by.mark.twofa.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Import({
        JwtConfiguration.class,
        SecurityConfiguration.class,
        WebConfiguration.class
})
@Configuration
public class TwoFaAutoConfiguration {

    @Bean
    public UserService userService(
            UserRepository userRepository,
            TotpManager totpManager,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder
    ) {
        return new UserService(userRepository, totpManager, jwtProvider, passwordEncoder);
    }

    @Bean
    public MessageService messageService(MessageRepository messageRepository) {
        return new MessageService(messageRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
