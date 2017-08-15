package com.jxggdxw.www.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class StoreData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//test();
       //save();
       //update();
       //delete();
		query();
	}
	
	static void query(){
		Session session = MysqlSessionFactory.getSessionFactory().openSession();
		Transaction ts = null;
		
		try{
			ts = session.beginTransaction();
			Query query = session.createQuery("from Good where goodName='x5'  ");
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
        
         for(Iterator i =  good.getGoodPics().iterator();i.hasNext();){
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
	

	static void save()
	{
		 
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  

        Good good = new Good();
        good.setGoodPrice("200");
        good.setGoodName("x5");
        
        GoodModel model = new GoodModel();
        model.setGood(good);
        model.setModel("6t");
        model.setPrice("2000");
        good.getGoodModels().add(model);
        
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
