<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewMonthDetailList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewMonthDetailListForm",navTab.getCurrentPanel()).submit();
	});
	$('input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	$("#viewMonthDetailListForm",navTab.getCurrentPanel()).submit();
	    }
	});
	//申请
	$("#viewMonthDetailList_Apply",navTab.getCurrentPanel()).click(function(){
		var empIdsStr="";
		var arMonth ="";
		var flag=false;
		$("input:[name='BATCH_DETAIL_APPLY']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
				arMonth = $(this).attr("sysArMonthStr");
				flag = true;
			}
		});
		empIdsStr = empIdsStr + "'empty'";
		if(flag == false){
			alertMsg.error('请选择要申请的数据');
			return false;
		}
		alertMsg.confirm('确定要申请吗？',
	  		{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/ess/tempEmp/monthDetailApply',
	  				data:{AR_MONTH:arMonth,EMPIDS:empIdsStr},
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
		return false;
	});

	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 320,
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
        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
        }, //多语言配置
	     "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0,7] }
	                     ],
	    "fixedColumns":{leftColumns: 4}
	});
});
</script>
<div class="pageHeader">
<form id="viewMonthDetailListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewMonthDetailList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 月份 --><spring:message code="ar.excelexport.title.month" /></td>
		<td>
			<input type="text" id="AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'MMyyyy',lang:'en'})" value="${AR_MONTH}"/>
		</td>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY }">
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
		</td>
		<td><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="NATIONALITY_CODE" id="NATIONALITY_CODE" parentNo="870" cnpyID="${defaultCpny}" selected="${NATIONALITY_CODE}" limit="all" />
		</td>
		<td class="text"><spring:message code="hrm.empinfo.EMP_OFFICE_NAME" /> <!-- 员工状态 --> </td>
		<td class="td_type">
			<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}" limit="all" />
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewMonthDetailList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
		<!--<li><a class="buttonActive" id="viewMonthDetailList_Apply" href="#"><span>申请<spring:message code="display.paecc.shenqing" /></span></a></li>-->
		<!--<li><a class="buttonActive" id="viewMonthDetailList_Save" href="#"><span>保存</span></a></li>-->
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=305','/ess/tempEmp/viewMonthDetailList')">
		<span><!--Excel导出--><spring:message code="ess.infoApply.EXCEL_OUT" /></span></a></li>
		
		<!--<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=295','/ess/tempEmp/viewMonthDetailList')">
		<span>月结Excel导出</span></a></li>-->
	</ul>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(viewMonthDetailList)}</div>
				<table class="orderList" width="5570px">
					<thead>
					    <tr>
						    <th colspan='4'><!--基本信息--><spring:message code="ess.empInfo.essential_information" /></th>
						    <th colspan='5'></th>
						    <th colspan='3'><!--平日加班--><spring:message code="ess.viewMonthDetailList.OT_ON_WEEKDAY.b" /></th>
						    <th colspan='2'><!--带薪假加班--><spring:message code="ess.viewMonthDetailList.OT_ON_SATURDAY.b" /></th>
						    <th colspan='2'><!--周末加班--><spring:message code="ess.viewMonthDetailList.OT_ON_WEEKEND.b" /></th>
						    <th colspan='2'><!--节假日加班--><spring:message code="ess.viewMonthDetailList.OT_ON_HOLIDAY.b" /></th>
						    <th colspan='3'><!--工作天数--><spring:message code="ess.viewMonthDetailList.WORK_DAYS.b" /></th>
						    <th rowspan='2' width="60px"><!--应出勤天数--><spring:message code="ess.infoApply.yingchuqintianshu" /></th>
						    <th rowspan='2' width="60px"><!--实际工作天数--><spring:message code="ess.viewMonthDetailList.REAL_WORK_DAYS.b" /></th>
						    <th rowspan='2' width="60px"><!--月工作天数--><spring:message code="ess.viewMonthDetailList.MONTH_WORK_DAYS.b" /></th>
						    <th colspan='2'><!--迟到--><spring:message code="ar.monthwork.title.Lateness" /></th>
						    <th colspan='2'><!--早退--><spring:message code="ar.monthwork.title.EarlyLeave" /></th>
						    <th rowspan='2' width="60px"><!--年假--><spring:message code="ar.viewArAnnualStandard.title.ninjia" /></th>
						    <th rowspan='2' width="120px"><!--无薪假(有理由)--><spring:message code="ess.viewMonthDetailList.UNPAID_LEAVE_RESON.b" /></th>
						    <th rowspan='2' width="120px"><!--无薪假(无理由)--><spring:message code="ess.viewMonthDetailList.UNPAID_LEAVE_UNRESON.b" /></th>
						    <th rowspan='2' width="60px"><!--产假--><spring:message code="ar.menu.title.bingjia" /></th>
						    <th rowspan='2' width="60px"><!--后病假--><spring:message code="ar.menu.title.keepHelth" /></th>
						    <th rowspan='2' width="60px"><!--产假--><spring:message code="ar.menu.title.chanjia" /></th>
						    <th rowspan='2' width="60px"><!--婚丧假--><spring:message code="ess.viewMonthDetailList.HUNSANGJIA.b" /></th>
						    <th rowspan='2' width="60px"><!----><spring:message code="is.joininstance.title.gongshang" /></th>
						    <th rowspan='2' width="60px"><!----><spring:message code="ess.viewMonthDetailList.COVID19.b" /></th>
						    <th colspan='${fn:length(viewMonthList)}'><!--日考勤明细--><spring:message code="ess.viewMonthDetailList.DAY_LEAVE_DETAIL.b" /></th>
						    <th colspan='${fn:length(viewMonthList)}'><!--日加班明细--><spring:message code="ess.viewMonthDetailList.DAY_OT_DETAIL.b" /></th>
						    <th colspan='${fn:length(viewMonthList)}'><!--夜加班明细--><spring:message code="ess.viewMonthDetailList.NIGHT_OT_DETAIL.b" /></th>
						</tr>
						<tr>
							<th width="20px">NO.</th>
							<th width="60px"><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th width="120px"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th width="150px"><!--部门--><spring:message code="ess.infoApply.DEPT" /></th>
							<th width="40px"><!--职级--><spring:message code="ess.trans.title.postGradeName" /></th>
							<th width="40px"><!--职责--><spring:message code="sys.affirm.title.duty" /></th>
							<th width="60px"><!--员工类型--><spring:message code="ess.infoApply.employee_type" /></th>
							<th width="60px"><!--入职日期--><spring:message code="ess.trans.title.entryJobDate" /></th>
							<th width="60px"><!--离职日期--><spring:message code="hrm.recruitManage.LEAVE_DATE" /></th>
							<th width="60px"><!--日加班--><spring:message code="ess.viewMonthDetailList.DAY_OT.b" /></th>
							<th width="60px"><!--夜加班--><spring:message code="ess.viewMonthDetailList.NIGHT_OT.b" /></th>
							<th width="60px"><!--夜加班--><spring:message code="ess.viewMonthDetailList.NIGHT_OT.b" />(210%)</th>
							<th width="60px"><!--日加班--><spring:message code="ess.viewMonthDetailList.DAY_OT.b" /></th>
							<th width="60px"><!--夜加班--><spring:message code="ess.viewMonthDetailList.NIGHT_OT.b" /></th>
							<th width="60px"><!--日加班--><spring:message code="ess.viewMonthDetailList.DAY_OT.b" /></th>
							<th width="60px"><!--夜加班--><spring:message code="ess.viewMonthDetailList.NIGHT_OT.b" /></th>
							<th width="60px"><!--日加班--><spring:message code="ess.viewMonthDetailList.DAY_OT.b" /></th>
							<th width="60px"><!--夜加班--><spring:message code="ess.viewMonthDetailList.NIGHT_OT.b" /></th>
							<th width="60px"><!--正常班--><spring:message code="ar.viewArShiftMonthCheckList.ZHENGCHANGBAN.b" /></th>
							<th width="60px"><!--夜班--><spring:message code="ess.viewMonthDetailList.NIGHT_SHIFT.b" /></th>
							<th width="60px"><!--夜班--><spring:message code="ess.viewMonthDetailList.NIGHT_SHIFT.b" /> (<spring:message code="ar.viewitemparameter.title.xiaoshi" />) </th>
							<th width="60px"><!--时长--><spring:message code="ess.infoApply.duration" /></th>
							<th width="60px"><!--次数--><spring:message code="ess.viewMonthDetailList.COUNT_NUMBER.b" /></th>
							<th width="60px"><!--时长--><spring:message code="ess.infoApply.duration" /></th>
							<th width="60px"><!--次数--><spring:message code="ess.viewMonthDetailList.COUNT_NUMBER.b" /></th>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="20px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="20px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="20px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewMonthDetailList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td>${item.DEPT_NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.POSITION_NAME}</td>
								<td>${item.EMP_TYPE_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<td class='td_center'>${item.REG_DAY_OT_WEEKDAY + item.PROB_DAY_OT_WEEKDAY}</td>
								<td class='td_center'>${item.REG_NIGHT_OT_WEEKDAY + item.PROB_NIGHT_OT_WEEKDAY}</td>
								<td class='td_center'>${item.REG_NIGHT_OT_WEEKDAY_210 +  item.PROB_NIGHT_OT_WEEKDAY_210}</td>
								<td class='td_center'>${item.REG_DAY_OT_SATURDAY + item.PROB_DAY_OT_SATURDAY}</td>
								<td class='td_center'>${item.REG_NIGHT_OT_SATURDAY + item.PROB_NIGHT_OT_SATURDAY}</td>
								<td class='td_center'>${item.REG_DAY_OT_WEEKEND + item.PROB_DAY_OT_WEEKEND}</td>
								<td class='td_center'>${item.REG_NIGHT_OT_WEEKEND + item.PROB_NIGHT_OT_WEEKEND}</td>
								<td class='td_center'>${item.REG_DAY_OT_HOLIDAY + item.PROB_DAY_OT_HOLIDAY}</td>
								<td class='td_center'>${item.REG_NIGHT_OT_HOLIDAY + item.PROB_NIGHT_OT_HOLIDAY}</td>
								<td class='td_center'>
								${item.REG_WORK_DAYS + item.REG_REST_PAY_DAYS + item.PROB_WORK_DAYS + item.PROB_REST_PAY_DAYS - item.PROB_LEAVE_NOT_PAY_DAYS - item.PROB_LEAVE_PAY_DAYS - item.REG_LEAVE_NOT_PAY_DAYS  - item.REG_LEAVE_PAY_DAYS}
								</td>
								<td class='td_center'>${item.REG_NIGHT_WORK_DAYS + item.PROB_NIGHT_WORK_DAYS}</td>
								<td class='td_center'>${item.REG_NIGHT_WORK_HOURS + item.PROB_NIGHT_WORK_HOURS}</td>
								<td class='td_center'>
								${item.REG_WORK_DAYS + item.REG_REST_PAY_DAYS + item.PROB_WORK_DAYS + item.PROB_REST_PAY_DAYS}
								</td>
								<td class='td_center'>
								${item.REG_WORK_DAYS + item.REG_REST_PAY_DAYS + item.PROB_WORK_DAYS + item.PROB_REST_PAY_DAYS - item.REG_LEAVE_NOT_PAY_DAYS  - item.REG_LEAVE_PAY_DAYS - item.PROB_LEAVE_NOT_PAY_DAYS - item.PROB_LEAVE_PAY_DAYS}
								</td>
								<td class='td_center'>
								${item.REG_WORK_DAYS + item.REG_REST_PAY_DAYS + item.PROB_WORK_DAYS + item.PROB_REST_PAY_DAYS - item.PROB_LEAVE_NOT_PAY_DAYS - item.REG_LEAVE_NOT_PAY_DAYS}
								</td>
								<td class='td_center'>${item.REG_LATE_ARRIVE_HOURS + item.PROB_LATE_ARRIVE_HOURS}</td>
								<td class='td_center'>${item.REG_LATE_ARRIVE_COUNT + item.PROB_LATE_ARRIVE_COUNT}</td>
								<td class='td_center'>${item.REG_EARLY_LEAVE_HOURS + item.PROB_EARLY_LEAVE_HOURS}</td>
								<td class='td_center'>${item.REG_EARLY_LEAVE_COUNT + item.PROB_EARLY_LEAVE_COUNT}</td>
								<td class='td_center'>${item.ANNUAL_LEAVE_DAYS}</td>
								<td class='td_center'>${item.REG_UNPAID_RAESON_DAYS + item.PROB_UNPAID_RAESON_DAYS}</td>
								<td class='td_center'>${item.REG_UNPAID_UNRAESON_DAYS + item.PROB_UNPAID_UNRAESON_DAYS}</td>
								<td class='td_center'>${item.REG_SICK_LEAVE_DAYS + item.PROB_SICK_LEAVE_DAYS}</td>
								<td class='td_center'>${item.REG_HELTH_LEAVE_DAYS + item.PROB_HELTH_LEAVE_DAYS}</td>
								<td class='td_center'>${item.REG_MATERNITYLEAVE_DAYS + item.PROB_MATERNITYLEAVE_DAYS}</td>
								<td class='td_center'>${item.REG_HUNSANGJIA_DAYS + item.PROB_HUNSANGJIA_DAYS}</td>
								<td class='td_center'>${item.REG_ACCIDENT_DAYS + item.PROB_ACCIDENT_DAYS}</td>
								<td class='td_center'>${item.REG_SHUTDOWN_LEAVE_DAYS + item.PROB_SHUTDOWN_LEAVE_DAYS}</td>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="j">
									<td class='td_center' <c:if test="${item1.TYPEID ne 1440}">style="background-color:#cccccc"</c:if>>${item[item1.DATE_KEY]}</td>
								</c:forEach>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="j">
									<td class='td_center' <c:if test="${item1.TYPEID ne 1440}">style="background-color:#cccccc"</c:if>>${item[item1.DAY_OT_KEY]}</td>
								</c:forEach>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="j">
									<td class='td_center' <c:if test="${item1.TYPEID ne 1440}">style="background-color:#cccccc"</c:if>>${item[item1.NIGHT_OT_KEY]}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
