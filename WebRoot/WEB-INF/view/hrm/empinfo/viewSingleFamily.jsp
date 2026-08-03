<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//$(function(){
//   var time="${personInfo.AGE}";alert(time);
//	var year=parseInt(time)/365;
//	var day=parseInt(time)%365;
//	    year=Math.floor(year);
//	if(year<1){
//		year=0;
//	}
//	var month=day/30;
//	month=Math.floor(month);
//	if(month<1){
//		month=0;
//	}
//	var totaltime=year+"<spring:message code='hrm.empinfo.AGE1' />";
//	$('#ageYearMonth').html(totaltime);
//});
function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='hrm.empinfo.SAVE_CONFIRM' />",//确定要保存吗?
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
  				url:'/hrm/empinfo/deleteHrFamily',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function changeMainOffice(index){
	var check=$('#changeMain').prop('checked');
	 if(check==true){
		 document.getElementById("cMain_"+index).checked=true;
		 $('#MAIN_LIAISON_OFFICE').attr('value','Y');
	}else{
		 document.getElementById("cMain_"+index).checked=false;
		 $('#MAIN_LIAISON_OFFICE').attr('value','N');
	} 
}

function copyAddress(){
	var personid1="${personInfo.PERSON_ID}";
	var personid2="${PERSON_ID}";
	var PERSON_ID;
	if(personid1!=""){
		PERSON_ID=personid1;
	}else{
		if(personid2!=""){
			PERSON_ID=personid2;
		}
	}
	
	
	$.ajax({
		type:'post',
		dateType:'text',
		url:'/hrm/empinfo/copyAddress',
		data:{PERSON_ID:PERSON_ID},
	    success:function(data){
	    	data=data.addressContent;
		  if(data!=""){
			 $('#FAM_ADDRESS').attr('value',data);
		  }
	   }
	});
}

function changeoffice(){
	var check=$('#office').prop('checked');
	if(check==true){
		$('#EMERGENCY_LIAISON_OFFICE').attr('value','Y');
	}else{
		$('#EMERGENCY_LIAISON_OFFICE').attr('value','N');
	}
}

function borndate(){
	var DOB = $("#FAM_BORNDATE").val();
	if($("#FAM_BORNDATE").val() == null || $("#FAM_BORNDATE").val() == ""){
		$("#AGE").val("");
	}else{
		var nowdate = new Date();
		var nowyear = nowdate.getFullYear();
		var birth = parseInt(DOB.substring(6,10));
		$("#AGE").val(nowyear - birth);
	}
	
}
</script>
<div class="pageContent" style="height:613px;">
	<div>
		<form id="editHrFamily" method="post" action="/hrm/empinfo/editHrFamily" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID" VALUE="${PERSON_ID}" >
        <input TYPE="hidden" NAME="isEssSystem" VALUE="${isEssSystem}" >
        <input type="hidden" name="FAMILY_NO" id="FAMILY_NO" value="${personInfo.FAMILY_NO}">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
							<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<tr>
									<td class="td_title" width="4%"><!-- 越南语姓名 -->
									<spring:message code="hrm.recruitManage.YUENAN_NAME.Z" /></td>
									<td class="td_type"  width="25%">
									 <input type="text" class="required" id="FAM_NAME" name="FAM_NAME" value="${personInfo.FAM_NAME}">
									</td>
									<td class="td_title" width="4%"><!-- 英文姓名 -->
									<spring:message code="hrm.empinfo.ENGLISH_NAME" /></td>
									<td class="td_type"  width="25%">
									<input type="text" id="FAM_ENGLISH_NAME" name="FAM_ENGLISH_NAME" value="${personInfo.FAM_ENGLISH_NAME}">
									</td>
				               </tr>
				               <tr>
									<td class="td_title" width="4%"><!-- 韩文姓名 -->
									<spring:message code="hrm.empinfo.KOREAN_NAME" /></td>
									<td class="td_type"  width="25%">
									 <input type="text" id="FAM_KOREAN_NAME" name="FAM_KOREAN_NAME" value="${personInfo.FAM_KOREAN_NAME}">
									</td>
									<td class="td_title" width="4%"><!-- 别称 -->
									<spring:message code="hrm.approve.BYNAME" /></td>
									<td class="td_type"  width="25%">
									<input type="text" id="FAM_ANOTHER_NAME" name="FAM_ANOTHER_NAME" value="${personInfo.FAM_ANOTHER_NAME}">
									</td>
				               </tr>
				           </c:if>
				           <c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td class="td_title" width="4%"><!-- 越南语姓名 -->
									<spring:message code="hrm.recruitManage.YUENAN_NAME.Z" /></td>
									<td class="td_type"  width="25%">
									 <input type="text" class="required" id="FAM_NAME" name="FAM_NAME" value="${personInfo.FAM_NAME}">
									</td>
									<td class="td_title" width="4%"><!-- 别称 -->
									<spring:message code="hrm.approve.BYNAME" /></td>
									<td class="td_type"  width="25%">
									<input type="text" id="FAM_ANOTHER_NAME" name="FAM_ANOTHER_NAME" value="${personInfo.FAM_ANOTHER_NAME}">
									</td>
				               </tr>
				           </c:if>
							 <tr>
							  <td class="td_title" width="4%"><!-- 关系 -->
							  <spring:message code="ess.empInfo.relationship" /></td>
									<td class="td_type"  width="25%">
									<ait:SelectSyCodeByCpnyID name="FAM_TYPE_CODE" id="FAM_TYPE_CODE"
                                 parentNo="950" cnpyID="${defaultCpny}" selected="${personInfo.FAM_TYPE_CODE}" />
									</td>
									<td class="td_title" width="4%"><!-- 性别 -->
									<spring:message code="hr.viewPersonalInfo.title.SEX" /></td>
									<td class="td_type"  width="25%">
									<ait:SelectSyCodeByCpnyID name="GENDER" id="GENDER"
                                 parentNo="1324" cnpyID="${defaultCpny}" selected="${personInfo.GENDER}" limit="all" />
									</td>
							 </tr>
							 <tr>
							   <td class="td_title" width="4%"><!-- 出生日期 -->
							   <spring:message code="main.home.message.chushengriqi" /></td>
									<td class="td_type"  width="25%">
									<input name="FAM_BORNDATE"  id="FAM_BORNDATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.FAM_BORNDATE}" onBlur="borndate()" />
									</td>
									<td class="td_title" width="4%"><!-- 年龄 -->
									<spring:message code="hrm.empinfo.AGE" /></td>
									
									<td class="td_type"  width="25%">
									<input type="text" id="AGE" readonly="readonly"
										name="AGE" value="${personInfo.AGE}" size="25" />
									</td>
									
							 </tr>
							 <tr>
							   <td class="td_title" width="4%"><!-- 地址复制 -->
							   <spring:message code="hr.hrm.empinfo.dizhifuzhi.Z" /></td>
									<td class="td_type"  width="25%">
									<input type="button" value="<spring:message code="hr.hrm.empinfo.renzhirenyuanbiaoqian.Z" />" onclick="copyAddress()">
									</td>
									<td class="td_title" width="4%"><!-- 紧急联络处 -->
									<spring:message code="hrm.recruitManage.EMERGENCY_CONTACT_YN" /></td>
									<td class="td_type"  width="25%">
									<c:if test="${personInfo.EMERGENCY_LIAISON_OFFICE=='Y'}">
									<input type="checkbox" checked='checked' id="office" onclick="changeoffice()">
									
									</c:if>
									<c:if test="${personInfo.EMERGENCY_LIAISON_OFFICE=='N' or personInfo.EMERGENCY_LIAISON_OFFICE=='' or personInfo.EMERGENCY_LIAISON_OFFICE== null}">
									<input type="checkbox" id="office" onclick="changeoffice()">
									</c:if>  
									<input type="hidden" id="EMERGENCY_LIAISON_OFFICE" name="EMERGENCY_LIAISON_OFFICE" value="${personInfo.EMERGENCY_LIAISON_OFFICE}">
									</td>
							  </tr>
							  <tr>
							   <td class="td_title" width="4%"><!-- 地址 -->
							   <spring:message code="hr.viewRelation.title.FAM_ADDRESS" /></td>
									<td class="td_type"  width="25%" colspan='3'>
									<input style="width: 95%;" type="text" id="FAM_ADDRESS" name="FAM_ADDRESS" value="${personInfo.FAM_ADDRESS}">
									</td>
							 </tr>
							 <tr>
							 	 <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							 <td class="td_title" width="4%"><!-- 有效开始日期 -->
							 <spring:message code="hrm.empinfo.YOUXIAO_START_DATE.Z" /></td>
									<td class="td_type"  width="25%">
									<input name="EFFECTIVE_START_DATE" id="EFFECTIVE_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.EFFECTIVE_START_DATE}" />
									</td>
								</c:if>
									<td class="td_title" width="4%"><!-- 国家 -->
									<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME" /></td>
									<td class="td_type"  width="25%">
									<ait:SelectSyCodeByCpnyID name="NATIONALITY" id="NATIONALITY"
                                 parentNo="870" cnpyID="${defaultCpny}" selected="${personInfo.NATIONALITY}" limit="all" />
									</td>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
							 		<td class="td_title" width="4%"></td>
									<td class="td_type"  width="25%"></td>
								</c:if>
							 </tr>
							 <tr>
							 	<td class="td_title" width="4%">
							 		<!-- 手机号码 --> <spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
							 	</td>
								<td class="td_type"  width="25%">
									<input type="text" id="FAM_PHONE" name="FAM_PHONE" value="${personInfo.FAM_PHONE}">
								</td>
									<td class="td_title" width="4%"><!-- 家人电子邮箱 -->
									<spring:message code="hrm.recruitManage.FAM_EMAIL" /></td>
									<td class="td_type"  width="25%">
									<input style="width: 95%;" type="text" id="FAM_EMAIL" name="FAM_EMAIL" value="${personInfo.FAM_EMAIL}">
									</td>
							 </tr>
							 <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			                 <tr>
			                    	<td class="td_title" width="4%"><!-- 家庭电话 -->
							  			<spring:message code="ess.personalinfo.title.familyTelphone" /></td>
									<td class="td_type"  width="25%">
									<input style="width: 95%;" type="text" id="FAM_FAMILY_PHONE" name="FAM_FAMILY_PHONE" value="${personInfo.FAM_FAMILY_PHONE}">
									</td>
									<td class="td_title" width="4%"><!-- 公司电话 -->
									<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /></td>
									<td class="td_type"  width="25%">
									<input style="width: 95%;" type="text" id="WORK_PHONE" name="WORK_PHONE" value="${personInfo.WORK_PHONE}">
									</td>
								</tr>
							</c:if>
							<tr>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			                    <td class="td_title" width="4%"><!-- 学历 -->
			                    <spring:message code="hr.viewPersonalInfo.title.DEGREE_NAME" /></td>
									<td class="td_type"  width="25%">
									<ait:SelectSyCodeByCpnyID name="FAM_EDUCATION" id="FAM_EDUCATION"
                                 parentNo="13769" cnpyID="${defaultCpny}" selected="${personInfo.FAM_EDUCATION}" limit="all" />
									</td>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			                    <td class="td_title" width="4%"><!-- 工作岗位 -->
			                    <spring:message code="hrm.empinfo.WORK_DUTY.Z" /></td>
									<td class="td_type"  width="25%">
										<input type="text" id="OCUPATION" name="OCUPATION" value="${personInfo.OCUPATION}">
									</td>
								</c:if>
									<td class="td_title" width="4%"><!-- 工作单位 -->
									<spring:message code="hr.viewWorkInfo.title.CPNY_NAME" /></td>
									<td class="td_type"  width="25%">
									<input type="text" id="FAM_COMPANY_NAME" name="FAM_COMPANY_NAME" value="${personInfo.FAM_COMPANY_NAME}">
									</td>
								</tr>
								<tr>
			                        <td class="td_title" width="4%"><!-- 备注 -->
			                        <spring:message code="inct.salesman.remark" /></td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARKS"  id="REMARKS" style="width:600px;height:80px">${personInfo.REMARKS}</textarea>
									</td>
								</tr>	
							</table>
							<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			    <c:if test="${not empty personInfo.UPDATED_BY}">
			    <td class="td_title" width="5%"><!-- 变更者 -->
			    <spring:message code="hrm.empinfo.UPDATED_BY" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 -->
					<spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATE_DATE }
					</td>
					</c:if>
					<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><!-- 变更者 -->
					<spring:message code="hrm.empinfo.UPDATED_BY" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATED_BY }&nbsp&nbsp${personInfo.CREATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 -->
					<spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATE_DATE }
					</td>
					</c:if>
								</tr>	
							</table>	
						</td>
					</tr>
				</table>	
						</td>
					</tr>
				</table>
			</div>
	  	</form>	
	</div>
</div>