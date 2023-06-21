package com.siddharth.application.dto.orderDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedDetailsDto {

    private String name;
    private StringBuilder completeAddress;
    private String mobileNumber;
    private LocalDate deliveryDate;
    private List<String> images;
}

