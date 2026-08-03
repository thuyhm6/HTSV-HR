<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

 <script type="text/javascript">

function changePic(photo){
	document.getElementById(photo).src = '/resources/photo/default.jpg';
}
</script>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="user_photo">
			<p>
			<img align="middle" id="${photoId}" src="${PhotoPath}" onerror="changePic(this.id);"/>
			</p>
		</td> 
		<td>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
				<tr>
					<td class="td_title" >
						<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
						<!--工号  -->
					</td>
					<td class="td_type" width="15%">
							${personInfo.EMPID }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门-->
					</td>
					<td class="td_type">
						${personInfo.DEPTNO_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DATE_STARTED"/>
						<!--入司日期 即子公司入职日期 -->
					</td>
					<td class="td_type">
						${personInfo.JOIN_COMPANY_DATE }
					</td>
					<td class="td_title">
						<spring:message
							code="hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY" />
						<!--司内工作年资-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.INNER_WORK_YEAR }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewHire.title.LOCALNAMEANDPINYIN"/>
						<!--姓名  (拼音)-->
					</td>
					<td class="td_type">
						${personInfo.LOCAL_NAME }(${personInfo.CHINESE_PINYIN})
					</td>
					<td class="td_title">
						<spring:message
							code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" />
						<!--职等-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.GRADE_LEVEL_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" />
						<!--员工状态-->
					</td>
					<td class="td_type" width="15%">
						${personInfo.EMP_OFFICE_NAME }
					</td>
					<td  class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.EMPLOYMENT_TYPE"/>
						<!--雇佣类型-->
					</td>
					<td>
						${personInfo.EMPLOYMENT_NAME }
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
						<!--身份证号-->
					</td>
					<td class="td_type">
						${personInfo.IDCARD_NO }
					</td>
					<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
						<!--职责-->
					</td>
					<td class="td_type">
						 ${personInfo.DUTY_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE" />
						<!--现部门异动日期-->
					</td>
					<td class="td_type">
						${personInfo.NOW_DEPARTMENT_DATE }
					</td>
					<td class="td_title">
						<%-- <spring:message code="hr.viewPersonalInfo.title.WORK_TIME_TYPE"/>--%>
						<!--工作时间类型  -->
					</td>
					<td class="td_type" width="15%">
						<%-- ${personInfo.WORK_TIME_NAME } --%>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.SEX" />
						<!--性别-->
					</td>
					<td class="td_type">
						${personInfo.SEX_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
						<!--职级(GGS)-->
					</td>
					<td class="td_type">
						 ${personInfo.POST_GRADE_NAME }
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.ADVANCEMENT_DATE"/>
						<!--晋升日期-->
					</td>
					<td>
						${personInfo.PROMOTION_DATE}
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
						<!--契约类型 （${personInfo.CONTRACT_NAME } 契约名称）-->
					</td>
					<td class="td_type">
						${personInfo.CONTRACT_NAME } 
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
					</td>
					<td class="td_type">
						${personInfo.DOB }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)  -->
					</td>
					<td class="td_type">
						 ${personInfo.POST_NAME }
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
						<!--离职日期-->
					</td>
					<td class="td_type">
						${personInfo.DATE_LEFT }
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
						<!--详细人力区分 -->
					</td>
					<td class="td_type" width="15%">
						${personInfo.EMP_TYPE_NAME }
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>