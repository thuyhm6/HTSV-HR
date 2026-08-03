<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewOrgPreExperirnceInsideList_Serch").click(function(){
		$("#viewOrgPreExperirnceInsideListForm").submit();
	});
	$("#viewOrgPreExperirnceInsideResumeNo").change(function(){
		$("#viewOrgPreExperirnceInsideListForm").submit();
	});
	$('.list tbody tr td:[sysLog="select"]').editable({type:'select'});
	
	//保存
	$("#viewOrgPreExperirnceInsideList_save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='viewOrgPreExperirnceInsideList_code']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked == true){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				jsonData += ' "SEQ": "' + $("#SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "RESUME_NO": "' + $("#viewOrgPreExperirnceInsideResumeNo").val() + '" ,';
				jsonData += ' "DEPTNAME": "' + $("#DEPTNAME_" + i,navTab.getCurrentPanel()).html() + '" ,';				
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info('<spring:message code="org.title.NOTSAVE_DATA"/>');
			return;
		}
		alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/org/orgManage/addPreExperirnceInsideInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});
</script>
<div class="pageHeader">
<form id="viewOrgPreExperirnceInsideListForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewOrgPreExperirnceInsideList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			
			<select id="viewOrgPreExperirnceInsideResumeNo" name="seach_RESUME_NO" disabled>
				<c:forEach items="${orgResumeList}" var="result">
					<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewOrgPreExperirnceInsideList_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
			<li>
				<a class="buttonActive" id="viewOrgPreExperirnceInsideList_save" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			</c:if>
			<li>
				<a class="buttonActive" href="/org/orgManage/viewResumeProcess?RESUME_NO=${RESUME_NO}" 
						target="navTab" rel="org0202" title='<spring:message code="org.title.ADAPTATION_PROCESS" />'><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>			
			</li>
	</ul>
</div>

<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${preExpInfoListSize}</div>
				<table class="list" width="100%">
					<thead>
						<tr>
							<th width="670px" colspan="8"></th>
							<th width="160px" colspan="2"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<%-- <c:if test="${LoginUser.cpnyId ne 'HTSV' and LoginUser.cpnyId ne 'HAE'}">
							<th width="160px" colspan="2"><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></th>
							</c:if> --%>
							<th width="160px" colspan="2"></th>
						</tr>
						<tr>
							<th width="30px" rowspan="2">No.</th>
							<th width="80px" rowspan="2"><input type="checkbox" class="checkboxCtrl" group="viewOrgPreExperirnceInsideList_code" /></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.ORSER_START_DATE" /><!-- 命令日期 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.TRANS_REASON_NAME" /><!-- 发令原因 --></th>
							<th width="80px"><spring:message code="org.title.before" /><!-- 以前 --></th>
							<th width="80px"><spring:message code="org.title.NOW" /><!-- 现在 --></th>
							<%-- <c:if test="${LoginUser.cpnyId ne 'HTSV' and LoginUser.cpnyId ne 'HAE'}">
							<th width="80px"><spring:message code="org.title.before" /><!-- 以前 --></th>
							<th width="80px"><spring:message code="org.title.NOW" /><!-- 现在 --></th>
							</c:if> --%>
							<th width="80px" rowspan="2"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${preExpInfoList}" var="item" varStatus="i">
							<tr>
								<td class='td_center' width="30px" >${i.count}</td>
								<td class='td_center'>
									<input type="checkbox" name="viewOrgPreExperirnceInsideList_code" value="1" />
									<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}"/>
								</td>
								<td  class='td_center'>${item.LOCAL_NAME}</td>
								<td  class='td_center'>${item.EMPID}</td>
								<td  class='td_center'>${item.POST_GRADE_NAME}</td>
								<td class='td_center'>${item.START_DATE}</td>
								<td class='td_center'>${item.TRANS_CODE_NAME}</td>
								<td class='td_center'>${item.REMARK}</td>
								<td class='td_center'>${item.DEPTNAME}</td>
								<td  class='td_center' sysLog="select" sysValue='${dept}' id="DEPTNAME_${i.index}"></td>
								<%-- <c:if test="${LoginUser.cpnyId ne 'HTSV' and LoginUser.cpnyId ne 'HAE'}">
								<td>${item.OLD_COST_CENTER_NAME}</td>
								<td sysLog="select" sysValue='${cbzx}' id="COST_CENTER_NAME_${i.index}"></td>
								</c:if> --%>
								<td>${item.UPDATED_BY} ${item.UPDATED_IP}</td>
								<td>${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
