package com.ts.app.backend;

import java.sql.Connection;
import java.sql.DriverManager;



public class BBDD_Conection {
		private static Connection conex = null;
	
		//private String url ="jdbc:mysql://192.168.1.95:32787/DES_TS";
		private String url ="jdbc:mysql://sierralfa.synology.me:32787/DES_TS";
		private String user = "PGPI20";
		private String pass = "paSSw0rd";
		
		/**
		 * Constructor privado para implementar singleton
		 * @return
		 */
	
		private Connection conexion() {	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conex = (Connection) DriverManager.getConnection(url, user, pass);
				System.out.println("Conexión establecida con BBDD");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("ERROR al establecer la conexión BBDD!");
			}
			return conex;
		}
	
		/**
		 * Singleton de la conexion
		 * @return
		 */
		public static Connection getConexionInstance() {
			if(conex !=null) {
				return conex;
			}else {
				conex = new BBDD_Conection().conexion();
				return conex;
			}
		}
}
