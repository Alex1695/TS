package com.ts.app.backend.model;

import java.util.Date;

public class timeOut {
	
	private int id;
	private String truckPlate;
	private int order_request;
	private int loadDownload;
	private Date bookingDate;
	private Date arrivalDate;
	private Date departureDate;
	private int state;
	
	public timeOut(int id, String truckPlate, int order_request, int loadDownload, Date bookingDate, Date arrivalDate,
			Date departureDate, int state) {
		super();
		this.id = id;
		this.truckPlate = truckPlate;
		this.order_request = order_request;
		this.loadDownload = loadDownload;
		this.bookingDate = bookingDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTruckPlate() {
		return truckPlate;
	}

	public void setTruckPlate(String truckPlate) {
		this.truckPlate = truckPlate;
	}

	public int getOrder_request() {
		return order_request;
	}

	public void setOrder_request(int order_request) {
		this.order_request = order_request;
	}

	public int getLoadDownload() {
		return loadDownload;
	}

	public void setLoadDownload(int loadDownload) {
		this.loadDownload = loadDownload;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}