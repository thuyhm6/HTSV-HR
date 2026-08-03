 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--乐天玛特异常明细-人员情况--%>
	乐天玛特异常明细-人员情况
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=LotteMart-renyuanqingkuang.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="5" >
		    			<b><font size="+2">乐天玛特${YEAR }年${MONTH }月，${DISTINGUISH_NAME }人员情况</font></b>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="center">月末在职人数：</td>
					<td align="center">${sysEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center" colpan="2">月末在职人数分析：</td>
		    	</tr>
		    	<tr>
		    		<td align="center">上月末在职人数：</td>
					<td align="center">${lastEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">店内合同工人数：</td>
					<td align="center">${normalEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月新增人数：</td>
					<td align="center">${ruZhiEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">店内劳务工人数：</td>
					<td align="center">${laborEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月离职人数：</td>
					<td align="center">${liZhiEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">店内计时工人数：</td>
					<td align="center">${hourEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月调入人数：</td>
					<td align="center">${diaoRuEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">派入人数：</td>
					<td align="center">${disInEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月调出人数：</td>
					<td align="center">${diaoChuEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">店内小计：</td>
					<td align="center">${totalEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月1日离职生效人数：</td>
					<td align="center">${liZhiFirstEmpListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">外派人数：</td>
					<td align="center">${disOutEmpListCnt }</td>
		    	</tr>
		    	<tr>
		    		<td align="center">本月计薪人数：</td>
					<td align="center">${jiXinListCnt }</td>
					<td align="center">&nbsp;</td>
					<td align="center">实发工资为0人数：</td>
					<td align="center">${actualSalaryZeroCnt }</td>
		    	</tr>
		    </table>
		    <br/>
		    <table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
		    	<tr><%-- 入职人员信息 --%>
		    		<td style="text-align: center" colspan="7">
		    			<font color="blue">入职人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">入职日期</td>
					<td align="center">工资标准</td>
					<td align="center">基本工资</td>
				</tr>        
		       	<c:forEach items="${ruZhiEmpList}" var="new" varStatus="i">
					<tr>
						<td style="text-align: center">${new.DISTINGUISH_NAME }</td>
						<td style="text-align: center">${new.DEPARTMENT }</td>
						<td style="text-align: center">${new.EMPID }</td>
						<td style="text-align: center">${new.LOCAL_NAME }</td>
						<td style="text-align: center">${new.DATE_STARTED }</td>
						<td style="text-align: center">${new.STANDARD_SALARY }</td>
						<td style="text-align: center">${new.BASE_SALARY }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(ruZhiEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(ruZhiEmpList)}" var="new" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 离职人员信息 --%>
		    		<td style="text-align: center" colspan="8">
		    			<font color="blue">离职人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">入职日期</td>
					<td align="center">离职日期</td>
					<td align="center">工资标准</td>
					<td align="center">基本工资</td>
				</tr>        
		       	<c:forEach items="${liZhiEmpList}" var="left" varStatus="i">
					<tr>
						<td style="text-align: center">${left.DISTINGUISH_NAME }</td>
						<td style="text-align: center">${left.DEPARTMENT }</td>
						<td style="text-align: center">${left.EMPID }</td>
						<td style="text-align: center">${left.LOCAL_NAME }</td>
						<td style="text-align: center">${left.DATE_STARTED }</td>
						<td style="text-align: center">${left.DATE_LEFT }</td>
						<td style="text-align: center">${left.STANDARD_SALARY }</td>
						<td style="text-align: center">${left.BASE_SALARY }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(liZhiEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(liZhiEmpList)}" var="left" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 调入人员信息 --%>
		    		<td style="text-align: center" colspan="7">
		    			<font color="blue">调入人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">调入日期</td>
					<td align="center">工资标准</td>
					<td align="center">基本工资</td>
				</tr>        
		       	<c:forEach items="${diaoruEmpList}" var="diaoru" varStatus="i">
					<tr>
						<td style="text-align: center">${diaoru.new_distinguish }</td>
						<td style="text-align: center">${diaoru.new_dept }</td>
						<td style="text-align: center">${diaoru.empid }</td>
						<td style="text-align: center">${diaoru.local_name }</td>
						<td style="text-align: center">${diaoru.start_date }</td>
						<td style="text-align: center">${diaoru.STANDARD_SALARY }</td>
						<td style="text-align: center">${diaoru.BASE_SALARY }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(diaoruEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(diaoruEmpList)}" var="diaoru" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 调出人员信息 --%>
		    		<td style="text-align: center" colspan="7">
		    			<font color="blue">调出人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">调出日期</td>
					<td align="center">工资标准</td>
					<td align="center">基本工资</td>
				</tr>        
		       	<c:forEach items="${diaochuEmpList}" var="diaochu" varStatus="i">
					<tr>
						<td style="text-align: center">${diaochu.old_distinguish }</td>
						<td style="text-align: center">${diaochu.old_dept }</td>
						<td style="text-align: center">${diaochu.empid }</td>
						<td style="text-align: center">${diaochu.local_name }</td>
						<td style="text-align: center">${diaochu.start_date }</td>
						<td style="text-align: center">${diaochu.STANDARD_SALARY }</td>
						<td style="text-align: center">${diaochu.BASE_SALARY }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(diaochuEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(diaochuEmpList)}" var="diaochu" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 派入人员信息 --%>
		    		<td style="text-align: center" colspan="6">
		    			<font color="blue">派入人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">社保地</td>
					<td align="center">工作地</td>
				</tr>        
		       	<c:forEach items="${disInEmpList}" var="disru" varStatus="i">
					<tr>
						<td style="text-align: center">${disru.DISTINGUISH_NAME }</td>
						<td style="text-align: center">${disru.DEPARTMENT }</td>
						<td style="text-align: center">${disru.EMPID }</td>
						<td style="text-align: center">${disru.LOCAL_NAME }</td>
						<td style="text-align: center">${disru.SOCIAL_SECURITY }</td>
						<td style="text-align: center">${disru.WORK_AREA }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(disInEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(disInEmpList)}" var="disru" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 派出人员信息 --%>
		    		<td style="text-align: center" colspan="6">
		    			<font color="blue">派出人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">社保地</td>
					<td align="center">工作地</td>
				</tr>        
		       	<c:forEach items="${disOutEmpList}" var="dischu" varStatus="i">
					<tr>
						<td style="text-align: center">${dischu.DISTINGUISH_NAME }</td>
						<td style="text-align: center">${dischu.DEPARTMENT }</td>
						<td style="text-align: center">${dischu.EMPID }</td>
						<td style="text-align: center">${dischu.LOCAL_NAME }</td>
						<td style="text-align: center">${dischu.SOCIAL_SECURITY }</td>
						<td style="text-align: center">${dischu.WORK_AREA }</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(disOutEmpList)<5 }">
					<c:forEach begin="1" end="${5-fn:length(disOutEmpList)}" var="dischu" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
	      </table>
	      <br/>
			<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr><%-- 实发工资为0的人员信息 --%>
		    		<td style="text-align: center" colspan="7">
		    			<font color="blue">实发工资为零的人员信息</font>
		    		</td>
		    	</tr>
				<tr>
					<td align="center">门店名称</td>
					<td align="center">部门</td>
					<td align="center">工号</td>
					<td align="center">姓名</td>
					<td align="center">员工状态</td>
					<td align="center">入职日期</td>
					<td align="center">离职日期</td>
				</tr>        
		       	<c:forEach items="${actualSalaryZero}" var="actual" varStatus="i">
					<tr>
						<td style="text-align: center">${actual.DISTINGUISH_NAME }</td>
						<td style="text-align: center">${actual.DEPARTMENT }</td>
						<td style="text-align: center">${actual.EMPID }</td>
						<td style="text-align: center">${actual.LOCAL_NAME }</td>
						<td style="text-align: center">${actual.STATUS }</td>
						<td style="text-align: center">${actual.DATE_STARTED }</td>
						<td style="text-align: center">${actual.DATE_LEFT }</td>
					</tr>
				</c:forEach>
				<c:if test="${actualSalaryZeroCnt<5 }">
					<c:forEach begin="1" end="${5-actualSalaryZeroCnt}" var="dischu" step="1">
						<tr>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
							<td style="text-align: center">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>