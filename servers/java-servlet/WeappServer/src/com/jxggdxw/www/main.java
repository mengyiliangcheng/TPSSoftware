package com.jxggdxw.www;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;  
import java.util.Iterator;  
import java.util.List;  

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileItemFactory;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

@WebServlet("/main")

public class main extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//windows ���ļ���ŵ�ַ
	private static final String TEMP_DIR_TEST 	= "d:/temp/";
	private static final String PIC_DIR_TEST  	= "d:/video/";
	
	//linux ���ļ���ŵ�ַ
	private static final String TEMP_DIR_SERVER 	= "/usr/local/tomcat/webapps/WeappServer/temp/";
	private static final String PIC_DIR_SERVER  	= "/usr/local/tomcat/webapps/WeappServer/pic/";
	
	
	private static final String TEMP_DIR = TEMP_DIR_SERVER; //Ҫ��������б��:temp/�������ļ�Ŀ¼,��Ҫ�ڴ����ֶ�����temp�ļ��У������ܱ���  
	private static final String PIC_DIR  = PIC_DIR_SERVER;
	
	//url����ַ
	private static final String PIC_URL_BASE	= "https://32906079.jxggdxw.com:8443/WeappServer/pic/";
	private static final String PIC_URL_FILE    = "/usr/local/tomcat/webapps/WeappServer/pic/url.txt";
	
	//��Ʒ��Ϣ�ַ�������
	private static final String STR_GOOD_NAME 	= "goodsname";
	private static final String STR_GOOD_PRICE	= "goodsprice";
	private static final String STR_GOOD_ABSTRACT = "goodsabstract";
	
	private static final int MAX_TEMP_BUF_SIZE		= 1024 * 512;  //д���ô�С�Ļ���󣬴���Ӳ���С�
	private static final int MAX_FILE_SIZE 			= 50 * 1024 * 1024;
	
    //set logger������־��¼
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
  /*
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException { 
    	test_logger log = new test_logger();
    	
    	logger.trace("doGet");
    	
    	logger.trace("client IP :"+request.getRemoteHost());
    	logger.trace("Host IP :" + request.getLocalAddr());
    	logger.trace("getContentType :" + request.getContentType());
    	logger.trace("getContentLength :" + request.getContentLength());
    	logger.trace("getContextPath :" + request.getContextPath());
    	
    	// Returns an Enumeration of String objects containing the names of the parameters contained in this request.   
        Enumeration paramNames = request.getParameterNames();  
        // Tests if this enumeration contains more elements.   
        while(paramNames.hasMoreElements()) {  
            // Returns the next element of this enumeration if this enumeration object has at least one more element to provide.   
            String paraName = (String)paramNames.nextElement();  
            logger.trace("paraName: "+paraName);
            //out.println("<tr><td>" + paraName + "/n<td>");  
            // Returns an array of String objects containing all of the values the given request parameter has, or null if the parameter does not exist.   
            // ע�����paraName�����������ܼ�˫���ţ���������Ҳ�������paraName�Ķ�Ӧֵ�ˡ�  
            String[] paramValues = request.getParameterValues(paraName);  
            // �������ֻ��һ��ֵ  
            if(paramValues.length == 1) {  
                String paramValue = paramValues[0];  
                if(paramValue.length() == 0) {  
                   // out.println("<I>no value</I>");  
                	logger.trace("paramValue: "+"no value");
                } else {  
                  //  out.println(paramValue);
                	logger.trace("paramValue: "+paramValue);
                }  
            }else {  
                // ��������кü���ֵ  
                //out.println("<UL>");  
                for(int i = 0; i < paramValues.length; i++) {  
                   // out.println("<LI>" + paramValues[i]);  
                	logger.trace("paramValue: "+paramValues[i]);
                }  
               // out.println("</UL>");  
            }  
        }  
    	
    	response.setContentType("text/html");  
        
    	  
        PrintWriter pw = response.getWriter();  
          
        //�ظ����ͻ���һ����Ϣ      
        pw.println("receive!");  


    }  */

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	
        
        logger.trace("doGet over\r\n");
        
    }
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    
    	logger.trace("doPost start ");
    	
    	Goods goods = new Goods();
    	goods.readGoods();
    	Good good = new Good();
    	
    	String tempDirectory = TEMP_DIR;
        try {    
            File repositoryFile = new File(tempDirectory);  
            FileItemFactory factory = new DiskFileItemFactory(MAX_TEMP_BUF_SIZE, repositoryFile);  
            
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("utf-8");  //�����ַ�����  
            upload.setSizeMax(MAX_FILE_SIZE); // ��������ϴ���С set every upload file'size less than 50M  
            List<FileItem> items = upload.parseRequest(request);   //���￪ʼִ���ϴ�  
            Iterator iter = items.iterator();  
              
            PrintWriter pw = response.getWriter();  
            
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();   //FileItem���Ǳ�ʾһ������  
                  
                if(item.isFormField()){ //isFormField���������ж�FileItem�Ƿ����һ����ͨ����(����file����)  
 
                    logger.trace("getFieldName: "+item.getFieldName());
                    logger.trace("item: " + item.getString());
                    
                    if(item.getFieldName().equals(STR_GOOD_NAME)){
                    	good.setGoodName(item.getString());
                    }else if(item.getFieldName().equals(STR_GOOD_PRICE)){
                    	good.setGoodPrice(item.getString());
                    }else if(item.getFieldName().equals(STR_GOOD_ABSTRACT)){
                    	good.setGoodAbstract(item.getString());
                    }else{
                    	
                    }
                    
                }else {    //file
                	
                	String fieldName = item.getFieldName();  //��ȡ����name���Ե�ֵ  
                    String fileName = item.getName();  		//�����ļ���
                    
                    logger.trace("fieldName: " + fieldName);
                    logger.trace("filename: " + fileName);
                    
                    good.setGoodName(fieldName);

                    File uploadedFile = new File(PIC_DIR + fileName);
                    item.write(uploadedFile);  
                    logger.trace("upload success!");
                    
                    //�ظ����ͻ���һ����Ϣ      
                    pw.println(PIC_URL_BASE + fileName);  
                    good.setGoodPicUrl(PIC_URL_BASE + fileName); 
                }  
            }    
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.warn("exception: " + e.toString());
        }  
        
        logger.trace("upload good: " + good.toString());
        
        goods.updateGoods(good);
        
        goods.saveGoods();
        
        logger.trace("doGet over\r\n");
    }  
    
    
    public void init() throws ServletException
    {
    	System.out.println("init..");
    }
    
    public void destory()
    {
    	System.out.println("destory..");
    	
    }

}
