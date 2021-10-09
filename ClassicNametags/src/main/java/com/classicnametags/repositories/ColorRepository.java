package com.classicnametags.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.classicnametags.models.Color;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {
	
	public List<Color> findByIdIn(List<Long> idList);
	
	public List<Color> findByIdNotIn(List<Long> idList);

}
