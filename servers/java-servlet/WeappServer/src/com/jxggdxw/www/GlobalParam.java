package com.jxggdxw.www;

public class GlobalParam {
	
    private static final String GOODS_SAVE_FILE_TEST = "e:/program/TPSSoftware/servers/java-servlet/WeappServer/goods.xml";
    private static final String GOODS_SAVE_FILE_SERVER = "/usr/local/tomcat/webapps/WeappServer/pic/goods.xml";   
    
	//windows ���ļ���ŵ�ַ
	private static final String TEMP_DIR_TEST 	= "d:/temp/";
	private static final String PIC_DIR_TEST  	= "d:/video/";
	
	//linux ���ļ���ŵ�ַ
	private static final String TEMP_DIR_SERVER 	= "/usr/local/tomcat/webapps/WeappServer/temp/";
	private static final String PIC_DIR_SERVER  	= "/usr/local/tomcat/webapps/WeappServer/pic/";
	
	//url����ַ
	public  static final String PIC_URL_BASE	= "https://32906079.jxggdxw.com:8443/WeappServer/pic/";
	public static final String PIC_URL_FILE    = "/usr/local/tomcat/webapps/WeappServer/pic/url.txt";
	
	//��Ʒ��Ϣ�ַ�������
	public static final String STR_GOOD_NAME 	= "goodsname";
	public static final String STR_GOOD_PRICE	= "goodsprice";
	public static final String STR_GOOD_ABSTRACT = "goodsabstract";
	
	//�����ֳ���
	public static final String STR_COMMAND					= "command";
	public static final String STR_COMMAND_ALL_GOODS		= "getgoodsname";
	public static final String STR_COMMAND_GOOD_INFO		= "getgoodinfo";
	public static final String STR_COMMAND_GOODS_INFO		= "getgoodsinfo";
	public static final String STR_COMMAND_GOOD_NAME		= "name";
	
	//buf����
	public static final int MAX_TEMP_BUF_SIZE		= 1024 * 512;  //д���ô�С�Ļ���󣬴���Ӳ���С�
	public static final int MAX_FILE_SIZE 			= 50 * 1024 * 1024;
	
	//�ļ��洢��ַ����
	public static final String GOODS_SAVE_FILE = GOODS_SAVE_FILE_TEST;
	public static final String TEMP_DIR = TEMP_DIR_TEST; //Ҫ��������б��:temp/�������ļ�Ŀ¼,��Ҫ�ڴ����ֶ�����temp�ļ��У������ܱ���  
	public static final String PIC_DIR  = PIC_DIR_TEST;
	
/*	public static final String GOODS_SAVE_FILE = GOODS_SAVE_FILE_SERVER;
	public static final String TEMP_DIR = TEMP_DIR_SERVER; //Ҫ��������б��:temp/�������ļ�Ŀ¼,��Ҫ�ڴ����ֶ�����temp�ļ��У������ܱ���  
	public static final String PIC_DIR  = PIC_DIR_SERVER;*/
	
	private GlobalParam(){
		
	}

}
