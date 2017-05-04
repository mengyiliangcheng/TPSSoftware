package com.jxggdxw.www;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Good {
	
	private String GoodName = " ";
	private String GoodPrice = " ";
	private List<String> GoodPics;
	private String GoodsAbstract = " ";
	
	static String strClassName = Goods.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
	
	public Good(String GoodName){
		setGoodName(GoodName);
	}

	public void setGoodName(String GoodName){
		this.GoodName = GoodName;
	}
	
	public String getGoodName(){
		return this.GoodName;
	}
	
	public void setGoodPrice(String GoodPrice){
		this.GoodPrice = GoodPrice;
	}
	public String getGoodPrice(){
		return this.GoodPrice;
	}
	
	public void setGoodAbstract(String GoodsAbstract){
		this.GoodsAbstract = GoodsAbstract;
	}
	public String getGoodsAbstract(){
		return this.GoodsAbstract;
	}
	
	public void setGoodPicUrl(String url){
		GoodPics.add(url);
	}
	public List<String> getGoodPicUrls(){
		return GoodPics;
	}

}
