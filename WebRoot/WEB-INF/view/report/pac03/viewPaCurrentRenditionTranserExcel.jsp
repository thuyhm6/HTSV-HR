
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>乐天餐饮给予现况表</title>
</head>
<body>
<%
	response.setHeader("Content-Type",
			"application/vnd.ms-excel; charset=UTF-8");
	response.setHeader("Content-Disposition",
			"attachment; filename=currentRenditionCollectExcel.xls");
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
%>
<table width="100%" border="2" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="left" colspan="21"><b><font size="+2">${YEAR
		}年${MONTH }月给予现况(<c:choose>
		                      <c:when test="${deptNo eq 'C0327'}">东北地区</c:when>
							  <c:when test="${deptNo eq 'C0315'}">华北地区</c:when>
							  <c:when test="${deptNo eq 'C034'}">华东地区</c:when>
							  <c:when test="${deptNo eq 'other'}">总部</c:when>
							  </c:choose>)</font> </b></td>
	</tr>

	<tr>
		<td>
		<table width="${100/10}%" border="1" align="center" cellpadding="0"
			cellspacing="0">

			<tr>
				
				<td>
				<c:forEach items="${getPaCurrentRenditionSumAvg}" var="total">
				<table width="30.0%" border="1" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td style="text-align: center" rowspan="4" colspan="1">区分</td>
	
						<td style="text-align: center" rowspan="3" colspan="2">TOTAL
						</td>

						<td style="text-align: center" rowspan="3" colspan="2">人当平均</td>



					</tr>


					<tr>
						<tr>
						</tr>



						<tr>
							<td style="text-align: center">金额</td>
							<td style="text-align: center"><font color="blue">%</font></td>

							<td style="text-align: center">金额</td>
							<td style="text-align: center"><font color="blue">%</font></td>


						</tr>
						<tr>
							<td style="text-align: center">基本工资</td>
							<td style="text-align: center">${total.BASE_SALARY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
							<td style="text-align: center">${total.BASE_SALARY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>


						</tr>
						<tr>
							<td style="text-align: center">奖&nbsp;&nbsp;金</td>

							<td style="text-align: center">${total.BONUS_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
							<td style="text-align: center">${total.BONUS_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">加班工资</td>

							<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
							<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">职级补贴</td>

							<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">职责补贴</td>

							<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">专业补贴</td>

							<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">餐费补贴</td>

							<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">法定休日补贴</td>

							<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">提成工资</td>
							<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
							<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>
						</tr>



						<tr>
							<td style="text-align: center">差减给与</td>
							<td style="text-align: center">${total.CHAJIAN_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.CHAJIAN_SUM_P}</font></td>
							<td style="text-align: center">${total.CHAJIAN_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.CHAJIAN_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#FFFF99">总所得额</td>
							<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.SHOULD_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.SHOULD_GET_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#C0C0C0">社保（个人部分）</td>
							<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_SUM}</td>
							<td style="text-align: center" bgcolor="#C0C0C0"><font
								color="blue">${total.INSURANCE_TOTAL_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_AVG}</td>
							<td style="text-align: center" bgcolor="#C0C0C0"><font
								color="blue">${total.INSURANCE_TOTAL_PERSONAL_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">保险</td>
							<td style="text-align: center">${total.INSURANCE_PERSONAL_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center">${total.INSURANCE_PERSONAL_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">住房公积金</td>
							<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#FFCC99">应税所得额</td>

							<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.PURE_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.PURE_GET_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">个人所得税</td>
							<td style="text-align: center">${total.TAX_PERSONAL_INCOME_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_SUM_P}</font></td>
							<td style="text-align: center">${total.TAX_PERSONAL_INCOME_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#FFCC99">實受令額</td>
							<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.ACT_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.ACT_GET_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#FFFF99">社保（公司部分）</td>

							<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.INSURANCE_TOTAL_COMPANY_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.INSURANCE_TOTAL_COMPANY_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">保&nbsp;&nbsp;险</td>

							<td style="text-align: center">${total.INSURANCE_COMPANY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_SUM_P}</font></td>
							<td style="text-align: center">${total.INSURANCE_COMPANY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">住房公积金</td>
							<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_SUM_P}</font></td>
							<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_AVG_P}</font></td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#FFFF00">總 額 賃 金</td>
							<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF00"><font
								color="blue">${total.TOTAL_PAY_COMPANY_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF00"><font
								color="blue">${total.TOTAL_PAY_COMPANY_AVG_P}</font></td>
						</tr>
				</table>
				</c:forEach>
				</td>
				
				<c:forEach items="${viewPaCurrentRenditionList}" var="pa">
					<td>
					<table width="${100/10}%" border="1" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td style="text-align: center">${pa.LOCAL_NAME}</td>
							<td style="text-align: center">${pa.POSITION_NAME}</td>
						</tr>
						<tr>
							<td style="text-align: center" colspan="2">${pa.DEPT_NAME}</td>
						</tr>
						<tr>
							<td style="text-align: center" bgcolor="#C0C0C0">${pa.OLDPOSTGRADE}</td>
							<td style="text-align: center" bgcolor="#C00000"><font
								color="white">${pa.PAYSTEP}</font></td>
						</tr>
						<tr>
							<td style="text-align: center">金额</td>
							<td style="text-align: center"><font color="blue">%</font></ttd>
						</tr>
						
							<tr>
								<td style="text-align: center">${pa.BASE_SALARY}</td>
								<td style="text-align: center"><font color="blue">${pa.BASE_SALARY_P}</font></td>
							</tr>
							<tr>	
								<td style="text-align: center">${pa.BONUS}</td>
								<td style="text-align: center"><font color="blue">${pa.BONUS_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.OVERTIME_PAY}</td>
								<td style="text-align: center"><font color="blue">${pa.OVERTIME_PAY_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.POSITION_ALLOWANCE}</td>
								<td style="text-align: center"><font color="blue">${pa.POSITION_ALLOWANCE_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.DUTY_ALLOWANCE}</td>
								<td style="text-align: center"><font color="blue">${pa.DUTY_ALLOWANCE_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.PROFESSIONAL_ALLOWANCE}</td>
								<td style="text-align: center"><font color="blue">${pa.PROFESSIONAL_ALLOWANCE_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.MEALS_ALLOWANCE}</td>
								<td style="text-align: center"><font color="blue">${pa.MEALS_ALLOWANCE_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.LEGAL_HOLIDAY_ALLOWANCE}</td>
								<td style="text-align: center"><font color="blue">${pa.LEGAL_HOLIDAY_ALLOWANCE_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.COMMISSION_SALARY}</td>
								<td style="text-align: center"><font color="blue">${pa.COMMISSION_SALARY_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.CHAJIAN}</td>
								<td style="text-align: center"><font color="blue">${pa.CHAJIAN_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${pa.SHOULD_GET}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font 
									color="blue">${pa.SHOULD_GET_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#C0C0C0">${pa.INSURANCE_TOTAL_PERSONAL}</td>
								<td style="text-align: center" bgcolor="#C0C0C0"><font 
									color="blue">${pa.INSURANCE_TOTAL_PERSONAL_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.INSURANCE_PERSONAL}</td> 
								<td style="text-align: center"><font color="blue">${pa.INSURANCE_PERSONAL_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.HOUSE_FUNDING_PERSONAL}</td> 
								<td style="text-align: center"><font color="blue">${pa.HOUSE_FUNDING_PERSONAL_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${pa.PURE_GET}</td> 
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${pa.PURE_GET_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.TAX_PERSONAL_INCOME}</td> 
								<td style="text-align: center"><font color="blue">${pa.TAX_PERSONAL_INCOME_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${pa.ACT_GET}</td> 
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${pa.ACT_GET_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${pa.INSURANCE_TOTAL_COMPANY}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${pa.INSURANCE_TOTAL_COMPANY_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.INSURANCE_COMPANY}</td> 
								<td style="text-align: center"><font color="blue">${pa.INSURANCE_COMPANY_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center">${pa.HOUSE_FUNDING_COMPANY}</td>
								<td style="text-align: center"><font color="blue">${pa.HOUSE_FUNDING_COMPANY_P}</font></td>
							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF00">${pa.TOTAL_PAY}</td>
								<td style="text-align: center" bgcolor="#FFFF00"><font color="blue">${pa.TOTAL_PAY_P}</font></td>
							</tr>
					</table>
					</td>

				</c:forEach>

			</tr>




		</table>
</body>
</html>