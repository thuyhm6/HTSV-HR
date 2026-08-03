<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewBadArchives.title.HAPPEN_DATE" /><!--发生日期--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="HAPPEN_DATE' + i + '" name="HAPPEN_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewBadArchives.title.ARCHIVES_TYPE" /><!--类型--></td>';
	   		htm+='<td  class="td_type"><select id="ARCHIVES_TYPE' + i + '" name="ARCHIVES_TYPE' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>' ;
	   			<c:forEach items="${codeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
		
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewBadArchives.title.REMARK" /><!--备注--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="REMARK' + i + '" name="REMARK' + i + '" class="textInput"  /></td>';
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';

	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title"><spring:message code="hr.viewBadArchives.title.DETAIL_DESCRIPT" /><!--详细描述--></td>';
	   		htm+='<td  class="td_type" colspan="5"><input type="text" id="DETAIL_DESCRIPT' + i + '" name="DETAIL_DESCRIPT' + i + '" class="textInput" maxlength="160" size="120"/></td>';
	   		htm+='</tr>';
	   		htm+='</table>';
	   		
	   	$("#createTable").append(htm) ;
	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewTrainingInfo(form, callback) {
	
		var $form = $("#viewBadArchivesInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());
		
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
	
	function agreementControl(){
	
	}
</script>

<div class="pageContent">
	<form id="viewBadArchivesInfo" method="post" action="/hrm/empinfo/addBadArchivesInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewTrainingInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewBadArchives.title.HAPPEN_DATE" />
						<!--发生日期-->
					</td>
					<td class="td_type">
						<input type="text" id="HAPPEN_DATE0" name="HAPPEN_DATE0" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewBadArchives.title.ARCHIVES_TYPE" />
						<!--类型-->
					</td>
					<td class="td_type">
						<select name="ARCHIVES_TYPE0" id="ARCHIVES_TYPE0"  >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${codeList}" var="recsource">
								<option value="${recsource.CODE_NO}" >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<!--
					<td class="td_title">
						<spring:message code="hr.viewBadArchives.title.FILE" />
						
					</td>
					<td class="td_type">
						<input id="file1" name="file" type="file" />
					
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
						<button type="submit" onclick="shangchuan();" id="shagnchuanfujian">上传附件</button>
					</td>
					--><td class="td_title">
						<spring:message code="hr.viewBadArchives.title.REMARK" />
						<!--备注-->
					</td>
					<td class="td_type">
						<input type="text" name="REMARK0" class="textInput"  />
					</td>
					
					<td rowspan='2'><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
					
					<td class="td_title">
						<spring:message code="hr.viewBadArchives.title.DETAIL_DESCRIPT" />
						<!--详细描述-->
					</td>
					<td class="td_type" colspan="5">
						<input type="text" name="DETAIL_DESCRIPT0" class="textInput" maxlength="160" size="120"/>
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