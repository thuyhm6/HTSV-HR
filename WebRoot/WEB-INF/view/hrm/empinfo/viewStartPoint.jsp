<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/hrm/empinfo/viewSingleStartPoint?PERSON_ID=${PERSON_ID}&SEQ=${SEQ }','viewStartPoint_unit');
});
function fangdajing6(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY6',navTab.getCurrentPanel()).val()));
	$('#fangda6',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=hr0204&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda6',navTab.getCurrentPanel()).click();
}
$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,//关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": $(document.body).height() - 230,
    "scrollX": true,
    "orderClasses": false
});
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }
	      }  
	 }  
};
</script>
<div class="pageHeader">
	<form id="editStartPoint_searchForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewStartPoint?PERSON_ID=${personInfo.PERSON_ID}" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY6" id="seach_KEY6" value="${personInfo.LOCAL_NAME}" onkeydown="javascript:if(event.keyCode == 13)fangdajing6('onkeyup');" /></div>
						<div style="float:left"><a class="btnLook" id="fangda6" onclick="fangdajing6()" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=hr0204" lookupGroup="person"></a></div>
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td>
						<input type="button" onclick="fangdajing6('onkeyup')" value="<spring:message code="button.search"/>">
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
<div class="formBar">
<ul class="toolBar">
	<li><a class="buttonActive"
		onclick="openOnRight('/hrm/empinfo/viewSingleStartPoint?PERSON_ID=${PERSON_ID}&SEQ=0','viewStartPoint_unit');"
		href="#"> <span><spring:message
		code="button.add" /><!-- 添加 --></span> </a></li>
	<li><a class="buttonActive"
		onclick="validateDeleteResumeInfoCallback('editStartPoint',navTabAjaxDoneWithForm)"
		href="#"><span><spring:message
		code="button.delete" /><!-- 删除 --></span></a></li>
	<li><a class="buttonActive"
		onclick="validateAddPointInfoCallback('editStartPoint',navTabAjaxDoneWithForm)"
		href="#"><span><spring:message
		code="button.sys.affirm.save" /><!-- 保存 --></span></a></li>
	<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=13&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!-- 导出到EXECL --></span></a></li>
</ul>
</div>
	<div id="viewStartPoint_left"
		style="float: left; display: block; overflow: auto; width: 430px; height: auto; border: solid 1px #CCC; line-height: 21px; background: #fff">
	Total:${totalcount }
	<table class="list" width="800px">
		<thead>
			<tr>
				<th width="3%">No.</th>
				<th width="10%"><spring:message code="hrm.empinfo.Command_date" /><!-- 发令日期 --></th>
				<th width="12%"><spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令类型 --></th>
				<th width="12%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></th>
				<th width="12%"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 --></th>
				<th width="12%"><spring:message code="hrm.contract.Rank" /><!-- 职级 --></th>
				<th width="12%"><spring:message code="hrm.empinfo.Working_status" /><!-- 员工状态 --></th>
				<th width="12%"><spring:message code="hrm.empinfo.ORDER_STATUS.Z" /><!-- 发令状态 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewStartPointList}" var="item" varStatus="i">
				<tr onclick="openOnRight('/hrm/empinfo/viewSingleStartPoint?PERSON_ID=${item.PERSON_ID}&SEQ=${item.SEQ }','viewStartPoint_unit');" sysIndex="${i.index }" sysSeq="${item.SEQ }">
					<td style="text-align: left">${i.count}</td>
					<td style="text-align: left">${item.START_DATE}</td>
					<td style="text-align: left">${item.TRANS_CODE_NAME}</td>
					<td style="text-align: left">${item.DEPTNAME}</td>
					<td style="text-align: left">${item.MAIN_BUSINESS_NAME}</td>
					<td style="text-align: left">${item.POST_GRADE_NO_NAME}</td>
					<td style="text-align: left">${item.EMP_OFFICE_NAME}</td>
					<td style="text-align: left">${item.ZHUANGTAI}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewStartPoint_unit','viewStartPoint_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewStartPoint_unit','viewStartPoint_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewStartPoint_left','viewStartPoint_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewStartPoint_left')"></div>
	</div>
	<div id="viewStartPoint_unit" style="display: block;">
	</div>
</div>