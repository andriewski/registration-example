package by.mark.twofa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String role;
}
