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
	
    //set logger������־��¼
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
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
    	
    	String tempDirectory = GlobalParam.TEMP_DIR;
        try {    
            File repositoryFile = new File(tempDirectory);  
            FileItemFactory factory = new DiskFileItemFactory(GlobalParam.MAX_TEMP_BUF_SIZE, repositoryFile);  
            
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("utf-8");  //�����ַ�����  
            upload.setSizeMax(GlobalParam.MAX_FILE_SIZE); // ��������ϴ���С set every upload file'size less than 50M  
            List<FileItem> items = upload.parseRequest(request);   //���￪ʼִ���ϴ�  
            Iterator iter = items.iterator();  
              
            PrintWriter pw = response.getWriter();  
            
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();   //FileItem���Ǳ�ʾһ������  
                  
                if(item.isFormField()){ //isFormField���������ж�FileItem�Ƿ����һ����ͨ����(����file����)  
 
                	//�����������
                    String fieldname = item.getFieldName();
                    fieldname = new String(fieldname.getBytes("iso8859-1"),"utf-8");
                    logger.trace("get field name��" + fieldname);
                    
                    String itemvalue = item.getString();
                    itemvalue = new String(itemvalue.getBytes("iso8859-1"),"utf-8");
                    logger.trace("value: " + itemvalue);
                    
                    if(item.getFieldName().equals(GlobalParam.STR_GOOD_NAME)){
                    	good.setGoodName(itemvalue);
                    }else if(item.getFieldName().equals(GlobalParam.STR_GOOD_PRICE)){
                    	good.setGoodPrice(itemvalue);
                    }else if(item.getFieldName().equals(GlobalParam.STR_GOOD_ABSTRACT)){
                    	good.setGoodAbstract(itemvalue);
                    }else{
                    	
                    }
                    
                }else {    //file
                	
                	String fieldName = item.getFieldName();  //��ȡ����name���Ե�ֵ  
                    String fileName = item.getName();  		//�����ļ���
                    
                    logger.trace("fieldName: " + fieldName);
                    logger.trace("filename: " + fileName);
                    
                    good.setGoodName(fieldName);

                    File uploadedFile = new File(GlobalParam.PIC_DIR + fileName);
                    item.write(uploadedFile);  
                    logger.trace("upload success!");
                    
                    //�ظ����ͻ���һ����Ϣ      
                    pw.println(GlobalParam.PIC_URL_BASE + fileName);  
                    good.setGoodPicUrl(GlobalParam.PIC_URL_BASE + fileName); 
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
