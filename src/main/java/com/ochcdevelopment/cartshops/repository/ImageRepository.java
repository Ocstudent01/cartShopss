package com.ochcdevelopment.cartshops.repository;

import com.ochcdevelopment.cartshops.model.Image;
import com.ochcdevelopment.cartshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long Id);
}
