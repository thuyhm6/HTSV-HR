<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
var href3;
function searthURL_hrCountInfoList() {
	//获取搜索条件
	var name = "&";

	var year = $("#seach_FROM_DATE1").attr("value");
	name += "seach_FROM_DATE=" + year;
	var year2 = $("#seach_TO_DATE1").attr("value");
	name += "&seach_TO_DATE=" + year2;

	var empType="";
	$("input[name='EMP_TYPE_CODE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = empType + "'empty'";
	name += "&EMP_TYPE_CODE=" + empType;

	var dept = $("input[syslong='viewApplyLeaveInfoList_seachDept1']").val();

	name += "&seach_managePart=" + dept;

	var currentIndex = $("#viewHistoryOrgPanel_currentIndex1").attr("value");

	href = $("#hreff1" + currentIndex).attr("type");

	$("#hreff1" + currentIndex).attr("href", href + name);
	var href2 = $("#hreff1" + currentIndex).attr("href");

	$("#link1" + currentIndex).click();

}
</script>
<form action="/sys/util/pageExportUtil" method="post" id="hr3206_form">
	<input type="hidden" id="hr3206_text" name="allData" value="" />
</form>
<div class="pageHeader">
	<div class="searchBar">
		<form class="j-ajax" action="/hrm/approve/hrCountInfoSonList"
			method="post" id="hrCountInfoSonList" name="hrCountInfoSonList"
			target="navTab">
			<table class="searchContent">
				<input type="hidden" id="currentIndex2" name="currentIndex" />
				<tr>
					<td class="tr_title">
						<spring:message code="hrm.empinfo.Period" /><!-- 期间 -->
					</td>
					<td>
						<input id="seach_FROM_DATE1" type="text" name="seach_FROM_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${FROM_DATE}" />
						~
						<input id="seach_TO_DATE1" type="text" name="seach_TO_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${TO_DATE}" />
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							selected="${DEPTNO}" limit="hr"
							id="viewApplyLeaveInfoList_seachDept1" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="hr" id="viewApplyLeaveInfoList_seachDept1"
							selected="${DEPTNO}" />
					</td>
				</tr>
				<tr>
					<td>
						<spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 -->
					</td>
					<td colspan="3">
									<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
										<input type="checkbox" name="EMP_TYPE_CODE" checked="checked" value="${item.CODE_NO }">
												${item.CODENAME}&nbsp;&nbsp;&nbsp;&nbsp;
									</c:forEach>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div>
							<div align="center">

								<a class="button"
									onclick="javascript:searthURL_hrCountInfoList()"
									style="cursor: pointer;"><span><spring:message
											code="public.title.search" /><!-- 查询 --> </span> </a>
							</div>
						</div>
					</li>
					<li>
						<div align="center">
							<a class="button" onclick="javascript:exportURL_hr3206()"
								style="cursor: pointer;">
								<span><spring:message code="hrm.empinfo.EXPORT" /><!-- 导出到EXCEL --> </span> </a>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
<div class="pageContent" layoutH="100">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex1"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>

					<!--<li>
						<a id="hreff10"
							href="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							type="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							class="j-ajax">
							<span id="link10"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /> 员工类型 </span> </a>
					</li>
				
						--><li>
							<a id="hreff11"
								href="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
								type="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
								class="j-ajax">
								<span id="link11"><spring:message code="hrm.report.RUZHIQUFE.Z" /> <!-- 入职区分 --> </span> </a>
						</li>
					<li>
						<a id="hreff12"
							href="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							type="/hrm/approve/hrCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							class="j-ajax">
							<span id="link12"> <spring:message code="hr.viewCondSql.title.ZHIJI" /><!-- 职级 --></span> 
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" layoutH="140">
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>

</div>
