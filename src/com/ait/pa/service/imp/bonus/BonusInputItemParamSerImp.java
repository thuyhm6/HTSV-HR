package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.pa.service.bonus.BonusInputItemParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemParamSerImp.java
 * @Description:
 * @Create date: 2012-1-14 下午02:12:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusInputItemParamSerImp implements BonusInputItemParamSer {

	Logger logger = Logger.getLogger(BonusInputItemParamSerImp.class);

	@Autowired
	private BonusInputItemParamDao bonusInputItemParamDao;

	/**
	 * 获取奖金输入项目参数个数（Gets the bonus item number input parameters）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaBonusInputItemParamCnt(HttpServletRequest request) {

		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = bonusInputItemParamDao
				.getPaBonusInputItemParamCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 查看奖金输入项目参数集合（View input parameter set bonus items）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewBonusInputItemParamList(HttpServletRequest request) {
		
		Map paramMap = new LinkedHashMap();
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		if (UiUtil.getPageNum(request) > 0){
			
			retrunList = bonusInputItemParamDao.viewBonusInputItemParamList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			
		}else{
			
			retrunList = bonusInputItemParamDao.viewBonusInputItemParamList(paramMap);
			
		}
		

		return retrunList;

	}
	
	/**
	 * 查看奖金输入项目参数集合（View input parameter set bonus items）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewBonusInputItemParamListForData(HttpServletRequest request) {
		
		Map paramMap = new LinkedHashMap();
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("ACTIVITY", "1");
		
		retrunList = bonusInputItemParamDao.viewBonusInputItemParamList(paramMap);
			
		
		

		return retrunList;

	}

	/**
	 * 添加奖金输入项目参数信息（Add a bonus item parameter information input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaBonusInputItemParamInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.bonusInputItemParamDao.addPaBonusInputItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 检查添加奖金输入项目参数信息，是否可添加（Check the add bonus input item parameter information, whether to add）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaBonusInputItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemParamDao
				.checkAddPaBonusInputItemParamInfo(paramMap);
	}

	/**
	 * 检查添加奖金输入项目参数信息，是否可删除（Check the add bonus input item parameter information, whether to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaBonusInputItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemParamDao
				.checkDeletePaBonusInputItemParamInfo(paramMap);
	}

	/**
	 * 删除奖金输入项目参数信息（Delete bonus input item parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemParamInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusInputItemParamDao
					.deletePaBonusInputItemParamInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 获取奖金输入项目参数信息（Gets the bonus item parameter information input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemParamInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusInputItemParamDao
				.getPaBonusInputItemParamInfo(paramMap);

		return returnObj;
	}

	/**
	 * 修改奖金输入项目参数信息（Modified bonus input item parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBonusInputItemParamInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.bonusInputItemParamDao.updatePaBonusInputItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 检查奖金输入项目参数信息（Check bonus item parameter information input by ParamItemNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkPaBonusInputItemParamByParamItemNo(
			HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemParamDao
				.checkPaBonusInputItemParamByParamItemNo(paramMap);
	}
}