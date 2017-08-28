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
	private GoodModel[] GoodModels = null ;
	private String GoodBonusInfo = "";
	private String GoodDetailParam = "";
	private String GoodSerial = "";
	private String[] GoodRecmd = null;
	
	
	static String strClassName = Good.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
	
    public Good(){
    	
    }
    
	public Good(String GoodName){
		setGoodName(GoodName);
	}

	public ArrayList<String> getParamList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("GoodName");
		list.add("GoodPrice");
		list.add("GoodsAbstract");
		list.add("GoodModels");
		list.add("GoodBonusInfo");
		list.add("GoodDetailParam");
		list.add("GoodSerial");
		list.add("GoodRecmd");
		list.add("GoodPics");
		
		return list;
	}
	
	public GoodModel[] getGoodModels() {
		return GoodModels;
	}

	public void setGoodModels(GoodModel[] goodModels) {
		GoodModels = goodModels;
	}

	public String getGoodBonusInfo() {
		return GoodBonusInfo;
	}

	public void setGoodBonusInfo(String goodBonusInfo) {
		GoodBonusInfo = goodBonusInfo;
	}

	public String getGoodDetailParam() {
		return GoodDetailParam;
	}

	public void setGoodDetailParam(String goodDetailParam) {
		GoodDetailParam = goodDetailParam;
	}

	public String getGoodSerial() {
		return GoodSerial;
	}

	public void setGoodSerial(String goodSerial) {
		GoodSerial = goodSerial;
	}

	public String[] getGoodRecmd() {
		return GoodRecmd;
	}

	public void setGoodRecmd(String[] goodRecmd) {
		GoodRecmd = goodRecmd;
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
		
		return ("name->"+GoodName + " price->" + GoodPrice + "BonusInfo->" + GoodBonusInfo
				 + "GoodDetailParam->" + GoodDetailParam + "GoodSerial->" + GoodSerial 
				 + "GoodModels->" + GoodModels.toString() +" urls: " + url + "\r\n");
	}
}
