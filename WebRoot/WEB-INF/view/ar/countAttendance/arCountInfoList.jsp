<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearcha(obj) {
	var currentIndex = $("#ar0152List_currentIndex").attr("value");
	var PA_COUNT = $("#PA_COUNT").attr("value");
	$("#ar0152_currentIndex").attr("value", currentIndex);
	var dept = $("input[syslong='arCountInfoList_seachDept']").val();
	var key = encodeURI($("#arCountInfoList_seachKey").attr("value"));
	var JobTypeGroupNo = $("#arCountInfoList_JobTypeGroupNo").attr("value");
	var EmpTypeCodeNo = $("#arCountInfoList_EmpTypeCodeNo").attr("value");
	var seach_STIME = $("#arCountInfoList_seach_STIME").attr("value");
	var seach_ETIME = $("#arCountInfoList_seach_ETIME").attr("value");
	var GROUP = $("#arCountInfoList_GROUP").attr("value");
	var SALARY_DISTIN_NO = $("#SALARY_DISTIN_NO_ar_CountInfoList").attr("value");
	var AFFIRM_FLAG = $("#arCountInfoList_AFFIRM_FLAG").attr("value");
	var seach_EMP_TYPE_CODE = $("#seach_EMP_TYPE_CODE").attr("value");
	var seach_SHIFT_NO = $("#seach_SHIFT_NO").attr("value");
	openOnRight('/ar/countAttendance/arCountInfoSonList?currentIndex='
			+ currentIndex + '&seach_DEPT_NO=' + dept + '&seach_KEY=' + key
			+ '&seach_JobTypeGroupNo=' + JobTypeGroupNo
			+ '&seach_EmpTypeCodeNo=' + EmpTypeCodeNo + '&seach_STIME='
			+ seach_STIME + '&seach_ETIME=' + seach_ETIME  + '&seach_AFFIRM_FLAG=' + AFFIRM_FLAG + "&PA_COUNT="
			+ PA_COUNT + "&SALARY_DISTIN_NO=" + SALARY_DISTIN_NO + "&seach_EMP_TYPE_CODE=" + seach_EMP_TYPE_CODE 
			+ "&seach_SHIFT_NO=" + seach_SHIFT_NO,
			'ar_CountInfoList_tag' + currentIndex);
	return false;
}
function downloadExl(url) {
	$("#ar0152_currentIndex").attr("value",
	$("#ar0152List_currentIndex").attr("value"));
	$('#arCountInfoList').attr("action", url);
	$('#arCountInfoList').attr("onsubmit", '');
	$('#arCountInfoList').submit();
	$('#arCountInfoList').attr("action", '/ar/countAttendance/arCountInfoList');
	$('#arCountInfoList').attr("onsubmit", 'return navTabSearcha(this);');
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearcha(this);"
		action="/ar/countAttendance/arCountInfoList" method="post"
		id="arCountInfoList" name="viewArPersonalList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<input type="hidden" id="ar0152_currentIndex" value="${currentIndex}"
						name="currentIndex">
					<input type="hidden" id='ar_CountInfoList_tagss'
						value="${currentIndex}" name="ar_CountInfoList_tag">
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arCountInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arCountInfoList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="arCountInfoList_seachKey"
							value="${KEY}" />
						<input type="hidden" name='CPNY' value="${LoginUser.cpnyId}">
					</td>
					<td>
						<!--员工类型--><spring:message code="ess.infoApply.employee_type" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE"
							name="seach_EMP_TYPE_CODE" parentNo="13864"
							selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}"
							limit="all" />
					</td>
						<%-- <td>
							工资区分
						</td>
						<td>
							<ait:SelectSyCodeByCpnyID id="SALARY_DISTIN_NO_ar_CountInfoList"
								name="seach_SALARY_DISTIN_NO" parentNo="14013797"
								cnpyID="${LoginUser.cpnyId}"
								selected="${paPayScheduleInfo.SALARY_DISTIN_NO }" />
						</td>
						<td>
							计算与否
						</td>
						<td>
							<select name="seach_PA_COUNT" id="PA_COUNT">
								<option value="0"
									<c:if test="${PA_COUNT eq '0'}">selected</c:if>>
									否
								</option>
								<option value="1"
									<c:if test="${PA_COUNT eq '1'}">selected</c:if>>
									是
								</option>
							</select>
						</td> --%>
				</tr>
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
						<input type="text" id="arCountInfoList_seach_STIME"
							name="seach_STIME" value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td style="position: relative; overflow: hidden">
						<input type="text" id="arCountInfoList_seach_ETIME"
							name="seach_ETIME" class="Wdate"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${ETIME}" />
					</td>
					<td>
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="seach_SHIFT_NO"
							limit="all" parentNo="400223" selected="${SHIFT_NO}"
							cnpyID="${LoginUser.cpnyId}" />
					</td>
					<%-- <td>
						班组
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_GROUP"
							id="arCountInfoList_GROUP" limit="all" parentNo="400223"
							cnpyID="${LoginUser.cpnyId}" />
					</td> --%>
					<!-- <td>
						审批状态
					</td>
					<td>
						<select name="seach_AFFIRM_FLAG" id="arCountInfoList_AFFIRM_FLAG">
							<option value="0">
								全部
							</option>
							<option value="1" selected="selected">
								部门长批准
							</option>
							<option value="2">
								部门申请
							</option>
						</select>
					</td> -->
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" /><!-- 查询 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div>
							<div class="buttonContent">
								<a class="buttonActive"
									onclick="downloadExl('/ar/countAttendance/arCountInfoSonList_Export')"
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
	<input type="hidden" id="ar0152List_currentIndex"
		value="${currentIndex}">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li>
					<a id="hreff0"
						href="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						type="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
						class="j-ajax"><span id="link0"><!-- 个人考勤现况 --><spring:message code="ar.arCountInfoList.GERENKAOQINXIANKUANG.b"/></span> </a>
				</li>
				<li>
					<a id="hreff1"
						href="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						type="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
						class="j-ajax"><span id="link1"><!-- 个人加班现况 --><spring:message code="ar.arCountInfoList.GERENJIABANXIANKUANG.b"/></span> </a>
				</li>
				<li>
					<a id="hreff2"
						href="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
						type="/ar/countAttendance/arCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
						class="j-ajax"><span id="link2"><!-- 个人年加班 --><spring:message code="ar.arCountInfoList.GERENJIABANXIANKUANG.b"/> <spring:message code="ar.excelexport.title.month"/></span> </a>
				</li>
			</ul>
		</div>
	</div>
	<div class="tabsContent" style="height: 565px;">
		<div id="ar_CountInfoList_tag0"></div>
		<div id="ar_CountInfoList_tag1"></div>
		<div id="ar_CountInfoList_tag2"></div>
	</div>
</div>
