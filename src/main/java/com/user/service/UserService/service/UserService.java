package com.user.service.UserService.service;

import com.user.service.UserService.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getSingleUser(String userId);

    List<User> getAllUsers();
}
