package com.ts.app.backend.model;


public class rol {
	
	private int id;
	private String rol;
	
	public rol(int id, String rol) {
		super();
		this.id = id;
		this.rol = rol;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
