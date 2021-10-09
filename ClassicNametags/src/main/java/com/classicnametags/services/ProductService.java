package com.classicnametags.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.classicnametags.models.Color;
import com.classicnametags.models.Product;
import com.classicnametags.repositories.ColorRepository;
import com.classicnametags.repositories.ProductRepository;

@Service
public class ProductService {
	
	ProductRepository pRepo;
	ColorRepository cRepo;
	public ProductService(ProductRepository pRepo, ColorRepository cRepo) {
		super();
		this.pRepo = pRepo;
		this.cRepo = cRepo;
	}
	
	// Color combo methods
	public void newColor(String letterColor, String bgColor, String frameColor) {
		Color newColor = new Color();		
		if(frameColor.isEmpty()) {
			newColor.setColorName(bgColor + " w/ " + letterColor + " letters");
			newColor.setLetterColor(letterColor);
			newColor.setBackgroundColor(bgColor);			
		} else {
			newColor.setColorName(frameColor + " frame");
			newColor.setFrameColor(frameColor);
		}
		cRepo.save(newColor);		
	}
	
	public List<Color> findColorsNotIn(Product product, List<Long> idList){
		List<Long> currentIds = new ArrayList<>();
		if(product.getColors().isEmpty()) {
			return (List<Color>) cRepo.findAll();
		} else {
			List<Color> colors = product.getColors();
			for(Color color : colors) {
				currentIds.add(color.getId());
			}
			return cRepo.findByIdNotIn(currentIds);		
		}
	}
	
	
	//Product methods
	public void saveProduct(Product product) {
		pRepo.save(product);
	}
	
	

}
