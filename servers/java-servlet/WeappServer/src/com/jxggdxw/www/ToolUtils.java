package com.jxggdxw.www;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ToolUtils {
	
	static String strClassName = ToolUtils.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    
    public static void main(String[] args){
    	boolean b;
    	ToolUtils tool = new ToolUtils();
		b = tool.DeleteFolder("D:\\temp\\","aodi");
		logger.trace("b = " + b);
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
        System.setProperty("sun.jnu.encoding","utf-8");
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
    
    public boolean DeleteFolder(String path,String folderName){
    	return deleteDirectory(path+folderName);
    }
    
    /** 
     *  根据路径删除指定的目录或文件，无论存在与否 
     *@param sPath  要删除的目录或文件 
     *@return 删除成功返回 true，否则返回 false。 
     */  
    public boolean DeleteFolder(String sPath) {  
        //flag = false;  
        File file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) {  // 不存在返回 false  
            return true;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) {  // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else {  // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
            }  
        }  
    }
    
    /** 
     * 删除单个文件 
     * @param   sPath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
    public boolean deleteFile(String sPath) {  
        //flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            return true;  
        }  
        return false;  
    }  
    

    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    } 

}
