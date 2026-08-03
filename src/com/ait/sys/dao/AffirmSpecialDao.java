package com.ait.sys.dao;

import java.util.List;

public interface AffirmSpecialDao {
	
	@SuppressWarnings("unchecked")
	public List getAffirmSpecialList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAffirmSpecialList_special(Object object);
	

	@SuppressWarnings("unchecked")
	public List getAffirmSpecialList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getAffirmSpecialList_special(Object object, int currentPage, int pageSize);
	
	public void updateAffirmSpecialInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object obj);
	
	public int getCodeListCntByParentCode(Object obj);

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object, int currentPage, int pageSize);
	
	public int getAffirmSpecialListCnt(Object obj);
	
	public int getAffirmSpecialListCnt_special(Object obj);

	@SuppressWarnings("unchecked")
	public List getItemDetail(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail_special(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorSpecialList(Object obj);
	
	public void deleteAffirmSpecialLevelInfo(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getDeptTreeList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType_special(Object obj);
	
	public void insertAffirmSpecialInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(Object object);
	
	public void deleteAffirmSpecialInfo(Object object) throws Exception;
	
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
    public boolean updateAffirmSpecialAppoint (Object object);
	@SuppressWarnings("unchecked")
	public List affirmSpecialAppointList(Object object);
	@SuppressWarnings("unchecked")
    public boolean cancleAppoint (Object object);
	
	public List getPersonCntByEmpid(Object obj);
	
	/**
	 * 根据部门查找该部门下所有的人员类型
	 */
	//public List getPersonEmpTypeByDeptNos(Object obj);
	public List getAffirmSpecialList_specialExcel(Object obj);
}
