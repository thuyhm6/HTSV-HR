<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';


	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.PHYSICAL_DATE"/><!--检查日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="PHYSICAL_DATE' + i + '"  name="PHYSICAL_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.PHYSICAL_TYPE_NAME"/><!--检查类型--></td>';
			htm+='<td class="td_type"><select name="PHYSICAL_TYPE_CODE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${physicalTypeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/><!--区分--></td>';
	   		htm+='<td class="td_type"><select name="INDUSTRY_DISTINGUISH_CODE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${industryDistinguishList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.EFFECTIVE_DATE"/><!--有效期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="EFFECTIVE_DATE' + i + '" name="EFFECTIVE_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.CHECK_YN_NAME"/><!--检查与否--></td>';
	   		htm+='<td class="td_type"><select name="CHECK_YN' + i + '"><option value="">请选择</option><option value="14893">是</option>	<option value="14894">否</option></select></td>';
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		
	   		
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title">	<spring:message code="hr.viewHealth.title.GENRAL_HEALTH_NAME"/><!--健康情况--></td>';
	   		htm+='<td class="td_type"><select name="GENERAL_HEALTH' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${generalHealthList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/><!--血型--></td>';
	   		htm+='<td class="td_type"><select name="BLOOD_TYPE_CODE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${bloodTypeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.HEALTH_CERTIFICATE_YN_NAME"/><!--是否提交健康证--></td>';
	   		htm+='<td class="td_type"><select name="HEALTH_CERTIFICATE_YN' + i + '"><option value="">请选择</option><option value="14893">是</option>	<option value="14894">否</option></select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewHealth.title.SPECIAL_MATTERS"/><!--特殊事项--></td>';
	   		htm+='<td class="td_type" colspan="3"><input type="text" name="REMARK' + i + '" class="textInput" maxlength="30"/></td>';
	   		
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
	
		var $form = $("#viewHealthInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		
		var count = parseInt($("#count").val());
		var activity ="0";
		
		for (i=0;i<count;i++){
			
			if(document.getElementById("PHYSICAL_DATE"+i) != null ){
				
				var sd=document.getElementById("PHYSICAL_DATE"+i).value;
				var ed=document.getElementById("EFFECTIVE_DATE"+i).value;
				
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				
				if (date1 - date2 > 0) {
					activity="1";
					alertMsg.error('<spring:message code="hr.alert.message.viewHealth.checkPhysicalDateAndEffectiveDate"/>');//检查日期不能晚于有效期
					document.getElementById("PHYSICAL_DATE"+i).focus();
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
	<form id="viewHealthInfo" method="post" action="/hrm/empinfo/addHealthInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewHealthInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
				<td class="td_title">
					<spring:message code="hr.viewHealth.title.PHYSICAL_DATE"/>
					<!--检查日期-->
				</td>
				<td class="td_type">
					<input type="text" id="PHYSICAL_DATE0" name="PHYSICAL_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
				</td>
				<td class="td_title">
					<spring:message code="hr.viewHealth.title.PHYSICAL_TYPE_NAME"/>
					<!--检查类型-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="PHYSICAL_TYPE_CODE0" parentNo="4570" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
					<!--区分-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="INDUSTRY_DISTINGUISH_CODE0" parentNo="14900" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewHealth.title.EFFECTIVE_DATE"/>
					<!--有效期-->
				</td>
				
				<td class="td_type">
					<input type="text" id="EFFECTIVE_DATE0" name="EFFECTIVE_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				</td>
				
				<td class="td_title">
					<spring:message code="hr.viewHealth.title.CHECK_YN_NAME"/>
					<!--检查与否-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="CHECK_YN0" parentNo="14892" cnpyID="${defaultCpny}"  limit="all"/>
				</td>
				<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
			</tr>
			<tr>

				<td class="td_title">
						<spring:message code="hr.viewHealth.title.GENRAL_HEALTH_NAME"/>
					<!--健康情况-->
				</td>
				<td class="td_type">
					 <ait:SelectSyCodeByCpnyID name="GENERAL_HEALTH0" parentNo="4572" cnpyID="${defaultCpny}" limit="all"/>
				</td>
			

				<td class="td_title">
					<spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/>
					<!--血型-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="BLOOD_TYPE_CODE0" parentNo="4573" cnpyID="${defaultCpny}" limit="all"/>
				</td>
			

				<td class="td_title">
					<spring:message code="hr.viewHealth.title.HEALTH_CERTIFICATE_YN_NAME"/>
					<!--是否提交健康证-->
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="HEALTH_CERTIFICATE_YN0" parentNo="14892" cnpyID="${defaultCpny}"  limit="all"/>
				</td>

				<td class="td_title">
					<spring:message code="hr.viewHealth.title.SPECIAL_MATTERS"/>
					<!--特殊事项-->
				</td>
				<td class="td_type" colspan="3">
					<input type="text" name="REMARK0" class="textInput" maxlength="30"/>
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