<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function viewFamilyhiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#layout3viewFamily").show();
	$("#layout5viewFamily").hide();
	$("#layout2viewFamily").hide();
	$("#layout4viewFamily").hide();
}
function viewFamilyshowId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewFamilyleft").css("width","430px");
	$("#layout5viewFamily").show();
	$("#layout2viewFamily").show();
	$("#layout3viewFamily").hide();
	$("#layout4viewFamily").hide();
}
function viewFamilyshowIdLeft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewFamilyleft").css("width","430px");
	$("#layout5viewFamily").show();
	$("#layout2viewFamily").show();
	$("#layout3viewFamily").hide();
	$("#layout4viewFamily").hide();
}
function viewFamilyhiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#layout5viewFamily").hide();
	$("#layout2viewFamily").show();
	$("#layout3viewFamily").hide();
	$("#layout4viewFamily").show();
}
$(function(){
	var aa=$("#Familylocalname").val();
	var bb=$("#Familylocalempid").val();
	var cc=$("#Familylocalpostgradenoname").val();
	var dd=$("#Familylocalpostgradeotherinf").val();
	var ff=$("#Familylocalcenter").val();
	var gg=$("#Familylocalempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename5').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	openOnRight('/hrm/empinfo/viewSingleFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&FAMILY_NO=${familyNo}','viewResumeList_viewFamilyunit');
});

function fangdajing5(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY5').val()));
	$('#fangda5').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewFamily&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda5').click();
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
			type="text" name="seach_KEY5" id="seach_KEY5" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing5('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda5" onclick="fangdajing5()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewFamily" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename5">${LOCAL_TITLE }</span>
		</td>
	</tr>
</table>
<div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSingleFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&FAMILY_NO=0','viewResumeList_viewFamilyunit');" href="#">
					<span><spring:message code="button.add" /><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editHrFamily',navTabAjaxDone)" href="#">
					<span><spring:message code="button.delete" /><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editHrFamily',navTabAjaxDone)" href="#">
					<span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a>
			</li>
			<!--<li>
				<a class="add" href="#" onclick="window.print()">
					<span><spring:message code="hrm.empinfo.PRINT" />打印</span></a>
			</li>-->
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=10&PERSON_ID=${PERSON_ID }">
					<span><spring:message code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewFamilyleft" style="float:left; display:block; overflow:auto;width:430px; height:430px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="600px;">
			<thead>
				<tr>
					<th >No.</th>
					<th ><spring:message code="hrm.empinfo.FAM_NAME" /><!--姓名--></th>
					<th ><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系--></th>
					<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
					<th ><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机号码--></th>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<th><spring:message code="hr.viewRelation.title.FAM_PHONE" /><!-- 联系电话 --></th>
					</c:if>
					
					<th ><spring:message code="hrm.empinfo.FAM_COMPANY_NAME" /> <!--工作单位--></th>
					<th ><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></th>
					<th ><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewFamilyList}" var="item" varStatus="i">
		
						<tr onclick="openOnRight('/hrm/empinfo/viewSingleFamily?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}&FAMILY_NO=${item.FAMILY_NO }','viewResumeList_viewFamilyunit');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.FAM_NAME}</td>
						<td style="text-align:left">${item.FAM_TYPE_CODE_NAME}</td>
						<td style="text-align:left">${item.FAM_PHONE}</td>
						<td style="text-align:left">${item.FAM_COMPANY_NAME}</td>
						<c:if test="${not empty item.UPDATED_BY}">
						<td style="text-align:left">${item.UPDATED_BY }&nbsp;&nbsp;${item.UPDATED_IP }</td>
						<td style="text-align:left">${item.UPDATE_DATE}</td>
						</c:if>
						<c:if test="${empty item.UPDATED_BY}">
						<td style="text-align:left">${item.CREATED_BY }&nbsp;&nbsp;${item.CREATED_IP }</td>
						<td style="text-align:left">${item.CREATE_DATE }</td>
						</c:if>
					</tr>
					
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="Familylocalname" value="${title.LOCAL_NAME }">
					<input type="hidden" id="Familylocalempid" value="${title.EMPID }">
					<input type="hidden" id="Familylocalpostgradenoname" value="${title.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="Familylocalpostgradeotherinf" value="${title.RANK_STATISTICS_NAME }">
					<input type="hidden" id="Familylocalcenter" value="${title.COST_CENTER_TITLE }">
					<input type="hidden" id="Familylocalempofficename" value="${title.EMP_OFFICE_NAME_TITLE }">
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5viewFamily" class="w-layout-collapse-left" onclick="viewFamilyhiddenRight('viewResumeList_viewFamilyunit','viewResumeList_viewFamilyleft')"></div>
		<div id="layout4viewFamily" class="w-layout-collapse-right" style="display:none;" onclick="viewFamilyshowIdLeft('viewResumeList_viewFamilyunit')"></div>
		<div id="layout2viewFamily" class="w-layout-collapse-right" onclick="viewFamilyhiddenleft('viewResumeList_viewFamilyleft','viewResumeList_viewFamilyunit')"></div>
		<div id="layout3viewFamily" class="w-layout-collapse-left" style="display:none;" onclick="viewFamilyshowId('viewResumeList_viewFamilyleft')"></div>
	</div>
	<div id="viewResumeList_viewFamilyunit"  style="display:block;">
	</div>
</div>