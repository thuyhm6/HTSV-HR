<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
var href3;
function searthURL() {
	//获取搜索条件
	var name = "&";

	var year = $("#seach_YEAR",navTab.getCurrentPanel()).attr("value");
	if(year!=''&&year!=null){
		years = parseInt(year) + 1; //例如   2015年份加一  为的是区组织中2015的最后一次组织变更  
		}
	 name += "seach_YEAR=" + years;


	var empType="";
	$("input[name='EMP_TYPE_CODE']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			empType = empType + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empType = empType + "'empty'";
	name += "&EMP_TYPE_CODE=" + empType;

	var dept = $("input[syslong='monthResignCountInfoList_seachDept1']").val();

	name += "&seach_managePart=" + dept;

	var currentIndex = $("#viewHistoryOrgPanel_currentIndex2").attr("value");

	href = $("#hreff2" + currentIndex).attr("type");

	$("#hreff2" + currentIndex).attr("href", href + name);
	var href2 = $("#hreff2" + currentIndex).attr("href");
    
	$("#link2" + currentIndex).click();

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
						<input id="seach_YEAR" type="text" name="seach_YEAR"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy',lang:'en'})"
							value="${DDATE}" />
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="manager"
							id="monthResignCountInfoList_seachDept1" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="manager"
							id="monthResignCountInfoList_seachDept1" selected="${DEPTNO}" />
					</td>
				</tr>
				<tr>
					<td>
						<!-- 员工类型--><spring:message code="ess.infoApply.employee_type" />
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
								<a onclick="javascript:searthURL()" style="cursor: pointer;"><span><spring:message
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
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex2"
			value="${currentIndex}">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>

					<li>
						<a id="hreff21"
							href="/ess/viewDept/monthResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							type="/ess/viewDept/monthResignCountInfoSonList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1"
							class="j-ajax"><span id="link21"><!--月别退职现况 --><spring:message code="ess.infoApply.monthly_retirement_status" /></span> </a>
					</li>


				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height: 565px;">
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
</div>
