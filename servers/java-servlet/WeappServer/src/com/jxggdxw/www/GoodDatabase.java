package com.jxggdxw.www;

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
		// TODO Auto-generated constructor stub
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
    		stmt.executeUpdate("create table Goods(id int, name varchar(80),price varchar(12),abstract varchar(200),urls varchar(20000))");
    		
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
	
	public boolean updateGood(Good good){
		
		if(good.getGoodName().equals(" ")){
			logger.warn("good is empty");
			return false;
		}
		
		if(!queryGood(good.getGoodName())){  //�����Ʒ�����������
			return insertTable(good);
		}
		
		String sql;
		//���ݿ��������
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
		
		//�����ݿ��в�ѯ��Ʒ
		jurl = queryUrls(good.getGoodName());
		if(jurl == null){       //������ݿ���û����Ʒurl��Ϣ���򴴽�
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
		}else{              //�������Ʒurl��Ϣ�������
			logger.trace("find url");
			JSONArray jarray;
			try{
			//ȡ�����е�json����
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
		
		//���ݿ��������
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
	        		//�õ�json����
	        		jurl = new JSONObject(url);
	        		//ȡ�����е�json����
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
	        		//�õ�json����
	        		jurl = new JSONObject(url);
	        		//ȡ�����е�json����
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
	
	public boolean queryTable(){
        //��ѯ����
		
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
	        		//�õ�json����
	        		jurl = new JSONObject(url);
	        		//ȡ�����е�json����
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
