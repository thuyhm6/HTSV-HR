package com.ait.pa.dao.imp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.salaryCodeDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class salaryCodeDaoImpl extends SqlMapClientSupport implements salaryCodeDao{
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@Override
	public List getSalaryCodeList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getSalaryCodeList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 需要决裁的（决裁查看）
	 */
	@Override
	public List getAffirmSalaryCodeList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAffirmSalaryCodeList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 需要决裁的（决裁查看）决裁查看页面决裁情况查看
	 */
	@Override
	public List getAffirmSalary(Object object) {
        List returnList = new ArrayList() ;
        try {
			returnList = this.queryForList("pa.salarycode.getSalary", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 需要决裁的（决裁页面）
	 */
	@Override
	public List getAffirmSalaryList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAffirmSalaryList(object, -1, -1) ;
		return returnList ;
	}

	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的信息的数量
	 * @param object
	 * @return
	 */
	@Override
	public int getSalaryCodeCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getSalaryCodeCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 需要决裁的（决裁查看）
	 */
	@Override
	public int getAffirmSalaryCodeCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getAffirmSalaryCodeCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	
	/**
	 * 需要决裁的（决裁页面）
	 */
	@Override
	public int getAffirmSalaryCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getAffirmSalaryCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	

	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的信息列表   
	 * @param object
	 * @return
	 */
	@Override
	public List getSalaryCodeList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salarycode.getSalaryCodeList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salarycode.getSalaryCodeList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	/**
	 * 需要决裁的（决裁查看）
	 */
	@Override
	public List getAffirmSalaryCodeList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salarycode.getAffirmSalaryCodeList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salarycode.getAffirmSalaryCodeList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 需要决裁的（决裁页面）
	 */
	@Override
	public List getAffirmSalaryList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salarycode.getAffirmSalaryList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salarycode.getAffirmSalaryList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查找单行记录的详细信息
	 */
	@Override
	public Object getSalaryCodeInfo(Object object) {
	    LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getSalaryCodeList(object) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}
	
	


	/**
	 * 决裁通过
	 */
	public int updateAffirmSalaryInfo(Object object) throws Exception{
		try {
			this.update("sys.salarymapping.updateAffirmSalaryInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 没有被决裁的可以被删除（决裁查看页面）
	 * @param object
	 * @throws Exception
	 */
	public void deleteAffirmSalaryInfo(Object object) throws Exception{
		try {
			this.syLanguageDao.deleteSyGlobalName(object);
			this.delete("sys.salarymapping.deleteAffirmSalaryCodeInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	public int getSalaryApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.salarymapping.getSalaryApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getSalaryCodeMappingList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 分页查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salarycode.getSalaryCodeMappingList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salarycode.getSalaryCodeMappingList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查找所有工资项目参数(基本项目参数，输入项目参数，计算项目参数)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeMappingCnt(Object object){
		 int returnInt = 0 ;
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getSalaryCodeMappingCnt", object)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return returnInt ;
	}
	

	/**
	 * 工资财务代码管理
	 */
	public List getPaItemList(Object object) {
		List result = null;
		try {
			result = this.queryForList("pa.salarycode.getPaItemList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	public List viewPaItemList(Object object) {
		List result = null;
		try {
			result = this.queryForList("pa.salarycode.viewPaItemList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public int addPaItemInfo(Object object)throws Exception {
		if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
			//需要update
			this.update("pa.salarycode.updatePaItemInfo",object);
		}else{
			int count = (Integer) this.queryForObject("pa.salarycode.isHavePaItemNo", object);
			if(count > 0){
				return 2;
			}
			this.insert("pa.salarycode.addPaItemInfo",object);
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deletePaItemInfo(Object object)throws Exception {
		this.update("pa.salarycode.deletePaItemInfo",object);
		return 1;
	}
	

	/**
	 * 工资财务代码公式管理
	 */
	public List getPaItemFormulaList(Object object) {
		List result = null;
		try {
			result = this.queryForList("pa.salarycode.getPaItemFormulaList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	public List viewPaItemFormulaList(Object object) {
		List result = null;
		try {
			result = this.queryForList("pa.salarycode.viewPaItemFormulaList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public int addPaItemFormulaInfo(Object object)throws Exception {
		if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
			//需要update
			this.update("pa.salarycode.updatePaItemFormulaInfo",object);
		}else{
			int count = (Integer) this.queryForObject("pa.salarycode.isHavePaItemFormulaNo", object);
			if(count > 0){
				return 2;
			}
			this.insert("pa.salarycode.addPaItemFormulaInfo",object);
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deletePaItemFormulaInfo(Object object)throws Exception {
		this.update("pa.salarycode.deletePaItemFormulaInfo",object);
		return 1;
	}
}


