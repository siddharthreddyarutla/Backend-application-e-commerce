package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.UserAddressDto;
import com.siddharth.application.dto.UserDto;
import com.siddharth.application.dto.UserLoginDto;
import com.siddharth.application.entity.UserAddressEntity;
import com.siddharth.application.entity.UserEntity;
import com.siddharth.application.repository.UserAddressRepository;
import com.siddharth.application.repository.UserRepository;
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
        if(userRepository.findByEmail(userEmail) == null) {
            UserEntity userEntity = userDto.toUserEntity();
            userRepository.save(userEntity);
            return userDto;
        } else {
            return null;
        }
    }

    @Override
    public UserLoginDto userLogin(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository.findByEmailAndPassword(userLoginDto.getEmail(),
                userLoginDto.getPassword());
        UserLoginDto userLoginDto1 = new UserLoginDto();
        if(userEntity == null) {
            log.info("Cannot validate because " + USER_NOT_FOUND);
            return null;
        } else {
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
        if(userEntity.isPresent()) {
            userRepository.delete(userEntity.get());
            return USER_DELETED;
        } else {
            return USER_NOT_FOUND;
        }
    }

    @Override
    public UserDto editUserById(Long id, UserDto userDto) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(id);
        if(userEntity.isPresent()) {
            if(!userDto.getName().isEmpty()) {
                userEntity.get().setName(userDto.getName());
            }
            if (!userDto.getMobileNo().isEmpty()) {
                userEntity.get().setMobileNo(userDto.getMobileNo());
            }
            if(!userDto.getLocation().isEmpty()) {
                userEntity.get().setLocation(userDto.getLocation());
            }
            if(!userDto.getEmail().isEmpty()) {
                userEntity.get().setEmail(userDto.getEmail());
            }
            if(!userDto.getPassword().isEmpty()) {
                userEntity.get().setPassword(userDto.getPassword());
            }
            if(!userDto.getRole().isEmpty()) {
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
            UserAddressEntity userAddressEntity = userAddressDto.toUserAddressEntity();
            userAddressEntity.setUserId(userId);
            userAddressRepository.save(userAddressEntity);
            return userAddressDto;
        }
        return null;
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
}
