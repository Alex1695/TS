package com.ts.app.views.reserva;

import com.ts.app.backend.Employee;
import com.ts.app.backend.booking.InsertBookings;
import com.ts.app.backend.model.booking;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.ts.app.MainView;
import com.ts.app.views.reserva.ReservaView.ReservaViewModel;

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

    @Id("check_trailer")
	private Checkbox check_trailer;
	@Id("check_furgoneta")
	private Checkbox check_furgoneta;
	@Id("check_lona")
	private Checkbox check_lona;
	@Id("pedido")
	private TextField pedido;
	@Id("matricula")
	private TextField matricula;
	@Id("check_descarga")
	private Checkbox check_descarga;
	@Id("check_carga")
	private Checkbox check_carga;
	@Id("reservar")
	private Button reservar;
	@Id("cancelar")
	private Button cancelar;
	@Id("seleccion_dia")
	private DatePicker seleccion_dia;
	@Id("seleccion_hora")
	private TimePicker seleccion_hora;
	@Id("check_reservar")
	private Checkbox check_reservar;
	@Id("check_modificar")
	private Checkbox check_modificar;
	@Id("cancelar_reserva")
	private Button cancelar_reserva;

    public ReservaView() {

    	reservar.setVisible(false);
    	cancelar.setVisible(false);
    	cancelar_reserva.setVisible(false);
    	
    	pedido.setClearButtonVisible(true);
    	matricula.setClearButtonVisible(true);
    	
    	/////////////////////// RESERVAR/MODIFICACIÓN //////////////////////////////////////
    	check_reservar.addValueChangeListener(e -> {
    		if (check_reservar.getValue().equals(true)) {
    			check_modificar.setValue(false);
    			reservar.setText("Reservar");
    			reservar.setVisible(true);
    	    	cancelar.setVisible(true);
    		}
    		
    		if (check_reservar.getValue().equals(false) && check_modificar.getValue().equals(false)) {
    			reservar.setVisible(false);
    	    	cancelar.setVisible(false);
    	    	cancelar_reserva.setVisible(false);
    		}

    	});
    	
    	check_modificar.addValueChangeListener(e -> {
    		if (check_modificar.getValue().equals(true)) {
    			check_reservar.setValue(false);
    			reservar.setText("Modificar");
    			reservar.setVisible(true);
    	    	cancelar.setVisible(true);
    	    	cancelar_reserva.setVisible(true);
    		}
    		
    		if (check_reservar.getValue().equals(true)) {
    			cancelar_reserva.setVisible(false);
    		}
    		
    		if (check_reservar.getValue().equals(false) && check_modificar.getValue().equals(false)) {
    			reservar.setVisible(false);
    	    	cancelar.setVisible(false);
    	    	cancelar_reserva.setVisible(false);
    		}
    	});
    	
    	
    	////////////////////// ELECCIÓN DE CARGA/DESCARGA /////////////////////////////
    	check_carga.addValueChangeListener(e -> {
    		if (check_carga.getValue().equals(true)) {
    			check_descarga.setValue(false);
    		}

    	});
    	
    	check_descarga.addValueChangeListener(e -> {
    		if (check_descarga.getValue().equals(true)) {
    			check_carga.setValue(false);
    		}
    	});
    	
    	/////////////////////////////////// ELECCIÓN TIPO CAMIÓN ////////////////////////
    	check_trailer.addValueChangeListener(e -> {
    		if(check_trailer.getValue().equals(true)) {
	    		check_furgoneta.setValue(false);
	    		check_lona.setValue(false);
    		}

    	});
    	
    	check_furgoneta.addValueChangeListener(e -> {
    		if(check_furgoneta.getValue().equals(true)) {
	    		check_trailer.setValue(false);
	    		check_lona.setValue(false);
    		}

    	});
    	
    	check_lona.addValueChangeListener(e -> {
    		if(check_lona.getValue().equals(true)) {
	    		check_furgoneta.setValue(false);
	    		check_trailer.setValue(false);
    		}

    	});

    	Label content = new Label("Reserva realizada!");
    	Notification notification = new Notification(content);
    	notification.setDuration(3000);
    	notification.setPosition(Position.MIDDLE);
    	
    	Label content1 = new Label(
    	        "Faltan datos o son incorrectos!");
    	Notification notification1 = new Notification(content1);
    	notification1.setDuration(3000);
    	notification1.setPosition(Position.MIDDLE);
    	
    	LocalDateTime dia_reserva = LocalDateTime.now();
    	seleccion_dia.setMin(dia_reserva.toLocalDate());
    	LocalDateTime dias_siguientes = dia_reserva.plusDays(5);

    	seleccion_dia.setMax(dias_siguientes.toLocalDate());
    	Binder<booking> binder = new Binder<>();
    	binder.forField(seleccion_dia).withValidator(
   	        value -> !DayOfWeek.SATURDAY.equals(value.getDayOfWeek()) && !DayOfWeek.SUNDAY.equals(value.getDayOfWeek()),
    	        "Debes elegir un día entre lunes y viernes").bind(booking::getArrivalDate, booking::setArrivalDate);
    	LocalDate dia = seleccion_dia.getValue();
    	cancelar.addClickListener(e -> {
    		pedido.clear();
    		matricula.clear();
    		check_descarga.setValue(false);
    		check_carga.setValue(false);
    		check_furgoneta.setValue(false);
    		check_lona.setValue(false);
    		check_trailer.setValue(false);
    		check_reservar.setValue(false);
    		check_modificar.setValue(false);
    		seleccion_dia.clear();
    		seleccion_hora.clear();
    	});
    	
    	InsertBookings prueba = new InsertBookings();
    	
    	// Imprimimos valores
    	reservar.addClickListener( e -> {
    		
    		String valor_pedido = pedido.getValue();
    		int numero_pedido = Integer.parseInt(valor_pedido);
    		
    		String accion = "";
    		String tipo = "";
    		String valor_matricula = matricula.getValue();
    		String dia_reservado = seleccion_dia.getValue().toString();
    		int validez_matricula = 0;
    		int validez_pedido = 0;
    		int carga_descarga = 0;
    		int state = 1;
    		/*seleccion_dia.addValueChangeListener(event -> {
    		    if (event.getValue() == null) {
    		        System.out.println("No date selected");
    		    } else {
    		    	fecha_reserva = event.getValue();
    		        System.out.println("Selected date: " + event.getValue());
    		    }
    		});*/
    		
    		// Comprobamos si el valor del pedido es correcto
    		if (valor_pedido.matches("^[0-9]{6}")) {
    			System.out.println("Pedido valido");
    			validez_pedido = 1;
    		} else {
    			System.out.println("Pedido invalido");
    			validez_pedido = 0;
    		}
    		
    		// Comprobamos si la matrícula tiene el formato correcto
    		if (valor_matricula.toUpperCase().matches("^[0-9]{4}[A-Z]{3}$")) {
		        System.out.println("Matrícula válida");
		        validez_matricula = 1;
		    }else{
		    	validez_matricula = 0;
		        System.out.println("Matrícula inválida");
		    }   
    		 
    		// Cogemos los valores de carga/descarga y el tipo de camión
    		if(check_descarga.getValue().equals(true)) {
    			accion = "Descarga";	
    			carga_descarga = 2;
    		}
    		
    		if(check_carga.getValue().equals(true)) {
    			accion = "Carga";	
    			carga_descarga = 1;
    		}
    		
    		if(check_trailer.getValue().equals(true)) {
    			tipo = "Trailer";	
    		}
    		
    		if(check_furgoneta.getValue().equals(true)) {
    			tipo = "Furgoneta";	
    		}
    		
    		if(check_lona.getValue().equals(true)) {
    			tipo = "Lona";	
    		}
    		
    		// Si todos los datos son correctos
    		if (validez_matricula == 1 && validez_pedido == 1
    				&& (check_descarga.getValue().equals(true) || check_carga.getValue().equals(true)) &&
    				(check_furgoneta.getValue().equals(true) || check_trailer.getValue().equals(true) || 
    						check_lona.getValue().equals(true) && check_reservar.getValue().equals(true) || check_modificar.getValue().equals(true))){
    			notification.open();
    			System.out.println("===================");
    			System.out.println("Pedido: " + valor_pedido);
        		System.out.println("Accion: " + accion);
        		System.out.println("Tipo: " + tipo);
        		System.out.println("Matricula: " + valor_matricula.toUpperCase());
        		System.out.println("Dia reserva: " + dia_reservado);
        		
        		prueba.InsertBooking(valor_matricula, numero_pedido, carga_descarga, dia, dia, state);
        		System.out.println("Después del prueba");
        		pedido.clear();
        		matricula.clear();
        		check_descarga.setValue(false);
        		check_carga.setValue(false);
        		check_furgoneta.setValue(false);
        		check_lona.setValue(false);
        		check_trailer.setValue(false);
        		check_reservar.setValue(false);
        		check_modificar.setValue(false);
    		} else {
    			notification1.open();
    		}
    		
    	});
    }
}
