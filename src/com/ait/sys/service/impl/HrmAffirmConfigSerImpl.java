package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.HrmAffirmConfigDao;
import com.ait.sys.service.HrmAffirmConfigSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HrmAffirmConfigSerImpl implements HrmAffirmConfigSer {

	@Autowired
	private HrmAffirmConfigDao hrmAffirmConfigDao;
	
	/**
	 * 查询人事令页面设置参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrmAffirmConfigDistinctList(HttpServletRequest request) throws Exception {
		//页面提交数据
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		//表头信息
		List distinctList=this.hrmAffirmConfigDao.getHrmAffirmConfigDistinctList(paramMap);
		return distinctList;
	}

	/**
	 * 查询人事令页面设置参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrmAffirmConfigTransCodeList(HttpServletRequest request)throws Exception {
		//页面提交数据
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		//发令类型
		List transCodeList=this.hrmAffirmConfigDao.getHrmAffirmConfigTransCodeList(paramMap);
		return transCodeList;
	}

	/**
	 * 获得上次保存的配置记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPreHrmAffirmConfigRecordList(HttpServletRequest request,List hrmAffirmConfigTransCodeList)
			throws Exception {
		//页面提交数据
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		//法人"C02"使用下拉选框选择时有效
		if("onChangeEvent".equals(StringUtil.checkNull(request.getParameter("FLAG")))){
			paramMap.put("interCpnyID", StringUtil.checkNull(request.getParameter("COMPANY_ID")));
		}
		//获得记录
		LinkedHashMap preHrmAffirmConfigRecordList = new LinkedHashMap();
		for(int i = 0; i < hrmAffirmConfigTransCodeList.size(); i++){
			paramMap.put("TRANS_CODE", ((Map)hrmAffirmConfigTransCodeList.get(i)).get("CODE_NO"));
			List tempList = this.hrmAffirmConfigDao.getPreHrmAffirmConfigRecordList(paramMap);
			if(!tempList.isEmpty()){
				preHrmAffirmConfigRecordList.put(paramMap.get("TRANS_CODE"), tempList);
			}else{
				preHrmAffirmConfigRecordList.put(paramMap.get("TRANS_CODE"), new LinkedHashMap());
			}
		}
		return preHrmAffirmConfigRecordList;
	}
	
	/**
	 * 保存人事令配置
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer saveHrmAffirmConfig(HttpServletRequest request)throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//页面提交数据
		LinkedHashMap paramMap=ObjectBindUtil.getRequestParamData(request);
		//获得页面选择的法人
		paramMap.put("CPNY_ID", request.getParameter("COMPANY_ID") != null ? request.getParameter("COMPANY_ID") :admin.getCpnyId());
		try {
			paramMap.put("TRANS_CODE", request.getParameter("TRANS_CODE") == null ? null : request.getParameter("TRANS_CODE"));
			Integer resultCount=this.hrmAffirmConfigDao.selectHrmAffirmConfigByCpnyId(paramMap);
			if(resultCount!= null && resultCount> 0){
				this.hrmAffirmConfigDao.deleteHrmAffirmConfigByCpnyId(paramMap);
			}
			//查询获得发令类型编号
			List transCodeNoList=this.hrmAffirmConfigDao.getHrmAffirmConfigTransCodeNoList(paramMap);
			//查询获得表头信息编号
			List distinctFiledList=this.hrmAffirmConfigDao.getHrmAffirmConfigDistinctList(paramMap);
			List fieldList=new ArrayList();
			for(int x = 0;x < distinctFiledList.size(); x++){
				String distinctStr=distinctFiledList.get(x).toString();
				String str=distinctStr.substring(distinctStr.indexOf("=") + 1,distinctStr.indexOf(","));
				fieldList.add(str);
			}
			String paramNumStr=request.getParameter("paramNum");
			int paramNum=Integer.parseInt(paramNumStr);
			String transCodeNostr=null;
			String transCodeNo=null;
			LinkedHashMap distinctList=new LinkedHashMap();
			for(int i=0;i<transCodeNoList.size();i++){
				for(int j=0;j<paramNum;j++){
					transCodeNostr=transCodeNoList.get(i).toString();
					transCodeNo=transCodeNostr.substring(transCodeNostr.indexOf("=")+1,transCodeNostr.indexOf("}"));
					distinctList.put("CPNY_ID", request.getParameter("COMPANY_ID"));
					distinctList.put("DISTINCT_FIELD", fieldList.get(j));
					distinctList.put("TRANS_CODE", transCodeNo);
					String look=request.getParameter("look_"+transCodeNo+"_"+(j+1));
					String update=request.getParameter("update_"+transCodeNo+"_"+(j+1));
					if(look==null){
						distinctList.put("TRANS_CONFIG_FLAG", "0");//"0"表示不显示
					}else if(look!=null && update==null){
						distinctList.put("TRANS_CONFIG_FLAG", "1");//"1"表示显示不修改
					}else{
						distinctList.put("TRANS_CONFIG_FLAG", "2");//"2"表示显示且修改
					}
					String search=request.getParameter("search_"+transCodeNo+"_"+(j+1));
					if(search==null){
						distinctList.put("TRANS_SEARCH_FLAG", "0");//"0"表示查询不显示
					}else{
						distinctList.put("TRANS_SEARCH_FLAG", "1");//"1"表示查询显示
					}
					distinctList.put("CREATE_BY", admin.getPersonId() !=null ? admin.getPersonId() : admin.getUsername());
					distinctList.put("ACTIVITY", "1");
					this.hrmAffirmConfigDao.saveHrmAffirmConfig(distinctList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
