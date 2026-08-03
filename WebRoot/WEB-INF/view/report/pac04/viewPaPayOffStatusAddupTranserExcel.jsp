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
	}
</style>

</head>   
                           
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=GongZiZhiFuXianZhuang(LeiJi).xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   
<table width="98%" border="1">
  <tr>
    <td colspan="14" height="42" width="98%"><b><font size="+4">${YEAR }年${MONTH }月考勤工资支付现状（累计）</font></b></td>
  </tr>
  <tr>
    <td colspan="4" style="text-align: left">${date }</td>
    <td colspan="10" style="text-align: right">单位：元、%</td>
  </tr>
  <tr>
	    <td colspan="4" rowspan="3">分类</td>
	    <td colspan="3">前年同期累计</td>
	    <td colspan="3">当年累计</td>
	    <td colspan="4">增减状况</td>
  	</tr>
  	<tr>
	    <td colspan="3">${salaryAmountMap.LAST_YEAR }年01月到${salaryAmountMap.LAST_YEAR }年${salaryAmountMap.MONTH }月考勤</td>
	    <td colspan="3">${salaryAmountMap.CURRENT_YEAR }年01月到${salaryAmountMap.CURRENT_YEAR }年${salaryAmountMap.MONTH }月考勤</td>
	    <td colspan="4">同期累计前比</td>
  	</tr>
  	<tr>
		<td>金额</td>
	    <td>人件费负担率</td>
	    <td>构成比</td>
	    
		<td>金额</td>
	    <td>人件费负担率</td>
	    <td>构成比</td>
	    
		<td>金额</td>
		<td>增减率</td>
	    <td>人件费负担率差(%P)</td>
	    <td>构成比差(%P)</td>
	</tr>
	<!-- 销售金额 -->
	<tr>
		<td colspan="4" style="text-align：center">销售额</td>
		<td colspan="3" style="text-align：center">
			<c:if test="${salaryAmountMap.LAST_YEAR_AMOUNT ne 1 }">
				<fmt:formatNumber value="${salaryAmountMap.LAST_YEAR_AMOUNT }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${salaryAmountMap.LAST_YEAR_AMOUNT == 1 }">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		<td colspan="3" style="text-align：center">
			<c:if test="${salaryAmountMap.CURRENT_YEAR_AMOUNT ne 1 }">
				<fmt:formatNumber value="${salaryAmountMap.CURRENT_YEAR_AMOUNT }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${salaryAmountMap.CURRENT_YEAR_AMOUNT == 1 }">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		
		<!-- 前年比 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${salaryAmountMap.CURRENT_YEAR_AMOUNT - salaryAmountMap.LAST_YEAR_AMOUNT }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${(salaryAmountMap.CURRENT_YEAR_AMOUNT - salaryAmountMap.LAST_YEAR_AMOUNT)/salaryAmountMap.LAST_YEAR_AMOUNT*100 }" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">-</td>
		<td style="text-align：center">-</td>
	</tr>
	
	<!-- 驻在员 -->
	<tr>
		<td colspan="4" style="text-align：center">驻在人员</td>
		<!-- 前年 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
		</td>
		<!-- 当月 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
		</td>
		<!-- 前年比 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY - zhuZaiYuanSalaryMap.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<c:if test="${zhuZaiYuanSalaryMap.LAST_YEAR_SALARY ne 0}">
				<fmt:formatNumber value="${(zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY - zhuZaiYuanSalaryMap.LAST_YEAR_SALARY)/zhuZaiYuanSalaryMap.LAST_YEAR_SALARY * 100 }"
				pattern="#,#00.00"/>%
			</c:if> 
			<c:if test="${zhuZaiYuanSalaryMap.LAST_YEAR_SALARY == 0}">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>%
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(zhuZaiYuanSalaryMap.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((zhuZaiYuanSalaryMap.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(zhuZaiYuanSalaryMap.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
			pattern="#,#00.00"/>%
		</td>
	</tr>
	<tr>
		<td rowspan="20" style="text-align：center">
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>中</p>
			<p>方</p>
			<p>合</p>
			<p>计</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
		</td>
		<td rowspan="10" style="text-align：center">
			<p>&nbsp;</p>
			<p>正</p>
			<p>式</p>
			<p>工</p>
			<p>&nbsp;</p>
		</td>
		<td rowspan="5" style="text-align：center">
			<p>给</p>
			<p>与</p>
			<p>性</p>
		</td>
		<!-- 正式工--给与性--基本工资 -->
		<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
			<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'BASE_SALARY'}">				
				<td style="text-align：center">基本工资</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--给与性--加班费 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'TOTAL_OT_FEE'}">
			<tr>
				<td style="text-align：center">加班费</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--给与性--夜班费 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'NIGHT_SHIFT_FEE'}">
			<tr>
				<td style="text-align：center">夜班费</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--给与性--其它 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'OTHER_SALARY'}">
			<tr>
				<td style="text-align：center">其它</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--给与性--应得合计 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'TOTAL_MONTH_WAGE'}">
			<tr>
				<td style="text-align：center">应得合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--扣款合计 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'DEDUCT_SALARY'}">
			<tr>
				<td colspan="2" style="text-align：center">扣款合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	
	<!-- 正式工--工资总额 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'TOTAL_WAGE'}">
			<tr>
				<td colspan="2" style="text-align：center">工资总额</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--公司缴纳--社保 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'SOCIAL_INSURANCE_COMPANY'}">
			<tr>
				<td rowspan="2" style="text-align：center">公司</td>
				<td style="text-align：center">社保</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--公司缴纳--公积金 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'HOUSE_FUNDING_COMPANY'}">
			<tr>
				<td style="text-align：center">公积金</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 正式工--正式工合计 -->
	<c:forEach items="${normalEmpSalaryList}" var="normalEmp">
		<c:if test="${'' ne normalEmp.PA_ITEM_NAME && normalEmp.PA_ITEM_NAME eq 'TOTAL_SALARY'}">
			<tr>
				<td colspan="2" style="text-align：center">正式工合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${normalEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(normalEmp.CURRENT_YEAR_SALARY - normalEmp.LAST_YEAR_SALARY)/normalEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${normalEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(normalEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((normalEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(normalEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--给与性--基本工资 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'BASE_SALARY'}">
			<tr>
				<td rowspan="9" style="text-align：center">
					<p>&nbsp;</p>
					<p>劳</p>
					<p>务</p>
					<p>工</p>
					<p>&nbsp;</p>
				</td>
				<td rowspan="5" style="text-align：center">
					<p>给</p>
					<p>与</p>
					<p>性</p>
				</td>
				<td style="text-align：center">基本工资</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--给与性--加班费 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'TOTAL_OT_FEE'}">
			<tr>
				<td style="text-align：center">加班费</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--给与性--夜班费 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'NIGHT_SHIFT_FEE'}">
			<tr>
				<td style="text-align：center">夜班费 </td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--给与性--其它 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'OTHER_SALARY'}">
			<tr>
				<td style="text-align：center">其它 </td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--给与性--应得合计 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'TOTAL_MONTH_WAGE'}">
			<tr>
				<td style="text-align：center">应得合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--扣款合计 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'DEDUCT_SALARY'}">
			<tr>
				<td colspan="2" style="text-align：center">扣款合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--工资总额 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'TOTAL_WAGE'}">
			<tr>
				<td colspan="2" style="text-align：center">工资总额</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--公司缴纳社保 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'SOCIAL_INSURANCE_COMPANY'}">
			<tr>
				<td colspan="2" style="text-align：center">公司缴纳社保</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	<!-- 中方职员--劳务工--劳务工合计 -->
	<c:forEach items="${paiQianEmpSalaryList}" var="paiQianEmp">
		<c:if test="${'' ne paiQianEmp.PA_ITEM_NAME && paiQianEmp.PA_ITEM_NAME eq 'TOTAL_SALARY'}">
			<tr>
				<td colspan="2" style="text-align：center">劳务工合计</td>
				<!-- 前年 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 当月 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
				</td>
				<!-- 前年比 -->
				<td style="text-align：center">
					<fmt:formatNumber value="${paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
				</td>
				<td style="text-align：center">
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY ne 0}">
						<fmt:formatNumber value="${(paiQianEmp.CURRENT_YEAR_SALARY - paiQianEmp.LAST_YEAR_SALARY)/paiQianEmp.LAST_YEAR_SALARY * 100 }"
						pattern="#,#00.00"/>%
					</c:if> 
					<c:if test="${paiQianEmp.LAST_YEAR_SALARY == 0}">
						<fmt:formatNumber value="0" pattern="#,#00.00"/>%
					</c:if>
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(paiQianEmp.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
					pattern="#,#00.00"/>%
				</td>
				<td style="text-align：center">
					<fmt:formatNumber value="${((paiQianEmp.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(paiQianEmp.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
					pattern="#,#00.00"/>%
				</td>
			</tr>			
		</c:if>
	</c:forEach>
	
	<!-- 中方合计 -->
	<tr>
		<td colspan="3" style="text-align：center">中方合计</td>
		<!-- 前年 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
		</td>
		<!-- 当月 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY * 100 }" pattern="#,#00.00"/>%
		</td>
		<!-- 前年比 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${totalChina.CURRENT_YEAR_SALARY - totalChina.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<c:if test="${totalChina.LAST_YEAR_SALARY ne 0}">
				<fmt:formatNumber value="${(totalChina.CURRENT_YEAR_SALARY - totalChina.LAST_YEAR_SALARY)/totalChina.LAST_YEAR_SALARY * 100 }"
				pattern="#,#00.00"/>%
			</c:if> 
			<c:if test="${totalChina.LAST_YEAR_SALARY == 0}">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>%
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((totalChina.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(totalChina.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((totalChina.CURRENT_YEAR_SALARY / totalCompany.CURRENT_YEAR_SALARY)-(totalChina.LAST_YEAR_SALARY / totalCompany.LAST_YEAR_SALARY))*100 }" 
			pattern="#,#00.00"/>%
		</td>
	</tr>
	
	<!-- 公司总计 -->
	<tr>
		<td colspan="4" style="text-align：center">公司总计</td>
		<!-- 前年 -->
		<td style="text-align：center">
			<c:if test="${totalCompany.LAST_YEAR_SALARY ne 1}">
				<fmt:formatNumber value="${totalCompany.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${totalCompany.LAST_YEAR_SALARY == 1}">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalCompany.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="100.00" pattern="#,#00.00"/>%
		</td>
		<!-- 当月 -->
		<td style="text-align：center">
			<c:if test="${totalCompany.CURRENT_YEAR_SALARY ne 1}">
				<fmt:formatNumber value="${totalCompany.CURRENT_YEAR_SALARY }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${totalCompany.CURRENT_YEAR_SALARY == 1}">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalCompany.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="100.00" pattern="#,#00.00"/>%
		</td>
		<!-- 前年比 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${totalCompany.CURRENT_YEAR_SALARY - totalCompany.LAST_YEAR_SALARY }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<c:if test="${totalCompany.LAST_YEAR_SALARY ne 0}">
				<fmt:formatNumber value="${(totalCompany.CURRENT_YEAR_SALARY - totalCompany.LAST_YEAR_SALARY)/totalCompany.LAST_YEAR_SALARY * 100 }"
				pattern="#,#00.00"/>%
			</c:if> 
			<c:if test="${totalCompany.LAST_YEAR_SALARY == 0}">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>%
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((totalCompany.CURRENT_YEAR_SALARY / salaryAmountMap.CURRENT_YEAR_AMOUNT)-(totalCompany.LAST_YEAR_SALARY / salaryAmountMap.LAST_YEAR_AMOUNT))*100 }" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="0.00" pattern="#,#00.00"/>%
		</td>
	</tr>
	
	<!-- 生产金额 -->
	<tr>
		<td colspan="4" style="text-align：center">生产金额</td>
		<!-- 前年 -->
		<td style="text-align：center">
			<c:if test="${productionAmountMap.LAST_YEAR_AMOUNT ne 1 }">
				<fmt:formatNumber value="${productionAmountMap.LAST_YEAR_AMOUNT }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${productionAmountMap.LAST_YEAR_AMOUNT == 1 }">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalCompany.LAST_YEAR_SALARY / productionAmountMap.LAST_YEAR_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">-</td>
		
		<!-- 当月 -->
		<td style="text-align：center">
			<c:if test="${productionAmountMap.CURRENT_YEAR_AMOUNT ne 1 }">
				<fmt:formatNumber value="${productionAmountMap.CURRENT_AMOUNT }" pattern="#,#00.00"/>
			</c:if>
			<c:if test="${productionAmountMap.CURRENT_YEAR_AMOUNT == 1 }">
				<fmt:formatNumber value="0" pattern="#,#00.00"/>
			</c:if>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${totalCompany.CURRENT_SALARY / productionAmountMap.CURRENT_AMOUNT * 100 }" pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">-</td>
		
		<!-- 前年比 -->
		<td style="text-align：center">
			<fmt:formatNumber value="${productionAmountMap.CURRENT_YEAR_AMOUNT - productionAmountMap.LAST_YEAR_AMOUNT }" pattern="#,#00.00"/>
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${(productionAmountMap.CURRENT_YEAR_AMOUNT - productionAmountMap.LAST_YEAR_AMOUNT)/productionAmountMap.LAST_YEAR_AMOUNT * 100 }" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">
			<fmt:formatNumber value="${((totalCompany.CURRENT_YEAR_SALARY / productionAmountMap.CURRENT_YEAR_AMOUNT)- (totalCompany.LAST_YEAR_SALARY / productionAmountMap.LAST_YEAR_AMOUNT))*100}" 
			pattern="#,#00.00"/>%
		</td>
		<td style="text-align：center">-</td>
	</tr>
  </table>
</body>
</html>