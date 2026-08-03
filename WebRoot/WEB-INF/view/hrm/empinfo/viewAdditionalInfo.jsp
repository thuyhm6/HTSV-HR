<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewAdditional.title.EVENT_DATE"/><!--发生日期--></td>';
	   		htm+='<td class="td_type"><input type="text"  name="EVENT_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewAdditional.title.INFO_TYPE_NAME"/><!--信息类型--></td>';
	   		htm+='<td align="center" class="l-table-edit-td"><select name="INFO_TYPE_CODE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${infoTypeCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewAdditional.title.REMARK"/><!--详细内容--></td>';
	   		htm+='<td class="td_type"><input type="text" name="REMARK' + i + '" class="textInput" maxlength="60"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewAdditional.title.CREATE_NAME"/><!--登记者--></td>';
	   		htm+='<td class="td_type"><input type="text" name="CREATED_BY' + i + '" class="textInput" value="${PERSON_NAME }" readonly="true"/></td>';
	  
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
    
    function validateCallbackViewAdditionalInfo(form, callback) {
	
		var $form = $("#viewAdditionalInfo");
		
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
	<form id="viewAdditionalInfo" method="post" action="/hrm/empinfo/addAdditionalInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewAdditionalInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		   	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewAdditional.title.EVENT_DATE"/>
						<!--发生日期-->
					</td>
					<td class="td_type">
						<input type="text"  name="EVENT_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
					
					<td class="td_title">
						<spring:message code="hr.viewAdditional.title.INFO_TYPE_NAME"/>
						<!--信息类型-->
					</td>
					<td class="td_type"><ait:SelectSyCodeByCpnyID name="INFO_TYPE_CODE0" parentNo="14903" cnpyID="${defaultCpny}" limit="all"/></td>
					
					
					<td class="td_title">
						<spring:message code="hr.viewAdditional.title.REMARK"/>
						<!--详细内容-->
					</td>
					<td class="td_type">
						<input type="text" name="REMARK0" class="textInput" maxlength="60"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewAdditional.title.CREATE_NAME"/>
						<!--登记者-->
					</td>
					<td class="td_type">
						<input type="text" name="CREATED_BY0" class="textInput" value="${PERSON_NAME }" readonly="true"/>
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