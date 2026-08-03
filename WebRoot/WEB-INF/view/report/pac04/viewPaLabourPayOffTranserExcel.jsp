 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工在职证明--%>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=GongZiFaFangBiao(LaoWu).xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
   %>
<table width="45%" border="0">
  <tr>
    <td>
    	<table width="45%" border="0" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td colspan="22" >
	    			<font size="+4">LOTTE  CHINA  FOODS  CO.LTD</font>
	    		</td>
		    </tr>
    		<tr>
	    		<td colspan="25" >
	    			<font size="+3">员工工资发放表</font>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td  colspan="5" style="text-align: left">
	    			<font size="+2">乐天(中国)食品有限公司</font>
	    		</td>
	    		<td></td>
	    		<td></td>
	    		<td style="text-align:center" colspan="3"><%--xxxx年xx月--%>
					<font size="+2">${YEAR }年${MONTH }月</font>
				</td>
	    	</tr>
	    	<%
	    		int count=0;		//计数器  用来控制表头循环结构(姓名,基本工资，补贴...)
	    	%>
			<c:forEach items="${viewPaDeptEmpIdList}" var="temp">
				<c:forEach items="${viewPaLabourPayOffList}" var="item" varStatus="i">
					<c:if test="${temp.DEPTNO == item.DEPTNO}">
					<%
						if(count==0){
					%>
						<c:forEach items="${viewPaAllDeptNameList}" var="dname">
							<c:if test="${temp.DEPTNO == dname.DEPTNO}">
								<tr>
									<td>${dname.CONTENT}</td>
									<td>${item.SERVICES_BELONG_NAME }</td>
								</tr>
							</c:if>
						</c:forEach>
						<tr>
							<td>序号</td>
							<td>姓名</td>
							<td>基本工资</td>
							<td>加班费</td>
							<td>岗贴</td>
							<td>夜班</td>
							<td>全勤补贴</td>
							<td>管理费</td>
							<td>补款</td>
							<td>应得合计</td>
							<td>病扣</td>
							<td>事扣</td>
							<td>其他扣</td>
							<td>补扣</td>
							<td>税扣</td>
							<td>实际所得</td>
							<td>养老</td>
							<td>失业</td>
							<td>医疗</td>
							<td>工伤</td>
							<td>生育</td>
							<td>工资合计</td>
							<td>身份证号</td>
						</tr>
					<%
						}
						count++;
					%>
						<tr>
							<td><%=count %></td>
							<td>${item.LOCAL_NAME }</td>
							<td>${item.DESERVE_BASIC_WAGE }</td>
							<td>${item.TOTAL_OT_FEE}</td>
							<td>${item.JOB_ALLOWANCE_STANDARD }</td>
							<td>${item.NIGHT_SHIFT_FEE }</td>
							<td>${item.FULL_ATTENDANCE_ALLOWANCE }</td>
							<td>${item.MANAGEMENT_FEE }</td>
							<td>${item.REPLENISHMENT }</td>
							<td>${item.TOTAL_MONTH_WAGE }</td>
							<td>${item.SICK_CHARGE_BACK }</td>
							<td>${item.LEAVE_CHARGE_BACK }</td>
							<td>${item.DAOXIU_CHARGE_BACK+item.ABSENTEEISM_DEDUCTIONS+item.TARDINESS_EARLYDAY_C_BACK+item.OTHERS_LEAVE_C_BACK }</td>
							<td>${item.BEFORE_TAXABLE_DEDUCT }</td>
							<td>${item.PERSONAL_INCOME_TAX }</td>
							<td>${item.NET_PAY_WAGE }</td>
							<td>${item.ENDOWMENT_COMPANY }</td>
							<td>${item.UNEMPLOYMENT_COMPANY}</td>
							<td>${item.MEDICAL_COMPANY }</td>
							<td>${item.INDUCTRIAL_COMPANY}</td>
							<td>${item.BIRTH_COMPANY}</td>
							<td>${item.PEOPLEWARE_FEE}</td>
							<td style="mso-number-format:'\@'">${item.IDCARD_NO}</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:forEach items="${viewPaLabourPayOffSumList}" var="sum">
					<c:if test="${sum.DEPTNO== temp.DEPTNO}">
						<tr>
							<td>部门小计</td>
							<td><%=count %></td>
							<td>${sum.DESERVE_BASIC_WAGE }</td>
							<td>${sum.TOTAL_OT_FEE }</td>
							<td>${sum.JOB_ALLOWANCE_STANDARD }</td>
							<td>${sum.NIGHT_SHIFT_FEE }</td>
							<td>${sum.FULL_ATTENDANCE_ALLOWANCE }</td>
							<td>${sum.MANAGEMENT_FEE }</td>
							<td>${sum.REPLENISHMENT }</td>
							<td>${sum.TOTAL_MONTH_WAGE }</td>
							<td>${sum.SICK_CHARGE_BACK }</td>
							<td>${sum.LEAVE_CHARGE_BACK }</td>
							<td>${sum.DAOXIU_CHARGE_BACK+sum.ABSENTEEISM_DEDUCTIONS+sum.TARDINESS_EARLYDAY_C_BACK+sum.OTHERS_LEAVE_C_BACK}</td>
							<td>${sum.BEFORE_TAXABLE_DEDUCT }</td>
							<td>${sum.PERSONAL_INCOME_TAX }</td>
							<td>${sum.NET_PAY_WAGE }</td>
							<td>${sum.ENDOWMENT_COMPANY }</td>
							<td>${sum.UNEMPLOYMENT_COMPANY }</td>
							<td>${sum.MEDICAL_COMPANY }</td>
							<td>${sum.INDUCTRIAL_COMPANY }</td>
							<td>${sum.BIRTH_COMPANY }</td>
							<td>${sum.PEOPLEWARE_FEE }</td>
						</tr>
					</c:if>
				</c:forEach>
				<%//当部门号不相等时     动态控制<tr>换行数目
					if(count!=0){
						count=0; 				
				%>
					<tr></tr>
				<%
					}else{
						count=0; 
					}
				%>
			</c:forEach>
			<c:forEach items="${viewPaLabourPayOffZongJiList}" var="zong">
					<tr>
						<td >总计</td>
						<td ></td>
						<td >${zong.DESERVE_BASIC_WAGE }</td>
						<td >${zong.TOTAL_OT_FEE }</td>
						<td >${zong.JOB_ALLOWANCE_STANDARD }</td>
						<td >${zong.NIGHT_SHIFT_FEE }</td>
						<td >${zong.FULL_ATTENDANCE_ALLOWANCE }</td>
						<td >${zong.MANAGEMENT_FEE }</td>
						<td >${zong.REPLENISHMENT }</td>
						<td >${zong.TOTAL_MONTH_WAGE }</td>
						<td >${zong.SICK_CHARGE_BACK }</td>
						<td >${zong.LEAVE_CHARGE_BACK }</td>
						<td >${zong.DAOXIU_CHARGE_BACK+zong.ABSENTEEISM_DEDUCTIONS+zong.TARDINESS_EARLYDAY_C_BACK+zong.OTHERS_LEAVE_C_BACK}</td>
						<td >${zong.BEFORE_TAXABLE_DEDUCT }</td>
						<td >${zong.PERSONAL_INCOME_TAX }</td>
						<td >${zong.NET_PAY_WAGE }</td>
						<td >${zong.ENDOWMENT_COMPANY }</td>
						<td >${zong.UNEMPLOYMENT_COMPANY }</td>
						<td >${zong.MEDICAL_COMPANY }</td>
						<td >${zong.INDUCTRIAL_COMPANY }</td>
						<td >${zong.BIRTH_COMPANY }</td>
						<td >${zong.PEOPLEWARE_FEE }</td>
					</tr>
			</c:forEach>
			<%--<tr>
				<td></td>
				<td colspan="2"><font size="+2">总经理：</font></td><td></td><td></td>
				<td colspan="2"><font size="+2">部长：</font></td><td></td><td></td>
				<td colspan="2"><font size="+2">科长：</font></td><td></td><td></td>
				<td colspan="2"><font size="+2">审批人：</font></td><td></td><td></td>
				<td colspan="2"><font size="+2">制表人：</font></td><td></td><td></td>
				<td><font size="+2">制表日期：</font></td>
				<td><font size="+2">${date }</font></td>xxxx年xx月xx日
			</tr> 
      		--%>
      </table> 
	</td>
  </tr>
</table>
</body>
</html>