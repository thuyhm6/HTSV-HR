<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#essAddEducationInfo");
		
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
function finaldegree() {
	var check = $('#final').prop('checked');
	if (check == true) {
		$('#FINAL_DEGREE_WHETHER').attr('value', 'Y');
	} else {
		$('#FINAL_DEGREE_WHETHER').attr('value', 'N');
	}
}

</script>

<div style="background-color: #fff;">
<!-- <h1>能力信息（学历事项）</h1> -->
	<form id="essAddEducationInfo" method="post"
		action="/ess/empinfo/essAddEducationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56"><!-- class="pageFormContent" -->
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
							cnpyID="${defaultCpny}"  />
						<input type="hidden" name="PLACE" />
						<input type="hidden" name='FINAL_DEGREE_WHETHER' />
						<input type="hidden" name="SUBJECT_SECOND" />
						<input type="hidden" name="GRADUATION_TYPE" />
						<input type="hidden" name="EDU_DEG_NUM" />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME"  class="required" />
					</td>
					<td class="td_title" width="20%">
					<!--学校地址--><spring:message code="hrm.empinfo.XUEXIAO_ADDRESS.Z" />
					</td>
					<td class="td_type">
						<input type="text" name="PLACE" class="textInput" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--学历--><spring:message code="ess.empInfo.Education" />
					</td>
					<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="DEGREE_CODE" parentNo="13769"
							cnpyID="${defaultCpny}"  />
					</td>
					<td width="15%" class="td_title">
						<spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否--></td>
					<td width="35%" class="td_type">
						<input type="checkbox" id="final" onclick="finaldegree()">
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
										 onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"/>			
					</td>
					<td class="td_title" width="20%">
						<!--毕业日期--><spring:message code="ess.empInfo.graduation_date" />
					</td>
					<td class="td_type">
						<input type="text" name="END_DATE" class="Wdate required"
										onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
					<!--专业--><spring:message code="ess.empInfo.major" />
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT" class="textInput" />
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!--副专业-->
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT_SECOND"  />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--教育形式--><spring:message code="hrm.empinfo.JIAOYU_XINGSHI.Z" />
					</td>
					<td class="td_type">
					<ait:SelectSyCodeByCpnyID name="GRADUATION_TYPE" parentNo="13704"
							cnpyID="${defaultCpny}"  />
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号-->
					</td>
					<td class="td_type">
						<input type="text" name="EDU_DEG_NUM"  />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME"  class="required" />
					</td>
					<td class="td_title" width="20%">
					<!--专业--><spring:message code="ess.empInfo.major" />
					</td>
					<td class="td_type">
						<input type="text" name="SUBJECT" class="textInput" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!--备注 --><spring:message code="ess.empInfo.remarks" />
					</td>
					<td class="td_type" colspan="3"> 
						<textarea rows="4"  name="REMARKS" class="textInput" cols="70"></textarea> 
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
												}"
											/>
										  
										  <div id="fileQueue_resume" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="FILE_URL" value=""/>
										  <input type="hidden" id="fileName" name="FILE_NAME" value=""/>
										  
											 <div class="buttonActive">
												<div class="buttonContent"><!--保存-->
												<button type="button" onclick="$('#testFileInput_resume').uploadify('upload', '*');return false;">
												<!--上传--><spring:message code="ess.empInfo.upload" />
											 		</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_resume').uploadify('cancel', '*');return false;">
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