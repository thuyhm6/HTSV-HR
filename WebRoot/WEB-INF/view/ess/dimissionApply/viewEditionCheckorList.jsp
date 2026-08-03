<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitForm(){
  	var $from = $("#viewEditionCheckList");
  	$from.submit();
}

function validateCallbackInsertContract(form, callback) {


	var $form = $("#updateEditionItemParamInfo");
	 
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("isChecked");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.qianding"/>');
		return false;
	}
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
function selectAll(parentCheckBox){
	var flag = parentCheckBox.checked;
	var ids= document.getElementsByName("isChecked");
	for(var i=0;i<ids.length;i++){
		ids[i].checked=flag;
	}
}
</script>
<div class="pageHeader">
	<form id="viewEditionCheckList" onsubmit="return navTabSearch(this);" action="/ess/dimissionApply/viewEditionCheckorList" method="post" rel="pagerForm" >
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 工号/姓名： --> <spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /> 
				</td>
				<td>
				
				<input type="text" name="seach_KEY" value="${KEY}" />
				
				</td>
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

<form id="updateEditionItemParamInfo" method="post"
	action="/ess/dimissionApply/updateEditionItemParamInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackInsertContract(this, navTabAjaxDone)" >
	<div class="formBar">
		</div>
	<table class="table" width="100%" layoutH="208">
		<thead>
			<tr>
				<th width="5%"><!-- 工号 --><spring:message code="display.emp.ben.serviceno"/></th>
				<th width="5%"><!-- 姓名 --><spring:message code="inct.salesman.Name"/></th>
				<th width="10%"><!-- 人员类型 --><spring:message code="is.company.title.PERSON_TYPE"/></th>
				<th width="10%"><spring:message code="ess.infoApply.title.essApplyTime"/><!-- 申请日期 --></th>
				<th width="10%"><spring:message code="display.pa.ecc.expectresigndate"/><!-- 预离职日期 --></th>
				<th width="10%"><spring:message code="ess.edition.title.edition_date"/><!-- 离职日期--></th>
				<th width="8%"><spring:message code="ess.trans.title.resignTypeName"/><!-- 离职类型--></th>
				<th width="21%"><spring:message code="ess.trans.title.resignReason"/><!-- 离职原因--></th>
				<th width="10%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/><!-- 状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionCheckList}" var="item" varStatus="i">
				<tr >
					<td style="text-align:left">${item.EMPID}</td>
					<td style="text-align:left"><a target="navTab"
						href="/ess/editionAffirm/viewAffirmEditionItemcheck?PERSON_ID=${item.PERSON_ID}&APPLY_NO=${item.APPLY_NO}" title="离职交接进度查看">${item.LOCAL_NAME}</a>
						</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME}</td>
					<td style="text-align:left">${item.APPLY_TIME}</td>
					<td style="text-align:left">${item.APPLY_LEAVE_TIME}</td>
					<td>${item.LEAVE_TIME}</td>
					<td><select name="LEAVE_TYPE_${i.index }" disabled="disabled">
					   <option value="0"<c:if test="${item.LEAVE_TYPE eq 0}">selected="selected"</c:if>><spring:message code="ess.edition.title.editiontype_beidong"/></option>
					   <option value="1"<c:if test="${item.LEAVE_TYPE eq 1}">selected="selected"</c:if>><spring:message code="ess.edition.title.editiontype_zhudong"/></option>
					</select></td>
					<td>${item.APPLY_REASON}</td>
					<td style="text-align:left">
					<c:if test="${item.AFFIRM_FG == 0 || item.AFFIRM_FG eq '0'}">
					    审批中
					</c:if>
					<c:if test="${item.AFFIRM_FG == 1 || item.AFFIRM_FG eq '1'}">
					    已通过
					</c:if>
					<c:if test="${item.AFFIRM_FG == 2 || item.AFFIRM_FG eq '2'}">
					   已终止
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/ess/dimissionApply/viewEditionCheckList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
