<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<div class="pageContent">
<input type="hidden" id="isEssSystem" value="${isEssSystem }"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="50">
	<tr>
		<td valign="top">
		<br/>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message
							code="hr.viewPersonalInfo.title.LOCAL_NAME" />
						<!--中文姓名-->
					</td>
					<td class="td_type" width="%30">
						${LoginUser.localName}
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/>
						<!--社号  -->
					</td>
					<td class="td_type" width="30%">
							${LoginUser.empID}
					</td>
				</tr>
				
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门  -->
					</td>
					<td class="td_type" width="30%">
							${LoginUser.content }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<!--部门长--><spring:message code="hrm.empinfo.HEAD_DEPARTMENT"/>
					</td>
					<td class="td_type" width="30%">
                       ${LoginUser.headDepartment }
					</td>
				</tr>
				<tr>
				    <td style="text-align:right" class="td_title" width="20%">
						<!--职群--><spring:message code="ess.empInfo.zhiqun"/>
					</td>
					<td class="td_type" width="30%">
                     ${LoginUser.postFamilyName }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<!--职级--><spring:message code="hrm.contract.Rank"/>
					</td>
					<td class="td_type" width="30%">
						${LoginUser.postGradeName}
					</td>					
				</tr>
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<!--职责--><spring:message code="org.title.POSITION_NO"/>
					</td>
					<td class="td_type" width="30%">
						${LoginUser.positionNoName }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<!--入社日期--><spring:message code="hrm.empinfo.DATE_STARTED"/>
					</td>
					<td class="td_type" width="30%">
                        ${LoginUser.dateSrarted }
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

