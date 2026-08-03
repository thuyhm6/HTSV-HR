<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<head>
</head>

<c:if test="${currentIndex eq '1'}">
<script type="text/javascript">
	function importExcel3(obj){
		var href1 = $("#monthPersonCISListExport",navTab.getCurrentPanel()).attr("action");
		var name = "&";
		var strFlag = $(obj).attr("strFlag");
		var year = $("#seach_YEAR",navTab.getCurrentPanel()).attr("value");
		var yearFlag = '';
		if (year < '2014/06') {
			yearFlag = 'flag';
		}
		if(strFlag=="delete"){
			name += "seach_YEAR=" + $(obj).attr("endDate");
		}else{
			name += "seach_YEAR=" + year;
		}
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
		var scount = $("#STATUS_CODE_COUNT",navTab.getCurrentPanel()).val();
		for ( var i = 1; i < scount; i++) {
			if ($("#STATUS_CODE" + i,navTab.getCurrentPanel()).prop("checked")) {
				var STATUS_CODE = $("#STATUS_CODE" + i,navTab.getCurrentPanel()).attr("value");
				name += "&seach_STATUS_CODE=" + STATUS_CODE;
			}
		}
		var index = $(obj).attr("index");
		var dept = $(obj).attr("deptno");
		name += "&INDEX=" + index;
		name += "&seach_DEPTNO=" + dept;
		if(index == '3'){
			var maxAge = $(obj).attr("maxAge");
			var minAge = $(obj).attr("minAge");
			name += "&MINAGE=" + minAge;
			name += "&MAXAGE=" + maxAge;
		}else if(index == '2'){
			var edu = $(obj).attr("edu");
			var sex = $(obj).attr("sex");
			if(edu==''){
				edu = 0;
			}
			if(sex==''){
				sex = 0;
			}
			var resignReson = $(obj).attr("resignReson");
			name += "&RESIGNRESON=" + resignReson;
			name += "&EDU=" + edu;
			name += "&SEX=" + sex;
			name += "&strFlag=add";
		}else if(index == '0'){
			var grade = $(obj).attr("grade");
			name += "&GRADE=" + grade;
			name += "&strFlag=" + strFlag;
			var fromDate =  $(obj).attr("fromDate");
			var endDate = $(obj).attr("endDate");
			name += "&FROM_DATE=" + fromDate;
			name += "&TO_DATE=" + endDate;
		}else if(index == '1'){
			var MONTH = $(obj).attr("month");
			var type = $(obj).attr("ruzhili");
			name += "&MONTH=" + MONTH;
			name += "&type=" + type;
		}else if(index == '4'){
			var emptype = $(obj).attr("emptype");
			name += "&empTypeForTable=" + emptype;
		}else if(index == '5'){
			var maxWorkAge = $(obj).attr("maxWorkAge");
			var minWorkAge = $(obj).attr("minWorkAge");
			name += "&MINWORKAGE=" + minWorkAge;
			name += "&MAXWORKAGE=" + maxWorkAge;
		}
	href1 = $("#monthPersonCISListExport").attr("action") + "?currentIndex=1" + name;
	$("#monthPersonCISListExport").attr("action",href1);
	$("#monthPersonCISListExport").submit();
	}
</script>
	<div class="pageHeader">
		<div class="searchBar">
			<form class="j-ajax"
				action="/pa/workManagement/monthPersonCISListExport"
				method="post" id = "monthPersonCISListExport">
				<input type="hidden" name="currentIndex" value="${currentIndex}">
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div>
									<button id="importExcel" deptno="${DEPTNO}" index="${INDEX}" onclick="javascript:importExcel3(this);"
										grade="${GRADE}" emptype="${empTypeForTable}" resignReson="${RESIGNRESON }" edu="${EDU}" sex="${SEX}" maxAge="${MAXAGE}" minAge="${MINAGE}" strFlag="${strFlag}" fromDate="${FROM_DATE}" endDate="${END_DATE}" 
										 maxWorkAge="${MAXWORKAGE}" minWorkAge="${MINWORKAGE}" month="${MONTH }" ruzhili="${LIZHI }">
										<span><!--导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span>
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</c:if>
<div class="pageContent">
	<c:if test="${currentIndex eq '0'}">


		<script type="text/javascript">

// 初始调用
$(document).ready(function() {
	$("#monthPersonCountInfo0", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : true,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 300,
		"scrollX" : true,
		"orderClasses" : false,
		"oLanguage" : {
			//正在加载中......
	    	 "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
		}
	//多语言配置
			});
});
</script>
		<table class="orderList" id="monthPersonCountInfo0">
			<thead>
				<tr>

					<th>
						No.
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--职群--><spring:message code="ess.empInfo.zhiqun" />
					</th>
					<th>
						<!--职级--><spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!--职责--><spring:message code="ess.trans.title.dutyName" />
					</th>
					<th>
						<!--主要业务--><spring:message code="org.title.MAIN_BUSINESS" />
					</th>
						<c:if test="${strFlag eq 'add'}">
						<th>
							<!--入社日期--><spring:message code="ess.empInfo.date_of_agency" />
						</th>
						</c:if>
						<c:if test="${strFlag eq 'delete'}">
						<th>
							<!--退社日期--><spring:message code="pa.monthPersonCountInfoSonList.TUISHERIQI.b" />
						</th>
						</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monthPersonCountInfoSonList}" var="item"
					varStatus="i">

					<tr target="" rel=""
						onclick="if($(this).attr('class').indexOf('selected')!=-1){$(this).removeClass('selected');$('#isSelectRow_${i.count}', navTab.getCurrentPanel()).text('');}else{$(this).addClass('selected');$('#isSelectRow_${i.count}', navTab.getCurrentPanel()).text('!');}">
						<td style="text-align: center">
							${i.count}
							<span id="isSelectRow_${i.count}"></span>
						</td>
						<td style="text-align: center">
							${item.EMPID}
						</td>
						<td style="text-align: center">
							${item.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${item.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${item.POST_FAMILY}
						</td>
						<td style="text-align: center">
							${item.POST_GRADE}
						</td>
						<td style="text-align: center">
							${item.POSITION_NAME}
						</td>
						<td style="text-align: center">
							${item.MAIN_BUSINESS}
						</td>
						<c:if test="${strFlag eq 'add'}">
							<td style="text-align: center">
								${item.DATE_STARTED}
							</td>
						</c:if>
						<c:if test="${strFlag eq 'delete'}">
							<td style="text-align: center">
								${item.DATE_LEFT}
							</td>
						</c:if>
					</tr>
				</c:forEach>
				<c:if test="${totalCount == 0 }">
					<tr>
						<td style="text-align: left;" colspan="7">
							<!--没有查找的数据--><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" />
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

	</c:if>


	<c:if test="${currentIndex eq '1'}">


		<script type="text/javascript">

// 初始调用
$(document).ready(function() {

});
</script>

		<table class="table" width="100%" layoutH="50">
			<thead>
				<tr>


					<th>
						No.
					</th>
					<th>
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--职级--><spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!--主要业务--><spring:message code="org.title.MAIN_BUSINESS" />
					</th>
					<th>
						<!--员工类型--><spring:message code="org.title.EMP_TYPE" />
					</th>
					<th>
						<!--状态--><spring:message code="org.title.status" />
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monthPersonCountInfoSonList}" var="item"
					varStatus="i">

					<tr>
						<td style="text-align: center">
							${i.count}
							<span id="isSelectRow_${i.count}"></span>
						</td>
						<td style="text-align: center">
							${item.EMPID}
						</td>
						<td style="text-align: center">
							${item.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${item.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${item.POST_GRADE}
						</td>
						<td style="text-align: center">
							${item.MAIN_BUSINESS}
						</td>
						<td style="text-align: center">
							${item.EMP_TYPE}
						</td>
						<td style="text-align: center">
							${item.EMP_OFFICE}
						</td>
					</tr>
				</c:forEach>
				<c:if test="${totalCount == 0 }">
					<tr>
						<td style="text-align: left;" colspan="9">
							<!--没有查找的数据--><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" />
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

	</c:if>

	<c:if test="${currentIndex eq '2'}">


		<script type="text/javascript">

// 初始调用
$(document).ready(function() {

});
</script>

		<table class="table" width="100%" layoutH="50">
			<thead>
				<tr>


					<th>
						No.
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--等级--><spring:message code="ess.infoApply.Grade" />
					</th>
					<th>
						<!--主要业务--><spring:message code="org.title.MAIN_BUSINESS" />
					</th>
					<th>
						<!--入社日期--><spring:message code="ess.empInfo.date_of_agency" />
					</th>
					<th>
						<!--员工类型--><spring:message code="org.title.EMP_TYPE" />
					</th>
					<th>
						<!--状态--><spring:message code="org.title.status" />
					</th>
					<th>
						<!--前月金额--><spring:message code="pa.monthPersonCountInfoSonList.QIANYUJINE.b" />
					</th>
					<th>
						<!--当月金额--><spring:message code="pa.monthPersonCountInfoSonList.DANGYUEJINE.b" />
					</th>
					<th>
						<!--差异--><spring:message code="ess.infoApply.difference" />
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monthPersonCountInfoSonList}" var="item"
					varStatus="i">

					<tr>
						<td style="text-align: center">
							${i.count}
							<span id="isSelectRow_${i.count}"></span>
						</td>
						<td style="text-align: center">
							${item.EMPID}
						</td>
						<td style="text-align: center">
							${item.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${item.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${item.POST_GRADE}
						</td>
						<td style="text-align: center">
							${item.MAIN_BUSINESS}
						</td>
						<td style="text-align: center">
							${item.DATE_STARTED}
						</td>
						<td style="text-align: center">
							${item.EMP_TYPE}
						</td>
						<td style="text-align: center">
							${item.EMP_OFFICE}
						</td>
					</tr>
				</c:forEach>
				<c:if test="${totalCount == 0 }">
					<tr>
						<td style="text-align: left;" colspan="9">
							<!--没有查找的数据--><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" />
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

	</c:if>
</div>

