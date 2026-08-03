<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function PassportPersonhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#PassportPersonlayout3").show();
	$("#PassportPersonlayout5").hide();
	$("#PassportPersonlayout2").hide();
	$("#PassportPersonlayout4").hide();
}
function PassportPersonshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPassportPersonleft").css("width","430px");
	$("#PassportPersonlayout5").show();
	$("#PassportPersonlayout2").show();
	$("#PassportPersonlayout3").hide();
	$("#PassportPersonlayout4").hide();
}
function PassportPersonshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewPassportPersonleft").css("width","430px");
	$("#PassportPersonlayout5").show();
	$("#PassportPersonlayout2").show();
	$("#PassportPersonlayout3").hide();
	$("#PassportPersonlayout4").hide();
}
function PassportPersonhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#PassportPersonlayout5").hide();
	$("#PassportPersonlayout2").show();
	$("#PassportPersonlayout3").hide();
	$("#PassportPersonlayout4").show();
}
$(function(){
	var aa=$("#Perlocalname").val();
	var bb=$("#Perlocalempid").val();
	var cc=$("#Perlocalpostgradenoname").val();
	var dd=$("#Perlocalpostgradeotherinf").val();
	var ff=$("#Perlocalcenter").val();
	var gg=$("#Perlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename14').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSinglePassportPerson?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PASSPER_NO=${passperNo }&flag=1','viewResumeList_viewPassportPersonunit');
});

function fangdajing14(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY14').val()));
	$('#fangda14').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPassportPerson&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda14').click();
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
			type="text" name="seach_KEY14" id="seach_KEY14" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing14('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda14" onclick="fangdajing14()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewPassportPerson" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename14">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSinglePassportPerson?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&PASSPER_NO=0&flag=1','viewResumeList_viewPassportPersonunit');" href="#">
					<span><spring:message code="button.add"/><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editPassportPerson',navTabAjaxDone)" href="#">
				<span><spring:message code="button.delete"/><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editPassportPerson',navTabAjaxDone)" href="#">
				<span><spring:message code="button.sys.affirm.save"/><!--保存--></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=25&PERSON_ID=${PERSON_ID }">
				<span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到EXECL--></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewPassportPersonleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1200px;">
			<thead>
				<tr>
					<th width="2%">No.</th>
					<th width="3%"><spring:message code="hrm.recruitManage.IDCARD_PNAME"/><!--证件人姓名--></th>
					<th width="3%"><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME"/><!--关系--></th>
					<th width="3%"><spring:message code="hrm.recruitManage.IDCARD_NAME"/><!--证件名称--></th>
					<th width="3%"><spring:message code="hrm.recruitManage.IDCARD_NO"/><!--证件号码--></th>
					<th width="3%"><spring:message code="hrm.recruitManage.IDCARD_ENDTIME"/><!--证件到期日期--></th>
					<th width="3%"><spring:message code="hrm.recruitManage.OFFICE_CODE"/><!--发证机关--></th>
					<th width="3%"><spring:message code="hrm.empinfo.REMARK"/><!--备注--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewPassportPerson}" var="item" varStatus="i">
						<tr onclick="openOnRight('/hrm/empinfo/viewSinglePassportPerson?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&PASSPER_NO=${item.PASSPER_NO }&flag=1','viewResumeList_viewPassportPersonunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.RELATION_NAME}</td>
						<td style="text-align:left">${item.RELATION }</td>
						<td style="text-align:left">${item.CERTIFICATE_TYPE_CODE }</td>
						<td style="text-align:left">${item.CERTIFICATE_NUM }</td>
						<td style="text-align:left">${item.CERTIFICATE_DATE }</td>
						<td style="text-align:left">${item.OFFICE_CODE }</td>
						<td style="text-align:left">${item.REMARK }</td>
					</tr>
					
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="Perlocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Perlocalempid" value="${title.EMPID }">
					<input type="hidden" id="Perlocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Perlocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Perlocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Perlocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="PassportPersonlayout5" class="w-layout-collapse-left" onclick="PassportPersonhiddenRight('viewResumeList_viewPassportPersonunit','viewResumeList_viewPassportPersonleft')"></div>
		<div id="PassportPersonlayout4" class="w-layout-collapse-right" style="display:none;" onclick="PassportPersonshowIdLeft('viewResumeList_viewPassportPersonunit')"></div>
		<div id="PassportPersonlayout2" class="w-layout-collapse-right" onclick="PassportPersonhiddenleft('viewResumeList_viewPassportPersonleft','viewResumeList_viewPassportPersonunit')"></div>
		<div id="PassportPersonlayout3" class="w-layout-collapse-left" style="display:none;" onclick="PassportPersonshowId('viewResumeList_viewPassportPersonleft')"></div>
	</div>
	<div id="viewResumeList_viewPassportPersonunit"  style="display:block;">
	</div>
</div>