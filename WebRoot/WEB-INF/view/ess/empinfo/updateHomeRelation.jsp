<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    
    
     function delectApplyInfo(){
    	 if(window.confirm('<spring:message code="ess.empInfo.sure_submit_application_deletion" />')){//确定提交删除申请吗？
               $("#deleteHomeRelationInfo").submit();
              }
    	
    	
    }
     
       function validateCallInfo(form, callback) {
	
		var $form = $("#deleteHomeRelationInfo");
		
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
	
		var $form = $("#addHomeRelationInfo");
		
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
    
    
 function copyAddressInfo(){
	
	 var copyAs=$("#My_Address").attr('value');
	 $("#By_Copy_Address").attr("value",copyAs);
 }
	
    
</script>
<div class="pageContent">

	<form id="addHomeRelationInfo" method="post" action="/ess/empinfo/addHomeRelationInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

<div class="pageFormContent" layoutH="56">

			<c:forEach items="${homeRelationList}" var="item" >
			
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table margin_b">
								<input type="hidden" name="UPDATE_FAMILY_NO" value='${item.FAMILY_NO}'/>
								<input type="hidden" name="APPLY_TYPE" value='${2}' />
					<tr>
						<td class="td_title" width='15%'>
							<!-- 关系 -->
							  <spring:message code="ess.empInfo.relationship" />
						</td>
						<td class="td_type" width='35%'>
							<ait:SelectSyCodeByCpnyID name="FAM_TYPE_CODE" parentNo="950"
								cnpyID="${defaultCpny}" selected="${item.FAM_TYPE_CODE}" />
							<input type="hidden" name="PERSON_ID" id="PERSON_ID" class="textInput"
								value="${LoginUser.adminID}" />
						</td>
						<td class="td_title" width='15%'>
							<!-- 性别 -->
									<spring:message code="hr.viewPersonalInfo.title.SEX" />
						</td>
						<td class="td_type" width='35%'>
							<ait:SelectSyCodeByCpnyID name="GENDER" parentNo="1324"
								cnpyID="${defaultCpny}" selected="${item.GENDER}" />
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
							<!--姓名-->
						</td>
						<td class="td_type">
							<input type="text" name="FAM_NAME" class="textInput" value="${item.FAM_NAME}"/>
						</td>
						<td class="td_title"></td>
						<td class="td_type"></td>
					</tr>
					<tr>
						
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.DOB" />
							<!--出生日期-->
						</td>
						<td class="td_type">
							<input type="text" name="FAM_BORNDATE" class="Wdate required" value="${item.FAM_BORNDATE}"
							readonly="true" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
						</td>
						
						<td class="td_title">
							<!-- 家庭电话 -->
							  <spring:message code="ess.personalinfo.title.familyTelphone" />
						</td>

						<td class="td_type">
							<input type="text" name="FAM_FAMILY_PHONE" class="textInput"
								maxlength="30" value='${item.FAM_FAMILY_PHONE}' />
						</td>
						<!--  添加按钮 <td rowspan="2">
						<img 

src="/resources/css/ligerUI/skins/icons/add.gif" border="0"
							align="absmiddle" 

style="cursor: hand" onclick="addrow()" />
					</td>-->
					</tr>
					<tr>
						<td class="td_title" width='15%'>
							<!-- 家人电子邮箱 -->
									<spring:message code="hrm.recruitManage.FAM_EMAIL" />
						</td>
						<td class="td_type" width='35%'>


							<input type="text" name="FAM_EMAIL" class="textInput"
								maxlength="30" value="${item.FAM_EMAIL}" />
						</td>
						<td class="td_title" width='15%'>
							<!-- 地址复制 -->
							   <spring:message code="hr.hrm.empinfo.dizhifuzhi.Z" />
						</td>
						<td class="td_type" width='35%'>
							<input type="button" onclick='javascript:copyAddressInfo()'
								value="<spring:message code="hr.hrm.empinfo.renzhirenyuanbiaoqian.Z" />" />
							</td>
					</tr>
					<tr>
						<td class="td_title" width='15%'>
							<!-- 国家 -->
									<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME" />
						</td>
						<td class="td_type" width='35%'>
							<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870"
								cnpyID="${defaultCpny}" selected="${item.NATIONALITY}" />

						</td>
						<td class="td_title" width='15%'>
							<!-- 紧急联络处 -->
									<spring:message code="hrm.recruitManage.EMERGENCY_CONTACT_YN" />
						</td>
						<td class="td_type" width='35%'>
							<c:if test="${item.EMERGENCY_LIAISON_OFFICE eq 'Y'}">
								<input type="checkbox" value="Y" name="EMERGENCY_LIAISON_OFFICE"
									checked="checked">
							</c:if>
							<c:if test="${item.EMERGENCY_LIAISON_OFFICE ne 'Y'}">
								<input type="checkbox" value="Y" name="EMERGENCY_LIAISON_OFFICE">
							</c:if>
						</td>

					</tr>

				</table>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table margin_b">

					<tr>
						<td class="td_title" width='15%'>
		<input type="hidden" id="My_Address" value="${addressInfo.ADDRESS_CONTENT}">
							<!-- 地址 -->
							   <spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
						</td>
						<td class="td_type" width='75%'>


							<input type="text" name="FAM_ADDRESS" id='By_Copy_Address'
								class="textInput" maxlength="300" style="width: 80%"
								value="${item.FAM_ADDRESS}" />
						</td>
					</tr>
				</table>
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table margin_b">

					<tr>
						<td class="td_title" width='15%'>
							<!-- 手机号码 -->
			                    <spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
						</td>
						<td class="td_type" width='35%'>


							<input type="text" name="FAM_PHONE" 
								class="textInput" maxlength="80" value="${item.FAM_PHONE}" />
						</td>


						<td class="td_title" width='15%'>
							<!-- 公司电话 -->
									<spring:message code="sys.basicMaint.title.companyTelPhoneNo" />
						</td>
						<td class="td_type" width='35%'>


							<input type="text" name="WORK_PHONE" class="textInput"
								maxlength="80" value="${item.WORK_PHONE}" />
						</td>
					</tr>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td class="td_title" width='15%'>
							<!-- 工作岗位 --> <spring:message code="hrm.empinfo.WORK_DUTY.Z" />
						</td>
						<td class="td_type" width='35%'>
							<input type="text" name="OCUPATION" 
								class="textInput" maxlength="80" value="${item.OCUPATION}" />
						</td>
						<td class="td_title" width='15%'>
							<!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
						</td>
						<td class="td_type" width='35%'>
							<input type="text" name="FAM_COMPANY_NAME" class="textInput"
								maxlength="80" value="${item.FAM_COMPANY_NAME}" />
						</td>
					</tr>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<tr style="display:none">
						<td class="td_title" width='15%'>
							<!-- 工作岗位 --> <spring:message code="hrm.empinfo.WORK_DUTY.Z" />
						</td>
						<td class="td_type" width='35%'>
							<input type="text" name="OCUPATION" 
								class="textInput" maxlength="80" value="" />
						</td>
						<td class="td_title" width='15%'>
							<!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
						</td>
						<td class="td_type" width='35%'>
							<input type="text" name="FAM_COMPANY_NAME" class="textInput"
								maxlength="80" value="" />
						</td>
					</tr>
					</c:if>
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
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
				<div class="button"><div class="buttonContent"><a type="button" class="close" onclick="javascript:delectApplyInfo()" ><span><spring:message code="public.title.delete"/> </span><!--删除 --> </a></div></div>
			</li>
				<li>
					<div class="button">
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
</div>

