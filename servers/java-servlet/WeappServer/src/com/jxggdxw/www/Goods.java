package com.jxggdxw.www;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    public static void main(String[] args){
    	Good good = new Good();
    	good.setGoodName("aaa");
    	good.setGoodPrice("123");
    	good.setGoodPicUrl("baidu.com");
    	good.setGoodPicUrl("nexgo.cn");
    	
    	Goods myGoods = new Goods();
    	myGoods.addGood(good);
    	myGoods.saveGoods();
    	logger.trace("goods over");
    }
    
    public Goods(){
    	document = initGoods();
    }
	
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
	
	public boolean saveGoods(){
		if(goods == null){
			return false;
		}
		
		Element goodsElem = document.createElement("goods");
		
		Iterator<Good> iter =  goods.iterator();
		while(iter.hasNext()){
			Good good = (Good)iter.next();
			Element goodElem = document.createElement("good");
			Element nameElem = document.createElement("name");
			Element priceElem = document.createElement("price");
			Element urlsElem = document.createElement("urls");
			
			nameElem.appendChild(document.createTextNode(good.getGoodName()));
			priceElem.appendChild(document.createTextNode(good.getGoodPrice()));
			
			Iterator<String> urlIter = good.getGoodPicUrls().iterator();
			while(urlIter.hasNext()){
				
				Element urlElem = document.createElement("url");
				urlElem.appendChild(document.createTextNode(urlIter.next()));
				urlsElem.appendChild(urlElem);
			}
			
			goodElem.appendChild(nameElem);
			goodElem.appendChild(priceElem);
			goodElem.appendChild(urlsElem);
			
			goodsElem.appendChild(goodElem);
		}
		
		document.appendChild(goodsElem);
        DOMSource source = new DOMSource(document);
        TransformerFactory tf = TransformerFactory.newInstance();
        try{
	        Transformer t = tf.newTransformer();
	        StreamResult result = new StreamResult(new File("goods.xml"));
	        t.transform(source,result);
        }catch(Exception e){
        	logger.error("transformer error�� " + e.getMessage());
        	return false;
        }
		return true;
	}
}
