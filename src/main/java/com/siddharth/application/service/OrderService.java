package com.siddharth.application.service;

import com.siddharth.application.dto.orderDtos.OrderDetailsCompleteDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDetailsDto orderProductItems(OrderDetailsDto orderDetailsDto);
    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getMyOrders(Long userId);

    List<OrderDetailsCompleteDto> getMyOrdersCompleteDetails(Long userId);

    List<OrderDetailsCompleteDto> searchByDeliveryDateBetween(LocalDate beforeDate, LocalDate  afterDate);

    List<OrderDetailsCompleteDto> searchByDeliveryDateBefore(LocalDate beforeDate);

    List<OrderDetailsCompleteDto> searchByDeliveryDateAfter(LocalDate afterDate);

    List<OrderDetailsCompleteDto> getCancelledOrders(Long userId);

    List<OrderDetailsCompleteDto> searchByAttributesInOrderDetails(Long userId, String attribute);
}
