<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>

<style>
	table{border-color:#5588AA;}
	table tr{height:25px;}
	table td{border-color:#5588AA}
	.con01{text-align: center; font-size:11pt;background: #D0DAEB;padding:5px;}
	.con02{text-align: left;background: #E1EFCB;padding:5px;}
	
	#loading{width:90%;text-align:center;padding-top: 200px;position: absolute;}
	.l-page-top{width:100%; height:20px; }
	#mainBody{padding-top: 20px;padding-bottom: 80px;}
	#save{width:60px; float:right;margin-right:15px;}
	.yans{background-color:rgb(245, 251, 254);color:#276fa4;}
</style>
<div class="pageContent">				
	<div class="pageFormContent" layoutH="56">
		<div class="panel">
						
					<h1>
						<spring:message code="hr.viewHire.title.COMPANYINFORMATIONIN" />
						<!--司内信息  -->
					</h1>
		<div>
	    <table id="info" border='1' width="100%" 
	    		cellspacing="0" cellpadding="0" class="user_table">
	    	<tr>
		    	<td width="10%" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.EMPID" /><!-- 社号 --></td>
		    	<td width="15%">
		    	     ${person.EMPID }
		    	</td>
		    	<td width="10%" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" /><!--职等--></td>
		    	<td  width="15%">
		    		${person.GRADE_LEVEL_NAME }&nbsp;
		    	</td>
		    	<td width="10%" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></td>
		    	<td  width="15%">
		    		${person.DEPTNO_NAME}
		    	</td>
		    	<td  width="15%"  class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.EMPLOYMENT_TYPE"/><!--雇佣类型--></td>
		    	<td  width="15%">
		    		${person.EMPLOYMENT_NAME}
		    	</td>
		    </tr>
		    <tr>
		    	<td width="150" class="td_center yans"><spring:message code="hr.viewHire.title.LOCALNAMEANDPINYIN"/><!--姓名  (拼音)--></td>
		    	<td >
                   ${person.LOCAL_NAME}(&nbsp;${person.CHINESE_PINYIN}&nbsp;)&nbsp;
				</td>
				<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/><!--职责--></td>
		    	<td >
		    		${person.DUTY_NAME}&nbsp;    				    		
		    	</td>
		    	<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.ENTRY_DATE"/><!--入司日期  --></td>
		    	<td >
		    		${person.JOIN_COMPANY_DATE}&nbsp; 
		    	</td>
		    	<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/><!--契约类型--></td>
		    	<td >
		    		${person.CONTRACT_NAME }&nbsp; 
		    	</td>
		    </tr>	
		    <tr>
		    	<td width="10%" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" /><!--身份证号--></td>
		    	<td width="10%">
	                ${person.IDCARD_NO}&nbsp;
		    	</td>
		    	<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME" />
								<!--职级(GGS)--></td>
		    	<td >
		    		${person.POST_GRADE_NAME}&nbsp;
		    	</td>
		    	<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.DOB" /><!--出生日期--></td>
		    	<td >
					${person.DOB}
				</td>
		    	<td class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.WORKING_TIME"/><!--工时制--></td>
		    	<td>
					${person.WORKING_TIMET_NAME }
		    	</td>
		    </tr>	
		    <tr>
		    	<td width="100" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.SEX" /><!--性别--></td>
		    	<td>
		    		${person.SEX_NAME}
 		    	</td>
 		    	<td width="150" class="td_center yans"><spring:message code="hr.viewPersonalInfo.title.POST_NAME" /><!--职级名称--></td>
		    	<td >
		    		${person.POST_NAME}&nbsp;
		    	</td>
 		    	
		    	<td width="150" class="td_center yans"></td>
		    	<td></td>
				<td class="td_center yans"><spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/><!--详细人力区分 --></td>
		    	<td>
					${person.EMP_TYPE_NAME }
		    	</td>
		    </tr>	
		</table>
		</div>
		</div>
		<div class="panel">
	    	<h1>
				<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION_DETAIL" />
				<!--个人基础信息-->
			</h1>	
			<div>
	    <table id="info" cellspacing="0" cellpadding="0" border="1" width="100%" class="user_table">		
				<tr>
					<td width="10%"  class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.ENGLISH_NAME" /><!--英文名 -->
					</td>
					<td  width="15%">
						${person.ENGLISH_NAME }
					</td>
					<td width="10%"  class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.WHETHER_FOREIGNERS" />
						<!--是否外国人 -->
					</td>
					<td  width="15%" >
						${person.WHETHER_FOREIGNERS_NAME }
					</td>
					<td width="10%"  class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
						<!--国籍-->
					</td>
					<td  width="15%">
						${person.NATIONALITY_NAME }
					</td>
					<td width="15%"  class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.NATION_NAME" />
						<!--民族 -->
					</td>
					<td  width="15%" >
						${person.NATION_NAME }
					</td>
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
						<!--户口性质-->
					</td>
					<td >
						${person.REG_TYPE_NAME }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
						<!--现住址(邮编)-->
					</td>
					<td  colspan="5">
						${person.PRESENT_ADDRESS_PROVINCE_NAME }&nbsp;${person.HOME_ADDRESS}
					</td>
					
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
						<!--手机号码-->
					</td>
					<td >
						${person.CELLPHONE }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
						<!--户口所在地(邮编)-->
					</td>
					<td  colspan="5">
						${person.ANMELDEN_PROVINCE_NAME }&nbsp;${person.REG_PLACE}
					</td>
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="hr.viewHire.title.HOME_PHONE" />
						<!-- 家庭电话 -->
					</td>
					<td >
						${person.HOME_PHONE }
					</td>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.EMAIL" />
						<!--E-mail-->
					</td>
					<td >
						${person.EMAIL }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" />
						<!--是否共产党员-->
					</td>
					<td >
						${person.WHETHER_COMMUNIST_NAME }
					</td>
					<td class="td_center yans">
						
					</td>
					<td  >
						
					</td>
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />
						<!--最终学历-->
					</td>
					<td >
						${person.FINAL_DEGREE_NAME }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.FINAL_SUBJECT_NAME" />
						<!--最终专业-->
					</td>
					<td >
						${person.FINAL_SUBJECT_NAME }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />
						<!--最终学校-->
					</td>
					<td >
						${person.FINAL_SCHOOL }
					</td>
					<td class="td_center yans">
						
					</td>
					<td >
						
					</td>
			</table>    	
	   </div>
	   </div>
	    <div class="panel">
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.work_information" />
			<!--工作信息 -->
		</h1>
		<div>
		<table id="info" cellspacing="0" cellpadding="0" border="1" width="100%" class="user_table">
				<tr>
					<td width="10%" class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.JOB_TYPE" />
						<!--职种-->
					</td>
					<td width="15%">
						${person.JOB_TYPE_NAME }
					</td>
					<td width="10%" class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
						<!--职(岗)位-->
					</td>
					<td  width="15%">
						${person.POSITION_NO_NAME }
					</td>
					<td width="10%" class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.JOIN_TYPE_NAME" />
						<!--入职类型-->
					</td>
					<td  width="15%">
						${person.JOIN_TYPE_CODE_NAME }
					</td>
					<td width="15%" class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE" />
						<!--预转正日期-->
					</td>
					<td  width="15%">
						${person.BEFORE_END_PROBATION_DATE }
					</td>
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/>
						<!-- 是否试用 -->
					</td>
					<td  width="15%">
						${person.IN_THE_DIFFERENCE }
					</td>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.SOCIAL_INSURANCE_ADDRESS" />
						<!--社会保险地-->
					</td>
					<td  width="15%">
						${person.SOCIAL_SECURITY_AREA_NAME}
					</td>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.HIRE_PATH"/>
						<!--雇佣路径-->
					</td>
					<td  width="15%">
						${person.REC_SOURCE_DETAIL_NO_NAME }
					</td>
					<td class="td_center yans">
						<spring:message code="liang.hr.viewPersonalInfo.title.DETAIL_HIRE_PATH" />
						<!--详细雇佣路径-->
					</td>
					<td  width="15%">
						${person.RECRUITMENT_SOURCE_TYPE }
					</td>
				</tr>
				<tr>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME" />
						<!--工作地-->
					</td>
					<td>
						${person.WORK_AREA_NAME }
					</td>
					<td class="td_center yans">
						<spring:message code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" />
						<!--保险类型-->
					</td>
					<td>
						${person.INSURANCE_TYPE_CODE_NAME }
					</td>
					<td class="td_center yans">
					</td>
					<td>
					</td>
					<td class="td_center yans">
					</td>
					<td>
					</td>
				</tr>
			</table>
			</div>
			</div>
		 </div>
	    <div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>	
			</ul>
		</div>
</div>
		
		
		
		
		
		
		
	    <!--<table id="info" cellspacing="2" cellpadding="2" border="1" width="100%" align="center">
	    	<tr>
	    		<td width="10%">身份证号</td>
		    	<td width="10%">
	                ${person.IDCARD_NO}&nbsp;
		    	</td>
		    	<td width="10%">出生日期</td>
		    	<td width="10%">
		    	    ${person.DOB }&nbsp;
				</td>
		    	<td width="10%">入职类型</td>
		    	<td width="10%">
                     ${person.JOIN_TYPE_AME }&nbsp;
		    	</td>
		    	<td width="10%">有无独生子女证</td>
		    	<td width="10%">
		    	     ${person.SINGLETON_FEMALE_CARD_NAME} &nbsp;
 		    	</td>
		    	<td width="10%">参加工作起始日期</td>
		    	<td width="10%">
					${person.EXP_DATE}&nbsp;
		    	</td>
	    	</tr>
	    	<tr>
	    		<td width="width="100"">性别 </td>
		    	<td>
		    	    ${person.SEX_NAME}
 		    	</td>
		    	<td>纳税地区</td>
		    	<td>
		    	    ${person.TAX_AREA_NAME }
 		    	</td>
		    	<td>公司E_Mail</td>
		    	<td>
					${person.EMAIL }
		    	</td>
		    	<td>工时制度</td>
		    	<td>
                    ${person.MAN_HOUR_SYSTEM_NAME }
		    	</td>
		    	<td>招聘来源</td>
		    	<td>
					${person.RECRUITMENT_SOURCE_TYPE_NAME }
		    	</td>
	    	</tr>
	    	<tr>
	    		<td>民族</td>
		    	<td>
					${person.NATION_NAME }
		    	</td>
		    	<td>转正日期</td>
		    	<td>
					${person.END_PROBATION_DATE } 
		    	</td>
		    	<td>个人E_Mail</td>
		    	<td>
					${person.PER_EMAIL }
		    	</td>
		    	<td>爱好/兴趣</td>
		    	<td>
					${person.HOBBY }
		    	</td>
		    	<td>劳务所属</td>
		    	<td>
			        ${person.SERVICES_BELONG }  
		    	</td>
	    	</tr>
	    	<tr>
	    		<td>婚否</td>
		    	<td>
		    	    ${person.MARITAL_STATUS_NAME }
		    	</td>
		    	<td>预转正日期 </td>
		    	<td>
		    		${person.BEFORE_END_PROBATION_DATE }
		    	</td>
		    	<td>籍贯 </td>
		    	<td>
		    	    ${person.POLITY_NAME }
		    	</td>
		    	<td>住房形式 </td>
		    	<td>
		    	    ${person.HOUSING_STATE_NAME }
 		    	</td>
		    	<td>职类 </td>
		    	<td>
                    ${person.JOB_CLASS_NAME }
		    	</td>	
	    	</tr>
	    	<tr>
		    	<td>有无子女</td>
		    	<td>
		    	    ${person.HAVE_CHILDREN_NAME }
 		    	</td>
		    	<td>现部门异动日期</td>
		    	<td>
					${person.NOW_DEPARTMENT_DATE }
		    	</td>
		    	<td>手机</td>
		    	<td>
					${person.CELLPHONE }
		    	</td>
		    	<td>户口性质</td>
		    	<td>
                    ${person.REG_TYPE_CODE_NAME }
 		    	</td>
		    	<td>生产区分</td>
		    	<td>
                    ${person.PRODUCTION_DISTINGUISH_NAME }
		    	</td>
	    	</tr>
	    	<tr>
		    	<td>入职地</td>
		    	<td>
		    	    ${person.ENTRY_AREA_NAME }
  		    	</td>
		    	<td>就业证号</td>
		    	<td>
		    	    ${person.EMPLOYMENT_PASS_NO }
		    	</td>
		    	<td>家庭电话</td>
		    	<td>
		    	    ${person.HOME_PHONE }
		    	</td>
		    	<td>户口所在地(邮编)</td>
		    	<td>
		    	    ${person.REG_PLACE_NAME }
		    	</td>
		    	<td>国籍</td>
		    	<td>         
		    	    ${person.NATIONALITY_NAME}
		    	</td>
	    	</tr>
	    	<tr>
		    	<td>社保地</td>
		    	<td>
		    	    ${person.SOCIAL_SECURITY_AREA_NAME }
		    	</td>
		    	<td width="100">是否新参统</td>
		    	<td>
		    	    ${person.FIRST_TIME_ATTEND_SOCIETY_NAME }
		    	</td>
		    	<td>办公室电话</td>
		    	<td>
		    	    ${person.OFFICE_PHONE }
		    	</td>
		    	<td>现住址(邮编)</td>
		    	<td>
		    	    ${person.HOME_ADDRESS }
		    	</td>
		    	<td>最终学历</td>
		    	<td>
		    	    ${person.FINAL_DEGREE_NAME }
		    	</td>
	    	</tr>
	    	<tr>
		    	<td>保险类型</td>
		    	<td>
		    	    ${person.INSURANCE_TYPE_NAME }
		    	</td>
		    	<td>护照号</td>
		    	<td>
		    	    ${person.PASSPORT_NO }
		    	</td>
		    	<td>政治面貌</td>
		    	<td>
		    	    ${person.POLITY_NAME }
		    	</td>
		    	<td>法律文书送达地址(邮编)</td>
		    	<td>
		    	    ${person.LEGAL_DOCUMENTS_ADDRESS }
		    	</td>
		    	<td>外国人身份证号</td>
		    	<td>
		    	    ${person.FOREIGNER_IDCARD_NO }
		    	</td>
	    	</tr>
	    </table>
	    -->
	   
