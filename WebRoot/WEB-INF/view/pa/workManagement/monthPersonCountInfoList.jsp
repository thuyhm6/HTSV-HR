<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function changeSearcha(obj, strFlag) {
	var currentIndex = '0';
	var PERSON_ID = obj.name;

	openOnRight('/pa/workManagement/monthPersonCountInfoSonList?currentIndex='
			+ currentIndex + '&PERSON_ID_ID=' + PERSON_ID + '&strFlag='
			+ strFlag, 'monthPersonCountInfoList_tag0');
	return false;
}
function pa1011_Linkage() {
	var obj = document.getElementById("pa1011_SALARY_DISTIN_NO");
	var dis = $(obj).attr("value");
	$("#pa1011_PAY_DATE option").remove();
	$("#pa1011_PAY_DATE_PRO option").remove();

	$("#pa1011_Linkages option").each(
			function() {

				if ($(this).attr("title") == dis) {
					var ddq = "<option value='" + $(this).attr('value')
							+ "' title='" + $(this).attr('title')
							+ "'  onChange='pa1011_LinkageTwo(this)'>"
							+ $(this).html() + "</option>";

					$("#pa1011_PAY_DATE").append(ddq);
					$("#pa1011_PAY_DATE_PRO").append(ddq);

				}
			});

}
function pa1011_LinkageTwo(obj) {

}

$(document).ready(function() {
	pa1011_Linkage();
	seldChange();
});

function seldChange() {
	var date = $("#pa1011_PAY_DATE_SELD").attr("value");
	var date_pro = $("#pa1011_PAY_DATE_PRO_SELD").attr("value");
	var distin = $("#pa1011_SALARY_DISTIN_NO_SELD").attr("value");

	$("#pa1011_PAY_DATE option").each(function() {
		if ($(this).attr("title") == distin && $(this).attr("value") == date) {
			$(this).attr("selected", "selected")
		}
	});

	$("#pa1011_PAY_DATE_PRO option").each(
			function() {
				if ($(this).attr("title") == distin
						&& $(this).attr("value") == date_pro) {
					$(this).attr("selected", "selected")
				}
			});

}
</script>
<div class="pageHeader">
	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/monthPersonCountInfoList" method="post"
		id="monthPersonCountInfoList" name="monthPersonCountInfoList">

		<div class="searchBar">
			<table class="searchContent">
				<!-- 支付计划 -->
				<tr>
					<td>
						<!--工资区分 --><spring:message code="pa.monthPersonCountInfoList.GONGZIQUFEN.b" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO"
							selected="${SALARY_DISTIN_NO}" parentNo="14013797"
							id="pa1011_SALARY_DISTIN_NO" cnpyID="${LoginUser.cpnyId}"
							onChangeName="pa1011_Linkage()" />
					</td>

					<td>
						<!--支付日期 --><spring:message code="display.pa.ecc.paydate" />
					</td>
					<td>

						<select id="pa1011_PAY_DATE_PRO" name="PAY_DATE_PRO">
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
						---
						<select id="pa1011_PAY_DATE" name="PAY_DATE"
							onchange="pa1011_LinkageTwo(this)">
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
				</tr>
				<select id="pa1011_Linkages" style="display: none">
					<c:forEach items="${paPayScheduleList}" var="paySchedule"
						varStatus="i">

						<option value="${paySchedule.PAY_DATE }"
							title="${paySchedule.SALARY_DISTIN_NO}">
							${paySchedule.PAY_DATE } 
						</option>
					</c:forEach>
				</select>
				<input type="hidden" id="pa1011_PAY_DATE_SELD" value="${PAY_DATE}" />
				<input type="hidden" id="pa1011_PAY_DATE_PRO_SELD"
					value="${PAY_DATE_PRO}" />
				<input type="hidden" id="pa1011_SALARY_DISTIN_NO_SELD"
					value="${SALARY_DISTIN_NO}" />
				<!-- 支付计划 -->
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center">
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
<div class="pageContent" style="width:98%">
	<div style="float: left; width: 60%">
		<%--    左
		--%>
		<table border="0" cellpadding="0" cellspacing="0" class="user_table" width="100%">
			<tr>
				<td style="text-align: center" class="td_title" width="25%">
					<!--工资区分 --><spring:message code="pa.monthPersonCountInfoList.GONGZIQUFEN.b" />
				</td>
				<td style="text-align: center" class="td_title" width="25%">
					<!--前月 --><spring:message code="display.emp.ben.or.benhs68" />
				</td>
				<td style="text-align: center" class="td_title" width="25%">
					<!--当月 --><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
				</td>
				<td style="text-align: center" class="td_title" width="25%">
					<!--增减人员 --><spring:message code="pa.monthPersonCountInfoList.ZENGJIANRENYUAN.b" />
				</td>
			</tr>
			<tr>
				<c:forEach items="${monthPersonCountInfoList}" var="item"
					varStatus="i">
					<td style="text-align: center" class="td_type">
						${item.SALARY_DISTIN}
					</td>
					<td style="text-align: center" class="td_type">
						${item.PASSTIME}
					</td>
					<td style="text-align: center" class="td_type">
						${item.NOWTIME}
					</td>
					<c:choose>
						<c:when test="${item.NOWTIME <  item.PASSTIME}">
							<td style="text-align: center" class="td_type">
								${item.PASSTIME-item.NOWTIME}
							</td>
						</c:when>
						<c:otherwise>
							<td style="text-align: center" class="td_type">
								${item.NOWTIME-item.PASSTIME}
							</td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
			<c:if test="${totalCount == 0 }">
				<tr>
					<td style="text-align: left;" colspan="9">
						<!--没有查找的数据 --><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" />
					</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div style="float: left; width: 20%">
		<!--中 -->
		<table class="user_table" width="100%">
			<tr>
				<td style="text-align: center" class="td_title" width="50%">
					<!--增加人员区分 --><spring:message code="pa.monthPersonCountInfoList.ZENGJIANRENYUANQUFEN.b" />
				</td>
				<td style="text-align: center" class="td_title" width="50%">
					<!--增加人员 --><spring:message code="pa.monthPersonCountInfoList.ZENGJIARENYUAN.b" />
				</td>
			</tr>
			<c:forEach items="${monthPersonIncreaseList}" var="item"
				varStatus="i">
				<tr>
					<td style="text-align: center" class="td_type">
						${item.PASSTIME}
					</td>
					<td style="text-align: center" class="td_type">
						<a style="text-decoration: none; cursor: pointer;"
							name="${item.PERSON_ID},${item.PERSON_ID1},${item.PERSON_ID2}"
							onclick="changeSearcha(this,'add')"><font color="blue">${item.NUMB}</font>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="float: left; width: 20%" width="20%">
		<!--  右   -->
		<table class="user_table" width="100%">
			<tr>
				<td style="text-align: center" class="td_title" width="50%">
					<!--减少人员区分 --><spring:message code="pa.monthPersonCountInfoList.JIANSHAORENYUANQUFEN.b" />
				</td>
				<td style="text-align: center" class="td_title" width="50%">
					<!--减少人员 --><spring:message code="pa.monthPersonCountInfoList.JIANSHAORENYUAN.b" />
				</td>
			</tr>
			<c:forEach items="${monthPersonDecreaseList}" var="item"
				varStatus="i">
				<tr>
					<td style="text-align: center" class="td_type">
						${item.PASSTIME}
					</td>
					<td style="text-align: center" class="td_type">
						<a style="text-decoration: none; cursor: pointer;"
							name="${item.PERSON_ID},${item.PERSON_ID1},${item.PERSON_ID2}"
							onclick="changeSearcha(this,'delete')"><font color="blue">${item.NUMB}</font>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>


	<div class="tabs" eventType="click" currentIndex="${currentIndex}"
		style="float: left; width: 100%">
		<input type="hidden" id="monthPersonCountInfoList_currentIndex"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li>
						<a id="hreff0"
							href="/pa/workManagement/monthPersonCountInfoSonList?currentIndex=0"
							type="/pa/workManagement/monthPersonCountInfoSonList?currentIndex=0"
							class="j-ajax"><span id="link0"><!--详细人员列表 --><spring:message code="pa.monthPersonCountInfoList.XIANGXIRENYUANLIEBIAO.b" /></span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="monthPersonCountInfoList_tag0"></div>
		</div>
	</div>
</div>