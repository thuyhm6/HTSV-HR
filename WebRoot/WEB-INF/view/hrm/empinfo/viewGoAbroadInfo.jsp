<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME"/><!--国家--></td>';
	   		htm+='<td class="td_type"><select name="COUNTRY' + i + '">' ;
	   			<c:forEach items="${countryList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/><!--开始时间--></td>';
	   		htm+='<td class="td_type"><input type="text" id="START_DATE' + i + '" name="START_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/><!--结束日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="END_DATE' + i + '" name="END_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
			
	   		htm+='<td  class="td_title"><spring:message code="hr.viewGoAbroad.title.COST"/><!--费用(元)--></td>';
	   		htm+='<td class="td_type"><input type="text" name="COST' + i + '" class="textInput number" maxlength="8"></td>';
	   		
			htm+='<td  class="td_title"><spring:message code="hr.viewGoAbroad.title.PURPOSE"/><!--目的--></td>';
	   		htm+='<td class="td_type"><input type="text" name="PURPOSE' + i + '" class="textInput" maxlength="100"/></td>';
	  
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
    
    function validateCallbackViewGoAbroadInfo(form, callback) {
	
		var $form = $("#viewGoAbroadInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());
		
		for (i=0;i<count;i++){
			
			if(document.getElementById("START_DATE"+i) != null ){
				
				var sd=document.getElementById("START_DATE"+i).value;
				var ed=document.getElementById("END_DATE"+i).value;
				
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				
				if (date1 - date2 > 0) {
					activity="1";
					//alert("开始时间不能晚于结束时间");
					alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');
					document.getElementById("START_DATE"+i).focus();
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
	<form id="viewGoAbroadInfo" method="post" action="/hrm/empinfo/addGoAbroadInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewGoAbroadInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td  class="td_title">
						<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME"/>
						<!--国家-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="COUNTRY0" parentNo="870" cnpyID="${defaultCpny}"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
						<!--开始时间-->
					</td>
					<td class="td_type">
						<input type="text" id="START_DATE0" name="START_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
						<!--结束日期-->
					</td>
					<td class="td_type">
						<input type="text" id="END_DATE0" name="END_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					<td  class="td_title">
						<spring:message code="hr.viewGoAbroad.title.COST"/>
						<!--费用(元)-->
					</td>
					<td class="td_type">
						<input type="text" name="COST0" class="textInput number" maxlength="8"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewGoAbroad.title.PURPOSE"/>
						<!--目的-->
					</td>
					<td class="td_type">
						<input type="text" name="PURPOSE0" class="textInput" maxlength="100"/>
					</td>
					
					<td><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>

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