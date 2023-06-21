package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.orderDtos.*;
import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.entity.userEntities.UserAddressEntity;
import net.bytebuddy.asm.Advice;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.PrimitiveIterator;

import static com.siddharth.application.constants.Constants.DELIVERY_CHARGES;
import static com.siddharth.application.constants.Constants.MINIMUM_DELIVERY_AMOUNT;
import static com.siddharth.application.constants.DateConstants.*;
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

    @Autowired
    CartServiceImpl cartServiceImpl;

    @Override
    public List<OrdersDto> orderProductItems(Long userId, List<Long> productIdList,List<Long> productQuantityList,
                                             Long shippingAddressId, Long billingAddressId, String paymentMode) {
        if (userId != null && shippingAddressId != null && !productIdList.isEmpty()) {
            List<OrdersEntity> ordersEntityList = new ArrayList<>();
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            for (int id = 0; id < productIdList.size(); id++) {
                Long productId = productIdList.get(id);
                Long quantity = productQuantityList.get(id);

                OrdersEntity ordersEntity = new OrdersEntity();
                ordersEntity.setUserId(userId);
                ordersEntity.setAddressId(shippingAddressId);
                ordersEntity.setProductId(productId);
                ordersEntity.setQuantity(quantity);

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
                    saveOrderDetails(orderId,productIdList,productQuantityList, billingAddressId, shippingAddressId, paymentMode);
                    ordersDtoList.add(ordersEntity.toOrdersDto());
                }
            }
            return ordersDtoList;
        }
        return null;
    }

    private void saveOrderDetails(Long orderId, List<Long> productIdList,List<Long> productQuantityList, Long billingAddressId,
                                  Long shippingAddressId, String paymentMode) {
        if (!productIdList.isEmpty()) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
            Long totalItems = 0L;
            Long deliveryCharges = 0L;
            Double totalAmount = 0D;
            Double orderAmount = 0D;
            Long taxCharges = 0L;
            for (int id = 0; id < productIdList.size(); id++) {
                Long productId = productIdList.get(id);
                Long quantity = productQuantityList.get(id);

                ProductEntity productEntity = productRepository.findByProductId(productId);
                totalItems = totalItems + 1;
                totalAmount = totalAmount + Double.valueOf(productEntity.getPrice()) * quantity;
            }

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("productIds", productIdList);
            String jsonString = jsonObject.toString();
            orderDetailsDto.setProductIds(jsonString);

            orderDetailsDto.setOrderId(orderId);
            orderDetailsDto.setShippingAddressId(shippingAddressId);
            orderDetailsDto.setBillingAddressId(billingAddressId);
            orderDetailsDto.setPaymentMethod(paymentMode);
            orderDetailsDto.setTotalItems(totalItems);
            if (totalAmount < MINIMUM_DELIVERY_AMOUNT) {
                orderAmount = totalAmount + DELIVERY_CHARGES;
                deliveryCharges = DELIVERY_CHARGES;
            } else {
                orderAmount = totalAmount;
            }
            orderDetailsDto.setDeliveryCharges(deliveryCharges);
            orderDetailsDto.setTaxCharges(taxCharges);
            orderDetailsDto.setTotalAmount(totalAmount);
            orderDetailsDto.setOrderAmount(orderAmount);
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

    @Override
    public List<OrdersCompleteDto> getMyOrdersByUserId(Long userId) {
        if (userId != null) {
            List<OrdersEntity> ordersEntityList = ordersRepository.findByUserId(userId);
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            List<OrdersCompleteDto> ordersCompleteDtoList = new ArrayList<>();

            for (OrdersEntity ordersEntity : ordersEntityList) {
                if (!ordersEntity.getOrderState().equals(DRAFT)) {
                    ordersDtoList.add(ordersEntity.toOrdersDto());
                }
            }
            if (!ordersDtoList.isEmpty()) {
                ordersCompleteDtoList = convertOrdersDtoListToOrderCompleteDtoList(ordersDtoList);
            }
            return ordersCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsDto> getMyOrderDetailsByOrderId(Long orderId) {
        if (orderId != null) {
            List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByOrderId(orderId);
            List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();

            if (!orderDetailsEntityList.isEmpty()) {
                for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
                    orderDetailsEntity.getProductIds().toString();
                    orderDetailsDtoList.add(orderDetailsEntity.toOrderDetailsDto());
                }
            }
            return orderDetailsDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public OrderDetailsCompleteDto getMyCompleteOrderDetailsByOrderId(Long userId, Long orderId) {
        OrdersEntity ordersEntity = ordersRepository.findByUserIdAndOrderId(userId, orderId);

        if (ObjectUtils.isNotEmpty(ordersEntity)) {
            List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByOrderId(orderId);

            if (!orderDetailsEntityList.isEmpty()) {
                OrderDetailsCompleteDto orderDetailsCompleteDto = new OrderDetailsCompleteDto();
                for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
                    String productIds = orderDetailsEntity.getProductIds();

                    JSONObject jsonObject = new JSONObject(productIds);
                    JSONArray productIdsArray = jsonObject.getJSONArray("productIds");

                    List<ProductEntity> productEntityList = new ArrayList<>();

                    for (int id = 0; id < productIdsArray.length(); id++) {
                        Long productId = productIdsArray.getLong(id);
                        ProductEntity productEntity = productRepository.findByProductId(productId);
                        productEntityList.add(productEntity);
                    }

                    Long shippingAddressId = orderDetailsEntity.getShippingAddressId();
                    Long billingAddressId = orderDetailsEntity.getBillingAddressId();
                    UserAddressEntity userShippingAddress = userAddressRepository.findByAddressId(shippingAddressId);
                    UserAddressEntity userBillingAddress = userAddressRepository.findByAddressId(billingAddressId);

                    List<ProductDto> productDtoList = new ArrayList<>();
                    for (ProductEntity productEntity : productEntityList) {
                        productDtoList.add(productEntity.toProductDto());
                    }
                    if(!productDtoList.isEmpty()) {
                        orderDetailsCompleteDto.setProductDtoList(productDtoList);
                    }
                    if (ObjectUtils.isNotEmpty(userBillingAddress)) {
                        orderDetailsCompleteDto.setBillingAddressDto(userBillingAddress.toUserAddressDto());
                    }
                    if (ObjectUtils.isNotEmpty(userShippingAddress)) {
                        orderDetailsCompleteDto.setShippingAddressDto(userShippingAddress.toUserAddressDto());
                    }

                    orderDetailsCompleteDto.setPaymentMethod(orderDetailsEntity.getPaymentMethod());
                    orderDetailsCompleteDto.setTotalItems(orderDetailsEntity.getTotalItems());
                    orderDetailsCompleteDto.setTotalAmount(orderDetailsEntity.getTotalAmount());
                    orderDetailsCompleteDto.setDeliveryCharges(orderDetailsEntity.getDeliveryCharges());
                    orderDetailsCompleteDto.setTaxCharges(orderDetailsEntity.getTaxCharges());
                    orderDetailsCompleteDto.setOrderAmount(orderDetailsEntity.getOrderAmount());
                    orderDetailsCompleteDto.setQuantity(ordersEntity.getQuantity());
                    orderDetailsCompleteDto.setDeliveryDate(ordersEntity.getDeliveryDate());

                }
                if (ObjectUtils.isNotEmpty(orderDetailsCompleteDto)) {
                    return orderDetailsCompleteDto;
                }
            }
        }
        return null;
    }

    @Override
    public List<OrdersCompleteDto> searchByProductTitleCategoryOrderIdAddressAndRecipientName(Long userId, String attribute) {
        if (userId != null) {
            List<OrdersEntity> ordersEntityList = ordersRepository.findByUserId(userId);
            List<OrdersDto> ordersDtoList = new ArrayList<>();

            if (!ordersEntityList.isEmpty()) {

                for (OrdersEntity ordersEntity : ordersEntityList) {
                    ProductEntity productEntity = productRepository
                            .findByProductIdAndTitle(ordersEntity.getProductId(), attribute);
                    ProductEntity productEntity1 = productRepository
                            .findByProductIdAndCategory(ordersEntity.getProductId(), attribute);

                    if (ObjectUtils.isNotEmpty(productEntity) || ObjectUtils.isNotEmpty(productEntity1)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }

                    List<OrdersDto> ordersDtoList1 = new ArrayList<>();
                    try {
                        Long orderId = Long.valueOf(attribute);
                        if (orderId == ordersEntity.getOrderId()) {
                            List<OrdersEntity> ordersEntityList1 = ordersRepository.findByOrderId(orderId);
                            for (OrdersEntity ordersEntity1 : ordersEntityList1) {
                                OrdersDto ordersDto = ordersEntity1.toOrdersDto();
                                if (!ordersDtoList1.contains(ordersDto)) {
                                    ordersDtoList1.add(ordersDto);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        log.info(String.valueOf(e));
                    }
                    if (!ordersDtoList1.isEmpty()) {
                        for (OrdersDto ordersDto : ordersDtoList1) {
                            ordersDtoList.add(ordersDto);
                        }
                    }

                    UserAddressEntity userAddressEntity = userAddressRepository
                            .findByAddressIdAndFullName(ordersEntity.getAddressId(), attribute);

                    if (ObjectUtils.isNotEmpty(userAddressEntity)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                }
            }
            List<OrdersCompleteDto> ordersCompleteDtoList = new ArrayList<>();
            if (!ordersDtoList.isEmpty()) {
                ordersCompleteDtoList = convertOrdersDtoListToOrderCompleteDtoList(ordersDtoList);
            }
            if (!ordersCompleteDtoList.isEmpty()) {
                return ordersCompleteDtoList;
            }
        }
        return new ArrayList<>();
    }

    private List<OrdersCompleteDto> convertOrdersDtoListToOrderCompleteDtoList(List<OrdersDto> ordersDtoList) {
        List<OrdersCompleteDto> ordersCompleteDtoList = new ArrayList<>();
        for (OrdersDto ordersDto : ordersDtoList) {
            OrdersCompleteDto ordersCompleteDto = new OrdersCompleteDto();

            UserAddressEntity userAddressEntity = userAddressRepository.findByAddressId(ordersDto.getAddressId());
            ProductEntity productEntity = productRepository.findByProductId(ordersDto.getProductId());
            ordersCompleteDto.setOrderId(ordersDto.getOrderId());
            ordersCompleteDto.setUserId(ordersDto.getUserId());
            if (ObjectUtils.isNotEmpty(userAddressEntity)) {
                ordersCompleteDto.setUserAddressDto(userAddressEntity.toUserAddressDto());
            }
            if (ObjectUtils.isNotEmpty(productEntity)) {
                ordersCompleteDto.setProductDto(productEntity.toProductDto());
            }
            ordersCompleteDto.setQuantity(ordersDto.getQuantity());
            ordersCompleteDto.setOrderPlacedDate(ordersDto.getOrderPlacedDate());
            ordersCompleteDto.setDeliveryDate(ordersDto.getDeliveryDate());
            ordersCompleteDto.setOrderState(ordersDto.getOrderState());
            if (ObjectUtils.isNotEmpty(ordersCompleteDto)) {
                ordersCompleteDtoList.add(ordersCompleteDto);
            }
        }
        return ordersCompleteDtoList;
    }

    @Override
    public OrderPlacedDetailsDto getOrderPlacedDetailsConfirmation(Long userId, List<Long> orderIds) {
        List<OrdersEntity> ordersEntityList = new ArrayList<>();
        if (userId != null && !orderIds.isEmpty()) {
            OrderPlacedDetailsDto orderPlacedDetailsDto = new OrderPlacedDetailsDto();
            for (Long orderId : orderIds) {
                OrdersEntity ordersEntity = ordersRepository.findByUserIdAndOrderId(userId, orderId);
                if (ObjectUtils.isNotEmpty(ordersEntity)) {
                    ordersEntityList.add(ordersEntity);
                }
            }
            if (!ordersEntityList.isEmpty()) {
                List<String> images = new ArrayList<>();
                List<LocalDate> deliveryDatesList = new ArrayList<>();
                Long addressId = 0L;

                for (OrdersEntity ordersEntity : ordersEntityList) {
                    addressId = ordersEntity.getAddressId();
                    ProductEntity productEntity = productRepository.findByProductId(ordersEntity.getProductId());
                    ProductInfoEntity productInfoEntity = productInfoRepository
                            .findByProductId(ordersEntity.getProductId());
                    if (ObjectUtils.isNotEmpty(productEntity) && ObjectUtils.isNotEmpty(productInfoEntity)) {
                        images.add(productEntity.getImage());
                        deliveryDatesList.add(productInfoEntity.getDeliveryDate());
                    }
                }
                LocalDate deliveryDate = cartServiceImpl.findAverageOfDeliveryDates(deliveryDatesList);
                UserAddressEntity userAddressEntity = userAddressRepository.findByAddressId(addressId);
                StringBuilder addressBuilder = new StringBuilder();
                addressBuilder.append(userAddressEntity.getHouseNo()).append(", ")
                        .append(userAddressEntity.getVillageOrStreet()).append(", ")
                        .append(userAddressEntity.getCityOrTown()).append(", ")
                        .append(userAddressEntity.getState()).append(", ")
                        .append(userAddressEntity.getPinCode()).append(", ")
                        .append(userAddressEntity.getCountry()).append(".");

                orderPlacedDetailsDto.setName(userAddressEntity.getFullName());
                orderPlacedDetailsDto.setCompleteAddress(addressBuilder);
                orderPlacedDetailsDto.setMobileNumber(userAddressEntity.getMobileNumber());
                orderPlacedDetailsDto.setDeliveryDate(deliveryDate);
                orderPlacedDetailsDto.setImages(images);
            }
            return orderPlacedDetailsDto;
        }
        return null;
    }

    @Override
    public List<OrdersCompleteDto> getMyFilteredOrdersOnOrderState(String orderType) {
        if (orderType != null) {
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            List<OrdersCompleteDto> ordersCompleteDtoList = new ArrayList<>();
            List<OrdersEntity> ordersEntityList = ordersRepository.findByOrderState(orderType);
            if (!ordersEntityList.isEmpty()) {
                for (OrdersEntity ordersEntity : ordersEntityList) {
                    ordersDtoList.add(ordersEntity.toOrdersDto());
                }
                ordersCompleteDtoList = convertOrdersDtoListToOrderCompleteDtoList(ordersDtoList);
            }
            return ordersCompleteDtoList;
        }
        return null;
    }

    @Override
    public List<OrdersCompleteDto> getFilterByOrderPlacedDateInMyOrders(Long userId, String attribute) {
        LocalDate currentDate = LocalDate.now();
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        List<OrdersCompleteDto> ordersCompleteDtoList = new ArrayList<>();

        List<OrdersEntity> ordersEntityList = ordersRepository.findByUserId(userId);
        if (!ordersEntityList.isEmpty()) {
            for (OrdersEntity ordersEntity : ordersEntityList) {
                LocalDate orderPlacedDate = ordersEntity.getOrderPlacedDate();
                if (attribute.equals(LAST_MONTH)) {
                    LocalDate lastMonthDate = currentDate.minusMonths(1);
                    if (orderPlacedDate.isBefore(currentDate) && orderPlacedDate.isAfter(lastMonthDate)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                }
                if (attribute.equals(LAST_THREE_MONTHS)) {
                    LocalDate lastThreeMonthsDate = currentDate.minusMonths(3);
                    if (orderPlacedDate.isBefore(currentDate) && orderPlacedDate.isBefore(lastThreeMonthsDate)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                }
                if (attribute.equals(LAST_SIX_MONTHS)) {
                    LocalDate lastSixMonthsDate = currentDate.minusMonths(6);
                    if (orderPlacedDate.isBefore(currentDate) && orderPlacedDate.isAfter(lastSixMonthsDate)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                }
                if (attribute.equals(LAST_ONE_YEAR)) {
                    LocalDate lastOneYearDate = currentDate.minusYears(1);
                    if (orderPlacedDate.isBefore(currentDate) && orderPlacedDate.isAfter(lastOneYearDate)) {
                        ordersDtoList.add(ordersEntity.toOrdersDto());
                    }
                } else {
                    try {
                        Long enteredYear = Long.valueOf(attribute);
                        Long previousYear = enteredYear - 1L;
                        int orderPlacedYear = orderPlacedDate.getYear();
                        if (orderPlacedYear > previousYear && orderPlacedYear <= enteredYear) {
                            ordersDtoList.add(ordersEntity.toOrdersDto());
                        }
                    } catch (NumberFormatException e) {
                        log.info(e.toString());
                    }
                }
            }
            ordersCompleteDtoList = convertOrdersDtoListToOrderCompleteDtoList(ordersDtoList);
            return ordersCompleteDtoList;
        }
        return null;
    }
}
