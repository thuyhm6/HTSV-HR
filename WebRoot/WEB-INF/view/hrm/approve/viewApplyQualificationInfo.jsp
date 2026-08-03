<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateQualificationInfo");

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
}function submitForm1(type){
	alert(type);
	
}
function submitForm2(type){
	if($("#SUBMIT_TYPE").val()==1){
			$("#SUBMIT_TYPE").val(2);
	var $form = $("#updateQualificationInfo").submit();
	}
}
function submitForm3(type){
	if($("#SUBMIT_TYPE").val()==1){
		$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	var $form = $("#updateQualificationInfo").submit();
	}
	
}
function submitForm4(type){
	alert(type);
	
}
</script>
<style type="text/css">
.a{
color: red;
}

</style>
<%-- <c:if test="${qualificationInfo.SUBMIT_TYPE eq 1}"> --%>

	<div class="pageHeader"
		style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

		<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
			<div class="subBar">
				<ul>
					<li>
						<div>
							<a class="buttonActive" onclick="print()"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span> <!-- 印刷  --> <spring:message code="hrm.approve.PRINTING" />       </span> </a>
						</div>
					</li>
					<c:if test="${qualificationInfo.SUBMIT_TYPE eq 1}">
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm2('submit');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--完结--><spring:message code="hrm.approve.OVER" />  </span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm3('back');" 
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 退回  --> <spring:message code="hrm.approve.RETURN"/></span>
							</a>
						</div>
					</li>
					</c:if>
					<%-- <li>
						<div>
							<a class="buttonActive" onclick="submitForm4('email');"
								alt="点击上传"><span><!--制定邮件 --><spring:message code="hrm.approve.MAKE_MAILE" /></span> </a>
						</div>
					</li> --%>
				</ul>
			</div>
		</div>

	</div>
	<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv">
		<form id="updateQualificationInfo"
			onsubmit="return validateCallbackViewFamilyInfo(this,navTabAjaxDoneWithForm);"
			action="/hrm/approve/updateQualificationInfo" method="post">
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
							 <!-- 申请前 -->  <spring:message code="hrm.approve.APPLY_FRONT" />
						</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>
							1
						</td>
						<td>
							<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
						</td>
						<td>
							<font style="line-height: 20px;" color="${qualificationInfo.QUAL_NAME ne qualificationInfoPro.QUAL_NAME ?'red':''}">${qualificationInfo.QUAL_NAME}
						</td>
						<td>
							${qualificationInfoPro.QUAL_NAME}
						</td>
					</tr>
					<tr>
						<td>
							2
						</td>
						<td>
							<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_LEVEL ne qualificationInfoPro.QUAL_LEVEL ?'red':''}">${qualificationInfo.QUAL_LEVEL}
						</td>
						<td>
							${qualificationInfoPro.QUAL_LEVEL}
						</td>
					</tr>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td>
							3
						</td>
						<td>
							<spring:message code="hrm.empinfo.ZIGE_FENSHU.Z" /><!--资格分数-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_GRADE ne qualificationInfoPro.QUAL_GRADE ?'red':''}">${qualificationInfo.QUAL_GRADE}
						</td>
						<td>
							${qualificationInfoPro.QUAL_GRADE}
						</td>
					</tr>
					<tr>
						<td>
							4
						</td>
						<td>
							<!-- 获证日期 --><spring:message code="hrm.empinfo.award_date" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.DATE_OBTAINED ne qualificationInfoPro.DATE_OBTAINED?'red':''}">${qualificationInfo.DATE_OBTAINED}
						</td>
						<td>
							${qualificationInfoPro.DATE_OBTAINED}
						</td>
					</tr>
					<tr>
						<td>
							5
						</td>
						<td>
							<!-- 有效日期 --> <spring:message code="hrm.empinfo.Valid_date" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.VALIDITY_DATE ne qualificationInfoPro.VALIDITY_DATE ?'red':''}">${qualificationInfo.VALIDITY_DATE}
						</td>
						<td>
							${qualificationInfoPro.VALIDITY_DATE}
						</td>
					</tr>
					<tr>
						<td>
							6
						</td>
						<td>
							<!--证书编号--> <spring:message code="hrm.empinfo.Certificate_number" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_CARD_NO ne qualificationInfoPro.QUAL_CARD_NO ?'red':''}">${qualificationInfo.QUAL_CARD_NO}
						</td>
						<td>
							${qualificationInfoPro.QUAL_CARD_NO}
						</td>
					</tr>
					<tr>
						<td>
							7
						</td>
						<td>
							<!-- 发行机关 --><spring:message code="hrm.approve.ISSUE_DEPARTMENT"/>
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_INSTITUTE ne qualificationInfoPro.QUAL_INSTITUTE ?'red':''}">${qualificationInfo.QUAL_INSTITUTE}
						</td>
						<td>
							${qualificationInfoPro.QUAL_INSTITUTE}
						</td>
					</tr>
				<tr>
					<td>
						8
					</td>
					<td>
						 <!-- 附件 --><spring:message code="hrm.recruitManage.ENCLOSURE" />
					</td>
					<td >
						<span id="fileN"></span>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
					<td>
					<c:forEach items="${fileList}" var="item" varStatus="i">
					<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
					</c:forEach>
						<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
				</tr>
				<tr>
						<td>
							9
						</td>
						<td>
							<!-- 备注 --><spring:message code="ess.empInfo.remarks" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_REMARK ne qualificationInfoPro.QUAL_REMARK ?'red':''}">${qualificationInfo.QUAL_REMARK}
						</td>
						<td>
							${qualificationInfoPro.QUAL_REMARK}
						</td>
					</tr>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<tr>
						<td>
							3
						</td>
						<td>
							<!-- 获证日期 --><spring:message code="hrm.empinfo.award_date" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.DATE_OBTAINED ne qualificationInfoPro.DATE_OBTAINED?'red':''}">${qualificationInfo.DATE_OBTAINED}
						</td>
						<td>
							${qualificationInfoPro.DATE_OBTAINED}
						</td>
					</tr>
					<tr>
						<td>
							4
						</td>
						<td>
							<!-- 有效日期 --> <spring:message code="hrm.empinfo.Valid_date" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.VALIDITY_DATE ne qualificationInfoPro.VALIDITY_DATE ?'red':''}">${qualificationInfo.VALIDITY_DATE}
						</td>
						<td>
							${qualificationInfoPro.VALIDITY_DATE}
						</td>
					</tr>
					<tr>
						<td>
							5
						</td>
						<td>
							<!--证书编号--> <spring:message code="hrm.empinfo.Certificate_number" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_CARD_NO ne qualificationInfoPro.QUAL_CARD_NO ?'red':''}">${qualificationInfo.QUAL_CARD_NO}
						</td>
						<td>
							${qualificationInfoPro.QUAL_CARD_NO}
						</td>
					</tr>
					<tr>
						<td>
							6
						</td>
						<td>
							<!-- 发行机关 --><spring:message code="hrm.approve.ISSUE_DEPARTMENT"/>
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_INSTITUTE ne qualificationInfoPro.QUAL_INSTITUTE ?'red':''}">${qualificationInfo.QUAL_INSTITUTE}
						</td>
						<td>
							${qualificationInfoPro.QUAL_INSTITUTE}
						</td>
					</tr>
				<tr>
					<td>
						7
					</td>
					<td>
						 <!-- 附件 --><spring:message code="hrm.recruitManage.ENCLOSURE" />
					</td>
					<td >
						<span id="fileN"></span>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
					<td>
					<c:forEach items="${fileList}" var="item" varStatus="i">
					<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
					</c:forEach>
						<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:if test="${qualificationInfo.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
				</tr>
				<tr>
						<td>
							8
						</td>
						<td>
							<!-- 备注 --><spring:message code="ess.empInfo.remarks" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${qualificationInfo.QUAL_REMARK ne qualificationInfoPro.QUAL_REMARK ?'red':''}">${qualificationInfo.QUAL_REMARK}
						</td>
						<td>
							${qualificationInfoPro.QUAL_REMARK}
						</td>
					</tr>
					</c:if>
<script type="text/javascript">
$("#fileN").html('');
var fileUrl = $("#fileUrls").val();
var checkVal = $("#fileNames").val();
var fileResult = fileUrl.split(";");
var fileResult2 = checkVal.split(";");
var url ='';
var personId =$("#personId").val();
if (fileResult.length > 0) {
	for ( var i = 0; i < fileResult.length; i++) {
		url += "<a class='a' href='/ess/infoApplyLeave/downloadFile?fileName=/resources/temp/apply/applyleave/"
			+personId+"/"+ fileResult[i] +"&file="+fileResult2[i]+"'>" + fileResult2[i] + "</a> | ";
	}
}
$("#fileN").html(url);
</script>

				<input type="hidden" id="fileUrls" name="FILE_URL"
					value="${qualificationInfo.FILE_URL}" />
				<input type="hidden" id="fileNames" name="FILE_NAME"
					value="${qualificationInfo.FILE_NAME}" />
				<input type="hidden" id="personId" name="personId"
					value="${LoginUser.personId}" />
				</tbody>
			</table>
			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							<!--回复 --><spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${qualificationInfo.CALLBACK}</textarea>
						</td>
					</tr>
				</table>
				<input type="hidden" value="${qualificationInfo.APPLY_TYPE_NUM}"
					name="APPLY_TYPE" id="APPLY_TYPE_NUM" />
				<!-- 1提交 2审批 3退回 4取消 -->
				<input type="hidden" value="${qualificationInfo.SUBMIT_TYPE}"
					id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
				<div width="100%">



					<input type="hidden" value="${qualificationInfo.PERSON_ID}"
						name="PERSON_NO" id="PERSON_NO" />
					<input type="hidden" value="${qualificationInfo.PERSON_ID}"
						name="PERSON_ID" id="PERSON_ID" />
					<input type="hidden" value="${qualificationInfo.ESS_TYPE_CODE }"
						id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
					<input type="hidden" value="${qualificationInfo.APPLY_TYPE }"
						id="APPLY_TYPES" name="APPLY_TYPES" />
					
					<input type="hidden" value="${qualificationInfo.QUAL_NO}"
						id="QUAL_NO" name="QUAL_NO" />
					<input type="hidden" value="${qualificationInfo.UPDATE_QUAL_NO}"
						id="UPDATE_QUAL_NO" name="UPDATE_QUAL_NO" />
					<input type="hidden"  value="${qualificationInfo.FILENOSSTR}" id="FILENOSSTR" name="FILENOSSTR" />

					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容 --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${qualificationInfo.EARROR}</textarea>
							</td>
						</tr>
					</table>
				</div>
		</form>
	</div>

<%-- </c:if> --%>