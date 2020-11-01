package by.mark.twofa.component.security;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.constant.ClaimField;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;

        try {
            username = jwtProvider.extractUsername(authToken);
        } catch (Exception e) {
            username = null;
            log.warn("Exception was thrown", e);
        }

        if (username == null || !jwtProvider.validateToken(authToken)) {
            return null;
        }

        Claims claims = jwtProvider.getClaimsFromToken(authToken);
        List<String> role = claims.get(ClaimField.ROLE, List.class);
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
