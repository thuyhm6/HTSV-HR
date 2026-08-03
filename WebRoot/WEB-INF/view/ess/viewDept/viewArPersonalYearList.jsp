<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

--%><script>
$(document).ready(function(){	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
    	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 350,
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

function changeURL(obj,ar_month_str) {

	//$("input[name='keleyicom']");
	
	
	obj.href="/ess/viewDept/viewArPersonalSingleList?ITEM_NO="+obj.name+"&PERSON_ID="+obj.type+"&AR_MONTH_STR="+ar_month_str;
	

	

}

/*
function exportExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewArVacationMonth");  
     
     var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}*/
</script>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewArPersonalYearList" 
		method="post" id="viewArPersonalList" name="viewArPersonalList" > 
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr> 
					<td>
						<spring:message code="pa.payear.title.payear"/><!-- 年份 -->
					</td>
					<td>
			 <input type="text" size="8" id="seach_FROM_DATE" name="YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy'})"value="${YEAR}" />

					</td>
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
					<!--  <li>
						<a class="buttonActive" onclick="exportExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
						  		<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
					</li>-->
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent"  >
	<%-- <div class="formBar">
		<ul class="toolBar">

			<li>
				<a class="buttonActive" onclick="exportExcle(this)"是否导出? title="<spring:message code='rp.report.title.exportYN'/>">
				  		<span>Excel导出<spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>

		</ul>
	</div> --%>
	<table class="orderList" width="600%"
			nowrapTD="false">
			<thead>
				<tr>
				    <th width="70px"><!--年月--><spring:message code="ess.infoApply.YEAR_MONTH"/></th>
					<th><!--试用天数--><spring:message code="ess.viewArPersonalYearList.PROBATION_DAYS.b"/></th>
					<th><!--正式天数--><spring:message code="ess.viewArPersonalYearList.REGULAR_DAYS.b"/></th>
					<th><!--应出勤天数--><spring:message code="ess.infoApply.yingchuqintianshu"/></th>
					<th><!--试用期夜班次数--><spring:message code="ess.viewArPersonalYearList.PROB_NIGHT_SHIFT_COUNT.b"/></th>
					<th><!--正式夜班次数--><spring:message code="ess.viewArPersonalYearList.NIGHT_SHIFT_COUNT.b"/></th>
					<th><!--试用平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT150_HOURS.b"/></th>
					<th><!--平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT150_HOURS.b"/></th>
					<th><!--试用平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT200_HOURS.b"/></th>
					<th><!--平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT200_HOURS.b"/></th>
					<th><!--试用平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT210_HOURS.b"/></th>
					<th><!--平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT210_HOURS.b"/></th>
					<th><!--试用周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT200_HOURS.b"/></th>
					<th><!--周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT200_HOURS.b"/></th>
					<th><!--试用周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT270_HOURS.b"/></th>
					<th><!--周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT270_HOURS.b"/></th>
					<th><!--试用节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT300_HOURS.b"/></th>
					<th><!--节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT300_HOURS.b"/></th>
					<th><!--试用节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT390_HOURS.b"/></th>
					<th><!--节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT390_HOURS.b"/></th>
					<th><!--离职年假补偿天数--><spring:message code="ess.viewArPersonalYearList.RESIGN_ANNUALLEAVE_PAY_DAYS.b"/></th>
					<th><!--试用事假天数--><spring:message code="ess.viewArPersonalYearList.PROB_PERSONALLEAVE_DAYS.b"/></th>
					<th><!--事假天数--><spring:message code="ess.viewArPersonalYearList.PERSONALLEAVE_DAYS.b"/></th>
					<th><!--试用一般病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_SICKLEAVE_DAYS.b"/></th>
					<th><!--一般病假天数--><spring:message code="ess.viewArPersonalYearList.SICKLEAVE_DAYS.b"/></th>
					<th><!--试用孩子病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_CHILD_SICKLEAVE_DAYS.b"/></th>
					<th><!--孩子病假天数--><spring:message code="ess.viewArPersonalYearList.CHILD_SICKLEAVE_DAYS.b"/></th>
					<th><!--试用长期病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_LONG_SICKLEAVE_DAYS.b"/></th>
					<th><!--长期病假天数--><spring:message code="ess.viewArPersonalYearList.LONG_SICKLEAVE_DAYS.b"/></th>
					<th><!--产假天数--><spring:message code="ess.viewArPersonalYearList.MATERNITYLEAVE_DAYS.b"/></th>
					<th><!--试用旷工天数--><spring:message code="ess.viewArPersonalYearList.PROB_ABSENTEEISM_DAYS.b"/></th>
					<th><!--旷工天数--><spring:message code="ess.viewArPersonalYearList.ABSENTEEISM_DAYS.b"/></th>
					<th><!--试用迟到分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_LATE_ARRIVE_MINUTES.b"/></th>
					<th><!--迟到分钟数--><spring:message code="ess.viewArPersonalYearList.LATE_ARRIVE_MINUTES.b"/></th>
					<th><!--试用早退分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_EARLY_LEAVE_MINUTES.b"/></th>
					<th><!--早退分钟数--><spring:message code="ess.viewArPersonalYearList.EARLY_LEAVE_MINUTES.b"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewArPersonalYearList}" var="personList" varStatus="i">
					<tr target="sid" rel="">
					    <td width="50px;" style="text-align: center">
							${personList.AR_MONTH}
						</td>
						<td width="50px;" style="text-align: center">
							${personList.PROBATION_DAYS}
						</td>
						
						<td  style="text-align: center" >
							<%-- <a style="cursor: pointer;" width="800"  height="400"  id="codeChange"
								 type="${personList.PERSON_ID}" onclick='javascript:changeURL(this,${personList.AR_MONTH_STR});'
								name="141456"  target="dialog">
								<span>${personList.NJ}</span>
							</a> --%>
							${personList.REGULAR_DAYS}
						</td>
						<td  style="text-align: center" >
						${personList.WORK_SCHEDULE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_NIGHT_SHIFT_COUNT}
						</td> 
						<td  style="text-align: center" >
							${personList.NIGHT_SHIFT_COUNT}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_WORKDAYOT150_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.WORKDAYOT150_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_WORKDAYOT200_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.WORKDAYOT200_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_WORKDAYOT210_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.WORKDAYOT210_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_WEEKLYOT200_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.WEEKLYOT200_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_WEEKLYOT270_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.WEEKLYOT270_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_HOLIDAYOT300_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.HOLIDAYOT300_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_HOLIDAYOT390_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.HOLIDAYOT390_HOURS}
						</td>
						<td  style="text-align: center" >
							${personList.RESIGN_ANNUALLEAVE_PAY_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_PERSONALLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PERSONALLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_CHILD_SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.CHILD_SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_LONG_SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.LONG_SICKLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.MATERNITYLEAVE_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_ABSENTEEISM_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.ABSENTEEISM_DAYS}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_LATE_ARRIVE_MINUTES}
						</td>
						<td  style="text-align: center" >
							${personList.LATE_ARRIVE_MINUTES}
						</td>
						<td  style="text-align: center" >
							${personList.PROB_EARLY_LEAVE_MINUTES}
						</td>
						<td  style="text-align: center" >
							${personList.EARLY_LEAVE_MINUTES}
						</td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>