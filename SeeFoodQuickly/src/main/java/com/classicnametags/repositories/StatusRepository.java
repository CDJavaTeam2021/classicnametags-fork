package com.classicnametags.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.classicnametags.models.Status;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {

}
