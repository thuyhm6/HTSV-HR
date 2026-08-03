<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addPaInputItemDataInfo(form, callback) {
	var $form = $("#addPaInputItemDataInfoForm");
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

function aaaaaa(){
		$.ajaxSettings.global = false;
}

function uploadifySuccess_Jiangcheng(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae_archives").html();
  var fileUrl = $("#fileUrl_archives").val();
  var fileName = $("#fileName_archives").val();
  var fileResult = data.split(";");
  //第一个文件
  if(files=="" || files==null){
    files = fileResult[0];
    fileName = fileResult[0];
    fileUrl = fileResult[1];
  }else{
    files+=";"+fileResult[0];
    fileName+=";"+fileResult[0];
    fileUrl+=";"+fileResult[1];
  }
  $("#fileNmae_archives").html(files);
  $("#fileUrl_archives").val(fileUrl);
  $("#fileName_archives").val(fileName);
}
</script>
<div class="pageContent">
	<form id="addPaInputItemDataInfoForm" name="addPaInputItemDataInfoForm" method="post"
		action="/hrm/empinfo/addOrUpdateBadArchivesInfo" class="pageForm required-validate"
		onsubmit="return addPaInputItemDataInfo(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="77">
				<dl>
					<dt><!--社号-->
						<spring:message code="public.title.empId"/>
					</dt>
					<dd>
						<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" />
						<input id="empId" name="dwz.person.empId" value="" type="text" lookupGroup="person" chass="textInput required" readonly="true" />
						<a class="btnLook"
							href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=hr&pageNum=1"
							lookupGroup="person">
						</a>
					</dd>
				</dl>
				<dl>
				<dt><!--发生日期--><spring:message code="hr.viewBadArchives.title.HAPPEN_DATE" />:</dt>
				<dd>
					<input type="text" id="HAPPEN_DATE0" name="HAPPEN_DATE0"
						class="required" readonly="true" format="yyyy-MM-dd"
						yearstart="-50" yearend="5" onClick="setdate(this);"
						value="" />
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a></td>
				</dd>
			</dl>
			<dl>
				<dt><!--类型--><spring:message code="hr.viewBadArchives.title.ARCHIVES_TYPE" />:</dt>
				<dd>
					<select name="ARCHIVES_TYPE0" id="ARCHIVES_TYPE0">
						<c:forEach items="${codeList}" var="recsource">
							<option value="${recsource.CODE_NO}">
								${recsource.CODENAME}
							</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!--详细描述--><spring:message code="hr.viewBadArchives.title.DETAIL_DESCRIPT" />:</dt>
				<dd>
					<input type="text" name="DETAIL_DESCRIPT0" class="textInput"
						maxlength="160" size="60" value="" />
				</dd>
			</dl>
			<dl>
				<dt><!--备注--><spring:message code="hr.viewBadArchives.title.REMARK" />:</dt>
				<dd>
					<input type="text" name="REMARK0" class="textInput"	value="" />
					<input type="hidden" name="count" class="textInput" value="1" />
				</dd>
			</dl>
				<dl>
			<dt width="20%"  class="td_title">
										附件上传
									</dt>
				 <dd width="30%" class="td_type">
				 <input id="testFileInput_Jiangcheng"
					type="file" name="file"
					 uploaderOption="{
						swf:'/resources/js/uploadify/scripts/uploadify.swf',
					    uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${personInfo.PERSON_ID}',
						formData:{ajax:1},
						queueID:'fileQueue_Jiangcheng',
						buttonText:'请选择',
						height:25,
						width:50,
						auto:false,
						onUploadSuccess:uploadifySuccess_Jiangcheng,
						removeTimeout:1
					    }" />
					<span id="fileNmae_archives"></span>
					<div id="fileQueue_Jiangcheng" class="fileQueue"></div> 
					<input type="hidden" id="fileUrl_archives" name="fileUrl" value="" /> 
					<input type="hidden" id="fileName_archives" name="fileName" value="" />

					<div class="buttonActive">
						<div class="buttonContent">
							<!--保存-->
							<button type="button"
								onclick="$('#testFileInput_Jiangcheng').uploadify('upload', '*');return false;">
								上传</button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--提交-->
							<button type="button"
								onclick="$('#testFileInput_Jiangcheng').uploadify('cancel', '*');return false;">
								取消</button>
						</div>
					</div></dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!-- 提交 -->
							<button type="submit">
								<spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!--取消-->
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>