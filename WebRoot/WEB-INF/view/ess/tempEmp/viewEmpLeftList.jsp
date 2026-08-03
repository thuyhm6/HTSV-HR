<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEmpLeftList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEmpLeftListForm",navTab.getCurrentPanel()).submit();
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
function confirmSingle_ess3446(flag, seq){
	//获取页面的值
	var jsonData = '[{';
		jsonData += ' "SEQ": "' + seq + '" ,';
		jsonData += ' "FLAG": "' + flag + '" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
		jsonData += '}]';
	
	alertMsg.confirm("<spring:message code='ess.viewTempEmpSalaryList.QUEDINGYAOQUXIAOMA.a' />",//确定要取消吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/tempEmp/empLeftConfirm',
				data: [{ name: 'jsonData', value: jsonData },
				       { name: 'page', value: 'essApply' }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
</script>
<div class="pageHeader">
<form id="viewEmpLeftListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewEmpLeftList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY }">
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewEmpLeftList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewEmpLeftList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!--员工状态--><spring:message code="ess.empInfo.employee_status" /></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td><!--期间--><spring:message code="ess.infoApply.Period" /></td>
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
		<li><a class="buttonActive" id="viewEmpLeftList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
		<li><a class="buttonActive" href="/ess/tempEmp/viewAddEmpLeftList" target="dialog" height="550" width='1000' mask="true" title=" <spring:message code='ess.empInfo.insert' />"><span><!--添加--><spring:message code="ess.empInfo.insert" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th>NO.</th>
							<th><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th><!--部门--><spring:message code="ess.infoApply.DEPT" /></th>
							<th><!--职群--><spring:message code="hrm.empinfo.POST_FAMILY" /></th>
							<th><!--岗位--><spring:message code="rp.report.title.dutyinfo" /></th>
							<th><!--入职日期--><spring:message code="ess.trans.title.entryJobDate" /></th>
							<th><!--离职日期--><spring:message code="hrm.recruitManage.LEAVE_DATE" /></th>
							<th><!--离职原因--><spring:message code="ess.trans.title.resignReason" /></th>
							<th><!--确认状态--><spring:message code="ess.infoApply.confirm_status" /></th>
							<th><!--操作人--><spring:message code="pa.salary.title.createPerson" /></th>
							<th><!--操作时间--><spring:message code="pa.salary.title.createTime" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewEmpLeftList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.POST_FAMILY_NAME}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<td class='td_center'>${item.REMARK}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 1}"><!--通过--><spring:message code="ess.affirmApply.title.remark.tongguo" /></c:if>
									<c:if test="${item.ACTIVITY eq 0}">
										<a href="#" onclick="confirmSingle_ess3446(3,'${item.PERSON_ID}')"><!--取消--><spring:message code="inct.salesman.button.cancelImport" /></a>
									</c:if>
									<c:if test="${item.ACTIVITY eq 2}"><!--否决--><spring:message code="ess.affirmApply.title.remark.foujue" /></c:if>
									<c:if test="${item.ACTIVITY eq 3}"><!--取消--><spring:message code="inct.salesman.button.cancelImport" /></c:if>
									<div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
