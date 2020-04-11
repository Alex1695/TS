package com.ts.app.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ts.app.backend.BBDD_Conection;

public class OrderService {
	
	
		//Create inserts for the table Docks
		public static boolean createOrders(ArrayList<String[]> listDocks)  {
			
				//truncate the tables
				truncateTables("TB_bookings");
				truncateTables("TB_orders");
				

				// Conection to database
				Connection conn = BBDD_Conection.getConexionInstance();
				

			    // Write the query to execute
			    String query = " INSERT INTO TB_orders (order_request, id) VALUES (?, ?)";
			    		    
			    
			    for(String[] order: listDocks) {
			    	
			    	try {
		    			
				    	PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.setInt (1, Integer.parseInt(order[0]));
						preparedStmt.setInt (2, Integer.parseInt(order[1]));

					    // execute the preparedstatement
					    preparedStmt.execute();
					    
				
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
			    }
			    
				return true;
			    		   
		}
		//Method to Truncate dynamic table
		public static int truncateTables(String table){
			try {

				Connection conn = BBDD_Conection.getConexionInstance();
				Statement statement = conn.createStatement();
				int result = statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0; ");
				int result2 = statement.executeUpdate("TRUNCATE " + table);
				int result3 = statement.executeUpdate("SET FOREIGN_KEY_CHECKS=1; ");

				
				conn.commit();
				return result2;

			} catch (Exception e) {
				return 0;
			}

		}
}
