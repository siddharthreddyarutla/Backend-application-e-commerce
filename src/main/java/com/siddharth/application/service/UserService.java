package com.siddharth.application.service;

import com.siddharth.application.dto.userDtos.UserAddressDto;
import com.siddharth.application.dto.userDtos.UserDto;
import com.siddharth.application.dto.userDtos.UserLoginDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto userDto);

  UserLoginDto userLogin(UserLoginDto userLoginDto);

  UserDto getUser(String name);

  UserDto getUserById(Long userId);

  String deleteUserById(Long userId);

  UserDto editUserById(Long userId, UserDto userDto);

  UserAddressDto createAddressForUser(Long userId, UserAddressDto userAddressDto);

  List<UserAddressDto> getAllUserAddressesByUserId(Long userId);

  String deleteAddressById(Long addressId);

  UserAddressDto editAddressById(Long addressId, UserAddressDto userAddressDto);
}