<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
</script>
<div class="pageHeader" style="border: 1px #B8D0D6 solid">
	<form id="viewPaPayrollCostsInfo" method="post"
		action="/report/pac04/viewPaJobOfferList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align: center">
						<%-- 员工类型--%>
						员工类型：
					</td>
					<td width="6%" style="text-align: left">
						<td class="td_type">
							<select name="PAY_STEP" id="PAY_STEP">
								<option value="">
									<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
									<!-- 请选择 -->
								</option>
								<c:forEach items="${getEmpType}" var="empType">
									<option value="${empType.CONTENT}">
										${empType.CONTENT}
									</option>
								</c:forEach>
							</select>
						</td>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="button" onclick="openPaPayrollCostsExecl(this)"
							<%--是否导出?--%>title="<spring:message code='rp.report.title.exportYN'/>">
							<span>
								<button id="excelemport" name="Excel导出" >Excel导出</button>
						</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>




						