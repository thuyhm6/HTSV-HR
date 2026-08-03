 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--年假信息查看--%>
	年假信息查看
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=ArHrmMasterInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   	<table width="100.8%" class="table" layoutH="100" border="1" >
		<thead>
		  <tr>
		    <th width="3%" rowspan="2">职号</th>
		    <th width="3%" rowspan="2">姓名</th>
		    <th width="3%" rowspan="2">部门</th>
		    <th width="3%" rowspan="2">职系</th>
		    <th width="5%" rowspan="2">入社日期</th>
		    <th colspan="3">工龄明细</th>
		    <th width="6%" rowspan="2">发生基准月</th>
		    <th width="5%" rowspan="2">对象与否</th>
		    <th width="5%" rowspan="2">基本休假</th>
		    <th width="5%" rowspan="2">基本扣除</th>
		    <th width="4%" rowspan="2">新标准</th>
		    <th width="4%" rowspan="2">新扣除</th>
		    <th width="5%" rowspan="2">年假合计</th>
		    <th width="5%" rowspan="2">本年移年年假</th>
		    <th width="5%" rowspan="2">本年清算年假</th>
		    <th width="5%" rowspan="2">移年年假</th>
		    <th width="5%" rowspan="2">移年清算年假</th>
		    <th width="5%" rowspan="2">已休移年年假</th>
		    <th nowrap width="5%" rowspan="2">已休本年年假</th>
		    <th  nowrap width="4%" rowspan="2">剩余年假</th>
		  </tr>
		  <tr>
		    <th width="4%">本公司</th>
		    <th width="5%">其他公司</th>
		    <th width="4%">总计</th>
		  </tr>
		</thead>
		<c:forEach items="${viewLeaveViewList}" var="oneResult" varStatus="i">
		<tbody>
		 	<td class="td_center">${oneResult.EMPID}</td>
			<td nowrap="nowrap">${oneResult.LOCAL_NAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.DEPTNAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.POST_COEFNAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.DATE_STARTED}</td>
			<td nowrap="nowrap">${oneResult.DAY_N}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${oneResult.DAY_W}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${oneResult.DAY}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${yearb}-12-20</td>
			<td nowrap="nowrap">Y</td>
			<td nowrap="nowrap">${oneResult.VAC_STANDARD_OLD}&nbsp;<!--天--><spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_DEDUCT_OLD}&nbsp;<!--天--><spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_STANDARD_NEW}<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_DEDUCT_NEW}&nbsp;<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.TOT_VAC_CNT}&nbsp;<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.NEXT_VAC}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LIQUIDATION}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LAST_YEAR_VAC}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LIQUIDATION_LAST}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.XIUYINIAN}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.XIUBENNIAN}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.SHENGYU}&nbsp;<spring:message code="display.mutual.day"/></td>
		</tbody>
		</c:forEach>
</table>