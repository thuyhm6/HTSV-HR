
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>乐天餐饮给予现况汇总表</title>
</head>
<body>
<%
	response.setHeader("Content-Type",
			"application/vnd.ms-excel; charset=UTF-8");
	response
			.setHeader("Content-Disposition",
					"attachment; filename=currentRenditionCollectListExcel.xls");
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
%>
<table  border="2" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="center" ><b><font size="+2">${YEAR}年${MONTH }月给予现况汇总表</font> </b></td>
	</tr>

	<tr>

		<td>
		<table width="30.0%" border="1" align="center" cellpadding="0"
			cellspacing="0">
			<c:forEach items="${viewPaCurrentRenditionList1}" var="total">

				<tr>
					<td style="text-align: center" rowspan="4" colspan="1">区分</td>

					<td style="text-align: center" rowspan="3" colspan="2">TOTAL</td>

					<td style="text-align: center" rowspan="3" colspan="2">人当平均</td>


				</tr>


				<tr>
					<tr>
					</tr>



					<tr>
						<td style="text-align: center">金额</td>
						<td style="text-align: center"><font color="blue">(%)</font></td>

						<td style="text-align: center">金额</td>
						<td style="text-align: center"><font color="blue">(%)</font></td>


					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">基本工资</td>
						<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_SUM}</td>
						<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
						<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_AVG}</td>
						<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>


					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">奖&nbsp;&nbsp;金</td>

						<td style="text-align: center">${total.BONUS_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
						<td style="text-align: center">${total.BONUS_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">加班工资</td>

						<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
						<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">职级补贴</td>

						<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
						<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">职责补贴</td>

						<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
						<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">专业补贴</td>

						<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
						<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">餐费补贴</td>

						<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
						<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">法定休日补贴</td>

						<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
						<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>
					</tr>
					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">提成工资</td>
						<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
						<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
						<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
						<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>
					</tr>



					<tr>
						<td style="text-align: center" bgcolor="#CDC9C9">差减给与</td>
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
			</c:forEach>
		</table>

		</td>
		<%--总部(15명)含外籍人4名--%>
		<td>
		<table width="7" border="1" align="center" cellpadding="0"
				cellspacing="0" height="100%">
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
		<td>
		<c:forEach items="${viewPaCurrentRenditionList5}" var="total">
			
			<table width="7" border="1" align="center" cellpadding="0"
				cellspacing="0" height="100%">
				<tr>
					<td align="center" colspan="4" >
					总部(${EmpCnt4}人)</td>
				</tr>
				<tr>
					<td style="text-align: center" colspan="2"><%--TOTAL--%> TOTAL</td>
					<td style="text-align: center" colspan="2"><%--人當平均--%> 人當平均</td>
				</tr>
				<tr>

					<td style="text-align: center"><%--金额--%> 金额</td>
					<td style="text-align: center"><%--%--%><font color="blue">(%)</font></td>

					<td style="text-align: center"><%--金额--%> 金额</td>
					<td style="text-align: center" ><%--%--%><font color="blue">(%)</font></td>
				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_SUM}</td>
					<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_AVG}</td>
					<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.BONUS_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
					<td style="text-align: center">${total.BONUS_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
					<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
					<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
					<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
					<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
					<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
					<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
					<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>

				</tr>

				<tr>

					<td style="text-align: center">${total.CHAJIAN_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.CHAJIAN_SUM_P}</font></td>
					<td style="text-align: center">${total.CHAJIAN_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.CHAJIAN_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_SUM}</td>
					<td style="text-align: center" bgcolor="#FFFF99"><font
						color="blue">${total.SHOULD_GET_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_AVG}</td>
					<td style="text-align: center" bgcolor="#FFFF99"><font
						color="blue">${total.SHOULD_GET_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_SUM}</td>
					<td style="text-align: center" bgcolor="#C0C0C0"><font
						color="blue">${total.INSURANCE_TOTAL_PERSONAL_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_AVG}</td>
					<td style="text-align: center" bgcolor="#C0C0C0"><font
						color="blue">${total.INSURANCE_TOTAL_PERSONAL_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.INSURANCE_PERSONAL_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_SUM_P}</font></td>
					<td style="text-align: center">${total.INSURANCE_PERSONAL_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_SUM_P}</font></td>
					<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_SUM}</td>
					<td style="text-align: center" bgcolor="#FFCC99"><font
						color="blue">${total.PURE_GET_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_AVG}</td>
					<td style="text-align: center" bgcolor="#FFCC99"><font
						color="blue">${total.PURE_GET_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.TAX_PERSONAL_INCOME_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_SUM_P}</font></td>
					<td style="text-align: center">${total.TAX_PERSONAL_INCOME_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_SUM}</td>
					<td style="text-align: center" bgcolor="#FFCC99"><font
						color="blue">${total.ACT_GET_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_AVG}</td>
					<td style="text-align: center" bgcolor="#FFCC99"><font
						color="blue">${total.ACT_GET_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_SUM}</td>
					<td style="text-align: center" bgcolor="#FFFF99"><font
						color="blue">${total.INSURANCE_TOTAL_COMPANY_SUM_P}</font></td>
					<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_AVG}</td>
					<td style="text-align: center" bgcolor="#FFFF99"><font
						color="blue">${total.INSURANCE_TOTAL_COMPANY_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.INSURANCE_COMPANY_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_SUM_P}</font></td>
					<td style="text-align: center">${total.INSURANCE_COMPANY_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_AVG_P}</font></td>

				</tr>
				<tr>

					<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_SUM}</td>
					<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_SUM_P}</font></td>
					<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_AVG}</td>
					<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_AVG_P}</font></td>

				</tr>
				<tr>

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
		<%--东北地区(23人)--%>
				<%--<td><c:forEach items="${viewPaCurrentRenditionList2}" var="total">
					
					<table border="1">
						<tr>
							<td align="center"  colspan="4">
							华北地区(${EmpCnt2}人)</td>
						</tr>
						<tr>
							<td style="text-align: center" colspan="2">TOTAL
							TOTAL</td>
							<td style="text-align: center" colspan="2">人當平均 人當平均</td>
						</tr>
						<tr>

							<td style="text-align: center">金额 金额</td>
							<td style="text-align: center" >% <font color="blue">(%)</font></td>
							<td style="text-align: center">金额 金额</td>
							<td style="text-align: center" >% <font color="blue">(%)</font></td>
						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_SUM}</td>
							<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_AVG}</td>
							<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.BONUS_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
							<td style="text-align: center">${total.BONUS_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
							<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>

						</tr>
						<tr>
						
							<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
							<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
							<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>

						</tr>

						<tr>
							
							<td style="text-align: center">${total.CHAJIAN_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.CHAJIAN_SUM_P}</font></td>
							<td style="text-align: center">${total.CHAJIAN_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.CHAJIAN_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.SHOULD_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.SHOULD_GET_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_SUM}</td>
							<td style="text-align: center" bgcolor="#C0C0C0"><font
								color="blue">${total.INSURANCE_TOTAL_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_AVG}</td>
							<td style="text-align: center" bgcolor="#C0C0C0"><font
								color="blue">${total.INSURANCE_TOTAL_PERSONAL_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.INSURANCE_PERSONAL_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center">${total.INSURANCE_PERSONAL_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_SUM_P}</font></td>
							<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.PURE_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.PURE_GET_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.TAX_PERSONAL_INCOME_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_SUM_P}</font></td>
							<td style="text-align: center">${total.TAX_PERSONAL_INCOME_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_SUM}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.ACT_GET_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_AVG}</td>
							<td style="text-align: center" bgcolor="#FFCC99"><font
								color="blue">${total.ACT_GET_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.INSURANCE_TOTAL_COMPANY_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF99"><font
								color="blue">${total.INSURANCE_TOTAL_COMPANY_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.INSURANCE_COMPANY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_SUM_P}</font></td>
							<td style="text-align: center">${total.INSURANCE_COMPANY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_SUM}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_SUM_P}</font></td>
							<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_AVG}</td>
							<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_AVG_P}</font></td>

						</tr>
						<tr>
							
							<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_SUM}</td>
							<td style="text-align: center" bgcolor="#FFFF00"><font
								color="blue">${total.TOTAL_PAY_COMPANY_SUM_P}</font></td>
							<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_AVG}</td>
							<td style="text-align: center" bgcolor="#FFFF00"><font
								color="blue">${total.TOTAL_PAY_COMPANY_AVG_P}</font></td>

						</tr>
					</table>
					
				</c:forEach></td>


				华东地区(27人)
				<td><c:forEach items="${viewPaCurrentRenditionList3}" var="total">
					
					<table border="1">
						<tr>
							<td align="center"  colspan="4">
							华东地区(${EmpCnt3}人)</td>
						</tr>
						<tr>
							<td style="text-align: center" colspan="2">TOTAL
							TOTAL</td>
							<td style="text-align: center" colspan="2">人當平均 人當平均</td>
						</tr>
						<tr>

							<td style="text-align: center">金额 金额</td>
							<td style="text-align: center" >% <font color="blue">(%)</font></td>
							<td style="text-align: center">金额 金额</td>
							<td style="text-align: center">% <font color="blue">(%)</font></td>
							<tr>
								
								<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_SUM}</td>
								<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_AVG}</td>
								<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>

							</tr>
							<tr>
								
								<td style="text-align: center">${total.BONUS_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
								<td style="text-align: center">${total.BONUS_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>

							</tr>
							<tr>
								
								<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
								<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>

							</tr>
							<tr>
								
								<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								
								<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
								<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>

							</tr>

							<tr>
								<td style="text-align: center">${total.CHAJIAN_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.CHAJIAN_SUM_P}</font></td>
								<td style="text-align: center">${total.CHAJIAN_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.CHAJIAN_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.SHOULD_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.SHOULD_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_SUM}</td>
								<td style="text-align: center" bgcolor="#C0C0C0"><font
									color="blue">${total.INSURANCE_TOTAL_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_AVG}</td>
								<td style="text-align: center" bgcolor="#C0C0C0"><font
									color="blue">${total.INSURANCE_TOTAL_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.INSURANCE_PERSONAL_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center">${total.INSURANCE_PERSONAL_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.PURE_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.PURE_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.TAX_PERSONAL_INCOME_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_SUM_P}</font></td>
								<td style="text-align: center">${total.TAX_PERSONAL_INCOME_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.ACT_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.ACT_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.INSURANCE_TOTAL_COMPANY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.INSURANCE_TOTAL_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.INSURANCE_COMPANY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_SUM_P}</font></td>
								<td style="text-align: center">${total.INSURANCE_COMPANY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_SUM_P}</font></td>
								<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF00"><font
									color="blue">${total.TOTAL_PAY_COMPANY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF00"><font
									color="blue">${total.TOTAL_PAY_COMPANY_AVG_P}</font></td>

							</tr>
					</table>
					
				</c:forEach></td>
				--%>
				<td><c:forEach items="${viewPaCurrentRenditionList4}" var="total">
					
					<table border="1">
						<tr>
							<td align="center" colspan="4">
							店铺(${EmpCnt1}人)</td>
						</tr>
						<tr>
							<td style="text-align: center" colspan="2"><%--TOTAL--%>
							TOTAL</td>
							<td style="text-align: center" colspan="2"><%--人當平均--%> 人當平均</td>
						</tr>
						<tr>

							<td style="text-align: center"><%--金额--%> 金额</td>
							<td style="text-align: center" ><%--%--%> <font color="blue">(%)</font></td>
							<td style="text-align: center"><%--金额--%> 金额</td>
							<td style="text-align: center" ><%--%--%> <font color="blue">(%)</font></td>
							<tr>
								<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_SUM}</td>
								<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#CDC9C9">${total.BASE_SALARY_AVG}</td>
								<td style="text-align: center" bgcolor="#CDC9C9"><font color="blue">${total.BASE_SALARY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.BONUS_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.BONUS_SUM_P}</font></td>
								<td style="text-align: center">${total.BONUS_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.BONUS_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.OVERTIME_PAY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_SUM_P}</font></td>
								<td style="text-align: center">${total.OVERTIME_PAY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.OVERTIME_PAY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.POSITION_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.POSITION_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.POSITION_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.DUTY_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.DUTY_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.DUTY_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.PROFESSIONAL_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.PROFESSIONAL_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.MEALS_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.MEALS_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.MEALS_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_SUM_P}</font></td>
								<td style="text-align: center">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.LEGAL_HOLIDAY_ALLOWANCE_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.COMMISSION_SALARY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_SUM_P}</font></td>
								<td style="text-align: center">${total.COMMISSION_SALARY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.COMMISSION_SALARY_AVG_P}</font></td>

							</tr>

							<tr>
								<td style="text-align: center">${total.CHAJIAN_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.CHAJIAN_SUM_P}</font></td>
								<td style="text-align: center">${total.CHAJIAN_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.CHAJIAN_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.SHOULD_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF99">${total.SHOULD_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.SHOULD_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_SUM}</td>
								<td style="text-align: center" bgcolor="#C0C0C0"><font
									color="blue">${total.INSURANCE_TOTAL_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#C0C0C0">${total.INSURANCE_TOTAL_PERSONAL_AVG}</td>
								<td style="text-align: center" bgcolor="#C0C0C0"><font
									color="blue">${total.INSURANCE_TOTAL_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.INSURANCE_PERSONAL_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center">${total.INSURANCE_PERSONAL_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_SUM_P}</font></td>
								<td style="text-align: center">${total.HOUSE_FUNDING_PERSONAL_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_PERSONAL_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.PURE_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFCC99">${total.PURE_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.PURE_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.TAX_PERSONAL_INCOME_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_SUM_P}</font></td>
								<td style="text-align: center">${total.TAX_PERSONAL_INCOME_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.TAX_PERSONAL_INCOME_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_SUM}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.ACT_GET_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFCC99">${total.ACT_GET_AVG}</td>
								<td style="text-align: center" bgcolor="#FFCC99"><font
									color="blue">${total.ACT_GET_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.INSURANCE_TOTAL_COMPANY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF99">${total.INSURANCE_TOTAL_COMPANY_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF99"><font
									color="blue">${total.INSURANCE_TOTAL_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.INSURANCE_COMPANY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_SUM_P}</font></td>
								<td style="text-align: center">${total.INSURANCE_COMPANY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.INSURANCE_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_SUM}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_SUM_P}</font></td>
								<td style="text-align: center">${total.HOUSE_FUNDING_COMPANY_AVG}</td>
								<td style="text-align: center"><font color="blue">${total.HOUSE_FUNDING_COMPANY_AVG_P}</font></td>

							</tr>
							<tr>
								<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_SUM}</td>
								<td style="text-align: center" bgcolor="#FFFF00"><font
									color="blue">${total.TOTAL_PAY_COMPANY_SUM_P}</font></td>
								<td style="text-align: center" bgcolor="#FFFF00">${total.TOTAL_PAY_COMPANY_AVG}</td>
								<td style="text-align: center" bgcolor="#FFFF00"><font
									color="blue">${total.TOTAL_PAY_COMPANY_AVG_P}</font></td>

							</tr>
					</table>
					
				</c:forEach></td>
		</tr>
		</table>
		</td>

	</tr>




</table>
</body>
</html>