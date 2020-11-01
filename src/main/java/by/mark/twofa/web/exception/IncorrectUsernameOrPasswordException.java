package by.mark.twofa.web.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User name or password are incorrect")
public class IncorrectUsernameOrPasswordException extends RuntimeException {
}
