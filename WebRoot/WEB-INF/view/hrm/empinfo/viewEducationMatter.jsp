<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function EducationMatterhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewEducationlayout3").show();
	$("#viewEducationlayout5").hide();
	$("#viewEducationlayout2").hide();
	$("#viewEducationlayout4").hide();
}
function EducationMattershowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEducationMatterleft").css("width","430px");
	$("#viewEducationlayout5").show();
	$("#viewEducationlayout2").show();
	$("#viewEducationlayout3").hide();
	$("#viewEducationlayout4").hide();
}
function EducationMattershowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEducationMatterleft").css("width","430px");
	$("#viewEducationlayout5").show();
	$("#viewEducationlayout2").show();
	$("#viewEducationlayout3").hide();
	$("#viewEducationlayout4").hide();
}
function EducationMatterhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewEducationlayout5").hide();
	$("#viewEducationlayout2").show();
	$("#viewEducationlayout3").hide();
	$("#viewEducationlayout4").show();
}
$(function(){
	var aa=$("#Edulocalname").val();
	var bb=$("#Edulocalempid").val();
	var cc=$("#Edulocalpostgradenoname").val();
	var dd=$("#Edulocalpostgradeotherinf").val();
	var ff=$("#Edulocalcenter").val();
	var gg=$("#Edulocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename8').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleEducationMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EDUC_NO=${educNo }','viewResumeList_viewEducationMatterunit');
});

function fangdajing8(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY8').val()));
	$('#fangda8').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEducationMatter&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda8').click();
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
			name="seach_KEY8" id="seach_KEY8" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing8('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda8"
			onclick="fangdajing8()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEducationMatter"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename8">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleEducationMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EDUC_NO=0','viewResumeList_viewEducationMatterunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editEducationMatter',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editEducationMatter',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=18&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewEducationMatterleft"
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="800px;">
	<thead>
		<tr>
			<th width="1%">No.</th>
			<th width="3%"><spring:message code="hrm.recruitManage.INSTITUTION_SCHOOL" /><!--毕业学校--></th>
			<th width="3%"><spring:message code="hrm.empinfo.DEGREE_CODE" /><!--学历--></th>
			<th width="3%"><spring:message code="hrm.recruitManage.SUBJECT" /><!--专业--></th>
			<th width="3%"><spring:message code="hrm.recruitManage.START_DATE" /><!--开始年月--></th>
			<th width="3%"><spring:message code="hrm.recruitManage.END_DATE" /><!--结束年月--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewEducationMatter}" var="item" varStatus="i">
			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleEducationMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&EDUC_NO=${item.EDUC_NO }','viewResumeList_viewEducationMatterunit');">
				<td style="text-align: left">${i.count}</td>
				<td style="text-align: left">${item.INSTITUTION_NAME }</td>
				<td style="text-align: left">${item.DEGREE_CODE_NAME }</td>
				<td style="text-align: left">${item.SUBJECT }</td>
				<td style="text-align: left">${item.START_DATE }</td>
				<td style="text-align: left">${item.END_DATE }</td>
				<!-- 
						<c:if  test="${item.FINAL_DEGREE_WHETHER=='Y' }">
						<td style="text-align:left">√</td>
						</c:if>
						<c:if  test="${item.FINAL_DEGREE_WHETHER!='Y' }">
						<td style="text-align:left"></td>
						</c:if>
						<c:if test="${not empty item.UPDATED_BY}">
						<td style="text-align:left">${item.UPDATED_BY }&nbsp&nbsp${item.UPDATED_IP }</td>
						<td style="text-align:left">${item.UPDATE_DATE}</td>
						</c:if>
						<c:if test="${empty item.UPDATED_BY}">
						<td style="text-align:left">${item.CREATED_BY }&nbsp&nbsp${item.CREATED_IP }</td>
						<td style="text-align:left">${item.CREATE_DATE }</td>
						</c:if> -->
			</tr>

		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Edulocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Edulocalempid" value="${title.EMPID }">
<input type="hidden" id="Edulocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Edulocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Edulocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Edulocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewEducationlayout5" class="w-layout-collapse-left"
	onclick="EducationMatterhiddenRight('viewResumeList_viewEducationMatterunit','viewResumeList_viewEducationMatterleft')"></div>
<div id="viewEducationlayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="EducationMattershowIdLeft('viewResumeList_viewEducationMatterunit')"></div>
<div id="viewEducationlayout2" class="w-layout-collapse-right"
	onclick="EducationMatterhiddenleft('viewResumeList_viewEducationMatterleft','viewResumeList_viewEducationMatterunit')"></div>
<div id="viewEducationlayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=EducationMattershowId('viewResumeList_viewEducationMatterleft');
></div>
</div>
<div id="viewResumeList_viewEducationMatterunit" style="display: block;">
</div>
</div>