<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<%
	response.setContentType("application/vnd.ms-excel");//设置正确的输出类型  
	response.setHeader("Content-disposition",
			"attachment;filename=total.xls");
%>

<script type="text/javascript">
var href3;
function searthURL() {
	//获取搜索条件
	var name = "&";
	var year = $("#seach_YEAR").attr("value");
	
	name += "seach_YEAR=" + year;
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
	var dept = $("input[syslong='viewApplyLeaveInfoList_seachDept']").val();
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
<form action="/sys/util/pageExportUtil" method="post" id="hr3204_form">
	<input type="hidden" id="hr3204_text" name="allData" value="" />

</form>
<div class="pageHeader">
	<div class="searchBar">
		<form class="j-ajax" action="/hrm/approve/ManageCountInfoSonList"
			method="post" id="ManageCountInfoSonList"
			name="ManageCountInfoSonList" target="navTab">
			<table class="searchContent">
				<input type="hidden" id="currentIndex2" name="currentIndex" />
				<tr>
					<td class="tr_title">
					<!-- 年月 -->  <spring:message code="hrm.approve.year_month" />
					</td>
					<td>
						<input id="seach_YEAR" type="text" name="seach_YEAR" class="Wdate"
							onClick="WdatePicker({dateFmt:'MM.yyyy',lang:'en'})" value="${FROM_DATE}" />
					</td>
				</tr>
				<tr>
					<td>
						<!-- 员工类型 --> <spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
					</td>
					<td>
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
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="hr" id="viewApplyLeaveInfoList_seachDept"
							selected="${managePart}" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="hr" id="viewApplyLeaveInfoList_seachDept"
							selected="${managePart}" />
						<input type="text" name="STATUS_CODE" id="STATUS_CODE" value="15119" style="display:none"/>
					</td>
					</tr>
					<!--<tr>
					<td>
					 员工状态  <spring:message code="hrm.empinfo.EMP_OFFICE_NAME" />
					</td>
					<td>
						<table>
							<tr>
								<td><input type="hidden" value="${fn:length(empStatusCodeList)}" name="STATUS_CODE_COUNT" id="STATUS_CODE_COUNT">
									<c:forEach items="${empStatusCodeList}" var="item1" varStatus="i">
										<input type="radio" name="STATUS_CODE" id="STATUS_CODE${i.index}" onclick="delSelect(this)" title="${i.index}" value="${item1.CODE_NO}">
												${item1.CODENAME}&nbsp;&nbsp;&nbsp;&nbsp;
									</c:forEach>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			--></table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div align="center">

								<a onclick="javascript:searthURL()" style="cursor: pointer;" id="seach_info"><span><spring:message
											code="public.title.search" /><!--查询 --></span> </a>
							</div>
						</div>
					</li>
					<li>
						<div align="center">
							<a class="button" onclick="javascript:exportURL()"
								style="cursor: pointer;"><span>  
								<!-- 导出到EXCEL -->  <spring:message code="hrm.empinfo.EXPORT" />  
								 </span> </a>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
<div class="pageContent" layoutH="140">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li>
						<a id="hreff0"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0"
							class="j-ajax"><span id="link0">   <!-- 职级别  -->  <spring:message code="hrm.approve.RANKBIE" />     </span> </a>
					</li>
					<li style="display: none">
						<a id="hreff1"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							class="j-ajax"><span id="link1">   <!--部门别  --> <spring:message code="ar.viewardetailcaculate.title.bydept" />             </span> </a>
					</li>
					<li>
						<a id="hreff2"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2"
							class="j-ajax"><span id="link2"> <!--学历别  -->  <spring:message code="hrm.approve.BYEDUCATION" />        </span> </a>
					</li>
					<li>
						<a id="hreff3"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=3"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=3"
							class="j-ajax"><span id="link3"> <!-- 年龄别  -->    <spring:message code="hrm.approve.BYEGE" />     </span> </a>
					</li>
					<li>
						<a id="hreff4"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=4"
							class="j-ajax"><span id="link4"> <!--员工类型别   --> <spring:message code="hrm.approve.BYEMPLOYEE_TYPE" /> </span> </a>
					</li>
					<li style="display: none">
						<a id="hreff5"
							href="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=5"
							type="/hrm/approve/ManageCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=5"
							class="j-ajax"><span id="link5"> <!-- 工龄别   --><spring:message code="hrm.empinfo.WORK_AGE.Z" />  </span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" layoutH="180">
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>

</div>


