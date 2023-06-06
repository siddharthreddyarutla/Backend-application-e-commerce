package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.ProductCompleteDetailsDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.dto.ProductInfoDto;
import com.siddharth.application.dto.ProductPrimaryDto;
import com.siddharth.application.entity.ProductEntity;
import com.siddharth.application.entity.ProductInfoEntity;
import com.siddharth.application.repository.ProductInfoRepository;
import com.siddharth.application.repository.ProductRepository;
import com.siddharth.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.siddharth.application.constants.Constants.*;
import static com.siddharth.application.constants.OrderConstants.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        ProductEntity productEntity = productDto.toProductEntity();
        productRepository.save(productEntity);
        return productDto;
    }

    @Override
    public List<ProductDto> getDetails() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public ProductDto getDetailsById(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if (productEntity != null) {
            return productEntity.toProductDto();
        }
        return null;
    }

    @Override
    public String deleteDetailsById(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if (productEntity != null) {
            productRepository.delete(productEntity);
            return PRODUCT_DELETED;
        }
        return PRODUCT_NOT_FOUND;
    }

    @Override
    public ProductDto editDetailsById(Long productId, ProductDto productDto) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if (productEntity != null) {
            if (!productDto.getTitle().isEmpty()) {
                productEntity.setTitle(productDto.getTitle());
            }
            if (!productDto.getBrand().isEmpty()) {
                productEntity.setBrand(productDto.getBrand());
            }
            if (!productDto.getCategory().isEmpty()) {
                productEntity.setCategory(productDto.getCategory());
            }
            if (productDto.getPrice() != null) {
                productEntity.setPrice(productDto.getPrice());
            }
            if (productDto.getStock() != null) {
                productEntity.setStock(productDto.getStock());
            }
            if (productDto.getDiscountPercentage() != null) {
                productEntity.setDiscountPercentage(productDto.getDiscountPercentage());
            }
            if (productDto.getRating() != null) {
                productEntity.setRating(productDto.getRating());
            }
            if (!productDto.getDescription().isEmpty()) {
                productEntity.setDescription(productDto.getDescription());
            }
            if (!productDto.getImage().isEmpty()) {
                productEntity.setImage(productDto.getImage());
            }
            productRepository.save(productEntity);
            return productEntity.toProductDto();
        }
        return null;
    }

    @Override
    public List<ProductDto> searchProductByCategory(String category) {
        List<ProductEntity> productEntityList = productRepository.findByCategory(category);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title) {
        List<ProductEntity> productEntityList = productRepository.findByTitle(title);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> searchProductByBrand(String brand) {
        List<ProductEntity> productEntityList = productRepository.findByBrand(brand);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> searchByHighestPrice(Long highestPrice) {
        List<ProductEntity> productEntityList = productRepository.findByPriceGreaterThanEqual(highestPrice);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> searchByLeastPriceAndHighestPrice(Long leastPrice, Long highestPrice) {
        List<ProductEntity> productEntityList = productRepository.findByPriceBetween(leastPrice, highestPrice);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> searchByLeastPrice(Long leastPrice) {
        List<ProductEntity> productEntityList =  productRepository.findByPriceLessThanEqual(leastPrice);
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> sortByRatingAsc() {
        List<ProductEntity> productEntityList = productRepository.findAllByOrderByRatingAsc();
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> sortByRatingDesc() {
        List<ProductEntity> productEntityList = productRepository.findAllByOrderByRatingDesc();
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> sortByDiscountAsc() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        productEntityList.sort(Comparator.comparing(ProductEntity :: getDiscountPercentage));
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    @Override
    public List<ProductDto> sortByDiscountDesc() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        productEntityList.sort(Comparator.comparing(ProductEntity :: getDiscountPercentage, Comparator.reverseOrder()));
        if (!productEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                productDtoList.add(productEntity.toProductDto());
            }
            return productDtoList;
        }
        return null;
    }

    // complete product details functions
    @Override
    public ProductCompleteDetailsDto postCompleteDetailsOfProduct(ProductCompleteDetailsDto productCompleteDetails) {
        try {
            ProductEntity productEntity = productCompleteDetails.getProductDto().toProductEntity();
            productRepository.save(productEntity);

            ProductInfoEntity productInfoEntity = productCompleteDetails.getProductInfoDto().toProductInfoEntity();
            productInfoEntity.setProductId(productEntity.getProductId());

            if(productCompleteDetails.getProductInfoDto().getProductQuantity() == 0) {
                productInfoEntity.setProductState(OUT_OF_STOCK);
            } else {
                productInfoEntity.setProductState(IN_STOCK);
            }
            productInfoRepository.save(productInfoEntity);

            return productCompleteDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductCompleteDetailsDto> getAllCompleteProductDetails() {
        try {
            List<ProductCompleteDetailsDto> productCompleteDetailsDtoList = new ArrayList<>();

            List<ProductEntity> productEntityList = productRepository.findAll();
            if (!productEntityList.isEmpty()) {
                for (ProductEntity productEntity : productEntityList) {
                    ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId
                            (productEntity.getProductId());
                    if (productInfoEntity.getProductState().equals(NOT_AVAILABLE)) {
                        continue;
                    } else {
                        ProductDto productDto = productEntity.toProductDto();
                        ProductInfoDto productInfoDto = productInfoEntity.toProductInfoDto();
                        ProductCompleteDetailsDto productCompleteDetailsDto = new
                                ProductCompleteDetailsDto(productDto, productInfoDto);

                        productCompleteDetailsDtoList.add(productCompleteDetailsDto);
                    }
                }
            }
            return productCompleteDetailsDtoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductCompleteDetailsDto getCompleteProductDetailsByProductId(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);
        if (productEntity != null && productInfoEntity != null) {
            ProductDto productDto = productEntity.toProductDto();
            ProductInfoDto productInfoDto = productInfoEntity.toProductInfoDto();
            ProductCompleteDetailsDto productCompleteDetailsDto = new ProductCompleteDetailsDto(productDto, productInfoDto);

            return productCompleteDetailsDto;
        }
        return null;
    }

    @Override
    public ProductCompleteDetailsDto putCompleteProductDetailsByProductId(Long productId,
                                                                          ProductCompleteDetailsDto productCompleteDetailsDto) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);

        ProductDto productDto = productCompleteDetailsDto.getProductDto();
        ProductInfoDto productInfoDto = productCompleteDetailsDto.getProductInfoDto();

        if (productEntity != null && productInfoEntity != null) {
            if (productDto.getTitle() != null) {
                productEntity.setTitle(productDto.getTitle());
            }
            if (productDto.getBrand() != null) {
                productEntity.setBrand(productDto.getBrand());
            }
            if (productDto.getCategory() != null) {
                productEntity.setCategory(productDto.getCategory());
            }
            if (productDto.getPrice() != null) {
                productEntity.setPrice(productDto.getPrice());
            }
            if (productDto.getStock() != null) {
                productEntity.setStock(productDto.getStock());
            }
            if (productDto.getDiscountPercentage() != null) {
                productEntity.setDiscountPercentage(productDto.getDiscountPercentage());
            }
            if (productDto.getRating() != null) {
                productEntity.setRating(productDto.getRating());
            }
            if (productDto.getDescription() != null) {
                productEntity.setDescription(productDto.getDescription());
            }
            if (productDto.getImage() != null) {
                productEntity.setImage(productDto.getImage());
            }
            if (productInfoDto.getProductState() != null) {
                productInfoEntity.setProductState(productInfoDto.getProductState());
            }
            if (productInfoDto.getProductQuantity() != null) {
                productInfoEntity.setProductQuantity(productInfoDto.getProductQuantity());
            }
            productRepository.save(productEntity);
            productInfoRepository.save(productInfoEntity);
            return productCompleteDetailsDto;
        }
        return null;
    }

    @Override
    public ProductInfoDto setStateOfProduct(Long productId, String productState) {
        ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);
        if (productInfoEntity != null) {
            productInfoEntity.setProductState(productState);
            productInfoRepository.save(productInfoEntity);
            return productInfoEntity.toProductInfoDto();
        }
        return null;
    }

    @Override
    public ProductInfoDto putProductQuantityByProductId(Long productId, Long productQuantity) {
        ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);
        if (productInfoEntity != null) {
            if (productQuantity == 0) {
                productInfoEntity.setProductState(OUT_OF_STOCK);
            } else {
                productInfoEntity.setProductState(IN_STOCK);
            }
            productInfoEntity.setProductQuantity(productQuantity);
            productInfoRepository.save(productInfoEntity);
            return productInfoEntity.toProductInfoDto();
        }
        return null;
    }

    @Override
    public String deleteCompleteProductDetailsByProductId(Long productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productId);

        if (productEntity != null && productInfoEntity != null) {
            productRepository.delete(productEntity);
            productInfoRepository.delete(productInfoEntity);
            return COMPLETE_PRODUCT_DELETE;
        }
        return COMPLETE_PRODUCT_NOT_FOUND;
    }

    @Override
    public List<ProductPrimaryDto> getAllPrimaryProductDetails() {
        try {
            List<ProductPrimaryDto> productPrimaryDtoList = new ArrayList<>();

            List<ProductEntity> productEntityList = productRepository.findAll();
            if (!productEntityList.isEmpty()) {
                for (ProductEntity productEntity : productEntityList) {
                    ProductInfoEntity productInfoEntity = productInfoRepository.findByProductId(productEntity.getProductId());

                    ProductDto productDto = productEntity.toProductDto();
                    ProductInfoDto productInfoDto = productInfoEntity.toProductInfoDto();

                    ProductPrimaryDto productPrimaryDto = new ProductPrimaryDto(productDto, productInfoDto);
                    productPrimaryDtoList.add(productPrimaryDto);

                }
            }
            return productPrimaryDtoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
