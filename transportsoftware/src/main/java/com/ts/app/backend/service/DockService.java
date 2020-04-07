package com.ts.app.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ts.app.backend.BBDD_Conection;
import com.ts.app.backend.model.truckType;

public class DockService {
	
	//Create inserts for the table Docks
	public static boolean createDocks(ArrayList<String[]> listDocks)  {
		
			//truncate the tables
			truncateTables("TB_bookings");
			truncateTables("TB_docks");
			
			

			// Conection to database
			Connection conn = BBDD_Conection.getConexionInstance();
			

		    // Write the query to execute
		    String query = " INSERT INTO TB_docks (id, truckType, range_6, range_7, range_8, range_9, range_10, range_11, range_12, range_13) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    		    
		    
		    for(String[] dock: listDocks) {
		    	
		    	try {
	    			
			    	PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt (1, Integer.parseInt(dock[0]));
					preparedStmt.setInt (2, truckType.getId(dock[1]));
					preparedStmt.setInt (3, StateService.getIdbyState(dock[2]));
				    preparedStmt.setInt (4, StateService.getIdbyState(dock[3]));
				    preparedStmt.setInt (5, StateService.getIdbyState(dock[4]));
				    preparedStmt.setInt (6, StateService.getIdbyState(dock[5]));
				    preparedStmt.setInt (7, StateService.getIdbyState(dock[6]));
				    preparedStmt.setInt (8, StateService.getIdbyState(dock[7]));
				    preparedStmt.setInt (9, StateService.getIdbyState(dock[8]));
				    preparedStmt.setInt (10, StateService.getIdbyState(dock[9]));

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
