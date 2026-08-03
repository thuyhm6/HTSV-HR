<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doCustInfoListExport(from){
  	//var $from =$(from);
  	//var url ="${base}/promoter/viewCustInfoListExcel";
  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
	var sform = document.getElementById("viewCustInfo");
	var eForm = document.getElementById("excelForm_cx0200");
	
	document.getElementById("cx0200Link").innerHTML = "EXCEL密码设置";
	eForm.YEAR.value		    = sform.seach_YEAR.value;
	eForm.PAY_AREA_CD.value		= sform.seach_PAY_AREA_02.value;
	eForm.SHOP_AREA_ID.value	= sform.seach_SHOP_AREA_ID.value;
	eForm.SHOP_LEVEL.value		= sform.seach_SHOP_LEVEL.value;
	eForm.STATE_NM.value		= sform.seach_STATE_NM.value;
	eForm.CITY_NM.value		    = sform.seach_CITY_NM.value;
	eForm.REGION_NM.value		= sform.seach_REGION_NM.value;
	eForm.SHOP_CD1.value    	= sform.seach_SHOP_CD1.value;
	
	$("#excelDialog_cx0200").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/promoter/expCustInfoListExcel"
					+"&navTabId=cx0200"
					+"&formId=excelForm_cx0200");
	$("#excelDialog_cx0200").attr('width', "300");
	$("#excelDialog_cx0200").attr('height', "150");
	$("#excelDialog_cx0200").click();
}

function expCustInfoList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewCustInfo");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doCustInfoListExport($from);}});
    } 
}

$(document).ready(function() {
	var sform = document.getElementById("viewCustInfo");
	var stateCd=$('#seach_STATE_NM').val();
	var cityCd0=$('#hCITY_NM').val();
	var region0=$('#hREGION_NM').val();
	changeState(stateCd,cityCd0);
	
	$('#seach_STATE_NM').live('change',function(){
		var st=$('#seach_STATE_NM').val();
		changeState(st,cityCd0);
		changeCity(null, null);
	});
	
	var cityCd=$('#seach_CITY_NM select').val();
	changeCity(cityCd0, region0);
	
	$('#seach_CITY_NM').live('change',function(){
		var st=$('#seach_CITY_NM select').val();
		changeCity(st, region0);
	});
	
	var payArea02=$('#seach_PAY_AREA_02').val();
	var branch02=$('#hBRANCH02').val();
	changePayArea2(payArea02,branch02);
	
	$('#seach_PAY_AREA_02').live('change',function(){
		var st02=$('#seach_PAY_AREA_02').val();
		document.getElementById('hBRANCH02').value = "";
		branch02="";
		changePayArea2(st02,"");
	});
});

function changeState(stateCd, cityCd){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITY_NM',
		type : "get",
		dataType : "html",
		success : function(data) {
			$('#seach_CITY_NM').html(data);
		}
	});
}

function changeCity(cityCd, region){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=REGION&parentNo='+cityCd+"&selected="+region+'&name=seach_REGION_NM',
		type : "get",
		dataType : "html",
		success : function(data) {
			$('#seach_REGION_NM').html(data);
		}
	});
}

function changePayArea2(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_02',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_02").html(data);
		}
	});
}
</script>

<div class="pageHeader">
	<form id="viewCustInfo" name="viewCustInfo" onsubmit="return navTabSearch(this);" action="/promoter/viewCustInfoList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <input id="hCITY_NM" name="hCITY_NM" type="hidden" value="${searchMap.seach_CITY_NM}" />
		<input id="hREGION_NM" name="hREGION_NM" type="hidden" value="${searchMap.seach_REGION_NM}" />
	    <input id="hBRANCH02" name="hBRANCH02" type="hidden" value="${BRANCH_02}" />
		<table class="searchContent">
			<tr>
				<td><!-- 年份： -->
					年份
				</td>
				<td>
					<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					        <option value=""></option>
							<c:forEach var="i" begin="2010" end="2025" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
				</td>
				<td><!-- 大区： -->
					大区
				</td>
				<td>
					<ait:SelectState id="seach_PAY_AREA_02" name="seach_PAY_AREA_02" type="PAYAREA" parentNo=""  selected="${PAY_AREA_02}" limit="all"/>
				</td>
				<td><!-- 支社： -->
					支社
				</td>
				<td>
					<span id="seach_BRANCH_02" name="seach_BRANCH_02"></select></span>
				</td>
			</tr>
			<tr>
				<td><!-- 省名称： -->
					省名称
				</td>
				<td>
					<ait:SelectState id="seach_STATE_NM" name="seach_STATE_NM" type="STATE" parentNo="" selected="${STATE_NM}" limit="all"/>
				</td>
				<td><!-- 城市名称： -->
					城市名称
				</td>
				<td>
					<span id="seach_CITY_NM" name="seach_CITY_NM"></select></span>
				</td>
				<td><!-- 地区名称： -->
					地区名称
				</td>
				<td>
					<span id="seach_REGION_NM" name="seach_REGION_NM"></select></span>
				</td>
			</tr>
			<tr>
				<td><!-- 门店地区： -->
					门店地区
				</td>
				<td>
					<ait:ComboSyCodeDescByCpnyID id="seach_SHOP_AREA_ID" name="seach_SHOP_AREA_ID" parentNo="211057" selected="${SHOP_AREA_ID}" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 门店等级： -->
					门店等级
				</td>
				<td>
					<ait:ComboSyCodeDescByCpnyID id="seach_SHOP_LEVEL" name="seach_SHOP_LEVEL" parentNo="210608" selected="${SHOP_LEVEL}" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 门店代码： -->
					门店代码
				</td>
				<td>
					<input type="text" name="seach_SHOP_CD1" maxlength="20" value="${SHOP_CD1}" />
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
							<button type="button" onclick="expCustInfoList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
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
	<c:set value="/promoter/updateCustInfoView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="261">
		<thead>
			<tr>
				<th width="60"><!-- 年份  -->
					年份
				</th>
				<th width="60"><!-- 大区   -->
					大区
				</th>
				<th width="60"><!-- 支社-->
					支社
				</th>
				<th width="60"><!-- 门店地区 -->
					门店地区
				</th>
				<th width="60"><!-- 门店代码-->
					门店代码
				</th>
				<th width="60"><!--门店名称 -->
					门店名称
				</th>
				<th width="60"><!-- 门店等级-->
					门店等级
				</th>
				<th width="60"><!-- GoldenShop-->
					GoldenShop
				</th>
				<th width="60"><!-- 渠道-->
					商场形态
				</th>
				<th width="60"><!-- 渠道-->
					渠道2
				</th>
				<th width="60"><!-- 省名称-->
					省名称
				</th>
				<th width="60"><!-- 城市名称-->
					城市名称
				</th>
				<th width="60"><!-- 地区名称-->
					地区名称
				</th>
				<th width="60"><!-- 开店时间-->
					开店时间
				</th>
				<th width="60"><!-- 状态-->
					状态
				</th>
				<th width="60"><!-- 门店提成率-->
					门店提成率
				</th>
				<th width="60"><!-- 门店提成率-->
					提成上限
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="YEAR=${item.YYYY}&COM_CD=${item.COM_CD}&SHOP_CD=${item.SHOP_CD}" >
					<td class="td_center">${item.YYYY}</td>
					<td class="td_center">${item.DIV_CD_NM}</td>
					<td class="td_center">${item.DEPT_NM}</td>
					<td class="td_center">${item.CITY_LEVEL_NM}</td>
					<td class="td_center">${item.SHOP_CD}</td>
					<td class="td_left">${item.SHOP_NAME}</td>
					<td class="td_center">${item.SHOP_LEVEL_NM}</td>
					<td class="td_center">${item.GOLDEN_SHOP}</td>
					<td class="td_center">${item.CHANNEL1_NAME}</td>
					<td class="td_center">${item.CHANNEL2_NAME}</td>
					<td class="td_center">${item.STATE_NAME}</td>
					<td class="td_center">${item.CITY_NAME}</td>
					<td class="td_center">${item.AREA_NAME}</td>
					<td class="td_center">${item.OPEN_DATE}</td>
					<td class="td_center">${item.USE_YN}</td>
					<td class="td_center">${item.DEDUCT_RATIO}</td>
					<td class="td_center">${item.INC_UP_LIMIT}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewCustInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
	<a id="excelDialog_cx0200" href="#" target="dialog" mask="true"><span
		id="cx0200Link" style="display: none"></span></a>
	<form id="excelForm_cx0200" name="excelForm_cx0200" method="post">
	    <input type="hidden" id="password" 			name="password" 		value="" />
	    <input type="hidden" id="YEAR" 		        name="YEAR" 		    value="" />
	    <input type="hidden" id="PAY_AREA_CD"       name="PAY_AREA_CD"      value="" />
	    <input type="hidden" id="SHOP_AREA_ID"      name="SHOP_AREA_ID"     value="" />
	    <input type="hidden" id="SHOP_LEVEL" 	    name="SHOP_LEVEL"       value="" />
	    <input type="hidden" id="STATE_NM" 	        name="STATE_NM" 	    value="" />
	    <input type="hidden" id="CITY_NM" 	        name="CITY_NM" 	        value="" />
	    <input type="hidden" id="REGION_NM" 	    name="REGION_NM" 	    value="" />
	    <input type="hidden" id="SHOP_CD1" 	        name="SHOP_CD1" 	    value="" />
	</form>

</div>
