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
    

</script>

<div class="pageContent">
	<!--<h1>
		 学历事项 <spring:message code="hrm.recruitManage.Education_matters" />
	</h1>
	--><form id="essAddEducationInfo" method="post"
		action="/ess/empinfo/essAddEducationInfo"
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
				<input type="hidden" name="UPDATE_EDUC_NO" value="${EDUC_NO}" />
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
						<!-- 毕业学校 --><spring:message code="hr.viewPersonalInfo.title.SCHOOL" />
					</td>
					<td class="td_type">
						${educationInfoList.INSTITUTION_NAME}
					</td>
					<td class="td_title" width="20%">
						<!--学校地址--><spring:message code="hrm.empinfo.XUEXIAO_ADDRESS.Z" />
					</td>
					<td class="td_type">
						${educationInfoList.PLACE}
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						<!-- 学历 --><spring:message code="ess.empInfo.Education" />
					</td>
					<td class="td_type">
						${educationInfoList.DEGREE_CODE_NAME}
					</td>
					<td class="td_title" >
						<spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否-->
					</td>
					<td class="td_type" colspan="3">
						<input type="checkbox" disabled="disabled" <c:if test="${educationInfoList.FINAL_DEGREE_WHETHER eq 'Y'}"> checked="checked" </c:if> ></input>
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
                <tr>
                	<td class="td_title" >
						<!-- 学历 --><spring:message code="ess.empInfo.Education" />
					</td>
					<td class="td_type" colspan="3">
						${educationInfoList.DEGREE_CODE_NAME}
					</td>
                </tr>
                </c:if>
                <tr>
					<td class="td_title" width="20%">
						<!-- 入学日期 --><spring:message code="hrm.recruitManage.START_DATE" />
					</td>
					<td class="td_type">
							${educationInfoList.START_DATE }
					</td>
					<td class="td_title" width="20%">
						<!-- 毕业日期 --><spring:message code="hrm.recruitManage.END_DATE" />
					</td>
					<td class="td_type">
							${educationInfoList.END_DATE }
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
						<!-- 专业 --><spring:message code="hrm.recruitManage.SUBJECT" />
					</td>
					<td class="td_type">
						${educationInfoList.SUBJECT }
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!--副专业-->
					</td>
					<td class="td_type">
						${educationInfoList.SUBJECT_SECOND }
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
						<!--教育形式--><spring:message code="hrm.empinfo.JIAOYU_XINGSHI.Z" />
					</td>
					<td class="td_type">
						${educationInfoList.GRADUATION_TYPE_NAME }
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号-->
					</td>
					<td class="td_type">
						${educationInfoList.EDU_DEG_NUM }
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="20%">
						<!-- 毕业学校 --><spring:message code="hr.viewPersonalInfo.title.SCHOOL" />
					</td>
					<td class="td_type">
						${educationInfoList.INSTITUTION_NAME}
					</td>
					<td class="td_title" width="20%">
						<!-- 专业 --><spring:message code="hrm.recruitManage.SUBJECT" />
					</td>
					<td class="td_type">
						${educationInfoList.SUBJECT }
					</td>
				</tr>
				</c:if>
				<tr>
					<td  class="td_title">
						<!-- 备注 --><spring:message code="org.title.REMARK" />
					</td>
					 <td  class="td_type" colspan="3">
					${educationInfoList.REMARKS }
					</td>
				</tr>
				<tr>
					<td width="20%" class="td_title">
						<!-- 附件 --><spring:message code="org.title.enclosure" />
					</td>
					 <td width="80%" class="td_type" colspan="3">
					<%--	<input id="testFileInput_resume" type="file" name="file"
							uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${LoginUser.adminID}',
													formData:{ajax:1},
													queueID:'fileQueue_resume',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_resume,
													removeTimeout:1
												}" /> --%>
						<span id="fileN"></span>
				<c:forEach items="${fileList}" var="item" varStatus="i">
						  <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
				</c:forEach>
					<%-- 	<div id="fileQueue_resume" class="fileQueue"></div>
						<input type="hidden" id="fileUrl" name="FILE_URL"
							value="${resumeInfo.FILE_URL}" />
						<input type="hidden" id="fileName" name="FILE_NAME"
							value="${resumeInfo.FILE_NAME}" /> --%>

						<!--  <div class="buttonActive">-->
						<!--	<div class="buttonContent">-->
								<!--保存-->
						<!--		<button type="button"-->
						<!--			onclick="$('#testFileInput_resume').uploadify('upload', '*');return false;">-->
						<!--			上传-->
						<!--		</button>-->
						<!--	</div>-->
						<!--</div>-->
						<!--<div class="buttonActive">-->
						<!--	<div class="buttonContent">-->
								<!--提交-->
						<!--		<button type="button"-->
						<!--			onclick="$('#testFileInput_resume').uploadify('cancel', '*');return false;">-->
						<!--			取消-->
						<!--		</button>-->
						<!--	</div>-->
						<!--</div>-->
					</td>
				</tr>

			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<%-- <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent" onclick="buttons()">
							<button type="submit">
								删除
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div> --%>
		
				<input type="hidden" id="fileUrl" name="FILE_URL"
					value="${educationInfoList.FILE_URL}" />
				<input type="hidden" id="fileName" name="FILE_NAME"
					value="${educationInfoList.FILE_NAME}" />
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