 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	人员查看
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=contractInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="16" >
	    			<b><font size="+2"><%--合同信息表--%>
	    				人员查看
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
													<!--社号  -->
												</th>
												<th width="80">
													<spring:message code="hr.viewHire.title.LOCALNAMEANDPINYIN"/>
													<!--姓名  (拼音)-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
													<!--身份证号-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.SEX" />
						<!--性别-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
												</th>
												<th width="80">
															<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门-->
									
												</th>
												<th width="80">
										<spring:message
							code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" />
						<!--职等-->
									
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
						<!--职责-->
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
						<!--职级(GGS)-->
												</th>
												<th width="80">
												
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)  -->
					</td>
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.ENTRY_DATE"/>
						<!--入司日期 即子公司入职日期 -->
												</th>
												<th width="80">
											<spring:message code="hr.viewPersonalInfo.title.IN_THE_DIFFERENCE" />
						<!--在职区分-->
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE" />
						<!--现部门异动日期-->
												</th>
												<th width="80">
											<spring:message code="liang.hr.viewPersonalInfo.title.ADVANCEMENT_DATE"/>
						<!--晋升日期-->	
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
						<!--离职日期-->	
												</th>
												<th width="80">
										<spring:message
							code="hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY" />
						<!--司内工作年资-->		
												</th>
												<th width="80">
									<spring:message code="hr.viewPersonalInfo.title.EMPLOYMENT_TYPE"/>
						<!--雇佣类型-->			
												</th>
												<th width="80">
												<spring:message code="hr.viewPersonalInfo.title.WORK_TIME_TYPE"/>
						<!--工作时间类型  -->	
												</th>
												<th width="80">
									<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
						<!--契约类型 （${personInfo.CONTRACT_NAME } 契约名称）-->			
												</th>
											
												<th width="80">
										<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
						<!--详细人力区分 -->		
												</th>
											</tr>
	      <c:forEach items="${perinfo}" var="item" varStatus="i">
												<tr >
													<td class='td_left'>${item.EMPID}</td>
													<td class='td_left'>${item.LOCAL_NAME}(${item.CHINESE_PINYIN})</td>
													<td>${item.IDCARD_NO}&nbsp;</td>
													<td>${item.SEX}</td>
													<td>
														<fmt:formatDate value="${item.DOB}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.DEPTNAME}</td>
													<td>${item.GRADE_LEVEL}</td>
													
													<td>${item.DUTY_NO}</td>
													<td>${item.GRADE_NO}</td>
													<td>${item.POST_NO}</td>
													<td>
														<fmt:formatDate value="${item.JOIN_COMPANY_DATE}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.IN_THE}</td>
													<td>${item.NOW_DEPARTMENT_DATE}</td>
													<td>
														<fmt:formatDate value="${item.PROMOTION_DATE}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.DATE_LEFT}</td>
													<td>${item.WORK_YEAR}</td>
													<td>${item.EMPLOYMENTTYPE}</td>
													<td>${item.WORKTIMETYPE}</td>
													<td>${item.CONTRACTTYPE}</td>
													<td>${item.TYPECODE}</td>
												</tr>	
											
											</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>