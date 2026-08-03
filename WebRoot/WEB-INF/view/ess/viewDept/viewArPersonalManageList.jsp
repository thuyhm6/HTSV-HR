<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script>

function changeURL(obj) {
  	obj.href = "/ess/viewDept/viewArPersonalSingleList?ITEM_NO=" + obj.name
			+ "&PERSON_ID=" + obj.type + "&STIMESS="
			+ $("#STIMESS").attr("value") + "&ETIMESS="
			+ $("#ETIMESS").attr("value");
 }

function downloadExl(url) {
	$('#viewArPersonalManageList').attr("action", url);
	$('#viewArPersonalManageList').attr("onsubmit", '');
	$('#viewArPersonalManageList').submit();
	$('#viewArPersonalManageList').attr("action",
			'/ess/viewDept/viewArPersonalManageList');
	$('#viewArPersonalManageList')
			.attr("onsubmit", 'return navTabSearch(this)');
}

function exportExcle(a) {
	var $this = $(a);
	var title = $this.attr("title");
	var $from = $("#viewArVacationMonth");

	var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	alertMsg.confirm(title, {
		okCall : function() {
			window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
					+ $from.serialize();
		}
	});
}
</script>
<div class="panel">
	<h1>
		个人考勤现况
	</h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewArPersonalManageList" rel="pagerForm"
		method="post" id="viewArPersonalManageList"
		name="viewArPersonalManageList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="viewArPersonalManageList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="viewArPersonalManageList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>

						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>

						<input type="hidden" format="yyyy/MM/dd" id="STIMESS"
							value="${STIME}" />
						<input type="hidden" format="yyyy/MM/dd" id="ETIMESS"
							value="${ETIME}" />
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" />
					</td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME"
							value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td>
						<input type="text" id="seach_ETIME" name="seach_ETIME"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})"
							value="${ETIME}" />
					</td>

					<%--<td>
						班次
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="SHIFT_NO"
							parentNo="14013793" cnpyID="${LoginUser.cpnyId}" />
					</td>
					--%>
					<td>
						班组
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_GROUP" id="GROUP"
							limit="all" parentNo="400223" cnpyID="${LoginUser.cpnyId}" />
					</td>
					<td>
						审批状态
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG"
							id="AFFIRM_FLAG" limit="all" parentNo="14014304"
							cnpyID="${LoginUser.cpnyId}" selected="${AFFIRM_FLAG}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=143')"
							href="#"> <span>导出到Excel</span> </a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">

	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="/ess/viewDept/viewArPersonalSingleList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
		target="dialog">
		<table class="table" layoutH="235" border="1" width="1500px"
			nowrapTD="false">
			<thead>
				<tr>
					<th>
						NO
					</th>
					<%--<td
						style="layout-flow: vertical-ideographic; height: 50px; overflow: auto">
						<!--姓名 -->
						姓名
					</td>
					--%>
					<th>
						<!--姓名 -->
						姓名
					</th>
					<th>
						<!--部门 -->
						社号
					</th>
					<th>
						<!--等级名-->
						部门名
					</th>
					<th>
						<!--GEN -->
						职级
					</th>
					<th>
						<!--GEN -->
						合计
					</th>
					<th>
						<!--GEN -->
						病假
					</th>
					<th>
						<!--GEN -->
						病假2
					</th>
					<th>
						<!--GEN -->
						事假
					</th>
					<th>
						<!--GEN -->
						产假
					</th>
					<th>
						<!--GEN -->
						产假1
					</th>
					<th>
						<!--GEN -->
						哺乳假
					</th>
					<th>
						<!--GEN -->
						哺乳集体
					</th>
					<th>
						<!--GEN -->
						计划生育假
					</th>
					<th>
						<!--GEN -->
						护理假
					</th>
					<th>
						<!--GEN -->
						产前检查假
					</th>
					<th>
						<!--GEN -->
						婚假
					</th>
					<th>
						<!--GEN -->
						丧假
					</th>
					<th>
						<!--GEN -->
						公假
					</th>
					<th>
						<!--GEN -->
						工伤
					</th>
					<th>
						<!--GEN -->
						工伤1
					</th>
					<th>
						<!--GEN -->
						停工休假
					</th>
					<th>
						<!--GEN -->
						迟到
					</th>
					<th>
						<!--GEN -->
						早退
					</th>
					<th>
						<!--GEN -->
						旷工
					</th>
					<th>
						<!--GEN -->
						年假
					</th>
					<th>
						<!--GEN -->
						倒休
					</th>
					<th>
						<!--GEN -->
						厂车迟到
					</th>
					<th>
						<!--GEN -->
						出差
					</th>
					<th>
						<!--GEN -->
						研修
					</th>
					<th>
						<!--GEN -->
						会议培训
					</th>
					<th>
						<!--GEN -->
						其他
					</th>





				</tr>





			</thead>
			<tbody>
				<c:forEach items="${personList}" var="personList" varStatus="i">
					<tr target="sid" rel="${personList.PERSON_ID_ID}">
						<td style="text-align: center">

							${i.count}
						</td>
						<td style="text-align: center">

							${personList.LOCAL_NAME}
						</td>
						<td style="text-align: center">

							${personList.EMPID_ID}
						</td>
						<td style="text-align: center">

							${personList.DEPT_NAME}
						</td>
						<td style="text-align: center">

							${personList.POST_GRADE_NO}
						</td>
						<td style="text-align: center">

							${personList.ALLNUM}
						</td>

						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="141464" type="${personList.PERSON_ID_ID}" target="dialog">
								<span>${personList.BJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="14013784" type="${personList.PERSON_ID_ID}"
								target="dialog"> <span>${personList.BJ2}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" onclick='javascript:changeURL(this);'
								name="141454" type="${personList.PERSON_ID_ID}" target="dialog">
								<span>${personList.SJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141461"
								target="dialog"> <span>${personList.CJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141463"
								target="dialog"> <span>${personList.CJ1}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141462"
								target="dialog"> <span>${personList.BRJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013787"
								target="dialog"> <span>${personList.BRJJT}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013788"
								target="dialog"> <span>${personList.JHSYJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013789"
								target="dialog"> <span>${personList.HLJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141460"
								target="dialog"> <span>${personList.CQJCJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141458"
								target="dialog"> <span>${personList.HJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141459"
								target="dialog"> <span>${personList.DJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="218125"
								target="dialog"> <span>${personList.GJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141465"
								target="dialog"> <span>${personList.GSJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013785"
								target="dialog"> <span>${personList.GSJ1}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013790"
								target="dialog"> <span>${personList.TGXJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141441"
								target="dialog"> <span>${personList.CD}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141442"
								target="dialog"> <span>${personList.ZT}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141443"
								target="dialog"> <span>${personList.KG}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141456"
								target="dialog"> <span>${personList.NJ}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013845"
								target="dialog"> <span>${personList.DX}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013783"
								target="dialog"> <span>${personList.CCCD}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="141469"
								target="dialog"> <span>${personList.CC}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013791"
								target="dialog"> <span>${personList.YX}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="218126"
								target="dialog"> <span>${personList.HYPX}</span> </a>
						</td>
						<td style="text-align: center">
							<a style="cursor: pointer;" width="800" height="400"
								id="codeChange" type="${personList.PERSON_ID_ID}"
								onclick='javascript:changeURL(this);' name="14013792"
								target="dialog"> <span>${personList.QT}</span> </a>
						</td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/ess/viewDept/viewArPersonalManageList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>