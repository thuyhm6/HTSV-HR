package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.is.dao.StopInsureDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class StopInsureDaoImpl extends SqlMapClientSupport implements StopInsureDao {
	@Autowired
	private SyLanguageDao syLanguageDao;
	@Override
	public List getPaBenStopInsureListBz(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			this.update("is.stopInstance.backPaBenStopInsureUpdate");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("is.stopInstance.getPaBenStopInsureList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("is.stopInstance.getPaBenStopInsureList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getPaBenStopInsureListBz(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaBenStopInsureListBz(obj, -1, -1) ;
		return returnList ;
	}

	@Override
	public int getStopInsureCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(
						ObjectUtils.toString(this.queryForObject("is.stopInstance.getPaBenStopInsureCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public void deleteStopInsureInfo(Object obj)  {
		try {
			this.delete("is.stopInstance.deleteStopInsureInfo", obj) ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//点击修改出现可修改文本
	@Override
	public void allowPaBenStopInsureUpdate(String id) {
		try {
			this.update("is.stopInstance.allowPaBenStopInsureUpdate", id);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("allowPaBenStopInsureUpdate information Exception. ", e);
		}
		
	}
	//获取要修改的参保对象信息List
	@Override
	public List getAllowPaBenStopInsureUpdate(Object obj) {
		List result;
		try {
			result = this.queryForList("is.stopInstance.getAllowPaBenStopInsureUpdate",obj);
		} catch (Exception e) {
			throw new GlRuntimeException("getAllowPaBenStopInsureUpdate Exception. ", e);
		}
		return result;
	}
	/**
	 * 点击修改出现可修改文本(不修改而返回)
	 */
	@Override
	public void backPaBenStopInsureUpdateBz() {
		try {
			this.update("is.stopInstance.backPaBenStopInsureUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("backPaBenStopInsureUpdate information Exception. ", e);
		}
	}
	@Override
	public void updatePaBenManageAddInfoBz(Object obj) {
		try {
			this.update("is.stopInstance.updatePaBenManageAddInfo", obj);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("update PaBenManageAddInfo information Exception. ", e);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getNOInsStopNumList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaBenStopInsureListBz(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getInsuranceAreaListByCpnyId(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("is.stopInstance.getInsuranceAreaListByCpnyId", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getInsuranceParamDataChList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getInsuranceParamDataChList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getInsuranceParamDataChList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("is.stopInstance.getInsuranceParamDataChList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("is.stopInstance.getInsuranceParamDataChList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public int getInsuranceParamDataChCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(
						ObjectUtils.toString(this.queryForObject("is.stopInstance.getInsuranceParamDataChCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 添加保险参数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addInsuranceParamData(Object obj) throws Exception {
		this.delete("is.stopInstance.deleteInsuranceParamData", obj);
		this.insert("is.stopInstance.insertInsuranceParamData", obj);
	}
	
	/**
	   * 修改保险参数
	   * 
	   * @param object
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public void updateInsuranceParamData(Object object) throws Exception {
	    this.update("is.stopInstance.updateInsuranceParamData", object);
	  }
	  
	  /**
		 * 导入保险参数--查看信息列表(get insurance param data import list)
		 * 
		 * @param parameterObject
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getIsParamImportInfoList(Object obj) {
			List returnList = new ArrayList();
			returnList = this.getIsParamImportInfoList(obj, -1, -1);
			return returnList;
		}

		/**
		 * 导入保险参数--查看信息列表(get insurance param data import list)
		 * 
		 * @param parameterObject
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getIsParamImportInfoList(Object obj, int currentPage, int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("is.stopInstance.getIsParamImportInfoList",obj, currentPage, pageSize);
				} else {
					returnList = this.queryForList("is.stopInstance.getIsParamImportInfoList",obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}

		/**
		 * 导入保险参数--查看信息列表总数(get insurance param data import list count)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int getIsParamImportInfoListCnt(Object obj) throws Exception {
			return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("is.stopInstance.getIsParamImportInfoListCnt", obj)),Integer.class);
		}
		
		/**
		 * 导入保险参数--查看错误信息列表总数(get error insurance param data import list count)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int getIsParamImportInfoListErrorCnt(Object obj) throws Exception {
			return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("is.stopInstance.getIsParamImportInfoListErrorCnt", obj)),Integer.class);
		}
		
		/**
		 * 删除导入的保险参数信息(delete insurance param data information)
		 * 
		 * @param object
		 * @return
		 * @throws Exception
		 */
		@Override
		public boolean delIsParamDataImport(Object object) throws Exception {
			Boolean flag = true;
			this.delete("is.stopInstance.delIsParamDataImportByNo", object);
			return flag;
		}
		

		/**
		 * 批量保存导入的保险参数(add insurance param data of import)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void addIsParamDataImport(List list) throws Exception {
			this.deleteForList("is.stopInstance.delIsParamDataImportBatch", list);
			this.insertForList("is.stopInstance.addIsParamDataImport", list);
		}
		
	  /**
	   * 更新导入的保险参数的check结果(update insurance param data of import for check result)
	   * 
	   * @param object
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public int updateIsParamDataCheckResult(Object object) throws Exception {
	    int flag = 0;
	    this.update("is.stopInstance.updateIsParamDataCheckResult", object);
	    return flag;
	  }
	  
	  /**
		 * 验证导入的大区编码是否存在
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
	  @SuppressWarnings("unchecked")
	  @Override
	  public List getInsuranceAreaCheckListByCpnyId(Object obj) {
		  List returnList = new ArrayList() ;
		  try {
			  returnList = this.queryForList("is.stopInstance.getInsuranceAreaCheckListByCpnyId", obj);
		  } catch (SQLException e) {			
			  e.printStackTrace();
		  }
		  return returnList ;
	  }
	  
	  /**
		 * 验证导入的福利地区是否存在
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
	  @SuppressWarnings("unchecked")
	  @Override
	  public List getInsrareaCheckListByCpnyId(Object obj) {
		  List returnList = new ArrayList() ;
		  try {
			  returnList = this.queryForList("is.stopInstance.getInsrareaCheckListByCpnyId", obj);
		  } catch (SQLException e) {			
			  e.printStackTrace();
		  }
		  return returnList ;
	  }
	  
	  /**
		 * 验证导入的福利项目是否存在
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
	  @SuppressWarnings("unchecked")
	  @Override
	  public List getInsureCheckListByCpnyId(Object obj) {
		  List returnList = new ArrayList() ;
		  try {
			  returnList = this.queryForList("is.stopInstance.getInsureCheckListByCpnyId", obj);
		  } catch (SQLException e) {			
			  e.printStackTrace();
		  }
		  return returnList ;
	  }
	  
	  /**
		 * 删除临时表中所有导入的保险参数(delete insurance param data information)
		 * 
		 * @param object
		 * @return
		 * @throws Exception
		 */
		@Override
		public boolean cancelIsParamDataImport(Object object) throws Exception {
			Boolean falg = true;
			this.delete("is.stopInstance.cancelIsParamDataImport", object);
			return falg;
		}
		
		/**
		 * 根据福利地区编号/名称查询福利地区信息(get the insrarea info by no or name)
		 * 
		 * @param object
		 * @return retrunList
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getInsAreaInfoListByKey(Object obj) {
			List returnList = new ArrayList();
			returnList = this.getInsAreaInfoListByKey(obj, -1, -1);
			return returnList;
		}
		
		/**
		 * 根据福利地区编号/名称查询福利地区信息(get the insrarea info by no or name)
		 * 
		 * @param object
		 * @param currentPage
		 * @param pageSize
		 * @return retrunList
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getInsAreaInfoListByKey(Object obj, int currentPage, int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("is.stopInstance.getInsAreaInfoListByKey",obj, currentPage, pageSize);
				} else {
					returnList = this.queryForList("is.stopInstance.getInsAreaInfoListByKey",obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		
		/**
		 * 根据福利项目编号/名称查询福利项目信息(get the insure info by no or name)
		 * 
		 * @param object
		 * @return retrunList
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getInsureInfoListByKey(Object obj) {
			List returnList = new ArrayList();
			returnList = this.getInsureInfoListByKey(obj, -1, -1);
			return returnList;
		}
		
		/**
		 * 根据福利项目编号/名称查询福利项目信息(get the insure info by no or name)
		 * 
		 * @param object
		 * @param currentPage
		 * @param pageSize
		 * @return retrunList
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getInsureInfoListByKey(Object obj, int currentPage, int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("is.stopInstance.getInsInsureInfoListByKey",obj, currentPage, pageSize);
				} else {
					returnList = this.queryForList("is.stopInstance.getInsInsureInfoListByKey",obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
}
