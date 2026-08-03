<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearchPaBankInfo(form,callback) {	
	var $form = $("#updatePaAccountCtrollerView");
	if (!$form.valid()) {
		return false;
	}
	
	var bankId  = document.getElementById("BANK_NAME_CODE").value;
	if(bankId == ''){
		alertMsg.error('开户行为必选项，请选择！');
		$("#BANK_NAME_CODE").focus();
		return false;
	}else{				
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
	return false ;
}
</script>
<div class="pageContent">
	<form id="updatePaAccountCtrollerView" method="post" action="/pa/wagebase/updatePaAccountInfo" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return navTabSearchPaBankInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label><spring:message code="public.title.empId"/><!--工号-->:</label>
				<label>${paAccountCtroller.EMPID }</label>
				<input type="hidden" name="PERSON_ID" value="${paAccountCtroller.PERSON_ID }" />
			</p>
			<p>
				<label><spring:message code="pa.title.message.empHrmName"/><!--人事姓名--></label>
				<label>${paAccountCtroller.CHINESE_NAME }</label>
			</p>
			<p>
				<label><spring:message code="pa.title.message.empPaName"/><!--账号名-->:</label>
				<!--<label>${paAccountCtroller.CARD_NAME }</label>-->
				<c:if test="${paAccountCtroller.NATIONALITY_CODE eq '871'}">
					<label>${paAccountCtroller.CARD_NAME }</label>
					<input type="hidden" id="CARD_NAME" name="CARD_NAME" value="${paAccountCtroller.CARD_NAME}" />
				</c:if>
				<c:if test="${paAccountCtroller.NATIONALITY_CODE ne '871'}">
					<input type="text" id="CARD_NAME" name="CARD_NAME" value="${paAccountCtroller.CARD_NAME }" class="required"/>
				</c:if>
			</p>
			<p>
				<label><spring:message code="public.title.deptName"/><!--部门-->:</label>
				<label>${paAccountCtroller.DEPTNAME }</label>
				<input type="hidden" name="DEPTNAME" value="${paAccountCtroller.DEPTNAME}" />
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.postGrade"/><!--职级-->:</label>
				<label>${paAccountCtroller.POST_GRADE_NAME }</label>
				<input type="hidden" name="POST_GRADE_NAME" value="${paAccountCtroller.POST_GRADE_NAME }" />
			</p>
			<p>
				<label><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->:</label>
				<label>${paAccountCtroller.STATUS_NAME }</label>
				<input type="hidden" name="STATUS_NAME" value="${paAccountCtroller.STATUS_NAME }" />
			</p>
			<p>
				<label><spring:message code="pa.wagebase.title.openAccountBanks"/><!--开户行--></label>
				<!--
				<ait:selectSyCode name="BANK_NAME_CODE" parentNo="772" selected="${paAccountCtroller.BANK_NAME_CODE }"/>
				-->
				<select class="combox" name="BANK_NAME_CODE" ID="BANK_NAME_CODE" ref="combox_BANK_BRANCH_CD"
					refUrl="/pa/wagebase/getBankBranchByBankNo?seach_BANK_NO={value}">
					<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
					<c:forEach items="${bankList}" var="bank">
						<option value="${bank.BANK_ID}" <c:if test="${bank.BANK_ID eq paAccountCtroller.BANK_ID}">selected</c:if>>${bank.BANK_NAME}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label><spring:message code="pa.title.message.bankBranchNameInfo"/><!-- 银行支行名称 --></label>
				<select class="combox" name="BANK_BRANCH_CD" ID="combox_BANK_BRANCH_CD">
					<%--所选银行是中国银行时，银行区域为必选项--%>
					<c:forEach items="${bankBranchList}" var="branch">
						<option value="${branch.BANK_BRANCH_CD}" <c:if test="${branch.BANK_BRANCH_CD eq paAccountCtroller.BANK_BRANCH_CD}">selected</c:if>>${branch.BANK_BRANCH_NAME}</option>
					</c:forEach>
				</select>
				<font color="red" size="1">(*开户行为中国银行时，必选项.)</font>
			</p>	              
 			<p>
				<label><spring:message code="pa.wagebase.title.accountNo"/><!--账号-->:</label>
				<input name="CARD_NO" type="text" value="${paAccountCtroller.CARD_NO }" class="required"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				     <spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>