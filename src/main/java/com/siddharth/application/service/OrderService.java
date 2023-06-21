package com.siddharth.application.service;

import com.siddharth.application.dto.orderDtos.*;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrdersDto> orderProductItems(Long orderId,List<Long> productIdList, List<Long> productQuantityList,
                                      Long shippingAddressId, Long billingAddressId, String paymentMode);

    String removeOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced(List<Long> orderId);

    List<OrdersDto> editOrderStateInMyOrders(List<Long> orderIds, String orderState);

    List<OrdersCompleteDto> getMyOrdersByUserId(Long userId);

    List<OrderDetailsDto> getMyOrderDetailsByOrderId(Long orderId);

    OrderDetailsCompleteDto getMyCompleteOrderDetailsByOrderId(Long userId, Long orderId);

    List<OrdersCompleteDto> searchByProductTitleCategoryOrderIdAddressAndRecipientName(Long userId, String attribute);

    OrderPlacedDetailsDto getOrderPlacedDetailsConfirmation(Long userId, List<Long> orderIds);

    List<OrdersCompleteDto> getMyFilteredOrdersOnOrderState(String orderType);

    List<OrdersCompleteDto> getFilterByOrderPlacedDateInMyOrders(Long userId, String attribute);
}
