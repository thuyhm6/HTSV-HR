<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doOfficeIncAdjustListExport(from){
  	//var $from =$(from);
  	//var url ="${base}/promoter/viewOfficeIncAdjustListExcel";
  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
	var sform = document.getElementById("viewOfficeIncAdjust");
	var eForm = document.getElementById("excelForm_cx0500");
	
	document.getElementById("cx0500Link").innerHTML = "EXCEL密码设置";
	eForm.PAY_AREA_CD.value		= sform.seach_PAY_AREA_CD.value;
	eForm.PROD_TP.value		    = sform.seach_PROD_TP.value;
	eForm.PROD_IDs.value		= sform.seach_PROD_IDs.value;
	
	$("#excelDialog_cx0500").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/promoter/expOfficeIncAdjustListExcel"
					+"&navTabId=cx0500"
					+"&formId=excelForm_cx0500");
	$("#excelDialog_cx0500").attr('width', "300");
	$("#excelDialog_cx0500").attr('height', "150");
	$("#excelDialog_cx0500").click();
}
function expOfficeIncAdjustList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewOfficeIncAdjust");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doOfficeIncAdjustListExport($from);}});
    } 
}
function importOfficeIncAdjust(){
	$("#importExcelDialog_cx0500").attr('href','/promoter/importDataFromExcel?importFunName=/importOfficeIncAdjust');
	$("#importExcelDialog_cx0500").attr('height',"200");
	$("#importExcelDialog_cx0500").click();
}

</script>

<a id="importExcelDialog_cx0500" href="#" target="dialog" mask="true"></a>
<a id="importExcel_cx0500"  href="#" target="navTab" mask="true"><span style="display:none;">大区单台提成调整导入结果</span></a>

<div class="pageHeader">
	<form id="viewOfficeIncAdjust" onsubmit="return navTabSearch(this);" action="/promoter/viewOfficeIncAdjustList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <table class="searchContent">
			<tr>
				<td><!-- 大区： -->
					大区
				</td>
				<td>
					<ait:SelectState id="seach_PAY_AREA_CD" name="seach_PAY_AREA_CD" type="PAYAREA" parentNo="" selected="${PAY_AREA_CD}" limit="all"/>
				</td>
				<td><!-- 产品类型： -->
					产品类型
				</td>
				<td>
				   <ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 产品ID： -->
					产品ID
					<input type="text" name="seach_PROD_IDs" id="seach_PROD_IDs" value="${PROD_IDs}"></select>
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
							<button type="button" onclick="expOfficeIncAdjustList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
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
	<c:set value="dialog" var="add_tab"/>
	<c:set value="400" var="add_width"/>
	<c:set value="430" var="add_height"/>
	<c:set value="/promoter/addOfficeIncAdjustView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="400" var="edit_width"/>
	<c:set value="430" var="edit_height"/>
	<c:set value="/promoter/updateOfficeIncAdjustView?{sid}" var="edit_Url"/>
	<c:set value="/promoter/downOfficeIncAdjustTemplate" var="excelT_Url"/>
	<c:set value="javascript:importOfficeIncAdjust();" var="excelU_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 大区   -->
					大区
				</th>
				<th width="100"><!-- 产品类型   -->
					产品类型
				</th>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 单价 -->
					单价
				</th>
				<th width="100"><!-- 总部单价-->
					总部单价
				</th>
				<th width="100"><!-- 标准提成-->
					标准提成
				</th>
				<th width="100"><!-- 调整比率  -->
					调整比率
				</th>
				<th width="100"><!--基本提成-->
					基本提成
				</th>
				<th width="100"><!-- 固定提成-->
					固定提成
				</th>
				<th width="100"><!-- 修改人 -->
					修改人
				</th>
				<th width="100"><!-- 使用标记 -->
					使用标记
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="PAY_AREA_CD=${item.PAY_AREA_CD}&PROD_TP=${item.PROD_TP}&PROD_ID=${item.PROD_ID}">
					<td>${item.PAY_AREA_NM}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td>${item.PROD_ID}</td>
	                <td>${item.UNIT_PRC}</td>
	                <td>${item.HEAD_UNIT_PRC}</td>
	                <td>${item.SUBSD_INCTV_AMT}</td>
	                <td>${item.DIFF_RAT}</td>
	                <td>${item.BASE_AMT}</td>
	                <td>${item.FXD_AMT}</td>
	                <td>${item.UPDT_USER}</td>
	                <td>${item.USE_YN}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewOfficeIncAdjustList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
	<a id="excelDialog_cx0500" href="#" target="dialog" mask="true"><span
		id="cx0500Link" style="display: none"></span></a>
	<form id="excelForm_cx0500" name="excelForm_cx0500" method="post">
	    <input type="hidden" id="password" 		name="password" 	value="" />
	    <input type="hidden" id="PAY_AREA_CD" 	name="PAY_AREA_CD" 	value="" />
	    <input type="hidden" id="PROD_TP" 		name="PROD_TP" 		value="" />
	    <input type="hidden" id="PROD_IDs"      name="PROD_IDs"     value="" />
	</form>
	
</div>
