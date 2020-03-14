package com.ts.app.backend.model;


public class dock {
	
	private int id;
	private int truckType;
	private int range_6;
	private int range_7;
	private int range_8;
	private int range_9;
	private int range_10;
	private int range_11;
	private int range_12;
	private int range_13;
	
	public dock(int id, int truckType, int range_6, int range_7, int range_8, int range_9, int range_10, int range_11,
			int range_12, int range_13) {
		super();
		this.id = id;
		this.truckType = truckType;
		this.range_6 = range_6;
		this.range_7 = range_7;
		this.range_8 = range_8;
		this.range_9 = range_9;
		this.range_10 = range_10;
		this.range_11 = range_11;
		this.range_12 = range_12;
		this.range_13 = range_13;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTruckType() {
		return truckType;
	}

	public void setTruckType(int truckType) {
		this.truckType = truckType;
	}

	public int getRange_6() {
		return range_6;
	}

	public void setRange_6(int range_6) {
		this.range_6 = range_6;
	}

	public int getRange_7() {
		return range_7;
	}

	public void setRange_7(int range_7) {
		this.range_7 = range_7;
	}

	public int getRange_8() {
		return range_8;
	}

	public void setRange_8(int range_8) {
		this.range_8 = range_8;
	}

	public int getRange_9() {
		return range_9;
	}

	public void setRange_9(int range_9) {
		this.range_9 = range_9;
	}

	public int getRange_10() {
		return range_10;
	}

	public void setRange_10(int range_10) {
		this.range_10 = range_10;
	}

	public int getRange_11() {
		return range_11;
	}

	public void setRange_11(int range_11) {
		this.range_11 = range_11;
	}

	public int getRange_12() {
		return range_12;
	}

	public void setRange_12(int range_12) {
		this.range_12 = range_12;
	}

	public int getRange_13() {
		return range_13;
	}

	public void setRange_13(int range_13) {
		this.range_13 = range_13;
	}
}