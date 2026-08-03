package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArEatCardAssociateDao;
import com.ait.ar.service.ArEatCardAssociateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @author Administrator 业务接口实现类
 */
@Service
public class ArEatCardAssociateSerImp implements ArEatCardAssociateSer {
	Logger logger = Logger.getLogger(ArCardAssociateSerImp.class);
	@Autowired
	private ArEatCardAssociateDao arEatCardAssociateDao;

	public ArEatCardAssociateDao getArEatCardAssociateDao() {
		return arEatCardAssociateDao;
	}

	public void setArEatCardAssociateDao(
			ArEatCardAssociateDao arEatCardAssociateDao) {
		this.arEatCardAssociateDao = arEatCardAssociateDao;
	}

	/**
	 * 查看卡号列表(get AttendanceKeeper List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public List getEatCardAssociateList(HttpServletRequest request) {

		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = arEatCardAssociateDao.getEatCardAssociateList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		} else {
			retrunList = arEatCardAssociateDao
					.getEatCardAssociateList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 取考勤员数量(get CardAssociate Cnt)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public int getEatCardAssociateCnt(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return arEatCardAssociateDao.getEatCardAssociateCnt(paramMap);
	}
	/**
	 * 修改保存(update CardAssociate Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int updateEatCardAssociateInfo(HttpServletRequest request){
		
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		List<LinkedHashMap<String, Object>> paraList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(arDetailInfoList != null){
			for(LinkedHashMap lmap : arDetailInfoList){
				lmap.put("CPNY_ID", admin.getCpnyId());
				lmap.put("CREATE_BY", admin.getPersonId());
				paraList.add(lmap);
			}
		}
		
		try {
			
			arEatCardAssociateDao.updateEatCardAssociateInfo(paraList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
}
