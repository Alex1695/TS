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
import com.ts.app.backend.model.order;

public class BookingService implements CRUD{

	private List<String> orders = new ArrayList<>();
	
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

	@Override
	public void read() {
		// TODO Auto-generated method stub
		
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
				System.out.println("Metido: " + order_data);
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

//@Override
//public void update() {
//	// TODO Auto-generated method stub
//	
//}


}
