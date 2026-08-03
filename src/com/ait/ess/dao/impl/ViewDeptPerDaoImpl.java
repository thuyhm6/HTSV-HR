package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.ViewDeptPerDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class ViewDeptPerDaoImpl extends SqlMapClientSupport implements ViewDeptPerDao {
	
	

	/**
	 * 查询人员结果列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonManageList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonManageList(obj, -1, -1);
		return returnList;
	}
	

	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListOt(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPersonListOt(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * 查询人员结果列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.getOtPersonList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.getOtPersonList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	@SuppressWarnings("unchecked")
	public List getPersonListOt(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.deptper.getOtPersonListOt", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ess.deptper.getOtPersonListOt", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@Override
	public int getPersonListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.getOtPersonListCnt", obj)),
				Integer.class);
	}
	
	@Override
	public int getPersonManageListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.getOtPersonManageListCnt", obj)),
				Integer.class);
	}
	
	/**
	 * 查询人员结果列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonManageList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.getOtPersonManageList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.getOtPersonManageList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo(Object obj) {
		Object returnList = new Object() ;
		try{
		returnList = this.queryForObject("ess.deptper.getPersonInfoByid", obj);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo2(Object obj) {
		Object returnList = new Object() ;
		try{
			returnList = this.queryForObject("ess.deptper.getPersonInfoByid2", obj);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnList ;
	}
	
	
	/**
	 * 取得所有动态组列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getDynamicGroupList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	/**
	 * 取得所有动态组信息列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.deptper.getDynamicArTypeList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.deptper.getDynamicArTypeList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	

	/**
	 * 部门系人员 ，单个考勤信息列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List  viewArPersonalSingleList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.viewArPersonalSingleList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	
	//部门汇总
	@SuppressWarnings("unchecked")
	public List  viewArSummarySingleList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.viewArSummarySingleList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	
	
	/**
	 * 部门系人员 ，单个考勤信息列表((get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.deptper.viewArPersonalSingleList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.deptper.viewArPersonalSingleList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	//部门汇总
	
	@SuppressWarnings("unchecked")
	public List viewArSummarySingleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.deptper.viewArSummarySingleList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.deptper.viewArSummarySingleList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	
	/**
	 * 部门系人员 ，加班考勤信息列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List  viewOtApplySingleList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.viewOtApplySingleList(obj, -1, -1) ;
			
		return returnList ;
	}
	/**
	 * 部门系人员 ，单个加班信息列表((get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.deptper.viewOtApplySingleList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.deptper.viewOtApplySingleList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 中夜班津贴列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List  viewAllowanceSingleList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("ess.deptper.viewAllowanceSingleList", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return returnList ;
	}
	/**
	 * 动态组 
	 */
	
	
	
	/**
	 * 动态列
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAutoColumnList(Object obj) {
		List returnList = new ArrayList();
		try{
		returnList = this.queryForList("ess.deptper.viewAutoColumnList",obj);
		}catch (Exception e) {
			// TODO: handle exception
		}
				return returnList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewArPersonalList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewArPersonalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewArPersonalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	
	
	@Override
	public int viewArPersonalListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewArPersonalListCnt", obj)),
				Integer.class);
	}
	/////end
	
	
	
	

	/**
	 * 个人加班现况
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewOtApplyPersonalList(obj, -1, -1);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAnnualLeaveList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewUseOfAnnualLeaveList(obj, -1, -1);
		return returnList;
	}
	//医疗期天数信息
	@SuppressWarnings("unchecked")
	@Override
	public List arForMedicalCountInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.arForMedicalCountInfoList(obj, -1, -1);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAdjustLeaveList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewUseOfAdjustLeaveList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewOtApplyPersonalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewOtApplyPersonalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAnnualLeaveList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewUseOfAnnualLeaveList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewUseOfAnnualLeaveList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAdjustLeaveList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewUseOfAdjustLeaveList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewUseOfAdjustLeaveList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	@Override
	public int viewOtApplyPersonalListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewOtApplyPersonalListCnt", obj)),
				Integer.class);
	}
	@Override
	public int viewUseOfAnnualLeaveListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewUseOfAnnualLeaveListCnt", obj)),
				Integer.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewMedicalInfo(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.deptper.viewMedicalInfo",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List leaveInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.deptper.leaveInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int viewUseOfAdjustLeaveListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewUseOfAdjustLeaveListCnt", obj)),
				Integer.class);
	}
	
	//end
	
	
/**
 *旷工查询页面
 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewAbsenteeismInfoList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewAbsenteeismInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewAbsenteeismInfoList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	@Override
	public int viewAbsenteeismInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewAbsenteeismInfoListCnt", obj)),
				Integer.class);
	}
	
	//end
	
	
	/**
	 *考勤出入数据查询
	 */
		@SuppressWarnings("unchecked")
		@Override
		public List viewEntryInfoList(Object obj) {
			List returnList = new ArrayList();
			returnList = this.viewEntryInfoList(obj, -1, -1);
			return returnList;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List viewEntryInfoList(Object obj, int currentPage, int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ess.deptper.viewEntryInfoList",
							obj, currentPage, pageSize);
				} else {
					returnList = this.queryForList("ess.deptper.viewEntryInfoList",
							obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		

		@Override
		public int viewEntryInfoListCnt(Object obj) throws Exception {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.deptper.viewEntryInfoListCnt", obj)),
					Integer.class);
		}
		
		//end
		
		
		
		/**
		 *manage权限下员工任职经历 
		 */
			@SuppressWarnings("unchecked")
			@Override
			public List ManageEmpPositionInfoList(Object obj) {
				List returnList = new ArrayList();
				returnList = this.ManageEmpPositionInfoList(obj, -1, -1);
				return returnList;
			}
			
			

			/**
			 *上海离职率
			 */
				@SuppressWarnings("unchecked")
				@Override
				public List totalEmpCountLastYear(Object obj) {
					List returnList = new ArrayList();
					returnList = this.totalEmpCountLastYear(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getDemissionRateSpcSh(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getDemissionRateSpcSh(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List LeftManTotalEmpCountLastYear(Object obj) {
					List returnList = new ArrayList();
					returnList = this.LeftManTotalEmpCountLastYear(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List NewManTotalEmpCountLastYear(Object obj) {
					List returnList = new ArrayList();
					returnList = this.NewManTotalEmpCountLastYear(obj, -1, -1);
					return returnList;
				}
				
				
				@SuppressWarnings("unchecked")
				@Override
				public List getCountPosition(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getCountPosition(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getOthers(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getOthers(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getSearchMonth(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getSearchMonth(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getPositionRULIzhi(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getPositionRULIzhi(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getEmpTypeRULIzhi(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getEmpTypeRULIzhi(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getGradeRuzhi(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getGradeRuzhi(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getGradeLizhi(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getGradeLizhi(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getPCountEMP(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getPCountEMP(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getEmpTypeCountEMP(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getEmpTypeCountEMP(obj, -1, -1);
					return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List getGradeCountEmp(Object obj) {
					List returnList = new ArrayList();
					returnList = this.getGradeCountEmp(obj, -1, -1);
					return returnList;
				}
			
			
				/**
				 *天津离职率
				 */
					@SuppressWarnings("unchecked")
					@Override
					public List totalEmpCountLastYearTJ(Object obj) {
						List returnList = new ArrayList();
						returnList = this.totalEmpCountLastYearTJ(obj, -1, -1);
						return returnList;
					}
					
					@SuppressWarnings("unchecked")
					@Override
					public List LeftManTotalEmpCountLastYearTJ(Object obj) {
						List returnList = new ArrayList();
						returnList = this.LeftManTotalEmpCountLastYearTJ(obj, -1, -1);
						return returnList;
					}
					
					@SuppressWarnings("unchecked")
					@Override
					public List NewManTotalEmpCountLastYearTJ(Object obj) {
						List returnList = new ArrayList();
						returnList = this.NewManTotalEmpCountLastYearTJ(obj, -1, -1);
						return returnList;
					}			
			
			
			
			@SuppressWarnings("unchecked")
			@Override
			public List ManageEmpPositionInfoList(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.ManageEmpPositionInfoList",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.ManageEmpPositionInfoList",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List getMonthAttDetailList(Object obj) {
				List returnList = new ArrayList();
				try { 
						returnList = this.queryForList("ess.deptper.getMonthAttDetailList",
								obj); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List totalEmpCountLastYear(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.totalEmpCountLastYear",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.totalEmpCountLastYear",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List getDemissionRateSpcSh(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getDemissionRateSpcSh",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getDemissionRateSpcSh",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List LeftManTotalEmpCountLastYear(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.LeftManTotalEmpCountLastYear",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.LeftManTotalEmpCountLastYear",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List NewManTotalEmpCountLastYear(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.NewManTotalEmpCountLastYear",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.NewManTotalEmpCountLastYear",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			
			@SuppressWarnings("unchecked")
			public List getCountPosition(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getCountPosition",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getCountPosition",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getOthers(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getOthers",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getOthers",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getSearchMonth(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getSearchMonth",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getSearchMonth",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getPositionRULIzhi(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getPositionRULIzhi",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getPositionRULIzhi",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getEmpTypeRULIzhi(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getEmpTypeRULIzhi",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getEmpTypeRULIzhi",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getGradeRuzhi(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getGradeRuzhi",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getGradeRuzhi",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getGradeLizhi(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getGradeLizhi",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getGradeLizhi",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getPCountEMP(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getPCountEMP",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getPCountEMP",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getEmpTypeCountEMP(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getEmpTypeCountEMP",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getEmpTypeCountEMP",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List getGradeCountEmp(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.getGradeCountEmp",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.getGradeCountEmp",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List totalEmpCountLastYearTJ(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.totalEmpCountLastYearTJ",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.totalEmpCountLastYearTJ",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List LeftManTotalEmpCountLastYearTJ(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.LeftManTotalEmpCountLastYearTJ",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.LeftManTotalEmpCountLastYearTJ",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@SuppressWarnings("unchecked")
			public List NewManTotalEmpCountLastYearTJ(Object obj, int currentPage, int pageSize) {
				List returnList = new ArrayList();
				try {
					if (currentPage > -1 && pageSize > -1) {
						returnList = this.queryForList("ess.deptper.NewManTotalEmpCountLastYearTJ",
								obj, currentPage, pageSize);
					} else {
						returnList = this.queryForList("ess.deptper.NewManTotalEmpCountLastYearTJ",
								obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return returnList;
			}
			
			@Override
			public int ManageEmpPositionInfoListCnt(Object obj) throws Exception {
				return NumberUtils.parseNumber(ObjectUtils.toString(this
						.queryForObject("ess.deptper.ManageEmpPositionInfoListCnt", obj)),
						Integer.class);
			}
			
			//end
			
			
			/**
			 *manage权限下员工任职经历   single
			 */
				@SuppressWarnings("unchecked")
				@Override
				public List ManageEmpPositionSinglList(Object obj) {
					List returnList = new ArrayList();
					try{
					returnList = this.queryForList("ess.deptper.ManageEmpPositionSinglList",obj);
					}catch (Exception e) {
						// TODO: handle exception
					}return returnList;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Object getEmpInfoById(Object obj) {
					Object returnList = new Object();
					try{
					returnList = this.queryForObject("ess.deptper.getEmpInfoById",obj);
					}catch (Exception e) {
						// TODO: handle exception
					}return returnList;
				}
				
				//end
	
	/*****
	 * 公司日历  一系列
	 */
	/**
	 * 取得所有公司日历信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getCompanyCalendarList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	/**
	 * 班组日历(get EmpCalendar List)
	 * @param obj
	 * @return List
	 * @throws 
	 */
	/* @author xuehaifei
	 * 班组日历查看
	 * 2014-7-9
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getArClassCalendarList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object) {
		String returnValue = null ;
		try {
			returnValue = StringUtil.checkNull(this.queryForObject("ess.deptper.getDefaultGroup", object));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnValue ;
	}
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getArClassCalendarListGs", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取班次信息(get Shift List)
	 * 
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(Object obj) {
		List returnList = new ArrayList() ;
		try {
		
			returnList = this.queryForList("ess.deptper.getShiftList", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	
	////////////////////////////end
	
	/**
	 * manage count 
	 */

	/*****
	 * 公司日历  一系列
	 */
	/**
	 * 取得所有公司日历信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List manageAgeCountList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("ess.deptper.manageAgeCountList", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 生成部门树
	 */
	
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getParentCodeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 生成部门区分列表
	 */
	
	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getParentCodeDifList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 根据部门编号统计年龄
	 */
	
	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageAgeCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getGradeBGZListByDeptNo", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getGradeSCZListByDeptNo", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.managePositionCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getEduListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageEduCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

	@SuppressWarnings("unchecked")
	public List getSexListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageSexCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageEmpTypeCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

	/**
	 * 得到所有部门
	 */
	@SuppressWarnings("unchecked")
	public List getAllDept(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllDept", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllDept1(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllDept1", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllDeptForHr(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllDeptForHr", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllShopItem(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopItem", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllShopItemsh(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopItemsh", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getAllShopItemsh1(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopItemsh", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getAllShopCountItem(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopCountItem", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getAllShopCountItemsh(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopCountItemsh", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getAllShopCountItemsh1(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllShopCountItemsh1", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public int viewDeptClildNum(Object obj) {
		int returnInt  = 0;
		try {
			returnInt = (Integer) this.queryForObject("ess.deptper.viewDeptClildNum", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int viewDeptLeve(Object obj) {
		int returnInt  = 0;
		try {
			returnInt = (Integer) this.queryForObject("ess.deptper.viewDeptLeve", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 得到所有部门  区分
	 */
	
	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.getAllDeptDif", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 月别采用现状
	 */
	@SuppressWarnings("unchecked")
	public List getMonthListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageMonthCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 月别退职现状
	 */
	@SuppressWarnings("unchecked")
	public List getMonthResignListByDeptNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptper.manageMonthResignCountList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 个人加班考勤
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalYearList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewArPersonalYearList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 考勤汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArSummaryList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewArSummaryList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 考勤汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArDetailSummaryForMonthList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewArDetailSummaryForMonthList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	/**
	 * 考勤汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArDetailSummaryForAttenceList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewArDetailSummaryForAttenceList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 考勤汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceForMonthList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewAttendanceForMonthList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 考勤汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceForSpcBjMonthList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewAttendanceForSpcBjMonthList",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 日报表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceForSpcBjMonthList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.viewAttendanceForSpcBjMonthList1",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAllShopDetailItem(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.getAllShopDetailItem",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	/*上海单独加的*/
	@SuppressWarnings("unchecked")
	@Override
	public List getAllShopDetailItemsh(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.getAllShopDetailItemsh",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	/*上海单独加的*/
	@SuppressWarnings("unchecked")
	@Override
	public List getAllShopDetailItemsh1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.getAllShopDetailItemsh1",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	/**
	 * 个人年假
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List yearUseInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.yearUseInfo",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	/**
	 * 个人年假
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List yearInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.yearInfo",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	/**
	 * 个人年假
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List vacInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.deptper.vacInfo",
					obj);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnList;
	}
	
	
	
	//manage  权限的加班考勤查询
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalManageList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewArPersonalManageList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalManageList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewArPersonalManageList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewArPersonalManageList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	
	
	@Override
	public int viewArPersonalManageListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewArPersonalManageListCnt", obj)),
				Integer.class);
	}
	/////end
	
	
	
	

	/**
	 * 个人加班现况
	 * 
	 * 
	 * 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalManageList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewOtApplyPersonalManageList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalManageList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.deptper.viewOtApplyPersonalManageList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.deptper.viewOtApplyPersonalManageList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	@Override
	public int viewOtApplyPersonalManageListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.deptper.viewOtApplyPersonalManageListCnt", obj)),
				Integer.class);
	}

	@Override
	public List arForMedicalCountInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.countAr.arForMedicalCountInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.countAr.arForMedicalCountInfoList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	//end
	
	
	
	
}