package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaCalculateDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaCalculateDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:16:29
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaCalculateDaoImpl extends SqlMapClientSupport implements PaCalculateDao {

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
	
	@SuppressWarnings("unchecked")
	public String paWithholdingCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
			this.insert("pa.paCalculate.paWithholdingCalculateLGE", paramMap) ;
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String paWithholdingArCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
			this.insert("pa.paCalculate.paWithholdingArCalculateLGE", paramMap) ;
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public int paWithholdingMonthlyStatusCnt(LinkedHashMap paramMap) {
		int returnString = 0 ;
		
		try {
			returnString = Integer.parseInt(this.queryForObject("pa.paCalculate.getPaWithholdingMonthlyStatusCnt", paramMap).toString());
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public void insertpaWithholdingMonthlyStatus(Object parameterObject) {
		try {
			this.insert("pa.paCalculate.insertPaWithholdingMonthlyStatus",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List getpaWithholdingMonthlyStatusList(Object parameterObject) {
		List list = null;
		try {
			list = this.queryForList("pa.paCalculate.getWithholdingMonthlyStatusList",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 工资确认
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryConfirm(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ; 
			
			String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			for (int i = 0; i < NOS.length; i++) {
				if(paramMap.get("interCpnyID").toString().equals("TSTO")){
					paramMap.put("deptid", NOS[i]);
				}else{
					
					paramMap.put("deptid", "");
				}
				this.insert("pa.paCalculate.paSalaryConfirm", paramMap) ;
				returnString += ObjectUtils.toString(paramMap.get("message")) ;
			}
			
			
			
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	/**
	 * 财务传送
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryTransfer(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
			if(paramMap.get("CPNY_ID").equals("TSTO")){
				this.insert("pa.paCalculate.paSalaryTransfer", paramMap);
				returnString  = ObjectUtils.toString(paramMap.get("message"));
			}else{
				this.insert("pa.paCalculate.paSalaryTransferProduce", paramMap) ;
				returnString  = ObjectUtils.toString(paramMap.get("message"));
			}
		
			 
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	 
	@SuppressWarnings("unchecked")
	public List getpaEmpGoup(LinkedHashMap parameterObject) {
		List list = null;
		try {
			list = this.queryForList("pa.paCalculate.getArStatisticEmpGoup",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 营业员/促销员预提传送
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryTransferYuti(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
			this.insert("pa.paCalculate.paSalaryTransferYuti", paramMap) ;
			returnString += ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	
	/**
	 * 工资计算页面 申请确认
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryConfirmApply(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			
			String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			for (int i = 0; i < NOS.length; i++) {
				if(paramMap.get("interCpnyID").toString().equals("TSTO")){
					paramMap.put("AR_DEPT_NO", NOS[i]);
				}else{
					
					paramMap.put("AR_DEPT_NO", "");
				}
				this.insert("pa.paCalculate.paSalaryConfirmApply", paramMap) ;
				returnString += ObjectUtils.toString(paramMap.get("message")) ;
			}
		 	
			
			
			//this.insert("pa.paCalculate.paCalculate", paramMap) ;
		
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
	 * 工资类型列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArStatisticList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getArStatisticList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 工资类型列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaStatisticList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getPaStatisticList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 工资类型列表  财务传送获取人员类型组
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArStatisticPatransferList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getArStatisticPatransferList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 财务传送检查
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPaifClose(LinkedHashMap paramMap) {
		
		
		 
			String returnString = "" ;
			
			try {
				paramMap.put("message", "") ;
				//this.insert("pa.paCalculate.paCalculate", paramMap) ;
				this.insert("pa.paCalculate.getPaifClose", paramMap) ;
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;
				
				e.printStackTrace();
			}
			
			return returnString ;
	 
		
	  
	}
	
	/**
	 * 营业员预提传送检查
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPaifCloseYuti(LinkedHashMap paramMap) {
		
		
		 
			String returnString = "" ;
			
			try {
				paramMap.put("message", "") ;
				//this.insert("pa.paCalculate.paCalculate", paramMap) ;
				this.insert("pa.paCalculate.getPaifCloseYuti", paramMap) ;
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;
				
				e.printStackTrace();
			}
			
			return returnString ;
	 
		
	  
	}
	/**
	 * 财务传送  工资关闭检查
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPaifClose2(LinkedHashMap paramMap) {
		
		
		 
			String returnString = "" ;
			 int xx= 0;
			try {
				paramMap.put("message", "") ;
				//this.insert("pa.paCalculate.paCalculate", paramMap) ;
				 
				xx = (Integer) this.queryForObject("pa.paCalculate.getPaifCloseTwo", paramMap) ;
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;
				
				e.printStackTrace();
			}
			
			return xx ;
	 
		
	  
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
	@SuppressWarnings("unchecked")
	public List getDeptAreaListByHr(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getDeptAreaListByHr", object) ;
			
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
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("pa.paCalculate.getPaSupervisorList", obj);
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
			
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paCalculate.getCheckPaCalculateType", object), "0"), Integer.class) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	@Override
	public List getSalaryProvideDatePa(LinkedHashMap object) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paCalculate.getSalaryProvideDatePa", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	
		
		return returnList ;
	}

	@Override
	public List getSalaryProvideDatePa2(LinkedHashMap object) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paCalculate.getSalaryProvideDatePa2", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	
		
		return returnList ;
	}

	
	
	@Override
	public void updatepaapplyCloseOpen(LinkedHashMap object) {
		try {
			this.update("pa.paCalculate.updatepaapplyCloseGuan", object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
	}
	
	
	@Override
	public void updateisapplyCloseOpen(LinkedHashMap object) {
		try {
			this.update("pa.paCalculate.updateisapplyCloseGuan", object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
	}

	@Override
	public void updatepapaOpenFlag(LinkedHashMap object) {
		try {
			this.update("pa.paCalculate.updatepapaOpenFlag", object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
	}
	
	@Override
	public void updatepaWithholdingapplyCloseOpen(LinkedHashMap object) {
		try {
			this.update("pa.paCalculate.updatepaWithholdingapplyCloseGuan", object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
	}
	
	
	
	@Override
	public String getpaapplyCloseOpen(LinkedHashMap object) {
		String flag = "";
		try {
			flag = this.queryForObject("pa.paCalculate.getPALOCKFLAG", object).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
		
		return flag;
	}
	@Override
	public String getpaapplyCloseOpenIsNo(LinkedHashMap object) {
		String flag = "";
		try {
			flag = this.queryForObject("pa.paCalculate.getPALOCKFLAGISNO", object).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
		
		return flag;
	}
	
	@Override
	public String getpaapplyDEPT_Name(LinkedHashMap object) {
		String flag = "";
		try {
			flag = this.queryForObject("pa.paCalculate.getpaapplyDEPT_Name", object).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
		
		return flag;
	}
	
	@Override
	public String getpaWithholdingapplyCloseOpen(LinkedHashMap object) {
		String flag = "";
		try {
			flag = this.queryForObject("pa.paCalculate.getPAWithholdingLOCKFLAG", object).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
		
		return flag;
	}
	
	
	@Override
	public Object getpaDepeNameAndEmpTypeName(LinkedHashMap object) throws SQLException {
		return this.queryForObject("pa.paCalculate.getpaDepeNameAndEmpTypeName", object);
	}
	
	/**
	 * 
	 * 预提工资
	 *   营业员和促销员
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * 取营业员和促销员类型ID
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeAreaList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getJobTypeGroupAreaList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeAreaList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("pa.paCalculate.getEmpTypeAreaList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/* 
	* Title: getPaConfrimMonth
	* Description:pn工资确认页面查询工资
	* @author 孙鹏  
	* @date 2015年3月27日 下午3:55:59  
	* @param paramMap
	* @param pageNum
	* @param numPerPage
	* @return 
	* @see com.ait.pa.dao.PaCalculateDao#getPaConfrimMonth(java.util.Map, int, int) 
	*/
	@Override
	public List getPaConfrimMonth(Map paramMap, int pageNum, int numPerPage) {
		List returnList = new ArrayList();
		try {
			if (pageNum > -1 && numPerPage > -1) {
				returnList = this.queryForList("pa.paCalculate.getPaConfrimMonth",
						paramMap, pageNum, numPerPage);
			} else {
				returnList = this
						.queryForList("pa.paCalculate.getPaConfrimMonth", paramMap);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public int getPaConfrimMonthCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int i =0;
		
		try {
		i = Integer.parseInt(this.queryForObject("pa.paCalculate.getPaConfrimMonthCnt", paramMap).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
	}

	@Override
	public List getPaConfrimMonth(Map paramMap) {
		// TODO Auto-generated method stub
		return this.getPaConfrimMonth(paramMap, -1, -1);
	}
	
	
}
