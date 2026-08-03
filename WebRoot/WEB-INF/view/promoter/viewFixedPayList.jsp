<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	var payAreaCd=$('#seach_PAY_AREA_CD').val();
	var branch0=$('#hBRANCH').val();
	changePayArea(payAreaCd,branch0);
	
	$('#seach_PAY_AREA_CD').live('change',function(){
		var st=$('#seach_PAY_AREA_CD').val();
		document.getElementById('hBRANCH').value = "";
		branch0="";
		changePayArea(st,"");
	});
	
});

function changePayArea(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_CD',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_CD").html(data);
		}
	});
}
</script>

<div class="pageHeader">
<form id="viewFixedPay" onsubmit="return navTabSearch(this);" action="/promoter/viewFixedPayList" method="post" rel="pagerForm">
<div class="searchBar">
	    <input id="hBRANCH" name="hBRANCH" type="hidden" value="${BRANCH_CD}" />
<table class="searchContent">
	<tr>
		<td><!-- 大区： -->
			大区
		</td>
		<td>
		    <ait:SelectState id="seach_PAY_AREA_CD" name="seach_PAY_AREA_CD" type="PAYAREA" parentNo="" selected="${PAY_AREA_CD}" limit="all"/>
		</td>
		<td><!-- 支社： -->
			支社
		</td>
		<td>
		    <span id="seach_BRANCH_CD" name="seach_BRANCH_CD"></select></span>
		</td>
	</tr>
	<tr>
		<td><!-- 门店地区： -->
			门店地区
		</td>
		<td>
		    <ait:ComboSyCodeDescByCpnyID id="seach_SHOP_AREA_ID" name="seach_SHOP_AREA_ID" parentNo="211057" selected="${SHOP_AREA_ID}" cnpyID="${defaultCpny}" limit="all" />
		</td>
		<td><!-- 产品类型： -->
			产品类型
		</td>
		<td>
		   <ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all" />
		</td>
		<td><!-- 门店等级： -->
			门店等级
		</td>
		<td>
		    <ait:ComboSyCodeDescByCpnyID id="seach_SHOP_LEVEL" name="seach_SHOP_LEVEL" parentNo="210608" selected="${SHOP_LEVEL}" cnpyID="${defaultCpny}" limit="all" />
		<td>
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
	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="450" var="add_height"/>
	<c:set value="/promoter/addFixedPayView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="450" var="edit_height"/>
	<c:set value="/promoter/updateFixedPayView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
<table class="table" width="100%" layoutH="256">
	<thead>
		<tr>
			<th width="10%">大区 </th>
			<th width="10%">支社</th>
			<th width="8%">门店地区</th>
			<th width="8%">产品 </th>
			<th width="5%">门店等级</th>
			<th width="7%">基本工资</th>
			<th width="7%">其他补助</th><!-- 
			<th width="7%">最低补助</th>
			<th width="7%">DVD 补助</th>
			<th width="7%">MIC 补助</th>
			<th width="7%">RAC 补助</th>
			<th width="7%">VACL 补助</th>
			<th width="7%">扩展补助</th> -->
			<th width="7%">更新人</th>
			<th width="7%">更新时间</th>
			<th width="3%">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${fixedPay}" var="item" varStatus="i">
			<tr target="sid" rel="PAY_AREA_CD=${item.PAY_AREA_CD}&BRANCH_CD=${item.BRANCH_CD}&SHOP_AREA_ID=${item.SHOP_AREA_ID}&SHOP_LEVEL=${item.SHOP_LEVEL}&PROD_TP=${item.PROD_TP}">
				<td>${item.PAY_AREA_NM}</td>
				<td class='td_center'>${item.BRANCH_NM}</td>
				<td class='td_center'>${item.SHOP_AREA_NM}</td>
				<td class='td_center'>${item.PROD_TP_NM}</td>
				<td class='td_center'>${item.SHOP_LEVEL_NM}</td>
				<td class='td_right'>${item.BASIC_INCTV_AMT}</td>
				<td class='td_right'>${item.ALOWN_AMT}</td><!-- 
				<td class='td_right'>${item.LOWST_INCTV_AMT}</td>
				<td class='td_right'>${item.DVD_ALOWN_AMT}</td>
				<td class='td_right'>${item.MIC_ALOWN_AMT}</td>
				<td class='td_right'>${item.RAC_ALOWN_AMT}</td>
				<td class='td_right'>${item.VACL_ALOWN_AMT}</td>
				<td class='td_right'>${item.SML_PROD_ALOWN_AMT}</td> -->
				<td class='td_center'>${item.UPDT_USER}</td>
				<td class='td_center'>${item.UPDT_DTIME}</td>
				<td class='td_center'>${item.USE_YN}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/promoter/viewFixedPayList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
