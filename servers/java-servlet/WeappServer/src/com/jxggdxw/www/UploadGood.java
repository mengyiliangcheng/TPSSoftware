package com.jxggdxw.www;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebServlet("/UploadGood")
public class UploadGood extends HttpServlet {
private static final long serialVersionUID = 1L;
	
    //set logger������־��¼
	static String strClassName = UploadGood.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	
        logger.trace("doGet over\r\n");
        
    }
    
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    
    	logger.error("doPost start ");
    	
    	//Goods goods = new Goods();
    	//goods.readGoods();
    	GoodDatabase db = new GoodDatabase();
    	
    	Good good = new Good();
    	Good goodpic = null;
    	
    	boolean flag = false;
    	
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
                    flag = true;
                    
                }else {    //file
                	goodpic = new Good();
                	String fieldName = item.getFieldName();  //��ȡ����name���Ե�ֵ  
                    String fileName = item.getName();  		//�����ļ���
                    
                    logger.trace("fieldName: " + fieldName);
                    logger.trace("filename: " + fileName);
                    
                    goodpic.setGoodName(fieldName);
                    
                    ToolUtils tool = new ToolUtils();
                    tool.makeDirs(GlobalParam.PIC_DIR, fieldName);

                    String picPath = GlobalParam.PIC_DIR + fieldName + "/" + fileName;
                    File uploadedFile = new File(picPath);
                    item.write(uploadedFile);  
                    logger.trace("upload success!");
                    
                    //�ظ����ͻ���һ����Ϣ      
                    String url = GlobalParam.PIC_URL_BASE + fieldName + "/" + fileName;
                    pw.println(url);  
                    goodpic.setGoodPicUrl(url); 
                }  
            }    
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.warn("exception: " + e.toString());
        }  
        
        logger.trace("upload good: " + good.toString());
        
        
        //�����ݸ��µ����ݿ�
        db.updateGood(good);
        
        db.updateUrl(goodpic);
        
        logger.trace("doGet over\r\n");
    }  
    
    
    public void init() throws ServletException
    {
    	System.out.println("init..");
    	GoodDatabase db = new GoodDatabase();
    	db.checkDriver();
    	db.createTable();
    }
}
