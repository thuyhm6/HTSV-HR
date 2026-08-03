<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
function save_viewcardassociate(form, callback) {

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			if(document.getElementById("card_no_"+ids[i].value).value == ''){
				//卡号不能为空!
				alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.cardnonull'/>");
				return false;
			}
			//开始时间不能为空！
			if($("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_START_"+ids[i].value+"']").val() == ''){
				//开始时间不能为空！
				alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.startdateisnotnull'/>");
				return false;
			}
			//开始时间和结束时间不能同时为空！
			if($("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_START_"+ids[i].value+"']").val() == ''
					&& $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_END_"+ids[i].value+"']").val() == ''){
				//开始时间和结束时间不能同时为空！
				alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.timebothnull'/>");
				return false;
			}
			//结束时间必须大于开始时间！
			var start_date = $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_START_"+ids[i].value+"']").val();
			var end_date = $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_END_"+ids[i].value+"']").val();
			
			if(start_date != '' && end_date != ''){
				//结束时间必须大于开始时间！
				if(comptime(start_date,end_date)!=1){
					alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.enddatemustbigger'/>");
					return false;
				}
			}
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行操作!
		alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.chooseinfo'/>");
		return false;
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
			
			jsonData += ' "PERSON_ID": "' + obj.value + '",';
			jsonData += ' "DATE_START": "' + $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_START_"+ids[i].value+"']").val() + '",';
			jsonData += ' "DATE_END": "' + $("#form_viewcardassociate", navTab.getCurrentPanel()).find("input[name='DATE_END_"+ids[i].value+"']").val() + '",';
			jsonData += ' "CARD_NO": "' + $("#form_viewcardassociate").find("#card_no_"+obj.value).val() + '"';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要更新的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.chooseinfo'/>");
		return false;
	}
	
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceSettings/updateCardAssociateInfo',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	
	return false;
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0];
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0];

	// alert(beginTime+endTime+beginTime);
	
	//alert(Date.parse(endTime)+" "+Date.parse(beginTime));

	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;

	if(a<0){
		return -1;
	}else if (a>0){
		return 1;
	}else if (a==0){
		return 1;
	}else{
		return 'exception'
	}
}
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewCardAssociate" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 工号/姓名--><spring:message code="public.title.empIdAndName"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 卡号 --><spring:message code="ar.viewCardAssociate.title.kahao"/></td>
				<td><input type="text" name="seach_CARD_NO" value="${CARD_NO}"/></td>
				<td><!-- 部门 --><spring:message code="public.title.deptName"/>:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<form id="form_viewcardassociate" name="form_viewcardassociate" onsubmit="return save_viewcardassociate(this, navTabAjaxDone);" class="pageForm required-validate" 
		action="/ar/attendanceSettings/updateCardAssociateInfo" method="post" rel="pagerForm">
	
	<div class="formBar">
		<ul>
			<li>
				<c:if test="${toolbarInfo.INSERTR == '1'}">
					<div class="buttonActive"><div class="buttonContent"><button type="submit" ><!-- 保存 --><spring:message code='ar.viewempcalender.title.save'/></button></div></div>
				</c:if>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="125">
		<thead>
			<tr>
				<th width="5" align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th width="70"><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="70"><!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="70"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="70"><!-- 卡号 --><spring:message code="ar.viewCardAssociate.title.kahao"/></th>
				<th width="70"><!-- 开始日期 --><spring:message code="public.title.startDate"/></th>
				<th width="70"><!-- 结束日期 --><spring:message code="public.title.endDate"/></th>
				<th width="70"><!-- 入职日期 --><spring:message code="ess.trans.title.entryJobDate"/></th>
				<th width="70"><spring:message code="hr.viewPromote.title.RESIGN_DATE"/><!--离职日期--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cardAssociateList}" var="cardlist">
			
				<tr target="personID" rel="${cardlist.PERSON_ID}">
					<td><input type="checkbox" name="c1" value="${cardlist.PERSON_ID}"></td>
					<td>${cardlist.EMPID}</td>
					<td>${cardlist.LOCAL_NAME}</td>
					<td>${cardlist.DEPT_NAME}</td>
					<td><input type="text" id="card_no_${cardlist.PERSON_ID}" name="card_no_${cardlist.PERSON_ID}" value="${cardlist.CARD_NO}"/></td>
					<td><input type="text" class="date" readonly id="DATE_START_${cardlist.PERSON_ID}" name="DATE_START_${cardlist.PERSON_ID}" value="${cardlist.DATE_START}"/></td>
					<td><input type="text" class="date" readonly id="DATE_END_${cardlist.PERSON_ID}" name="DATE_END_${cardlist.PERSON_ID}" value="${cardlist.DATE_END}"/></td>
					<td>${cardlist.DATE_STARTED}</td>
					<td>${cardlist.DATE_LEFT}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/ar/attendanceSettings/viewCardAssociate" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
