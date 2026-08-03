<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewVacConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewVacConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	$(".list",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 280,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	    "fixedColumns":false,
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

function vacConfirmSingle(flag, index){
	$("input[name='vac']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#vac_" + index).attr("checked","checked");
	leaveConfirmBatch(flag);
}

function leaveConfirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='vac']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "APPLY_NO": "' + obj.value + '" ,';
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
				url: '/ess/arConfirm/vacConfirm',
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
<form id="viewVacConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/arConfirm/viewVacApplyInfoConfirmList?firstFlag=N" method="post">
<div class="searchBar">
<table class="searchContent">
	       <tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门： --> 
				</td>
				<td>
					<ait:deptList name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar" id="viewArShiftRecordCheckList_seachDept_vac" /> 
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar" id="viewArShiftRecordCheckList_seachDept_vac" selected="${DEPTNO}" />
				</td>
				<td>社号/姓名</td>
				<td>
					<input type="text" name="seach_KEY" id="seach_KEY_ATT" value="${KEY}"/>
				</td>
				<td>基准日</td>
				<td>
					<input type="text" id="seach_VAR_YEAR" name="seach_VAR_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${VAR_YEAR}"/>
				</td>
				<td>员工状态</td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="1372" limit="all"/>
				</td>
				<td>
					确认状态
				</td>
				<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>>全部</option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>>未确认</option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>>通过</option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>>否决</option> 
					</select>
				</td>
			</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewVacConfirmList_Serch" href="#"><span>查询</span></a></li>
		<li><a class="buttonActive" href="#" onclick="vacConfirmBatch(1)"><span>批量通过</span></a></li>
		<li><a class="buttonActive" href="#" onclick="vacConfirmBatch(2)"><span>批量否决</span></a></li>
</div>
<div class="pageContent">
				<table class="list" width="99%" id="viewVacConfirmListTable">
					<thead>
						<th>NO.</th>
					    <th>
					       <input type="checkbox" class="checkboxCtrl" group="vac" />
					    </th>
						<th>
							社号
						</th>
						<th>
							姓名
						</th>
						<th>
							部门
						</th>
						<th>
							职群
						</th>
						<th>
							职级
						</th>
						<th>
							年假开始时间
						</th>
						<th>
							年假结束时间
						</th>
						<th>
							年假总天数
						</th>
						<th>
							备注
						</th>
						<th>
							创建人
						</th>
						<th>
							人事确认
						</th>
					</thead>
					<tbody>
					<c:forEach items="${viewVacConfirmList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
							    <td class='td_center'>
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}">
								    <input type="checkbox" id="vac_${i.index}" name="vac" value="${item.APPLY_NO }" />
								    </c:if>
								     <c:if test="${item.CONFIRM_FLAG ne 0}">
								    <input type="hidden" id="vac_${i.index}" name="vac" value="${item.APPLY_NO }" />
								    </c:if>
							    </td>
							    <td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME }</td>
								<td class='td_center'>${item.DEPT_NAME }</td>
								<td class='td_center'>${item.POST_FAMILY_NAME }</td>
								<td class='td_center'>${item.POST_GRADE_NAME }</td>
								<td class='td_center'>${item.STRT_DATE }</td>
								<td class='td_center'>${item.END_DATE }</td>
								<td class='td_center'>${item.TOT_VAC_CNT }</td>
								<td  class='td_center'>${item.REMARK }</td>
								<td class='td_center'>${item.CREATED_BY}</td>				
								<td class='td_center'>
									<c:if test="${item.CONFIRM_FLAG eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;通过</c:if>
									<c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}"><a href="#" onclick="vacConfirmSingle(1,${i.index})">通过</a>|<a href="#" onclick="vacConfirmSingle(2,${i.index})">否决</a></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;否决</c:if>
								</td>	
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
