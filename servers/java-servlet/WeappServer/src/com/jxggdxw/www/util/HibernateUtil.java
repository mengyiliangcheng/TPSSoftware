package com.jxggdxw.www.util;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateUtil {

	public static boolean createSave(Object obj){
		Session session = null;
		Transaction ts = null;
		
		try {
			
			session = MysqlSessionFactory.getSessionFactory().openSession();
			ts = session.beginTransaction();
			session.save(obj);
			
			ts.commit();
			
		} catch (Exception e) {
			if(ts != null){
				ts.rollback();
				
				throw new RuntimeException(e.getMessage());
			}
			// TODO: handle exception
		}finally{
			session.close();
		}
		
		return true;
	}
	
	public static boolean createDelete(String hql){
		Session session = null;
		Transaction ts = null;
		
		try {
			
			session = MysqlSessionFactory.getSessionFactory().openSession();
			ts = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			
			ts.commit();
			
		} catch (Exception e) {
			if(ts != null){
				ts.rollback();
				
				throw new RuntimeException(e.getMessage());
			}
			// TODO: handle exception
		}finally{
			session.close();
		}
		
		return true;
	}
	
	public static List createQuery(String hql){
		
		Session session = null;
		Transaction ts = null;
		List list = null;
		
		try {
			
			session = MysqlSessionFactory.getSessionFactory().openSession();
			ts = session.beginTransaction();
			list = session.createQuery(hql).list();
			
			ts.commit();
			
		} catch (Exception e) {
			if(ts != null){
				ts.rollback();
				
				throw new RuntimeException(e.getMessage());
			}
			// TODO: handle exception
		}finally{
			session.close();
		}
		
		return list;
	}
	
	public static List createQuery2(String hql){
		
		Session session = null;
		Transaction ts = null;
		List list = null;
		
		try {
			
			session = MysqlSessionFactory.getSessionFactory().getCurrentSession();
			ts = session.beginTransaction();
			list = session.createQuery(hql).list();
			
			ts.commit();
			
		} catch (Exception e) {
			if(ts != null){
				ts.rollback();
				
				throw new RuntimeException(e.getMessage());
			}
			// TODO: handle exception
		}finally{
			//session.close();
			//MysqlSessionFactory.getSessionFactory()
		}
		
		return list;
	}

	public static boolean createUpdate(String hql){
		
		Session session = null;
		Transaction ts = null;
		boolean ret = true;
		
		try {
			
			session = MysqlSessionFactory.getSessionFactory().openSession();
			ts = session.beginTransaction();
			Query query = session.createQuery(hql);
			
			query.executeUpdate();
			
			ts.commit();
			
		} catch (Exception e) {
			if(ts != null){
				ts.rollback();
				ret = false;
				
				throw new RuntimeException(e.getMessage());
			}
			// TODO: handle exception
		}finally{
			session.close();
		}
		
		return ret;
	}
}
