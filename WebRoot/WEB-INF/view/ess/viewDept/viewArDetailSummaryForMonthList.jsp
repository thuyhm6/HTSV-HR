<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){	
    $("#viewArDetailSummaryForMonthList_Serch",navTab.getCurrentPanel()).click(function(){
    	if($("#seach_MONTH",navTab.getCurrentPanel()).val() == null || $("#seach_MONTH",navTab.getCurrentPanel()).val()==""){
    		alertMsg.error("请选择年月");
    		return false;
    	}
		$("#viewArDetailSummaryForMonthList",navTab.getCurrentPanel()).submit();
   });
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
	     "fixedColumns":{leftColumns: 4},
	     "scrollY": $(document.body).height() - 380,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
       "oLanguage": {//多语言配置
       	"sProcessing": "正在加载中......",
           "sZeroRecords": "查询不到相关数据！",
           "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
           "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
           "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
           "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
           "sInfoFiltered": "(从 _MAX_ 条记录过滤)",
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
</script>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewArDetailSummaryForMonthList?firstflag=N" 
		method="post" id="viewArDetailSummaryForMonthList" name="viewArDetailSummaryForMonthList" > 
		<div class="searchBar">
			<table class="searchContent">
				<tr> 
					<td>
						<!--年月 -->
						<spring:message code="ess.infoApply.YEAR_MONTH" />
					</td>
					<td>
			 			<input type="text"  id="seach_MONTH" name="seach_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM'})"value="${MONTH}" />
					</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						 <li><a class="buttonActive" id="viewArDetailSummaryForMonthList_Serch" href="#"><span><spring:message code="org.title.SELECT"/><!-- 查询 --></span></a></li>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent"  >
	<table class="orderList" width="200%" nowrapTD="false">
			<thead>
				<tr>
					<th><spring:message code="org.title.LOCAL_NAME"/><!-- 姓名 --></th>
					<th><spring:message code="ess.infoApply.DEPT_NAME"/><!-- 部门名 --></th>
					<th><spring:message code="org.title.EMPID"/><!-- 社号 --></th>
					<th><spring:message code="org.title.POST_GRADE_NAME"/><!-- 职级 --></th>
					<th><spring:message code="org.title.DUTY_NO"/><!-- 岗位 --></th>
					<th><spring:message code="ess.empInfo.entry_date"/><!-- 入职日期 --></th>
					<th><spring:message code="ess.empInfo.leaveDate"/><!-- 离职日期 --></th>
					<th>1</th>
					<th>2</th>
					<th>3</th>
					<th>4</th>
					<th>5</th>
					<th>6</th>
					<th>7</th>
					<th>8</th>
					<th>9</th>
					<th>10</th>
					<th>11</th>
					<th>12</th>
					<th>13</th>
					<th>14</th>
					<th>15</th>
					<th>16</th>
					<th>17</th>
					<th>18</th>
					<th>19</th>
					<th>20</th>
					<th>21</th>
					<th>22</th>
					<th>23</th>
					<th>24</th>
					<th>25</th>
					<th>26</th>
					<th>27</th>
					<th>28</th>
					<c:if test="${DAY_OF_MONTH ge 29 }">
					<th>29</th>
					</c:if>
					<c:if test="${DAY_OF_MONTH ge 30 }">
					<th>30</th>
					</c:if>
					<c:if test="${DAY_OF_MONTH ge 31 }">
					<th>31</th>
					</c:if>
					<th><spring:message code="ess.empInfo.leaveDate"/><!-- 总工时 --></th><!-- 
					<th>1.5倍</th>
					<th>2倍</th>
					<th>3倍</th>
					<th>夜5</th>
					<th>夜8</th>
					<th>事假</th>
					<th>病假</th>
					<th>旷工</th>
					<th>产假</th>
					<th>有薪假</th>
					<th>身份证号</th>
					<th>事假调休</th>
					<th>事假合计</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewArDetailSummaryForMonthList}" var="item" varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">
						${item.LOCAL_NAME }
						</td>
						<td  style="text-align: center" >
						${item.DEPTNAME }
						</td>
						<td  style="text-align: center" >
						${item.EMPID }
						</td>
						<td  style="text-align: center" >
						${item.POST_GRADE_NAME }
						</td> 
						<td  style="text-align: center" >
						${item.DUTY_NAME }
						</td>
						<td  style="text-align: center" >
						${item.DATE_STARTED }
						</td>
						<td  style="text-align: center" >
						${item.DATE_LEFT }
						</td>
						<td  style="text-align: center" >
						${item.DAY_1 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_2 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_3 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_4 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_5 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_6 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_7 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_8 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_9 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_10 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_11 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_12 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_13 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_14 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_15 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_16 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_17 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_18 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_19 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_20 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_21 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_22 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_23 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_24 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_25 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_26 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_27 }
						</td>
						<td  style="text-align: center" >
						${item.DAY_28 }
						</td>
						<c:if test="${DAY_OF_MONTH ge 29 }">
						<td  style="text-align: center" >
						${item.DAY_29 }
						</td>
						</c:if>
						<c:if test="${DAY_OF_MONTH ge 30 }">
						<td  style="text-align: center" >
						${item.DAY_30 }
						</td>
						</c:if>
						<c:if test="${DAY_OF_MONTH ge 31 }">
						<td  style="text-align: center" >
						${item.DAY_31 }
						</td>
						</c:if>	
						<td  style="text-align: center" >
						${item.GONGSHI }
						</td>
						<%-- <td  style="text-align: center" >
						${item.NORMAL_OT }
						</td>
						<td  style="text-align: center" >
						${item.WEEKEND_OT }
						</td>
						<td  style="text-align: center" >
						${item.HOLIDAY_OT }
						</td>
						<td  style="text-align: center" >
						${item.YE_5 }
						</td>
						<td  style="text-align: center" >
						${item.YE_8 }
						</td>
						<td  style="text-align: center" >
						${item.CASUAL_LEAVE }
						</td>
						<td  style="text-align: center" >
						${item.SICK_LEAVE }
						</td>
						<td  style="text-align: center" >
						${item.ABSENTEEISM }
						</td>
						<td  style="text-align: center" >
						${item.MATERNITY_LEAVE }
						</td>
						<td  style="text-align: center" >
						${item.MATERNITY_LEAVE }
						</td>
						<td  style="text-align: center" >
						${item.IDCARD_NO }
						</td>
						<td  style="text-align: center" >
						${item.PAY_LEAVE }
						</td>
						<td  style="text-align: center" >
						${item.CASUAL_LEAVE_CNT }
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>