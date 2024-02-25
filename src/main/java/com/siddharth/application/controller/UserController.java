package com.siddharth.application.controller;

import com.siddharth.application.dto.userDtos.UserAddressDto;
import com.siddharth.application.dto.userDtos.UserDto;
import com.siddharth.application.dto.userDtos.UserLoginDto;
import com.siddharth.application.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.siddharth.application.constants.Constants.EMAIL_EXISTS;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  UserServiceImpl userServiceImpl;

  // post user details
  @PostMapping(value = "/postDetails")
  private ResponseEntity addUser(@RequestBody UserDto userDto) {
    UserDto userDto1 = userServiceImpl.createUser(userDto);
    if (userDto1 == null) {
      return new ResponseEntity<>(EMAIL_EXISTS, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }
  }

  // login api to validate whether user is present or not
  @PostMapping(value = "/login")
  private ResponseEntity<UserLoginDto> validateUser(@RequestBody UserLoginDto userLoginDto) {
    return new ResponseEntity<>(userServiceImpl.userLogin(userLoginDto), HttpStatus.OK);
  }

  // get all user details
  @GetMapping(value = "/getDetails")
  private ResponseEntity<UserDto> getUser(@RequestParam String name) {
    return new ResponseEntity<>(userServiceImpl.getUser(name), HttpStatus.OK);
  }

  // get user details by user id
  @GetMapping(value = "/getDetailsById/{userId}")
  private ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
    return new ResponseEntity<>(userServiceImpl.getUserById(userId), HttpStatus.OK);
  }

  // delete user details by user id
  @DeleteMapping(value = "/deleteUserById/{userId}")
  private ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
    return new ResponseEntity<>(userServiceImpl.deleteUserById(userId), HttpStatus.OK);
  }

  // edit user details by user id
  @PutMapping(value = "/editUserById/{userId}")
  private ResponseEntity<UserDto> editUser(@PathVariable Long userId,
      @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userServiceImpl.editUserById(userId, userDto), HttpStatus.OK);
  }

  // add/post location for user reference to user id
  @PostMapping(value = "/postAddress")
  private ResponseEntity<UserAddressDto> postAddressForUser(@RequestParam Long userId,
      @RequestBody UserAddressDto userAddressDto) {
    return new ResponseEntity<>(userServiceImpl.createAddressForUser(userId, userAddressDto),
        HttpStatus.OK);
  }

  // get addresses by user id
  @GetMapping(value = "/getAddressById")
  private ResponseEntity<List<UserAddressDto>> getAllAddressByUserId(@RequestParam Long userId) {
    return new ResponseEntity<>(userServiceImpl.getAllUserAddressesByUserId(userId), HttpStatus.OK);
  }

  // delete address by address id
  @DeleteMapping(value = "/deleteAddressByAddressId")
  private ResponseEntity<String> deleteAddressByAddressId(@RequestParam Long addressId) {
    return new ResponseEntity<>(userServiceImpl.deleteAddressById(addressId), HttpStatus.OK);
  }

  // edit address by address id
  @PutMapping(value = "/editAddressByAddressId")
  private ResponseEntity<UserAddressDto> editAddressByAddressId(@RequestParam Long addressId,
      @RequestBody UserAddressDto userAddressDto) {
    return new ResponseEntity<>(userServiceImpl.editAddressById(addressId, userAddressDto),
        HttpStatus.OK);
  }
}
