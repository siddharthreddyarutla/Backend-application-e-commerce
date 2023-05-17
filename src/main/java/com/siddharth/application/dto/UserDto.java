package com.siddharth.application.dto;

import com.siddharth.application.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String mobileNo;
    private String location;
    private String email;
    private String password;
    private String role;

    public UserEntity toUserEntity() {
        return  UserEntity.builder().name(name).mobileNo(mobileNo).location(location).email(email)
                .password(password).role(role).build();
    }
}
