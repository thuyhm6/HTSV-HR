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
	
		var $form = $("#viewFamilyInfo");
		
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
	<!-- 紧急联系地址   hr_emergency_address  没有更换名字 -->
	<form id="viewFamilyInfo" method="post" action="/hrm/empinfo/addFamilyInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
		<input type="hidden" id="isEssSystem" name="isEssSystem" value="${isEssSystem }"/>
		<div class="pageFormContent" layoutH="56">
		   	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>
						<!--关系-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="FAM_TYPE_CODE0" parentNo="1693" cnpyID="${defaultCpny}" />
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
						<!--姓名-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_NAME0" class="textInput" maxlength="13"/>
					</td>
					<%--<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
						<!--身份证号码-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_IDCARD0" class="textInput alphanumeric" maxlength="30"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DOB"/>
						<!--出生日期-->
					</td>
					<td class="td_type">
						<input type="text"  name="FAM_BORNDATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.FAM_ADDRESS"/>
						<!--地址-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_ADDRESS0" class="textInput" maxlength="100"/>
					</td>
				
				</tr>
				<tr>	
					--%>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.FAM_PHONE"/>
						<!--联系电话-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_PHONE0" class="textInput" maxlength="20"/>
					</td>
					<%--<td class="td_title">
						<spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/>
						<!--工作单位/职(岗)位-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_COMPANY_NAME0" class="textInput" maxlength="30"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/>
						<!--一起居住与否-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="LIVE_YN0" parentNo="14892" cnpyID="${defaultCpny}"  limit="all"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/>
						<!--是否紧急联系人-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="EMERGENCY_CONTACT_YN0" parentNo="14892" cnpyID="${defaultCpny}"  limit="all"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/>
						<!--亲属员工号-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_PERSON_ID0" class="textInput alphanumeric" maxlength="30"/>
					</td>
					
					
				--%>
					<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
			</table>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
			</li>
		</ul>
	</div>
	</form>	
</div>