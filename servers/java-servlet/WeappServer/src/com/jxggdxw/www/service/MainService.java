package com.jxggdxw.www.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jxggdxw.www.GlobalParam;

@WebServlet("/MainService")
public class MainService extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//set logger设置日志记录
	static String strClassName = MainService.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.trace("main doGet start"); 
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.trace("main doPost start"); 
		
/*		RequestDispatcher dispatcher =  request.getRequestDispatcher("/DownloadService");
		dispatcher.forward(request, response);*/
		
    	String tag = request.getParameter(GlobalParam.STR_TARGET);
    	logger.trace("target: " + tag);
		
    	if(null == tag){
    		return ;
    	}
    	
    	RequestDispatcher dispatcher = null;
    	
    	switch(tag){
    		
    	case GlobalParam.STR_TARGET_DOWNLOAD:
    		dispatcher =  request.getRequestDispatcher("/DownloadService");
    		break;
    	case GlobalParam.STR_TARGET_UPLOAD:
    		dispatcher = request.getRequestDispatcher("UploadService");
    		break;
    		
    	default:
    		PrintWriter pw = response.getWriter();
    		pw.write("error target name");
    		logger.trace("error target name");
    		return ;
    	
    	}
    	
    	dispatcher.forward(request, response);
    	
    	return ;
		
	}
	

}
