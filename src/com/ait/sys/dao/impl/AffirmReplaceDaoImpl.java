package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.AffirmReplaceDao;
import com.ait.web.util.SqlMapClientSupport;

/** 
* @ClassName: AffirmReplaceDaoImpl 
* @Description: TODO 决裁者替换与终止持久层方法
* @author 孙鹏
* @date 2014年12月1日 下午3:37:34 
*  
*/
@Repository
public class AffirmReplaceDaoImpl extends SqlMapClientSupport implements AffirmReplaceDao {


	/* 
	* Title: getAffirmReplaceList
	* Description:查询裁决者替换终止日志列表
	* @author 孙鹏  
	* @date 2014年12月1日 下午4:47:18  
	* @param paraMap
	* @param pageNum
	* @param numPerPage
	* @return
	* @throws Exception 
	* @see com.ait.sys.dao.AffirmReplaceDao#getAffirmReplaceList(java.util.Map, int, int) 
	*/
	@Override
	public List getAffirmReplaceList(Map paraMap, int pageNum, int numPerPage)
			{
		List returnlist=new ArrayList();
		try {
			if(pageNum>-1&&numPerPage>-1){
		
				returnlist=this.queryForList("sys.affirmreplace.getAffrimReplaceList",paraMap,pageNum,numPerPage);
			
			}else{
				returnlist=this.queryForList("sys.affirmreplace.getAffrimReplaceList",paraMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnlist;
	}

	/* 
	* Title: getAffirmReplaceList
	* Description:查询不带分页的裁决者替换终止列表
	* @author 孙鹏  
	* @date 2014年12月1日 下午4:47:39  
	* @param paraMap
	* @return
	* @throws Exception 
	* @see com.ait.sys.dao.AffirmReplaceDao#getAffirmReplaceList(java.util.Map) 
	*/
	@Override
	public List getAffirmReplaceList(Map paraMap)  {
		List returnlist=new ArrayList();
		returnlist=this.getAffirmReplaceList(paraMap, -1, -1);
		return returnlist;
	}

	/* 
	* Title: getAffirmReplaceListCnt
	* Description:查询裁决者日志列表数量
	* @author 孙鹏  
	* @date 2014年12月1日 下午4:54:05  
	* @param paraMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getAffirmReplaceListCnt(java.util.Map) 
	*/
	@Override
	public int getAffirmReplaceListCnt(Map paraMap) {
		int returncnt=0;
		try {
			returncnt=NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirmreplace.getAffrimReplaceListCnt",paraMap)),Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returncnt;
	}

	/* 
	* Title: getAffirmEmpList
	* Description:查询裁决人员列表
	* @author 孙鹏  
	* @date 2014年12月2日 上午10:38:41  
	* @param paramMap
	* @param pageNum
	* @param numPerPage
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getAffirmEmpList(java.util.Map, int, int) 
	*/
	@Override
	public List getAffirmEmpList(Map paramMap, int pageNum, int numPerPage) {
		List returnlist=new ArrayList();
		try {
			if(pageNum>-1&&numPerPage>-1){
		
				returnlist=this.queryForList("sys.affirmreplace.getAffirmEmpList",paramMap,pageNum,numPerPage);
			
			}else{
				returnlist=this.queryForList("sys.affirmreplace.getAffirmEmpList",paramMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnlist;
	}

	@Override
	public List getAffirmEmpList(Map paramMap) {
		List returnlist=new ArrayList();
		returnlist=this.getAffirmEmpList(paramMap, -1, -1);
		return returnlist;
	}

	/* 
	* Title: getAffirmEmpCnt
	* Description:查询人员数量
	* @author 孙鹏  
	* @date 2014年12月2日 上午10:42:01  
	* @param paraMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getAffirmEmpCnt(java.util.Map) 
	*/
	@Override
	public int getAffirmEmpCnt(Map paraMap) {
		int returncnt=0;
		try {
			returncnt=NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirmreplace.getAffirmEmpCnt",paraMap)),Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returncnt;
	}

	/* 
	* Title: insertAffirmReplace
	* Description:裁决替换方法 日志表插入
	* @author 孙鹏  
	* @date 2014年12月2日 下午1:28:13  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#insertAffirmReplace(java.util.Map) 
	*/
	@Override
	public int insertAffirmReplace(Map paramMap) {
		int returnint=0;
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			returnint=(Integer) this.insert("sys.affirmreplace.insertAffirmReplace", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return returnint;
		}
		return returnint;
	}
	
	/* 
	* Title: insertAffirmReplace
	* Description: 将最终裁决标的数据复制到替换临时表。主键为替换表的主键
	* @author 孙鹏  
	* @date 2015年2月3日 下午5:10:24  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#insertAffirmReplace(java.util.Map) 
	*/
	public int insertAffirmReplaceFromFinal(Map paramMap) throws Exception {
		int returnint=0;
		//try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.insert("sys.affirmreplace.insertAffirmReplaceFromFinal", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return returnint;
//		}catch (Exception e) {
//			logger.error(e.toString());
//			e.printStackTrace();
//			return returnint;
//		}
		return 1;
	}
	/* 
	* Title: insertAffirmReplace
	* Description: 将特殊裁决表的数据复制到替换临时表。主键为替换表的主键
	* @author 孙鹏  
	* @date 2015年2月3日 下午5:10:24  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#insertAffirmReplace(java.util.Map) 
	*/
	public int insertAffirmReplaceFromSpecial(Map paramMap)  throws  Exception{
		int returnint=0;
//		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.insert("sys.affirmreplace.insertAffirmReplaceFromSpecial", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return returnint;
//		}catch (Exception e) {
//			logger.error(e.toString());
//			e.printStackTrace();
//			return returnint;
//		}
		return 1;
	}

	/* 
	* Title: updateDepartmentAffirm
	* Description:更新最终裁决表
	* @author 孙鹏  
	* @date 2015年2月4日 上午11:26:43  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#updateDepartmentAffirm(java.util.Map) 
	*/
	public int updateFinalAffirm(Map paramMap) throws  Exception {
		int returnint=0;
//		try {
			this.update("sys.affirmreplace.UpdateFinalAffirm", paramMap);		
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return returnint;
//		}catch (Exception e) {
//			logger.error(e.toString());
//			e.printStackTrace();
//			return returnint;
//		}
		return 1;
	}
	/** 
	* @Title: updateFinalAffirm 
	* @Description: 更新特殊裁决表的裁决者 
	* @param @param paramMap
	* @param @return    
	* @return int    
	* @throws 
	*/
	public int updateSpecialAffirm(Map paramMap) throws  Exception {
		int returnint=0;
//		try {
			this.update("sys.affirmreplace.UpdateSpecialAffirm", paramMap);		
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return returnint;
//		}catch (Exception e) {
//			logger.error(e.toString());
//			e.printStackTrace();
//			return returnint;
//		}
		return 1;
	}
	/** 
	* @Title: updateFianlAffirmRollBack 
	* @Description: TODO 回退最终裁决表的人员id
	* @param @param paramMap
	* @param @return
	* @param @throws Exception    
	* @return int    
	* @throws 
	*/
	public int updateFianlAffirmRollBack(Map paramMap) throws  Exception {
		int returnint=0;
			this.update("sys.affirmreplace.UpdateFinalAffirmRollBack", paramMap);		
		return 1;
	}
	/** 
	* @Title: updateSpecialAffirmRollBack 
	* @Description: TODO 回退特殊裁决表的人员id
	* @param @param paramMap
	* @param @return
	* @param @throws Exception    
	* @return int    
	* @throws 
	*/
	public int updateSpecialAffirmRollBack(Map paramMap) throws  Exception {
		int returnint=0;
			this.update("sys.affirmreplace.UpdateSpecialAffirmRollBack", paramMap);		
		return 1;
	}
	/* 
	* Title: updateDepartmentAffirm
	* Description:更新部门裁决者
	* @author 孙鹏  
	* @date 2014年12月3日 上午11:18:01  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#updateDepartmentAffirm(java.util.Map) 
	*/
	@Override
	public int updateDepartmentAffirm(Map paramMap) {
		int returnint=0;
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.update("sys.affirmreplace.UpdateDepartmentAffirm", paramMap);
			//更新此法人的所有部门update时间。保证决裁线生成正确
			this.update("sys.affirmreplace.UpdateALLDepartmentAffirm",paramMap);
			//执行决裁线生成函数
			this.update("sys.affirmreplace.FunctionDepartmentAffirm", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return returnint;
		}
		return 1;
	}

	@Override
	public int updateHRAffirm(Map paramMap) {
		int returnint=0;
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.update("sys.affirmreplace.UpdateHRAffirm", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return returnint;
		}
		return 1;
	}

	/* 
	* Title: updateESSAffirm
	* Description:替换essaffirm表的裁决人personid
	* @author 孙鹏  
	* @date 2014年12月2日 下午4:56:56  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#updateESSAffirm(java.util.Map) 
	*/
	@Override
	public int updateESSAffirm(Map paramMap) {
		int returnint=0;
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.update("sys.affirmreplace.UpdateESSAffirm", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return returnint;
		}
		return 1;
	}

	/* 
	* Title: deleteAffirmReplace
	* Description:删除日志的方法
	* @author 孙鹏  
	* @date 2014年12月2日 下午4:56:48  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#deleteAffirmReplace(java.util.Map) 
	*/
	@Override
	public int deleteAffirmReplace(Map paramMap) {
		int returnint=0;
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.delete("sys.affirmreplace.DeleteAffirmReplace", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return returnint;
		}
		return 1;
	}
	
	/** 
	* @Title: deleteAffirmReplaceData 
	* @Description: TODO 删除临时表中的数据
	* @param @param paramMap
	* @param @return    
	* @return int    
	* @throws 
	*/
	public int deleteAffirmReplaceData(Map paramMap) throws Exception {
		int returnint=0;
			this.delete("sys.affirmreplace.DeleteAffirmReplaceData", paramMap);
		return 1;
	}
	/* 
	* Title: getReplaceDeptid
	* Description:查询被替换人的部门id
	* @author 孙鹏  
	* @date 2014年12月3日 上午10:54:53  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getReplaceDeptid(java.util.Map) 
	*/
	@Override
	public String getReplaceDeptid(Map paramMap) {
		List returnlist=new ArrayList();
		String newdept="";
		try {
			
			returnlist=this.queryForList("sys.affirmreplace.getReplaceDeptid",paramMap);
			if(returnlist!=null){//将list 里的linkmap 转化为list
				for(int i=0;i<returnlist.size();i++){
					
					Iterator<String> iter = ((Map) returnlist.get(i)).keySet().iterator();

					while (iter.hasNext()) {

					    newdept= newdept+","+(String) ((Map)returnlist.get(i)).get(iter.next());

					}
				}
				;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(newdept.length()>2){
			return newdept.substring(1, newdept.length());
			}else{
				return newdept;
			}
	}

	/* 
	* Title: getHRAffirmNo
	* Description:获取hraffirm的no
	* @author 孙鹏  
	* @date 2014年12月5日 上午10:00:16  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getHRAffirmNo(java.util.Map) 
	*/
	@Override
	public String getHRAffirmNo(Map paramMap) {
		List returnlist=new ArrayList();
		String newdept="";
		try {
			
			returnlist=this.queryForList("sys.affirmreplace.getHRAffirmNo",paramMap);
			if(returnlist!=null){//将list 里的linkmap 转化为list
				for(int i=0;i<returnlist.size();i++){
					
					Iterator<String> iter = ((Map) returnlist.get(i)).keySet().iterator();

					while (iter.hasNext()) {

					    newdept= newdept+","+(String) ((Map)returnlist.get(i)).get(iter.next()).toString();

					}
				}
				;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(newdept.length()>2){
		return newdept.substring(1, newdept.length());
		}else{
			return newdept;
		}
	}

	/* 
	* Title: getESSAffirmNo
	* Description:获取essaffirm的no
	* @author 孙鹏  
	* @date 2014年12月5日 上午10:01:15  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getESSAffirmNo(java.util.Map) 
	*/
	@Override
	public String getESSAffirmNo(Map paramMap) {
		List returnlist=new ArrayList();
		String newdept="";
		try {
			
			returnlist=this.queryForList("sys.affirmreplace.getESSAffirmNo",paramMap);
			if(returnlist!=null){//将list 里的linkmap 转化为list
				for(int i=0;i<returnlist.size();i++){
					
					Iterator<String> iter = ((Map) returnlist.get(i)).keySet().iterator();

					while (iter.hasNext()) {

					    newdept= newdept+","+(String) ((Map)returnlist.get(i)).get(iter.next()).toString();

					}
				}
				;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(newdept.length()>2){
			return newdept.substring(1, newdept.length());
			}else{
				return newdept;
			}
	}
	/* 
	* Title: getReplaceDeptid
	* Description:查询最终裁决表的要替换的主键值并转为格式
	* @author 孙鹏  
	* @date 2015年2月3日 下午3:45:53  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getReplaceDeptid(java.util.Map) 
	*/
	@Override
	public String getReplaceFinal(Map paramMap) {
		List returnlist=new ArrayList();
		String newdept="";
		try {
			
			returnlist=this.queryForList("sys.affirmreplace.getReplaceFinal",paramMap);
			if(returnlist.size()>0){//将list 里的linkmap 转化为list
				/*for(int i=0;i<returnlist.size();i++){					
					Iterator<String> iter = ((Map) returnlist.get(i)).keySet().iterator();
					if(iter.hasNext()){
						String ss=iter.next().toString();
					 newdept= newdept+","+(String) ((Map)returnlist.get(i)).get(iter.next().toString());
					}
				}*/
				newdept=String.valueOf(returnlist.size())+"条数据";
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newdept;

	}

	/* 
	* Title: getReplaceSpecial
	* Description:查询特殊裁决表中要替换的数据的主键值 
	* @author 孙鹏  
	* @date 2015年2月3日 下午3:47:39  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#getReplaceSpecial(java.util.Map) 
	*/
	@Override
	public String getReplaceSpecial(Map paramMap) {
		List returnlist=new ArrayList();
		String newdept="";
		try {
			
			returnlist=this.queryForList("sys.affirmreplace.getReplaceSpecial",paramMap);
			if(returnlist.size()>0){//将list 里的linkmap 转化为list
				newdept=String.valueOf(returnlist.size())+"条数据";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
			return newdept;
			
	}

	/* 
	* Title: insertAffirm
	* Description:裁决替换插入方法，为了控制事务将插入语句让入此处
	* @author 孙鹏  
	* @date 2015年2月5日 上午10:54:26  
	* @param paramMap
	* @return
	* @throws Exception 
	* @see com.ait.sys.dao.AffirmReplaceDao#insertAffirm(java.util.Map) 
	*/
	@Override
	public int insertAffirm(Map paramMap) throws Exception {
		int returnInt=0;
		returnInt=this.insertAffirmReplace(paramMap);
		paramMap.put("REPLACE_NO", returnInt);
		if(paramMap.get("REPLACE_TYPE").equals("TH")){
			if(paramMap.get("TABLE_NAME").equals("ALL")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromFinal(paramMap);
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromSpecial(paramMap);
				if(returnInt>0)returnInt=this.updateFinalAffirm(paramMap);
				if(returnInt>0)returnInt=this.updateSpecialAffirm(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("ZZ")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromFinal(paramMap);
				if(returnInt>0)returnInt=this.updateFinalAffirm(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("TS")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromSpecial(paramMap);
				if(returnInt>0)returnInt=this.updateSpecialAffirm(paramMap);	
			}
		}else if(paramMap.get("REPLACE_TYPE").equals("ZZ")){
			if(paramMap.get("TABLE_NAME").equals("ALL")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromFinal(paramMap);
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromSpecial(paramMap);
				if(returnInt>0)returnInt=this.deleteAffirmReplaceForFinal(paramMap);
				if(returnInt>0)returnInt=this.deleteAffirmReplaceForSpecial(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("ZZ")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromFinal(paramMap);
				if(returnInt>0)returnInt=this.deleteAffirmReplaceForFinal(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("TS")){
				if(returnInt>0)returnInt=this.insertAffirmReplaceFromSpecial(paramMap);
				if(returnInt>0)returnInt=this.deleteAffirmReplaceForSpecial(paramMap);
				}
		}
		return returnInt;
	}

	/* 
	* Title: deleteAffrim
	* Description:回退方法的所有dao处理，集中此处，为了事务处理。
	* @author 孙鹏  
	* @date 2015年2月5日 上午11:05:10  
	* @param paramMap
	* @return
	* @throws Exception 
	* @see com.ait.sys.dao.AffirmReplaceDao#deleteAffrim(java.util.Map) 
	*/
	@Override
	public int deleteAffrim(Map paramMap) throws Exception {
		int returnInt=0;
		returnInt = this.deleteAffirmReplace(paramMap);
		if(paramMap.get("REPLACE_TYPE").equals("TH")){
			if(paramMap.get("TABLE_NAME").equals("ALL")){
				returnInt = this.updateFianlAffirmRollBack(paramMap);
				returnInt = this.updateSpecialAffirmRollBack(paramMap);
				
			}else if(paramMap.get("TABLE_NAME").equals("ZZ")){
				returnInt = this.updateFianlAffirmRollBack(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("TS")){
				returnInt = this.updateSpecialAffirmRollBack(paramMap);	
			}
		}else if(paramMap.get("REPLACE_TYPE").equals("ZZ")){//如果是终止
			if(paramMap.get("TABLE_NAME").equals("ALL")){
				returnInt = this.insertFianl(paramMap);
				returnInt = this.insertSpecial(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("ZZ")){
				returnInt = this.insertFianl(paramMap);
			}else if(paramMap.get("TABLE_NAME").equals("TS")){
				returnInt = this.insertSpecial(paramMap);	
			}
		}
		returnInt = this.deleteAffirmReplaceData(paramMap);
		return returnInt;
	}
	/* 
	* Title: deleteAffirmReplace
	* Description:删除最终裁决表里的数据，为终止服务
	* @author 孙鹏  
	* @date 2015年2月5日 下午1:45:23  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#deleteAffirmReplace(java.util.Map) 
	*/
	public int deleteAffirmReplaceForFinal(Map paramMap) throws SQLException {
		int returnint=0;
		
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.delete("sys.affirmreplace.DeleteAffirmReplaceForFinal", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		return 1;
	}
	/** 
	* @Title: deleteAffirmReplaceForFinal 
	* @Description: TODO 删除特殊裁决表里的数据，为终止服务。
	* @param @param paramMap
	* @param @return
	* @param @throws SQLException    
	* @return int    
	* @throws 
	*/
	public int deleteAffirmReplaceForSpecial(Map paramMap) throws SQLException {
		int returnint=0;
		
			//is.insuranceSystemCalculate.getPaBenStandardSeriousinsertAffirmReplace
			this.delete("sys.affirmreplace.DeleteAffirmReplaceForSpecial", paramMap);
			//this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		return 1;
	}
	/* 
	* Title: insertAffirmReplace
	* Description:将临时表的数据会退到最终裁决表
	* @author 孙鹏  
	* @date 2015年2月5日 下午1:50:50  
	* @param paramMap
	* @return 
	* @see com.ait.sys.dao.AffirmReplaceDao#insertAffirmReplace(java.util.Map) 
	*/
	public int insertFianl(Map paramMap) throws SQLException {
		int returnint=0;
		
			 this.insert("sys.affirmreplace.insertFinal", paramMap);
		
		return 1;
	}
	/** 
	* @Title: insertSpecial 
	* @Description: TODO 将临时表数据回退到特殊裁决表
	* @param @param paramMap
	* @param @return
	* @param @throws SQLException    
	* @return int    
	* @throws 
	*/
	public int insertSpecial(Map paramMap) throws SQLException {
		int returnint=0;
		
			 this.insert("sys.affirmreplace.insertSpecial", paramMap);
		
		return 1;
	}
}
