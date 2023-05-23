package com.siddharth.application.entity;

import com.siddharth.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long userId;
    @Column(name="NAME")
    private String name;
    @Column(name="MOBILE_NO")
    private String mobileNo;
    @Column(name="LOCATION")
    private String location;
    @Column(name="EMAIL")
    private String email;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="ROLE")
    private String role;

    public UserDto toUserDto() {
        return UserDto.builder().name(name).mobileNo(mobileNo).location(location)
                .email(email).password(password).role(role).build();
    }
}
