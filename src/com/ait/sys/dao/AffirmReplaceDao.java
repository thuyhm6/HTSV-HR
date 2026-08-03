package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AffirmReplaceDao {

	List getAffirmReplaceList(Map paraMap, int pageNum, int numPerPage) ;

	List getAffirmReplaceList(Map paraMap) ;

	int getAffirmReplaceListCnt(Map paraMap) ;

	List getAffirmEmpList(Map paramMap, int pageNum, int numPerPage);
	List getAffirmEmpList(Map paramMap);

	int getAffirmEmpCnt(Map paraMap);

	int insertAffirmReplace(Map paramMap);

	int updateDepartmentAffirm(Map paramMap);

	int updateESSAffirm(Map paramMap);

	int updateHRAffirm(Map paramMap);

	int deleteAffirmReplace(Map paramMap);

	String getReplaceDeptid(Map paramMap);

	String getHRAffirmNo(Map paramMap);

	String getESSAffirmNo(Map paramMap);

	String getReplaceFinal(Map paramMap);
	String getReplaceSpecial(Map paramMap);

	int insertAffirmReplaceFromFinal(Map paramMap) throws  Exception;

	int insertAffirmReplaceFromSpecial(Map paramMap) throws Exception;

	int updateFinalAffirm(Map paramMap)throws Exception;
	int updateSpecialAffirm(Map paramMap)throws Exception;

	int insertAffirm(Map paramMap)throws Exception;

	int deleteAffrim(Map paramMap)throws Exception;

}
