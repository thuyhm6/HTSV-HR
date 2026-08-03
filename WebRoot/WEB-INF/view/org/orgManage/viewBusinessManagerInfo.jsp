<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//保存
	$("#viewBusinessManagerInfo_save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='IS_DEFAULT']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var IS_DEFAULT = 0;
			if(obj.checked){
				IS_DEFAULT = 1;
			}
			jsonData += ' "SEQ": "' + $("#viewBusinessManagerInfo_SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "CODE_NAME": "' + $("#CODE_NAME_" + i,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "IS_DEFAULT": "' + IS_DEFAULT + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ';
			jsonData += '}';
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info('<spring:message code="org.title.NOTSAVE_DATA" />');
			return;
		}
		alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/org/orgManage/saveBusinessInfo?RESUME_NO=${RESUME_NO}&DEPTNO=${DEPTNO}',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: divAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 500,
        "scrollX": true,
        "orderClasses": false
	});
	$('table tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select'});
	$("#addEmptyData",navTab.getCurrentPanel()).click(function(){
		openOnRight('/org/orgManage/viewBusinessManagerInfo?FLAG=FLAG&RESUME_NO=${RESUME_NO }&DEPTNO=${DEPTNO}','viewBusinessManagerInfo_right_unit');
	});
});
//默认的只能是唯一的
function viewBusinessManagerInfo_checked(index){
	$("input[name='IS_DEFAULT']",navTab.getCurrentPanel()).each(function(i, obj){
		if( i != index){
			obj.checked = false;
		}
	});
}
</script>
<div class="pageContent">
	<div style="float:right;">
		<a class="w_button" href="#" id="addEmptyData"><span><spring:message code="org.title.INSERT" /><!-- 添加 --></span></a>
		<a class="w_button" href="/org/orgManage/deleteBusinessInfo?SEQ={SEQ}&RESUME_NO=${RESUME_NO }&DEPTNO=${DEPTNO}"  target="ajaxTodo" callback="divAjaxDone" title='<spring:message code="button.delete.sure" />'><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
		<a class="w_button" id="viewBusinessManagerInfo_save"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a><!--
		<a class="w_button" id="viewModifyOrgInfo_modifyOrgInfo"><span>印刷</span></a>
		--><a class="w_button" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=216&RESUME_NO=${ RESUME_NO}&DEPTNO=${DEPTNO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>
	</div>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;display:bolck;">Total:${orgBusinessSize}</div>
				<table class="list" width="100%">
					<thead>
						<tr>
							<th width="5%">No.</th>
							<th width="25%"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="30%"><spring:message code="org.title.MAIN_BUSINESS" /><!-- 主要业务 --></th>
							<th width="8%"><spring:message code="org.title.IS_DEFAULT" /><!-- 默认与否 --></th>
							<th width="17%"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
							<th width="15%"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgBusinessList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}
									<input type="hidden" id="viewBusinessManagerInfo_SEQ_${i.index}" name="SEQ" value="${item.SEQ}"></td>
								<td style="text-align:left">${item.DEPT_NAME}</td>
								<td class='td_center' sysLog="select" sysValue='${CODE_NAME }' id="CODE_NAME_${i.index}">${item.CODE_NAME}</td>
								<td class='td_center'>
									<input type="checkbox" name="IS_DEFAULT" value="1" onclick="viewBusinessManagerInfo_checked(${i.index});"
										<c:if test="${item.IS_DEFAULT eq '1' }">checked</c:if>/>
								</td>
								<td class='td_center'>[${item.EMPID}]${item.LOCAL_NAME} ${item.UPDATED_IP}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
