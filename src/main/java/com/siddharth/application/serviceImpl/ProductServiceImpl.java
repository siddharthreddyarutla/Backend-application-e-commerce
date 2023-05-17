package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.entity.ProductEntity;
import com.siddharth.application.repository.ProductRepository;
import com.siddharth.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.siddharth.application.constants.Constants.PRODUCT_DELETED;
import static com.siddharth.application.constants.Constants.PRODUCT_NOT_FOUND;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        ProductEntity productEntity = productDto.toProductEntity();
        productRepository.save(productEntity);
        return productDto;
    }

    @Override
    public List<ProductDto> getDetails() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        if(!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public ProductDto getDetailsById(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if(productEntity != null) {
            return productEntity.toProductDto();
        }
        return null;
    }

    @Override
    public String deleteDetailsById(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if(productEntity != null) {
            productRepository.delete(productEntity);
            return PRODUCT_DELETED;
        }
        return PRODUCT_NOT_FOUND;
    }

    @Override
    public ProductDto editDetailsById(Long productId, ProductDto productDto) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if(productEntity != null) {
            if(productDto.getTitle() != null) {
                productEntity.setTitle(productDto.getTitle());
            }
            if(productDto.getBrand() !=null) {
                productEntity.setBrand(productDto.getBrand());
            }
            if(productDto.getCategory() != null) {
                productEntity.setCategory(productDto.getCategory());
            }
            if(productDto.getPrice() != null) {
                productEntity.setPrice(productDto.getPrice());
            }
            if(productDto.getStock() != null) {
                productEntity.setStock(productDto.getStock());
            }
            if(productDto.getDiscountPercentage() != null) {
                productEntity.setDiscountPercentage(productDto.getDiscountPercentage());
            }
            if(productDto.getRating() != null) {
                productEntity.setRating(productDto.getRating());
            }
            if(productDto.getDescription() != null) {
                productEntity.setDescription(productDto.getDescription());
            }
            if(productDto.getImage() != null) {
                productEntity.setImage(productDto.getImage());
            }
            productRepository.save(productEntity);
            return productEntity.toProductDto();
        }
        return null;
    }

    @Override
    public ProductDto searchProductByCategory(String category) {
        ProductEntity productEntity = productRepository.findByCategory(category);
        if(productEntity != null) {
            return productEntity.toProductDto();
        }
        return null;
    }
}
