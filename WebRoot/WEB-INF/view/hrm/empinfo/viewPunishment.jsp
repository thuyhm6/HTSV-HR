<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function Punishmenthiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewPunishmentlayout3").show();
	$("#viewPunishmentlayout5").hide();
	$("#viewPunishmentlayout2").hide();
	$("#viewPunishmentlayout4").hide();
}
function PunishmentshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPunishmentleft").css("width","430px");
	$("#viewPunishmentlayout5").show();
	$("#viewPunishmentlayout2").show();
	$("#viewPunishmentlayout3").hide();
	$("#viewPunishmentlayout4").hide();
}
function PunishmentshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPunishmentleft").css("width","430px");
	$("#viewPunishmentlayout5").show();
	$("#viewPunishmentlayout2").show();
	$("#viewPunishmentlayout3").hide();
	$("#viewPunishmentlayout4").hide();
}
function PunishmenthiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewPunishmentlayout5").hide();
	$("#viewPunishmentlayout2").show();
	$("#viewPunishmentlayout3").hide();
	$("#viewPunishmentlayout4").show();
}
$(function(){
	var aa=$("#Punlocalname").val();
	var bb=$("#Punlocalempid").val();
	var cc=$("#Punlocalpostgradenoname").val();
	var dd=$("#Punlocalpostgradeotherinf").val();
	var ff=$("#Punlocalcenter").val();
	var gg=$("#Punlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename12').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	var punishno="${PUNISHNO }";
	if(punishno==''){
		openOnRight('/hrm/empinfo/viewSinglePunishment?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PUNISH_NO=${punishNo }','viewResumeList_viewPunishmentunit');
	}else{
		$('#PUN_'+punishno).click();
		$('#PUN_'+punishno).attr('class','even selected');
	}


});

function fangdajing12(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY12').val()));
	$('#fangda12').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPunishment&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda12').click();
}
$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": 300,
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
			name="seach_KEY12" id="seach_KEY12" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing12('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda12"
			onclick="fangdajing12()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPunishment"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename12">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSinglePunishment?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PUNISH_NO=0','viewResumeList_viewPunishmentunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editPunishment',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editPunishment',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=23&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewPunishmentleft"
	style="float: left; display: block; width: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="1100px;">
	<thead>
		<tr>
			<th width="2%">No.</th>
			<th width="3%"><spring:message code="hrm.empinfo.punishment_code" /><!-- 惩罚代码--></th>
			<th width="3%"><spring:message code="hrm.empinfo.punishment_day" /><!-- 惩罚日--></th>
			<th width="3%"><spring:message code="hrm.empinfo.relieve_day" /><!-- 解除日--></th>
			<th width="3%"><spring:message code="hrm.empinfo.punishment_organ_name" /><!-- 惩罚机关名--></th>
			<th width="3%"><spring:message code="hrm.empinfo.pay_cut_start_date" /><!-- 减薪开始日--></th>
			<th width="3%"><spring:message code="hrm.empinfo.pay_cut_end_date" /><!-- 减薪结束日--></th>
			<th width="3%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></th>
			<th width="3%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewPunishment}" var="item" varStatus="i">
			<tr id="PUN_${item.PUNISH_NO }"
				onclick="openOnRight('/hrm/empinfo/viewSinglePunishment?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&PUNISH_NO=${item.PUNISH_NO }','viewResumeList_viewPunishmentunit');">
				<td style="text-align: left" nowrap="nowrap">${i.count}</td>
				<td style="text-align: left" nowrap="nowrap">${item.PUNISH_CODE_NAME }</td>
				<td style="text-align: left" nowrap="nowrap">${item.PUNISH_DATE}</td>
				<td style="text-align: left" nowrap="nowrap">${item.RELEASE_DATE }</td>
				<td style="text-align: left" nowrap="nowrap">${item.PUNISH_DEPARTMENT }</td>
				<td style="text-align: left" nowrap="nowrap">${item.PAYCUT_START_DATE }</td>
				<td style="text-align: left" nowrap="nowrap">${item.PAYCUT_END_DATE }</td>
				<c:if test="${not empty item.UPDATED_BY}">
					<td style="text-align: left" nowrap="nowrap">${item.UPDATED_BY }&nbsp&nbsp${item.UPDATED_IP }</td>
					<td style="text-align: left" nowrap="nowrap">${item.UPDATE_DATE}</td>
				</c:if>
				<c:if test="${empty item.UPDATED_BY}">
					<td style="text-align: left" nowrap="nowrap">${item.CREATED_BY }&nbsp&nbsp${item.CREATED_IP }</td>
					<td style="text-align: left" nowrap="nowrap">${item.CREATE_DATE }</td>
				</c:if>
			</tr>

		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Punlocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Punlocalempid" value="${title.EMPID }">
<input type="hidden" id="Punlocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Punlocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Punlocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Punlocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewPunishmentlayout5" class="w-layout-collapse-left"
	onclick="PunishmenthiddenRight('viewResumeList_viewPunishmentunit','viewResumeList_viewPunishmentleft')"></div>
<div id="viewPunishmentlayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="PunishmentshowIdLeft('viewResumeList_viewPunishmentunit')"></div>
<!-- <div id="viewPunishmentlayout2" class="w-layout-collapse-right" onclick="Punishmenthiddenleft('viewResumeList_viewPunishmentleft','viewResumeList_viewPunishmentunit')"></div> -->
<div id="viewPunishmentlayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=
	PunishmentshowId('viewResumeList_viewPunishmentleft');
></div>
</div>
<div id="viewResumeList_viewPunishmentunit" style="display: block;">
</div>
</div>