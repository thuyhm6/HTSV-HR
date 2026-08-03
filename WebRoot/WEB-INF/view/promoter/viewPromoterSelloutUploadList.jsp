<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function importSellout(){
	$("#importExcelDialog_cx1000").attr('href','/promoter/importDataFromExcel?importFunName=/importSellout');
	$("#importExcelDialog_cx1000").attr('height',"200");
	$("#importExcelDialog_cx1000").click();
}
$(document).ready(function() {
	var payAreaCd=$('#seach_PAY_AREA_1').val();
	var branch0=$('#hBRANCH1').val();
	chgPayArea1(payAreaCd,branch0);
	
	$('#seach_PAY_AREA_1').live('change',function(){
		var st=$('#seach_PAY_AREA_1').val();
		document.getElementById('hBRANCH1').value = "";
		branch0="";
		chgPayArea1(st,"");
	});
	
});

function chgPayArea1(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_1',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_1").html(data);
		}
	});
}
function newSelloutReq(){
    var params   = $("#viewPromoterSelloutUpload").serialize();
    var PAY_AREA_CD = document.viewPromoterSelloutUpload.seach_PAY_AREA_1.value;
    var BRANCH = document.viewPromoterSelloutUpload.seach_BRANCH_1.value;
    
    if(BRANCH == null||BRANCH ==""){
        alert("请选择支社！");
    }else
    {
    	$("#importExcelDialog_cx1000").attr('href','/promoter/viewSelloutRequest?' + params);
    	$("#importExcelDialog_cx1000").attr('width',"800");
    	$("#importExcelDialog_cx1000").attr('height',"600");
    	$("#importExcelDialog_cx1000").click();
    }
}
</script>

<a id="importExcelDialog_cx1000" href="#" target="dialog" mask="true"></a>
<a id="importExcel_cx1000"  href="#" target="navTab" mask="true"><span style="display:none;">促销员实绩上报结果</span></a>

<div class="pageHeader">
	<form id="viewPromoterSelloutUpload" name="viewPromoterSelloutUpload" onsubmit="return navTabSearch(this);" action="/promoter/viewPromoterSelloutUploadList" method="post" rel="pagerForm">
		<input name="hBRANCH1" id="hBRANCH1" type="hidden" value="${BRANCH_1}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 月份： -->
						月份
					</td>
					<td>
						<ait:date yearName="year" yearSelected="${year}" monthName="month" monthSelected="${month}"/>
					</td>
					<td><!-- 大区： -->
						大区
					</td>
					<td>
						<ait:SelectState id="seach_PAY_AREA_1" name="seach_PAY_AREA_1" type="PAYAREA" parentNo="" selected="${PAY_AREA_1}" limit="all"/>
					</td>
					<td>
						支社
					</td>
					<td>
						<span id="seach_BRANCH_1" name="seach_BRANCH_1"></select></span>
					</td>
					<td><!-- 产品类型： -->
						产品类型
					</td>
					<td>
						<ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/>
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
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="/promoter/downSelloutTemplate" var="excelT_Url"/>
	<c:set value="javascript:importSellout();" var="excelU_Url"/>
	<c:set value="javascript:newSelloutReq();" var="addReq_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>
<table class="table" width="100%" layoutH="260">
	<thead>
		<tr>
			<th>月份</th>
			<th>支社</th>
			<th>社号</th>
			<th>日期</th>
			<th>产品类型</th>
			<th>产品ID</th>
			<th>客户ID</th>
			<th>CHANNEL</th>
			<th>销售数量</th>
			<th>NOTICE_PRICE</th>
			<th>SELLOUT_PRICE</th>
			<th>SEQ</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${items}" var="item" varStatus="i">
			<tr>
				<td>${item.SALE_MONTH}</td>
				<td>${item.BRANCH}</td>
				<td>${item.EMP_NO}</td>
				<td><fmt:formatDate value="${item.SALE_DAY}" pattern="yyyy-MM-dd" /></td>
				<td>${item.PROD_TP_NM}</td>
				<td>${item.MODEL_CODE}</td>
				<td>${item.SHIP_TO_CODE}</td>
				<td>${item.CHANNEL_CODE}</td>
				<td>${item.SALE_QTY}</td>
				<td>${item.NOTICE_PRICE}</td>
				<td>${item.SELLOUT_PRICE}</td>
				<td>${item.IMP_SEQ}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/promoter/viewPromoterSelloutUploadList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
