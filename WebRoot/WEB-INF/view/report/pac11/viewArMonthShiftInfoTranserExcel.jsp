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
		response.setHeader("Content-Disposition", "attachment; filename=YuanGongKaoQinYueBiao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   
<table width="1000" border="1">
  <tr>
    <td colspan="19" ><font size="+3">乐天文化中心店-员工考勤月表             ${YEAR }年${MONTH }月</font></td>
  </tr>
  <tr>
    <td>序号</td>
    <td>部门</td>
    <td>工号</td>
    <td>姓名</td>
    <td>正式病假</td>
    <td>试用病假</td>
    <td>正式事假</td>
    <td>试用事假</td>
    <td>年假</td>
    <td>婚丧</td>
    <td>工伤假</td>
    <td>生日假</td>
    <td>调休</td>
    <td>流产假</td>
    <td>出差</td>
    <td>夜班</td>
    <td>迟早</td>
    <td>辞新时间</td>
    <td>备注</td>
    
  </tr>
  <c:forEach items="${dataList}" var="temp" varStatus="i">
  	<tr>
	    <td>${i.index+1 }</td>
	    <td>${temp.DEPT_NAME }</td>
	    <td>${temp.EMPID }</td>
	    <td>${temp.LOCAL_NAME }</td>
	    <td>${temp.ZHENGSHIBJSHISHU }</td>
	    <td>${temp.SHIYONGBJSHISHU }</td>
	    <td>${temp.ZHENGSHISJSHISHU }</td>
	    <td>${temp.SHIYONGSJSHISHU }</td>
	    <td>${temp.ANNUAL_VACATION }</td>
	    <td>${temp.HUNSANG }</td>
	    <td>${temp.INDUSTRY_INJURY }</td>
	    <td>${(temp.BIRTHDAY_TAL) eq null?0:(temp.BIRTHDAY_TAL) }</td>
	    <td>${temp.TIAOXIUTIANSHU }</td>
	    <td>${temp.LIUCHANJIATIANSHU }</td>
	    <td>${temp.WAICHUSHISHU }</td>
	    <td>${temp.NIGHT_SHIFT }</td>
	    <td>${temp.CHIDAOZAOTUI }</td>
	    <td>${((temp.WEIQIN) eq null?0:(temp.WEIQIN ))<0?0:((temp.WEIQIN) eq null?0:(temp.WEIQIN) )}</td>
	    <td>&nbsp;</td>
	  </tr>
  </c:forEach>
  
 
</table>
 
</body>
</html>