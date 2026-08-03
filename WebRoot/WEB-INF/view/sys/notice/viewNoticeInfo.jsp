<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
.formBar .toolBar li a span,.formBar .toolBar li.hover a span {
	background-position: 100% -50px;
	padding-right: 5px;
}
</style>
<script type="text/javascript">
function delete_NoticeInfo(callback){
	var checked=false;
	var ids= document.getElementsByName("c");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewArAnnualStandard.choosedelete'/>");
		return ;
	}

	//var defaultCpny = $("#defaultCpny").val();
	//json传值
	var jsonData = '[';

	$.each($("input[name='c']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			jsonData += ' "ID": "' + obj.value + '"';
			//jsonData += ' "CPNY_ID": "' + defaultCpny + '"';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要删除的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/> ");
		return;
	}
	
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url: '/sys/notice/delNoticeInfo',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});	
	}
}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/sys/notice/viewNoticeInfo" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="ess.title.Title"/><!-- 标题 --></td>
		<td><input type="text" id="seach_TITLE" name="seach_TITLE"
			value="${TITLE}" /></td>
		<td><spring:message code="ess.title.notecontent"/><!-- 公告内容 --></td>
		<td><input type="text" name="seach_CONTENT" value="${CONTENT}" />
		<td><spring:message code="ess.title.faburiqi"/><!-- 发布日期 -->
		</td>
		<td>
		    <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE" value="${FROM_DATE }" class="Wdate" readonly="true" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
		<!--<input id="seach_FROM_DATE" type="text"
			name="seach_FROM_DATE" class="date required" readonly="true"
			value="${FROM_DATE}" /> -->
			<!--<a class="inputDateButton"><spring:message code="public.title.choose" /> 选择 </a>-->
			</td>
		<td><spring:message code="public.title.endDate" /><!-- 结束日期 -->
		</td>
		<td>
		<input type="text" name="seach_TO_DATE" id="seach_TO_DATE" value="${TO_DATE }" class="Wdate" readonly="true" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
		<!--<input id="seach_TO_DATE" type="text" name="seach_TO_DATE"
			class="date required" readonly="true" value="${TO_DATE}" /> <a
			class="inputDateButton"><spring:message
			code="public.title.choose" /> 选择 </a>
	    -->
	    </td>
	</tr>
</table>
<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
</div>
</form>
</div>
<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="540" var="add_height"/>
	<c:set value="/sys/notice/addNoticeInfoView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/notice/delNoticeInfo?ID={ID}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="540" var="edit_height"/>
	<c:set value="/sys/notice/updateNoticeInfoView?ID={ID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>	
  <table class="table" width="100%" layoutH="206"><!-- nowrapTD="false"-->
	<thead>
	  <tr>
		<th><spring:message code="ess.title.Title"/><!-- 标题 --></th>
		<th><spring:message code="ess.title.notecontent"/><!-- 公告内容 --></th>
		<th style="display:none;"><spring:message code="ess.title.fujianchakan"/><!--附件查看 --></th>
		<th><spring:message code="ess.title.faburiqi"/><!-- 发布日期 --></th>
		<th><spring:message code="public.title.endDate"/></th>
		<th><spring:message code="sys.essParam.title.legalPerson"/><!-- 法人 --></th>
	  </tr>
	</thead>
	<tbody>
	  <c:forEach items="${nList }" var="item">
		<tr target="ID" rel="${item.ID}">
			<td style="text-align:left;width:25%;">
			<c:if test="${item.COLOR_FLAG eq '1'}">
			<a href="/sys/notice/viewNotice?ID=${item.ID}" target="dialog" mask="true" width="600" height="300" title="${item.TITLE }"><font color="red">${item.TITLE }</font></a>
			</c:if>
			<c:if test="${item.COLOR_FLAG ne '1'}">
			<a href="/sys/notice/viewNotice?ID=${item.ID}" target="dialog" mask="true" width="600" height="300" title="${item.TITLE }">${item.TITLE }</a>
			</c:if>
			</td>
			<td style="text-align:left;width:40%;">
			<a href="/sys/notice/viewNotice?ID=${item.ID}" target="dialog" mask="true" width="600" height="300" title="${item.TITLE }">${item.CONTENT}</a></td>
			<td style="text-align: center;display:none;">
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME }</a></div>
							</c:forEach>
			</td>
			<td style="text-align:center;width:15%;">${item.FROM_DATE}</td>
			<td style="text-align:center;width:15%;">${item.TO_DATE}</td>
			<td style="text-align:center;width:15%;">
						<c:if test="${authority eq '1'}">
							${item.CPNY_ID}
						</c:if>
						<c:if test="${authority ne '1'}">
							${defaultCpnyId }
						</c:if>
			</td>
		</tr>
	  </c:forEach>
	</tbody>
</table>
<c:set value="/sys/notice/viewNoticeInfo" var="pageUrl"/>
<%@include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>