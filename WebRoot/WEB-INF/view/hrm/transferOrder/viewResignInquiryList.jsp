<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageContent">
<div class="pageFormContent">
	<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align: right;width:10%;">申请人</td>
			<td style="text-align: left;width:20%;">[${resignMst.REQ_EMPID}]${resignMst.REQ_NAME}</td>
			<td class="td_title" style="text-align: center;width:10%;">申请日期</td>
			<td style="text-align: left;width:20%;">${resignMst.REQ_DATE}</td>
			<td class="td_title" style="text-align: center;width:10%;">申请类型</td>
			<td style="text-align: left;width:30%;">
				<c:if test="${resignMst.TRANS_CODE eq 'RESIGN'}">离职</c:if>
				<c:if test="${resignMst.TRANS_CODE eq 'RESIGNREVOKE'}">离职撤销</c:if>
			</td>
		</tr>
	</table>
</div>
<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
	<table class="table" width="100%" layoutH="270" targetType="dialog" >
		<thead>
			<tr >
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%">
					人员类型
				</th>
				<th width="7%">
					职责
				</th>
				<th width="9%">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<font color="red">*</font>
					<!--离职日期-->
				</th>
				<th width="12%">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<font color="red">*</font>
					<!--离职类型-->
				</th>
				<th width="13%">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<font color="red">*</font>
					<!--离职原因-->
				</th>
				<th width="6%" >
					<spring:message code="hr.viewResign.title.BLACKYN"/>
					<!--能否再入职-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewResign.title.BLACKREMARK"/>
					<!--黑名单理由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resignInqList}" var="resignInfo">
				<tr target="sid" rel="${resignInfo.EMPID}">
					<td class="td_center">
						<input type="hidden" name="PERSON_ID_${resignInfo.EMPID}" value="${resignInfo.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${resignInfo.EMPID}" value="${resignInfo.DEPTNO}"/>
						<input type="hidden" name="POSITION_NO_${resignInfo.EMPID}" value="${resignInfo.POSITION_NO}"/>
						<input type="hidden" name="POST_NO_${resignInfo.EMPID}" value="${resignInfo.POST_NO}"/>
						<input type="hidden" name="EXP_INSIDE_NO_${resignInfo.EMPID}" value="${resignInfo.EXP_INSIDE_NO}"/>
						<input type="hidden" name="REQTYPE" id="REQTYPE" value="" />
						${resignInfo.EMPID}
					</td>					
					<td class='td_center'>
						${resignInfo.LOCAL_NAME}
					</td>					
					<td>
						${resignInfo.DEPT_NAME}
					</td>					
					<td>
						${resignInfo.EMP_TYPE_NAME}
					</td>					
					<td class='td_center'>
						${resignInfo.POSITION_NAME}
					</td>					
					<td class='td_center'>
						${resignInfo.RESIGN_DATE}
					</td>						
					<td>
						${resignInfo.RESIGN_TYPE_NAME}
					</td>
					<td>
						${resignInfo.RESIGN_REASON_DESC}
					</td>					
					<td class='td_center'>	
						${resignInfo.BLACKLIST_YN_DESC}
					</td>					
					<td>
						${resignInfo.REMARK}
					</td>					
					<td  class='td_center'>
						${resignInfo.BLACKLIST_REASON}
					</td>					
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewResignInquiryList?pageNum=1&REQ_ID=${searchMap.APPLY_NO}&navTabId=ess0253">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</td></tr>
<c:if test="${searchMap.affirmFlag eq 1 and (STATE ne 20)}">
<tr><td>
	<form style="margin:0px;padding:0px;" id="RevokeResignForm" name="RevokeResignForm" 
	onsubmit="return validateCallbackRevokeResign(this,navTabAjaxDone);" 
	action="/hrm/transferOrder/revokeResign" 
	method="post" 
	class="pageForm required-validate">
	<input type="hidden" name="REQ_ID" id="REQ_ID" value="${resignMst.EXP_INSIDE_NO}"/>
		<table class="table" width="100%">
		<tbody>
		<tr>
		    <td width="7%" style="text-align:center">
		             附件
		    </td>
		    <td class='td_type'>
			<c:forEach items="${fileList}" var="file" varStatus="i">
			    <a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
			    &nbsp;&nbsp;&nbsp;
			</c:forEach>
		    </td>
		</tr>			
		</tbody>
		</table>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align:right;width:7%;"><!-- 决裁线 -->
					决裁线
				</td>
				<td colspan="7">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="resign_affirmor_list">
						<thead>
							<td class="td_title" style="text-align: center;width:7%;"><!-- 审批等级 -->
							审批等级</td>
							<td class="td_title" style="text-align: center;width:16%;"><!-- 审批者 -->
							审批者</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批情况 -->
							审批情况</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
							审批时间</td>
							<td class="td_title" style="text-align: center;width:34%;"><!-- 审批批注 -->
							审批批注</td>
							<td class="td_title" style="text-align: center;width:7%;"><!-- 审批批注 -->
							审批者(+/-)</td>
							<!-- td class="td_title" style="text-align: center;width:6%;">
								check(+/-)
							</td> -->
						</thead>
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
								<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
									<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
									<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">否决</c:if>
									</td>
									<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
									<td class="td_type">
										${affirmor.AFFIRM_CONTENT}										
										<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
										<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
										<!-- 隐藏的一些参数 -->
										<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${searchMap.APPLY_NO}" />
										<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
										<input id="AFFIRM_FLAG_RESIGN_APPLY" name="AFFIRM_FLAG" type="hidden" value="" />
										<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID}" />
										<input id="affirm_count" name="affirm_count" type="hidden" value="${fn:length(affirmorList)}" />
									</td>
									<td class="td_type" style="text-align: center">
										<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>										
									</td>
								</tr>			
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</form>
</td></tr>
</c:if>
</table>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" id="btnClose" name="btnClose" class="close">
					关闭</button>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>