<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_addarannualleaveview(form, callback) {

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewSummaryParamItem" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 汇总项目名称 --><spring:message code="ar.viewsummaryitem.title.huizongxiangmumingcheng"/>：<input type="text" name="ITEM_NAME" value="${ITEM_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form> 
</div>
<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addSummaryParamItemView" var="add_Url"/>
	
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteSummaryParamItemInfo?PARAM_NO={paramNo}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateSummaryParamItemView?PARAM_NO={paramNo}" var="edit_Url"/>
	
	
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	
	<form onsubmit="return validateCallback_addarannualleaveview(this,navTabAjaxDone);" action="/ar/attendanceSettings/viewShowSummaryParamItem" method="post">
	<!-- <div class="formBar">
			<ul  class="toolBar">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div> -->
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="6%" align="center"> <input type="checkbox" class="checkboxCtrl" group="c1"> </th>
				<th width="10%"><!-- 公司名称 --><spring:message code="ar.viewcycleparameter.title.gongsimingcheng"/></th>
				<th width="20%"><!-- 汇总项目名称 --><spring:message code="ar.viewsummaryitem.title.huizongxiangmumingcheng"/></th>
				<th width="8%"><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></th>
				<th width="10%"><!-- 最小单位 --><spring:message code="ar.viewitemparameter.title.zuixiaodanwei"/></th>
				<th width="10%"><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></th>
				<th width="10%"><!-- 排序 --><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/></th>
<%--				<th width="10%">是否显示</th>--%>
				<th width="10%"><!-- 显示顺序 --><spring:message code="ar.viewSummaryParamItem.XIANSHISHUNXU.b"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemParamList}" var="item" varStatus="i">
				<tr  target="paramNo" rel="${item.PARAM_NO}&CPNY_ID=${item.CPNY_ID}&STA_ITEM_ID=${item.STA_ITEM_ID}">
				    <td style="text-align: center" > <input type="checkbox" id="c1_${i.index}" name="c1" value="${item.ITEM_NO}"> </td>
					<td style="text-align: center" >${item.CPNY_NAME}</td>
					<td style="text-align: center">${item.ITEM_NAME}</td>
					<td style="text-align: center"><c:if test="${item.UNIT eq 'DAY'}"><!-- 天 --><spring:message code="ar.viewsummaryparameteritem.title.day"/></c:if>
						<c:if test="${item.UNIT eq 'HOUR'}"><!-- 小时 --><spring:message code="ar.viewsummaryparameteritem.title.hour"/></c:if>
						<c:if test="${item.UNIT eq 'MINUTE'}"><!-- 分钟 --><spring:message code="ar.viewsummaryparameteritem.title.minite"/></c:if>
						<c:if test="${item.UNIT eq 'TIME'}"><!-- 计数 --><spring:message code="ar.viewsummaryparameteritem.title.count"/></c:if>
					</td>
					<td style="text-align: center">${item.MIN_UNIT}</td>
					<td style="text-align: center"><img src="/resources/images/a_${item.ACTIVITY}.gif"/></td>
					<td style="text-align: center">
						<c:if test="${i.first and not i.last}">
							<a href="/ar/attendanceSettings/updateSummaryParamItemOrder?downParamNo=${item.PARAM_NO}&downOrder=${item.DOWNORDER}&upParamNo=${item.UP_PARAM_NO}&upOrder=${item.CAL_ORDER}" target="ajaxTodo">
								<img src="/resources/images/button/down.gif" style="cursor:hand"/>
							</a>
						</c:if>
						<c:if test="${i.last and not i.first}">
							<a href="/ar/attendanceSettings/updateSummaryParamItemOrder?downParamNo=${item.DOWN_PARAM_NO}&downOrder=${item.CAL_ORDER}&upParamNo=${item.PARAM_NO}&upOrder=${item.UPORDER}" target="ajaxTodo">
								<img src="/resources/images/button/up.gif" style="cursor:hand"/>
							</a>
						</c:if>
						<c:if test="${not i.first and not i.last}">
							<a href="/ar/attendanceSettings/updateSummaryParamItemOrder?downParamNo=${item.PARAM_NO}&downOrder=${item.DOWNORDER}&upParamNo=${item.UP_PARAM_NO}&upOrder=${item.CAL_ORDER}" target="ajaxTodo">
								<img src="/resources/images/button/down.gif" style="cursor:hand"/>
							</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<a href="/ar/attendanceSettings/updateSummaryParamItemOrder?downParamNo=${item.DOWN_PARAM_NO}&downOrder=${item.CAL_ORDER}&upParamNo=${item.PARAM_NO}&upOrder=${item.UPORDER}" target="ajaxTodo">
								<img src="/resources/images/button/up.gif" style="cursor:hand"/>
							</a>
						</c:if>
					</td>
<%--					<td style="text-align: center">--%>
<%--					    <select name="showyn_${item.ITEM_NO}" id="showyn_${item.ITEM_NO}">--%>
<%--					       <option  <c:if test="${item.SHOW_YN eq 'Y' }">selected="selected"</c:if>  value="Y">是</option>--%>
<%--					       <option  <c:if test="${item.SHOW_YN eq 'N' }">selected="selected"</c:if> value="N">否</option>--%>
<%--					    </select>--%>
<%--					</td>--%>
					<td style="text-align: center"> <input type="text"  size="4" name="showorder_${item.ITEM_NO}" id="showorder_${item.ITEM_NO}" value="${item.CAL_ORDER }" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
</div>		