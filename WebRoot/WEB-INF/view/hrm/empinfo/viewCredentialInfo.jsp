<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCredential.title.CREDENTIAL_TYPE_NAME"/><!--证照类型--></td>';
	   		htm+='<td class="td_type"><select name="CREDENTIAL_TYPE' + i + '">' ;
	   			<c:forEach items="${credentialList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCredential.title.CREDENTIAL_NO"/><!--证照号码--></td>';
	   		htm+='<td class="td_type"><input type="text" name="CREDENTIAL_NO' + i + '" class="textInput alphanumeric required" maxlength="50"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE"/><!--签发地--></td>';
	   		htm+='<td class="td_type"><input type="text" name="CREDENTIAL_SOURCE' + i + '" class="textInput" maxlength="60"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/><!--取证日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="CREDENTIAL_BEGIN_DATE' + i + '" name="CREDENTIAL_BEGIN_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCredential.title.CREDENTIAL_END_DATE"/><!--到期日--></td>';
	   		htm+='<td class="td_type"><input type="text" id="CREDENTIAL_END_DATE' + i + '" name="CREDENTIAL_END_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewPromote.title.REMARK"/><!--备注--></td>';
	   		htm+='<td class="td_type"><input type="text" name="REMARK' + i + '" class="textInput" maxlength="30"/></td>';
	   		
	   		htm+='<td><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewCredentialInfo(form, callback) {
	
		var $form = $("#viewCredentialInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());
		
		for (i=0;i<count;i++){
			
			if(document.getElementById("CREDENTIAL_BEGIN_DATE"+i) != null && document.getElementById("CREDENTIAL_END_DATE"+i) !=null){
				
				var sd=document.getElementById("CREDENTIAL_BEGIN_DATE"+i).value;
				var ed=document.getElementById("CREDENTIAL_END_DATE"+i).value;
				
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				
				if (date1 - date2 > 0) {
					//alert("取证日期不能晚于有效期");
					alertMsg.error('<spring:message code="hr.alert.message.viewCompetence.addNull"/>');
					document.getElementById("CREDENTIAL_BEGIN_DATE"+i).focus();
					return false;
				}
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
	<form id="viewCredentialInfo" method="post" action="/hrm/empinfo/addCredentialInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewCredentialInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td  class="td_title">
						<spring:message code="hr.viewCredential.title.CREDENTIAL_TYPE_NAME"/>
						<!--证照类型-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="CREDENTIAL_TYPE0" parentNo="4297" cnpyID="${defaultCpny}"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewCredential.title.CREDENTIAL_NO"/>
						<!--证照号码-->
					</td>
					<td class="td_type">
						<input type="text" name="CREDENTIAL_NO0" class="textInput alphanumeric required" maxlength="50"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE"/>
						<!--签发地-->
					</td>
					<td class="td_type">
						<input type="text" name="CREDENTIAL_SOURCE0" class="textInput" maxlength="60"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/>
						<!--取证日期-->
					</td>
					<td class="td_type">
						<input type="text" id="CREDENTIAL_BEGIN_DATE0" name="CREDENTIAL_BEGIN_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewCredential.title.CREDENTIAL_END_DATE"/>
						<!--到期日-->
					</td>
					<td class="td_type">
						<input type="text" id="CREDENTIAL_END_DATE0" name="CREDENTIAL_END_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>		
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewPromote.title.REMARK"/>
						<!--备注-->
					</td>
					<td class="td_type">
						<input type="text" name="REMARK0" class="textInput" maxlength="30"/>
					</td>
				
					<td><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
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