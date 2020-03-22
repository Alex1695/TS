package com.ts.app.views.reserva;

import com.ts.app.backend.Employee;
import com.ts.app.backend.booking.InsertBookings;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.model.order;
import com.ts.app.backend.service.BookingService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.ts.app.MainView;
import com.ts.app.views.reserva.ReservaView.ReservaViewModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.component.html.NativeButton;

@Route(value = "reserva", layout = MainView.class)
@PageTitle("Reserva")
@JsModule("./src/views/reserva/reserva-view.js")
@Tag("reserva-view")
public class ReservaView extends PolymerTemplate<ReservaViewModel> {

    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/reserva/reserva-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    public static interface ReservaViewModel extends TemplateModel {

    }

	@Id("order")
	private TextField order;
	@Id("plate")
	private TextField plate;
	@Id("reserve_modify")
	private Button reserve_modify;
	@Id("cancel")
	private Button cancel;
	@Id("date_selection")
	private DatePicker date_selection;
	@Id("hour_selection")
	private TimePicker hour_selection;
	@Id("check_book")
	private Checkbox check_book;
	@Id("check_modify")
	private Checkbox check_modify;
	@Id("cancel_booking")
	private Button cancel_booking;
	@Id("combo_action")
	private ComboBox<String> combo_action;
	@Id("combo_type")
	private ComboBox<String> combo_type;
	@Id("check")
	private Button check;

    public ReservaView() {

    	check.setVisible(false);
    	reserve_modify.setVisible(false);
    	cancel.setVisible(false);
    	cancel_booking.setVisible(false);
    	combo_action.setItems("Descarga", "Carga");
    	combo_type.setItems("Trailer", "Lona", "Furgoneta");
    	order.setClearButtonVisible(true);
    	plate.setClearButtonVisible(true);
    	
    	check_book.addValueChangeListener(e -> {
    		if (check_book.getValue().equals(true)) {
    			check_modify.setValue(false);
    			reserve_modify.setText("Reservar");
    			reserve_modify.setVisible(true);
    			cancel.setVisible(true);
    		}
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			reserve_modify.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(false);
    		}
    	});
    	
    	check_modify.addValueChangeListener(e -> {
    		if (check_modify.getValue().equals(true)) {
    			check_book.setValue(false);
    			reserve_modify.setText("Modificar");
    			reserve_modify.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(true);
    			plate.setReadOnly(true);
    			combo_action.setReadOnly(true);
    			combo_type.setReadOnly(true);
    			date_selection.setReadOnly(true);
    			hour_selection.setReadOnly(true);
    			
    		}
    		
    		if (check_book.getValue().equals(true)) 
    			cancel_booking.setVisible(false);
    		
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			reserve_modify.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(false);
    			plate.setReadOnly(false);
    			combo_action.setReadOnly(false);
    			combo_type.setReadOnly(false);
    			date_selection.setReadOnly(false);
    			hour_selection.setReadOnly(false);
    		}
    	});
    	
    	Label booking_correct = new Label("Reserva realizada!");
    	Notification notification_booking_correct = new Notification(booking_correct);
    	notification_booking_correct.setDuration(3000);
    	notification_booking_correct.setPosition(Position.MIDDLE);
    	
    	Label booking_wrong = new Label("Faltan datos o son incorrectos!");
    	Notification notification_booking_wrong = new Notification(booking_wrong);
    	notification_booking_wrong.setDuration(3000);
    	notification_booking_wrong.setPosition(Position.MIDDLE);
    	
    	Label order_wrong = new Label("Pedido no válido!");
    	Notification notification_order_wrong = new Notification(order_wrong);
    	notification_order_wrong.setDuration(3000);
    	notification_order_wrong.setPosition(Position.MIDDLE);
    	
    	LocalDateTime day_reserve = LocalDateTime.now();
    	date_selection.setMin(day_reserve.toLocalDate());

    	Binder<booking> binder = new Binder<>();
    	binder.forField(date_selection).withValidator(
   	        value -> !DayOfWeek.SATURDAY.equals(value.getDayOfWeek()) && !DayOfWeek.SUNDAY.equals(value.getDayOfWeek()),
    	        "Debes elegir un día entre lunes y viernes").bind(booking::getArrivalDate, booking::setArrivalDate);
    	
    	cancel.addClickListener(e -> {
    		order.clear();
    		plate.clear();
    		check_book.setValue(false);
    		check_modify.setValue(false);
    		date_selection.clear();
    		hour_selection.clear();
    	});
    	
    	Obtain_booking_data data = new Obtain_booking_data();
		
    	combo_action.addValueChangeListener(e -> {
    		data.setAction(e.getValue());
    	});
    	
    	combo_type.addValueChangeListener(e -> {
    		data.setType(e.getValue());
    	});
    	
    	plate.addValueChangeListener(e -> {
    		data.setPlate(plate.getValue());
    	});
    	
    	order.addValueChangeListener(e -> {
    		data.setOrder(order.getValue());
    	});
    	
    	date_selection.addValueChangeListener(e -> {
    		data.setDay(date_selection.getValue());
    	});
    	
    	BookingService bookings = new BookingService();
    	
    	List<String> orders = bookings.read_order();
    	
    	List<booking> books = bookings.read();
    	
    	check.addClickListener(e -> {
    		int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
    		if (orders.contains(order_string) == true) {
    			
    			reserve_modify.setVisible(true);
    			cancel.setVisible(true);
    			cancel_booking.setVisible(true);
    			check.setVisible(true);
    			order.setReadOnly(true);
    			plate.setReadOnly(false);
    			combo_action.setReadOnly(false);
    			combo_type.setReadOnly(false);
    			date_selection.setReadOnly(false);
    			hour_selection.setReadOnly(false);
    			
    			int index = orders.indexOf(order_string);
    			int action = books.get(index).getLoadDownload();
    			int type = books.get(index).getTruckType();
    			LocalDate date = books.get(index).getBookingDate();
    			
    			plate.setValue(books.get(index).getTruckPlate());
    			date_selection.setValue(date);
    			
    			if (action == 1) {
    				combo_action.setValue("Carga");
    			} else if (action == 2) {
    				combo_action.setValue("Descarga");
    			}
    			
    			if (type == 1) {
    				combo_type.setValue("Furgoneta");
    			} else if (type == 2) {
    				combo_type.setValue("Lona");
    			} else if (type == 3) {
    				combo_type.setValue("Trailer");
    			}
    			
    		}
    		
    	});
    	reserve_modify.addClickListener( e -> {
    			
        	String value_plate = data.getPlate();
        	int value_type = data.getType();
        	int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
        	int load_download = data.getAction();
        	LocalDate day = data.getDay();
    		int state = 1;
    		
    		if (value_plate != "Invalida" && value_order != 0 && value_type != 0 && day != null && load_download != 0
    				 && check_book.getValue().equals(true) || check_modify.getValue().equals(true)){
    			
    			if (check_book.getValue().equals(true)) {
    				
	    			if (orders.contains(order_string) == true) {
	    				
	    				BookingService.create(value_plate, value_type, value_order, load_download, day, state);
	    				
	    				order.clear();
	            		plate.clear();
	            		check_book.setValue(false);
	            		check_modify.setValue(false);
	            		combo_action.clear();
	            		combo_type.clear();
	            		
	            		notification_booking_correct.open();
	    			} else {
	    				notification_order_wrong.open();
	    			}
	    		} else if (check_modify.getValue().equals(true)) {
    				
    				BookingService.update(value_plate, value_type, value_order, load_download, day);
    				
    				order.clear();
            		plate.clear();
            		check_book.setValue(false);
            		check_modify.setValue(false);
            		combo_action.clear();
            		combo_type.clear();
            		order.setReadOnly(false);
            		
            		booking_correct.setText("Modificación realizada");
            		notification_booking_correct.open();
    			} else {
	    			notification_booking_wrong.open();
	    		}
    		}
    	});
    }

	public class Obtain_booking_data {
    	private int action;
    	private int type;
    	private String value_plate;
    	private int value_order;
    	private LocalDate day;
    	
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
		    	value_plate = "Invalida";
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
}
