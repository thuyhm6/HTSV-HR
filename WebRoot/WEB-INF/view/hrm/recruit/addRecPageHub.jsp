<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function hiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#RecPagelayout3").show();
	$("#RecPagelayout5").hide();
	$("#RecPagelayout2").hide();
	$("#RecPagelayout4").hide();
}
function showId(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewRecPageleft").css("width","430px");
	$("#RecPagelayout5").show();
	$("#RecPagelayout2").show();
	$("#RecPagelayout3").hide();
	$("#RecPagelayout4").hide();
}
function RecPageRecPagehiddenleft(showId){
	$("#" + showId).show();
	$("#viewResumeList_viewRecPageleft").css("width","430px");
	$("#RecPagelayout5").show();
	$("#RecPagelayout2").show();
	$("#RecPagelayout3").hide();
	$("#RecPagelayout4").hide();
}
function RecPagehiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#RecPagelayout5").hide();
	$("#RecPagelayout2").show();
	$("#RecPagelayout3").hide();
	$("#RecPagelayout4").show();
}

function uploadAttDialogPhoto(id,val,seq,applyType){
	$.pdialog.open("/sys/notice/uploadWindowPhoto?id=" + id + "&val=" + val
			+ "&seq=" + seq + "&applyType=" + applyType, "uploadWindowPhoto", 
			"<spring:message code="hrm.alert.empinfo.upload_Enclosure" />", {width:550,height:320,mask:true});//附件上传
}

$(function(){
	openOnRight('/hrm/recruit/addSingleRecPageHub?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${rec_employee_no}','viewResumeList_viewRecPageInfo');
	
});

function displayAlert(name){
	alert(name+' <spring:message code="hrm.empinfo.haveNotCV"/>');
}

$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": true,//关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": false,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"columnDefs": [//自定义排序类型
                   { "orderable": false, "targets": [0,1,2] }
               ],
	"scrollY": $(document.body).height() - 270,
    "scrollX": true,
    "orderClasses": false,
    "oLanguage": {//多语言配置
    	"sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
        "oPaginate": {
            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
        }
    }
});

$("#viewAddRecPageHub_srarch",navTab.getCurrentPanel()).click(function(){
	$("#viewAddRecPageHub_Form",navTab.getCurrentPanel()).submit();
});
</script>
<div id="viewAddRecPageHub">
<div class="pageHeader" >
<form id="viewAddRecPageHub_Form" onsubmit="return navTabSearch(this);" action="/hrm/recruit/addRecPageHub" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				    <!-- 姓名： --> <spring:message code="alert.pa.pasalarycanshu.xingming" />
				</td>
				<td>
				    <input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
				</td>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<td>
				    <!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" />
				</td>
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_POST_TYPE_CODE" selected="${POST_TYPE_CODE}" parentNo="90000339" limit="all"/>
				</td>
				</c:if>
				<td>
				    <!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />  
				</td>
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_FINAL_EDU_CODE" selected="${FINAL_EDU_CODE}" parentNo="13769" limit="all"/>
				</td>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<td>
				    <!--  外语能力--><spring:message code="hrm.empinfo.Foreign_language_ability" />  
				</td>
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_LANGUAGE_ABILITY" selected="${LANGUAGE_ABILITY}" parentNo="14015514" limit="all"/>
				</td>
				</c:if>
			</tr>
		</table>
	</div>
</form>
</div>

<div class="pageContent">
<div class="formBar">

	<ul class="toolBar">
		<!--<c:if test="${toolbarInfo.INSERTR == '1'}"></c:if>-->
		    <li>
				<a class="buttonActive" class="edit" id="viewAddRecPageHub_srarch"
						href="#"><span><!-- 查询 --><spring:message code="button.search" /></span></a>					
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/recruit/addSingleRecPageHub?pageNum=1&menuNo=2540&navTabId=rec0101&addFlag=1','viewResumeList_viewRecPageInfo');" href="#">
					<span><!-- 添加 --> <spring:message code="button.add" /></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('editRegPage',navTabAjaxDone)" href="#"><span><!-- 删除 --><spring:message code="button.delete" /></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editRegPage',navTabAjaxDone)" href="#"><span><!-- 保存 --><spring:message code="button.sys.affirm.save" /></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/pa/excelExport/downloadExcelAddRecTemplate?file=AddBatch_RecPage">
					<span><spring:message code="ar.addempshift.title.downloadmodule" /><!-- 下载导入模板 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive"  target="dialog" href="/pa/excelImport/importExcelData?importFunName=/importRecPageTemp">
					<span><spring:message code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span>
				</a>					
			</li>
			<li>
			<a class="buttonActive" id="fileupload" href="#" onclick="uploadAttDialogPhoto('viewAddRecPageHub','/hrm/recruit/addRecPageHub?UPLOAD_STATUS=1','upload_CV','upload_CV')">
				<span><spring:message code="hrm.empinfo.uploadCV" /><!-- 寻找 --></span></a>
			</li>
	</ul>
</div>
	<div id="viewResumeList_viewRecPageleft" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:${totalcount }
		<table class="list" width="1000px">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="8%"><!-- 姓名： --> <spring:message code="alert.pa.pasalarycanshu.xingming" /></th>
					<th width="8%"><!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" /></th>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th width="6%"><!--  招聘时间--><spring:message code="hrm.addRecPage.interviewPeriod.k" /></th>
					<th width="3%"><!--  工作经验--><spring:message code="hrm.addRecPage.workExperience.k" /></th>
					<th width="8%"><!--  工作单位--><spring:message code="hr.viewWorkInfo.title.CPNY_NAME" /></th>
					<th width="6%"><!--  导入日期--><spring:message code="sys.basic.title.createDate" /></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewRecPageList}" var="item" varStatus="i">
				<c:if test="${UPLOAD_STATUS eq 1 }">
					<c:if test="${item.FILE_NAME eq '' || item.FILE_NAME eq null}">
						<script type="text/javascript">
							$(function (){
								displayAlert('${item.EMP_NAME}');
							});
						</script>
					</c:if>
				</c:if>
						<tr onclick="openOnRight('/hrm/recruit/addSingleRecPageHub?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${item.REC_EMPLOYEE_NO}','viewResumeList_viewRecPageInfo');">
						<td style="text-align:center">${i.index+1}</td>
						<td style="text-align:center">${item.EMP_NAME}</td>
						<td style="text-align:center">${item.FINAL_EDU_NAME}</td>
						<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td style="text-align:center">${item.INTERVIEW_PERIOD}</td>
						<td style="text-align:center">${item.WORK_EXPERIENCE}</td>
						<td style="text-align:center">${item.OLD_COMPANY}</td>
						<td style="text-align:center">${item.CREATE_DATE}</td>
						</c:if>
					</tr>
				</c:forEach>
				        
			</tbody>
		</table>
		
		<%@ include file="/WEB-INF/view/inc/initPagination11.jsp"%>
	</div>
	<div class="w-layout-collapse">
		<div id="RecPagelayout5" class="w-layout-collapse-left" onclick="RecPagehiddenRight('viewResumeList_viewRecPageInfo','viewResumeList_viewRecPageleft')"></div>
		<div id="RecPagelayout4" class="w-layout-collapse-right" style="display:none;" onclick="RecPageRecPagehiddenleft('viewResumeList_viewRecPageInfo')"></div>
		<div id="RecPagelayout2" class="w-layout-collapse-right" onclick="hiddenleft('viewResumeList_viewRecPageleft','viewResumeList_viewRecPageInfo')"></div>
		<div id="RecPagelayout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewResumeList_viewRecPageleft')"></div>
	</div>
	<div id="viewResumeList_viewRecPageInfo"  style="display:block;">
	</div>
</div>
</div>