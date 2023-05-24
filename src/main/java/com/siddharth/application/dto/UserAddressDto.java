package com.siddharth.application.dto;

import com.siddharth.application.entity.UserAddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {
    private String fullName;
    private String mobileNumber;
    private String pinCode;
    private String houseNo;
    private String villageOrStreet;
    private String cityOrTown;
    private String state;
    private String country;
    private String addressType;
    private Boolean defaultAddress;

    public UserAddressEntity toUserAddressEntity() {
        return UserAddressEntity.builder().fullName(fullName).mobileNumber(mobileNumber).pinCode(pinCode)
                .houseNo(houseNo).villageOrStreet(villageOrStreet).cityOrTown(cityOrTown).state(state)
                .country(country).addressType(addressType).defaultAddress(defaultAddress).build();
    }
}