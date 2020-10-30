package by.mark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class User {

    @Id
    private Long id;

    private boolean isUsing2FA;
    private String secret;

    public User() {
        super();
        this.secret = Base32.random();
    }
}