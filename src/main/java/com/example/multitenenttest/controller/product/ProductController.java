package com.example.multitenenttest.controller.product;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.example.multitenenttest.exception.NotFoundException;
import com.example.multitenenttest.model.ProductDto;
import com.example.multitenenttest.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productValues = productService.getProducts();
        return new ResponseEntity<>(productValues, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") long productId) {
        try {
            ProductDto branch = productService.getProduct(productId);
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productValue) {
        ProductDto product = productService.createProduct(productValue);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.LOCATION, "/products/" + product.getProductId());
        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{productId}")
    ResponseEntity<ProductDto> updateProduct(@PathVariable long productId, @Valid @RequestBody ProductDto productValue) {
        productValue.setProductId(productId);
        try {
            ProductDto product = productService.updateProduct(productValue);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/products/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        try {
            productService.deleteProductById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }



}
