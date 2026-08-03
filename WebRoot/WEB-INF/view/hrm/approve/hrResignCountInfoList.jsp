<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
var href3;
function searthURL_hrResignCountInfoSonList() {
	//获取搜索条件
	var name = "&";

	var year = $("#seach_FROM_DATE2").attr("value");
	name += "seach_FROM_DATE=" + year;
	var year2 = $("#seach_TO_DATE2").attr("value");
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

	var dept = $("input[syslong='viewApplyLeaveInfoList_seachDept2']").val();

	name += "&seach_managePart=" + dept;

	var currentIndex = $("#viewHistoryOrgPanel_currentIndex2").attr("value");

	href = $("#hreff2" + currentIndex).attr("type");

	$("#hreff2" + currentIndex).attr("href", href + name);
	var href2 = $("#hreff2" + currentIndex).attr("href");

	$("#link2" + currentIndex).click();

}
</script>

<form action="/sys/util/pageExportUtil" method="post" id="hr3205_form">
	<input type="hidden" id="hr3205_text" name="allData" value="" />
</form>

<div class="pageHeader">
	<div class="searchBar">
		<form class="j-ajax" action="/hrm/approve/hrResignCountInfoSonList"
			method="post" id="hrResignCountInfoSonList2"
			name="hrResignCountInfoSonList" target="navTab">


			<table class="searchContent">

				<input type="hidden" id="currentIndex2" name="currentIndex" />

				<tr>
					<td class="tr_title">
						  <!-- 期间  -->     <spring:message code="hrm.empinfo.Period" />
					</td>
					<td>

						<input id="seach_FROM_DATE2" type="text" name="seach_FROM_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${FROM_DATE}" />
						~
						<input id="seach_TO_DATE2" type="text" name="seach_TO_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${TO_DATE}" />
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="hr" id="viewApplyLeaveInfoList_seachDept2"
							selected="${managePart}" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="hr" id="viewApplyLeaveInfoList_seachDept2"
							selected="${managePart}" />
					</td>
				</tr>
				<tr>
					<td>
					<!-- 员工类型 -->    <spring:message code="org.title.EMP_TYPE" />

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
						<div class="buttonActive">
							<div align="center">

								<a onclick="javascript:searthURL_hrResignCountInfoSonList()"
									style="cursor: pointer;"><span><spring:message
											code="public.title.search" /> </span> </a>

							</div>
						</div>
					</li>
					<li>
						<div align="center">
							<a class="button" onclick="javascript:exportURL_hr3205()"
								style="cursor: pointer;"><span> <!-- 导出到EXCEL -->  <spring:message code="org.title.exportLOtImportExcel" />    </span> </a>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
<div class="pageContent" layoutH="100">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex2"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li>
						<a id="hreff20"
							href="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							type="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							class="j-ajax"><span id="link20">    <!-- 职级   -->        <spring:message code="org.title.POST_GRADE_NAME" />    </span> </a>
					</li>
					
						<li style="display:none">
							<a id="hreff21"
								href="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
								type="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
								class="j-ajax"><span id="link21">   <!-- 职种  -->      <spring:message code="hrm.contract.POSITION" />            </span> </a>
						</li>
					
					<li>
						<a id="hreff22"
							href="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							type="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							class="j-ajax"><span id="link22"> <!-- 原因    -->     <spring:message code="hrm.empinfo.reason" />  </span> </a>
					</li>
			

					<li>
						<a id="hreff24"
							href="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							type="/hrm/approve/hrResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							class="j-ajax"><span id="link24"> 
						<!-- 员工类型  -->	 <spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
						</span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" layoutH="140">
			<div></div>
			<div></div>
			<div></div>

			<div></div>
		</div>
	</div>

</div>
