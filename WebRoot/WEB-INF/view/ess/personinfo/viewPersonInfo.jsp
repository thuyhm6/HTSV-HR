<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script src="/resources/js/reg.js" type="text/javascript"></script>
<script type="text/javascript">

function validateCallbackPersonalInfo(form, callback) {	
	var $form = $(form);
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

<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
     <div><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead.jsp"%></div>
</div>
	<div class="panel collapse">
		<h1><spring:message code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION"/>
			<!--个人基础信息-->
		</h1>
		<div>
			<form method="post" action="/ess/infoApply/addPersonalInfoApply" class="pageForm required-validate" onsubmit="return validateCallbackPersonalInfo(this, navTabAjaxDone);">
				
			   <div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="ess.infoApply.title.apply"/><!--申请-->
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
											class="user_table">
											<tr>
												<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />
												<!--最终学历-->
												</td>
												<td class="td_type">
													${personInfo.FINAL_DEGREE_NAME }
												</td>
												<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />
												<!--最终学校-->
												</td>
												<td class="td_type" >
													${personInfo.FINAL_SCHOOL }
												</td>
												<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.FINAL_SUBJECT_NAME" />
												<!--最终专业-->
												</td>
												<td class="td_type" width="15%">
													${personInfo.FINAL_SUBJECT_NAME }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" />
													<!--是否共产党员-->
												</td>
												<td class="td_type">
													${personInfo.WHETHER_COMMUNIST_NAME }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
													<!--手机号码-->
												</td>
												<td class="td_type">
													<input type="text" name="personInfo_CELLPHONE" id="personInfo_CELLPHONE" value="${personInfo.CELLPHONE }" class="number"/>
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
													<!--现住址(邮编)-->
												</td>
												<td class="td_type" colspan="5">
													${personInfo.PRESENT_ADDRESS_PROVINCE_NAME }
													<input type="text" name="personInfo_HOME_ADDRESS" id="personInfo_HOME_ADDRESS" value="${personInfo.HOME_ADDRESS }" size="65"/>
												</td>
												
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
													<!--户口性质-->
												</td>
												<td class="td_type" width="15%">
													${personInfo.REG_TYPE_NAME }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
													<!--户口所在地(邮编)-->
												</td>
												<td class="td_type" colspan="5">
													${personInfo.ANMELDEN_PROVINCE_NAME }${personInfo.REG_PLACE }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.ENGLISH_NAME" />
													<!--英文名 -->
												</td>
												<td class="td_type" width="15%">
													<input type="text" name="personInfo_ENGLISH_NAME" id="personInfo_ENGLISH_NAME" value="${personInfo.ENGLISH_NAME }"/>
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.WHETHER_FOREIGNERS" />
													<!--是否外国人 -->
												</td>
												<td class="td_type" width="15%">
													 ${personInfo.WHETHER_FOREIGNERS_NAME } 
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
													<!--国籍-->
												</td>
												<td class="td_type">
													${personInfo.NATIONALITY_NAME }
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.NATION_NAME" />
													<!--民族 -->
												</td>
												<td class="td_type" width="15%">
													${personInfo.NATION_NAME }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="liang.hr.viewPersonalInfo.title.EMAIL" />
													<!--E-mail-->
												</td>
												<td class="td_type">
													<input type="text" name="personInfo_EMAIL" id="personInfo_EMAIL" value="${personInfo.EMAIL }" class="email" />
												</td>
												<td class="td_title">
													<spring:message code="hr.viewHire.title.HOME_PHONE" />
													<!-- 家庭电话 -->
												</td>
												<td class="td_type">
													${personInfo.HOME_PHONE}
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.DISABLED_OR_NOT" />
													<!--残疾与否-->
												</td>
												<td class="td_type" >
													${personInfo.DISABLED_OR_NOT }
												</td>
												<td class="td_type" ></td>
												<td class="td_type" ></td>
											</tr>
										</table>
				</div>
			</form>	
		</div>
	</div>	
	<div style="clear: both;"></div>
	<div class="panel collapse">		
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.work_information" />
			<!--工作信息 -->
		</h1>	
		<div>
			<form method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
				
			   <div class="formBar">
					<ul>
						<li>
							
						</li>
					</ul>
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="user_table">
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.JOB_TYPE" />
												<!--职种-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.JOB_TYPE_NAME}
											</td>
											<td class="td_title">
												<spring:message code="liang.hr.viewPersonalInfo.title.POST"/>
												<!--职务-->
											</td>
											<td class="td_type">
												 ${personInfo.POSITION_NO_NAME }
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.WHETHER_THE_UNION_MEMBERS" />
												<!--是否工会会员-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.WHETHER_NAME }
											</td>
											<td class="td_title">
												<%-- <spring:message code="hr.viewPersonalInfo.title.WORKING_TIME"/>
												<!--工时制--> --%>
											</td>
											<td class="td_type">
												<%-- ${personInfo.WORKING_TIMET_NAME }  --%>
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="liang.hr.viewPersonalInfo.title.HIRE_PATH"/>
												<!--雇佣路径-->
											</td>
											<td class="td_type">
												${personInfo.REC_SOURCE_DETAIL_NO_NAME}
											</td>
											<td class="td_title">
												<spring:message code="liang.hr.viewPersonalInfo.title.DETAIL_HIRE_PATH" />
												<!--详细雇佣路径-->
											</td>
											<td class="td_type" width="8%">
												<!--  ${personInfo.RECRUITMENT_SOURCE_NAME} code？-->
												
												${personInfo.RECRUITMENT_SOURCE_TYPE}
											</td>
											<td class="td_title">
												<spring:message code="liang.hr.viewPersonalInfo.title.ENTRY_TYPE" />
												<!--入司类型-->
											</td>
											<td class="td_type" >
												${personInfo.JOIN_TYPE_CODE_NAME }
											</td>
											<td class="td_title">
												<spring:message
													code="liang.hr.viewPersonalInfo.title.waibugongzuonianzi" />
												<!--司外工作年资-->
											</td>
											<td class="td_type" width="15%">
												<c:if test="${personInfo.YEAR_AGE_LIMIT ne 0 and personInfo.YEAR_AGE_LIMIT ne null }">${personInfo.YEAR_AGE_LIMIT}<spring:message code="liang.hr.viewWorkInfo.title.YEAR" /></c:if>
												<c:if test="${personInfo.MONTH_AGE_LIMIT ne 0 and personInfo.MONTH_AGE_LIMIT ne null}">${personInfo.MONTH_AGE_LIMIT}<spring:message code="hr.viewPersonalInfo.title.WORKINFO_MONTH" /></c:if>
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD" />
											<!--合同次数-->
											</td>
											<td class="td_type">
												${personInfo.TOTAL_PERIOD }
											</td>
											<td class="td_title">
												<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/>
												<!-- 是否试用 -->
											</td>
											<td class="td_type">
												${personInfo.IN_THE_DIFFERENCE}
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE" />
												<!--预转正日期-->
											</td>
											<td class="td_type">
												${personInfo.BEFORE_END_PROBATION_DATE }
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME" />
												<!--工作地-->
											</td>
											<td class="td_type">
												${personInfo.WORK_AREA_NAME }
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message
													code="liang.hr.viewPersonalInfo.title.SOCIAL_INSURANCE_ADDRESS" />
												<!--社会保险地-->
											</td>
											<td class="td_type">
												${personInfo.SOCIAL_SECURITY_AREA_NAME }
											</td>
											<td class="td_title">
												<c:if test="${LoginUser.cpnyId ne 'HAE'}">
													<spring:message code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" />
													<!--保险类型-->
												</c:if>
												<c:if test="${LoginUser.cpnyId eq 'HAE'}">
													<spring:message code="hrm.empinfo.REG_TYPE_CODE"/><!-- 户口性质-->
												</c:if>
											</td>
											<td class="td_type" >
												${personInfo.INSURANCE_TYPE_CODE_NAME }
											</td>
											<td class="td_type"></td>
											<td class="td_type"></td>
											<td class="td_type"></td>
											<td class="td_type"></td>
										</tr>
									</table>
				</div>
			</form>	
		</div>		
	</div>		