<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(function() {
	$('#TestA', navTab.getCurrentPanel()).hide();
});

function validateCallInfo(form) {

	var $form = $("#setPaParam");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}

function exportExcel() {
	var post = document.getElementById('postName');
	post.action = "/pa/paView/viewWageComExcel";
	post.submit();
}

function selectAll(obj) {
	var name = obj.id;
	var PARAM_NO = 'PARAM_NO_' + name;

	$("[alt='" + PARAM_NO + "']").attr("checked", obj.checked);

}
</script>
<a id="importExcel_pa0824" href="#" target="navTab" mask="true"><span
	style="display: none;"><!--工资数据导入结果--><spring:message code="pa.viewPaParamDownloud.GONGZISHUJUDAORUJIEGUO.C" /></span> </a>
<div class="pageContent">
	<form onsubmit="return validateCallInfo(this);" rel="pagerForm"
		action="/pa/workManagement/setPaParam" method="post" id="setPaParam"
		id="postName">
		<input type="hidden" id="afterErrorNum" name="afterErrorNum"
			value="${afterErrorNum2.ERROR_NUMBER}">

		<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent" height="100PX">
					<tr>
						<td>
							<!--错误项目数--><spring:message code="ar.viewPaParamUploudList.ERROR_ITEM_NUMS.b" />: ${afterErrorNum2.ERROR_NUMBER}
						</td>
					</tr>

				</table>


				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div>
									<button type="submit">
										<span><!--确认导入--><spring:message code="ar.viewPaParamUploudList.CONFIRM_IMPORT.b" /></span>
									</button>
								</div>

							</div>
						</li>


					</ul>
				</div>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<form name="viewPaParamUploudList" id="viewPaParamUploudList" method="post"
		action="/pa/workManagement/viewPaParamUploudList" >
		<table width="100%">

			<table class="table" width="100%" layoutH="200">
				<thead>
					<tr>
						<th width="12">
							<!--序号--><spring:message code="org.title.NO" />
						</th>
						<th width="12">
							<!--项目 --><spring:message code="hr.viewCondSql.title.XIANGMU" />CODE
						</th>
						<th width="12">
							<!--项目名称--><spring:message code="pa.insurance.title.itemName" />
						</th>
						<th width="12">
							<!--社号--><spring:message code="public.title.empId" />
						</th>
						<th width="12">
							<!--开始月--><spring:message code="pa.insurance.title.startMonth" />
						</th>
						<th width="12">
							<!--结束月--><spring:message code="pa.insurance.title.endMonth" />
						</th>
						<th width="12">
							<!--数值--><spring:message code="pa.insurance.title.dataValue" />
						</th>
						<th width="12">
							<!--备注--><spring:message code="pa.salary.canShu.beiZhu" />
						</th>
						<th width="12">
							<!--错误项目--><spring:message code="ar.viewPaParamUploudList.ERROR_ITEM.b" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${afterList}" var="afterList" varStatus="i">
						<tr>
							<td>
								${i.count }
							</td>
							<td>
								${afterList.PARAM_ITEM_NO }
							</td>
							<td>
								${afterList.PARAM_ITEM_NAME }
							</td>
							<td>
								${afterList.EMPID }
							</td>
							<td>
								${afterList.START_MONTH }
							</td>
							<td>
								${afterList.END_MONTH }
							</td>
							<td>
								${afterList.RETURN_VALUE }
							</td>
							<td>
								${afterList.REMARK }
							</td>
							<td>
								<font color="red">${afterList.UPLOAD_ERROR_MSG } </font>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</form>
	<c:set value="/pa/workManagement/viewPaParamUploudList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>