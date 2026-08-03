 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年资等级信息导出</title>
<style type="text/css">
	td {
		text-align: center;
		font-size: 13;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=paGradeHaoFeng.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
					
	<table border="1" class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="30"><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th width="120"><!--法人 --><spring:message code="sys.essParam.title.legalPerson" /></th>
				<th width="200"><!--职级 --><spring:message code="ess.trans.title.postGradeName" /></th>
				<th width="100"><!--年资等级 --><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z" /></th>
				<th width="100"><!--开始月份 --><spring:message code="hrm.empinfo.START_YEAR_MONTH" /></th>
				<th width="100"><!--结束月份--><spring:message code="hrm.empinfo.END_YEAR_MONTH" /></th>
				<th width="140" ><spring:message code="pa.salary.canShu.jibengongzi"/><!--基本工资--></th>
				<th width="200"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${haoFengInfo}" var="item" varStatus="i">
				<tr target="PQD_NO" rel="${item.POST_GRADE_NO}">
					<td ><center>${i.index + 1}</center></td>
					<td ><center>${item.CPNY_ID}</center></td>
					<td ><center>${item.POST_GRADE_NO}</center></td>
					<td style="vnd.ms-excel.numberformat:@;"><center>${item.PAY_STEP}</center></td>
					<td ><center>${item.START_MONTH}</center></td>
					<td ><center>${item.END_MONTH}</center></td>
					 <td><center>${item.BASE_PAY}</center></td>
					<td ><center><c:if test="${item.ACTIVITY eq '1'}"><!--启用--><spring:message code="org.title.START" /></c:if><c:if test="${item.ACTIVITY eq '0'}"><!--未启用 --><spring:message code="sys.arAffirmPost.title.enable" /></c:if></center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
 
 