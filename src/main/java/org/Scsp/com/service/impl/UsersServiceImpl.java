package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.dto.ScheduleDTO;
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
        newUser.setName(usersRegisterDto.getName());
        newUser.setEmail(usersRegisterDto.getEmail());
        newUser.setPassword(usersRegisterDto.getPassword());
        newUser.setRole(Role.MEMBER);
        newUser.setAddictionLevel(usersRegisterDto.getAddictionLevel());
        return userRepository.save(newUser);
    }
        return null;
    }

    @Override
    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);
        if (user == null) {
            return null;
        } else if (user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        } else return null;
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

    @Override
    public List<Long> getAllCoachIds() {
        List<User> coaches = userRepository.findByRole(Role.COACH);
        List<Long> coachIds = coaches.stream()
                .map(User::getUserId)
                .toList();
        return coachIds;
    }

}

