package com.jxggdxw.www.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jxggdxw.www.DatabaseUtils;
import com.jxggdxw.www.domain.Good;
import com.jxggdxw.www.domain.GoodModel;
import com.jxggdxw.www.domain.GoodPic;
import com.jxggdxw.www.util.HibernateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class GoodTable {
	
	String asTableName = "GoodInfo";
	
	static String strClassName = GoodTable.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
	
	public static void main(String[] args){
		
		Good goodInfo = new Good(true);
		goodInfo.setGoodName("x7");
		//getOneGood(goodInfo);
		//queryNum(goodInfo);
		
		//getGoodsNameJson();
		
		getGoodJson("qewrqwe8");
/*		GoodModel model = new GoodModel();
		model.setInfo("modelinfo");
		model.setGood(null);
		
		getGoodJson(goodInfo);*/
	}
	
	public static JSONObject getGoodJson(String name){
		Good good = new Good(true);
		good.setGoodName(name);
		return getGoodJson(good);
	}
	
	public static JSONObject getGoodJson(Good goodInfo) {
		return getGoodJson(goodInfo,new String[]{"good","goodinfo"});
	}

	public static JSONObject getGoodJson(Good goodInfo,String[] filter) {
		Good good1 = getOneGood(goodInfo);
		
		JsonConfig config = new JsonConfig();

		/*设置过滤器*/
		config.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object source, String name, Object value) {
				// TODO Auto-generated method stub
				for(int i = 0;i < filter.length;i++){
					//logger.trace("filter " + filter[i]);
					if(name.equals(filter[i])){
						return true;           //过滤属性
					}
				}
				return false;
			}
		});
		
		JSONObject obj = JSONObject.fromObject(good1,config);
		logger.trace("json " + obj.toString());
		return obj;
	}
	
	public static JSONArray getGoodsJson(){
		JSONArray json = null;
		String[] filter = new String[]{"good","goodinfo"};
		
		String hql;
		hql = "from Good";
		
		/*查询*/
		logger.trace("hql=" + hql);
		List<Good> list = HibernateUtil.createQuery(hql);
		
		JsonConfig config = new JsonConfig();

		/*设置过滤器*/
		config.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object source, String name, Object value) {
				// TODO Auto-generated method stub
				for(int i = 0;i < filter.length;i++){
					//logger.trace("filter " + filter[i]);
					if(name.equals(filter[i])){
						return true;           //过滤属性
					}
				}
				return false;
			}
		});
		
		json = JSONArray.fromObject(list,config);
		logger.trace(json.toString());
		
		return json;
	}
	
	public static JSONArray getGoodsNameJson(){
		JSONArray json = null;
		
		List<String> names = getGoodsName();
		
		json = JSONArray.fromObject(names);
		
		logger.trace(json.toString());
		
		return json;
	}
	
	public static List<String> getGoodsName(){
		
		String hql;
		hql = "select goodName from Good";
		
		/*查询*/
		logger.trace("hql=" + hql);
		List<String> list = HibernateUtil.createQuery(hql);
		
/*		Iterator i = list.iterator();
		while(i.hasNext()){
			logger.trace(i.next());
		}*/
		
		return list;
	}

	public static Good getOneGood(Good goodInfo) {
		
		/*查询匹配条数*/
		String num = GoodTable.queryNum(goodInfo);
		logger.trace("GoodTable.queryNum " + num);
		Integer i = new Integer(num);
		if(i != 1){
			return null;
		}
		
		/*组hql语句*/
		String hql = "from Good ";
		if(goodInfo.getGoodName() != null || goodInfo.getGoodModels() != null
				|| goodInfo.getGoodPrice() != null || goodInfo.getGoodPics() != null){
			hql += "where ";
		}
		if(goodInfo.getGoodName() != null && goodInfo.getGoodName().length() > 0){
			hql += "goodName='";
			hql += goodInfo.getGoodName();
			hql += "' ";
		}
		if(goodInfo.getGoodPrice() != null && goodInfo.getGoodPrice().length() > 0){
			hql += "goodPrice='";
			hql += goodInfo.getGoodPrice();
			hql += "' ";
		}
		
		/*查询*/
		logger.trace("hql=" + hql);
		List<Good> list = HibernateUtil.createQuery(hql);
		Good goodinfo = list.get(0);
		
		logger.trace(goodinfo.getGoodName() + " " + goodinfo.getGoodPrice() + " id:" + goodinfo.getId());
		return goodinfo;
	}
	
	public static boolean saveGood(Good goodInfo){
		
		
		return true;
	}

	public static String queryNum(Good goodInfo) {
		String hql = null;
		
		hql = "select count(*) from Good ";
		if(goodInfo.getGoodName() != null || goodInfo.getGoodModels() != null
				|| goodInfo.getGoodPrice() != null || goodInfo.getGoodPics() != null){
			hql += "where ";
		}
		if(goodInfo.getGoodName() != null){
			hql += "goodName='";
			hql += goodInfo.getGoodName();
			hql += "' ";
		}
		if(goodInfo.getGoodPrice() != null){
			hql += "goodPrice='";
			hql += goodInfo.getGoodPrice();
			hql += "' ";
		}
		
		logger.trace("hql=" + hql);
		List list = HibernateUtil.createQuery(hql);
		String str = list.get(0).toString();
		logger.trace("count = " + str);
		
		return str;
	}
	
	

}
