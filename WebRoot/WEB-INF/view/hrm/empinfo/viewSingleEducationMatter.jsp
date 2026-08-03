<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	function validateAddResumeInfoCallback(form, callback) {
		var $form = $("#" + form);
		if (!$form.valid()) {
			return false;
		}
		alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>", {//确定要保存吗？
			okCall : function() {
				$.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					data : $form.serializeArray(),
					dataType : "json",
					cache : false,
					success : callback || DWZ.ajaxDone,
					error : DWZ.ajaxError
				});
			}
		});
		return false;
	}

	function validateDeleteResumeInfoCallback(form, callback) {
		var $form = $("#" + form);
		alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>", {//确定要删除吗？
			okCall : function() {
				$.ajax({
					type : form.method || 'POST',
					url : '/hrm/empinfo/deleteEducationMatter',
					data : $form.serializeArray(),
					dataType : "json",
					cache : false,
					success : callback || DWZ.ajaxDone,
					error : DWZ.ajaxError
				});
			}
		});
		return false;
	}

	function finaldegree() {
		var check = $('#final').prop('checked');
		if (check == true) {
			$('#FINAL_DEGREE_WHETHER').attr('value', 'Y');
		} else {
			$('#FINAL_DEGREE_WHETHER').attr('value', 'N');
		}
		var check1 = $('#final1').prop('checked');
		if (check1 == true) {
			$('#STUDY_EXPERIENCE').attr('value', 'Y');
		} else {
			$('#STUDY_EXPERIENCE').attr('value', 'N');
		}
	}

	//附件上传
	function uploadifySuccess_educationfile(file, data, response) {
		//获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
		var files = $("#fileNmae", navTab.getCurrentPanel()).html();
		var fileUrl = $("#fileUrl", navTab.getCurrentPanel()).val();
		var fileName = $("#fileName", navTab.getCurrentPanel()).val();
		var fileResult = data.split(";");
		//第一个文件
		if (files == "") {
			files = fileResult[0];
			fileName = fileResult[0];
			fileUrl = fileResult[1];
		} else {
			files += ";" + fileResult[0];
			fileName += ";" + fileResult[0];
			fileUrl += ";" + fileResult[1];
		}
		$("#fileNmae", navTab.getCurrentPanel()).html(files);
		$("#fileUrl", navTab.getCurrentPanel()).val(fileUrl);
		$("#fileName", navTab.getCurrentPanel()).val(fileName);
	}

</script>
<div>
<form id="editEducationMatter" method="post"
	action="/hrm/empinfo/editEducationMatter"
	class="pageForm required-validate"
	onsubmit="return validateAddResumeInfoCallback(this,navTab);"><input
	TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID"
	VALUE="${PERSON_ID}"> <input type="hidden" name="EDUC_NO"
	id="EDUC_NO" value="${personInfo.EDUC_NO}">
<div>

<table class="user_table" width="100%">
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校--></td>
		<td width="35%" class="td_type" colspan="3"><input type="text"
			 id="INSTITUTION_NAME" name="INSTITUTION_NAME"
			value="${personInfo.INSTITUTION_NAME}" size="90" /></td>
	</tr>
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校--></td>
		<td width="35%" class="td_type"><input type="text"
			 id="INSTITUTION_NAME" name="INSTITUTION_NAME"
			value="${personInfo.INSTITUTION_NAME}" size="20" /></td>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.XUEXIAO_ADDRESS.Z" /><!--学校地址--></td>
		<td width="35%" class="td_type"><input type="text"
			 id="PLACE" name="PLACE"
			value="${personInfo.PLACE}" size="20" /></td>
	</tr>
	</c:if>
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.DEGREE_CODE" /><!--学历--></td>
		<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
			name="DEGREE_CODE" selected="${personInfo.DEGREE_CODE}"
			parentNo="13769" limit="all" /></td>
		<td width="15%" class="td_title"><spring:message
			code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否--></td>
		<td width="35%" class="td_type">
			<c:if test="${personInfo.FINAL_DEGREE_WHETHER=='Y' }">
				<input type="checkbox" checked="final" id="final"
					onclick="finaldegree()">
				<input type="hidden" name='FINAL_DEGREE_WHETHER' id='FINAL_DEGREE_WHETHER' 
					value="Y">
			</c:if> 
			<c:if test="${personInfo.FINAL_DEGREE_WHETHER !='Y' }">
				<input type="checkbox" id="final" onclick="finaldegree()">
				<input type="hidden" id="FINAL_DEGREE_WHETHER"
					name="FINAL_DEGREE_WHETHER" value="N">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_title"><spring:message
					code="hrm.recruitManage.START_DATE" /><!--入学日期--></td>
		<td width="35%" class="td_type"><input type="text"
			id="edu_START_DATE" name="START_DATE" onchange="eduDateSize(0)" onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
			value="${personInfo.START_DATE}" style="float: left" /></td>
		<td class="td_title"><spring:message
					code="hrm.recruitManage.END_DATE" /><!--毕业日期--></td>
		<td width="35%" class="td_type" colspan="3"><input type="text" id="edu_END_DATE" name="END_DATE"
			onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
			value="${personInfo.END_DATE}" style="float: left" /></td>
	</tr>
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hrm.recruitManage.SUBJECT" /><!--专业--></td>
		<td width="35%" class="td_type"><input type="text" id="SUBJECT"
			name="SUBJECT" value="${personInfo.SUBJECT}" size="19" /></td>
		<td width="15%" class="td_title"><spring:message
			code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!--副专业--></td>
		<td width="35%" class="td_type"><input type="text" id="SUBJECT_SECOND"
			name="SUBJECT_SECOND" value="${personInfo.SUBJECT_SECOND}" size="19" /></td>
	</tr>
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.country" /><!--国家--></td>
		<td width="35%" class="td_type">
			<ait:SelectSyCodeByCpnyID name="SITE_COUNTRY" id="SITE_COUNTRY"
               parentNo="870" cnpyID="${defaultCpny}" selected="${personInfo.SITE_COUNTRY}" limit="all" />
		</td>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号--></td>
		<td width="35%" class="td_type"><input type="text" id="EDU_DEG_NUM"
			name="EDU_DEG_NUM" value="${personInfo.EDU_DEG_NUM}" size="19" /></td>
	</tr>
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	<tr>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.JIAOYU_XINGSHI.Z" /><!--教育形式--></td>
		<td width="35%" class="td_type">
			<ait:SelectSyCodeByCpnyID name="SCHOOL_LENGTH" id="SCHOOL_LENGTH"
               parentNo="13704" cnpyID="${defaultCpny}" selected="${personInfo.SCHOOL_LENGTH}" limit="all" />
		</td>
		<td width="15%" class="td_title"><spring:message
			code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号--></td>
		<td width="35%" class="td_type"><input type="text" id="EDU_DEG_NUM"
			name="EDU_DEG_NUM" value="${personInfo.EDU_DEG_NUM}" size="19" /></td>
	</tr>
	</c:if>
	<tr>
		<td class="td_title"><spring:message
					code="hrm.empinfo.REMARK" /><!--备注--></td>
		<td width="85%" class="td_type" colspan='5'>
			<textarea name="REMARK" style="width: 570px; height: 80px">${personInfo.REMARK}</textarea>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="user_table">
	<tr>
		<c:if test="${not empty personInfo.UPDATED_BY}">
			<td class="td_title" width="5%"><spring:message
				code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
			<td class="td_type" width="25%">${personInfo.UPDATED_BY
			}&nbsp&nbsp${personInfo.UPDATED_IP }</td>
			<td class="td_title" width="5%"><spring:message
				code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
			<td class="td_type" width="25%">${personInfo.UPDATE_DATE }</td>
		</c:if>
		<c:if test="${empty personInfo.UPDATED_BY}">
			<td class="td_title" width="5%"><spring:message
				code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
			<td class="td_type" width="25%">${personInfo.CREATED_BY
			}&nbsp&nbsp${personInfo.CREATED_IP }</td>
			<td class="td_title" width="5%"><spring:message
				code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
			<td class="td_type" width="25%">${personInfo.CREATE_DATE }</td>
		</c:if>
	</tr>
</table>

</div>
<c:if test="${educ_no=='0' }">
	<div>
		<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
			<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
			   <span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!--附加文件--></span>
			</a> 
			<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
			   <span><spring:message code="button.delete" /><!--删除--></span>
			</a>
		</div>
	<table id="fileTable" class="list" width="100%">
		<thead>
			<tr>
				<th width="10%">V</th>
				<th width="90%"><spring:message
					code="hrm.recruitManage.ENCLOSURE" /><!--附件--></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
</c:if> <c:if test="${educ_no!='0' }">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<a class="w_button" href="#"
		onclick="uploadAttDialog_new('viewResumeList_viewEducationMatterunit','/hrm/empinfo/viewSingleEducationMatter?PERSON_ID=${PERSON_ID}$EDUC_NO=${educ_no }','${educ_no}','hrEducation','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message
		code="hrm.recruitManage.ATTACHED_FILE" /><!--附加文件--></span></a> <a
		class="w_button" href="#"
		onclick="deleteAttList_new('viewResumeList_viewEducationMatterunit','/hrm/empinfo/viewSingleEducationMatter?PERSON_ID=${PERSON_ID}&EDUC_NO=${educ_no }',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
		<span><spring:message code="button.delete" /><!--删除--></span></a></div>
	<table class="table" width="100%" layoutH="670">
		<thead>
			<tr>
				<th width="10%">V</th>
				<th width="90%"><spring:message
					code="hrm.recruitManage.ENCLOSURE" /><!--附件--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personInfo.fileList}" var="item" varStatus="i">
				<tr>
					<td class='td_center'><input type="checkbox" name="FILE_NO"
						value="${item.FILE_NO}" /></td>
					<td><a
						href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</c:if></form>
</div>
