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
		var punishno = $('#PUNISH_NO').val();
		if (punishno == '') {//增加
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
		} else {//修改
			alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>", {//确定要保存吗？
				okCall : function() {
					$.ajax({
						type : form.method || 'POST',
						url : $form.attr("action"),
						data : $form.serializeArray(),
						dataType : "json",
						cache : false,
						success : function(json) {
							DWZ.ajaxDone(json);
							if (json.statusCode == DWZ.statusCode.ok) {
								navTab.reload(json.forwardUrl);
							}
						},
						error : DWZ.ajaxError
					});
				}
			});
		}
		return false;
	}

	function validateDeleteResumeInfoCallback(form, callback) {
		var $form = $("#" + form);
		alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>", {//确定要删除吗？
			okCall : function() {
				$.ajax({
					type : form.method || 'POST',
					url : '/hrm/empinfo/deletePunishment',
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


	//附件上传
	function uploadifySuccess_Punognitionfile(file, data, response) {
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
	function pcutDateSize(a) {
		var startdate = $('#PAYCUT_START_DATE').val();
		var enddate = $('#PAYCUT_END_DATE').val();
		if (startdate != '' && enddate != '') {
			sdate = parseInt(startdate.replace('-', '').replace('-', ''));
			edate = parseInt(enddate.replace('-', '').replace('-', ''));
			if (sdate > edate) {
				alert("<spring:message code="ar.attendanceView.viewNoSwipingCard.beginTimeDontendTime"/>");//开始时间不能大于结束时间!
				if (a == '0') {
					$('#PAYCUT_START_DATE').attr('value', '');
				} else if (a == '1') {
					$('#PAYCUT_END_DATE').attr('value', '');
				}
			}
		}
	}

	function finalinquiry() {
		var check = $('#final').prop('checked');
		if (check == true) {
			$('#PERSONNEL_CARD_INQUIRY').attr('value', 'Y');
		} else {
			$('#PERSONNEL_CARD_INQUIRY').attr('value', 'N');
		}
	}
</script>
<div>
	<form id="editPunishment" method="post"
		action="/hrm/empinfo/editPunishment"
		class="pageForm required-validate"
		onsubmit="return validateAddResumeInfoCallback(this,navTabAjaxDoneP);">
		<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
		<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID"
			VALUE="${PERSON_ID}"> <input type="hidden" name="PUNISH_NO"
			id="PUNISH_NO" value="${personInfo.PUNISH_NO}">
		<div>
			<table class="user_table" width="100%" border="1" cellpadding="2"
				cellspacing="1">
				<tr>
					<td>
						<table class="user_table" width="100%">
							<tr>
								<td>
									<table class="user_table" width="100%">
										<tr>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.punishment_code" /><!-- 惩罚代码--></td>
											<td width="35%" class="td_type" colspan='3' id="punishcode">
												<ait:SelectSyCodeByCpnyID name="PUNISH_CODE"
													id="PUNISH_CODE" parentNo="13997"
													cnpyID="${defaultCpny}" 
													selected="${personInfo.PUNISH_CODE}" limit="all" />
											</td>
										</tr>
										<tr>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.punishment_day" /><!-- 惩罚日--></td>
											<td width="35%" class="td_type" id="punishdate"><input
												name="PUNISH_DATE" id="PUNISH_DATE" type="text"
												class="required"
												onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
												value="${personInfo.PUNISH_DATE}" /></td>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.relieve_day" /><!-- 解除日--></td>
											<td width="35%" class="td_type"><input
												name="RELEASE_DATE" id="RELEASE_DATE" type="text"
												onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
												value="${personInfo.RELEASE_DATE}" /></td>
										</tr>
										<tr>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.punishment_organ_name" /><!-- 惩罚机关名--></td>
											<td width="35%" class="td_type">
												<input type="text" id="PUNISH_DEPARTMENT" name="PUNISH_DEPARTMENT" value="${personInfo.PUNISH_DEPARTMENT }" /></input>
											</td>	
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.Personnel_card_inquiry" /><!-- 人事卡查询与否--></td>
											<td width="35%" class="td_type">	
												<c:if test="${personInfo.PERSONNEL_CARD_INQUIRY=='Y' }">
													<input type="checkbox" checked="final" id="final" onclick="finalinquiry()">
													<input type="hidden" name='PERSONNEL_CARD_INQUIRY' id='PERSONNEL_CARD_INQUIRY' value="Y">
												</c:if> 
												<c:if test="${personInfo.PERSONNEL_CARD_INQUIRY !='Y' }">
													<input type="checkbox" id="final" onclick="finalinquiry()">
													<input type="hidden" id="PERSONNEL_CARD_INQUIRY" name="PERSONNEL_CARD_INQUIRY" value="N">
												</c:if>
			                            	</td>
										</tr>
										<tr>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.pay_cut_start_date" /><!-- 减薪开始日--></td>
											<td width="35%" class="td_type"><input
												name="PAYCUT_START_DATE" id="PAYCUT_START_DATE" type="text"
												onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
												value="${personInfo.PAYCUT_START_DATE}" /></td>
											<td width="15%" class="td_title">
												<spring:message code="hrm.empinfo.pay_cut_end_date" /><!-- 减薪结束日--></td>
											<td width="35%" class="td_type"><input
												name="PAYCUT_END_DATE" id="PAYCUT_END_DATE" type="text"
												onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
												value="${personInfo.PAYCUT_END_DATE}" /></td>
										</tr>
										<tr>

											<td width="15%" class="td_title">
												<c:if test="${LoginUser.cpnyId eq 'HTSV' }">
													<spring:message code="hrm.empinfo.DISCIPLINE_REASON" /><!-- 惩罚原因--></td>
												</c:if>
												<c:if test="${LoginUser.cpnyId eq 'HAE' }">
													<spring:message code="ar.viewarcardrecord.title.beizhu" /><!-- 备注--></td>
												</c:if>
											<td width="35%" class="td_type" id="punishreason" colspan="3"><textarea
													name="PUNISH_REASON" id="PUNISH_REASON"
													style="width: 500px; height: 80px">${personInfo.PUNISH_REASON}</textarea>
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
									<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!-- 变更者--></td>
									<td class="td_type" width="25%">${personInfo.UPDATED_BY
										}&nbsp;&nbsp;${personInfo.UPDATED_IP }</td>
									<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间--></td>
									<td class="td_type" width="25%">${personInfo.UPDATE_DATE
										}</td>
								</c:if>
								<c:if test="${empty personInfo.UPDATED_BY}">
									<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!-- 变更者--></td>
									<td class="td_type" width="25%">${personInfo.CREATED_BY
										}&nbsp;&nbsp;${personInfo.CREATED_IP }</td>
									<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间--></td>
									<td class="td_type" width="25%">${personInfo.CREATE_DATE
										}</td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<c:if test="${punish_no=='0' }">
			<div>
				<div
					style="font: 10px; float: left; height: 20px; line-height: 20px;">
					<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
					<span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件--></span></a>
					<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
					<span><spring:message code="button.delete" /><!-- 删除--></span></a>
				</div>
				<table id="fileTable" class="list" width="100%">
					<thead>
						<tr>
							<th width="10%">V</th>
							<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!-- 附件--></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${punish_no!='0' }">
			<div>
				<div
					style="font: 10px; float: left; height: 20px; line-height: 20px;">
					<a class="w_button" href="#"
						onclick="uploadAttDialog_new('viewResumeList_viewPunishmentunit','/hrm/empinfo/viewSinglePunishment?PERSON_ID=${PERSON_ID}$PUNISH_NO=${punish_no }','${punish_no}','hrPunishment','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件--></span></a>
					<a class="w_button" href="#"
						onclick="deleteAttList_new('viewResumeList_viewPunishmentunit','/hrm/empinfo/viewSinglePunishment?PERSON_ID=${PERSON_ID}&PUNISH_NO=${punish_no }',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
						<span><spring:message code="button.delete" /><!-- 删除--></span></a>
				</div>
				<table class="table" width="100%" layoutH="670">
					<thead>
						<tr>
							<th width="10%">V</th>
							<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!-- 附件--></th>
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
		</c:if>
	</form>
</div>
