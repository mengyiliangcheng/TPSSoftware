package com.jxggdxw.www;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseUtils implements DBInterface{

	public static final String DB_USER_NAME = "root";
	public static final String DB_USER_PWD  = "pp@265358";
	public static final String JDBC_URL="jdbc:mysql://localhost:3306/tps?useUnicode=true&characterEncoding=utf-8";    //JDBC��URL
	public static final String DABASE_URL = "jdbc:mysql://localhost:3306/elvis?useUnicode=true&characterEncoding=utf-8";
    private String tableName;
    private Connection conn;
    
	static String strClassName = DatabaseUtils.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    
    public DatabaseUtils(String tableName){
    	setTableName(tableName);
    }
    
    public DatabaseUtils(){
    	
    }

    public void setTableName(String tableName){
    	this.tableName = tableName;
    }
    
    public boolean checkDriver(){
        try{
            //����Class.forName()����������������
            Class.forName("com.mysql.jdbc.Driver");
            logger.trace("check driver ok ");
            return true;
        }catch(ClassNotFoundException e1){
        	logger.error("check driver failed " + e1.toString());
            return false;
        }
        
    }
    
    public boolean createDatabase(){
        //����DriverManager�����getConnection()���������һ��Connection����
       // Connection conn;
        try {
            conn = DriverManager.getConnection(JDBC_URL,DB_USER_NAME,DB_USER_PWD);
            //����һ��Statement����

            Statement stmt = conn.createStatement(); //����Statement����
            logger.trace("link to database success ");

            String sql = "create database " + tableName;
            logger.trace("sql " + sql.toString());
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        }catch (SQLException e){
        	logger.error("create database error " + e.toString());
        	return false;
        }
        logger.trace("create database success ");
    	return true;
    }
    
    public boolean createTable(){
    	
    	return false;
    }
    
	@Override
	public boolean insertTable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean queryTable() {
		// TODO Auto-generated method stub
		return false;
	}

    public static void main(String[] args){
    	
    }
    
    public Statement getDBStatement(){
    	Statement stmt;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL,DB_USER_NAME,DB_USER_PWD);
    		stmt = conn.createStatement();
    	}catch(SQLException e){
        	logger.error("get database statement error " + e.toString());
        	return null;
        }
    	return stmt;
    }
    
    private void selfTest(){
    	try{
            //����Class.forName()����������������
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("�ɹ�����MySQL������");
        }catch(ClassNotFoundException e1){
            System.out.println("�Ҳ���MySQL����!");
            e1.printStackTrace();
        }

        String url="jdbc:mysql://localhost:3306/mysql";    //JDBC��URL
        //����DriverManager�����getConnection()���������һ��Connection����
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,"root","pp@265358");
            //����һ��Statement����

            Statement stmt = conn.createStatement(); //����Statement����
            System.out.print("�ɹ����ӵ����ݿ⣡");

            String sql = "create database elvis";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

            url = "jdbc:mysql://localhost:3306/elvis?useUnicode=true&characterEncoding=utf-8";
            conn = DriverManager.getConnection(url,"root","pp@265358");
            stmt = conn.createStatement();

            stmt.executeUpdate("create table test(id int, name varchar(80))");

            //�������
            stmt.executeUpdate("insert into test values(1, '����')");
            stmt.executeUpdate("insert into test values(2, '����')");

            //��ѯ����
            ResultSet result = stmt.executeQuery("select * from test");
            while (result.next())
            {
                System.out.println(result.getInt("id") + " " + result.getString("name"));
            }

            //�ر����ݿ�
            result.close();
            stmt.close();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }





}
