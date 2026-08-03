<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
		
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/><!--关系--></td>';
	   		htm+='<td  class="td_type"><select name="FAM_TYPE_CODE' + i + '">' ;
	   			<c:forEach items="${codeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="FAM_NAME' + i + '" class="textInput" maxlength="13"/></td>';
	   		
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/><!--身份证号码--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_IDCARD' + i + '" class="textInput alphanumeric" maxlength="30"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.DOB"/><!--出生日期--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text"  name="FAM_BORNDATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_ADDRESS"/><!--地址--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_ADDRESS' + i + '" class="textInput" maxlength="100"/></td>';--%>
<%--	   		--%>
<%--	   		--%>
<%--	   		htm+='</tr>';--%>
<%--	   		--%>
<%--	   		htm+='<tr>';--%>
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_PHONE"/><!--联系电话--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="FAM_PHONE' + i + '" class="textInput" maxlength="20"/></td>';
	   		
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/><!--工作单位/职(岗)位--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_COMPANY_NAME' + i + '" class="textInput" maxlength="30"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/><!--一起居住与否--></td>';--%>
<%--	   		htm+='<td  class="td_type"><select name="LIVE_YN' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/></option><option value="Y"><spring:message code="hr.viewRelation.title.YES"/></option>	<option value="N"><spring:message code="hr.viewRelation.title.NO"/></option></select></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/><!--是否紧急联系人--></td>';--%>
<%--	   		htm+='<td  class="td_type"><select name="EMERGENCY_CONTACT_YN' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择--></option><option value="Y"><spring:message code="hr.viewRelation.title.YES"/><!--是--></option><option value="N"><spring:message code="hr.viewRelation.title.NO"/><!--否--></option></select></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/><!--亲属员工号--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_PERSON_ID' + i + '" class="textInput" maxlength="30"/></td>';--%>
<%--	   		--%>
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
	   
    }

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
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
 
	
    
</script>

<div class="pageContent">
	<!-- 地址信息 -->
	<form id="addHomeRelationInfo" method="post" action="/ess/empinfo/addHomeRelationInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
		<input type="hidden" id="isEssSystem" name="isEssSystem" value="${isEssSystem}"/>
		<div class="pageFormContent" layoutH="56">
		<c:forEach items="${AddressList}" var="item">
			<input type="hidden" name="APPLY_TYPE" value='${2}' />
		<input type="hidden" name="UPDATE_FAMILY_NO" value='${item.FAMILY_NO}'/>
		 		<input type="hidden" name="PERSON_ID" value='${item.PERSON_ID}'/>
		 
		   	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!--地址类型--><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
					</td>
					<td class="td_type" width="35%">
						${item.ADDRESS_TYPE_NAME}
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID}"/>
						<input type="hidden" name="ADDRESS_NO" class="textInput" value="${item.ADDRESS_NO}"/>
					
					</td>
					<td class="td_title" width="15%">
				<!--有效开始日期--><spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" />
					</td>
					<td class="td_type" width="35%">
						${item.EFFECTIVE_START_DATE}
					</td>
				</tr>
			</table>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%">
						<!-- 地址 --><spring:message code="org.title.ADDRESS" />
					</td>
					<td class="td_type" width="85%">
						${item.ADDRESS_CONTENT}
					</td>
					</tr>
					<tr>
					<td class="td_title" >
							<!-- 国家 --><spring:message code="org.title.COUNTRY" />
					</td>
					<td class="td_type" >
						${item.NATIONALITY_NAME}
					</td>
			
				</tr>
			</table>
			</c:forEach>
			
	
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
	
	</form>	
</div>