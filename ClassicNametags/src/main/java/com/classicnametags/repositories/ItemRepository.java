package com.classicnametags.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.classicnametags.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
