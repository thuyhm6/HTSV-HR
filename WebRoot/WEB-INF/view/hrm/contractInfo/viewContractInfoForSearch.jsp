<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#viewContractInfoTable",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 270,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
         "fixedColumns":{leftColumns: 7},
	     "deferRender":true,
            "language": {
                "processing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
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
            "sDom":'<"top"r<"clear">fB>t<"bottom"ip<"clear">>',
            "buttons": []
		});
});
function searchPop_hr0303(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/hrm/contractInfo/viewContractInfoForSearch?seach_FIRST_FLAG=1';
	var refreshMenuCode = 'hr0303';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.contract_search"/>'));//合同查询
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_hr0303", navTab.getCurrentPanel())
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
		$("#searchPop_hr0303", navTab.getCurrentPanel()).click();
}
function downloadExl(url) {
	$('#viewContractInfoForSearch').attr("action", url);
	$('#viewContractInfoForSearch').attr("onsubmit", '');
	$('#viewContractInfoForSearch').submit();
	$('#viewContractInfoForSearch').attr("action",
			'/hrm/contractInfo/viewContractInfoForSearch');
	$('#viewContractInfoForSearch').attr("onsubmit",
			'return navTabSearch(this);');
}
function qianding08() {
	var red = $('#TOTAL_PERIOD08').prop('checked');
	if (red == true) {
		$('#TOTAL_PERIOD08').attr('value', '11');
	} else {
		$('#TOTAL_PERIOD08').attr('value', '');
	}
}

</script>
<div class="pageHeader">
<input id="loginUser_this" type="hidden" value="${LoginUser.adminID}">
<input id="loginUser_cpnyId" type="hidden" value="${LoginUser.cpnyId}">
 
	<form id="viewContractInfoForSearch"
		onsubmit="return navTabSearch(this);"
		action="/hrm/contractInfo/viewContractInfoForSearch?firstFlag=N" method="post">
		<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG"
			value="1" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
							onkeydown="javascript:if(event.keyCode == 13)searchPop_hr0303('onkeyup');" />
					</td>
					<td style="text-align: left">
						<a class="btnLook" id="searchPop_hr0303"
							onclick="searchPop_hr0303()" href="#" lookupGroup="person"> </a>
						<!-- ${empInfoShow } -->
					</td>
					<td>
						<spring:message code="hrm.contract.seach_S_END_DATE" /><!-- 合同终止日 -->
					</td>
					<td>
						<input type="text" id="seach_S_END_DATE" name="seach_S_END_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${S_END_DATE}" size="20" />
						-
					</td>
					<td colspan="2">
						<input type="text" id="seach_E_END_DATE" name="seach_E_END_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${E_END_DATE}" size="20" />
					</td>
				</tr>
				<tr>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td COLSPAN="2">
						<ait:deptList name="seach_DEPTNO" limit="hr"
							id="viewContractInfoForSearchchange_seachDept" />
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr"
							id="viewContractInfoForSearchchange_seachDept"
							selected="${DEPTNO}" />
					</td>
					
					<!--<td>
						<spring:message code="hrm.empinfo.DATE_STARTED" /> 入职日期 
					</TD>
					<TD>
						<input type="text" id="S_DATE_STARTED" name="S_DATE_STARTED"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${S_DATE_STARTED}" size="20" /> -
					</td>
					<td colspan="2">
						<input type="text" id="E_DATE_STARTED" name="E_DATE_STARTED"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${E_DATE_STARTED}" size="20" />
					</td>
				-->
					<td>
						<!-- 次数： -->
						<spring:message code="hr.viewExpiredContract.title.TOTALPERIOD" />
					</td>
					<td>
						<select name="seach_eqOrMore">
							<option value="0"
								<c:if test="${eqOrMore eq 0}">selected="selected"</c:if>>
								<spring:message code="hrm.contract.GREATER_EQUAL" /><!-- 大于等于-->
							</option>
							<option value="1"
								<c:if test="${eqOrMore eq 1}">selected="selected"</c:if>>
								<spring:message code="hrm.contract.equal" /><!-- 等于 -->
							</option>
						</select>
								<select name="seach_CONCOUNT" style="width:50px;">
									<option value="0"
										<c:if test="${CONCOUNT eq 0}">selected="selected"</c:if>>
										0
									</option>
									<option value="1"
										<c:if test="${CONCOUNT eq 1}">selected="selected"</c:if>>
										1
									</option>
									<option value="2"
										<c:if test="${CONCOUNT eq 2}">selected="selected"</c:if>>
										2
									</option>
									<option value="3"
										<c:if test="${CONCOUNT eq 3}">selected="selected"</c:if>>
										3
									</option>
									<option value="4"
										<c:if test="${CONCOUNT eq 4}">selected="selected"</c:if>>
										4
									</option>
									<option value="5"
										<c:if test="${CONCOUNT eq 5}">selected="selected"</c:if>>
										5
									</option>
									<option value="6"
										<c:if test="${CONCOUNT eq 6}">selected="selected"</c:if>>
										6
									</option>
									<option value="7"
										<c:if test="${CONCOUNT eq 7}">selected="selected"</c:if>>
										7
									</option>
								</select>
					</td>
					<td>
						<!-- 合同类型 -->
						<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_CONTRACT_TYPE_CODE"
							parentNo="123199" selected="${CONTRACT_TYPE_CODE }"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
				</tr>
				<tr>
					<td>
						<spring:message code="hrm.contract.Job_status" /><!-- 在职状态 -->
					</td>
					<td COLSPAN="2">
						<ait:SelectSyCodeByCpnyID id="seach_STATUS_CODE"
							name="seach_STATUS_CODE" parentNo="15118" selected="${STATUS_CODE}"
							cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					<td>
						<spring:message code="hrm.contract.Whether_ALL" /><!-- 是否显示全部-->
						<select name="showAllFlag">
							<option value="N"
								<c:if test="${showAllFlag == 'N' }">selected</c:if>>
								N
							</option>
							<option value="Y"
								<c:if test="${showAllFlag == 'Y' }">selected</c:if>>
								Y
							</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" /><!-- 查询 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=17')"
							href="#"> <span><spring:message code="hrm.contract.Extract_data" /><!-- 导出到Excel --></span> </a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table id="viewContractInfoTable" class="orderList"  
		width="1800px">
		<thead>
			<tr>
				<th width="30px;">
					<spring:message code="hrm.contract.NO" /><!-- 序号 -->
				</th>
					<th>
						<!--合同次数-->
						<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD" />
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
					<th>
						<spring:message code="hrm.contract.CONTRACT_TYPE" /><!-- 合同类型 -->
					</th>
					<th>
						<!--合同开始日期:-->
						<spring:message code="hrm.empinfo.Contract_start_date" />
					</th>
					<th>
						<!--合同终止日期:-->
						<spring:message code="hrm.empinfo.Contract_end_date" />
					</th>
					<th>
						<spring:message code="ess.empInfo.age" /><!-- 年龄 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.title.workContent" />
						<!-- 工作内容 -->
					</th>
					<th>
						<spring:message code="hrm.contract.WORK_TIME" />
						<!-- 工时 -->
					</th>
					<th>
						<spring:message code="sys.mainHub.GONGZI.b" />
						<!-- 工资 -->
					</th>
					<th>
						<spring:message code="hrm.empinfo.REMARK" /><!-- 备注 -->
					</th>
					<th><spring:message code="hrm.contractInfo.QIANDING_REN.Z" /><!-- 签订人 --></th>
					<th><spring:message code="hrm.contractInfo.QIANDING_DATE.Z" /><!-- 签订时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="contractInfo" varStatus="i">
				<tr >
					<td style="text-align: center">${i.count}</td>
						<td style="text-align: center">${contractInfo.TOTAL_PERIOD}</td>
						<td style="text-align: center">${contractInfo.LOCAL_NAME}</td>
						<td style="text-align: center">${contractInfo.EMPID}</td>
						<td>${contractInfo.DEPARTMENT_NAME}</td>
						<td>${contractInfo.POST_GRADE}</td>
						<td>${contractInfo.POSITION_NO_NAME}</td>
						<td>${contractInfo.DATE_STARTED}</td>
						<td>${contractInfo.CONTRACT_TYPE_CODE_NAME}</td>
						<td style="text-align:center">${contractInfo.START_CONTRACT_DATE}</td>
						<td style="text-align:center">${contractInfo.END_CONTRACT_DATE}</td>
						<td style="text-align:center">${contractInfo.CONTRACT_LEN}</td>
						<td>${contractInfo.WORK_CONTENT}</td>
						<td>${contractInfo.WORK_HOUR_NAME}</td>
						<td>${contractInfo.SALARY}</td>
					    <td style="text-align: center" title="${contractInfo.REMARK }">${fn:substring(contractInfo.REMARK,0,10)}</td>
						<td>${contractInfo.QIANDING_REN}</td>
						<td>${contractInfo.QIANDING_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
