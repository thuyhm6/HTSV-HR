<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewChangeShopHistoryList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewChangeShopHistoryListForm",navTab.getCurrentPanel()).submit();
	});
	
	$(".list",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50, 1000], [15,20, 35, 50, 1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 280,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	    //"fixedColumns":{leftColumns: 3},
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
</script>
<div class="pageHeader">
<form id="viewChangeShopHistoryListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewChangeShopHistoryList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
		<td>
			<input type="text" name="seach_KEY" value="${KEY}">
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewChangeShopHistoryList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewChangeShopHistoryList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!--员工状态--><spring:message code="ess.empInfo.employee_status" /></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<c:if test="${LoginUser.cpnyId eq 'HTSV' or LoginUser.cpnyId eq 'SPC_SH' }">
		<td><!--调店区分--><spring:message code="ess.title.DIAODIANQUFEN" /></td>
		<td>
			<select name="seach_CHANGE_TYPE">
				<option value=""><!--全部--><spring:message code="org.title.ALL" /></option>
				<option value="1" <c:if test="${CHANGE_TYPE eq 1}">selected</c:if>><!--临时--><spring:message code="ess.title.LINSHI" /></option>
				<option value="2" <c:if test="${CHANGE_TYPE eq 2}">selected</c:if>><!--正式--><spring:message code="org.title.ALL" /></option>
			</select>
		</td>
		</c:if>
		<td><!-- 期间 --><spring:message code="ess.infoApply.Period" /></td>
		<td>
			<input type="text" id="START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
			<input type="text" id="END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewChangeShopHistoryList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
		<li><a class="delete" href="/ess/tempEmp/deleteChangeShopInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="确定要删除吗?"><span><!--删除--><spring:message code="ess.empInfo.Delete" /></span></a></li>
		<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importTempEmp" target="dialog" mask="true"><span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="list" width="99%">
					<thead>
						<tr>
							<th width="3%">NO.</th>
							<th width="6%"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th width="6%"><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th width="10%"><!--岗位--><spring:message code="org.title.DUTY_NO" /></th>
							<th width="6%"><!--日期--><spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime" /></th>
							<th width="6%"><!--日期--><spring:message code="ar.attendanceView.viewNoSwipingCard.endTime" /></th>
							<th width="6%"><!--调店区分--><spring:message code="ess.title.DIAODIANQUFEN" /></th>
							<th width="14%"><!--店铺--><spring:message code="ess.title.DIAPU" /></th>
							<th width="10%"><!--操作时间--><spring:message code="pa.salary.title.createTime" /></th>
							<th width="12%"><!--操作人--><spring:message code="pa.salary.title.createPerson" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewChangeShopHistoryList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
							 	<td class='td_center'>${item.CHANGE_DATE}</td>
							 	<td class='td_center'>${item.SHIFT_TO_TIME}</td>
							 	<td class='td_center'>${item.CHANGE_TYPE_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
