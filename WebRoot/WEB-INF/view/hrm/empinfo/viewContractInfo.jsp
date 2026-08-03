<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

    function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	    	
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
							
	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_NO"/><!--档案号--></td>';
	   		htm+='<td class="td_type"><input type="text" name="FILE_NO' + i + '" class="textInput required" maxlength="30"/></td>';

			htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_TYPE_NAME"/><!--档案类型--></td>';
	   		htm+='<td class="td_type"><select name="FILE_TYPE' + i + '">' ;
	   			<c:forEach items="${fileTypeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_RELATION_NAME"/><!--档案关系--></td>';
	   		htm+='<td class="td_type"><select name="FILE_RELATION' + i + '">' ;
	   			<c:forEach items="${fileRelationCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/><!--档案转入--></td>';
	   		htm+='<td  class="td_type"><select id="FILE_INTO_YN' + i + '" name="FILE_INTO_YN' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${fileInfoYnList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table' + i + '\')" style="cursor:hand"> </td>';
	   		
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_DATE"/><!--转入日期--></td>';
	   		htm+='<td align="left" class="td_type"><input type="text" id="FILE_DATE' + i + '" name="FILE_DATE' + i + '" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_CONTENT"/><!--档案内容--></td>';
	   		htm+='<td align="left" class="td_type"><input type="text" name="FILE_CONTENT' + i + '" class="textInput" maxlength="100"/></td>';
	   		
	  		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.FILE_AREA_NAME"/><!--存档归属地--></td>';
	   		htm+='<td class="td_type"><select name="FILE_AREA' + i + '">' ;
	   			<c:forEach items="${fileAreaList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewContract.title.COST_END_DATE"/><!--存档费截至日--></td>';
	   		htm+='<td class="td_type"><input type="text"  name="COST_END_DATE' + i + '" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   	
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }

   	function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewContractInfo(form, callback) {
	
		var $form = $("#viewContractInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());
		
		for (i=0;i<count;i++){
			if(document.getElementById("FILE_INTO_YN"+i).value == '14893' && document.getElementById("FILE_DATE"+i).value == ''){
				alert("转入日期必填");
				document.getElementById("FILE_DATE"+i).focus();
				return false;
			}
			if(document.getElementById("FILE_INTO_YN"+i).value != '14893'){
				document.getElementById("FILE_DATE"+i).value='';
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
	<form id="viewContractInfo" method="post" action="/hrm/empinfo/addFileInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewContractInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_NO"/>
						<!--档案号-->
					</td>
					<td class="td_type">
						<input type="text" name="FILE_NO0" class="textInput required" maxlength="30"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_TYPE_NAME"/>
						<!--档案类型-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="FILE_TYPE0" parentNo="4577" cnpyID="${defaultCpny}"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_RELATION_NAME"/>
						<!--档案关系-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="FILE_RELATION0" parentNo="1384" cnpyID="${defaultCpny}"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/>
						<!--档案转入-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="FILE_INTO_YN0" parentNo="14892" cnpyID="${defaultCpny}"  limit="all"/>
					</td>
					<td rowspan='2'><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_DATE"/>
						<!--转入日期-->
					</td>
					<td class="td_type">
						<input type="text" id="FILE_DATE0" name="FILE_DATE0" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_CONTENT"/>
						<!--档案内容-->
					</td>
					<td class="td_type">
						<input type="text" name="FILE_CONTENT0" class="textInput" maxlength="100"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.FILE_AREA_NAME"/>
						<!--存档归属地-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="FILE_AREA0" parentNo="4578" cnpyID="${defaultCpny}"/>
					</td>
				
					<td  class="td_title">
						<spring:message code="hr.viewContract.title.COST_END_DATE"/>
						<!--存档费截至日-->
					</td>
					<td class="td_type">
						<input type="text"  name="COST_END_DATE0" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					
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