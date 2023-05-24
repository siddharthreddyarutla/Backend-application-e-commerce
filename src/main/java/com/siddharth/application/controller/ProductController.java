package com.siddharth.application.controller;

import com.siddharth.application.dto.ProductCompleteDetailsDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.dto.ProductInfoDto;
import com.siddharth.application.dto.ProductPrimaryDto;
import com.siddharth.application.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    // post product details
    @PostMapping(value = "/postDetails")
    private ResponseEntity<ProductDto> postProductDetails(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productServiceImpl.addProduct(productDto), HttpStatus.OK);
    }

    // get all product details
    @GetMapping(value = "/getDetails")
    private ResponseEntity<List<ProductDto>> getDetails() {
        return new ResponseEntity<>(productServiceImpl.getDetails(), HttpStatus.OK);
    }

    // get all products by productId
    @GetMapping(value = "/getDetailsById/{productId}")
    private ResponseEntity<ProductDto> getDetailsById(@PathVariable Long productId) {
        return new ResponseEntity<>(productServiceImpl.getDetailsById(productId), HttpStatus.OK);
    }

    // delete product by productId
    // THIS API IS USED ONLY IF YOU WANT TO DELETE A PRODUCT COMPLETELY
    @DeleteMapping(value = "/deleteDetailsById")
    private ResponseEntity<String> deleteDetailsById(@RequestParam Long productId) {
        return new ResponseEntity<>(productServiceImpl.deleteDetailsById(productId), HttpStatus.OK);
    }

    // edit product details by productId
    @PutMapping(value = "/editDetailsById")
    private ResponseEntity<ProductDto> editDetailsById(@RequestParam Long productId, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productServiceImpl.editDetailsById(productId, productDto), HttpStatus.OK);
    }

    // search product by category
    @PostMapping(value = "/searchProductsByCategory")
    private ResponseEntity<List<ProductDto>> getProductByCategory(@RequestParam String category) {
        return new ResponseEntity<>(productServiceImpl.searchProductByCategory(category), HttpStatus.OK);
    }

    // search product by title
    @PostMapping(value = "/searchProductsByTitle")
    private ResponseEntity<List<ProductDto>> getProductByTitle(@RequestParam String title) {
        return new ResponseEntity<>(productServiceImpl.searchProductByTitle(title), HttpStatus.OK);
    }

    // search product by brand
    @PostMapping(value = "/searchProductByBrand")
    private ResponseEntity<List<ProductDto>> getProductByBrand(@RequestParam String brand) {
        return new ResponseEntity<>(productServiceImpl.searchProductByBrand(brand), HttpStatus.OK);
    }

    // filter by price, less than or greater than and between both as well
    @PostMapping(value = "/filterByPrice")
    private ResponseEntity<List<ProductDto>> filterByPrice(@RequestParam(required = false) Long leastPrice,
                                                           @RequestParam(required = false) Long highestPrice) {
        if (leastPrice != null && highestPrice == null) {
            return new ResponseEntity<>(productServiceImpl.searchByLeastPrice(leastPrice), HttpStatus.OK);
        } else if (leastPrice == null && highestPrice != null) {
            return new ResponseEntity<>(productServiceImpl.searchByHighestPrice(highestPrice), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productServiceImpl.searchByLeastPriceAndHighestPrice(leastPrice, highestPrice),
                    HttpStatus.OK);
        }
    }

    // sort by rating in ASC
    @GetMapping(value = "/sortByRatingAsc")
    private ResponseEntity<List<ProductDto>> sortByRatingAsc() {
        return new ResponseEntity<>(productServiceImpl.sortByRatingAsc(), HttpStatus.OK);
    }

    // sort by rating in DESC
    @GetMapping(value = "/sortByRatingDesc")
    private ResponseEntity<List<ProductDto>> sortByRatingDesc() {
        return new ResponseEntity<>(productServiceImpl.sortByRatingDesc(), HttpStatus.OK);
    }

    // sort by discount percentage in ASC
    @GetMapping(value = "/sortByDiscountAsc")
    private ResponseEntity<List<ProductDto>> sortByDiscountAsc() {
        return new ResponseEntity<>(productServiceImpl.sortByDiscountAsc(), HttpStatus.OK);
    }

    // sort by discount percentage in DESC
    @GetMapping(value = "/sortByDiscountDesc")
    private ResponseEntity<List<ProductDto>> sortByDiscountDesc() {
        return new ResponseEntity<>(productServiceImpl.sortByDiscountDesc(), HttpStatus.OK);
    }

    // post all complete product details
    @PostMapping(value = "/postCompleteProductDetails")
    private ResponseEntity<ProductCompleteDetailsDto> postProductCompleteDetails(@RequestBody ProductCompleteDetailsDto
                                                                                             productCompleteDetails) {
        return new ResponseEntity<>(productServiceImpl.postCompleteDetailsOfProduct(productCompleteDetails), HttpStatus.OK);
    }

    // get all complete product details
    @GetMapping(value = "/getAllCompleteProductDetails")
    private ResponseEntity<List<ProductCompleteDetailsDto>> getAllCompleteProductDetails() {
        return new ResponseEntity<>(productServiceImpl.getAllCompleteProductDetails(), HttpStatus.OK);
    }

    // get all complete product details by ProductId
    @GetMapping(value = "/getCompleteProductDetailsByProductId/{productId}")
    private ResponseEntity<ProductCompleteDetailsDto> getCompleteProductDetailById(@PathVariable Long productId) {
        return new ResponseEntity<>(productServiceImpl.getCompleteProductDetailsByProductId(productId), HttpStatus.OK);
    }

    // put all complete product details by ProductId
    @PutMapping(value = "/editCompleteProductDetailsByProductId")
    private ResponseEntity<ProductCompleteDetailsDto> editCompleteProductDetailsById(@RequestParam Long productId,
                                                                                     @RequestBody ProductCompleteDetailsDto productCompleteDetailsDto) {
        return new ResponseEntity<>(productServiceImpl.putCompleteProductDetailsByProductId(productId,
                productCompleteDetailsDto), HttpStatus.OK);
    }

    // mark the product as OUT OF STOCK ( Instead of deleting the product if product is not available
    // we will mark the state as OUT OF STOCK
    // if quantity becomes 0 automatically it will be set as OUT OF STOCK but for any reasons we can edit state or mark it as OUT OF STOCK
    @PutMapping(value = "/setStateOfProductByProductId")
    private ResponseEntity<ProductInfoDto> SetStateAsOutOfStock(@RequestParam Long productId,
                                                                @RequestParam String productState) {
        return new ResponseEntity<>(productServiceImpl.setStateOfProduct(productId, productState), HttpStatus.OK);
    }

    @PutMapping(value = "/updateProductQuantityByProductId")
    private ResponseEntity<ProductInfoDto> editProductQuantityByProductId(@RequestParam Long productId,
                                                                          @RequestParam Long productQuantity) {
        return new ResponseEntity<>(productServiceImpl.putProductQuantityByProductId(productId, productQuantity),
                HttpStatus.OK);
    }

    // Delete the complete product details by productId

    /**
     * THIS API IS USED ONLY IF YOU WANT TO DELETE COMPLETE PRODUCT DETAILS PERMANENTLY
     * USE ONLY WHEN YOU DON'T WANT THE PRODUCT ANYMORE
     */
    @DeleteMapping(value = "/deleteCompleteProductDetailsByProductId")
    private ResponseEntity<String> deleteCompleteProductDetailsByProductId(@RequestParam Long productId) {
        return new ResponseEntity<>(productServiceImpl.deleteCompleteProductDetailsByProductId(productId), HttpStatus.OK);
    }

    // get all primary details of product
    @GetMapping(value = "/getAllPrimaryDetailsOfProduct")
    private ResponseEntity<List<ProductPrimaryDto>> getAllPrimaryDetails() {
        return new ResponseEntity<>(productServiceImpl.getAllPrimaryProductDetails(), HttpStatus.OK);
    }
}
