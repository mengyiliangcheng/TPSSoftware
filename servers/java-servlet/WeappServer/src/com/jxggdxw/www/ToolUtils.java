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
     *  ����·��ɾ��ָ����Ŀ¼���ļ������۴������ 
     *@param sPath  Ҫɾ����Ŀ¼���ļ� 
     *@return ɾ���ɹ����� true�����򷵻� false�� 
     */  
    public boolean DeleteFolder(String sPath) {  
        //flag = false;  
        File file = new File(sPath);  
        // �ж�Ŀ¼���ļ��Ƿ����  
        if (!file.exists()) {  // �����ڷ��� false  
            return true;  
        } else {  
            // �ж��Ƿ�Ϊ�ļ�  
            if (file.isFile()) {  // Ϊ�ļ�ʱ����ɾ���ļ�����  
                return deleteFile(sPath);  
            } else {  // ΪĿ¼ʱ����ɾ��Ŀ¼����  
                return deleteDirectory(sPath);  
            }  
        }  
    }
    
    /** 
     * ɾ�������ļ� 
     * @param   sPath    ��ɾ���ļ����ļ��� 
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false 
     */  
    public boolean deleteFile(String sPath) {  
        //flag = false;  
        File file = new File(sPath);  
        // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            return true;  
        }  
        return false;  
    }  
    

    
    /** 
     * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ� 
     * @param   sPath ��ɾ��Ŀ¼���ļ�·�� 
     * @return  Ŀ¼ɾ���ɹ�����true�����򷵻�false 
     */  
    public boolean deleteDirectory(String sPath) {  
        //���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //ɾ���ļ����µ������ļ�(������Ŀ¼)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //ɾ�����ļ�  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //ɾ����Ŀ¼  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //ɾ����ǰĿ¼  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    } 

}
