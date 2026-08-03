<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="panel">
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION" />
			<!--员工基础信息-->
		</h1>
		<div>
			<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead3.jsp"%>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div class="tabs" currentIndex="${tabsSelected }" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
						<li>
							<a href="javascript:;"><span>${menu.MENU_NAME } </span>
								
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="tabsContent" id="displaycheckbox">
			<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
				<c:if test="${menu.MENU_NO eq '2540' }">
					<!-- 基础信息 -->
					<div>
						<div style="display: block;" id="displaycheckbox_1">
<!-- isEssSystem的值不为‘1’，则是业务系统,否则为ESS系统, 以下均是!-->
						<c:if test="${isEssSystem ne '1'}">
							<c:set value="${i.index}" var="tabsSelected" />
							<c:set value="hrm/empinfo/viewPersonalInfo" var="actionUrl" />
							<c:set value="1250" var="edit_width" />
							<c:set value="630" var="edit_height" />
							<c:set
								value="/hrm/empinfo/viewEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="edit_rel" />
							<c:set
								value="/hrm/empinfo/viewEduPerInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="edit_Url" />
							<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
						</c:if>
							<div>
								<div class="panel">
									<h1>
										<spring:message
											code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" />
										<!--个人信息 -->
									</h1>

									<div>
										<table width="100%" border="0" cellpadding="0" cellspacing="0"
											class="user_table">
											<tr>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.ENGLISH_NAME" />
													<!--英文名 -->
												</td>
												<td class="td_type" width="15%">
													${personInfo.ENGLISH_NAME }
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.WHETHER_FOREIGNERS" />
													<!--是否外国人 -->
												</td>
												<td class="td_type" width="15%">
													 ${personInfo.WHETHER_FOREIGNERS } 
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
													<spring:message
														code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
													<!--户口性质-->
												</td>
												<td class="td_type" width="15%">
													${personInfo.REG_TYPE_NAME }
												</td>
												<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.TOTAL_PERIOD" />
												<!--合同次数-->
												</td>
												<td class="td_type">
													${personInfo.TOTAL_PERIOD }
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" />
													<!--是否共产党员-->
												</td>
												<td class="td_type">
													${personInfo.WHETHER_COMMUNIST }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
													<!--户口所在地(邮编)-->
												</td>
												<td class="td_type">
													${personInfo.ANMELDEN_PROVINCE_CODE }${personInfo.REG_PLACE }
												</td>
											</tr>
											<tr>
												<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />
												<!--最终学历-->
												</td>
												<td class="td_type">
													${personInfo.FINAL_DEGREE_NAME }
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
												<spring:message
													code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />
												<!--最终学校-->
												</td>
												<td class="td_type" >
													${personInfo.FINAL_SCHOOL }
												</td>
												<td class="td_title">
													<spring:message
														code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
													<!--现住址(邮编)-->
												</td>
												<td class="td_type">
													${personInfo.PRESENT_ADDRESS_PROVINCE_CODE }${personInfo.HOME_ADDRESS }
												</td>
											</tr>
											<tr>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.shouji" />
													<!--手机-->
												</td>
												<td class="td_type">
													${personInfo.CELLPHONE }
												</td>
												<td class="td_title">
													<spring:message code="hr.viewHire.title.HOME_PHONE" />
													<!-- 家庭电话 -->
												</td>
												<td class="td_type">
													${personInfo.HOME_PHONE}
												</td>
												<td class="td_title">
													<spring:message code="hr.viewPersonalInfo.title.EMAIL" />
													<!--公司E-mail-->
												</td>
												<td class="td_type">
													${personInfo.EMAIL }
												</td>
												<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" />
												<!--保险类型-->
												</td>
												<td class="td_type" >
													${personInfo.INSURANCE_TYPE_NAME }
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>



							
							

							

							<div class="panel">
								<h1>
									<spring:message
										code="hr.viewPersonalInfo.title.work_information" />
									<!--工作信息 -->
								</h1>

								<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="user_table">
										<tr>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.JOB_CLASS_NAME" />
												<!--职类-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.JOB_CLASS_NAME}
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.JOB_TYPE" />
												<!--职种-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.JOB_TYPE}
											</td>
											<td class="td_title">
												<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" />
												<!--员工状态-->
											</td>
											<td class="td_type">
												${personInfo.STATUS_NAME }
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.SERVICES_BELONG_NAME" />
												<!--劳务所属-->
											</td>
											<td class="td_type">
												${personInfo.SERVICES_BELONG_NAME }
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY" />
												<!--司内工作年资-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.INNER_WORK_YEAR }
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.WHETHER_THE_UNION_MEMBERS" />
												<!--是否工会会员-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.WHETHER_THE_UNION_MEMBERS }
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.IN_THE_DIFFERENCE" />
												<!--在职区分-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.IN_THE_DIFFERENCE }
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.DISABLED_OR_NOT" />
												<!--残疾与否-->
											</td>
											<td class="td_type" colspan="3">
												${personInfo.DISABLED_OR_NOT }
											</td>
										</tr>
										<tr>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.waibugongzuonianzi" />
												<!--外部工作年资-->
											</td>
											<td class="td_type" width="15%">
												${personInfo.OUTER_WORK_YEAR}
											</td>
											<td class="td_title">
												
											</td>
											<td class="td_type">
												
											</td>
											<td class="td_title">
												<spring:message
													code="hr.viewPersonalInfo.title.REC_SOURCE_DETAIL" />
												<!--详细采用路径-->
											</td>
											<td class="td_type" width="8%">
												<!--  ${personInfo.RECRUITMENT_SOURCE_NAME} code？-->
												
												${personInfo.RECRUITMENT_SOURCE_TYPE}
											</td>
											<td class="td_title">
<!-- 												<spring:message code="hr.viewPersonalInfo.title.REFERRER" /> -->
												<!-- 推荐人 -->
											</td>

											<td class="td_type">
<!-- 												${personInfo.REFERRER} -->
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '123188' }">
					
					<div style="display: block;" id="displaycheckbox_2">
					<c:set value="${i.index}" var="tabsSelected" />
								<div class="panel">
									<h1>
										<spring:message code="hr.viewPersonalInfo.title.SCHOOLTAG" />
										<!--毕业学校-->
									</h1>
									<c:if test="${isEssSystem ne '1'}">
										<c:set value="1000" var="add_width" />
										<c:set value="420" var="add_height" />
										<c:set value="dialog" var="add_tab" />
										<c:set
											value="/hrm/empinfo/viewEducationInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
											var="add_Url" />
	
										<c:set value="800" var="delete_width" />
										<c:set value="300" var="delete_height" />
										<c:set value="dialog" var="delete_tab" />
	
										<c:set value="0" var="delete_mask_exit" />
										<c:set value="true" var="delete_mask" />
										<c:set value="0" var="delete_range" />
	
										<c:set
											value="/hrm/empinfo/deleteEducation?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
											var="delete_Url" />
											
										<c:set value="1250" var="edit_width" />
										<c:set value="420" var="edit_height" />
										<c:set value="dialog" var="edit_tab" />
										<c:set
											value="/hrm/empinfo/updateEducation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
											var="edit_Url" />
											
										<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
									</c:if>
									<div id="edudiv">
										<table class="table" width="101%" nowrapTD="false">
											<thead>
												<tr>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.INSTITUTION_NAME" />
														<!--学校名-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.SUBJECTNAME" />
														<!--专业-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.SUBJECT_SECOND_NAME" />
														<!--第二专业-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.START_DATE" />
														<!--入学日期-->
													</th>
													<th width="50">
														<spring:message code="hr.viewPersonalInfo.title.END_DATE" />
														<!--毕业日期-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.DEGREE_NAME" />
														<!--学历-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.PARTICULAR_DEGREE" />
														<!--详细学历-->
													</th>
													<th width="50">
														<spring:message
															code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" />
														<!--所在地-->
													</th>
													<th width="50">
														<spring:message
															code="liang.hr.viewPersonalInfo.title.REMARKS" />
														<!--备注-->
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${educationList}" var="item">
													<tr target="sid" rel="${item.EDUC_NO}">
														<td>
															${item.INSTITUTION_NAME}
														</td>
														<td>
															${item.SUBJECTNAME}
														</td>
														<td>
															${item.SUBJECT_SECOND_NAME}
														</td>
														<td>
															${item.START_DATE}
														</td>
														<td>
															${item.END_DATE}
														</td>
														<td>
															${item.DEGREE_NAME}
														</td>
														<td>
															${item.PARTICULAR_DEGREE}
														</td>
														<td>
															${item.SCHOOL_ADDRESS}
														</td>
														<td>
															${item.REMARKS}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '2541' }">
					<div>
						<!-- 发令信息 -->
						<div style="display: block;" id="displaycheckbox_3">
							<div class="panel">
								<h1>
									<spring:message code="hr.viewPromote.title.MATTERS_TO_THE" />
									<!--发令事项-->
								</h1>
								<div id="edudiv">
									<table class="table table-border-lrt" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewPromote.title.RESHUFFLED_TYPE" />
													<!--异动类型-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
													<!--部门-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.POSITION_NAME" />
													<!--职(岗)位-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.POST_NAME" />
													<!--职级名称（职务）-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME" />
													<!--职责-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.STATUS_NAME" />
													<!--员工状态-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE" />
													<!--生效日期-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewPromote.title.RESHUFFLED_CONTENT" />
													<!--异动内容-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.REMARK" />
													<!--备注-->
												</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${expInsideList}" var="item" varStatus="i">

												<tr target="sid" rel="${item.EXP_INSIDE_NO}">
													<td>
														${item.TRANS_NO_NAME}
													</td>
													<td>
														${item.DEPTNAME}
													</td>
													<td>
														${item.POSITION_NAME}
													</td>
													<td>
														${item.POST_NAME}
													</td>
													<td>
														${item.DUTY_NAME}
													</td>
													<td>
														${item.STATUS_NAME}
													</td>
													<td>
														${item.START_DATE}
													</td>
													<td>
														${item.TRANS_NAME}
													</td>
													<td>
														${item.REMARK}
													</td>

												</tr>

											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<div style="clear: both;"></div>
							<div class="panel">
								<h1>
									<spring:message code="hr.viewPromote.title.LEFT_MATTERS" />
									<!--离职事项-->
								</h1>
								<div id="edudiv">
									<table class="table table-border-lrt" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewPromote.title.RESIGN_DATE" />
													<!--离职日期-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
													<!--部门-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.POSITION_NAME" />
													<!--职(岗)位-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.POST_NAME" />
													<!--职级名称（职务）-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.SETTLEMENT_DATE" />
													<!--工资结算日-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewPromote.title.RESIGN_TYPE_NAME" />
													<!--离职类型-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.RESIGN_REASON" />
													<!--离职原因-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.REMARK" />
													<!--备注-->
												</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${resignationInfo}" var="item"
												varStatus="i">

												<tr target="sid" rel="${item.PERSON_ID}">
													<td>
														${item.RESIGN_DATE}
													</td>
													<td>
														${item.DEPTNAME}
													</td>
													<td>
														${item.POSITION_NAME}
													</td>
													<td>
														${item.POST_NAME}
													</td>
													<td>
														${item.SETTLEMENT_DATE}
													</td>
													<td>
														${item.RESIGN_TYPE_NAME}
													</td>
													<td>
														${item.RESIGN_REASON_NAME}
													</td>
													<td>
														${item.REMARK}
													</td>
												</tr>

											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div style="clear: both;"></div>
						<div>
							<!-- 兼职/派遣 -->

							<div class="panel">
								<h1>
									<spring:message code="hr.viewTranslate.title.PART_TIME_JOB" />
									<!--兼职-->
								</h1>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="80">
													<spring:message code="hr.viewTranslate.title.THE_TYPE" />
													<!--发令类型-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_START_DATE" />
													<!--开始日期-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													<!--结束日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
													<!--部门-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewPersonalInfo.title.POSITION_NAME" />
													<!--职(岗)位-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PART_TIME_TYPE" />
													<!--兼职类型-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewPersonalInfo.title.PLU_DEPTNAME" />
													<!--兼职部门-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewPersonalInfo.title.PLU_POSITINO_NAME" />
													<!--兼职职(岗)位-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewPersonalInfo.title.PLU_DUTY_NAME" />
													<!--兼职职责-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PART_TIME_CONTENTS" />
													<!--兼职事由-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPromote.title.REMARK" />
													<!--备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pluralityList}" var="item" varStatus="i">

												<tr target="sid" rel="${item.PERSON_ID}">
													<td>
														${item.TRANS_NAME}
													</td>
													<td>
														${item.START_DATE}
													</td>
													<td>
														${item.END_DATE}
													</td>
													<td>
														${item.DEPTNAME}
													</td>
													<td>
														${item.POSITION_NAME}
													</td>
													<td>
														${item.PLU_TYPE_NAME}
													</td>
													<td>
														${item.PLU_DEPTNAME}
													</td>
													<td>
														${item.PLU_POSITION_NAME}
													</td>
													<td>
														${item.PLU_DUTY_NAME}
													</td>
													<td>
														${item.PLU_REASON}
													</td>
													<td>
														${item.REMARK}
													</td>

												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>
				</c:if>
				
				<c:if test="${menu.MENU_NO eq '123189' }">
					<div>
						<!-- 合同  -->
						<div style="display: block;" id="displaycheckbox_4">


							<div class="panel">
								<h1>
									<spring:message
										code="hr.viewContract.title.CONTRACT_INFORMATION" />
									<!--合同信息-->
								</h1>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message
														code="hr.viewContract.title.CONTRACT_TYPE_NAME" />
													<!--合同类型-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_START_DATE" />
													<!--开始日-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													<!--结束日-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewContract.title.CONTRACT_PERIOD" />
													<!--合同期限-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.REMARK" />
													<!--备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${contracList}" var="item" varStatus="i">

												<tr target="sid" rel="${item.CONTRACT_NO}">
													<td>
														${item.CONTRACT_TYPE_NAME}
													</td>
													<td>
														${item.START_CONTRACT_DATE}
													</td>
													<td>
														${item.END_CONTRACT_DATE}
													</td>
													<td>
														<c:if test="${empty item.END_CONTRACT_DATE}">
															<spring:message code="hr.viewContract.title.NO_ENDDATE" />
															<!--无固定期限-->
														</c:if>
														<c:if test="${not empty item.END_CONTRACT_DATE}">${item.CONTRACT_PERIOD}</c:if>
													</td>
													<td>
														${item.REMARK}
													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>

						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '2551' }">
					<div>
						<!-- 司外经历 -->
						<div style="display: block;" id="displaycheckbox_5">

							<c:set value="/hrm/empinfo/viewWorkInfo" var="turn_to_url" />

							<c:set value="${i.index}" var="tabsSelected" />

							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1100" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
									value="/hrm/empinfo/viewWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
									var="add_Url" />
	
								<c:set value="800" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set
									value="/hrm/empinfo/deleteWorkExpreience?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="delete_Url" />
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1100" var="edit_width" />
								<c:set value="440" var="edit_height" />
								<c:set
									value="/hrm/empinfo/updateWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message code="hr.viewWorkInfo.title.WORK_EXPERIENCE" />
									<!--工作经历-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbarInfogongzuo}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_START_DATE" />
													<!--开始时间-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													<!--结束时间-->
												</th>
												<th width="80">
													<spring:message code="liang.hr.viewTranslate.title.AGE_LIMIT" />
													<!-- 年限 -->
												</th>
												<th width="80">
													<spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
													<!--工作单位-->
												</th>
												<th width="80">
													<spring:message code="hr.viewWorkInfo.title.DUTY" />
													<!--负责业务-->
												</th>
												<th width="80">
													<spring:message code="hr.viewWorkInfo.title.POSITION" />
													<!--职位-->
												</th>
												<th width="80">
													<spring:message code="liang.hr.viewWorkInfo.title.WORK_ADDRESS" />
													<!-- 工作地 -->
												</th>
												
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${workExperienceList}" var="item"
												varStatus="i">

												<tr target="personId" rel="${item.HEALTH_NO}">
													<td>
														${item.START_DATE}
													</td>
													<td>
														${item.END_DATE}
													</td>
													<td>
														<c:if test="${item.YEAR_AGE_LIMIT ne 0 and item.YEAR_AGE_LIMIT ne null }">${item.YEAR_AGE_LIMIT}<spring:message code="liang.hr.viewWorkInfo.title.YEAR" /></c:if>
														<c:if test="${item.MONTH_AGE_LIMIT ne 0 and item.MONTH_AGE_LIMIT ne null}">${item.MONTH_AGE_LIMIT}<spring:message code="liang.hr.viewWorkInfo.title.MONTH" /></c:if>
													</td>
													<td>
														${item.CPNY_NAME}
													</td>
													<td>
														${item.DUTY}
													</td>
													<td>
														${item.POSITION}
													</td>
													<td>
														${item.WORK_ADDRESS}
													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '2545' }">
					<div>
						<!-- 培训信息 -->
						<div style="display: block;" id="displaycheckbox_6">
							<!--培训信息-->
							<c:set value="${i.index}" var="tabsSelected" />
							
							<c:set value="/hrm/empinfo/viewTraining" var="turn_to_url" />
							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1250" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
									value="/hrm/empinfo/viewTrainingInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
									var="add_Url" />
	
	
	
								<c:set value="850" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
								<c:set
									value="/hrm/empinfo/deleteTraining?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="delete_Url" />
	
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1250" var="edit_width" />
								<c:set value="440" var="edit_height" />
	
								<c:set
									value="/hrm/empinfo/updateTrainingInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message
										code="hr.viewTraining.title.NAVIGATION_TRAINING" />
									<!--培训信息-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbarInfopeixun}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="80">
													<spring:message code="hr.viewTraining.title.COURSE_NAME" />
													<!--课程名-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_START_DATE" />
													<!--开始日期-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTranslate.title.PUBLIC_END_DATE" />
													<!--结束日期-->
												</th>
												<th width="80">
													<spring:message
														code="hr.viewTraining.title.INSTITUTION_NAME" />
													<!--培训机关-->
												</th>
												<th width="80">
													<spring:message code="hr.viewTraining.title.COURSE_FEE" />
													<!--课程费用-->
												</th>
												<th width="80">
													<spring:message code="liang.hr.viewTraining.title.TRAINING_METHOD" />
													<!--培训方法-->
												</th>
												<th width="80">
													<spring:message code="liang.hr.viewTraining.title.REMARKS" />
													<!--备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${trainingInfoList}" var="item"
												varStatus="i">

												<tr target="trainNo" rel="${item.TRAIN_NO}">
													<td>
														${item.COURSE_NAME}
													</td>
													<td>
														${item.START_DATE}
													</td>
													<td>
														${item.END_DATE}
													</td>
													<td>
														${item.INSTITUTION_NAME}
													</td>
													<td>
														<fmt:formatNumber value="${item.COURSE_FEE}"
															pattern="#,##0.00" />
													</td>
													<td>${item.TRAINING_METHOD_NAME}</td>
													<td>${item.REMARKS}</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				
				
				
				<c:if test="${menu.MENU_NO eq '123190' }">
					<div>
						<!-- 家人联系 -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_7">
							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1250" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
								value="/hrm/empinfo/viewHomeRelationInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
								var="add_Url" />
	
								<c:set value="1200" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
	
								<c:set
								value="/hrm/empinfo/deleteHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="delete_Url" />
	
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1200" var="edit_width" />
								<c:set value="440" var="edit_height" />
								<c:set
								value="/hrm/empinfo/updateHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message code="hr.viewRelation.title.FAMILY_RELATIONS" />
									<!--家人关系-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbarInfoshehui}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
													<!--关系-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
													<!--姓名-->
												</th>
												<%--<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
													<!--身份证号码-->
												</th>
												--%><th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
													<!--出生日期-->
												</th>
												<%--<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
													<!--地址-->
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PHONE" />
													<!--联系电话-->
												</th>
												
												<th width="100">
													<spring:message code="hr.viewRelation.title.LIVE_YN_NAME" />
													<!--一起居住与否-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME" />
													<!--是否紧急联系人-->
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PERSON_ID" />
													<!--亲属员工号-->
												</th>
											--%>
												<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.FAMILY_YINYANGRILI" />
													<!--阴/阳历区分-->
												</th>
											
												<th width="100">
													<spring:message
														code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME" />
													<!--单位名称-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${homeRelationList}" var="item" varStatus="i">

												<tr target="sid" rel="${item.FAMILY_NO}">
													<td>
														${item.FAM_TYPE_NAME}
													</td>
													<td>
														${item.FAM_NAME}
													</td>
													<%--<td>
														${item.FAM_IDCARD}
													</td>
													--%><td>
														${item.FAM_BORNDATE}
													</td>
													<%--<td>
														${item.FAM_ADDRESS}
													</td>
													<td>
														${item.FAM_PHONE}
													</td>
													
													<td>
														${item.LIVE_YN_NAME}
													</td>
													<td>
														${item.EMERGENCY_CONTACT_YN_NAME}
													</td>
													<td>
														${item.FAM_PERSON_ID}
													</td>
												--%>
													<td>
														${item.FAM_YINYANGLINAME }
													</td>
													<td>
														${item.FAM_COMPANY_NAME}
													</td>
													</tr>
											</c:forEach>

										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>
				</c:if>
				
				<c:if test="${menu.MENU_NO eq '2554' }">
					<div>
						<!-- 资格信息 -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_8">
							<c:set var="toolbarInfo" value="${toolbarInfozige}" />
							<!--   <c:set value="/hrm/empinfo/viewCompetence" var="turn_to_url" />  -->


							<c:if test="${isEssSystem ne '1'}">
							<c:set value="1250" var="add_width" />
							<c:set value="630" var="add_height" />
							<c:set value="dialog" var="add_tab" />
							<c:set
								value="/hrm/empinfo/viewCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="add_Url" />


							<c:set value="900" var="delete_width" />
							<c:set value="550" var="delete_height" />
							<c:set value="dialog" var="delete_tab" />
							<c:set value="0" var="delete_range" />
							<c:set value="0" var="delete_mask_exit" />
							<c:set value="true" var="delete_mask" />
							<c:set
								value="/hrm/empinfo/deleteCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="delete_Url" />

							<c:set value="dialog" var="edit_tab" />
							<c:set value="1200" var="edit_width" />
							<c:set value="600" var="edit_height" />
							<c:set
								value="/hrm/empinfo/updateCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
								var="edit_Url" />




							<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

							</c:if>

							<div class="panel">
								<h1>
									<spring:message code="hr.viewCompetence.title.CREDENTIALS" />
									<!--资格证书-->
								</h1>

								<div>

									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewCompetence.title.QUAL_NAME" />
													<!--资格证名称-->
												</th>
												<!--<th width="100">
													<spring:message code="hr.viewCompetence.title.QUAL_CARD_NO" />
													证件号
												</th>
												--><th width="100">
													<spring:message
														code="hr.viewCompetence.title.QUAL_LEVEL_NAME" />
													<!--证件级别-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewCompetence.title.QUAL_INSTITUTE" />
													<!--发证处-->
												</th>
												<!--<th width="100">
													<spring:message
														code="hr.viewCompetence.title.ACQUISITION_NAME" />
													取得方式
												</th>
												--><th width="100">
													<spring:message
														code="hr.viewCompetence.title.DATE_OBTAINED" />
													<!--取证日期-->
												</th>
												<th width="100">
													<spring:message
														code="hr.viewCompetence.title.VALIDITY_DATE" />
													<!--有效期-->
												</th>
												<th width="100">
														<spring:message
														code="heran.hr.viewLanguage.PAYMENT_Y_N" />
														<!-- 支付津贴与否 -->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${qualificationList}" var="item"
												varStatus="i">

												<tr target="sid" rel="${item.QUAL_NO}">
													<td>
														${item.QUAL_NAME}
													</td>
													<!--<td>
														${item.QUAL_CARD_NO}
													</td>
													--><td>
														${item.QUAL_LEVEL_NAME}
													</td>
													<td>
														${item.QUAL_INSTITUTE}
													</td>
													<!--<td>
														${item.ACQUISITION_NAME}
													</td>
													-->
													<td>
														${item.DATE_OBTAINED}
													</td>
													<td>
														${item.VALIDITY_DATE }
													</td>
													
													<td>
														${item.PAYMENTALLOWANCENNAME}
													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
							
						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '123191' }">
					<div>
						<!--紧急联系   -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_9">
							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1250" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
									value="/hrm/empinfo/viewFamilyInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
									var="add_Url" />
	
								<c:set value="1200" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
	
	
								<c:set
									value="/hrm/empinfo/deleteFamily?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="delete_Url" />
	
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1200" var="edit_width" />
								<c:set value="440" var="edit_height" />
								<c:set
									value="/hrm/empinfo/updateFamilyInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message code="heran.hr.viewLanguage.EMERGCENCYCONTACT" />
									<!--紧急联系-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbarInfoshehui}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
													<!--关系-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
													<!--姓名-->
												</th>
												<!--<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
													身份证号码
												</th>
												<th width="100">
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
													出生日期
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_ADDRESS" />
													地址
												</th>
												-->
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PHONE" />
													<!--联系电话-->
												</th>
												<!--<th width="100">
													<spring:message
														code="hr.viewRelation.title.FAM_COMPANY_NAME" />
													工作单位/职(岗)位
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.LIVE_YN_NAME" />
													一起居住与否
												</th>
												<th width="100">
													<spring:message
														code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME" />
													是否紧急联系人
												</th>
												<th width="100">
													<spring:message code="hr.viewRelation.title.FAM_PERSON_ID" />
													亲属员工号
												</th>
											--></tr>
										</thead>
										<tbody>
											<c:forEach items="${familyList}" var="item" varStatus="i">

												<tr target="sid" rel="${item.FAMILY_NO}">
													<td>
														${item.FAM_TYPE_NAME}
													</td>
													<td>
														${item.FAM_NAME}
													</td>
													<!--<td>
														${item.FAM_IDCARD}
													</td>
													<td>
														${item.FAM_BORNDATE}
													</td>
													<td>
														${item.FAM_ADDRESS}
													</td>
													--><td>
														${item.FAM_PHONE}
													</td>
													<!--<td>
														${item.FAM_COMPANY_NAME}
													</td>
													<td>
														${item.LIVE_YN_NAME}
													</td>
													<td>
														${item.EMERGENCY_CONTACT_YN_NAME}
													</td>
													<td>
														${item.FAM_PERSON_ID}
													</td>
												--></tr>

											</c:forEach>

										</tbody>
									</table>

								</div>
							</div>

						</div>
						</div>
					
				</c:if>
				<c:if test="${menu.MENU_NO eq '2542' }">
					<div>
						<!-- 评价信息 -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_10">
							<div class="panel">

								<h1>
									<spring:message
										code="hr.viewEvaluate.title.EVALUATEIMFORMATION" />
									<!--评价信息-->
								</h1>
								<%
									// request.setAttribute( "toolbarInfo", request.getAttribute("toolbarInfopingjia")) ;
								%>
								<c:set value="/hrm/empinfo/viewEvaluate" var="turn_to_url" />
								<c:if test="${isEssSystem ne '1'}">
									<c:set value="900" var="add_width" />
									<c:set value="400" var="add_height" />
									<c:set value="dialog" var="add_tab" />
									<c:set
										value="/hrm/empinfo/viewEvsInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
										var="add_Url" />
	
									<c:set value="900" var="delete_width" />
									<c:set value="400" var="delete_height" />
	
									<c:set value="0" var="delete_mask_exit" />
									<c:set value="true" var="delete_mask" />
									<c:set value="0" var="delete_range" />
									<c:set value="dialog" var="delete_tab" />
									<c:set
										value="/hrm/empinfo/deleteEvs?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
										var="delete_Url" />
	
	
									<c:set value="1200" var="edit_width" />
									<c:set value="400" var="edit_height" />
									<c:set
										value="/hrm/empinfo/viewEditEvsInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
										var="edit_Url" />
									<c:set var="toolbarInfo" value="${toolbarInfopingjia}" />
	
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="100">
													<spring:message code="hr.viewEvaluate.title.EV_PERIOD" />
													<!--评价期间-->
												</th>
												
												<th width="100">
													<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME" />
													<!--评价类型-->
												</th>
												<th width="100">
													<spring:message code="hr.viewEvaluate.title.EV_MARK" />
													<!--评价分数-->
												</th>
												<th width="100">
													<spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME" />
													<!--评价等级-->
												</th>
												<th width="100">
													<spring:message code="hr.viewSuggestion.title.Suggestion" />
													<!--意见-->
												</th>
												<th width="100">
													<spring:message code="hr.viewFinalSequence.title.FinalSequence" />
													<!--最终顺位-->
												</th>
												<th width="100">
													<spring:message code="hr.viewTotalPeople.title.TotalPeople" />
													<!--总职级员人数-->
												</th>
												<th width="100">
													<spring:message code="hr.viewPromote.title.REMARK" />
													<!--备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${EvsInfo}" var="item" varStatus="i">

												<tr target="PERSON_ID"
													rel="${item.PERSON_ID}&EV_PERIOD=${item.EV_PERIOD}">
													<td>
														${item.EV_PERIOD}
													</td>
													
													<td>
														${item.EV_TYPE_NAME}
													</td>
													<td>
														${item.EV_MARK}
													</td>
													<td>
														${item.EV_GRADE_NAME}
													</td>
													<td>
														${item.SUGGESTION }
													</td>
													<td>
														${item.FINAL_SEQUENCE }
													</td>
													<td>
														${item.TOTAL_PEOPLE }
													</td>
													<td>
														${item.EV_REMARK}
													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				
				
				<c:if test="${menu.MENU_NO eq '123192' }">
					<div>
						<!-- 外国语 -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_11">
							<div class="panel">
								<h1>
									<spring:message code="hr.viewCompetence.title.FOREIGN_LANGUAGE" />
									<!--外国语-->
								</h1>
								<%
									// request.setAttribute( "toolbarInfo", request.getAttribute("toolbarInfopingjia")) ;
								%>
								<c:set value="/hrm/empinfo/viewEvaluate" var="turn_to_url" />
								<c:if test="${isEssSystem ne '1'}">
									<c:set value="900" var="add_width" />
									<c:set value="400" var="add_height" />
									<c:set value="dialog" var="add_tab" />
									<c:set
										value="/hrm/empinfo/viewLanguageLevelInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
										var="add_Url" />
	
									<c:set value="900" var="delete_width" />
									<c:set value="400" var="delete_height" />
	
									<c:set value="0" var="delete_mask_exit" />
									<c:set value="true" var="delete_mask" />
									<c:set value="0" var="delete_range" />
									<c:set value="dialog" var="delete_tab" />
									<c:set
										value="/hrm/empinfo/deleteLanguage?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
										var="delete_Url" />
	
	
									<c:set value="900" var="edit_width" />
									<c:set value="400" var="edit_height" />
									<c:set
										value="/hrm/empinfo/updateLanguage?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
										var="edit_Url" />
									<c:set var="toolbarInfo" value="${toolbarInfopingjia}" />
	
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<%--<th width="100">
													<spring:message
														code="hr.viewCompetence.title.LANGUAGE_TYPE_NAME" />
													<!--语言类型-->
												</th>
												--%>
												<th width="100">
													<spring:message
														code="hr.viewLanguage.KAOSHIDATE" />
													<!--考试日期-->
												</th>
												<th width="100">
													<spring:message code="hr.viewCompetence.title.EXAM_NAME" />
													<!--考试名-->
												</th>
												<%--<th width="100">
													<spring:message
														code="hr.viewCompetence.title.QUALIFICATION_NAME" />
													<!--证书名称-->
												</th>
												--%><th width="100">
													<spring:message
														code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME" />
													<!--等级-->
												</th>
												<th width="100">
													<spring:message code="hr.viewCompetence.title.MARK" />
													<!--分数-->
												</th>
												
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${languageLevelList}" var="item"
												varStatus="i">

												<tr target="sid" rel="${item.LANGUAGE_NO}">
													<td>
														${fn:substring(item.KAOSHIDATE,0, 10)}
													</td>
													<%--<td>
														${item.LANGUAGE_TYPE_NAME}
													</td>
													--%><td>
														${item.EXAM_NAME}
													</td>
													<%--<td>
														${item.QUALIFICATION_NAME}
													</td>
													--%><td>
														${item.LANGUAGE_LEVEL_NAME}
													</td>
													<td>
														${item.MARK}
													</td>
													
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '123193' }">
					<div>
						<!-- 残疾 信息-->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_12">

							<!--<c:set value="/hrm/empinfo/viewHealth" var="turn_to_url" />-->


							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1100" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
									value="/hrm/empinfo/viewDisabledInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
									var="add_Url" />
	
								<c:set value="800" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
								<c:set
									value="/hrm/empinfo/deleteDisabled?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="delete_Url" />
	
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1100" var="edit_width" />
								<c:set value="440" var="edit_height" />
	
								<c:set
									value="/hrm/empinfo/updateDisabledInfo?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message code="hr.viewDisabled.title.DISABLED_INFO" />
									<!--残疾信息-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbarInfodisability}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<th width="80">
													<spring:message code="hr.viewDisabled.title.DISABLED_TYPE" />
													<!--残疾类型-->
												</th>
												<th width="80">
													<spring:message code="liang.hr.viewDisabled.title.DISABILITY_LEVEL" />
													<!--  残疾程度-->
												</th>
												<th width="80">
													<spring:message code="hr.viewDisabled.title.DISABLED_AFFIRM_DATE" />
													<!--  残疾认定日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewDisabled.title.DISABLED_DATE_CLOSED" />
													<!-- 残疾结束日期 -->
												</th>
												<th width="80">
													<spring:message code="hr.viewDisabled.title.DISABLED_REMARK" />
													<!--  备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${disabilityinfoList}" var="item" varStatus="i">

												<tr target="healthNo" rel="${item.T_ID}">
													<td>
														${item.DISABILITY_TYPE_NAME}
													</td>
													<td>
														${item.DISABILITY_LEVEL_NAME}
													</td>
													<td>
														${item.ADDDATE}
													</td>
													<td>
														${item.QUITDATE}
													</td>
													<td>
														${item.REMARK}
													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${menu.MENU_NO eq '123194' }">
					<div>
						<!-- 工会 -->
						<c:set value="${i.index}" var="tabsSelected" />
						<div style="display: block;" id="displaycheckbox_13">

							<c:set value="/hrm/empinfo/viewTrade" var="turn_to_url" />


							<c:if test="${isEssSystem ne '1'}">
								<c:set value="1100" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab" />
								<c:set
									value="/hrm/empinfo/viewTradeunionInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }"
									var="add_Url" />
	
								<c:set value="800" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab" />
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit" />
								<c:set value="true" var="delete_mask" />
								<c:set
									value="/hrm/empinfo/deleteTradeunion?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="delete_Url" />
								<c:set value="dialog" var="edit_tab" />
								<c:set value="1100" var="edit_width" />
								<c:set value="440" var="edit_height" />
								<c:set
									value="/hrm/empinfo/updateTradeunion?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }"
									var="edit_Url" />
							</c:if>
							<div class="panel">
								<h1>
									<spring:message code="hr.viewHealth.title.TRADEUNIONTITLE" />
									<!--工会信息-->
								</h1>
								<c:if test="${isEssSystem ne '1'}">
									<c:set var="toolbarInfo" value="${toolbartradeunion}" />
									<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
								</c:if>
								<div>
									<table class="table" width="101%">
										<thead>
											<tr>
												<%--<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
													<!--序号-->
												</th>
												--%>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE" />
													<!--公会内部职责-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" />
													<!--入会日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" />
													<!--退会日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" />
													<!--备注-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${tradeUnionList}" var="item" varStatus="i">

												<tr target="healthNo" rel="${item.HEALTH_NO}">
													<%--<td>
														${i.count}
													</td>
													--%><td>${item.RES}</td>
													<td  width="80"> ${fn:substring(item.ADDDATE,0, 10)}</td>
													<td  width="80"> ${fn:substring(item.QUITDATE,0, 10)}</td>
													<td>${item.REMARK}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
