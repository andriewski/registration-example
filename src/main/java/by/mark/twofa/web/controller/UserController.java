package by.mark.twofa.web.controller;

import by.mark.twofa.model.User;
import by.mark.twofa.component.jwt.TotpManager;
import by.mark.twofa.service.UserService;
import by.mark.twofa.web.request.LoginRequest;
import by.mark.twofa.web.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(UserController.API_PATH)
@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String API_PATH = "/api/v1/user";

    private final UserService userService;
    private final TotpManager totpManager;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getCode());

        return ResponseEntity.accepted().body(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User createdUser = userService.registerUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(SignupResponse.builder()
                        .secretImageUri(totpManager.getUriForImage(createdUser.getSecret()))
                        .build());
    }
}
