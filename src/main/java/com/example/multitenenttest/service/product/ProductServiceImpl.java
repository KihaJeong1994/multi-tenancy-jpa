package com.example.multitenenttest.service.product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.EntityNotFoundException;

import com.example.multitenenttest.domain.entity.tenant.Product;
import com.example.multitenenttest.model.ProductDto;
import com.example.multitenenttest.repository.tenant.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProduct(long productId) {
        return productRepository.findById(productId)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Product " + productId + " not found"));
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productValue) {
        Product product = Product.builder()
                .name(productValue.getName())
                .build();
        product = productRepository.save(product);
        return ProductDto.fromEntity(product);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productValue) {
        Product product = productRepository.findById(productValue.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product " + productValue.getProductId() + " not found"));
        product.setName(productValue.getName());
        return ProductDto.fromEntity(product);
    }

    @Override
    @Transactional
    public void deleteProductById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product " + productId + " not found"));
        productRepository.delete(product);
    }
}
