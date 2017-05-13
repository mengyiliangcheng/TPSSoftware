package com.jxggdxw.www;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


@WebServlet("/DownloadInfo")
public class DownloadInfo extends HttpServlet {
    //set logger设置日志记录
	static String strClassName = GoodInfo.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	//response.setCharacterEncoding("GB2312");
    	
    	
    	File file = new File(GlobalParam.GOODS_SAVE_FILE);
    	if(!file.exists()){
            PrintWriter pw = response.getWriter();  
            
            //回复给客户端一个信息      
            pw.println("no file!"); 
    	}
        //PrintWriter pw = response.getWriter();  
        
        //response.setHeader("content-type","text/html;charset=UTF-8");
        //response.setCharacterEncoding("GB2312");
        //回复给客户端一个信息      
        //pw.println("中文!"); 
    	
    	//Map<String,String[]> data = request.getParameterMap();
    	//logger.trace(data.toString());
    	
    	//sendGoodsNameJson(response);
    	//sendGoodInfoJson("中文",response);
    	
    	String cmd = request.getParameter(GlobalParam.STR_COMMAND);
    	logger.error("command: " + cmd);
    	
    	if(null == cmd){
    		return ;
    	}
    	switch(cmd){
    		case GlobalParam.STR_COMMAND_ALL_GOODS:
    			sendGoodsNameJson(response);
    			break;
    			
    		case GlobalParam.STR_COMMAND_GOOD_INFO:
    			String name = request.getParameter(GlobalParam.STR_COMMAND_GOOD_NAME);
    			name = new String(name.getBytes("iso8859-1"),"utf-8");
    			logger.trace("name->" + name);
    			sendGoodInfoJson(name,response);
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
