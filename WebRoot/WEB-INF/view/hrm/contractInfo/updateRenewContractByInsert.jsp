<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">

$(document).ready(function(){
	$("#CONTRACT_TYPE_CODE").change(function(){
		if($("#CONTRACT_TYPE_CODE").val() == '1819'){
			$("#contractEndTime").hide();
		$("#END_CONTRACT_DATE").attr("value","");//隐藏时清空结束日期的值
		}else{
			$("#contractEndTime").show();
		}
	});
	if($("#CONTRACT_TYPE_CODE").val() == '1819'){
			$("#contractEndTime").hide();
		$("#END_CONTRACT_DATE").attr("value","");//隐藏时清空结束日期的值
		}
});

function validateCallbackUpdateRenewContractByInsert(form, callback) {
	var $form = $("#updateRenewContractByInsert");
		
		if (!$form.valid()) {
			return false;
		}
		
		if($("#CONTRACT_TYPE_CODE").val()== 1819 && ""!= $("#END_CONTRACT_DATE").val()){
			alertMsg.error('<spring:message code="hr.alert.message.viewContractByInsert.noEndDate"/>');//"无固定期限合同不能有结束日期"
			document.getElementById("END_CONTRACT_DATE").value="";
			return false;
		}
			
		if(""!=(document.getElementById("START_CONTRACT_DATE").value) && ""!=(document.getElementById("END_CONTRACT_DATE").value)){
			
			var sd=document.getElementById("START_CONTRACT_DATE").value;
			var ed=document.getElementById("END_CONTRACT_DATE").value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				activity="1";
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//"合同开始时间不能晚于结束时间"
				document.getElementById("START_CONTRACT_DATE").focus();
				return false;
			}
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

 <div class="pageContent">
     <form id="updateRenewContractByInsert" method="post" action="/hrm/contractInfo/updateRenewContractByInsertInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallbackUpdateRenewContractByInsert(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">     
            <dl>
				<dt><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</dt>
				<dd>
					${personInfo.EMPID }
				</dd>
			</dl>
			<dl>
				<dt><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</dt>
				<dd>
					${personInfo.LOCAL_NAME }
				</dd>
			</dl>
			<dl>
				<dt><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</dt>
				<dd>
					${personInfo.DEPTNAME }
				</dd>
			</dl>
		   <dl>
				<dt><!--合同类型-->
					<spring:message code="hr.viewContract.title.CONTRACT_TYPE_NAME"/>
				</dt>
				<dd>                
                    <select id="CONTRACT_TYPE_CODE" name="CONTRACT_TYPE_CODE">
                    <%--
                        <option value="">
                            <spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择-->
                        </option>
                    --%>
                        <c:forEach items="${detailList}" var="item">
                            <option value="${item.CODE_NO }"
                                <c:if test="${item.CODE_NO eq personInfo.EMP_TYPE_CODE }">selected</c:if>
                            >${item.CODE_NAME }</option>
                        </c:forEach>
                    </select>
                    <%--
					<ait:selectSyCode name="CONTRACT_TYPE_CODE" parentNo="${personInfo.EMP_TYPE_CODE}" />
                     --%>
					<input name="PERSON_ID" type="hidden" size="30"  value="${PERSONID }"/>
				</dd>
			</dl>
		    <dl>
				<dt><!--起始日期:-->
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
				</dt>
				<dd>
					<input type="text" id="START_CONTRACT_DATE" name="START_CONTRACT_DATE" value="${personInfo.END_CONTRACT_DATE }" class="date required" readonly="true" />
				</dd>
			</dl>
			<dl id="contractEndTime">
				<dt><!--终止日期:-->
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
				</dt>
				<dd>
					<input type="text" id="END_CONTRACT_DATE" name="END_CONTRACT_DATE" class="date" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt><!--描述:-->
					<spring:message code="hr.viewPromote.title.REMARK"/>
				</dt>
				<dd>
					<textarea cols="100" rows="4" class="l-textarea" name="REMARK" id="REMARK" style="width:400px" class="required" maxlength="60"></textarea>
				</dd>
			</dl>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
					<li>
						<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
					</li>
				</ul>
			</div> 
			</div>
	</form>
</div>
