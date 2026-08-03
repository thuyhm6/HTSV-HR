package com.ait.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.PageStructureDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.service.PageStructureSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;


/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName PageStructureSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-4-19 am 12:00:22
 * @version 5.0
 * 
 */
@Service
public class PageStructureSerImpl implements PageStructureSer {
	
	@Autowired
	private PageStructureDao pageStructureDao;
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPageStructureList(HttpServletRequest request) {
		LinkedHashMap object=ObjectBindUtil.getRequestParamData(request);
		return this.pageStructureDao.getPageStructureList(object);
	}

	@Override
	public int getPageStructureListCnt(HttpServletRequest request) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List getPsDataList(HttpServletRequest request){
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		return this.pageStructureDao.getPsDataList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemListByTableName(HttpServletRequest request) {
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		return this.pageStructureDao.retrieveReportItemListByTableName(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int addNewAliasInfo(HttpServletRequest request){
		List list = new ArrayList();
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String checkedNum=request.getParameter("checkedNum");
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			for (int i = 0; i < Integer.valueOf(checkedNum).intValue(); i++) {
				 LinkedHashMap map = new LinkedHashMap();
				 String searchNewAlias = paramMap.get("searchNewAlias"+i).toString();
				 map.put("ITEM_ID", searchNewAlias);
				 map.put("TYPE", paramMap.get("newAliasTypeAlias"+searchNewAlias).toString());
				 map.put("TABLE_NAME", paramMap.get("newAliasTableName"+searchNewAlias).toString());
				 map.put("ORDER_NO", paramMap.get("newAliasOrderNo"+searchNewAlias).toString());
				 map.put("ITEM_NO", paramMap.get("newAliasItemNo"+searchNewAlias).toString());
				 map.put("RT_NO", paramMap.get("RT_NO").toString());
				 map.put("interCpnyID", paramMap.get("interCpnyID").toString());
				 list.add(map);
			}
			this.pageStructureDao.addNewAliasInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(HttpServletRequest request){
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		return this.pageStructureDao.retrieveReportItemList(paramMap);
	}
	@SuppressWarnings("unchecked")
	public int updateNewAliasInfo(HttpServletRequest request){
		int result=0;
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String deleteItemNos=request.getParameter("delete_item_no");
			if(deleteItemNos!=null){
				List list = new ArrayList();
				String[] deleteItemNo=deleteItemNos.split(",");
				String rtNo = request.getParameter("RT_NO");
				for(int i=0;i<deleteItemNo.length;i++){
					LinkedHashMap map = new LinkedHashMap();
					map.put("REF_ITEM_NO", deleteItemNo[i]);
					map.put("interCpnyID", admin.getCpnyId());
					map.put("RT_NO", rtNo);
					list.add(map);
				}
				this.pageStructureDao.deleteNewAliasInfo(list);
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
		
	}
	/**
	 * 页面构造页面提交
	 */
	@SuppressWarnings("unchecked")
	public int saveAliasInfo(HttpServletRequest request,List langList){
		int result=0;
		try {
			String update_rt_no=request.getParameter("update_rt_no");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			//修改操作
			if(update_rt_no!=null){//修改列
				String[] update_rt_nos = update_rt_no.split(",");
				for(int i=0;i<update_rt_nos.length;i++){
					LinkedHashMap map = new LinkedHashMap();
					map.put("NO", update_rt_nos[i]);
			    	map.put("CREATED_BY", admin.getAdminID());
					
				    for(int j=0;j<langList.size();j++){
				    	String proName =  request.getParameter("proName_"+((Map)(langList.get(j))).get("LANGUAGE")+"_"+update_rt_nos[i]);
				    	map.put("proName_"+((Map)(langList.get(j))).get("LANGUAGE"), proName);
				    }
				    this.syLanguageDao.updateSyGlobalName(map);	
				}
			}
			//添加操作
			String addNum = request.getParameter("add_table_index");
			if(addNum!=null&&!addNum.equals("")){
				int addIndex = Integer.parseInt(addNum);
				List list = new ArrayList();
				for(int i=0;i<addIndex;i++){
					String viewModel = request.getParameter("VIEW_MODEL_"+i);
					String reportType = request.getParameter("REPORT_TYPE_"+i);
					String rtNo = ""; 
					if(viewModel!=null){
						LinkedHashMap map = new LinkedHashMap();
						map.put("CREATED_BY", admin.getAdminID());
						for(int j=0;j<langList.size();j++){
							String proName = request.getParameter("proName_"+((Map)(langList.get(j))).get("LANGUAGE")+"_"+i);
							map.put("proName_"+((Map)(langList.get(j))).get("LANGUAGE"), proName);
						}
						//对国际化进行操作
						LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(map);
						rtNo = object.get("NO").toString();
						
						String inputItemNo = request.getParameter("inputItemNo_"+i);
						String inputItemID = request.getParameter("inputItemID_"+i);
						if(inputItemNo!=null&&!inputItemNo.equals("")){
							String[] itemNos=inputItemNo.split(",");
							String[] itemIds=inputItemID.split(",");
							List itemList=new ArrayList();
							for(int k=0;k<itemNos.length;k++){
								LinkedHashMap itemMap = new LinkedHashMap();
								itemMap.put("RT_NO", rtNo);
//								itemMap.put("TABLE_NAME", reportType.equals("1")?"PA_HISTORY_"+admin.getCpnyId():"AR_HISTORY_"+admin.getCpnyId());
								itemMap.put("TABLE_NAME", reportType.equals("1")?"PA_SUMMARY_"+admin.getCpnyId():"AR_SUMMARY_"+admin.getCpnyId());
								itemMap.put("ITEM_NO", itemNos[k]);
								itemMap.put("ITEM_ID", itemIds[k]);
								itemMap.put("ORDER_NO", k+1);
								itemMap.put("CREATED_BY", admin.getAdminID());
								itemMap.put("interCpnyID", admin.getCpnyId());
								itemList.add(itemMap);
							}
							this.pageStructureDao.addNewAliasInfo(itemList);
						}
					}
					LinkedHashMap obj = new LinkedHashMap();
					obj.put("RT_NO", rtNo);
					obj.put("MENU_NO", request.getParameter("MENU_NO"));
					obj.put("REPORT_TYPE", reportType);
					obj.put("VIEW_MODEL", viewModel);
					obj.put("interCpnyID", admin.getCpnyId());
					obj.put("CREATED_BY", admin.getAdminID());
					list.add(obj);
				}
				this.pageStructureDao.addAliasInfo(list);
			}
			//删除操作
			String del_rt_no = request.getParameter("del_rt_no");
			if(del_rt_no!=null&&!del_rt_no.equals("")){//如果做了删除操作
				List delList = new ArrayList();
				String[] delArr = del_rt_no.split(",");
				for(int i=0;i<delArr.length;i++){
					LinkedHashMap map = new LinkedHashMap();
					map.put("RT_NO", delArr[i]);
					map.put("interCpnyID", admin.getCpnyId());
					map.put("NO", delArr[i]);
					this.syLanguageDao.deleteSyGlobalName(map);
					delList.add(map);
				}
				this.pageStructureDao.deleteAliasInfo(delList);
			}
			result=1;
		} catch (Exception e) {
			result=0;
		}
		return result;
	}
}
