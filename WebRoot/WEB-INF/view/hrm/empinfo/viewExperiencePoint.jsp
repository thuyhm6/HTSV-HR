<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function hiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#ExperiencePointlayout3").show();
	$("#ExperiencePointlayout5").hide();
	$("#ExperiencePointlayout2").hide();
	$("#ExperiencePointlayout4").hide();
}
function ExperiencePointshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewExperiencePointleft").css("width","430px");
	$("#ExperiencePointlayout5").show();
	$("#ExperiencePointlayout2").show();
	$("#ExperiencePointlayout3").hide();
	$("#ExperiencePointlayout4").hide();
}
function ExperiencePointExperiencePointhiddenleft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewExperiencePointleft").css("width","430px");
	$("#ExperiencePointlayout5").show();
	$("#ExperiencePointlayout2").show();
	$("#ExperiencePointlayout3").hide();
	$("#ExperiencePointlayout4").hide();
}
function ExperiencePointhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#ExperiencePointlayout5").hide();
	$("#ExperiencePointlayout2").show();
	$("#ExperiencePointlayout3").hide();
	$("#ExperiencePointlayout4").show();
}
$(function(){
	var aa=$("#Explocalname").val();
	var bb=$("#Explocalempid").val();
	var cc=$("#Explocalpostgradenoname").val();
	var dd=$("#Explocalpostgradeotherinf").val();
	var ff=$("#Explocalcenter").val();
	var gg=$("#Explocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename7').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleExperiencePoint?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&WORK_EXPER_NO=${workExperNo }','viewResumeList_viewExperiencePointunit');
});

function fangdajing7(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY7').val()));
	$('#fangda7').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewExperiencePoint&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda7').click();
}
$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,//关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": 370,
    "scrollX": true,
    "orderClasses": false
});
</script>

<div class="pageContent">

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="user_table">
	<tr>
		<td class="td_title" style="width: 10%"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
		<td class="td_type" style="width: 10%"><input type="text"
			name="seach_KEY7" id="seach_KEY7" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing7('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda7"
			onclick="fangdajing7()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewExperiencePoint"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename7">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleExperiencePoint?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&WORK_EXPER_NO=0','viewResumeList_viewExperiencePointunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editExperiencePoint',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editExperiencePoint',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=14&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewExperiencePointleft"
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="1000px;">
	<thead>
		<tr>
			<th width="1%">No.</th>
			<th width="3%"><spring:message code="hrm.empinfo.COMPANY_NAME" /><!--公司名称--></th>
			<th width="3%"><spring:message code="ess.infoApply.DEPT" /><!--部门--></th>
			<th width="3%"><spring:message code="hrm.recruitManage.DATE_STARTED" /><!--入职日期--></th>
			<th width="3%"><spring:message code="hrm.recruitManage.LEAVE_DATE" /><!--离职日期--></th>
			<th width="3%"><spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!--月薪--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewExperiencePointList}" var="item" varStatus="i">
			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleExperiencePoint?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&WORK_EXPER_NO=${item.WORK_EXPER_NO }','viewResumeList_viewExperiencePointunit');">
				<td style="text-align: left">${i.count}</td>
				<td style="text-align: left">${item.CPNY_NAME }</td>
				<td style="text-align: left">${item.DEPT_NAME }</td>
				<td style="text-align: left">${item.START_DATE }</td>
				<td style="text-align: left">${item.END_DATE }</td>
				<td style="text-align: left">${item.PAY_YEAR }</td>
			</tr>

		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Explocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Explocalempid" value="${title.EMPID }">
<input type="hidden" id="Explocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Explocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Explocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Explocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="ExperiencePointlayout5" class="w-layout-collapse-left"
	onclick="ExperiencePointhiddenRight('viewResumeList_viewExperiencePointunit','viewResumeList_viewExperiencePointleft')"></div>
<div id="ExperiencePointlayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="ExperiencePointExperiencePointhiddenleft('viewResumeList_viewExperiencePointunit')"></div>
<div id="ExperiencePointlayout2" class="w-layout-collapse-right"
	onclick="hiddenleft('viewResumeList_viewExperiencePointleft','viewResumeList_viewExperiencePointunit')"></div>
<div id="ExperiencePointlayout3" class="w-layout-collapse-left"
	style="display: none;" onclick=ExperiencePointshowId('viewResumeList_viewExperiencePointleft');
></div>
</div>
<div id="viewResumeList_viewExperiencePointunit" style="display: block;">
</div>
</div>