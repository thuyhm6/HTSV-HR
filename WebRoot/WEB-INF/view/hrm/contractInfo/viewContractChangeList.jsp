<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
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
		     "scrollY": $(document.body).height() - 250,
		     "scrollX": $(document.body).width(),
		     "scrollCollapse": false,
		     "deferRender":true,
		     //"scroller":true,
	         "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0,11,12,13,14,15] }
	                      ],
	         //"fixedColumns":{leftColumns: 7},
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
            },
            "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
            "buttons": [] 
		});
		initEditFun_hr0304();
	
		$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_hr0304();
		} );
});
function initEditFun_hr0304(){
	
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#hr0304Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
        	$("#hr0304Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
        	if(val=='一年合同' || val =='HĐ 1 năm'){
        		var START_CONTRACT_DATE = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
        		$.ajax({
        			type: 'POST',
        			url: '/hrm/recruitManage/doSql',
        			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd.MM.yyyy'),12),'dd.MM.yyyy') DATE_STR from dual"},
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
        			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd.MM.yyyy'),24),'dd.MM.yyyy') DATE_STR from dual"},
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
        			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + START_CONTRACT_DATE + "','dd.MM.yyyy'),36),'dd.MM.yyyy') DATE_STR from dual"},
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
        	$("#hr0301Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}


//计算合同时长
function calContractLength(index) {
	var date1 = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var sql = "SELECT CASE " +
				"        WHEN TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd.MM.yyyy') + 1, "  +
				"                TO_DATE('" + date1 + "', 'dd.MM.yyyy')), "  +
				" 12)) = 0 THEN "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd.MM.yyyy') + 1, "  +
				"         TO_DATE('" + date1 + "', 'dd.MM.yyyy')) / 12) || ' <spring:message code="hrm.empinfo.YEAR"/> ' "  +//年
				" ELSE "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd.MM.yyyy') + 1, "  +
				"         TO_DATE('" + date1 + "', 'dd.MM.yyyy')) / 12) || ' <spring:message code="hrm.empinfo.YEAR"/> ' || "  +//年
				" TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'dd.MM.yyyy') + 1, "  +
				"             TO_DATE('" + date1 + "', 'dd.MM.yyyy')), "  +
				" 12)) || ' <spring:message code="hrm.empinfo.MONTH"/>' "  +//月
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

function validateCallbackChangeContract() {
		var checked=false;
		var ids= document.getElementsByName("hr0304Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				var index = ids[i].value;
				var contract_type = $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html();
				var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
				if(contract_type == '培训与试用期合同' || contract_type == '试用期合同' || contract_type == '一年合同' || contract_type == '两年合同' || contract_type == '三年合同'
					|| contract_type == 'HĐ thử việc và đào tạo' || contract_type == 'HĐ thử việc' || contract_type == 'HĐ 1 năm' || contract_type == 'HĐ 2 năm' || contract_type == 'HĐ 3 năm'){
				if (date2 == '') {
					alertMsg.error('<spring:message code="hrm.alert.empinfo.contract_endtime_notnull"/>');//合同结束日期不能为空!
					return;
				}
				}
			}
		}

		//获取页面的值
		var jsonData = '[';
		$("input[name='hr0304Check']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = obj.value;
				jsonData += ' "TOTAL_PERIOD": "' + $("#TOTAL_PERIOD_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "CONTRACT_NO": "' + $("#CONTRACT_NO_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "CONTRACT_TYPE_CODE": "' + $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "START_CONTRACT_DATE": "' + $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "END_CONTRACT_DATE": "' + $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
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
				$.ajax({
					type: 'POST',
					url:"/hrm/contractInfo/updateContractInfo",
					data: [{ name: 'jsonData', value: jsonData }],
					dataType:"json",
					cache: false,
					success : doAjaxDoneWithForm,
					error: DWZ.ajaxError
				});
			}
		});
		return false;
}

function downloadExl(url) {
	$('#viewChangeContractInfo').attr("action", url);
	$('#viewChangeContractInfo').attr("onsubmit", '');
	$('#viewChangeContractInfo').submit();
	$('#viewChangeContractInfo').attr("action",
			'/hrm/contractInfo/viewContractChangeList');
	$('#viewChangeContractInfo').attr("onsubmit",
			'return navTabSearch(this);');
}

function searchPop_hr0304(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/hrm/contractInfo/viewContractChangeList?seach_FIRST_FLAG=1';
	var refreshMenuCode = 'hr0304';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.contract_change"/>'));//合同变更
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_hr0304", navTab.getCurrentPanel())
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
		$("#searchPop_hr0304", navTab.getCurrentPanel()).click();
}
</script>
<div class="pageHeader">
	<form id="viewChangeContractInfo" onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewContractChangeList?firstFlag=N" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<!--<td> 部门： 
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</td>
				<td>
						<ait:deptList name="seach_DEPTNO" limit="hr"
							id="viewContractChangeList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr"
							id="viewContractChangeList_seachDept" selected="${DEPTNO}" />
				</td>
				<td>
					</td>
				<td> 次数： 
					<spring:message code="hr.viewExpiredContract.title.TOTALPERIOD"/>
				</td>
				<td>
					<input type="text" name="seach_TOTALPERIOD" value="${TOTALPERIOD}"/>
				</td>
				<td> 合同类型 
					<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
				</td>
				<td>
                   <ait:SelectSyCodeByCpnyID name="seach_CONTRACT_TYPE_CODE" parentNo="123199" selected="${CONTRACT_TYPE_CODE }" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				--><td><!-- 社号/姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
				</td>
				<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
							onkeydown="javascript:if(event.keyCode == 13)searchPop_hr0304('onkeyup');" />
					</td>
					<td>
						<a class="btnLook" id="searchPop_hr0304"
							onclick="searchPop_hr0304()" href="#" lookupGroup="person"> </a>
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
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<a class="buttonActive" onclick="validateCallbackChangeContract();" href="#"> 
						<span><spring:message code="hrm.contract.sign" /><!-- 签订 --></span>
					</a>
				</li>
				<li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=218')"
							href="#"> <span><spring:message code="hrm.contract.Extract_data" /><!-- 导出到Excel --></span> </a>
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
					<th>
						<input type="checkbox" class="checkboxCtrl" group="hr0304Check" />
					</th>
					<th>
						<!--合同次数-->
						<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD" />
					</th>
					<th>
						<!-- 姓名 -->
						<spring:message code="inct.salesman.Name" />
					</th>
					<th style="display:none">
						<!-- 年龄 -->
						<spring:message code="hrm.empinfo.AGE" />
					</th>
					<th>
						<!-- 社号 -->
						<spring:message code="display.emp.ben.serviceno" />
					</th>
					<th>
						<!-- 部门 -->
						<spring:message code="public.title.deptName" />
					</th>
					<th style="display:none">
						<!-- 职种 -->
						<spring:message code="hrm.contract.POSITION" />
					</th>
					<th>
						<!-- 职级 -->
						<spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!-- 入社日期-->
						<spring:message code="hrm.empinfo.DATE_STARTED" />
					</th>
					<th class="titleColor">
						<spring:message code="hrm.contract.CONTRACT_TYPE" /><!-- 合同类型 -->
					</th>
					<th class="titleColor"><!--起始日期-->
						<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
					</th>
					<th class="titleColor"><!--终止日期-->
						<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
					</th>
					<th style="display:none"><spring:message code="hrm.contract.CHANGE_DATE" /><!-- 变更日期 --></th>
					<th>
						<spring:message code="ess.empInfo.age" /><!-- 年龄 -->
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
						<th><spring:message code="hrm.contract.jibengongzi"/><!-- 基本工资 --></th>
					</c:if>
					<th>
						<spring:message code="hrm.contract.WORK_TIME" />
						<!-- 工时 -->
					</th>
					<th class="titleColor">
						<spring:message code="hrm.empinfo.REMARK" /><!-- 备注 -->
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
						<th><spring:message code="hrm.contractInfo.QIANDING_REN.Z" /><!-- 签订人 --></th>
						<th><spring:message code="hrm.contractInfo.QIANDING_DATE.Z" /><!-- 签订时间 --></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemList}" var="contractInfo" varStatus="i">
					<tr>
						<td class="td_center" style="text-align: center; padding-top: 7px;">
							<input type="checkbox" id="hr0304Check_${i.index}" name="hr0304Check" value="${i.index}" />
							<div id="CONTRACT_NO_${i.index}" sysIndex="${i.index}" style="display:none;">${contractInfo.CONTRACT_NO}</div>
						</td>
						<td style="text-align: center" id="TOTAL_PERIOD_${i.index}">${contractInfo.TOTAL_PERIOD}</td>
						<td style="text-align: center">${contractInfo.LOCAL_NAME}</td>
						<td style="text-align: center;display:none">${contractInfo.AGE}</td>
						<td style="text-align: center">${contractInfo.EMPID}</td>
						<td>${contractInfo.DEPARTMENT_NAME}</td>
						<td style="display:none">${contractInfo.POSITION_NAME}</td>
						<td style="text-align: center">${contractInfo.POST_GRADE}</td>
						<td style="text-align: center">${contractInfo.DATE_STARTED}</td>
						<td sysLog="select" sysValue='${htlx}' sysIndex="${i.index}" id="CONTRACT_TYPE_CODE_${i.index}">${contractInfo.CONTRACT_TYPE_CODE_NAME}</td>
						<td style="text-align:center" sysLog="date" sysFlag="contract" format="dd/MM/yyyy" sysIndex="${i.index}" id="START_CONTRACT_DATE_${i.index}">${contractInfo.START_CONTRACT_DATE}</td>
						<td style="text-align:center" sysLog="date" sysFlag="contract" format="dd/MM/yyyy" sysIndex="${i.index}" id="END_CONTRACT_DATE_${i.index}">${contractInfo.END_CONTRACT_DATE}</td>
						<td style="display:none">${contractInfo.CHANGE_DATE}</td>
						<td style="text-align:center" id="CONTRACT_LEN_${i.index}">${contractInfo.CONTRACT_LEN}</td>
						<td>${contractInfo.WORK_HOUR_NAME}</td>
						<td sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}">${contractInfo.REMARK}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
