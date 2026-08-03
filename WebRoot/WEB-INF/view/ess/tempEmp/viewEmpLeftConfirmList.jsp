<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEmpLeftConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEmpLeftConfirmListForm",navTab.getCurrentPanel()).submit();
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

function confirmSingle_ar0906(flag, index){
	$("input[name='empLeftConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#empLeftConfirm_" + index).attr("checked","checked");
	confirmBatch_ar0906(flag);
}

function confirmBatch_ar0906(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='empLeftConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
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
		//请先勾选要操作的数据
		alertMsg.info("<spring:message code='ar.viewPOtApplyInfoConfirmList.QINGXIANGOUXUANCAOZUOSHUJU.b' />");
		return;
	}
	//确定要执行此操作吗？
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation' />",
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/tempEmp/empLeftConfirm',
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
<form id="viewEmpLeftConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewEmpLeftConfirmList?firstFlag=N" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
		<td>
			<input type="text" name="seach_KEY" value="${KEY }">
		</td>
		<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="ar" id="viewEmpLeftConfirmList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewEmpLeftConfirmList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!-- 期间 --><spring:message code="ess.infoApply.Period" /></td>
		<td>
			<input type="text" id="START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
			<input type="text" id="END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
		</td>
		<td>
			<!-- 确认状态 --><spring:message code="ess.infoApply.confirm_status" />
		</td>
		<td>
			<select name="seach_ACTIVITY">
				<option value=""<c:if test="${ACTIVITY == null}">selected</c:if>><!-- 全部 --><spring:message code="ess.infoApply.whole"/></option>
				<option value="0"<c:if test="${ACTIVITY == '0'}">selected</c:if>><!-- 未确认  --><spring:message code="ess.title.WEIQUEREN"/></option>
				<option value="1"<c:if test="${ACTIVITY == '1'}">selected</c:if>><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></option>
				<option value="2"<c:if test="${ACTIVITY == '2'}">selected</c:if>><!-- 否决  --><spring:message code="ess.infoApply.veto"/></option> 
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewEmpLeftConfirmList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="confirmBatch_ar0906(1)"><span><!-- 批量通过 --><spring:message code="ess.trans.title.passInBatch"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="confirmBatch_ar0906(2)"><span><!-- 批量否决 --><spring:message code="ess.title.rejectInBatch"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewEmpLeftConfirmListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=287','/ess/tempEmp/viewEmpLeftConfirmList?firstFlag=N')"><span><!-- Excel导出 --><spring:message code="ess.infoApply.EXCEL_OUT"/></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="list"  width="99%">
					<thead>
						<tr>
							<th>NO.</th>
				    	    <th><input type="checkbox" class="checkboxCtrl" group="empLeftConfirm" /></th>
							<th><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID"/></th>
							<th><!-- 姓名 --><spring:message code="ess.infoApply.NAME"/></th>
							<th><!-- 部门 --><spring:message code="ess.infoApply.DEPT"/></th>
							<th><!-- 职群 --><spring:message code="ess.empInfo.zhiqun"/></th>
							<th><!-- 岗位 --><spring:message code="ess.message.jobStations"/></th>
							<c:if test="${LoginUser.cpnyId eq 'HTSV' && item.POSITION_NO eq '14014037'}">
								<th><!-- 职责 --><spring:message code="hr.viewWorkInfo.title.DUTY"/></th>
							</c:if>
							<th><!-- 入职日期 --><spring:message code="main.home.message.ruzhiriqi"/></th>
							<th><!-- 离职日期 --><spring:message code="pa.insurance.title.resignDate"/></th>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<th><!-- 主要原因 --><spring:message code="ar.viewEmpLeftConfirmList.ZHUYAOYUANYIN.b"/></th>
							</c:if>
							<th><!-- 离职原因 --><spring:message code="hr.viewPromote.title.RESIGN_REASON"/></th>
							<th><!-- 操作人 --><spring:message code="ar.viewTempEmpConfirmList.CAOZUOREN.b"/></th>
							<th><!-- 操作时间 --><spring:message code="pa.salary.title.createTime"/></th>
							<th><!-- 确认状态 --><spring:message code="ess.humanConfirm.title.confirmStatus"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewEmpLeftConfirmList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
						    	<c:if test="${item.ACTIVITY eq 0}">
						        	<input type="checkbox" id="empLeftConfirm_${i.index}" name="empLeftConfirm" value="${item.PERSON_ID}" />
						        </c:if>
					         	</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.POST_FAMILY_NAME}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
								<c:if test="${LoginUser.cpnyId eq 'HTSV' && item.POSITION_NO eq '14014037'}"  >
									<td class='td_center'>${item.POSITION_NAME}</td>
								</c:if>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
									<td class='td_center'>${item.TRANS_RESOURCE}</td>
								</c:if>
								<td class='td_center'>${item.REMARK}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 通过  --><spring:message code="ess.infoApply.adopt"/></c:if>
									<c:if test="${item.ACTIVITY eq 0}"><a href="#" onclick="confirmSingle_ar0906(1,${i.index})"><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></a>|<a href="#" onclick="confirmSingle_ar0906(2,${i.index})"><!-- 否决  --><spring:message code="ess.infoApply.veto"/></a></c:if>
									<c:if test="${item.ACTIVITY eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 否决  --><spring:message code="ess.infoApply.veto"/></c:if>
									<c:if test="${item.ACTIVITY eq 3}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 取消  --><spring:message code="public.title.cancle"/></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
