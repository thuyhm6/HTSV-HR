<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateWorkInfo");

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
		
	     var $form = $("#updateWorkInfo").submit();
	}
}
function submitForm3(type){
		if($("#SUBMIT_TYPE").val()==1){
	$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	     var $form = $("#updateWorkInfo").submit();
	}
	
}
function submitForm4(type){
	alert(type);
	
}

</script>
<%-- <c:if  test="${workExperienceList.SUBMIT_TYPE eq 1}" > --%>

<div class="pageHeader"
	style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

	<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
		<div class="subBar">
			<ul>
				<li>
					<div>
						<a class="buttonActive" onclick="print()" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 印刷  --> <spring:message code="hrm.approve.PRINTING" /> </span>
						</a>
					</div>
				</li>
				<c:if  test="${workExperienceList.SUBMIT_TYPE eq 1}" >
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
				</c:if>
				<!-- <li>
					<div>
						<a class="buttonActive" onclick="submitForm4('email');" alt="点击上传"><span>制定邮件</span>
						</a>
					</div>
				</li> -->
			</ul>
		</div>
	</div>

</div>
<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv">
	<form id="updateWorkInfo"
		onsubmit="return validateCallbackViewFamilyInfo(this, navTabAjaxDoneWithForm);"
		action="/hrm/approve/updateWorkInfo" method="post">
		<table class="table" width="100%" 
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
						<!-- 申请后 --><spring:message code="hrm.approve.APPLY_AFTER" />
					</th>
					<th width="25%">
						<!-- 申请前 --> <spring:message code="hrm.approve.APPLY_FRONT" />
					</th>
				</tr>
			</thead>
			<tbody>

				
				<tr>
					<td>
						1
					</td>
					<td>
						<!-- 公司名称 --> <spring:message code="hrm.empinfo.COMPANY_NAME" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.CPNY_NAME ne workExperienceInfoPro.CPNY_NAME ?'red':''}">${workExperienceList.CPNY_NAME}</font>
					</td>
					<td>
						${workExperienceInfoPro.CPNY_NAME}
					</td>
				</tr>
				<tr>
					<td>
						2
					</td>
					<td>
						<!-- 入职日期 --><spring:message code="hrm.recruitManage.DATE_STARTED" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.START_DATE ne workExperienceInfoPro.START_DATE ?'red':''}">${workExperienceList.START_DATE}
					</td>
					<td>
						${workExperienceInfoPro.START_DATE}
					</td>
				</tr>
				<tr>
					<td>
						3
					</td>
					<td>
						<!-- 离职日期 --> <spring:message code="hrm.recruitManage.LEAVE_DATE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.END_DATE ne workExperienceInfoPro.END_DATE ?'red':''}">${workExperienceList.END_DATE}
					</td>
					<td>
						${workExperienceInfoPro.END_DATE}
					</td>
				</tr>
				<tr>
					<td>
						4
					</td>
					<td>
						<spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!--月薪-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.PAY_YEAR ne workExperienceInfoPro.PAY_YEAR?'red':''}">${workExperienceList.PAY_YEAR}</font>
					</td>
					<td>
						${workExperienceInfoPro.PAY_YEAR}
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td>
						5
					</td>
					<td>
					    <spring:message code="hr.viewCondSql.title.LIZHIYUANYIN" /><!--离职事由-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.RESIGN_REASON ne workExperienceInfoPro.RESIGN_REASON?'red':''}">${workExperienceList.RESIGN_REASON}
					</td>
					<td>
						${workExperienceInfoPro.RESIGN_REASON}
					</td>
				</tr>
				<tr>
					<td>
						6
					</td>
					<td>
					    <!-- 备注 --> <spring:message code="hrm.empinfo.REMARK" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.REMARK ne workExperienceInfoPro.REMARK?'red':''}">${workExperienceList.REMARK}
					</td>
					<td>
						${workExperienceInfoPro.REMARK}
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td>
						5
					</td>
					<td>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.DEPT_NAME ne workExperienceInfoPro.DEPT_NAME?'red':''}">${workExperienceList.DEPT_NAME}</font>
					</td>
					<td>
						${workExperienceInfoPro.DEPT_NAME}
					</td>
				</tr>
				<tr>
					<td>
						6
					</td>
					<td>
						<!--岗位--><spring:message code="rp.report.title.dutyinfo" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.DUTY ne workExperienceInfoPro.DUTY?'red':''}">${workExperienceList.DUTY}</font>
					</td>
					<td>
						${workExperienceInfoPro.DUTY}
					</td>
				</tr>
				<tr>
					<td>
						7
					</td>
					<td>
						<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!--主要业务-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${workExperienceList.POSITION ne workExperienceInfoPro.POSITION?'red':''}">${workExperienceList.POSITION}</font>
					</td>
					<td>
						${workExperienceInfoPro.POSITION}
					</td>
				</tr>
				</c:if>

 <input type="hidden" value="${workExperienceList.APPLY_TYPE_NUM}" name="APPLY_TYPE" id="APPLY_TYPE_NUM"/>
          <!-- 1提交 2审批 3退回 4取消 -->
          <input type="hidden"  value="${workExperienceList.SUBMIT_TYPE}" id="SUBMIT_TYPE" name="ACTIVITY_NUM" />


		<input type="hidden" value="${workExperienceList.ESS_TYPE_CODES }"
						id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
		<input type="hidden" value="${workExperienceList.APPLY_TYPES }"
						id="APPLY_TYPES" name="APPLY_TYPES" />
		<input type="hidden" value="${workExperienceList.PERSON_ID}" name="PERSON_NO" id="PERSON_NO"/>
		<input type="hidden" value="${workExperienceList.PERSON_ID}" name="PERSON_ID" id="PERSON_ID"/>
        <input type="hidden"  value="${workExperienceList.WORK_EXPER_NO}" id="WORK_EXPER_NO" name="WORK_EXPER_NO" />
	    <input type="hidden"  value="${workExperienceList.UPDATE_WORK_EXPER_NO}" id="UPDATE_WORK_EXPER_NO" name="UPDATE_WORK_EXPER_NO" />
		

			</tbody>
		</table>
		<div width="100%">
			<table class="user_table" width="100%">
				<tr>
					<td calss="td_title" width="20%" style="background: #ddd">
						 <!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
					</td>
					<td calss="td_type" width="80%">
						<textarea name="CALLBACK" style="width: 200px; height: 80px">${workExperienceList.CALLBACK}</textarea>
					</td>
				</tr>
			</table>

			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							<!-- 错误内容  --> <spring:message code="hrm.approve.ERROR_CONTENT" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="EARROR" style="width: 200px; height: 80px">${workExperienceList.EARROR}</textarea>
						</td>
					</tr>
				</table>
			</div>
	</form>
</div>
<%-- </c:if> --%>