package org.Scsp.com.repository;

import org.Scsp.com.Enum.Role;
import org.Scsp.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    Role role(Role role);
}