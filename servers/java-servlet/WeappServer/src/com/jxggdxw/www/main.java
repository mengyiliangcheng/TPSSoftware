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
        //����request���󷵻ؿͻ�������������  
        try (ServletInputStream sis = request.getInputStream()) {  
              
            OutputStream os = new FileOutputStream("file.bin");  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            byte[] buf= new byte[4096];  
            int length = 0;  
            length = sis.readLine(buf, 0, buf.length);//ʹ��sis�Ķ�ȡ���ݵķ���  
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
        // �趨���뷽ʽ  
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
    	String tempDirectory = "d:/temp/";    //Ҫ��������б��:temp/�������ļ�Ŀ¼  
        try {  
            int sizeThreshold = 1024 * 512;  //д���ô�С�Ļ���󣬴���Ӳ���С�  
            File repositoryFile = new File(tempDirectory);  
            FileItemFactory factory = new DiskFileItemFactory(sizeThreshold, repositoryFile);  
            
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("utf-8");  //�����ַ�����  
            upload.setSizeMax(50 * 1024 * 1024); // ��������ϴ���С set every upload file'size less than 50M  
            List<FileItem> items = upload.parseRequest(request);   //���￪ʼִ���ϴ�  
            Iterator iter = items.iterator();  
              
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();   //FileItem���Ǳ�ʾһ������  
                  
                if(item.isFormField()){ //isFormField���������ж�FileItem�Ƿ����һ����ͨ����(����file����)  
 
                    logger.trace("getFieldName: "+item.getFieldName());
                    logger.trace("item: " + item.getString());
                }else {  
                    //String fieldName = item.getFieldName();  //��ȡ����name���Ե�ֵ  
                    //String fileName = item.getName();     //���ظ��ļ��ڿͻ����ϵ��ļ�����e.g: e:\dianying\\video\1.wmv  
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
        
/*        //����request���󷵻ؿͻ�������������  
        try (ServletInputStream sis = request.getInputStream()) {  
              
            OutputStream os = new FileOutputStream("file.txt");  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            byte[] buf= new byte[10240];  
            int length = 0;  
            length = sis.readLine(buf, 0, buf.length);//ʹ��sis�Ķ�ȡ���ݵķ���  
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
