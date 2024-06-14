package com.user.service.UserService.controller;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE USER API
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user) , HttpStatus.CREATED);
    }

    //GET ONLY USER DETAILS BASED UPON ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getSingleUser(userId),HttpStatus.OK);
    }

    //GET THE LIST OF ONLY USER DETAILS
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    //GET SINGLE USER WITH USER DETAILS AND RATINGS &HOTEL DETAILS ON WHICH THAT THE USER HAS MADE
    @GetMapping("/userwithratings&hotels/{userId}")
    public ResponseEntity<User> getSingleUserWithRatingAndHotel(@PathVariable String userId){
        return new ResponseEntity<>(userService.getSingleUserWithRatingAndHotel(userId),HttpStatus.OK);
    }

    //GET SINGLE USER WITH USER DETAILS AND RATINGS THE USER HAS MADE
    @GetMapping("/userwithratings/{userId}")
    public ResponseEntity<User> getSingleUserWithRating(@PathVariable String userId){
        return new ResponseEntity<>(userService.getSingleUserWithRating(userId),HttpStatus.OK);
    }
}
