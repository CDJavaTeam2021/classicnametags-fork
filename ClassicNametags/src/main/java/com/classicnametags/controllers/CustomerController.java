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
public class CustomerController {
	@Autowired
	OrderService oServ;
	@Autowired
	UserService uServ;
	@Autowired
	ProductService pServ;

	

	

}
