package com.ts.app.backend.model;

import java.time.LocalDate;
import java.util.Date;

public class booking {
	
	private int id;
	private String truckPlate;
	private int order_request;
	private int loadDownload;
	private LocalDate bookingDate;
	private LocalDate arrivalDate;
	private LocalDate departureDate;
	private int state;
	private int truckType;
	private String hour;
	
	public booking(int id, String truckPlate, int order_request, int loadDownload, LocalDate bookingDate, LocalDate arrivalDate,
			LocalDate departureDate, int state) {
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
	
	public booking() {}
	
	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getTruckType() {
		return truckType;
	}

	public void setTruckType(int truckType) {
		this.truckType = truckType;
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

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
