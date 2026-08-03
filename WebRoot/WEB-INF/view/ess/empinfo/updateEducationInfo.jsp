<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

  function buttons(){
	  $("input[name=APPLY_TYPE]").val("3");
  }

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#essAddEducationInfo");
	

	
	var STUDY_EXPERIENCES = $("#STUDY_EXPERIENCES");
	if(STUDY_EXPERIENCES.attr('checked')=="checked"){
		$("input[name=STUDY_EXPERIENCE]").val("Y");
	}else{
		$("input[name=STUDY_EXPERIENCE]").val("N");
	}
	
	var FINAL_DEGREE_WHETHERS = $("#FINAL_DEGREE_WHETHERS");
	if(FINAL_DEGREE_WHETHERS.attr('checked')=="checked"){
		$("input[name=FINAL_DEGREE_WHETHER]").val("Y");
	}else{
		$("input[name=FINAL_DEGREE_WHETHER]").val("N");
	}
	
	 var fileNosStr="";
	  $("input[name='FILE_NO']").each(function(){
			if($(this).attr("checked") == "checked"){
				fileNosStr = fileNosStr + "" + $(this).val() + "" + ",";
			}
		});
	  fileNosStr = fileNosStr + "'empty'";
	  $("input[name='FILENOSSTR']").val(fileNosStr);
	//判断上传
	if($("#files").val()=='1'){
	if($("#fileQueue_resume").html()==null||$("#fileQueue_resume").html()==""){
		return false;
		}
	}
	

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

function uploadifySuccess_resume(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae").html();
	  var fileUrl = $("#fileUrl").val();
	  var fileName = $("#fileName").val();
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
	  $("#fileNmae").html(files);
	  $("#fileUrl").val(fileUrl);
	  $("#fileName").val(fileName);
}
function eduDateSize(a) {
	var startdate = $('#edu_START_DATE').val();
	var enddate = $('#edu_END_DATE').val();
	if (startdate != '' && enddate != '') {
		sdate = parseInt(startdate.replace('.', ''));
		edate = parseInt(enddate.replace('.', ''));
		if (sdate > edate) {
			alert("<spring:message code="ar.attendanceView.viewNoSwipingCard.beginTimeDontendTime"/>");//开始时间不能大于结束时间!
			if (a == '0') {
				$('#edu_START_DATE').attr('value', '');
			} else if (a == '1') {
				$('#edu_END_DATE').attr('value', '');
			}
		}
	}
}

</script>

<!-- <div class="pageContent"> -->
<div style="background-color: #fff;">
	<!-- <h1>
		能力信息（学历事项）
	</h1> -->
	<form id="essAddEducationInfo" method="post"
		action="/ess/empinfo/essAddEducationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<!-- <div class="pageFormContent" layoutH="56"> -->
		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<c:if test="${APPLY_TYPE=='1'}">
					<input type="hidden" name="APPLY_TYPE" value="${1}" />

				</c:if>
				<c:if test="${APPLY_TYPE=='2'}">
					<input type="hidden" name="APPLY_TYPE" value="${2}" />

				</c:if>
				<input type="hidden" name="UPDATE_EDUC_NO" value="${EDUC_NO}" />
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>	
					<td class="td_title" width="20%">
					<!--学历--><spring:message code="ess.empInfo.Education" />
					</td>
					<td class="td_type" colspan="3">
					<ait:SelectSyCodeByCpnyID name="DEGREE_CODE" parentNo="13769"
							cnpyID="${defaultCpny}" selected="${educationList.DEGREE_CODE}" />
						<input type="hidden" name="PLACE" class="textInput" value='' />
						<input type="hidden" name='FINAL_DEGREE_WHETHER' id='FINAL_DEGREE_WHETHER' value='' />
						<input type="hidden" name="SUBJECT_SECOND" class="textInput" value='' />
						<input type="hidden" name="GRADUATION_TYPE" class="textInput" value='' />
						<input type="hidden" name="EDU_DEG_NUM" class="textInput" value='' />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
				<td class="td_title" width="20%">
				    <spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME"  class="required" value="${educationList.INSTITUTION_NAME}" />
					</td>
					<td class="td_title" width="20%">
					<!--学校地址--><spring:message code="hrm.empinfo.XUEXIAO_ADDRESS.Z" />
					</td>
					<td class="td_type">
						<input type="text" name="PLACE" class="textInput" value='${educationList.PLACE }' />
					</td>
				</tr>
				<tr>	
					<td class="td_title" width="20%">
					<!--学历--><spring:message code="ess.empInfo.Education" />
					</td>
					<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="DEGREE_CODE" parentNo="13769"
							cnpyID="${defaultCpny}" selected="${educationList.DEGREE_CODE}" />
					</td>
					<td width="15%" class="td_title">
						<spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否--></td>
					<td width="35%" class="td_type">
						<input type="checkbox" id="final" <c:if test="${educationList.FINAL_DEGREE_WHETHER eq 'Y'}"> checked="checked" </c:if>  onclick="finaldegree()">
						<input type="hidden" name='FINAL_DEGREE_WHETHER' id='FINAL_DEGREE_WHETHER' >
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!--入学日期--><spring:message code="ess.empInfo.admission_date" />
					</td>
					<td class="td_type">
					<input type="text" name="START_DATE" class="Wdate required"
								id="edu_START_DATE"	onchange="eduDateSize(0)"	 onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${educationList.START_DATE }"/>
					</td>
					<td class="td_title" width="20%">
					<!--毕业日期--><spring:message code="ess.empInfo.graduation_date" />
					</td>
					<td class="td_type">
						<input type="text" name="END_DATE" class="Wdate required"
								id="edu_END_DATE"	onchange="eduDateSize(1)"	onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${educationList.END_DATE }" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
					<!--专业--><spring:message code="ess.empInfo.major" />
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT" class="textInput" value='${educationList.SUBJECT }' />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!--副专业-->
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT_SECOND" class="textInput" value='${educationList.SUBJECT_SECOND }' />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
						<!--教育形式--><spring:message code="hrm.empinfo.JIAOYU_XINGSHI.Z" />
					</td>
					<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="GRADUATION_TYPE" parentNo="13704"
							cnpyID="${defaultCpny}" selected="${educationList.GRADUATION_TYPE}" />
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号-->
					</td>
					<td class="td_type">
						<input type="text" name="EDU_DEG_NUM" class="textInput" value='${educationList.EDU_DEG_NUM }' />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
				<td class="td_title" width="20%">
				    <spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME"  class="required" value="${educationList.INSTITUTION_NAME}" />
					</td>
					<td class="td_title" width="20%">
					<!--专业--><spring:message code="ess.empInfo.major" />
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT" class="textInput" value='${educationList.SUBJECT }' />
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!--备注--><spring:message code="ess.empInfo.remarks" />
					</td>
					<td class="td_type" colspan="3"> 
						<textarea rows="4"  name="REMARKS" class="textInput" cols="70">${educationList.REMARK }</textarea> 
				</td> 
				</tr>
				<tr>
				<td class="td_title" width="20%">
				    <!--附件--><spring:message code="ess.empInfo.enclosure" />
					</td>
					<td class="td_type" colspan="3">
						<span id="fileNmae"></span>
					</td>
				</tr>
<!-- 				<tr> -->

<!-- 					<td class="td_title" width="20%"> -->
<!-- 						专业 -->
<!-- 					</td> -->


<!-- 					<td class="td_type"> -->

<!-- 						<input type="text" name="SUBJECT" class="textInput" /> -->
<!-- 					</td> -->

<!-- 				</tr> -->


<!-- 				<tr> -->

<!-- 					<td class="td_title" width="20%"> -->
<!-- 						备注 -->
<!-- 					</td> -->


<!-- 					<td class="td_type"> -->

<!-- 						<textarea rows="6"  name="REMARK" class="textInput" cols="100"></textarea>  -->
<!-- 					</td> -->

<!-- 				</tr> -->

				<tr>
					<td width="20%" class="td_title">
					<!--附件上传--><spring:message code="ess.empInfo.attachment_upload" />
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ' || LoginUser.cpnyId eq 'SPC_NJ'}"><strong style="color: red;">*</strong>
							<input type="hidden" value="1" id="files">
						</c:if>
					</td>
					<td width="80%" class="td_type" colspan="3">
						<input id="testFileInput_resume" type="file" name="file"
							uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${LoginUser.adminID}',
													formData:{ajax:1},
													queueID:'fileQueue_resume',
													buttonText:'<spring:message code="hr.viewCondSql.title.QINGXUANZE" />',//请选择
													height:25,
													width:60,
													auto:false,
													onUploadSuccess:uploadifySuccess_resume,
													removeTimeout:1
												}" />
						
						<div id="fileQueue_resume" class="fileQueue"></div>
						<input type="hidden" id="fileUrl" name="FILE_URL"
							value="" />
						<input type="hidden" id="fileNosStr" name="FILENOSSTR"
							value="" />
						<input type="hidden" id="fileName" name="FILE_NAME"
							value="" />

						  <div class="buttonActive">
							<div class="buttonContent">
								
								<button type="button"
									onclick="$('#testFileInput_resume').uploadify('upload', '*');return false;">
									<!--上传--><spring:message code="ess.empInfo.upload" />
								</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								
								<button type="button"
									onclick="$('#testFileInput_resume').uploadify('cancel', '*');return false;">
									<!--取消--><spring:message code="ess.empInfo.cancel" />
								</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		<table class="list" width="100%" layoutH="670">
		<thead>
			<tr>
				<th width="10%">V</th>
				<th width="90%"><spring:message
					code="hrm.recruitManage.ENCLOSURE" /><!--附件--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fileList}" var="item" varStatus="i">
				<tr>
					<td class='td_center'><input type="checkbox" name="FILE_NO"
						value="${item.FILE_NO}" /></td>
					<td><a
						href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<%-- <spring:message code="public.title.submit" /> --%>
								<!--修改--><spring:message code="ess.empInfo.modify" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent" onclick="buttons()">
							<button type="submit">
							<!--删除--><spring:message code="ess.empInfo.Delete" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

