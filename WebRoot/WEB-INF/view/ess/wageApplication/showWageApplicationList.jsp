<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<div class="pageContent">
	<div>
		<table width="100%" layoutH="300" class="user_table margin_b">
			<c:if test="${fn:length(messageList) > 0}">
				<tr>
					<td class="td_title" width="15%" style="text-align: center">申请者/工号</td>
					<td class="td_title" width="15%" style="text-align: center">费用类型</td>
					<td class="td_title" width="30%" style="text-align: center">申请费用发放期间</td>
					<td class="td_title" width="15%" style="text-align: center">金额</td>
					<td class="td_title" width="15%" style="text-align: center">备注</td>
				</tr>
			</c:if>
			<c:forEach items="${messageList}" var="message" varStatus="i">
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${message.EMPNAME}[${message.EMPID}]</td>
					<td class="td_type" width="15%" style="text-align: center">${message.TYPENAME}</td>
					<td class="td_type" width="30%" style="text-align: center">${message.START_DATE}~${message.END_DATE}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.MONEY}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.DEMO}</td>
				</tr>
				<c:if test="${fn:length(messageList)==i.count}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="30%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center">追加款：${message.ZHENG }</td>
						<td class="td_type" width="15%" style="text-align: center">追减款：${message.FU}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<c:set value="/ess/wageApplication/showWageApplicationList" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
	<div>
		<table class="user_table" layoutH="320" width="100%">
			<tr>
				<td class="td_title" width="11%" style="text-align: center" rowspan="${fn:length(proveList)+1 }">决裁线</td>
				<td class="td_title" width="100" style="text-align: center">决裁等级</td>
				<td class="td_title" width="180" style="text-align: center">决裁者</td>
				<td class="td_title" width="100" style="text-align: center">决裁情况</td>
				<td class="td_title" width="180" style="text-align: center">审批时间</td>
				<td class="td_title" style="text-align: center">决裁批注</td>
			</tr>
			<c:forEach items="${proveList}" var="affirmor" varStatus="j">
				<tr id="${affirmor.AFFIRMOR_ID }">
					<td class="td_type" width="100" style="text-align: center">
						${affirmor.AFFIRM_LEVEL}
					</td>
					<td class="td_type" width="180" style="text-align: center">
						${affirmor.EMPNAME}[${affirmor.EMPID}]
					</td>
					<td class="td_type" width="100" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
					</td>
					<td class="td_type" width="180" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type">${affirmor.AFFIRM_CONTENT}</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="td_type" colspan="6"><br /></td>
			</tr>
			<tr>
				<c:if test="${fn:length(checkorList) > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${fn:length(checkorList)*2+1 }">Review</td>
				</c:if>
				<c:if test="${fn:length(checkorList) == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${3}">Review</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center">Type</td>
				<td class="td_title" width="30%" style="text-align: center" colspan="2">Requests</td>
				<td class="td_title" width="45%" style="text-align: center" colspan="2">Reviewed</td>
			</tr>

			<c:forEach items="${checkorList}" var="checkor" varStatus="i">
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
						<c:if test="${checkor.AFFIRM_FLAG == 0}">未决裁</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 1}">已通过</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 2}">已否决</c:if>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">未Check</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">已Check</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						<textarea name="affirmRemark" cols="50" rows="2"
							disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						<textarea name="checkRemark" cols="50" rows="2"
							disabled="disabled">[Check]：${checkor.CHECK_CONTENT}</textarea>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${fn:length(checkorList) == 0}">
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">&nbsp;</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">&nbsp;</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">&nbsp;</td>
				</tr>
			</c:if>
		</table>
	</div>
</div>