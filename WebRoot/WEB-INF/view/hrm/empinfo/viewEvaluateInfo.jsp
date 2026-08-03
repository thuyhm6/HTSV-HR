<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function EvaluateInfohiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewEvaluateInfolayout3").show();
	$("#viewEvaluateInfolayout5").hide();
	$("#viewEvaluateInfolayout2").hide();
	$("#viewEvaluateInfolayout4").hide();
}
function EvaluateInfoshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEvaluateInfoleft").css("width","430px");
	$("#viewEvaluateInfolayout5").show();
	$("#viewEvaluateInfolayout2").show();
	$("#viewEvaluateInfolayout3").hide();
	$("#viewEvaluateInfolayout4").hide();
}
function EvaluateInfoshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEvaluateInfoleft").css("width","430px");
	$("#viewEvaluateInfolayout5").show();
	$("#viewEvaluateInfolayout2").show();
	$("#viewEvaluateInfolayout3").hide();
	$("#viewEvaluateInfolayout4").hide();
}
function EvaluateInfohiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewEvaluateInfolayout5").hide();
	$("#viewEvaluateInfolayout2").show();
	$("#viewEvaluateInfolayout3").hide();
	$("#viewEvaluateInfolayout4").show();
}
$(function(){
	var aa=$("#Evaluatelocalname").val();
	var bb=$("#Evaluatelocalempid").val();
	var cc=$("#Evaluatelocalpostgradenoname").val();
	var dd=$("#Evaluatelocalpostgradeotherinf").val();
	var ff=$("#Evaluatelocalcenter").val();
	var gg=$("#Evaluatelocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename30').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleEvaluateInfo?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EVALUATE_NO=${evaluateNo }','viewResumeList_viewEvaluateInfounit');
});

function fangdajing30(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY30').val()));
	$('#fangda30').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvaluateInfo&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda30').click();
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
		<td class="td_title"  style="width: 10%"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td class="td_type"  style="width: 10%"><input
			type="text" name="seach_KEY30" id="seach_KEY30" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing30('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda30" onclick="fangdajing30()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvaluateInfo" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename30">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSingleEvaluateInfo?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EVALUATE_NO=0','viewResumeList_viewEvaluateInfounit');" href="#">
					<span><spring:message code="button.add" /><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editEvaluateInfo',navTabAjaxDone)" href="#">
				<span><spring:message code="button.delete" /><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editEvaluateInfo',navTabAjaxDone)" href="#">
				<span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=215&PERSON_ID=${PERSON_ID }">
				<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a>					
			</li>
			<li>
				<a class="buttonActive" href="/pa/excelExport/exportEvaluateInfoExcelModel?CPNY_ID=${CPNY_ID}">
				<span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/pa/excelImport/importEvaluateInfoData?&importFunName=/importEvaluateInfoExcel" target="dialog" mask="true" width="400" height="200" >
				<span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span></a>
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewEvaluateInfoleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="3%"><spring:message code="hrm.empinfo.Evaluation_year" />
										<!-- 评价年度 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.January" />
										<!-- 1月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.February" />
										<!-- 2月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.March" />
										<!-- 3月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.April" />
										<!-- 4月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.May" />
										<!-- 5月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.June" />
										<!-- 6月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.July" />
										<!-- 7月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.August" />
										<!-- 8月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.September" />
										<!-- 9月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.October" />
										<!-- 10月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.November" />
										<!-- 11月 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.December" />
										<!-- 12月 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewEvaluateInfo}" var="item" varStatus="i">
						<tr onclick="openOnRight('/hrm/empinfo/viewSingleEvaluateInfo?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&EVALUATE_NO=${item.EVALUATE_NO }','viewResumeList_viewEvaluateInfounit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.EVALUATE_YEAR }</td>
						<td style="text-align:left">${item.JANUARY }</td>
						<td style="text-align:left">${item.FEBRUARY }</td>
						<td style="text-align:left">${item.MARCH }</td>
						<td style="text-align:left">${item.APRIL }</td>
						<td style="text-align:left">${item.MAY }</td>
						<td style="text-align:left">${item.JUNE }</td>
						<td style="text-align:left">${item.JULY }</td>
						<td style="text-align:left">${item.AUGUST }</td>
						<td style="text-align:left">${item.SEPTEMBER }</td>
						<td style="text-align:left">${item.OCTOBER }</td>
						<td style="text-align:left">${item.NOVEMBER }</td>
						<td style="text-align:left">${item.DECEMBER }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="Evaluatelocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Evaluatelocalempid" value="${title.EMPID }">
					<input type="hidden" id="Evaluatelocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Evaluatelocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Evaluatelocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Evaluatelocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="viewEvaluateInfolayout5" class="w-layout-collapse-left" onclick="EvaluateInfohiddenRight('viewResumeList_viewEvaluateInfounit','viewResumeList_viewEvaluateInfoleft')"></div>
		<div id="viewEvaluateInfolayout4" class="w-layout-collapse-right" style="display:none;" onclick="EvaluateInfoshowIdLeft('viewResumeList_viewEvaluateInfounit')"></div>
		<div id="viewEvaluateInfolayout2" class="w-layout-collapse-right" onclick="EvaluateInfohiddenleft('viewResumeList_viewEvaluateInfoleft','viewResumeList_viewEvaluateInfounit')"></div>
		<div id="viewEvaluateInfolayout3" class="w-layout-collapse-left" style="display:none;" onclick="EvaluateInfoshowId('viewResumeList_viewEvaluateInfoleft')"></div>
	</div>
	<div id="viewResumeList_viewEvaluateInfounit"  style="display:block;">
	</div>
</div>