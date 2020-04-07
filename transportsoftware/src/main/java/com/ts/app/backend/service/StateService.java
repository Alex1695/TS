package com.ts.app.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.ts.app.backend.model.State;

public class StateService {
	
	static List<State> states = new ArrayList<State>();
	
	public StateService() {
		
		states.add(new State(1, "CARGA"));
		states.add(new State(2, "DESCARGA"));
		states.add(new State(3, "NO DISPONIBLE"));
	}
	
	
	public static int getIdbyState(String str) {
		
		for(State state: states) {
			if(state.getEstado().equals(str)) {
				return state.getId();
			}
		}
		System.out.println("peta bro!");
		return 0;		
	}
	
}
