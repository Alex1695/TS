package com.ts.app.views.reserva;

import com.ts.app.backend.Employee;
import com.ts.app.backend.booking.InsertBookings;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.model.dock;
import com.ts.app.backend.model.order;
import com.ts.app.backend.service.BookingService;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.ts.app.MainView;
import com.ts.app.views.reserva.ReservaView.ReservaViewModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;

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
	@Id("hour_selection")
	private ComboBox<String> hour_selection;
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
	@Id("item_action")
	private FormItem item_action;
	@Id("item_type")
	private FormItem item_type;
	@Id("item_date")
	private FormItem item_date;
	@Id("item_hour")
	private FormItem item_hour;
	@Id("item_plate")
	private FormItem item_plate;
	@Id("check_hours")
	private Button check_hours;
	@Id("date_selection")
	private ComboBox<String> date_selection;

    public ReservaView() {

    	check.setVisible(false);
    	reserve_modify.setVisible(false);
    	cancel.setVisible(false);
    	cancel_booking.setVisible(false);
    	combo_action.setItems("Descarga", "Carga");
    	combo_type.setItems("Trailer", "Lona", "Furgoneta");
    	order.setClearButtonVisible(true);
    	plate.setClearButtonVisible(true);
    	hour_selection.setReadOnly(true);
    	
    	BookingService bookings = new BookingService();
    	
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
    			Icon logoV = new Icon(VaadinIcon.EDIT);
    			check_hours.setIcon(logoV);
    			hour_selection.setReadOnly(false);
    			check_book.setValue(false);
    			reserve_modify.setText("Modificar");
    			reserve_modify.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(true);
    			item_action.setVisible(false);
    			item_type.setVisible(false);
    			item_plate.setVisible(false);
    			item_date.setVisible(false);
    			item_hour.setVisible(false);
    			
    		}
    		
    		if (check_book.getValue().equals(true)) 
    			cancel_booking.setVisible(false);
    		
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			reserve_modify.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(false);
    			plate.setReadOnly(false);
    		}
    	});
    	
    	Label booking_correct = new Label("Reserva realizada!");
    	Notification notification_booking_correct = new Notification(booking_correct);
    	notification_booking_correct.setDuration(3000);
    	notification_booking_correct.setPosition(Position.MIDDLE);
    	
    	//mensaje de pedido eliminado
    	Label booking_delete = new Label("Pedido eliminado!");
    	Notification notification_booking_delete = new Notification(booking_delete);
    	notification_booking_delete.setDuration(3000);
    	notification_booking_delete.setPosition(Position.MIDDLE);
    	
    	Label booking_wrong = new Label("Faltan datos o son incorrectos!");
    	Notification notification_booking_wrong = new Notification(booking_wrong);
    	notification_booking_wrong.setDuration(3000);
    	notification_booking_wrong.setPosition(Position.MIDDLE);
    	
    	Label order_wrong = new Label("Pedido no válido!");
    	Notification notification_order_wrong = new Notification(order_wrong);
    	notification_order_wrong.setDuration(3000);
    	notification_order_wrong.setPosition(Position.MIDDLE);
    	
    	cancel.addClickListener(e -> {
    		order.clear();
    		plate.clear();
    		check_book.setValue(false);
    		check_modify.setValue(false);
    	});
    	
    	Obtain_booking_data data = new Obtain_booking_data();
		
    	combo_action.addValueChangeListener(e -> {
    		data.setAction(e.getValue());
    	});
    	
    	combo_type.addValueChangeListener(e -> {
    		data.setType(e.getValue());
    		
    	});
    	
    	plate.addValueChangeListener(e -> {
    		data.setPlate(e.getValue());
    	});
    	
    	order.addValueChangeListener(e -> {
    		data.setOrder(e.getValue());
    	});
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date hoy = new Date();
    	String j = "";
    	List<String> p = new ArrayList<>();

    	Calendar c = Calendar.getInstance();
    	
    	c.setTime(hoy);
    	
        int addedDays = 0;
        while (addedDays < 5) {
        	c.add(Calendar.DATE, 1);
            if (!(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY|| c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                ++addedDays;
                j = dateFormat.format(c.getTime());
                p.add(j);
            }
        }
        
        date_selection.setItems(p);
        date_selection.addValueChangeListener(e -> {
        	if (e.getValue() != null) {
        		String dia = e.getValue();
	        	LocalDate localDate = LocalDate.parse(dia);
	        	data.setDay(localDate);
        	}
        });


        String[] available = {"false","false","false","false","false","false","false","false"};
		String[] hours = {"06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00"};
		List<String> hours_available = new ArrayList<>();
		
		List<Integer> docks_available = new ArrayList<>();
		
    	check_hours.addClickListener(e -> {
    		List<dock> docks = bookings.read_docks(data.getType());
    		int num_elements = docks.size();

    		for (int i = 0; i< num_elements; i++) {
    			
    			if (docks.get(i).getRange_6() == data.getAction()) { available[0] = "true"; 
    			docks_available.add(docks.get(i).getId()); 
    			}
    			if (docks.get(i).getRange_7() == data.getAction()) { available[1] = "true"; docks_available.add(docks.get(i).getId());}
    			if (docks.get(i).getRange_8() == data.getAction()) { available[2] = "true"; docks_available.add(docks.get(i).getId()); }
    			if (docks.get(i).getRange_9() == data.getAction()) { available[3] = "true"; docks_available.add(docks.get(i).getId());}
    			if (docks.get(i).getRange_10() == data.getAction()) { available[4] = "true"; docks_available.add(docks.get(i).getId());}
    			if (docks.get(i).getRange_11() == data.getAction()) { available[5] = "true"; docks_available.add(docks.get(i).getId());}
    			if (docks.get(i).getRange_12() == data.getAction()) { available[6] = "true"; docks_available.add(docks.get(i).getId());}
    			if (docks.get(i).getRange_13() == data.getAction()) { available[7] = "true"; docks_available.add(docks.get(i).getId());}
    			
    		}
    		
    		hour_selection.setReadOnly(false);
    		
    		for (int i = 0; i<available.length; i++) {
    			if (available[i] == "true") {
    				hours_available.add(hours[i]);
    			}
    		}
    		System.out.println(docks_available);
    		
    		
    		hour_selection.setItems(hours_available);
    		
    	});
    	
    	hour_selection.addValueChangeListener(e -> {
        	data.setHour(e.getValue());
        });
    	
    	check.addClickListener(e -> {
        	List<String> orders = bookings.read_order();
        	List<booking> books = bookings.read();
    		int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
        	
    		if (orders.contains(order_string) == true) {
    			
    			reserve_modify.setVisible(true);
    			cancel.setVisible(true);
    			cancel_booking.setVisible(true);
    			check.setVisible(true);
    			order.setReadOnly(true);
    			item_action.setVisible(true);
    			item_type.setVisible(true);
    			item_plate.setVisible(true);
    			item_date.setVisible(true);
    			item_hour.setVisible(true);
    			
    			int index = orders.indexOf(order_string);
    			int action = books.get(index).getLoadDownload();
    			int type = books.get(index).getTruckType();
    			String hour = books.get(index).getHour();
    			LocalDate date = books.get(index).getBookingDate();
    			String date_s = date.toString();
    			plate.setValue(books.get(index).getTruckPlate());
    			hour_selection.setItems(hours_available);
    			hour_selection.setValue(hour);
    			
    			date_selection.setValue(date_s);
    			
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
    			
    		} else {
				notification_order_wrong.open();
			}
    		
    	});
    	
    	reserve_modify.addClickListener( e -> {
        	List<String> orders = bookings.read_order();

        	String value_plate = data.getPlate();
        	int value_type = data.getType();
        	int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
        	int load_download = data.getAction();
        	LocalDate day = data.getDay();
        	String hour_inicial = data.getHour();
        	
    		int state = 1;
    		int dock_chosen = 0;
    		dock_chosen = docks_available.get(0);
    		docks_available.remove(0);
    		
    		if (value_plate != "Invalida" && value_order != 0 && value_type != 0 && day != null && load_download != 0 && hour_selection != null
    				 && check_book.getValue().equals(true) || check_modify.getValue().equals(true)){
    			
    			if (check_book.getValue().equals(true)) {
    				
	    			if (orders.contains(order_string) == true) {
	    				
	    				BookingService.create(value_plate, value_type, value_order, load_download, day, state, hour_inicial, dock_chosen);
	    				
	    				order.clear();
	            		plate.clear();
	            		check_book.setValue(false);
	            		check_modify.setValue(false);
	            		combo_action.clear();
	            		combo_type.clear();
	            		hour_selection.clear();
	            		notification_booking_correct.open();

	    			} else {
	    				notification_order_wrong.open();
	    			}
	    		} else if (check_modify.getValue().equals(true)) {
    				
    				BookingService.update(value_plate, value_type, value_order, load_download, day, hour_inicial, dock_chosen);
    				
    				order.clear();
            		plate.clear();
            		check_book.setValue(false);
            		check_modify.setValue(false);
            		combo_action.clear();
            		combo_type.clear();
            		hour_selection.clear();
            		order.setReadOnly(false);
            		item_action.setVisible(false);
        			item_type.setVisible(false);
        			item_plate.setVisible(false);
        			item_date.setVisible(false);
        			item_hour.setVisible(false);
        			
            		booking_correct.setText("Modificación realizada");
            		notification_booking_correct.open();
            		
            		
    			} else {
	    			notification_booking_wrong.open();
	    		}
    		}
    	});
    	
    	cancel_booking.addClickListener( e -> {
    		List<String> orders = bookings.read_order();
    		int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
    		if (orders.contains(order_string) == true) {
    			
    			BookingService.deleteOrder(value_order);
    			
    			order.clear();
        		plate.clear();
        		check_book.setValue(false);
        		check_modify.setValue(false);
        		combo_action.clear();
        		combo_type.clear();
        		
        		notification_booking_delete.open();
    		} else {
    			notification_order_wrong.open();
    		}
    	});
    }

	public class Obtain_booking_data {
    	private int action;
    	private int type;
    	private String value_plate;
    	private int value_order;
    	private LocalDate day;
    	private String hour_booking;
    	
    	public void setHour(String hour) {
    		hour_booking = hour;
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
