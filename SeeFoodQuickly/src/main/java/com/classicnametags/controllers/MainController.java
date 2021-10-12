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
import org.springframework.web.bind.annotation.RequestParam;

import com.classicnametags.models.User;
import com.classicnametags.services.OrderService;
import com.classicnametags.services.ProductService;
import com.classicnametags.services.UserService;
import com.classicnametags.validators.UserValidator;

@Controller
public class MainController {
	@Autowired
	OrderService oServ;
	@Autowired
	UserService uServ;
	@Autowired
	ProductService pServ;
	@Autowired
	UserValidator uVal;
	
	@RequestMapping("/")
	public String landing() {
		return "index.jsp";
	}
	
	//Get and Post pair for Model Attribute
	@GetMapping("/register")
	public String registerGet(@ModelAttribute("newUser") User user) {
		return "registration.jsp";
	}
	
	@PostMapping("/register")
	public String registerPost(@Valid @ModelAttribute("newUser") User user, BindingResult result, HttpSession session) {
		uVal.validate(user, result);
		if(result.hasErrors()) {
			return "registration.jsp";
		} else {
			uServ.newUser(user);
			return "redirect:/"; //todo set up dashboard, add user to session
		}
		
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
			@RequestParam("password") String Password, HttpSession session) {
		
		if(uServ.authenticateUser(email, Password)) {
			User user = uServ.findUserByEmail(email);
			uServ.login(session, user);
			
			return "redirect:/";
		} else {
			return "index.jsp";
		}
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		uServ.logout(session);
		return "redirect:/";
	}
	

}
