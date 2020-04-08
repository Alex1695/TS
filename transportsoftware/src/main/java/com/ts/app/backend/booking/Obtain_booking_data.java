package com.ts.app.backend.booking;

import java.time.LocalDate;
import java.util.List;

import com.ts.app.backend.model.booking;

public class Obtain_booking_data {
	private int action;
	private int type;
	private String value_plate;
	private int value_order;
	private LocalDate day;
	private String hour_booking;
	
	public List<booking> books;
	
	public List<booking> getBooks(){
		return books;
	}
	
	public void setBooks(List<booking> books){
		this.books = books;
	}
	
	private String arrival_hour;
	private String exit_hour;
	
	
	public String getArrival_hour() {
		return arrival_hour;
	}

	public void setArrival_hour(String arrival_hour) {
		this.arrival_hour = arrival_hour;
	}

	public String getExit_hour() {
		return exit_hour;
	}

	public void setExit_hour(String exit_hour) {
		this.exit_hour = exit_hour;
	}
	
	public void setHour(String hour) {
		this.hour_booking = hour;
	}
	
	public String getHour() {
		return hour_booking;
	}
	public void setAction(String load_download) {
		if (load_download == "Descarga") {
			action = 2;
			
		} else if (load_download == "Carga") {
			action = 1;
		} else {
			action = 0;
		}
	}
	
	public int getAction() {
		return action;
	}
	
	public void setPlate(String plate) {
		if (plate.toUpperCase().matches("^[0-9]{4}[A-Z]{3}$")) {
			value_plate = plate;
	    }else{
	    	value_plate = "";
	    }  
	}
	
	public String getPlate() {
		return value_plate;
	}
	
	public void setOrder(String order) {
		if (order.matches("^[0-9]{6}")) {
			value_order = Integer.parseInt(order);
		} else {
			value_order = 0;
		}
	}
	
	public int getOrder() {
		return value_order;
	}
	
	public void setDay(LocalDate day_reserved) {
		if(day_reserved == null) {
			day = null;
		} else if (day_reserved != null){
			day = day_reserved;
		}
	}
	
	public LocalDate getDay() {
		return day;
	}
	
	public void setType(String truck_type) {
		if (truck_type == null) {
			type = 0;
		} else if (truck_type == "Trailer"){
			type = 3;
		} else if (truck_type == "Lona") {
			type = 2;
		} else if (truck_type == "Furgoneta") {
			type = 1;
		}
	}
	
	public int getType() {
		return type;
	}
}
