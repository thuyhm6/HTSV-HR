 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	参保人员信息表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=instanceJoinInsurenum1.xls");
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
	    			<b><font size="+2"><%--合同信息表--%>
	    				参保人员信息表
	    			</font></b>
	    		</td>
	    	</tr>
			<tr >
				<th width="50">
					<input type="checkbox" name="c1_bx0103_c" id="c1_bx0103_c" class="checkboxCtrl" group="check" >
				
				<th width="100" >
					<!-- 序号--> <spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
				</th>
				<th width="100" >
					<!-- 部门--> <spring:message code="public.title.deptName" />
				</th>
				<th width="100" >
					<!-- 职号--> <spring:message code="display.emp.statistics.mes209" />
				</th>
				<th width="100" >
					<!-- 姓名--> <spring:message code="public.title.name" />
				</th>
                <th width="100" >
					<!-- 户口性质 --> <spring:message
						code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
				</th>
				<th width="100" >
				       <!-- 入社日期--><spring:message code="display.emp.statistics.mes206" />
				</th>
				<th width="100" >
					<!-- 离职日期--> <spring:message code="ess.trans.title.resignDate" />
				</th>
				<th width="100" >
					<!-- 开始缴纳月 --> <spring:message
						code="is.joininstance.title.startwithmonth" />
				</th>
				<th width="100" >
					<!-- 基础参照工资--> <spring:message code="is.joininstance.title.basesalary" />
				</th>
				<th width="100" >
					<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
				</th>
				<th width="100" >
					<!-- 标记--> <spring:message code="is.joininstance.title.remarking" />
				</th>
				<th width="100" >
					<!-- 缴纳基数--> <spring:message code="is.joininstance.title.paybasenum" />
				</th>
				<th width="100" >
					<!-- 状态-->
					<spring:message code="is.joininstance.title.statement"/>
				</th>
				<th width="100" ><!-- class="asc" -->
					<!-- 提示--> <spring:message code="is.joininstance.title.message" />
				</th>
				</tr>
			<c:forEach items="${itemList}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
                        	<td class="td_center" style="white-space:nowrap">
								<input type="checkbox" name="check"  value="${show.PA_BEN_MANAGE_SEQ}" />
							</td>
							<td  style="white-space:nowrap">
								${i.index + 1}&nbsp;
							</td>
							<td style="white-space:nowrap" align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.EMPID}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.CHINESENAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.REG_TYPE_CODE_NAME}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.DATE_STARTED}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
							<td  style="white-space:nowrap">
								${show.START_DATE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.REFER_VALUE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.JOIN_VALUE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.BASELINE}&nbsp;
							</td>
							<td  style="white-space:nowrap">
								${show.ENDOWMENT_BASE}&nbsp;
							</td>
					
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_TYPE}">-</c:when>
									<c:otherwise>${show.DATE_TYPE}</c:otherwise>
								</c:choose>
							</td>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.ERROR_REMARK}">-</c:when>
									<c:otherwise>${show.ERROR_REMARK}</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
</table>
</body>
</html>