package com.siddharth.application.controller;

import com.siddharth.application.dto.orderDtos.OrderDetailsCompleteDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
import com.siddharth.application.dto.orderDtos.OrdersDto;
import com.siddharth.application.dto.orderDtos.OrderPlacedDetailsDto;
import com.siddharth.application.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    OrderServiceImpl orderServiceImpl;

    // buy or order products from the cart
    /**
     * here everything will be in draft state until finally the is order placed
     */
    @PostMapping(value = "/orderProductItems")
    private ResponseEntity<List<OrdersDto>> orderProducts(@RequestParam Long userId, @RequestParam List<Long> productIdList,
                                                          @RequestParam List<Long> productQuantityList, @RequestParam Long shippingAddressId, @RequestParam Long
                                                          billingAddressId, @RequestParam String paymentMode) {
        return new ResponseEntity<>(orderServiceImpl.orderProductItems(userId, productIdList, productQuantityList,
                shippingAddressId, billingAddressId, paymentMode), HttpStatus.OK);
    }

    // handles if user cancels in any step until the final order is placed
    /**
     need to delete the data which is stored in DB on next steps while ordering
     */
    @DeleteMapping(value = "/deleteOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced")
    private ResponseEntity<String> deleteOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced(@RequestParam List<Long>
                                                                                                      orderIds) {
        return new ResponseEntity<>(orderServiceImpl.removeOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced(orderIds),
                HttpStatus.OK);
    }

    // changing the orders state from DRAFT to ORDERED on final order placing or for changing the order state
    // by the seller
    @PutMapping(value = "/editOrderState")
    private ResponseEntity<List<OrdersDto>> editOrderStateInOrders(@RequestParam List<Long> orderIds,
                                                                   @RequestParam String orderState) {
        return new ResponseEntity<>(orderServiceImpl.editOrderStateInMyOrders(orderIds, orderState), HttpStatus.OK);
    }

    // get my orders by userId
    @GetMapping(value = "/getMyOrdersByUserId")
    private ResponseEntity<List<OrdersDto>> getMyOrdersByUserId(@RequestParam Long userId) {
        return new ResponseEntity<>(orderServiceImpl.getMyOrdersByUserId(userId),HttpStatus.OK);
    }

    // get my complete order details by orderId
    @GetMapping(value = "/getMyOrderDetailsByOrderId")
    private ResponseEntity<List<OrderDetailsDto>> getMyOrderDetails(@RequestParam Long orderId) {
        return new ResponseEntity<>(orderServiceImpl.getMyOrderDetailsByOrderId(orderId),
                HttpStatus.OK);
    }

}
