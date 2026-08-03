<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script>

$(document).ready(function(){
	$("#insertMacRecordList_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEntryInfoList",navTab.getCurrentPanel()).submit();
	});
	$('input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	$("#viewEntryInfoList",navTab.getCurrentPanel()).submit();
	    }
	});
});
function changeURL(obj) {

	//$("input[name='keleyicom']");

	obj.href = "viewEntryInfoList?CODE_NO=" + obj.name;

}

function exportExcle(a) {
	var $this = $(a);
	var title = $this.attr("title");
	var $from = $("#viewArVacationMonth");

	var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	alertMsg.confirm(title, {
		okCall : function() {
			window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
					+ $from.serialize();
		}
	});
}

function downloadExl(url) {
	$('#viewEntryInfoList').attr("action", url);
	$('#viewEntryInfoList').attr("onsubmit", '');
	$('#viewEntryInfoList').submit();
	$('#viewEntryInfoList').attr("action",
			'/ess/viewDept/viewEntryInfoList');
	$('#viewEntryInfoList')
			.attr("onsubmit", 'return navTabSearch(this)');
}
</script>
<script type="text/javascript"> 

	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEntryInfoList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
 	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEntryInfoList&seach_KEY='+name);
    }); 
	//判断是否有值
	var p =$(".pername");
	var seach_KEY =$("[name='seach_KEY']");
	if(p != null){
		seach_KEY.val(p.val());
	}

</script>
<script>
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 310,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
        	"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
            "sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！
            "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
            "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
            "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
            "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
            "sInfoFiltered": "(<spring:message code='hrm.alert.contractInfo.Record_filter'/>)",//从 _MAX_ 条记录过滤
            "oPaginate": {
                "sPrevious": '<spring:message code="hrm.alert.contractInfo.Previous_page"/>',
                "sNext": '<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>'
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [
              ] 
	});
});

function insertMacRecordList(form,callback,flag){
    var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
		
    var STIME = $("#seach_STIME").val();
    STIME_FORMAT = STIME.substring(6,10)+"-"+STIME.substring(3,5)+"-"+STIME.substring(0,2);
	var RTIME = $("#seach_ETIME").val();
	RTIME_FORMAT = RTIME.substring(6,10)+"-"+RTIME.substring(3,5)+"-"+RTIME.substring(0,2);
    $.ajax({
		type: form.method || 'POST',
		url: "/ar/attendanceMintenance/insertMacRecordListLGE?STIME="+STIME_FORMAT+"&RTIME="+RTIME_FORMAT,
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("viewEntryInfoList");
				alertMsg.correct(data.message);
			}else{
				if(data.result=="2"){
					alertMsg.info(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}   
   	 	}  ,
		error: DWZ.ajaxError
	});
}
</script>
<a id="importExcel_ar0104"  href="#" target="navTab" mask="true"><span style="display:none;"><spring:message code="ess.infoApply.cardinsertmodify" /></span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewEntryInfoList"  method="post"
		id="viewEntryInfoList" name="viewEntryInfoList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />

						<input type="hidden" value="${LoginUser.adminID}" name="adminId">
						<input type="hidden" value="${LoginUser.cpnyId}" name="interCpnyID">
					</td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<%-- <div style="float:left"><a class="btnLook" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEntryInfoList" lookupGroup="person"></a></div>
						
						<c:if test="${not empty personInfo}">
							${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }
							<input type="hidden" name="pername" class="pername" value="${personInfo.LOCAL_NAME}">
						</c:if>
					 --%>
					</td>
				</tr>
				<tr>
					<td>
						<!--期间 -->
						<spring:message code="ess.infoApply.Period" />
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${STIME}" />~
						<input type="text" id="seach_ETIME" name="seach_ETIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${ETIME}" />
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<%-- <ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewEntryInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewEntryInfoList_seachDept" selected="${DEPTNO}" /> --%>
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPT_NO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
					<td><!-- 职级 --><spring:message code="sys.postManage.title.postGrade" /></td>
					<td>
					 <ait:SelectSyCodeCombinByCpnyID name="seach_POST_GRADE_NO" combinParentNo="14015813,14015815" 
											cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${POST_GRADE_NO }"/>
					</td>
					<td ><!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/> </td>
					<td >
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${shiftList}" var="result">
								<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td><!--缺卡状态--><spring:message code="ar.viewArCardRecordDay.QUEKAZHUANGTAI.b" /></td>
					<td>
						 <select id="seach_CARD_FLAG" name="seach_CARD_FLAG">
						      <option value="" <c:if test="${CARD_FLAG eq null || CARD_FLAG eq ''}" >selected</c:if>><!--全部--><spring:message code="org.title.ALL" /></option>
						      <option value="1" <c:if test="${CARD_FLAG eq '1'}" >selected</c:if>><!--缺失上班卡--><spring:message code="ar.viewArCardRecordDay.QUESHISHANGBANKA.b" /></option>
						      <option value="2" <c:if test="${CARD_FLAG eq '2'}" >selected</c:if>><!--缺失下班卡--><spring:message code="ar.viewArCardRecordDay.QUESHIXIABANKA.b" /></option>
						      <option value="3" <c:if test="${CARD_FLAG eq '3'}" >selected</c:if>><!--全部缺失--><spring:message code="ar.viewArCardRecordDay.QUANBUQUESHI.b" /></option>
						      <option value="4" <c:if test="${CARD_FLAG eq '4'}" >selected</c:if>><!--不缺卡--><spring:message code="ar.viewArCardRecordDay.BUQUEKA.b" /></option>
						 </select>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			<c:if test="${toolbarInfo.INSERTR eq '1'}">
				<li>	            
					<a class="buttonActive" id="insertMacRecordList" onclick="insertMacRecordList('viewEntryInfoList',DWZ.ajaxDone,'1')" href="#"><span><spring:message code="ess.infoApply.getcardinfo" /><!-- 读取刷卡记录 --></span></a> 
				</li>		
			</c:if>
			<li>	            
				<a class="buttonActive" id="insertMacRecordList_search" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a> 
			</li>	
			<!--<c:if test="${toolbarInfo.INSERTR eq '1'}">
				<li>
					<a href="/ess/infoApplyLeave/downloadFile?fileName=/resources/template/cardTemplate.xls&file=cardTemplate.xls"><span> 下载导入模板 <spring:message code="ar.addempshift.title.downloadmodule"/></span></a>
				</li>
				<li>
					<a class="buttonActive" href="/pa/excelImport/importExcelData?&importFunName=/importArCardRecordExcel" target="dialog" mask="true" width="400" height="200" ><span> EXCEL导入 <spring:message code="ar.addempshift.title.excelimport"/></span></a>
				</li>
			</c:if>-->
			<li><a class="buttonActive" onclick="downloadExcel('viewEntryInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=148','/ess/viewDept/viewEntryInfoList')"><span><spring:message code="ar.addempshift.title.excelexport"/><!-- 导出到Excel --></span></a></li>
	</ul>
</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="viewEntryInfoList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
		target="dialog">
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th width="2%">NO</th>
					<th width="6%"><spring:message code="org.title.EMPID" /><!-- 社号 --></th>
					<th width="12%"><!--姓名 --> <spring:message code="org.title.LOCAL_NAME" /></th>
					<th width="9%">TEAM</th>
					<th width="13%"><!--部门 --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></th>
					<th width="8%"><!-- 职级 --><spring:message code="ess.infoApply.Rank" /></th>
					<th width="8%"><spring:message code="org.title.DATE" /><!-- 日期 --></th>
					<th width="10%"><spring:message	code="ar.viewarcardrecord.title.jinmen" /><!-- 进门 --></th>
					<th width="10%"><spring:message	code="ar.viewarcardrecord.title.chumen" /><!-- 出门 --></th>
					<th width="15%"><spring:message	code="ar.viewarcardrecord.title.beizhu" /> <!-- 备注 --></th>
					<th width="15%"><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></th>
				</tr>

			</thead>
			<tbody>
				<c:forEach items="${viewEntryInfoList}" var="personList"
					varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">

							${i.count}
						</td>
						<td style="text-align: center">${personList.EMPID}</td>
						<td style="text-align: center">${personList.LOCAL_NAME}</td>
						<td style="text-align: center">${personList.DEPT_TEAM}</td>
						<td style="text-align: center">${personList.DEPTNAME}</td>
						<td style="text-align: center">${personList.POST_GRADE_NO_NAME}</td>
						<td style="text-align: center">${personList.AR_DATE_STR}</td>
						<td style="text-align: center">${personList.ENTRYTIME}</td>
						<td style="text-align: center">${personList.OUTTIME}</td>
						<td style="text-align: center">${personList.LEAVE_CONTENT} ${personList.REMAX}</td>
						<td style="text-align: center">${personList.SHIFT_NAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
