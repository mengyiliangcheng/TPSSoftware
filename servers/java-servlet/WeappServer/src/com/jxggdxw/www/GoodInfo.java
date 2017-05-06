package com.jxggdxw.www;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/GoodInfo")
public class GoodInfo extends HttpServlet {
	
    //set logger设置日志记录
	static String strClassName = GoodInfo.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
	
	public GoodInfo(){
		
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	
    	File file = new File(GlobalParam.GOODS_SAVE_FILE);
    	if(!file.exists()){
            PrintWriter pw = response.getWriter();  
            
            //回复给客户端一个信息      
            pw.println("no file!"); 
    	}
    	
    	//Map<String,String[]> data = request.getParameterMap();
    	//logger.trace(data.toString());
    	
    	String cmd = request.getParameter(GlobalParam.STR_COMMAND);
    	logger.trace("command: " + cmd);
    	if(null == cmd){
    		return ;
    	}
    	switch(cmd){
    		case GlobalParam.STR_COMMAND_ALL_GOODS:
    			sendGoodsName(response);
    			break;
    			
    		case GlobalParam.STR_COMMAND_GOOD_INFO:
    			String name = request.getParameter(GlobalParam.STR_COMMAND_GOOD_NAME);
    			logger.trace("name->" + name);
    			sendGoodInfo(name,response);
    			break;
    			
    		default:
    			logger.error("error cmd");
    			break;
    	}
    	
    	//sendGoodsName(response);
    	
    	//sendGoodInfo("wuuuu",response);
        
        logger.trace("doGet over\r\n");
        
    }
    
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    
    	logger.trace("doPost start");
    	doGet(request,response);
    }
    
    public void sendGoodInfo(String name,HttpServletResponse response){
    	
    	Goods goods = new Goods();
    	goods.readGoods();
    	
    	PrintWriter pw ;
    	
    	List<String> info = goods.getGoodInfo(name);
    	
    	if(null == info){
    		info = new ArrayList();
    		info.add("no good info");
    	}
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodInfo error" + e.toString());
    		return ;
    	}
    	
        //回复给客户端一个信息      
        pw.println(info); 
    }
    
    public void sendGoodsName( HttpServletResponse response){
    	
    	Goods goods = new Goods();
    	
    	goods.readGoods();
    	
    	List<String> nameList = goods.getGoodsName();
    	PrintWriter pw ;
    	
    	try{
    		pw = response.getWriter();  
    	}catch(Exception e){
    		logger.error("sendGoodsName error " + e.toString());
    		return ;
    	}
        
        //回复给客户端一个信息      
        pw.println(nameList); 
    }
    
    public  void sendFile(HttpServletResponse response){
    	
    	File file = new File(GlobalParam.GOODS_SAVE_FILE);

    	/**
    	 * 
    	 * 给client回复文件*/
    	try{
	        FileInputStream fileInputStream = new FileInputStream(file);  
	        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);  
	        byte[] b = new byte[bufferedInputStream.available()];  
	        bufferedInputStream.read(b);  
	        OutputStream outputStream = response.getOutputStream();  
	        outputStream.write(b);  
	        
	        //
	        bufferedInputStream.close();  
	        outputStream.flush();  
	        outputStream.close(); 
    	}catch(Exception e){
    		logger.trace("send file error " + e.toString());
    	}
    }
    
}
