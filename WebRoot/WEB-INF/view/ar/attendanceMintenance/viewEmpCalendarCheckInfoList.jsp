<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
     $("#viewArShiftGroupForm_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewArShiftGroupForm",navTab.getCurrentPanel()).submit();
	   });
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 350,
            "scrollX": false,
            "orderClasses": false,
            "oLanguage": {
                //正在加载中......
                "sProcessing": "<spring:message code='ess.message.loading'/>",
                //查询不到相关数据！
                "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",
                //表中无数据存在！
                "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",
                //快速筛选
                "sSearch": "<spring:message code='ess.message.rapid_screening'/>"
            } //多语言配置
		});
});
	function searchPop_ess3404(flag) {
		var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
				.val()));
		var refreshUrl = '/ess/deptEmpAtt/viewArShiftGroupList?firstView=1';
		var refreshMenuCode = 'ess3404';
		//班组变更
		var refreshMenuName = encodeURI(encodeURI('<spring:message code="ar.viewArShiftGroupList.BANZUBIANGENG.b"/>'));
		//$('#searchPop',navTab.getCurrent())
		$("#searchPop_ess3404", navTab.getCurrentPanel())
				.attr(
						'href',
						'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
								+ name
								+ '&refreshUrl='
								+ refreshUrl
								+ '&refreshMenuCode='
								+ refreshMenuCode
								+ '&refreshMenuName=' + refreshMenuName);
		if (flag == 'onkeyup')
			$("#searchPop_ess3404", navTab.getCurrentPanel()).click();
	}
	function downloadExl(url){
		$('#viewArShiftGroupForm').attr("action",url) ;
		$('#viewArShiftGroupForm').attr("onsubmit",'') ;
		$('#viewArShiftGroupForm').submit() ;
		$('#viewArShiftGroupForm').attr("action",'/ess/deptEmpAtt/viewArShiftGroupList') ;
		$('#viewArShiftGroupForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
</script>
<div class="pageHeader">
	<form id="viewArShiftGroupForm" onsubmit="return navTabSearch(this);"
		action="/ar/attendanceMintenance/viewEmpCalendarInfoList" method="post">
		<input type="hidden" value="ar" name="limit" />
		<input type="hidden" value="1" name="firstView" />
	<div class="searchBar">
		<table class="searchContent">
			<%-- <tr>
				<td>
					<!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
				</td>
				<td><input type="text" name="seach_KEY" id="seach_KEY"
					value="${KEY}"
					onkeydown="javascript:if(event.keyCode == 13)searchPop_ess3404('onkeyup');" />
				</td>
				<td>
					<!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
				</td>
				<td>
					<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
				</td>
			</tr> --%>
			<tr>
				<%-- <td><!--开始日期--><spring:message code="public.title.startDate" /></td>
				<td><input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}" /></td>
				<td><!--结束日期--><spring:message code="public.title.endDate" /></td>
				<td><input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}" /></td>
				<td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				<td>
					<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
						<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
						<c:forEach items="${shiftList}" var="result">
							<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
						</c:forEach>
					</select>
				</td> --%>
				<td><spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->: ${empCalendarInfoListCnt}</td>
				<td><spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->: ${empCalendarListFailCnt}</td> 
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent">
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(arShiftGroupList)}</div>
	<ul class="toolBar">
	<c:if test="${empCalendarListFailCnt > 0 }"> 
		<a class="buttonActive" title="<spring:message code="ar.alert.message.excelimport.rulewrong"/>"><span>
		<spring:message code="ar.alert.message.excelimport.rulewrong"/><!--提交--></span></a>
	
	</c:if>
	<c:if test="${empCalendarListFailCnt <= 0 }">
	<li>
		<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/ar/attendanceMintenance/submitImportExcelCalendarData" title="<spring:message code="org.title.IS_SUBMIT"/>"><span>
		<spring:message code="public.title.submit"/><!--提交--></span></a>
	</li>
	</c:if>
	<%-- <li>
		<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			 title="<spring:message code="ess.empInfo.cancel"/>"><span>	<spring:message code="ess.empInfo.cancel"/><!--Cancel--></span></a>
	</li> --%>
	 </ul>
</div>
	<form name="shiftGroupDataForm" id="shiftGroupDataForm" method="post"
		action="/ess/deptEmpAtt/addArShiftGroupInfo">
		<table class="list">  
			<thead>
				<tr>
					<th width="2%">NO.</th>
					<th width="6%"><!--工号--><spring:message code="public.title.empId" /></th>
					<th width="12%"><!--姓名--><spring:message code="public.title.name" /></th>
					<th width="12%"><!--部门--><spring:message code="org.title.dept" /></th>
					<th width="10%"><!--考勤日期--><spring:message code="ess.infoApply.attendance_date" /></th>
					<th width="10%"><!--日期性质--><spring:message code="ar.viewCompanyCalendar.title.riqixingzhi" /></th>
					<th width="10%"><!--班次--><spring:message code="ar.viewshift.title.bancileixing" /></th>
					<th width="10%"><!--Fail--><spring:message code="hrm.approve.ERROR_CONTENT" /></th>
					<th width="10%"><!--变更者--><spring:message code="org.title.UPDATED_IP" /></th>
					<th width="10%"><!--变更时间--><spring:message code="org.title.UPDATE_DATE" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${empCalendarInfoList}" var="itemData" varStatus="status">
					<tr onclick="batchArShiftGroupChangeColor(this,${status.count});">
						<td  class='td_center'>${status.count}</td>
						<td  class='td_center'>${itemData.EMPID}</td>
						<td  class='td_center'>${itemData.LOCAL_NAME}</td>
						<td  class='td_center'>${itemData.DEPTNAME}</td>
						<td  class='td_center'>${itemData.AR_DATE_STR}</td>
						<td  class='td_center'>${itemData.DATE_TYPE}</td>
						<td  class='td_center'>${itemData.SHIFT_NAME}</td>
						<td  class='td_center'>${itemData.UPLOAD_ERROR_MSG}</td>
						<td  class='td_center'>${itemData.CREATED_BY}</td>
						<td  class='td_center'>${itemData.CREATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>