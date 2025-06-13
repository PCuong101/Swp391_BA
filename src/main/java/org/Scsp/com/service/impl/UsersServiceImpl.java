package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.UsersService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;


    @Override
    public User registerUser(UsersRegisterDto usersRegisterDto) {
    User existingUser = userRepository.findByEmail(usersRegisterDto.getEmail())
            .orElse(null);
    if (existingUser == null) {
        User newUser = new User();
        newUser.setEmail(usersRegisterDto.getEmail());
        newUser.setPassword(usersRegisterDto.getPassword());
        return userRepository.save(newUser);
    }
        return null;
    }

    @Override
    public User loginUser(LoginRequest userLogin) {
        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().equals(userLogin.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User saveUser(User users) {
        return userRepository.save(users);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

