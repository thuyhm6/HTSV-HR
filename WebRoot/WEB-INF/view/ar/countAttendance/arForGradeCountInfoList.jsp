<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearcha(obj) {

	var currentIndex = $("#arForGradeCountInfoList_currentIndex").attr("value");

	$("#currentIndex_ar0154").attr("value", currentIndex);
	var dept = $("input[syslong='arForGradeCountInfoList_seachDept']").val();

	var seach_STIME = $("#arForGradeCountInfoList_seach_STIME").attr("value");
	var seach_ETIME = $("#arForGradeCountInfoList_seach_ETIME").attr("value");

	openOnRight('/ar/countAttendance/arForGradeCountInfoSonList?currentIndex='
			+ currentIndex + '&seach_DEPT_NO=' + dept + '&seach_STIME='
			+ seach_STIME + '&seach_ETIME=' + seach_ETIME,
			'arForGradeCountInfoList_tag' + currentIndex);
	return false;
}

function downloadExl(url) {
	$("#currentIndex_ar0154").attr("value",
			$("#arForGradeCountInfoList_currentIndex").attr("value"));
	$('#arForGradeCountInfoList').attr("action", url);
	$('#arForGradeCountInfoList').attr("onsubmit", '');
	$('#arForGradeCountInfoList').submit();
	$('#arForGradeCountInfoList').attr("action",
			'/ar/countAttendance/arForDateCountInfoList');
	$('#arForGradeCountInfoList').attr("onsubmit",
			'return navTabSearcha(this);');
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearcha(this);"
		action="/ar/countAttendance/arForGradeCountInfoList" method="post"
		id="arForGradeCountInfoList" name="viewArPersonalList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">

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
						<input type="text" id="arForGradeCountInfoList_seach_STIME"
							name="seach_STIME" value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td>
						<input type="text" id="arForGradeCountInfoList_seach_ETIME"
							name="seach_ETIME" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${ETIME}" />
					</td>
				</tr>
				<tr>
					<input type="hidden" id="currentIndex_ar0154"
						value="${currentIndex}" name="currentIndex">
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arForGradeCountInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arForGradeCountInfoList_seachDept"
							selected="${DEPTNO}" />
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
						<div>
							<div class="buttonContent">
								<a class="buttonActive"
									onclick="downloadExl('/ar/countAttendance/arForGradeCountInfoSonListExport')"
									href="#"> <span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span> </a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="tabs" eventType="click" currentIndex="${currentIndex }">
	<input type="hidden" id="arForGradeCountInfoList_currentIndex"
		value="${currentIndex}">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li>
					<a id="hreff0"
						href="/ar/countAttendance/arForGradeCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						type="/ar/countAttendance/arForGradeCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						class="j-ajax"><span id="link0"><!--职级别考勤--><spring:message code="ar.arForGradeCountInfoList.ZHIJIBIEKAOQIN.b"/></span> </a>
				</li>

				<li>
					<a id="hreff1"
						href="/ar/countAttendance/arForGradeCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						type="/ar/countAttendance/arForGradeCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						class="j-ajax"><span id="link1"><!--职级别加班--><spring:message code="ar.arForGradeCountInfoList.ZHIJIBIEJIABAN.b"/></span> </a>
				</li>


			</ul>
		</div>
	</div>
	<div class="tabsContent" style="height: 565px;">
		<div id="arForGradeCountInfoList_tag0"></div>
		<div id="arForGradeCountInfoList_tag1"></div>

	</div>
</div>
