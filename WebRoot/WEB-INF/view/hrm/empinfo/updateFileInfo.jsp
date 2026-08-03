<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateFileInfo(form, callback) {


	var $form = $("#updateFileInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("FEN");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	
	var fileListSizenum= document.getElementById("fileListSize").value;
	
	for (i=0;i<fileListSizenum;i++){
		var feno=document.getElementById("FEN"+i).value;
		if(document.getElementById("FILE_INTO_YN_"+feno).value == '14893' && document.getElementById("FILE_DATE_"+i).value == ''){
			//alert("转入日期必填");
			alertMsg.error('<spring:message code="hr.alert.message.viewContract.fileDateIsNotNull"/>');
			document.getElementById("FILE_DATE_"+i).focus();
			return false;
		}
		if(document.getElementById("FILE_INTO_YN_"+feno).value != '14893'){
			document.getElementById("FILE_DATE_"+i).value='';
		}
		
	}
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	

		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
//-->
</script>


<div class="pageContent">
	<form id="updateFileInfo" method="post" action="/hrm/empinfo/editFileInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateFileInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="fileListSize" name="fileListSize" value="${fn:length(fileList)}" />
	<table class="table" width="101.7%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_NO"/>
					<!--档案号-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_TYPE_NAME"/>
					<!--档案类型-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_RELATION_NAME"/>
					<!--档案关系-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/>
					<!--档案转入-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_DATE"/>
					<!--转入日期-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_CONTENT"/>
					<!--档案内容-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.FILE_AREA_NAME"/>
					<!--存档归属地-->
				</th>
				<th width="100">
					<spring:message code="hr.viewContract.title.COST_END_DATE"/>
					<!--存档费截至日-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fileList}" var="item" varStatus="i">
			
				<tr>
					<td><input type="checkbox" id="FEN${i.index}" name="FEN" value="${item.FILE_EMP_NO}" /><br></td>
					<td><input type="text" name="FILE_NO_${item.FILE_EMP_NO}" value="${item.FILE_NO }" class="textInput required" maxlength="30"/><br></td>
					<td><ait:SelectSyCodeByCpnyID name="FILE_TYPE_${item.FILE_EMP_NO}" selected="${item.FILE_TYPE}" parentNo="4577" cnpyID="${defaultCpny}"/></td>
					<td><ait:SelectSyCodeByCpnyID name="FILE_RELATION_${item.FILE_EMP_NO}" selected="${item.FILE_RELATION}" parentNo="1384" cnpyID="${defaultCpny}"/></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="FILE_INTO_YN_${item.FILE_EMP_NO}" selected="${item.FILE_INTO_YN}" parentNo="14892" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td>
						<input type="text" id="FILE_DATE_${i.index}" name="FILE_DATE_${item.FILE_EMP_NO}" class="date" value="${item.FILE_DATE }" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					<td><input type="text" name="FILE_CONTENT_${item.FILE_EMP_NO}" value="${item.FILE_CONTENT }" class="textInput" maxlength="100"/><br></td>
					<td><ait:SelectSyCodeByCpnyID name="FILE_AREA_${item.FILE_EMP_NO}" selected="${item.FILE_AREA}" parentNo="4578" cnpyID="${defaultCpny}"/></td>
					<td>
						<input type="text"  name="COST_END_DATE_${item.FILE_EMP_NO}" class="date" value="${item.COST_END_DATE }" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>

				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	
	
	
	
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