<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
function pageFromSea(a){
	var seach_PERSON_ID=$("#seach_PERSON_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_PERSON_ID",navTab.getCurrentPanel()).val();
	var seach_AR_MONTH=$("#seach_AR_MONTH",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AR_MONTH",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApply/viewLOtImportList?seach_PERSON_ID="+seach_PERSON_ID
			+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}

function delLOvertimeApplyImport(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要删除吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/delOvertimeApplyImport',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewLOtImportList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
//-->
</script>

<script type="text/javascript">
function exportLImportModel(){
	var url = "/ess/infoApply/exportBatchOtModuleL?navTabId=ess0231";
	document.getElementById("exportImportExcel").href=encodeURI(url);
}

function importLOtBatch(){
	$("#importExcelDialog_ess0231").attr('href','/pa/excelImport/importExcelData?importFunName=/importLOtApplyTemp');
	$("#importExcelDialog_ess0231").click();
}

function exportLOtImportExcel(){
	var url = "/ess/infoApply/exportLOtImportExcel?navTabId=ess0231";
	document.getElementById("exportLOtImportExcel").href=encodeURI(url);
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewLOtImportList" method="post" rel="pagerForm"
		id="viewLOtImportList" name="viewLOtImportList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%">
						<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
					</td>
					<td width="20%">
						${totalCnt}
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
					</td>
					<td width="10%">
						<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
					</th>
					<td width="20%">							
						${errCnt}
					</td>
					<td width="10%">
						出错与否：
					</td>
					<td width="20%">							
						<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
								<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
								<option value="0" <c:if test="${RESULT_FLAG eq '0' }">selected</c:if>>是</option>
								<option value="1" <c:if test="${RESULT_FLAG eq '1' }">selected</c:if>>否</option>
						</select>
					</td>
					<td>&nbsp;</td>
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
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" >
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/ess/infoApply/saveLOtApplyImport?AFFIRM_FLAG=-1" title="确定要暂存吗?"><span>暂存</span></a>
			</li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/ess/infoApply/saveLOtApplyImport?AFFIRM_FLAG=0" 
					title="确定要提交吗?"><span>提交</span></a>
			</li>
			<li>
				<a class="buttonActive" target="ajaxTodo" href="/ess/infoApply/deleteLOtApplyImport" title="确定要全部取消吗?"><span>取消</span></a>
			</li>
			<li class="line">line</li>
			<%-- 
			<li>
				<a class="buttonActive" id ="exportImportExcel" onclick="exportLImportModel();" href="#">
					<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a>
			</li>
			--%>
			<li>
				<a class="buttonActive" onclick="importLOtBatch();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" id ="exportLOtImportExcel" onclick="exportLOtImportExcel();" href="#">
					<span><spring:message code="ar.addempshift.title.excelexport"/><%--导出Excel--%></span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th width="10">
			    	<!--<input type="checkbox" class="checkboxCtrl" group="c1" />-->
			    	序号
			    </th>
			   	<th width="15" style="text-align:left"><!-- 社号 -->
					社号
				</th>
			    <th width="30" style="text-align:left"><!-- 姓名 -->
					姓名
				</th>
				<th width="50" style="text-align:left"><!-- 部门 -->
					部门
				</th>
				<th width="60" style="text-align: center"><!--申请日期-->
					申请日期
				</th>
				<th width="40" style="text-align: center"><!--申请时长-->
					申请时长
				</th>
				<th width="40" style="text-align: center"><!--加班类型-->
					加班类型
				</th>
				<th width="40" style="text-align: center"><!--社内/外-->
					社内/外
				</th>
				<th width="40" style="text-align: center"><!--加班事由-->
					加班事由
				</th>
				<th width="30" style="text-align: center"><!--正常与/否-->
					正常与/否
				</th>
				<th width="30" style="text-align: center"><!--错误原因-->
					错误原因
				</th>
				<th width="40" style="text-align: center"><!--是否删除-->
					删除
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${otImportList}" var="otApply" varStatus="i">			
				<tr target="sid" rel="${otApply.APPLY_NO}">
				    <td style="text-align: center">
				    	<!--<input type="checkbox" id="c1" name="c1" value="${otApply.APPLY_NO}" />-->
				        ${i.index+1 }
				    </td>
				    <td style="text-align: center">${otApply.PERSON_TEMP}</td>
				    <td style="text-align: center">${otApply.LOCAL_NAME}</td>
				    <td style="text-align: center">${otApply.DEPT_NAME}</td>
				    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>
				    	
					<td style="text-align: center">${otApply.OT_APPLY_HOUR}时${otApply.OT_APPLY_MINUTE}分</td>
					<td style="text-align: center">${otApply.APPLY_TYPE_NAME}</td>
					<td style="text-align: center">
						<c:if test="${otApply.ADJUST_YN eq '0'}" >
							否
						</c:if>
						<c:if test="${otApply.ADJUST_YN eq '1'}" >
						  	是
						</c:if>
					</td>
					<td style="text-align: center">
						<a rel="otLImportRemark" href="/ess/infoApply/viewFullApplyInfoImport?seach_APPLY_NO=${otApply.APPLY_NO}" title="加班事由"
				          target="dialog" mask="true" width="300" height="300" id="otLImportRemark" >${otApply.INTRO}...</a>
					</td>
					<td style="text-align: center">
						<c:if test="${otApply.CHECK_FLAG eq '0'}" >
							<font color="green">正常</font>
						</c:if>
						<c:if test="${otApply.CHECK_FLAG eq '1'}" >
						  	<font color="red">异常</font>
						</c:if>
					</td>
					<td style="text-align: center">
						<c:if test="${otApply.CHECK_FLAG eq '1'}" >
							<a rel="otLImportRemark" href="/ess/infoApply/viewFullImportCheckError?seach_APPLY_NO=${otApply.APPLY_NO}" title="验证结果"
				          		target="dialog" mask="true" width="300" height="300" id="otLImportRCheckErrorHref" >${otApply.ERROR_INFO}...</a>
				        </c:if>
					</td>
					<td style="text-align: center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delLOvertimeApplyImport('${otApply.APPLY_NO}')"
					 		style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <div id="otLImportRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div> 
    <c:set value="/ess/infoApply/viewLOtImportList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>