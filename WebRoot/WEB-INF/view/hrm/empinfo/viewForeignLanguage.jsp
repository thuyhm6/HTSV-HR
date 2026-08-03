<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function ForeignLanguagehiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewForeignlayout3").show();
	$("#viewForeignlayout5").hide();
	$("#viewForeignlayout2").hide();
	$("#viewForeignlayout4").hide();
}
function ForeignLanguageshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewForeignLanguageleft").css("width","430px");
	$("#viewForeignlayout5").show();
	$("#viewForeignlayout2").show();
	$("#viewForeignlayout3").hide();
	$("#viewForeignlayout4").hide();
}
function ForeignLanguageshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewForeignLanguageleft").css("width","430px");
	$("#viewForeignlayout5").show();
	$("#viewForeignlayout2").show();
	$("#viewForeignlayout3").hide();
	$("#viewForeignlayout4").hide();
}
function ForeignLanguagehiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewForeignlayout5").hide();
	$("#viewForeignlayout2").show();
	$("#viewForeignlayout3").hide();
	$("#viewForeignlayout4").show();
}
$(function(){
	var aa=$("#Forlocalname").val();
	var bb=$("#Forlocalempid").val();
	var cc=$("#Forlocalpostgradenoname").val();
	var dd=$("#Forlocalpostgradeotherinf").val();
	var ff=$("#Forlocalcenter").val();
	var gg=$("#Forlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename10').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleForeignLanguage?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&LANGUAGE_NO=${languageNo }','viewResumeList_viewForeignLanguageunit');
});

function fangdajing10(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY10').val()));
	$('#fangda10').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewForeignLanguage&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda10').click();
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
			name="seach_KEY10" id="seach_KEY10" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing10('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda10"
			onclick="fangdajing10()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewForeignLanguage"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename10">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleForeignLanguage?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&LANGUAGE_NO=0','viewResumeList_viewForeignLanguageunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editForeignLanguage',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editForeignLanguage',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=20&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewForeignLanguageleft"
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="600px;">
	<thead>
		<tr>
			<th width="1%">No.</th>
			<th width="3%"><spring:message code="hrm.empinfo.LANGUAGE" /><!--语言--></th>
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="3%"><spring:message code="hrm.empinfo.LISTEN.Z" /><!--听--></th>
			</c:if>
			<th width="3%"><spring:message code="hrm.empinfo.SAY" /><!--说--></th>
			<th width="3%"><spring:message code="hrm.empinfo.READ" /><!--读--></th>
			<th width="3%"><spring:message code="hrm.empinfo.WRITE" /><!--写--></th>
			<th width="3%"><spring:message code="hrm.empinfo.comprehensive" /><!--综合--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewForeignLanguage}" var="item" varStatus="i">
			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleForeignLanguage?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&LANGUAGE_NO=${item.LANGUAGE_NO }','viewResumeList_viewForeignLanguageunit');">
				<td style="text-align: left">${i.count}</td>
				<td style="text-align: left">${item.LANGUAGE_TYPE }</td>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td style="text-align: left">${item.LISTEN_NAME }</td>
				</c:if>
				<td style="text-align: left">${item.SAY_NAME }</td>
				<td style="text-align: left">${item.READ_NAME }</td>
				<td style="text-align: left">${item.WRITE_NAME }</td>
				<td style="text-align: left">${item.COMBINED_NAME }</td>
			</tr>

		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Forlocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Forlocalempid" value="${title.EMPID }">
<input type="hidden" id="Forlocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Forlocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Forlocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Forlocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewForeignlayout5" class="w-layout-collapse-left"
	onclick="ForeignLanguagehiddenRight('viewResumeList_viewForeignLanguageunit','viewResumeList_viewForeignLanguageleft')"></div>
<div id="viewForeignlayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="ForeignLanguageshowIdLeft('viewResumeList_viewForeignLanguageunit')"></div>
<div id="viewForeignlayout2" class="w-layout-collapse-right"
	onclick="ForeignLanguagehiddenleft('viewResumeList_viewForeignLanguageleft','viewResumeList_viewForeignLanguageunit')"></div>
<div id="viewForeignlayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=
	ForeignLanguageshowId('viewResumeList_viewForeignLanguageleft');
></div>
</div>
<div id="viewResumeList_viewForeignLanguageunit" style="display: block;">
</div>
</div>