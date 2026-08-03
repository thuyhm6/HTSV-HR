<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">
<!--
function validateCallbackUpdateHealthInfo(form, callback) {


	var $form = $("#updateDisabledInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("HNO");
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
	
//	验证开始日期大于结束日期
// 	var disabledListSizenum= document.getElementById("disabledListSize").value;
	
// 	for (i=0;i<disabledListSizenum;i++){
// 			if(document.getElementById("HNO"+i).checked && document.getElementById("QUITDATE_"+i).value!=""){
// 				var sd=document.getElementById("ADDDATE_"+i).value;
// 				var ed=document.getElementById("QUITDATE_"+i).value;
				
// 				var date1 = sd.replaceAll("-","");
// 				var date2 = ed.replaceAll("-","");
				
// 				if (date1 - date2 > 0) {
// 					alertMsg.error('<spring:message code="liang.hr.alert.message.viewDisabledInfo.AdddateAndQuitdate"/>');//认定日期不能晚于结束日期
// 					document.getElementById("ADDDATE_"+i).focus();
// 					return false;
// 				}
// 			}
// 		}
	
	
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
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  	var tld="HNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>

<div class="pageContent">
	<form id="updateDisabledInfo" method="post" action="/hrm/empinfo/editDisabledInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateHealthInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="disabledListSize" name="disabledListSize" value="${fn:length(disabledList)}" />
	<table class="table" width="102.2%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="80">
					<spring:message code="hr.viewDisabled.title.DISABLED_TYPE" />
					<!--残疾类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" />
					<!--  残疾认定日期-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewDisabled.title.DISABILITY_VALIDITY" />
					<!--  有效期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewDisabled.title.DISABLED_REMARK" />
					<!--  备注-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${disabledList}" var="item" varStatus="i">
				<tr target="healthNo" rel="${item.T_ID}">
					
					<td><input type="checkbox" id="HNO${i.index}" name="HNO" value="${item.T_ID}" /></td>
					
					<td>
						<ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.index})" name="DISABILITY_TYPE_NAME_${item.T_ID}" parentNo="123264" selected="${item.DISABILITY_TYPE}" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td><input   type="text" id="ADDDATE_${i.index}" name="ADDDATE_${item.T_ID}" class="date required" value="${item.ADDDATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.index})"/></td>
					
					<td>
						<select name="DISABILITY_VALIDITY_${item.T_ID}" id="DISABILITY_VALIDITY_${i.index}" onchange="setCheckboxChecked(${i.index});" >
							<c:forEach items="${disabilityValidityList}" var="recsource">
								<option value="${recsource.CODE_NO}"  <c:if test="${recsource.CODE_NO==item.DISABILITY_VALIDITY_CODE}">selected</c:if>>
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					
					<td>
						<input onclick="setCheckboxChecked(${i.index})" type="text" id="REMARK_${i.index}" name="REMARK_${item.T_ID}" value="${item.REMARK}" />
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