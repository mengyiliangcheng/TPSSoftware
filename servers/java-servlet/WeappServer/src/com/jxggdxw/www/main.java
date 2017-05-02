package com.jxggdxw.www;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;  
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/main")

public class main extends HttpServlet {

	private static final long serialVersionUID = 1L;
    //private static Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException { 
    	/*test_logger log = new test_logger();*/
    	
    	logger.trace("doGet\r\n");
    	
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
            logger.trace("buf===="+buf);
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = sis.read(buf);  
                logger.trace("buf===="+buf);
            }  
            sis.close();  
            bos.close();  
            os.close();  
        }
        logger.trace("end");
    	
/*    	logger.trace("entry");
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
        out.write("</html>");  */

    }  
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException
    {
    	System.out.println("post");
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
