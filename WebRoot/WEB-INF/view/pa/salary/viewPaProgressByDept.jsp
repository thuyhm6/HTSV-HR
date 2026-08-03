<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <html>
 <title></title>
 <head>   
<script type="text/javascript">
<%--
function f_lockAttProgressByDeptUpdate(index,att_mo_flag,stat_no,dept_distinguish_no){
	var params = [];
	var PA_MONTH = $("#seach_paYear").val() + $("#seach_paMonth").val();
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: PA_MONTH
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	ATT_MO_FLAG = att_mo_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_ATT_MO_FLAG',
		value: ATT_MO_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else{
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
}

function f_lockAttMonthProgressByDeptUpdate(index,att_mo_lock_flag,stat_no,dept_distinguish_no){
	var params = [];
	var PA_MONTH = $("#seach_paYear").val() + $("#seach_paMonth").val();
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: PA_MONTH
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	ATT_MO_LOCK_FLAG = att_mo_lock_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_ATT_MO_LOCK_FLAG',
		value: ATT_MO_LOCK_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else{
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
}

function f_openPaProgressByDeptUpdate(index,pa_open_flag,stat_no,dept_distinguish_no){
	var params = [];
	var PA_MONTH = $("#seach_paYear").val() + $("#seach_paMonth").val();
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: PA_MONTH
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	PA_OPEN_FLAG = pa_open_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_PA_OPEN_FLAG',
		value: PA_OPEN_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else{
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
} 
--%>
function f_lockPaProgressByDeptUpdate(index,pa_lock_flag,stat_no,dept_distinguish_no){
	var params = [];
	var PA_MONTH = $("#seach_paYear").val() + $("#seach_paMonth").val();
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: PA_MONTH
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	PA_LOCK_FLAG = pa_lock_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_PA_LOCK_FLAG',
		value: PA_LOCK_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//门店薪资锁定成功！
			alert('<spring:message code="pa.alert.message.mendianlockissuccessed"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else{
			//门店薪资锁定失败！
			alert('<spring:message code="pa.alert.message.mendianlockisfailed"/>');
		}
	  }
	});
}

function f_deletePaProgressByDept(index,pa_month_str,stat_no,dept_distinguish_no){
	var params = [];
	
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: pa_month_str
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	
	$.ajax({
	  url: '/pa/salary/deletePaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//删除成功！
			alert('<spring:message code="pa.alert.message.deletethelockinfosuccessed"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else{
			//删除失败！
			alert('<spring:message code="pa.alert.message.deletethelockinfofailed"/>');
		}
	  }
	});
}

function f_copyPaProgressByDept(index,pa_month_str,stat_no,dept_distinguish_no){
	var params = [];
	
	params.push({
		name: 'seach_PA_MONTH_STR',
		value: pa_month_str
	});
	params.push({
		name: 'seach_STAT_NO',
		value: stat_no
	});
	params.push({
		name: 'seach_DEPT_DISTINGUISH_NO',
		value: dept_distinguish_no
	});
	
	$.ajax({
	  url: '/pa/salary/copyToNextMonthPaProgressByDept',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//复制至下月成功！
			alert('<spring:message code="pa.alert.message.copythelockinfotonextmonthsuccessed"/>');
			//页面重载
			navTabSearch(document.searchPaProgressByDeptForm);
		}else if(responseText == "E"){
			//下个月此门店的锁定信息已存在，不允许再次复制！
			alert('<spring:message code="pa.alert.message.thenextmonthlockinfoexist"/>');
		}else{
			//复制至下月失败！
			alert('<spring:message code="pa.alert.message.copythelockinfotonextmonthfailed"/>');
		}
	  }
	});
}
</script>
</head>
<body style="padding:0px">
	<div class="pageHeader" >
		<form id="searchPaProgressByDeptForm" name="searchPaProgressByDeptForm" onsubmit="return navTabSearch(this);" 
			action="/pa/salary/viewPaProgressByDept" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->：
							<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"/>
						</td>
						<td>
							所属区域：
							<select name="seach_DEPT_TYPE_NO">
								<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
								<c:forEach items="${deptTypeList}" var="list">
									<option value="${list.DEPT_TYPE_NO}" <c:if test="${list.DEPT_TYPE_NO eq DEPT_TYPE_NO}">selected</c:if>>${list.DEPT_TYPE}</option>
								</c:forEach>
							</select>
						</td>
						<td><!--部门区分-->
							<spring:message code="org.orgManage.title.deptDistinct"/>：
							<select name="seach_DEPT_DISTINGUISH_NO">
								<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
								<c:forEach items="${deptDistinguishList}" var="list">
									<option value="${list.DEPT_DISTINGUISH_NO}" <c:if test="${list.DEPT_DISTINGUISH_NO eq DEPT_DISTINGUISH_NO}">selected</c:if>>${list.DEPT_DISTINGUISH_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td><!-- 区间 -->
							<spring:message code="ar.viewcycleparameter.title.qujian"/>：
							<select name="seach_STAT_NO">
								<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
								<c:forEach items="${statnoList}" var="list">
									<option value="${list.STAT_NO}" <c:if test="${list.STAT_NO eq STAT_NO}">selected</c:if>>${list.STAT_NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search"/><!--检索-->
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<table class="table" width="100%" layoutH="148">
			<thead>
				<tr>
					<th width="10%">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
					</th>
					<th width="15%"><!-- 区间 -->
						<spring:message code="ar.viewcycleparameter.title.qujian"/>
					</th>
					<th width="15%"><!--部门区分-->
							<spring:message code="org.orgManage.title.deptDistinct"/>
					</th><!--
					<th width="15%"> 日考勤锁定 
						<spring:message code="ar.viewarprogress.title.rikaoqinsuoding"/>
					</th>--><!--
					<th width="15%"> 月考勤锁定 
						<spring:message code="ar.viewarprogress.title.yuekaoqinsuoding"/>
					</th>-->
					<th width="15%"><!-- 工资锁定 -->
						<spring:message code="ar.viewarprogress.title.gongzisuoding"/>
					</th><!--
					<th width="15%"> 工资开放 
						<spring:message code="pa.salary.title.salaryOpen"/>
					</th>-->
					<th width="15%"><!--操作-->
						<spring:message code="pa.title.message.palockoperate"/>
					</th>
					<th width="15%"><!--复制至下月-->
						<spring:message code="pa.title.message.copylockinfotonextmonthope"/>
					</th>
				</tr>
			</thead>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PA_MONTH_STR}">
					<td width="10%">${item.PA_MONTH_STR}</td>
					<td width="15%">${item.STAT_NAME}</td>
					<td width="15%">${item.DEPT_DISTINGUISH_NAME}</td>
					<%-- 
					<td width="15%">
						<img src="/resources/images/${item.ATT_MO_FLAG}.gif"  onclick="f_lockAttProgressByDeptUpdate(${i.index},${item.ATT_MO_FLAG},'${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
						 style="cursor: hand" />
					</td>
					<td width="15%">
						<img src="/resources/images/${item.ATT_MO_LOCK_FLAG}.gif"  onclick="f_lockAttMonthProgressByDeptUpdate(${i.index},${item.ATT_MO_LOCK_FLAG},'${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
						 style="cursor: hand" />
					</td>--%>
					<td width="15%">
						 <img src="/resources/images/${item.PA_LOCK_FLAG}.gif" onclick="f_lockPaProgressByDeptUpdate(${i.index},${item.PA_LOCK_FLAG},'${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
						 style="cursor: hand" />
					</td><%--
					<td width="15%">
						 <img src="/resources/images/${item.PA_OPEN_FLAG}.gif" onclick="f_openPaProgressByDeptUpdate(${i.index},${item.PA_OPEN_FLAG},'${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
						 style="cursor: hand" />
					</td>--%>
					<td width="15%">
						<c:if test="${item.STAT_NO eq '21841'}">
							 <img src="/resources/images/button/Delete.gif" onclick="f_deletePaProgressByDept(${i.index},'${item.PA_MONTH_STR}','${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
							 style="cursor: hand" />
						 </c:if>
						 <c:if test="${item.STAT_NO ne '21841'}">
						 	&nbsp;&nbsp;
						 </c:if>
					</td>
					<td width="15%">
						 <img src="/resources/images/button/Add_little.gif" onclick="f_copyPaProgressByDept(${i.index},${item.PA_MONTH_STR},'${item.STAT_NO}','${item.DEPT_DISTINGUISH_NO}')"
						 style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:set value="/pa/salary/viewPaProgressByDept" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</body>
</html>