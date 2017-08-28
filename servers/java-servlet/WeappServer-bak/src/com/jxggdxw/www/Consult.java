package com.jxggdxw.www;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@WebServlet("/Consult")
public class Consult extends HttpServlet {
private static final long serialVersionUID = 1L;
	
    //set logger设置日志记录
	static String strClassName = Consult.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	logger.trace("content length = " + request.getContentLength());
		String echostr = request.getParameter(GlobalParam.STR_ECHOSTR);
    	logger.trace("STR_ECHOSTR: " + echostr);
    	if(null != echostr){
    		valid(request,response);
    	}else{
    		responseMesg(request,response);
    	}
    	
        logger.trace("doGet over\r\n");
        
    }
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException{
    	
    	logger.trace("dopost start"); 
    	doGet(request,response);
        logger.trace("dopost over\r\n");
        
    }
    
    private void valid(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter pw = response.getWriter();
    	if(checkSign(request)){
            //回复给客户端一个信息      
    		String echostr = request.getParameter(GlobalParam.STR_ECHOSTR);
        	logger.trace("STR_ECHOSTR: " + echostr);
            pw.println(echostr);
    	}else{
    		logger.trace("sign fail ");
    		pw.println("error");
    	}
    }
    
    private boolean checkSign(HttpServletRequest request){
    	String sign = request.getParameter(GlobalParam.STR_SIGNATURE);
    	logger.trace("STR_SIGNATURE: " + sign);
    	String stamp = request.getParameter(GlobalParam.STR_TIMESTAMP);
    	logger.trace("STR_TIMESTAMP: " + stamp);
    	String nonce = request.getParameter(GlobalParam.STR_NONCE);
    	logger.trace("STR_NONCE: " + nonce);
    	/*String echostr = request.getParameter(GlobalParam.STR_ECHOSTR);
    	logger.trace("STR_ECHOSTR: " + echostr);*/
    	if(null == sign || null == stamp || null == nonce){
    		return false;
    	}
    	
    	ArrayList<String> list=new ArrayList<String>();
    	list.add("TPSJXGGDXW");
    	list.add(stamp);
    	list.add(nonce);

    	Collections.sort(list);
    	
    	ToolUtils tool = new ToolUtils();
    	String tmpStr = tool.SHAHashing(list.get(0)+list.get(1)+list.get(2));
    	logger.trace("cal sign = " + tmpStr);
    	logger.trace("read sign = " + sign);
    	
    	if(sign.equals(tmpStr)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    private void responseMesg(HttpServletRequest request, HttpServletResponse response){
    	
    	if(request.getContentLength() <= 0){
    		logger.trace("empty mesg");
    		return ;
    	}
    	Document docs = null;
    	try{
    	 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     
         DocumentBuilder db = dbf.newDocumentBuilder();     
         docs = db.parse(request.getInputStream());  
    	}catch(Exception e){
    		logger.trace(e.toString());
    	}
    	
    	String user = getValueByTagName(docs,GlobalParam.STR_TOUSERNAME);
    	logger.trace("tousername: "+user);
    	
    }
    
    public static String getValueByTagName(Document doc, String tagName){  
        if(doc == null || null == tagName){  
            return "";  
        }  
        NodeList pl = doc.getElementsByTagName(tagName);  
        if(pl != null && pl.getLength() > 0){  
        	return pl.item(0).getTextContent();
            //return StringUtil.dealParam(StringUtil.convertNull( pl.item(0).getTextContent()));  
        }  
        return null;  
    }  
    
    private String getRespMesg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
    	
    	ServletInputStream sis = null;
    	byte[] xmldataByte = null;
    	
        try {  
            // 取HTTP请求流  
            sis = request.getInputStream();  
            // 取HTTP请求流长度  
            int size = request.getContentLength();  
            // 用于缓存每次读取的数据  
            byte[] buffer = new byte[size];  
            // 用于存放结果的数组  
            xmldataByte = new byte[size];  
            int count = 0;  
            int rbyte = 0;  
            // 循环读取  
            while (count < size) {   
                // 每次实际读取长度存于rbyte中  
                rbyte = sis.read(buffer);   
                for(int i=0;i<rbyte;i++) {  
                    xmldataByte[count + i] = buffer[i];  
                }  
                count += rbyte;  
            }  
        }catch(Exception e){
        	logger.trace("getRespMesg error " + e.toString());
        }
        
        return new String(xmldataByte, "UTF-8"); 
    }
}
