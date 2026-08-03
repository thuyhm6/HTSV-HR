<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportTempEmpTransferOrderImportInfo(a){
    var $from = $("#viewTempEmpTransferOrderImportResult");
    alertMsg.confirm("Do you want to export?", {
		okCall: function(){ doTempEmpTransferOrderImportInfoExport($from);}});
  } 
function doTempEmpTransferOrderImportInfoExport(from){
    var $from = $("#viewTempEmpTransferOrderImportResult"); 
    var url ="/hrm/transferOrder/viewTempEmpTransferOrderResultListExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
} 
//提交导入数据
function doTempEmpTransferOrderDataImport()
{
    var params   = $("#viewTempEmpTransferOrderImportResult").serialize();
        alertMsg.confirm("确认导入临时职人员发令数据?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/hrm/transferOrder/createTempEmpTransferOrderImportResult?" + params,
						success : function(result) {
							if (result == 1){
								alert("临时职人员发令数据导入成功！");
								$.pdialog.closeCurrent();
								navTabSearch($("#searchTransferOrderForm"));
							}else{
								alert("临时职人员发令数据导入失败！");
								//页面重载
								dwzSearch($("#viewTempEmpTransferOrderImportResult"),'dialog');
							}							
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewTempEmpTransferOrderImportResult" name="viewTempEmpTransferOrderImportResult"
			action="/hrm/transferOrder/viewTempEmpTransferOrderResultList" 
			onsubmit="return dwzSearch(this,'dialog')"
			method="post" 
			rel="pagerForm" >
			<input type="hidden" name="pageNum" value="${pageNum}" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</td>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<td>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</td>
				<td>							
					${errCnt}
				</td>
				<td>
					出错与否：
				</td>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是</option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否</option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button id="btnSearch_hr0515_1" name="btnSearch_hr0515_1" type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">	
	
	<div class="formBar">
		<ul>			
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="button"  onclick="exportTempEmpTransferOrderImportInfo(this)">
				<spring:message code="inct.salesman.downloadToExcel" /><!--excel导出--></button>
			</div></div></li>
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="submit"  onclick="doTempEmpTransferOrderDataImport()">
				<spring:message code="public.title.submit"/><!--提交--></button>
			</div></div></li>
			<li><div class="button"><div class="buttonContent">
				<button type="button" id="btnClose" name="btnClose" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button>
			</div></div></li>
		</ul>
	</div>	
		
	<table class="table" width="150%" layoutH="150">
		<thead>
			<tr>
				<th width="3%">Line<!--excel行号--></th>
				<th width="5%">法人</th>
				<th width="8%">社编</th>		
				<th width="8%">姓名</th>				
				<th width="8%">发令日期</th>
				<th width="4%">发令原因</th>
				<th width="8%">新部门</th>
				<th width="5%">新人员类型</th>
				<th width="5%">新班号</th>
				<th width="5%">新职责</th>
				<th width="3%">新级号</th>
				<th width="3%">新级号等级</th>
				<th width="3%">新基本工资</th>
				<th width="3%">新变动工资</th>
				<th width="3%">新年薪</th>
				<th width="3%">新ID卡号</th>
				<th width="3%">新职务</th>
				<th width="10%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="5%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="5%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">			
				<tr>
					<td class='td_center'>${mdata.LINE_ID}</td>
					<td class='td_center'>${mdata.CPNY_ID}</td>
					<td class='td_center'>${mdata.EMPID}</td>
					<td class='td_center'>${mdata.EMPNAME}</td>	
					<td class='td_center'>${mdata.START_DATE}</td>
					<td>${mdata.TRANSFER_ORDER_REASON}</td>								
					<td>${mdata.CUR_DEPTNO}</td>
					<td>${mdata.CUR_EMP_TYPE_CODE}</td>
					<td class='td_center'>${mdata.CUR_SHIFT_NO}</td>								
					<td class='td_center'>${mdata.CUR_POSITION_NO}</td>							
					<td class='td_center'>${mdata.CUR_PAY_GRADE}</td>
					<td class='td_center'>${mdata.CUR_PAY_STEP}</td>
					<td class='td_right'>${mdata.CUR_BASE_PAY}</td>
					<td class='td_right'>${mdata.CUR_VARB_PAY}</td>	
					<td class='td_right'>${mdata.CUR_ANSAL}</td>
					<td class='td_center'>${mdata.CUR_ID_CARD_NO}</td>	
					<td class='td_center'>${mdata.CUR_JOB_TITLE_CD}</td>
					<td>${mdata.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewTempEmpTransferOrderResultList?navTabId=${request_navTabId}">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>