package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.NumberUtils;

public interface AffirmDao {
	
	@SuppressWarnings("unchecked")
	public List getAffirmList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAffirmList_final(Object object);
	

	@SuppressWarnings("unchecked")
	public List getAffirmList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getAffirmList_final(Object object, int currentPage, int pageSize);
	
	public void updateAffirmInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object obj);
	
	public int getCodeListCntByParentCode(Object obj);

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object, int currentPage, int pageSize);
	
	public int getAffirmListCnt(Object obj);
	
	public int getAffirmListCnt_final(Object obj);

	@SuppressWarnings("unchecked")
	public List getItemDetail(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail_final(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorList(Object obj);
	
	public void deleteAffirmLevelInfo(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getDeptTreeList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType_final(Object obj);
	
	public void insertAffirmInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(Object object);
	
	public void deleteAffirmInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPidEidList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPidEidList2(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPidEidList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPidEidList2(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(Object object, int currentPage, int pageSize);
	
	public int getEmpIdListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(Object object);
	@SuppressWarnings("unchecked")
    public boolean updateaffirmAppoint (Object object);
	@SuppressWarnings("unchecked")
	public List affirmAppointList(Object object);
	@SuppressWarnings("unchecked")
    public boolean cancleAppoint (Object object);
	
	public List getPersonCntByEmpid(Object obj);
	
	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)包含离职
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getPidEidListFull(Object obj);
	@SuppressWarnings("unchecked")
	public List getPidEidListFull(Object obj, int currentPage, int pageSize);
	
	/**
	 * 获取最终确认批量导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmTempList(Object object) ;
	
	/**
	 * 获取最终确认批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempCnt(Object object);
	
	/**
	 * 获取出错的最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempErrorCnt(Object object);
	
	public List getAffirmList_finalExcel(Object obj);
}
