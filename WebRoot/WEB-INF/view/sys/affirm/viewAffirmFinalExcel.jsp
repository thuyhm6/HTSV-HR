 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
   最终确认
</title>
<style type="text/css">
	td {
		text-align: center;
		font-size: 13;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=affirmFinalList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
    <tr>
				<th width="40" class="${orderDirection}"><spring:message code="sys.affirm.title.indexNum"/><!--序号--></th>
				<th width="120" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}"><spring:message code="public.title.deptName"/><!--部门-->/<spring:message code="sys.affirm.title.personName"/><!--人员名称--></th>
				<c:forEach items="${typeList}" var="type" varStatus="in">
				 <th width="120" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">${type.CONTENT}</th>
				</c:forEach>
			</tr>
			<c:forEach items="${affirmList}" var="affirm" varStatus="i">
			
				<tr height="30" >
					<td>${i.index+1}</td>
					<td>
					    <span id="${affirm.EMPID}">${affirm.LOCAL_NAME}</span>
					</td>
					<c:forEach items="${typeList}" var="type" varStatus="var">
						<td>
						<c:forEach items="${affirm.detailList}" var="detail" varStatus="d">
							<c:if test="${detail.AFFIRM_TYPE_ID eq type.CODE_NO}">
								<dt style="padding: 2px;">${detail.AFFIRM_LEVEL}.${detail.NAME}</dt>
							</c:if>
						</c:forEach>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
</table>
</body>
</html>