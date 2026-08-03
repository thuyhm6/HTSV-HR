<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateFamilyInfo(form, callback) {


	var $form = $("#updateFamilyInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("FN");
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
  var tld="FN"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>


<div class="pageContent">
	<form id="updateFamilyInfo" method="post" action="/hrm/empinfo/updateHomeRelationInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateFamilyInfo(this, dialogAjaxDone);">
	<input type="hidden" id="isEssSystem" name="isEssSystem" value="${isEssSystem }"/>
	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	<table class="table" width="101.7%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" size="13"  value="${PERSON_ID }"/></th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>
					<!--关系-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<%--<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					<!--身份证号码-->
				</th>
				--%><th width="100">
					<spring:message code="hr.viewPersonalInfo.title.DOB"/>
					<!--出生日期-->
				</th>
				<%--<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_ADDRESS"/>
					<!--地址-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PHONE"/>
					<!--联系电话-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/>
					<!--工作单位/职(岗)位-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/>
					<!--一起居住与否-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/>
					<!--是否紧急联系人-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/>
					<!--亲属员工号-->
				</th>
			--%>
				<%--<th width="100">
					<spring:message
						code="hr.viewPersonalInfo.title.FAMILY_YINYANGRILI" />
					<!--阴/阳历区分-->
				</th>
											
				--%><th width="100">
					<spring:message
							code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME" />
					<!--单位名称-->
				</th>
				</tr>
		</thead>
		<tbody>
			<c:forEach items="${homeRelationList}" var="item" varStatus="i">
			
				<tr>
					<td><input type="checkbox" id="FN${i.count}" name="FN" value="${item.FAMILY_NO}" /></td>
					
					<td><ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="FAM_TYPE_CODE_${item.FAMILY_NO}" parentNo="950" cnpyID="${defaultCpny}" selected="${item.FAM_TYPE_CODE }" /></td>
					
					<td><input onclick="setCheckboxChecked(${i.count})" type="text" name="FAM_NAME_${item.FAMILY_NO}" class="textInput" size="13" maxlength="13" value="${item.FAM_NAME}"/></td>
					
					<%--<td><input type="text" name="FAM_IDCARD_${item.FAMILY_NO}"  class="textInput" maxlength="30" value="${item.FAM_IDCARD}"/></td>
					--%>
					<td>
						<input type="text"  name="FAM_BORNDATE_${item.FAMILY_NO}" class="date required" value="${item.FAM_BORNDATE}" size="12" readonly="true" format="yyyy-MM-dd" yearstart="-100" yearend="5" onClick="setCheckboxChecked(${i.count})"/>
					</td>
					
					<%--<td><input type="text" name="FAM_ADDRESS_${item.FAMILY_NO}"  class="textInput" maxlength="100" value="${item.FAM_ADDRESS }"/></td>
					
					<td><input type="text" name="FAM_PHONE_${item.FAMILY_NO}"  class="textInput" maxlength="20" value="${item.FAM_PHONE }"/></td>
					
					<td><input type="text" name="FAM_COMPANY_NAME_${item.FAMILY_NO}"  class="textInput" maxlength="30" value="${item.FAM_COMPANY_NAME }"/></td>
					
					<td>
						<ait:SelectSyCodeByCpnyID name="LIVE_YN_${item.FAMILY_NO}" selected="${item.LIVE_YN}" parentNo="14892" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					
					<td>
						<ait:SelectSyCodeByCpnyID name="EMERGENCY_CONTACT_YN_${item.FAMILY_NO}" selected="${item.EMERGENCY_CONTACT_YN}" parentNo="14892" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					
					<td><input type="text" name="FAM_PERSON_ID_${item.FAMILY_NO}" size="13" class="textInput alphanumeric" maxlength="30" value="${item.FAM_PERSON_ID }"/></td>
					
					
					<td><ait:SelectSyCodeByCpnyID limit="all" onClickName="setCheckboxChecked(${i.count})" name="FAM_YINYANGLI${item.FAMILY_NO}" parentNo="123276" cnpyID="${defaultCpny}" selected="${item.FAM_YINYANGLI }"  /></td>
					--%>
                    <td><input type="text" name="FAM_COMPANY_NAME_${item.FAMILY_NO}"  class="textInput" maxlength="30" value="${item.FAM_COMPANY_NAME }" onclick="setCheckboxChecked(${i.count})"/></td>
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