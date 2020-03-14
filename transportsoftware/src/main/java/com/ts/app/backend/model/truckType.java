package com.ts.app.backend.model;

public class truckType {
	
	private int id;
	private String truckType;
	private int load;
	private int download;
	
	public truckType(int id, String truckType, int load, int download) {
		super();
		this.id = id;
		this.truckType = truckType;
		this.load = load;
		this.download = download;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public int getDownload() {
		return download;
	}

	public void setDownload(int download) {
		this.download = download;
	}
}
