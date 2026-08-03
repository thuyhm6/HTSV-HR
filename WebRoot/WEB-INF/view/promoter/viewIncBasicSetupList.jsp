<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doIncBasicSetupListExport(from){
  	//var $from =$(from);
  	//var url ="${base}/promoter/viewIncBasicSetupListExcel";
  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
	var sform = document.getElementById("viewIncBasicSetup");
	var eForm = document.getElementById("excelForm_cx0400");
	
	document.getElementById("cx0400Link").innerHTML = "EXCEL密码设置";
	eForm.PROD_TP.value		    = sform.seach_PROD_TP.value;
	eForm.PROD_IDs.value		= sform.seach_PROD_IDs.value;
	
	$("#excelDialog_cx0400").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/promoter/expIncBasicSetupListExcel"
					+"&navTabId=cx0400"
					+"&formId=excelForm_cx0400");
	$("#excelDialog_cx0400").attr('width', "300");
	$("#excelDialog_cx0400").attr('height', "150");
	$("#excelDialog_cx0400").click();
}
function expIncBasicSetupList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewIncBasicSetup");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doIncBasicSetupListExport($from);}});
    } 
}

function importIncBasicSetup(){
	$("#importExcelDialog_cx0400").attr('href','/promoter/importDataFromExcel?importFunName=/importIncBasicSetup');
	$("#importExcelDialog_cx0400").attr('height',"200");
	$("#importExcelDialog_cx0400").click();
}
</script>

<a id="importExcelDialog_cx0400" href="#" target="dialog" mask="true"></a>
<a id="importExcel_cx0400"  href="#" target="navTab" mask="true"><span style="display:none;">总公司单台提成设置导入结果</span></a>

<div class="pageHeader">
	<form id="viewIncBasicSetup" onsubmit="return navTabSearch(this);" action="/promoter/viewIncBasicSetupList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <table class="searchContent">
			<tr>
				<td><!-- 产品类型： -->
					产品类型
				</td>
				<td>
				   <ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 产品ID： -->
					产品ID
					<input type="text" name="seach_PROD_IDs" id="seach_PROD_IDs" maxlength="20" value="${PROD_IDs}">
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="expIncBasicSetupList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
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
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="400" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/promoter/updateIncBasicSetupView?{sid}" var="edit_Url"/>
	<c:set value="/promoter/downIncBasicSetupTemplate" var="excelT_Url"/>
	<c:set value="javascript:importIncBasicSetup();" var="excelU_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 产品类型   -->
					产品类型
				</th>
				<th width="100"><!-- 单价 -->
					单价
				</th>
				<th width="100"><!-- 总部单价-->
					总部单价
				</th>
				<th width="100"><!-- 提成率-->
					提成率(%)
				</th>
				<th width="100"><!-- 总部提成  -->
					总部提成
				</th>
				<th width="100"><!--更新人 -->
					更新人
				</th>
				<th width="100"><!-- 状态-->
					状态
				</th>
				<th width="100"><!-- 单价同步时间 -->
					单价同步时间
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="PROD_TP=${item.PROD_TP}&PROD_ID=${item.PROD_ID}">
					<td>${item.PROD_ID}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td>${item.UNIT_PRC}</td>
	                <td>${item.HEAD_UNIT_PRC}</td>
	                <td>${item.INC_RATE}</td>
	                <td>${item.SUBSD_INCTV_AMT}</td>
	                <td>${item.UPDT_USER}</td>
	                <td>${item.USE_YN}</td>
	                <td>${item.PRICE_IF_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewIncBasicSetupList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
	<a id="excelDialog_cx0400" href="#" target="dialog" mask="true"><span
		id="cx0400Link" style="display: none"></span></a>
	<form id="excelForm_cx0400" name="excelForm_cx0400" method="post">
	    <input type="hidden" id="password" 		name="password" 	value="" />
	    <input type="hidden" id="PROD_TP" 		name="PROD_TP" 		value="" />
	    <input type="hidden" id="PROD_IDs"      name="PROD_IDs"     value="" />
	</form>
	
</div>
