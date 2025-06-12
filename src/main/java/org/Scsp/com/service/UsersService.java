package org.Scsp.com.service;

import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    Users registerUser(UsersRegisterDto usersRegisterDto);
    Users loginUser(LoginRequest loginRequest);
    Users saveUser(Users users);
    Optional<Users> getUserById(Long id);
    List<Users> getAllUsers();
    Users updateUser(Users users);
    void deleteUser(Long id);
}