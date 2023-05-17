package com.siddharth.application.controller;

import com.siddharth.application.dto.ProductDto;
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

    @PostMapping(value = "/postDetails")
    private ResponseEntity<ProductDto> postProductDetails(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productServiceImpl.addProduct(productDto), HttpStatus.OK);
    }

    @GetMapping(value = "/getDetails")
    private ResponseEntity<List<ProductDto>> getDetails() {
        return new ResponseEntity<>(productServiceImpl.getDetails(), HttpStatus.OK);
    }

    @GetMapping(value = "/getDetailsById/{productId}")
    private ResponseEntity<ProductDto> getDetailsById(@PathVariable Long productId) {
        return new ResponseEntity<>(productServiceImpl.getDetailsById(productId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteDetailsById")
    private ResponseEntity<String> deleteDetailsById(@RequestParam Long productId) {
        return new ResponseEntity<>(productServiceImpl.deleteDetailsById(productId), HttpStatus.OK);
    }

    @PutMapping(value = "/editDetailsById")
    private ResponseEntity<ProductDto> editDetailsById(@RequestParam Long productId, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productServiceImpl.editDetailsById(productId, productDto), HttpStatus.OK);
    }

    //search product by category
    @PostMapping(value = "/searchProductByCategory")
    private ResponseEntity<ProductDto> searchProductByCategory(@RequestParam String category) {
        return new ResponseEntity<>(productServiceImpl.searchProductByCategory(category), HttpStatus.OK);
    }
}
