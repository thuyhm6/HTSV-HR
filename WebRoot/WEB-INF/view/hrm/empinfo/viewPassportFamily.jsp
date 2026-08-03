<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function PassportFamilyhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#PassportFamilylayout3").show();
	$("#PassportFamilylayout5").hide();
	$("#PassportFamilylayout2").hide();
	$("#PassportFamilylayout4").hide();
}
function PassportFamilyshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPassportFamilyleft").css("width","430px");
	$("#PassportFamilylayout5").show();
	$("#PassportFamilylayout2").show();
	$("#PassportFamilylayout3").hide();
	$("#PassportFamilylayout4").hide();
}
function PassportFamilyshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPassportFamilyleft").css("width","430px");
	$("#PassportFamilylayout5").show();
	$("#PassportFamilylayout2").show();
	$("#PassportFamilylayout3").hide();
	$("#PassportFamilylayout4").hide();
}
function PassportFamilyhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#PassportFamilylayout5").hide();
	$("#PassportFamilylayout2").show();
	$("#PassportFamilylayout3").hide();
	$("#PassportFamilylayout4").show();
}
$(function(){
	var aa=$("#PaFalocalname").val();
	var bb=$("#PaFalocalempid").val();
	var cc=$("#PaFalocalpostgradenoname").val();
	var dd=$("#PaFalocalpostgradeotherinf").val();
	var ff=$("#PaFalocalcenter").val();
	var gg=$("#PaFalocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename15').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSinglePassportFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PASSPER_NO=${passperNo }&flag=2','viewResumeList_viewPassportFamilyunit');
});

function fangdajing15(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY15').val()));
	$('#fangda15').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPassportFamily&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda15').click();
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
			type="text" name="seach_KEY15" id="seach_KEY15" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing15('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda15" onclick="fangdajing15()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPassportFamily" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename15">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSinglePassportFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PASSPER_NO=0&flag=2','viewResumeList_viewPassportFamilyunit');" href="#">
					<span><!-- 添加 --><spring:message code="ess.empInfo.insert" /></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editPassportFamily',navTabAjaxDone)" href="#"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete" /></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editPassportFamily',navTabAjaxDone)" href="#"><span><!-- 保存 --><spring:message code="org.title.SAVE" /></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=26&PERSON_ID=${PERSON_ID }"><span><!-- 导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewPassportFamilyleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="2%">No.</th>
					<th width="3%"><!-- 关系 --><spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" /></th>
					<th width="3%"><!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" /></th>
					<th width="3%"><!-- 护照号码 --><spring:message code="hrm.viewpassportFamily.HUZHAOHAOMA.b" /></th>
					<th width="3%"><!-- 护照有效期 --><spring:message code="hrm.empinfo.passport's_period_validity" /></th>
					<th width="3%"><!-- 身份证号码 --><spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" /></th>
					<th width="3%"><!-- 居留许可证号码 --><spring:message code="hrm.empinfo.Residence_permit_number" /></th>
					<th width="3%"><!-- 居留许可证有效期 --><spring:message code="hrm.empinfo.Valid_period_residence_permit" /></th>
					<th width="3%"><!-- 变更者 --><spring:message code="hrm.empinfo.UPDATED_BY" /></th>
					<th width="3%"><!-- 变更时间 --><spring:message code="hrm.empinfo.UPDATE_DATE" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewPassportPerson}" var="item" varStatus="i">
						<tr onclick="openOnRight('/hrm/empinfo/viewSinglePassportFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&PASSPER_NO=${item.PASSPER_NO }&flag=2','viewResumeList_viewPassportFamilyunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.RELATION }</td>
						<td style="text-align:left">${item.RELATION_NAME }</td>
						<td style="text-align:left">${item.PASSPORTNUM}</td>
						<td style="text-align:left">${item.PASSPORT_DATE }</td>
						<td style="text-align:left">${item.IDCARD_NO }</td>
						<td style="text-align:left">${item.RESI_PERMIT_NUM }</td>
						<td style="text-align:left">${item.RESI_PERMIT_DATE }</td>
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
		<input type="hidden" id="PaFalocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="PaFalocalempid" value="${title.EMPID }">
					<input type="hidden" id="PaFalocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="PaFalocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="PaFalocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="PaFalocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="PassportFamilylayout5" class="w-layout-collapse-left" onclick="PassportFamilyhiddenRight('viewResumeList_viewPassportFamilyunit','viewResumeList_viewPassportFamilyleft')"></div>
		<div id="PassportFamilylayout4" class="w-layout-collapse-right" style="display:none;" onclick="PassportFamilyshowIdLeft('viewResumeList_viewPassportFamilyunit')"></div>
		<div id="PassportFamilylayout2" class="w-layout-collapse-right" onclick="PassportFamilyhiddenleft('viewResumeList_viewPassportFamilyleft','viewResumeList_viewPassportFamilyunit')"></div>
		<div id="PassportFamilylayout3" class="w-layout-collapse-left" style="display:none;" onclick="PassportFamilyshowId('viewResumeList_viewPassportFamilyleft')"></div>
	</div>
	<div id="viewResumeList_viewPassportFamilyunit"  style="display:block;">
	</div>
</div>