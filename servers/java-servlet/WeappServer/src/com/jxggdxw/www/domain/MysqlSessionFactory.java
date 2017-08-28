package com.jxggdxw.www.domain;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

final public class MysqlSessionFactory {
	
	private static SessionFactory factory = null;
	
	private MysqlSessionFactory(){
		
	}
	
	static{
		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		return factory;
	}

}
