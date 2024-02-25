package com.siddharth.application.entity.userEntities;

import com.siddharth.application.dto.userDtos.UserAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
public class UserAddressEntity {
  @Id
  @Column(name = "ADDRESS_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long addressId;
  @Column(name = "USER_ID")
  private Long userId;
  @Column(name = "FULL_NAME")
  private String fullName;
  @Column(name = "MOBILE_NUMBER")
  private String mobileNumber;
  @Column(name = "PIN_CODE")
  private String pinCode;
  @Column(name = "HOUSE_NO")
  private String houseNo;
  @Column(name = "VILLAGE_OR_STREET")
  private String villageOrStreet;
  @Column(name = "CITY_OR_TOWN")
  private String cityOrTown;
  @Column(name = "STATE")
  private String state;
  @Column(name = "COUNTRY")
  private String country;
  @Column(name = "ADDRESS_TYPE")
  private String addressType;
  @Column(name = "DEFAULT_ADDRESS")
  private Boolean defaultAddress;

  public UserAddressDto toUserAddressDto() {
    return UserAddressDto.builder().addressId(addressId).fullName(fullName)
        .mobileNumber(mobileNumber).pinCode(pinCode).houseNo(houseNo)
        .villageOrStreet(villageOrStreet).cityOrTown(cityOrTown).state(state).country(country)
        .addressType(addressType).defaultAddress(defaultAddress).build();
  }
}