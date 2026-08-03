<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function affirmEditionItem(flag){
		$("#affirmEditionItemInfoFLAG_affirm").val(flag);
		$("#affirmEditionItemInfo").submit();
	}
	
// 	function validateCallback_dimission_item(form, callback) {
// 	var $form = $("#affirmEditionItemInfo");
// 	    if (!$form.valid()) {
// 		    return false;
// 	    }
// 		$.ajax({
// 			type: form.method || 'POST',
// 			url:$form.attr("action"),
// 			data:$form.serializeArray(),
// 			dataType:"json",
// 			cache: false,
// 			success: callback || DWZ.ajaxDone,
// 			error: DWZ.ajaxError
// 		});
// 		return false;
// 	}
</script>
<div class="pageContent">
	<form id="affirmEditionItemInfo" method="post" action="/ess/dimissionApply/updatePersonEditionParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<input type="hidden" id="affirmEditionItemInfoFLAG_affirm" name="FLAG" value="1" />
					<input type="hidden" name="APPLY_NO" value="${affirmItemInfo.APPLY_NO}"/>
					<input type="hidden" name="PERSON_ID" value="${affirmItemInfo.PERSON_ID}"/>
					<input type="hidden" name="DEPTNO" value="${affirmItemInfo.DEPTNO}"/>
	<div class="formBar">
	<c:if test="${affirm_fg == 0}">
			<ul>
				<li><div class="buttonContent"><button type="button" onclick="affirmEditionItem(2);">终止</button></div></li>
			</ul>
			</c:if>
		</div>
	<table class="table" width="100%" layoutH="460" nowrapTD="false">
		<thead>
			<tr>
				<th width="5%">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="5%">
					<!-- 姓名 -->
					<spring:message code="inct.salesman.Name" />
				</th>
				<th width="10%">
					部门
				</th>
				<th width="10%"><spring:message
						code="ess.infoApply.title.essApplyTime" />
					<!-- 申请日期 -->
				</th>
				<th width="10%"><spring:message
						code="display.pa.ecc.expectresigndate" />
					<!-- 预离职日期 -->
				</th>
				<th width="21%"><spring:message
						code="ess.trans.title.resignReason" />
					<!-- 离职原因-->
				</th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td style="text-align:left">${affirmItemInfo.EMPID }
					</td>
					<td style="text-align:left">${affirmItemInfo.LOCAL_NAME }</td>
					<td style="text-align:left">${affirmItemInfo.DEPT_NAME }</td>
					<td style="text-align:left">${affirmItemInfo.APPLY_TIME }</td>
					<td>${affirmItemInfo.APPLY_LEAVE_TIME }</td>
					<td>${affirmItemInfo.APPLY_REASON}</td>
				</tr>
		</tbody>
	</table>
		<div class="pageFormContent">
							<table class="table" width="100%" layoutH="220" nowrapTD="false">
		                       <thead>
		                       <tr>
								<th width="10%" class="td_title" style="text-align: center">
									项目
								</th>
								<th width="25%" class="td_title" style="text-align: center">
									内容
								</th>
								<th width="10%" class="td_title" style="text-align: center">
									担当者
								</th>
								<th width="10%" class="td_title" style="text-align: center">
									担当者(管理者)审核/日期
								</th>
								</tr>
								</thead>
							<tbody>
			   <c:forEach items="${affirmItemList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left">${item.EDITION_TYPE_NAME }
					<td colspan="2" style="text-align:left">${item.EDITION_ITEM_NAME }
					</td>
					<td style="text-align:left">${item.AFFIRM_NAME }</td>
					<td style="text-align:center">
					<c:if test="${affirm_fg == 0}">
					<c:if test="${item.AFFIRM_PERSON_ID eq pId}">
					<c:if test="${item.AFFIRM_FLAG == '0' || item.AFFIRM_FLAG eq '0'}">
					<input type="hidden" name="EDITION_PERSON_NO" value="${item.EDITION_PERSON_NO }">
					<a onclick="javascript:affirmEditionItem(1);">确认</a>
					</c:if></c:if></c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1' || item.AFFIRM_FLAG == 1}">
					   ${item.AFFIRM_NAME }已确认/${item.AFFIRM_DATE }
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2' || item.AFFIRM_FLAG == 2}">
					 ${item.AFFIRM_NAME }已终止/${item.AFFIRM_DATE }
					</c:if> 
					</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
		</div>
	</form>	
</div>