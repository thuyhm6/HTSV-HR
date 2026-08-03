<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	//页面加载完成后，自动刷新右边
	openOnRight('/org/orgManage/viewAddOrgExperirnceInsideInfo?SEQ=${SEQ}&RESUME_NO=${RESUME_NO}','viewOrgExperirnceInsideList_unit');
	//查询按钮增加onclick事件，点击提交表单
	$("#viewOrgExperirnceInsideList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewOrgExperirnceInsideListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewOrgExperirnceInsideList_ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewOrgExperirnceInsideListForm",navTab.getCurrentPanel()).submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 250,
        "scrollX": true,
        "orderClasses": false
	});
	//删除
	$("#viewOrgExperirnceInsideList_delete",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var jsonData = '[';
		$("input[name='viewOrgExperirnceInsideList_personId']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			if(obj.checked){
				jsonData += ' "SEQ": "' + obj.value + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info('<spring:message code="org.title.SELECT_DELETEDATA" />');
			return;
		}

		alertMsg.confirm('<spring:message code="button.delete.sure" />',
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/org/orgManage/deleteOrgExperirnceInsideInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	  	
		return false;
	});
});
</script>

<div class="pageHeader">
<form id="viewOrgExperirnceInsideListForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewOrgExperirnceInsideList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			<select id="viewOrgExperirnceInsideList_ResumeNo" name="seach_RESUME_NO">
				<c:forEach items="${orgResumeList}" var="result">
					<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
				</c:forEach>
			</select>
		</td>
		<td><!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<c:if test="${RESUME_NO ne null}">
				<ait:resumeDeptList name="seach_DEPTNO" resumeNo="${RESUME_NO}" id="viewEmpInfoList_seachDept" />
				<ait:resumeDeptTreeIcon name="seach_DEPTNO" resumeNo="${RESUME_NO}" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
			</c:if>
			<c:if test="${RESUME_NO eq null}">
				<ait:deptList name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewEmpInfoList_seachDept"/>
				<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
			</c:if>
		</td>
		<td><!-- 工号/姓名： --><spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
		<td><spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 --></td>
		<td>
		 		<ait:SelectSyCodeByCpnyID id="EXPERIENCE_TYPE_NO" name="seach_EXPERIENCE_TYPE_NO" selected="${EXPERIENCE_TYPE_NO}" 
									   		include="400428,400430,400432,400431,400433" parentNo="14013956" limit="all"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewOrgExperirnceInsideList_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
			<li>
				<a class="buttonActive" id="viewOrgExperirnceInsideList_delete" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddOrgExperirnceInsideInfoCallback('viewAddOrgExperirnceInsideInfoForm',navTabAjaxDone)" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			</c:if>
			<li>
				<a class="add" onclick="print();" href="#"><span><spring:message code="hrm.approve.PRINTING" /><!-- 印刷 --></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=29&RESUME_NO=${ RESUME_NO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
			<li>
				<a class="buttonActive" href="#"  onclick="navTabNum('/org/orgManage/viewOrgPreExperirnceInsideList','RESUME_NO=${RESUME_NO}&pageNum=1&amp;menuNo=14013679&amp;navTabId=org0206','org0206','<spring:message code="org.title.PRORDERS_CHECK" />');"><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>					
			</li>
	</ul>
</div>

	<div id="viewOrgExperirnceInsideList_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px;; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgExperirnceInsideSize}</div>
		<table class="list" width="1200px;">
			<thead>
				<tr>
					<th width="25px;" class='td_center'>No.</th>
					<th width="35px" class='td_center'><input type="checkbox" class="checkboxCtrl" group="viewOrgExperirnceInsideList_personId" /></th>
					<th width="50px" class='td_center'><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.dept" /><!-- 部门 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职务 --></th>
					<!--<th width="50px" class='td_center'><spring:message code="org.title.DUTY_NO" /> 岗位 </th>-->
					<th width="50px" class='td_center'><spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
					<th width="50px" class='td_center'><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orgExperirnceInsideList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/org/orgManage/viewAddOrgExperirnceInsideInfo?SEQ=${item.SEQ}&RESUME_NO=${item.RESUME_NO}','viewOrgExperirnceInsideList_unit');">
						<td width="25px;" class='td_center'>${i.count}</td>
						<td width="35px" class='td_center'><input type="checkbox" name="viewOrgExperirnceInsideList_personId" value="${item.SEQ}"/></td>
						<td width="50px"  class='td_center'>${item.LOCAL_NAME}</td>
						<td width="50px"  class='td_center'>${item.EMPID}</td>
						<td width="50px"  class='td_center'>${item.DEPTNAME}</td>
						<td width="50px"  class='td_center'>${item.POST_GRADE_NAME}</td>
						<!--<td width="50px"  class='td_center'>${item.DUTY_NAME}</td>-->
						<td width="50px"  class='td_center'>${item.EXPERIENCE_TYPE_NAME}</td>
						<td width="50px" style="text-align:left">${item.COST_CENTER_NAME}</td>
						<td width="50px" class='td_center'>${item.UPDATED_BY}</td>
						<td width="50px" class='td_center'>${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewOrgExperirnceInsideList_unit','viewOrgExperirnceInsideList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewOrgExperirnceInsideList_unit','viewOrgExperirnceInsideList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewOrgExperirnceInsideList_left','viewOrgExperirnceInsideList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewOrgExperirnceInsideList_left')"></div>
	</div>
	<div id="viewOrgExperirnceInsideList_unit">
	</div>
</div>