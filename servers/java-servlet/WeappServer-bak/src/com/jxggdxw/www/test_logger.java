package com.jxggdxw.www;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class test_logger {
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
	public static void main(String[] args){
		System.out.println("hello");;
		
		String str = new String("���");
		
		logger.trace(str);
		
    	logger.trace("entry");
    	logger.warn("��������Ϊ0.");
    	logger.error("��������Ϊ0.");
    	logger.trace("exit");
	}
	
	public test_logger(){
/*    	logger.trace("entry");
    	logger.warn("��������Ϊ0.");
    	logger.error("��������Ϊ0.");
    	logger.trace("exit");*/
	}

}
