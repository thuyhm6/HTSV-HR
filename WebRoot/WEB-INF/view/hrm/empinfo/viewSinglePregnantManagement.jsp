<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
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
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deletePregnantManagement',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
function expDateSize(a){
	var startdate=$('#START_BABYCARE_DATE').val();
	var enddate=$('#END_BABYCARE_DATE').val();
	if(startdate!=''&&enddate!=''){
		sdate=parseInt(startdate.replace('-','').replace('-',''));
		edate=parseInt(enddate.replace('-','').replace('-',''));
		if(sdate>edate){
			alert("<spring:message code='hrm.viewSinglepregnantManagement.KAISHISHIJIANBUNENGDAYUJIESHUSHIJIAN.b' />");//开始时间不能大于结束时间!
			if(a=='0'){
				$('#START_BABYCARE_DATE').attr('value','');
			}else if(a=='1'){
				$('#END_BABYCARE_DATE').attr('value','');
			}
		}
	}
}
</script>
	<div>
		<form id="editPregnantManagement" method="post" action="/hrm/empinfo/editPregnantManagement" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID" VALUE="${PERSON_ID}" >
        <input TYPE="hidden" NAME="isEssSystem" VALUE="${isEssSystem}" >
        <input type="hidden" name="HR_PREGNANT_MANAGE_NO" id="HR_PREGNANT_MANAGE_NO" value="${personInfo.HR_PREGNANT_MANAGE_NO}">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" >
								<tr>
									<td class="td_title" width="4%"><!-- 员工号 --><spring:message code="hrm.viewpregnantManagement.YUANGONGHAO.b" /></td>
									<td class="td_type"  width="25%" colspan='3'>
									<input  type="text" class="required" id="PERSON_ID" name="PERSON_ID" value="${personInfo.PERSON_ID }" style="width: 300px" disabled ="true" readOnly="true">
									</td>
							    </tr>
							    <tr>
									<td class="td_title" width="4%"><!-- 怀孕日期 --><spring:message code="hrm.viewpregnantManagement.HUANYUNRIQI.b" /></td>
									<td class="td_type"  width="25%">
									<input name="FETATION_DATE" id="FETATION_DATE"  onchange="expDateSize(4)" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${personInfo.FETATION_DATE }">
									</td>
							    </tr>
							    <tr>
									<td class="td_title" width="4%"><!-- 预产期 --><spring:message code="liang.ess.infoApply.title.expected_date" /></td>
									<td class="td_type"  width="25%" >
									<input name="EXPECTED_BIRTH_DATE" id="EXPECTED_BIRTH_DATE" onchange="expDateSize(3)" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${personInfo.EXPECTED_BIRTH_DATE }">
									</td>
							    </tr>
							    <tr>
									<td class="td_title" width="4%"><!-- 生育日期 --><spring:message code="hrm.viewpregnantManagement.SHENGYURIQI.b" /></td>
									<td class="td_type"  width="25%">
									<input name="CHILDBIRTH_DATE" id="CHILDBIRTH_DATE" onchange="expDateSize(2)" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${personInfo.CHILDBIRTH_DATE }">
									</td>
							    </tr>
							     <tr>
									<td class="td_title" width="4%"><!-- 哺乳期开始日期 --><spring:message code="hrm.viewpregnantManagement.BURUQIKAISHIRIQI.b" /></td>
									<td class="td_type"  width="25%" >
									<input name="START_BABYCARE_DATE" id="START_BABYCARE_DATE" onchange="expDateSize(0)" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${personInfo.START_BABYCARE_DATE}"/>
									</td>
									<td class="td_title" width="4%"><!-- 哺乳期结束日期 --><spring:message code="hrm.viewpregnantManagement.BURUQIJIESHURIQI.b" /></td>
									<td class="td_type"  width="25%" >
									<input name="END_BABYCARE_DATE"   id="END_BABYCARE_DATE" onchange="expDateSize(1)" class="Wdate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${personInfo.END_BABYCARE_DATE}"/>
									</td>
							    </tr>
							    <tr>
									<td class="td_title" width="4%"><!-- 备注 --><spring:message code="ess.empInfo.remarks" /></td>
									<td class="td_type"  width="25%" colspan='3'>
										<textarea id="REMARK" name="REMARK" style="width:300px;height:80px">${personInfo.REMARK}</textarea>
									</td>
							    </tr>
							</table>
							<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			    <c:if test="${not empty personInfo.UPDATED_BY}">
			    <td class="td_title" width="5%"><!-- 变更者 --><spring:message code="org.title.UPDATED_IP" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 --><spring:message code="org.title.UPDATE_DATE" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATE_DATE }
					</td>
					</c:if>
					<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><!-- 变更者 --><spring:message code="org.title.UPDATED_IP" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATED_BY }&nbsp&nbsp${personInfo.CREATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 --><spring:message code="org.title.UPDATE_DATE" /></td>
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
