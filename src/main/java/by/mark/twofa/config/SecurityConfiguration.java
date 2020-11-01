package by.mark.twofa.config;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.component.security.AuthenticationManagerImpl;
import by.mark.twofa.web.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static by.mark.twofa.model.UserRole.ROLE_ADMIN;
import static by.mark.twofa.model.UserRole.ROLE_USER;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/admin**").hasRole(ROLE_ADMIN.getRole())
                .antMatchers("/user**").hasRole(ROLE_USER.getRole())
                .antMatchers("**/register", "**/auth", "**/login").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager(JwtProvider jwtProvider) {
        return new AuthenticationManagerImpl(jwtProvider);
    }
}
