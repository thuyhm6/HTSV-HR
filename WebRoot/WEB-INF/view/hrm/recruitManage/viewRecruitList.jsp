<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	if($("#viewRecruitList_left table tr:eq(1)").html() == null){
		openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=8888&currentIndex=0','viewRecruitList_unit');
	}else{
		$("#viewRecruitList_left table tr:eq(1)").click();
	}
	$("#viewRecruitList_srarch",navTab.getCurrentPanel()).click(function(){
		$("#viewRecruitList_Form",navTab.getCurrentPanel()).submit();
	});
});

function viewRecruitListTrClick(personId){
	openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=' + personId +
			'&currentIndex=' + $("#viewAddRecruitInfoPanel_currentIndex",navTab.getCurrentPanel()).val(),'viewRecruitList_unit');
}


function executeRecruit(typeStr) {
	var empIdsStr="";
	var flag=false;
	$("input[name='viewRecruitList_PERSON_ID']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empIdsStr = empIdsStr + "'empty'";
	if(flag == false){
		alertMsg.error("<spring:message code="hrm.alert.empinfo.Choice_Perform_operation" />");//请先选择要执行此操作
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Perform_operation" />",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/recruitManage/executeRecruit',
  				data:{empIds:empIdsStr,type:typeStr},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>

<div class="pageHeader">
<form id="viewRecruitList_Form" onsubmit="return navTabSearch(this);" action="/hrm/recruitManage/viewRecruitList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><input type="radio" name="seach_OBJECT" value="0" <c:if test="${OBJECT eq '0' }">checked</c:if>/>
			<spring:message code="hrm.recruitManage.The_entry" /><!-- 入职发令 --></td>
		<td><input type="radio" name="seach_OBJECT" value="1" <c:if test="${OBJECT eq '1' }">checked</c:if>/>
			<spring:message code="hrm.recruitManage.The_entry_end" /><!-- 入职发令结束者 --></td>
		<td width="300px;">&nbsp;</td>
		<td><spring:message code="hrm.recruitManage.RUZHI_FALING_DATE.Z" /><!-- 入职发令日期 --></td>
		<td>
			<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE }"/>~
			<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE }"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ar.viewarcardrecord.title.shujulaiyuan" /><!-- 数据来源 -->：</td>
		<td><input type="radio" name="seach_DATA_SOURCES" value="1" <c:if test="${DATA_SOURCES eq '1' }">checked</c:if>/>
			<spring:message code="hrm.recruitManage.SHOUDONG_SHURU.Z" /><!-- 手动输入 --></td>
		<td><input type="radio" name="seach_DATA_SOURCES" value="0" <c:if test="${DATA_SOURCES eq '0' }">checked</c:if>/>
			<spring:message code="hrm.recruitManage.ZHAOPIN_WORK.Z" /><!-- 招聘工作 --></td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
				<c:if test="${OBJECT eq '1' }">
						<li>
							<a class="buttonActive" href="#" onclick="executeRecruit('CANCEL')">
								<span><spring:message code="hrm.recruitManage.Back_by_qualified" /><!-- 拉回 采用合格者 --></span>
							</a>
						</li>
				</c:if>
				<c:if test="${OBJECT eq '0' }">
						<li>
							<a class="buttonActive" href="#" onclick="executeRecruit('CONFIRM')">
								<span><spring:message code="hrm.recruitManage.Confirm" /><!-- 发令确认 --></span>
							</a>
						</li>
				</c:if>
				<li>
					<a class="buttonActive" id="viewRecruitList_srarch" href="#">
						<span><spring:message code="button.search" /><!-- 查询 --></span>
					</a>
				</li>
				<li>
					<a class="delete" onclick="downloadExcel('viewRecruitList_Form','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=37','/hrm/recruitManage/viewRecruitList')" href="#"><span><spring:message code="hrm.empinfo.EXPORT" /><!-- 导出到EXECL --></span></a>					
				</li>
		</ul>
	</div>
	<div id="viewRecruitList_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:520px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewRecruitSize}</div>
		<table class="list" width="2000px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="15px"><input type="checkbox" class="checkboxCtrl" group="viewRecruitList_PERSON_ID"/></th>
					<th width="35px"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></th>
					<th width="35px"><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></th>
					<th width="35px"><spring:message code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></th>
					<th width="35px"><spring:message code="hrm.empinfo.END_PROBATION_DATE" /><!-- 试用结束日期 --></th>
					<th width="35px"><!-- 入社 --><spring:message code="hrm.recruitManage.JOIN_TYPE.Z" /></th>
					<th width="35px"><!-- 入社细节区分 --> <spring:message code="hrm.recruitManage.JOIN_DETAIL_TYPE.Z" /></th>
					<th width="35px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></th>
					<th width="35px"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 --></th>
					<th width="35px"><spring:message code="ess.personalinfo.title.IDCardNo" /><!-- 身份证号 --></th>
					<th width="35px"><spring:message code="hrm.empinfo.SEXCODE" /><!-- 性别 --></th>
					<th width="35px"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!-- 国籍 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewRecruitList}" var="item" varStatus="i">
					<tr onclick="viewRecruitListTrClick('${item.PERSON_ID}')">
					
						<td class='td_center'>${i.count}</td>
						<td class='td_center'><input type="checkbox" name="viewRecruitList_PERSON_ID" value="${item.PERSON_ID}"/></td>
						<td>${item.LOCAL_NAME}</td>
						<td>${item.EMPID}</td>
						<td>${item.DATE_STARTED}</td>
						<td>${item.END_PROBATION_DATE}</td>
						<td>${item.JOIN_TYPE_NAME}</td>
						<td>${item.JOIN_DETAIL_NAME}</td>
						<td>${item.DEPTNAME}</td>
						<td>${item.EMP_TYPE_CODE_NAME}</td>
						<td>${item.IDCARD_NO}</td>
						<td>${item.SEXCODE_NAME}</td>
						<td>${item.NATIONALITY_CODE_NAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewRecruitList_unit','viewRecruitList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewRecruitList_unit','viewRecruitList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewRecruitList_left','viewRecruitList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewRecruitList_left')"></div>
	</div>
	<div id="viewRecruitList_unit"  style="display:block;">
	</div>
</div>