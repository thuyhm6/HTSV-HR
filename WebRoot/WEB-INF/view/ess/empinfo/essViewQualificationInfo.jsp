<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#essAddQualificationInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
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
	  if(files==""||files==null){
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

<div style="background-color: #fff;">
<!-- <h1>能力信息（资格事项）</h1> -->
	<form id="essAddQualificationInfo" method="post"
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
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_NAME" class="required" />
					</td>
					<td class="td_title" width="20%"></td>
					<td class="td_type"></td>
				</tr>	
				<tr>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_LEVEL" class="textInput" />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.ZIGE_FENSHU.Z" /><!--资格分数-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_GRADE"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="20%">
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_NAME" class="required" />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_LEVEL" class="textInput" />
						<input type="hidden" name="QUAL_GRADE"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!--获证日期--><spring:message code="ess.empInfo.certified_date" />
					</td>
					<td class="td_type">
									<input type="text" name="DATE_OBTAINED" class="Wdate required"
										 onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
					<td class="td_title" width="20%">
					<!--有效日期--><spring:message code="ess.empInfo.effective_date" />
					</td>
					<td class="td_type">
									<input type="text" name="VALIDITY_DATE" class="Wdate required"
										onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--证书编号--><spring:message code="ess.empInfo.certificate_number" />
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_CARD_NO" class="textInput" />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Issuing_authority" /><!--发证机关-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_INSTITUTE" class="textInput" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--备注--><spring:message code="ess.empInfo.remarks" />
					</td>
					<td class="td_type" colspan="3">
						<textarea rows="4"  name="QUAL_REMARK" class="textInput" cols="70"></textarea> 
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
				<tr>
					<td width="20%" class="td_title">
					<!--附件上传--><spring:message code="ess.empInfo.attachment_upload" />
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
							value="${resumeInfo.FILE_URL}" />
						<input type="hidden" id="fileName" name="FILE_NAME"
							value="${resumeInfo.FILE_NAME}" />

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
								<!--提交-->
								<button type="button"
									onclick="$('#testFileInput_resume').uploadify('cancel', '*');return false;">
									<!--取消--><spring:message code="ess.empInfo.cancel" />
								</button>
							</div>
						</div>
					</td>
				</tr>



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
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
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