package com.ts.app.views.reserva;

import com.ts.app.backend.booking.Obtain_booking_data;
import com.ts.app.backend.controller.CsvReader;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.model.dock;
import com.ts.app.backend.service.BookingService;
import com.ts.app.backend.service.DockService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
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
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ts.app.views.reserva.ReservaView.ReservaViewModel;
import com.ts.app.views.utils.DictionaryManager;
import com.ts.app.views.utils.Notifications;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.html.H1;

@Route(value = "reserva" /*, layout = MainView.class*/)
@RouteAlias(value = "" /*, layout = MainView.class*/)
@PageTitle("Reserva")
@JsModule("./src/views/reserva/reserva-view.js")
@Tag("reserva-view")
@CssImport("./styles/reservas-styles.css")
public class ReservaView extends PolymerTemplate<ReservaViewModel> {

    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/reserva/reserva-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String CLASS_NAME = "reservas-styles";
	private Dialog check_information;
	
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
	@Id("h1")
	private H1 h1;
	@Id("doQuestionLbl")
	private Label doQuestionLbl;
	@Id("selectLangLbl")
	private Label selectLanLbl;
	@Id("selectLangLbl")
	private Label selectLangLbl;
	@Id("plateLbl")
	private Label plateLbl;
	@Id("actionLbl")
	private Label actionLbl;
	@Id("truckTypeLbl")
	private Label truckTypeLbl;
	@Id("dateLbl")
	private Label dateLbl;
	@Id("hourLbl")
	private Label hourLbl;
	@Id("navAdminLbl")
	private Label navAdminLbl;

    public ReservaView() throws UnsupportedEncodingException {
    	
    	init();
    	
    	// Indicate a default lenguage 
    	check_spanish.setValue(true);
    	check_english.setValue(false);

    	// Set elements of the comboboxes
    	combo_action.setItems("Descarga", "Carga");
    	combo_type.setItems("Trailer", "Lona", "Furgoneta");
    	
    	// Add behaviour of the elements
    	order.setClearButtonVisible(true);
    	plate.setClearButtonVisible(true);
    	hour_selection.setClearButtonVisible(true);
    	date_selection.setClearButtonVisible(true);
    	combo_action.setClearButtonVisible(true);
    	combo_type.setClearButtonVisible(true);
    	hour_selection.setReadOnly(true);
    	combo_action.setReadOnly(true);
    	combo_type.setReadOnly(true);
    	cancel_booking.setEnabled(false);
    	cancel_booking.setVisible(false);
    	check_info.setEnabled(false);
    	date_selection.setReadOnly(true);
    	plate.setReadOnly(true);
    	order.setReadOnly(true);
    	check.setEnabled(false);
    	check.setVisible(false);
    	cancel.setEnabled(false);
    	check_hours_modify.setVisible(false);
    	check_hours.setEnabled(false);
    	
    	// Create the buttons
    	Button reserve_modify = new Button(DictionaryManager.translateField("booking.btnBook"));
    	Button close = new Button(DictionaryManager.translateField("booking.btnClose"));
    	
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
    			check_modify.setEnabled(false);
    			check_book.setEnabled(false);
    			
    			plate.setReadOnly(false);
    			combo_action.setReadOnly(false);
    			combo_type.setReadOnly(false);
    			order.setReadOnly(false);
    			date_selection.setReadOnly(false);
    			cancel.setEnabled(true);
    			check_info.setEnabled(true);
    			check_hours.setEnabled(true);
    			try {
					reserve_modify.setText(DictionaryManager.translateField("booking.btnBook"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			hour_selection.setReadOnly(true);
    	    	combo_action.setReadOnly(true);
    	    	combo_type.setReadOnly(true);
    	    	cancel_booking.setEnabled(false);
    	    	cancel_booking.setVisible(false);
    	    	check_info.setEnabled(false);
    	    	date_selection.setReadOnly(true);
    	    	plate.setReadOnly(true);
    	    	order.setReadOnly(true);
    	    	check.setEnabled(false);
    	    	check.setVisible(false);
    	    	cancel.setEnabled(false);
    	    	check_hours_modify.setVisible(false);
    	    	check_hours.setEnabled(false);
    	    	
    	    	plate.clear();
    	    	order.clear();
    	    	combo_action.clear();
    	    	combo_type.clear();
    	    	date_selection.clear();
    	    	hour_selection.clear();
    		}
    	});
    	
    	// Behaviour when the checkbox is selected
    	check_modify.addValueChangeListener(e -> {
    		if (check_modify.getValue().equals(true)) {
    			check_book.setValue(false);
    			check_modify.setEnabled(false);
    			check_book.setEnabled(false);
    			
    			check_hours.setVisible(false);
    			check_hours_modify.setVisible(true);
    			check_hours_modify.setEnabled(false);
    			cancel.setEnabled(true);
    			order.setReadOnly(false);
    			check.setVisible(true);
    			check.setEnabled(true);
    			try {
					reserve_modify.setText(DictionaryManager.translateField("booking.checkModify"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}
    		
    		if (check_book.getValue().equals(true)) 
    			cancel_booking.setVisible(false);
    		
    		if (check_book.getValue().equals(false) && check_modify.getValue().equals(false)) {
    			hour_selection.setReadOnly(true);
    	    	combo_action.setReadOnly(true);
    	    	combo_type.setReadOnly(true);
    	    	cancel_booking.setEnabled(false);
    	    	cancel_booking.setVisible(false);
    	    	check_info.setEnabled(false);
    	    	date_selection.setReadOnly(true);
    	    	plate.setReadOnly(true);
    	    	order.setReadOnly(true);
    	    	check.setEnabled(false);
    	    	check.setVisible(false);
    	    	cancel.setEnabled(false);
    	    	check_hours_modify.setVisible(false);
    	    	check_hours_modify.setEnabled(false);
    	    	check_hours.setEnabled(false);
    	    	
    	    	plate.clear();
    	    	order.clear();
    	    	combo_action.clear();
    	    	combo_type.clear();
    	    	date_selection.clear();
    	    	hour_selection.clear();
    		}
    	});
    	
    	// Behaviour when the cancel button is clicked
    	cancel.addClickListener(e -> {
    		check_book.setEnabled(true);
    		check_modify.setEnabled(true);
    		hour_selection.setReadOnly(true);
	    	combo_action.setReadOnly(true);
	    	combo_type.setReadOnly(true);
	    	cancel_booking.setEnabled(false);
	    	cancel_booking.setVisible(false);
	    	check_info.setEnabled(false);
	    	date_selection.setReadOnly(true);
	    	plate.setReadOnly(true);
	    	order.setReadOnly(true);
	    	check.setEnabled(false);
	    	check.setVisible(false);
	    	cancel.setEnabled(false);
	    	check_hours_modify.setVisible(false);
	    	check_hours.setEnabled(false);
	    	
	    	plate.clear();
	    	order.clear();
	    	combo_action.clear();
	    	combo_type.clear();
	    	date_selection.clear();
	    	hour_selection.clear();
    		
    		if (check_book.getValue().equals(true)) {
    			check_book.setValue(false);
    			hour_selection.setReadOnly(true);
    		} else if (check_modify.getValue().equals(true)) {
    			check_modify.setValue(false);
    			check_hours.setVisible(true);
    			check_hours.setEnabled(false);
    			check_hours_modify.setVisible(false);
    			check_hours_modify.setEnabled(false);
    			hour_selection.setReadOnly(true);
    		}
    	});
    	
    	
    	// Obtain the data of the comboboxes and textfields
    	combo_action.addValueChangeListener(e -> { data.setAction(e.getValue()); data.setOldAction(e.getOldValue());
    		if (data.getOldAction() == 0) {
    		
    		} else if (data.getAction() != data.getOldAction()) {
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.changesNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			hour_selection.clear();
    		}
    	});
    	combo_type.addValueChangeListener(e -> { data.setType(e.getValue()); data.setOldType(e.getOldValue());
    		if (data.getOldType() == 0) {
    			
    		}else if (data.getType() != data.getOldType()) {
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.changesNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			hour_selection.clear();
    		}
    	});
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
    			// Show notification of correct modification
        		
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.missingDataNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
    		
    		hour_selection.setReadOnly(false);
    		
    		// Get the data of the type and the action
    		int value_type = data.getType();
    		int load_download = data.getAction();
    		String plate = data.getPlate();
    		
    		// Delete all the items
    		hours_available.removeAll(hours_available);
    		
    		// If the data is missing
    		if (value_type == 0 || load_download == 0 || plate.equals("")) {
    			// Show notification of correct modification
        		try {
					Notifications.customNotify(DictionaryManager.translateField("booking.missingDataNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
    			
    			check_info.setEnabled(true);
    			cancel_booking.setEnabled(true);
    			cancel_booking.setVisible(true);
    			order.setReadOnly(true);
        		check.setEnabled(false);
        		plate.setReadOnly(false);
        		combo_action.setReadOnly(false);
        		combo_type.setReadOnly(false);
        		date_selection.setReadOnly(false);
        		check_hours_modify.setEnabled(true);

        		int i = 0;
    			// We get the data of the order
    			for (booking element: books) {
    				// We get the order from the books made
    				int order_searched = element.getOrder_request();
    				String order_searched_s = Integer.toString(order_searched);
    				
    				
    				// If the order search and an order from the bookings made are the same
    				if (order_searched_s.equals(order_string)) {
    					// Get the index of the elements
    					i = 1;
    					int index = books.indexOf(element);
    					
    					// Get the data me need
    					int action = books.get(index).getLoadDownload();
    	    			int type = books.get(index).getTruckType();
    	    			String hour = books.get(index).getHour();
    	    			LocalDate date = books.get(index).getBookingDate();
    	    			String date_s = date.toString();
    	    			String plate_s = books.get(index).getTruckPlate();
    	    			
    	    			// We set the data into the combobox and the textfields
    	    			plate.setValue(plate_s);
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
    				}
    			}
    			if (i == 0) {
	    			check_info.setEnabled(false);
	    			cancel_booking.setEnabled(false);
	    			cancel_booking.setVisible(false);
	    			order.setReadOnly(false);
	        		check.setEnabled(true);
	        		plate.setReadOnly(true);
	        		combo_action.setReadOnly(true);
	        		combo_type.setReadOnly(true);
	        		date_selection.setReadOnly(true);
	        		check_hours_modify.setEnabled(false);
	        		
					// Show notification of booking incorrect
	    			try {
						Notifications.customNotify(DictionaryManager.translateField("booking.failNotify"), 3000, "green");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

    			}
    		} else {
    			// Show notification of booking incorrect
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.invalidOrderNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    	});

    	// Behaviour of the button
    	check_info.addClickListener(e -> { 
    		
    		List<booking> books = bookings.read();
    		List<String> orders = bookings.read_order();
    		
    		String value_plate = plate.getValue().toUpperCase();
        	String value_type = combo_type.getValue();
        	int value_order = data.getOrder();
        	String load_download = combo_action.getValue();
        	String day = date_selection.getValue();
        	String hour = hour_selection.getValue();
        	int action_element = 0;
        	int tpye_element = 0;
        	String hour_element = "";
        	
        	//if (check_modify.getValue().equals(true)) {
	        	// Order in string format
	        	String order_string =  Integer.toString(value_order);
	        	
	        	// If the order is in the database
	    		if (orders.contains(order_string) == true) {
	    			for (booking element: books) {
	    				
	    				// We get the order from the books made
	    				int order_searched = element.getOrder_request();
	    				String order_searched_s = Integer.toString(order_searched);
	    				
	    				// If the order search and an order from the bookings made are the same
	    				if (order_searched_s.equals(order_string)) {
	    					int index = books.indexOf(element);
	    					
	    					// Get the data we need
	    					action_element = books.get(index).getLoadDownload();
	    					tpye_element = books.get(index).getTruckType();
	    					hour_element = books.get(index).getHour();
	    				}
	    			}
	    		}
	    		/*if (action_element != load_download || tpye_element != value_type && hour_element.equals(hour)) {
	    			// Show notification of booking incorrect
	    			Notifications.customNotify("Se ha cambiado la acción o el tipo de camión. Debe escoger una hora para la reserva!", 3000, "green");
					hour_selection.clear();
				} */
	    	//}
	    		
    		if (value_order == 0  || hour == null || value_type == null || load_download.equals(null) || day.equals(null) || value_plate.equals(null)) {

				// Show notification of booking incorrect
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.missingInvalidNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

    		} else {
	    		// Create a new dialog
	    		check_information = new Dialog();
	    		
	        	// Create the labels to show data
    			Label title;
				try {
					title = new Label(DictionaryManager.translateField("booking.orderInfoLabel"));
		        	Label info_order = new Label(DictionaryManager.translateField("booking.orderLabel") +  Integer.toString(data.getOrder()));
		        	Label info_plate = new Label(DictionaryManager.translateField("booking.plateLabel") + data.getPlate());
		        	Label info_action = new Label(DictionaryManager.translateField("booking.actionLabel") + combo_action.getValue());
		        	Label info_type = new Label(DictionaryManager.translateField("booking.typeLabel") + combo_type.getValue());
		        	Label info_date = new Label(DictionaryManager.translateField("booking.dateLabel") + date_selection.getValue());
		        	Label info_hour = new Label(DictionaryManager.translateField("booking.hourLabel") + hour_selection.getValue());
				
	        	// Creation of the layouts
	        	VerticalLayout vertical_check_information = new VerticalLayout();
	        	HorizontalLayout horizontal_botons_check_information = new HorizontalLayout();
	        	
	        	// Addition of the components to the layouts
	        	horizontal_botons_check_information.add(close, reserve_modify);
	        	vertical_check_information.add(title, info_order, info_plate, info_action, info_type, info_date, info_hour);
	        	 
	        	check_information.add(vertical_check_information, horizontal_botons_check_information); 
	        	// Open the window
	    		check_information.open();
	    		
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} 
	    	}
    		
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
    			
    			 //We get the first dock available and delete it from the array
	    		dock_chosen = docks_available.get(0);
	    		docks_available.remove(0);
    			
    			// If the checkbox of making a booking is selected
    			if (check_book.getValue().equals(true)) {
    				
    				if (orders_booked.contains(order_string) == false) {
    			
	    				// If the order is in the database
		    			if (orders.contains(order_string) == true) {
		    				// Create the new booking
		    				BookingService.create(value_plate, value_type, value_order, load_download, day, state, hour_inicial, dock_chosen);
		    				
		    				// Behaviour when the booking is created
		    				order.clear();
		            		plate.clear();
		            		combo_action.clear();
		            		combo_type.clear();
		            		hour_selection.clear();
		            		date_selection.clear();
		            		check_book.setEnabled(true);
		            		check_modify.setEnabled(true);
		            		check_book.setValue(false);
		            		check_modify.setValue(false);
		            		
		            		// Show notification of correct booking		        		
		            		try {
								Notifications.customNotify(DictionaryManager.translateField("booking.succesNotify"), 3000, "green");
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		
	    				} else {
	    					// Show notification of booking incorrect
	    	    			try {
								Notifications.customNotify(DictionaryManager.translateField("booking.invalidOrderNotify"), 3000, "green");
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}  
	    				}
    				} else {
    					// Show notification of used ordered
    	    			try {
							Notifications.customNotify(DictionaryManager.translateField("booking.orderAlreadyBooked"), 3000, "green");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    	}
	    		
	    		// If the checkbox of modify is selected
	    		} else if (check_modify.getValue().equals(true)) {
    				// Make an update of the booking
    				BookingService.update(value_plate, value_type, value_order, load_download, day, hour_inicial, dock_chosen);
    				
    				// behaviour when the modification is done
    				order.clear();
            		plate.clear();
            		check_book.setEnabled(true);
            		check_modify.setEnabled(true);
            		check_book.setValue(false);
            		check_modify.setValue(false);
            		combo_action.clear();
            		combo_type.clear();
            		hour_selection.clear();
            		date_selection.clear();
            		order.setReadOnly(true);
        			
        			// Show notification of correct modification
            		try {
						Notifications.customNotify(DictionaryManager.translateField("booking.succesModifyNotify"), 3000, "green");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			} else {
    				// Show notification of booking incorrect
	    			try {
						Notifications.customNotify(DictionaryManager.translateField("booking.missingInvalidNotify"), 3000, "green");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    		}
    		} else {
    			// Show notification of booking incorrect
    			try {
					Notifications.customNotify(DictionaryManager.translateField("booking.missingInvalidNotify"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		check_information.close();
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
                		check_book.setEnabled(true);
                		check_modify.setEnabled(true);
                		check_book.setValue(false);
                		check_modify.setValue(false);
                		combo_action.clear();
                		combo_type.clear();
                		hour_selection.clear();
                		date_selection.clear();
                		order.setReadOnly(true);
    		    		
    		    		// Show notification when delete is correct
    		    		Notifications.customNotify(DictionaryManager.translateField("booking.deleteNotify"), 3000, "green");
    				} else {
    					// Show notification of wrong order
    	    			Notifications.customNotify(DictionaryManager.translateField("booking.invalidOrderNotify"), 3000, "green");
    		    	}

        		} else {
        			// Show notification when a book cant be cancelled
        			Notifications.customNotify(DictionaryManager.translateField("booking.imposibleCancelBook"), 3000, "green");
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
    		// Behaviuor when spanish is selected or no one because we deselect spanish
    		if (check_spanish.getValue().equals(true) || (check_spanish.getValue().equals(false) && check_english.getValue().equals(false))) {
    			check_english.setValue(false);
    			check_spanish.setValue(true);
    			try {
					DictionaryManager.setLanguage("lang_ES");
					refreshDesignLanguage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
//    		if (check_spanish.getValue().equals(false) && check_english.getValue().equals(false)) {
//    			check_spanish.setValue(true);
//    		}
    	});
    	
    	// Selection of english
    	check_english.addClickListener(e -> {
    		// Behaviuor when english is selected or no one because we deselect english
    		if (check_english.getValue().equals(true) || (check_spanish.getValue().equals(false) && check_english.getValue().equals(false))) {
    			check_spanish.setValue(false);
    			check_english.setValue(true);
    			try {
					DictionaryManager.setLanguage("lang_EN");
					refreshDesignLanguage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
//    		if (check_spanish.getValue().equals(false) && check_english.getValue().equals(false)) {
//    			check_english.setValue(true);
//    			try {
//					DictionaryManager.setLanguage("lang_ES");
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//    		}
    	});
    }

	private void init() {
		//cargamos el diccionario de la app
    	try {
			DictionaryManager.setLanguage("lang_ES");
			refreshDesignLanguage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void refreshDesignLanguage() {
		//DictionaryManager.translateField("booking.checkModify")
		try {
			h1.setText(DictionaryManager.translateField("booking.design.h1"));
			check_book.setLabel(DictionaryManager.translateField("booking.design.checkBook"));
			check_modify.setLabel(DictionaryManager.translateField("booking.design.modifyBook"));
			doQuestionLbl.setText(DictionaryManager.translateField("booking.design.doLbl"));
			selectLangLbl.setText(DictionaryManager.translateField("booking.design.selLanLabel"));
			plateLbl.setText(DictionaryManager.translateField("booking.design.plateLabel"));
			actionLbl.setText(DictionaryManager.translateField("booking.design.actionLabel"));
			truckTypeLbl.setText(DictionaryManager.translateField("booking.design.truckTypeLabel"));
			dateLbl.setText(DictionaryManager.translateField("booking.design.dateLabel"));
			hourLbl.setText(DictionaryManager.translateField("booking.design.hourLabel"));
			
			check_info.setText(DictionaryManager.translateField("booking.design.checkBookBtn"));
			cancel_booking.setText(DictionaryManager.translateField("booking.design.cancelBookBtn"));
			cancel.setText(DictionaryManager.translateField("booking.btnCancel"));
			navAdminLbl.setText(DictionaryManager.translateField("booking.btnCancel"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	
}
