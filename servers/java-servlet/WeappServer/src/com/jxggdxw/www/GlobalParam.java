package com.jxggdxw.www;

public class GlobalParam {
	
    private static final String GOODS_SAVE_FILE_TEST = "e:/program/TPSSoftware/servers/java-servlet/WeappServer/goods.xml";
    private static final String GOODS_SAVE_FILE_SERVER = "/usr/local/tomcat/webapps/WeappServer/pic/goods.xml";   
    
	//windows 端文件存放地址
	private static final String TEMP_DIR_TEST 	= "d:/temp/";
	private static final String PIC_DIR_TEST  	= "d:/video/";
	
	//linux 端文件存放地址
	private static final String TEMP_DIR_SERVER 	= "/usr/local/tomcat/webapps/WeappServer/temp/";
	private static final String PIC_DIR_SERVER  	= "/usr/local/tomcat/webapps/WeappServer/pic/";
	
	//url基地址
	public  static final String PIC_URL_BASE	= "https://32906079.jxggdxw.com:8443/WeappServer/pic/";
	public static final String PIC_URL_FILE    = "/usr/local/tomcat/webapps/WeappServer/pic/url.txt";
	
	//商品信息字符串常量
	public static final String STR_GOOD_NAME 	= "goodsname";
	public static final String STR_GOOD_PRICE	= "goodsprice";
	public static final String STR_GOOD_ABSTRACT = "goodsabstract";
	
	//命令字常量
	public static final String STR_COMMAND					= "command";
	public static final String STR_COMMAND_ALL_GOODS		= "getgoodsname";
	public static final String STR_COMMAND_GOOD_INFO		= "getgoodinfo";
	public static final String STR_COMMAND_GOODS_INFO		= "getgoodsinfo";
	public static final String STR_COMMAND_GOOD_NAME		= "name";
	
	//buf常量
	public static final int MAX_TEMP_BUF_SIZE		= 1024 * 512;  //写满该大小的缓存后，存入硬盘中。
	public static final int MAX_FILE_SIZE 			= 50 * 1024 * 1024;
	
	//文件存储地址常量
	public static final String GOODS_SAVE_FILE = GOODS_SAVE_FILE_TEST;
	public static final String TEMP_DIR = TEMP_DIR_TEST; //要在最后加上斜杠:temp/，缓存文件目录,需要在磁盘手动建立temp文件夹，否则不能保存  
	public static final String PIC_DIR  = PIC_DIR_TEST;
	
/*	public static final String GOODS_SAVE_FILE = GOODS_SAVE_FILE_SERVER;
	public static final String TEMP_DIR = TEMP_DIR_SERVER; //要在最后加上斜杠:temp/，缓存文件目录,需要在磁盘手动建立temp文件夹，否则不能保存  
	public static final String PIC_DIR  = PIC_DIR_SERVER;*/
	
	private GlobalParam(){
		
	}

}
