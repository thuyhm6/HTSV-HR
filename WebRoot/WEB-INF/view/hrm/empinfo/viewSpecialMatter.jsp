<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function SpecialMatterhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#SpecialMatterlayout3").show();
	$("#SpecialMatterlayout5").hide();
	$("#SpecialMatterlayout2").hide();
	$("#SpecialMatterlayout4").hide();
}
function SpecialMattershowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewSpecialMatterleft").css("width","430px");
	$("#SpecialMatterlayout5").show();
	$("#SpecialMatterlayout2").show();
	$("#SpecialMatterlayout3").hide();
	$("#SpecialMatterlayout4").hide();
}
function SpecialMattershowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewSpecialMatterleft").css("width","430px");
	$("#SpecialMatterlayout5").show();
	$("#SpecialMatterlayout2").show();
	$("#SpecialMatterlayout3").hide();
	$("#SpecialMatterlayout4").hide();
}
function SpecialMatterhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#SpecialMatterlayout5").hide();
	$("#SpecialMatterlayout2").show();
	$("#SpecialMatterlayout3").hide();
	$("#SpecialMatterlayout4").show();
}
$(function(){
	var aa=$("#Spelocalname").val();
	var bb=$("#Spelocalempid").val();
	var cc=$("#Spelocalpostgradenoname").val();
	var dd=$("#Spelocalpostgradeotherinf").val();
	var ff=$("#Spelocalcenter").val();
	var gg=$("#Spelocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlenameSPE').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleSpecialMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&SPECIAL_NO=${specialNo }','viewResumeList_viewSpecialMatterunit');
});

function fangdajing13(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY13').val()));
	$('#fangda13').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSpecialMatter&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda13').click();
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
			type="text" name="seach_KEY13" id="seach_KEY13" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing13('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda13" onclick="fangdajing13()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSpecialMatter" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlenameSPE">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSingleSpecialMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&SPECIAL_NO=0','viewResumeList_viewSpecialMatterunit');" href="#">
					<span><spring:message code="button.add"/><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editSpecialMatter',navTabAjaxDone)" href="#">
				<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editSpecialMatter',navTabAjaxDone)" href="#">
				<span><spring:message code="button.sys.affirm.save"/><!-- 保存 --></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=24&PERSON_ID=${PERSON_ID }">
				<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewSpecialMatterleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1000px;">
			<thead>
				<tr>
					<th width="2%">No.</th>
					<th width="3%"><spring:message code="hr.hrm.empinfo.INFOR_DIS_CODE.Z"/><!-- 信息区分代码 --></th>
					<th width="3%"><spring:message code="hr.hrm.empinfo.GENERATION_TITLE.Z"/><!-- 生成题目 --></th>
					<th width="3%"><spring:message code="inct.salesman.createTime"/><!-- 注册时间 --></th>
					<th width="3%"><spring:message code="hrm.contract.content"/><!-- 内容 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.UPDATED_BY"/><!-- 变更者 --></th>
					<th width="3%"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!-- 变更时间 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewSpecialMatter}" var="item" varStatus="i">
						<tr onclick="openOnRight('/hrm/empinfo/viewSingleSpecialMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&SPECIAL_NO=${item.SPECIAL_NO }','viewResumeList_viewSpecialMatterunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.INFOR_DIS_CODE_NAME }</td>
						<td style="text-align:left">${item.GENERATION_TITLE }</td>
						<td style="text-align:left">${item.REGISTRATION_DATE}</td>
						<td style="text-align:left">${item.SPECIAL_CONTENT}</td>
						<c:if test="${not empty item.UPDATED_BY}">
						<td style="text-align:left">${item.UPDATED_BY }&nbsp&nbsp${item.UPDATED_IP }</td>
						<td style="text-align:left">${item.UPDATE_DATE}</td>
						</c:if>
						<c:if test="${empty item.UPDATED_BY}">
						<td style="text-align:left">${item.CREATED_BY }&nbsp&nbsp${item.CREATED_IP }</td>
						<td style="text-align:left">${item.CREATE_DATE }</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="Spelocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Spelocalempid" value="${title.EMPID }">
					<input type="hidden" id="Spelocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Spelocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Spelocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Spelocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="SpecialMatterlayout5" class="w-layout-collapse-left" onclick="SpecialMatterhiddenRight('viewResumeList_viewSpecialMatterunit','viewResumeList_viewSpecialMatterleft')"></div>
		<div id="SpecialMatterlayout4" class="w-layout-collapse-right" style="display:none;" onclick="SpecialMattershowIdLeft('viewResumeList_viewSpecialMatterunit')"></div>
		<div id="SpecialMatterlayout2" class="w-layout-collapse-right" onclick="SpecialMatterhiddenleft('viewResumeList_viewSpecialMatterleft','viewResumeList_viewSpecialMatterunit')"></div>
		<div id="SpecialMatterlayout3" class="w-layout-collapse-left" style="display:none;" onclick="SpecialMattershowId('viewResumeList_viewSpecialMatterleft')"></div>
	</div>
	<div id="viewResumeList_viewSpecialMatterunit"  style="display:block;">
	</div>
</div>