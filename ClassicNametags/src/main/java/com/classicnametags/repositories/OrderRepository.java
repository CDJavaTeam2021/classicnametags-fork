package com.classicnametags.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.classicnametags.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findByIdNotIn(List<Long> status4List);

}
