<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateAddressInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}
function submitForm1(type){
	alert(type);
	
}
function submitForm2(type){
if($("#SUBMIT_TYPE").val()==1){
		$("#SUBMIT_TYPE").val(2);
	var $form = $("#updateAddressInfo").submit();
	}
}
function submitForm3(type){
	if($("#SUBMIT_TYPE").val()==1){
		 $("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	var $form = $("#updateAddressInfo").submit();
	}
	
}
function submitForm4(type){
	alert(type);
	
}
</script>
<c:if test="${AddressList.SUBMIT_TYPE eq 1}">
<div class="pageHeader"
	style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

	<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
		<div class="subBar">
			<ul>
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm1('print');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 印刷  --> <spring:message code="hrm.approve.PRINTING" /> </span>
						</a>
					</div>
				</li>
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm2('submit');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span> <!-- 完结 --> <spring:message code="hrm.approve.OVER" /></span>
						</a>
					</div>
				</li>
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm3('back');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--退回  --><spring:message code="hrm.approve.RETURN" /> </span>
						</a>
					</div>
				</li>
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm4('email');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 制定邮件 --> <spring:message code="hrm.approve.MAKE_MAILE"/>       </span>
						</a>
					</div>
				</li>
			</ul>
		</div>
	</div>

</div>
<div class="pageContent" style="margin: 0px; padding: 0px;">
	<form id="updateAddressInfo" onsubmit="return validateCallbackViewFamilyInfo(this, navTabAjaxDoneWithForm);"
		action="/hrm/approve/updateAddressInfo" method="post">
		<table class="table" width="100%" layoutH="288"
			style="margin: 0px; padding: 0px;">
			<thead>
				<tr>

					<th width="25%">
						NO
					</th>
					<th width="25%">
						Line
					</th>
					<th width="25%">
						 <!-- 申请后 --> <spring:message code="hrm.approve.APPLY_AFTER" />
					</th>
					<th width="25%">
						<!-- 申请前  --> <spring:message code="hrm.approve.APPLY_FRONT" />
					</th>
				</tr>
			</thead>
			<tbody>
          <input type="hidden" value="${AddressList.APPLY_TYPE_NUM}" name="APPLY_TYPE" id="APPLY_TYPE_NUM"/>
          <!-- 1提交 2审批 3退回 4取消 -->
          <input type="hidden"  value="${AddressList.SUBMIT_TYPE}" id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
				<tr>
					<td>
						1
					</td>
					<td>
						<!-- 地址类型 --> <spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" />
					</td>
					<td>
						<font
							color="${AddressList.ADDRESS_TYPE ne AddressInfoPro.ADDRESS_TYPE ?'red':''}">${AddressList.ADDRESS_TYPE}

						
					</td>
					<td>
						${AddressInfoPro.ADDRESS_TYPE}
					</td>
				</tr>
				<tr>
					<td>
						2
					</td>
					<td>
						<!-- 有效开始日 --><spring:message code="hrm.approve.EFFECTIVE_START_DATE" />
					</td>
					<td>
						<font
							color="${AddressList.EFFECTIVE_START_DATE ne AddressInfoPro.EFFECTIVE_START_DATE ?'red':''}">${AddressList.EFFECTIVE_START_DATE}

						
					</td>
					<td>
						${AddressInfoPro.EFFECTIVE_START_DATE}
					</td>
				</tr>
				<tr>
					<td>
						3
					</td>
					<td>
					 <!-- 地址 --> <spring:message code="hrm.empinfo.FAM_ADDRESS" />
					</td>
					<td>
						<font
							color="${AddressList.ADDRESS_CONTENT ne AddressInfoPro.ADDRESS_CONTENT ?'red':''}">${AddressList.ADDRESS_CONTENT}

						
					</td>
					<td>
						${AddressInfoPro.ADDRESS_CONTENT}
					</td>
				</tr>
				<tr>
					<td>
						4
					</td>
					<td>
						<!-- 国家 --> <spring:message code="hrm.empinfo.country" />
					</td>
					<td>
						<font
							color="${AddressList.NATIONALITY ne AddressInfoPro.NATIONALITY ?'red':''}">${AddressList.NATIONALITY}

						
					</td>
					<td>
						${AddressInfoPro.NATIONALITY}
					</td>
				</tr>
				<input type="hidden" value="${AddressList.PERSON_ID}" name="PERSON_ID" id="PERSON_ID"/>
                <input type="hidden"  value="${AddressList.ADDRESS_NO}" id="ADDRESS_NO" name="ADDRESS_NO" />
                                <input type="hidden"  value="${AddressList.UPDATE_ADDRESS_NO}" id="UPDATE_ADDRESS_NO" name="UPDATE_ADDRESS_NO" />
                    
          
         
			</tbody>
		</table>
		<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							 <!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${AddressList.CALLBACK}</textarea>
						</td>
					</tr>
				</table>

				<div width="100%">
					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容 --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${AddressList.EARROR}</textarea>
							</td>
						</tr>
					</table>
				</div>
	</form>
</div>
</c:if>