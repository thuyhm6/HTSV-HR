<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
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
	     "scrollY": $(document.body).height() - 340,
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

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/workgroup/viewPersonShiftList" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="ess.workgroup.title.duration"/><!--期间-->：
					</td>
					<td>
							<input type="text" name="seach_sDate" class="Wdate"
								value="${sDate}" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
								
								
					</td>
					<td>~</td>
					<td>
							<input type="text" name="seach_eDate" class="Wdate"
								value="${eDate}" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
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
	<table class="orderList" width="99%" >
		<thead>
			<tr>
			    <th width="10%"> 
					<spring:message code="ess.workgroup.title.Seq" text="NO"/><!--NO-->
				</th>
				<th width="10%">
					<spring:message code="ess.workgroup.title.dateTime.date"/><!--日期-->
				</th>
				
				<th width="10%"><spring:message code="ess.infoApply.DATETYPE"/><!--日期类型--></th>
				<th width="20%"><spring:message code="ess.infoApply.WORKTYPE"/><!--工作形态--></th>
				<th width="20%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></th>
				<th width="20%"><spring:message code="ess.infoApply.end_time"/><!--结束时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personShift}" var="personShift" varStatus="i">
				<tr target="" rel="">
				    <td style="text-align: center">${i.count}</td>
				    <td style="text-align: center">${personShift.DDATE_STR}</td>
					<td style="text-align: center">${personShift.DATE_NAME}</td>
					<td style="text-align: center">${personShift.SHIFT_NAME}</td>
					<td style="text-align: center">${personShift.SHIFT_START_TIME}</td>
					<td style="text-align: center">${personShift.SHIFT_END_TIME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>