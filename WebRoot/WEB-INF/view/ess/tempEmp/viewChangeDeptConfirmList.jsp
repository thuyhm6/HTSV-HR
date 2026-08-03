<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewChangeDeptConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewChangeDeptConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	
	$(".list",navTab.getCurrentPanel()).dataTable({
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
        	"sProcessing": "正在加载中......",
            "sZeroRecords": "查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sSearch": "快速筛选",
            "sLengthMenu": "每页 _MENU_ 条记录",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoFiltered": "(从 _MAX_ 条记录过滤)",
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [] 
	});
});

function confirmSingle_ar0907(flag, index){
	$("input[name='changeDeptConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#changeDeptConfirm_" + index,navTab.getCurrentPanel()).attr("checked","checked");
	confirmBatch_ar0907(flag);
}

function confirmBatch_ar0907(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='changeDeptConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "SEQ": "' + obj.value + '" ,';
			jsonData += ' "FLAG": "' + flag + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	
	if (jsonData.length == 2) {
		alertMsg.info("请先勾选要操作的数据");
		return;
	}
	alertMsg.confirm("确定要执行此操作吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/tempEmp/changeDeptConfirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
</script>
<div class="pageHeader">
<form id="viewChangeDeptConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewChangeDeptConfirmList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>姓名/社号</td>
		<td>
			<input type="text" name="seach_KEY" value="${KEY}">
		</td>
		<td>部门</td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewChangeDeptConfirmList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewChangeDeptConfirmList_deptList" selected="${DEPTNO}"/>
		</td>
		<td>员工状态</td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td>期间</td>
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
		<li><a class="buttonActive" id="viewChangeDeptConfirmList_Serch" href="#"><span>查询</span></a></li>
		<li><a class="buttonActive" id="viewChangeDeptConfirmList_Confirm" onclick="confirmBatch_ar0907(3)" href="#"><span>批量确认</span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="list" width="99%">
					<thead>
						<tr>
							<th width="3%">NO.</th>
				    	    <th width="3%"><input type="checkbox" class="checkboxCtrl" group="changeDeptConfirm" /></th>
							<th width="6%">姓名</th>
							<th width="6%">社号</th>
							<th width="8%">岗位</th>
							<th width="8%">日期</th>
							<th width="14%">原店铺</th>
							<th width="14%">调入店铺</th>
							<th width="10%">操作时间</th>
							<th width="12%">操作人</th>
							<th width="12%">岗位修改</th>
							<th width="7%">确认状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewChangeDeptConfirmList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
						    	<c:if test="${item.CONFIRM_FLAG eq 0}">
						        	<input type="checkbox" id="changeDeptConfirm_${i.index}" name="changeDeptConfirm" value="${item.SEQ}" />
						        </c:if>
					         	</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
							 	<td class='td_center'>${item.CHANGE_DATE}</td>
							 	<td class='td_center'>${item.OLD_DEPT_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<td class='td_center'><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewStartPoint','pageNum=1&amp;menuNo=14013651&amp;navTabId=hr0204&amp;PERSON_ID=${item.PERSON_ID}','hr0204','个人发令');">个人发令</a></td>
								<td class='td_center'>
									<c:if test="${item.CONFIRM_FLAG eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;已确认</c:if>
									<c:if test="${item.CONFIRM_FLAG eq 0}"><a href="#" onclick="confirmSingle_ar0907(3,${i.index})">确认</a></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
