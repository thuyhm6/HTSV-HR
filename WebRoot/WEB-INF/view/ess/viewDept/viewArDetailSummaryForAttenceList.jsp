<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){	
	 $("#viewArDetailSummaryForAttenceList_Serch",navTab.getCurrentPanel()).click(function(){
	    	if($("#seach_MONTH",navTab.getCurrentPanel()).val() == null || $("#seach_MONTH",navTab.getCurrentPanel()).val()==""){
	    		alertMsg.error("请选择年月");
	    		return false;
	    	}
			$("#viewArDetailSummaryForAttenceList",navTab.getCurrentPanel()).submit();
	   });
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
    	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "fixedColumns":{leftColumns: 4},
	     "scrollY": $(document.body).height() - 300,
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
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewArDetailSummaryForAttenceList?firstflag=N" 
		method="post" id="viewArDetailSummaryForAttenceList" name="viewArDetailSummaryForAttenceList" > 
		<div class="searchBar">
			<table class="searchContent">
			<tr> 
					<td>
						<!-- 年月 --><spring:message code="ess.infoApply.YEAR_MONTH" />
					</td>
					<td>
			 			<input type="text"  id="seach_MONTH" name="seach_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM'})"value="${MONTH}" />
					</td>					
					<td><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewArDetailSummaryForAttenceList1_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewArDetailSummaryForAttenceList1_seachDept" selected="${DEPTNO}"/>
					</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<li>
						 <li><a class="buttonActive" id="viewArDetailSummaryForAttenceList_Serch" href="#"><span><!--查询 --><spring:message code="org.title.SELECT" /></span></a></li>
					</li>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent"  >
	<table class="orderList" width="250%">
			<thead>
				<tr>
					<th>NO</th>
					<th><!--姓名 --><spring:message code="org.title.LOCAL_NAME" /></th>
					<th><!--部门名 --><spring:message code="ess.infoApply.DEPT_NAME" /></th>
					<th><!--社号 --><spring:message code="org.title.EMPID" /></th>
					<th><!-- 职级  --><spring:message code="org.title.POST_GRADE_NAME" /></th>
					<th><!-- 岗位  --><spring:message code="org.title.DUTY_NO" /></th>
					<th><!-- 身份证号  --><spring:message code="hrm.empinfo.IDCARD_NO" /></th>
					<th><!-- 入职日期  --><spring:message code="org.title.DATE_STARTED" /></th>
					<th><!-- 离职日期  --><spring:message code="ess.empInfo.leaveDate" /></th>
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
					<th><!-- 总工时  --><spring:message code="ess.infoApply.zonggongshi" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewArDetailSummaryForAttenceList}" var="item" varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">
						${i.index + 1 }
						</td>
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
						${item.IDCARD_NO }
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
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>