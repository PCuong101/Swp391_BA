package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
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

