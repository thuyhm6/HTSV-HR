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
		font-size: 15;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=RenShiDengJiBiao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="54%" border="1" >
   		<c:forEach var="temp"  items="${personBasicInfo}" >
		  <tr>
		    <td colspan="7" height="80">人事登记表</td>
		  </tr>
		  <tr>
		    <td colspan="7" style="text-align: left" height="30">1.基本资料</td>
		  </tr>
		  <tr>
		    <td colspan="2" rowspan="9">&nbsp;</td>
		    <td height="25" width="100">部门</td>
		    <td width="100">${temp.DNAME}</td>
		    <td width="100">职位</td>
		    <td colspan="2">${temp.POSITION_NAME}</td>
		  </tr>
		  <tr>
		    <td height="25">员工号</td>
		    <td>${temp.EMPID}</td>
		    <td height="25">姓名</td>
		    <td colspan="2">${temp.LOCAL_NAME}</td>
		  </tr>
		  <tr>
		    <td>性别</td>
		    <td>${temp.SEX_NAME}</td>
		    <td>民族</td>
		    <td colspan="2">${temp.NATION_NAME}</td>
		  </tr>
		  <tr>
		    <td height="25">出生日期</td>
		    <td>${temp.DOB}</td>
		    <td>身份证号</td>
		    <td colspan="2">${temp.IDCARD_NO}&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="25">手机号码</td>
		    <td>${temp.CELLPHONE}</td>
		    <td>婚姻状况</td>
		    <td colspan="2">${temp.MARITAL_STATUS_NAME}</td>
		  </tr>
		  <tr>
		    <td height="25">户籍地址</td>
		    <td colspan="4">${temp.IDCARD_ADDR}</td>
		  </tr>
		  <tr>
		    <td height="25">联系地址</td>
		    <td colspan="4">${temp.HOME_ADDRESS}</td>
		  </tr>
		  <tr>
		    <td height="25">电子邮件</td>
		    <td colspan="4">${temp.HOME_ADDRESS}</td>
		  </tr>
		  <tr>
		    <td height="25">入社日期</td>
		    <td>${temp.JOIN_COMPANY_DATE}</td>
		    <td>填表日期</td>
		    <td colspan="2">${temp.NEWTIME}</td>
		  </tr>
		  <tr>
		    <td colspan="7" style="text-align: left" height="30">2.教育经历</td>
		  </tr>
		  <tr>
		    <td height="25">入学日期</td>
		    <td>毕业日期</td>
		    <td>学校</td>
		    <td>区分</td>
		    <td>专业</td>
		    <td width="110">地区</td>					<!--所有td的width属性都是控制单元格长度的        勿动！！！！！！！！！！！！！-->
		    <td>日间/夜间</td>
		  </tr>
		  <c:forEach items="${temp.educationList}" var="item">
		  <tr>
		    <td height="25">${item.START_DATE}</td>
		    <td>${item.END_DATE}</td>
		    <td>${item.INSTITUTION_NAME}</td>
		    <td>&nbsp;</td>
		    <td>${item.SUBJECT}</td>
		    <td>${item.SCHOOL_ADDRESS}</td>
		    <td>&nbsp;</td>
		  </tr>
		  </c:forEach>
		  <c:if test="${(4-temp.educationListCnt)>0}">
		  <c:forEach  begin="1" end="${4-temp.educationListCnt}" var="item">
		  <tr>
		    <td height="25">&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  </c:forEach>
		  </c:if>
    	  <tr>
		    <td colspan="7" style="text-align: left" height="30">3.资格/语言</td>
		  </tr>
		  <tr>
		    <td height="25">区分</td>
		    <td>证书</td>
		    <td>登记</td>
		    <td>发证日期</td>
		    <td colspan="2">发证机关</td>
		    <td width="90">备注</td>
		  </tr>
		  <c:forEach items="${temp.qualificationList}" var="item">
		  <tr>
		    <td height="25">资格证书</td>
		    <td>${item.QUAL_NAME}</td>
		    <td>${item.QUAL_LEVEL_NAME}</td>
		    <td>${item.DATE_OBTAINED}</td>
		    <td colspan="2">${item.QUAL_INSTITUTE}</td>
		    <td>&nbsp;</td>
		  </tr>
		  </c:forEach>
		  <c:forEach items="${temp.languageList}" var="item">
		  	<tr>
			    <td height="25">语言证书</td>
			    <td>${item.QUALIFICATION_NAME}</td>
			    <td>${item.LANGUAGE_LEVEL_NAME}</td>
			    <td>${item.DATE_OBTAINED}</td>
			    <td colspan="2">${item.LANGUAGE_LOCATION}</td>
			    <td>&nbsp;</td>
			  </tr>
		  </c:forEach>
		  <c:if test="${(3-temp.qualificationListCnt-temp.languageListCnt)>0}">
		  <c:forEach begin="1" end="${3-temp.qualificationListCnt-temp.languageListCnt}" var="item">
		  <tr>
		    <td height="25">&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  </c:forEach>
		  </c:if>
		  <tr>
		    <td colspan="7" style="text-align: left" height="30">4.家庭情况</td>
		  </tr>
		  
		  <tr>
		    <td height="25">关系</td>
		    <td>姓名</td>
		    <td>出生日期</td>
		    <td>联系电话</td>
		    <td colspan="3">工作单位/职位</td>
		  </tr>
		  <c:forEach items="${temp.homeRelationList}" var="item">
		  <tr>
		    <td height="25">${item.FAM_TYPE_NAME}</td>
		    <td>${item.FAM_NAME}</td>
		    <td>${item.FAM_BORNDATE}</td>
		    <td>${item.FAM_PHONE}</td>
		    <td colspan="3">${item.FAM_COMPANY_NAME}</td>
		  </tr>
		  </c:forEach>
		  <c:if test="${(3-temp.homeRelationListCnt)>0}">
		  <c:forEach begin="1" end="${3-temp.homeRelationListCnt}" var="item">
		  <tr>
		    <td height="25">&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td colspan="3">&nbsp;</td>
		  </tr>
		  </c:forEach>
		  </c:if>
		  <tr>
		    <td colspan="7" style="text-align: left" height="30">5.工作经历</td>
		  </tr>
		  <tr>
		    <td height="25">开始日期</td>
		    <td>结束日期</td>
		    <td>公司名称</td>
		    <td>部门</td>
		    <td>职位</td>
		    <td colspan="2">离职原因</td>
		  </tr>
		  <c:forEach items="${temp.workExperienceList}" var="item">
		  <tr>
		    <td height="25">${item.START_DATE}</td>
		    <td>${item.END_DATE}</td>
		    <td>${item.CPNY_NAME}</td>
		    <td>${item.DEPT_NAME}</td>
		    <td>${item.POSITION}</td>
		    <td colspan="2">${item.RESIGN_REASON}</td>
		  </tr>
		  </c:forEach>
		  <c:if test="${(5-temp.workExperienceListCnt)>0}">
		  <c:forEach begin="1" end="${5-temp.workExperienceListCnt}" var="item">
		  <tr>
		    <td height="25">&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  </c:forEach>
		  </c:if>
		</c:forEach>	
	</table>
</body>
</html>