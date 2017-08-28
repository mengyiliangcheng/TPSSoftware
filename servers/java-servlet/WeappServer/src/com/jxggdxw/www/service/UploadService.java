package com.jxggdxw.www.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.json.JSONObject;

import com.jxggdxw.www.GlobalParam;
import com.jxggdxw.www.domain.Good;
import com.jxggdxw.www.domain.GoodPic;
import com.jxggdxw.www.ToolUtils;
import com.jxggdxw.www.UploadGood;
import com.jxggdxw.www.dao.GoodUpdate;

/**
 * @author pengyicheng
 *
 */
@WebServlet("/UploadService")
public class UploadService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
 //set logger设置日志记录
	static String strClassName = UploadService.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.error("doPost start ");
		
		Good good = new Good();
		Good goodpic = null;
		
		String tempDirectory = GlobalParam.TEMP_DIR;
        try {    
            File repositoryFile = new File(tempDirectory);  
            FileItemFactory factory = new DiskFileItemFactory(GlobalParam.MAX_TEMP_BUF_SIZE, repositoryFile);  
            
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("utf-8");  //设置字符编码  
            upload.setSizeMax(GlobalParam.MAX_FILE_SIZE); // 设置最大上传大小 set every upload file'size less than 50M  
            List<FileItem> items = upload.parseRequest(request);   //这里开始执行上传  
            Iterator iter = items.iterator();  
              
            response.setHeader("content-type","text/html;charset=GB2312");
            response.setCharacterEncoding("GB2312");
            PrintWriter pw = response.getWriter();  
            
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();   //FileItem就是表示一个表单域。  
                  
                if(item.isFormField()){ //isFormField方法用于判断FileItem是否代表一个普通表单域(即非file表单域)  
 
                	//解决中文乱码
                    String fieldname = item.getFieldName();
                    fieldname = new String(fieldname.getBytes("iso8859-1"),"utf-8");
                    logger.trace("get field name：" + fieldname);
                    
                    String itemvalue = item.getString();
                    itemvalue = new String(itemvalue.getBytes("iso8859-1"),"utf-8");
                    logger.trace("value: " + itemvalue);
                    itemvalue = URLDecoder.decode(itemvalue,"utf-8");
                    logger.trace("decode = " + itemvalue);
                    
                    if(item.getFieldName().equals(GlobalParam.STR_GOOD_NAME)){
                    	
                    	good.setGoodName(itemvalue);
                    }else if(item.getFieldName().equals(GlobalParam.STR_GOOD_PRICE)){
                    	good.setGoodPrice(itemvalue);
                    }else if(item.getFieldName().equals(GlobalParam.STR_GOOD_ABSTRACT)){
                    	good.setGoodAbs(itemvalue);
                    }
                    
                }else {    //file

                	goodpic = new Good();
                	GoodPic pic = new GoodPic();
                	
                	String fieldName = item.getFieldName();  //获取表单域name属性的值  
                    String fileName = item.getName();  		//返回文件名
                    
                    logger.trace("fieldName: " + fieldName);
                    logger.trace("filename: " + fileName);
                    
                    fieldName = URLDecoder.decode(fieldName,"utf-8");
                    logger.trace("decode fieldName = " + fieldName);

                    goodpic.setGoodName(fieldName);
                    
                    /*文件夹名是文件名的hash值*/
                    String folderName = String.valueOf(fieldName.hashCode());
                    logger.trace("hash value = " + folderName);
                    
                    ToolUtils tool = new ToolUtils();
                    tool.makeDirs(GlobalParam.PIC_DIR, folderName);

                    String picPath = GlobalParam.PIC_DIR + folderName + "/" + fileName;
                    File uploadedFile = new File(picPath);
                    item.write(uploadedFile);  
                    logger.trace("upload success!");
                    
                    //回复给客户端一个信息      
                    String url = GlobalParam.PIC_URL_BASE + folderName + "/" + fileName;
                    JSONObject json = new JSONObject();
                    json.put("url", url);
                    pw.println(json.toString());  
                    
                    pic.setPath(url);
                    pic.setInfo(fieldName);
                    goodpic.getGoodPics().add(pic); 
                }  
            }    
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.warn("exception: " + e.toString());
        }  
        
        GoodUpdate update = new GoodUpdate();
        update.saveGood(good);
        update.saveUrl(goodpic);
        
        logger.trace("doGet over\r\n");
	}

    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start"); 
    	
    	doPost(request, response);
    	
        logger.trace("doGet over\r\n");
        
    }
	

}
