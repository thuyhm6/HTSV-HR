<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
<table class="table" width="100%" layoutH="270">
	<thead>
		<tr>
			<th width="10%"><spring:message code="ar.excelexport.title.month" /> <!--月份--></th>
			<th width="10%"><spring:message code="inct.salesman.empNo" /> <!--社号--></th>
			<th width="10%"><spring:message code="inct.salesman.adjustInct" /><!--调整提成--></th>
			<th width="70%"><spring:message code="inct.salesman.remark" /><!--备注--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${itemList}" var="item" varStatus="i">
			<tr>
				<td class='td_center'>${item.INCTV_MON }</td>
				<td class='td_center'>${item.EMPNO }</td>
				<td class='td_right'>${item.ADJST_AMT }</td>
				<td>${item.ADJST_REASON }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/inct/salesman/viewIncentiveCalcAdjuDtlList?REQ_ID=${REQ_ID }" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<div class="pageContent" >
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmListCnt + 1 }"><!-- 决裁线 -->
					决裁线
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
				决裁等级
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
				决裁者
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
				决裁情况
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
				决裁批注
			</td>
		</tr>
		<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" width="15%" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						未决裁
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						已通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						已否决
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
		<tr>
					<td class="td_title" style="text-align: center" rowspan="${checkListCnt + 1}">
						Check
					</td>
					<td colspan="5">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center">
									Type
								</td>
								<td class="td_title" style="text-align: center">
									Requests
								</td>
								<td class="td_title" style="text-align: center">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkList}" var="check" varStatus="i">
								<tr>
									<td class="td_type" style="text-align: center" width="10%">
										public
									</td>
									<td class="td_type" width="45%">
										[${check.EMPID_R}]-${check.LOCAL_NAME_R }&nbsp;&nbsp;${check.POSITION_NO_R }&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br/>
										[Request]${check.CHECK_REASON}
									</td>
									<td class="td_type" width="45%">
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
									<td class="td_title" style="text-align: center" width="10%">Public</td>
									<td class="td_type" width="45%">
										无
									</td>
									<td class="td_type" width="45%">
										无
									</td>
								</tr>		
							</c:if>
							</tbody>
						</table>
					</td>
				</tr>
	</table>
	
</div>
<div class="formBar">
	<ul>			
		<li><div class="button"><div class="buttonContent">
			<button type="button" id="btnClose" name="btnClose" class="close">
			关闭</button>
		</div></div></li>
	</ul>
</div>	