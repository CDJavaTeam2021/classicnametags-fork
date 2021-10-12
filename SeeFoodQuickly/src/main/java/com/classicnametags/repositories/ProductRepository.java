package com.classicnametags.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.classicnametags.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
