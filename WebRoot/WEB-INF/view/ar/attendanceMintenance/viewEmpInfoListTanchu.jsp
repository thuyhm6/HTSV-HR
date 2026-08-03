<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function changeUrlToNavTabNum(url, param, menu_code, menu_name) {
	url = encodeURI(encodeURI(url));
	alert(url);
	navTabNum(url, '', menu_code, menu_name);
}
$(document).ready(function() {
	if ('${totalCount}' == 1) {
		$('#xp').dblclick();
	}
});
</script>
<div class="pageHeader">
	<form id="viewEmpInfo" onsubmit="return dwzSearch(this,'dialog');"
		action="/ar/attendanceMintenance/viewEmpInfoListTanchu?dataSearch=${dataSearch}&firstFlag=N&searchChange=${searchChange }&defaultCpny=${defaultCpny }"
		method="post" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门 --><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td>
						<ait:deptList name="TANCHUDEPTNO" limit="${limit}"
							id="viewEmpInfoListTanchu_search" />
						<ait:deptTreeIcon name="TANCHUDEPTNO" limit="${limit}"
							id="viewEmpInfoListTanchu_search" selected="${TANCHUDEPTNO}" />
					</td>
					<td>
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<%-- <td>在职状态</td>
		<td>
			<ait:SelectSyCodeByCpnyID id="TANCHUEMPOFFICE" name="TANCHUEMPOFFICE" parentNo="15118" selected="${TANCHUEMPOFFICE}" cnpyID="${defaultCpny}" limit="all"/>
		</td> --%>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" id="jiansuo">
									<spring:message code="public.title.search" />
									<!-- 检索 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="128" targetType="dialog">
		<thead>
			<tr>
				<th orderfield="empId">
					<!-- 员工号 -->
					<spring:message code="hr.viewPersonalInfo.title.EMPID" />
				</th>
				<th orderfield="empName">
					<!-- 员工姓名 -->
					<spring:message code="public.title.name" />
				</th>
				<th orderfield="empDept">
					<!-- 部门 -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
				</th>
				<!-- <th orderfield="empDept">人员类型</th> -->
				<th orderfield="empDept">
					<!-- 在职区分 -->
					<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" />
				</th>
				<%-- <th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th> --%>
			</tr>
		</thead>
		<c:if test="${searchChange=='viewPersonalInfo' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewPersonalInfo?pageNum=1&menuNo=125244&navTabId=hr2100&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','hr2100','<spring:message code='hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--综合简介-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewEmpInfo' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewEmpInfo?pageNum=1&menuNo=125244&navTabId=viewEmpInfo&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','viewEmpInfo','<spring:message code='hrm.empinfo.COMPANYINFORMATIONIN' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--员工基础信息-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewCardInfoList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewCardInfoList?pageNum=1&menuNo=125244&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','hr3101','<spring:message code='hrm.empinfo.personnel_card' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--人事卡-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewHrPersonalInfo' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewHrPersonalInfo?pageNum=1&menuNo=125244&navTabId=viewHrPersonalInfo&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','viewHrPersonalInfo','<spring:message code='org.title.PERSON_INFO' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--个人信息-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewEmergencyAddress' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewEmergencyAddress?pageNum=1&menuNo=125244&navTabId=viewEmergencyAddress&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewEmergencyAddress','<spring:message code='ar.viewEmpInfoListTanchu.JINJILIANXIDIZHI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--紧急联系地址-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewAddressMatters' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewAddressMatters?pageNum=1&menuNo=125244&navTabId=viewAddressMatters&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewAddressMatters','<spring:message code='hrm.empinfo.FAM_ADDRESS_TYPE' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--地址类型-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewFamily' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewFamily?pageNum=1&menuNo=125244&navTabId=viewFamily&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewFamily','<spring:message code='hrm.empinfo.FAMILY_MATTERS.Z' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--家庭事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='hr0204' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewStartPoint?pageNum=1&menuNo=125244&navTabId=hr0204&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr0204','<spring:message code='hrm.empinfo.The_person' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--个人发令-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewExperiencePoint' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewExperiencePoint?pageNum=1&menuNo=125244&navTabId=viewExperiencePoint&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewExperiencePoint','<spring:message code='hrm.recruitManage.Experience_issues' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--经历事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>


				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewEducationMatter' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewEducationMatter?pageNum=1&menuNo=125244&navTabId=viewEducationMatter&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewEducationMatter','<spring:message code='hrm.recruitManage.Education_matters' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--学历事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewBidMatter' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewBidMatter?pageNum=1&menuNo=125244&navTabId=viewBidMatter&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewBidMatter','<spring:message code='ess.empInfo.qualifications_matter' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--资格事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>


		<c:if test="${searchChange=='viewForeignLanguage' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewForeignLanguage?pageNum=1&menuNo=125244&navTabId=viewForeignLanguage&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewForeignLanguage','<spring:message code='hrm.empinfo.Foreign_language_ability' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--外语能力-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewRecognition' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewRecognition?pageNum=1&menuNo=125244&navTabId=viewRecognition&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewRecognition','<spring:message code='ess.empInfo.commend_matter' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--表彰事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewPunishment' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewPunishment?pageNum=1&menuNo=125244&navTabId=viewPunishment&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewPunishment','<spring:message code='ar.viewEmpInfoListTanchu.CHENGFASHIXIANG.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--惩罚事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewSpecialMatter' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewSpecialMatter?pageNum=1&menuNo=125244&navTabId=viewSpecialMatter&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewSpecialMatter','<spring:message code='ar.viewEmpInfoListTanchu.TEJISHIXIANG.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--特记事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewPassportPerson' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewPassportPerson?pageNum=1&menuNo=125244&navTabId=viewPassportPerson&flag=1&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewPassportPerson','<spring:message code='hrm.empinfo.Personal_passport_information' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--个人护照信息-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewPassportFamily' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewPassportFamily?pageNum=1&menuNo=125244&navTabId=viewPassportFamily&flag=2&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewPassportFamily','<spring:message code='hrm.empinfo.FAMILY_PASSPORT_INFORMATION' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--家人护照信息-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>


			</tbody>
		</c:if>

		<c:if test="${searchChange=='emergencyAddressSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/emergencyAddressSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=emergencyAddressSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3601','<spring:message code='ar.viewEmpInfoListTanchu.JINJILIANXIRENSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--紧急联系人搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>
		<!-- 个人支付工资明细 -->
		<c:if test="${searchChange=='viewPaResultList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/pa/workManagement/viewPaResultList?PERSON_ID=${keeper.PERSON_ID}&KEY001=${keeper.EMPID}&KEY002=${keeper.LOCAL_NAME}&KEY=${KEY }&PAY_DATE_PRO=${PAY_DATE_PRO }&PAY_DATE=${PAY_DATE }&SALARY_DISTIN=${SALARY_DISTIN}&SALARY_DISTIN_NO=${SALARY_DISTIN_NO}','','pa0134','<spring:message code='ar.viewEmpInfoListTanchu.GERENZHIFUMINGXI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--个人支付明细-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='familySearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/familySearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=familySearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3603','<spring:message code='ar.viewEmpInfoListTanchu.JIATINGSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--家庭搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='experienceSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/experienceSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=experienceSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3604','<spring:message code='ar.viewEmpInfoListTanchu.JINGLISOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--经历搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='educationSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/educationSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=educationSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3606','<spring:message code='ar.viewEmpInfoListTanchu.XUELISOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--学历搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='addressSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/addressSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=addressSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3602','<spring:message code='ar.viewEmpInfoListTanchu.DIZHISOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--地址搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='retireSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/retireSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=retireSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3605','<spring:message code='ar.viewEmpInfoListTanchu.TUIZHISOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--退职搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='gradeSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/gradeSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=gradeSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3607','<spring:message code='ar.viewEmpInfoListTanchu.ZHIJISOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--职级搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='recognitionSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/recognitionSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=recognitionSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3608','<spring:message code='ar.viewEmpInfoListTanchu.BIAOZHANGSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--表彰搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='punishmentSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/punishmentSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=punishmentSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3609','<spring:message code='ar.viewEmpInfoListTanchu.CHENGJIESOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--惩戒搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>

		<c:if test="${searchChange=='bidSearch' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/bidSearch?dataSearch=${dataSearch}&pageNum=1&menuNo=125244&navTabId=bidSearch&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','hr3610','<spring:message code='ar.viewEmpInfoListTanchu.ZIGESOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--资格搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>


		<c:if test="${searchChange=='viewExperienceList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/recruitManage/viewExperienceList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','hr0207','<spring:message code='ar.viewEmpInfoListTanchu.FALINGJIANSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--发令检索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewVacEmpList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceSettings/viewVacEmpList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0232','<spring:message code='ar.viewArNavigationPage.NIANJIASHIYONGGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--年假使用管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewTxEmpList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceSettings/viewTxEmpList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0233','<spring:message code='ar.viewEmpInfoListTanchu.DAOXIUSHIYONGGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休使用管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewTxEmpTSTOList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceSettings/viewTxEmpTSTOList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&AR_MONTH=${AR_MONTH}','','ar3421','<spring:message code='ar.viewEmpInfoListTanchu.DAOXIUSHIYONGGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休使用管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewAttendanceManagentForSerchInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewAttendanceManagentForSerchInfoList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&APPLY_CODE=${APPLY_CODE}&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0231','<spring:message code='ar.viewEmpInfoListTanchu.KAOQINSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--考勤搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewAdjustLeaveTSTOBatchList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApply/viewAdjustLeaveTSTOBatchList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&APPLY_CODE=${APPLY_CODE}&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ess3803','<spring:message code='ar.viewEmpInfoListTanchu.DAOXIUSHENQING.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休申请-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewCoordApplyOtInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApply/viewCoordApplyOtInfoList?firstFlag=N&ADJSTYN=0&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ess3405','<spring:message code='ar.viewEmpInfoListTanchu.JIABANCHAXUN.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--加班查询-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewCoordApplyAdjustInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApply/viewCoordApplyAdjustInfoList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ess3420','<spring:message code='ar.viewEmpInfoListTanchu.DAOXIUCHAXUN.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休查询-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewCoordApplyAttendanceInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApplyAttendance/viewCoordApplyAttendanceInfoList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ess3406','<spring:message code='ar.viewAttendanceManagentForSerchInfo.KAOQINCHAXUN.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--考勤查询-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewPersonOverTimeLimtList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApply/viewPersonOverTimeLimtList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&AR_MONTH=${AR_MONTH}','','ar0704','<spring:message code='ar.viewEmpInfoListTanchu.GERENJIABANXIANZHI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--个人加班限制-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewPiciOtAffirmLBatchList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApply/viewPiciOtAffirmLBatchList?firstFlag=N&attenState=all&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&APPLY_CODE=${APPLY_CODE}&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ess3403','<spring:message code='ess.infoApply.title.overtimeApply' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--加班申请-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewApplyAttenanceManagentInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList?firstFlag=N&deleteYN=Y&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&APPLY_CODE=${APPLY_CODE}&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0230','<spring:message code='ar.viewArNavigationPage.KAOQINGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--考勤管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewArAdjustHolidayManagent'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewArAdjustHolidayManagent?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0706','<spring:message code='ar.viewArAdjustHolidayManagent.DAOXIUGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewSearchAdjustHolidayInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewSearchAdjustHolidayInfoList?firstFlag=N&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0707','<spring:message code='ar.viewSearchAdjustHolidayInfoList.DAOXIUSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--倒休搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewArOvertimeManagent'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewArOvertimeManagent?firstFlag=N&ADJSTYN=0&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0701','<spring:message code='ar.viewArNavigationPage.JIABANGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--加班管理-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		<c:if test="${searchChange=='viewSearchApplyOtInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewSearchApplyOtInfoList?firstFlag=N&ADJSTYN=0&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&FROM_DATE=${FROM_DATE}&TO_DATE=${TO_DATE}','','ar0702','<spring:message code='ar.viewSearchApplyOtInfoList.JIABANSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--加班搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewApplyAttenanceBatchInfoList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?firstFlag=N&deleteYN=Y&ADJSTYN=0&attenState=all&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&applyBatchdate=${applyBatchdate}','','ess3401','<spring:message code='ess.infoApply.attendance_applicated' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--考勤申请-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewSupervisorInfoList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewSupervisorInfoList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','hr3203','Supervisor<spring:message code='ar.viewEmpInfoListTanchu.LVLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--Supervisor履历-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>



		<c:if test="${searchChange=='viewArBaseEmpInfoList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/arShiftGroupManagement/viewArBaseEmpInfoList?firstView=1&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0606','<spring:message code='ar.viewArBaseEmpInfoList.KAOQINJIBENSHIXIANG.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--考勤基本事项-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>


		<c:if test="${searchChange=='viewArShiftRecordCheckList'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/arShiftGroupManagement/viewArShiftRecordCheckList?firstView=1&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0607','<spring:message code='ar.viewEmpInfoListTanchu.BANZULVLICHAXUN.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--班组履历的查询-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>


		<c:if test="${searchChange=='viewArCardRecordDay'}">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewArCardRecordDay?firstFlag=1&menuNo=14013647&navTabId=ar1234&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar1234','<spring:message code='ar.viewArNavigationPage.CHURUSHUJUSOUSUO.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--出入数据搜索-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewSearchOtInfo' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewSearchOtInfo?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0703','<spring:message code='ar.viewEmpInfoListTanchu.ZONGHEGONGSHISHIYUANJIABAN.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})"><!--综合工时人员加班-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<c:if test="${searchChange=='viewArCardRecord' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/ar/attendanceMintenance/viewArCardRecord?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','ar0801','<spring:message code='ar.viewArNavigationPage.CHURUJILUGUANLI.b' />');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
							})"><!--出入记录维护-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>

		<!-- 工资模块放大镜  勿误删 -->
		<c:if test="${searchChange=='detaiPersonCountInfo' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/pa/workManagement/detailPersonCountInfo?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','pa1014','<spring:message code='pa.viewPaMain.GONGZIXIANGXIMINGXI.C' />');javascript:
							$.bringBack({ 
							   PERSON_ID:'${keeper.PERSON_ID}',
								
							})"><!--工资详细明细-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>


		<c:if test="${searchChange=='detailmonthCountInfoLeft' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/pa/workManagement/detailmonthCountInfoLeft?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','pa0132','<spring:message code='pa.viewPaMain.YUEGONGZIMINGXI.C' />');javascript:
							$.bringBack({ 
							   PERSON_ID:'${keeper.PERSON_ID}',
								
							})"><!--月工资明细-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewEvsTargetInfoList' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/evs/manage/viewEvsTargetInfoList?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&seach_RESUME_SEQ=${RESUME_SEQ}&seach_evsType=${evsType }','','evs0106','<spring:message code='ar.viewEmpInfoListTanchu.YEJIKAOHEMUBIAODENGJIZHUANGTAI.b' />');javascript:
							$.bringBack({ 
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
							})"><!--业绩考核目标登记状态-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewEvsResult' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/evs/manage/viewEvsResult?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&seach_RESUME_SEQ=${RESUME_SEQ}&seach_evsType=${evsType }','','evs0107','<spring:message code='ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b' />');javascript:
							$.bringBack({ 
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
							})"><!--业绩考评结果-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewEvsResultHistory' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/evs/manage/viewEvsResultHistory?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','viewEvsResultHistory','<spring:message code='ar.viewEmpInfoListTanchu.PINGJIALISHIXINXI.b' />');javascript:
							$.bringBack({ 
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
							})"><!--评价历史信息-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewEvsResultPer' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/evs/manage/viewEvsResultPer?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','viewEvsResultPer','<spring:message code='ar.viewEmpInfoListTanchu.KAOPINGJIEGUO.b' />');javascript:
							$.bringBack({ 
								person_id:'${keeper.PERSON_ID}', 
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
							})"><!--考评结果-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='detailYearCountInfoLeft' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/pa/workManagement/detailYearCountInfoLeft?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','','pa0133','<spring:message code='pa.viewPaMain.NIANGONGZIMINGXI.C' />');javascript:
							$.bringBack({ 
							   PERSON_ID:'${keeper.PERSON_ID}',
								
							})"><!--年工资明细-->
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		
		<c:if test="${searchChange=='viewPregnantManagement' }">
			<tbody>
				<c:forEach items="${empInfo}" var="keeper">
					<tr id='xp'
						ondblclick="changeUrlToNavTabNum('/hrm/empinfo/viewPregnantManagement?pageNum=1&menuNo=125244&navTabId=viewPregnantManagement&PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }&LOCAL_TITLE=${keeper.LOCAL_NAME } / ${keeper.EMPID } / ${keeper.POST_GRADE_NO_NAME}(${personInfo.RANK_STATISTICS_NAME}) / ${keeper.COST_CENTER} / ${keeper.EMP_OFFICE_NAME_SECOND }','','viewPregnantManagement','PregnantInfoMgmt');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})">
						<td>
							${keeper.EMPID}
						</td>
						<td>
							${keeper.LOCAL_NAME}
						</td>
						<td>
							${keeper.DEPT_NAME}
						</td>
						<td>
							${keeper.EMP_OFFICE_NAME}
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</c:if>
		



	</table>
	<form id="pagerForm" method="post"
		action="/ar/attendanceMintenance/viewEmpInfoListTanchu?firstFlag=N&searchChange=${searchChange }&defaultCpny=${defaultCpny }">

		<div class="panelBar">
			<div class="pages">
				<span>
					<!-- 显示 -->
					<spring:message code="public.title.view" />
				</span>
				<select class="combox" name="numPerPage"
					onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"
						<c:if test="${numPerPage == 10 }" >selected</c:if>>
						10
					</option>
					<option value="20"
						<c:if test="${numPerPage == 20 }" >selected</c:if>>
						20
					</option>
					<option value="30"
						<c:if test="${numPerPage == 30 }" >selected</c:if>>
						30
					</option>
				</select>
				<span>
					<!-- 条 -->
					<spring:message code="public.title.tiao" />，<!-- 共 -->
					<spring:message code="public.title.gong" />${totalCount}<!-- 条 -->
					<spring:message code="public.title.tiao" />
				</span>
			</div>
			<div class="pagination" targetType="dialog"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				currentPage="${pageNum}"></div>
		</div>
	</form>
</div>
<script type="text/javascript">
</script>