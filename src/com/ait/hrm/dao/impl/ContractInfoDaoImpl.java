package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.ContractInfoDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ContractInfoDaoImpl extends SqlMapClientSupport implements
		ContractInfoDao {

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractListForSearch(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getContractListForSearch(obj, -1, -1);

		return returnList;
	}

	/**
	 * 未签合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNOContractList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getNOContractList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractListForSearchALL(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getContractListForSearchALL(obj, -1, -1);

		return returnList;
	}

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractListForSearch(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractInfoList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractListForSearchALL(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.login.getContractList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("sys.login.getContractList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 未签合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNOContractList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getNotExistsContractList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getNotExistsContractList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有合同的数量 (Get all the number of contract)
	 * 
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getContractCntForSearch(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.contractInfo.getContractInfoCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 得到所有合同的数量 (Get all the number of contract)
	 * 
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getContractCntForSearchALL(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.login.getContractCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 未签合同的数量 (Get all the number of contract)
	 * 
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getNOContractCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.contractInfo.getNotExistsContractCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 执行添加签订合同(Executive add sign the contract)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void saveNewContract(Object obj) throws Exception {

		this.insert("hrm.contractInfo.addContract", obj);

	}

	/**
	 * 转正评价
	 * 
	 * @param obj
	 * @throws Exception
	 */

	@Override
	public void insertBecomeRegularEvaluate(Object obj){
		try {
			this.insertForList("hrm.contractInfo.updateBecomeRegularEvaluate", (List)obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到所有签订合同信息 (Get all the information signing the contract)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 */
	@Override
	public List getContractByInsertForGrid(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.searchEmpWithoutContract", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.searchEmpWithoutContract", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 得到所有签订合同信息 (Get all the information signing the contract)
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List getContractByInsertForGrid(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getContractByInsertForGrid(obj, -1, -1);

		return returnList;
	}

	/**
	 * 签订合同数量查询 (Sign a contract number query)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int getContractByInsertCntForSearch(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.contractInfo.searchEmpWithoutCntContract",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 续签合同数量查询 (Current contract number query)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int getRenewContractCntForGrid(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.contractInfo.getRenewContractCntForGrid",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 得到所有续签合同信息 (Get all the current contract information)
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List getRenewContractForGrid(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getRenewContractForGrid(obj, -1, -1);

		return returnList;
	}
	
	public List getExpiredIdCardList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getExpiredIdCardList(obj, -1, -1);

		return returnList;
	}
	public List getPaNotImportList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPaNotImportList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 得到所有续签合同信息 (Get all the current contract information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRenewContractForGrid(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getRenewContractForGrid", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getRenewContractForGrid", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getExpiredIdCardList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getExpiredIdCardList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getExpiredIdCardList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaNotImportList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getPaNotImportList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getPaNotImportList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}


	
	
	
	/**
	 * 转正提醒
	 */
	public List getBecomeRegularWarnList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getBecomeRegularWarn(obj, -1, -1);

		return returnList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getBecomeRegularWarn(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getBecomeRegularWarn", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getBecomeRegularWarn", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	
	/**
	 * 续签审批
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List expiredContractApprove(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.expiredContractApprove", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.expiredContractApprove", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 续签审批
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List expiredContractApprove(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getRenewContractForGrid(obj, -1, -1);

		return returnList;
	}

	/**
	 * 签订合同
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author weizhengchen@ait.net.cn
	 * @date 2013-10-14 下午5:21:33
	 * @version V1.0
	 */
	@Override
	public void insertContract( Object obj) throws Exception {
		this.insertForList("hrm.contractInfo.addContract", (List)obj);
	}

	/**
	 * 执行添加续签合同(Executive add current contract)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void updateRenewContractByInsert(HttpServletRequest request,
			LinkedHashMap object) throws Exception {
		String[] isChecked = request.getParameterValues("hr0301Check");
		for (int i = 0; i < isChecked.length; i++) {
			object.put("PERSON_ID", object
					.get("PERSON_ID" + "_" + isChecked[i]));
			object.put("CONTRACT_TYPE_CODE", object.get("CONTRACT_TYPE_CODE"
					+ "_" + isChecked[i]));
			object.put("START_CONTRACT_DATE", object.get("START_CONTRACT_DATE"
					+ "_" + isChecked[i]));
			object.put("END_CONTRACT_DATE", object.get("END_CONTRACT_DATE"
					+ "_" + isChecked[i]));
			object.put("REMARK", object.get("REMARK" + "_" + isChecked[i]));
			object.put("DEPTNO", object.get("DEPTNO" + "_" + isChecked[i]));
			object.put("POST_GRADE_NO", object.get("POST_GRADE_NO" + "_"
					+ isChecked[i]));
			object.put("POSITION_NO", object.get("POSITION_NO" + "_"
					+ isChecked[i]));
			object.put("WORK_POSITION", object.get("WORK_POSITION" + "_"
					+ isChecked[i]));
			object.put("WORK_CONTENT", object.get("WORK_CONTENT" + "_"
					+ isChecked[i]));
			object.put("WORK_TIME", object
					.get("WORK_TIME" + "_" + isChecked[i]));
			object.put("SALARY", object.get("SALARY" + "_" + isChecked[i]));
			object.put("EMP_DISTIN", object.get("EMP_DISTIN" + "_"
					+ isChecked[i]));
			object.put("WORK_HOUR_TYPE", object.get("WORK_HOUR_TYPE" + "_"
					+ isChecked[i]));
			this.insert("hrm.contractInfo.addContract", object);
		}
	}

	/***
	 * 只是修改08年后合同签订次数
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRenewContract(Object object) throws Exception {
		this.update("hrm.contractInfo.updateContractInfoCnt", object);
	}

	/**
	 * 获取合同序号
	 * 
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	public int getContractSeq(Object object) throws Exception {

		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("hrm.contractInfo.getContractSeq")),
				Integer.class);

	}

	@Override
	public Object getPersonalInfoForContract(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.contractInfo.getPersonalInfoForContract", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Object getContractForUpdate(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.contractInfo.getContractForUpdate", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 获取提示天数
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public String getParamVale(Object obj) throws Exception {
		String length = "0.0";
		length = (String) this.queryForObject("hrm.contractInfo.getParamVale",
				obj);
		return length;
	}

	/**
	 * 执行修改合同(update contract)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void updateContractInfo(Object object) throws Exception {

		this.insertForList("hrm.contractInfo.updateContractInfo", (List)object);
		this.insertForList("hrm.contractInfo.insertChangeContractInfo", (List)object);

	}
	
	/**
	 * 执行修改合同(update contract)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void updateContractInfo1(Object object) throws Exception {

		this.updateForList("hrm.contractInfo.updateContractInfo1", (List)object);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addContractInfo1(Object object) throws Exception {
		
		this.insert("hrm.contractInfo.addContractInfo1",object);
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteContractInfo1(List list) throws Exception {
	
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.delete("hrm.contractInfo.deleteContractInfo1",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * 查询详细人力区分类型
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getParticularHumanDistinguishList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList(
				"hrm.contractInfo.getParticularHumanDistinguishList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeList(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("hrm.contractInfo.getCodeList", paramMap);
	}

	@Override
	public List getRenLiAndQiYueList(LinkedHashMap paramMap) {
		List returnList = null;
		try {
			returnList = this.queryForList(
					"hrm.contractInfo.getRenLiAndQiYueList2", paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 续签审批数量查询 (Current contract number query)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int expiredContractApproveCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.contractInfo.expiredContractApproveCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 续签审批
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public void approveExpiredContract(Object object) throws Exception {

		this.insert("hrm.contractInfo.approveExpiredContract", object);

	}

	/**
	 * 插入续签合同决裁者
	 * 
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	@Override
	public void insertContractAffirm(Object object) throws Exception {
		this.insert("ess.infoApply.insertApplyReviewer", object);
	}

	/**
	 * 获取决裁者列表
	 * 
	 * @param paramMap
	 * @author weizhengchen
	 * @return
	 */
	@Override
	public List<LinkedHashMap> getEssAffirmList(LinkedHashMap paramMap) {
		List returnList = null;
		try {
			returnList = this.queryForList("hrm.contractInfo.getEssAffirmList",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 决裁合同
	 * 
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	@Override
	public void affirmContract(Object object) throws Exception {

		this.insert("hrm.contractInfo.affirmContract", object);

	}

	/**
	 * 合同变更查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractChangeList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractChangeList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 合同修改查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractUpdateList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractUpdateList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getNullContractUpdateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getNullContractUpdateList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getnullContractUpdateList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 合同变更查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getContractChangeList(obj, -1, -1);

		return returnList;
	}
	
	/**
	 * 合同修改查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getContractUpdateList(obj, -1, -1);

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteContractUpdateList(Object obj) {
		int returnInt = 0;

		try {
			this.delete("hrm.contractInfo.deleteContractNullInfo1", obj);
			returnInt = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnInt = 0;
		}
		
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getNullContractUpdateList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getNullContractUpdateList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 得到所有可变更合同的数量 (Get all the number of contract)
	 * 
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getContractChangeCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.contractInfo.getContractChangeCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 得到所有可变更合同的数量 (Get all the number of contract)
	 * 
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int checkContractNo(Object obj) {
		int returnInt = 1;
		try {
			Object object = this.queryForObject(
					"hrm.contractInfo.checkContractNo", obj);
			if (object == null) {
				returnInt = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(Object object) {
		return this.getContractTempList(object, 1, 10);
	}

	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getContractTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.contractInfo.getContractTempCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取出错的合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.contractInfo.getContractTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 合同担当查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractManagerList() {
		List returnList = null;
		try {
			returnList = this.queryForList(
					"hrm.contractInfo.getContractManagerList", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 需要续签的合同信息查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractRemindList() {
		List returnList = null;
		try {
			returnList = this.queryForList(
					"hrm.contractInfo.getContractRemindList", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateContractInfoForUpdate(Object obj) {
		int returnInt = 1;
		try {
			
			this.updateForList("hrm.contractInfo.updateContractInfoForUpdate",(List)obj);

		} catch (SQLException e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}

	/**
	 * 合同变更履历查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewChangeContractHistoryList(Object obj) {
		List returnList = null;
		try {
			returnList = this.queryForList(
					"hrm.contractInfo.viewChangeContractHistoryList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}


}
