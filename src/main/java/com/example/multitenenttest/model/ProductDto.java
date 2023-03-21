package com.example.multitenenttest.model;

import com.example.multitenenttest.domain.entity.tenant.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    @JsonProperty("productId")
    private Long productId;

    @NotNull
    @Size(max = 255)
    @JsonProperty("name")
    private String name;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .build();
    }

    public static Product fromDto(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getProductId())
                .name(productDto.getName())
                .build();
    }

}
