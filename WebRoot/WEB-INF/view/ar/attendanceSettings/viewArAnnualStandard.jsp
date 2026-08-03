<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<script type="text/javascript"> 
function f_delete_annual_standard(callback) {

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewArAnnualStandard.choosedelete'/>");
		return;
	}

	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			jsonData += ' "VAC_NO": "' + obj.value + '"';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要删除的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/>");
		return;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceSettings/deleteArAnnualStandard',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}
</script>
<%--
<div class="pageHeader">
	<form id="id1" onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewArAnnualStandard" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>公司:</td>
					<td>
						<select name="seach_CPNY_ID" class="combox">
							<option value="">全部</option>
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div> --%>
<div class="pageContent">

	<c:set value="dialog" var="add_tab"/>
	<c:set value="750" var="add_width"/>
	<c:set value="300" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addArAnnualStandardView" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:f_delete_annual_standard(navTabAjaxDone);" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="750" var="edit_width"/>
	<c:set value="300" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateArAnnualStandardView?VAC_NO={statno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="50%" layoutH="88">
		<thead>
			<tr>
				<th width="5" align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th width="20"><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/></th>
				<th width="20"><!-- 开始月 --><spring:message code="ar.viewArAnnualStandard.title.startmonth"/></th>
				<th width="20"><!-- 结束月 --><spring:message code="ar.viewArAnnualStandard.title.endmonth"/></th>
				<th width="20"><!-- 休假时间 --><spring:message code="ar.viewArAnnualStandard.title.vacmonth"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arAnnualStandardList}" var="list" varStatus="i">
			
				<tr target="statno" rel="${list.VAC_NO}">
					<td><input type="checkbox" name="c1" value="${list.VAC_NO }"></td>
					<td>${list.CONTENT}</td>
					<td>${list.STRT_MONTH}</td>
					<td>${list.END_MONTH}</td>
					<td>${list.VAC_DAY_CNT_DAY}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewArAnnualStandard" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
