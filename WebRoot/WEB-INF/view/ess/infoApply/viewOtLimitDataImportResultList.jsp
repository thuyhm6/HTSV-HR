<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function doSalesmanEvalImportInfoExport(from){
    var $from = $("#viewEvalDataExcelImportResult"); 
    var url ="/inct/salesman/viewEvaluationDataImportResultListExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
} 
//提交导入数据
function doOtLimitDataImport()
{
    var params   = $("#viewOtLimitDataImportResultList").serialize();
        alertMsg.confirm("确认导入加班上限数据?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/ess/infoApply/insertOTLimit?" + params,
						success : function(result) {
							if (result == 1){
								alert("加班上限数据导入成功！");
								$.pdialog.closeCurrent();
							}else{
								alert("加班上限数据导入失败！");
								//页面重载
								dwzSearch($("#viewOtLimitDataImportResultList"),'dialog');
							}							
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewOtLimitDataImportResultList" name="viewOtLimitDataImportResultList"
			action="/ess/infoApply/viewOtLimitDataImportResultList" 
			onsubmit="return dwzSearch(this,'dialog')"
			method="post" 
			rel="pagerForm" >
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
							<button id="btnSearch_se0102_1" name="btnSearch_se0102_1" type="submit">
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
				<button type="submit"  onclick="doOtLimitDataImport()">
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
				<th width="15%">月份</th>
				<th width="5%">姓名</th>
				<th width="5%">社号</th>				
				<th width="10%">部门</th>
				<th width="10%">职级</th>
				<th width="10%">月初加班上限</th>		
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">			
				<tr>
					<td class='td_right'>${mdata.LINE_ID}</td>
					<td class='td_center'>${mdata.AR_MONTH_STR}</td>
					<td class='td_center'>${mdata.LOCAL_NAME}</td>
					<td class='td_center'>${mdata.EMPID}</td>					
					<td class='td_center'>${mdata.DEPARTMENT}</td>					
					<td class='td_center'>${mdata.POST_GRADE_NAME}</td>
					<td class='td_right'>${mdata.FINAL_ADJUST_TIME}</td>			
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>