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
	
    //set logger设置日志记录
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
            // 注意参数paraName（变量）不能加双引号，否则就是找参数名叫paraName的对应值了。  
            String[] paramValues = request.getParameterValues(paraName);  
            // 这个参数只有一个值  
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
                // 这个参数有好几条值  
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
          
        //回复给客户端一个信息      
        pw.println("receive!");  
        //利用request对象返回客户端来的输入流  
        try (ServletInputStream sis = request.getInputStream()) {  
              
            OutputStream os = new FileOutputStream("file.bin");  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            byte[] buf= new byte[4096];  
            int length = 0;  
            length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法  
            logger.trace("buf length===="+length);
            logger.trace("buf===="+new String(buf));
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = sis.read(buf);  
                logger.trace("buf===="+ new String(buf));
            }  
            sis.close();  
            bos.close();  
            os.close();  
        }
        logger.trace("end\r\n");
    	
    	logger.trace("entry");
    	logger.warn("main");
    	logger.error("HttpServlet");
    	logger.trace("exit");
    	
        PrintWriter out = response.getWriter();  
        out.write("<html>\r\n");  
        out.write("<head>\r\n");  
        // 设定解码方式  
        out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");  
        out.write("</head>\r\n");  
        out.write("\r\n");  
        out.write("<body>\r\n");  
        out.write("<H1>\r\n");  
        out.write("helloworld,boy!");  
        out.write("\r\n");  
        out.write("</H1>\r\n");  
        out.write("</body>\r\n");  
        out.write("</html>");  

    }  */

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	
    	logger.trace("doGet start");
    	String tempDirectory = "d:/temp/";    //要在最后加上斜杠:temp/，缓存文件目录  
        try {  
            int sizeThreshold = 1024 * 512;  //写满该大小的缓存后，存入硬盘中。  
            File repositoryFile = new File(tempDirectory);  
            FileItemFactory factory = new DiskFileItemFactory(sizeThreshold, repositoryFile);  
            
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("utf-8");  //设置字符编码  
            upload.setSizeMax(50 * 1024 * 1024); // 设置最大上传大小 set every upload file'size less than 50M  
            List<FileItem> items = upload.parseRequest(request);   //这里开始执行上传  
            Iterator iter = items.iterator();  
              
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();   //FileItem就是表示一个表单域。  
                  
                if(item.isFormField()){ //isFormField方法用于判断FileItem是否代表一个普通表单域(即非file表单域)  
 
                    logger.trace("getFieldName: "+item.getFieldName());
                    logger.trace("item: " + item.getString());
                }else {  
                    //String fieldName = item.getFieldName();  //获取表单域name属性的值  
                    //String fileName = item.getName();     //返回该文件在客户机上的文件名。e.g: e:\dianying\\video\1.wmv  
                    //System.out.println("*****"+fieldName);  
                    //System.out.println("*****"+fileName);  
                    String path = item.getName();  
                    logger.trace("org name: " + path);
                    String fileName = "pic.jpg";  
                    logger.trace("filename: " + fileName);
                    File uploadedFile = new File("d:/video/" + fileName);  
                    item.write(uploadedFile);  
                    logger.trace("upload success!");
                }  
            } 
            
            
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.warn("exception: " + e.toString());
        }  
        
/*        //利用request对象返回客户端来的输入流  
        try (ServletInputStream sis = request.getInputStream()) {  
              
            OutputStream os = new FileOutputStream("file.txt");  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            byte[] buf= new byte[10240];  
            int length = 0;  
            length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法  
            logger.trace("buf length===="+length);
            logger.trace("buf===="+new String(buf));
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = sis.read(buf);  
                logger.trace("buf===="+ new String(buf));
            }  
            sis.close();  
            bos.close();  
            os.close();  
        }*/
  
    }
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	logger.trace("doPost");
    	this.doGet(request, response);
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
