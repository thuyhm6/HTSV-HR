<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function AddressMattershiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#layout3viewAddressMatters").show();
	$("#layout5viewAddressMatters").hide();
	$("#layout2viewAddressMatters").hide();
	$("#layout4viewAddressMatters").hide();
}
function AddressMattersshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewAddressMattersleft").css("width","430px");
	$("#layout5viewAddressMatters").show();
	$("#layout2viewAddressMatters").show();
	$("#layout3viewAddressMatters").hide();
	$("#layout4viewAddressMatters").hide();
}
function AddressMattersshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewAddressMattersleft").css("width","430px");
	$("#layout5viewAddressMatters").show();
	$("#layout2viewAddressMatters").show();
	$("#layout3viewAddressMatters").hide();
	$("#layout4viewAddressMatters").hide();
}
function AddressMattershiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#layout5viewAddressMatters").hide();
	$("#layout2viewAddressMatters").show();
	$("#layout3viewAddressMatters").hide();
	$("#layout4viewAddressMatters").show();
}
$(function(){
	var aa=$("#Addlocalname").val();
	var bb=$("#Addlocalempid").val();
	var cc=$("#Addlocalpostgradenoname").val();
	var dd=$("#Addlocalpostgradeotherinf").val();
	var ff=$("#Addlocalcenter").val();
	var gg=$("#Addlocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename4').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleAddressMatters?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&ADDRESS_NO=${addressNo}','viewResumeList_viewAddressMattersunit');
});

function fangdajing4(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY4').val()));
	$('#fangda4').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewAddressMatters&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda4').click();
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
			type="text" name="seach_KEY4" id="seach_KEY4" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing4('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda4" onclick="fangdajing4()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewAddressMatters" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename4">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSingleAddressMatters?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&ADDRESS_NO=0','viewResumeList_viewAddressMattersunit');" href="#">
					<span><spring:message code="button.add" /><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editHrAddressMatters',navTabAjaxDone)" href="#"><span><spring:message code="button.delete" /><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editHrAddressMatters',navTabAjaxDone)" href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
			</li>
			<!-- <li>
				<a class="add" href="#" onclick="window.print()"><span>打印</span></a>
			</li> -->
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=9&PERSON_ID=${PERSON_ID }"><span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewAddressMattersleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="5%"><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" /><!--地址类型--></th>
					<th width="5%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址--></th>
					<th width="5%"><spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" /><!--有效开始日--></th>
					<th width="5%"><spring:message code="org.title.UPDATED_IP" /><!--变更者--></th>
					<th width="5%"><spring:message code="org.title.UPDATE_DATE" /><!--变更时间--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewAddressMattersList}" var="item" varStatus="i">
		
						<tr onclick="openOnRight('/hrm/empinfo/viewSingleAddressMatters?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&ADDRESS_NO=${item.ADDRESS_NO }','viewResumeList_viewAddressMattersunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.ADDRESS_TYPE_NAME}</td>
						<td style="text-align:left">${item.ADDRESS_CONTENT}</td>
						<td style="text-align:left">${item.EFFECTIVE_START_DATE}</td>
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
		            <input type="hidden" id="Addlocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Addlocalempid" value="${title.EMPID }">
					<input type="hidden" id="Addlocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Addlocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Addlocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Addlocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5viewAddressMatters" class="w-layout-collapse-left" onclick="AddressMattershiddenRight('viewResumeList_viewAddressMattersunit','viewResumeList_viewAddressMattersleft')"></div>
		<div id="layout4viewAddressMatters" class="w-layout-collapse-right" style="display:none;" onclick="AddressMattersshowIdLeft('viewResumeList_viewAddressMattersunit')"></div>
		<div id="layout2viewAddressMatters" class="w-layout-collapse-right" onclick="AddressMattershiddenleft('viewResumeList_viewAddressMattersleft','viewResumeList_viewAddressMattersunit')"></div>
		<div id="layout3viewAddressMatters" class="w-layout-collapse-left" style="display:none;" onclick="AddressMattersshowId('viewResumeList_viewAddressMattersleft')"></div>
	</div>
	<div id="viewResumeList_viewAddressMattersunit"  style="display:block;">
	</div>
</div>