<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportReguEmpTransferOrderImportInfo(a){
    var $from = $("#viewReguEmpTransferOrderResultList");
    alertMsg.confirm("Do you want to export?", {
		okCall: function(){ doTempEmpTransferOrderImportInfoExport($from);}});
  } 
function doTempEmpTransferOrderImportInfoExport(from){
    var $from = $("#viewReguEmpTransferOrderResultList"); 
    var url ="/hrm/transferOrder/viewReguEmpTransferOrderResultListExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
} 
//提交导入数据
function doReguEmpTransferOrderDataImport()
{
    var params   = $("#viewReguEmpTransferOrderResultList").serialize();
        alertMsg.confirm("确认导入发令数据?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/hrm/transferOrder/createEmpTypeTransferOrderImportResult?" + params,
						success : function(result) {
							if (result == 1){
								alert("人员变更发令数据导入成功！");
								$.pdialog.closeCurrent();
								navTabSearch($("#searchReguEmpTransferOrder"));
							}else{
								alert("人员变更发令数据导入失败！");
								//页面重载
								dwzSearch($("#viewReguEmpTransferOrderResultList"),'dialog');
							}							
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewReguEmpTransferOrderResultList" name="viewReguEmpTransferOrderResultList"
			action="/hrm/transferOrder/viewReguEmpTransferOrderResultList" 
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
				<button type="button"  onclick="exportReguEmpTransferOrderImportInfo(this)">
				<spring:message code="inct.salesman.downloadToExcel" /><!--excel导出--></button>
			</div></div></li>
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="submit"  onclick="doReguEmpTransferOrderDataImport()">
				<spring:message code="public.title.submit"/><!--提交--></button>
			</div></div></li>
			<li><div class="button"><div class="buttonContent">
				<button type="button" id="btnClose" name="btnClose" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button>
			</div></div></li>
		</ul>
	</div>	
		
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="5%">Line<!--excel行号--></th>
				<th width="7%">法人</th>
				<th width="10%">社编</th>		
				<th width="8%">姓名</th>				
				<th width="10%">发令日期</th>
				<th width="10%">发令原因</th>
				<th width="15%">新人员类型</th>				
				<th width="15%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="10%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="10%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>			
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
					<td>${mdata.CUR_EMP_TYPE_CODE}</td>
					<td>${mdata.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewReguEmpTransferOrderResultList?navTabId=${request_navTabId}">
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