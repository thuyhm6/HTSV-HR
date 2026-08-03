 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	临时职人员导入
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=tempEmpImportList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="200%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="1" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
			<tr>
				<th>法人代码</th>
				<th>部门 代码</th>
				<th>法人入职日期</th>
				<th>入职类型</th>
				<th>中文姓名</th>
				<th>英文姓名</th>
				<th>试用期开始日期</th>
				<th>试用期结束日期</th>
				<th>试用期比例%</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>最终学历</th>
				<th>生日</th>
				<th>ID卡号</th>
				<th>手机号码</th>
				<th>邮箱</th>
				<th>户口性质</th>
				<th>户口所在地</th>
				<th>工资级号</th>
				<th>工资级号等级</th>
				<th>基本工资</th>
				<th>变动工资</th>
				<th>开户行</th>
				<th>银行账号</th>
				<th>福利地区</th>
				<th>人员类型(CHR)</th>
				<th>工作地区</th>
				<th>工作类型(CHR)</th>
				<th>班号</th>
				<th>促销员所属</th>
				<th>星级级别</th>
				<th>产品</th>
				<th>兼卖产品</th>
				<th width="100"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.CPNY_ID}</td>
					<td>${item.DEPTNO}</td>
					<td><fmt:formatDate value="${item.JOIN_COMPANY_DATE}" pattern="yyyy-MM-dd" /></td>
					<td>${item.JOIN_TYPE_CODE}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.CHINESE_PINYIN}</td>
					<td><fmt:formatDate value="${item.PROB_STRT_DATE}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${item.END_PROBATION_DATE}" pattern="yyyy-MM-dd" /></td>
					<td>${item.PROB_PAY_RAT}</td>
					<td>${item.SEXCODE}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.FINAL_DEGREE_CODE}</td>
					<td><fmt:formatDate value="${item.DOB}" pattern="yyyy-MM-dd" /></td>
					<td>${item.IDCARD_ADDR}</td>
					<td>${item.CELLPHONE}</td>
					<td>${item.EMAIL}</td>
					<td>${item.REG_TYPE_CODE}</td>
					<td>${item.REG_PLACE}</td>
					<td>${item.PAY_GRADE}</td>
					<td>${item.PAY_STEP}</td>
					<td>${item.BASE_PAY}</td>
					<td>${item.VARB_PAY}</td>
					<td>${item.CARD_NAME}</td>
					<td>${item.CARD_NO}</td>
					<td>${item.INSRAREA_ID}</td>
					<td>${item.EMP_TYPE_CODE}</td>
					<td>${item.WORK_AREA}</td>
					<td>${item.PROMTR_WORK_TP}</td>
					<td>${item.SHIFT_NO}</td>
					<td>${item.PROMTR_TP}</td>
					<td>${item.STAR_TP}</td>
					<td>${item.PROD_TP}</td>
					<td>${item.PROD_ADD}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>