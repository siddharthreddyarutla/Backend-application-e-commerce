package com.siddharth.application.controller;

import com.siddharth.application.dto.UserDto;
import com.siddharth.application.dto.UserLoginDto;
import com.siddharth.application.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.siddharth.application.constants.Constants.EMAIL_EXISTS;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping(value = "/postDetails")
    private ResponseEntity addUser(@RequestBody UserDto userDto) {
        UserDto userDto1 = userServiceImpl.createUser(userDto);
        if (userDto1 == null) {
            return new ResponseEntity<>(EMAIL_EXISTS, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDto1, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/login")
    private ResponseEntity<UserLoginDto> validateUser(@RequestBody UserLoginDto userLoginDto) {
        return new ResponseEntity<>(userServiceImpl.userLogin(userLoginDto), HttpStatus.OK);
    }

    @GetMapping(value = "/getDetails")
    private ResponseEntity<UserDto> getUser(@RequestParam String name) {
        return new ResponseEntity<>(userServiceImpl.getUser(name), HttpStatus.OK);
    }

    @GetMapping(value = "/getDetailsById/{userId}")
    private ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userServiceImpl.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUserById/{userId}")
    private ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userServiceImpl.deleteUserById(userId), HttpStatus.OK);
    }

    @PutMapping(value = "/editUserById/{userId}")
    private ResponseEntity<UserDto> editUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userServiceImpl.editUserById(userId, userDto), HttpStatus.OK);
    }
}
