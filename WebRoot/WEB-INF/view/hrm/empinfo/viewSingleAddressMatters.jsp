<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeleteResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteHrAddressMatters',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function changeMainOffice(index){
	var check=$('#changeMain').prop('checked');
	  
	 if(check==true){
		 document.getElementById("cMain_"+index).checked=true;
		 $('#MAIN_LIAISON_OFFICE').attr('value','Y');
	}else{
		 document.getElementById("cMain_"+index).checked=false;
		 $('#MAIN_LIAISON_OFFICE').attr('value','N');
	} 
	
	
}
</script>
<div class="pageContent" style="height:613px;">
	<div>
		<form id="editHrAddressMatters" method="post" action="/hrm/empinfo/editHrAddressMatters" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID" VALUE="${PERSON_ID}" >
        <input TYPE="hidden" NAME="isEssSystem" VALUE="${isEssSystem}" >
        <input type="hidden" name="ADDRESS_NO" id="ADDRESS_NO" value="${personInfo.ADDRESS_NO}">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" /><!--地址类型--></td>
									<td class="td_type"  width="25%">
										<ait:SelectSyCodeByCpnyID name="ADDRESS_TYPE" id="ADDRESS_TYPE"
                                 			parentNo="14013840" cnpyID="${defaultCpny}" selected="${personInfo.ADDRESS_TYPE}" limit="all"/>
									</td>
				 </tr>
				 <tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.approve.EFFECTIVE_START_DATE" /><!--有效开始日--></td>
					<td class="td_type"  width="25%" >
					<input name="EFFECTIVE_START_DATE"  id="EFFECTIVE_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.EFFECTIVE_START_DATE}" />
					</td>	
				 </tr>
				 <tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.empinfo.country" /><!--国家--></td>
				   <td class="td_type"  width="25%" >	<ait:SelectSyCodeByCpnyID name="NATIONALITY" id="NATIONALITY"
                                 parentNo="870" cnpyID="${defaultCpny}" selected="${personInfo.NATIONALITY}" limit="all" />
				 </td>
				 </tr>
				 <tr>
				 <td class="td_title" width="4%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址--></td>
					<td class="td_type"  width="25%" >
					 <input type="text"  id="ADDRESS_CONTENT" name="ADDRESS_CONTENT" value=${personInfo.ADDRESS_CONTENT }>
					</td>	
				 </tr>
			</table>
		
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			   <c:if test="${not empty personInfo.UPDATED_BY}">
			    <td class="td_title" width="5%"><spring:message code="org.title.UPDATED_IP" /><!--变更者--></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
					</td>
					<td class="td_title" width="5%"><spring:message code="org.title.UPDATE_DATE" /><!--变更时间--></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATE_DATE }
					</td>
					</c:if>
					<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><spring:message code="org.title.UPDATED_IP" /><!--变更者--></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATED_BY }&nbsp&nbsp${personInfo.CREATED_IP }
					</td>
					<td class="td_title" width="5%"><spring:message code="org.title.UPDATE_DATE" /><!--变更时间--></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATE_DATE }
					</td>
					</c:if>
								</tr>	
							</table>	
						</td>
					</tr>
				</table>
			</div>
	  	</form>	
	</div>
</div>