package com.example.multitenenttest.repository.tenant.product;

import com.example.multitenenttest.domain.entity.tenant.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
