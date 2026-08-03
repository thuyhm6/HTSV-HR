<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewContract" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>

	<c:set value="1250" var="add_width" />
	<c:set value="630" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewContractInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	
	<c:set value="1200" var="delete_width" />
	<c:set value="500" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteFile?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1200" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	
	<c:set value="/hrm/empinfo/updateFileInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	
	
	
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewContract.title.CONTRACT_INFORMATION"/>
			<!--合同信息-->
		</h1>
	<div>
			<table class="table" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewContract.title.CONTRACT_TYPE_NAME"/>
							<!--合同类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
							<!--开始日-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
							<!--结束日-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.CONTRACT_PERIOD"/>
							<!--合同期限-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.REMARK"/>
							<!--备注-->
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contracList}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.CONTRACT_NO}">
							<td>${item.CONTRACT_TYPE_NAME}</td>
							<td>${item.START_CONTRACT_DATE}</td>
							<td>${item.END_CONTRACT_DATE}</td>
							<td>
								<c:if test="${empty item.END_CONTRACT_DATE}" >
									<spring:message code="hr.viewContract.title.NO_ENDDATE"/>
									<!--无固定期限-->
								</c:if>
								<c:if test="${not empty item.END_CONTRACT_DATE}" >${item.CONTRACT_PERIOD}</c:if>
							</td>
							<td>${item.REMARK}</td>
						</tr>
					
					</c:forEach>
					
				</tbody>
			</table>
			</div>
		</div>

	
</div>
