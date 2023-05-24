package com.siddharth.application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "/ADDRESS")
public class UserAddressEntity {
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
    private String State;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "ADDRESS_TYPE")
    private String addressType;
    @Column(name = "DEFAULT_ADDRESS")
    private  Boolean defaultAddress;
}