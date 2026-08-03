<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			<%--员工在职证明--%>
			<spring:message code="rp.report.title.certificateofemployee" />
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
		response.setHeader("Content-Disposition", "attachment; filename=hourly_worker_salary_sheet.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		%>
		<table border="1">
			<tr align="center">
				<td colspan="16" style="font-size: 24;">${YEAR}年${MONTH}月 小时工资发放表</td>
			</tr>
			<tr>
				<td colspan="14" style="text-align: left;">乐天利(北京)餐饮有限公司</td>
				<td colspan="2" style="text-align: right;" id="nyr">${_rDate}</td>
			</tr>
			<tr style="background-color: #B7DEE8;">
				<td>序号</td>
				<td>姓名</td>
				<td>职位</td>
				<td>平日工时</td>
				<td>三薪工时/日</td>
				<td>事假</td>
				<td>病假</td>
				<td>基本工资</td>
				<td>三薪</td>
				<td>缺勤扣款</td>
				<td>工资总额</td>
				<td>社会保险(个人部分)</td>
				<td>所得税</td>
				<td>实付工资</td>
				<td>社会保险(公司部分)</td>
				<td>公司支付总额</td>
			</tr>
			
			<c:set var="paindex" value="0"></c:set>
			<c:forEach items="${paDeptList}" var="deptpa">
				<c:set var="ordernumber" value="0"></c:set>
				<c:forEach items="${paList}" var="pa" begin="${paindex}" end="${deptpa.EMPCOUNT + paindex -1}">
					<c:set var="ordernumber" value="${ordernumber + 1}"></c:set>
					<tr>
						<td>${ordernumber}</td>
						<td>${pa.LOCAL_NAME}</td>
						<td>${pa.CONTENT}</td>
						<td>${pa.HOURLY_TIME}</td>
						<td>${pa.SANXINXIAOSHI}</td>
						<td>${pa.SHIJIA}</td>
						<td>${pa.BINGJIA}</td>
						<td>${pa.BASE_SALARY}</td>
						<td>${pa.LEGAL_HOLIDAY_ALLOWANCE}</td>
						<td>${pa.NO_FREQUENTLY_DEDUCTIONS}</td>
						<td>${pa.TOTAL_WAGE}</td>
						<td>${pa.INSURANCE_TOTAL_PERSONAL}</td>
						<td>${pa.TAX_PERSONAL_INCOME}</td>
						<td>${pa.ACTUAL_RELEASE_SALARY}</td>
						<td>${pa.INSURANCE_TOTAL_COMPANY}</td>
						<td>${pa.ZHIFU}</td>
					</tr>
				</c:forEach>
				<c:set var="paindex" value="${deptpa.EMPCOUNT + paindex}"></c:set>
				<tr style="background-color: #FABF8F;">
					<td></td>
					<td>${deptpa.DEPTNAME}</td>
					<td></td>
					<td>${deptpa.HOURLY_TIME}</td>
					<td>${deptpa.SANXINXIAOSHI}</td>
					<td>${deptpa.SHIJIA}</td>
					<td>${deptpa.BINGJIA}</td>
					<td>${deptpa.BASE_SALARY}</td>
					<td>${deptpa.LEGAL_HOLIDAY_ALLOWANCE}</td>
					<td>${deptpa.NO_FREQUENTLY_DEDUCTIONS}</td>
					<td>${deptpa.TOTAL_WAGE}</td>
					<td>${deptpa.INSURANCE_TOTAL_PERSONAL}</td>
					<td>${deptpa.TAX_PERSONAL_INCOME}</td>
					<td>${deptpa.ACTUAL_RELEASE_SALARY}</td>
					<td>${deptpa.INSURANCE_TOTAL_COMPANY}</td>
					<td>${deptpa.ZHIFU}</td>
				</tr>
			</c:forEach>
			
			<c:forEach items="${paSumList}" var="sumlist">
				<tr style="background-color: #B7DEE8;">
					<td>${sumlist.SUMCOUNT}</td>
					<td>总和</td>
					<td></td>
					<td>${sumlist.HOURLY_TIME}</td>
					<td>${sumlist.SANXINXIAOSHI}</td>
					<td>${sumlist.SHIJIA}</td>
					<td>${sumlist.BINGJIA}</td>
					<td>${sumlist.BASE_SALARY}</td>
					<td>${sumlist.LEGAL_HOLIDAY_ALLOWANCE}</td>
					<td>${sumlist.NO_FREQUENTLY_DEDUCTIONS}</td>
					<td>${sumlist.TOTAL_WAGE}</td>
					<td>${sumlist.INSURANCE_TOTAL_PERSONAL}</td>
					<td>${sumlist.TAX_PERSONAL_INCOME}</td>
					<td>${sumlist.ACTUAL_RELEASE_SALARY}</td>
					<td>${sumlist.INSURANCE_TOTAL_COMPANY}</td>
					<td>${sumlist.ZHIFU}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>