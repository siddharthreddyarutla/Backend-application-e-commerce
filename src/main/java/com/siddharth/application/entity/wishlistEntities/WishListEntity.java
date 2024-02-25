package com.siddharth.application.entity.wishlistEntities;

import com.siddharth.application.dto.wishlistDtos.WishlistDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WISHLIST")
public class WishListEntity {
  @Id
  @Column(name = "WISHLIST_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long wishlistId;
  @Column(name = "WISHLIST_NAME")
  private String wishlistName;
  @Column(name = "USER_ID")
  private Long userId;
  @Column(name = "PRODUCT_ID")
  private Long productId;

  public WishlistDto toWishlistDto() {
    return WishlistDto.builder().wishlistId(wishlistId).wishlistName(wishlistName).userId(userId)
        .productId(productId).build();
  }
}
