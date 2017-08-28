package com.jxggdxw.www;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class GoodModel {
	
	private String price = "";
	private String model = "";
	private String info  = "";
	private String other = "";
	
	static String strClassName = GoodModel.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
	public static void main(String[] args){
		GoodModel model = new GoodModel("standard","10000","hgoogd","");
		//model.toString();
		logger.trace(model.toString());
	}
	
	public GoodModel(String model,String price,String info,String other){
		this.price = price;
		this.model = model;
		this.info = info;
		this.other = other;
	}
	public GoodModel(){
		
	}
	
	@Override
	public String toString() {
		
		return "model:[" + model +  "]" + "   price:[" + price +  "]" + "   info:[" + info +  "]" + 
				"   other:[" + other +  "]";
	}
	
	public JSONObject toJson(){
		
		JSONObject json = new JSONObject();
		try{
		json.put("model", model);
		json.put("price", price);
		json.put("info", info);
		json.put("other", other);
		}catch(Exception e){
			logger.error(e.toString());
		}
		return json;
	}
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
	

}
