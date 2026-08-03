<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
// 初始调用
$(document).ready(function() {
	//布局

		$('#tree_changeOrzTemp1').treeTable( {
			expandLevel : 3
		});

	});
function saveDept() {

	var dept_all_two = [];

	$('input[name="dept_All_No"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				var deptno = $(this).attr("value");

				var deptno1 = $("#" + deptno + "1").attr("value");
				var deptno2 = $("#" + deptno + "2").attr("value");
				var deptno3 = $("#" + deptno + "3").attr("value");
				dept_all_two.push(deptno + "-" + deptno1 + "-" + deptno2 + "-"
						+ deptno3);

			});

	$("#from_Value").attr("value", dept_all_two);

	saveValue();
}

function saveValue() {

	$("#changOrzTemp").attr("action", "/hrm/report/saveOrzTemp");

	$("#changOrzTemp").attr("onsubmit",
			"return navTabSearch_changOrzTemp(this);");
	$("#changOrzTemp").submit();

	$("#changOrzTemp").attr("action", "/hrm/report/changeOrzTemp");

	$("#changOrzTemp").attr("onsubmit", "return navTabSearch(this);");
}

function navTabSearch_changOrzTemp(form) {

	var $form = $("#changOrzTemp");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;

}

function  changeColor(dept){
 
	 $(".cleanColor").css("background-color",""); 
	 $("#"+dept).css("background-color","#A8D1E0"); 
	
}
</script>

<style type="text/css">
.cleanColor:hover {
	background-color: #A8D1E0
}
</style>
<div class="pageHeader">
	<form id="changOrzTemp" onsubmit="return navTabSearch(this);"
		action="/hrm/report/changeOrzTemp" method="post">
		<div class="searchBar">
			<table class="searchContent">

				<tr>
					<td>
						日期
					</td>
					<td>
						<input type="text" id="seach_DATE" name="seach_DATE" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${DATE}" />

					</td>
					<td>
						<input type="hidden" value="" name="from_Value" id="from_Value">
					</td>
				</tr>
			</table>

			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div align="center">

								<button>
									<span><spring:message code="public.title.search" /> </span>
								</button>

							</div>
						</div>
					</li>

					<li>

						<div align="center">

							<a class="button" onclick="saveDept();"><span>保存</span> </a>

						</div>

					</li>
				</ul>
			</div>
		</div>


	</form>
</div>

<div class="pageContent">


	<table class="user_table" width="75%">
		<thead>

			<td width="25%" class="td_title" style="text-align: center;">
				部门
			</td>
			<td width="25%" class="td_title" style="text-align: center;">
				部门区分
			</td>

			<td width="25%" class="td_title" style="text-align: center;">
				津贴区分
			</td>
			<td width="25%" class="td_title" style="text-align: center;">
				直间接区分
			</td>



		</thead>
		<tbody>
			<tr>
				<td colspan="4" width="100%">
					<table id="tree_changeOrzTemp1" width="100%">



						<c:forEach items="${allDeptList}" var="item" varStatus="i">


							<tr id="${item.DEPTNO}" width="25%" class="cleanColor"
								<c:if test="${item.DEPTNO ne item.PARENT_DEPT_NO}">pid="${item.PARENT_DEPT_NO}"</c:if>>

								<td width="25%" onclick="changeColor('${item.DEPTNO}')">
									<input type="hidden" name="dept_All_No" value="${item.DEPTNO}">
									<span controller="true"> ${item.ORG_NAME_LOCAL} </span>
								</td>
								<td onclick="changeColor('${item.DEPTNO}')">
									<ait:SelectSyCodeByCpnyID id="${item.DEPTNO}1"
										name="DEPT_DISTINGUISH_STANDARD" parentNo="14014426"
										cnpyID="${defaultCpny}"
										selected="${item.DEPT_DISTINGUISH_STANDARD}" />
								</td>

								<td width="25%" onclick="changeColor('${item.DEPTNO}')">
									<ait:SelectSyCodeByCpnyID id="${item.DEPTNO}2"
										name="DEPT_DISTINGUISH_STANDARD_TWO" parentNo="14014427"
										cnpyID="${defaultCpny}"
										selected="${item.DEPT_DISTINGUISH_STANDARD_TWO}" />
								</td>
								<td width="25%" onclick="changeColor('${item.DEPTNO}')">
									<ait:SelectSyCodeByCpnyID id="${item.DEPTNO}3"
										name="DEPT_DISTINGUISH_STANDARD_THREE" parentNo="14014046"
										cnpyID="${defaultCpny}"
										selected="${item.DEPT_DISTINGUISH_THREE}" />
								</td>

							</tr>
						</c:forEach>


					</table>
				</td>
			</tr>
		</tbody>
	</table>


</div>
