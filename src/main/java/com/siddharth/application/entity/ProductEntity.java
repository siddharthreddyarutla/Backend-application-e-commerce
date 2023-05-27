package com.siddharth.application.entity;

import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.dto.ProductPrimaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PRODUCT_DESCRIPTION")
public class ProductEntity {
    @Id
    @Column(name="PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name="TITLE")
    private String title;
    @Column(name="BRAND")
    private String brand;
    @Column(name="CATEGORY")
    private String category;
    @Column(name="PRICE")
    private Long price;
    @Column(name="STOCK")
    private Long stock;
    @Column(name="DISCOUNT")
    private Double discountPercentage;
    @Column(name="RATING")
    private Double rating;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="IMAGE")
    private String image;

    public ProductDto toProductDto() {
        return ProductDto.builder().productId(productId).title(title).brand(brand).category(category).price(price)
                .stock(stock).discountPercentage(discountPercentage).rating(rating).description(description).image(image).build();
    }
}
