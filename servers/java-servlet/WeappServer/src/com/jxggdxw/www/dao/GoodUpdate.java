package com.jxggdxw.www.dao;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jxggdxw.www.domain.Good;
import com.jxggdxw.www.domain.GoodPic;
import com.jxggdxw.www.domain.MysqlSessionFactory;
import com.jxggdxw.www.service.UploadService;
import com.jxggdxw.www.util.HibernateUtil;

public class GoodUpdate {
	
	static String strClassName = UploadService.class.getName();  
    static Logger logger = LogManager.getLogger(strClassName);
    
    public static void main(String[] args){
    	
    	Good good = new Good();
    	good.setGoodName("x6");
    	
    	GoodPic pic = new GoodPic();
    	pic.setPath("jksjdk.ico");
    	
    	good.getGoodPics().add(pic);
    	
    	//new GoodUpdate().saveUrl(good);
    	
    	new GoodUpdate().deleteGood(good);
    }
	
	public boolean saveUrl(Good good){
		
		logger.trace("save url");
		
		Good filter = new Good(true);
		filter.setGoodName(good.getGoodName());
		Good dbGood = GoodTable.getOneGood(filter);
		if(dbGood == null){
			logger.trace("save url fail ,GoodTable.getOneGood null");
			return false;
		}
		
		Session session = MysqlSessionFactory.getSessionFactory().openSession();

        //creating transaction object  
        Transaction t=session.beginTransaction();  
        
        Good goodRet = (Good)session.load(Good.class, dbGood.getId());
        logger.trace("save url id =  "+ goodRet.getId() + "name = " +goodRet.getGoodName());
        
        for(GoodPic pci : good.getGoodPics()){
			logger.trace("save url= "+ pci.getPath());
			pci.setGoodinfo(goodRet);
			goodRet.getGoodPics().add(pci);
        }
        
       // session.save(goodRet);
        
        t.commit();
        session.close();
        
        logger.trace("save url succ");
		
		return true;
		
	}
	
	public boolean deleteGood(String name){
		
		Good good = new Good();
		good.setGoodName(name);
		return deleteGood(good);
		
	}
	
	public boolean deleteGood(Good good){
		
/*		
 * 		不能用hql语句删除
 * 		String hql;
		hql = "delete Good where goodName='";
		hql += good.getGoodName() + "'";
		
		logger.trace("delete hql=" + hql);
		
		HibernateUtil.createDelete(hql);*/
		
		Good fileter = new Good(true);
		fileter.setGoodName(good.getGoodName());
		
		/*没找到直接返回*/
		Good goodinfo = GoodTable.getOneGood(fileter);
		if(goodinfo == null){
			return true;
		}
		
		/*找到就删除*/
		Session session = null;
		Transaction ts = null;
		try{
			session = MysqlSessionFactory.getSessionFactory().openSession();
			
	        //creating transaction object  
	        ts=session.beginTransaction();      
	        session.delete(goodinfo);
	        ts.commit();
        }catch(Exception e){
			if(ts != null){
				ts.rollback();
				
				throw new RuntimeException(e.getMessage());
			}
        }finally{
		
        	session.close();
        }
		logger.trace("delete succ");
		return true;
	}
	
	public boolean updateGood(Good good){
		
		String hql;
		hql = "update Good set ";
		if(good.getGoodPrice().length() > 0 && good.getGoodPrice() != null){
			hql += "goodPrice ='" +good.getGoodPrice() + "' , ";
		}
		if(good.getGoodAbs().length() > 0 && good.getGoodAbs() != null){
			hql += "goodAbs ='" + good.getGoodAbs() + "' ";
		}
		
		hql += " where goodName='" + good.getGoodName() + "'";
		
		return HibernateUtil.createUpdate(hql);
	}
	
	public boolean saveGood(Good good){
		
		Good filter = new Good(true);
		
		filter.setGoodName(good.getGoodName());
		String str = GoodTable.queryNum(filter);
		if(str == null){
			return false;
		}
		Integer num = new Integer(str);
		if(num == 0 ){
			HibernateUtil.createSave(good);   //insert插入
		}else if(num == 1){
			return updateGood(good);          //更新
		}else{
			return false;					  //出错
		}
		
		return true;
	}

}
