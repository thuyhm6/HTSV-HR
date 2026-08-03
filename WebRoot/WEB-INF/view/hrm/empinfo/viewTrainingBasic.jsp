<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function TrainingBasichiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewTrainingBasiclayout3").show();
	$("#viewTrainingBasiclayout5").hide();
	$("#viewTrainingBasiclayout2").hide();
	$("#viewTrainingBasiclayout4").hide();
}
function TrainingBasicshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewTrainingBasicleft").css("width","430px");
	$("#viewTrainingBasiclayout5").show();
	$("#viewTrainingBasiclayout2").show();
	$("#viewTrainingBasiclayout3").hide();
	$("#viewTrainingBasiclayout4").hide();
}
function TrainingBasicshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewTrainingBasicleft").css("width","430px");
	$("#viewTrainingBasiclayout5").show();
	$("#viewTrainingBasiclayout2").show();
	$("#viewTrainingBasiclayout3").hide();
	$("#viewTrainingBasiclayout4").hide();
}
function TrainingBasichiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewTrainingBasiclayout5").hide();
	$("#viewTrainingBasiclayout2").show();
	$("#viewTrainingBasiclayout3").hide();
	$("#viewTrainingBasiclayout4").show();
}
$(function(){
	var aa=$("#Traininglocalname").val();
	var bb=$("#Traininglocalempid").val();
	var cc=$("#Traininglocalpostgradenoname").val();
	var dd=$("#Traininglocalpostgradeotherinf").val();
	var ff=$("#Traininglocalcenter").val();
	var gg=$("#Traininglocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename20').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleTrainingBasic?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&TRAIN_NO=${TRAIN_NO }','viewResumeList_viewTrainingBasicunit');
});

function fangdajing20(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY20').val()));
	$('#fangda20').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewTrainingBasic&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda20').click();
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

	function excelimport_pa0818(){
		$("#importExcelDialog_pa0818").attr('href','/pa/excelImport/importExcelData?importFunName=/importPaemp1');
		$("#importExcelDialog_pa0818").click();
	}	
</script>
<a id="importExcelDialog_pa0818"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0818" href="#" target="navTab" mask="true"><span style="display:none;">导入结果</span></a>
<div class="pageContent">

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="user_table">
	<tr>
		<td class="td_title" style="width: 10%"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
		<td class="td_type" style="width: 10%"><input type="text"
			name="seach_KEY20" id="seach_KEY20" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing20('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda20"
			onclick="fangdajing20()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewTrainingBasic"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename20">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleTrainingBasic?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&TRAIN_NO=0','viewResumeList_viewTrainingBasicunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editTrainingBasic',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editTrainingBasic',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<!--<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'HTSV'}">
		<li>
			<a class="buttonActive"  href="/pa/excelExport/downloadExcelTrain?file=train"  >
				<span><spring:message code="hrm.contract.Download_templates" />模板下载</span>
			</a>
		</li>
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'HTSV'}">
		<li>
			<a class="buttonActive" onclick="excelimport_pa0818()" href="#" >
				<span><spring:message code="hrm.contract.Excel_import" />Excel导入</span>
			</a>
		</li>
	</c:if>
	--><li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=214&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewTrainingBasicleft"
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="1200px;">
	<thead>
		<tr>
			<th width="1%">No.</th>
			<th width="3%"><spring:message code="hrm.empinfo.TRAIN_ADDRESS" /><!--培训地点--></th>
			<th width="3%"><spring:message code="hrm.empinfo.training_distinction" /><!--培训区分--></th>
			<th width="3%"><spring:message code="hrm.empinfo.TRAIN_curriculum" /><!--培训课程--></th>
			<th width="3%"><spring:message code="hrm.empinfo.Training_form" /><!--培训形式--></th>
			<th width="3%"><spring:message code="hrm.empinfo.START_DATE" /><!--培训开始日期--></th>
			<th width="3%"><spring:message code="hrm.empinfo.END_DATE" /><!--培训结束日期--></th>
			<th width="3%"><spring:message code="hrm.empinfo.TRAINING_RESULT" /><!--培训结果--></th>
			<th width="3%"><spring:message code="hrm.empinfo.REMARK" /><!--备注--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewTrainingBasic}" var="item" varStatus="i">
			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleTrainingBasic?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&TRAIN_NO=${item.TRAIN_NO }','viewResumeList_viewTrainingBasicunit');">
				<td style="text-align: left">${i.count}</td>
				<td style="text-align: left">${item.PLACE}</td>
				<td style="text-align: left">${item.TRAINING_DIFFERENTIATE }</td>
				<td style="text-align: left">${item.COURSE_NAME }</td>
				<td style="text-align: left">${item.TRAINING_METHOD }</td>
				<td style="text-align: left">${item.START_DATE }</td>
				<td style="text-align: left">${item.END_DATE }</td>
				<td style="text-align: left">${item.TRAINING_RESULT }</td>
				<td style="text-align: left">${item.REMARKS}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Traininglocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Traininglocalempid" value="${title.EMPID }">
<input type="hidden" id="Traininglocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Traininglocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Traininglocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Traininglocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewTrainingBasiclayout5" class="w-layout-collapse-left"
	onclick="TrainingBasichiddenRight('viewResumeList_viewTrainingBasicunit','viewResumeList_viewTrainingBasicleft')"></div>
<div id="viewTrainingBasiclayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="TrainingBasicshowIdLeft('viewResumeList_viewTrainingBasicunit')"></div>
<div id="viewTrainingBasiclayout2" class="w-layout-collapse-right"
	onclick="TrainingBasichiddenleft('viewResumeList_viewTrainingBasicleft','viewResumeList_viewTrainingBasicunit')"></div>
<div id="viewTrainingBasiclayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=
	TrainingBasicshowId('viewResumeList_viewTrainingBasicleft');
></div>
</div>
<div id="viewResumeList_viewTrainingBasicunit" style="display: block;">
</div>
</div>