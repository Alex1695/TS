package com.ts.app.backend.model;

public class State {
	
	int id;
	String state;
	
	
	public State(int id, String estado) {
		super();
		this.id = id;
		this.state = estado;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEstado() {
		return state;
	}
	public void setEstado(String estado) {
		this.state = estado;
	}
}
