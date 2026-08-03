<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function BidMatterhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#viewBidMatterlayout3").show();
	$("#viewBidMatterlayout5").hide();
	$("#viewBidMatterlayout2").hide();
	$("#viewBidMatterlayout4").hide();
}
function BidMattershowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewBidMatterleft").css("width","430px");
	$("#viewBidMatterlayout5").show();
	$("#viewBidMatterlayout2").show();
	$("#viewBidMatterlayout3").hide();
	$("#viewBidMatterlayout4").hide();
}
function BidMattershowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewBidMatterleft").css("width","430px");
	$("#viewBidMatterlayout5").show();
	$("#viewBidMatterlayout2").show();
	$("#viewBidMatterlayout3").hide();
	$("#viewBidMatterlayout4").hide();
}
function BidMatterhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#viewBidMatterlayout5").hide();
	$("#viewBidMatterlayout2").show();
	$("#viewBidMatterlayout3").hide();
	$("#viewBidMatterlayout4").show();
}
$(function(){
	var aa=$("#Bidlocalname").val();
	var bb=$("#Bidlocalempid").val();
	var cc=$("#Bidlocalpostgradenoname").val();
	var dd=$("#Bidlocalpostgradeotherinf").val();
	var ff=$("#Bidlocalcenter").val();
	var gg=$("#Bidlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename9').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleBidMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&QUAL_NO=${qualNo }','viewResumeList_viewBidMatterunit');
});

function fangdajing9(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY9').val()));
	$('#fangda9').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewBidMatter&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda9').click();
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
			name="seach_KEY9" id="seach_KEY9" value="${KEY}"
			onkeydown="javascript:if(event.keyCode == 13)fangdajing9('onkeyup');" />
		</td>
		<td class="td_type"><a class="btnLook" id="fangda9"
			onclick="fangdajing9()"
			href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewBidMatter"
			lookupGroup="person"> </a> <span style="margin-left: 50px;"
			id="titlename9">${LOCAL_TITLE }</span></td>
	</tr>
</table>
<div class="formBar">

<ul class="toolBar">
	<c:if test="${toolbarInfo.INSERTR == '1'}">
	</c:if>
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleBidMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&QUAL_NO=0','viewResumeList_viewBidMatterunit');"
		href="#"> <span><spring:message code="button.add" /><!--添加--></span>
	</a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editBidMatter',navTabAjaxDone)"
		href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
	</li>
	<li><a class="buttonActive"
		onclick="validateAddResumeInfoCallback('editBidMatter',navTabAjaxDone)"
		href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
	</li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=19&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
</ul>
</div>
<div id="viewResumeList_viewBidMatterleft"
	style="float: left; display: block; overflow: auto; width: 430px; height: 430px; border: solid 1px #CCC; line-height: 21px; background: #fff">
Total:${totalcount }
<table class="list" width="1200px;">
	<thead>
		<tr>
			<th width="1%">No.</th>
			<th width="3%"><spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--资格名称--></th>
			<th width="3%"><spring:message code="hrm.empinfo.Qualification_grade" /><!--资格等级--></th>
			<th width="3%"><spring:message code="hrm.empinfo.award_date" /><!--获证日期--></th>
			<th width="3%"><spring:message code="hrm.empinfo.Issuing_authority" /><!--发证机关--></th>
			<th width="3%"><spring:message code="hrm.empinfo.Valid_date" /><!--有效日期--></th>
			<th width="3%"><spring:message code="hrm.empinfo.Certificate_number" /><!--证书编号--></th>
			<th width="3%"><spring:message code="hrm.empinfo.REMARK" /><!--备注--></th>
			<th width="3%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></th>
			<th width="3%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewBidMatter}" var="item" varStatus="i">
			<tr
				onclick="openOnRight('/hrm/empinfo/viewSingleBidMatter?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&QUAL_NO=${item.QUAL_NO }','viewResumeList_viewBidMatterunit');">
				<td style="text-align: left">${i.count}</td>
				<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
				<td style="text-align: left">${item.QUAL_NAME}</td>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
				<td style="text-align: left">${item.QUAL_NAME_CODE}</td>
				</c:if>
				<td style="text-align: left">${item.QUAL_LEVEL}</td>
				
				<td style="text-align: left">${item.VALIDITY_DATE }</td>
				<td style="text-align: left">${item.QUAL_INSTITUTE }</td>
				<td style="text-align: left">${item.VALIDITY_DATE }</td>
				<td style="text-align: left">${item.QUAL_CARD_NO }</td>
				<td style="text-align: left">${item.QUAL_REMARK }</td>
				<c:if test="${not empty item.UPDATED_BY}">
					<td style="text-align: left">${item.UPDATED_BY
					}&nbsp&nbsp${item.UPDATED_IP }</td>
					<td style="text-align: left">${item.UPDATE_DATE}</td>
				</c:if>
				<c:if test="${empty item.UPDATED_BY}">
					<td style="text-align: left">${item.CREATED_BY
					}&nbsp&nbsp${item.CREATED_IP }</td>
					<td style="text-align: left">${item.CREATE_DATE }</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="Bidlocalname" value="${title.LOCAL_NAME }">
<input type="hidden" id="Bidlocalempid" value="${title.EMPID }">
<input type="hidden" id="Bidlocalpostgradenoname"
	value="${title.POST_GRADE_NO_NAME_TITLE }"> <input
	type="hidden" id="Bidlocalpostgradeotherinf"
	value="${title.RANK_STATISTICS_NAME }"> <input type="hidden"
	id="Bidlocalcenter" value="${title.COST_CENTER_TITLE }"> <input
	type="hidden" id="Bidlocalempofficename"
	value="${title.EMP_OFFICE_NAME_TITLE }"> <%@ include
	file="/WEB-INF/view/inc/initPagination11.jsp"%>
</div>
<div class="w-layout-collapse">
<div id="viewBidMatterlayout5" class="w-layout-collapse-left"
	onclick="BidMatterhiddenRight('viewResumeList_viewBidMatterunit','viewResumeList_viewBidMatterleft')"></div>
<div id="viewBidMatterlayout4" class="w-layout-collapse-right"
	style="display: none;"
	onclick="BidMattershowIdLeft('viewResumeList_viewBidMatterunit')"></div>
<div id="viewBidMatterlayout2" class="w-layout-collapse-right"
	onclick="BidMatterhiddenleft('viewResumeList_viewBidMatterleft','viewResumeList_viewBidMatterunit')"></div>
<div id="viewBidMatterlayout3" class="w-layout-collapse-left"
	style="display: none;"
	onclick=
	BidMattershowId('viewResumeList_viewBidMatterleft');
></div>
</div>
<div id="viewResumeList_viewBidMatterunit" style="display: block;">
</div>
</div>