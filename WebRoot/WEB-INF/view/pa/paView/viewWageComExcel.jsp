 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工资监控</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=viewWageComExcel.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
					<th width="18%"><spring:message code="public.title.name"/><!--姓名--></th>
					<th width="18%"><spring:message code="public.title.empId"/><!--工号--></th>
					<th width="18%"><spring:message code="public.title.deptName"/><!--部门--></th>
					<th width="18%"><spring:message code="pa.viewWageCom.Wageproject"/><!--工资项目 --></th>
					<th width="18%"><spring:message code="pa.viewWageCom.Lastmonthdata"/><!--上月数据--></th>
					<th width="18%"><spring:message code="pa.viewWageCom.monthdata"/><!--本月数据--></th>
					<th width="18%"><spring:message code="pa.viewWageCom.Differencetwo"/><!--差额--></th>
					<th width="18%"><spring:message code="pa.viewWageCom.Percentage"/><!--百分比--></th>
	</tr>
		<c:forEach items="${getListExcel}" var="getList" varStatus="i">
				<tr>	
					<td>${getList.LOCAL_NAME }</td>
					<td>${getList.PERSONID }</td>
					<td>${getList.DEPTNAME }</td>
					<td>${getList.ITEM }</td>
					<td>${getList.SHANGYUE }</td>
					<td>${getList.BENYUE }</td>
					<td>${getList.CHAE }</td>
					<td>${getList.BAIFENBI }%</td>
				</tr>
			</c:forEach>
</table>
</body>
</html>