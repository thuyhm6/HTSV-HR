<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
		
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUZHIZE"/><!--工会内部职责--></td>';
			//htm+='<td class="td_type"><ait:SelectSyCodeByCpnyID  name="PHYSICAL_TYPE_CODE0" parentNo="123251" cnpyID="${defaultCpny}" limit="all"/></td>';
	   		
	   		htm+='<td  class="td_type"><select name="PHYSICAL_TYPE_CODE' + i + '">' ;
	   			<c:forEach items="${codeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE"/><!--加会日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="TRADEUNION_ADDDATE'+i+'" name="TRADEUNION_ADDDATE'+i+ '"class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE"/><!--退会日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="TRADEUNION_QUITDATE'+i+'"name="TRADEUNION_QUITDATE'+i+ '"class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';

	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG"/><!--参加 工会与否--></td>';
	   		htm+='<td class="td_type" width="80px">${join_flag}</td>';
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr><tr>';
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG"/><!--会费支付状态--></td>';
	   		htm+='<td  class="td_type"><select name="PAY_FLAG' + i + '">' ;
   			<c:forEach items="${paymentStatusList}" var="item" >
   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
			</c:forEach>
   			htm+='</select></td>';
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE"/><!--支付方式--></td>';
	   		htm+='<td  class="td_type"><select name="PAY_TYPE' + i + '">' ;
   			<c:forEach items="${paymentTypeList}" var="item" >
   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
			</c:forEach>
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK"/><!--描述--></td>';
	   		htm+='<td class="td_type" colspan="3"><input type="text" maxlength="100" name="TRADEUNION_REMARK'+i+ '" id="TRADEUNION_REMARK"/></td>';
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
	
		var $form = $("#viewTradeunionInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		
		var count = parseInt($("#count").val());
		
		var activity ="0";
		for (i=0;i<count;i++){
			if(document.getElementById("TRADEUNION_ADDDATE"+i)!=null){
				if(document.getElementById("TRADEUNION_QUITDATE"+i).value != null && document.getElementById("TRADEUNION_QUITDATE"+i).value !="" )
				{
				var sd=document.getElementById("TRADEUNION_ADDDATE"+i).value;
				var ed=document.getElementById("TRADEUNION_QUITDATE"+i).value;
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				if (date1 - date2 > 0) {
					alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//开始日期不能晚于结束日期
					document.getElementById("TRADEUNION_QUITDATE"+i).focus();
					return false;
				}
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
	<form id="viewTradeunionInfo" method="post" action="/hrm/empinfo/addTradeunionInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewHealthInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUZHIZE"/>
					<!--工会内部职责-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID  name="PHYSICAL_TYPE_CODE0" parentNo="123251" cnpyID="${defaultCpny}" />
					<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE"/>
					<!--加会日期-->
				</td>
				<td class="td_type">
					<input type="text" id="TRADEUNION_ADDDATE0" name="TRADEUNION_ADDDATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE"/>
					<!--退会日期-->
				</td>
				
				<td class="td_type">
					<input type="text" id="TRADEUNION_QUITDATE0" name="TRADEUNION_QUITDATE0" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				</td>
				
				
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG"/>
					<!--参加 工会与否-->
				</td>
				
				<td class="td_type" width="80px">
					${join_flag}
				</td>
				<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG"/>
					<!--会费支付状态-->
				</td>
				
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID  name="PAY_FLAG0" parentNo="123224" cnpyID="${defaultCpny}" />
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE"/>
					<!--支付方式-->
				</td>
				
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID  name="PAY_TYPE0" parentNo="211654" cnpyID="${defaultCpny}" />
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK"/>
					<!--描述-->
				</td>
				<td class="td_type" colspan="3">
					<input type="text" maxlength="100" name="TRADEUNION_REMARK0" id="TRADEUNION_REMARK"/>
				</td>
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