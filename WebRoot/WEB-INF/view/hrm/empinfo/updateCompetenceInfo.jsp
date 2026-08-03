<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">




<!--
function validateCallbackUpdateCompetenceInfo(form, callback) {


	var $form = $("#updateCompetenceInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	
	var checked=false;
	var ids= document.getElementsByName("QNO");
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
	
	var qualificationListSizenum= document.getElementById("qualificationListSize").value;
	
	for (i=0;i<qualificationListSizenum;i++){
		 
		if(document.getElementById("DATE_OBTAINED_"+i)!=null){
		if(document.getElementById("VALIDITY_DATE_"+i).value != null&&document.getElementById("VALIDITY_DATE_"+i).value != ""){
			
			var sd=document.getElementById("DATE_OBTAINED_"+i).value;//取证日期
			var ed=document.getElementById("VALIDITY_DATE_"+i).value;//有效期
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				//alert("取证日期不能晚于有效期");
				alertMsg.error('<spring:message code="heran.hr.viewLanguage.DATENULL"/>');
				document.getElementById("DATE_OBTAINED_"+i).focus();
				return false;
			}
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
function setCheckboxChecked(elemName,index){
		
  var ckElems = document.getElementsByName(elemName);
 
  if (ckElems != null && ckElems.length != null &&
    index >=0 && index < ckElems.length){
		ckElems(index).checked=true;
  }
}
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="QNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>


<div class="pageContent">
	<form id="updateCompetenceInfo" method="post" action="/hrm/empinfo/editCompetenceInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateCompetenceInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="qualificationListSize" name="qualificationListSize" value="${fn:length(qualificationList)}" />
	<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
	<table class="table" width="101.7%" layoutH="150">
		<thead>
			<tr>
					<th width="10">
						
					</th>
										<th class="td_title">
											<spring:message code="hr.viewCompetence.title.QUAL_NAME" />
													<!--资格证名称-->
												</th>
												<!--<th width="100">
													<spring:message code="hr.viewCompetence.title.QUAL_CARD_NO" />
													证件号
												</th>
												--><th class="td_title">
													<spring:message
														code="hr.viewCompetence.title.QUAL_LEVEL_NAME" />
													<!--证件级别-->
												</th>
                                                <th class="td_title">
                                                    <spring:message
                                                        code="hr.viewCompetence.title.QUAL_GRADE_NAME" />
                                                    <!--职称-->
                                                </th>
												<th class="td_title">
													<spring:message
														code="hr.viewCompetence.title.QUAL_INSTITUTE" />
													<!--发证处-->
												</th>
												<!--<th width="100">
													<spring:message
														code="hr.viewCompetence.title.ACQUISITION_NAME" />
													取得方式
												</th>
												--><th class="td_title">
													<spring:message
														code="hr.viewCompetence.title.DATE_OBTAINED" />
													<!--取证日期-->
												</th>
                                                
												<th class="td_title" style="display:none">
													<spring:message
														code="hr.viewCompetence.title.VALIDITY_DATE" />
													<!--有效期-->
												</th>
                                                <th class="td_title">
                                                        <spring:message
                                                        code="hr.viewPersonalInfo.title.jintiebiaozhun" />
                                                        <!-- 津贴标准(金额) -->
                                                </th>
											</tr>
		</thead>
		<tbody>
			<c:forEach items="${qualificationList}" var="item" varStatus="i">
				<tr>
					
					<td><input type="checkbox" id="QNO${i.count}" name="QNO" value="${item.QUAL_NO}" /></td>
					
					<td class="td_type"><input name="QUAL_NAME_${item.QUAL_NO}" class="required" type="text" maxlength="30" size="15"	value="${item.QUAL_NAME}" onclick="setCheckboxChecked('${i.count}');" /></td>
					
<!--					<td><input name="QUAL_CARD_NO_${item.QUAL_NO}" class="required alphanumeric" type="text" maxlength="50" size="15"	value="${item.QUAL_CARD_NO}" /></td>-->
					
					<td class="td_type"><ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="QUAL_LEVEL_${item.QUAL_NO}" parentNo="14910" selected="${item.QUAL_LEVEL}" cnpyID="${defaultCpny}" limit="all"/></td>
                    
                    <td class="td_type"><ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="QUAL_GRADE_${item.QUAL_NO}" parentNo="123485" selected="${item.QUAL_GRADE}" cnpyID="${defaultCpny}" limit="all"/></td>
					
					<td class="td_type"><input onclick="setCheckboxChecked(${i.count})" name="QUAL_INSTITUTE_${item.QUAL_NO}" type="text" maxlength="30" size="15"	value="${item.QUAL_INSTITUTE}" /></td>
					
<!--					<td><ait:SelectSyCodeByCpnyID name="ACQUISITION_MODES_${item.QUAL_NO}" parentNo="14906" selected="${item.ACQUISITION_MODES}" cnpyID="${defaultCpny}" limit="all"/></td>-->
					
					<td class="td_type">
						<input   type="text" id="DATE_OBTAINED_${i.index}" name="DATE_OBTAINED_${item.QUAL_NO}" value="${item.DATE_OBTAINED}" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.count})"/>		
					</td>
					
					<td class="td_type" style="display:none">
						<input onclick="setCheckboxChecked(${i.count})"  type="text" id="VALIDITY_DATE_${i.index}" name="VALIDITY_DATE_${item.QUAL_NO}" value="${item.VALIDITY_DATE}" value="${item.VALIDITY_DATE}" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>	
					</td>

                    <td class="td_type"><input name="QUAL_REMARK_${item.QUAL_NO}" class="textInput" type="text" maxlength="30" size="15"   value="${item.QUAL_REMARK}" onclick="setCheckboxChecked('${i.count}');" /></td>
                    
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