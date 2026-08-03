<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
function addrow() {

	var count = parseInt($("#count").val());

	var i = count;
	var htm = "";
	htm += '<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	htm += '<tr>';

	htm += '<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/><!--关系--></td>';
	htm += '<td  class="td_type"><select name="FAM_TYPE_CODE' + i + '">';
<c:forEach items="${codeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="FAM_NAME' + i + '" class="textInput" maxlength="13"/></td>';
	   		
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/><!--身份证号码--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_IDCARD' + i + '" class="textInput alphanumeric" maxlength="30"/></td>';--%>
<%--	   		--%>
	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.DOB"/><!--出生日期--></td>';
	   		htm+='<td  class="td_type"><input type="text"  name="FAM_BORNDATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_ADDRESS"/><!--地址--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_ADDRESS' + i + '" class="textInput" maxlength="100"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';--%>
<%--	   		htm+='</tr>';--%>
<%--	   		--%>
<%--	   		htm+='<tr>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_PHONE"/><!--联系电话--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_PHONE' + i + '" class="textInput" maxlength="20"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/><!--工作单位/职(岗)位--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_COMPANY_NAME' + i + '" class="textInput" maxlength="30"/></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/><!--一起居住与否--></td>';--%>
<%--	   		htm+='<td  class="td_type"><select name="LIVE_YN' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/></option><option value="Y"><spring:message code="hr.viewRelation.title.YES"/></option>	<option value="N"><spring:message code="hr.viewRelation.title.NO"/></option></select></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/><!--是否紧急联系人--></td>';--%>
<%--	   		htm+='<td  class="td_type"><select name="EMERGENCY_CONTACT_YN' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择--></option><option value="Y"><spring:message code="hr.viewRelation.title.YES"/><!--是--></option><option value="N"><spring:message code="hr.viewRelation.title.NO"/><!--否--></option></select></td>';--%>
<%--	   		--%>
<%--	   		htm+='<td  class="td_title"><spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/><!--亲属员工号--></td>';--%>
<%--	   		htm+='<td  class="td_type"><input type="text" name="FAM_PERSON_ID' + i + '" class="textInput" maxlength="30"/></td>';--%>
	   			
				<%--htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.FAMILY_YINYANGRILI"/><!-阴阳历区分--></td>';--%>
		   		<%--htm+='<td  class="td_type"><select name="FAM_YINYANGLI' + i + '">' ;--%>
	   			<%--<c:forEach items="${codeList1}" var="item" >
	   			<%--htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';--%>
				<%--</c:forEach>--%>
	   			<%--htm+='</select></td>';--%>

	   			htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME"/><!--单位名称--></td>';
		   		htm+='<td  class="td_type"><input type="text" name="FAM_COMPANY_NAME' + i + '" class="textInput" maxlength="30"/></td>';
	   			
		   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
			htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
	   
    }

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function delectApplyInfo(){
   	 if(window.confirm('<spring:message code="ess.empInfo.sure_submit_application_deletion" />')){//确定提交删除申请吗？
              $("#deleteWorkExperienceInfo").submit();
             }
   	
   	
   }
    
    function validateCallInfo(form, callback) {
    	
		var $form = $("#deleteWorkExperienceInfo");
		
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
    
    function buttons(){
    	
    $("input[name=APPLY_TYPE]").val("3");
    	
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#essAddWorkInfo");
		
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
    

</script>

<div class="pageContent">
	<form id="essAddWorkInfo" method="post"
		action="/ess/empinfo/essAddWorkInfo"
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
				<input type="hidden" name="UPDATE_WORK_EXPER_NO"
					value="${WORK_EXPER_NO}" />
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
				<td class="td_title">
						<!-- 公司名称 --><spring:message code="hrm.empinfo.COMPANY_NAME" />
					</td>
					<td class="td_type" width="15%">
							${workExperienceList.CPNY_NAME}
					</td>
					<td class="td_title">
						<!-- 月薪 --><spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" />
					</td>
					<td class="td_type" width="15%">
						${workExperienceList.PAY_YEAR}
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title">
						<!-- 公司名称 --><spring:message code="hrm.empinfo.COMPANY_NAME" />
					</td>
					<td class="td_type" width="15%" colspan="3">
							${workExperienceList.CPNY_NAME}
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td class="td_type" width="15%">
							${workExperienceList.DEPT_NAME}
					</td>
					<td class="td_title">
						<!-- 月薪 --><spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" />
					</td>
					<td class="td_type" width="15%">
						${workExperienceList.PAY_YEAR}
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<!--岗位--><spring:message code="rp.report.title.dutyinfo" />
					</td>
					<td class="td_type" width="15%">
							${workExperienceList.DUTY}
					</td>
					<td class="td_title">
						<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!--主要业务-->
					</td>
					<td class="td_type" width="15%">
						${workExperienceList.POSITION}
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title">
						<!-- 入职日期 --><spring:message code="hr.viewPersonalInfo.title.DATE_STARTED" />
					</td>
					<td class="td_type" width="15%">
						${workExperienceList.START_DATE}

					</td>
					<td class="td_title">
						<!-- 离职日期 --><spring:message code="hr.viewPersonalInfo.title.DATE_LEFT" />
					</td>
					<td class="td_type" width="35%">
						${workExperienceList.END_DATE}
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title">
						<!-- 离职事由 --><spring:message code="hr.viewCondSql.title.LIZHIYUANYIN" />
					</td>
					<td class="td_type" width="35%" colspan="3">
					${workExperienceList.RESIGN_REASON}
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<!-- 备注 --><spring:message code="org.title.REMARK" />
					</td>
					<td class="td_type" width="35%" colspan="3">
					${workExperienceList.REMARK}
					</td>
				</tr>
				</c:if>
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
				<c:if test="${APPLY_TYPE=='2'}">
					  	<li>
					<div class="buttonActive">
						<div class="buttonContent" onclick="buttons()">
						<button type="submit">
								删除
							</button>
						</div>
				</div>
				</li>

				</c:if>
				
			
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
	</form>
</div>


<!-- 删除申请-->
<!-- <div style="visibility: hidden;"> -->

<!-- <form id="deleteWorkExperienceInfo" method="post" -->
<!-- 		action="/ess/empinfo/essAddWorkInfo" -->
<!-- 		class="pageForm required-validate" -->
<!-- 		onsubmit="return validateCallInfo(this, dialogAjaxDone);"> -->
	
<!-- 			<div class="pageFormContent" layoutH="56"> -->
<%-- 			<c:forEach items="${workExperienceList}"> --%>
<!-- 			<table width="100%" border="0" cellpadding="0" cellspacing="0" -->
<!-- 				class="user_table margin_b"> -->
<%-- 			<input type="hidden" name="UPDATE_WORK_EXPER_NO" value='${workExperienceList.WORK_EXPER_NO}'/> --%>
<%-- 			<input type="hidden" name="APPLY_TYPE" value='${3}'/> --%>
				
<!-- 				<tr> -->
<!-- 					<td class="td_title"> -->
<!-- 						工作地点 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="CPNY_NAME" -->
<%-- 							value="${workExperienceList.WORK_ADDRESS}" class="textInput" /> --%>
<!-- 					</td> -->
<!-- 					<td class="td_title"> -->
<!-- 						公司名称 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="CPNY_NAME" -->
<%-- 							value="${workExperienceList.CPNY_NAME}" class="textInput" /> --%>

<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title"> -->
<!-- 						职务 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="POSITION" -->
<%-- 							value="${workExperienceList.POSITION}" class="textInput" /> --%>

<!-- 					</td> -->
<!-- 					<td class="td_title"> -->
<!-- 						联系电话 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="POSITION" -->
<%-- 							value="${workExperienceList.CELLPHONE}" class="textInput" /> --%>

<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title"> -->
<!-- 						入职日期 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<%-- 						<input type="text" value="${workExperienceList.START_DATE}" --%>
<!-- 							name="START_DATE" class="Wdate required" readonly="true" -->
<!-- 							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  /> -->

<!-- 					</td> -->
<!-- 					<td class="td_title"> -->
<!-- 						离职日期 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="35%"> -->

<%-- 						<input type="text" value="${workExperienceList.END_DATE}" --%>
<!-- 							name="END_DATE" class="Wdate required" readonly="true" -->
<!-- 							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  /> -->

<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title"> -->
<!-- 						证明人 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="POSITION" -->
<%-- 							value="${workExperienceList.WITNESS}" class="textInput" /> --%>
<!-- 					</td> -->
<!-- 					<td class="td_title"> -->
<!-- 						年薪 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<table> -->
<!-- 							<tr> -->
<!-- 								<td> -->
<!-- 									<input type="text" name="PAYROLL_YEAR" -->
<%-- 										value="${workExperienceList.YEAR_PAY}" class="textInput" /> --%>

<!-- 								</td> -->
<!-- 								<td> -->
<!-- 								元 -->
<!-- 								</td> -->
<!-- 							</tr> -->
<!-- 						</table> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="td_title"> -->
<!-- 						是否海外 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="POSITION" -->
<!-- 							value="" class="textInput" /> -->
<!-- 					</td> -->
<!-- 					<td class="td_title"> -->
<!-- 						国家 -->
<!-- 					</td> -->
<!-- 					<td class="td_type" width="15%"> -->
<!-- 						<input type="text" name="POSITION" -->
<!-- 							value="" class="textInput" /> -->
<!-- 					</td> -->
						
<!-- 					</td> -->
<!-- 					<td class="td_type" width="35%"> -->
						
<!-- 					</td> -->

<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 				<td class="td_title"> -->
<!-- 						备注 -->
<!-- 					</td> -->
					
<!-- 					<td class="td_type" width="35%"> -->
<%-- 					<textarea rows="5" name="REMARK" cols="30">${workExperienceList.REMARK}</textarea> --%>
			  		  
<!-- 					</td> -->
				
<!-- 				</tr> -->
			

<!-- 			</table> -->
			
<%-- 			</c:forEach> --%>


<!-- 			<div id="createTable" width="100%"></div> -->

<!-- 			<input type="hidden" name="count" id="count" value="1"> -->
<!-- 		</div> -->
		
<!-- 	</form> -->

<!-- </div> -->