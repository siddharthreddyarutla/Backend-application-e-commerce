package com.siddharth.application.service;

import com.siddharth.application.dto.UserDto;
import com.siddharth.application.dto.UserLoginDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserLoginDto userLogin(UserLoginDto userLoginDto);

    UserDto getUser(String name);

    UserDto getUserById(Long userId);

    String deleteUserById(Long userId);

    UserDto editUserById(Long userId, UserDto userDto);
}