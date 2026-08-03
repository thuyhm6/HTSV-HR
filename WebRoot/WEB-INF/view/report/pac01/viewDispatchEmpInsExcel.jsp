 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--乐天玛特-外派人员保险信息表--%>
	乐天玛特-外派人员保险信息表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=LotteMartInsInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="44" >
		    			<b><font size="+2">乐天玛特${YEAR_INS }年${MONTH_INS }月，外派人员保险信息</font></b>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td style="text-align: center" rowspan="3"><%--序号--%>
						序号
					</td>
					<td style="text-align: center" rowspan="3"><%--派入店--%>
						派入店
					</td>
					<td style="text-align: center" rowspan="3"><%--工号--%>
						工号
					</td>
					<td style="text-align: center" rowspan="3"><%--姓名--%>
						姓名
					</td>
					<td style="text-align: center" rowspan="3"><%--社保地--%>
						社保地
					</td>
					<td style="text-align: center" rowspan="3"><%--部门--%>
						部门
					</td>
					<td style="text-align: center" rowspan="3"><%--职级--%>
						职级
					</td>
					<td style="text-align: center" rowspan="3"><%--入职日期--%>
						入职日期
					</td><%--
					<td style="text-align: center" rowspan="3">工作地
						工作地
					</td>--%>
					
					<td style="text-align: center" rowspan="3"><%--基本薪资--%>
						基本薪资
					</td>
					<td style="text-align: center" rowspan="3"><%--外派津贴--%>
						外派津贴
					</td>
					<td style="text-align: center" rowspan="3"><%--其它给付--%>
						其它给付
					</td>
					<td style="text-align: center" rowspan="3"><%--应发薪资--%>
						应发薪资
					</td>
					
					<td style="text-align: center" colspan="3"><%--缴金基准--%>
						缴金基准
					</td>
					
					<td style="text-align: center" colspan="22"><%--单位部分社保--%>
						单位部分社保
					</td>
					<td style="text-align: center" colspan="16"><%--个人部分社保--%>
						个人部分社保
					</td>
					<td style="text-align: center" rowspan="3"><%--社保合计--%>
						社保合计
					</td>
		    	</tr>
				<tr>
					<%--缴金基准--%>
					<td style="text-align: center" rowspan="2"><%--养老缴金基数--%>
						养老缴金基数
					</td>
					<td style="text-align: center" rowspan="2"><%--医疗缴金基数--%>
						医疗缴金基数
					</td>
					<td style="text-align: center" rowspan="2"><%--公积金缴金基数--%>
						公积金缴金基数
					</td>
					
					<%--单位保险--%>
					<td style="text-align: center" colspan="3"><%--养老--%>
						养老
					</td>
					<td style="text-align: center" colspan="3"><%--医疗--%>
						医疗
					</td>
					<td style="text-align: center" colspan="3"><%--大病医疗--%>
						大病医疗
					</td>
					<td style="text-align: center" colspan="3"><%--失业--%>
						失业
					</td>
					<td style="text-align: center" colspan="3"><%--工伤--%>
						工伤
					</td>
					<td style="text-align: center" colspan="3"><%--生育--%>
						生育
					</td>
					<td style="text-align: center" colspan="3"><%--公积金--%>
						公积金
					</td>
					<td style="text-align: center" rowspan="2"><%--单位合计--%>
						单位合计
					</td>
					
					<%--个人保险--%>
					<td style="text-align: center" colspan="3"><%--养老--%>
						养老
					</td>
					<td style="text-align: center" colspan="3"><%--医疗--%>
						医疗
					</td>
					<td style="text-align: center" colspan="3"><%--大病医疗--%>
						大病医疗
					</td>
					<td style="text-align: center" colspan="3"><%--失业--%>
						失业
					</td>
					<td style="text-align: center" colspan="3"><%--公积金--%>
						公积金
					</td>
					<td style="text-align: center" rowspan="2"><%--个人合计--%>
						个人合计
					</td>
				</tr>
				<tr>
					<c:forEach var="i" begin="1" end="12" step="1"> 
				    	<td style="text-align: center"><%--缴金基数--%>
							缴金基数
						</td>
						<td style="text-align: center"><%--缴金比例--%>
							缴金比例
						</td>
						<td style="text-align: center"><%--应缴值--%>
							应缴值
						</td>
				    </c:forEach>
				</tr>        
		       	<c:forEach items="${dispatchEmpInsList}" var="pa" varStatus="i">
					<tr>
						<td style="text-align: center">${i.index + 1 }</td>
						<td style="text-align: center">${pa.DEPT_DISTINGUISH_NAME}</td>
						<td style="text-align: center">${pa.EMPID}</td>
						<td style="text-align: center">${pa.LOCAL_NAME}</td>
						<td style="text-align: center">${pa.SOCIAL_SECURITY_AREA_NAME}</td>
						<td style="text-align: center">${pa.DEPT_NAME}</td>
						<%--<td style="text-align: center">${pa.WORK_AREA_NAME}</td>--%>
						<td style="text-align: center">${pa.POST_GRADE_NAME}</td>
						<td style="text-align: center">${pa.DATE_STARTED}</td>
						
						<td style="text-align: center">${pa.STANDARD_SALARY}</td>
						<td style="text-align: center">${pa.RESIDENT_OUTSIDE_ALLOWANCE}</td>
						<td style="text-align: center">${pa.ALLOWANCE_TOTAL}</td>
						<td style="text-align: center">${pa.SHOULD_RELEASE_SALARY}</td>
						
						<%--缴金基准--%>
						<td style="text-align: right"><%--养老缴金基数--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_BASE_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--医疗缴金基数--%>
							<fmt:formatNumber value="${pa.MEDICAL_BASE_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--公积金缴金基数--%>
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						
						<%--单位保险信息--%>
						<%--公司养老--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_BASE_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_PROPORTION_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司医疗--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.MEDICAL_BASE_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.MEDICAL_PROPORTION_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.MEDICAL_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司大病医疗--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_FLAG_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_PRO_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司失业--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_CAL_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_PROPORTION_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_IS_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司工伤--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.INDUCTRIAL_INJURY_CAR_CNY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.INDUCTRIAL_INJURY_PRO_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.INDUCTRIAL_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司生育--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.BIRTH_INSURANCE_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.BIRTH_PROPORTION}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.BIRTH_INSURANCE_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司公积金--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_CAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_PROPORTION_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_COMPANY}" pattern="#,##0.00#"/>
						</td>
						<%--公司社保追缴、公司公积金追缴、公司社保应缴值合计
						<td style="text-align: right"><%--公司社保追缴
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_AFTER_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--公司公积金追缴
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_AFTERWARDS_COM}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--公司社保应缴值合计
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_TOTAL_COMPANY}" pattern="#,##0.00#"/>
						</td>
						--%>
						<td style="text-align: right"><%--公司社保应合计（不包含公司社保追缴）--%>
							<fmt:formatNumber value="${pa.COMPANY_INS_TOTAL}" pattern="#,##0.00#"/>
						</td>
						
						<%--个人保险信息--%>
						<%--个人养老--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_BASE_CAL_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_PROPORTION_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ENDOWMENT_IS_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<%--个人医疗--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.MEDICAL_BASE_CAL_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.MEDICAL_PROPORTION_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.MEDICAL_INSURANCE_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<%--个人大病医疗--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_FLAG_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_PRO_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ILLNESS_MEDICAL_IS_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<%--个人失业--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_CAL_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_PROPORTION_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.UNEMPLOYMENT_IS_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<%--个人公积金--%>
						<td style="text-align: right"><%--基数--%>
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_CAL_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--比例--%>
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_PROPORTION_PEL}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值--%>
							<fmt:formatNumber value="${pa.ACCUMULATION_FUND_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						<%--个人社保追缴、个人公积金追缴、个人社保应缴值合计
						<td style="text-align: right"><%--个人社保追缴
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_AFTER_PER}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--个人公积金追缴
							<fmt:formatNumber value="${pa.ACCUMULA_FUND_AFTERWARDS_PEL}" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right"><%--应缴值
							<fmt:formatNumber value="${pa.SOCIAL_SECURITY_TOTAL_PERSONAL}" pattern="#,##0.00#"/>
						</td>
						--%>
						<td style="text-align: right"><%--个人社保合计（不包含个人社保追缴）--%>
							<fmt:formatNumber value="${pa.PERSON_INS_TOTAL}" pattern="#,##0.00#"/>
						</td>
						
						<td style="text-align: right"><%--社保合计--%>
							<fmt:formatNumber value="${pa.COMPANY_INS_TOTAL + pa.PERSON_INS_TOTAL}" pattern="#,##0.00#"/>
						</td>
					</tr>
				</c:forEach>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>