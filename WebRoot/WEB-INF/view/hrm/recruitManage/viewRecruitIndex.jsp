<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div style="height:529px;line-height:529px;overflow:auto;overflow-x:hidden;background:url('/resources/images/recruit_index.jpg') no-repeat;">
		<div style="width:170px;margin-left:265px;margin-top:101px;height:300px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/recruitManage/viewRecruitList?OBJECT=0','pageNum=1&amp;menuNo=14013649&amp;navTabId=hr0202','hr0202','<spring:message code="hrm.empinfo.RECRUITMENT_ORDER.Z" />');">
			<font color="white"><spring:message code="hrm.empinfo.RECRUITMENT_ORDER.Z" /><!-- 录用发令 --></font></a></div>
			<div style="padding:3px;margin-top:47px;"><a style="text-decoration:none ;" href="/hrm/empinfo/viewEducationMatter" target="navTab" rel="viewEducationMatter" title="<spring:message code="hrm.recruitManage.Education_matters" />">
			<font color="white"><spring:message code="hrm.empinfo.INPUT_EDUCATION_INFORMATION.Z" /><!-- 输入学历信息 --></font></a></div>
			<div style="padding:3px;margin-top:47px;"><a style="text-decoration:none ;" href="/hrm/empinfo/viewFamily" target="navTab" rel="viewFamily" title="<spring:message code="hrm.empinfo.FAMILY_MATTERS.Z" />">
			<font color="white"><spring:message code="hrm.empinfo.INPUT_FAMILY_INFORMATTION.Z" /><!-- 输入家庭成员事项 --></font></a></div>
			<div style="padding:3px;margin-top:47px;"><a style="text-decoration:none ;" href="/hrm/empinfo/viewExperiencePoint" target="navTab" rel="viewExperiencePoint" title="<spring:message code="hrm.recruitManage.Experience_issues" />">
			<font color="white"><spring:message code="hrm.empinfo.INPUT_EXPERIENCE_INFORMATION.Z" /><!-- 输入经历事项 --></font></a></div>
			<div style="padding:3px;margin-top:46px;"><a href="#" style="text-decoration:none ;" >
			<font color="white"><spring:message code="hrm.empinfo.NEW_EMPLOYEE.Z" /><!-- 生成新员工 --></font></a></div>
		</div>
	</div>
</div>