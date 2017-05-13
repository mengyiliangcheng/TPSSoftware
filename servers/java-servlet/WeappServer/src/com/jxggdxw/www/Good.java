package com.jxggdxw.www;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Good {
	
	private String GoodName = " ";
	private String GoodPrice = " ";
	private List<String> GoodPics = new ArrayList();
	private String GoodsAbstract = " ";
	
	static String strClassName = Good.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
	
    public Good(){
    	
    }
    
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
	
	public JSONObject getGoodPicUrlsJson(){
		JSONArray json = new JSONArray();
		JSONObject jurl = new JSONObject();
		
		try{
			json = getGoodPicUrlsJsonObj();
			jurl.put("urls",json);
		}catch(Exception e){
			logger.error("getGoodPicUrlsJson error " + e.toString());
			return null;
		}
		
		return jurl;
	}
	
	public JSONArray getGoodPicUrlsJsonObj(){
		JSONArray jurls = new JSONArray();
		JSONObject jurl ;
		Iterator iter = GoodPics.iterator();
		while(iter.hasNext()){
			String url = (String)iter.next();
			jurl = new JSONObject();;
			try{
			jurl.put("url", url);
			}catch(JSONException e){
				logger.error("getGoodPicUrlsJsonObj error " + e.toString());
				return null;
			}
			jurls.put(jurl);
		}
		return jurls;
	}
	

	public List<String> getGoodInfo(){
		
		List<String> info = new ArrayList<String>();
		info.add(GoodName);
		info.add(GoodPrice);
		info.add(GoodsAbstract);
		
		Iterator pics = GoodPics.iterator();
		while(pics.hasNext()){
			info.add((String)pics.next());
		}
		
		return info;
	}
	
	public JSONArray getGoodInfoJson(){
		JSONArray json = new JSONArray();
		JSONObject jname , jprice,jabs,jurl;
		
		try{
			jname = new JSONObject();
			jname.put("GoodName", GoodName);
		
			jprice = new JSONObject();
			jprice.put("GoodPrice", GoodPrice);
			
			jabs = new JSONObject();
			jabs.put("GoodAbstract", GoodsAbstract);
			
			jurl = new JSONObject();
			jurl.put("urls", GoodPics);
		}catch(Exception e){
			logger.error("json error " + e.toString());
			return null;
		}
		
		json.put(jname);
		json.put(jprice);
		json.put(jabs);
		json.put(jurl);
		
		return json;
	}
	
	public String toString(){
		
		String url = new String();
		//logger.trace(GoodPics.toString());
		
		Iterator<String> iter = GoodPics.iterator();
		while(iter.hasNext()){
			url += " url->"+ iter.next();
		}
		
		return ("name->"+GoodName + " price->" + GoodPrice + " abstract: " + GoodsAbstract +" urls: " + url + "\r\n");
	}
}
