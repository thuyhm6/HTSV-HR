<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div style="height:882px;line-height:529px;overflow:auto;overflow-x:hidden;background:url('/resources/images/org_index.jpg') no-repeat;">
		<div style="width:190px;padding-left:360px;margin-top:136px;height:300px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="height:120px;overflow:auto;overflow-x:hidden;">
				<c:if test="${LoginUser.username ne 'HQ' }">
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewResumeList','pageNum=1&menuNo=14013674&navTabId=org0201','org0201','<spring:message code="org.title.ADAPTATION_RESUME" />');"><spring:message code="org.title.ADAPTATION_RESUME" /><!-- 改编概要 --></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewResumeProcess','pageNum=1&menuNo=14013675&navTabId=org0202','org0202','<spring:message code="org.title.ADAPTATION_PROCESS" />');"><spring:message code="org.title.ADAPTATION_PROCESS" /><!-- 改编流程 --></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewComposeOrg','pageNum=1&menuNo=14013676&navTabId=org0203','org0203','<spring:message code="org.title.ORG_CONSTITUTE" />');"><spring:message code="org.title.ORG_CONSTITUTE" /><!-- 组织图构成 --></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewDeptManagerCheck','pageNum=1&amp;menuNo=14013677&amp;navTabId=org0204','org0204','<spring:message code="org.title.OCCUPANT_CHECK" />');"><spring:message code="org.title.OCCUPANT_CHECK" /><!-- 任职者核查 --></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewOrgExperirnceInsideList','pageNum=1&amp;menuNo=14013678&amp;navTabId=org0205','org0205','<spring:message code="org.title.ORDERS_CHECK" />');"><spring:message code="org.title.ORDERS_CHECK" /></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewOrgPreExperirnceInsideList','pageNum=1&amp;menuNo=14013679&amp;navTabId=org0206','org0206','<spring:message code="org.title.PRORDERS_CHECK" />');"><spring:message code="org.title.PRORDERS_CHECK" /></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewEmpChangeInfoList','pageNum=1&amp;menuNo=14013680&amp;navTabId=org0207','org0207','<spring:message code="org.title.UPDATE_RESUME_PERSON" />');"><spring:message code="org.title.UPDATE_RESUME_PERSON" /></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewOrgChangeInfoList','pageNum=1&amp;menuNo=14013681&amp;navTabId=org0208','org0208','<spring:message code="org.title.UPDATE_RESUME_DEPT" />');"><spring:message code="org.title.UPDATE_RESUME_DEPT" /></a></div>
					<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewCommonOrgTreeInfo?targetRel=viewChangeInfoList','pageNum=1&amp;menuNo=14013682&amp;navTabId=org0209','org0209','<spring:message code="org.title.UPDATE_CHECK" />');"><spring:message code="org.title.UPDATE_CHECK" /></a></div>
				</c:if>
			</div>
			<div style="margin-top:80px;">
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewHistoryOrgInfo','pageNum=1&amp;menuNo=14013690&amp;navTabId=org0403','org0403','<spring:message code="org.title.ORGHISTORY_SELECT" />');"><spring:message code="org.title.ORGHISTORY_SELECT" /><!-- 组织历史查询 --></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewCommonOrgTreeInfo?targetRel=viewOrgChangePanel','pageNum=1&menuNo=14013691&navTabId=org0404','org0404','<spring:message code="org.title.ORG_RESUME" />');"><spring:message code="org.title.ORG_RESUME" /><!-- 组织履历 --></a></div>
			</div>
		</div>
		<div style="width:170px;margin-left:190px;margin-top:135px;height:300px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewCurrentOrgInfo','pageNum=1&amp;menuNo=14013688&amp;navTabId=org0401','org0401','<spring:message code="org.title.ORGNOW_SELECT" />');"><spring:message code="org.title.ORGNOW_SELECT" /><!-- 现组织查询 --></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewOrgInfo','pageNum=1&menuNo=14013689&navTabId=org0402','org0402','<spring:message code="org.title.ORG_PHOTO" />');"><spring:message code="org.title.ORG_PHOTO" /><!-- 组织结构图 --></a></div>
 		</div>
		<div style="width:170px;margin-left:-155px;margin-top:340px;height:120px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewCommonOrgTreeInfo?targetRel=viewBusinessManagerInfo','pageNum=1&amp;menuNo=14013683&amp;navTabId=org0301','org0301','<spring:message code="org.title.DEPT_SERVICE_MANAGE" />');"><spring:message code="org.title.DEPT_SERVICE_MANAGE" /><!-- 部门业务管理 --></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewCostCenterManagerInfo','pageNum=1&amp;menuNo=14013686&amp;navTabId=org0304','org0304','<spring:message code="org.title.COST_CENTER_MANAGE" />');"><spring:message code="org.title.COST_CENTER_MANAGE" /><!-- 成本中心管理 --></a></div>
			<!-- <div style="padding:3px;"><a href="#" onclick="navTabNum('/org/orgManage/viewWorkAreaManagerInfo','pageNum=1&amp;menuNo=14013687&amp;navTabId=org0305','org0305','<spring:message code="org.title.WORD_AREA" />');"><spring:message code="org.title.WORD_AREA" />工作地管理</a></div> -->
		</div>
	</div>
</div>
