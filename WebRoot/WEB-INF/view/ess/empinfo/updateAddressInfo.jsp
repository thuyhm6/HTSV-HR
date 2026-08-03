<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
     function delectApplyInfo(){
    	 if(window.confirm('<spring:message code="ess.empInfo.sure_submit_application_deletion" />')){//确定提交删除申请吗？
               $("#deleteAddressInfo").submit();
              }
    	
    	
    }
     
       function validateCallInfo(form, callback) {
	
		var $form = $("#deleteAddressInfo");
		
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
    
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#addAddressInfo");
		
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

<div class="pageContent">
	<!-- 地址信息 -->
	<form id="addAddressInfo" method="post" action="/ess/empinfo/addAddressInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
		<input type="hidden" id="isEssSystem" name="addAddressInfo" value="${isEssSystem}"/>
		<div class="pageFormContent" layoutH="56">
		<c:forEach items="${AddressList}" var="item">
			<input type="hidden" name="APPLY_TYPE" value='${2}' />
		<input type="hidden" name="UPDATE_ADDRESS_NO" value='${item.ADDRESS_NO}'/>
		 		<input type="hidden" name="PERSON_ID" value='${item.PERSON_ID}'/>
		   	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!--地址类型--><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
					</td>
					<td class="td_type" width="35%">
						${item.ADDRESS_TYPE_NAME}
						<input type="hidden" name="ADDRESS_TYPE" class="textInput" value="${item.ADDRESS_TYPE}"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID}"/>
						<input type="hidden" name="ADDRESS_NO" class="textInput" value="${item.ADDRESS_NO}"/>
					
					</td>
					<td class="td_title" width="15%">
						<spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" />
									<!--有效开始日期-->
					</td>
					<td class="td_type" width="35%">
						<input name="EFFECTIVE_START_DATE" id="EFFECTIVE_START_DATE" class="Wdate" 
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${item.EFFECTIVE_START_DATE}"/>
					</td>
				</tr>
			</table>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!--地址--><spring:message code="ess.empInfo.address" />
					</td>
					<td class="td_type" width="85%">
						<input type="text" name="ADDRESS_CONTENT" value="${item.ADDRESS_CONTENT}" class="textInput" style="width: 500px"/>
					</td>
					</tr>
					<tr>
					<td class="td_title" >
						<!--国家--><spring:message code="org.title.COUNTRY" />
					</td>
					<td class="td_type" >
						<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870" cnpyID="${defaultCpny}" selected="${item.NATIONALITY}" />
					</td>
			
				</tr>
			</table>
			</c:forEach>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><a type="button" class="close" onclick="javascript:delectApplyInfo()" ><span><spring:message code="public.title.delete"/> </span><!--删除 --> </a></div></div>
			</li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
			</li>
		</ul>
	</div>
	</form>	
</div>

<!-- 删除 申请-->
<div style="visibility: hidden;">
	<form id="deleteAddressInfo" method="post" action="/ess/empinfo/addAddressInfo" class="pageForm required-validate"  onsubmit="return validateCallInfo(this, dialogAjaxDone);">

<c:forEach items="${AddressList}" var="item">
			<input type="hidden" name="APPLY_TYPE" value='${3}' />
		<input type="hidden" name="UPDATE_ADDRESS_NO" value='${item.ADDRESS_NO}'/>
		 		<input type="hidden" name="PERSON_ID" value='${item.PERSON_ID}'/>
		   	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!--地址类型--><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
					</td>
					<td class="td_type" width="35%">
						<ait:SelectSyCodeByCpnyID name="ADDRESS_TYPE" parentNo="14013840" cnpyID="${defaultCpny}" selected="${item.ADDRESS_TYPE_NAME}" />
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID}"/>
						<input type="hidden" name="ADDRESS_NO" class="textInput" value="${item.ADDRESS_NO}"/>
					
					</td>
					<td class="td_title" width="15%">
						<spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" />
									<!--有效开始日期-->
					</td>
					<td class="td_type" width="35%">
						<input type="text" value="${item.EFFECTIVE_START_DATE}"  name="EFFECTIVE_START_DATE" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					
				</tr>
			</table>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!--地址--><spring:message code="ess.empInfo.address" />
					</td>
					<td class="td_type" width="85%">
						<input type="text" name="ADDRESS_CONTENT" value="${item.ADDRESS_CONTENT}" class="textInput" style="width: 500px"/>
					</td>
					</tr>
					<tr>
					<td class="td_title" >
						<!--国家--><spring:message code="org.title.COUNTRY" />
					</td>
					<td class="td_type" >
						<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870" cnpyID="${defaultCpny}" selected="${item.NATIONALITY}" />
					</td>
			
				</tr>
			</table>
			</c:forEach>
<div>

</form>