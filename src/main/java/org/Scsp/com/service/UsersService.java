package org.Scsp.com.service;

import org.Scsp.com.model.User;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Long id);
}