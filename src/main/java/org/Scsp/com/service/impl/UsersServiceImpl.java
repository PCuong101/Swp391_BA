package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.Users;
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
    public Users registerUser(UsersRegisterDto usersRegisterDto) {
    Users existingUser = userRepository.findByEmail(usersRegisterDto.getEmail())
            .orElse(null);
    if (existingUser == null) {
        Users newUser = new Users();
        newUser.setEmail(usersRegisterDto.getEmail());
        newUser.setPassword(usersRegisterDto.getPassword());
        return userRepository.save(newUser);
    }
        return null;
    }

    @Override
    public Users loginUser(LoginRequest loginRequest) {
        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public Users saveUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users updateUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

