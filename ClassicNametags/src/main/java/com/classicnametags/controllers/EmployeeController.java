package com.classicnametags.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.classicnametags.models.User;
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


}
