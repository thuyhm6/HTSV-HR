<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewSupervisorInfoList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewSupervisorInfoListForm",navTab.getCurrentPanel()).submit();
	});
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSupervisorInfoList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSupervisorInfoList&seach_KEY='+name);
    });
	$("#cleanPersonInfo",navTab.getCurrentPanel()).click(function(){
		$("#clean_PERSON_ID",navTab.getCurrentPanel()).val("");
		$("#personInfo",navTab.getCurrentPanel()).html("");
	});
	
});

</script>


<div class="pageHeader">
<form id="viewSupervisorInfoListForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewSupervisorInfoList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td width="7%"><!--社号/姓名--><spring:message code="public.title.empIdAndName" /></td>
		<td width="23%">
			<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
			<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
			<input type="hidden" name="PERSON_ID" id="clean_PERSON_ID" value="${PERSON_ID}"/>
			<a class="w_button" id="cleanPersonInfo"><span><!--删除--><spring:message code="public.title.delete" /></span></a>
		</td>
		<td width="50%" colspan="3">
			<span style="margin-left: 50px;" id="personInfo">${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
		</td>
		<td width="20%"></td>
	</tr>
	<tr>
		<td><!--部门--><spring:message code="public.title.deptName" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="hr" id="viewSupervisorInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewSupervisorInfoList_seachDept" selected="${DEPTNO}"/>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>><!--下位部门包括--><spring:message code="hrm.empinfo.Department_include" />
		</td>
		<td><!--等级--><spring:message code="sys.affirm.title.affirmLevel" /></td>
		<td><ait:selectCodeMulti id="seach_POST_GRADE_NO" name="seach_POST_GRADE_NO_NAME" parentNo="14014289" selected="${POST_GRADE_NO}" selectedNm="${POST_GRADE_NO_NAME}"/></td>
		<td><!--职责名--><spring:message code="sys.postManage.title.dutyName" /></td>
		<td><ait:selectCodeMulti id="seach_POSITION_NO" name="seach_POSITION_NO_NAME" parentNo="13813" selected="${POSITION_NO}" selectedNm="${POSITION_NO_NAME}"/></td>
		
	</tr>
	<tr>
	    <td><!--主要业务--><spring:message code="org.title.MAIN_BUSINESS" /></td>
		<td><ait:selectCodeMulti id="seach_MAIN_BUSINESS" name="seach_MAIN_BUSINESS_NAME" parentNo="400098" selected="${MAIN_BUSINESS}" selectedNm="${MAIN_BUSINESS_NAME}"/></td>
		<td width="5%" class="td_type" >
			<input type="radio" name="seach_eme" id="EMP_DEFINED" value="manage" <c:if test="${eme eq 'manage' }">checked="checked"</c:if>>部门长
			<input type="radio" name="seach_eme" id="EMP_DEFINED" value="emp" <c:if test="${eme eq 'emp' }">checked="checked"</c:if>>员工
        </td>
      </tr>
      <tr>
		<td><!--管理者任职状态--><spring:message code="hrm.viewSupervisorInfoList.GUANLIZHERENZHIZHUANGTAI.b" /></td>
		<td><ait:selectCodeMulti id="seach_MANAGE_OFFICE" name="seach_MANAGE_OFFICE_NAME" parentNo="15118" selected="${MANAGE_OFFICE}" selectedNm="${MANAGE_OFFICE_NAME}"/></td>
		<td><!--员工任职状态--><spring:message code="hrm.viewSupervisorInfoList.YUANGONGRENZHIZHUANGTAI.b" /></td>
		<td><ait:selectCodeMulti id="seach_EMP_OFFICE" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE}" selectedNm="${EMP_OFFICE_NAME}"/></td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewSupervisorInfoList_Serch" href="#"><span><!--查询--><spring:message code="ess.infoApply.SELECT" /></span></a></li>
		<li><a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=44"><span><!--导出到EXECL--><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewSupervisorInfoListCnt}</div>
				<table class="list" width="100%" layoutH="220">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="250px" colspan="5"><!--部门长--><spring:message code="org.title.MINISTER" /></th>
							<th width="250px" colspan="5"><!--员工--><spring:message code="sys.rights.title.employee" /></th>
						</tr>
						<tr>
							<th width="30px">No.</th>
							<th width="50px"><!--姓名--><spring:message code="public.title.name" /></th>
							<th width="50px"><spring:message code="hrm.empinfo.empid" /></th>
							<th width="50px"><!--部门--><spring:message code="public.title.deptName" /></th>
							<th width="50px"><!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" /></th>
							<th width="50px"><!--等级--><spring:message code="sys.affirm.title.affirmLevel" /></th>
							
							<th width="50px"><!--姓名--><spring:message code="public.title.name" /></th>
							<th width="50px"><spring:message code="hrm.empinfo.empid" /></th>
							<th width="50px"><!--部门--><spring:message code="public.title.deptName" /></th>
							<th width="50px"><!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" /></th>
							<th width="50px"><!--等级--><spring:message code="sys.affirm.title.affirmLevel" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewSupervisorInfoList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td>${item.MANAGE_NAME}</td>
								<td>${item.MANAGE_ID}</td>
								<td>${item.MANAGE_NO}</td>
								<td>${item.MANAGE_DEPT_NAME}</td>
								<td>${item.MANAGE_POST_GRADE_NAME}</td>
								<td>${item.EMP_NAME}</td>
								<td>${item.EMP_ID}</td>
								<td>${item.EMP_NO}</td>
								<td>${item.EMP_DEPT_NAME}</td>
								<td>${item.EMP_POST_GRADE_NAME}</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>