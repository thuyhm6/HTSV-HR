<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": $(document.body).width() - 30,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns" : {
				leftColumns : 8
			},
	    "oLanguage": {//多语言配置
			//正在加载中......
	    	"sProcessing": "<spring:message code='ess.message.loading' />",
	    	//查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
	        //每页 _MENU_ 条记录
	        "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
	        //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
	        //(从 _MAX_ 条记录过滤)
	        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
	        "oPaginate": {
		        //上一页
	            "sPrevious": "<spring:message code='ess.message.previous_page' />",
	            //下一页
	            "sNext": "<spring:message code='ess.message.next_page' />"
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
		});
});
function pa1302_searchPop(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	$("#pa1302_searchPop", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY=' + name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if (flag == 'onkeyup')
		$("#pa1302_searchPop", navTab.getCurrentPanel()).click();
}
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
			} else {
				return false;
			}
		}
	} else {
		target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "INPUT") {
				return false;
			} else {
				return true;
			}
		}
	}
}

function viewPaArSummarySearchReport(){
      $("#viewPaArSummarySearchList").attr("action","/pa/workManagement/viewPaArSummarySearchListReport");
      $("#viewPaArSummarySearchList").attr("onsubmit","");
      
      $("#viewPaArSummarySearchList").submit();
      
      $("#viewPaArSummarySearchList").attr("action","/pa/workManagement/viewPaArSummarySearchList");
      $("#viewPaArSummarySearchList").attr("onsubmit","return navTabSearch(this);");
	
}
</script>



<div class="pageHeader" style="z-index: 20">
	<form id="viewPaArSummarySearchList"
		onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaArSummarySearchList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
							onkeydown="javascript:if(event.keyCode == 13)pa1302_searchPop('onkeyup');" />
					</td>
					<td>
						<a class="btnLook" id="pa1302_searchPop"
							onclick="pa1302_searchPop()" href="#" lookupGroup="person"> </a>
					</td>
					<td colspan="3">
						<input id="dwz.person.empInfo" type="text" readonly
							lookupGroup="person" size="60" value="${empInfo}" />

						</a>
					</td>
				</tr>
				<tr>
					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:
					</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }"
											value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">
											${paySchedule.PAY_DATE } -${paySchedule.SALARY_DISTIN }
										</option>
									</c:when>
									<c:otherwise>
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }"
											value="${paySchedule.PAY_SCHEDULE_NO }">
											${paySchedule.PAY_DATE } -${paySchedule.SALARY_DISTIN }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>


					<td>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="viewpa1302_seachDept" selected="${DEPT_NO}" />
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}"
							limit="ar" id="viewpa1302_seachDept" selected="${DEPT_NO}" />
					</td>

				</tr>

			</table>
			<div class="subBar">
				<ul>

					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><spring:message code="public.title.search" /><!--检索--></button>
							</div>
						</div>
					</li>
					<li><a class="buttonActive" href="/pa/excelExport/downloadExcelArSummary?file=AR_Summary_HTSV">
					<span><spring:message code="ar.addempshift.title.downloadmodule"/><!-- 下载导入模板 --></span></a></li>
					<li><a class="buttonActive" href="/pa/excelImport/importExcelData?importFunName=/importArSummary&TYPE=${PAY_SCHEDULE_NO}" target="dialog" mask="true">
						<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></a></li>
					<%-- <li>
						<a class="buttonActive" onclick="viewPaArSummarySearchReport();"
						href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
					</li> --%>

				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
 Total:${fn:length(viewPaArSummarySearchList)}
	<table id="ViewPaArSummaryForManageTable" width="4000px" class="orderList">
		<thead>
			<tr>
				<th>No</th>
				<th style="width:70px"><!--工号--><spring:message code="ess.infoApply.EMP_ID" /></th>
				<th style="width:130px"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
				<th style="width:200px"><!--部门--><spring:message code="ess.infoApply.DEPT" /></th>
				<th><!--职级--><spring:message code="hrm.contract.Rank" /></th>
				<th><!--入社日期--><spring:message code="ess.empInfo.date_of_agency" /></th>
				<th><!--试用期结束日期--><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE" /></th>
				<th><!--离职日期--><spring:message code="ess.trans.title.resignDate" /></th>
				<th><!--试用天数--><spring:message code="ess.viewArPersonalYearList.PROBATION_DAYS.b" /></th>
				<th><!--正式天数--><spring:message code="ess.viewArPersonalYearList.REGULAR_DAYS.b" /></th>
				<th><!--应出勤天数--><spring:message code="ess.infoApply.yingchuqintianshu" /></th>
				<th><!--试用期夜班次数--><spring:message code="ess.viewArPersonalYearList.PROB_NIGHT_SHIFT_COUNT.b" /></th>
				<th><!--正式夜班次数--><spring:message code="ess.viewArPersonalYearList.NIGHT_SHIFT_COUNT.b" /></th>
				<th><!--试用平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT150_HOURS.b" /></th>
				<th><!--平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT150_HOURS.b" /></th>
				<th><!--试用平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT200_HOURS.b" /></th>
				<th><!--平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT200_HOURS.b" /></th>
				<th><!--试用平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT210_HOURS.b" /></th>
				<th><!--平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT210_HOURS.b" /></th>
				<th><!--试用周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT200_HOURS.b" /></th>
				<th><!--周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT200_HOURS.b" /></th>
				<th><!--试用周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT270_HOURS.b" /></th>
				<th><!--周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT270_HOURS.b" /></th>
				<th><!--试用周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT200_HOURS.b" /></th>
				<th><!--周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT200_HOURS.b" /></th>
				<th><!--试用周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT270_HOURS.b" /></th>
				<th><!--周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT270_HOURS.b" /></th>
				<th><!--试用节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT300_HOURS.b" /></th>
				<th><!--节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT300_HOURS.b" /></th>
				<th><!--试用节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT390_HOURS.b" /></th>
				<th><!--节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT390_HOURS.b" /></th>
				<th><!--离职年假补偿天数--><spring:message code="ess.viewArPersonalYearList.RESIGN_ANNUALLEAVE_PAY_DAYS.b" /></th>
				<th><!--试用事假天数--><spring:message code="ess.viewArPersonalYearList.PROB_PERSONALLEAVE_DAYS.b" /></th>
				<th><!--事假天数--><spring:message code="ess.viewArPersonalYearList.PERSONALLEAVE_DAYS.b" /></th>
				<th><!--试用一般病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_SICKLEAVE_DAYS.b" /></th>
				<th><!--一般病假天数--><spring:message code="ess.viewArPersonalYearList.SICKLEAVE_DAYS.b" /></th>
				<th><!--试用孩子病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_CHILD_SICKLEAVE_DAYS.b" /></th>
				<th><!--孩子病假天数--><spring:message code="ess.viewArPersonalYearList.CHILD_SICKLEAVE_DAYS.b" /></th>
				<th><!--试用长期病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_LONG_SICKLEAVE_DAYS.b" /></th>
				<th><!--长期病假天数--><spring:message code="ess.viewArPersonalYearList.LONG_SICKLEAVE_DAYS.b" /></th>
				<th><!--产假天数--><spring:message code="ess.viewArPersonalYearList.MATERNITYLEAVE_DAYS.b" /></th>
				<th><!--试用旷工天数--><spring:message code="ess.viewArPersonalYearList.PROB_ABSENTEEISM_DAYS.b" /></th>
				<th><!--旷工天数--><spring:message code="ess.viewArPersonalYearList.ABSENTEEISM_DAYS.b" /></th>
				<th><!--试用迟到分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_LATE_ARRIVE_MINUTES.b" /></th>
				<th><!--迟到分钟数--><spring:message code="ess.viewArPersonalYearList.LATE_ARRIVE_MINUTES.b" /></th>
				<th><!--试用早退分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_EARLY_LEAVE_MINUTES.b" /></th>
				<th><!--早退分钟数--><spring:message code="ess.viewArPersonalYearList.EARLY_LEAVE_MINUTES.b" /></th>
				<th><!--无薪假天数--><spring:message code="ess.viewArPersonalYearList.UNPAIDLEAVE_DAYS.b" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewPaArSummarySearchList}" var="PST" varStatus="i">
				<tr>
					<td style="text-align: center">	${i.count}</td>
					<td style="text-align: center">	${PST.EMPID}</td>
					<td style="text-align: center">	${PST.LOCAL_NAME}</td>
					<td style="text-align: center">	${PST.DEPT_NAME}</td>
					<td style="text-align: center">	${PST.POST_GRADE}</td>
					<td style="text-align: center">	${PST.DATE_STARTED}</td>
					<td style="text-align: center">	${PST.END_PROBATION_DATE}</td>
					<td style="text-align: center">	${PST.DATE_LEFT}</td>
					<td style="text-align: center">	${PST.PROBATION_DAYS}</td>
					<td style="text-align: center">	${PST.REGULAR_DAYS}</td>
					<td style="text-align: center">	${PST.WORK_SCHEDULE_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_NIGHT_SHIFT_COUNT}</td>
					<td style="text-align: center">	${PST.NIGHT_SHIFT_COUNT}</td>
					<td style="text-align: center">	${PST.PROB_WORKDAYOT150_HOURS}</td>
					<td style="text-align: center">	${PST.WORKDAYOT150_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_WORKDAYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.WORKDAYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_WORKDAYOT210_HOURS}</td>
					<td style="text-align: center">	${PST.WORKDAYOT210_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_SATURDAYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.SATURDAYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_SATURDAYOT270_HOURS}</td>
					<td style="text-align: center">	${PST.SATURDAYOT270_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_WEEKLYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.WEEKLYOT200_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_WEEKLYOT270_HOURS}</td>
					<td style="text-align: center">	${PST.WEEKLYOT270_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_HOLIDAYOT300_HOURS}</td>
					<td style="text-align: center">	${PST.HOLIDAYOT300_HOURS}</td>
					<td style="text-align: center">	${PST.PROB_HOLIDAYOT390_HOURS}</td>
					<td style="text-align: center">	${PST.HOLIDAYOT390_HOURS}</td>
					<td style="text-align: center">	${PST.RESIGN_ANNUALLEAVE_PAY_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_PERSONALLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.PERSONALLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_CHILD_SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.CHILD_SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_LONG_SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.LONG_SICKLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.MATERNITYLEAVE_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_ABSENTEEISM_DAYS}</td>
					<td style="text-align: center">	${PST.ABSENTEEISM_DAYS}</td>
					<td style="text-align: center">	${PST.PROB_LATE_ARRIVE_MINUTES}</td>
					<td style="text-align: center">	${PST.LATE_ARRIVE_MINUTES}</td>
					<td style="text-align: center">	${PST.PROB_EARLY_LEAVE_MINUTES}</td>
					<td style="text-align: center">	${PST.EARLY_LEAVE_MINUTES}</td>
					<td style="text-align: center">	${PST.UNPAIDLEAVE_DAYS}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


</div>
