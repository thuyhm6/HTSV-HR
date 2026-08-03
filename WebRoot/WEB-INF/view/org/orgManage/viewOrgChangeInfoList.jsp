<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewOrgChangeInfoList_Serch").click(function(){
		$("#viewOrgChangeInfoListForm").submit();
	});
	$("#viewOrgChangeInfoList_RESUME_NO",navTab.getCurrentPanel()).change(function(){
		$("#viewOrgChangeInfoListForm").submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 270,
	    "scrollX": true,
	    "orderClasses": false
	});
	//保存
	$("#viewOrgChangeInfoList_save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='viewOrgChangeInfoList_SEQ']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "SEQ": "' + $("#viewOrgChangeInfoList_SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
			jsonData += ' "RESUME_NO": "' + '${RESUME_NO}' + '" ,';
			jsonData += ' "REMARK": "' + $("#viewOrgChangeInfoList_REMARK_" + i,navTab.getCurrentPanel()).html() + '" ';
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
					url: '/org/orgManage/saveOrgChangeInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	$('.list tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			this.editing = false;
		}
	});
});


//显示下拉框
function viewOrgChangeInfoList_display_no(index){
	$("#viewOrgChangeInfoList_ADD_DIV_" + index,navTab.getCurrentPanel()).show();
	$("#viewOrgChangeInfoList_DISPLAY_DIV_" + index,navTab.getCurrentPanel()).hide();
	$("#viewOrgChangeInfoList_REMARK_" + index,navTab.getCurrentPanel()).focus();
}
//隐藏下拉框
function viewOrgChangeInfoList_display_name(index){
	$("#viewOrgChangeInfoList_DISPLAY_DIV_" + index,navTab.getCurrentPanel()).html($("#viewOrgChangeInfoList_REMARK_" + index,navTab.getCurrentPanel()).val());
	$("#viewOrgChangeInfoList_ADD_DIV_" + index,navTab.getCurrentPanel()).hide();
	$("#viewOrgChangeInfoList_DISPLAY_DIV_" + index,navTab.getCurrentPanel()).show();
}
</script>
<div class="pageHeader">
<form id="viewOrgChangeInfoListForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewOrgChangeInfoList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			<select id="viewOrgChangeInfoList_RESUME_NO" name="seach_RESUME_NO">
				<c:forEach items="${orgResumeList}" var="result">
					<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
				</c:forEach>
			</select>
		</td>
		<td><!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<select name="seach_FLAG">
				<option value="1" <c:if test="${FLAG eq '1'}">selected</c:if>><spring:message code="org.title.before" /><!-- 以前 --></option>
				<option value="0" <c:if test="${FLAG eq '0'}">selected</c:if>><spring:message code="org.title.NOW" /><!-- 现在 --></option>
			</select>
			<ait:resumeDeptList name="seach_DEPTNO" resumeNo="${RESUME_NO}" id="viewOrgChangeInfoList_deptList"/>
			<ait:resumeDeptTreeIcon name="seach_DEPTNO" resumeNo="${RESUME_NO}"  id="viewOrgChangeInfoList_deptList" selected="${DEPTNO}"/>
		
			<!--<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="viewOrgChangeInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="viewOrgChangeInfoList_seachDept" selected="${DEPTNO}"/>
		--></td>
		<td><spring:message code="org.title.EXPERIENCE_TYPE" /><!-- 变更类型 --></td>
		<td>
		 	<ait:SelectSyCodeByCpnyID id="seach_EXPERIENCE_TYPE" name="seach_EXPERIENCE_TYPE" parentNo="14013935" selected="${EXPERIENCE_TYPE}" limit="all"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>


<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewOrgChangeInfoList_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
			<li>
				<a class="buttonActive" id="viewOrgChangeInfoList_save" href="#">
					<span><spring:message code="org.title.SAVE" /><!-- 保存 --></span>
				</a>
			</li>
			</c:if>
			<!--<li>
				<a class="add" onclick="print();" href="#"><span>印刷</span></a>
			</li>
			--><li>
				<a class="delete" onclick="downloadExcel('viewOrgChangeInfoListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=32&RESUME_NO=${ RESUME_NO}','/org/orgManage/viewOrgChangeInfoList')" href="#"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
			<li>
				<a class="buttonActive" href="/org/orgManage/viewResumeProcess?RESUME_NO=${RESUME_NO}" 
						target="navTab" rel="org0202" title='<spring:message code="org.title.ADAPTATION_PROCESS" />'><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>			
			</li>
	</ul>
</div>

<div class="pageContent" sysLong="printDiv">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgChangeInfoSize}</div>
				<table class="list" width="1100px;">
					<thead>
						<tr>
							<th width="40px" rowspan="2">No.</th>
							<th width="310px" colspan="2"><spring:message code="org.title.before" /><!-- 以前 --></th>
							<th width="310px" colspan="2"><spring:message code="org.title.NOW" /><!-- 现在 --></th>
							<th width="130px" rowspan="2"><spring:message code="org.title.EXPERIENCE_TYPE" /><!-- 变更类型 --></th>
							<th width="200px" rowspan="2"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
							<th class="titleColor" width="250px" rowspan="2"><spring:message code="org.title.REMARK" /><!-- 备注 --></th>
						</tr>
						<tr>
							<th width="130px"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></th>
							<th width="180px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="130px"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></th>
							<th width="180px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgChangeInfoList}" var="item" varStatus="i">
							<tr>
								<td width="30px" >${i.count}</td>
								<td width="80px">${item.DEPTNO}</td>
								<td width="80px">${item.DEPT_NAME}</td>
								<td width="80px">${item.CURRENT_DEPTNO}</td>
								<td width="80px">${item.CURRENTDEPTNAME}</td>
								<td width="80px">${item.EXPERIENCENAME}</td>
								<td width="80px">${item.UPDATE_DATE }</td>
								<input type="hidden" id="viewOrgChangeInfoList_SEQ_${i.index}" name="viewOrgChangeInfoList_SEQ" value="${item.SEQ}"/>
								<td width="80px" sysLog="text" sysIndex="${i.index}" id="viewOrgChangeInfoList_REMARK_${i.index}">${item.REMARK}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
