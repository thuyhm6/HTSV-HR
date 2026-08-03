 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	临时职人员发令数据导入结果
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=tempEmpTransferOrderList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="8%">社编*</th>						
				<th width="8%">发令日期*</th>
				<th width="12%">发令原因*</th>
				<th width="16%">新部门</th>
				<th width="5%">新人员类型</th>
				<th width="5%">新班号</th>
				<th width="6%">新职责</th>
				<th width="3%">新级号</th>
				<th width="3%">新级号等级</th>
				<th width="3%">新基本工资</th>
				<th width="3%">新变动工资</th>
				<th width="3%">新ID卡号</th>
				<th width="3%">新职务</th>
				<th width="12%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="5%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="5%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">			
				<tr>
					<td class='td_center'>${mdata.EMPID}</td>
					<td class='td_center'>${mdata.START_DATE}</td>
					<td>${mdata.TRANSFER_ORDER_REASON}</td>								
					<td>${mdata.CUR_DEPTNO}</td>
					<td>${mdata.CUR_EMP_TYPE_CODE}</td>
					<td class='td_center'>${mdata.CUR_SHIFT_NO}</td>	
					<td class='td_center'>${mdata.CUR_POSITION_NO}</td>														
					<td class='td_center'>${mdata.CUR_PAY_GRADE}</td>
					<td class='td_center'>${mdata.CUR_PAY_STEP}</td>
					<td class='td_center'>${mdata.CUR_BASE_PAY}</td>
					<td class='td_center'>${mdata.CUR_VARB_PAY}</td>
					<td class='td_center'>${mdata.CUR_ID_CARD_NO}</td>
					<td class='td_center'>${mdata.CUR_JOB_TITLE_CD}</td>
					<td>${mdata.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>	
				</tr>			
			</c:forEach>			
		</tbody>
	</table>  
	</td>
  </tr>
</table>
</body>
</html>