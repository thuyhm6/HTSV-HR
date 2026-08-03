<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(document).ready(function(){
	codeRelation('${resumeInfo.EVS_CYCLE}','EVS_MONTH','${resumeInfo.EVS_MONTH}');
});
function validateAddEvsResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
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

function validateDeleteEvsResumeInfoCallback(form,callback) {
	var ACTIVITY = $("#ACTIVITY",navTab.getCurrentPanel()).val();
	if(ACTIVITY == 4){
		alertMsg.warn("<spring:message code='sys.viewfileRoomList.PINGJIAJIESHUBUNENGSHANCHU.b'/>");//评价已经结束，不能删除
		return false;
	}
	var $form = $("#" + form,navTab.getCurrentPanel());	
	alertMsg.confirm("<spring:message code='js.upload.msg.confirmToDelete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/evs/manage/deleteResumeInfo',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
function pingjiayue (value,num) {
	codeRelation(value,'EVS_MONTH','${resumeInfo.EVS_MONTH}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
</script>
		<form id="viewAddEvsResumeInfo_${evsType}" method="post" action="/evs/manage/addResumeInfo" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名-->
									</td>
									<td width="85%" class="td_type">
										<input type="hidden" id="evsType" name="evsType" value="${evsType}"/>
										<input type="hidden" id="EVS_TYPE" name="EVS_TYPE" value="${evsType}"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${resumeInfo.SEQ}"/>
										<input type="hidden" id="ACTIVITY" name="ACTIVITY" value="${resumeInfo.ACTIVITY}"/>
										<input type="text" id="RESUME_NAME" name="RESUME_NAME" value="${resumeInfo.RESUME_NAME}" size="112" class="required"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="ar.viewItem.title.shuoming"/><!--说明-->
									</td>
									<td width="85%" class="td_type">
										<textarea name="REMARK"  style="width:650px;height:80px">${resumeInfo.REMARK}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewAddResumeInfo.ZHOUQI.a"/><!--周期-->
									</td>
									<td width="85%" class="td_type">
									   	<%-- <ait:SelectSyCodeByCpnyID name="EVS_CYCLE" selected="${resumeInfo.EVS_CYCLE}" parentNo="14015038" cnpyID="${LoginUser.cpnyId}" onChangeName="codeRelation(this.value,'EVS_MONTH','${resumeInfo.EVS_MONTH}');" limit="all"/> --%>
									   	<ait:SelectSyCodeByCpnyID name="EVS_CYCLE" selected="${resumeInfo.EVS_CYCLE}" parentNo="14015038" cnpyID="${LoginUser.cpnyId}" onChangeName="pingjiayue(this.value,'1');" limit="all"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.Evaluation_year"/><!--评价年度-->
									</td>
									<td width="85%" class="td_type">
										<input type="text" name="EVS_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy'})" value="${resumeInfo.EVS_YEAR }"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewResumeList.PINGJIAYUE.a"/><!--评价月-->
									</td>
									<td width="85%" class="td_type">
										<select name="EVS_MONTH" id="EVS_MONTH">
										</select>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewAddResumeInfo.BIAOZHUNRIQI.a"/><!--标准日期-->
									</td>
									<td width="85%" class="td_type">
										<input type="text" name="STANDARD_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${resumeInfo.STANDARD_DATE }"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hr.viewEvaluate.title.EV_PERIOD"/><!--评价期间-->
									</td>
									<td width="85%" class="td_type">
										<input type="text" name="EVS_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${resumeInfo.EVS_START_DATE }"/>~
										<input type="text" name="EVS_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${resumeInfo.EVS_END_DATE }"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewResumeList.GONGZUOJINGXINGZHUANGTAI.a"/><!--工作进行状态-->
									</td>
									<td width="85%" class="td_type">
										<c:if test="${resumeInfo.ACTIVITY eq 2}"><spring:message code="evs.viewResumeList.KAOHEZHUNBEIJIEDUAN.a"/><!--考核准备阶段--></c:if>
										<c:if test="${resumeInfo.ACTIVITY eq 3}"><spring:message code="evs.viewResumeList.KAOHEJINXINGJIEDUAN.a"/><!--考核进行阶段--></c:if>
										<c:if test="${resumeInfo.ACTIVITY eq 4}"><spring:message code="evs.viewResumeList.KAOHEWANCHENGJIEDUAN.a"/><!--考核完成阶段--></c:if>
										<c:if test="${resumeInfo.ACTIVITY eq 1}"><spring:message code="evs.viewResumeList.KAOHEWANCHENGJIEDUAN.a"/><!--考核完成阶段--></c:if>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewAddResumeInfo.PINGJIRENJIEDUANSHU.a"/><!--评级人阶段数-->
									</td>
									<td width="85%" class="td_type">
										<c:if test="${empty resumeInfo.EVS_LEVEL}">
									   		<ait:SelectSyCodeByCpnyID name="EVS_LEVEL" selected="14015062" parentNo="14015060" cnpyID="${LoginUser.cpnyId}"/>
									   	</c:if>
										<c:if test='${not empty resumeInfo.EVS_LEVEL}'>
									   		<ait:SelectSyCodeByCpnyID name="EVS_LEVEL" selected="${resumeInfo.EVS_LEVEL}" parentNo="14015060" cnpyID="${LoginUser.cpnyId}"/>
									   	</c:if>
									</td>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'SST' and evsType eq 'performance'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewAddResumeInfo.MUBIAOPIZHUNJIEDUAN.a"/><!--目标批准阶段-->
									</td>
									<td width="85%" class="td_type">
										<c:if test="${empty resumeInfo.TARGET_LEVEL}">
									   		<ait:SelectSyCodeByCpnyID name="TARGET_LEVEL" selected="14015062" parentNo="14015060" cnpyID="${LoginUser.cpnyId}"/>
									   	</c:if>
										<c:if test='${not empty resumeInfo.TARGET_LEVEL}'>
									   		<ait:SelectSyCodeByCpnyID name="TARGET_LEVEL" selected="${resumeInfo.TARGET_LEVEL}" parentNo="14015060" cnpyID="${LoginUser.cpnyId}"/>
									   	</c:if>
									</td>
								</tr>
								</c:if>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewAddResumeInfo.FUZHIDUIXIANG.a"/><!--复制对象-->
									</td>
									<td width="85%" class="td_type">
										<select name="COPY_OBJECT">
											<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
											<c:forEach items="${resumeCodeList}" var="item" varStatus="i">
												<option value="${item.SEQ }" <c:if test="${item.SEQ eq resumeInfo.COPY_OBJECT}">selected="selected"</c:if >>${item.RESUME_NAME }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>	
						</td>
					</tr>
				</table>
			</div>
			
			<c:if test="${empty resumeInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件--></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="org.title.enclosure"/><!--附件--></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</c:if>
			<c:if test="${not empty resumeInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewEvsResumeList_${evsType}_unit','/evs/manage/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}','${resumeInfo.SEQ}','EVS_RESUME','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件--></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewEvsResumeList_${evsType}_unit','/evs/manage/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><spring:message code="button.delete"/><!--删除--></span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="org.title.enclosure"/><!--附件--></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${resumeInfo.fileList}" var="item" varStatus="i">
								<tr>
									<td class='td_center'><input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/></td>
									<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
	  	</form>	
