<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function navTabSearchb(obj) {
	var currentIndex = $("#arForDeptCountInfoList_currentIndex").attr("value");

	$("#ar0151_currentIndex").attr("value", currentIndex);

	var empType="";
	$("input[name='EMP_TYPE_CODE_PAGE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = "&EMP_TYPE_CODE=" + empType + "'empty'";
	var dept = $("input[syslong='viewApplyLeaveInfoList_seachDept']").val();
	var seach_STIME = $("#arForDeptCountInfoList_seach_STIME").attr("value");
	var seach_ETIME = $("#arForDeptCountInfoList_seach_ETIME").attr("value");
	openOnRight('/ar/countAttendance/arForDeptCountInfoSonList?currentIndex='
			+ currentIndex + '&seach_managePart=' + dept + '&seach_STIME='
			+ seach_STIME  + '&seach_ETIME=' + seach_ETIME + empType
			, 'arForDeptCountInfoList_tag' + currentIndex);
	return false;
}
function downloadExl(url) {
	var empType="";
	$("input[name='EMP_TYPE_CODE_PAGE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = "&EMP_TYPE_CODE=" + empType + "'empty'";
	url += '?'+empType;
	$("#currentIndex_ar0151").attr("value",
			$("#arForDeptCountInfoList_currentIndex").attr("value"));
	$('#ManageCountInfoSonList').attr("action", url);
	$('#ManageCountInfoSonList').attr("onsubmit", '');
	$('#ManageCountInfoSonList').submit();
	$('#ManageCountInfoSonList').attr("action",
			'/ar/countAttendance/arForDeptCountInfoSonList');
	$('#ManageCountInfoSonList')
			.attr("onsubmit", 'return  navTabSearchb(this);');
}
</script>

<div class="pageContent">
	<div class="searchBar">
		<form class="j-ajax" onsubmit="return  navTabSearchb(this)"
			action="/ar/countAttendance/arForDeptCountInfoSonList" method="post"
			id="ManageCountInfoSonList" name="ManageCountInfoSonList"
			target="navTab">


			<table class="searchContent">
				<input type="hidden" id="currentIndex_ar0151" value="${currentIndex}" name="currentIndex">
				<input type="hidden" id="ar0151_currentIndex" name="currentIndex" />

				<tr>
					<td>

						<!-- 期间 --><spring:message code="ess.infoApply.Period"/>
					</td>
					<td>
						<input type="text" id="arForDeptCountInfoList_seach_STIME"
							name="seach_STIME" value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
						~
						<input type="text" id="arForDeptCountInfoList_seach_ETIME"
							name="seach_ETIME" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${ETIME}" />
					</td>


					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_managePart" cpnyId="${defaultCpny}"
							limit="ar" id="viewApplyLeaveInfoList_seachDept"
							selected="${managePart}" />
						<ait:deptTreeIcon name="seach_managePart" cpnyId="${defaultCpny}"
							limit="ar" id="viewApplyLeaveInfoList_seachDept"
							selected="${managePart}" />
					</td>

				</tr>
				<tr>
					<td>
						<!-- 员工类型 -->
						<spring:message code="ess.infoApply.employee_type" />
					</td>
					<td>
						<table>
							<tr>
								<td>
									<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
										<input type="checkbox" id="arForDeptCountInfoList_EMP_TYPE_CODE_${i.index}" name="EMP_TYPE_CODE_PAGE" checked="checked" value="${item.CODE_NO }">
												${item.CODENAME}&nbsp;&nbsp;&nbsp;&nbsp;
									</c:forEach>
								</td>
							</tr>
						</table>
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
			</table>
			<div class="subBar" style="margin-top: 5px">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center">
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
									onclick="downloadExl('/ar/countAttendance/arForDeptCountInfoSonListExport')"
									href="#"> <span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span> </a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>


	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="arForDeptCountInfoList_currentIndex"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li>
						<a id="hreff0"
							href="/ar/countAttendance/arForDeptCountInfoSonList?managePart=${managePart }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							type="/ar/countAttendance/arForDeptCountInfoSonList?managePart=${managePart }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							class="j-ajax"><span id="link0"><!--部门考勤现况--><spring:message code="ar.arForDeptCountInfoList.BUMENKAOQINXIANKUANG.b" /></span> </a>
					</li>

					<li>
						<a id="hreff2"
							href="/ar/countAttendance/arForDeptCountInfoSonList?managePart=${managePart }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							type="/ar/countAttendance/arForDeptCountInfoSonList?managePart=${managePart }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							class="j-ajax"><span id="link2"><!--部门加班现况--><spring:message code="ar.arForDeptCountInfoList.BUMENJIABANXIANKUANG.b" /></span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height: 565px;">
			<div id="arForDeptCountInfoList_tag0"></div>
			<div id="arForDeptCountInfoList_tag1"></div>

		</div>
	</div>

</div>
