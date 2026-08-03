 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--乐天玛特实发薪资汇总表（全公司）--%>
	乐天玛特实发薪资汇总表（全公司）
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=LotteMartPayrollCosts.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="19" >
		    			<b><font size="+2">乐天玛特${YEAR }年${MONTH }月薪资成本汇总表（全公司）</font></b>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td style="text-align: center" rowspan="2"><%--项目--%>
						项目
					</td>
					<td style="text-align: center" rowspan="2"><%--计薪人数--%>
						计薪人数
					</td>
					<td style="text-align: center" colspan="3"><%--员工薪资--%>
						员工薪资
					</td>
					<td style="text-align: center" colspan="9"><%--公司支付四金--%>
						公司支付四金
					</td>
					<td style="text-align: center" rowspan="2"><%--公司支付四金合计--%>
						公司支付四金合计
					</td>
					<td style="text-align: center" rowspan="2"><%--独生子女费--%>
						独生子女费
					</td>
					<td style="text-align: center" rowspan="2"><%--公司支付劳务管理费--%>
						公司支付劳务管理费
					</td>
					<td style="text-align: center" rowspan="2"><%--税后给付--%>
						税后给付
					</td>
					<td style="text-align: center" rowspan="2"><%--实发成本--%>
						实发成本
					</td>
					
		    	</tr>
				<tr>
					<td style="text-align: center"><%--应发工资--%>
						应发工资
					</td>
					<td style="text-align: center"><%--基本工资--%>
						基本工资
					</td>
					<td style="text-align: center"><%--津贴--%>
						津贴
					</td>
					
					<td style="text-align: center"><%--养老金--%>
						养老金
					</td>
					<td style="text-align: center"><%--公积金--%>
						公积金
					</td>
					<td style="text-align: center"><%--医疗金--%>
						医疗金
					</td>
					<td style="text-align: center"><%--失业金--%>
						失业金
					</td>
					<td style="text-align: center"><%--工伤--%>
						工伤
					</td>
					
					<td style="text-align: center"><%--生育--%>
						生育
					</td>
					<td style="text-align: center"><%--大病医疗--%>
						大病医疗
					</td>
					<td style="text-align: center"><%--社保追缴--%>
						社保追缴
					</td>
					<td style="text-align: center"><%--公积金追缴--%>
						公积金追缴
					</td>
				</tr>        
		       	<c:forEach items="${payrollCostsList}" var="pa" varStatus="i">
					<tr>
						<td style="text-align: center">
							<c:if test="${pa.TYPE_TOTAL eq 'AREA_TOTAL'}">
								<font color="red">
									<b>${pa.DEPT_TYPE_NAME }~合计</b>
								</font>
							</c:if>
							<c:if test="${pa.TYPE_TOTAL eq 'AREA2_TOTAL'}">
								<c:if test="${pa.DEPT_TYPE eq '15945' || pa.DEPT_TYPE eq '15946' || pa.DEPT_TYPE eq '15947' || pa.DEPT_TYPE eq '15948' 
									|| pa.DEPT_TYPE eq '122939' || pa.DEPT_TYPE eq '122940'}">	
									<font color="red">
										<b>门店~合计</b>
									</font>
								</c:if>
								<c:if test="${pa.DEPT_TYPE ne '15945' && pa.DEPT_TYPE ne '15946' && pa.DEPT_TYPE ne '15947' && pa.DEPT_TYPE ne '15948' 
									&& pa.DEPT_TYPE ne '122939' && pa.DEPT_TYPE ne '122940'}">
									<font color="red">
										<b>总部~合计</b>
									</font>
								</c:if>									
							</c:if>
							<c:if test="${pa.TYPE_TOTAL eq 'COM_TOTAL'}">
								<font color="red">
									<b>总合计</b>
								</font>
							</c:if>
							<c:if test="${pa.TYPE_TOTAL ne 'AREA_TOTAL' && pa.TYPE_TOTAL ne 'AREA2_TOTAL' && pa.TYPE_TOTAL ne 'COM_TOTAL'}">
								${pa.DEPT_DISTINGUISH_NAME }
							</c:if>
						</td>
						<td style="text-align: right">${pa.PACNT}</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SHOULD_RELEASE_SALARY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.OFFICE_RELEASE_SALARY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ALLOWANCE_RELEASE_SALARY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ENDOWMENT_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_COMPANY}" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.MEDICAL_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.INDUCTRIAL_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.BIRTH_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_AFTER_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_AFTERWARDS_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_TOTAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ONLY_CHILDREN_ALLOWANCE}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.LABOR_MANAGE_FEE}" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.AFTER_TAXABLE_PAY_PLUS}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.PEOPLEWARE_FEE}" pattern="#,##0.00#"/>
						</td>
						
					</tr>
				</c:forEach>    
				<tr>
					<td style="text-align:center">人事处长：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">支援部长：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">副总经理：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">总经理：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>