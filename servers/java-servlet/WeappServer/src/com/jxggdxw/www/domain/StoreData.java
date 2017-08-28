package com.jxggdxw.www.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.jxggdxw.www.service.DownloadService;
import com.jxggdxw.www.util.HibernateUtil;

public class StoreData {
	
	static String strClassName = StoreData.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//test();
      // save();
       //update();
       //delete();
		//query();
		//queryObj();
		//querynum();
		queryOrder();
		logger.fatal("main.......");
	}
	
	public static void showResultByPage(int pageSize){
		
		int pageNow = 1;
		
		int pageCount = 1;
		int rowCount = 1;
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			Query<Good> query = session.createQuery("select count(*) from Good");
			 
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		
	}

	public static void querynum() {
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			Query<Good> query = session.createQuery("select count(*) from Good where goodName='x5'  ");
			List list = query.list();
			System.out.println("size=" + list.size());
			for(int i = 0;i < list.size();i++)
			{
				Object obj = (Object)list.get(i);
				System.out.println(obj.toString());
				
			}
			 
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
	}

	public static void queryObj() {
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			
			/*查询一项*/
			Query query = session.createQuery("select goodName from Good");
			List list = query.list();
			System.out.println("size=" + list.size());
			for(int i = 0;i < list.size();i++)
			{
				Object obj = (Object)list.get(i);
				System.out.println(obj.toString());
				
			}
			
			/*查询多项,查询出来的是对象数组*/
			query = session.createQuery("select goodName,goodPrice from Good");
			list = query.list();
			System.out.println("size=" + list.size());
			for(int i = 0;i < list.size();i++)
			{
				Object[] obj = (Object[])list.get(i);
				System.out.println(obj[0].toString() + " " + obj[1].toString());
				
			}
			 
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
	}
	
	static void query(){
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			Query<Good> query = session.createQuery("from Good where goodName='x5'  ");
			List<Good> list = query.list();
			for(Good g : list){
				System.out.println(g.getGoodName() + " " + g.getGoodPrice());
			}
			 
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		
	}
	
	static void standard(){
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		
	}
	
	static void test(){
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  
        
        Good good = (Good)session.load(Good.class, 1);
        
         for(Iterator<?> i =  good.getGoodPics().iterator();i.hasNext();){
        	 GoodPic pic = (GoodPic)i.next();
        	 System.out.println(" " + pic.getPath());
         }
        
        session.flush();
        
        t.commit();
        session.close();
	}
	
	static void delete(){
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  
        Good good = (Good)session.load(Good.class, 2);
        
        session.delete(good);
        
        t.commit();
        session.close();
	}
	
	static void update(){
		
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  
        
        Good good = (Good)session.load(Good.class, 2);
        good.setGoodName("x6");
        
        t.commit();
        session.close();
	}
	
	static void queryOrder(){
		
		String hql;
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			List<Good> list = session.createQuery("from Good order by goodPrice ").setFirstResult(0).setMaxResults(6).list();
			for(Good good : list){
				System.out.println(good.getGoodName() + " " + good.getGoodPrice());
			}
			
		}catch(Exception e){
			if(ts != null){
				ts.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		
	}
	

	static void save()
	{
		 
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  

        Good good = new Good();
        good.setGoodPrice("200");
        good.setGoodName("x7");
        
        GoodModel model = new GoodModel();
        model.setGood(good);
        model.setModel("6t");
        model.setPrice("2000");
        good.getGoodModels().add(model);
        
        GoodModel model2 = new GoodModel();
        model2.setGood(good);
        model2.setModel("7t");
        model2.setPrice("3000");
        good.getGoodModels().add(model2);
        
        GoodPic pic = new GoodPic();
        pic.setInfo("dddsss");
        pic.setPath("/pic/xxx.jpg");
        pic.setGoodinfo(good);
        good.getGoodPics().add(pic);
        
        
        
        session.save(good);
        
        session.flush();

        t.commit();             //transaction is committed  
        
        
        session.close();  

       
        System.out.println("successfully saved");  
	}

}
