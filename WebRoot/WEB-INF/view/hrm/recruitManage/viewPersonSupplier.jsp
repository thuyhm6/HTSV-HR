<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewPersonSupplier_Search",navTab.getCurrentPanel()).click(function(){
		$("#viewPersonSupplierForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewPersonSupplier_SEQ").change(function(){
		$("#viewPersonSupplierForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewPersonSupplier_add",navTab.getCurrentPanel()).click(function(){
		navTab.reload("/hrm/recruitManage/viewPersonSupplier?SEQ=${SEQ}&FLAG=ADD");
	});
	//保存
	$("#viewPersonSupplier_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='ACTIVITY']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			//var IS_PART_TIME = 0;
			//if(obj.checked){
			//	IS_PART_TIME = 1;
			//}
			jsonData += ' "SEQ": "' + $("#SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "COMPANY_ID": "' + $("#COMPANY_ID_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "COMPANY_NAME": "' + $("#COMPANY_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "ADDRESS": "' + $("#ADDRESS_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "PHONE": "' + $("#PHONE_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			jsonData += '}';
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='hrm.empinfo.NOTSAVE_DATA'/>");//没有需要保存的数据
			return;
		}

		alertMsg.confirm("<spring:message code='hrm.empinfo.SAVE_CONFIRM'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/hrm/recruitManage/addPersonSupplier',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: divAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	
	$('.list tbody tr td:[sysLog="text"]').editable({type:'text'});
});


</script>
<div class="pageHeader">
<form id="viewPersonSupplierForm" onsubmit="return navTabSearch(this);" action="/hrm/recruitManage/viewPersonSupplier" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="pa.wagebase.title.companyName"/><!-- 公司名称 --></td>
		<td>
			<select id="viewersonSupplier_NAME" name="seach_NAME">
				<c:forEach items="${supplierList}" var="result">
					<option value="${result.COMPANY_NAME}" selected >${result.COMPANY_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:forEach>
			</select>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewPersonSupplier_Search" href="#">
			<span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
		<li><a class="buttonActive" id="viewPersonSupplier_add" href="#">
			<span><spring:message code="button.add"/><!--添加--></span></a></li>
			<li><a class="buttonActive" id="viewPersonSupplier_Save" href="#">
			<span><spring:message code="button.sys.affirm.save"/><!--保存--></span></a></li>
		<li><a class="buttonActive" href="/hrm/recruitManage/deletePersonSupplierInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="hrm.alert.empinfo.Sure.delete"/>"><!-- 确定要删除吗? -->
			<span><spring:message code="button.delete"/><!-- 删除 --></span></a></li>
		<%-- <li><a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=39&SEQ=${ SEQ}">
			<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a></li> --%>
		
	</ul>
</div>

<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewExperienceBatchListCnt}</div>
				<table class="list" width="100%" >
					<thead>
						<tr>
							<th width="1%">No.</th>
							<th width="1%">check</th>
							<th width = "10%"><spring:message code = "org.title.COMPANYID"/><!-- 公司ID --></th>
							<th width="15%"><spring:message code="pa.wagebase.title.companyName"/><!-- 公司名称 --></th>
							<th width="15%"><spring:message code="hrm.empinfo.FAM_ADDRESS"/><!-- 地址 --></th>
							<th width="10%"><spring:message code="hr.viewRelation.title.FAM_PHONE"/><!-- 联系电话 --></th>
							<th width="20%"><spring:message code="sys.basic.title.createBy"/><!-- 创建者 --></th>
							<th width="20%"><spring:message code="inct.salesman.updateBy"/><!-- 更新人 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${supplierList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td width="1%" class='td_center'>${i.count}</td>
								<td width="1%" class='td_center'>
									<input type="checkbox" name="ACTIVITY" value="1"/>
									<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}"/>
								</td>
								<td width = "10%" sysLog="text" id="COMPANY_ID_${i.index}">${item.COMPANY_ID}</td>
								<td width="15%" sysLog="text" id="COMPANY_NAME_${i.index}">${item.COMPANY_NAME}</td>
								<td width="15%" sysLog="text" id="ADDRESS_${i.index}">${item.ADDRESS}</td>
								<td width="10%" sysLog="text" id="PHONE_${i.index}">${item.PHONE}</td>
								<td width="20%" >${item.CREATED_BY} - ${item.CREATE_DATE} - ${item.CREATED_IP }</td>
								<td width="20%" >${item.UPDATED_BY} - ${item.UPDATE_DATE} - ${item.UPDATED_IP }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
