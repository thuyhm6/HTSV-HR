<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
var href3;
function searthURL() {
	//获取搜索条件
	var name = "&";
	var year = $("#seach_YEAR",navTab.getCurrentPanel()).attr("value");
	var yearFlag = '';
	if (year < '2014/06') {
		yearFlag = 'flag';
	}
	name += "seach_YEAR=" + year;
	name += "&yearFlag=" + yearFlag;
	var empType="";
	$("input[name='EMP_TYPE_CODE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = empType + "'empty'";
	name += "&seach_EMP_TYPE_CODE=" + empType;
	var scount = $("#STATUS_CODE_COUNT").val();
	for ( var i = 1; i < scount; i++) {
		if ($("#STATUS_CODE" + i).prop("checked")) {
			var STATUS_CODE = $("#STATUS_CODE" + i).attr("value");
			name += "&seach_STATUS_CODE=" + STATUS_CODE;
		}
	}
	var dept = $("input[syslong='ManageCountInfoList_seachDept']").val();
	name += "&seach_managePart=" + dept;
	var currentIndex = $("#viewHistoryOrgPanel_currentIndex").attr("value");
	href = $("#hreff" + currentIndex).attr("type");
	$("#hreff" + currentIndex).attr("href", href + name);
	var href2 = $("#hreff" + currentIndex).attr("href");
	$("#link" + currentIndex).click();
}

function delSelect(obj) {
	if (obj.title == "1") {
		obj.title = "0";
		obj.checked = "";
	} else {
		obj.title = "1";
	}
}
</script>
<div class="pageContent">
	<div class="searchBar">
		<form class="j-ajax" action="/ess/viewDept/ManageCountInfoSonList"
			method="post" id="ManageCountInfoSonList"
			name="ManageCountInfoSonList" target="navTab">
			<table class="searchContent">
				<input type="hidden" id="currentIndex2" name="currentIndex" />
				<tr>
					<td class="tr_title">
					<!-- 年月 --><spring:message code="ess.infoApply.YEAR_MONTH" />
					</td>
					<td>
						<input id="seach_YEAR" type="text" name="seach_YEAR" class="Wdate"
							onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${DDATE}" />
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="ManageCountInfoList_seachDept" limit="manager" selected="${managePart}"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="ManageCountInfoList_seachDept" limit="manager" selected="${managePart}" />
					</td>
				</tr>
				<tr>
					<td>
						<!-- 员工类型 --> <spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
					</td>
					<td colspan="3">
						<table>
							<tr>
								<td>
									<input type="hidden" value="${fn:length(empTypeCodeList)}" name="EMP_TYPE_CODE_COUNT" id="EMP_TYPE_CODE_COUNT">
									<c:forEach items="${empTypeCodeList}" var="item" varStatus="i">
										<input type="checkbox" id="EMP_TYPE_CODE${i.index}" name="EMP_TYPE_CODE" checked="checked" value="${item.CODE_NO}">
												${item.CODENAME}&nbsp;&nbsp;&nbsp;&nbsp;
									</c:forEach>
								</td>
							</tr>
							
						</table>
					</td>
				</tr>

			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div align="center">

								<a onclick="javascript:searthURL()" style="cursor: pointer;"><span><!--查询 --><spring:message
											code="public.title.search" />
								</span> </a>

							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>

	<a id=""></a>
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li>
						<a id="hreff0"
							href="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							type="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							class="j-ajax"><span id="link0"><!--职级别 --><spring:message code="ess.infoApply.vocational_level" /></span> </a>
					</li>
					<li>
						<a id="hreff2"
							href="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							type="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							class="j-ajax"><span id="link2"><!--学历别 --><spring:message code="ess.infoApply.education_level" /></span> </a>
					</li>
					<li>
						<a id="hreff3"
							href="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=3"
							type="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=3"
							class="j-ajax"><span id="link3"><!--年龄别 --><spring:message code="ess.infoApply.age_level" /></span> </a>
					</li>
					<li>
						<a id="hreff4"
							href="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							type="/ess/viewDept/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							class="j-ajax"><span id="link4"><!--员工类型别 --><spring:message code="hrm.approve.BYEMPLOYEE_TYPE" /> </span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
</div>
