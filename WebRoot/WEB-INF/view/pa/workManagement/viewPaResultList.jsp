<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(
		function() {

			$("#viewPaResultList_Search",navTab.getCurrentPanel()).click(function(){
				$("#viewPaResultList",navTab.getCurrentPanel()).submit();
			});
			$("#viewPaResult_orderList", navTab.getCurrentPanel()).dataTable( {
				"bPaginate": true,    //分页
			    "bAutoWidth":false,//表格宽度自动变化
			    "bProcessing":true,
			    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
				"bLengthChange": true,  //按多少条记录显示下拉框
				"iDisplayLength": 20, //默认每页显示的记录数
				"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		     	"searching": true,//本地搜索
				"bSort": true,   //排序功能
				"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite":true,
			     "orderClasses": false,
			     "order":[],//初始化不用自动排序
			     "scrollY": $(document.body).height() - 290,
			     "scrollX": $(document.body).width(),
			     "scrollCollapse": false,
			     "deferRender":true,
		         "columnDefs": [//使某个字段不支持快速检索
		                        { "searchable": false, "targets": [0] }
		                      ],
				"fixedColumns" : {
					leftColumns : 9
				},
				"oLanguage" : {//多语言配置
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
				}
			//多语言配置
					});
			$("#viewPaResult_orderList tbody", navTab.getCurrentPanel()).on(
					'click',
					'tr',
					function() {
						$(this).toggleClass('selected');
						$("#viewPaResult_orderList", navTab.getCurrentPanel()).dataTable().api().fixedColumns().update();
					});
			$("#viewPaResult_orderList_HAE", navTab.getCurrentPanel()).dataTable( {
				"bPaginate": true,    //分页
			    "bAutoWidth":false,//表格宽度自动变化
			    "bProcessing":true,
			    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
				"bLengthChange": true,  //按多少条记录显示下拉框
				"iDisplayLength": 20, //默认每页显示的记录数
				"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		     	"searching": true,//本地搜索
				"bSort": true,   //排序功能
				"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite":true,
			     "orderClasses": false,
			     "order":[],//初始化不用自动排序
			     "scrollY": $(document.body).height() - 290,
			     "scrollX": $(document.body).width(),
			     "scrollCollapse": false,
			     "deferRender":true,
		         "columnDefs": [//使某个字段不支持快速检索
		                        { "searchable": false, "targets": [0] }
		                      ],
				"fixedColumns" : {
					leftColumns : 9
				},
				"oLanguage" : {//多语言配置
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
				}
			//多语言配置
					});
			$("#viewPaResult_orderList_HAE tbody", navTab.getCurrentPanel()).on(
					'click',
					'tr',
					function() {
						$(this).toggleClass('selected');
						$("#viewPaResult_orderList_HAE", navTab.getCurrentPanel()).dataTable().api().fixedColumns().update();
					});
		});

function pa0134_searchPop(flag) {

	var name=encodeURI(encodeURI($("#seach_KEY",navTab.getCurrentPanel()).val()));
	$("#pa0134_searchPop",navTab.getCurrentPanel()).attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if(flag == 'onkeyup')
		$("#pa0134_searchPop",navTab.getCurrentPanel()).click();

}
function downloadExl(url) {
	$('#viewPaResultList').attr("action", url);
	$('#viewPaResultList').attr("onsubmit", '');
	$('#viewPaResultList').submit();
	$('#viewPaResultList')
			.attr("action", '/pa/workManagement/viewPaResultList');
	$('#viewPaResultList').attr("onsubmit", 'return navTabSearch(this);');
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
}
</script>
<div class="pageHeader">

	<form id="viewPaResultList" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaResultList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input
						type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)pa0134_searchPop('onkeyup');"/>
						</td>
						<td>
						<a class="btnLook" id="pa0134_searchPop" onclick="pa0134_searchPop()" href="#" lookupGroup="person">
						</a>
					</td>
						<td colspan="3">
							<input id="dwz.person.empInfo" name="empInfo" type="text" readonly lookupGroup="person" size="60" value="${empInfo}"/>
							<%-- ${empInfoShow } --%>
						</a>
					</td>
					</tr><tr>
					<td><!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }" value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:when>
									<c:otherwise>
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }"  value="${paySchedule.PAY_SCHEDULE_NO }" >${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td><!--部门--><spring:message code="ess.infoApply.DEPT" />:</td>
					<td>
					<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="ar" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
				</tr>

			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewPaResultList_Search"  > <span><spring:message
									code="public.title.search" /> </span> </a>
					</li>
					<li>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=245')" href="#" >
								<span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span>
							</a>
						</c:if>
						<c:if test="${LoginUser.cpnyId eq 'HAE'}">
							<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=270')" href="#" >
								<span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span>
							</a>
						</c:if>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
	<div class="pageContent">
			Total:${fn:length(viewPaResultList)}
			<table class="orderList " id="viewPaResult_orderList" width="15000">
					<thead id="viewPaResult_orderList_Tr">
						<tr>
							<th>
								No
							</th>
							<th>
								<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
							</th>
							<th>
								<!--姓名--><spring:message code="ess.infoApply.NAME" />
							</th>
							<th>
								<!--部门--><spring:message code="ess.infoApply.DEPT" />
							</th>
							<th>
								<!--职群--><spring:message code="hrm.empinfo.POST_FAMILY" />
							</th>
							<th>
								<!--职级--><spring:message code="hrm.contract.Rank" />
							</th>
							<th>
								<!--职责--><spring:message code="ess.trans.title.dutyName" />
							</th>
							<th>
								<!--入社日期--><spring:message code="ess.empInfo.date_of_agency" />
							</th>
							<th>
								<!--离职日期--><spring:message code="ess.trans.title.resignDate" />
							</th>
							<th>
								<!--试用天数--><spring:message code="ess.viewArPersonalYearList.PROBATION_DAYS.b" />
							</th>
							<th>
								<!--正式天数--><spring:message code="ess.viewArPersonalYearList.REGULAR_DAYS.b" />
							</th>
							<th>
								<!--应出勤天数--><spring:message code="ess.infoApply.yingchuqintianshu" />
							</th>
							<th>
								<!--试用期夜班次数--><spring:message code="ess.viewArPersonalYearList.PROB_NIGHT_SHIFT_COUNT.b" />
							</th>
							<th>
								<!--正式夜班次数--><spring:message code="ess.viewArPersonalYearList.NIGHT_SHIFT_COUNT.b" />
							</th>
							<th>
								<!--试用平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT150_HOURS.b" />
							</th>
							<th>
								<!--平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT150_HOURS.b" />
							</th>
							<th>
								<!--试用平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT210_HOURS.b" />
							</th>
							<th>
								<!--平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT210_HOURS.b" />
							</th>
							<th>
								<!--试用周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT270_HOURS.b" />
							</th>
							<th>
								<!--周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT270_HOURS.b" />
							</th>
							<th>
								<!--试用周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT200_HOURS.b" />
							</th>
							<th>
								<!--周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT270_HOURS.b" />
							</th>
							<th>
								<!--周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT270_HOURS.b" />
							</th>
							<th>
								<!--试用节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT300_HOURS.b" />
							</th>
							<th>
								<!--节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT300_HOURS.b" />
							</th>
							<th>
								<!--试用节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT390_HOURS.b" />
							</th>
							<th>
								<!--节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT390_HOURS.b" />
							</th>
							<th>
								<!--离职年假补偿天数--><spring:message code="ess.viewArPersonalYearList.RESIGN_ANNUALLEAVE_PAY_DAYS.b" />
							</th>
							<th>
								<!--试用事假天数--><spring:message code="ess.viewArPersonalYearList.PROB_PERSONALLEAVE_DAYS.b" />
							</th>
							<th>
								<!--事假天数--><spring:message code="ess.viewArPersonalYearList.PERSONALLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用一般病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--一般病假天数--><spring:message code="ess.viewArPersonalYearList.SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用孩子病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_CHILD_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--孩子病假天数--><spring:message code="ess.viewArPersonalYearList.CHILD_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用长期病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_LONG_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--长期病假天数--><spring:message code="ess.viewArPersonalYearList.LONG_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--产假天数--><spring:message code="ess.viewArPersonalYearList.MATERNITYLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用旷工天数--><spring:message code="ess.viewArPersonalYearList.PROB_ABSENTEEISM_DAYS.b" />
							</th>
							<th>
								<!--旷工天数--><spring:message code="ess.viewArPersonalYearList.ABSENTEEISM_DAYS.b" />
							</th>
							<th>
								<!--试用迟到分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_LATE_ARRIVE_MINUTES.b" />
							</th>
							<th>
								<!--迟到分钟数--><spring:message code="ess.viewArPersonalYearList.LATE_ARRIVE_MINUTES.b" />
							</th>
							<th>
								<!--试用早退分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_EARLY_LEAVE_MINUTES.b" />
							</th>
							<th>
								<!--早退分钟数--><spring:message code="ess.viewArPersonalYearList.EARLY_LEAVE_MINUTES.b" />
							</th>
							<th>
								<!--无薪假天数--><spring:message code="ess.viewArPersonalYearList.UNPAIDLEAVE_DAYS.b" />
							</th>
							<th>
								<!--基本工资--><spring:message code="pa.salary.canShu.jibengongzi" />
							</th>
							<th>
								<!--职责津贴--><spring:message code="pa.insurance.title.zhizejintie" />
							</th>
							<th>
								<!--交通补助--><spring:message code="pa.viewPaResultList.JIAOTONGBUZHU.C" />
							</th>
							<th>
								<!--工龄补助--><spring:message code="pa.paSummary.LONG_ATTENDANCE.b" />
							</th>
							<th>
								<!--满勤奖--><spring:message code="pa.viewPaResultList.MANQINJIANG.C" />
							</th>
							<th>
								<!--夜班补助--><spring:message code="pa.paSummary.NIGHTSHIFT_ALLOWANCE.b" />
							</th>
							<th>
								<!--评价奖金--><spring:message code="pa.paSummary.EVALUATION_BONUS.b" />
							</th>
							<!--<th>
								试用加班小时工资<spring:message code="pa.paSummary.PROB_OT_HOUR_SALARY.b" />
							</th>
							<th>
								加班小时工资<spring:message code="pa.paSummary.OT_HOUR_SALARY.b" />
							</th>-->
							<th>
								<!--试用平日加班费150%--><spring:message code="pa.paSummary.PROB_WORKDAYOT150_FEE.b" />
							</th>
							<th>
								<!--平日加班费150%--><spring:message code="pa.paSummary.WORKDAYOT150_FEE.b" />
							</th>
							<th>
								<!--试用平日加班费200%--><spring:message code="pa.paSummary.PROB_WORKDAYOT200_FEE.b" />
							</th>
							<th>
								<!--平日加班费200%--><spring:message code="pa.paSummary.WORKDAYOT200_FEE.b" />
							</th>
							<th>
								<!--试用平日加班费210%--><spring:message code="pa.paSummary.PROB_WORKDAYOT210_FEE.b" />
							</th>
							<th>
								<!--平日加班费210%--><spring:message code="pa.paSummary.WORKDAYOT210_FEE.b" />
							</th>
							<th>
								<!--平日加班费合计--><spring:message code="pa.paSummary.WORKDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用周六加班费200%--><spring:message code="pa.paSummary.PROB_SATURDAYOT200_FEE.b" />
							</th>
							<th>
								<!--周六加班费200%--><spring:message code="pa.paSummary.SATURDAYOT200_FEE.b" />
							</th>
							<th>
								<!--试用周六加班费270%--><spring:message code="pa.paSummary.PROB_SATURDAYOT270_FEE.b" />
							</th>
							<th>
								<!--周六加班费270%--><spring:message code="pa.paSummary.SATURDAYOT270_FEE.b" />
							</th>
							<th>
								<!--周六加班费合计--><spring:message code="pa.paSummary.SATURDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用周末加班费200%--><spring:message code="pa.paSummary.PROB_WEEKLYOT200_FEE.b" />
							</th>
							<th>
								<!--周末加班费200%--><spring:message code="pa.paSummary.WEEKLYOT200_FEE.b" />
							</th>
							<th>
								<!--试用周末加班费270%--><spring:message code="pa.paSummary.PROB_WEEKLYOT270_FEE.b" />
							</th>
							<th>
								<!--周末加班费270%--><spring:message code="pa.paSummary.WEEKLYOT270_FEE.b" />
							</th>
							<th>
								<!--周末加班费合计--><spring:message code="pa.paSummary.WEEKLYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用节日加班费300%--><spring:message code="pa.paSummary.PROB_HOLIDAYOT300_FEE.b" />
							</th>
							<th>
								<!--节日加班费300%--><spring:message code="pa.paSummary.HOLIDAYOT300_FEE.b" />
							</th>
							<th>
								<!--试用节日加班费390%--><spring:message code="pa.paSummary.PROB_HOLIDAYOT390_FEE.b" />
							</th>
							<th>
								<!--节日加班费390%--><spring:message code="pa.paSummary.HOLIDAYOT390_FEE.b" />
							</th>
							<th>
								<!--节日加班费合计--><spring:message code="pa.paSummary.HOLIDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--加班费合计--><spring:message code="pa.paSummary.OT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--年假清算补偿--><spring:message code="pa.paSummary.ANNUALLEAVE_CLEAR_PAY.b" />
							</th>
							<th>
								<!--离职年假补偿--><spring:message code="pa.paSummary.RESIGN_ANNUALLEAVE_PAY.b" />
							</th>
							<th>
								<!--试用事假扣除--><spring:message code="pa.paSummary.PROB_PERSONALLEAVE_DEDUCT.b" />
							</th>
							<th>
								<!--事假扣除--><spring:message code="pa.viewPaResultList.SHIJIAKOUCHU.C" />
							</th>
							<th>
								<!--试用病假扣除--><spring:message code="pa.paSummary.PROB_SICKLEAVE_DEDUCT.b" />
							</th>
							<th>
								<!--病假扣除--><spring:message code="pa.viewPaResultList.BINGJIAKOUCHU.C" />
							</th>
							<th>
								<!--产假扣除--><spring:message code="pa.viewPaResultList.CHANJIAKOUCHU.C" />
							</th>
							<th>
								<!--试用旷工扣除--><spring:message code="pa.paSummary.PROB_ABSENT_DEDUCT.b" />
							</th>
							<th>
								<!--旷工扣除--><spring:message code="pa.viewPaResultList.KUANGGONGKOUCHU.C" />
							</th>
							<th>
								<!--试用迟到早退扣除--><spring:message code="pa.paSummary.PROB_LATEEARLY_DEDUCT.b" />
							</th>
							<th>
								<!--迟到早退扣除--><spring:message code="pa.viewPaResultList.CHIDAOZAOTUIKOUCHU.C" />
							</th>
							<th>
								<!--考勤扣除合计--><spring:message code="pa.viewPaResultList.KAOQINKOUCHUHEJI.C" />
							</th>
							<th>
								<!--税前工资--><spring:message code="pa.viewPaResultList.SHUIQIANGONGZI.C" />
							</th>
							<th>
								<!--个税税率--><spring:message code="pa.paSummary.PT_TAX_RATE.b" />
							</th>
							<th>
								<!--速算扣除数--><spring:message code="pa.viewResultConfirmSonList.SUSUANKOUCHUSHU.b" />
							</th>
							<th>
								<!--加班免税项目--><spring:message code="pa.paSummary.OT_SALARY_NOT_PIT.b" />
							</th>
							<th>
								<!--应纳税所得额--><spring:message code="pa.viewResultConfirmSonList.YINGNASHUISUODEE.b" />
							</th>
							<th>
								<!--个人所得税--><spring:message code="ess.viewpersonalpainfo.gerensuodeshui" />
							</th>
							<th>
								<!--个税年度核算--><spring:message code="pa.paSummary.PIT_SETTLEMENT.b" />
							</th>
							<th>
								<!--退社失业保险补偿金--><spring:message code="pa.paSummary.SEVERANCE_ALLOWANCE.b" />
							</th>
							<th>
								<!--实得工资--><spring:message code="pa.viewPaResultList.SHIDEGONGZI.C" />
							</th>
							<th>
								<!--工会费--><spring:message code="pa.paSummary.TRADE_UNION.b" />
							</th>
							<th>
								<!--工会费(公司)--><spring:message code="pa.paSummary.TRADE_UNION_CORP.b" />
							</th>
							<th>
								<!--保险基数--><spring:message code="pa.paSummary.INSURANCE_BASE.b" />
							</th>
							<th>
								<!--社会保险（个人）--><spring:message code="pa.viewResultConfirmSonList.SHEHUIBAOXIANGEREN.b" />
							</th>
							<th>
								<!--医疗保险（个人）--><spring:message code="pa.viewResultConfirmSonList.YILIAOBAOXIANGEREN.b" />
							</th>
							<th>
								<!--失业保险（个人）--><spring:message code="pa.viewResultConfirmSonList.SHIYEBAOXIANGEREN.b" />
							</th>
							<th>
								<!--个人社保合计--><spring:message code="pa.viewResultConfirmSonList.GERENSHEBAOHEJI.b" />
							</th>
							<th>
								<!--社会保险（公司）--><spring:message code="pa.paSummary.SOCIAL_INS_COMPANY.b" />
							</th>
							<th>
								<!--医疗保险（公司）--><spring:message code="pa.paSummary.MEDICAL_INS_COMPANY.b" />
							</th>
							<th>
								<!--失业保险（公司）--><spring:message code="pa.paSummary.UNEMPLOYMENT_INS_COMPANY.b" />
							</th>
							<th>
								<!--公司社保合计--><spring:message code="pa.paSummary.COMPANY_INS_TOTAL.b" />
							</th>
							<th>
								<!--行业病保险（公司）--><spring:message code="pa.paSummary.SOCIAL_SICK_INS_COMPANY.b" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewPaResultList}" var="PST" varStatus="i">
							<tr>
								<td style="text-align: center">
									${i.count}
								</td>
								<td style="text-align: center">
									${PST.EMPID}
								</td>
								<td style="text-align: center">
									${PST.LOCAL_NAME}
								</td>
								<td style="text-align: center">
									${PST.DEPT_NAME}
								</td>
								<td style="text-align: center">
									${PST.POST_FAMILY_NAME}
								</td>
								<td style="text-align: center">
									${PST.POST_GRADE_NAME}
								</td>
								<td style="text-align: center">
									${PST.POSITION_NAME}
								</td>
								<td style="text-align: center">
									${PST.DATE_STARTED}
								</td>
								<td style="text-align: center">
									${PST.DATE_LEFT}
								</td>
								<td style="text-align: center">
									${PST.PROBATION_DAYS}
								</td>
								<td style="text-align: center">
									${PST.REGULAR_DAYS}
								</td>
								<td style="text-align: center">
									${PST.WORK_SCHEDULE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.RESIGN_ANNUALLEAVE_PAY_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.MATERNITYLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.PROB_EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.UNPAIDLEAVE_DAYS}
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.BASIC_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.POSITION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRANSPORTATION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LONG_ATTENDANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.FULL_ATTENDANCE_BONUS}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.NIGHTSHIFT_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.EVALUATION_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_OT_HOUR_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_HOUR_SALARY}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ANNUALLEAVE_CLEAR_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.RESIGN_ANNUALLEAVE_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MATERNITYLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ATT_DEDUCT_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INCOME_BEFORE_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PT_TAX_RATE}" pattern="#,##0.0#" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.QUICK_DEDUCTION_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_SALARY_NOT_PIT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PIT_SETTLEMENT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SEVERANCE_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.REAL_WAGES}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION_CORP}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INSURANCE_BASE}"
										pattern="#,##0" />
									 <!-- 保险基数 -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 社会保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 医疗保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_PERSONAL}"
										pattern="#,##0" />
									<!---- 失业保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}" pattern="#,##0" />
									<!---- 个人社保合计-->
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_COMPANY}"
										pattern="#,##0" />
									<!-----社会保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_COMPANY}"
										pattern="#,##0" />
									<!--  ---医疗保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_COMPANY}"
										pattern="#,##0" />
									<!----失业保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.COMPANY_INS_TOTAL}"
										pattern="#,##0" />
									<!----公司社保合计-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_SICK_INS_COMPANY}"
										pattern="#,##0" />
									<!----行业病保险(公司)-->
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot> 
					<c:forEach items="${viewPaResultListSum}" var="PST" varStatus="i">
							<tr bgcolor="#B5B5B5">
								<td style="text-align: center" colspan="9">
									<!--合计--><spring:message code="ess.viewpersonalpainfo.heji" />
								</td>
								<td style="text-align: center">
									${PST.PROBATION_DAYS}
								</td>	
								<td style="text-align: center">
									${PST.REGULAR_DAYS}
								</td>
								<td style="text-align: center">
									${PST.WORK_SCHEDULE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.RESIGN_ANNUALLEAVE_PAY_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.MATERNITYLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.PROB_EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.UNPAIDLEAVE_DAYS}
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.BASIC_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.POSITION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRANSPORTATION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LONG_ATTENDANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.FULL_ATTENDANCE_BONUS}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.NIGHTSHIFT_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.EVALUATION_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_OT_HOUR_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_HOUR_SALARY}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ANNUALLEAVE_CLEAR_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.RESIGN_ANNUALLEAVE_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MATERNITYLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ATT_DEDUCT_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INCOME_BEFORE_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PT_TAX_RATE}" pattern="#,##0.0#" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.QUICK_DEDUCTION_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_SALARY_NOT_PIT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PIT_SETTLEMENT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SEVERANCE_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.REAL_WAGES}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION_CORP}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INSURANCE_BASE}"
										pattern="#,##0" />
									 <!-- 保险基数 -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_PERSONAL}"
										pattern="#,##0" />
									 <!-- 社会保险(个人) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_PERSONAL}"
										pattern="#,##0" />
									 <!-- 医疗保险(个人) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- 失业保险(个人) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}" pattern="#,##0" />
									<!-- 个人社保合计 -->
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_COMPANY}"
										pattern="#,##0" />
									<!-- 社会保险(公司) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_COMPANY}"
										pattern="#,##0" />
									  <!-- 医疗保险(公司) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_COMPANY}"
										pattern="#,##0" />
									<!-- 失业保险(公司) -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.COMPANY_INS_TOTAL}"
										pattern="#,##0" />
									<!-- 公司社保合计 -->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_SICK_INS_COMPANY}"
										pattern="#,##0" />
									<!----行业病保险(公司)-->
								</td>
							</tr>
						</c:forEach>
					</tfoot>
				</table>
	</div>
</c:if>
<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	<div class="pageContent">
			Total:${fn:length(viewPaResultList)}
			<table class="orderList " id="viewPaResult_orderList_HAE" width="16000">
					<thead id="viewPaResult_orderList_Tr">
						<tr>
							<th>
								No
							</th>
							<th>
								<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
							</th>
							<th>
								<!--姓名--><spring:message code="ess.infoApply.NAME" />
							</th>
							<th>
								<!--部门--><spring:message code="ess.infoApply.DEPT" />
							</th>
							<th>
								<!--职群--><spring:message code="hrm.empinfo.POST_FAMILY" />
							</th>
							<th>
								<!--职级--><spring:message code="hrm.contract.Rank" />
							</th>
							<th>
								<!--职责--><spring:message code="ess.trans.title.dutyName" />
							</th>
							<th>
								<!--入社日期--><spring:message code="ess.empInfo.date_of_agency" />
							</th>
							<th>
								<!--离职日期--><spring:message code="ess.trans.title.resignDate" />
							</th>
							<th>
								<!--试用天数--><spring:message code="ess.viewArPersonalYearList.PROBATION_DAYS.b" />
							</th>
							<th>
								<!--正式天数--><spring:message code="ess.viewArPersonalYearList.REGULAR_DAYS.b" />
							</th>
							<th>
								<!--应出勤天数--><spring:message code="ess.infoApply.yingchuqintianshu" />
							</th>
							<!--<th>
								试用期夜班次数<spring:message code="ess.viewArPersonalYearList.PROB_NIGHT_SHIFT_COUNT.b" />
							</th>
							<th>
								正式夜班次数<spring:message code="ess.viewArPersonalYearList.NIGHT_SHIFT_COUNT.b" />
							</th>-->
							<th>
								<!--试用平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT150_HOURS.b" />
							</th>
							<th>
								<!--平日加班150%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT150_HOURS.b" />
							</th>
							<th>
								<!--试用平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--平日加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WORKDAYOT210_HOURS.b" />
							</th>
							<th>
								<!--平日加班210%总时数--><spring:message code="ess.viewArPersonalYearList.WORKDAYOT210_HOURS.b" />
							</th>
							<th>
								<!--试用周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--周六加班200%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_SATURDAYOT270_HOURS.b" />
							</th>
							<th>
								<!--周六加班270%总时数--><spring:message code="ess.viewArPersonalYearList.SATURDAYOT270_HOURS.b" />
							</th>
							<th>
								<!--试用周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT200_HOURS.b" />
							</th>
							<th>
								<!--周末加班200%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT200_HOURS.b" />
							</th>
							<th>
								<!--试用周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_WEEKLYOT270_HOURS.b" />
							</th>
							<th>
								<!--周末加班270%总时数--><spring:message code="ess.viewArPersonalYearList.WEEKLYOT270_HOURS.b" />
							</th>
							<th>
								<!--试用节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT300_HOURS.b" />
							</th>
							<th>
								<!--节日加班300%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT300_HOURS.b" />
							</th>
							<th>
								<!--试用节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.PROB_HOLIDAYOT390_HOURS.b" />
							</th>
							<th>
								<!--节日加班390%总时数--><spring:message code="ess.viewArPersonalYearList.HOLIDAYOT390_HOURS.b" />
							</th>
							<th>
								<!--离职年假补偿天数--><spring:message code="ess.viewArPersonalYearList.RESIGN_ANNUALLEAVE_PAY_DAYS.b" />
							</th>
							<th>
								<!--试用事假天数--><spring:message code="ess.viewArPersonalYearList.PROB_PERSONALLEAVE_DAYS.b" />
							</th>
							<th>
								<!--事假天数--><spring:message code="ess.viewArPersonalYearList.PERSONALLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用一般病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--一般病假天数--><spring:message code="ess.viewArPersonalYearList.SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用孩子病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_CHILD_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--孩子病假天数--><spring:message code="ess.viewArPersonalYearList.CHILD_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用长期病假天数--><spring:message code="ess.viewArPersonalYearList.PROB_LONG_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--长期病假天数--><spring:message code="ess.viewArPersonalYearList.LONG_SICKLEAVE_DAYS.b" />
							</th>
							<th>
								<!--产假天数--><spring:message code="ess.viewArPersonalYearList.MATERNITYLEAVE_DAYS.b" />
							</th>
							<th>
								<!--试用旷工天数--><spring:message code="ess.viewArPersonalYearList.PROB_ABSENTEEISM_DAYS.b" />
							</th>
							<th>
								<!--旷工天数--><spring:message code="ess.viewArPersonalYearList.ABSENTEEISM_DAYS.b" />
							</th>
							<th>
								<!--试用迟到分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_LATE_ARRIVE_MINUTES.b" />
							</th>
							<th>
								<!--迟到分钟数--><spring:message code="ess.viewArPersonalYearList.LATE_ARRIVE_MINUTES.b" />
							</th>
							<th>
								<!--试用早退分钟数--><spring:message code="ess.viewArPersonalYearList.PROB_EARLY_LEAVE_MINUTES.b" />
							</th>
							<th>
								<!--早退分钟数--><spring:message code="ess.viewArPersonalYearList.EARLY_LEAVE_MINUTES.b" />
							</th>
							<th>
								<!--无薪假天数--><spring:message code="ess.viewArPersonalYearList.UNPAIDLEAVE_DAYS.b" />
							</th>
							<th>
								<!--基本工资--><spring:message code="pa.salary.canShu.jibengongzi" />
							</th>
							<th>
								<!--职责津贴--><spring:message code="pa.insurance.title.zhizejintie" />
							</th>
							<!--<th>
								交通补助<spring:message code="pa.viewPaResultList.JIAOTONGBUZHU.C" />
							</th>
							<th>
								工龄补助<spring:message code="pa.paSummary.LONG_ATTENDANCE.b" />
							</th>-->
							<th>
								<!--满勤奖--><spring:message code="pa.viewPaResultList.MANQINJIANG.C" />
							</th>
							<!--<th>
								夜班补助<spring:message code="pa.paSummary.NIGHTSHIFT_ALLOWANCE.b" />
							</th>-->
							<th>
								<!--评价奖金--><spring:message code="pa.paSummary.EVALUATION_BONUS.b" />
							</th>
							<th>
								<!--餐补--><spring:message code="pa.paSummary.LUNCH_ALLOWANCE.b" />
							</th>
							<th>
								<!--住房补贴--><spring:message code="pa.paSummary.HOUSING_ALLOWANCE.b" />
							</th>
							<th>
								<!--追加津贴--><spring:message code="pa.paSummary.ADDITIONAL_ALLOWANCE.b" />
							</th>
							<th>
								<!--女员工补助--><spring:message code="pa.paSummary.WOMEN_SPECIAL_ALLOWANCE.b" />
							</th>
							<!--<th>
								试用加班小时工资<spring:message code="pa.paSummary.PROB_OT_HOUR_SALARY.b" />
							</th>
							<th>
								加班小时工资<spring:message code="pa.paSummary.OT_HOUR_SALARY.b" />
							</th>-->
							<th>
								<!--试用平日加班费150%--><spring:message code="pa.paSummary.PROB_WORKDAYOT150_FEE.b" />
							</th>
							<th>
								<!--平日加班费150%--><spring:message code="pa.paSummary.WORKDAYOT150_FEE.b" />
							</th>
							<th>
								<!--试用平日加班费200%--><spring:message code="pa.paSummary.PROB_WORKDAYOT200_FEE.b" />
							</th>
							<th>
								<!--平日加班费200%--><spring:message code="pa.paSummary.WORKDAYOT200_FEE.b" />
							</th>
							<th>
								<!--试用平日加班费210%--><spring:message code="pa.paSummary.PROB_WORKDAYOT210_FEE.b" />
							</th>
							<th>
								<!--平日加班费210%--><spring:message code="pa.paSummary.WORKDAYOT210_FEE.b" />
							</th>
							<th>
								<!--平日加班费合计--><spring:message code="pa.paSummary.WORKDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用周六加班费200%--><spring:message code="pa.paSummary.PROB_SATURDAYOT200_FEE.b" />
							</th>
							<th>
								<!--周六加班费200%--><spring:message code="pa.paSummary.SATURDAYOT200_FEE.b" />
							</th>
							<th>
								<!--试用周六加班费270%--><spring:message code="pa.paSummary.PROB_SATURDAYOT270_FEE.b" />
							</th>
							<th>
								<!--周六加班费270%--><spring:message code="pa.paSummary.SATURDAYOT270_FEE.b" />
							</th>
							<th>
								<!--周六加班费合计--><spring:message code="pa.paSummary.SATURDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用周末加班费200%--><spring:message code="pa.paSummary.PROB_WEEKLYOT200_FEE.b" />
							</th>
							<th>
								<!--周末加班费200%--><spring:message code="pa.paSummary.WEEKLYOT200_FEE.b" />
							</th>
							<th>
								<!--试用周末加班费270%--><spring:message code="pa.paSummary.PROB_WEEKLYOT270_FEE.b" />
							</th>
							<th>
								<!--周末加班费270%--><spring:message code="pa.paSummary.WEEKLYOT270_FEE.b" />
							</th>
							<th>
								<!--周末加班费合计--><spring:message code="pa.paSummary.WEEKLYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--试用节日加班费300%--><spring:message code="pa.paSummary.PROB_HOLIDAYOT300_FEE.b" />
							</th>
							<th>
								<!--节日加班费300%--><spring:message code="pa.paSummary.HOLIDAYOT300_FEE.b" />
							</th>
							<th>
								<!--试用节日加班费390%--><spring:message code="pa.paSummary.PROB_HOLIDAYOT390_FEE.b" />
							</th>
							<th>
								<!--节日加班费390%--><spring:message code="pa.paSummary.HOLIDAYOT390_FEE.b" />
							</th>
							<th>
								<!--节日加班费合计--><spring:message code="pa.paSummary.HOLIDAYOT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--加班费合计--><spring:message code="pa.paSummary.OT_FEE_TOTAL.b" />
							</th>
							<th>
								<!--加班福利(扣税)合计--><spring:message code="pa.paSummary.SP_OT_FEE_TAXCAL_TOTAL.b" />
							</th>
							<th>
								<!--加班福利(免税)合计--><spring:message code="pa.paSummary.SP_OT_FEE_TAXFREE_TOTAL.b" />
							</th>
							<th>
								<!--年假清算补偿--><spring:message code="pa.paSummary.ANNUALLEAVE_CLEAR_PAY.b" />
							</th>
							<th>
								<!--离职年假补偿--><spring:message code="pa.paSummary.RESIGN_ANNUALLEAVE_PAY.b" />
							</th>
							<th>
								<!--试用事假扣除--><spring:message code="pa.paSummary.PROB_PERSONALLEAVE_DEDUCT.b" />
							</th>
							<th>
								<!--事假扣除--><spring:message code="pa.viewPaResultList.SHIJIAKOUCHU.C" />
							</th>
							<th>
								<!--试用病假扣除--><spring:message code="pa.paSummary.PROB_SICKLEAVE_DEDUCT.b" />
							</th>
							<th>
								<!--病假扣除--><spring:message code="pa.viewPaResultList.BINGJIAKOUCHU.C" />
							</th>
							<th>
								<!--产假扣除--><spring:message code="pa.viewPaResultList.CHANJIAKOUCHU.C" />
							</th>
							<th>
								<!--试用旷工扣除--><spring:message code="pa.paSummary.PROB_ABSENT_DEDUCT.b" />
							</th>
							<th>
								<!--旷工扣除--><spring:message code="pa.viewPaResultList.KUANGGONGKOUCHU.C" />
							</th>
							<th>
								<!--试用迟到早退扣除--><spring:message code="pa.paSummary.PROB_LATEEARLY_DEDUCT.b" />
							</th>
							<th>
								<!--迟到早退扣除--><spring:message code="pa.viewPaResultList.CHIDAOZAOTUIKOUCHU.C" />
							</th>
							<th>
								<!--考勤扣除合计--><spring:message code="pa.viewPaResultList.KAOQINKOUCHUHEJI.C" />
							</th>
							<th>
								<!--税前工资--><spring:message code="pa.viewPaResultList.SHUIQIANGONGZI.C" />
							</th>
							<th>
								<!--应纳税所得额--><spring:message code="pa.viewResultConfirmSonList.YINGNASHUISUODEE.b" />
							</th>
							<th>
								<!--个税税率--><spring:message code="pa.paSummary.PT_TAX_RATE.b" />
							</th>
							<th>
								<!--速算扣除数--><spring:message code="pa.viewResultConfirmSonList.SUSUANKOUCHUSHU.b" />
							</th>
							<th>
								<!--加班免税项目--><spring:message code="pa.paSummary.OT_SALARY_NOT_PIT.b" />
							</th>
							<th>
								<!--额外15%应纳税--><spring:message code="pa.paSummary.TAXABLE_INCOME15.b" />
							</th>
							<th>
								<!--个人所得税--><spring:message code="ess.viewpersonalpainfo.gerensuodeshui" />
							</th>
							<th>
								<!--个税年度核算--><spring:message code="pa.paSummary.PIT_SETTLEMENT.b" />
							</th>
							<th>
								<!--退社失业保险补偿金--><spring:message code="pa.paSummary.SEVERANCE_ALLOWANCE.b" />
							</th>
							<th>
								<!--实得工资--><spring:message code="pa.viewPaResultList.SHIDEGONGZI.C" />
							</th>
							<th>
								<!--工会费个人--><spring:message code="pa.paSummary.TRADE_UNION.b" />
							</th>
							<th>
								<!--工会费(公司)--><spring:message code="pa.paSummary.TRADE_UNION_CORP.b" />
							</th>
							<th>
								<!--社保基数--><spring:message code="pa.paSummary.INSURANCE_BASE.b" />
							</th>
							<th>
								<!--社会保险（个人）--><spring:message code="pa.viewResultConfirmSonList.SHEHUIBAOXIANGEREN.b" />
							</th>
							<th>
								<!--医疗保险（个人）--><spring:message code="pa.viewResultConfirmSonList.YILIAOBAOXIANGEREN.b" />
							</th>
							<th>
								<!--失业保险（个人）--><spring:message code="pa.viewResultConfirmSonList.SHIYEBAOXIANGEREN.b" />
							</th>
							<th>
								<!--个人社保合计--><spring:message code="pa.viewResultConfirmSonList.GERENSHEBAOHEJI.b" />
							</th>
							<th>
								<!--社会保险（公司）--><spring:message code="pa.paSummary.SOCIAL_INS_COMPANY.b" />
							</th>
							<th>
								<!--医疗保险（公司）--><spring:message code="pa.paSummary.MEDICAL_INS_COMPANY.b" />
							</th>
							<th>
								<!--失业保险（公司）--><spring:message code="pa.paSummary.UNEMPLOYMENT_INS_COMPANY.b" />
							</th>
							<th>
								<!--公司社保合计--><spring:message code="pa.paSummary.COMPANY_INS_TOTAL.b" />
							</th>
							<th>
								<!--行业病保险（公司）--><spring:message code="pa.paSummary.SOCIAL_SICK_INS_COMPANY.b" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewPaResultList}" var="PST" varStatus="i">
							<tr>
								<td style="text-align: center">
									${i.count}
								</td>
								<td style="text-align: center">
									${PST.EMPID}
								</td>
								<td style="text-align: center">
									${PST.LOCAL_NAME}
								</td>
								<td style="text-align: center">
									${PST.DEPT_NAME}
								</td>
								<td style="text-align: center">
									${PST.POST_FAMILY_NAME}
								</td>
								<td style="text-align: center">
									${PST.POST_GRADE_NAME}
								</td>
								<td style="text-align: center">
									${PST.POSITION_NAME}
								</td>
								<td style="text-align: center">
									${PST.DATE_STARTED}
								</td>
								<td style="text-align: center">
									${PST.DATE_LEFT}
								</td>
								<td style="text-align: center">
									${PST.PROBATION_DAYS}
								</td>
								<td style="text-align: center">
									${PST.REGULAR_DAYS}
								</td>
								<td style="text-align: center">
									${PST.WORK_SCHEDULE_DAYS}
								</td>
								<!--<td style="text-align: center">
									${PST.PROB_NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.NIGHT_SHIFT_COUNT}
								</td>-->
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.RESIGN_ANNUALLEAVE_PAY_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.MATERNITYLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.PROB_EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.UNPAIDLEAVE_DAYS}
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.BASIC_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.POSITION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRANSPORTATION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LONG_ATTENDANCE}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.FULL_ATTENDANCE_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.NIGHTSHIFT_ALLOWANCE}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.EVALUATION_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_OT_HOUR_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_HOUR_SALARY}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LUNCH_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOUSING_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ADDITIONAL_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WOMEN_SPECIAL_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SP_OT_FEE_TAXCAL_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SP_OT_FEE_TAXFREE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ANNUALLEAVE_CLEAR_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.RESIGN_ANNUALLEAVE_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MATERNITYLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ATT_DEDUCT_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INCOME_BEFORE_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PT_TAX_RATE}" pattern="#,##0.##" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.QUICK_DEDUCTION_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_SALARY_NOT_PIT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME15}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PIT_SETTLEMENT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SEVERANCE_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.REAL_WAGES}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION_CORP}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INSURANCE_BASE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 社会保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 医疗保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_PERSONAL}"
										pattern="#,##0" />
									<!---- 失业保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}" pattern="#,##0" />
									<!---- 个人社保合计-->
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_COMPANY}"
										pattern="#,##0" />
									<!-----社会保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_COMPANY}"
										pattern="#,##0" />
									<!--  ---医疗保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_COMPANY}"
										pattern="#,##0" />
									<!----失业保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.COMPANY_INS_TOTAL}"
										pattern="#,##0" />
									<!----公司社保合计-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_SICK_INS_COMPANY}"
										pattern="#,##0" />
									<!----行业病保险（公司）-->
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot> 
						<c:forEach items="${viewPaResultListSum}" var="PST" varStatus="i">
						    <tr bgcolor="#B5B5B5">
								<td style="text-align: center" colspan="9">
									<!--合计--><spring:message code="ess.viewpersonalpainfo.heji" />
								</td>
								<td style="text-align: center">
									${PST.PROBATION_DAYS}
								</td>
								<td style="text-align: center">
									${PST.REGULAR_DAYS}
								</td>
								<td style="text-align: center">
									${PST.WORK_SCHEDULE_DAYS}
								</td>
								<!--<td style="text-align: center">
									${PST.PROB_NIGHT_SHIFT_COUNT}
								</td>
								<td style="text-align: center">
									${PST.NIGHT_SHIFT_COUNT}
								</td>-->
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT150_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WORKDAYOT210_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.SATURDAYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT200_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.WEEKLYOT270_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT300_HOURS}
								</td>
								<td style="text-align: center">
									${PST.PROB_HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.HOLIDAYOT390_HOURS}
								</td>
								<td style="text-align: center">
									${PST.RESIGN_ANNUALLEAVE_PAY_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PERSONALLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.CHILD_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.LONG_SICKLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.MATERNITYLEAVE_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.ABSENTEEISM_DAYS}
								</td>
								<td style="text-align: center">
									${PST.PROB_LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.LATE_ARRIVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.PROB_EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.EARLY_LEAVE_MINUTES}
								</td>
								<td style="text-align: center">
									${PST.UNPAIDLEAVE_DAYS}
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.BASIC_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.POSITION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRANSPORTATION_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LONG_ATTENDANCE}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.FULL_ATTENDANCE_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.NIGHTSHIFT_ALLOWANCE}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.EVALUATION_BONUS}" pattern="#,##0" />
								</td>
								<!--<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_OT_HOUR_SALARY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_HOUR_SALARY}" pattern="#,##0" />
								</td>-->
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LUNCH_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOUSING_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ADDITIONAL_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WOMEN_SPECIAL_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT150_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT210_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WORKDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SATURDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT200_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT270_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.WEEKLYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT300_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT390_FEE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.HOLIDAYOT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_FEE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SP_OT_FEE_TAXCAL_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SP_OT_FEE_TAXFREE_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ANNUALLEAVE_CLEAR_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.RESIGN_ANNUALLEAVE_PAY}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONALLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SICKLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MATERNITYLEAVE_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ABSENT_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PROB_LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.LATEEARLY_DEDUCT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.ATT_DEDUCT_TOTAL}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INCOME_BEFORE_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PT_TAX_RATE}" pattern="#,##0.##" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.QUICK_DEDUCTION_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.OT_SALARY_NOT_PIT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TAXABLE_INCOME15}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_TAX}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PIT_SETTLEMENT}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SEVERANCE_ALLOWANCE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.REAL_WAGES}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.TRADE_UNION_CORP}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.INSURANCE_BASE}" pattern="#,##0" />
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 社会保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_PERSONAL}"
										pattern="#,##0" />
									<!-- -- 医疗保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_PERSONAL}"
										pattern="#,##0" />
									<!---- 失业保险(个人)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}" pattern="#,##0" />
									<!---- 个人社保合计-->
								</td>
								
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_INS_COMPANY}"
										pattern="#,##0" />
									<!-----社会保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.MEDICAL_INS_COMPANY}"
										pattern="#,##0" />
									<!--  ---医疗保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_COMPANY}"
										pattern="#,##0" />
									<!----失业保险(公司)-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.COMPANY_INS_TOTAL}"
										pattern="#,##0" />
									<!----公司社保合计-->
								</td>
								<td style="text-align: center">
									<fmt:formatNumber value="${PST.SOCIAL_SICK_INS_COMPANY}"
										pattern="#,##0" />
									<!----行业病保险（公司）-->
								</td>
							</tr>
						</c:forEach>
					</tfoot>
				</table>
	</div>
</c:if>