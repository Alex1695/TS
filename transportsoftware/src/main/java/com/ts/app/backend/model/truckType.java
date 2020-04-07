package com.ts.app.backend.model;

public class truckType {
	
	enum type{
		Lona,
		Furgoneta,
		Trailer
	}
	
	public truckType() {
		
	}
	
	public static int Furgoneta = 1;
	public static int Lona = 2;
	public static int Trailer =  3;
	
	
	public static int getId(String str) {
		
	    if(str.equals(type.Lona.toString())) {
	    	return Lona;
	    }else if(str.equals(type.Furgoneta.toString())) {
	    	return Furgoneta;
	    }else if(str.equals(type.Trailer.toString())) {
	    	return Trailer;
	    }
	    return 0;
	}
	
}
