package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.*;
import com.siddharth.application.entity.*;
import com.siddharth.application.repository.*;
import com.siddharth.application.service.CartService;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.siddharth.application.constants.Constants.*;
import static com.siddharth.application.constants.OrderConstants.*;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    PreOrderDetailsRepository preOrderDetailsRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Override
    public CartOrWishlistDto addToCart(CartOrWishlistDto cartOrWishlistDto) {
        CartOrWishlistEntity cartOrWishlistEntity = cartRepository
                .findByUserIdAndProductId(cartOrWishlistDto.getUserId(), cartOrWishlistDto.getProductId());
        if (cartOrWishlistEntity == null) {
            CartOrWishlistEntity cartOrWishlistEntity1 = cartOrWishlistDto.toCartOrWishlistEntity();
            cartRepository.save(cartOrWishlistEntity1);
        } else {
            Long quantity = cartOrWishlistEntity.getQuantity() + 1L;
            cartOrWishlistEntity.setQuantity(quantity);
            cartRepository.save(cartOrWishlistEntity);
        }
        /**
         * Below function is used to post the pre-order details of my cart of the respective user
         */
        postPreOrderDetailsForCart(cartOrWishlistDto.getUserId());
        return cartOrWishlistDto;
    }

    @Override
    public List<ProductDto> getAllProductsFromCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);

        if (!cartOrWishlistEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                if (cartOrWishlistEntity.getCartState().equals(CART)) {
                    ProductEntity productEntity = productRepository.findByProductId(cartOrWishlistEntity.getProductId());
                    ProductDto productDto = productEntity.toProductDto();
                    productDtoList.add(productDto);
                }
            }
            return productDtoList;
        }
        return new ArrayList<>(); // Return an empty list if cartOrWishlistEntityList is empty
    }

    @Override
    public List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);

        if (!cartOrWishlistEntityList.isEmpty()) {
            List<CartCompleteDto> cartCompleteDtoList = new ArrayList<>();
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                if (cartOrWishlistEntity.getCartState().equals(CART)) {
                    ProductEntity productEntity = productRepository.findByProductId(cartOrWishlistEntity.getProductId());
                    ProductInfoEntity productInfoEntity = productInfoRepository
                            .findByProductId(cartOrWishlistEntity.getProductId());

                    CartCompleteDto cartCompleteDto = new CartCompleteDto(cartOrWishlistEntity, productEntity, productInfoEntity);
                    cartCompleteDtoList.add(cartCompleteDto);
                }
            }
            return cartCompleteDtoList;
        }
        return new ArrayList<>();
    }

    // posting pre-order cart details in the DB when ever the product is added it to cart by the respective user
    public void postPreOrderDetailsForCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);

        if (!cartOrWishlistEntityList.isEmpty()) {
            PreOrderDetailsDto preOrderDetailsDto = new PreOrderDetailsDto();
            List<LocalDate> deliveryDateList = new ArrayList<>();
            Long totalItemsInCart = 0L;
            Double totalAmount = 0D;
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                Long productId = cartOrWishlistEntity.getProductId();
                ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);
                deliveryDateList.add(productInfoEntity.getDeliveryDate());

                ProductEntity productEntity = productRepository.findByProductId(productId);
                Long price = productEntity.getPrice();
                Long quantity = cartOrWishlistEntity.getQuantity();
                Double totalPrice = Double.valueOf(price * quantity);
                totalAmount = totalAmount + totalPrice;
                totalItemsInCart = totalItemsInCart + quantity;
            }
            String eligibleForFreeDelivery = eligibilityOfOrder(totalAmount);
            LocalDate expectedDeliveryDate = findAverageOfDeliveryDates(deliveryDateList);

            preOrderDetailsDto.setUserId(userId);
            preOrderDetailsDto.setExpectedDeliveryDate(expectedDeliveryDate);
            preOrderDetailsDto.setEligibleForFreeDelivery(eligibleForFreeDelivery);
            preOrderDetailsDto.setTotalItemsInCart(totalItemsInCart);
            preOrderDetailsDto.setTotalAmount(totalAmount);
            PreOrderDetailsEntity preOrderDetailsEntity = preOrderDetailsDto.toPreOrderDetailsEntity();
            preOrderDetailsRepository.save(preOrderDetailsEntity);
        } else {
            PreOrderDetailsEntity preOrderDetailsEntity = preOrderDetailsRepository.findByUserId(userId);
            preOrderDetailsEntity.setExpectedDeliveryDate(null);
            preOrderDetailsEntity.setEligibleForFreeDelivery(null);
            preOrderDetailsEntity.setTotalItemsInCart(0L);
            preOrderDetailsEntity.setTotalAmount(0D);
            preOrderDetailsRepository.save(preOrderDetailsEntity);
        }
    }

    private LocalDate findAverageOfDeliveryDates(List<LocalDate> deliveryDateList) {
        if (!deliveryDateList.isEmpty()) {
            deliveryDateList.sort(Comparator.naturalOrder());
            LocalDate firstDate = deliveryDateList.get(0);

            Long totaldays = deliveryDateList.stream()
                    .mapToLong(date -> ChronoUnit.DAYS.between(firstDate, date))
                    .sum();

            Long averageDays = totaldays / deliveryDateList.size();
            LocalDate averageDate = firstDate.plusDays(averageDays);

            return averageDate;
        } else {
            return null;
        }
    }

    public String eligibilityOfOrder(Double totalAmount) {
        String eligibleForFreeDelivery = " ";
        if (totalAmount > MINIMUM_DELIVERY_AMOUNT) {
            eligibleForFreeDelivery = ORDER_ELIGIBLE_FOR_FREE_DELIVERY;
        } else {
            Double remainingAmountForFreeDelivery = MINIMUM_DELIVERY_AMOUNT - totalAmount;
            eligibleForFreeDelivery = "Add Worth Rupees " +  remainingAmountForFreeDelivery  +
                    " of eligible items to your order to qualify for FREE Delivery.";
        }
        return eligibleForFreeDelivery;
    }

    @Override
    public PreOrderDetailsDto getPreOrderDetailsOfCartByUserId(Long userId) {
        PreOrderDetailsEntity preOrderDetailsEntity = preOrderDetailsRepository.findByUserId(userId);
        if (!ObjectUtils.isEmpty(preOrderDetailsEntity)) {
            PreOrderDetailsDto preOrderDetailsDto = preOrderDetailsEntity.toPreOrderDetailsDto();
            return preOrderDetailsDto;
        }
        return null;
    }

    @Override
    public String deleteProductFromCart(Long userId, Long productId) {
        CartOrWishlistEntity cartOrWishlistEntity = cartRepository.findByUserIdAndProductId(userId, productId);
        if (!ObjectUtils.isEmpty(cartOrWishlistEntity)) {
            cartRepository.delete(cartOrWishlistEntity);
            // to update pre-order details of cart after deleting items from the cart
            postPreOrderDetailsForCart(cartOrWishlistEntity.getUserId());
            return ITEM_REMOVED_FROM_CART;
        }
        return ERROR_REMOVING_ITEM_FROM_CART;
    }

    @Override
    public OrderDetailsDto orderProductItems(OrderDetailsDto orderDetailsDto) {
        if (!ObjectUtils.isEmpty(orderDetailsDto)) {
            String productIds = orderDetailsDto.getProductId();
            String[] productIdsList = productIds.split(",");
            Long totalItems = 0L;
            Double totalAmount = 0D;
            Long deliveryCharges = 0L;
            for (String productId : productIdsList) {
                ProductEntity productEntity = productRepository.findByProductId(Long.valueOf(productId));
                if (ObjectUtils.isNotEmpty(productEntity)) {
                    totalAmount = totalAmount + productEntity.getPrice();
                    totalItems++;
                }
            }
            if (totalAmount < MINIMUM_DELIVERY_AMOUNT) {
                deliveryCharges = DELIVERY_CHARGES;
            }
            OrderDetailsEntity orderDetailsEntity = orderDetailsDto.toOrderDetailsEntity();
            orderDetailsEntity.setTotalItems(totalItems);
            orderDetailsEntity.setDeliveryCharges(deliveryCharges);
            orderDetailsEntity.setTotalAmount(totalAmount);
            orderDetailsEntity.setOrderState(ORDERED);
            orderDetailsRepository.save(orderDetailsEntity);
        }
        return null;
    }

    @Override
    public String updateOrderState(Long orderId, String orderState) {
        if (!orderState.isEmpty()) {
            List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByOrderId(orderId);
            for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
                if (ObjectUtils.isNotEmpty(orderDetailsEntity)) {
                    orderDetailsEntity.setOrderState(orderState);
                    orderDetailsRepository.save(orderDetailsEntity);
                    return orderState;
                }
            }
        }
        return null;
    }

    @Override
    public String deleteCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);
        if (!cartOrWishlistEntityList.isEmpty()) {
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                cartRepository.delete(cartOrWishlistEntity);
            }
            // to update pre-order details of cart after deleting items from the cart
            postPreOrderDetailsForCart(userId);
            return CART_IS_EMPTY;
        }
        return null;
    }

    @Override
    public List<OrderDetailsDto> getAllOrderDetails() {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findAll();
        if (!orderDetailsEntityList.isEmpty()) {
            List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
            for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
                orderDetailsDtoList.add(orderDetailsEntity.toOrderDetailsEntity());
            }
            return orderDetailsDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsDto> getMyOrders(Long userId) {
        if (userId != null) {
            List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByUserId(userId);
            if (!orderDetailsEntityList.isEmpty()) {
                List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
                for (OrderDetailsEntity  orderDetailsEntity : orderDetailsEntityList) {
                    orderDetailsDtoList.add(orderDetailsEntity.toOrderDetailsEntity());
                }
                return orderDetailsDtoList;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsCompleteDto> getMyOrdersCompleteDetails(Long userId) {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByUserId(userId);

        if (!orderDetailsEntityList.isEmpty()) {
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList =
                    convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
            return orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    public List<OrderDetailsCompleteDto> convertToOrderDetailsCompleteDetailsDto(List<OrderDetailsEntity>
                                                                                         orderDetailsEntityList) {
        List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList = new ArrayList<>();
        if (!orderDetailsEntityList.isEmpty()) {
            for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
                List<UserAddressEntity> userAddressEntityList = userAddressRepository
                        .findByUserId(orderDetailsEntity.getUserId());
                String[] productIds = orderDetailsEntity.getProductId().split(",");

                List<ProductEntity> productEntityList = new ArrayList<>();
                List<ProductDto> productDtoList = new ArrayList<>();
                for (String productId : productIds) {
                    ProductEntity productEntity = productRepository.findByProductId(Long.valueOf(productId));
                    productEntityList.add(productEntity);
                }
                OrderDetailsCompleteDto orderDetailsCompleteDto =  new OrderDetailsCompleteDto();
                for (UserAddressEntity userAddressEntity : userAddressEntityList) {
                    orderDetailsCompleteDto.setUserAddressDto(userAddressEntity.toUserAddressDto());
                }
                for (ProductEntity productEntity : productEntityList) {
                    productDtoList.add(productEntity.toProductDto());
                }
                orderDetailsCompleteDto.setProductDtoList(productDtoList);
                orderDetailsCompleteDto.setOrderPlacedDate(orderDetailsEntity.getOrderPlacedDate());
                orderDetailsCompleteDto.setDeliveryDate(orderDetailsEntity.getDeliveryDate());
                orderDetailsCompleteDto.setPaymentMethod(orderDetailsEntity.getPaymentMethod());
                orderDetailsCompleteDto.setTotalItems(orderDetailsEntity.getTotalItems());
                orderDetailsCompleteDto.setDeliveryCharges(orderDetailsEntity.getDeliveryCharges());
                orderDetailsCompleteDto.setTotalAmount(orderDetailsEntity.getTotalAmount());
                orderDetailsCompleteDto.setOrderState(orderDetailsEntity.getOrderState());
                orderDetailsCompleteDtoList.add(orderDetailsCompleteDto);
            }
            return  orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsCompleteDto> searchByDeliveryDateBetween(LocalDate beforeDate, LocalDate afterDate) {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository
                .findByDeliveryDateBetween(beforeDate, afterDate);
        if (!orderDetailsEntityList.isEmpty()) {
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList =
                    convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
            return orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsCompleteDto> searchByDeliveryDateBefore(LocalDate beforeDate) {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository
                .findByDeliveryDateLessThanEqual(beforeDate);
        if (!orderDetailsEntityList.isEmpty()) {
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList =
                    convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
            return orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsCompleteDto> searchByDeliveryDateAfter(LocalDate afterDate) {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository
                .findByDeliveryDateGreaterThanEqual(afterDate);
        if (!orderDetailsEntityList.isEmpty()) {
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList =
                    convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
            return orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderDetailsCompleteDto> getCancelledOrders(Long userId) {
        List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByUserId(userId);

        List<OrderDetailsEntity> orderDetailsEntities = new ArrayList<>();
        for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntityList) {
            if (orderDetailsEntity.getOrderState().equals(CANCELLED)) {
                orderDetailsEntities.add(orderDetailsEntity);
            }
        }
        if (!orderDetailsEntities.isEmpty()) {
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList =
                    convertToOrderDetailsCompleteDetailsDto(orderDetailsEntities);
            return orderDetailsCompleteDtoList;
        }
        return new ArrayList<>();
    }

    //You can search by product title, order number, brand, category, or recipient name.
    @Override
    public List<OrderDetailsCompleteDto> searchByAttributesInOrderDetails(Long userId, String attribute) {
        // find by user id to get related order details to search or filter
        List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepository.findByUserId(userId);

        // the list to store and return the searched order details list based on attribute
        List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList = new ArrayList<>();
        // to store the list of products on search on title, brand, category
        List<ProductEntity> productEntityList = new ArrayList<>();
        if (attribute != null && userId != null) {
            // search by title
            List<ProductEntity> productEntityList1 = productRepository.findByTitle(attribute);
            // search by brand
            List<ProductEntity> productEntityList2  =productRepository.findByBrand(attribute);
            // search by category
            List<ProductEntity> productEntityList3 = productRepository.findByCategory(attribute);

            if (!productEntityList1.isEmpty()) {
                productEntityList.addAll(productEntityList1);
            }
            if (!productEntityList2.isEmpty()) {
                productEntityList.addAll(productEntityList2);
            }
            if (!productEntityList3.isEmpty()) {
                productEntityList.addAll(productEntityList3);
            }

            // search by recipient name
            List<UserAddressEntity> userAddressEntityList = userAddressRepository.findByFullName(attribute);

            // to add all product entities list those are matched or got on search
            List<ProductEntity> productEntities = new ArrayList<>();
            // creating a constructor for adding the list of product list
            OrderDetailsCompleteDto orderDetailsCompleteDto = new OrderDetailsCompleteDto();
            // to get addressId to map the address and to add in constructor to return
            Long addressId = null;
            if (!productEntityList.isEmpty()) {
                for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
                    String[] productIds = orderDetailsEntity.getProductId().split(",");
                    for (ProductEntity productEntity : productEntityList) {
                        for (String productId : productIds) {
                            if (productEntity.getProductId().equals(Long.valueOf(productId))) {
                                productEntities.add(productEntity);
                                addressId = orderDetailsEntity.getAddressId();
                                orderDetailsCompleteDto.setOrderPlacedDate(orderDetailsEntity.getOrderPlacedDate());
                                orderDetailsCompleteDto.setDeliveryDate(orderDetailsEntity.getDeliveryDate());
                                orderDetailsCompleteDto.setPaymentMethod(orderDetailsEntity.getPaymentMethod());
                                orderDetailsCompleteDto.setTotalItems(orderDetailsEntity.getTotalItems());
                                orderDetailsCompleteDto.setDeliveryCharges(orderDetailsEntity.getDeliveryCharges());
                                orderDetailsCompleteDto.setTotalAmount(orderDetailsEntity.getTotalAmount());
                                orderDetailsCompleteDto.setOrderState(orderDetailsEntity.getOrderState());
                            }
                        }
                    }
                }
            }

            // find by addressId to add into the orderDetailsCompleteDto
            UserAddressEntity userAddressEntity = userAddressRepository.findByAddressId(addressId);
            // set user address dto in orderDetailsCompleteDto
            if (ObjectUtils.isNotEmpty(userAddressEntity)) {
                orderDetailsCompleteDto.setUserAddressDto(userAddressEntity.toUserAddressDto());
            }

            // converting product entity list to product dto list to add in orderDetailsCompleteDto
            List<ProductDto> productDtoList = new ArrayList<>();
            if (!productEntities.isEmpty()) {
                for (ProductEntity productEntity : productEntities) {
                    productDtoList.add(productEntity.toProductDto());
                }
            }

            // set product list in orderDetailsCompleteDto
            if (!productDtoList.isEmpty()) {
                orderDetailsCompleteDto.setProductDtoList(productDtoList);
            }
            // add in temporary list
            List<OrderDetailsCompleteDto> orderDetailsCompleteDtoList1 = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(orderDetailsCompleteDto)) {
                orderDetailsCompleteDtoList1.add(orderDetailsCompleteDto);
            }
            // adding the temp list in final orderDetailsCompleteDtoList
            if (!orderDetailsCompleteDtoList1.isEmpty()) {
                orderDetailsCompleteDtoList.addAll(orderDetailsCompleteDtoList1);
            }

            try {
                // find by orderId
                Long orderId = Long.valueOf(attribute);
                List<OrderDetailsEntity> orderDetailsEntityList = orderDetailsRepository.findByOrderId(orderId);

                // converting to orderDetailsCompleteDtoList
                if (!orderDetailsEntityList.isEmpty()) {
                    orderDetailsCompleteDtoList = convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid attribute value. Unable to convert to Long.");
                e.printStackTrace();
            }

            // to add user address entity found by recipient name and adding it to final list
            List<OrderDetailsEntity> orderDetailsEntityList = new ArrayList<>();
            if (!userAddressEntityList.isEmpty()) {
                for (UserAddressEntity userAddressEntity1 : userAddressEntityList) {
                    for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
                        if (userAddressEntity1.getAddressId().equals(orderDetailsEntity.getAddressId())) {
                            orderDetailsEntityList.add(orderDetailsEntity);
                        }
                    }
                }
                orderDetailsCompleteDtoList =
                        convertToOrderDetailsCompleteDetailsDto(orderDetailsEntityList);
            }
        }
        // at the end check whether there is any match in search and add to orderDetailsCompleteDtoList and return
        if (!orderDetailsCompleteDtoList.isEmpty()) {
            return orderDetailsCompleteDtoList;
        }
        // else return empty list
        return new ArrayList<>();
    }
}
