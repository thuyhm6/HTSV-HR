<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
$(document).ready(function(){
	$("#insertMacRecordMealList_search",navTab.getCurrentPanel()).click(function(){
		$("#viewArMealRecordForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArCardRecord&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArCardRecord&seach_KEY='+name);
    });

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 310,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
			//正在加载中......
	    	"sProcessing": "<spring:message code='ess.message.loading' />",
	    	//查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
        } //多语言配置
	});
});

function f_delete_viewarcardrecord(callback) {
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewArAnnualStandard.choosedelete'/>");
		return;
	}
	var defaultCpny = $("#defaultCpny").val();
	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			jsonData += ' "SEQ": "' + obj.value + '",';
			jsonData += ' "CPNY_ID": "${LoginUser.cpnyId}" ';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要删除的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/>");
		return;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='button.delete.sure'/>")){	
		$.ajax({
			type: 'POST',
			url: '/ar/attendanceMintenance/deleteArCardMealInfo',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});	
	}
}
function insertMealRecordDayList(form,callback,flag){
    var $form=$('#viewArMealRecordForm',navTab.getCurrentPanel());
    var STIME = $("#seach_STIME",navTab.getCurrentPanel()).val();
    STIME_FORMAT = STIME.substring(6,10)+"-"+STIME.substring(3,5)+"-"+STIME.substring(0,2);
	var RTIME = $("#seach_RTIME",navTab.getCurrentPanel()).val();
	RTIME_FORMAT = RTIME.substring(6,10)+"-"+RTIME.substring(3,5)+"-"+RTIME.substring(0,2);
	var EMPID = $("#seach_EMPID",navTab.getCurrentPanel()).val();
    $.ajax({
		type: 'POST',
		url: "/ar/attendanceMintenance/insertMealRecordList?STIME="+STIME_FORMAT+"&RTIME="+RTIME_FORMAT+"&EMPID="+EMPID,
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("viewArMealRecordForm");
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
<a id="importExcel_ar0104"  href="#" target="navTab" mask="true"><span style="display:none;"><!--刷卡维护数据导入结果--><spring:message code="ess.infoApply.cardinsertmodify" /> </span></a>
<div class="pageHeader">
	<form id="viewArMealRecordForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArCardRecordMeal" method="post">
		<input type="hidden" name='CPNY' value="${LoginUser.cpnyId }"/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left">
							<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
						</div>
						<input type="hidden" name="seach_EMPID" id="seach_EMPID" value="${personInfo.EMPID}"/>
						
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td>
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${STIME }"/>~
						<input type="text" id="seach_RTIME" name="seach_RTIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${RTIME }"/>
					</td>
					<td>
						<spring:message code="ess.infoApply.out_door_time" text="期间"/>
					</td>
					<td>
						<input type="text" id="seach_OUT_TIME_FROM" name="seach_OUT_TIME_FROM" value="${OUT_TIME_FROM }"/>~
						<input type="text" id="seach_OUT_TIME_TO" name="seach_OUT_TIME_TO" value="${OUT_TIME_TO }"/>
					</td>
					<td><spring:message code="inct.salesman.classify" /></td>
					<td>
					    <select id="seach_EAT_DATE_FLAG" name="seach_EAT_DATE_FLAG">
					        <option value="" <c:if test="${EAT_DATE_FLAG eq null || EAT_DATE_FLAG eq ''}" >selected</c:if>><!--全部--><spring:message code="org.title.ALL" /></option>
						      <option value="BREAKFAST" <c:if test="${EAT_DATE_FLAG eq 'BREAKFAST'}" >selected</c:if>>BREAKFAST</option>
						      <option value="LUNCH" <c:if test="${EAT_DATE_FLAG eq 'LUNCH'}" >selected</c:if>>LUNCH</option>
						      <option value="DINNER" <c:if test="${EAT_DATE_FLAG eq 'DINNER'}" >selected</c:if>>DINNER</option>
						      <option value="NIGHT" <c:if test="${EAT_DATE_FLAG eq 'NIGHT'}" >selected</c:if>>NIGHT</option>
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
			<li>	            
				<a class="buttonActive" id="insertMealRecordDayList" onclick="insertMealRecordDayList('viewArMealRecordForm',DWZ.ajaxDone,'1')" href="#"><span><!-- 读取刷卡记录 --><spring:message code="ess.infoApply.getcardinfo"/></span></a> 
			</li>
			<li>	            
				<a class="buttonActive" id="insertMacRecordMealList_search" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a> 
			</li>
			<li>
				<a href="/ess/infoApplyLeave/downloadFile?fileName=/resources/template/mealTemplate.xls&file=mealTemplate.xls"><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/pa/excelImport/importExcelData?&importFunName=/importArCardMealExcel" target="dialog" mask="true" width="400" height="200" ><span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="javascript:f_delete_viewarcardrecord(navTabAjaxDoneWithForm);"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete"/></span></a>
			</li>	
			<li><a class="buttonActive" onclick="downloadExcel('viewArMealRecordForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=58&firstFlag=N','/ar/attendanceMintenance/viewArCardRecordMeal?firstFlag=N')">
			<span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	</ul>
</div>
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(getArCardRecordMealList)}</div>
	<table class="list" width="99%">
		<thead>
			<tr>
				<th width="2%" align="center" >No.</th>
				<th width="1%" align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th width="5%"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="10%"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="12%"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="8%"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></th>
				<th width="6%"><!-- Attendance Date --><spring:message code="ess.infoApply.attendance_date"/></th>
				<th width="6%"><!-- 打卡日期 --><spring:message code="ar.viewArCardRecord.DAKARIQI.b"/></th>
				<th width="6%"><!-- 打卡时间 --><spring:message code="ess.infoApply.card_clock_time"/></th> 
				<th width="20%">Dish Name</th>
				<th width="5%"><!-- Meal type --><spring:message code="inct.salesman.classify"/></th>
				<th width="2%">Amount</th>
				<th width="18%"><!-- Out time --><spring:message code="ess.infoApply.out_door_time"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${getArCardRecordMealList}" var="list" varStatus="i">
				<tr target="SEQ" rel="${list.SEQ}">
					<td>${i.count}</td>
					<td><input type="checkbox" name="c1" value="${list.SEQ }"></td>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPTNAME}</td>
					<td>${list.POST_GRADE_NAME}</td>
					<td>${list.ATTENDANCE_DATE}</td> 
					<td>${list.R_DATE}</td> 
					<td>${list.R_TIME}</td>
					<td>${list.EAT_NAME}</td> 
					<td>${list.EAT_DATE}</td>
					<td>${list.AMOUNT}</td>
					<td>${list.OUTDOOR_TIME}</td>
				</tr>
			</c:forEach>			
		</tbody>
	</table>
</div>
