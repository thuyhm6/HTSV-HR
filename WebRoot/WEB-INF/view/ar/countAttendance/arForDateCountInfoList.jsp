<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearcha(obj) {

	var currentIndex = $("#ar0153_currentIndex").attr("value");

	$("#currentIndex_ar0153").attr("value", currentIndex);
	var dept = $("input[syslong='arForDateCountInfoList_seachDept']").val();

	var seach_STIME = $("#arForDateCountInfoList_seach_STIME").attr("value");
	var seach_ETIME = $("#arForDateCountInfoList_seach_ETIME").attr("value");

	openOnRight('/ar/countAttendance/arForDateCountInfoSonList?currentIndex='
			+ currentIndex + '&seach_DEPT_NO=' + dept + '&seach_STIME='
			+ seach_STIME + '&seach_ETIME=' + seach_ETIME,
			'arForDateCountInfoList_tag' + currentIndex);
	return false;
}

function downloadExl(url) {
	$("#currentIndex_ar0153").attr("value",
			$("#ar0153_currentIndex").attr("value"));
	$('#arForDateCountInfoList').attr("action", url);
	$('#arForDateCountInfoList').attr("onsubmit", '');
	$('#arForDateCountInfoList').submit();
	$('#arForDateCountInfoList').attr("action",
			'/ar/countAttendance/arForDateCountInfoList');
	$('#arForDateCountInfoList')
			.attr("onsubmit", 'return navTabSearcha(this);');
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearcha(this);"
		action="/ar/countAttendance/arForDateCountInfoList" method="post"
		id="arForDateCountInfoList" name="viewArPersonalList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>

						<input type="hidden" format="dd/MM/yyyy" id="STIMESS"
							value="${STIME}" />
						<input type="hidden" format="dd/MM/yyyy" id="ETIMESS"
							value="${ETIME}" />
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" />
					</td>
					<td>
						<input type="text" id="arForDateCountInfoList_seach_STIME"
							name="seach_STIME" value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td>
						<input type="text" id="arForDateCountInfoList_seach_ETIME"
							name="seach_ETIME" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${ETIME}" />
					</td>
				</tr>
				<tr>
					<input type="hidden" id="currentIndex_ar0153"
						value="${currentIndex}" name="currentIndex">
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arForDateCountInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arForDateCountInfoList_seachDept"
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
									onclick="downloadExl('/ar/countAttendance/arForDateCountInfoSonListExport')"
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
	<input type="hidden" id="ar0153_currentIndex" value="${currentIndex}">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li>
					<a id="hreff0"
						href="/ar/countAttendance/arForDateCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						type="/ar/countAttendance/arForDateCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						class="j-ajax"><span id="link0"><!-- 日期别考勤 --><spring:message code="ar.arForDateCountInfoList.RIQIBIEKAOQIN.b"/></span> </a>
				</li>

				<li>
					<a id="hreff1"
						href="/ar/countAttendance/arForDateCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						type="/ar/countAttendance/arForDateCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						class="j-ajax"><span id="link1"><!-- 日期别加班 --><spring:message code="ar.arForDateCountInfoList.RIQIBIEJIABAN.b"/></span> </a>
				</li>


			</ul>
		</div>
	</div>
	<div class="tabsContent" style="height: 565px;">
		<div id="arForDateCountInfoList_tag0"></div>
		<div id="arForDateCountInfoList_tag1"></div>

	</div>
</div>
