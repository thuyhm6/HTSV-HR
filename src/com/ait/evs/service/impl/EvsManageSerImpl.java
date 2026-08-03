package com.ait.evs.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.evs.dao.EvsManageDao;
import com.ait.evs.service.EvsManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class EvsManageSerImpl implements EvsManageSer {
	Logger logger = Logger.getLogger(EvsManageSerImpl.class);

	@Autowired
	private EvsManageDao evsManageDao;

	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewEvsInfoList(HttpServletRequest request,String target) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
        SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
		Calendar c = Calendar.getInstance();    
		if(request.getParameter("firstFlag")==null&&(request.getParameter("seach_EVS_YEAR")==""||request.getParameter("seach_EVS_YEAR")==null )){
			//获取当前年：
			String year = format.format(c.getTime());
			param.put("EVS_YEAR",year);
		}
		return evsManageDao.viewEvsList(param,target);
	}

	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewEvsInfoList(Map param,String target) {
		return evsManageDao.viewEvsList(param,target);
	}

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewEvsInfoCnt(HttpServletRequest request,String target) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		return evsManageDao.viewEvsCnt(param,target);
	}

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewEvsInfoCnt(Map param,String target) {
		return evsManageDao.viewEvsCnt(param,target);
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.evsManageDao.addEvsInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addHrEvsInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.evsManageDao.addHrEvsInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.evsManageDao.addActivityInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJson(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			if (dataList.get(0).get("SEQ") != null && !"".equals(dataList.get(0).get("SEQ"))) {
				this.evsManageDao.addEvsInfoByJson(dataList,target);
			} else {
				this.evsManageDao.addEvsInfoByJson(dataList,"addScoreInfo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJsonPro(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsInfoByJsonPro(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String message = "OK";
		try {
			message = this.evsManageDao.addEnsInfoProcedure(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 新增概要信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.evsManageDao.addEvsResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTarget(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			List list = new ArrayList();
			String[] itemName = request.getParameterValues("ITEM_NAME");
			String[] itemContent = request.getParameterValues("ITEM_CONTENT");
			String[] itemScore = request.getParameterValues("ITEM_SCORE");
			String[] startDate = request.getParameterValues("START_DATE");
			String[] endDate = request.getParameterValues("END_DATE");
			if(itemName != null && itemName.length > 0){
				for(int i=0;i<itemName.length; i++){
					Map map = new LinkedHashMap();
					map.put("ITEM_NAME", itemName[i]);
					map.put("ITEM_CONTENT", itemContent[i]);
					map.put("ITEM_SCORE", itemScore[i]);
					map.put("START_DATE", startDate == null ? "" : startDate[i]);
					map.put("END_DATE", endDate == null ? "" : endDate[i]);
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("adminID", paramMap.get("adminID"));
					list.add(map);
				}
			}
			this.evsManageDao.addSSTEvsItem(list,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelf(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsBySelf(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * TSTO一次考评
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoTSTO(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsDetailInfoTSTO(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfHTSV(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String[] ITEM_CONTENT = request.getParameterValues("ITEM_CONTENT");
			String[] ITEM_NAME = request.getParameterValues("ITEM_NAME");
			String[] ITEM_SCORE = request.getParameterValues("ITEM_SCORE");
			String[] EVS_SCORE = request.getParameterValues("EVS_SCORE");
			List dataList = new ArrayList();
			if(ITEM_CONTENT != null && ITEM_CONTENT.length > 0){
				for(int i=0;i < ITEM_CONTENT.length;i++ ){
					Map map = new LinkedHashMap();
					map.put("ITEM_NAME", ITEM_NAME[i]);
					map.put("ITEM_CONTENT", ITEM_CONTENT[i]);
					map.put("ITEM_SCORE", ITEM_SCORE[i]);
					map.put("EVS_SCORE", EVS_SCORE[i]);
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("adminID", paramMap.get("adminID"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("interCpnyID", paramMap.get("interCpnyID"));
					dataList.add(map);
				}
			}
			this.evsManageDao.addEvsBySelfHTSV(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfTSTOAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsBySelfTSTOAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.saveEvsObjectInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 力量1次考核
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoHTSVAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsDetailInfoHTSVAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfSSTAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addEvsBySelfTSTOAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.modifyObjectActivityForAffirm(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增考核对象
	 */
	@SuppressWarnings("unchecked")
	public String addEvsObject(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String result = "OK";
		try {
			result = evsManageDao.addEvsObject(param);
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		return result;
	}
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfoByJson(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.deleteEvsInfoByJson(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectConfirmInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.saveEvsObjectConfirmInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTargetProbation(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			List list = new ArrayList();
			String[] itemName = request.getParameterValues("ITEM_NAME");
			String[] itemContent = request.getParameterValues("ITEM_CONTENT");
			String[] itemScore = request.getParameterValues("ITEM_SCORE");
			if(itemName != null && itemName.length > 0){
				for(int i=0;i<itemName.length; i++){
					Map map = new LinkedHashMap();
					map.put("ITEM_NAME", itemName[i]);
					map.put("ITEM_CONTENT", itemContent[i]);
					map.put("ITEM_SCORE", itemScore[i]);
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("adminID", paramMap.get("adminID"));
					list.add(map);
				}
			}
			
			//封装考核者
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List affirmList = new ArrayList();
			String[] affirmIdStart = request.getParameterValues("AFFIRMOR_ID");
			String[] approvTypeIndex = request.getParameterValues("approvTypeIndex");
			if(affirmIdStart == null || affirmIdStart.length == 0){
				// 未给该员工设置决裁者时
				throw new CommonException("请先设置考核者");// alert.ess.approval.no_approver
			}
			List<String> affirmId=new ArrayList();
			List<String> approvTypeList=new ArrayList();
			for (int i = 0;i< affirmIdStart.length;i++) {
				String strings = affirmIdStart[i];
		    	if(!strings.equals("")){
		    		if(admin.getAdminID().equals(strings)){
		    			throw new CommonException("不能把自己设置为考核者");// alert.ess.approval.no_approver
		    		}
		    		affirmId.add(strings);
		    		approvTypeList.add(request.getParameter("approvType" + approvTypeIndex[i]));
		    	}
		    }
			if(affirmId == null || affirmId.size()==0){
				throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
			}
			//添加决裁者 
			for(int i=0; i<affirmId.size(); i++){
				LinkedHashMap affirmMap = new LinkedHashMap() ;
				affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
				affirmMap.put("AFFIRM_LEVEL", i+1);
				affirmMap.put("AFFIRM_TYPE", approvTypeList.get(i));
				affirmList.add(affirmMap);
			}
			paramMap.put("affirmList", affirmList);
			
			this.evsManageDao.addSSTEvsItemProbation(list,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int addProbationEvsAffirmInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.addProbationEvsAffirmInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.saveProbationResult(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 资料室信息添加
	 */
	@SuppressWarnings("unchecked")
	public int addFileRoomInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.evsManageDao.addFileRoomInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除考核等级
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfo(HttpServletRequest request, String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.evsManageDao.deleteEvsInfo(dataList, target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String evsAffirmorImportDemo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		String name="";
		aliasNameList.add("Employee_ID");
		aliasNameList.add("Employee_Name");
		aliasNameList.add("Evaluator_ID_1");
		aliasNameList.add("Evaluator_Name_1");
		aliasNameList.add("Evaluator_ID_2");
		aliasNameList.add("Evaluator_Name_2");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "0001");
			map.put("CELL1", "Employee 1");
			map.put("CELL2", "Evaluator ID 1");
			map.put("CELL3", "Evaluator Name 1");
			map.put("CELL4", "Evaluator ID 2");
			map.put("CELL5", "Evaluator Name 2");
			
			list.add(map);
			name="EVS_Evaluator";
		return name;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String evsAffirmTargetImportDemo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		String name="";
		aliasNameList.add("Employee_ID");
		aliasNameList.add("Employee_Name");
		aliasNameList.add("Evs_Grade");
		//aliasNameList.add("Final_Grade");
		//aliasNameList.add("Comment");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "0001");
			map.put("CELL1", "Employee 1");
			map.put("CELL2", "S");
			//map.put("CELL3", "100");
			//map.put("CELL4", "Comment");
			list.add(map);
			name="EVS_Evaluation";
		return name;
	}
}
