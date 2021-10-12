package com.classicnametags.services;

import java.text.SimpleDateFormat;
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
	
	//helper method to convert datetime to a string format HTML can process
	public String htmlDate(Date date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'kk:mm");
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
	
	//Helper method to convert HTML datetime format to Date format (2021-10-10T16:01 to 2021-10-10 16:01:41.279)
	public Date htmlStringToDate(String htmlDate) {
		LocalDateTime dateTime = LocalDateTime.parse(htmlDate);
		Date formattedDate = java.sql.Timestamp.valueOf(dateTime);
		return formattedDate;
	}
	
	public int getQueueTime() {
		return this.estQueueTime;
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
	public List<Order> getAllOrders() {
		return (List<Order>) oRepo.findAll();
	}
	
	public Order checkout(HttpSession session, String orderEmail, String orderPhone) {
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
		
		
		// add contact info from customer
		order.setContactEmail(orderEmail);
		order.setContactPhone(orderPhone);
		// 
		
		order.setOpen(true);
		
		Long userId = (Long) session.getAttribute("userId");
		order.setCustomer(uServ.findUserById(userId));
		oRepo.save(order);
		
		emptyCart(session);
		
		return order;
	}
	
	public Order findOrderById(Long id) {
		Order order = oRepo.findById(id).get();
		order.setDueDateString(convertDate(order.getDueDate()));
		return oRepo.findById(id).get();
	}
	
	public List<Order> getOpenOrders(){
		for(Order order : oRepo.findByOpenIsOrderByDueDate(true)) {
			order.setDueDateString(convertDate(order.getDueDate()));
		}		
		return oRepo.findByOpenIsOrderByDueDate(true);
	}
	
	public void confirmOrder(String orderIdS, String htmlDueDate) {
		Long orderId = Long.valueOf(orderIdS);
		Order order = findOrderById(orderId);
		order.setDueDate(htmlStringToDate(htmlDueDate));
		if(order.getOrderStatus().getId() < 2) {
			order.setOrderStatus(sRepo.findById((long)2).get());
		}
		oRepo.save(order);		
	}
	
	//Attempts to close order. Checks if there are open lines
	//If there are open lines, returns false and leaves order unchanged
	//Otherwise, closes order and return true
	public Boolean completeOrder(String orderIdS) {
		Long orderId = Long.valueOf(orderIdS);
		Order order = oRepo.findById(orderId).get();
		Boolean ended = false;
		for(Item item : order.getItems()) {
			if(item.getItemStatus().getId() == (long)1) {
				return false;
			} else {
				order.setOrderStatus(sRepo.findById((long)4).get());
				order.setOpen(false);
				this.estQueueTime -= order.getEstDuration();
				oRepo.save(order);
				ended = true;
			}
		}
		return ended;
		
	}
	
	//Empty the cart
	public void emptyCart(HttpSession session) {
		List<Item> empty = new ArrayList<Item>();
		session.setAttribute("myCart", empty);
		session.setAttribute("cartTotal", 0f);
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
	
	public void completeItem(String itemIdS) {
		Long itemId = Long.valueOf(itemIdS);
		//find the item by ID, set its status to status 4 (completed)
		Item item = iRepo.findById(itemId).get();
		item.setItemStatus(sRepo.findById((long)4).get());
		if(item.getItemOrder().getOrderStatus().getId() < 3) {
			Order order = item.getItemOrder();
			order.setOrderStatus(sRepo.findById((long)3).get());
			oRepo.save(order);
		}
		iRepo.save(item);		
	}


}
