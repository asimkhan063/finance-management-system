package com.asim.finance.service;

import com.asim.finance.entity.Role;
import com.asim.finance.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> findByEmail(String email);

    boolean emailExists(String email);
  //  void save(User user);

   // User findByEmail(String email);
   List<User> getAllUsers();
    void deleteUser(Long id);
    void updateRole(Long id, Role role);

    Optional<User> findById(Long id);

    User save(User user);

    List<User> searchUsers(String keyword);

}