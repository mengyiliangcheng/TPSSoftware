package com.jxggdxw.www;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Goods {
	
	Document document = null;
	
	static String strClassName = Goods.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    List<Good> goods = new ArrayList<Good>();
    
    /**
     * 
     * 测试代码*/
    public static void main(String[] args){
    	Good good = new Good();
    	good.setGoodName("张三");
    	good.setGoodPrice("123");
    	good.setGoodPicUrl("baidu.com");
    	good.setGoodPicUrl("nexgo.cn");
    	
    	Good good2 = new Good();
    	good2.setGoodName("李四");
    	good2.setGoodPicUrl("xgd.cn");
    	
    	Goods myGoods = new Goods();
    	myGoods.addGood(good);
    	myGoods.saveGoods();
    	logger.trace("goods over");
    	myGoods.readGoods();
    	logger.trace("read over");
    	logger.trace(myGoods.toString());
    	logger.trace("toString over");
    	myGoods.updateGoods(good2);
    	logger.trace("add over");
    	logger.trace(myGoods.toString());
    	myGoods.saveGoods();
    	logger.trace("save over");
    }
    
    public Goods(){
    	document = initGoods();
    }
	
    /**
     * 创建文件句柄*/
	private Document initGoods() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.newDocument();
		}catch(Exception ParserConfigurationException ){
			logger.error("builder xml error: " + ParserConfigurationException.getMessage());
			return null;
		}
        
	}
	
	public  boolean  addGood(Good good){
		if(good == null){
			return false;
		}
		
		goods.add(good);
		return true;
	}
	
	public List<Good> getGoods(){
		return goods;
	}
	
	/**
	 * 
	 * 更新goods*/
	public boolean updateGoods(Good good){
		boolean flag = false;
		
		if(good == null){
			return false;
		}
		
		
		Iterator<Good> iter = goods.iterator();
		//logger.trace("goods size " + goods.size());
		while(iter.hasNext()){
			Good tmp = (Good)iter.next();
			//logger.trace("tmp.getGoodName()" + tmp.getGoodName());
			//logger.trace("good.getGoodName()" + good.getGoodName());
			if(tmp.getGoodName().equals(good.getGoodName())){
				flag = true;
				
				List<String> urls = good.getGoodPicUrls();
				Iterator<String> urlsList = urls.iterator();
				while(urlsList.hasNext()){
					tmp.setGoodPicUrl((String)urlsList.next());
				}
				//logger.trace("equal");
				break;
			}
		}
		
		if(flag){
			
		}else{
			addGood(good);
		}
		
		return true;
	}
	
	public JSONArray getGoodInfoJson(String name){
		JSONArray json ;
		
		Iterator iter = goods.iterator();
		while(iter.hasNext()){
			
			Good good = (Good) iter.next();
			logger.trace("good name: " + good.getGoodName());
			logger.trace("name: " + name);
			if(good.getGoodName().equals(name)){
				json = good.getGoodInfoJson();
				return json;
			}
		}
		
		logger.error("can not found good info");
		return null;
	}
	
	public List<String> getGoodInfo(String name){
		List<String> info = new ArrayList<String>();
		
		Iterator iter = goods.iterator();
		while(iter.hasNext()){
			
			Good good = (Good) iter.next();
			logger.trace("good name: " + good.getGoodName());
			logger.trace("name: " + name);
			if(good.getGoodName().equals(name)){
				info = good.getGoodInfo();
				return info;
			}
		}
		
		logger.error("can not found good info");
		return null;
	}
	
	public List<String> getGoodsName(){
		
		List<String> nameList = new ArrayList<String>();
		
		
		Iterator iter = goods.iterator();
		while(iter.hasNext()){
			
			Good good = (Good)iter.next();
			nameList.add(good.getGoodName());
		}
		
		return nameList;
	}
	
	/**
	 * 
	 * 打印goods*/
	public String toString(){
		
		String str = new String();
		Iterator iter = goods.iterator();
		while(iter.hasNext()){
			Good good = (Good)iter.next();
			//logger.trace(good.toString());
			str += good.toString();
		}
		//logger.trace("toString rlt "+str);
		return str;
	}
	
	
	/**
	 * 
	 * 从文件中读取，放到goods中去*/
	public boolean readGoods(){
		
		File file = new File(GlobalParam.GOODS_SAVE_FILE);
		if(!file.exists()){
			return false;
		}
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc;
        try{
        	
    	   DocumentBuilder builder = factory.newDocumentBuilder();
           doc = builder.parse(GlobalParam.GOODS_SAVE_FILE);      
        }catch(Exception e){
        	logger.error("read goods error " + e.getMessage());
        	return false;
        }
     
        NodeList plist = doc.getElementsByTagName("good");
        goods = new ArrayList<Good>();
        
        for(int i=0;i<plist.getLength();i++){
            Element elem = (Element)plist.item(i);
            Good good = null;
            
            try{
	            good = new Good(elem.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
	            good.setGoodPrice(elem.getElementsByTagName("price").item(0).getFirstChild().getNodeValue());
	            good.setGoodAbstract(elem.getElementsByTagName("abstract").item(0).getFirstChild().getNodeValue());
	            
	            Node urlsNode = elem.getElementsByTagName("urls").item(0);
	            NodeList urlList = urlsNode.getChildNodes();
	      
	            //logger.trace("url num: " + urlList.getLength());
	            for(int j =0; j< urlList.getLength();j++){
	            	
	            	Element urlelem = (Element)urlList.item(j);
	            	//logger.trace(urlelem.getFirstChild().getNodeValue());
	            	good.setGoodPicUrl(urlelem.getFirstChild().getNodeValue());
	            }
            }catch(Exception e){
            	logger.error("read goods error: " + "list i: " + i + " info: " + e.getMessage());
            	
            }  
            goods.add(good);
        }
        logger.trace("read success: "+goods.toString());
		return true;
	}
	
	/**
	 * 
	 * 保存xml文件*/
	public boolean saveGoods(){
		if(goods == null){
			return false;
		}
		
		document = initGoods();
		
		Element goodsElem = document.createElement("goods");
		
		//添加节点
		Iterator<Good> iter =  goods.iterator();
		while(iter.hasNext()){
			Good good = (Good)iter.next();
			Element goodElem = document.createElement("good");
			Element nameElem = document.createElement("name");
			Element priceElem = document.createElement("price");
			Element abstractElem = document.createElement("abstract");
			Element urlsElem = document.createElement("urls");
			
			nameElem.appendChild(document.createTextNode(good.getGoodName()));
			priceElem.appendChild(document.createTextNode(good.getGoodPrice()));
			abstractElem.appendChild(document.createTextNode(good.getGoodsAbstract()));
			
			Iterator<String> urlIter = good.getGoodPicUrls().iterator();
			while(urlIter.hasNext()){
				
				Element urlElem = document.createElement("url");
				urlElem.appendChild(document.createTextNode(urlIter.next()));
				urlsElem.appendChild(urlElem);
			}
			
			goodElem.appendChild(nameElem);
			goodElem.appendChild(priceElem);
			goodElem.appendChild(abstractElem);
			goodElem.appendChild(urlsElem);
			
			goodsElem.appendChild(goodElem);
		}
		
		File file = new File(GlobalParam.GOODS_SAVE_FILE);
		if(file.exists()){
			file.delete();
		}

		//保存文件
		document.appendChild(goodsElem);
        DOMSource source = new DOMSource(document);
        TransformerFactory tf = TransformerFactory.newInstance();
        try{
	        Transformer t = tf.newTransformer();
	        StreamResult result = new StreamResult(new File(GlobalParam.GOODS_SAVE_FILE));
	        t.transform(source,result);
        }catch(Exception e){
        	logger.error("transformer error： " + e.getMessage());
        	return false;
        }
        
        logger.trace("save over");
		return true;
	}
}
