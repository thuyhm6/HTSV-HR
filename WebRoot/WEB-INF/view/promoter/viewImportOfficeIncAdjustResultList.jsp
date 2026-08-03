<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportImportInfo(a){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewDataExcelImportResult");
  	alertMsg.confirm(title, {okCall: function(){ doImportInfoExport($from);}});
}
function doImportInfoExport(from){
  	var $from =$(from);
  	var url ="${base}/promoter/viewImportOfficeIncAdjustResultExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}

function doDataImport()
{
    var params = $("#viewDataExcelImportResult").serialize();
        alertMsg.confirm("确认导入大区单台提成调整数据?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/promoter/createImportOfficeIncAdjustResult?" + params,
						success : function(result) {
							if (result == 1){
								alert("数据导入成功！");
							}else{
								alert("数据导入失败！");
							}
							//页面重载
							navTabSearch($("#viewDataExcelImportResult"));
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewDataExcelImportResult" name="viewDataExcelImportResult"
			action="/promoter/viewImportOfficeIncAdjustResultList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					出错与否：
				</th>
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
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="exportImportInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
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
				<button type="submit"  onclick="doDataImport()">
				<spring:message code="public.title.submit"/><!--提交--></button>
			</div></div></li>
			<li><div class="button"><div class="buttonContent">
				<button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button>
			</div></div></li>
		</ul>
	</div>	
		
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="5%">Line<!--excel行号--></th>
				<th width="100"><!-- 大区   -->
					大区
				</th>
				<th width="100"><!-- 产品类型   -->
					产品类型
				</th>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 调整比率  -->
					调整比率
				</th>
				<th width="100"><!-- 固定提成-->
					固定提成
				</th>
				<th width="100"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="100"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${item}" var="item" varStatus="i">			
				<tr>
					<td class='td_right'>${item.LINE_ID}</td>
					<td>${item.PAY_AREA_NM}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td>${item.PROD_ID}</td>
	                <td>${item.DIFF_RAT}</td>
	                <td>${item.FXD_AMT}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
					<td>${item.UPDT_DTIME}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/promoter/viewImportOfficeIncAdjustResultList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>