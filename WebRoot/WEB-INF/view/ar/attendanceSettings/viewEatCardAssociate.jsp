<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
function save_vieweatcardassociate(form, callback) {
	
	var checked=false;
	var ids= document.getElementsByName("eat_c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			if(document.getElementById("eat_card_no_"+ids[i].value).value == ''){
				//卡号不能为空!
				alertMsg.error("<spring:message code='ar.alert.message.viewCardAssociate.cardnonull'/>");
				return false;
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

	$.each($("input[name='eat_c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			
			jsonData += ' "PERSON_ID": "' + obj.value + '",';
			jsonData += ' "CARD_NO": "' + $("#form_vieweatcardassociate").find("#eat_card_no_"+obj.value).val() + '"';
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
			url: '/ar/attendanceSettings/updateEatCardAssociateInfo',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}	
	return false;
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewEatCardAssociate" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 工号/姓名--><spring:message code="public.title.empIdAndName"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
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
	<form id="form_vieweatcardassociate" name="form_vieweatcardassociate" onsubmit="return save_vieweatcardassociate(this, navTabAjaxDone);" class="pageForm required-validate" 
		action="/ar/attendanceSettings/updateEatCardAssociateInfo" method="post" rel="pagerForm">
	
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
				<th width="5" align="center" ><input type="checkbox" class="checkboxCtrl" group="eat_c1"></th>
				<th width="80"><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="80"><!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="100"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="80"><!-- 卡号 --><spring:message code="ar.viewCardAssociate.title.kahao"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${eatCardAssociateList}" var="cardlist">
			
				<tr target="personID" rel="${cardlist.PERSON_ID}">
					<td><input type="checkbox" name="eat_c1" value="${cardlist.PERSON_ID}"></td>
					<td>${cardlist.EMPID}</td>
					<td>${cardlist.LOCAL_NAME}</td>
					<td>${cardlist.DEPT_NAME}</td>
					<td><input type="text" id="eat_card_no_${cardlist.PERSON_ID}" name="eat_card_no_${cardlist.PERSON_ID}" value="${cardlist.CARD_NO}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/ar/attendanceSettings/viewEatCardAssociate" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
