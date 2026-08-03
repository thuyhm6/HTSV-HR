package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaForLeftMenDao {

	@SuppressWarnings("unchecked")
	public String paCalculate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaCalculateTypeList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaAllItemList(Object object);
	
	@SuppressWarnings("unchecked")
	public void submitAddPaForleftMen(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(Object object, int currentPage,int pageSize);
	
	public int getPaForLeftMenDetailListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(Object object, int currentPage,int pageSize);
	
	public int getPaForLeftMenApplyListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(Object object, int currentPage,int pageSize);
	
	public int getPaForLetMenTempListCnt(Object object);
	
	public int getPaForLetMenTempErrorCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int savePaForLetMen(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public int deletePaForLetMenDetail(LinkedHashMap paHashMap);
	
	public void insertPaBackAffirmor(Object object)  throws Exception;
	
	public void deletePaBackAffirmor(Object object)  throws Exception;
	
	/**
	 * 更新导入的离职人员工资补发的check结果(update pa info for emp left data of import for check result)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updatePaForLetMenDataCheckResult(Object object) throws Exception;
	
	/**
	 * 验证导入的城市等级、省份、城市、地区是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCodeNoCheckList(Object obj);
	
	/**
	 * 更新导入的离职人员工资补发的person_id(update pa info for emp left data of import for person_id)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updatePaForLetMenDataToPersonId(Object object) throws Exception;
	
	/**
	 * 更新导入的离职人员工资补发的item_type(update pa info for emp left data of import for item_type)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updatePaForLetMenDataToItemType(Object object) throws Exception;
	
	/**
	 * 更新导入的离职人员工资补发的item_no(update pa info for emp left data of import for item_no)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updatePaForLetMenDataToItemNo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(Object object, int currentPage,int pageSize);
	
	public int getPaForLeftMenAffirmListCnt(Object object);
	
	/**
	 * 根据离职员工薪资补发申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftAffirmorByApplyNoList(Object object);
	
	/**
	 * 离职员工薪资补发申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckorByApplyNoList(Object object);
	
	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int savePaForLeftApplyAffirm(LinkedHashMap object) throws Exception;
	
	/**
	 * 批量通过/否决离职员工薪资补发申请(batch pass and reject pa info for emp of left apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int savePaForLeftApplyAffirmInBatch(List list) throws Exception;
	
	/**
	 * 批量删除离职员工薪资补发申请(batch delete pa info for emp of left apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delPaForLeftApplyInBatch(List list) throws Exception;
	
	/**
	 * 删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delPaForLeftApply(Object object) throws Exception;
	
	/**
	 * 根据apply_no,person_id查询此人是否是此次申请的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 添加决裁者信息(add apply affirmor)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception;
	
	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 获得决裁信息(get ess_affirm information by applyNo and level)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj) throws Exception;
	
	/**
	 * 删除临时表中所有导入的离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelPaForLeftApplyImport(Object object) throws Exception;
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception;
	
	/**
	 * 获得Check信息Cnt(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEssCheckCntByCheckorId(LinkedHashMap obj) throws Exception;
	
	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckList(Object obj) throws Exception;
	
	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckList(Object obj, int currentPage, int pageSize)
			throws Exception;
	/**
	 * 离职员工薪资补发申请check总数(pa for emp of left apply check list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPaForLeftCheckListCnt(Object obj) throws Exception;
	
	/**
	 * 修改当前裁决者
	 * @param object
	 * @throws Exception
	 */
	public void affirmPaForLeftInfoAffirm(Object object)  throws Exception;
	
	public void updateApplyPaForLeftByApplyNo(Object object)  throws Exception;

	int savePaForLeftApplyAffirm_ep(LinkedHashMap object) throws Exception;
}
