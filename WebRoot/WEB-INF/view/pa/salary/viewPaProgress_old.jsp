<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <html>
 <title></title>
 <head>   
<script type="text/javascript">
function f_lockPaProgressUpdate(index,pa_lock_flag,stat_no){
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
	PA_LOCK_FLAG = pa_lock_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_PA_LOCK_FLAG',
		value: PA_LOCK_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressInfo',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.searchPaProgressForm);
		}else{
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
}
function f_openPaProgressUpdate(index,pa_open_flag,stat_no){
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
	PA_OPEN_FLAG = pa_open_flag == 1 ? 0: 1;
	params.push({
		name: 'seach_PA_OPEN_FLAG',
		value: PA_OPEN_FLAG
	});
	$.ajax({
	  url: '/pa/salary/updatePaProgressInfo',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.searchPaProgressForm);
		}else{
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
} 

//通过选择的保险月 查询出发放日期的list
function getSalaryProvideDatePa(){
	var paMonth = $("#paYear",navTab.getCurrentPanel()).val() + $("#paMonth",navTab.getCurrentPanel()).val();
	var sel = $("#GIVE_DATE",navTab.getCurrentPanel());//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/salary/getSalaryProvideDatePa?",
		 data: 'paMonth=' + paMonth,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
</script>
</head>
<body style="padding:0px">
	<div class="pageHeader" >
		<form id="searchPaProgressForm" name="searchPaProgressForm" onsubmit="return navTabSearch(this);" 
			action="/pa/salary/viewPaProgress" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->：
							<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}" onChange=""/>
						</td>
						<td>
							<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
									<select id="GIVE_DATE" name="GIVE_DATE" class="select" >
										<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
									</select>
						</td>
						<td><!-- 区间 -->
							<spring:message code="ar.viewcycleparameter.title.qujian"/>:
						</td>
						<td>
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
		<table class="table" width="100%" layoutH="142">
			<thead>
				<tr>
					<th width="10%">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
					</th>
					<th width="18%"><!-- 区间 -->
						<spring:message code="ar.viewcycleparameter.title.qujian"/></th>
					<th width="18%"><!-- 日考勤锁定 -->
						<spring:message code="ar.viewarprogress.title.rikaoqinsuoding"/>
					</th>
					<th width="18%"><!-- 月考勤锁定 -->
						<spring:message code="ar.viewarprogress.title.yuekaoqinsuoding"/>
					</th>
					<th width="18%"><!-- 工资锁定 -->
						<spring:message code="ar.viewarprogress.title.gongzisuoding"/>
					</th>
					<th width="18%"><!-- 工资开放 -->
						<spring:message code="pa.salary.title.salaryOpen"/>
					</th>
				</tr>
			</thead>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PA_MONTH_STR}">
					<td>
						${item.PA_MONTH_STR}
					</td>
					<td>${item.STAT_NAME}</td>
					<td>
						<img src="/resources/images/${item.ATT_MO_FLAG}.gif" style="cursor: hand" />
					</td>
					<td>
						<img src="/resources/images/${item.ATT_MO_LOCK_FLAG}.gif" style="cursor: hand" />
					</td>
					<td>
						 <img src="/resources/images/${item.PA_LOCK_FLAG}.gif" onclick="f_lockPaProgressUpdate(${i.index},${item.PA_LOCK_FLAG},'${item.STAT_NO}')"
						 style="cursor: hand" /></a>
					</td>
					<td>
						 <img src="/resources/images/${item.PA_OPEN_FLAG}.gif" onclick="f_openPaProgressUpdate(${i.index},${item.PA_OPEN_FLAG},'${item.STAT_NO}')"
						 style="cursor: hand" /></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:set value="/pa/salary/viewPaProgress" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</body>
</html>