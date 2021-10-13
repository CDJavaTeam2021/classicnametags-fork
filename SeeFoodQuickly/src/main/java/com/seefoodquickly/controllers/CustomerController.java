package com.classicnametags.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.classicnametags.models.Item;
import com.classicnametags.models.Order;
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
	
	//Order form
	@GetMapping("/orders/items/new")
	public String orderForm(Model viewModel, HttpSession session) {
		viewModel.addAttribute("allProducts", pServ.getAllProducts());
		viewModel.addAttribute("allColors", pServ.getAllColors());
		
		return "newOrder.jsp";
	}
	
	//Add items to cart
	@PostMapping("/orders/items/new")
	public String addToCart(HttpSession session, 
			@RequestParam("itemProduct") String prodIdS,
			@RequestParam("line1") String line1,
			@RequestParam("line2") String line2,
			@RequestParam("itemColor") String colorIdS,
			@RequestParam("quantity") String quantity) {
		
		oServ.addItemToCart(session, prodIdS, colorIdS, line1, line2, quantity);
		
	
		return "redirect:/orders/items/new";
		
	}
	
	//Checkout
	@PostMapping("/orders/items/checkout")
	public String checkout(HttpSession session, 
			Model model, 
			@RequestParam("orderEmail") String orderEmail,
			@RequestParam("orderPhone") String orderPhone) {
		Order order = oServ.checkout(session, orderEmail, orderPhone);
		Long orderId = order.getId();
		return "redirect:/orders/"+ orderId + "/submitted";		
	}
	
	@GetMapping("/orders/{id}/submitted")
	public String submitted(Model viewModel, @PathVariable("id") long orderId, HttpSession session) {
		Order myOrder = oServ.findOrderById(orderId);
		if((Boolean)session.getAttribute("loggedIn") == false) {
			return "redirect:/";
		} else if (session.getAttribute("permissions").equals("customer") && 
				(long)session.getAttribute("userId") != myOrder.getCustomer().getId() ) {
			return "redirect:/";
		} else {
			viewModel.addAttribute("myOrder", myOrder);
			Date due = myOrder.getDueDate();
			String formattedDate = oServ.convertDate(due);
			viewModel.addAttribute("due", formattedDate);
			return "orderSubmission.jsp";
		}
	}
	
	@GetMapping("/orders/{orderId}/view/{orderNumber}")
	public String viewOrder(HttpSession session, @PathVariable("orderId") long orderId, Model viewModel) {
		Order order = oServ.findOrderById(orderId);
		if((Boolean)session.getAttribute("loggedIn") == false) {
			return "redirect:/";
		} else if (session.getAttribute("permissions").equals("customer") && 
				(long)session.getAttribute("userId") != order.getCustomer().getId() ) {
			return "redirect:/";
		} else {
			viewModel.addAttribute("thisOrder", order);
			viewModel.addAttribute("htmlDueDate", oServ.htmlDate(order.getDueDate()));
			
			return "orderView.jsp";
		}
	}
		
	@GetMapping("orders/my_orders")
	public String viewMyOrders(HttpSession session, Model viewModel) {
		if((Boolean)session.getAttribute("loggedIn") == false) {
			return "redirect:/";
		} else {
			User user = uServ.findUserById((long)session.getAttribute("userId"));
			viewModel.addAttribute("allOrders", user.getOrders());
			return "orderHistory.jsp";
		}
	}

		
		
		
	
	

}
