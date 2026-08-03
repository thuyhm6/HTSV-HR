<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
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
	<form onsubmit="return navTabSearch(this);"
		action="/pa/salary/viewPaComputeItemParamList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
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
	<c:set value="/pa/salary/addPaComputeItemParamView" var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/salary/deletePaComputeItemParamInfo?PARAM_NO={PARAM_NO}" var="delete_Url" />
	<c:set value="/pa/salary/updatePaComputeItemParamView?PARAM_NO={PARAM_NO}" var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<form onsubmit="return validateCallback_addarannualleaveview(this,navTabAjaxDone);" action="/pa/salary/viewShowPaComputeItemParamList" method="post">
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
	<table class="table" width="100%" layoutH="156">
		<thead>
			<tr>
			    <th width="6%" align="center"> <input type="checkbox" class="checkboxCtrl" group="c1"> </th>
				<th width="50">
					<spring:message code="pa.insurance.title.company"/><!--公司-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.precision"/><!--精度-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.carry"/><!--进位-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.caculateOrder"/><!--计算顺序-->
				</th>
				<!-- <th width="10%">是否显示</th>
				<th width="10%">显示顺序</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paItemPramList}" var="list" varStatus="i">
				<tr target="PARAM_NO"
					rel="${list.PARAM_NO}&ITEM_NO=${list.ITEM_NO }">
					 <td style="text-align: center" >
					  <input type="checkbox" id="c1_${i.index}" name="c1" value="${list.ITEM_NO}"> 
					  </td>
					<td>
						${list.CPNY_NAME}
					</td>
					<td>
						${list.ALIAS_NAME}
					</td>
					<td>
						${list.PRICISION}
					</td>
					<td>
						${list.CARRY_BIT}
					</td>
					<td>
						<c:if test="${i.first and not i.last}">
							<a
								href="/pa/salary/updatePCInfoByCalcuOrder?type=0&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo"> 
								<img src="/resources/images/button/down.gif" style="cursor: hand" />
							</a>
						</c:if>
						<c:if test="${i.last and not i.first}">
							<a
								href="/pa/salary/updatePCInfoByCalcuOrder?type=1&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo"> 
								<img src="/resources/images/button/up.gif" style="cursor: hand" />
							</a>
						</c:if>
						<c:if test="${not i.first and not i.last}">
							<a
								href="/pa/salary/updatePCInfoByCalcuOrder?type=0&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo"> 
								<img src="/resources/images/button/down.gif" style="cursor: hand" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<a
								href="/pa/salary/updatePCInfoByCalcuOrder?type=1&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo"> 
								<img src="/resources/images/button/up.gif" style="cursor: hand" />
							</a>
						</c:if>
					</td>
					<%-- <td style="text-align: center">
					    <select name="showyn_${list.ITEM_NO}" id="showyn_${list.ITEM_NO}">
					       <option  <c:if test="${list.SHOW_YN eq 'Y' }">selected="selected"</c:if>  value="Y">是</option>
					       <option  <c:if test="${list.SHOW_YN eq 'N' }">selected="selected"</c:if> value="N">否</option>
					    </select>
					</td>
					<td style="text-align: center"> <input type="text"  size="4" name="showorder_${list.ITEM_NO}" id="showorder_${list.ITEM_NO}" value="${list.SHOW_ORDER }" /></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
		<c:set value="/pa/salary/viewShowPaComputeItemParamList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>