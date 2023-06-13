package com.siddharth.application.controller;

import com.siddharth.application.dto.orderDtos.OrderDetailsCompleteDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
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
    @PostMapping(value = "/orderProductItems")
    private ResponseEntity<OrderDetailsDto> orderProducts(@RequestBody OrderDetailsDto orderDetailsDto) {
        return new ResponseEntity<>(orderServiceImpl.orderProductItems(orderDetailsDto), HttpStatus.OK);
    }

    // get all orders history
    @GetMapping(value = "/getAllOrders")
    private ResponseEntity<List<OrderDetailsDto>> getAllOrders() {
        return new ResponseEntity<>(orderServiceImpl.getAllOrderDetails(), HttpStatus.OK);
    }

    // get all orders history by user id
    @GetMapping(value = "/getMyOrders")
    private ResponseEntity<List<OrderDetailsDto>> getMyOrderDetails(@RequestParam Long userId) {
        return new ResponseEntity<>(orderServiceImpl.getMyOrders(userId), HttpStatus.OK);
    }

    // get all complete order details by user id
    @GetMapping(value = "/getMyOrdersCompleteDetails")
    private ResponseEntity<List<OrderDetailsCompleteDto>> getMyOrdersCompleteDetails(@RequestParam Long userId) {
        return new ResponseEntity<>(orderServiceImpl.getMyOrdersCompleteDetails(userId), HttpStatus.OK);
    }

    // search orders by delivered date before or after or in between the dates
    @GetMapping(value = "/searchOrdersByDeliveryDate")
    private ResponseEntity<List<OrderDetailsCompleteDto>> searchByDeliveryDateBeforeOrAfterOrBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beforeDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate afterDate) {

        if (beforeDate != null && afterDate == null) {
            return new ResponseEntity<>(orderServiceImpl.searchByDeliveryDateBefore(beforeDate), HttpStatus.OK);
        } else if (afterDate != null && beforeDate == null) {
            return new ResponseEntity<>(orderServiceImpl.searchByDeliveryDateAfter(afterDate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderServiceImpl.searchByDeliveryDateBetween(beforeDate, afterDate), HttpStatus.OK);
        }
    }

    // get order details of orders cancelled
    @GetMapping(value = "/getCanceledOrderDetails")
    private ResponseEntity<List<OrderDetailsCompleteDto>> getCanceledOrderDetails(@RequestParam Long userId) {
        return new ResponseEntity<>(orderServiceImpl.getCancelledOrders(userId), HttpStatus.OK);
    }

    //You can search by product title, order number, brand, category, or recipient name.
    @GetMapping(value = "/searchByTitleByOrderByBrandByCategoryByReeceipientNameInOrderDetails")
    private ResponseEntity<List<OrderDetailsCompleteDto>> getSearchDataInOrderDetails(@RequestParam Long userId,
                                                                                      @RequestParam String attribute) {
        return new ResponseEntity<>(orderServiceImpl
                .searchByAttributesInOrderDetails(userId, attribute), HttpStatus.OK);
    }
}
