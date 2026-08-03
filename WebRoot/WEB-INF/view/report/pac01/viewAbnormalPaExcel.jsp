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
		response.setHeader("Content-Disposition", "attachment; filename=LotteMartActualSalary.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="27" >
		    			<b><font size="+2">乐天玛特${YEAR }年${MONTH }月实发薪资汇总表（全公司）</font></b>
		    		</td>
		    	</tr>
				<tr>
					<td align="center"><%--类别--%>
						类别
					</td>
					<td align="center"><%--人数--%>
						人数
					</td>
					<td align="center"><%--基本工资--%>
						基本工资
					</td>
					<td align="center"><%--津贴--%>
						津贴
					</td>
					<td align="center"><%--加班费--%>
						加班费
					</td>
					
					<td align="center"><%--驻外津贴--%>
						驻外津贴
					</td>
					<td align="center"><%--午餐费--%>
						误餐费
					</td>
					<td align="center"><%--其他给付--%>
						其他给付
					</td>
					<td align="center"><%--薪资追补--%>
						薪资追补
					</td>
					<td align="center"><%--事假扣款--%>
						事假扣款
					</td>
					
					<td align="center"><%--病假扣款--%>
						病假扣款
					</td>
					<td align="center"><%--其他扣款--%>
						其他扣款
					</td>
					<td align="center"><%--应发工资--%>
						应发工资
					</td>
					<td align="center"><%--所得税--%>
						所得税
					</td>
					<td align="center"><%--公积金--%>
						公积金
					</td>
					
					<td align="center"><%--养老金--%>
						养老金
					</td>
					<td align="center"><%--医疗金--%>
						医疗金
					</td>
					<td align="center"><%--大病医疗--%>
						大病医疗
					</td>
					<td align="center"><%--失业金--%>
						失业金
					</td>
					<td align="center"><%--其他--%>
						个人社保追缴
					</td>
					
					<td align="center"><%--其他--%>
						个人公积金追缴
					</td>
					<td align="center"><%--其他--%>
						其他
					</td>
					<td align="center"><%--劳务管理费--%>
						劳务管理费
					</td>
					<td align="center"><%--独生子女费--%>
						独生子女费
					</td>
					<td align="center"><%--税后加项--%>
						税后加项
					</td>
					
					<td align="center"><%--税后给付--%>
						税后给付
					</td>
					<td align="center"><%--实发薪资--%>
						实发薪资
					</td>
				</tr>        
		       	<c:forEach items="${actualSalaryList}" var="pa" varStatus="i">
					<tr>
						<td style="text-align: center">
							${pa.DEPT_DISTINGUISH_NAME }
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.PACNT }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.BASE_SALARY }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ALLOWANCE_TOTAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.OT_TOTAL }" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SPECIAL_R_OUTSIDE_ALLOWANCE }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.DELAY_MEAL_ALLOWANCE }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ALLOWANCE_TOTAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.TAXABLE_PAY }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.PERSONAL_LEAVE_DEDUCTIONS }" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SICK_LEAVE_DEDUCTIONS }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.OTHER_DEDUCT }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SHOULD_RELEASE_SALARY }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.TAX_PERSONAL_INCOME }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_PERSONAL }" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ENDOWMENT_IS_PERSONAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.MEDICAL_INSURANCE_PERSONAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_IS_PERSONAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_IS_PERSONAL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_AFTER_PER }" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_AFTERWARDS_PEL }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.OTHER_DEDUCT }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.LABOR_MANAGE_FEE }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ONLY_CHILDREN_ALLOWANCE }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.AFTER_TAXABLE_PAY_DEDUCT }" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.AFTER_TAXABLE_PAY_PLUS }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${pa.ACTUAL_RELEASE_SALARY }" pattern="#,##0.00#"/>
						</td>
					</tr>
				</c:forEach>    
				<tr>
					<td style="text-align:center">人事处长：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">支援部长：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">副总经理：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td style="text-align:center">总经理：</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
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