package com.ts.app.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ts.app.backend.BBDD_Conection;

public class DockService {
	
	
	public static boolean createDocks(int id, int range_6, int range_7, int range_8, int range_9, int range_10, int range_11, int range_12, int range_13)  {

			// Conection to database
			Connection conn = BBDD_Conection.getConexionInstance();

			// Transform the format from localdate to date
		    //Date date = java.sql.Date.valueOf(day_reserved);

		    // Write the query to execute
		    String query = " INSERT INTO TB_docks (id, truckType, range_6, range_7, range_8, range_9, range_10, range_11, range_12, range_13) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		    
		    // Create the mysql insert PreparedStatements
		    try {
		
		    	PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt (1, id);
				preparedStmt.setInt (2, range_6);
				preparedStmt.setInt (3, range_7);
			    preparedStmt.setInt   (4, range_8);
			    preparedStmt.setInt (5, range_9);
			    preparedStmt.setInt   (6, range_10);
			    preparedStmt.setInt(7, range_11);
			    preparedStmt.setInt    (8, range_12);
			    preparedStmt.setInt    (8, range_13);

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
