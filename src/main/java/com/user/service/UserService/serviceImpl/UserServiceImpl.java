package com.user.service.UserService.serviceImpl;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.exception.ResourceNotFoundException;
import com.user.service.UserService.repository.UserRepository;
import com.user.service.UserService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
      String randomUserId=  UUID.randomUUID().toString();
      user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public User getSingleUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
