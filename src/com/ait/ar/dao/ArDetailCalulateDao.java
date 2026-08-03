package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArDetailCalulateDao.java
 * @Description: implement Class ArDetailCalulateDaoImpl.java
 * @Create date: 2012-2-7 下午12:01:50
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArDetailCalulateDao {
	 @SuppressWarnings("unchecked")
	 public  List getArSupervisorList(Object object);
	 @SuppressWarnings("unchecked")
     public String detailCalculate(LinkedHashMap object) ;
	 @SuppressWarnings("unchecked")
     public String detailShiHouCalculate(LinkedHashMap object) ;
	 @SuppressWarnings("unchecked")
	 public  List getardetailsendview(Object object);
	 @SuppressWarnings("unchecked")
     public String detailLastMonthCalculate(LinkedHashMap object) ;
	 
}
