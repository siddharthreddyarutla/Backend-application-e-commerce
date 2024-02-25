package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.userDtos.UserAddressDto;
import com.siddharth.application.dto.userDtos.UserDto;
import com.siddharth.application.dto.userDtos.UserLoginDto;
import com.siddharth.application.entity.userEntities.UserAddressEntity;
import com.siddharth.application.entity.userEntities.UserEntity;
import com.siddharth.application.repository.userRepositories.UserAddressRepository;
import com.siddharth.application.repository.userRepositories.UserRepository;
import com.siddharth.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.siddharth.application.constants.Constants.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserAddressRepository userAddressRepository;

  @Override
  public UserDto createUser(UserDto userDto) {
    String userEmail = userDto.getEmail();
    if (userRepository.findByEmail(userEmail) == null) {
      UserEntity userEntity = userDto.toUserEntity();
      userRepository.save(userEntity);
      return userDto;
    } else {
      return null;
    }
  }

  @Override
  public UserLoginDto userLogin(UserLoginDto userLoginDto) {
    UserEntity userEntity =
        userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
    UserLoginDto userLoginDto1 = new UserLoginDto();
    if (userEntity == null) {
      log.info("Cannot validate because " + USER_NOT_FOUND);
      return null;
    } else {
      userLoginDto1.setUserId(userEntity.getUserId());
      userLoginDto1.setEmail(userEntity.getEmail());
      userLoginDto1.setPassword(userEntity.getPassword());
      userLoginDto1.setRole(userEntity.getRole());
    }
    return userLoginDto1;
  }

  @Override
  public UserDto getUser(String name) {
    UserEntity userEntity = userRepository.findByName(name);
    return userEntity.toUserDto();
  }

  @Override
  public UserDto getUserById(Long id) {
    Optional<UserEntity> userEntity = userRepository.findByUserId(id);
    return userEntity.get().toUserDto();
  }

  @Override
  public String deleteUserById(Long id) {
    Optional<UserEntity> userEntity = userRepository.findByUserId(id);
    if (userEntity.isPresent()) {
      userRepository.delete(userEntity.get());
      return USER_DELETED;
    } else {
      return USER_NOT_FOUND;
    }
  }

  @Override
  public UserDto editUserById(Long id, UserDto userDto) {
    Optional<UserEntity> userEntity = userRepository.findByUserId(id);
    if (userEntity.isPresent()) {
      if (!userDto.getName().isEmpty()) {
        userEntity.get().setName(userDto.getName());
      }
      if (!userDto.getMobileNo().isEmpty()) {
        userEntity.get().setMobileNo(userDto.getMobileNo());
      }
      if (!userDto.getLocation().isEmpty()) {
        userEntity.get().setLocation(userDto.getLocation());
      }
      if (!userDto.getEmail().isEmpty()) {
        userEntity.get().setEmail(userDto.getEmail());
      }
      if (!userDto.getPassword().isEmpty()) {
        userEntity.get().setPassword(userDto.getPassword());
      }
      if (!userDto.getRole().isEmpty()) {
        userEntity.get().setRole(userDto.getRole());
      }
      userRepository.save(userEntity.get());
      return userEntity.get().toUserDto();
    }
    return null;
  }

  @Override
  public UserAddressDto createAddressForUser(Long userId, UserAddressDto userAddressDto) {
    Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
    if (userEntity != null) {
      if (userAddressDto.getDefaultAddress() == true) {
        makeDefaultAddressFalse(userId);
      }
      UserAddressEntity userAddressEntity = userAddressDto.toUserAddressEntity();
      userAddressEntity.setUserId(userId);
      userAddressRepository.save(userAddressEntity);
      return userAddressDto;
    }
    return null;
  }

  public void makeDefaultAddressFalse(Long userId) {
    List<UserAddressEntity> userAddressEntityList = userAddressRepository.findByUserId(userId);
    if (!userAddressEntityList.isEmpty()) {
      for (UserAddressEntity userAddressEntity : userAddressEntityList) {
        userAddressEntity.setDefaultAddress(false);
        userAddressRepository.save(userAddressEntity);
      }
    }
  }

  @Override
  public List<UserAddressDto> getAllUserAddressesByUserId(Long userId) {
    Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
    if (userEntity != null) {
      List<UserAddressEntity> userAddressEntityList = userAddressRepository.findByUserId(userId);
      List<UserAddressDto> userAddressDtoList = new ArrayList<>();

      if (!userAddressEntityList.isEmpty()) {
        for (UserAddressEntity userAddressEntity : userAddressEntityList) {
          userAddressDtoList.add(userAddressEntity.toUserAddressDto());
        }
      }
      return userAddressDtoList;
    }
    return null;
  }

  @Override
  public String deleteAddressById(Long addressId) {
    UserAddressEntity userAddressEntity = userAddressRepository.findByAddressId(addressId);
    if (userAddressEntity != null) {
      userAddressRepository.delete(userAddressEntity);
      return ADDRESS_DELETE;
    }
    return ADDRESS_NOT_FOUND;
  }

  @Override
  public UserAddressDto editAddressById(Long addressId, UserAddressDto userAddressDto) {
    UserAddressEntity userAddressEntity = userAddressRepository.findByAddressId(addressId);
    if (userAddressEntity != null) {
      if (userAddressDto.getFullName() != null) {
        userAddressEntity.setFullName(userAddressDto.getFullName());
      }
      if (userAddressDto.getMobileNumber() != null) {
        userAddressEntity.setMobileNumber(userAddressDto.getMobileNumber());
      }
      if (userAddressDto.getPinCode() != null) {
        userAddressEntity.setPinCode(userAddressDto.getPinCode());
      }
      if (userAddressDto.getHouseNo() != null) {
        userAddressEntity.setHouseNo(userAddressDto.getHouseNo());
      }
      if (userAddressDto.getVillageOrStreet() != null) {
        userAddressEntity.setVillageOrStreet(userAddressDto.getVillageOrStreet());
      }
      if (userAddressDto.getCityOrTown() != null) {
        userAddressEntity.setCityOrTown(userAddressDto.getCityOrTown());
      }
      if (userAddressDto.getState() != null) {
        userAddressEntity.setState(userAddressDto.getState());
      }
      if (userAddressDto.getCountry() != null) {
        userAddressEntity.setCountry(userAddressDto.getCountry());
      }
      if (userAddressDto.getAddressType() != null) {
        userAddressEntity.setAddressType(userAddressDto.getAddressType());
      }
      if (userAddressDto.getDefaultAddress() != null) {
        if (userAddressDto.getDefaultAddress() == true) {
          makeDefaultAddressFalse(userAddressEntity.getUserId());
        }
        userAddressEntity.setDefaultAddress(userAddressDto.getDefaultAddress());
      }
      userAddressRepository.save(userAddressEntity);
      return userAddressEntity.toUserAddressDto();
    }
    return null;
  }
}
