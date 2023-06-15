package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
import com.siddharth.application.dto.orderDtos.OrdersDto;
import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import com.siddharth.application.entity.orderEntities.OrdersEntity;
import com.siddharth.application.entity.productEntities.ProductEntity;
import com.siddharth.application.entity.productEntities.ProductInfoEntity;
import com.siddharth.application.repository.orderRepositories.OrderDetailsRepository;
import com.siddharth.application.repository.orderRepositories.OrdersRepository;
import com.siddharth.application.repository.productRepositories.ProductInfoRepository;
import com.siddharth.application.repository.productRepositories.ProductRepository;
import com.siddharth.application.repository.userRepositories.UserAddressRepository;
import com.siddharth.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.siddharth.application.constants.Constants.DELIVERY_CHARGES;
import static com.siddharth.application.constants.Constants.MINIMUM_DELIVERY_AMOUNT;
import static com.siddharth.application.constants.OrderConstants.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    OrdersRepository ordersRepository;


    @Override
    public List<OrdersDto> orderProductItems(Long userId, List<Long> productIds, Long shippingAddressId,
                                             Long billingAddressId, String paymentMode) {
        if (userId != null && shippingAddressId != null && !productIds.isEmpty()) {
            List<OrdersEntity> ordersEntityList = new ArrayList<>();
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            for (Long productId : productIds) {
                OrdersEntity ordersEntity = new OrdersEntity();
                ordersEntity.setUserId(userId);
                ordersEntity.setAddressId(shippingAddressId);
                ordersEntity.setProductId(productId);
                ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);
                if (ObjectUtils.isNotEmpty(productInfoEntity)) {
                    LocalDate deliveryDate = productInfoEntity.getDeliveryDate();
                    ordersEntity.setDeliveryDate(deliveryDate);
                }
                ordersEntity.setOrderPlacedDate(LocalDate.now());
                ordersEntity.setOrderState(DRAFT);
                if (ObjectUtils.isNotEmpty(ordersEntity)) {
                    ordersEntityList.add(ordersEntity);
                }
            }
            if (!ordersEntityList.isEmpty()) {
                for (OrdersEntity ordersEntity : ordersEntityList) {
                    ordersRepository.save(ordersEntity);
                    Long orderId = ordersEntity.getOrderId();
                    saveOrderDetails(orderId,productIds, billingAddressId, shippingAddressId, paymentMode);
                    ordersDtoList.add(ordersEntity.toOrdersDto());
                }
            }
            return ordersDtoList;
        }
        return null;
    }

    private void saveOrderDetails(Long orderId, List<Long> productIds, Long billingAddressId,
                                  Long shippingAddressId, String paymentMode) {
        if (!productIds.isEmpty()) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
            Long totalItems = 0L;
            Long deliveryCharges = 0L;
            Double totalAmount = 0D;
            Long taxCharges = 0L;
            for (Long productId : productIds) {
                ProductEntity productEntity = productRepository.findByProductId(productId);
                totalItems = totalItems + 1;
                totalAmount = totalAmount + Double.valueOf(productEntity.getPrice());
            }
            orderDetailsDto.setOrderId(orderId);
            orderDetailsDto.setShippingAddressId(shippingAddressId);
            orderDetailsDto.setBillingAddressId(billingAddressId);
            orderDetailsDto.setPaymentMethod(paymentMode);
            orderDetailsDto.setTotalItems(totalItems);
            if (totalAmount < MINIMUM_DELIVERY_AMOUNT) {
                totalAmount = totalAmount + DELIVERY_CHARGES;
                deliveryCharges = DELIVERY_CHARGES;
            }
            orderDetailsDto.setDeliveryCharges(deliveryCharges);
            orderDetailsDto.setTaxCharges(taxCharges);
            orderDetailsDto.setTotalAmount(totalAmount);
            OrderDetailsEntity orderDetailsEntity = orderDetailsDto.toOrderDetailsEntity();
            orderDetailsRepository.save(orderDetailsEntity);
        }
    }

    @Override
    public String removeOrdersAndOrderDetailsOnCancelBeforeOrderIsPlaced(List<Long> orderIds) {
        if (!orderIds.isEmpty()) {
            for (Long orderId : orderIds) {
                List<OrdersEntity> ordersEntityList = ordersRepository.findByOrderId(orderId);
                if (!ordersEntityList.isEmpty()) {
                    for (OrdersEntity ordersEntity : ordersEntityList) {
                        ordersRepository.delete(ordersEntity);
                    }
                } else {
                    return ORDER_DETAILS_NOT_FOUND;
                }
            }
        }
        return DELETED_ORDERS;
    }

    @Override
    public List<OrdersDto> editOrderStateInMyOrders(List<Long> orderIds, String orderState) {
        if (!orderIds.isEmpty()) {
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            for (Long orderId : orderIds) {
                List<OrdersEntity> ordersEntityList = ordersRepository.findByOrderId(orderId);
                if (!ordersEntityList.isEmpty()) {
                    for (OrdersEntity ordersEntity : ordersEntityList) {
                        ordersEntity.setOrderState(orderState);
                        ordersRepository.save(ordersEntity);
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                }
            }
            return ordersDtoList;
        }
        return new ArrayList<>();
    }
}
