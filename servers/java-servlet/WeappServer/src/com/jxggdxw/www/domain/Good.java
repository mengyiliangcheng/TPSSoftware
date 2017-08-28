package com.jxggdxw.www.domain;

import java.util.HashSet;
import java.util.Set;

public class Good {
	
	private int id;
	private String goodName = new String();
	private String goodPrice = new String();
	private String goodAbs   = new String();
	private Set<GoodModel> goodModels = new HashSet<GoodModel>();
	private Set<GoodPic> goodPics = new HashSet<GoodPic>();
	
	public Good(){

	}
	
	public Good(boolean bisNull){
		if(bisNull){
			id = 0;
			goodName = null;
			goodPrice = null;
			goodModels = null;
			goodPics = null;
			goodAbs = null;
		}
	}
	
	public String getGoodAbs() {
		return goodAbs;
	}

	public void setGoodAbs(String goodAbs) {
		this.goodAbs = goodAbs;
	}

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
