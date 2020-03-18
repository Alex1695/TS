package com.ts.app.backend.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import com.ts.app.backend.BBDD_Conection;

public class BookingService implements CRUD{

	


public static boolean create(String truckPlate, int order_request, int loadDownload, LocalDate dia, /*LocalDate arrivalDate,*/
		/*LocalDate departureDate,*/ int state)  {
	
	Connection conn = BBDD_Conection.getConexionInstance();
	
	Calendar calendar = Calendar.getInstance();
    java.sql.Date testDATE = new java.sql.Date(calendar.getTime().getTime());
    
 // the mysql insert statement
    String query = " INSERT INTO TB_bookings (truckPlate, order_request, loadDownload, bookingDate, state) VALUES (?, ?, ?, ?, ?)";

    // create the mysql insert PreparedStatement
    try {
    	PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString (1, truckPlate);
		preparedStmt.setInt (2, order_request);
	    preparedStmt.setInt   (3, loadDownload);
	    preparedStmt.setDate (4, testDATE);
	    //preparedStmt.setInt    (5, arrivalDate);
	    //preparedStmt.setInt    (5, departureDate);
	    preparedStmt.setInt    (5, state);
	    
	    // execute the preparedstatement
	    preparedStmt.execute();
	    return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
   
    
}

//@Override
//public void read() {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public boolean delete() {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//@Override
//public void update() {
//	// TODO Auto-generated method stub
//	
//}
	

	
}
