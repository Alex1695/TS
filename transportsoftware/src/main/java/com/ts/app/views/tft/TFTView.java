package com.ts.app.views.tft;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

import com.ts.app.MainView;
import com.ts.app.backend.booking.Obtain_booking_data;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.service.BookingService;
import com.ts.app.views.tft.TFTView.TFTViewModel;
import com.ts.app.views.utils.DictionaryManager;
import com.ts.app.views.utils.Notifications;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;

@Route(value = "tft", layout = MainView.class)
@PageTitle("TFT")
@JsModule("./src/views/tft/t-ft-view.js")
@Tag("t-ft-view")
public class TFTView extends PolymerTemplate<TFTViewModel> {

    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/tft/t-ft-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    /**
     * All data we send to the client (template). Implementation is generated by the
     * framework.
     */
    public static interface TFTViewModel extends TemplateModel {

    }

    // elementos de la aplicacion
	@Id("grid_trucks")
	private Grid <booking> grid_trucks;

	@Id("text_entrance")
	private TextField text_entrance;

	@Id("button_entrance")
	private Button button_entrance;

	@Id("text_exit")
	private TextField text_exit;

	@Id("button_exit")
	private Button button_exit;
	
	@Id("text_hour_entrance")
	private TextField text_hour_entrance;

	@Id("search_button")
	private Button search_button;

	@Id("combo_hours")
	private ComboBox<String> combo_hours;

	@Id("h2")
	private H2 h1;
	
	// comienzo de la estructura de la aplicacion
    public TFTView() throws UnsupportedEncodingException {

    	refreshDesignLanguage();
    	
    	BookingService bookings = new BookingService();
    	Obtain_booking_data data = new Obtain_booking_data();

    	Label orderLbl = new Label(); 
    	Label plateLbl = new Label();
    	Label dockLbl = new Label();
    	Label hourLbl = new Label();
    	Label actionLbl = new Label();
    	Label typeLbl = new Label();
    	
    	orderLbl.setText(DictionaryManager.translateField("tft.design.orderLabel"));
    	plateLbl.setText(DictionaryManager.translateField("tft.design.plateLabel"));
    	dockLbl.setText(DictionaryManager.translateField("tft.design.dockLabel"));
    	hourLbl.setText(DictionaryManager.translateField("tft.design.hourLabel"));
    	actionLbl.setText(DictionaryManager.translateField("tft.design.actionLabel"));
    	typeLbl.setText(DictionaryManager.translateField("tft.design.typeLabel"));
    	
    	grid_trucks.addColumn(booking::getOrder_request).setHeader(orderLbl).setAutoWidth(true);
		grid_trucks.addColumn(booking::getTruckPlate).setHeader(plateLbl).setAutoWidth(true);
		grid_trucks.addColumn(booking::getDock).setHeader(dockLbl).setAutoWidth(true);
		grid_trucks.addColumn(booking::getHour).setHeader(hourLbl).setAutoWidth(true);
		grid_trucks.addColumn(booking::getLoadDownload).setHeader(actionLbl).setAutoWidth(true);
		grid_trucks.addColumn(booking::getTruckType).setHeader(typeLbl).setAutoWidth(true);
    	
		grid_trucks.setWidth("930px");
		grid_trucks.setHeight("300px");
		grid_trucks.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid_trucks.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		// variables para coger las matriculas y las horas
		text_entrance.addValueChangeListener(e -> { data.setPlate(e.getValue()); });
		text_exit.addValueChangeListener(e -> { data.setPlate(e.getValue()); });
		text_hour_entrance.addValueChangeListener(e -> { data.setArrival_hour(e.getValue()); });
		combo_hours.addValueChangeListener(e -> {data.setHour(e.getValue()); });
		
		// comienzo de la funcion del boton de entrada
    	button_entrance.addClickListener(e -> {
    		
    		String value_plate = text_entrance.getValue().toUpperCase();
    		
    		String entrance_hour = text_hour_entrance.getValue();

    		String hour_selected = data.getHour();
  
    		
    		if (value_plate.equals("")) {    		
    			try {
					Notifications.customNotify(DictionaryManager.translateField("tft.design.missingData"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    		else if (entrance_hour.equals("")) {
    			try {
					Notifications.customNotify(DictionaryManager.translateField("tft.design.missingData"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    		else if (hour_selected == null) {
    			try {
					Notifications.customNotify(DictionaryManager.translateField("tft.design.missingData"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    		else {		
    			
    			data.setPlate(value_plate);
    				
    			if(data.getPlate() == "") {
    				
    				try {
						Notifications.customNotify(DictionaryManager.translateField("tft.design.wrongPlate"), 3000, "green");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    			
    			else {
    				
    				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
					Date d = null;
					try {
						d = df.parse(hour_selected);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					Calendar cal = Calendar.getInstance();
					cal.setTime(d);
					cal.add(Calendar.MINUTE, 10);
					
					SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
					Date d2 = null;
					try {
						d2 = df2.parse(entrance_hour);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(d2);
					
					
					long inicio = cal.getTimeInMillis();
					long actual = cal2.getTimeInMillis();

					if ((actual > inicio))
					{
						try {
							Notifications.customNotify(DictionaryManager.translateField("tft.design.bookinLateLbl"), 3000, "green");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						bookings.delete_booking(value_plate, hour_selected);
					}
					else
					{
	    				List<booking> books = bookings.search_plate(value_plate, entrance_hour, hour_selected);
	    				data.setBooks(books);
	    				grid_trucks.setItems(books);

	    				try {
	    					Notifications.customNotify(DictionaryManager.translateField("tft.design.truckEnter"), 3000, "green");
	    				} catch (UnsupportedEncodingException e1) {
	    					// TODO Auto-generated catch block
	    					e1.printStackTrace();
	    				}
					}
				}
			}
    			
    		text_entrance.clear();
    		text_hour_entrance.clear();
    		combo_hours.clear();
    		
    		search_button.setEnabled(true);
    		
    	});
    	
    	// comienzo de la funcion del boton de salida
    	button_exit.addClickListener(e -> {
    		
    		String value_plate2 = text_exit.getValue().toUpperCase();
    		String hour_selected = "";
    		String truck_plate = "";
    		String newTime = "";
    		
    		List<booking> books = data.getBooks();
    		
    		if (value_plate2.equals("")) {    
    			
    			try {
					Notifications.customNotify(DictionaryManager.translateField("tft.design.missingData"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    		else {
    			
    			data.setPlate(value_plate2);
				
    			if(data.getPlate() == "") {
    				
    				try {
						Notifications.customNotify(DictionaryManager.translateField("tft.design.wrongPlate"), 3000, "green");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    			
    			else {
    			
					if(books == null) {
						
						try {
							Notifications.customNotify(DictionaryManager.translateField("tft.design.nullPlateLbl"), 3000, "green");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					else {	
						
						for (int i = 0; i<books.size(); i++) {	
							
							truck_plate = books.get(i).getTruckPlate();
		
							if(truck_plate.contains(value_plate2) == false) {
								
								try {
									Notifications.customNotify(DictionaryManager.translateField("tft.design.nullPlateLbl"), 3000, "green");
								} catch (UnsupportedEncodingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}		
							} 
							
							else if (truck_plate.contains(value_plate2) == true){
								
								hour_selected = books.get(i).getHour();
								int type = books.get(i).getTruckType();
								int loadDownload = books.get(i).getLoadDownload();
								String hour = books.get(i).getHour();
								
								
								// 1. furgoneta, carga = 20m, descarga = 15m
								if (type == 1) {
									
									if (loadDownload == 1) {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
										Date d = null;
										
										try {
											d = df.parse(hour);
										}
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 20);
			    						newTime = df.format(cal.getTime());
									}
									
									else {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
										Date d = null;
										
										try {
											d = df.parse(hour);
										}
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 15);
			    						newTime = df.format(cal.getTime());
									}
								}
								
								// 2. lona, carga = 40m, descarga = 30m
								else if (type == 2) {
									
									if (loadDownload == 1) {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
										Date d = null;
										
										try {
											d = df.parse(hour);
										} 
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 40);
			    						newTime = df.format(cal.getTime());
									}
									
									else {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			    						Date d = null;
			    						
										try {
											d = df.parse(hour);
										}
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 30);
			    						newTime = df.format(cal.getTime());
									}
								}
								
								// 3. trailer, carga = 60m, descarga = 45m
								else if (type == 3) {
									
									if (loadDownload == 1) {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
										Date d = null;
										
										try {
											d = df.parse(hour);
										} 
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 60);
			    						newTime = df.format(cal.getTime());
									}
									
									else {
										
										SimpleDateFormat df = new SimpleDateFormat("HH:mm");
										Date d = null;
										
										try {
											d = df.parse(hour);
										} 
										
										catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										
			    						Calendar cal = Calendar.getInstance();
			    						cal.setTime(d);
			    						cal.add(Calendar.MINUTE, 45);
			    						newTime = df.format(cal.getTime());
									}
								}
								
								bookings.book_done(value_plate2, 0, newTime, hour_selected);
								
								books.remove(i);
								data.setBooks(books);
								
								grid_trucks.setItems(books);
								
								try {
			    					Notifications.customNotify(DictionaryManager.translateField("tft.design.truckExit"), 3000, "green");
			    				} catch (UnsupportedEncodingException e1) {
			    					// TODO Auto-generated catch block
			    					e1.printStackTrace();
			    				}
							}
						}
					}
    			}
    			
    			text_exit.clear();
    		}
    	});
    	
    	search_button.setDisableOnClick(true);
    	
    	// comienzo de la funcion del boton de busqueda de las horas de reserva
    	search_button.addClickListener(e -> {
    		
    		boolean encontrado = false;
    		List<booking> books = bookings.read();
    		List<String> hours = new ArrayList<>();
    		String value_plate = text_entrance.getValue().toUpperCase();

    		hours.removeAll(hours);
	
    		for (booking element : books) {
    			if (element.getTruckPlate().contains(value_plate) == true) {
    				
    				encontrado = true;
    				hours.add(element.getHour());
    			}
    		}
    		
    		if(encontrado == false) {
    			try {
					Notifications.customNotify(DictionaryManager.translateField("tft.design.nullPlateLbl"), 3000, "green");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}

    		combo_hours.setItems(hours);
    		
    	});  	
    }
    
    private void refreshDesignLanguage() {
		//DictionaryManager.translateField("booking.checkModify")
		try {
			h1.setText(DictionaryManager.translateField("tft.design.h1"));
			text_entrance.setLabel(DictionaryManager.translateField("tft.design.textEntrance"));
			combo_hours.setLabel(DictionaryManager.translateField("tft.design.textComboHours"));
			text_hour_entrance.setLabel(DictionaryManager.translateField("tft.design.textHourEntrance"));
			button_entrance.setText(DictionaryManager.translateField("tft.design.buttonEntrance"));
			text_exit.setLabel(DictionaryManager.translateField("tft.design.textExit"));
			button_exit.setText(DictionaryManager.translateField("tft.design.buttonExit"));
			text_hour_entrance.setPlaceholder(DictionaryManager.translateField("tft.design.Placeholder"));
	    	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
    
}