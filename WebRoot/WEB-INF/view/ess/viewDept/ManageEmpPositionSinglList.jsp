<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<div style="background-color: #fff;">
<!-- <div class="pageContent"> -->
		<table class="user_table" width="100%">
			<tr>
				<td width="25%" class="td_title">
					<!-- 姓名： --> <spring:message code="alert.pa.pasalarycanshu.xingming" />
				</td>
				<td width="25%" class="td_type">
					 ${empInfo.LOCAL_NAME} 
				</td>
				<td width="25%" class="td_title">
					<!-- 工号： --> <spring:message code="ess.infoApply.EMPID" />
				</td>
				<td width="25%" class="td_type">
					${empInfo.EMPID} 
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<!-- 部门 --> <spring:message code="ess.infoApply.DEPT" />
				</td>
				<td class="td_type">
					${empInfo.DEPT_NAME} 
				</td>
				<td class="td_title">
					<!-- 职级 --> <spring:message code="ess.infoApply.Rank" />
				</td>
				<td class="td_type">
					${empInfo.POST_GRADE_NO} 
				</td>
			</tr>
		</table>
		
	

</div>

<div style="background-color: #fff;width:100% ">

	<table class="list" layoutH="110" border="1" width="100%"
		nowrapTD="false">
		<thead>
			<tr>
				<th>
					<!-- 命令日期 --> <spring:message code="hrm.empinfo.starter_START_DATE" />
				</th>

				<th>
					<!-- 人事命令 --> <spring:message code="org.title.EXPERIENCE_TYPE_NAME" />
				</th>
				<th>
					<!-- 部门： --> <spring:message code="ess.infoApply.DEPT" />
				</th>
				<th>
					<!-- 主要业务 --> <spring:message code="org.title.MAIN_BUSINESS" />
				</th>
				<th>
					<!-- 职级 --> <spring:message code="ess.infoApply.Rank" />
				</th>


			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ManageEmpPositionSinglList}" var="personList"
				varStatus="i">
				<tr target="sid" rel="">
					<td style="text-align: center">

						${personList.START_DATE}
					</td>
					<td style="text-align: center">

						${personList.TRANS_CODE}
					</td>
					<td style="text-align: center">

						${personList.DEPT_NAME}
					</td>

					<td style="text-align: center">
						${personList.MAIN_BUSINESS}

					</td>
					<td style="text-align: center">

						${personList.POST_GRADE}
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>