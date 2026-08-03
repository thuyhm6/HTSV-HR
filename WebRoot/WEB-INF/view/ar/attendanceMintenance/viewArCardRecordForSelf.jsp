<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript"> 
$(document).ready(function(){
	$("#insertMacRecordList_search",navTab.getCurrentPanel()).click(function(){
		$("#viewarcardrecordForSelfForm",navTab.getCurrentPanel()).submit();
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


function insertMacRecordList(form,callback,flag){
    var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
		
    var STIME = $("#seach_STIME",navTab.getCurrentPanel()).val();
    STIME_FORMAT = STIME.substring(6,10)+"-"+STIME.substring(3,5)+"-"+STIME.substring(0,2);
	var RTIME = $("#seach_RTIME",navTab.getCurrentPanel()).val();
	RTIME_FORMAT = RTIME.substring(6,10)+"-"+RTIME.substring(3,5)+"-"+RTIME.substring(0,2);
	var EMPID = $("#seach_EMPID",navTab.getCurrentPanel()).val();
    $.ajax({
		type: form.method || 'POST',
		url: "/ar/attendanceMintenance/insertMacRecordListLGE?STIME="+STIME_FORMAT+"&RTIME="+RTIME_FORMAT+"&COMPANY_CARD=1"+EMPID,
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchLeaveApplyAffirmForm");
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
	<form id="viewarcardrecordForSelfForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArCardRecordForSelf?firstFlag=N" method="post">
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
					<td></td>
				    <td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${STIME }"/>~
						<input type="text" id="seach_RTIME" name="seach_RTIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${RTIME }"/>
					</td>
					<td>
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
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
				<a class="buttonActive" id="insertMacRecordList" onclick="insertMacRecordList('viewarcardrecord',DWZ.ajaxDone,'1')" href="#"><span><!-- 读取刷卡记录 --><spring:message code="ess.infoApply.getcardinfo"/></span></a> 
			</li>		
			<li>	            
				<a class="buttonActive" id="insertMacRecordList_search" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a> 
			</li>	
			<li><a class="buttonActive" onclick="downloadExcel('viewarcardrecordForSelfForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=2&firstFlag=N','/ar/attendanceMintenance/viewArCardRecord?firstFlag=N')">
			<span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	</ul>
</div>
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(getArCardRecordList)}</div>
	<table class="list" width="99%">
		<thead>
			<tr>
				<th width="3%" align="center" >No.</th>
				<th width="8%"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="14%"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="18%"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="8%">
					<!-- 打卡日期 --><spring:message code="ar.viewArCardRecord.DAKARIQI.b"/>
				</th>
				<th width="10%"><!-- 打卡时间 --><spring:message code="ess.infoApply.card_clock_time"/></th>
				<th width="6%"><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/></th>
				<th width="14%"><!-- 数据来源 --><spring:message code="ar.viewarcardrecord.title.shujulaiyuan"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${getArCardRecordList}" var="list" varStatus="i">
				<tr target="RECORD_NO" rel="${list.RECORD_NO}">
					<td>${i.count}</td>
					<td>${list.CARD_NO}</td>
					<td>${list.EMPLOYEE_NAME}</td>
					<td>${list.DEPTNAME}</td>
					<td>${fn:substring(list.R_TIME,0,10)}</td>
					<td>${fn:substring(list.R_TIME,10,19)}</td>
					<td>${list.DOOR_TYPE}</td>
					<td>${list.DEVICENAME}</td>
				</tr>
			</c:forEach>			
		</tbody>
	</table>
</div>
