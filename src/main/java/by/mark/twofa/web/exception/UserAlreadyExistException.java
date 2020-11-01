package by.mark.twofa.web.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User has already existed")
public class UserAlreadyExistException extends RuntimeException {
}
