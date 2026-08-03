<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
	    <div style="height:882px;line-height:700px;background:url('/resources/images/hr_main.jpg') no-repeat;">
	    <div style="width:230px;padding-left:30px;margin-top:230px;height:60px;float:left;">
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
	    		<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/approve/viewEssApplyInfo','pageNum=1&menuNo=14014333&navTabId=sy0501','sy0501','Info Change Request');">* <!-- 员工信息改变管理 -->Info Change Request</a></div>
	    	</c:if>
		</div>
		<div style="width:180px;margin-left:30px;margin-top:130px;height:120px;float:left;">
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewHrPersonalInfo?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewHrPersonalInfo','viewHrPersonalInfo','<spring:message code="hr.viewCondSql.title.GERENXINXI" />');">* <spring:message code="hrm.empinfo.Basic_matters" /> <!-- 基本事项 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewFamily?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewFamily','viewFamily','<spring:message code="hrm.empinfo.FAMILY_MATTERS.Z" />');">* <spring:message code="hrm.empinfo.FAMILY_MATTERS.Z" /> <!-- 家庭事项 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewEmergencyAddress?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewEmergencyAddress','viewEmergencyAddress','<spring:message code="hrm.empinfo.emergency_contact" />');">* <spring:message code="hrm.empinfo.emergency_contact" /> <!-- 紧急联系人 --></a></div>
				<%-- <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewSpecialMatter?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewSpecialMatter','viewSpecialMatter','<spring:message code="hr.viewAdditional.title.SPECIAL_MATTERS" />');">* <spring:message code="hr.viewAdditional.title.SPECIAL_MATTERS" /> <!-- 特殊事项 --></a></div> --%>
			</c:if>
		</div>
		<div style="width:180px;margin-left:50px;margin-top:130px;height:120px;float:left;">
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/recruitManage/viewRecruitList?OBJECT=0','pageNum=1&amp;menuNo=14013649&amp;navTabId=hr0202','hr0202','<spring:message code="hrm.empinfo.RECRUITMENT_ORDER.Z" />');">* <spring:message code="hrm.empinfo.RECRUITMENT_ORDER.Z" /> <!-- 录用发令 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruitManage/viewRecruitBatchList','pageNum=1&menuNo=14013650&navTabId=hr0203','hr0203','<spring:message code="hrm.empinfo.UNIFORM_RECRUITMENT_ORDER.Z" />');">* <spring:message code="hrm.empinfo.UNIFORM_RECRUITMENT_ORDER.Z" /> <!-- 统一录用发令 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewStartPoint?PERSON_ID=${PERSON_ID}','pageNum=1&menuNo=14013651&navTabId=hr0204','hr0204','<spring:message code="hrm.empinfo.The_person" />');">* <spring:message code="hrm.empinfo.The_person" /> <!-- 个人发令 --></a></div>
			  	<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruitManage/viewExperienceBatchList','pageNum=1&menuNo=14013652&navTabId=hr0205','hr0205','<spring:message code="hrm.empinfo.UNIFORM_ORDER.Z" />');">* <spring:message code="hrm.empinfo.UNIFORM_ORDER.Z" /> <!-- 统一发令 --></a></div>
			  	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			  	<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruitManage/viewPersonSupplier','pageNum=1&menuNo=14013653&navTabId=hr0206','hr0206','<spring:message code="hrm.empinfo.personSupplier" />');">* <spring:message code="hrm.empinfo.personSupplier" /> <!-- 供人公司管理 --></a></div>
			  	</c:if>
			</c:if>
		</div>
		<div style="width:170px;margin-left:75px;margin-top:245px;height:120px;float:left;">
				<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewPersonalInfo','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','<spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" />');">* <spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" /> <!-- 综合简介 --></a></div>
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/viewHTSVCardInfoList?firstFlag=1','pageNum=1&menuNo=14013656&navTabId=hr3101','hr3101','<spring:message code="hrm.empinfo.personnel_card" />');">* <spring:message code="hrm.empinfo.personnel_card" /> <!-- 人事卡 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/employeeSearch','pageNum=1&menuNo=14013658&navTabId=hr3201','hr3201','<spring:message code="hrm.empinfo.EMPLOYEE_SEARCH.Z" />');">* <spring:message code="hrm.empinfo.EMPLOYEE_SEARCH.Z" /> <!-- 员工搜索 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/recruitManage/viewExperienceList?firstFlag=1','pageNum=1&menuNo=14013654&navTabId=hr0207','hr0207','<spring:message code="hrm.empinfo.ORDER_SEARCH.Z" />');">* <spring:message code="hrm.empinfo.ORDER_SEARCH.Z" /> <!-- 发令检索 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/approve/ManageCountInfoList','pageNum=1&menuNo=14013661&navTabId=hr3204','hr3204','<spring:message code="hrm.empinfo.DEPARTMENT_STATISTICS.Z" />');">* <spring:message code="hrm.empinfo.DEPARTMENT_STATISTICS.Z" /> <!-- 部门统计 --></a></div>
			    <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/contractInfo/viewContractInfoForSearch','pageNum=1&menuNo=2556&navTabId=hr0303','hr0303','<spring:message code="hrm.empinfo.contract_search" />');">* <spring:message code="hrm.empinfo.contract_search" /> <!-- 合同查询 --></a></div>
			</c:if>
			<c:if test="${isInformationUser == 1 and isSuperUser != 1 and isSuperHrUser != 1 and isHrUser != 1}">
			    <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/empinfo/employeeSearch','pageNum=1&menuNo=14013658&navTabId=hr3201','hr3201','<spring:message code="hrm.empinfo.EMPLOYEE_SEARCH.Z" />');">* <spring:message code="hrm.empinfo.EMPLOYEE_SEARCH.Z" /> <!-- 员工搜索 --></a></div>
			    <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/approve/ManageCountInfoList','pageNum=1&menuNo=14013661&navTabId=hr3204','hr3204','<spring:message code="hrm.empinfo.DEPARTMENT_STATISTICS.Z" />');">* <spring:message code="hrm.empinfo.DEPARTMENT_STATISTICS.Z" /> <!-- 部门统计 --></a></div>
			    <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/approve/hrCountInfoList','pageNum=1&menuNo=14013662&navTabId=hr3205','hr3205','<spring:message code="hrm.viewHrMain.ZHAOPINXIANKUANG.b" />');">* <spring:message code="hrm.viewHrMain.ZHAOPINXIANKUANG.b" /> <!-- 招聘现况 --></a></div>
			    <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/hrm/approve/hrResignCountInfoList','pageNum=1&menuNo=14013663&navTabId=hr3206','hr3206','<spring:message code="hrm.viewHrMain.LIZHIXIANKUANG.b" />');">* <spring:message code="hrm.viewHrMain.LIZHIXIANKUANG.b" /> <!-- 离职现况 --></a></div>
			</c:if>
		</div>
		<div style="width:170px;margin-left:-915px;margin-top:350px;height:120px;float:left;">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1 or isRecruitUser == 1}">
			<div style="padding:3px;margin-left: 5px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/addRecPageHub','pageNum=1&menuNo=90000327&navTabId=hr3701','hr3701','<spring:message code="hr.main.page.addresume" />');">* <!-- 简历录入 --><spring:message code="hr.main.page.addresume" /></a></div>
			<div style="padding:3px;margin-left: 5px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/resumeSelection','pageNum=1&menuNo=90000328&navTabId=hr3702','hr3702','<spring:message code="hr.main.page.resumeselection" />');">* <!-- 简历筛选 --><spring:message code="hr.main.page.resumeselection" /></a></div>
			<div style="padding:3px;margin-left: 5px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/resumeSearch','pageNum=1&menuNo=90000329&navTabId=hr3703','hr3703','<spring:message code="hr.main.page.resumesearch" />');">* <!-- 简历查看 --><spring:message code="hr.main.page.resumesearch" /></a></div>
			<div style="padding:3px;margin-left: 5px;""><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/interviewSchedule','pageNum=1&menuNo=90000330&navTabId=hr3704','hr3704','<spring:message code="hr.main.page.interviewschedule" />');">* <!-- 面试安排 --><spring:message code="hr.main.page.interviewschedule" /></a></div>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<div style="padding:3px;margin-left: 5px;""><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/interviewScheduleConfirm','pageNum=1&menuNo=90000331&navTabId=hr3707','hr3707','<spring:message code="hr.main.page.interviewscheduleConfirm" />');">* <!-- 面试计划 --><spring:message code="hr.main.page.interviewscheduleConfirm" /></a></div>
			</c:if>
			<div style="padding:3px;margin-left: 5px;""><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/interviewProcess','pageNum=1&menuNo=90000332&navTabId=hr3705','hr3705','<spring:message code="hr.main.page.interviewprocess" />');">* <!-- 面试记录 --><spring:message code="hr.main.page.interviewprocess" /></a></div>
			<div style="padding:3px;margin-left: 5px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/recruit/recHrConfirm','pageNum=1&menuNo=90000333&navTabId=hr3706','hr3706','<spring:message code="hr.main.page.rechrconfirm" />');">* <!-- 人事招聘确认 --><spring:message code="hr.main.page.rechrconfirm" /></a></div>
		    </c:if>
		</div>
		<div style="width:170px;margin-left:-655px;margin-top:330px;height:120px;float:left;">
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewEducationMatter?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewEducationMatter','viewEducationMatter','<spring:message code="hrm.recruitManage.Education_matters" />');">* <spring:message code="hrm.recruitManage.Education_matters" /> <!-- 学历事项 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewBidMatter?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewBidMatter','viewBidMatter','<spring:message code="ess.empInfo.qualifications_matter" />');">* <spring:message code="ess.empInfo.qualifications_matter" /> <!-- 资格事项 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewRecognition?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewRecognition','viewRecognition','<spring:message code="ess.empInfo.commend_matter" />');">* <spring:message code="ess.empInfo.commend_matter" /> <!-- 表彰事项 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewExperiencePoint?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewExperiencePoint','viewExperiencePoint','<spring:message code="hrm.recruitManage.Experience_issues" />');">* <spring:message code="hrm.recruitManage.Experience_issues" /> <!-- 经历事项 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewForeignLanguage?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewForeignLanguage','viewForeignLanguage','<spring:message code="hrm.empinfo.Foreign_language_ability" />');">* <spring:message code="hrm.empinfo.Foreign_language_ability" /> <!-- 外语能力 --></a></div>
			</c:if>
		</div>
		<div style="width:170px;margin-left:-425px;margin-top:330px;height:120px;float:left;">
	    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
				<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewEmpInfo?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewEmpInfo','viewEmpInfo','<spring:message code="hr.viewHire.title.COMPANYINFORMATIONIN" />');">* <spring:message code="hr.viewHire.title.COMPANYINFORMATIONIN" /> <!-- 员工基础信息 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewPunishment?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewPunishment','viewPunishment','<spring:message code="hr.viewCondSql.title.CHENGJIEXINXI" />');">* <spring:message code="hr.viewCondSql.title.CHENGJIEXINXI" /> <!-- 惩戒信息 --></a></div>
				<div style="padding:3px; "><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/empinfo/viewEvaluateInfo?PERSON_ID=${PERSON_ID}','TABS_SELECTED=0&navTabId=viewEvaluateInfo','viewEvaluateInfo','<spring:message code="hrm.empinfo.Evaluation_message" />');">* <spring:message code="hrm.empinfo.Evaluation_message" /> <!-- 评价信息 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/contractInfo/viewNOContractInfo?specialNumPerPage=20','pageNum=1&menuNo=5308&navTabId=hr0305','hr0305','<spring:message code="hrm.empinfo.contract_sign" />');">* <spring:message code="hrm.empinfo.contract_sign" /> <!-- 签订合同 --></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/hrm/contractInfo/viewExpiredContract?specialNumPerPage=20','pageNum=1&menuNo=2558&navTabId=hr0301','hr0301','<spring:message code="hrm.empinfo.contract_renew" />');">* <spring:message code="hrm.empinfo.contract_renew" /> <!-- 续签合同 --></a></div>
			</c:if>
		</div>
	</div>
</div>
