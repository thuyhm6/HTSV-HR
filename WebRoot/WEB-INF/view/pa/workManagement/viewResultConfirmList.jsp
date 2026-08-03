<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearcha(obj) {
	var ITEM_TYPE = '1';
	var PAGE_TYPE = '2';
	var currentIndex = $("#viewResultConfirmList_currentIndex").attr("value");
	if (currentIndex == '3') {
		ITEM_TYPE = '3';
	}
	var dept = $("input[syslong='pa1019_seachDept']").val();
	var seach_PAY_DATE = $("#pa1019_PAY_DATE").attr("value");
	var seach_SALARY_DISTIN_NO = $("#pa1019_SALARY_DISTIN_NO").attr("value");
	var seach_PAY_DATE_PRO = $("#pa1019_PAY_DATE_PRO").attr("value");
	var KEY = $("#viewResultConfirmSonList5_KEY").attr("value");
	openOnRight('/pa/workManagement/viewResultConfirmSonList?currentIndex='
			+ currentIndex + '&seach_DEPT_NO=' + dept + '&seach_PAY_DATE='
			+ seach_PAY_DATE + '&seach_PAY_DATE_PRO=' + seach_PAY_DATE_PRO
			+ '&ITEM_TYPE=' + ITEM_TYPE + '&seach_SALARY_DISTIN_NO='
			+ seach_SALARY_DISTIN_NO + '&KEY=' + KEY + '&PAGE_TYPE='
			+ PAGE_TYPE, 'viewResultConfirmList_tag' + currentIndex);
	return false;
}
function pa1019_Linkage() {
	var obj = document.getElementById("pa1019_SALARY_DISTIN_NO");
	var dis = $(obj).attr("value");
	$("#pa1019_PAY_DATE option").remove();
	$("#pa1019_PAY_DATE_PRO option").remove();
	$("#pa1019_Linkages option").each(
			function() {
				if ($(this).attr("title") == dis) {
					var ddq = "<option value='" + $(this).attr('value')
							+ "' title='" + $(this).attr('title')
							+ "'  onChange='pa1019_LinkageTwo(this)'>"
							+ $(this).html() + "</option>";
					$("#pa1019_PAY_DATE").append(ddq);
					$("#pa1019_PAY_DATE_PRO").append(ddq);
				}
			});
}
function pa1019_LinkageTwo(obj) {
}
$(document).ready(function() {
	pa1019_Linkage();
	seldChange();
});
function seldChange() {
	var date = $("#pa1019_PAY_DATE_SELD").attr("value");
	var date_pro = $("#pa1019_PAY_DATE_PRO_SELD").attr("value");
	var distin = $("#pa1019_SALARY_DISTIN_NO_SELD").attr("value");
	$("#pa1019_PAY_DATE option").each(function() {
		if ($(this).attr("title") == distin && $(this).attr("value") == date) {
			$(this).attr("selected", "selected")
		}
	});
	$("#pa1019_PAY_DATE_PRO option").each(
			function() {
				if ($(this).attr("title") == distin
						&& $(this).attr("value") == date_pro) {
					$(this).attr("selected", "selected")
				}
			});
}
//获取工资区分的名称
function getSALARY_DISTIN() {

	var SALARY_DISTIN = $("#pa1019_SALARY_DISTIN_NO").find("option:selected")
			.text();
	$("#pa1019_SALARY_DISTIN").attr("value", SALARY_DISTIN);
	$("#viewPaResultList").submit();
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearcha(this);"
		action="/pa/workManagement/viewResultConfirmList" method="post"
		id="viewResultConfirmList" name="viewResultConfirmList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<!-- 支付计划 -->
				<tr>
					<td>
						<!--工资区分--><spring:message code="pa.monthPersonCountInfoList.GONGZIQUFEN.b" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO"
							selected="${SALARY_DISTIN_NO}" parentNo="14013797"
							id="pa1019_SALARY_DISTIN_NO" cnpyID="${LoginUser.cpnyId}"
							onChangeName="pa1019_Linkage()" />
					</td>
					<td>
						<!--支付日期 （前月--当月）--><spring:message code="pa.viewResultConfirmList.ZHIFURIQIQIANDANGYU.b" />
					</td>
					<td>
							<select id="pa1019_PAY_DATE_PRO" name="PAY_DATE_PRO">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE_PRO == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE }">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							--
							<select id="pa1019_PAY_DATE" name="PAY_DATE"
								onchange="pa1019_LinkageTwo(this)">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE}">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td>
							<!--部门--><spring:message code="ess.infoApply.DEPT" />
						</td>
						<td>
							<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="all" id="pa1019_seachDept" selected="${DEPT_NO}" />
							<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
								limit="all" id="pa1019_seachDept" selected="${DEPT_NO}" />
						</td>
					<%-- <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
						<td>
							<select id="pa1019_PAY_DATE_PRO" name="PAY_DATE_PRO">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE_PRO == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE }">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							--
							<select id="pa1019_PAY_DATE" name="PAY_DATE"
								onchange="pa1019_LinkageTwo(this)">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE}">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'SST'}">
						<td>
							<select id="pa1019_PAY_DATE" name="PAY_DATE"
								onchange="pa1019_LinkageTwo(this)">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE}">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							---
							<select id="pa1019_PAY_DATE_PRO" name="PAY_DATE_PRO">
								<c:forEach items="${paPayScheduleList}" var="paySchedule"
									varStatus="i">
									<c:choose>
										<c:when test="${PAY_DATE_PRO == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE }">
												${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>

						</td>
					</c:if> --%>
				</tr>
				<select id="pa1019_Linkages" style="display: none">
					<c:forEach items="${paPayScheduleList}" var="paySchedule"
						varStatus="i">

						<option value="${paySchedule.PAY_DATE }"
							title="${paySchedule.SALARY_DISTIN_NO}">
							${paySchedule.PAY_DATE } 
						</option>

					</c:forEach>
				</select>
				<input type="hidden" id="pa1019_PAY_DATE_SELD" value="${PAY_DATE}" />
				<input type="hidden" id="pa1019_PAY_DATE_PRO_SELD"
					value="${PAY_DATE_PRO}" />
				<input type="hidden" id="pa1019_SALARY_DISTIN_NO_SELD"
					value="${SALARY_DISTIN_NO}" />
				<!-- 支付计划 -->
				<%-- <tr>
					<td>
						工资项目别案例
					</td>
					<td>
					</td>
					<td>
						工资项目
					</td>
					<td>
						<select id="viewResultConfirmList_seach_ITEM_ID"
							name="seach_ITEM_ID">
							<c:forEach items="${itemValueInfo}" var="itemValueInfo"
								varStatus="i">
								<c:choose>
									<c:when test="${ITEM_ID  eq itemValueInfo.ITEM_ID}">
										<option value="${itemValueInfo.ITEM_ID}" selected="selected">
											${itemValueInfo.ITEM_NAME}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${itemValueInfo.ITEM_ID}">
											${itemValueInfo.ITEM_NAME}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						例外包含
					</td>
					<td>

						<input type="checkbox" name="includeLY" />
					</td>
				</tr> --%>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" class="button">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="tabs" eventType="click" currentIndex="${currentIndex }">
	<input type="hidden" id="viewResultConfirmList_currentIndex"
		value="${currentIndex }">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li>
					<a id="hreff0"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=0"
						type="0" class="j-ajax"><span id="link0">1.<!--变动明细--><spring:message code="pa.viewResultConfirmList.BIANDONGMINGXI.b" /></span> </a>
				</li>
				<li>
					<a id="hreff1"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=1"
						type="1" class="j-ajax"><span id="link1">2.<!--支付项目对比--><spring:message code="pa.viewResultConfirmList.ZHIFUXIANGMUDUIBI.b" /></span> </a>
				</li>
				<li>
					<a id="hreff2"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=2"
						type="3" class="j-ajax"><span id="link1">3.<!--加班统计--><spring:message code="pa.viewResultConfirmList.JIABANTONGJI.b" /></span> </a>
				</li>
				<li>
					<a id="hreff3"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=3"
						type="3" class="j-ajax"><span id="link1">4.<!--保险对比--><spring:message code="pa.viewResultConfirmList.BAOXIANDUIBI.b" /></span> </a>
				</li>
				<li>
					<a id="hreff6"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=6"
						type="5" class="j-ajax"><span id="link1">5.<!--保险明细--><spring:message code="pa.viewResultConfirmList.BAOXIANMINGXI.b" /> </span> </a>
				</li>
				<li>
					<a id="hreff5"
						href="/pa/workManagement/viewResultConfirmSonList?currentIndex=5"
						type="5" class="j-ajax"><span id="link1">6.<!--税金--><spring:message code="display.pa.tax" /> </span> </a>
				</li>
			</ul>
		</div>
	</div>
	<div class="tabsContent" style="height: 665px;">
		<div id="viewResultConfirmList_tag0"></div>
		<div id="viewResultConfirmList_tag1"></div>
		<div id="viewResultConfirmList_tag2"></div>
		<div id="viewResultConfirmList_tag3"></div>
		<div id="viewResultConfirmList_tag6"></div>
		<div id="viewResultConfirmList_tag5"></div>
	</div>
</div>
