<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	工会信息表
</title>
</head>     
<body>
 <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=laborUnion.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
  %>


	<table id="1" class="table" width="60%" border="1">
		    
		    <tr>
		        <td style="text-align:center;" colspan="7"><font size="4" color="black">工会报表</font></td>
		    </tr>
		    
			<tr>
				<td>部门</td>
				<td>姓名</td>
				<td>员工号</td>
				<td>入职日期</td>
				<td>离职日期</td>
				<td>入会日期</td>
				<td>离会日期</td>				
			</tr>			
			
			<c:forEach var="itemList" items="${itemList}">
				<tr>
	
					
					<td style="text-align:left">${itemList.DEPTNAME}</td>
					<td style="text-align:left">${itemList.LOCAL_NAME} </td>
					<td style="text-align:left">${itemList.EMPID} </td>
					<td style="text-align:left">${itemList.DATE_STARTED}</td>
					<td style="text-align:left">${itemList.DATE_LEFT}</td>
					<td style="text-align:left">${itemList.START_DATE}</td>
					<td style="text-align:left">${itemList.END_DATE}</td>
					
				</tr>
			</c:forEach>
		
			
			

	</table>

	

	
</div>

	<c:set value="/laborUnion/laborUnionInfo/laborUnionListExcel" var="pageUrl"/>
	
	
</body>
</html>