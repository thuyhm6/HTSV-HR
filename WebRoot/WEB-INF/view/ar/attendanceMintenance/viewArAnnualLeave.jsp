<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<script type="text/javascript"> 
function f_delete(callback) {

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行删除操作!
		alertMsg.error("<spring:message code='ar.alert.message.viewdynamicgroup.chooseperson'/>");
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

			jsonData += ' "VACATION_NO": "' + obj.value + '"';
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
			url: '/ar/attendanceMintenance/deleteArAnnualLeave',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}

function f_init(callback) {
	
	var vac_id = $("#seach_VAC_ID").val();

	if(vac_id == ''){
		//请选择要初始化的年份
		alertMsg.error("<spring:message code='ar.alert.message.viewarannualeave.title.chooseyear'/>");
		return;
	}
	//确定要初始化该年年假数据吗？如果该年数据存在，将会删除旧数据，重新生成该年新的数据！
	if (confirm ("<spring:message code='ar.alert.message.viewarannualeave.title.init'/>"+"("+vac_id+")")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceMintenance/createArAnnualLeaveInfo',
			data: $('#id1').serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}
function pageFromSea(a){
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPTNO=$("#seach_DEPTNO",navTab.getCurrentPanel()).val()
	var seach_VAC_ID=$("#seach_VAC_ID",navTab.getCurrentPanel()).val()
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ar/attendanceMintenance/viewArAnnualLeave?seach_KEY="+seach_KEY+"&seach_DEPTNO="+seach_DEPTNO+"&seach_VAC_ID="+seach_VAC_ID);
}



</script>
<div class="pageHeader">
	<form id="id1" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArAnnualLeave" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:&nbsp;<input name="seach_KEY" type="text" id="seach_KEY" value="${KEY}"/>
					</td>
					<td>
						<!-- 部门 --><spring:message code="public.title.deptName"/>:&nbsp;<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
					</td>
					<td>
						<!-- 年假ID --><spring:message code="ar.viewarannualeave.title.annualeaveID"/>:&nbsp;<ait:date yearName="seach_VAC_ID" yearSelected="${VAC_ID}" yearMinus="5" yearPlus="5"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">

	<c:set value="0" var="init_target_exit"/>
	<c:set value="javascript:f_init(navTabAjaxDone);" var="init_Url"/>
	<c:set value="navTab" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceMintenance/addArAnnualLeaveView" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:f_delete(navTabAjaxDone);" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceMintenance/updateArAnnualLeaveView?VACATION_NO={statno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th ><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th ><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th ><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th ><!-- 年假ID --><spring:message code="ar.viewarannualeave.title.annualeaveID"/></th>
				<th ><!-- 开始日期 --><spring:message code="public.title.startDate"/></th>
				<th ><!-- 结束日期 --><spring:message code="public.title.endDate"/></th>
				<th ><!-- 年假天数 --><spring:message code="ar.viewarannualeave.title.annualeavetime1"/></th>
				<th ><!-- 年假时数 --><spring:message code="ar.viewarannualeave.title.annualeavetime"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arAnnualLeaveList}" var="list" varStatus="i">
			
				<tr target="statno" rel="${list.VACATION_NO}">
					<td><input type="checkbox" name="c1" value="${list.VACATION_NO }"></td>
					<td style="text-align:center" >${list.EMPID}</td>
					<td style="text-align:center" >${list.LOCAL_NAME}</td>
					<td style="text-align:center" >${list.DEPTNAME}</td>
					<td style="text-align:center" >${list.VAC_ID}</td>
					<td style="text-align:center" >${list.STRT_DATE}</td>
					<td style="text-align:center" >${list.END_DATE}</td>
					<td style="text-align:center" >${list.TOT_VAC_CNT/8}</td>
					<td style="text-align:center" >${list.TOT_VAC_CNT}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArAnnualLeave" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
