 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

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
 
 <tr>
    <td rowspan="42" width="25">生产部</td>
    <td  rowspan="30" width="25">直接</td>
    <td  rowspan="15" width="25">饼干</td>
    <%int num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0425' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0425' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0425'}">
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
    	<c:if test="${temp.DEPTNO=='C0427' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0427' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0427'}">
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
    	<c:if test="${temp.DEPTNO=='C0429' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0429' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0429'}">
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
    	<c:if test="${temp.DEPTNO=='C0428' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0428' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0428'}">
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
    	<c:if test="${temp.PARENT_DEPT_NO=='C0426' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">饼干小计</td>
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
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0426' && temp.EMP_TYPE_CODE=='14890'}">
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
		<c:forEach items="${shengChanBuMenHeJiXiaoJiList}" var="temp">
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0426'}">
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
    <td rowspan="12">口香糖</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0424' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0424' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0424'}">
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
    	<c:if test="${temp.DEPTNO=='C0422' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0422' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0422'}">
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
    	<c:if test="${temp.DEPTNO=='C0423' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0423' && temp.EMP_TYPE_CODE=='14890'}">
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
	    	<c:if test="${temp.DEPTNO=='C0423'}">
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
    	<c:if test="${temp.PARENT_DEPT_NO=='C0421' && temp.EMP_TYPE_CODE=='1369'}">
	    	<%if(0!=num){%><tr><%}%>
	    	<td  rowspan="3">口香糖小计</td>
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
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0421' && temp.EMP_TYPE_CODE=='14890'}">
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
		<c:forEach items="${shengChanBuMenHeJiXiaoJiList}" var="temp">
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0421'}">
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
    <td colspan="2" rowspan="3">直接合计</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanZhiJieHeJiList}" var="temp">
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
		<c:forEach items="${shengChanZhiJieHeJiXiaoJiList}" var="temp">
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
    <td rowspan="9">间接</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanList}" var="temp">
    	<c:if test="${temp.DEPTNO=='C0419' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0419' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0419'}">
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
    	<c:if test="${temp.DEPTNO=='C0418' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0418' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.DEPTNO=='C0418'}">
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
    <td colspan="2" rowspan="3">间接合计</td>
  	<%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanBuMenXiaoJiList}" var="temp">
    	<c:if test="${temp.PARENT_DEPT_NO=='C0417' && temp.EMP_TYPE_CODE=='1369'}">
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
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0417' && temp.EMP_TYPE_CODE=='1369'}">
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
		<c:forEach items="${shengChanBuMenHeJiXiaoJiList}" var="temp">
	    	<c:if test="${temp.PARENT_DEPT_NO=='C0426'}">
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
    <td colspan="3" rowspan="3">生产部合计</td>
    <%num = 0; %><!--用个num计数器控制刚进入循环的时候不换行直接是td  不加tr   等循环到第二个数据时换行 加tr-->
    <c:forEach items="${shengChanZongHeList}" var="temp">
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
		<c:forEach items="${shengChanZongHeXiaoJiList}" var="temp">
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
  
  
