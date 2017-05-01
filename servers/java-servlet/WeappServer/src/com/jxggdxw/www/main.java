package com.jxggdxw.www;

import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@WebServlet(name="MainServlet")

public class main extends HttpServlet {

	private static final long serialVersionUID = 1L;
    //private static Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws IOException, ServletException { 
    	/*test_logger log = new test_logger();*/
    	
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
        out.write("helloworld");  
        out.write("\r\n");  
        out.write("</H1>\r\n");  
        out.write("</body>\r\n");  
        out.write("</html>");  
        /*
    	String info = request.getParameter("info");
    	PrintWriter out = response.getWriter();
    	out.write("<html>");
    	out.write("<head><title>TITLE</title></head>");
    	out.write("<body>");
    	out.write("<h1>"+info+"</h1>");
    	out.write("</body>");
    	out.write("</html>");
    	out.close();
    	System.out.println("get");
    	*/
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
