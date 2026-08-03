 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	<!-- 合同信息表 --><spring:message code="hrm.contractInfo.HETONGXINXIBIAO" />
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
	    		<td align="center" colspan="11" >
	    			<b><font size="+2"><%--合同信息表--%>
	    		<!-- 合同信息表 --><spring:message code="hrm.contractInfo.HETONGXINXIBIAO" />
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="60"><!--合同次数-->
					<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD"/>
				</th>
				<th width="70"><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th width="60"><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th width="100"><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th width="80"><!-- 工作地-->
					<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu"/>
				</th>
				<th width="130"><!-- 合同类型-->
					<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
				</th>
				<th width="120"><!--起始日期-->
					<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
				</th>
				<th width="120"><!--终止日期-->
					<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
				</th>
				<th width="100"><!--续签意见:-->
					<spring:message code="hr.contract.title.xuqian.yijian"/>
				</th>
				<th width="100"><!--审批意见-->
					<spring:message code="hr.contract.title.shenpi.yijian"/>
				</th>
				<th width="70"><!--审批情况-->
					<spring:message code="hr.contract.title.shenpi.qingkuang"/>
				</th>
			</tr> 
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.TOTAL_PERIOD}</td>
					<td class="td_center">${item.EMPID}</td>
					<td class="td_center">${item.LOCAL_NAME}</td>
					<td>${item.DEPARTMENT_NAME}</td>
					<td class="td_center">${item.WORK_AREA_NAME}</td>
					<td>${item.CONTRACT_TYPE}</td>
					<td class="td_center">${item.CONTRACTSTARTDATE}</td>
					<td class="td_center">${item.CONTRACTENDDATE}</td>
					<td class="td_center">${item.REMARK}</td>
					<td style="text-align: center;padding-top:7px;">
					${item.EXPRIED_CONTRACT}
					</td>
					<td class="td_center">
						<c:if test="${item.ACTIVITY==1}">
							<font color="blue">
								OK
							</font>
						</c:if>
						<c:if test="${item.ACTIVITY==2}">
							<font color="red">
								REJECT
							</font>
						</c:if>
						<c:if test="${item.ACTIVITY==0}">
							<font color="red">
								<spring:message code="ess.viewApply.title.notAffirmed"/>
							</font>
						</c:if>
					</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>