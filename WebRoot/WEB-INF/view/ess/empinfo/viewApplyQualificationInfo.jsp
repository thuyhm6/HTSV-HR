<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#essAddQualificationInfo");

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
 

</script>

<div class="pageContent">
<!--<h1> 资格事项 <spring:message code="ess.empInfo.qualifications_matter" /></h1>
	--><form id="essAddQualificationInfo" method="post"
		action="/ess/empinfo/essAddQualificationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<c:if test="${APPLY_TYPE=='1'}">
					<input type="hidden" name="APPLY_TYPE" value="${1}" />

				</c:if>
				<c:if test="${APPLY_TYPE=='2'}">
					<input type="hidden" name="APPLY_TYPE" value="${2}" />

				</c:if>
				<input type="hidden" name="UPDATE_QUAL_NO" value="${QUAL_NO}" />
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
						<!-- 资格证书 --><spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_NAME}
					</td>
					<td class="td_title" width="20%"></td>
					<td class="td_type"></td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
						<!-- 资格等级 --><spring:message code="hrm.empinfo.Qualification_grade" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_LEVEL}
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hrm.empinfo.ZIGE_FENSHU.Z" /><!--资格分数-->
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_GRADE}
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="20%">
						<!-- 资格证书 --><spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_NAME}
					</td>
					<td class="td_title" width="20%">
						<!-- 资格等级 --><spring:message code="hrm.empinfo.Qualification_grade" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_LEVEL}
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!-- 获证日期 --><spring:message code="ess.empInfo.certified_date" />
					</td>
					<td class="td_type">
						${qualificationInfo.DATE_OBTAINED}
					</td>
					<td class="td_title" width="20%">
					<!-- 有效日期 --><spring:message code="ess.empInfo.effective_date" />
					</td>
					<td class="td_type">
						${qualificationInfo.VALIDITY_DATE}
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
						<!-- 证书编号 --><spring:message code="ess.empInfo.certificate_number" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_CARD_NO}
					</td>
					<td class="td_title" width="20%">
						<!-- 发证机关 --><spring:message code="hrm.empinfo.Issuing_authority" />
					</td>
					<td class="td_type">
						${qualificationInfo.QUAL_INSTITUTE}
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!-- 备注 --><spring:message code="org.title.REMARK" />
					</td>
					
					<td class="td_type" colspan="3">
					
								${qualificationInfo.QUAL_REMARK}
								
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%"><!-- 附件 --><spring:message code="org.title.enclosure" /></td>
					
					<td class="td_type" colspan="4">
					
								<span id="fileN"></span>
				<c:forEach items="${fileList}" var="item" varStatus="i">
						  <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
				</c:forEach>
								
					</td>
				</tr>




			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<input type="hidden" id="fileUrl" name="FILE_URL"
					value="${qualificationInfo.FILE_URL}" />
				<input type="hidden" id="fileName" name="FILE_NAME"
					value="${qualificationInfo.FILE_NAME}" />
					<input type="hidden" id="personId" name="personId"
					value="${LoginUser.personId}" />
	</form>
</div>
<script type="text/javascript">
$("#fileN").html('');
var fileUrl = $("#fileUrl").val();
var checkVal = $("#fileName").val();
var PERSON_ID=$("#PERSON_ID").val();
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