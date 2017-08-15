package com.jxggdxw.www.dao;

import java.util.HashSet;
import java.util.Set;

public class Good {
	
	private int id;
	private String goodName = new String();
	private String goodPrice = new String();
	private Set<GoodModel> goodModels = new HashSet<GoodModel>();
	private Set<GoodPic> goodPics = new HashSet<GoodPic>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	public Set<GoodModel> getGoodModels() {
		return goodModels;
	}
	public void setGoodModels(Set<GoodModel> goodModels) {
		this.goodModels = goodModels;
	}
	public Set<GoodPic> getGoodPics() {
		return goodPics;
	}
	public void setGoodPics(Set<GoodPic> goodPics) {
		this.goodPics = goodPics;
	}
	
	
	
}
