package com.ts.app.backend.booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
@Deprecated
public class InsertBookings {
	
	//private static Connection conex = null;

	//private String url ="jdbc:mysql://192.168.1.95:32787/DES_TS";
	//private String url ="jdbc:mysql://sierralfa.synology.me:32787/DES_TS";
	//private String user = "PGPI20";
	//private String pass = "paSSw0rd";
	
	//Statement state_booking = null;
	//ResultSet result_booking = null;
	//Connection conex = null;
	
	public void InsertBooking(String truckPlate, int order_request, int loadDownload, LocalDate arrivalDate,
			LocalDate departureDate, int state) {
		try {
			Statement state_booking = null;
			ResultSet result_booking = null;
			Connection conex = null;
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conex = DriverManager.getConnection("jdbc:mysql://192.168.1.95:32787/DES_TS", "PGPI20", "paSSw0rd");
			state_booking = conex.createStatement();
			
			System.out.println("Conexión establecida con BBDD");
			
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date bookingDate = new java.sql.Date(calendar.getTime().getTime());
			//java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		      // the mysql insert statement
		      
			String query = " insert into users (truckPlate, order_request, loadDownload, bookingDate, arrivalDate, departureDate, state)"
			        + " values ("+truckPlate+","+order_request+"," +loadDownload+"," +bookingDate+","+arrivalDate+"," +departureDate+","+state+");";
		     
			state_booking.executeUpdate(query);
			
			// create the mysql insert preparedstatement
		      /*PreparedStatement preparedStmt = conex.prepareStatement(query);
		      preparedStmt.setString (1, truckPlate);
		      preparedStmt.setInt 	(2, order_request);
		      preparedStmt.setInt   (3, loadDownload);
		      preparedStmt.setDate	(4, bookingDate);
		      preparedStmt.setInt    (5, 5000);*/
	
		      // execute the preparedstatement
		      //preparedStmt.execute();
		     
			result_booking.close();
			state_booking.close();
			conex.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR al establecer la conexión BBDD!");
		}
	}

}
