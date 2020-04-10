package com.ts.app.backend.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.ts.app.backend.BBDD_Conection;
import com.ts.app.backend.model.booking;
import com.ts.app.backend.model.dock;
import com.ts.app.backend.model.order;

public class BookingService implements CRUD{

	private List<String> orders = new ArrayList<>();
	private List<booking> bookings = new ArrayList<>();
	private List<dock> docks = new ArrayList<>();
	private List<String> orders_booked = new ArrayList<>();
	private List<String> hours_truck = new ArrayList<>();
	private List<booking> book_plate = new ArrayList<>();
	
	// Insert a new booking
	public static boolean create(String truckPlate, int truckType, int order_request, int loadDownload, LocalDate day_reserved, /*LocalDate arrivalDate,*/
		/*LocalDate departureDate,*/ int state, String hour, int dock)  {

		// Conection to database
		Connection conn = BBDD_Conection.getConexionInstance();

		// Transform the format from localdate to date
	    Date date = java.sql.Date.valueOf(day_reserved);

	    // Write the query to execute
	    String query = " INSERT INTO TB_bookings (truckPlate, truckType, order_request, loadDownload, bookingDate, dock, hour, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    // Create the mysql insert PreparedStatement
	    try {
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, truckPlate);
			preparedStmt.setInt (2, truckType);
			preparedStmt.setInt (3, order_request);
		    preparedStmt.setInt   (4, loadDownload);
		    preparedStmt.setDate (5, date);
		    preparedStmt.setInt   (6, dock);
		    preparedStmt.setString(7, hour);
		    //preparedStmt.setInt    (5, arrivalDate);
		    //preparedStmt.setInt    (5, departureDate);
		    preparedStmt.setInt    (8, state);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
		    
		    
		    //////// ACTUALIZACIÓN TABLA MUELLES
		    String range = "";
		    if (hour.equals("06:00")) {
		    	range = "range_6";
		    } else if(hour.equals("07:00")) {
		    	 range = "range_7";
		    } else if(hour.equals("08:00")) {
		    	 range = "range_8";
		    }else if(hour.equals("09:00")) {
		    	 range = "range_9";
		    }else if(hour.equals("10:00")) {
		    	 range = "range_10";
		    } else if(hour.equals("11:00")) {
		    	 range = "range_11";
		    } else if(hour.equals("12:00")) {
		    	 range = "range_12";
		    } else if(hour.equals("13:00")) {
		    	 range = "range_13";
		    }
		    
		    
		    String query_update = "UPDATE TB_docks SET " + range + " = 3 where id = " + dock + ";";
		    
		    try {
		    	PreparedStatement preparedStmt_update = conn.prepareStatement(query_update);
		    	preparedStmt_update.execute();
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		    
		   }
		    return true;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    
	   
	}

	//leemos pedido a modificar
	@Override
	public List<booking> read() {
		// TODO Auto-generated method stub
		Statement state_booking = null;
		ResultSet result_booking = null;
		
		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			state_booking = (Statement) conn.createStatement();
			result_booking = state_booking.executeQuery("SELECT * FROM DES_TS.TB_bookings");
			
			while (result_booking.next()) {
				booking book = new booking();
				book.setOrder_request(result_booking.getInt("order_request"));
				book.setTruckPlate(result_booking.getString("truckPlate"));
				book.setHour(result_booking.getString("hour"));
				book.setTruckType(result_booking.getInt("truckType"));
				book.setLoadDownload(result_booking.getInt("loadDownload"));
				book.setBookingDate(result_booking.getDate("bookingDate").toLocalDate());
				bookings.add(book);
			}
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return bookings;
	}
	
	public List<String> read_hours(String plate) {
		// TODO Auto-generated method stub
		Statement state_booking = null;
		ResultSet result_booking = null;
		
		Connection conn = BBDD_Conection.getConexionInstance();
		
		String query = "SELECT hour FROM DES_TS.TB_bookings where truckPlate = '" + plate + "';";
		
		try {
			
		  
		   
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    //preparedStmt.setString (1, plate);
	    	result_booking = preparedStmt.executeQuery(query);
		    
		
			while (result_booking.next()) {
				String hour_data = "";
				hour_data = result_booking.getString("hour");
				hours_truck.add(hour_data);
			}
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return hours_truck;
	}
	
	// Read orders from table bookings
	public List<String> orders_booked() {
		// TODO Auto-generated method stub
		Statement state_booking = null;
		ResultSet result_booking = null;
		
		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			state_booking = (Statement) conn.createStatement();
			result_booking = state_booking.executeQuery("SELECT order_request FROM DES_TS.TB_bookings");
			
			while (result_booking.next()) {
				String order_data = "";
				int aux = 0;
				aux = result_booking.getInt("order_request");
				order_data = Integer.toString(aux);
				orders_booked.add(order_data);
			}
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return orders_booked;
	}
	
	//leemos pedidos
	@Override
	public List<String> read_order() {
		Statement state_orders = null;
		ResultSet result_orders = null;

		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			state_orders = (Statement) conn.createStatement();
			result_orders = state_orders.executeQuery("SELECT id FROM DES_TS.TB_orders");
			
			while (result_orders.next()) {
				String order_data = "";
				int aux = 0;
				aux = result_orders.getInt("id");
				order_data = Integer.toString(aux);
				orders.add(order_data);
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return orders;
	}
	
	//buscamos reserva de matricula
	public List<booking> search_plate(String plate, String hour, String hour_selected) {
		ResultSet result_orders = null;

		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			
			// seleccionamos todos los elementos de la reserva que coincidan con la matricula y hora de reserva en la tabla bookings
			String query = "SELECT * FROM DES_TS.TB_bookings WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, plate);
			preparedStmt.setString (2, hour_selected);

			result_orders = preparedStmt.executeQuery();
			
			while (result_orders.next()) {
				booking book = new booking();
				book.setOrder_request(result_orders.getInt("order_request"));
				book.setTruckPlate(result_orders.getString("truckPlate"));
				book.setDock(result_orders.getInt("dock"));
				book.setHour(result_orders.getString("hour"));
				book.setLoadDownload(result_orders.getInt("loadDownload"));
				book.setTruckType(result_orders.getInt("truckType"));
				book.setBookingDate(result_orders.getDate("bookingDate").toLocalDate());
				book_plate.add(book);
				
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		
		try {
			
			// actualizamos el valor de la hora de llegada de la reserva que coincida con la matricula y la hora de reserva
			String query2 = " UPDATE DES_TS.TB_bookings set arrivalDate = ? WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query2);
			preparedStmt.setString (1, hour);
			preparedStmt.setString (2, plate);
			preparedStmt.setString (3, hour_selected);

			preparedStmt.execute();
			
		} catch(Exception e) {
			System.out.println(e); 
		}
		
		return book_plate;
	}

	//borramos la reserva cuando llega tarde a la entrada
	public void delete_booking (String plate, String hour) {
		
		ResultSet result_orders3 = null;
		
		booking book = new booking();
		
		String hora = "";
		int old = 0;
		int dock = 0;
		
		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			
			// actualizamos el valor del estado de la reserva que coincida con la matricula y la hora de reserva
			String query3 = " UPDATE DES_TS.TB_bookings set state = ? WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query3);
			preparedStmt.setInt(1, 2);
			preparedStmt.setString(2, plate);
			preparedStmt.setString (3, hour);

			preparedStmt.execute();
			
		} catch(Exception e) {
			System.out.println(e); 
		}
		
		try {
			
			// seleccionamos todos los elementos de la reserva que coincidan con la matricula y hora de reserva en la tabla bookings
			String query6 = "SELECT * FROM DES_TS.TB_bookings WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query6);
			preparedStmt.setString(1, plate);
			preparedStmt.setString (2, hour);


			result_orders3 = preparedStmt.executeQuery();
			
			while (result_orders3.next()) {
				book.setTruckType(result_orders3.getInt("truckType"));
				book.setOrder_request(result_orders3.getInt("order_request"));
				book.setTruckPlate(result_orders3.getString("truckPlate"));
				book.setDock(result_orders3.getInt("dock"));
				book.setHour(result_orders3.getString("hour"));
				book.setLoadDownload(result_orders3.getInt("loadDownload"));
				book.setBookingDate(result_orders3.getDate("bookingDate").toLocalDate());
				book.setArrivalDate(result_orders3.getString("arrivalDate"));
				book.setDepartureDate(result_orders3.getString("departureDate"));
				book.setState(result_orders3.getInt("state"));
				dock = result_orders3.getInt("dock");
				hora = result_orders3.getString("hour");
				old = result_orders3.getInt("loadDownload");
			}
			
		} catch(Exception e) {
			System.out.println(e); 
		}
		
		try {
	    	
			// insertamos en la tabla timeOut (que contiene las reservas canceladas por retrasos) la reserva seleccionada anteriormente
	    	String query5 = " INSERT INTO DES_TS.TB_timeOut (truckPlate, order_request, loadDownload, bookingDate, arrivalDate, departureDate, state) VALUES (?, ?, ?, ?, ?, ?, ?)";
		   	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query5);
			preparedStmt.setString(1, book.getTruckPlate());
			preparedStmt.setInt(2, book.getOrder_request());
			preparedStmt.setInt(3, book.getLoadDownload());
			preparedStmt.setString(4, book.getBookingDate().toString());
			preparedStmt.setString(5, "");
			preparedStmt.setString(6, "");
			preparedStmt.setInt(7, book.getState());
	
		    // execute the preparedstatement
		    preparedStmt.execute();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		////////ACTUALIZACIÓN TABLA MUELLES
		String range = "";
		if (hora.equals("06:00")) {
		    range = "range_6";
		} else if(hora.equals("07:00")) {
		    range = "range_7";
		} else if(hora.equals("08:00")) {
		    range = "range_8";
		}else if(hora.equals("09:00")) {
		    range = "range_9";
		}else if(hora.equals("10:00")) {
		    range = "range_10";
		} else if(hora.equals("11:00")) {
		    range = "range_11";
		} else if(hora.equals("12:00")) {
		    range = "range_12";
		} else if(hora.equals("13:00")) {
		    range = "range_13";
		}
		
		// actualizamos el estado del muelle a la hora de la reserva seleccionada
		String query_update = "UPDATE TB_docks SET " + range + " = " + old + " where id = " + dock + ";";
		    
		try {
		    PreparedStatement preparedStmt_update = conn.prepareStatement(query_update);
		    preparedStmt_update.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		    
		}
		
	    try {
	    	
	    	// eliminamos de la tabla de bookings (que contiene las reservas) la reserva que coincida con la matricula y la hora de reserva
	    	String query4 = " DELETE FROM DES_TS.TB_bookings WHERE truckPlate = ? and hour = ?";
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query4);
			preparedStmt.setString(1, plate);
			preparedStmt.setString (2, hour);

	
		    // execute the preparedstatement
		    preparedStmt.execute();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
		
		
	//buscamos reserva de la matricula que ya se ha realizado
	public void book_done (String plate, int state, String hour, String hour_selected) {
		ResultSet result_orders3 = null;
		
		booking book = new booking();

		String hora = "";
		int old = 0;
		int dock = 0;
		int type = 0;
		String hour_s = "";
		Connection conn = BBDD_Conection.getConexionInstance();

		try {
			
			// actualizamos el valor del estado y la hora de salida de la reserva que coincida con la matricula y la hora de reserva
			String query3 = " UPDATE DES_TS.TB_bookings set state = ?, departureDate = ? WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query3);
			preparedStmt.setInt(1, state);
			preparedStmt.setString(2, hour);
			preparedStmt.setString(3, plate);
			preparedStmt.setString(4, hour_selected);

			preparedStmt.execute();
			
		} catch(Exception e) {
			System.out.println(e); 
		}
		
		try {
			
			// seleccionamos todos los elementos de la reserva que coincidan con la matricula y hora de reserva en la tabla bookings
			String query6 = "SELECT * FROM DES_TS.TB_bookings WHERE truckPlate = ? and hour = ?";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query6);
			preparedStmt.setString(1, plate);
			preparedStmt.setString(2, hour_selected);

			result_orders3 = preparedStmt.executeQuery();
			
			while (result_orders3.next()) {
				book.setTruckType(result_orders3.getInt("truckType"));
				book.setOrder_request(result_orders3.getInt("order_request"));
				book.setTruckPlate(result_orders3.getString("truckPlate"));
				book.setDock(result_orders3.getInt("dock"));
				book.setHour(result_orders3.getString("hour"));
				book.setLoadDownload(result_orders3.getInt("loadDownload"));
				book.setBookingDate(result_orders3.getDate("bookingDate").toLocalDate());
				book.setArrivalDate(result_orders3.getString("arrivalDate"));
				book.setDepartureDate(result_orders3.getString("departureDate"));
				book.setState(result_orders3.getInt("state"));
				type = result_orders3.getInt("truckType");
				dock = result_orders3.getInt("dock");
				hora = result_orders3.getString("hour");
				old = result_orders3.getInt("loadDownload");	
				hour_s = result_orders3.getDate("bookingDate").toString();
			}
			
		} catch(Exception e) {
			System.out.println(e); 
		}
		
		try {
	    	
			// insertamos en la tabla bookings_done (que contiene los pedidos completados) la reserva seleccionada anteriormente
	    	String query5 = " INSERT INTO DES_TS.TB_bookings_done (truckPlate, truckType, order_request, loadDownload, bookingDate, dock, hour_reserved, houre_entered, hour_exit, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		   	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query5);
			preparedStmt.setString(1, plate);
			preparedStmt.setInt(2, type);
			preparedStmt.setInt(3, book.getOrder_request());
			preparedStmt.setInt(4, book.getLoadDownload());
			preparedStmt.setString(5, hour_s);
			preparedStmt.setInt(6, book.getDock());
			preparedStmt.setString(7, book.getHour());
			preparedStmt.setString(8, book.getArrivalDate());
			preparedStmt.setString(9, book.getDepartureDate());
			preparedStmt.setInt(10, book.getState());
	
		    // execute the preparedstatement
		    preparedStmt.execute();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		
		////////ACTUALIZACIÓN TABLA MUELLES
		String range = "";
		if (hora.equals("06:00")) {
		    range = "range_6";
		} else if(hora.equals("07:00")) {
		    range = "range_7";
		} else if(hora.equals("08:00")) {
		    range = "range_8";
		}else if(hora.equals("09:00")) {
		    range = "range_9";
		}else if(hora.equals("10:00")) {
		    range = "range_10";
		} else if(hora.equals("11:00")) {
		    range = "range_11";
		} else if(hora.equals("12:00")) {
		    range = "range_12";
		} else if(hora.equals("13:00")) {
		    range = "range_13";
		}
		
		// actualizamos el estado del muelle a la hora de la reserva seleccionada
		String query_update = "UPDATE TB_docks SET " + range + " = " + old + " where id = " + dock + ";";
		
		try {
		    PreparedStatement preparedStmt_update = conn.prepareStatement(query_update);

		    preparedStmt_update.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		    
		}
		
		
	    try {
	    	
	    	// eliminamos de la tabla de bookings (que contiene las reservas) la reserva que coincida con la matricula y la hora de reserva
	    	String query4 = " DELETE FROM DES_TS.TB_bookings WHERE truckPlate = ? and hour = ?";
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query4);
			preparedStmt.setString(1, plate);
			preparedStmt.setString(2, hour_selected);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}



	//obtener los datos del muelle
	public List<dock> read_docks(int truck_type) {
		ResultSet result_docks = null;
		Statement state_docks = null;
		
		Connection conn = BBDD_Conection.getConexionInstance();
	
		
		try {
			
			state_docks = (Statement) conn.createStatement();
			
		    String query = "SELECT * FROM DES_TS.TB_docks where truckType = " + truck_type + ";";
		    
		        
		    result_docks = state_docks.executeQuery(query);
			
			while (result_docks.next()) {
				dock dock_data = new dock();
				dock_data.setId(result_docks.getInt("id"));
				dock_data.setTruckType(result_docks.getInt("truckType"));
				dock_data.setRange_6(result_docks.getInt("range_6"));
				dock_data.setRange_7(result_docks.getInt("range_7"));
				dock_data.setRange_8(result_docks.getInt("range_8"));
				dock_data.setRange_9(result_docks.getInt("range_9"));
				dock_data.setRange_10(result_docks.getInt("range_10"));
				dock_data.setRange_11(result_docks.getInt("range_11"));
				dock_data.setRange_12(result_docks.getInt("range_12"));
				dock_data.setRange_13(result_docks.getInt("range_13"));
				docks.add(dock_data);
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return docks;
	}
	
//@Override
//public boolean delete() {
//	// TODO Auto-generated method stub
//	return false;
//}
//
	public int read_docks_final (int type, String hour, int action) {
		String range = "";
		int final_dock = 0;
		int dock = 0;
		List<Integer> docks_final = new ArrayList<>();
		
		ResultSet result_docks = null;
		Statement state_docks = null;
		
		Connection conn = BBDD_Conection.getConexionInstance();
	
		////////ACTUALIZACIÓN TABLA MUELLES
		    
	    if (hour.equals("06:00")) {
	    	range = "range_6";
	    } else if(hour.equals("07:00")) {
	    	 range = "range_7";
	    } else if(hour.equals("08:00")) {
	    	 range = "range_8";
	    }else if(hour.equals("09:00")) {
	    	 range = "range_9";
	    }else if(hour.equals("10:00")) {
	    	 range = "range_10";
	    } else if(hour.equals("11:00")) {
	    	 range = "range_11";
	    } else if(hour.equals("12:00")) {
	    	 range = "range_12";
	    } else if(hour.equals("13:00")) {
	    	 range = "range_13";
	    }
		
		try {
			
			state_docks = (Statement) conn.createStatement();
			
		    String query = "SELECT id FROM DES_TS.TB_docks where truckType = " + type + " and " + range + "= " + action + ";";
		        
		    result_docks = state_docks.executeQuery(query);
			
			while (result_docks.next()) {
				
				dock = result_docks.getInt("id");
				docks_final.add(dock);
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		final_dock = docks_final.get(0);
		return final_dock;
		
	}

	//modificado de pedido
	public static boolean update(String truckPlate, int truckType, int order_request, int loadDownload, LocalDate dia, String hour, int dock){
		// TODO Auto-generated method stub
		Connection conn = BBDD_Conection.getConexionInstance();
		
		//Calendar calendar = Calendar.getInstance();
	    //java.sql.Date testDATE = new java.sql.Date(calendar.getTime().getTime());
	    
	    Date date = java.sql.Date.valueOf(dia);
	    String old_hour = "";
	    int old_dock = 0;
	    int old_loadDownload = 0;
	    
	    // the mysql insert statement
	    String query_hour = "SELECT hour, dock, loadDownload FROM TB_bookings where order_request = " + order_request + ";";
	    
	    try {
		    Statement state_hours = (Statement) conn.createStatement();
		    
		    ResultSet result_hours = state_hours.executeQuery(query_hour);
		    
		    
		    while (result_hours.next()) {
				old_hour = result_hours.getString("hour");
				old_dock = result_hours.getInt("dock");
				old_loadDownload = result_hours.getInt("loadDownload");
			} 
		    
	    } catch (SQLException e){
	    	// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	    }
	    
	    //String query = " UPDATE DES_TS.TB_bookings set truckPlate = ?, truckType = ?, loadDownload = ?, bookingDate = ? , dock = ?, hour = ? where order_request = ?";
	    // create the mysql insert PreparedStatement

	    try {
	    	String query = " UPDATE DES_TS.TB_bookings set truckPlate = ?, truckType = ?, loadDownload = ?, bookingDate = ? , dock = ?, hour = ? WHERE order_request = ?";

	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, truckPlate);
			preparedStmt.setInt (2, truckType);
		    preparedStmt.setInt   (3, loadDownload);
		    preparedStmt.setDate (4, date);
		    preparedStmt.setInt(5, dock);
		    preparedStmt.setString(6,  hour);
		    //preparedStmt.setInt    (5, arrivalDate);
		    //preparedStmt.setInt    (5, departureDate);
		    preparedStmt.setInt    (7, order_request);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
	    }catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
		    System.out.println(e);
		   }
		    //////// ACTUALIZACIÓN TABLA MUELLES
		    String range = "";
		    if (hour.equals("06:00")) {
		    	range = "range_6";
		    } else if(hour.equals("07:00")) {
		    	 range = "range_7";
		    } else if(hour.equals("08:00")) {
		    	 range = "range_8";
		    }else if(hour.equals("09:00")) {
		    	 range = "range_9";
		    }else if(hour.equals("10:00")) {
		    	 range = "range_10";
		    } else if(hour.equals("11:00")) {
		    	 range = "range_11";
		    } else if(hour.equals("12:00")) {
		    	 range = "range_12";
		    } else if(hour.equals("13:00")) {
		    	 range = "range_13";
		    }
		    
		    
		    String query_update = "UPDATE TB_docks SET " + range + " = 3 where id = " + dock + ";";
		    
		    try {
		    	PreparedStatement preparedStmt_update = conn.prepareStatement(query_update);
		    	preparedStmt_update.execute();
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		    
		   }
		    String old_range = "";
		    if (old_hour.equals("06:00")) {
		    	old_range = "range_6";
		    } else if(old_hour.equals("07:00")) {
		    	old_range = "range_7";
		    } else if(old_hour.equals("08:00")) {
		    	old_range = "range_8";
		    }else if(old_hour.equals("09:00")) {
		    	old_range = "range_9";
		    }else if(old_hour.equals("10:00")) {
		    	old_range = "range_10";
		    } else if(old_hour.equals("11:00")) {
		    	old_range = "range_11";
		    } else if(old_hour.equals("12:00")) {
		    	old_range = "range_12";
		    } else if(old_hour.equals("13:00")) {
		    	old_range = "range_13";
		    }
		    
		    String query_update_range = "UPDATE TB_docks SET " + old_range + " = " + old_loadDownload + " where id = " + old_dock + ";";
		    
		    try {
		    	PreparedStatement preparedStmt_update_range = conn.prepareStatement(query_update_range);
		    	preparedStmt_update_range.execute();
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		    
		   }
		    return true;

		/*} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}*/
	}
	
	//borrado de pedido
	public static boolean deleteOrder(int order_request) {
		// TODO Auto-generated method stub
		Connection conn = BBDD_Conection.getConexionInstance();

	    String deleteQuery = " DELETE FROM TB_bookings WHERE order_request = ?";
	    // create the mysql insert PreparedStatement
    
	    try {
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(deleteQuery);
			preparedStmt.setInt (1, order_request);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
		    return true;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void updateDockDelete(String hour, String order_string) {
		
		//obtenemos el valor anterior del muelle para modificarlo CARGA/DESCARGA
		Connection conn2 = BBDD_Conection.getConexionInstance();

	    int old_loadDownload = 0;
	    // the mysql insert statement
	    String query_loadDownload = "SELECT loadDownload FROM TB_bookings where order_request = " + order_string + ";";
	    try {
		    Statement state_loadDownload = (Statement) conn2.createStatement();
		    ResultSet result_loadDownload = state_loadDownload.executeQuery(query_loadDownload);
		    while (result_loadDownload.next()) {
		    	old_loadDownload = result_loadDownload.getInt("loadDownload");
			} 
		    
	    } catch (SQLException e){
	    	// TODO Auto-generated catch block
			e.printStackTrace();
	    }
		
		//obtenemos el muelle a traves del pedido
		int dock=0;
		//obtenemos el muelle de BBDD
		Statement state_orders = null;
		ResultSet result_orders = null;
		Connection conn = BBDD_Conection.getConexionInstance();
		try {
			state_orders = (Statement) conn.createStatement();
			result_orders = state_orders.executeQuery("SELECT dock FROM TB_bookings WHERE order_request="+order_string+";");
			while (result_orders.next()) {
				dock = result_orders.getInt("dock");
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		
		////////ACTUALIZACIÓN TABLA MUELLES
		String range = "";
		if (hour.equals("06:00")) {
		    range = "range_6";
		} else if(hour.equals("07:00")) {
		    range = "range_7";
		} else if(hour.equals("08:00")) {
		    range = "range_8";
		}else if(hour.equals("09:00")) {
		    range = "range_9";
		}else if(hour.equals("10:00")) {
		    range = "range_10";
		} else if(hour.equals("11:00")) {
		    range = "range_11";
		} else if(hour.equals("12:00")) {
		    range = "range_12";
		} else if(hour.equals("13:00")) {
		    range = "range_13";
		}
		    
		String query_update = "UPDATE TB_docks SET " + range + " = " + old_loadDownload + " where id = " + dock + ";";
		    
		try {
		    PreparedStatement preparedStmt_update = conn.prepareStatement(query_update);
		    preparedStmt_update.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		    
		}  
	}
	
	//funcion para obtener los dias festivos nacionales cargados en BBDD
	public static ArrayList<String> holidaysDays(){
		ArrayList<String> holidays = new ArrayList<String>();
		Statement state_holidays = null;
		ResultSet result_holidays = null;
		Connection conn = BBDD_Conection.getConexionInstance();
		String queryHolidays = "SELECT * FROM DES_TS.TB_holidays;";
		try {
			state_holidays = (Statement) conn.createStatement();
			result_holidays = state_holidays.executeQuery(queryHolidays);
			while (result_holidays.next()) {
				String day = result_holidays.getString("date");
				holidays.add(day);
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return holidays;
	}
}
