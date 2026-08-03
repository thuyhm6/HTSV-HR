<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    
      function delectApplyInfo(){
    	    // 确定提交删除申请吗？
    	   if(window.confirm('<spring:message code="ess.empInfo.sure_submit_application_deletion" />')){
    		   $("input[name=APPLY_TYPE]").val("3");
    	 /* $("#viewEmergencyAddressInfo").submit(); */
    	 	
    	}
    }
     
       function validateCallInfo(form, callback) {
	
		var $form = $("#deleteEmergencyAddressInfo");
		
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
	
		var $form = $("#viewEmergencyAddressInfo");
		
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

<!-- <div class="pageContent"> -->
<div style="background-color: #fff;">
	<!-- 紧急联系地址   hr_emergency_address  没有更换名字 -->

	<form id="viewEmergencyAddressInfo" method="post"
		action="/ess/empinfo/addEmergencyAddressInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
	
		<div class="pageFormContent" layoutH="56">
			<c:forEach items="${EmergencyAddressList}" var="item">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">
			<input type="hidden" name="UPDATE_EMERGENCY_NO" value='${item.EMERGENCY_NO}'/>
			<input type="hidden" name="APPLY_TYPE" value='${2}'/>
				
				<tr>
					<td class="td_title" width="15%">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
						<!--姓名-->
					</td>
					<td class="td_type" width="35%">
							<input type="text" name="EMER_NAME" 
							class="textInput" maxlength="80" value="${item.EMER_NAME}" />
					</td>
					<td class="td_title" width="15%">
						<spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" /><!--主要联络处与否-->
					</td>
					<td class="td_type" width='35%'>
						<input type="checkbox" value="Y" name="MAIN_LIAISON_OFFICE"
							<c:if test="${item.MAIN_LIAISON_OFFICE eq 'Y'}">checked="checked"</c:if>>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="15%">
						<spring:message code="ess.empInfo.relationship" />
						<!-- 关系-->
					</td>
					<td class="td_type" width="35%">
						<ait:SelectSyCodeByCpnyID name="EMER_TYPE_CODE" selected="${item.EMER_TYPE_CODE}" parentNo="1693"
							cnpyID="${defaultCpny}" />
					<td class="td_title" width='15%'>
						<!-- E-Mail --><spring:message code="hrm.empinfo.EMAIL" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_EMAIL" 
						value='${item.EMER_EMAIL}'	class="textInput" maxlength="80"  />
					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<!--联系电话--><spring:message code="ess.empInfo.contact_number" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_PHONE" value="${item.EMER_PHONE}"
							class="textInput" maxlength="80"  />
					</td>
					<td class="td_title" width='15%'>
						<!--联系电话2--><spring:message code="hrm.empinfo.LIANXI_DIANHUA_TWO.Z" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_PHONE_SECOND" value="${item.EMER_PHONE_SECOND}"
							class="textInput" maxlength="80"  />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_type" width='35%' style="display:none">
						<input type="text" name="EMER_CELLPHONE" 
							class="textInput" maxlength="80" value="${item.EMER_CELLPHONE}"/>
					</td>
					<td class="td_title" width='15%'>
					<!--办公电话--><spring:message code="ess.empInfo.office_telephone" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_WORK_PHONE" 
							class="textInput" maxlength="80" value="${item.EMER_WORK_PHONE}"/>
					</td>
					<td class="td_title" width='15%'>
					<!--国家--><spring:message code="ess.empInfo.Country" />
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY"  parentNo="870" selected="${item.NATIONALITY}"
							cnpyID="${defaultCpny}" />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr style="display:none">
					<td class="td_title" width='15%'>
						<spring:message code="ess.empInfo.phone_number" /><!--手机号码-->
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_CELLPHONE" 
							class="textInput" maxlength="80" value=""/>
					</td>
					<td class="td_title" width='15%'>
					<!--办公电话--><spring:message code="ess.empInfo.office_telephone" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_WORK_PHONE" 
							class="textInput" maxlength="80" value=""/>
					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
					<!--国家--><spring:message code="ess.empInfo.Country" />
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY"  parentNo="870" selected="${item.NATIONALITY}"
							cnpyID="${defaultCpny}" />
					</td>
					<td class="td_title" width='15%'></td>
					<td class="td_type" width='35%'></td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="ess.empInfo.home_address" />
						<!--地址-->
					</td>
					<td class="td_type" width='75%' colspan="3">
						<input type="text" name="EMER_ADDRESS" value="${item.EMER_ADDRESS}"
							class="textInput" maxlength="300" style="width: 98%" />
					</td>
				</tr>

			</table>
			
			</c:forEach>


			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<%-- <spring:message code="public.title.submit" /> --%>
								<!-- 保存 -->
								<!--修改--><spring:message code="ess.empInfo.modify" />
								</button>
						</div>
					</div>
				</li>
				<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<%-- <a type="button" class="close" onclick="javascript:delectApplyInfo()" >
							<span><spring:message code="public.title.delete"/></span></a> --%>
							<a  onclick="javascript:delectApplyInfo()">
								<button><spring:message code="public.title.delete"/></button>
								<!-- 删除 -->
							</a>
					</div>
				</div>
			</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
<!-- </div> --></div>




<!-- 删除申请-->
<%-- <div style="visibility: hidden;">

<form id="deleteEmergencyAddressInfo" method="post"
		action="/ess/empinfo/deleteEmergencyAddressInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallInfo(this, dialogAjaxDone);">
	
			<div class="pageFormContent" layoutH="56">
			<c:forEach items="${EmergencyAddressList}" var="item">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">
			<input type="hidden" name="UPDATE_EMERGENCY_NO" value='${item.EMERGENCY_NO}'/>
			<input type="hidden" name="APPLY_TYPE" value='${3}'/>
				
				<tr>
					<td class="td_title" width="15%">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
						<!--姓名-->
					</td>
					<td class="td_type" width="35%">
						<input type="text" name="EMER_NAME" 
							class="textInput" maxlength="80" value="${item.EMER_NAME}" />
					</td>
					<td class="td_title" width="15%">
						关系
						<!-- 关系-->
					</td>
					<td class="td_type" width="35%">
						<ait:SelectSyCodeByCpnyID name="EMER_TYPE_CODE" selected="${item.EMER_TYPE_CODE}" parentNo="870"
							cnpyID="${defaultCpny}" />
					</td>
<!-- 					<td class="td_title" width="15%"> -->
<!-- 						主要联络处与否 -->
<!-- 						主要联络处与否 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="35%"> -->
					<c:if test="${item.MAIN_LIAISON_OFFICE=='N'}">
					${item.MAIN_LIAISON_OFFICE}
<!-- 						<input type="checkbox" value="N" name="MAIN_LIAISON_OFFICE"> -->
                </c:if>    <c:if test="${item.MAIN_LIAISON_OFFICE=='Y'}">
<!--                 						<input type="checkbox" value="Y" name="MAIN_LIAISON_OFFICE" checked="checked"> -->
                
					</c:if>
<!-- 					</td> -->
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<!--	<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>-->
						手机号码
						<!--地址-->
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="MOBILE_PHONE" value="${item.MOBILE_PHONE}"
							class="textInput" maxlength="80"  />
					</td>
					<td class="td_title" width='15%'>
							E-mail
						<!--地址-->
					</td>
					<td class="td_type" width='35%'>


						<input type="text" name="EMER_EMAIL" 
						value='${item.EMER_EMAIL}'	class="textInput" maxlength="80"  />
					</td>
				
<!-- 					<td class="td_title" width="15%"> -->
<!-- 						联系电话 -->
<!-- 						联系电话 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="35%"> -->
<!-- <input type="text" name="EMER_PHONE"  -->
							class="textInput" maxlength="80" value="${item.EMER_PHONE}" />
<!-- 					</td> -->
<!-- 					<td class="td_title" width="15%"> -->
<!-- 						关系 -->
<!-- 						关系 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="35%"> -->
						<ait:SelectSyCodeByCpnyID name="EMER_TYPE_CODE" selected="${item.EMER_TYPE_CODE}" parentNo="870"
							cnpyID="${defaultCpny}" />
<!-- 					</td> -->

				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<!--	<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>-->
							工作处电话
						<!--地址-->
					</td>
					<td class="td_type" width='35%'>


						<input type="text" name="EMER_PHONE_SECOND" 
							class="textInput" maxlength="80" value="${item.EMER_PHONE_SECOND}"/>
					</td>
					<td class="td_title" width='15%'>
						国籍
						<!--国家-->
					</td>
					<td class="td_type" width='75%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY"  parentNo="870" selected="${item.NATIONALITY}"
							cnpyID="${defaultCpny}" />

					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<!--	<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>-->
						地址
						<!--地址-->
					</td>
					<td class="td_type" width='75%'>


						<input type="text" name="EMER_ADDRESS" value="${item.EMER_ADDRESS}"
							class="textInput" maxlength="300" style="width: 80%" />
					</td>
				</tr>
			

			</table>
			
<!-- 			<table width="100%" border="0" cellpadding="0" cellspacing="0" -->
<!-- 				class="user_table margin_b"> -->

<!-- 				<tr> -->
<!-- 					<td class="td_title" width='15%'> -->
<!-- 							<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/> -->
<!-- 						手机号码 -->
<!-- 						地址 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width='35%'> -->


						<input type="text" name="MOBILE_PHONE" value="${item.MOBILE_PHONE}"
<!-- 							class="textInput" maxlength="80"  /> -->
<!-- 					</td> -->
				
			
<!-- 					<td class="td_title" width='15%'> -->
<!-- 							<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/> -->
<!-- 							工作处电话 -->
<!-- 						地址 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width='35%'> -->


<!-- 						<input type="text" name="EMER_PHONE_SECOND"  -->
							class="textInput" maxlength="80" value="${item.EMER_PHONE_SECOND}"/>
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title" width='15%'> -->
<!-- 					E-mail -->
<!-- 						地址 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width='35%'> -->


<!-- 						<input type="text" name="EMER_EMAIL"  -->
						value='${item.EMER_EMAIL}'	class="textInput" maxlength="80"  />
<!-- 					</td> -->
				
			
<!-- 					<td class="td_title" width='15%'> -->
					
<!-- 					</td> -->
<!-- 					<td class="td_type" width='35%'> -->


						
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
			
<!-- 			<table width="100%" border="0" cellpadding="0" cellspacing="0" -->
<!-- 				class="user_table margin_b"> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title" width='15%'> -->
<!-- 						国家 -->
<!-- 						国家 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width='75%'> -->
						<ait:SelectSyCodeByCpnyID name="NATIONALITY"  parentNo="870" selected="${item.NATIONALITY}"
							cnpyID="${defaultCpny}" />

<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title" width='15%'> -->
<!-- 							<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/> -->
<!-- 						地址 -->
<!-- 						地址 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width='75%'> -->


						<input type="text" name="EMER_ADDRESS" value="${item.EMER_ADDRESS}"
<!-- 							class="textInput" maxlength="300" style="width: 80%" /> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
			</c:forEach>


			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		
	</form>

</div> --%>