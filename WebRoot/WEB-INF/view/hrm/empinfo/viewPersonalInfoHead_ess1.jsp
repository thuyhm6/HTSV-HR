<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<input type="hidden" id="isEssSystem" value="${isEssSystem }"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="50">
	<tr>
		<td valign="top">
		<br/>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
				<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="alert.pa.pasalarycanshu.xingming" />
						<!-- 姓名 -->
					</td>
					<td class="td_type" width="%30">
						${personInfo.LOCAL_NAME } <c:if
					test="${not empty personInfo.ENGLISH_NAME}">(${personInfo.ENGLISH_NAME })</c:if>
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/>
						<!-- 社号 -->  
					</td>
					<td class="td_type" width="30%">
							${personInfo.EMPID}
					</td>
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!-- 部门 -->  
					</td>
					<td class="td_type" width="30%">
							${personInfo.DEPTNO }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<!-- 职级 --><spring:message code="ess.infoApply.Rank" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.POST_GRADE_NO_NAME}
					</td>
					<!--<td <c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">rowspan="7" </c:if> 
				<c:if test="${LoginUser.cpnyId ne 'SPC_SH' || LoginUser.cpnyId ne 'SPC_HZ'}">rowspan="6" </c:if> width="10%" class="td_type">
										<img src="${personInfo.PHOTO_PATH}" width="116px"
											height="149px" />

					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message
							code="hr.empinfo.chinese.name" />
						中文姓名
					</td>
					<td class="td_type" width="%30">
						${personInfo.LOCAL_NAME } <c:if
					test="${not empty personInfo.ENGLISH_NAME}">(${personInfo.ENGLISH_NAME })</c:if>
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/>
						社号  
					</td>
					<td class="td_type" width="30%">
							${personInfo.EMPID}
					</td>
				</tr>
				
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						部门  
					</td>
					<td class="td_type" width="30%">
							${personInfo.DEPTNO }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					部门长<spring:message code="hrm.empinfo.HEAD_DEPARTMENT" />
					</td>
					<td class="td_type" width="30%">
                       ${personInfo.HEAD_DEPARTMENT }
					</td>
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
					职级<spring:message code="ess.infoApply.Rank" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.POST_GRADE_NO_NAME}
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					岗位<spring:message code="ess.message.jobStations" />
					</td>
					<td class="td_type" width="30%">
                     ${personInfo.DUTY_NO_NAME }
					</td>
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
					职责<spring:message code="hrm.contract.POSITION_NO" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.POSITION_NO_NAME }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					入社日期<spring:message code="ess.empInfo.date_of_agency" />
					</td>
					<td class="td_type" width="30%">
                        ${personInfo.DATE_STARTED }
					</td>
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
					在职时间<spring:message code="ess.empInfo.in_service_time" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.WORK_TIME }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					上次晋升日期<spring:message code="ess.empInfo.date_of_last_promotion" />
					</td>
					<td class="td_type" width="30%">
                        ${personInfo.PROMOTION_DAY }
					</td>
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
					当前合同日期<spring:message code="ess.empInfo.current_contract_date" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.START_CONTRACT_DATE }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					合同到期日期<spring:message code="ess.empInfo.expiration_date_of_contract" />
					</td>
					<td class="td_type" width="30%">
                        ${personInfo.END_CONTRACT_DATE }
					</td>
				</tr>
			<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
					上次号俸日期<spring:message code="ess.empInfo.last_of_date_salary" />
					</td>
					<td class="td_type" width="30%">
						${personInfo.LAST_HAOFENG_DATE }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
					外派信息<spring:message code="ess.empInfo.assignment_information" />
					</td>
					<td class="td_type" width="30%">
                        ${personInfo.ASSIGNMENT_INFORMATION }
					</td>
				</tr>
			</c:if>
			
			</table>
		</td>
	--></tr>
</table>


