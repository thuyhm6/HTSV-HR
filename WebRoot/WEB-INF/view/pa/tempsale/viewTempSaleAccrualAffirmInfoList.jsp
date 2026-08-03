<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align:right;width:10%;">申请人</td>
			<td colspan="7" class="td_type" style="text-align:left;width:90%;">[${eventInfo.EMPID}]${eventInfo.LOCAL_NAME}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请时间</td>
			<td colspan="7" class="td_type" style="text-align: left;">${eventInfo.APPLY_DATE}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请内容</td>
			<td colspan="7" style="text-align: center;">
				<table width="100%">
					<tr>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">大区</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.PAY_AREA_NAME}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">支社</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.BRANCH_NAME}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">支付月份</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.PAY_DATE}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">对应共同社编</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.COMMON_EMPID}</td>
					</tr>
					<c:forEach items="${paTempSalesAccuralInfoList}" var="item" varStatus="i">
						<tr target="INFO_NO" rel="${item.INFO_NO}">
							<td class="td_title" style="text-align: center">${i.count}</td>
							<td class="td_title" style="text-align: center">产品类型</td>
							<td colspan="6" class="td_type">${item.PROD_TP}</td>
							<td colspan="2" class="td_title" style="text-align: center">金额</td>
							<td colspan="6" class="td_type">${item.TOTAL_PAY}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" class="td_type"></td>
						<td colspan="2" class="td_title" style="text-align:center;">合计</td>
						<td colspan="6" class="td_type">${eventInfo.TOTAL_SALARY}</td>
					</tr>
				</table>
			</td>
		</tr>
</table>
</div>
<div class="pageContent" >
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" width="10%" style="text-align: right" rowspan="${affirmListCnt + 1 }"><!-- 决裁线 -->
					审批线
			</td>
			<td class="td_title" width="8%" style="text-align: center"><!-- 审批等级 -->
				审批等级
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批者 -->
				审批者
			</td>
			<td class="td_title" width="12%" style="text-align: center"><!-- 审批情况 -->
				审批情况
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" width="40%" style="text-align: center"><!-- 审批批注 -->
				审批批注
			</td>
		</tr>
		<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						未审批
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						否决
					</c:if>
				</td>
				<td class="td_type" style="text-align: center">${affirmor.UPDATE_DATE}</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
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
		<tr>
					<td class="td_title" style="text-align:right" <c:if test="${checkListCnt eq '0'}">rowspan=2</c:if> <c:if test="${checkListCnt ne '0'}">rowspan="${checkListCnt + 1}"</c:if>>
						Check
					</td>
					<td class="td_title" style="text-align: center">
						Type
					</td>
					<td colspan="3" class="td_title" style="text-align: center">
						Requests
					</td>
					<td class="td_title" style="text-align: center">
						Checked
					</td>
			</tr>
			<c:forEach items="${checkList}" var="check" varStatus="i">
								<tr>
									<td class="td_type" style="text-align: center">
										public
									</td>
									<td colspan="3" class="td_type">
										[${check.EMPID_R}]-${check.LOCAL_NAME_R }&nbsp;&nbsp;${check.POSITION_NO_R }&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br/>
										[Request]${check.CHECK_REASON}
									</td>
									<td class="td_type">
										[${check.EMPID_C}]-${check.LOCAL_NAME_C }&nbsp;&nbsp;${check.POSITION_NO_C }&nbsp;&nbsp;(${check.DEPTNAME_C })
										<c:if test="${check.CHECK_FLAG eq '0'}">
											/未Check
										</c:if>
										<c:if test="${check.CHECK_FLAG ne '0'}">
										/${check.DATE_C }
										</c:if>
										<br/>
										[Check]${check.CHECK_CONTENT}
									</td>
								</tr>
							</c:forEach>
							<c:if test="${checkListCnt == 0}">
								<tr>
									<td class="td_title" style="text-align: center">Public</td>
									<td colspan="3" class="td_type">
										无
									</td>
									<td class="td_type">
										无
									</td>
								</tr>		
							</c:if>
	</table>
</div>
