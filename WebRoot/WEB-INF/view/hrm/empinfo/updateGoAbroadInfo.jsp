<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateGoAbroadInfo(form, callback) {


	var $form = $("#updateGoAbroadInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("NO");
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
	
	
	var goAbroadListnum= document.getElementById("goAbroadListSize").value;
	
	for (i=0;i<goAbroadListnum;i++){
		if(document.getElementById("NO"+i).checked){
			var sd=document.getElementById("START_DATE_"+i).value;
			var ed=document.getElementById("END_DATE_"+i).value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				//alert("开始时间不能晚于结束时间");
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');
				document.getElementById("START_DATE_"+i).focus();
				return false;
			}
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
	<form id="updateGoAbroadInfo" method="post" action="/hrm/empinfo/editGoAbroadInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateGoAbroadInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="goAbroadListSize" name="goAbroadListSize" value="${fn:length(goAbroadList)}" />
	<table class="table" width="102%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME"/>
					<!--国家-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
					<!--结束日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.COST"/>
					<!--费用(元)-->
				</th>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.PURPOSE"/>
					<!--目的-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${goAbroadList}" var="item" varStatus="i">
			
				<tr>
					<td><input type="checkbox" id="NO${i.index}" name="NO" value="${item.NO}" /></td>
					
					<td><ait:SelectSyCodeByCpnyID name="COUNTRY_${item.NO}" parentNo="870" cnpyID="${defaultCpny}" selected="${item.COUNTRY }" /></td>
					
					<td>
						<input id="START_DATE_${i.index}"  name="START_DATE_${item.NO}" type="text"  class="date required" value="${item.START_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					<td>
						<input id="END_DATE_${i.index}"  name="END_DATE_${item.NO}" type="text"  class="date required" value="${item.END_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
						
					<td><input type="text" name="COST_${item.NO}"  class="textInput number" maxlength="10" value="${item.COST}" /></td>
					
					<td><input type="text" name="PURPOSE_${item.NO}"  class="textInput" maxlength="100" value="${item.PURPOSE}" /></td>

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