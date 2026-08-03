<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function hiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#PregnantManagementlayout3").show();
	$("#PregnantManagementlayout5").hide();
	$("#PregnantManagementlayout2").hide();
	$("#PregnantManagementlayout4").hide();
}
function PregnantManagementshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPregnantManagementleft").css("width","430px");
	$("#PregnantManagementlayout5").show();
	$("#PregnantManagementlayout2").show();
	$("#PregnantManagementlayout3").hide();
	$("#PregnantManagementlayout4").hide();
}
function PregnantManagementPregnantManagementhiddenleft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPregnantManagementleft").css("width","430px");
	$("#PregnantManagementlayout5").show();
	$("#PregnantManagementlayout2").show();
	$("#PregnantManagementlayout3").hide();
	$("#PregnantManagementlayout4").hide();
}
function PregnantManagementhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#PregnantManagementlayout5").hide();
	$("#PregnantManagementlayout2").show();
	$("#PregnantManagementlayout3").hide();
	$("#PregnantManagementlayout4").show();
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
	openOnRight('/hrm/empinfo/viewSinglePregnantManagement?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&HR_PREGNANT_MANAGE_NO=${HR_PREGNANT_MANAGE_NO }','viewResumeList_viewPregnantManagementunit');
});

function fangdajing7(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY7').val()));
	$('#fangda7').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPregnantManagement&seach_KEY='+name);
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
		<td class="td_title"  style="width: 10%"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td class="td_type"  style="width: 10%"><input
			type="text" name="seach_KEY7" id="seach_KEY7" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing7('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda7" onclick="fangdajing7()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPregnantManagement" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename7">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSinglePregnantManagement?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&HR_PREGNANT_MANAGE_NO=0','viewResumeList_viewPregnantManagementunit');" href="#">
					<span><!-- 添加 --><spring:message code="ess.empInfo.insert" /></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editPregnantManagement',navTabAjaxDone)" href="#"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete" /></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editPregnantManagement',navTabAjaxDone)" href="#"><span><!-- 保存 --><spring:message code="org.title.SAVE" /></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=158&PERSON_ID=${PERSON_ID }"><span><!-- 导出到EXECL --><spring:message code="ess.infoApply.export_to_Excel" /></span></a>  
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewPregnantManagementleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="3%"><!-- 员工号 --><spring:message code="hrm.viewpregnantManagement.YUANGONGHAO.b" /></th>
					<th width="3%"><!-- 怀孕日期 --><spring:message code="hrm.viewpregnantManagement.HUANYUNRIQI.b" /></th>
					<th width="3%"><!-- 预产期 --><spring:message code="liang.ess.infoApply.title.expected_date" /></th>
					<th width="3%"><!-- 生育日期 --><spring:message code="hrm.viewpregnantManagement.SHENGYURIQI.b" /></th>
					<th width="3%"><!-- 哺乳期开始日期 --><spring:message code="hrm.viewpregnantManagement.BURUQIKAISHIRIQI.b" /></th>
					<th width="3%"><!-- 哺乳期结束日期 --><spring:message code="hrm.viewpregnantManagement.BURUQIJIESHURIQI.b" /></th>
					<th width="3%"><!-- 备注 --><spring:message code="ess.empInfo.remarks" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewPregnantManagementList}" var="item" varStatus="i">
						<tr onclick="openOnRight('/hrm/empinfo/viewSinglePregnantManagement?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&HR_PREGNANT_MANAGE_NO=${item.HR_PREGNANT_MANAGE_NO }','viewResumeList_viewPregnantManagementunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.PERSON_ID }</td>
						<td style="text-align:left">${item.FETATION_DATE }</td>
						<td style="text-align:left">${item.EXPECTED_BIRTH_DATE }</td>
						<td style="text-align:left">${item.CHILDBIRTH_DATE }</td>
						<td style="text-align:left">${item.START_BABYCARE_DATE }</td>
						<td style="text-align:left">${item.END_BABYCARE_DATE }</td>
						<td style="text-align:left">${item.REMARK }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="Explocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Explocalempid" value="${title.EMPID }">
					<input type="hidden" id="Explocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Explocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Explocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Explocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="PregnantManagementlayout5" class="w-layout-collapse-left" onclick="PregnantManagementhiddenRight('viewResumeList_viewPregnantManagementunit','viewResumeList_viewPregnantManagementleft')"></div>
		<div id="PregnantManagementlayout4" class="w-layout-collapse-right" style="display:none;" onclick="PregnantManagementPregnantManagementhiddenleft('viewResumeList_viewPregnantManagementunit')"></div>
		<div id="PregnantManagementlayout2" class="w-layout-collapse-right" onclick="hiddenleft('viewResumeList_viewPregnantManagementleft','viewResumeList_viewPregnantManagementunit')"></div>
		<div id="PregnantManagementlayout3" class="w-layout-collapse-left" style="display:none;" onclick="PregnantManagementshowId('viewResumeList_viewPregnantManagementleft')"></div>
	</div>
	<div id="viewResumeList_viewPregnantManagementunit"  style="display:block;">
	</div>
</div>