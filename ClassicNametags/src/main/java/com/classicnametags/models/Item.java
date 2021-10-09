package com.classicnametags.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	private String line1;
	
	private String line2;
	
	private int quantity;
	
	private float subtotal;
	
	
	//Relationship attributes
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="color_id", updatable=true)
	private Color itemColor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", updatable=true)
	private Product itemProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id", updatable = false)
	private Order itemOrder;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	//Empty constructor
	
	public Item() {
	}

	//Getters and setters go here
	
	public Long getId() {
		return id;
	}

	

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Color getItemColor() {
		return itemColor;
	}

	public void setItemColor(Color itemColor) {
		this.itemColor = itemColor;
	}

	public Product getItemProduct() {
		return itemProduct;
	}

	public void setItemProduct(Product itemProduct) {
		this.itemProduct = itemProduct;
	}

	public Order getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Order itemOrder) {
		this.itemOrder = itemOrder;
	}
	
	
	
	


}
