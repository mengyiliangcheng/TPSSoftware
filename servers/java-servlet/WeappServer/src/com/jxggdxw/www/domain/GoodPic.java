package com.jxggdxw.www.domain;

public class GoodPic {
	
	private int id;
	private String path = new String();
	private String info = new String();
	private Good goodinfo = new Good();
	
	public Good getGoodinfo() {
		return goodinfo;
	}
	public void setGoodinfo(Good goodinfo) {
		this.goodinfo = goodinfo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	

}
