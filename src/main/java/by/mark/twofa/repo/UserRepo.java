package by.mark.twofa.repo;

import by.mark.twofa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String name);
}