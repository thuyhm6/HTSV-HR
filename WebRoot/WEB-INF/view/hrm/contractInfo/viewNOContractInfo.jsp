<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"searching": true,//本地搜索
		"bSort": true,   //关闭排序功能
		"bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 250,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    } 
	});

	initEditFun_hr0305();
	
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_hr0305();
	} );
});

function initEditFun_hr0305(){

	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$("#hr0305Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
        	$("#hr0305Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
	
	$('.orderList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
    	$(this).html(val);
    	if(val=='一年合同' || val =='HĐ 1 năm'){
    		var START_CONTRACT_DATE = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
    		$.ajax({
    			type: 'POST',
    			url: '/hrm/recruitManage/doSql',
    			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd/MM/yyyy'),12),'dd/MM/yyyy') DATE_STR from dual"},
    			dataType:"json",
    			cache: false,
    			success: function(data){
    				$("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html(data.result[0].DATE_STR);
    				calContractLength(index);
    			},
    			error: DWZ.ajaxError
    		});
		}
    	if(val=='两年合同' || val =='HĐ 2 năm'){
    		var START_CONTRACT_DATE = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
    		$.ajax({
    			type: 'POST',
    			url: '/hrm/recruitManage/doSql',
    			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd/MM/yyyy'),24),'dd/MM/yyyy') DATE_STR from dual"},
    			dataType:"json",
    			cache: false,
    			success: function(data){
    				$("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html(data.result[0].DATE_STR);
    				calContractLength(index);
    			},
    			error: DWZ.ajaxError
    		});
		}

    	if(val=='三年合同' || val =='HĐ 3 năm'){
    		var START_CONTRACT_DATE = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
    		$.ajax({
    			type: 'POST',
    			url: '/hrm/recruitManage/doSql',
    			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd/MM/yyyy'),36),'dd/MM/yyyy') DATE_STR from dual"},
    			dataType:"json",
    			cache: false,
    			success: function(data){
    				$("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html(data.result[0].DATE_STR);
    				calContractLength(index);
    			},
    			error: DWZ.ajaxError
    		});
		}
    	if(val=='无固定期合同' || val =='HĐ không xác định thời hạn'){
			$("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html('');
			calContractLength(index);
}
			
        	$("#hr0305Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}

function validateCallbackInsertContract() {
	var checked = false;
	var ids = document.getElementsByName("hr0305Check");
	for ( var i = 0; i < ids.length; i++) {
		var index = ids[i].value;
		if (ids[i].checked) {
			checked = true;
			var contract_type = $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html();
			if(contract_type == ''){
				alertMsg.error('<spring:message code="hrm.alert.empinfo.contract_type_notnull"/>');//合同类型不能为空!
				return;
			}
			var date = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
			if (date == '' || date == null) {
				alertMsg.error('<spring:message code="hrm.alert.empinfo.contract_starttime_notnull"/>');//合同开始日期不能为空!
				return;
			}
			var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
			if(contract_type == '培训与试用期合同' || contract_type == '试用期合同' || contract_type == '一年合同' || contract_type == '两年合同' || contract_type == '三年合同'
				|| contract_type == 'HĐ thử việc và đào tạo' || contract_type == 'HĐ thử việc' || contract_type == 'HĐ 1 năm' || contract_type == 'HĐ 2 năm' || contract_type == 'HĐ 3 năm'){
				if (date2 == '' || date2 == null) {
					alertMsg.error('<spring:message code="hrm.alert.empinfo.contract_endtime_notnull" />');//合同结束日期不能为空!
					return;
				}
			}
			/* if($("#WORK_CONTENT_" + index,navTab.getCurrentPanel()).html() == ''){
				alertMsg.error('<spring:message code="hrm.contractInfo.WORK_CONTEN_NOTNULL.Z" />');//工作内容不能为空!
				return;
			} 
			if($("#WORK_HOUR_TYPE_" + index,navTab.getCurrentPanel()).html() == ''){
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullManHourSystem" />');//工时不能为空!
				return;
			}
			if($("#SALARY_" + index,navTab.getCurrentPanel()).html() == ''){
				alertMsg.error('<spring:message code="hrm.contractInfo.SALARY_NOTNULL.Z" />');//工资不能为空!
				return;
			}*/
		}
	}

	//获取页面的值
	var jsonData = '[';
	$("input[name='hr0305Check']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.value;
			var START_CONTRACT_DATE = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
			var CONTRACT_TYPE_CODE = $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html();
			//var TOTAL_PERIOD08 = 0; 
			//if(START_CONTRACT_DATE > "2008.0.0"){
			//	if (CONTRACT_TYPE_CODE == '上海固定合同' || CONTRACT_TYPE_CODE == '上海无固定合同'){
			//		TOTAL_PERIOD08 = 1;
			//		jsonData += ' "TOTAL_PERIOD08": "'+ TOTAL_PERIOD08 +'" ,';
			//	}else{
			//		jsonData += ' "TOTAL_PERIOD08": "0" ,';
			//	}
			//}else{
			//	jsonData += ' "TOTAL_PERIOD08": "0" ,';
			//}
			jsonData += ' "TOTAL_PERIOD": "1" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "CONTRACT_TYPE_CODE": "' + $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "START_CONTRACT_DATE": "' + $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "END_CONTRACT_DATE": "' + $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
			/* jsonData += ' "WORK_CONTENT": "' + $("#WORK_CONTENT_" + index,navTab.getCurrentPanel()).html() + '" ,'; */
			jsonData += ' "SALARY": "' + $("#SALARY_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "WORK_HOUR_TYPE": "' + $("#WORK_HOUR_TYPE_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "REMARK": "' + $("#REMARK_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	if (jsonData.length == 2) {
		alertMsg.info("<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.qianding"/>");
		return;
	}
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.Sign"/>", {//确定要签订吗？
		okCall : function() {
			$.ajax( {
				type : 'POST',
				url : "/hrm/contractInfo/insertContract",
				data: [{ name: 'jsonData', value: jsonData }],
				dataType : "json",
				cache : false,
				success : doAjaxDoneWithForm,
				error : DWZ.ajaxError
			});
		}
	});
}

//计算合同时长
function calContractLength(index) {
	var date1 = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var sql = "SELECT CASE " +
				"        WHEN TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd/MM/yyyy') + 1, "  +
				"                TO_DATE('" + date1 + "', 'dd/MM/yyyy')), "  +
				" 12)) = 0 THEN "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd/MM/yyyy') + 1, "  +
				"         TO_DATE('" + date1 + "', 'dd/MM/yyyy')) / 12) || ' <spring:message code="hrm.empinfo.YEAR"/> ' "  +//年
				" ELSE "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd/MM/yyyy') + 1, "  +
				"         TO_DATE('" + date1 + "', 'dd/MM/yyyy')) / 12) || ' <spring:message code="hrm.empinfo.YEAR"/> ' || "  +//年
				" TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd/MM/yyyy') + 1, "  +
				"             TO_DATE('" + date1 + "', 'dd/MM/yyyy')), "  +
				" 12)) || '<spring:message code="hrm.empinfo.MONTH"/>' "  +//月
				" END AS CONTRACT_LEN "  +
				" FROM DUAL " ;
	if(date1 != '' && date2 != ''){
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:sql},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#CONTRACT_LEN_" + index,navTab.getCurrentPanel()).html(data.result[0].CONTRACT_LEN);
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#CONTRACT_LEN_" + index,navTab.getCurrentPanel()).html('');
	}
}

function searchPop_hr0305(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/hrm/contractInfo/viewNOContractInfo?seach_FIRST_FLAG=1';
	var refreshMenuCode = 'hr0305';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.contract_sign"/>'));//签订合同
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_hr0305", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPop_hr0305", navTab.getCurrentPanel()).click();
}
function downloadExl(url) {
	$('#viewNOContractInfo').attr("action", url);
	$('#viewNOContractInfo').attr("onsubmit", '');
	$('#viewNOContractInfo').submit();
	$('#viewNOContractInfo').attr("action",
			'/hrm/contractInfo/viewNOContractInfo');
	$('#viewNOContractInfo').attr("onsubmit", 'return navTabSearch(this);');
}

</script>
<div class="pageHeader">
	<form id="viewNOContractInfo" onsubmit="return navTabSearch(this);"
		action="/hrm/contractInfo/viewNOContractInfo" method="post" >
		<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG"
			value="1" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="hr"
							id="viewNOContractInfo_seachDept" />
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr"
							id="viewNOContractInfo_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
							onkeydown="javascript:if(event.keyCode == 13)searchPop_hr0305('onkeyup');" />
					</td>
					<td>
						<a class="btnLook" id="searchPop_hr0305"
							onclick="searchPop_hr0305()" href="#" lookupGroup="person"> </a>
					</td>
					<td>
						${empInfoShow }
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!-- 检索 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"
							onclick="validateCallbackInsertContract();" href="#"> 
							<span><spring:message code="hrm.contract.sign" /><!-- 签订 --></span>
						</a>
					</li>
					<li>
					   <a class="buttonActive" onclick="downloadExcel('viewNOContractInfo','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=15','/hrm/contractInfo/viewNOContractInfo')">
					   <span><spring:message code="hrm.contract.Extract_data" /><!-- 导出到Excel --></span></a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th width="20">
						<input type="checkbox" class="checkboxCtrl" group="hr0305Check" />
					</th>
					<th>
						<!-- 姓名 -->
						<spring:message code="inct.salesman.Name" />
					</th>
					<th>
						<!-- 社号 -->
						<spring:message code="display.emp.ben.serviceno" />
					</th>
					<th>
						<!-- 部门 -->
						<spring:message code="public.title.deptName" />
					</th>
					<th>
						<!-- 职级 -->
						<spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!-- 职责 -->
						<spring:message code="ess.infoApply.title.dutyName" />
					</th>
					<th>
						<!-- 入社日期-->
						<spring:message code="hrm.empinfo.DATE_STARTED" />
						
					</th>
					<th class="titleColor">
						<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE" />
						<!-- 合同类型 -->
					</th>
					<th class="titleColor">
						<spring:message
							code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" />
						<!-- 起始日期 -->
					</th>
					<th class="titleColor">
						<spring:message
							code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" />
						<!-- 终止日期 -->
					</th>
					<th>
						<spring:message code="ess.empInfo.age" /><!-- 年龄 -->
					</th>
					<%-- <th class="titleColor">
						<spring:message code="hrm.contract.Job_content" />
						<!-- 工作内容 -->
					</th> --%>
					<th class="titleColor">
						<spring:message code="hrm.contract.WORK_TIME" />
						<!-- 工时 -->
					</th>
					<th class="titleColor">
						<spring:message code="hrm.contract.GONGZI.Z" />
						<!-- 工资 -->
					</th>
					<th class="titleColor">
						<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" />
						<!-- 备注-->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemList}" var="contractInfo" varStatus="i">
					<tr>
						<td style="text-align: center; padding-top: 7px;">
							<input type="checkbox" id="hr0305Check_${i.index}" name="hr0305Check" value="${i.index}" />
							<div id="PERSON_ID_${i.index}" sysIndex="${i.index}" style="display:none;">${contractInfo.PERSON_ID}</div>
						</td>
						<td style="text-align: center">${contractInfo.LOCAL_NAME}</td>
						<td style="text-align: center">${contractInfo.EMPID}</td>
						<td>${contractInfo.DEPARTMENT_NAME}</td>
						<td>${contractInfo.POST_GRADE}</td>
						<td>${contractInfo.POSITION_NO_NAME}</td>
						<td>${contractInfo.DATE_STARTED}</td>
						<td sysLog="select" sysValue='${htlx}' sysIndex="${i.index}" id="CONTRACT_TYPE_CODE_${i.index}">${contractInfo.CONTRACT_TYPE_CODE_NAME}</td><!-- 合同类型 -->
						<td style="text-align:center" sysLog="date" sysFlag="contract" format="dd/MM/yyyy" lang="en" sysIndex="${i.index}" id="START_CONTRACT_DATE_${i.index}">${contractInfo.START_CONTRACT_DATE}</td>
						<td style="text-align:center" sysLog="date" sysFlag="contract" format="dd/MM/yyyy" lang="en" sysIndex="${i.index}" id="END_CONTRACT_DATE_${i.index}">${contractInfo.END_CONTRACT_DATED}</td>
						<td style="text-align:center" id="CONTRACT_LEN_${i.index}">${contractInfo.CONTRACT_LENT }
						<c:if test="${ contractInfo.CONTRACT_LENT != null }">
						<spring:message code="ar.excelexport.title.month"/>
						</c:if>
						</td>
						<td sysLog="select" sysValue='${gongshi}' sysIndex="${i.index}" id="WORK_HOUR_TYPE_${i.index}"></td>
						<td sysLog="text" sysIndex="${i.index}" id="SALARY_${i.index}">${contractInfo.SALARY}</td>
						<td sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}">${contractInfo.REMARK}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
