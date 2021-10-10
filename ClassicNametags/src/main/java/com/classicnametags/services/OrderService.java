package com.classicnametags.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.classicnametags.models.Item;
import com.classicnametags.models.Order;
import com.classicnametags.models.Status;
import com.classicnametags.repositories.ItemRepository;
import com.classicnametags.repositories.OrderRepository;
import com.classicnametags.repositories.StatusRepository;

@Service
public class OrderService {
	
	OrderRepository oRepo;
	StatusRepository sRepo;
	ItemRepository iRepo;
	ProductService pServ;
	UserService uServ;
	int estQueueTime;
	
	
	public OrderService(OrderRepository oRepo, StatusRepository sRepo, 
			ItemRepository iRepo, ProductService pServ, UserService uServ
	) {
		this.oRepo = oRepo;
		this.sRepo = sRepo;
		this.iRepo = iRepo;
		this.pServ = pServ;
		this.uServ = uServ;


		for(Order order : oRepo.findAll()) {
			if(order.getOrderStatus().getId() < 4) {
				this.estQueueTime += order.getEstDuration();
			}
			
		}

		System.out.println("Estimated queue time = " + estQueueTime + " minutes.");
	}
	
	//Helper function to convert dates to short date with time (10/9/21 3:12PM)
	public String convertDate(Date date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy hh:mma");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		LocalDateTime localDate = instant.atZone(defaultZoneId).toLocalDateTime();
		String formattedDate = localDate.format(formatter);
		return formattedDate;
	}
	
	//Helper method to get now
	public Date getNow() {
		LocalDateTime now = LocalDateTime.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = now.atZone(defaultZoneId);  //atStartOfDay(defaultZoneId);
		Date today = Date.from(zonedDateTime.toInstant());
		return today;
		
		
	}
	
	//Status Methods
	public Status findStatusById(Long id) {
		// id 1 = New
		// id 2 = Confirmed
		// id 3 = In Progress
		// id 4 = Complete
		return sRepo.findById(id).get();
	}
	
	//Order methods
	public Order checkout(HttpSession session) {
		Order order = new Order();
		List<Item> newItems = (List<Item>) session.getAttribute("myCart");
		order.setItems(newItems);
		order.setOrderTotal((float) session.getAttribute("cartTotal"));
		oRepo.save(order);
		for(Item item : newItems) {
			item.setItemOrder(order);
			item.setItemStatus(sRepo.findById((long) 1).get());
			iRepo.save(item);
		}
		Long id = (long) 1; // Find order status "New" and set the order to new
		order.setOrderStatus(findStatusById(id));
		String orderId = String.valueOf(order.getId());
		String zeros = "";
		for(int i = 0; i < 6 - orderId.length(); i++) {
			zeros += "0";
		}
		String orderNumber = "ORD";
		orderNumber += zeros;
		orderNumber += orderId;
		order.setOrderNumber(orderNumber);
		
		
		//find the item with the longest time to make and add to current time to 
		//arrive at a due date and time
		int max = 0;
		for(Item line : order.getItems()) {
			if(line.getItemProduct().getMakeMinutes() > max) {
				max = line.getItemProduct().getMakeMinutes();
			}
		}
		this.estQueueTime += max;
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
		Date due = new Date(timeInSecs + (this.estQueueTime * 60 * 1000));
		order.setEstDuration(max);
		order.setDueDate(due);
		
		
		//
		
		order.setOpen(true);
		
		Long userId = (Long) session.getAttribute("userId");
		order.setCustomer(uServ.findUserById(userId));
		oRepo.save(order);
		
		emptyCart(session);
		
		return order;
	}
	
	public List<Order> getOpenOrders(){
		for(Order order : oRepo.findByOpenIsOrderByDueDate(true)) {
			order.setDueDateString(convertDate(order.getDueDate()));
		}		
		return oRepo.findByOpenIsOrderByDueDate(true);
	}
	
	//Empty the cart
	public void emptyCart(HttpSession session) {
		List<Item> empty = new ArrayList<Item>();
		session.setAttribute("myCart", empty);
		session.setAttribute("cartTotal", 0f);
	}
	
	public Order findOrderById(Long id) {
		return oRepo.findById(id).get();
	}
	

	
	
	//Item Methods
	public void addItemToCart(HttpSession session, String prodIdS, 
			String colorIdS, String line1, String line2, String quantityS) {
		
		int quantity = Integer.valueOf(quantityS);
		Long prodId = Long.valueOf(prodIdS);
		Long colorId = Long.valueOf(colorIdS);
		
		Item newItem = new Item();
		newItem.setItemColor(pServ.getColorById(colorId));
		newItem.setItemProduct(pServ.getProductById(prodId));
		newItem.setLine1(line1);
		newItem.setLine2(line2);
		newItem.setQuantity(quantity);
		float cost = pServ.getProductById(prodId).getPrice();
		cost *= quantity;
		newItem.setSubtotal(cost);
		List<Item> cart = (List<Item>) session.getAttribute("myCart");
		cart.add(newItem);
		session.setAttribute("myCart", cart);
		float total = (float) session.getAttribute("cartTotal");
		total += cost;
		session.setAttribute("cartTotal", total);
		
	}



}
