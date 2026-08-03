<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doMinGoalSetupListExport(from){
  	var $from =$(from);
  	var url ="${base}/promoter/viewMinGoalSetupListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expMinGoalSetupList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewMinGoalSetup");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doMinGoalSetupListExport($from);}});
    } 
}

</script>

<div class="pageHeader">
	<form id="viewMinGoalSetup" onsubmit="return navTabSearch(this);" action="/promoter/viewMinGoalSetupList" method="post" rel="pagerForm">
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
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="450" var="add_width"/>
	<c:set value="300" var="add_height"/>
	<c:set value="/promoter/addMinGoalSetupView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="300" var="edit_height"/>
	<c:set value="/promoter/updateMinGoalSetupView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 大区   -->
					大区
				</th>
				<th width="100"><!-- 产品类型   -->
					产品类型
				</th>
				<th width="100"><!-- 最小目标 -->
					最小目标
				</th>
				<th width="100"><!-- 更新时间-->
					更新时间
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
				<tr target="sid" rel="PAY_AREA_CD=${item.PAY_AREA_CD}&PROD_TP=${item.PROD_TP}">
					<td>${item.PAY_AREA_NM}</td>
					<td>${item.PROD_TP_NM}</td>
	                <td class='td_right'>${item.MIN_GOAL_AMT}</td>
	                <td>${item.UPDT_DTIME}</td>
	                <td>${item.UPDT_USER}</td>
	                <td class='td_center'>${item.USE_YN}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewMinGoalSetupList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
