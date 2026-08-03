<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function downFiles(P,F){
	$("#PERSON_ID").attr("value",P);
	$("#NEWNAME").attr("value",F);
	if(P!=null && F!=null){
		$("#downLoadFile").submit();
	}
}
</script>
<div class="pageContent">
	<div>
	<form action="/ess/wageApplication/downLoadFile" method="post" id="downLoadFile">
		<table width="100%" class="user_table margin_b">
			<tr>
				<td class="td_title" style="text-align: center" width="80%">附件名</td>
				<td class="td_title" style="text-align: center" width="20%">下载</td>
			</tr>
			<c:forEach items="${fileList}" var="file" varStatus="j">
				<tr>
					<td class="td_type" style="text-align: center" width="80%">${file.FILENAME}</td>
					<td class="td_type" style="text-align: center" width="20%">
						<a href="#" onclick="downFiles('${file.CREATED_BY}','${file.NEWNAME}')">下载</a>
					</td>
				</tr>
			<c:if test="${fn:length(fileList)==j.count}">
				<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${file.WAGENO}"/>
			</c:if>
			</c:forEach>
			<c:if test="${fn:length(fileList)==0}">
				<tr>
					<td class="td_type" style="text-align: center" colspan="2">没有附件</td>
				</tr>
			</c:if>
			<input type="hidden" name="PERSON_ID" id="PERSON_ID" value=""/>
			<input type="hidden" name="NEWNAME" id="NEWNAME" value=""/>
		</table>
	</form>
	</div>
</div>
