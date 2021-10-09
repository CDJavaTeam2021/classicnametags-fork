package com.classicnametags.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.classicnametags.models.User;
import com.classicnametags.models.Product;
import com.classicnametags.models.Color;
import com.classicnametags.services.OrderService;
import com.classicnametags.services.ProductService;
import com.classicnametags.services.UserService;
import com.classicnametags.validators.UserValidator;

@Controller
public class EmployeeController {
	@Autowired
	OrderService oServ;
	@Autowired
	UserService uServ;
	@Autowired
	ProductService pServ;
	
	//Test endpoint to determine permissions of user
	@RequestMapping("/perm")
	public String printPermissions(HttpSession session) {
		if(uServ.isAdmin(session)) {
			System.out.println("admin");
		} else if (uServ.isEmployee(session)) {
			System.out.println("employee");
		} else {
			System.out.println("customer or guest");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/products")
	public String productsPage(HttpSession session, Model viewModel) {
		if(uServ.isAdmin(session) || uServ.isEmployee(session)) {
			viewModel.addAttribute("products", pServ.getAllProducts());
			viewModel.addAttribute("allColors", pServ.getAllColors());
			return "products.jsp";
		} else {
			return "redirect:/";
		}
		
	}
	
	//No functionality built to view individual products yet
	//Redirects to main products page
	@RequestMapping("/products/{id}")
	public String productWithId() {
		return "redirect:/products";
	}
	
	@PostMapping("/products/new")
	public String newProduct(HttpSession session, 
			@RequestParam("type") String type,
			@RequestParam("price") float price,
			@RequestParam("makeMinutes") int makeTime, 
			@RequestParam("colors") List<Long> colorIds) {
		if(uServ.isAdmin(session) || uServ.isEmployee(session)) {
			Product newProduct = new Product();
			newProduct.setType(type);
			newProduct.setPrice(price);
			newProduct.setMakeMinutes(makeTime);
			List<Color> newColors = new ArrayList<>();
			for(Long colorId : colorIds) {
				newColors.add(pServ.getColorById(colorId));
			}
			newProduct.setColors(newColors);
			pServ.saveProduct(newProduct);
			
			return "redirect:/products";
		} else {
			return "redirect:/";
		}	
	}
	
	@PostMapping("/products/colors/new")
	public String newColor(HttpSession session,
			@RequestParam("backgroundColor") String bgColor,
			@RequestParam("letterColor") String lColor,
			@RequestParam("frameColor") String fColor) {
		if(uServ.isAdmin(session) || uServ.isEmployee(session)) {
			pServ.newColor(lColor, bgColor, fColor);
			return "redirect:/products";
		} else {
			return "redirect:/";
		}
	}


}
