package com.example.multitenenttest.service.product;

import com.example.multitenenttest.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();

    ProductDto getProduct(long productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProductById(long productId);
}
