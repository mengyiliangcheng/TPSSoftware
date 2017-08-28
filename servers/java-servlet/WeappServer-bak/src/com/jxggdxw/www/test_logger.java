package com.jxggdxw.www;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class test_logger {
	static String strClassName = main.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
	public static void main(String[] args){
		System.out.println("hello");;
		
		String str = new String("你好");
		
		logger.trace(str);
		
    	logger.trace("entry");
    	logger.warn("除数不能为0.");
    	logger.error("除数不能为0.");
    	logger.trace("exit");
	}
	
	public test_logger(){
/*    	logger.trace("entry");
    	logger.warn("除数不能为0.");
    	logger.error("除数不能为0.");
    	logger.trace("exit");*/
	}

}
