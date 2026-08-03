<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewTempEmpConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewTempEmpConfirmListForm",navTab.getCurrentPanel()).submit();
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

function confirmSingle(flag, index){
	$("input[name='tempEmpConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#tempEmpConfirm_" + index).attr("checked","checked");
	confirmBatch(flag);
}

function confirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='tempEmpConfirm']",navTab.getCurrentPanel()).each(function(i, obj){
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
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation' />",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/tempEmp/tempEmpConfirm',
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
<form id="viewTempEmpConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewTempEmpConfirmList?firstFlag=N" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
		<td>
			<input type="text" name="seach_KEY" value="${KEY }">
		</td>
		<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="ar" id="viewTempEmpConfirmList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewTempEmpConfirmList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!-- 员工状态 --><spring:message code="ess.empInfo.employee_status" /></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td><!-- 期间 --><spring:message code="ess.infoApply.Period" /></td>
		<td>
			<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
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
		<li><a class="buttonActive" id="viewTempEmpConfirmList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="confirmBatch(1)"><span><!-- 批量通过 --><spring:message code="ess.trans.title.passInBatch"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="confirmBatch(2)"><span><!-- 批量否决 --><spring:message code="ess.title.rejectInBatch"/></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="list"  width="99%">
					<thead>
						<tr>
							<th>NO.</th>
				    	    <th><input type="checkbox" class="checkboxCtrl" group="tempEmpConfirm" /></th>
							<th><!-- 姓名 --><spring:message code="ess.infoApply.NAME"/></th>
							<th><!-- 社号 --><spring:message code="ess.infoApply.EMPID"/></th>
							<th><!-- 部门 --><spring:message code="ess.infoApply.DEPT"/></th>
							<th><!-- 身份证号 --><spring:message code="hrm.empinfo.IDCARD_NO"/></th>
							<th><!-- 出生日期 --><spring:message code="hrm.empinfo.FAM_BORNDATE"/></th>
							<th><!-- 年龄 --><spring:message code="hrm.empinfo.AGE"/></th>
							<th><!-- 性别 --><spring:message code="hrm.empinfo.SEXCODE"/></th>
							<c:if test="${LoginUser.cpnyId eq 'SPC_HZ'}">
							<th><!-- 手机号 --><spring:message code="hrm.empinfo.CELLPHONE"/></th>
							</c:if>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
							<th><!-- 银行帐号 --><spring:message code="rp.report.title.bankcardno"/></th>
							</c:if>
							<th><!-- 员工状态 --><spring:message code="hrm.empinfo.Working_status"/></th>
							<th><!-- 入职日期 --><spring:message code="hrm.recruitManage.DATE_STARTED"/></th>
							<th><!-- 离职日期 --><spring:message code="hrm.recruitManage.LEAVE_DATE"/></th>
							<th><!-- 操作人 --><spring:message code="ar.viewTempEmpConfirmList.CAOZUOREN.b"/></th>
							<th><!-- 操作时间--><spring:message code="pa.salary.title.createTime"/></th>
							<th><!-- 确认状态--><spring:message code="ess.humanConfirm.title.confirmStatus"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewTempEmpConfirmList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
						    	<c:if test="${item.ACTIVITY eq 0}">
						        	<input type="checkbox" id="tempEmpConfirm_${i.index}" name="tempEmpConfirm" value="${item.SEQ}" />
						        </c:if>
					         	</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.ID_CARD_NO}</td>
								<td class='td_center'>${item.DOB}</td>
								<td class='td_center'>${item.AGE}</td>
								<td class='td_center'>${item.SEXCODE_NAME}</td>
								
								<c:if test="${LoginUser.cpnyId eq 'SPC_HZ'}">
								<td class='td_center'>${item.PHONE}</td>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<td class='td_center'>${item.BANK_NO}</td>
								</c:if>
							
								<td class='td_center'>${item.EMP_OFFICE_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center'>${item.DATE_LEFT}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 通过  --><spring:message code="ess.infoApply.adopt"/></c:if>
									<c:if test="${item.ACTIVITY eq 0}"><a href="#" onclick="confirmSingle(1,${i.index})"><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></a>|<a href="#" onclick="confirmSingle(2,${i.index})"><!-- 否决  --><spring:message code="ess.infoApply.veto"/></a></c:if>
									<c:if test="${item.ACTIVITY eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 否决  --><spring:message code="ess.infoApply.veto"/></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
