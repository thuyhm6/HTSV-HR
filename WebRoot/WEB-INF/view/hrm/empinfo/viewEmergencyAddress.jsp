<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function EmergencyAddresshiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewEmergencyAddresslayout3").show();
	$("#viewEmergencyAddresslayout5").hide();
	$("#viewEmergencyAddresslayout2").hide();
	$("#viewEmergencyAddresslayout4").hide();
}
function EmergencyAddressshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEmergencyAddressleft").css("width","430px");
	$("#viewEmergencyAddresslayout5").show();
	$("#viewEmergencyAddresslayout2").show();
	$("#viewEmergencyAddresslayout3").hide();
	$("#viewEmergencyAddresslayout4").hide();
}
function EmergencyAddressshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewEmergencyAddressleft").css("width","430px");
	$("#viewEmergencyAddresslayout5").show();
	$("#viewEmergencyAddresslayout2").show();
	$("#viewEmergencyAddresslayout3").hide();
	$("#viewEmergencyAddresslayout4").hide();
}
function hiddenRightEmergencyAddress(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewEmergencyAddresslayout5").hide();
	$("#viewEmergencyAddresslayout2").show();
	$("#viewEmergencyAddresslayout3").hide();
	$("#viewEmergencyAddresslayout4").show();
}
$(function(){
	var aa=$("#localname").val();
	var bb=$("#localempid").val();
	var cc=$("#localpostgradenoname").val();
	var dd=$("#localpostgradeotherinf").val();
	var ff=$("#localcenter").val();
	var gg=$("#localempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename3').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleHrEmergencyAddress?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EMERGENCY_NO=${emergencyNo}','viewResumeList_viewEmergencyAddressunit');
});

function fangdajing3(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY3').val()));
	$('#fangda3').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEmergencyAddress&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda3').click();
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
			name="seach_KEY3" id="seach_KEY3" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing3('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda3"
			onclick="fangdajing3()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEmergencyAddress"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename3">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">
<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleHrEmergencyAddress?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EMERGENCY_NO=0','viewResumeList_viewEmergencyAddressunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span> </a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editHrEmergencyAddress',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a></li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editHrEmergencyAddress',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a></li>
	<!--<li>
				<a class="add" href="#" onclick="print()">
					<span><spring:message code="hrm.empinfo.PRINT" />打印</span></a>
	</li>
	--><li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=8&PERSON_ID=${PERSON_ID }">
			<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a>
	</li>
</ul>
</div>
<div id="viewResumeList_viewEmergencyAddressleft" sysLong='printDiv'
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="600px;">
	<thead>
		<tr>
			<th width="2%">No.</th>
			<th width="5%"><spring:message code="hrm.empinfo.FAM_NAME" /><!--姓名--></th>

			<th width="10%"><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" /><!--主要联络处与否--></th>
			<th width="10%"><spring:message code="hrm.empinfo.FAM_PHONE" /><!--联系电话--></th>

			<th width="10%"><spring:message code="hrm.empinfo.EMAIL" /><!--E-Mail--></th>
			<th width="10%"><spring:message code="hrm.empinfo.FAM_ADDRESS" /><!--地址--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${hrEmergencyAddressList}" var="item" varStatus="i">

			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleHrEmergencyAddress?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&EMERGENCY_NO=${item.EMERGENCY_NO }','viewResumeList_viewEmergencyAddressunit');">
				<td style="text-align: left">${i.count}</td>
				<td style="text-align: left">${item.EMER_NAME}</td>
				<td style="text-align: left"><input type="checkbox" disabled="disabled" <c:if test="${item.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input></td>
				<td style="text-align: left">${item.EMER_PHONE}</td>
				<td class='td_center'>${item.EMER_EMAIL}</td>
				<td class='td_center'>${item.EMER_ADDRESS }</td>
			</tr>

		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="localname" value="${title.LOCAL_NAME }">
<input type="hidden" id="localempid" value="${title.EMPID }"> <input
	type="hidden" id="localpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="localpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="localcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="localempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewEmergencyAddresslayout5" class="w-layout-collapse-left"
	onclick="hiddenRightEmergencyAddress('viewResumeList_viewEmergencyAddressunit','viewResumeList_viewEmergencyAddressleft')"></div>
<div id="viewEmergencyAddresslayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="EmergencyAddressshowIdLeft('viewResumeList_viewEmergencyAddressunit')"></div>
<div id="viewEmergencyAddresslayout2" class="w-layout-collapse-right"
	onclick="EmergencyAddresshiddenleft('viewResumeList_viewEmergencyAddressleft','viewResumeList_viewEmergencyAddressunit')"></div>
<div id="viewEmergencyAddresslayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=
	EmergencyAddressshowId('viewResumeList_viewEmergencyAddressleft');
></div>
</div>
<div id="viewResumeList_viewEmergencyAddressunit"
	style="display: block;"></div>
</div>