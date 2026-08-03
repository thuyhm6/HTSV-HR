<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
	$(".list",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 320,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
	    "oLanguage": {//多语言配置
	        //正在加载中......
	    	"sProcessing": "<spring:message code='ess.message.loading' />",
	    	//查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
	        "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
	        "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
	        "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
	        //(从 _MAX_ 条记录过滤)
	        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
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
function changeURL(obj) {

    var  STIMESS = $("#seach_STIME",navTab.getCurrentPanel()).val();
    STIMESS = STIMESS.substring(6,10)+ "/" +STIMESS.substring(3,5)+ "/" +STIMESS.substring(0,2);
    
    var  ETIMESS = $("#seach_ETIME",navTab.getCurrentPanel()).val();
    ETIMESS = ETIMESS.substring(6,10)+ "/" +ETIMESS.substring(3,5)+ "/" +ETIMESS.substring(0,2);

	$.pdialog.open("/ess/viewDept/viewOtApplySingleList?ITEM_NO=" + obj.name
					+ "&PERSON_ID=" + obj.type + "&STIME="
					+ STIMESS + "&ETIME=" + ETIMESS, "addAffirmWindow", obj.title, {width:800,height:400,mask:true});
}

function changeURLForAllow(obj) {

	//$("input[name='keleyicom']");
    var  STIMESS = $("#seach_STIME",navTab.getCurrentPanel()).val();
    var  ETIMESS = $("#seach_ETIME",navTab.getCurrentPanel()).val();
    var  AFFIRM_FLAG = $("#AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	obj.href = "/ess/viewDept/viewAllowanceSingleList?PERSON_ID=" + obj.type + "&STIME="
			+ STIMESS + "&ETIME="
			+ ETIMESS ;

}

function downloadExl(url) {
	$('#viewOtApplyPersonalList').attr("action", url);
	$('#viewOtApplyPersonalList').attr("onsubmit", '');
	$('#viewOtApplyPersonalList').submit();
	$('#viewOtApplyPersonalList').attr("action",
			'/ess/viewDept/viewOtApplyPersonalList');
	$('#viewOtApplyPersonalList')
			.attr("onsubmit", 'return navTabSearch(this)');
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
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewOtApplyPersonalList"
		method="post" id="viewOtApplyPersonalList"
		name="viewOtApplyPersonalList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>

<input type="hidden" value="${LoginUser.adminID}" name="adminId">

					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewOTApplyLeaveInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewOTApplyLeaveInfoList_seachDept"
							selected="${DEPT_NO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />

        


					</td>
					<td width="10%"><!--员工类型--><spring:message code="ess.infoApply.employee_type" /></td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
				<tr>
					<td>
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" />
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME"
						value="${FROM_DATE}" 	class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${FROM_DATE }" 
							/>
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td>
						<input type="text" id="seach_ETIME" name="seach_ETIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${TO_DATE}" />
					</td>
					<td>
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="seach_SHIFT_NO"
							parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					<!--<td>
						审批状态
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG"
							id="AFFIRM_FLAG" parentNo="14014304" cnpyID="${LoginUser.cpnyId}"
							selected="${AFFIRM_FLAG}" limit="all" />
					</td>-->
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">

			<li>
				<a class="buttonActive" onclick="downloadExcel('viewOtApplyPersonalList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=147&CPNY=${LoginUser.cpnyId}','/ess/viewDept/viewOtApplyPersonalList')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span></a>
			</li>

		</ul>
	</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="/ess/viewDept/viewArPersonalSingleList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
		target="dialog">
		<table class="list"  border="1" width="100%" nowrapTD="false">
			<thead>
				<tr>
					<th>
						NO
					</th>
					<th>
						<!--工号 --><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th>
						<!--姓名 --><spring:message code="public.title.empName" />
					</th>
					<th>
						<!--部门名 --><spring:message code="ess.infoApply.DEPT_NAME" />
					</th>
					<th>
						<!--职级 --><spring:message code="ess.infoApply.Rank" />
					</th>
					<th>
						<!--合计 --><spring:message code="ess.viewpersonalpainfo.heji" />
					</th>
					<th>
						<!--平日 --><spring:message code="ar.viewitemparameter.title.pingshi" />
					</th>
					<th>
						<!--周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" />
					</th>
					<th>
						<!--法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewOtApplyPersonalList}" var="personList"
					varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">

							${i.count}
						</td>
						<td style="text-align: center">
							${personList.EMPID}
						</td>
						<td style="text-align: center">
							${personList.LOCAL_NAME}
						</td>
						
						<td style="text-align: center">
							${personList.DEPART_NAME}
						</td>
						<td style="text-align: center">
							${personList.PSOT_NAME}
						</td>
						<td style="text-align: center">
							${personList.OT_TOTAIL} <!--小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi" />
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;"  title="Weekdays"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="90000295,90000296,90000297" type="${personList.PERSON_ID}">
								<span>${personList.WEEKDAY_OT_TOTAIL} <!--小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi" /></span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" title="Weekends"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="90000298,90000299" type="${personList.PERSON_ID}">
								<span>${personList.WEEKEND_OT_TOTAIL} <!--小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi" /></span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" title="Holiday"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="90000300,90000301" type="${personList.PERSON_ID}">
								<span> ${personList.HOILDAY_OT_TOTAIL} <!--小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi" /></span> </a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<%--<c:set value="/ess/viewDept/viewOtApplyPersonalList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"--%>
</div>