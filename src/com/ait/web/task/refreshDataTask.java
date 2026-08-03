package com.ait.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.TransferOrderSer;

@Component
public class refreshDataTask {

	Logger logger = Logger.getLogger(refreshDataTask.class);
	@Autowired
	private TransferOrderDao transferOrderDao;
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0 0 * * ?")
	//@Scheduled(cron = "0 0/2 0-23 * * ?")
	public void refreshData() throws Exception {
		logger.debug("开始更新数据......" + new Date()+"===================");
		//入职发令==================================start============================================
		//查询出要更新的数据从 hr_employee_temp
		List emploteeTempList = transferOrderSer.getEmployeeTempList();
		//查询出要更新的数据从 hr_personal_info_temp
		List personalInfoTempList = transferOrderSer.getPersonalInfoTempList();
		//保存到hr_employee,hr_personal_info
		transferOrderSer.saveEmpAndPerForHireTimers(emploteeTempList,personalInfoTempList);
		//修改HR_EXPERIENCE_INSIDE activity = 1
		for(int i=0;i<emploteeTempList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map emploteeTemp =(Map) emploteeTempList.get(i);
			paramMap.put("EXP_INSIDE_NO", emploteeTemp.get("EXP_INSIDE_NO"));
			transferOrderSer.updateHrEmployeeTemp(paramMap);
		}
		//入职发令===================================end===========================================
		
		//调动发令================================start==============================================
		//查询出要更新的调动数据 从hr_Experience_Inside 里
		List experienceInsideListForUpgrade = transferOrderSer.getExperienceInsideListForUpgrade();
		
		for(int i=0;i<experienceInsideListForUpgrade.size();i++){
			Map paramMap = new LinkedHashMap();
			Map experienceInside =(Map) experienceInsideListForUpgrade.get(i);
			//现部门异动
			String newDeptNo=experienceInside.get("DEPTNO").toString();
			String oldDeptNo=experienceInside.get("OLD_DEPTNO").toString();
			if(!newDeptNo.equals(oldDeptNo)){
				paramMap.put("NOW_DEPARTMENT_DATE", "Y");
				
			}
			String newPostGrade=experienceInside.get("POST_GRADE_NO").toString();
			String oldPostGrade=experienceInside.get("OLD_POST_GRADE_NO").toString();
			if(!newPostGrade.equals(oldPostGrade)){
				paramMap.put("POST_GRADE_CHANGE_DATE", "Y");
				
			}
			//职责晋升 123316   职级晋升123317   职责降职123524  职级降职123525
			String transNo=experienceInside.get("TRANS_NO").toString();
			if(transNo.equals("123316")||transNo.equals("123317")||transNo.equals("123524")||transNo.equals("123525")){ 
				paramMap.put("PROMOTION_DATE", "Y");
			}

			paramMap.put("PERSON_ID", experienceInside.get("PERSON_ID"));
			paramMap.put("POST_GRADE_NO", experienceInside.get("POST_GRADE_NO"));
			paramMap.put("POSITION_NO", experienceInside.get("POSITION_NO"));
			paramMap.put("POST_NO", experienceInside.get("POST_NO"));
			paramMap.put("DEPTNO", experienceInside.get("DEPTNO"));
			paramMap.put("DUTY_NO", experienceInside.get("DUTY_NO"));
			paramMap.put("WORK_AREA", experienceInside.get("WORK_AREA"));
			paramMap.put("GRADE_LEVEL", experienceInside.get("GRADE_LEVEL"));
			paramMap.put("EMP_TYPE_CODE", experienceInside.get("DETAIL_HR_DIFF"));
			paramMap.put("EXP_INSIDE_NO", experienceInside.get("EXP_INSIDE_NO"));
			paramMap.put("TRANS_NO", experienceInside.get("TRANS_NO"));
			paramMap.put("TRANS_CODE", experienceInside.get("TRANS_CODE"));
			paramMap.put("START_DATE", experienceInside.get("START_DATE"));
			
			transferOrderSer.updateEmpAndExpForUpgradeTimers(paramMap);
			
			//兼职 123314  ,离职 123356 ,待处理 123359
			/*
			 * 兼职生效的时候，有结束日期的时候 自动生成一条生效的兼职解除的调令
			 */
			if(transNo.equals("123314")||transNo.equals("123356")||transNo.equals("123359")){
				//结束时间
				String end_date="";
				if(experienceInside.get("END_DATE")!=null){
					 end_date= experienceInside.get("END_DATE").toString().subSequence(0, 10).toString();
				}else{
					 end_date="0000-00-00";
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				//当前时间
				String curDate=formatter.format(new Date()).substring(0,10).toString();
				String endTrans="";
				if(transNo.equals("123314")){
					endTrans="123315";//兼职取消  123315
				}else if(transNo.equals("123356")||transNo.equals("123359")){
					endTrans="642";//惩戒 642
				}
				if(transNo.equals("123356")){
					/*RESIGN_TYPE   离职类型  960
					RESIGN_REASON 离职原因  4291
					 EXP_INSIDE_NO             ,
	                  PERSON_ID                 ,
	                  TRANS_NO                  ,
	                  TRANS_CODE                ,
	                  PUN_TYPE_ID				,
	                  DATE_PUNISHED				,
	                  PUN_REASON				,
	                  CREATE_DATE               ,
	                  CREATED_BY                ,
	                  ORDERNO					,
	                  POSITION_NO               ,
	                  POST_NO					,
	                  DEPTNO					,
	                  PUN_BONUS	
	                  				,*/
					/*
					 * 离职调令 离职类型：公司解除劳动契约 离职原因：解除劳动契约 生效时 生成一条生效的惩罚调令(惩罚方式：违纪解除契约)
					 */
					if(experienceInside.get("RESIGN_TYPE").equals("960")&&experienceInside.get("RESIGN_REASON").equals("4291")){
						
					
						Map paramMap1=new LinkedHashMap<String, Object>();
						paramMap1.put("PERSONID", experienceInside.get("PERSON_ID"));
					    List paHrVList=transferOrderDao.getPaHrVList(paramMap1);
						LinkedHashMap experienceInside1 = new LinkedHashMap();
						paramMap.put("PERSON_ID",experienceInside1.get("PERSON_ID"));
						paramMap.put("TRANS_NO", endTrans);
						paramMap.put("TRANS_CODE","16184");//违纪解除契约
						paramMap.put("PUN_TYPE_ID","16184");
						paramMap.put("POSITION_NO",((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
						paramMap.put("POST_NO",((Map)paHrVList.get(0)).get("CUR_POST_NO"));
						paramMap.put("ORDERNO", "");
						paramMap.put("DEPTNO",((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
						paramMap.put("CREATED_BY", "");
						paramMap.put("ACTIVITY", "1");
						paramMap.put("DATE_PUNISHED",curDate);
						paramMap.put("PUN_REASON",experienceInside.get("REMARK"));
						paramMap.put("PUN_BONUS","0");
						String expInsideNo = transferOrderDao.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("CPNY_ID", experienceInside.get("CPNY_ID"));
						this.transferOrderDao.saveExperienceForPunishMent(paramMap);

					}	
						
				}
				
				/*
				 * 待处理调令生效时 生成一条生效的惩罚调令(惩罚方式：待处理)
				 */
				if(transNo.equals("123359")){
					Map paramMap1=new LinkedHashMap<String, Object>();
					paramMap1.put("PERSONID", experienceInside.get("PERSON_ID"));
				    List paHrVList=transferOrderDao.getPaHrVList(paramMap1);
					LinkedHashMap experienceInside1 = new LinkedHashMap();
					paramMap.put("PERSON_ID",experienceInside1.get("PERSON_ID"));
					paramMap.put("TRANS_NO", endTrans);
					paramMap.put("TRANS_CODE","123527");//待处理
					paramMap.put("PUN_TYPE_ID","123527");
					paramMap.put("POSITION_NO",((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
					paramMap.put("POST_NO",((Map)paHrVList.get(0)).get("CUR_POST_NO"));
					paramMap.put("ORDERNO", "");
					paramMap.put("DEPTNO",((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
					paramMap.put("CREATED_BY", "");
					paramMap.put("ACTIVITY", "1");
					paramMap.put("DATE_PUNISHED",curDate);
					paramMap.put("PUN_REASON",experienceInside.get("REMARK"));
					paramMap.put("PUN_BONUS","0");
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("CPNY_ID", experienceInside.get("CPNY_ID"));
					this.transferOrderDao.saveExperienceForPunishMent(paramMap);
				}
				
				if(end_date.equals(curDate)){
					if(transNo.equals("123314")){
						Map paramMap2=new LinkedHashMap<String, Object>();
						paramMap.put("PERSONID", experienceInside.get("PERSON_ID"));
						List paHrVList=transferOrderDao.getPaHrVList(paramMap2);
						//插入兼职解除
						Map paramMap1=new LinkedHashMap<String, Object>();
						//对应的兼职序列
						String Exp_Inside_No=experienceInside.get("EXP_INSIDE_NO").toString();
						paramMap1.put("RELATION_EXP_INSIDE_NO", Exp_Inside_No);
						paramMap1.put("PERSON_ID",  experienceInside.get("PERSON_ID"));
						paramMap1.put("TRANS_NO",  experienceInside);
						paramMap1.put("START_DATE", experienceInside.get("END_DATE").toString().subSequence(0, 10));
						/*CUR_DUTY_NO	VARCHAR2(30)	Y			现职责
						CUR_POST_GRADE_NO	VARCHAR2(30)	Y			现职级
						CUR_POST_NO	VARCHAR2(30)	Y			现职级名称
						CUR_POSITION_NO	VARCHAR2(30)	Y			现职位
						CUR_DEPTNO	VARCHAR2(30)	Y			现部门
						CUR_GRADE_LEVEL	VARCHAR2(30)	Y			现职等
						
						*  --现部门
		       				HRE.DEPTNO CUR_DEPTNO,
					       --现职等
					       Hre.GRADE_LEVEL CUR_GRADE_LEVEL,
					       --现职责
					       HRE.DUTY_NO CUR_DUTY_NO,
					       --现职级
					       HRE.POST_GRADE_NO CUR_POST_GRADE_NO,
					       --现职级名称
					       HRE.POST_NO CUR_POST_NO,
					       --现职位
					       HRE.POSITION_NO CUR_POSITION_NO*/
						
						paramMap1.put("CUR_DUTY_NO", ((Map)paHrVList.get(0)).get("CUR_DUTY_NO"));
						paramMap1.put("CUR_GRADE_LEVEL", ((Map)paHrVList.get(0)).get("CUR_GRADE_LEVEL"));
						paramMap1.put("CUR_DEPTNO", ((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
						paramMap1.put("CUR_POSITION_NO", ((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
						paramMap1.put("CUR_POST_NO", ((Map)paHrVList.get(0)).get("CUR_POST_NO"));
						paramMap1.put("CUR_POST_GRADE_NO", ((Map)paHrVList.get(0)).get("CUR_POST_GRADE_NO"));
						paramMap1.put("TRANS_NO",  "123315");
						paramMap1.put("TRANS_CODE",  "123315");
						String expInsideNo1 = transferOrderDao.getNextExpInside();
						paramMap1.put("ACTIVITY", "1");
						paramMap1.put("EXP_INSIDE_NO", expInsideNo1);
						paramMap1.put("CURRENT_AFFIRM_ID", "");	
						Map paramMapCount=new LinkedHashMap<String, Object>();
						paramMapCount.put("RELATION_EXP_INSIDE_NO", Exp_Inside_No);
						List Listcount=transferOrderDao.getCountTransByrelation(paramMapCount);
						int count=Integer.parseInt(((Map)Listcount.get(0)).get("COUNT").toString());
						if(count<=0){
							transferOrderDao.SaveHrExperienceInsideSave1(paramMap1);
						}
						
					}
				}
			}
			
			
		}
		
		//调动发令=================================end=============================================
		/*
		//转正发令================================start==============================================
		//查询出要更新的调动数据 从hr_Experience_Inside 里
		List probationListForTransferNormal = transferOrderSer.getProbationListForTransferNormal();
		for(int i=0;i<probationListForTransferNormal.size();i++){
			Map paramMap = new LinkedHashMap();
			Map probation =(Map) probationListForTransferNormal.get(i);
			String transCode = probation.get("TRANS_CODE").toString();
			//如果转正类型是实习转试用 那么员工状态就改为试用 code=1374
			if (transCode.equals("4085")) {//实习转试用
				paramMap.put("STATUS_CODE", "1374");//试用
			}else{
				paramMap.put("STATUS_CODE", "14891");//正式
			}
			paramMap.put("PERSON_ID",probation.get("PERSON_ID"));
			paramMap.put("EMP_TYPE_CODE",probation.get("EMP_TYPE_CODE"));
			paramMap.put("START_DATE",probation.get("PROBATION_DATE"));
			paramMap.put("EXP_INSIDE_NO",probation.get("EXP_INSIDE_NO"));
			
			transferOrderSer.updateHrEmployeeForTN(paramMap);
			//如果是实习转正 就要补 初次入社日期
			if (transCode.equals("1678")) {
				transferOrderSer.updateHrEmployeeDateStated(paramMap);
			}
			transferOrderSer.updateProbationForTransferNormal(paramMap);
		}
		//转正发令=================================end=============================================
		*/
		/*
		//晋升降职发令================================start============================================
		List experienceInsideListForTransferPromote = transferOrderSer.getExperienceInsideListForTransferPromote();
		for(int i=0;i<experienceInsideListForTransferPromote.size();i++){
			Map paramMap = new LinkedHashMap();
			Map experienceInside =(Map) experienceInsideListForTransferPromote.get(i);
			paramMap.put("PERSON_ID", experienceInside.get("PERSON_ID"));
			paramMap.put("POST_GRADE_NO", experienceInside.get("POST_GRADE_NO"));
			paramMap.put("POSITION_NO", experienceInside.get("POSITION_NO"));
			paramMap.put("POST_NO", experienceInside.get("POST_NO"));
			paramMap.put("DEPTNO", experienceInside.get("DEPTNO"));
			paramMap.put("DUTY_NO", experienceInside.get("DUTY_NO"));
			paramMap.put("WORK_AREA", experienceInside.get("WORK_AREA"));
			paramMap.put("EXP_INSIDE_NO", experienceInside.get("EXP_INSIDE_NO"));
			transferOrderSer.updateEmpAndExpForUpgradeTimers(paramMap);
		}
		//晋升降职发令=================================end=============================================
		*/
		/*
		//兼职、取消兼职发令================================start============================================
		//兼职发令 list
		List PluralityList = transferOrderSer.getPluralityList();
		for(int i=0;i<PluralityList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map plurality =(Map) PluralityList.get(i);
			paramMap.put("PERSON_ID", plurality.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", plurality.get("EXP_INSIDE_NO"));
			transferOrderSer.updatePluralityForTimer(paramMap);
		}
		//取消兼职发令 list
		List PluralityListForCancle = transferOrderSer.PluralityListForCancle();
		for(int i=0;i<PluralityListForCancle.size();i++){
			Map paramMap = new LinkedHashMap();
			Map plurality =(Map) PluralityListForCancle.get(i);
			paramMap.put("PERSON_ID", plurality.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", plurality.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", plurality.get("START_DATE"));
			paramMap.put("RELATION_EXP_INSIDE_NO", plurality.get("RELATION_EXP_INSIDE_NO"));
			
			
			paramMap.put("TRANS_NO", plurality.get("TRANS_NO"));
			paramMap.put("TRANS_CODE", plurality.get("TRANS_CODE"));
			paramMap.put("PLU_REASON", plurality.get("PLU_REASON"));
			paramMap.put("START_DATE", plurality.get("START_DATE"));
			paramMap.put("PLU_DEPTNO", plurality.get("PLU_DEPTNO"));
			paramMap.put("PLU_POSITION_NO", plurality.get("PLU_POSITION_NO"));
			paramMap.put("PLU_REASON", plurality.get("PLU_REASON"));
			paramMap.put("DEPTNO", plurality.get("DEPTNO"));
			
			paramMap.put("POSITION_NO", plurality.get("POSITION_NO"));
			paramMap.put("PLU_DUTY_NO", plurality.get("PLU_DUTY_NO"));
			paramMap.put("CURRENT_AFFIRM_ID", plurality.get("CURRENT_AFFIRM_ID"));
			paramMap.put("RELATION_EXP_INSIDE_NO", plurality.get("RELATION_EXP_INSIDE_NO"));
			paramMap.put("ACTIVITY", plurality.get("ACTIVITY"));
			paramMap.put("CREATE_DATE", plurality.get("CREATE_DATE"));
			paramMap.put("CREATED_BY", plurality.get("CREATED_BY"));
			
			//transferOrderSer.updatePluralityForTimer(paramMap);
			transferOrderSer.updatePluralityCancleForTimer(paramMap);
		}
		//兼职、取消兼职发令=================================end=============================================
		*/
		/*
		//停职发令================================start============================================
		List SuspendList = transferOrderSer.getSuspendListForTimer();
		for(int i=0;i<SuspendList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map suspend =(Map) SuspendList.get(i);
			paramMap.put("PERSON_ID", suspend.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", suspend.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", suspend.get("START_DATE"));
			paramMap.put("RELATION_EXP_INSIDE_NO", suspend.get("RELATION_EXP_INSIDE_NO"));
			
			paramMap.put("AR_FROM_TIME", suspend.get("START_DATE"));
			paramMap.put("AR_TO_TIME", "9999-12-31");
			paramMap.put("caltype", "emp");
			
			String cpnyId = transferOrderSer.getCpnyIdByPersonId(paramMap);
			paramMap.put("CPNY_ID", cpnyId);
			
			//修改HR_employee 
			//向AR_APPLY_RESULT 插入数据
			//执行存储过程
			//修改HR_SUSPENSION activity=1
			transferOrderSer.updateDateForSuspendTimers(paramMap);
		}
		//停职发令=================================end=============================================
		*/
		/*
		//复职发令================================start============================================
		List reinstatedList = transferOrderSer.getReinstatedListForTimer();
		for(int i=0;i<reinstatedList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map reinstated =(Map) reinstatedList.get(i);
			paramMap.put("PERSON_ID", reinstated.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", reinstated.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", reinstated.get("START_DATE"));
			paramMap.put("RELATION_EXP_INSIDE_NO", reinstated.get("RELATION_EXP_INSIDE_NO"));
			
			//调用存储过程参数start
			Calendar   c   =   Calendar.getInstance(); 
		    int year = c.get(Calendar.YEAR);
		    int month = c.get(Calendar.MONTH);
		    c.set(c.YEAR,   c.get(Calendar.YEAR)); 
			c.set(c.MONTH,   c.get(Calendar.MONTH));
			String fromTime = year + "-" + (month+1) + "-" + c.getActualMinimum(c.DAY_OF_MONTH);
			String toTIme = year + "-" + (month+1) + "-" + c.getActualMaximum(c.DAY_OF_MONTH);
			
			paramMap.put("AR_FROM_TIME",fromTime);
			paramMap.put("AR_TO_TIME", toTIme);
			paramMap.put("caltype", "emp");
			//调用存储过程参数end
			
			String cpnyId = transferOrderSer.getCpnyIdByPersonId(paramMap);
			paramMap.put("CPNY_ID", cpnyId);
			
			//修改HR_employee 
			//修改HR_SUSPENSION
			//修改AR_APPLY_RESULT
			//执行存储过程
			//修改HR_SUSPENSION activity=1
			transferOrderSer.updateDateForReinstatedTimers(paramMap);
		}
		//复职发令=================================end=============================================
		*/
		//奖励发令================================start============================================
		List rewardList = transferOrderSer.getRewardListForTimer();
		for(int i=0;i<rewardList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map reward =(Map) rewardList.get(i);
			paramMap.put("PERSON_ID", reward.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", reward.get("EXP_INSIDE_NO"));
			transferOrderSer.updateHrRewardForTimers(paramMap);
		}
		//奖励发令=================================end=============================================
		
		//惩戒发令================================start============================================
		List punishmentList = transferOrderSer.getPunishmentListForTimer();
		for(int i=0;i<punishmentList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map punishment =(Map) punishmentList.get(i);
			paramMap.put("PERSON_ID", punishment.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", punishment.get("EXP_INSIDE_NO"));
			transferOrderSer.updateHrPunishmentForTimers(paramMap);
		}
		//惩戒发令=================================end=============================================
		/*
		//离职发令================================start============================================
		List resignList = transferOrderSer.getResignListForTimer();
		for(int i=0;i<resignList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map resign =(Map) resignList.get(i);
			paramMap.put("PERSON_ID", resign.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", resign.get("EXP_INSIDE_NO"));
			paramMap.put("RESIGN_DATE", resign.get("RESIGN_DATE"));
			paramMap.put("SETTLEMENT_DATE", resign.get("SETTLEMENT_DATE"));
			
			transferOrderSer.updateHrResignForTimers(paramMap);
		}
		//离职发令=================================end=============================================
		*/
		/*
		//薪资调整发令================================start============================================
		List payriseList = transferOrderSer.getPayriseListForTimer();
		for(int i=0;i<payriseList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map payrise =(Map) payriseList.get(i);
			paramMap.put("PERSON_ID", payrise.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", payrise.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", payrise.get("START_DATE"));
			paramMap.put("RETURN_VALUE", payrise.get("RETURN_VALUE"));
			paramMap.put("ITEM_NO", payrise.get("ITEM_NO"));
			String cpnyId = transferOrderSer.getCpnyIdByPersonId(paramMap);
			paramMap.put("CPNY_ID", cpnyId);
			
			transferOrderSer.updateHrPayriseForTimers(paramMap);
			
		}
		//薪资调整发令=================================end=============================================
		*/
		/*
		//代理发令================================start============================================
		List agentList = transferOrderSer.getAgentListForTimer();
		for(int i=0;i<agentList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map agent =(Map) agentList.get(i);
			paramMap.put("PERSON_ID", agent.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", agent.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", agent.get("START_DATE"));
			paramMap.put("DEPTNO", agent.get("AGENT_DEPTNO"));
			paramMap.put("POSITION_NO", agent.get("AGENT_POSITION_NO"));
			paramMap.put("DUTY_NO", agent.get("AGENT_DUTY_NO"));
			paramMap.put("POST_GRADE_NO", agent.get("AGENT_POST_GRADE_NO"));
			paramMap.put("WORK_AREA", agent.get("AGENT_WORK_AREA"));
			transferOrderSer.updateHremployeeForTimers(paramMap);
		}
		//代理发令=================================end=============================================
		*/
		/*
		//取消代理发令================================start============================================
		List cancleAgentList = transferOrderSer.getCancleAgentListForTimer();
		for(int i=0;i<cancleAgentList.size();i++){
			Map paramMap = new LinkedHashMap();
			Map cancleAgent =(Map) cancleAgentList.get(i);
			paramMap.put("PERSON_ID", cancleAgent.get("PERSON_ID"));
			paramMap.put("EXP_INSIDE_NO", cancleAgent.get("EXP_INSIDE_NO"));
			paramMap.put("START_DATE", cancleAgent.get("START_DATE"));
			paramMap.put("AGENT_POST_GRADE_NO", cancleAgent.get("AGENT_POST_GRADE_NO"));
			paramMap.put("RELATION_EXP_INSIDE_NO", cancleAgent.get("RELATION_EXP_INSIDE_NO"));
			transferOrderSer.updateHremployeeForCancleAgentTimers(paramMap);
		}
		//取消代理发令=================================end=========================================
		*/
		//号俸发令=================================end=============================================
//		List getPayStep = transferOrderSer.getExperienceInsideListForPayStep();
//		for(int i=0;i<getPayStep.size();i++){
//			Map paramMap = new LinkedHashMap();
//			Map experienceInside =(Map) getPayStep.get(i);
//			paramMap.put("PERSON_ID", experienceInside.get("PERSON_ID"));
//			paramMap.put("POST_GRADE_NO", experienceInside.get("POST_GRADE_NO"));
//			paramMap.put("POSITION_NO", experienceInside.get("POSITION_NO"));
//			paramMap.put("POST_NO", experienceInside.get("POST_NO"));
//			paramMap.put("EXP_INSIDE_NO", experienceInside.get("EXP_INSIDE_NO"));
//			paramMap.put("OLD_POST_GRADE_NO", experienceInside.get("OLD_POST_GRADE_NO"));
//			paramMap.put("PAY_STEP_NO", experienceInside.get("PAY_STEP_NO"));
//			paramMap.put("CREATED_BY", experienceInside.get("CREATED_BY")); 
//			paramMap.put("START_DATE", experienceInside.get("START_DATE"));
//			paramMap.put("ACTIVITY", "1"); 
//			transferOrderSer.savePayStepForTimer(paramMap);
//			
//		}
	}
}
