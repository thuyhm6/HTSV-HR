<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doIncBasicSetupByDayListExport(from){
  	var $from =$(from);
  	var url ="${base}/promoter/viewIncBasicSetupByDayListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expIncBasicSetupByDayList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewIncBasicSetupByDay");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doIncBasicSetupByDayListExport($from);}});
    } 
}
function checkDate(){
	var START_DATE = document.viewIncBasicSetupByDay.seach_START_DATE.value;
	var END_DATE   = document.viewIncBasicSetupByDay.seach_END_DATE.value;
	
	if(START_DATE !="" && END_DATE =="")
	{
		alert("请选择结束日期！");
		return false;
	}
	if(START_DATE =="" && END_DATE !=""){
		alert("请选择开始日期！");
		return false;
	}
	return true;
}

</script>

<div class="pageHeader">
	<form id="viewIncBasicSetupByDay" name="viewIncBasicSetupByDay" onsubmit="return navTabSearch(this);" action="/promoter/viewIncBasicSetupByDayList" method="post" rel="pagerForm">
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
					<input type="text" name="seach_PROD_IDs" id="seach_PROD_IDs" value="${PROD_IDs}" maxlength="20"></select>
				</td>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
                    <!--结束时间:-->
                </td>
			    <td>
			        <input type="text" name="seach_START_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${START_DATE }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>
				<td>
				    <input type="text" name="seach_END_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value = "${END_DATE }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onClick="return checkDate();"><spring:message code="public.title.search"/><!-- 检索 --></button>
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
	<c:set value="450" var="add_height"/>
	<c:set value="/promoter/addIncBasicSetupByDayView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="450" var="edit_height"/>
	<c:set value="/promoter/updateIncBasicSetupByDayView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 序列 -->
					序列
				</th>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 产品类型-->
 					产品类型
				</th>
				<th width="100"><!-- 提成率 -->
 					提成率
				</th>
				<th width="100"><!-- 总部提成 -->
 					总部提成
				</th>
				<th width="100"><!-- 总部单价-->
 					总部单价
				</th>
				<th width="100"><!--更新人 -->
					更新人
				</th>
				<th width="100"><!-- 状态-->
					状态
				</th>
				<th width="100"><!-- 提成开始日期 -->
 					提成开始日期
				</th>
				<th width="100"><!-- 提成结束日期 -->
					提成结束日期
				</th>
				<th width="100"><!-- 实贩卖月份 -->
					实贩卖月份
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="SEQ=${item.SEQ}">
					<td>${item.SEQ}</td>
	                <td>${item.PROD_ID}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td class='td_right'>${item.INC_RATE}</td>
	                <td class='td_right'>${item.SUBSD_INCTV_AMT}</td>
	                <td class='td_right'>${item.HEAD_UNIT_PRC}</td>
	                <td class='td_center'>${item.UPDT_USER}</td>
	                <td class='td_center'>${item.USE_YN}</td>
	                <td class='td_center'>${item.PRC_BEGIN_DAY}</td>
	                <td class='td_center'>${item.PRC_END_DAY}</td>
	                <td class='td_center'>${item.YYYYMM}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewIncBasicSetupByDayList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
