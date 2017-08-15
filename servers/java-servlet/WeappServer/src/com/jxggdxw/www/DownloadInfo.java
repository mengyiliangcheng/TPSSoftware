package com.jxggdxw.www;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/DownloadInfo")
public class DownloadInfo extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//set logger设置日志记录
	static String strClassName = GoodInfo.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	//response.setCharacterEncoding("GB2312");
    	
    	
    	String cmd = request.getParameter(GlobalParam.STR_COMMAND);
    	logger.trace("command: " + cmd);
		
		//sendGoodsInfoJsonEncode(response);
    	
    	if(null == cmd){
    		return ;
    	}
    	switch(cmd){
    	
    		//获得所有商品名称
    		case GlobalParam.STR_COMMAND_ALL_GOODS:
    			sendGoodsNameJson(response);
    			break;
    			
    		//下发一条商品信息
    		case GlobalParam.STR_COMMAND_GOOD_INFO:
    			String name = request.getParameter(GlobalParam.STR_COMMAND_GOOD_NAME);
    			name = new String(name.getBytes("iso8859-1"),"utf-8");
    			name = URLDecoder.decode(name,"utf-8");
    			logger.trace("name->" + name);
    			sendGoodInfoJsonEncode(name,response);
    			break;
    			
    		//下发所有商品信息
    		case GlobalParam.STR_COMMAND_GOODS_INFO:
    			//sendGoodsInfoJson(response);
    			sendGoodsInfoJsonEncode(response);
    			break;
    			
    		//删除指定商品
    		case  GlobalParam.STR_COMMAND_GOOD_DEL:
    			String del_name = request.getParameter(GlobalParam.STR_COMMANDS_GOOD_NAME);
    			del_name = new String(del_name.getBytes("iso8859-1"),"utf-8");
    			delGoodsInfo(del_name,response);
    			break;
    			
    		default:
    			logger.error("error cmd");

    			break;
    	}
    	

        logger.trace("doGet over\r\n");
        
    }
    
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    
    	logger.trace("doPost start");
    	doGet(request,response);
    }
    
    public void delGoodsInfo(String names,HttpServletResponse response){
    	
    	logger.trace("names = " + names);
    	
		String[] dels = names.split(",");
		for(int i = 0;i < dels.length;i ++){
			logger.trace("name->" + dels[i]);
			delGoodInfo(dels[i],response);
		}
    }
    
    //删除一条记录
    public void delGoodInfo(String name,HttpServletResponse response){
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = db.deleteGoodJson(name);
    	try{
	    	if(json.getString("ret").equals("success")){
                String folderName = String.valueOf(name.hashCode());
                logger.trace("hash value = " + folderName);
                
	    		ToolUtils tool = new ToolUtils();
	    		boolean b = tool.DeleteFolder(GlobalParam.PIC_DIR,folderName);
	    		logger.trace("delete file = " + b);
	    	}
    	}catch(JSONException e){
    		logger.error(e.toString());
    	}
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
    	logger.trace("json string: " + json.toString());
        //回复给客户端一个信息      
        pw.println(json.toString()); 
    }
    
    public void sendGoodsInfoJsonEncode(HttpServletResponse response){
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = null;
    	try{
    		json = db.getGoodsInfoEncode();
    	}catch(Exception e){
    		logger.error(e.toString());
    	}
    	logger.trace(json.toString());
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
        //回复给客户端一个信息      
        pw.println(json.toString()); 
    }
    
    public void sendGoodsInfoJson(HttpServletResponse response){
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = db.getGoodsInfo();
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
/*    	String str ;
    	logger.trace("json string: " + json.toString());
    	try{
    	str = URLEncoder.encode(json.toString(), "utf-8");
    	}catch(Exception e){
    		logger.trace("encode error");
    		pw.print("error");
    		return ;
    	}
    	
        pw.println(str.toString()); */
    	
    	
        //回复给客户端一个信息      
        pw.println(json.toString()); 
        
    }
    
    public void sendGoodInfoJsonEncode(String name,HttpServletResponse response){
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = db.getGoodInfoEncode(name);
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
    	logger.trace("json string: " + json.toString());
        //回复给客户端一个信息      
        pw.println(json.toString()); 
    }
    
    public void sendGoodInfoJson(String name,HttpServletResponse response){
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = db.getGoodInfo(name);
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
    	logger.trace("json string: " + json.toString());
        //回复给客户端一个信息      
        pw.println(json.toString()); 
    }
    public void sendGoodsNameJson( HttpServletResponse response){
       	
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	JSONObject json = db.getGoodsName();
    	
    	PrintWriter pw ;
    	response.setHeader("content-type","text/html;charset=GB2312");
    	response.setCharacterEncoding("GB2312");
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
    	
    	logger.trace("json string: " + json.toString());
        //回复给客户端一个信息      
        pw.println(json.toString()); 
    }
}
