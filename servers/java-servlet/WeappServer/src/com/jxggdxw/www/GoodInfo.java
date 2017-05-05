package com.jxggdxw.www;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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
    
    private static final String GOODS_SAVE_FILE_TEST = "goods.xml";
    private static final String GOODS_SAVE_FILE_SERVER = "/usr/local/tomcat/webapps/WeappServer/pic/goods.xml";
    
    private static final String GOODS_SAVE_FILE = GOODS_SAVE_FILE_TEST;
    
	
	public GoodInfo(){
		
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	
    	File file = new File(GOODS_SAVE_FILE);
    	if(!file.exists()){
            PrintWriter pw = response.getWriter();  
            
            //回复给客户端一个信息      
            pw.println("no file!"); 
    	}
    	
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
        
        logger.trace("doGet over\r\n");
        
    }
    
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    
    	logger.trace("doPost start");
    	doGet(request,response);
    }
    
}
