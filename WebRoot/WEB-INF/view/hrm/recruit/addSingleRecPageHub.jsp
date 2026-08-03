<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(function(){
	var cpnyid="${defaultCpny}";
	var WORK_EXPERIENCE = "${recPageInfo.WORK_EXPERIENCE}";
	if(WORK_EXPERIENCE == null || WORK_EXPERIENCE == ""){
		$("#gongzuojingli",navTab.getCurrentPanel()).css("display","none");
		$("#jingli",navTab.getCurrentPanel()).css("display","none");
	}else{
		$("#gongzuojingli",navTab.getCurrentPanel()).css("display","");
		$("#jingli",navTab.getCurrentPanel()).css("display","");
	}
});

function getToDay(){//获得今天的时间
	   var now = new Date();
	   var nowYear = now.getFullYear();
	   var nowMonth = now.getMonth()+1;
	   var nowDate = now.getDate();
	   if(nowMonth<10){
		   nowMonth="0"+nowMonth;
	   }
	   if(nowDate<10){
		   nowDate="0"+nowDate;
	   }
	   return nowYear+""+nowMonth+""+nowDate;
	  }
//验证 部门,职群,职级,员工类型是否为空方法
function isNotEmpty(){
	var deptname=$('#viewSingleStartPoint_deptList').val();
	var emptypecode=$('#EMP_TYPE_CODE').val();
	var postfamily=$('#POST_FAMILY').val();
	var postgradeno=$('#GRADE_NO').val();
	if(deptname==''||deptname==null){
		alert("<spring:message code='pa.salary.canShu.bmbnwk' />");//部门不能为空!
		return false;
	}
	if(emptypecode==""||emptypecode==null){
		alert("<spring:message code='hr.alert.message.viewHire.checkNotNullEmpTypeCode' />");//员工类型不能为空!
		return false;
	}
	if(postfamily==""||postfamily==null){
		alert("<spring:message code='hr.hrm.empinfo.zhiqunbunengweikong.a' />");//职群不能为空!
		return false;
	}
	if(postgradeno==""||postgradeno==null){
		alert("<spring:message code='hr.hrm.empinfo.zhijibunengweikong.a' />");//职级不能为空!
		return false;
	}
	if(deptname!=""&&emptypecode!=""&&postfamily!=""&&postgradeno!=""){
		return true;
	}else{
		return false;
	}
	
}
function validateAddResumeInfoCallback(form,callback) {	
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='zxc.hr.viewEvaluate.title.SAVE_CONFIRM' />",//确定要保存吗?
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
		alertMsg.confirm("<spring:message code='zxc.hrm.transferOrder.CONFIRM_DELETE' />",//确认删除吗?
 		  	{okCall:function(){
		  	    $.ajax({
	 				type: form.method || 'POST',
	 				url:'/hrm/recruit/deleteRecPageInfo',
	 				data:$form.serializeArray(),
	 				dataType:"json",
	 				cache: false,
	 				success: callback || DWZ.ajaxDone,
	 				error: DWZ.ajaxError
	 			});
 	        }});  
	return false;
}
function workexperience(){
	if($("#WORK_EXPERIENCE",navTab.getCurrentPanel()).val() == null || $("#WORK_EXPERIENCE",navTab.getCurrentPanel()).val() == ""){
		$("#gongzuojingli",navTab.getCurrentPanel()).css("display","none");
		$("#jingli",navTab.getCurrentPanel()).css("display","none");
	}else{
		$("#gongzuojingli",navTab.getCurrentPanel()).css("display","");
		$("#jingli",navTab.getCurrentPanel()).css("display","");
	}
}
</script>
	<div>
		<form id="editRegPage" method="post" action="/hrm/recruit/addEditRecPageInfoHub" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }">
			<input type="hidden" name="currentCount" id="currentCount" value="${currentCount }">
			<input type="hidden" name="REC_EMPLOYEE_NO" id="REC_EMPLOYEE_NO" value="${recPageInfo.REC_EMPLOYEE_NO }"/>
			<input type="hidden" name="interCpnyID" id="interCpnyID" value="${LoginUser.cpnyId }"/>
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" >
							<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<tr>
							      <td class="td_title" width="15%" style="text-align:right">
							            <!-- 姓名 --> <spring:message code="empsubject.userNm" />
							      </td>
							      <td class="td_type" width="15%">
							         <input type="text" id="EMP_NAME" name="EMP_NAME" size="25" value="${recPageInfo.EMP_NAME }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							            <!-- 证件类型 --> <spring:message code="hr.viewCondSql.title.ZHENGJIANLEIXING" />   
							      </td>
							      <td class="td_type" width="15%">
							         <ait:SelectSyCodeByCpnyID name="CERT_TYPE_CODE" selected="${recPageInfo.CERT_TYPE_CODE }" parentNo="4575" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 证件号码 --> <spring:message code="hrm.addRecPage.IdCardNo.k" />   
							      </td>
							      <td class="td_type" width="15%">
							         <input type="text" id="CERT_NUMBER" name="CERT_NUMBER" size="25" value="${recPageInfo.CERT_NUMBER }"/>
							      </td>
							   </tr>
				               <tr>
				                  <td class="td_title" width="15%" style="text-align:right">
							      <!-- 电话 --> <spring:message code="empsubject.officePhone" />
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="EMP_TELPHONE" name="EMP_TELPHONE" size="25" value="${recPageInfo.EMP_TELPHONE }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							          <!-- 住址  --> <spring:message code="hrm.empinfo.FAM_ADDRESS" />   
							      </td>
							      <td class="td_type" width="15%" colspan=3>
							          <input type="text" id="EMP_ADDRESS" name="EMP_ADDRESS" size="93" value="${recPageInfo.EMP_ADDRESS }"/>
							      </td>
				               </tr>
							   <tr>
								  <td class="td_title" width="15%" style="text-align:right">
							           <!-- 性别 --> <spring:message code="empsubject.sexName" />
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="SEX_CODE" selected="${recPageInfo.SEX_CODE }" parentNo="1324" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 国籍 --><spring:message code="hr.viewCondSql.title.GUOJI" />  
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="NATIONALITY_CODE" selected="${recPageInfo.NATIONALITY_CODE }" parentNo="870" limit="all"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 出生日期 --> <spring:message code="main.home.message.chushengriqi" />
							      </td>
							      <td class="td_type" width="15%">
							           <input name="EMP_BIRTHDAY"  id="EMP_BIRTHDAY" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.EMP_BIRTHDAY }"/>
							      </td>
							   </tr>
							   <tr id="empoffice" style="">
							   	 <td class="td_title" width="15%" style="text-align:right">
							      	<spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态-->
							      </td>
								  <td class="td_type" width="25%">
							  		<ait:SelectSyCodeByCpnyID name="MARITAL_STATUS_CODE" id="MARITAL_STATUS_CODE"
										parentNo="1709" cnpyID="${defaultCpny}" selected="${recPageInfo.MARITAL_STATUS_CODE}" limit="all" />
								  </td>
							     <td class="td_title" width="15%" style="text-align:right">
							     <!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />  
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="FINAL_SCHOOL" name="FINAL_SCHOOL" size="25" value="${recPageInfo.FINAL_SCHOOL }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							     <!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />  
							      </td>
							      <td class="td_type" width="15%">
							          <ait:SelectSyCodeByCpnyID name="FINAL_EDU_CODE" selected="${recPageInfo.FINAL_EDU_CODE }" parentNo="13769" limit="all"/>
							      </td>
							   </tr>
							   <tr>
							   	  <td class="td_title" width="15%" style="text-align:right">
							     <!--  专业--><spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME" />  
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="SUBJECT" name="SUBJECT" size="25" value="${recPageInfo.SUBJECT }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!--  毕业日期--><spring:message code="hr.viewPersonalInfo.title.END_DATE" />  
							      </td>
							      <td class="td_type" width="15%">
							           <input name="FINAL_GRAD_DATE"  id="FINAL_GRAD_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.FINAL_GRAD_DATE }"/>
							      </td>
							       <td class="td_title" width="15%" style="text-align:right">
							   	   <!--  Email--><spring:message code="ess.personalinfo.title.email" />   
							      </td>
							      <td class="td_type" width="15%">
							           <input type="text" id="EMP_EMAIL" name="EMP_EMAIL" class="email"  size="33" value="${recPageInfo.EMP_EMAIL }"/>
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" />   
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="POST_TYPE_CODE" selected="${recPageInfo.POST_TYPE_CODE }" parentNo="90000339" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 工作经验--><spring:message code="hrm.addRecPage.workExperience.k" />   
							      </td>
							      <td class="td_type" width="15%">
							           <input type="text" id="WORK_EXPERIENCE" name="WORK_EXPERIENCE" value="${recPageInfo.WORK_EXPERIENCE }" size="4"/><!-- 年--><spring:message code="rp.report.title.year" />    
							      </td>
							      <td width="15%" class="td_title" style="text-align:right">
							      <spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 -->
							      </td>
							   	<td class="td_type" width="15%">
							   	<input type="text" id="DEPT_NAME" name="DEPT_NAME" size="25" value="${recPageInfo.DEPT_NAME }"/>
							</td>
								</tr>
							</c:if>
							<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
							      <td class="td_title" width="15%" style="text-align:right">
							            <!-- 姓名 --> <spring:message code="empsubject.userNm" />
							      </td>
							      <td class="td_type" width="15%">
							         <input class="required" type="text" id="EMP_NAME" name="EMP_NAME" size="25" value="${recPageInfo.EMP_NAME }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 身份证号 --> <spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" />   
							      </td>
							      <td class="td_type" width="15%">
							         <input class="required" type="text" id="CERT_NUMBER" name="CERT_NUMBER" size="25" value="${recPageInfo.CERT_NUMBER }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 签发日期 --> <spring:message code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" />   
							      </td>
							      <td class="td_type" width="15%">
							      	<input class="required" name="IDCARD_START_DATE"  id="IDCARD_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.IDCARD_START_DATE }"/>
							      </td>
							   </tr>
							   <tr>
							   	  <td class="td_title" width="15%" style="text-align:right">
							       <!-- 签发地点 --> <spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE" />   
							      </td>
							      <td class="td_type" width="15%">
							         <input class="required" type="text" id="IDCARD_ADDRESS" name="IDCARD_ADDRESS" size="25" value="${recPageInfo.IDCARD_ADDRESS }"/>
							      </td>
							       <td class="td_title" width="15%" style="text-align:right">
							           <!-- 出生日期 --> <spring:message code="main.home.message.chushengriqi" />
							      </td>
							      <td class="td_type" width="15%">
							           <input name="EMP_BIRTHDAY"  id="EMP_BIRTHDAY" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.EMP_BIRTHDAY }"/>
							      </td>
							       <td class="td_title" width="15%" style="text-align:right">
							           <!-- 出生地 --> <spring:message code="hrm.empinfo.ORIGIN.Z" />
							      </td>
							      <td class="td_type" width="15%">
							            <input class="required" type="text" id="EMP_ADDRESS" name="EMP_ADDRESS" size="25" value="${recPageInfo.EMP_ADDRESS }"/>
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 性别 --> <spring:message code="empsubject.sexName" />
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="SEX_CODE" selected="${recPageInfo.SEX_CODE }" parentNo="1324" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 国籍 --><spring:message code="hr.viewCondSql.title.GUOJI" />  
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="NATIONALITY_CODE" selected="${recPageInfo.NATIONALITY_CODE }" parentNo="870" limit="all"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      	<spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态-->
							      </td>
								  <td class="td_type" width="25%">
								  		<ait:SelectSyCodeByCpnyID name="MARITAL_STATUS_CODE" id="MARITAL_STATUS_CODE"
											parentNo="1709" cnpyID="${defaultCpny}" selected="${recPageInfo.MARITAL_STATUS_CODE}" limit="all" />
								  </td>
							   </tr>
							   <tr id="empoffice" style="">
							     <td class="td_title" width="15%" style="text-align:right">
							     <!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />  
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="FINAL_SCHOOL" name="FINAL_SCHOOL" size="25" value="${recPageInfo.FINAL_SCHOOL }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							     <!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />  
							      </td>
							      <td class="td_type" width="15%">
							          <ait:SelectSyCodeByCpnyID name="FINAL_EDU_CODE" selected="${recPageInfo.FINAL_EDU_CODE }" parentNo="13769" limit="all"/>
							      </td>
							       <td class="td_title" width="15%" style="text-align:right">
							     <!--  专业--><spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME" />  
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="SUBJECT" name="SUBJECT" size="25" value="${recPageInfo.SUBJECT }"/>
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!--  毕业日期--><spring:message code="hr.viewPersonalInfo.title.END_DATE" />  
							      </td>
							      <td class="td_type" width="15%">
							           <input name="FINAL_GRAD_DATE"  id="FINAL_GRAD_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.FINAL_GRAD_DATE }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 毕业成绩--><spring:message code="hrm.recruitManage.BIYECHENGJI.Z" />   
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="GRADUATION_ACHIEVEMENT" selected="${recPageInfo.GRADUATION_ACHIEVEMENT }" parentNo="14014324" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 平均分数--><spring:message code="hrm.recruitManage.PINGJUNFENSHU.Z" />   
							      </td>
							      <td class="td_type" width="15%">
							            <input type="text" id="AVERAGE_SCORE" name="AVERAGE_SCORE" value="${recPageInfo.AVERAGE_SCORE }" />    
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 外语能力--><spring:message code="hrm.empinfo.Foreign_language_ability" />   
							      </td>
							      <td class="td_type" width="15%">
							           <ait:SelectSyCodeByCpnyID name="LANGUAGE_ABILITY" selected="${recPageInfo.LANGUAGE_ABILITY }" parentNo="14015514" limit="all"/> 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 工作经验--><spring:message code="hrm.addRecPage.workExperience.k" />   
							      </td>
							      <td class="td_type" width="15%">
							           <input type="text" id="WORK_EXPERIENCE" name="WORK_EXPERIENCE" onBlur="workexperience()"value="${recPageInfo.WORK_EXPERIENCE }" size="4"/><!-- 年--><spring:message code="rp.report.title.year" />    
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="OLD_COMPANY" name="OLD_COMPANY" size="25" value="${recPageInfo.OLD_COMPANY }"/>
							      </td>
								</tr>
								<tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 电话 --> <spring:message code="empsubject.officePhone" />
							      </td>
							      <td class="td_type" width="15%">
							          <input type="text" id="EMP_TELPHONE" name="EMP_TELPHONE" size="25" value="${recPageInfo.EMP_TELPHONE }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 招聘时间--><spring:message code="hrm.addRecPage.interviewPeriod.k" />  
							      </td>
							      <td class="td_type" width="15%">
							           <input name="INTERVIEW_PERIOD"  id="INTERVIEW_PERIOD" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageInfo.INTERVIEW_PERIOD }"/>
							      </td>
							      <td class="td_title" width="15%" style="text-align:right"></td>
							      <td class="td_type" width="15%"></td>
								</tr>
							</c:if>
						    </table>
							<%--<c :if test="${LoginUser.cpnyId eq 'HAE'}">
								<div id="gongzuojingli" style="font: 10px; float: left; height: 20px; line-height: 20px;">
	                               <!-- 工作经历 --><spring:message code="hr.viewWorkInfo.title.WORK_EXPERIENCE" />
	                               <input type="hidden" name="REC_WORK_EXPER_NO" id="REC_WORK_EXPER_NO" value="${recPageworkInfo.REC_WORK_EXPER_NO }"/>
	                            </div>
								<table id="jingli" class="user_table" width="100%">
									<tr>
										<td class="td_title" width="153" style="text-align:right">
											<!-- 公司名 --><spring:message code="ess.empInfo.corporate_name" />
										</td>
										<td class="td_type" width="20%">
											 <input type="text" id="CPNY_NAME" name="CPNY_NAME" size="25" value="${recPageworkInfo.CPNY_NAME }"/>
										</td>
										<td class="td_title" width="13%" style="text-align:right">
											<!-- 开始日 --><spring:message code="hrm.recruitManage.START_DATE1" />
										</td>
										<td class="td_type" width="20%">
											<input name="START_DATE"  id="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageworkInfo.START_DATE }"/>
										</td>
										<td class="td_title" width="13%" style="text-align:right">
											<!-- 结束日 --><spring:message code="hrm.recruitManage.END_DATE1" />
										</td>
										<td class="td_type" width="20%">
											<input name="END_DATE"  id="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recPageworkInfo.END_DATE }"/>
										</td>
									</tr>
									<tr>
										<td class="td_title" width="153" style="text-align:right">
											<!-- 岗位 --><spring:message code="rp.report.title.dutyinfo" />
										</td>
										<td class="td_type" width="20%">
											<input type="text" id="DUTY" name="DUTY" size="25" value="${recPageworkInfo.DUTY }"/>
										</td>
										<td class="td_title" width="13%" style="text-align:right">
											<!-- 主要业务 --><spring:message code="evs.affirm.main_business.e" />
										</td>
										<td class="td_type" width="20%">
											<input type="text" id="MIAN_BUSINESS" name="MIAN_BUSINESS" size="25" value="${recPageworkInfo.MIAN_BUSINESS }"/>
										</td>
										<td class="td_title" width="13%" style="text-align:right">
											<!-- 毕业成绩 --><spring:message code="hrm.recruitManage.BIYECHENGJI.Z" />
										</td>
										<td class="td_type" width="20%">
											<ait:SelectSyCodeByCpnyID name="GRADUATION_ACHIEVEMENT_WORK" selected="${recPageworkInfo.GRADUATION_ACHIEVEMENT }" parentNo="14014324" limit="all"/> 
							      		</td>
									</tr>
									<tr>
										<td class="td_title" width="153" style="text-align:right">
											<!-- 外语能力 --><spring:message code="hrm.empinfo.Foreign_language_ability" />
										</td>
										<td class="td_type" width="20%">
											<ait:SelectSyCodeByCpnyID name="LANGUAGE_ABILITY_WORK" selected="${recPageworkInfo.LANGUAGE_ABILITY }" parentNo="14015514" limit="all"/> 
							      		</td>
										<td class="td_title" width="13%" style="text-align:right"></td>
										<td class="td_type" width="20%"></td>
										<td class="td_title" width="13%" style="text-align:right"></td>
										<td class="td_type" width="20%"></td>
									</tr>
								</table>
							</c:if> --%>
							 <div>
								<div style="font:10px;float:left;height:20px;line-height:20px;">
									<a class="w_button" href="#" onclick="uploadAttDialogInsert();">
										<span><!--附加文件--><spring:message code="hrm.recruitManage.ATTACHED_FILE" /></span></a>
									<a class="w_button" href="#" onclick="deleteAttListInsert_new();">
									<span><!--删除--><spring:message code="button.delete" /></span></a>
								</div>
								<table id="fileTable" class="list" width="100%">
									<thead>
										<tr>
											<th width="10%">V</th>
											<th width="90%"><!--附件--><spring:message code="pa.ins.alert.message.exportdata.fileAdd" /></th>
										</tr>
									</thead>
									<tbody>
									    <c:if test="${recPageInfo.FILE_PATH ne null && recPageInfo.FILE_PATH ne ''}">
											<td class="td_center">
											<input type="checkbox" name="FILE_NO">
											<input type="hidden" name="fileUrl" value="${recPageInfo.FILE_PATH }">
											<input type="hidden" name="fileName" value="${recPageInfo.FILE_NAME }">
											</td>
											<td>
											<a href="/ess/infoApplyLeave/downloadFile?fileName=${recPageInfo.FILE_PATH }&file=${recPageInfo.FILE_NAME }">
											   ${recPageInfo.FILE_NAME }
											</a>
											</td>
										</c:if>
									</tbody>
								</table>
						     </div>
							<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
								<tr>
								    <c:if test="${not empty recPageInfo.UPDATED_BY}">
								    <td class="td_title" width="5%"><!-- 变更者 --> <spring:message code="hrm.empinfo.UPDATED_BY" /></td>
										<td class="td_type"  width="25%" >
										${recPageInfo.UPDATED_NAME }(${recPageInfo.UPDATED_BY })
										</td>
										<td class="td_title" width="5%"><!-- 变更时间 --> <spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
										<td class="td_type"  width="25%" >
										${recPageInfo.UPDATE_DATE }
										</td>
									</c:if>
									<c:if test="${empty recPageInfo.UPDATED_BY}">
										<td class="td_title" width="5%"><!-- 变更者 --> <spring:message code="hrm.empinfo.UPDATED_BY" /></td>
										<td class="td_type"  width="25%" >
										${recPageInfo.CREATED_NAME }(${recPageInfo.CREATED_BY })
										</td>
										<td class="td_title" width="5%"><!-- 变更时间 --> <spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
										<td class="td_type"  width="25%" >
										${recPageInfo.CREATE_DATE }
										</td>
									</c:if>
								</tr>	
							</table>	
						</td>
					</tr>
				</table>	
			</div>
	  	</form>	
	</div>
