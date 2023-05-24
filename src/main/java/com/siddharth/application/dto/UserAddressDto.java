package com.siddharth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {
    private Long userId;
    private String fullName;
    private String mobileNumber;
    private String pinCode;
    private String houseNo;
    private String villageOrStreet;
    private String cityOrTown;
    private String State;
    private String country;
    private String addressType;
    private  Boolean defaultAddress;
}