<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeleteResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteBidMatter',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function finaldegree(){
	var check=$('#final').prop('checked');
	if(check==true){
		$('#FINAL_DEGREE_WHETHER').attr('value','Y');
	}else{
		$('#FINAL_DEGREE_WHETHER').attr('value','N');
	}
}

//附件上传
function uploadifySuccess_qualfile(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
	  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
	  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",navTab.getCurrentPanel()).html(files);
	  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
	  $("#fileName",navTab.getCurrentPanel()).val(fileName);
}
</script>
<div>
<form id="editBidMatter" method="post"
	action="/hrm/empinfo/editBidMatter" class="pageForm required-validate"
	onsubmit="return validateAddResumeInfoCallback(this,navTab);"><input
	TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID"
	VALUE="${PERSON_ID}"> <input type="hidden" name="QUAL_NO"
	id="QUAL_NO" value="${personInfo.QUAL_NO}">
<div>
<table class="user_table" width="100%" border="1" cellpadding="2"
	cellspacing="1">
	<tr>
		<td>
		<table class="user_table" width="100%">
			<tr>
				<td>
				<table class="user_table" width="100%">
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<tr>
							<td width="15%" class="td_title">
								<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
							</td>
							<td width="35%" class="td_type">
								<input type="text" class="required" id="QUAL_NAME" name="QUAL_NAME" value="${personInfo.QUAL_NAME}">
							</td>
							<td width="15%" class="td_title">
								<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
							</td>
							<td width="35%" class="td_type">
								<input type="text" id="QUAL_LEVEL" name="QUAL_LEVEL" value="${personInfo.QUAL_LEVEL}">
							</td>
						</tr>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<tr>
							<td width="15%" class="td_title">
								<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
							</td>
							<td width="35%" class="td_type">
								<input type="text" class="required" id="QUAL_NAME" name="QUAL_NAME" value="${personInfo.QUAL_NAME}">
							</td>
							<td width="15%" class="td_title"></td>
							<td width="35%" class="td_type"></td>
						</tr>
						<tr>
							<td width="15%" class="td_title">
								<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
							</td>
							<td width="35%" class="td_type">
								<input type="text" id="QUAL_LEVEL" name="QUAL_LEVEL" value="${personInfo.QUAL_LEVEL}">
							</td>
							<td width="15%" class="td_title">
								<spring:message code="hrm.empinfo.ZIGE_FENSHU.Z" /><!--资格分数-->
							</td>
							<td width="35%" class="td_type">
								<input type="text" id="QUAL_GRADE" name="QUAL_GRADE" value="${personInfo.QUAL_GRADE}">
							</td>
						</tr>
					</c:if>
					<tr>
						<td width="15%" class="td_title"><spring:message
							code="hrm.empinfo.award_date" /><!--获证日期--></td>
						<td width="35%" class="td_type"><input
							type="text" id="DATE_OBTAINED" name="DATE_OBTAINED" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.DATE_OBTAINED}" /></td>
						<td width="15%" class="td_title"><spring:message
							code="hrm.empinfo.Issuing_authority" /><!--发证机关--></td>
						<td width="35%" class="td_type"><input
							type="text" id="QUAL_INSTITUTE" name="QUAL_INSTITUTE"
							value="${personInfo.QUAL_INSTITUTE}"></td>
					</tr>
					<tr>
						<td width="15%" class="td_title"><spring:message
							code="hrm.empinfo.Valid_date" /><!--有效日期--></td>
						<td width="35%" class="td_type"><input
							type="text" id="VALIDITY_DATE" name="VALIDITY_DATE" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${personInfo.VALIDITY_DATE}" /></td>
						<td width="15%" class="td_title"><spring:message
							code="hrm.empinfo.Certificate_number" /><!--证书编号--></td>
						<td width="35%" class="td_type"><input
							type="text" id="QUAL_CARD_NO" name="QUAL_CARD_NO"
							value="${personInfo.QUAL_CARD_NO}"></td>
					</tr>
					<tr>
						<td width="15%" class="td_title"><spring:message
							code="hrm.empinfo.REMARK" /><!--备注--></td>
						<td width="85%" class="td_type" colspan="3"><textarea name="QUAL_REMARK"
							id="QUAL_REMARK" style="width: 580px; height: 80px">${personInfo.QUAL_REMARK}</textarea>
						</td>
					</tr>
				</table>
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
		</td>
	</tr>
</table>
</div>
<c:if test="${qual_no=='0' }">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message
		code="hrm.recruitManage.ATTACHED_FILE" /><!--附加文件--></span></a> <a class="w_button" href="#"
		onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message
		code="button.delete" /><!--删除--></span></a></div>
	<table id="fileTable" class="list" width="100%">
		<thead>
			<tr>
				<th width="10%">V</th>
				<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!--附件--></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
</c:if> <c:if test="${qual_no!='0' }">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<a class="w_button" href="#"
		onclick="uploadAttDialog_new('viewResumeList_viewBidMatterunit','/hrm/empinfo/viewSingleBidMatter?PERSON_ID=${PERSON_ID}$QUAL_NO=${qual_no }','${qual_no}','hrQualification','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message
		code="hrm.recruitManage.ATTACHED_FILE" /><!--附加文件--></span></a> <a class="w_button" href="#"
		onclick="deleteAttList_new('viewResumeList_viewBidMatterunit','/hrm/empinfo/viewSingleBidMatter?PERSON_ID=${PERSON_ID}&QUAL_NO=${qual_no }',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><spring:message
		code="button.delete" /><!--删除--></span></a></div>
	<table class="table" width="100%" layoutH="670">
		<thead>
			<tr>
				<th width="10%">V</th>
				<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!--附件--></th>
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
