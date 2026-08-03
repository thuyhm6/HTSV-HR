<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';


	   		htm+='<td  class="td_title"><spring:message code="hr.viewDisabled.title.DISABLED_TYPE"/><!--残疾类型--></td>';
	   		htm+='<td class="td_type"><select name="DISABILITY_TYPE_NAME' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${disabledTypeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE"/><!--残疾认定日期--></td>';
			htm+='<td class="td_type"><input type="text" id="ADDDATE' + i + '"  name="ADDDATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
			
			htm+='<td  class="td_title"><spring:message code="liang.hr.viewDisabled.title.DISABILITY_VALIDITY" /><!--  有效期--></td>';
	   		htm+='<td class="td_type"><select name="DISABILITY_VALIDITY' + i + '">' ;
	   			<c:forEach items="${disabilityValidityList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewDisabled.title.DISABLED_REMARK"/><!--备注--></td>';
	   		htm+='<td class="td_type"><input type="text" id="REMARK' + i + '" name="REMARK' + i + '"  /></td>';
	   		
	   		
	   		
	   		htm+='<td rowspan="1"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		
	   		
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewHealthInfo(form, callback) {
	
		var $form = $("#viewDisabledInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		
// 		var count = parseInt($("#count").val());
		
		
// 		for (i=0;i<count;i++){
// 		if(document.getElementById("ADDDATE"+i)!= null){
// 			if(document.getElementById("QUITDATE"+i).value!= "" ){
				
// 				var sd=document.getElementById("ADDDATE"+i).value;
// 				var ed=document.getElementById("QUITDATE"+i).value;
				
// 				var date1 = sd.replaceAll("-","");
// 				var date2 = ed.replaceAll("-","");
				
// 				if (date1 - date2 > 0) {
					
// 					alertMsg.error('<spring:message code="liang.hr.alert.message.viewDisabledInfo.AdddateAndQuitdate"/>');//认定日期不能晚于结束日期
// 					document.getElementById("ADDDATE"+i).focus();
// 					return false;
// 				}
// 			}
// 			}
// 		}
		
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
	<form id="viewDisabledInfo" method="post" action="/hrm/empinfo/addDisabledInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewHealthInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
				<td class="td_title">
					<spring:message code="hr.viewDisabled.title.DISABLED_TYPE" />
					<!--残疾类型-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="DISABILITY_TYPE_NAME0" parentNo="123264" cnpyID="${defaultCpny}" limit="all"/>
					<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
				</td>
				<td class="td_title">
					<spring:message code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" />
					<!--  残疾认定日期-->
				</td>
				<td class="td_type">
					<input type="text" id="ADDDATE0" name="ADDDATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				</td>
				<td class="td_title">
					<spring:message code="liang.hr.viewDisabled.title.DISABILITY_VALIDITY" />
					<!--  有效期-->
				</td>
				<td class="td_type">
					<select name="DISABILITY_VALIDITY0" id="DISABILITY_VALIDITY0"  >
							<c:forEach items="${disabilityValidityList}" var="recsource">
								<option value="${recsource.CODE_NO}" >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
				</td>
				<td class="td_title">
					<spring:message code="hr.viewDisabled.title.DISABLED_REMARK" />
					<!--  备注-->
				</td>
				<td class="td_type">
					<input type="text" name="REMARK0" id="REMARK0" />
				</td>
				<td rowspan="1"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
			</tr>
			</table> 
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>	
</div>