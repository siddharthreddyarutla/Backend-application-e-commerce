package com.siddharth.application.service;

import com.siddharth.application.dto.orderDtos.OrderDetailsCompleteDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
import com.siddharth.application.dto.orderDtos.OrdersDto;
import com.siddharth.application.dto.orderDtos.OrderPlacedDetailsDto;
import net.bytebuddy.asm.MemberSubstitution;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrdersDto> orderProductItems(Long orderId,List<Long> productIdList, List<Long> productQuantityList,
                                      Long shippingAddressId, Long billingAddressId, String paymentMode);

    String removeOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced(List<Long> orderId);

    List<OrdersDto> editOrderStateInMyOrders(List<Long> orderIds, String orderState);

    List<OrdersDto> getMyOrdersByUserId(Long userId);

    List<OrderDetailsDto> getMyOrderDetailsByOrderId(Long orderId);
}
