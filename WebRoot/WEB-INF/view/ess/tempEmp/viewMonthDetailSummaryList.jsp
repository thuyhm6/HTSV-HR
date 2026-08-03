<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewMonthDetailSummaryList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewMonthDetailSummaryListForm",navTab.getCurrentPanel()).submit();
	});
	//申请
	$("#viewMonthDetailSummaryList_Apply",navTab.getCurrentPanel()).click(function(){
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
			alertMsg.error('请选选择要申请的数据');
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
		"scrollY": $(document.body).height() - 280,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
            "sProcessing": "正在加载中......",
            "sZeroRecords": "查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sSearch": "快速筛选"
        }, //多语言配置
	     "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0,7] }
	                     ],
	    "fixedColumns":{leftColumns: 3}
	});

});
</script>
<div class="pageHeader">
<form id="viewMonthDetailSummaryListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewMonthDetailSummaryList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>月份</td>
		<td>
			<input type="text" id="AR_MONTH" name="seach_AR_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${AR_MONTH}"/>
		</td>
		<td>姓名/社号</td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY }">
		</td>
		<td>部门</td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewMonthDetailSummaryList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewMonthDetailSummaryList_deptList" selected="${DEPTNO}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewMonthDetailSummaryList_Serch" href="#"><span>查询</span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
			<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailSummaryListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=281','/ess/tempEmp/viewMonthDetailSummaryList')"><span>Excel导出</span></a></li>
		</c:if>
		<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
			<li><a class="buttonActive" href="#" onclick="downloadExcel('viewMonthDetailSummaryListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=268','/ess/tempEmp/viewMonthDetailSummaryList')"><span>Excel导出</span></a></li>
		</c:if>
	</ul>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(viewMonthDetailSummaryList)}</div>
				<table class="orderList" width="2800px">
					<thead>
						<tr>
							<th width="20px">NO.</th>
							<th width="60px">工号</th>
							<th width="60px">姓名</th>
							<th width="150px">部门</th>
							<th width="60px">职级</th>
							<th width="60px">岗位</th>
							<th width="70px">入职日期</th>
							<th width="70px">离职日期</th>
							<c:forEach items="${viewMonthList}" var="item" varStatus="i">
								<th width="40px" id="changeFlag_${i.index }" <c:if test="${item.TYPEID ne 1440}">style="background-color:#cccccc"</c:if> sysDateStr="${item.DDATE_STR }">
									${item.DDATE_TITLE }
								</th>
							</c:forEach>
							
							<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
								<th width="40px">未勤<br/>天数</th>
								<th width="40px">出勤<br/>天数</th>
							</c:if>
							
							<th width="40px">总工时</th>
							
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<th width="40px">正餐</th>
							</c:if>
							
							<th width="40px">1.5倍</th>
							<th width="40px">2倍</th>
							<th width="40px">3倍</th>
							
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<th width="40px">迟到/早退</th>
								<th width="40px">事假</th>
								<th width="40px">病假</th>
								<th width="40px">旷工</th>
								<th width="40px">其他</th>
							</c:if>
							
							
							<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
								<th width="40px">事假</th>
								<th width="40px">病假</th>
								<th width="40px">年假</th>
								<th width="40px">调休</th>
								<th width="40px">婚假</th>
								<th width="40px">丧假</th>
								<th width="40px">产假</th>
								<th width="40px">陪产假</th>
								<th width="40px">产检假</th>
								<th width="40px">哺乳假</th>
								<th width="40px">工伤假</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewMonthDetailSummaryList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td>${item.DEPT_NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.DUTY_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<c:forEach items="${viewMonthList}" var="item1" varStatus="i">
									<td class='td_center' <c:if test="${item1.TYPEID ne 1440}">style="background-color:#cccccc"</c:if>>${item[item1.DATE_KEY]}</td>
								</c:forEach>		
														
								<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
									<td class='td_center'>${item.NOATT}</td>
									<td class='td_center'>${item.ATT}</td>
								</c:if>
								
								<td class='td_center'>${item.WORK_HOUR}</td>
								
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<td class='td_center'>${item.ALMUERZO}</td>
								</c:if>
								
								<td class='td_center'>${item.OT1}</td>
								<td class='td_center'>${item.OT2}</td>
								<td class='td_center'>${item.OT3}</td>
								
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
									<td class='td_center'>${item.TARDINESS_EARLYDAY}</td>
									<td class='td_center'>${item.CASUAL_LEAVE}</td>
									<td class='td_center'>${item.SICK_LEAVE}</td>
									<td class='td_center'>${item.ABSENTEEISM}</td>
									<td class='td_center'>${item.OTHER_LEAVE}</td>
								</c:if>
								
								<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
									<td class='td_center'>${item.CASUAL_LEAVE}</td>
									<td class='td_center'>${item.SICK_LEAVE}</td>
									<td class='td_center'>${item.ANNUAL_VACATION}</td>
									<td class='td_center'>${item.PAY_LEAVE}</td>
									<td class='td_center'>${item.MARRIAGE_LEAVE}</td>
									<td class='td_center'>${item.BEREFT_LEAVE}</td>
									<td class='td_center'>${item.MATERNITY_LEAVE}</td>
									<td class='td_center'>${item.PEICHAN_JIA}</td>
									<td class='td_center'>${item.CHECK_MATERNITY_LEAVE}</td>
									<td class='td_center'>${item.NURSING_LEAVE}</td>
									<td class='td_center'>${item.INDUSTRY_INJURY}</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
