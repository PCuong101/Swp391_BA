package org.Scsp.com.repository;

import org.Scsp.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}