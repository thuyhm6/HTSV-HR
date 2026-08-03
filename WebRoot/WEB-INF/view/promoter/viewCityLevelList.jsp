<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doCityLevelListExport(from){
  	//var $from =$(from);
  	//var url ="${base}/promoter/viewCityLevelListExcel";
  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
	var sform = document.getElementById("viewCityLevel");
	var eForm = document.getElementById("excelForm_cx0300");
	
	document.getElementById("cx0300Link").innerHTML = "EXCEL密码设置";
	eForm.STATENM.value		= sform.seach_STATENM.value;
	eForm.CITYNM.value		= sform.seach_CITYNM.value;
	eForm.REGION.value		= sform.seach_REGION.value;
	eForm.CITYLEVEL.value   = sform.seach_CITYLEVEL.value;
	
	$("#excelDialog_cx0300").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/promoter/expCityLevelListExcel"
					+"&navTabId=cx0300"
					+"&formId=excelForm_cx0300");
	$("#excelDialog_cx0300").attr('width', "300");
	$("#excelDialog_cx0300").attr('height', "150");
	$("#excelDialog_cx0300").click();
}
function expCityLevelList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewCityLevel");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doCityLevelListExport($from);}});
    } 
}

$(document).ready(function() {
	var stateCd=$('#seach_STATENM').val();
	var cityCd0=$('#hCITYNM').val();
	var region0=$('#hREGION').val();
	changeState(stateCd,cityCd0);
	
	$('#seach_STATENM').live('change',function(){
		var st=$('#seach_STATENM').val();
		changeState(st,cityCd0);
		changeCity(null, null);
	});
	
	var cityCd=$('#seach_CITYNM select').val();
	changeCity(cityCd0, region0);
	
	$('#seach_CITYNM select').live('change',function(){
		var st=$('#seach_CITYNM select').val();
		changeCity(st, region0);
	});
});

function changeState(stateCd, cityCd){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_CITYNM").html(data);
		}
	});
}

function changeCity(cityCd, region){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=REGION&parentNo='+cityCd+"&selected="+region+'&name=seach_REGION',
		type : "get",
		dataType : "html",
		success : function(data) {
			$('#seach_REGION').html(data);
		}
	});
}

</script>

<div class="pageHeader">
	<form id="viewCityLevel" onsubmit="return navTabSearch(this);" action="/promoter/viewCityLevelList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <input id="hCITYNM" name="hCITYNM" type="hidden" value="${searchMap.seach_CITYNM}" />
		<input id="hREGION" name="hREGION" type="hidden" value="${searchMap.seach_REGION}" />
		<table class="searchContent">
			<tr>
				<td><!-- 省名称： -->
					省名称
					<ait:SelectState id="seach_STATENM" name="seach_STATENM" type="STATE" parentNo="" selected="${STATENM}" limit="all"/>
				</td>
				<td><!-- 城市名称： -->
					城市名称
					<span id="seach_CITYNM" name="seach_CITYNM"></select></span>
				</td>
				<td><!-- 地区名称： -->
					地区名称
					<span id="seach_REGION" name="seach_REGION"></select></span>
				</td>
				<td><!-- 城市等级： -->
					城市等级
					<ait:ComboSyCodeDescByCpnyID id="seach_CITYLEVEL" name="seach_CITYLEVEL" parentNo="211057" selected="${CITYLEVEL}" cnpyID="${defaultCpny}" limit="all"/>
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
							<button type="button" onclick="expCityLevelList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
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
	<c:set value="/promoter/updateCityLevelView?{sid}" var="edit_Url"/>
	<div id="city_list"></div>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 省编号  -->
					省编号
				</th>
				<th width="100"><!-- 省名称   -->
					省名称
				</th>
				<th width="100"><!-- 城市编号-->
					城市编号
				</th>
				<th width="100"><!-- 城市名称 -->
					城市名称
				</th>
				<th width="100"><!-- 地区编号-->
					地区编号
				</th>
				<th width="100"><!--地区名称 -->
					地区名称
				</th>
				<th width="100"><!-- 城市等级-->
					城市等级
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="STATENM=${item.STATE_CD}&CITYNM=${item.CITY_CD}&REGION=${item.REGION_CD}" >
					<td class="td_center">${item.STATE_CD}</td>
					<td class="td_center">${item.STATE_NM}</td>
					<td class="td_center">${item.CITY_CD}</td>
					<td class="td_center">${item.CITY_NM }</td>
					<td class="td_center">${item.REGION_CD }</td>
					<td class="td_center">${item.REGION_NM }</td>
					<td class="td_center">${item.CITY_LEVEL}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewCityLevelList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
	<a id="excelDialog_cx0300" href="#" target="dialog" mask="true"><span
		id="cx0300Link" style="display: none"></span></a>
	<form id="excelForm_cx0300" name="excelForm_cx0300" method="post">
	    <input type="hidden" id="password" 			name="password" 	value="" />
	    <input type="hidden" id="STATENM" 	        name="STATENM" 	    value="" />
	    <input type="hidden" id="CITYNM" 	        name="CITYNM" 	    value="" />
	    <input type="hidden" id="REGION" 	        name="REGION" 	    value="" />
	    <input type="hidden" id="CITYLEVEL" 	    name="CITYLEVEL" 	value="" />
	</form>
	
</div>
