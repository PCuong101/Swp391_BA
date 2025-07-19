package org.Scsp.com.service;

import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.User;
import org.springframework.stereotype.Service;
import org.Scsp.com.dto.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    User registerUser(UsersRegisterDto usersRegisterDto);
    User loginUser(LoginRequest userLogin);
    User saveUser(User users);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long userId, UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);
    List<Long> getAllCoachIds();
}