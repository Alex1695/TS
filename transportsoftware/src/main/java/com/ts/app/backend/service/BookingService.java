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
import com.ts.app.backend.model.order;

public class BookingService implements CRUD{

	private List<String> orders = new ArrayList<>();
	private List<booking> bookings = new ArrayList<>();
	
	public static boolean create(String truckPlate, int truckType, int order_request, int loadDownload, LocalDate dia, /*LocalDate arrivalDate,*/
		/*LocalDate departureDate,*/ int state)  {

		Connection conn = BBDD_Conection.getConexionInstance();

		//Calendar calendar = Calendar.getInstance();
	    //java.sql.Date testDATE = new java.sql.Date(calendar.getTime().getTime());
	    Date date = java.sql.Date.valueOf(dia);

	    // the mysql insert statement

	    String query = " INSERT INTO TB_bookings (truckPlate, truckType, order_request, loadDownload, bookingDate, state) VALUES (?, ?, ?, ?, ?, ?)";
	    // create the mysql insert PreparedStatement
    
	    try {
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, truckPlate);
			preparedStmt.setInt (2, truckType);
			preparedStmt.setInt (3, order_request);
		    preparedStmt.setInt   (4, loadDownload);
		    preparedStmt.setDate (5, date);
		    //preparedStmt.setInt    (5, arrivalDate);
		    //preparedStmt.setInt    (5, departureDate);
		    preparedStmt.setInt    (6, state);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
		    return true;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

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
	
	@Override
	public List<String> read_order() {
		Statement state_orders = null;
		ResultSet result_orders = null;

		Connection conn = BBDD_Conection.getConexionInstance();
		
		try {
			state_orders = (Statement) conn.createStatement();
			result_orders = state_orders.executeQuery("SELECT * FROM DES_TS.TB_orders");
			
			while (result_orders.next()) {
				String order_data = "";
				int aux = 0;
				aux = result_orders.getInt("order_request");
				order_data = Integer.toString(aux);
				orders.add(order_data);
			} 
			
		} catch(Exception e) {
			System.out.println(e); 
		} 
		return orders;
	}

	
//@Override
//public boolean delete() {
//	// TODO Auto-generated method stub
//	return false;
//}
//

	public static boolean update(String truckPlate, int truckType, int order_request, int loadDownload, LocalDate dia) {
		// TODO Auto-generated method stub
		Connection conn = BBDD_Conection.getConexionInstance();
		
		//Calendar calendar = Calendar.getInstance();
	    //java.sql.Date testDATE = new java.sql.Date(calendar.getTime().getTime());
	    
	    Date date = java.sql.Date.valueOf(dia);

	    // the mysql insert statement

	    String query = " UPDATE TB_bookings set truckPlate = ?, truckType = ?, loadDownload = ?, bookingDate = ? where order_request = ?";
	    // create the mysql insert PreparedStatement
    
	    try {
	
	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, truckPlate);
			preparedStmt.setInt (2, truckType);
		    preparedStmt.setInt   (3, loadDownload);
		    preparedStmt.setDate (4, date);
		    //preparedStmt.setInt    (5, arrivalDate);
		    //preparedStmt.setInt    (5, departureDate);
		    preparedStmt.setInt    (5, order_request);
	
		    // execute the preparedstatement
		    preparedStmt.execute();
		    return true;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


}
