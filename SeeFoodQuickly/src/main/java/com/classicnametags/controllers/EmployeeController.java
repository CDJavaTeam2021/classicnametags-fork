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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.classicnametags.models.User;
import com.classicnametags.models.Product;
import com.classicnametags.models.Color;
import com.classicnametags.models.Order;
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
	
	@GetMapping("/orders/queue")
	public String openOrderQueue(HttpSession session, Model viewModel) {
		if(uServ.isAdmin(session) || uServ.isEmployee(session)) {
			viewModel.addAttribute("openOrders", oServ.getOpenOrders());
			viewModel.addAttribute("queueTime", oServ.getQueueTime());
			return "openOrders.jsp";
		} else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/orders/{order_id}/confirm")
	public String confirmOrder(
			@PathVariable("order_id") String orderIdS, 
			@RequestParam("confirmDue") String confirmDue ) {
		oServ.confirmOrder(orderIdS, confirmDue);		
		return "redirect:/orders/{order_id}/view/confirmed";
	}
	
	@PostMapping("/orders/item/{item_id}/complete")
	public String completeItem(@PathVariable("item_id")String itemIdS, 
			@RequestParam("origin") String origin) {
		oServ.completeItem(itemIdS);
		if(origin.equals("queue")) {
			return "redirect:/orders/queue";
		} else {
			return "redirect:/orders/"+ origin +"/view/viewOrder";
		}
	}
	
	@RequestMapping("/orders/{order_id}/close")
	public String closeOrder(@PathVariable("order_id") String orderIdS, 
			@RequestParam("origin") String origin, Model viewModel, RedirectAttributes redAttr) {
		if(origin.equals("queue")) {
			if(oServ.completeOrder(orderIdS)) {
				return "redirect:/orders/queue";
			} else {
				redAttr.addFlashAttribute("errorString", "All items must be completed first!");
				return "redirect:/orders/queue";
			} 
		} else {
			if(oServ.completeOrder(orderIdS)) {
				return "redirect:/orders/" + origin + "/view/viewOrder";
			} else {
				//fail
				redAttr.addFlashAttribute("errorString", "All items must be completed first!");
				return "redirect:/orders/{order_id}/view/error";
			}
		}
	}
	
	@GetMapping("orders/all_orders")
	public String viewMyOrders(HttpSession session, Model viewModel) {
		if(uServ.isAdmin(session) || uServ.isEmployee(session)) {
			viewModel.addAttribute("allOrders", oServ.getAllOrders());
			return "orderHistory.jsp";
		} else {
			return "redirect:/";
		}
	}
	
	


}
