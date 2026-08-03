<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent" >
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<spring:message code="public.title.close"/><!-- 关闭 -->
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<table class="user_table" width="100%" layoutH="60" border="0">
				<tr>
					<td colspan="6">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center;"><!-- 社号/姓名 --><spring:message code="public.title.empIdAndName" /> </td>
								<td>[${contractInfo.EMPID}]${contractInfo.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 合同次数  --><spring:message code="hrm.contract.Contract_TOTAL_PERIOD" /></td>
								<td>${contractInfo.TOTAL_PERIOD}</td>
								<td class="td_title" style="text-align: center"><!-- 合同编号 --><spring:message code="hrm.contractInfo.CONTRACT_ID"/></td>
								<td>${contractInfo.CONTRACT_NUMBER}</td>
								<td class="td_title" style="text-align: center"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /></td>
								<td>${contractInfo.DEPTNAME}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center"><!-- 合同类型 --><spring:message code="hrm.contract.CONTRACT_TYPE" /></td>
								<td>${contractInfo.CONTRACT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 合同版本 --><spring:message code="hrm.contract.Contract_version" /></td>
								<td>${contractInfo.CONTRACT_VERSION}</td>
								<td class="td_title" style="text-align: center"><!-- 起始日期  --><spring:message code="hrm.contractInfo.STAR_DATE" /></td>
								<td>${contractInfo.START_CONTRACT_DATE}</td>
								<td class="td_title" style="text-align: center"><!-- 终止日期 --><spring:message code="hrm.contractInfo.END_DATE" /></td>
								<td>${contractInfo.END_CONTRACT_DATE}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center"><!-- 工作地区 --><spring:message code="hrm.contractInfo.WORK_AREA" /></td>
								<td>${contractInfo.WORK_AREA_NAME}</td>
								<td class="td_title" style="text-align: center"><!-- 签订意见 --><spring:message code="hr.contract.title.xuqian.yijian" />     </td>
								<td colspan="5">${contractInfo.REMARK}</td>
							</tr>
						</table>
					</td>
				</tr>
		<tr>
			<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmListCnt + 1 }"><!-- 决裁线 -->
				<!-- 决裁线 --><spring:message code="hrm.contractInfo.DECISION_LINE" />
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
				<spring:message code="sys.affirm.title.affirmGradeLevel" />
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
				<spring:message code="sys.affirm.title.affirmPerson" />
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
				 <spring:message code="ess.viewApply.title.affirmCondition" />
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
				<spring:message code="hrm.contractInfo.APPROVAL_TIME" />
			</td>
			<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
				<spring:message code="hrm.contractInfo.DECISION_MARK" />
			</td>
		</tr>
		<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" width="15%" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						 <!-- 未决裁 --> <spring:message code="liang.pa.insuranceApply.title.weicaijue" />
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						 <!-- 已通过 --> <spring:message code="hr.viewTransactionTransViewList.title.PASS" />
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						 <!-- 已否决 --> <spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN" />
					</c:if>
				</td>
				<td class="td_type" width="15%" style="text-align: center">${affirmor.UPDATE_DATE}</td>
				<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmListCnt == 0}">
								<tr>
									<td class="td_title" >&nbsp;</td>
									<td class="td_type" >&nbsp;</td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
								</tr>		
							</c:if>
	</table>
</div>
