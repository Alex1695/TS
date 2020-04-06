package com.ts.app.views.reserva;

import com.ts.app.backend.booking.Obtain_booking_data;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.model.dock;
import com.ts.app.backend.service.BookingService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ts.app.views.reserva.ReservaView.ReservaViewModel;
import com.ts.app.views.utils.DictionaryManager;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;

@Route(value = "reserva" /*, layout = MainView.class*/)
@RouteAlias(value = "" /*, layout = MainView.class*/)
@PageTitle("Reserva")
@JsModule("./src/views/reserva/reserva-view.js")
@Tag("reserva-view")
public class ReservaView extends PolymerTemplate<ReservaViewModel> {

    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/reserva/reserva-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static interface ReservaViewModel extends TemplateModel {

    }

	@Id("order")
	private TextField order;
	@Id("plate")
	private TextField plate;
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
	@Id("admin_button")
	private Button admin_button;
	@Id("check_hours_modify")
	private Button check_hours_modify;
	@Id("check_info")
	private Button check_info;
	@Id("check_spanish")
	private Checkbox check_spanish;
	@Id("check_english")
	private Checkbox check_english;

    public ReservaView() {
    	
    	init();

    	check_spanish.setValue(true);
    	check_english.setValue(false);

    	check.setVisible(false);
    	check_info.setVisible(false);
    	cancel.setVisible(false);
    	check_hours_modify.setVisible(false);
    	cancel_booking.setVisible(false);
    	combo_action.setItems("Descarga", "Carga");
    	combo_type.setItems("Trailer", "Lona", "Furgoneta");
    	order.setClearButtonVisible(true);
    	plate.setClearButtonVisible(true);
    	hour_selection.setReadOnly(true);
    	
    	
    	// Create the buttons
    	Button reserve_modify = new Button("Reservar");
    	Button close = new Button("Cerrar");
    	
    	// Set width and themes of the buttons
    	close.setWidth("150px");
    	reserve_modify.setWidth("150px");
    	reserve_modify.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    	
    	// Creation of BookingService
    	BookingService bookings = new BookingService();
    	
    	// Creation of Obtain_booking_data
    	Obtain_booking_data data = new Obtain_booking_data();
    	
    	// Behaviour when the checkbox is selected
    	check_book.addValueChangeListener(e -> {
    		if (check_book.getValue().equals(true)) {
    			check_modify.setValue(false);
    			reserve_modify.setText("Reservar");
    			check_info.setVisible(true);
    			cancel.setVisible(true);
    		}
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			check_info.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(false);
    		}
    	});
    	
    	// Behaviour when the checkbox is selected
    	check_modify.addValueChangeListener(e -> {
    		if (check_modify.getValue().equals(true)) {
    			hour_selection.setReadOnly(false);
    			check_book.setValue(false);
    			reserve_modify.setText("Modificar");
    			check_info.setVisible(false);
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
    			check_info.setVisible(false);
    			cancel.setVisible(false);
    			cancel_booking.setVisible(false);
    			check.setVisible(false);
    			plate.setReadOnly(false);
    			order.setReadOnly(false);
    			order.clear();
    			plate.clear();
    			hour_selection.setReadOnly(true);
    			combo_action.clear();
    			combo_type.clear();
    			date_selection.clear();
    			item_action.setVisible(true);
    			item_type.setVisible(true);
    			item_plate.setVisible(true);
    			item_date.setVisible(true);
    			item_hour.setVisible(true);
    		}
    	});
    	
    	// Creation of the notification of a correct booking
    	Label booking_correct = new Label("Reserva realizada!");
    	Notification notification_booking_correct = new Notification(booking_correct);
    	notification_booking_correct.setDuration(3000);
    	notification_booking_correct.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification of elimination of a booking
    	Label booking_delete = new Label("Reserva eliminada!");
    	Notification notification_booking_delete = new Notification(booking_delete);
    	notification_booking_delete.setDuration(3000);
    	notification_booking_delete.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification when missing data
    	Label booking_wrong = new Label("Faltan datos o son incorrectos!");
    	Notification notification_booking_wrong = new Notification(booking_wrong);
    	notification_booking_wrong.setDuration(3000);
    	notification_booking_wrong.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification when the order is incorrect
    	Label order_wrong = new Label("Pedido no válido!");
    	Notification notification_order_wrong = new Notification(order_wrong);
    	notification_order_wrong.setDuration(3000);
    	notification_order_wrong.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification when there is missing data for the hours
    	Label missing_data_hours = new Label("Faltan el dato del tipo de camión, si es carga/descarga o la matrícula!");
    	Notification notification_missing_data_hours = new Notification(missing_data_hours);
    	notification_missing_data_hours.setDuration(3000);
    	notification_missing_data_hours.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification when there is less than 24hours to the booking date
    	Label booking_cancelation_warning = new Label("No se puede cancelar la reserva");
    	Notification notification_booking_cancelation_warning = new Notification(booking_cancelation_warning);
    	notification_booking_cancelation_warning.setDuration(3000);
    	notification_booking_cancelation_warning.setPosition(Position.MIDDLE);
    	
    	// Creation of the notification when the orders is already used for a booking
    	Label booking_used_order_warning = new Label("No se puede realizar la reserva. Ya se ha realizado una reserva con ese pedido!");
    	Notification notification_used_order_warning = new Notification(booking_used_order_warning);
    	notification_used_order_warning.setDuration(3000);
    	notification_used_order_warning.setPosition(Position.MIDDLE);
    	
    	// Behaviour when the cancel button is clicked
    	cancel.addClickListener(e -> {
    		order.clear();
    		plate.clear();
    		hour_selection.clear();
    		date_selection.clear();
    		combo_action.clear();
    		combo_type.clear();
    		check_book.setValue(false);
    		check_modify.setValue(false);
    		check_hours.setVisible(true);
    		check_hours_modify.setVisible(false);
    		
    		if (check_book.getValue().equals(true)) {
    			hour_selection.setReadOnly(true);
    		} else if (check_modify.getValue().equals(true)) {
    			hour_selection.setReadOnly(false);
    		}
    	});
    	
    	// Obtain the data of the comboboxes and textfields
    	combo_action.addValueChangeListener(e -> { data.setAction(e.getValue()); });
    	combo_type.addValueChangeListener(e -> { data.setType(e.getValue()); });
    	plate.addValueChangeListener(e -> { data.setPlate(e.getValue()); });
    	order.addValueChangeListener(e -> { data.setOrder(e.getValue()); });
    	
    	// Change the format of the date
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	// Obtain the actual date
    	Date today = new Date();
    	
    	// Create a string to save the date in a string format
    	String dates_string = "";
    	
    	// A list to contain the days available to make a booking
    	List<String> days_available = new ArrayList<>();

    	// Instance to the calendar
    	Calendar calendar = Calendar.getInstance();
    	
    	// Set time to the actual day
    	calendar.setTime(today);
    	
    	// Variable to control the amount of days we add
        int addedDays = 0;
        
        //holidays
        ArrayList<String> holidays = new ArrayList<String>();
        holidays = BookingService.holidaysDays();
        
        // Adding days to the available days
        while (addedDays < 5) {
        	// Adding a new day to the date
        	calendar.add(Calendar.DATE, 1);
        	
        	// If the day is not in the weekend
            if (!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                ++addedDays;
                
                // Get the day in a string format
                dates_string = dateFormat.format(calendar.getTime());
                
                // Add the day into the list
                days_available.add(dates_string);
                int aux1=0;
                aux1=days_available.size();
                days_available.removeAll(holidays);
                int aux2=0;
                aux2=days_available.size();
                if(aux1 != aux2) {
                	--addedDays;
                }
            }
        }
        
        // Set the items of the combobox with the elements of the list
        date_selection.setItems(days_available);
        
        // We get the value of the optiones selected
        date_selection.addValueChangeListener(e -> {
        	if (e.getValue() != null) {
        		String day = e.getValue();
	        	LocalDate localDate = LocalDate.parse(day);
	        	data.setDay(localDate);
        	}
        });

        // Create strings and list to save the hours
        String[] available = {"false","false","false","false","false","false","false","false"};
		String[] hours = {"06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00"};
		List<String> hours_available = new ArrayList<>();
		
		// Create a list to save the docks available
		List<Integer> docks_available = new ArrayList<>();
		
		// When the button is clicked
    	check_hours.addClickListener(e -> {

    		// Get the data of the type and the action
    		int value_type = data.getType();
    		int load_download = data.getAction();
    		String plate = data.getPlate();
    		
    		// Delete all the items
    		hours_available.removeAll(hours_available);
    		
    		// If the data is missing
    		if (value_type == 0 || load_download == 0 || plate.equals("")) {
    			// Show notification of missing data
    			notification_missing_data_hours.open();
    		} else {
	    		// Obtain the data of the docks
	    		List<dock> docks = bookings.read_docks(data.getType());
	    		List<String> hours_searched = bookings.read_hours(plate);
	    		
	    		// Get the numbers of docks
	    		int num_elements = docks.size();
	
	    		for (int i = 0; i< num_elements; i++) {
	    			// If the dock has the same action that selected (load/download)
	    			// Set the array element to true
	    			if (docks.get(i).getRange_6() == data.getAction()) { available[0] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_7() == data.getAction()) { available[1] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_8() == data.getAction()) { available[2] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_9() == data.getAction()) { available[3] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_10() == data.getAction()) { available[4] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_11() == data.getAction()) { available[5] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_12() == data.getAction()) { available[6] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_13() == data.getAction()) { available[7] = "true"; docks_available.add(docks.get(i).getId());}
	    		}
	    		
	    		// Make it editable
	    		hour_selection.setReadOnly(false);
	    		
	    		// Adding the hours to the array 
	    		for (int i = 0; i<available.length; i++) {
	    			if (available[i] == "true") {
	    				hours_available.add(hours[i]);
	    			}
	    		}

	    		for (int i = 0; i<hours_available.size(); i++) {
	    			for (int j = 0; j<hours_searched.size();j++) {
	    				if (hours_available.get(i).equals(hours_searched.get(j))) {
		    				hours_available.remove(i);
		    			}
	    			}
	    			
	    		}
	    		hour_selection.setItems(hours_available);
	    		
    		}
    	});
    	
    	// When the button is clicked
    	check_hours_modify.addClickListener(e -> {

    		// Get the data of the type and the action
    		int value_type = data.getType();
    		int load_download = data.getAction();
    		String plate = data.getPlate();
    		
    		// Delete all the items
    		hours_available.removeAll(hours_available);
    		
    		// If the data is missing
    		if (value_type == 0 || load_download == 0 || plate.equals("")) {
    			// Show notification of missing data
    			notification_missing_data_hours.open();
    		} else {
	    		// Obtain the data of the docks
	    		List<dock> docks = bookings.read_docks(data.getType());
	    		List<String> hours_searched = bookings.read_hours(plate);
	    		
	    		// Get the numbers of docks
	    		int num_elements = docks.size();
	
	    		for (int i = 0; i< num_elements; i++) {
	    			// If the dock has the same action that selected (load/download)
	    			// Set the array element to true
	    			if (docks.get(i).getRange_6() == data.getAction()) { available[0] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_7() == data.getAction()) { available[1] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_8() == data.getAction()) { available[2] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_9() == data.getAction()) { available[3] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_10() == data.getAction()) { available[4] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_11() == data.getAction()) { available[5] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_12() == data.getAction()) { available[6] = "true"; docks_available.add(docks.get(i).getId());}
	    			if (docks.get(i).getRange_13() == data.getAction()) { available[7] = "true"; docks_available.add(docks.get(i).getId());}
	    		}
	    		
	    		// Make it editable
	    		hour_selection.setReadOnly(false);
	    		
	    		// Adding the hours to the array 
	    		for (int i = 0; i<available.length; i++) {
	    			if (available[i] == "true") {
	    				hours_available.add(hours[i]);
	    			}
	    		}

	    		for (int i = 0; i<hours_available.size(); i++) {
	    			for (int j = 0; j<hours_searched.size();j++) {
	    				if (hours_available.get(i).equals(hours_searched.get(j))) {
		    				hours_available.remove(i);
		    			}
	    			}
	    			
	    		}
	    		hour_selection.setItems(hours_available);
    		}
    	});
    	
    	// Select the hour of the booking
    	hour_selection.addValueChangeListener(e -> { data.setHour(e.getValue()); });

    	// Behaviour of the check data for one order
    	check.addClickListener(e -> {
    		// We read the data of the orders and the bookings saved
        	List<String> orders = bookings.read_order();
        	List<booking> books = bookings.read();
        	
        	// Order introduced in the textfield
    		int value_order = data.getOrder();
    		
    		// Order in string format
        	String order_string =  Integer.toString(value_order);
        	
        	// If the order is in the database
    		if (orders.contains(order_string) == true) {
    			
    			check_info.setVisible(true);
    			cancel.setVisible(true);
    			cancel_booking.setVisible(true);
    			check.setVisible(true);
    			order.setReadOnly(true);
    			item_action.setVisible(true);
    			item_type.setVisible(true);
    			item_plate.setVisible(true);
    			item_date.setVisible(true);
    			item_hour.setVisible(true);
    			check_hours.setVisible(false);
    			check_hours_modify.setVisible(true);
    			
    			// We get the data of the order 
    			int index = orders.indexOf(order_string);
    			int action = books.get(index).getLoadDownload();
    			int type = books.get(index).getTruckType();
    			String hour = books.get(index).getHour();
    			LocalDate date = books.get(index).getBookingDate();
    			String date_s = date.toString();
    			plate.setValue(books.get(index).getTruckPlate());
    			
    			// We set the data into the combobox and the textfields
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

    	// Behaviour of the button
    	check_info.addClickListener(e -> { 
    		// Create a new dialog
    		Dialog check_information = new Dialog();
    		
        	// Create the labels to show data
    		Label title = new Label("Los datos del pedido son: ");
        	Label info_order = new Label("- El pedido es: " +  Integer.toString(data.getOrder()));
        	Label info_plate = new Label("- La matrícula del camión es: " + data.getPlate());
        	Label info_action = new Label("- La acción requerida es: " + combo_action.getValue());
        	Label info_type = new Label("- El camión es un: " + combo_type.getValue());
        	Label info_date = new Label("- El día de la reserva es el: " + date_selection.getValue());
        	Label info_hour = new Label("- La hora reservada es: " + hour_selection.getValue());
    		
        	// Creation of the layouts
        	VerticalLayout vertical_check_information = new VerticalLayout();
        	HorizontalLayout horizontal_botons_check_information = new HorizontalLayout();
        	
        	// Addition of the components to the layouts
        	horizontal_botons_check_information.add(close, reserve_modify);
        	vertical_check_information.add(title, info_order, info_plate, info_action, info_type, info_date, info_hour);
        	check_information.add(vertical_check_information, horizontal_botons_check_information);
      
        	// Open the window
    		check_information.open(); 
    		
    		// Behaviour of the button
    		close.addClickListener(e1 -> { 
    			// Closing the window
    			check_information.close(); 
    		});
    	});
    	
    	// Behaviour when the button reserve or modify is clicked
    	reserve_modify.addClickListener( e -> {
    		// We get the data of the orders
        	List<String> orders = bookings.read_order();
        	List<String> orders_booked = bookings.orders_booked();
        	
        	// We get the data of the textfields and comboboxes
        	String value_plate = data.getPlate().toUpperCase();
        	int value_type = data.getType();
        	int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
        	int load_download = data.getAction();
        	LocalDate day = data.getDay();
        	String hour_inicial = data.getHour();
        	
    		int state = 1;
    		int dock_chosen = 0;
    		
  
    		
    		// If all the form items are filled
    		if (value_plate != "" && value_order != 0 && value_type != 0 && day != null && load_download != 0 && hour_selection != null
    				 && check_book.getValue().equals(true) || check_modify.getValue().equals(true)){
    			
    			
    			// If the checkbox of making a booking is selected
    			if (check_book.getValue().equals(true)) {
    				
    				if (orders_booked.contains(order_string) == false) {
    			
	    				// If the order is in the database
		    			if (orders.contains(order_string) == true) {
		    					
		    				// We get the first dock available and delete it from the array
		    	    		dock_chosen = docks_available.get(0);
		    	    		docks_available.remove(0);
		    	    		
		    				// Create the new booking
		    				BookingService.create(value_plate, value_type, value_order, load_download, day, state, hour_inicial, dock_chosen);
		    				
		    				// Behaviour when the booking is created
		    				order.clear();
		            		plate.clear();
		            		check_book.setValue(false);
		            		check_modify.setValue(false);
		            		combo_action.clear();
		            		combo_type.clear();
		            		hour_selection.clear();
		            		date_selection.clear();
		            		
		            		// Show notification of correct booking
		            		notification_booking_correct.open();
	    				} else {
	    					notification_order_wrong.open();
	    				}
    				} else {
    					notification_used_order_warning.open();
		    	}
	    		
	    		// If the checkbox of modify is selected
	    		} else if (check_modify.getValue().equals(true)) {
    				// Make an update of the booking
    				BookingService.update(value_plate, value_type, value_order, load_download, day, hour_inicial, dock_chosen);
    				
    				// behaviour when the modification is done
    				order.clear();
            		plate.clear();
            		check_book.setValue(false);
            		check_modify.setValue(false);
            		combo_action.clear();
            		combo_type.clear();
            		hour_selection.clear();
            		date_selection.clear();
            		order.setReadOnly(false);
            		item_action.setVisible(false);
        			item_type.setVisible(false);
        			item_plate.setVisible(false);
        			item_date.setVisible(false);
        			item_hour.setVisible(false);
        			
        			// Show notification of correct modification
            		booking_correct.setText("Modificación realizada");
            		notification_booking_correct.open();
            		
    			} else {
    				// Show notification of booking incorrect
	    			notification_booking_wrong.open();
	    		}
    		} else {
    			notification_booking_wrong.open();
    		}
    	});
		
    	// Behaviour when the cancel booking button is clicked
    	cancel_booking.addClickListener( e -> {
    		
    		// Get the data of the orders
    		List<String> orders = bookings.read_order();
    		
			// Get the value of the texfield of orders
    		int value_order = data.getOrder();
        	String order_string =  Integer.toString(value_order);
        	
        	// Values used to change the state of the dock
        	String hour="";
        	hour = data.getHour();
        	
        	// Get the actual day and reserved day
        	LocalDate today_date = LocalDate.now();
    		LocalDate reserved_day = data.getDay();
    		
    		//HH converts hour in 24 hours format (0-23), day calculation
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    		Date date_today = null;
    		Date date_reserved = null;

    		try {
    			date_today = format.parse(today_date.toString());
    			date_reserved = format.parse(reserved_day.toString());

    			// Calculate the difference between the days
    			long difference = date_reserved.getTime() - date_today.getTime();
    			
    			// Calculate the days of difference
    			long days = difference / (24 * 60 * 60 * 1000);
    			
    			// If there is more than a day, it can be cancelled
        		if (days >1) {

    		    	// If the order is in the database
    				if (orders.contains(order_string) == true) {
    					// Change the state of the dock
    					BookingService.updateDockDelete(hour, order_string);
    					
    					// Delete of the booking made
    					BookingService.deleteOrder(value_order);
    					
    					// Behaviour when the delete is done
    					order.clear();
    		    		plate.clear();
    		    		check_book.setValue(false);
    		    		check_modify.setValue(false);
    		    		combo_action.clear();
    		    		combo_type.clear();
    		    		hour_selection.clear();
    		    		date_selection.clear();
    		    		
    		    		// Show notification when delete is correct
    		    		notification_booking_delete.open();
    				} else {
    					// Show notification of wrong order
    					notification_order_wrong.open();
    		    	}

        		} else {
        			// Show the notification
        			notification_booking_cancelation_warning.open();
        		}
    		} catch (Exception e1) {
    			e1.printStackTrace();
    		}
    	});
    	
    	//Admin Button clicklistener to navigate /login 
    	admin_button.addClickListener(e -> {
    		admin_button.getUI().ifPresent(ui ->
            ui.navigate("login"));
    	});
    	
    	// Selection of spanish
    	check_spanish.addClickListener(e -> {
    		// Behaviuor when spanish is selected
    		if (check_spanish.getValue().equals(true)) {
    			check_english.setValue(false);
    		}
    		
    		if (check_spanish.getValue().equals(false) && check_english.getValue().equals(false)) {
    			check_spanish.setValue(true);
    		}
    	});
    	
    	// Selection of english
    	check_english.addClickListener(e -> {
    		// Behaviuor when english is selected
    		if (check_english.getValue().equals(true)) {
    			check_spanish.setValue(false);
    			try {
					DictionaryManager.setLanguage("lang_EN");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    		if (check_spanish.getValue().equals(false) && check_english.getValue().equals(false)) {
    			check_english.setValue(true);
    			try {
					DictionaryManager.setLanguage("lang_ES");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
    }

	private void init() {
		//cargamos el diccionario de la app
    	try {
			DictionaryManager.setLanguage("lang_ES");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
