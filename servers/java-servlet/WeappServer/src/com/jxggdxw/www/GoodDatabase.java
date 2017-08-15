package com.jxggdxw.www;

import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodDatabase extends DatabaseUtils{
	
	static String strClassName = GoodDatabase.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    private static final String GOODS_TABLE_NAME = "Goods";

	public GoodDatabase(String tableName) {
		super(tableName);
	}
	public GoodDatabase(){
		super();
	}
	
	
	
	public boolean createTable(){
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		String sql = "create table Goods"
    				+ "(id int, name varchar(80),price varchar(12),abstract varchar(200),"
    				+ "models varchar(100),bonus varchar(200),param varchar(200),serial varchar(100),"
    				+ "recommand varchar(100), urls varchar(20000))";
    		stmt.executeUpdate(sql);
    		
    		stmt.close();
    		conn.close();
    		
    	}catch(SQLException e){
    		logger.error("createTable error " + e.toString());
        	return false;
        }
		
		logger.trace("createTable success");

		return true;
	}
	
	public boolean deleteTable(){
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		stmt.executeUpdate("DROP table Goods");
    		
    		stmt.close();
    		conn.close();
    		
    	}catch(SQLException e){
    		logger.error("deleteTable error " + e.toString());
        	return false;
        }
		
		logger.trace("deleteTable success");

		return true;
	}
	
	public boolean insertTable(Good good){
		
		String sql = new String();
		sql = "insert into " + GOODS_TABLE_NAME + " values(";
		sql += "1," + " \'" + good.getGoodName() + "\'," + "\'" + good.getGoodPrice() + "\'," + 
		"\'" + good.getGoodsAbstract() + "\'," + "\'" + good.getGoodPicUrlsJson() + "\')";
		
		logger.trace("insertSql " + sql.toString());
		
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    		
    		stmt.close();
    		conn.close();
    	}catch(SQLException e){
    		logger.error("insertTable error " + e.toString());
        	return false;
        }
    	logger.trace("insertTable success ");
		return true;
	}
	
	public boolean deleteGood(String name){
		if(null == name){
			logger.error("good name is null");
			return true;
		}
		
		if(!queryGood(name)){  //如果商品不存在则插入
			logger.warn("good is not exist");
			return true;
		}
		
		String sql;
		//数据库更新数据
		sql = "delete from " + GOODS_TABLE_NAME ;
		sql += " where name=\'" + name + "\'";
		
		logger.trace("sql " + sql.toString());
		
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    		
    		stmt.close();
    		conn.close();
    	}catch(SQLException e){
    		logger.error("deleteGood error " + e.toString());
        	return false;
        }
		return true;
		
	}
	
	public JSONObject deleteGoodJson(String name){
		JSONObject json = new JSONObject();
		try{
			if(deleteGood(name)){
				json.put("ret", "success");
			}else{
				json.put("ret", "fail");
			}
		}catch(JSONException e){
			logger.error("json error " + e.toString());
		}
		return json;
	}
	
	public boolean updateGood(Good good){
		
		if(good.getGoodName().equals(" ")){
			logger.warn("good is empty");
			return false;
		}
		
		if(!queryGood(good.getGoodName())){  //如果商品不存在则插入
			return insertTable(good);
		}
		
		String sql;
		//数据库更新数据
		sql = "update " + GOODS_TABLE_NAME ;
		if(good.getGoodName().isEmpty()){
			return false;
		}
		if(!good.getGoodPrice().isEmpty()){
			sql += " set price = \'" + good.getGoodPrice() + "\' ";
		}
		if(!good.getGoodsAbstract().isEmpty()){
			sql += ", abstract = \'" + good.getGoodsAbstract() + "\' ";
		}
		if(null != good.getGoodPicUrlsJsonObj()){
			//sql += ", urls= \'" + good.getGoodPicUrlsJsonObj().toString() + "\' ";
		}
		sql += " where name=\'" + good.getGoodName() + "\'";
		logger.trace("sql " + sql.toString());
		
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    		
    		stmt.close();
    		conn.close();
    	}catch(SQLException e){
    		logger.error("updateTable error " + e.toString());
        	return false;
        }
		return true;
		
	}
	
	public boolean updateUrl(Good good){
		String sql;
		JSONObject jurl ;
		
		if(null == good){
			logger.error("update url,good is null");
			return false;
		}
		
		//从数据库中查询商品
		jurl = queryUrls(good.getGoodName());
		if(jurl == null){       //如果数据库中没有商品url信息，则创建
			logger.trace("find no url");
			jurl = new JSONObject();
			JSONArray jarray = new JSONArray();
			JSONObject jtmp ;
			jtmp = good.getGoodPicUrlsJson();
			jarray.put(jtmp);
			try{
				jurl.put("urls", jarray);
			}catch(JSONException e){
				logger.error("updateTable error " + e.toString());
			}
		}else{              //如果有商品url信息，则添加
			logger.trace("find url");
			JSONArray jarray;
			try{
			//取到其中的json数组
			jarray = (JSONArray)jurl.get("urls");
			logger.trace("jurl:" + jurl.toString());
			logger.trace("jarray:" + jarray.toString());
			
			ArrayList list = (ArrayList)good.getGoodPicUrls();
			Iterator iter = list.iterator();
			while(iter.hasNext()){
				JSONObject jtmp = new JSONObject();
				jtmp.put("url", iter.next().toString());
				jarray.put(jtmp);
			}
			jurl.put("urls", jarray);
			}catch(JSONException e){
				logger.error("updateTable error " + e.toString());
			}
		}
		
		//数据库更新数据
		sql = "update " + GOODS_TABLE_NAME + " set urls=\'" +jurl.toString()+ "\'" + " where name=\'" + good.getGoodName() + "\'";
		logger.trace("sql " + sql.toString());
		
    	Statement stmt;
    	Connection conn;
    	try{
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    		
    		stmt.close();
    		conn.close();
    	}catch(SQLException e){
    		logger.error("updateTable error " + e.toString());
        	return false;
        }
		return true;
	}
	
	public JSONObject queryUrls(String goodName){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONObject jurl = null;
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		String sql = "select * from Goods where name = \'" + goodName + "\'"; 
	        ResultSet result = stmt.executeQuery(sql);
	        while (result.next())
	        {
	        	logger.trace(result.getInt("id") + " " + result.getString("name") + " " + result.getString("urls"));
	        	String url = result.getString("urls");

	        	try {
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
    	}catch(SQLException e){
    		logger.error("queryUrls error " + e.toString());
    		return null;
        }
    	return jurl;
	}
	
	public JSONObject queryUrls(){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONObject jurl = null;
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("select * from Goods");
	        while (result.next())
	        {
	        	logger.trace(result.getInt("name") + " " + result.getString("price") + " " 
	        + result.getString("abstract") + " " + result.getString("urls"));
	        	String url = result.getString("urls");

	        	try {
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
    	}catch(SQLException e){
    		logger.error("queryTable error " + e.toString());
    		return null;
        }
    	
    	return jurl;
	}
	
	public boolean queryGood(String name){
		if(null == queryUrls(name)){
			return false;
		}
		return true;
	}
	
	public JSONObject getGoodsName(){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONArray jGoodNameArrays = new JSONArray() ;
    	JSONObject jNamesInfo = new JSONObject();
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("select * from Goods");
	        while (result.next())
	        {
	        	//logger.trace(result.getString("name") + " " + result.getString("price") + " " 
	       // + result.getString("abstract") + " "+ result.getString("urls"));
	        	String url = result.getString("urls");
	        	try {
	        	
	        		JSONObject tmp = new JSONObject();
		        	tmp.put("name", result.getString("name"));
		        	jGoodNameArrays.put(tmp);
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
	        
	        try{
	        	jNamesInfo.put("names", jGoodNameArrays);
	        }catch(JSONException e){
	    		logger.error("jGoodsInfo error " + e.toString());
	    		return null;
	        }
	        
    	}catch(SQLException e){
    		logger.error("queryTable error " + e.toString());
    		return null;
        }
    	return jNamesInfo;
	}
	
	public JSONObject getGoodInfoEncode(String name){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null , jgood = new JSONArray();
    	JSONObject jurl = null , jGoodInfo = new JSONObject();
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		String sql = "select * from Goods where name = \'" + name + "\'"; 
	        ResultSet result = stmt.executeQuery(sql);
	        while (result.next())
	        {
	        	logger.trace(result.getInt("id") + " " + result.getString("name") + " " + result.getString("urls"));
	        	String url = result.getString("urls");
	        
	        	try {
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
					
		        	JSONObject jtmp = new  JSONObject();
		        	String name1 = URLEncoder.encode(result.getString("name"), "utf-8");
		        	jtmp.put("name", name1);
		        	
		        	jgood.put(jtmp);
		        	
		        	jtmp = new  JSONObject();
		        	jtmp.put("price", result.getString("price"));
		        	jgood.put(jtmp);
		        	
		        	jtmp = new  JSONObject();
		        	String abs = URLEncoder.encode(result.getString("abstract"), "utf-8");
		        	jtmp.put("abstract", abs);
		        	jgood.put(jtmp);
		        	
		        	jgood.put(jurl);
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
	        jGoodInfo.put("good", jgood);
    	}catch(Exception e){
    		logger.error("queryUrls error " + e.toString());
    		return null;
        }
    	
    	
    	return jGoodInfo;
	}
	
	public JSONObject getGoodInfo(String name){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null , jgood = new JSONArray();
    	JSONObject jurl = null , jGoodInfo = new JSONObject();
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
    		String sql = "select * from Goods where name = \'" + name + "\'"; 
	        ResultSet result = stmt.executeQuery(sql);
	        while (result.next())
	        {
	        	logger.trace(result.getInt("id") + " " + result.getString("name") + " " + result.getString("urls"));
	        	String url = result.getString("urls");
	        
	        	try {
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
					
		        	JSONObject jtmp = new  JSONObject();
		        	jtmp.put("name", result.getString("name"));
		        	jgood.put(jtmp);
		        	
		        	jtmp = new  JSONObject();
		        	jtmp.put("price", result.getString("price"));
		        	jgood.put(jtmp);
		        	
		        	jtmp = new  JSONObject();
		        	jtmp.put("abstract", result.getString("abstract"));
		        	jgood.put(jtmp);
		        	
		        	jgood.put(jurl);
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
	        jGoodInfo.put("good", jgood);
    	}catch(Exception e){
    		logger.error("queryUrls error " + e.toString());
    		return null;
        }
    	
    	
    	return jGoodInfo;
	}
	
	public JSONObject getGoodsInfoEncode() throws JSONException{
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONArray jGoodInfoArray,jGoodInfoArrays = new JSONArray() ;
    	JSONObject jurl,jGoodInfo,jGoodsInfo = new JSONObject();
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("select * from Goods");
	        while (result.next())
	        {
	        	logger.trace(result.getString("name") + " " + result.getString("price") + " " 
	        + result.getString("abstract") + " "+ result.getString("urls"));
	        	String url = result.getString("urls");
	        	jGoodInfo = new JSONObject();
	        	try {
	        		jGoodInfoArray = new JSONArray();
	        		JSONObject tmp = new JSONObject();
	        		String name = URLEncoder.encode(result.getString("name"), "utf-8");
		        	tmp.put("name", name);
		        	jGoodInfoArray.put(tmp);
		        	
		        	tmp = new JSONObject();
		        	tmp.put("price", result.getString("price"));
		        	jGoodInfoArray.put(tmp);
		        	
		        	tmp = new JSONObject();
		        	String abs = URLEncoder.encode(result.getString("abstract"), "utf-8");
		        	tmp.put("abstract", abs);
		        	jGoodInfoArray.put(tmp);
		        	
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					//logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
	        		
	        		jGoodInfoArray.put(jurl);
	        		//jGoodsInfo.put("good",jGoodInfoArray );
	        		jGoodInfoArrays.put(jGoodInfoArray);
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
	        
	        try{
	        	jGoodsInfo.put("goods", jGoodInfoArrays);
	        }catch(JSONException e){
	    		logger.error("jGoodsInfo error " + e.toString());
	    		return null;
	        }
	        
    	}catch(SQLException e){
    		logger.error("queryTable error " + e.toString());
    		return null;
        }
    	
    	return jGoodsInfo;
	}
	
	public JSONObject getGoodsInfo(){
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONArray jGoodInfoArray,jGoodInfoArrays = new JSONArray() ;
    	JSONObject jurl,jGoodInfo,jGoodsInfo = new JSONObject();
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("select * from Goods");
	        while (result.next())
	        {
	        	logger.trace(result.getString("name") + " " + result.getString("price") + " " 
	        + result.getString("abstract") + " "+ result.getString("urls"));
	        	String url = result.getString("urls");
	        	jGoodInfo = new JSONObject();
	        	try {
	        		jGoodInfoArray = new JSONArray();
	        		JSONObject tmp = new JSONObject();
		        	tmp.put("name", result.getString("name"));
		        	jGoodInfoArray.put(tmp);
		        	
		        	tmp = new JSONObject();
		        	tmp.put("price", result.getString("price"));
		        	jGoodInfoArray.put(tmp);
		        	
		        	tmp = new JSONObject();
		        	tmp.put("abstract", result.getString("abstract"));
		        	jGoodInfoArray.put(tmp);
		        	
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					//logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
	        		
	        		jGoodInfoArray.put(jurl);
	        		//jGoodsInfo.put("good",jGoodInfoArray );
	        		jGoodInfoArrays.put(jGoodInfoArray);
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
	        
	        try{
	        	jGoodsInfo.put("goods", jGoodInfoArrays);
	        }catch(JSONException e){
	    		logger.error("jGoodsInfo error " + e.toString());
	    		return null;
	        }
	        
    	}catch(SQLException e){
    		logger.error("queryTable error " + e.toString());
    		return null;
        }
    	
    	return jGoodsInfo;
	}
	
	public boolean queryTable(){
        //查询数据
		
		Statement stmt;
    	Connection conn;
    	JSONArray json = null;
    	JSONObject jurl;
    	try{
    		
    		conn = DriverManager.getConnection(DatabaseUtils.JDBC_URL,DatabaseUtils.DB_USER_NAME,DatabaseUtils.DB_USER_PWD);
    		stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery("select * from Goods");
	        while (result.next())
	        {
	        	logger.trace(result.getString("name") + " " + result.getString("price") + " " 
	        + result.getString("abstract") + " "+ result.getString("urls"));
	        	String url = result.getString("urls");

	        	try {
	        		//得到json对象
	        		jurl = new JSONObject(url);
	        		//取到其中的json数组
	        		json = (JSONArray)jurl.get("urls");
					//logger.trace("jurl:" + jurl.toString());
					//logger.trace("json:" + json.toString());
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("JSONArray error " + e.toString());
					e.printStackTrace();
				}
	        	
	        }
    	}catch(SQLException e){
    		logger.error("queryTable error " + e.toString());
    		return false;
        }
    	
    	return true;
	}

}
