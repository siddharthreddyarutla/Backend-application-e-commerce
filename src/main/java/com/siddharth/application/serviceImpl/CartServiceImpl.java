package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.CartCompleteDto;
import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.dto.PreOrderDetailsDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.entity.CartOrWishlistEntity;
import com.siddharth.application.entity.PreOrderDetailsEntity;
import com.siddharth.application.entity.ProductEntity;
import com.siddharth.application.entity.ProductInfoEntity;
import com.siddharth.application.repository.CartRepository;
import com.siddharth.application.repository.PreOrderDetailsRepository;
import com.siddharth.application.repository.ProductInfoRepository;
import com.siddharth.application.repository.ProductRepository;
import com.siddharth.application.service.CartService;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.siddharth.application.constants.Constants.*;

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
}
