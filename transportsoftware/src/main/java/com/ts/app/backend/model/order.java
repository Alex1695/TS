package com.ts.app.backend.model;

public class order {
	
	private int id;
	private int order_request;
	
	public order(int id, int order_request) {
		super();
		this.id = id;
		this.order_request = order_request;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_request() {
		return order_request;
	}
	public void setOrder_request(int order_request) {
		this.order_request = order_request;
	}	
}
