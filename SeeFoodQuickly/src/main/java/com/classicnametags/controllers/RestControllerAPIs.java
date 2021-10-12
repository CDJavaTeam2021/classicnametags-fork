package com.classicnametags.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.classicnametags.models.Color;
import com.classicnametags.services.ProductService;

@RestController
public class RestControllerAPIs {
	
	@Autowired
	ProductService pServ;
	
	

	@RequestMapping("/APIs/Colors/{colorNumber}")
	@ResponseBody
	public HashMap<String, String> getColor(@PathVariable("colorNumber") String colorNumber) {
		HashMap<String, String> colors = new HashMap<String, String>();
		Long colorId = Long.valueOf(colorNumber);
		Color color = pServ.getColorById(colorId);
		colors.put("BGColor", color.getBackgroundColor());
		colors.put("lColor", color.getLetterColor());
		return colors;
		
	}

	
}
