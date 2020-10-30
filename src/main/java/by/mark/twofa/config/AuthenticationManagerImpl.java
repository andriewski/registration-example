package by.mark.twofa.config;

import by.mark.twofa.component.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final JwtProvider jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;

        try {
            username = jwtUtil.extractUsername(authToken);
        } catch (Exception e) {
            username = null;
            log.warn("Exception was thrown", e);
        }

        if (username == null || !jwtUtil.validateToken(authToken)) {
            return null;
        }

        Claims claims = jwtUtil.getClaimsFromToken(authToken);
        List<String> role = claims.get("role", List.class);
        List<SimpleGrantedAuthority> authorities = role.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
    }
}
