 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--人员任情表--%>
	人员任情表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=empPaRise.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="25" >
	    			<b><font size="+2"><%--人员任情表--%>
	    				人员任情表
	    			</font></b>
	    		</td>
	    	</tr>
	    	
			<tr>
				<th style="text-align: center" rowspan="2"><%--No--%>
					No
				</th>
				<th style="text-align: center" rowspan="2"><%--现职地--%>
					现职地
				</th>
				<th style="text-align: center" rowspan="2"><%--社保地--%>
					社保地
				</th>
				<th style="text-align: center" rowspan="2"><%--入职地--%>
					入职地
				</th><%-- 
				<th style="text-align: center" rowspan="2">照片
					照片
				</th>--%>
				
				<th style="text-align: center" rowspan="2"><%--工号--%>
					工号
				</th>
				<th style="text-align: center" rowspan="2"><%-- 姓名--%>
					 姓名
				</th>
				<th style="text-align: center" rowspan="2"><%--部门--%>
					部门
				</th>
				<th style="text-align: center" rowspan="2"><%--职级--%>
					职级
				</th>
				<th style="text-align: center" rowspan="2"><%--性别--%>
					性别
				</th>
				
				<th style="text-align: center" rowspan="2"><%--出生年月--%>
					出生年月
				</th>
				<th style="text-align: center" rowspan="2"><%--年龄--%>
					年龄
				</th>
				<th style="text-align: center" rowspan="2"><%--入职日--%>
					入职日
				</th>
				<th style="text-align: center" rowspan="2"><%--最高学历--%>
					最高学历
				</th>
				<th style="text-align: center" rowspan="2"><%--合同到期日--%>
					合同到期日
				</th>
				
				<th style="text-align: center" rowspan="2"><%--学校名称--%>
					学校名称
				</th>
				<th style="text-align: center" rowspan="2"><%--专业--%>
					专业
				</th>
				<th style="text-align: center" rowspan="2"><%--月工资--%>
					月工资
				</th>
				<th style="text-align: center" rowspan="2"><%--联系方式--%>
					联系方式
				</th>
				
				<th style="text-align: center" colspan="3"><%--社外经历--%>
					社外经历
				</th>
				<th style="text-align: center" colspan="4"><%--社内经历--%>
					社内经历
				</th>
			</tr>
			<tr>
				<th style="text-align: center"><%--时间--%>
					时间
				</th>
				<th style="text-align: center"><%--单位--%>
					单位
				</th>
				<th style="text-align: center"><%--职务--%>
					职务
				</th>
				
				<th style="text-align: center"><%--时间--%>
					时间
				</th>
				<th style="text-align: center"><%--单位--%>
					单位
				</th>
				<th style="text-align: center"><%--职务--%>
					职务
				</th>
				<th style="text-align: center"><%--薪资--%>
					薪资
				</th>
			</tr>        
	       	<c:forEach items="${empPaRiseList}" var="item" varStatus="i">
				<tr>
					<td style="text-align: center" rowspan="${item.experNum }">${i.index+1 }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.DEPT_DISTINGUISH_NAME }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.SOCIAL_SECURITY_AREA }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.ENTRY_AREA }</td>
					<%-- <td style="text-align: center" rowspan="${item.experNum }">
						<p><img align="middle" id="${item.PERSON_ID}" src="${item.PhotoPath}"/></p>
					</td>--%>
					
					<td style="text-align: center" rowspan="${item.experNum }">${item.EMPID }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.LOCAL_NAME }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.DEPARTMENT }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.POST_GRADE_NAME }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.SEX }</td>
					
					<td style="text-align: center" rowspan="${item.experNum }">${item.DOB }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.AGE }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.DATE_STARTED }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.FINAL_DEGREE_NAME }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.END_CONTRACT_DATE }</td>
					
					<td style="text-align: center" rowspan="${item.experNum }">${item.FINAL_SCHOOL }</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.FINAL_SUBJECT }</td>
					<td style="text-align: right" rowspan="${item.experNum }">
						<fmt:formatNumber value="${item.PA_BASIC_DATA }" pattern="#,##0.00#"/>
					</td>
					<td style="text-align: center" rowspan="${item.experNum }">${item.CELLPHONE }</td>
					<td style="text-align: center">${item.OUT_START_DATE }
						<c:if test="${item.OUT_END_DATE ne '' && exper.OUT_END_DATE ne null}">&nbsp;&nbsp;~&nbsp;&nbsp;</c:if>
						${item.OUT_END_DATE }
					</td>
					<td style="text-align: center">${item.OUT_CPNY_NAME }</td>
					<td style="text-align: center">${item.OUT_POST_NAME }</td>
					
					<td style="text-align: center">${item.IN_START_DATE }
						<c:if test="${item.IN_END_DATE ne '' && item.IN_END_DATE ne null}">&nbsp;&nbsp;~&nbsp;&nbsp;</c:if>
						${item.IN_END_DATE }
					</td>
					<td style="text-align: center">${item.IN_CPNY_NAME }
						<c:if test="${item.IN_DEPT_NAME ne '' && item.IN_DEPT_NAME ne null}">-</c:if>
						${item.IN_DEPT_NAME }
					</td>
					<td style="text-align: center">${item.IN_POST_NAME }</td>
					<td style="text-align: center">
						<fmt:formatNumber value="${item.IN_PA_BASIC_DATA }" pattern="#,##0.00#"/>
					</td>
				</tr>
				<c:if test="${item.experNum > 1}">
					<c:forEach items="${item.experienceList}" var="exper" varStatus="j">
						<tr>
							<td style="text-align: center">${exper.OUT_START_DATE }
								<c:if test="${exper.OUT_END_DATE ne '' && exper.OUT_END_DATE ne null }">&nbsp;&nbsp;~&nbsp;&nbsp;</c:if>
								${exper.OUT_END_DATE }</td>
							<td style="text-align: center">${exper.OUT_CPNY_NAME }</td>
							<td style="text-align: center">${exper.OUT_POST_NAME }</td>
							
							<td style="text-align: center">${exper.IN_START_DATE }
								<c:if test="${exper.IN_END_DATE ne '' && exper.IN_END_DATE ne null}">&nbsp;&nbsp;~&nbsp;&nbsp;</c:if>
								${exper.IN_END_DATE }</td>
							<td style="text-align: center">${exper.IN_CPNY_NAME }
								<c:if test="${exper.IN_DEPT_NAME ne '' && exper.IN_DEPT_NAME ne null}">-</c:if>
								${exper.IN_DEPT_NAME }</td>
							<td style="text-align: center">${exper.IN_POST_NAME }</td>
							<td style="text-align: center">
								<fmt:formatNumber value="${exper.IN_PA_BASIC_DATA }" pattern="#,##0.00#"/>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>