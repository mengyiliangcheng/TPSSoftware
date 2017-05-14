package com.jxggdxw.www;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ToolUtils {
	
	static String strClassName = ToolUtils.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    public static void main(String[] args){
    	//boolean b;
		//b = makeDirs("D:\\temp\\","aodi");
		//logger.trace("b = " + b);
    }
	
	public ToolUtils(){
		
	}
	
    public boolean makeDirs(String filePath,String folderName) {
     
        if (folderName == null || folderName.isEmpty()) {
            return false;
        }
        
        filePath += folderName;
        logger.trace("filepath = " + filePath);
 
        File folder = new File(filePath);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

}
