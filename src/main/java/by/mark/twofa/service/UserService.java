package by.mark.twofa.service;

import by.mark.twofa.component.jwt.JwtProvider;
import by.mark.twofa.component.jwt.TotpManager;
import by.mark.twofa.model.User;
import by.mark.twofa.repo.UserRepository;
import by.mark.twofa.web.exception.IncorrectUsernameOrPasswordException;
import by.mark.twofa.web.exception.NoValidCodeException;
import by.mark.twofa.web.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final TotpManager totpManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean userExistByName(String name) {
        return userRepo.findByUsername(name) != null;
    }

    public User registerUser(User user) {
        if (userExistByName(user.getUsername())) {
            throw new UserAlreadyExistException();
        }
        if (user.isMultiFactorAuthentication()) {
            user.setSecret(totpManager.generateSecret());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    public String login(String username, String password, String code) {
        User user = userRepo.findByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
        if (!totpManager.verifyCode(code, user.getSecret())) {
            throw new NoValidCodeException();
        }

        return jwtProvider.generateToken(user);
    }
}
