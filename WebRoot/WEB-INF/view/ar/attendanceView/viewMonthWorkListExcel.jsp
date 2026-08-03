 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%--月考勤查看（个人）信息--%>
			月考勤查看（个人）信息
		</title>
	</head>                              
	<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=workMonthList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="9" >
	    			<b><font size="+2">月考勤查看（个人）信息</font></b>
	    		</td>
	    	</tr>
			<c:forEach items="${monthWorkList}" var="monthWorkList" varStatus="i">
	   <tr>
         <td>工号</td><td>  ${monthWorkList.EMPID}       </td>
             
         <td>姓名</td><td>  ${monthWorkList.LOCAL_NAME}       </td>
         
         <td>职位</td><td>  ${monthWorkList.POSITION_NAME}       </td>
         
         <td>部门 </td><td>${monthWorkList.DEPT_NAME}         </td>
      </tr>
      
      <tr>
         <td>计划上班天数</td><td>${monthWorkList.STAND_SCHEDULED_DAYS}         </td>
             
         <td>实际上班天数</td><td>  ${monthWorkList.ACTUAL_WORK_DAYS}       </td>
         
         <td>早退次数</td><td>  ${monthWorkList.EARLYDAY_COUNT}       </td>
         
         <td>迟到次数 </td><td>  ${monthWorkList.TARDINESS_COUNT}       </td>
      </tr>
      
      <tr>
         <td>计划上班时数</td><td>  ${monthWorkList.SHIFT_TIME_TOTAL}       </td>
             
         <td>实际上班时数</td><td>  ${monthWorkList.ACTUAL_WORK_HOURS}       </td>
         
         <td>加班时间</td><td>    ${monthWorkList.EMPID}     </td>
         
         <td>年假使用次数 </td><td>  ${monthWorkList.ANNUAL_VACATION}       </td>
      </tr>
	     
		<thead>
			<tr>
			    <th>序号</th>
				<th>日期</th>
				<th>考勤区间</th>
				<th>班次</th>
				<th>班次上班时间</th>
				<th>班次下班时间</th>
				<th>实际上班时间</th>
				<th>实际下班时间</th>
				<th>实际考勤时间</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${monthWorkList.everyDayWorkList}" var="edwl" varStatus="i">
				<tr>
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${edwl.AR_DATE_STR}</td>
					<td style="text-align: center">${edwl.STAT_NAME}</td>
					<td style="text-align: center">${edwl.SHIFT_NAME}</td>
					<td style="text-align: center">${edwl.AR_FROM_TIME}</td>
					<td style="text-align: center">${edwl.AR_TO_TIME}</td>
					<td style="text-align: center">${edwl.FROMTIME}</td>
					<td style="text-align: center">${edwl.TOTIME}</td>
					<td style="text-align: center">${edwl.QUANTITY}</td>
				</tr>
			</c:forEach>  
			  
		</tbody>
		</c:forEach>
      </table>  
	</body>
</html>