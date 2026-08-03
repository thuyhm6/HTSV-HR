<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
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
<script>

 function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
} 


   function delectApplyInfo(){
    	 if(window.confirm('<spring:message code="ess.empInfo.sure_submit_application_deletion" />')){//确定提交删除申请吗？
    		   $("input[name=APPLY_TYPE]").val("3");
               $("#essAddQualificationInfo").submit();
              }
    	
    	
    } 
     
        function validateCallInfo(form, callback) {
	
		var $form = $("#deleteQualificationInfo");
		
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

 function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#essAddQualificationInfo");

	if (!$form.valid()) {
		return false;
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
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="20%">
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
					</td>
					<td class="td_type">
						<input type="text" readonly="readonly" name="QUAL_NAME" class="required" value="${qualificationInfo.QUAL_NAME}"   />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
					</td>
					<td class="td_type">
						<input type="text" readonly="readonly" name="QUAL_LEVEL"  class="textInput" value="${qualificationInfo.QUAL_LEVEL}"   />
						<input type="hidden" name="QUAL_GRADE" value=""   />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width="20%">
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
					</td>
					<td class="td_type">
						<input type="text" readonly="readonly" name="QUAL_NAME" class="required" value="${qualificationInfo.QUAL_NAME}"   />
					</td>
					<td class="td_title" width="20%"></td>
					<td class="td_type"></td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
					</td>
					<td class="td_type">
						<input type="text" readonly="readonly" name="QUAL_LEVEL"  class="textInput" value="${qualificationInfo.QUAL_LEVEL}"   />
					</td>
					<td class="td_title" width="20%">
						<spring:message code="hrm.empinfo.ZIGE_FENSHU.Z" /><!--资格分数-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_GRADE" value="${qualificationInfo.QUAL_GRADE}"   />
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width="20%">
					<!--获证日期--><spring:message code="ess.empInfo.certified_date" />
					</td>
					<td class="td_type">
						<input type="text" name="DATE_OBTAINED"  value="${qualificationInfo.DATE_OBTAINED}" readonly="readonly" "/>
					</td>
					<td class="td_title" width="20%">
					<!--有效日期--><spring:message code="ess.empInfo.effective_date" />
					</td>
					
					<td class="td_type">
						<input type="text" name="VALIDITY_DATE" class="Wdate required" value="${qualificationInfo.VALIDITY_DATE}" 
										 onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--证书编号--><spring:message code="ess.empInfo.certificate_number" />
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_CARD_NO" class="textInput" value="${qualificationInfo.QUAL_CARD_NO}"   />
					</td>
					<td class="td_title" width="20%">
					<spring:message code="hrm.empinfo.Issuing_authority" /><!--发证机关-->
					</td>
					<td class="td_type">
						<input type="text" name="QUAL_INSTITUTE" class="textInput" value="${qualificationInfo.QUAL_INSTITUTE}" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--备注 --><spring:message code="ess.empInfo.remarks" />
					</td>
					<td class="td_type" colspan="3"> 
						<textarea rows="4"  name="QUAL_REMARK" class="textInput" cols="70">${qualificationInfo.QUAL_REMARK }</textarea> 
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
						<c:if test="${LoginUser.cpnyId eq 'SPC_HZ' || LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_NJ'}"><strong style="color: red;">*</strong>
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
							value="${resumeInfo.FILE_URL}" />
						<input type="hidden" id="fileNosStr" name="FILENOSSTR"
							value="" />
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
								<!--提交--><spring:message code="public.title.submit" />
							</button>
						</div>
					</div>
				</li>
				<li>
<%-- 				<div class="button"><div class="buttonContent"><a type="button" class="close" onclick="javascript:delectApplyInfo()" ><span><spring:message code="public.title.delete"/> </span><!--删除 --> </a></div></div>
 --%>				 <div class="buttonActive" >
					<div class="buttonContent" >
						<button type="button" onclick="javascript:delectApplyInfo()">
							<spring:message code="public.title.delete" /><!--删除 -->
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




<!-- 删除申请 -->
<div style="visibility: hidden;">
<form id="deleteQualificationInfo" method="post"
		action="/ess/empinfo/essAddQualificationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				
					<input type="hidden" name="APPLY_TYPE" value="${3}" />

			
				<input type="hidden" name="UPDATE_QUAL_NO" value="${QUAL_NO}" />

				<tr>
					<td class="td_title" width="20%">
					<!--证书名称--><spring:message code="ess.empInfo.certificate_name" />
					</td>
					<td class="td_type">

						<input type="text" name="QUAL_CARD_NO" class="textInput" value=""   />
					</td>
<!-- 					<td class="td_title" width="20%"> -->
<!-- 						资格 -->
<!-- 					</td> -->


<!-- 					<td class="td_type"> -->
<%-- 				<input type="hidden" name="QUAL_NAME" value="${qualificationInfo.QUAL_NAME}" /> --%>

<%-- 					<ait:SelectSyCodeByCpnyID name="QUAL_NA" parentNo="13769" --%>
<%-- 							cnpyID="${defaultCpny}" selected="${qualificationInfo.QUAL_NAME}" disabled="disabled" /> --%>
<!-- 					</td> -->

				</tr>
				
				
				<tr>

					<td class="td_title" width="20%">
					<!--等级--><spring:message code="ess.infoApply.Grade" />
					</td>


					<td class="td_type">
				<input type="hidden" name="QUAL_LEVEL" value="${qualificationInfo.QUAL_LEVEL}" />

					<ait:SelectSyCodeByCpnyID name="QUAL_LE" parentNo="13769"
							cnpyID="${defaultCpny}" selected="${qualificationInfo.QUAL_LEVEL}" disabled="disabled"  />
					</td>

				</tr>

				<tr>

					<td class="td_title" width="20%">
					<!--获证日期--><spring:message code="ess.empInfo.certified_date" />
					</td>


					<td class="td_type">
					
									<input type="hidden" name="DATE_OBTAINED" value="${qualificationInfo.DATE_OBTAINED}" />
					
					
									<input type="text" name="DATE_OBTAIN" class="date required"
										readonly="true" format="yyyy-MM-dd" yearstart="-50"
										yearend="5" onClick="setdate(this);"  value="${qualificationInfo.DATE_OBTAINED}" disabled="disabled"/>
								
					</td>
				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--有效日期--><spring:message code="ess.empInfo.effective_date" />
					</td>
					
					<td class="td_type">
					
									<input type="text" name="VALIDITY_DATE" class="date required"
										readonly="true" format="yyyy-MM-dd" yearstart="-50"
										yearend="5" onClick="setdate(this);" value="${qualificationInfo.VALIDITY_DATE}" />
								
					</td>

				</tr>


				<tr>

					<td class="td_title" width="20%">
					<!--发行机关--><spring:message code="ess.empInfo.issuing_authority" />
					</td>


					<td class="td_type">

						<input type="text" name="QUAL_INSTITUTE" class="textInput" value="${qualificationInfo.QUAL_INSTITUTE}" />
					</td>

				</tr>
				<tr>
					<td class="td_title" width="20%">
					<!--证书编号--><spring:message code="ess.empInfo.certificate_number" />
					</td>


					<td class="td_type">

						<input type="text" name="QUAL_CARD_NO" class="textInput" value="${qualificationInfo.QUAL_CARD_NO}"   />
					</td>
				</tr>



			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		
	</form>

</div>