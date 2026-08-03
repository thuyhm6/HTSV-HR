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
		response.setHeader("Content-Disposition", "attachment; filename=KeBieKaoQinZhiFu.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   
<table  border="1">
  <tr>
    <td colspan="29">${YEAR }年${MONTH }月 科别考勤工资支付现状</td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
    <td colspan="12">当月（${MONTH }月）</td>
    <td colspan="12">累计（1-${MONTH }月）</td>
  </tr>
  <tr>
    <td colspan="3" rowspan="3">部门科室</td>
    <td colspan="2">考勤年月</td>
    <td colspan="4">${YEAR -1}年${MONTH }月考勤</td>
    <td colspan="4">${YEAR }年${MONTH }月考勤</td>
    <td colspan="4">增减</td>
    <td colspan="4">${YEAR -1}年1-${MONTH }月考勤</td>
    <td colspan="4">${YEAR }年1-${MONTH }月考勤</td>
    <td colspan="4">增减</td>
  </tr>
  <tr>
    <td colspan="2">销售金额</td>
    <c:forEach items="${xiaoShouJinE}" var="temp">
	    <td colspan="4">${temp.RETURN_VALUE1 }</td>
	    <td colspan="4">${temp.RETURN_VALUE2 }</td>
	    <td colspan="4">${temp.RETURN_VALUE2-temp.RETURN_VALUE1 }</td>
	    <td colspan="4">${temp.RETURN_VALUE3 }</td>
	    <td colspan="4">${temp.RETURN_VALUE4 }</td>
	    <td colspan="4">${temp.RETURN_VALUE4-temp.RETURN_VALUE3 }</td>
    </c:forEach>	
  </tr>
  <tr>
    <td colspan="2">分类</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
    <td>人数</td>
    <td>人件费</td>
    <td>人均人件费</td>
    <td>负担率</td>
  </tr>
  
  
  
  <jsp:include page="viewPaDivisionsAttendanceTranserExcel2.jsp">
  	<jsp:param value="${shengChanList}" name="shengChanList"/>
  	<jsp:param value="${shengChanXiaoJiList}" name="shengChanXiaoJiList"/>
  	<jsp:param value="${shengChanBuMenXiaoJiList}" name="shengChanBuMenXiaoJiList"/>
  	<jsp:param value="${shengChanBuMenHeJiXiaoJiList}" name="shengChanBuMenHeJiXiaoJiList"/>
  	<jsp:param value="${shengChanZhiJieHeJiList}" name="shengChanZhiJieHeJiList"/>
  	<jsp:param value="${shengChanZhiJieHeJiXiaoJiList}" name="shengChanZhiJieHeJiXiaoJiList"/>
  	<jsp:param value="${shengChanZongHeList}" name="shengChanZongHeList"/>
  	<jsp:param value="${shengChanZongHeXiaoJiList}" name="shengChanZongHeXiaoJiList"/>
  </jsp:include>
  
  


  
  
  
  
  <tr>
    <td colspan="2" rowspan="33">管理部</td>
    <td rowspan="15">支援team</td>
    <%int num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0415' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C0415' && temp.EMP_TYPE_CODE=='14890'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C0415'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>
  
  
  
  
  
  <tr>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0414' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C0414' && temp.EMP_TYPE_CODE=='14890'}">
	    	<tr>
			    <td>劳务</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  </tr>
			</c:if>
	    </c:forEach>	
		
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C0414'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>
  
  
  
  <tr>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0413' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C0413' && temp.EMP_TYPE_CODE=='1369'}">
	    	<tr>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  </tr>
			</c:if>
	    </c:forEach>	
		
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C0413'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>





  <tr>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0410' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C0410' && temp.EMP_TYPE_CODE=='1369'}">
	    	<tr>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  </tr>
			</c:if>
	    </c:forEach>	
		
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C0410'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>
  


 <tr>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${zongWuRenShiXiaoJiList}" var="temp">
    	<c:if test="${temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">总务人事小计</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.EMP_TYPE_CODE=='14890'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${zongWuRenShiZongXiaoJiList}" var="temp">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
    </c:forEach>
  </tr>



  
<tr>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C048' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td colspan="2" rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C048' && temp.EMP_TYPE_CODE=='1369'}">
	    	<tr>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  </tr>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C048'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>




  <tr>
    <td rowspan="9">生产管理科</td>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C046' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C046' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C046'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>



<tr>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C047' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C047' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C047'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>

  
  
  
  
  
  <tr>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanBuMenXiaoJiList}" var="temp">
    	<c:if test="${temp.PARENT_DEPT_NO=='C045' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">生产管理科小计</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.PARENT_DEPT_NO=='C045' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanBuMenHeJiXiaoJiList}" var="temp">
	    	<c:if test="${temp.PARENT_DEPT_NO=='C045'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>
  
  
  
  
   <tr>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C049' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td colspan="2" rowspan="3">${temp.DEPT_NAME}</td>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.DEPTNO=='C049' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0</td>
			    <td>0%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${shengChanXiaoJiList}" var="temp">
	    	<c:if test="${temp.DEPTNO=='C049'}">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
	    	
    </c:forEach>
  </tr>
  
  <tr>
    <td colspan="2" rowspan="3">管理部合计</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${guanLiBuHeJiList}" var="temp">
    	<c:if test="${temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>正式</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.EMP_TYPE_CODE=='14890'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		<c:forEach items="${guanLiBuHeJiXiaoJiList}" var="temp">
	    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
			    <td>小计</td>
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
    </c:forEach>
  </tr>
  <tr>
    <td colspan="5">驻在员</td>
    <c:forEach items="${zhuZaiYuanMonthList}" var="temp">
			    <td>${temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${temp.RS2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.RENJUN2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${temp.RS2-temp.RS1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.RENJUN2-temp.RENJUN1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${temp.RS4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.RENJUN4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${temp.RS4-temp.RS3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.RENJUN4-temp.RENJUN3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
    </c:forEach>
  </tr>
  
  
  
  <tr>
    <td colspan="4" rowspan="3">公司总计</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${gongSiZongJi}" var="temp">
    <c:forEach items="${gongSiZongJiRS}" var="item">
    	<c:if test="${temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>正式</td>
			    <td>${item.Z1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.PEOPLEWARE_FEE1/item.Z1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${item.Z2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.PEOPLEWARE_FEE2/item.Z2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${item.Z2-item.Z1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.PEOPLEWARE_FEE2/item.Z2-temp.PEOPLEWARE_FEE1/item.Z1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${item.Z3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.PEOPLEWARE_FEE3/item.Z3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${item.Z4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.PEOPLEWARE_FEE4/item.Z4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${item.Z4-item.Z3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.PEOPLEWARE_FEE4/item.Z4-temp.PEOPLEWARE_FEE3/item.Z3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
	    	<%if(0!=num){%></tr><%}num=1;%>
	    	</c:if>
	    	<c:if test="${temp.EMP_TYPE_CODE=='14890'}">
	    	<%if(0!=num){%><tr><%}%>
			    <td>劳务</td>
			    <td>${item.Z1}</td>
			    <td>${temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.PEOPLEWARE_FEE1/item.Z1}</td>
			    <td>${temp.FUDANLV1}%</td>
			    <td>${item.Z2}</td>
			    <td>${temp.PEOPLEWARE_FEE2}</td>
			    <td>${temp.PEOPLEWARE_FEE2/item.Z2}</td>
			    <td>${temp.FUDANLV2}%</td>
			    <td>${item.Z2-item.Z1}</td>
			    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
			    <td>${temp.PEOPLEWARE_FEE2/item.Z2-temp.PEOPLEWARE_FEE1/item.Z1}</td>
			    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
			    <td>${item.Z3}</td>
			    <td>${temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.PEOPLEWARE_FEE3/item.Z3}</td>
			    <td>${temp.FUDANLV3}%</td>
			    <td>${item.Z4}</td>
			    <td>${temp.PEOPLEWARE_FEE4}</td>
			    <td>${temp.PEOPLEWARE_FEE4/item.Z4}</td>
			    <td>${temp.FUDANLV4}%</td>
			    <td>${item.Z4-item.Z3}</td>
			    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
			    <td>${temp.PEOPLEWARE_FEE4/item.Z4-temp.PEOPLEWARE_FEE3/item.Z3}</td>
			    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
			  <%if(0!=num){%></tr><%}num=1;%>
			</c:if>
		</c:forEach>	
		</c:forEach>	
		<c:forEach items="${gongSiZongJiXiaoJi}" var="temp">
			<c:forEach items="${gongSiZongJiRS}" var="item">
		    	<%if(0!=num){%><tr style="background-color: yellow"><%}%>
				    <td>小计</td>
				    <td>${item.L1+item.Z1}</td>
				    <td>${temp.PEOPLEWARE_FEE1}</td>
				    <td>${temp.PEOPLEWARE_FEE1/item.L1+item.Z1}</td>
				    <td>${temp.FUDANLV1}%</td>
				    <td>${item.L2+item.Z2}</td>
				    <td>${temp.PEOPLEWARE_FEE2}</td>
				    <td>${temp.PEOPLEWARE_FEE2/item.L2+item.Z2}</td>
				    <td>${temp.FUDANLV2}%</td>
				    <td>${item.L2+item.Z2-item.L1-item.Z1}</td>
				    <td>${temp.PEOPLEWARE_FEE2-temp.PEOPLEWARE_FEE1}</td>
				    <td>${temp.PEOPLEWARE_FEE2/item.L2+item.Z2-temp.PEOPLEWARE_FEE1/item.L1+item.Z1}</td>
				    <td>${temp.FUDANLV2-temp.FUDANLV1}%</td>
				    <td>${item.L3+item.Z3}</td>
				    <td>${temp.PEOPLEWARE_FEE3}</td>
				    <td>${temp.PEOPLEWARE_FEE3/item.L3+item.Z3}</td>
				    <td>${temp.FUDANLV3}%</td>
				    <td>${item.L4+item.Z4}</td>
				    <td>${temp.PEOPLEWARE_FEE4}</td>
				    <td>${temp.PEOPLEWARE_FEE4/item.L4+item.Z4}</td>
				    <td>${temp.FUDANLV4}%</td>
				    <td>${item.L4+item.Z4-item.L3-item.Z3}</td>
				    <td>${temp.PEOPLEWARE_FEE4-temp.PEOPLEWARE_FEE3}</td>
				    <td>${temp.PEOPLEWARE_FEE4/item.L4+item.Z4-temp.PEOPLEWARE_FEE3/item.L3+item.Z3}</td>
				    <td>${temp.FUDANLV4-temp.FUDANLV4}%</td>
				  <%if(0!=num){%></tr><%}num=1;%>
	    	</c:forEach>	
    </c:forEach>
  </tr>	
</table>
 
</body>
</html>