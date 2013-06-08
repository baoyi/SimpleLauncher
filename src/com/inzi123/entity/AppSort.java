package com.inzi123.entity;

public class AppSort {

	private int _id;
	private String name;
	private long mInst;
	private int frequency;
	private String packagename;
	
	public AppSort() {
		super();
	}
	public AppSort(int _id, String name, long mInst, int frequency,
			String packagename) {
		super();
		this._id = _id;
		this.name = name;
		this.mInst = mInst;
		this.frequency = frequency;
		this.packagename = packagename;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getmInst() {
		return mInst;
	}
	public void setmInst(long mInst) {
		this.mInst = mInst;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	

}
