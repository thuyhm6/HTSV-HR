<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <html>
 <title></title>
 <head>   
<script type="text/javascript">
function f_viewarprogress_update(index,att_mo_flag,att_mo_lock_flag,stat_no,ar_dept_no){
	var params = [];

	var AR_MONTH = $("#seach_year_ar0208").val() + $("#seach_month_ar0208").val();

	params.push({name: 'arMonth',value: AR_MONTH},
			{name: 'STAT_NO',value: stat_no},
			{name: 'AR_DEPT_NO',value: ar_dept_no});

	var arMoLockFlag = att_mo_lock_flag == 1 ? 0: 1;

	params.push({
		name: 'ATT_MO_LOCK_FLAG',
		value: arMoLockFlag
	});

	if (arMoLockFlag == 1) {
		params.push({
			name: 'ATT_MO_FLAG',
			value: 1
		});

	}

	$.ajax({
	  url: '/ar/attendanceMintenance/updateArProgressInfo',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//保存成功!
			alert("<spring:message code='ar.alert.message.addempshift.success'/>");
			//页面重载
			navTabSearch(document.id1);
		}else{
			//保存失败！
			alert("<spring:message code='alert.message.add_fail'/>");
		}
	  }
	});
	
}

function f_viewarprogress_update2(index,att_mo_flag,att_mo_lock_flag,stat_no,ar_dept_no){
	var params = [];

	var AR_MONTH = $("#seach_year_ar0208").val() + $("#seach_month_ar0208").val();

	params.push({name: 'arMonth',value: AR_MONTH},
			{name: 'STAT_NO',value: stat_no},
			{name: 'AR_DEPT_NO',value: ar_dept_no} );

	var arMoLockFlag = att_mo_lock_flag;
	var ATT_MO_FLAG = att_mo_flag;

	if (arMoLockFlag == 1 && ATT_MO_FLAG == 1){
		//月考勤已锁定，请开放月考勤
		alert('<spring:message code="ar.viewarprogress.title.openarlock"/>');
		return;
	}
	ATT_MO_FLAG = att_mo_flag == 1 ? 0: 1;
	params.push({
		name: 'ATT_MO_FLAG',
		value: ATT_MO_FLAG
	});

	$.ajax({
	  url: '/ar/attendanceMintenance/updateArProgressInfo',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//保存成功!
			alert("<spring:message code='ar.alert.message.addempshift.success'/>");
			//页面重载
			navTabSearch(document.id1);
		}else{
			//保存失败！
			alert("<spring:message code='alert.message.add_fail'/>");
		}
	  }
	});
}  
</script>
</head>
<body style="padding:0px">
<div class="pageHeader">
	<form id="id1" name="id1" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArProgress" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 考勤月 --><spring:message code="ar.excelexport.title.armonth"/>:
					</td>
					<td>
						<ait:date yearName="seach_year_ar0208" monthName="seach_month_ar0208" yearSelected="${year_ar0208}" monthSelected="${month_ar0208}"/>
					</td>
					<td>
						<!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/>:
					</td>
					<td>
						<select class="combox" name="seach_STAT_NO">
							<option value="">select</option>
							<c:forEach items="${statnoList}" var="list">
								<option value="${list.STAT_NO}" <c:if test="${list.STAT_NO eq STAT_NO}">selected</c:if>>${list.STAT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<c:if test="${CPNY_ID eq 'TSTO'}">
							<td>
								大区:
							</td>
							<td>
								<select class="combox" name="seach_DEPT_NO">
									<option value="">select</option>
								     <c:forEach items="${deptList}" var="vlist" varStatus="i">
									      <option value="${vlist.DEPTNO}" <c:if test="${vlist.DEPTNO eq DEPT_NO}">selected</c:if>>${vlist.DEPTNAME}</option>
								    </c:forEach>
							   </select>
							
						   </td>
				   </c:if>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code='button.search'/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
				<th width="15%"><!-- 工资月 --><spring:message code="ar.viewarprogress.title.gongziyue"/></th>
				<th width="20%"><!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/></th>
				<c:if test="${CPNY_ID eq 'TSTO' }">
				  <th width="20%">地域区分</th>
				</c:if>
				<!--<th width="25"> 日考勤锁定<spring:message code="ar.viewarprogress.title.rikaoqinsuoding"/></th> -->
				<th width="25%">考勤申请关闭</th>
				<th width="25%">考勤确认</th>
				<!-- <th width="25">工资锁定 <spring:message code="ar.viewarprogress.title.gongzisuoding"/></th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arProgressList}" var="list" varStatus="i">
			
				<tr>
					<td style="text-align:center" >${list.PA_MONTH_STR}</td>
					<td style="text-align:center" >${list.STAT_NAME}</td>
				     <c:if test="${CPNY_ID eq 'TSTO' }">
					   <td style="text-align:center" >${list.AR_DEPT_NAME}</td>
					 </c:if>
					<td style="text-align:center" >
						<img src="/resources/images/${list.ATT_APPLY_LOCK_FLAG}.gif"  style="cursor:hand">
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${list.ATT_MO_LOCK_FLAG}.gif"  style="cursor:hand">
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="viewArProgress" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ar/attendanceMintenance/viewArProgress" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</body>
</html>