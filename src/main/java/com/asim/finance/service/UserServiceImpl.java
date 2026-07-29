package com.asim.finance.service;

import com.asim.finance.entity.Role;
import com.asim.finance.entity.User;
import com.asim.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }



    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }
    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);


    }

    @Override
    public void updateRole(Long id, Role role) {

        User user = userRepository.findById(id)
                .orElseThrow();

        user.setRole(role);

        userRepository.save(user);
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> searchUsers(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll();
        }

        return userRepository
                .findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}