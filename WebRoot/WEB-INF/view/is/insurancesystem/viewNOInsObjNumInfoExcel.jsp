 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	对象管理信息表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=instanceObjmanagementnum1.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="12" >
	    			<b><font size="+2">
	    				对象管理信息表
	    			</font></b>
	    		</td>
	    	</tr>
			<thead>
			<tr>
				<th width="50"><!-- 序号-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM"/>
				</th>
				<th width="100" ><!-- 部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="100" ><!-- 职号-->
					<spring:message code="display.emp.statistics.mes209"/>
				</th>
				<th width="80"><!-- 姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="80">
				<!-- 社保状态 --><spring:message code="is.objmanagement.title.socialstate" />
				</th>
				
				<th width="130">
				<!-- 社会保险号码 --><spring:message code="is.objmanagement.title.socialInsureNum"/>
				</th>
				<th width="100">
				<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
				</th>				
				<th width="100" >
				<!-- 年度基数--> <spring:message code="display.emp.statistics.mes208" />
				</th>
				<th width="100" >
				<!-- 标记--> <spring:message code="is.joininstance.title.remarking" />
				</th>
			
				<th width="50">
				<!-- 养老 --><spring:message code="is.joininstance.title.yanglao" />
				</th>
				<th width="50">
				<!-- 医疗 --><spring:message code="is.joininstance.title.yiliao" />
				</th>
				<th width="50">
				<!-- 生育 --><spring:message code="is.joininstance.title.shengyu" />
				</th>
				<th width="50">
				<!-- 工伤 --><spring:message code="is.joininstance.title.gongshang" />
				</th>
				<th width="50">
				<!-- 失业 --><spring:message code="is.joininstance.title.shiye" />
				</th>	
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
							
							<td >
								${i.index + 1}&nbsp;
							</td>
							<td align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td >
								${show.EMPID}&nbsp;
							</td>
							<td >
								${show.CHINESENAME}&nbsp;
							</td>
							<td >
								${show.SOCIAL_STATUS}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.JOIN_VALUE}&nbsp;
							</td>
							<td >
								${show.AVG_SALARY}&nbsp;
							</td>
							<td >
								${show.BASELINE}&nbsp;
							</td>
							<td >
								${show.ENDOWMENT_BASE}&nbsp;
							</td>
							<td >
								${show.MEDICARE_BASE}&nbsp;
							</td>
							<td >
								${show.SHENGYU_BASE}&nbsp;
							</td>
							<td >
								${show.COMPO_BASE}&nbsp;
							</td>
							<td >
								${show.UNEMP_BASE}&nbsp;
							</td>
						</tr>
						</c:forEach>
					</tbody>
</table>
</body>
</html>