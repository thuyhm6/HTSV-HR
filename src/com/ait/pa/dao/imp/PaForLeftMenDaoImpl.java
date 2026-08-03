package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.pa.dao.PaCalculateDao;
import com.ait.pa.dao.PaForLeftMenDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LGE Company: LGE
 * 
 * @fileName: PaForLeftMenDaoImpl.java
 * @Description:
 * @Create date: 2014-9-6 下午16:54:03
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaForLeftMenDaoImpl extends SqlMapClientSupport implements PaForLeftMenDao {
	
	private static String APPLY_TYPE_NO = "224";
	
	//离职人员工资补发申请决裁邀请名称
	private static String APPLY_TYPE_NAME = "离职人员工资补发审批邀请";
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	/**
	 * 工资计算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
			this.insert("pa.paCalculate.paCalculateLGE", paramMap) ;
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 工资类型列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaCalculateTypeList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paCalculate.getPaCalculateTypeList", object) ;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发，added on 2014-06-29
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArStatisticList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paCalculate.getPaForLetMenList", object) ;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发，取得所有工资项目，added on 2014-06-29
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaAllItemList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.history.getPaAllItemList", object);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	  * 修改考勤明细信息(update ArDetail Info)
	  * @param List
	  * @return void
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void submitAddPaForleftMen(List list) throws Exception {
		this.updateForList("pa.history.submitAddPaForleftMen", list);
	}

	/**
	 * 工资大区列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paCalculate.getDeptAreaList", object) ;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询工资担当列表(get ArSupervisor List)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.paCalculate.getPaSupervisorList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 验证奖金是否也是一合并计税方式,计算奖金
	 * @param List
	 * @return
	 */
	public int getCheckPaCalculateType(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paCalculate.getCheckPaCalculateType", object), "0"), 
					Integer.class) ;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnInt ;
	}

	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaForLeftMenDetailList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.history.getPaForLeftMenDetailList", object,currentPage, pageSize);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPaForLeftMenDetailListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLeftMenDetailListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 离职人员工资补发申请信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaForLeftMenApplyList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发申请信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.history.getPaForLeftMenApplyList", object,currentPage, pageSize);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPaForLeftMenApplyListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLeftMenApplyListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(Object obj) {
		List returnList = new ArrayList() ;
		//returnList = this.getPaForLetMenTempList(obj, -1, -1) ;
		try {
			returnList = this.queryForList("pa.history.getPaForLetMenTempList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.history.getPaForLetMenTempList", object,currentPage, pageSize);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPaForLetMenTempListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLetMenTempListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getPaForLetMenTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLetMenTempErrorCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getPaForLeftApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLeftApplySeq")),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int savePaForLetMen(LinkedHashMap paramMap) throws Exception{
		try {
			int batchNo = 0;
			List<LinkedHashMap> affirmList = new ArrayList<LinkedHashMap>();
			List paForLeftImportList = new ArrayList();
			affirmList = (ArrayList)paramMap.get("affirmList");
			for(int i=0;i<affirmList.size();i++){
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap = affirmList.get(i);
				String affirmLevel = tempMap.get("AFFIRM_LEVEL")!=null?tempMap.get("AFFIRM_LEVEL").toString():"";
				String currentAffirmId = tempMap.get("AFFIRMOR_ID")!=null?tempMap.get("AFFIRMOR_ID").toString():"";
				if(affirmLevel!=null && !"".equals(affirmLevel) && "1".equals(affirmLevel)){
					paramMap.put("CURRENT_AFFIRM_ID", currentAffirmId);
					paramMap.put("PRE_AFFIRM_EMPID", currentAffirmId);
					break;
				}
			}
			paForLeftImportList = (ArrayList)paramMap.get("paForLeftImportList");
			if (paForLeftImportList.size() > 0) {
				//申请批次号
				batchNo = this.getPaForLeftApplySeq();
				paramMap.put("BATCH_NO", batchNo);
				//1.首先插入主表PA_LEFTMEN_APPLYinsertPaInfoForEmpLeftApply
				this.insert("pa.history.insertPaInfoForEmpLeftApply", paramMap);
					
						LinkedHashMap lgepMap = new LinkedHashMap();
						lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
						lgepMap.put("APPLY_NO", batchNo);
						lgepMap.put("APPLY_TYPE_NAME",APPLY_TYPE_NAME);
						lgepMap.put("APPLY_TITLE",APPLY_TYPE_NAME);
						lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
						
						lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewFullPaForLeftApplyAffirmorList?LGEP=LGEP&LANGUAGE=zh&personId=123&BATCH_NO=" + paramMap.get("BATCH_NO"));
						lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewFullPaForLeftApplyAffirmorList?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("PRE_AFFIRM_EMPID") + "&BATCH_NO=" + paramMap.get("BATCH_NO"));
						lgepMap.put("AFFIRM_LEVEL", "1");
						lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("PRE_AFFIRM_EMPID"));
						lgepMap.put("CURRENT_AFFIRM_ID",paramMap.get("CURRENT_AFFIRM_ID"));
						this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
				//2.由导入临时表PA_LEFTMEN_ADDING_TEMP转入表PA_LEFTMEN_ADDING
				//将主表的batchNo放入detail表中
				for(int m=0;m<paForLeftImportList.size();m++){
					((LinkedHashMap)paForLeftImportList.get(m)).put("BATCH_NO", batchNo);
				}
				this.insertForList("pa.history.insertPaInfoForEmpLeftDetail", paForLeftImportList);
				//3.删除导入临时表PA_LEFTMEN_ADDING_TEMP中的导入数据
				this.delete("pa.history.deletePaInfoForEmpLeftDetail", paramMap);
				//4.将决裁信息插入决裁表中ess_affirm
				if(affirmList != null && affirmList.size() > 0) {
					LinkedHashMap tempMap = new LinkedHashMap();
					for(LinkedHashMap parmers:affirmList) {
						tempMap.put("APPLY_NO", batchNo);
						tempMap.put("APPLY_TYPE", "224");
						tempMap.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
						tempMap.put("AFFIRM_COM_TYPE", parmers.get("AFFIRM_COM_TYPE"));
						tempMap.put("CREATED_BY", paramMap.get("CREATED_BY"));
						
						this.insert("pa.history.insertPaBackAffirmor",tempMap);
					}
				}
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int deletePaForLetMenDetail(LinkedHashMap paramMap){
		try {
			this.update("pa.tempsale.deleteWageApplicationDetail", paramMap);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void insertPaBackAffirmor(Object object)  throws Exception{
		this.insert("pa.history.insertPaBackAffirmor", object) ;
	}
	
	/**
    * 更新导入的离职人员工资补发的check结果(update pa info for emp left data of import for check result)
    * 
    * @param object
    * @return
    * @throws Exception
    */
    @Override
    public int updatePaForLetMenDataCheckResult(Object object) throws Exception {
    	int flag = 0;
    	this.update("pa.history.updatePaForLetMenDataCheckResult", object);
    	return flag;
    }
	  
   /**
	* 验证导入的法人、工号、项目类型、详细项目、补发月份是否存在，是否正确
	* 
	* @param obj
	* @return
	* @throws Exception
	*/
    @SuppressWarnings("unchecked")
    @Override
    public List getPaForLeftCodeNoCheckList(Object obj) {
	  LinkedHashMap paramMap = (LinkedHashMap)obj;
	  List returnList = new ArrayList() ;
	  String checkType = paramMap.get("CHECK_TYPE")!=null?paramMap.get("CHECK_TYPE").toString():"CPNY";
	  try {
		  if("CPNY".equals(checkType)){//验证法人ID是否正确
			  returnList = this.queryForList("pa.history.getCpnyCheckList", obj);
		  }else if("EMP".equals(checkType)){//验证工号是否存在
			  returnList = this.queryForList("pa.history.getEmpidCheckList", obj);
		  }else if("MONTH".equals(checkType)){//验证月份是否正确
			  returnList = this.queryForList("pa.history.getCodeCheckList", obj);
		  }else if("EXIST_NORMAL".equals(checkType)){//验证导入数据在正式表是否存在
			  returnList = this.queryForList("pa.history.getExistDataNormalCheckList", obj);
		  }else if("EXIST_TEMP".equals(checkType)){//验证导入数据在临时表中是否重复
			  returnList = this.queryForList("pa.history.getExistDataTempCheckList", obj);
		  }else if("ITEM".equals(checkType)){//验证项是否正确
			  returnList = this.queryForList("pa.history.getItemCheckList", obj);
		  }else if("ITEM_ALON".equals(checkType)){
			  returnList = this.queryForList("pa.history.getItemTypeAndItemCheckList", obj);
		  }
	  } catch (SQLException e) {			
		  e.printStackTrace();
	  }
	  return returnList ;
    }
    
    /**
     * 更新导入的离职人员工资补发的person_id(update pa info for emp left data of import for person_id)
     * 
     * @param object
     * @return
     * @throws Exception
     */
     @Override
     public int updatePaForLetMenDataToPersonId(Object object) throws Exception {
     	int flag = 0;
     	this.update("pa.history.updatePaForLetMenDataToPersonId", object);
     	return flag;
     }
     
     /**
      * 更新导入的离职人员工资补发的item_type(update pa info for emp left data of import for item_type)
      * 
      * @param object
      * @return
      * @throws Exception
      */
      @Override
      public int updatePaForLetMenDataToItemType(Object object) throws Exception {
      	int flag = 0;
      	this.update("pa.history.updatePaForLetMenDataToItemType", object);
      	return flag;
      }
     
     /**
      * 更新导入的离职人员工资补发的item_no(update pa info for emp left data of import for item_no)
      * 
      * @param object
      * @return
      * @throws Exception
      */
      @Override
      public int updatePaForLetMenDataToItemNo(Object object) throws Exception {
      	int flag = 0;
      	this.update("pa.history.updatePaForLetMenDataToItemNo", object);
      	return flag;
      }
	
	@Override
	public void deletePaBackAffirmor(Object object)  throws Exception{
		this.delete("pa.history.deletePaBackAffirmor", object) ;
	}
	
	/**
	 * 离职人员工资补发决裁信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaForLeftMenAffirmList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 离职人员工资补发决裁信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.history.getPaForLeftMenAffirmList", object,currentPage, pageSize);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPaForLeftMenAffirmListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLeftMenAffirmListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据离职员工薪资补发申请NO决裁信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftAffirmorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.history.getPaForLeftAffirmorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 离职员工薪资补发申请check信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftCheckorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.history.getPaForLeftCheckorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePaForLeftApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		this.update("pa.history.updateEssAffirmPaForLeftByAffirmNo", object);
		this.update("pa.history.updateEssApplyPaForLeftByApplyNo", object);
		//审批者决裁完后更新他添加的check信息为已check
		this.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过
	 	if ("1".equals(flag)) { 
	 		//以下几行，是从SY_PARAM_INFO_PARAM表里查数据据--系统参数的判断--暂不使用
			//Object obj = this.queryForObject("ess.infoApply.getParamInfoValue",object);
			//String ifConfirmFlag = obj != null ? obj.toString() : "";
	 		//如果不需要人事确认
			//if ("0".equals(ifConfirmFlag)) {
	 			//将裁决通过的离职员工薪资补发数据插入到对应的薪资表里，如PA_SUMMARY_LGECH
				LinkedHashMap paForLeftApply = (LinkedHashMap) this.queryForObject("pa.history.getPaForLeftApplyInfoByApplyNo",object);
				paForLeftApply.put("ADMIN_ID", object.get("ADMIN_ID"));
				paForLeftApply.put("CPNY_ID", object.get("CPNY_ID"));
				//批量将PA_LEFTMEN_APPLY中的数据更新到对应的pa_summary_XXX表中
				doBatchInsertPaForLeftApplyResult(paForLeftApply);
			//}
		}
	 	//发送邮件的功能，暂时没有开发
	 	//this.sendToLGEP(object);
		return 1;
	}
	
	
	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePaForLeftApplyAffirm_ep(LinkedHashMap object) throws Exception {
		LinkedHashMap paForLeftApply = (LinkedHashMap) this.queryForObject("pa.history.getPaForLeftApplyInfoByApplyNo",object);
		paForLeftApply.put("ADMIN_ID", object.get("ADMIN_ID"));
		paForLeftApply.put("CPNY_ID", object.get("CPNY_ID"));
		doBatchInsertPaForLeftApplyResult(paForLeftApply);
		return 1;
	}
	
	/**
	 * 批量申请审批通过并完成，插入pa_summary_XXX
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void doBatchInsertPaForLeftApplyResult(LinkedHashMap paForLeftApply)throws Exception {
		List list = this.queryForList("pa.history.getPaForLeftApplyDetailList", paForLeftApply);
		int paCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap detailMap = (LinkedHashMap) list.get(i);
			paCnt = NumberUtils.parseNumber(ObjectUtils.toString(
					this.queryForObject("pa.history.getPaInfoListCnt", detailMap)), Integer.class) ;
			doInsertPaForLeftApplyResult(detailMap,paCnt);
		}
	}
	
	/**
	 * 审批通过并完成，插入pa_summary_XXX
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private void doInsertPaForLeftApplyResult(LinkedHashMap detailMap,int paCnt)throws Exception {	
		//1.如果薪资表中此月份已有此人薪资数据，则对其补发的薪资项目进行update
		if(paCnt >= 1){
//			this.update("pa.history.updatePaForLeftByApplyResult",detailMap);
			//更新PA_LEFTMEN_ADDING表中的send_flag标志已经转至pa_summary_XXX
//			this.update("pa.history.updatePaForLeftSendFlagByApplyNo",detailMap);
		}else if(paCnt == 0){
			//2.如果薪资表中此月份没有此人薪资数据，则对其补发的薪资项目进行insert
			if(detailMap.get("CPNY_ID").equals("TSTO"))
				this.insert("pa.history.insertPaForLeftByApplyResultLGECH",detailMap);
			else
				this.insert("pa.history.insertPaForLeftByApplyResult",detailMap);
			//更新PA_LEFTMEN_ADDING表中的send_flag标志已经转至pa_summary_XXX
			this.update("pa.history.updatePaForLeftSendFlagByApplyNo",detailMap);
		}
	}
	
	/**
	 * 批量通过/否决离职员工薪资补发申请(batch pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePaForLeftApplyAffirmInBatch(List list) throws Exception {
		LinkedHashMap dataMap = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			dataMap = (LinkedHashMap) list.get(i);
			String flag = dataMap.get("FLAG") != null ? dataMap.get("FLAG").toString(): "0";
			this.update("pa.history.updateEssAffirmPaForLeftByAffirmNo", dataMap);
			this.update("pa.history.updateEssApplyPaForLeftByApplyNo", dataMap);
			//审批者决裁完后更新他添加的check信息为已check
			this.updateCheckFlagByEssAffirmNo(dataMap);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				//不需要再判断是否需要人事确认
				//Object obj = this.queryForObject("ess.infoApply.getParamInfoValue", map);
				//String ifConfirmFlag = obj != null ? obj.toString() : "";
				//if ("0".equals(ifConfirmFlag)) {
					//将裁决通过的离职员工薪资补发数据插入到对应的薪资表里，如PA_SUMMARY_LGECH
					LinkedHashMap paForLeftApply = (LinkedHashMap) this.queryForObject("pa.history.getPaForLeftApplyInfoByApplyNo",dataMap);
					paForLeftApply.put("ADMIN_ID", dataMap.get("ADMIN_ID"));
					paForLeftApply.put("CPNY_ID", dataMap.get("CPNY_ID"));
					//批量将PA_LEFTMEN_APPLY中的数据更新到对应的pa_summary_XXX表中
					doBatchInsertPaForLeftApplyResult(paForLeftApply);
				//}
			}
			//发送邮件的功能，暂时没有开发
			//this.sendToLGEP(dataMap);
		}
		return 1;
	}
	
	/**
	 * 批量删除离职员工薪资补发申请(batch delete pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delPaForLeftApplyInBatch(List list) throws Exception {
		for(int i=0;i<list.size();i++){
			LinkedHashMap map = (LinkedHashMap)list.get(i);
			map.put("APPLY_TYPE", APPLY_TYPE_NO);
			//删除发送LGEP
			affirmInfoToLGEPSer.deleteAffirm(map);
		}
		try {
			this.deleteForList("pa.history.delPaForLeftApplyDetailByApplyNo", list);
			this.deleteForList("pa.history.delPaForLeftAffirmByApplyNo", list);
			this.deleteForList("pa.history.delPaForLeftApplyByApplyNo", list);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delPaForLeftApply(Object object) throws Exception {
		Boolean falg = true;
		LinkedHashMap map = (LinkedHashMap)object;
		map.put("APPLY_TYPE", APPLY_TYPE_NO);
		//删除发送LGEP
		affirmInfoToLGEPSer.deleteAffirm(map);
		
		this.delete("pa.history.delPaForLeftApplyDetailByApplyNo", object);
		this.delete("pa.history.delPaForLeftAffirmByApplyNo", object);
		this.delete("pa.history.delPaForLeftApplyByApplyNo", object);
		return falg;
	}
	
	/**
	 * 根据apply_no,person_id查询此人是否是此次申请的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getAffirmorCntByApplyNo",obj)), Integer.class);
	}
	
	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception {
		//插入新决裁者之前先将等级比较高的决裁者等级加1
		this.update("pa.history.updateAffirmLevelByApplyNo",map);
		//插入新的决裁者
		this.insert("pa.history.insertApplyAffirmor",map);
	}
	
	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj)throws Exception {
		return this.queryForObject("pa.history.getEssAffirmInfoByAffirmNo", obj);
	}
	
	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getMaxAffirmLevelByApplyNo",obj)), Integer.class);
	}
	
	/**
	 * 获得决裁信息(get ess_check information by applyNo and level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj) throws Exception {
		return this.queryForObject("pa.history.getEssAffirmInfoByApplyNoAndLevel", obj);
	}
	
	/**
	 * 删除临时表中所有导入的离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelPaForLeftApplyImport(Object object) throws Exception {
		Boolean falg = true;
		this.delete("pa.history.cancelPaForLeftApplyImport", object);
		return falg;
	}
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception{
		this.update("pa.tempsale.updateCheckFlagByEssAffirmNo", object) ;
	}	
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings("unchecked")
	private void sendToLGEP(LinkedHashMap paramMap){
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
		if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){//决裁中是4
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
			lgepMap.put("AFFIRM_FLAG", 1);
		}else{//决裁完成
			lgepMap.put("FINISH", "FINISH");
			lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("currentAffirmLevel"));
		}
		lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
		lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
		lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
		lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEssCheckCntByCheckorId(LinkedHashMap obj)throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getEssCheckCntByCheckorId", obj)), Integer.class);
	}
	
	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftCheckList(Object obj) throws Exception {
		return this.getPaForLeftCheckList(obj, -1, -1);
	}
	
	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("pa.history.getPaForLeftCheckList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("pa.history.getPaForLeftCheckList", obj);
		}
		return returnList;
	}
	
	/**
	 * 离职员工薪资补发申请check总数(pa for emp of left apply check list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaForLeftCheckListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getPaForLeftCheckListCnt", obj)),Integer.class);
	}
	
	/**
	 * 修改当前裁决者
	 * @param object
	 * @throws Exception
	 */
	public void affirmPaForLeftInfoAffirm(Object object)  throws Exception{
		this.update("pa.history.affirmPaForLeftInfoAffirm", object) ;
		
	}
	
	public void updateApplyPaForLeftByApplyNo(Object object)  throws Exception{
		this.update("pa.history.updateApplyPaForLeftByApplyNo", object) ;
	}
}
