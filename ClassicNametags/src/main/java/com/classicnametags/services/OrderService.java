package com.classicnametags.services;

import java.util.Date;
import java.util.List;


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
	
	public OrderService(OrderRepository oRepo, StatusRepository sRepo, ItemRepository iRepo) {
		super();
		this.oRepo = oRepo;
		this.sRepo = sRepo;
		this.iRepo = iRepo;
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
	public void newOrder(Order order) {
		oRepo.save(order);
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
		int max = 0;
		for(Item line : order.getItems()) {
			if(line.getItemProduct().getMakeMinutes() > max) {
				max = line.getItemProduct().getMakeMinutes();
			}
		}
		long timeInSecs = java.time.Instant.now().getEpochSecond();
		
		Date due = new Date(timeInSecs + (max * 60 * 1000));
		order.setDueDate(due);
		oRepo.save(order);
	}
	
	public List<Order> findOpenOrders(){
		
		
	}
	
	
	
	
	public List<Order> findAllOrders(){
		return oRepo.findAll();
	}
	
	public List<Order> findOrdersByCustomer(Long id){
		
	}
	
	
	//Item Methods

}
