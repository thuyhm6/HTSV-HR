<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function makePortalSap(navTabId) {
	var paMonth = $("#sapYear",navTab.getCurrentPanel()).val() + $("#sapMonth",navTab.getCurrentPanel()).val();
	var giveDate = $("#SAP_GIVE_DATE",navTab.getCurrentPanel()).val();
	if(giveDate == ''){
		alert('<spring:message code="pa.salary.title.salaryProvideDateIsMust"/>');
	}else{
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.shengcheng'/>"+"[" + paMonth + "]"+"<spring:message code='pa.viewsapcalculate.title.monthsap'/>"+"?",
				{
					okCall : function() {
							$.ajax( {
							type : 'post',
							cache : false,
							url : '/pa/salary/addPortalInfo?PA_MONTH='+paMonth+'&SAP_GIVE_DATE='+giveDate,
							success : function(responseText) {
								if (responseText == "Y"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewPortal");
									var params = $("#viewPortal").serializeArray();
									navTab.reload($form.attr('action')+'?pageNum=1', {data: params, navTabId:navTabId});
								}else if(responseText == "N"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
								}else{
									alert('<spring:message code="alert.message.pa.salary.title.salaryMonthHasNoData"/>');
								}
							}
						});
					}
				});
		}
}
</script>
<div class="pageHeader" >
	<form id="viewPortal" onsubmit="return navTabSearch(this);" action="/pa/salary/viewPortalSap" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td> 
					<spring:message code="pa.salary.title.date"/><!--日期-->：
				</td>
				<td align="center" width="32%" colspan="2">
					<input type="text" id="seach_FIRST_DATE" name="seach_FIRST_DATE" class="date" readonly="true" 
						<c:if test="${FIRST_DATE eq '' || FIRST_DATE == null}">value="${dateParam.FIRST_DATE }"</c:if> 
						<c:if test="${FIRST_DATE ne '' }">value="${FIRST_DATE }"</c:if> />
					<a class="inputDateButton" href="javascript:;">
						<spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:-->
					</a>
				</td>
				<td><spring:message code="pa.insurance.title.status"/><!--状态-->
				</td>
				<td>
					<select id="seach_SYNCHRON_STATUS" name="seach_SYNCHRON_STATUS" >
						<option value="">
							<spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:-->
						</option>
						<option value="1" <c:if test="${SYNCHRON_STATUS eq '1' }">selected</c:if>> 
							<spring:message code="pa.insurance.title.success"/><!--成功-->
						</option>
						<option value="2" <c:if test="${SYNCHRON_STATUS eq '2' }">selected</c:if>> 
							<spring:message code="pa.insurance.title.fail"/><!--失败-->
						</option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul><li><div class="buttonActive"><div class="buttonContent"><button type="submit">
		 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div></li></ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
			<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
			<ait:date yearName="sapYear" monthName="sapMonth" />
			</li>
			<li>
			<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 --></li>
			<li>
			<input id="SAP_GIVE_DATE" name="SAP_GIVE_DATE" class="required date">
			</li>
			<li>
				<a class="l-button"
					style="width: 60px; float: left; margin-left: 10px;"
					onclick="makePortalSap()">
					<spring:message code="pa.salary.title.generateSAP"/><!--生成SAP-->
				</a>
			</li> 
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="5%" align="center"><spring:message code="pa.insurance.title.orderNo"/><!--序号-->
				</th>
				<th width="25%" align="center"><%--PORTAL 名字--%>
					<spring:message code="pa.salary.title.portalName"/><!--名字-->
				</th>
				<th width="10%" align="center">   
					<spring:message code="pa.salary.title.genarateType"/><!--生成类型-->
				</th>
				<th width="13%" align="center"> 
					<spring:message code="pa.salary.title.createTime"/><!--操作时间-->
				</th>
				<th width="10%" align="center"> 
					<spring:message code="pa.salary.title.createPerson"/><!--操作者-->
				</th>
				<th width="10" align="center"><spring:message code="pa.insurance.title.status"/><!--状态-->
				</th>
				<th width="25%" align="center"> 
					<spring:message code="pa.salary.title.fullInfo"/><!--详细信息-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${portalLogList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="5%" align="center">${i.index+1}</td>
					<td width="25%" align="center">${item.PORTAL_NAME }</td>
					<td width="10%" align="center">
						<c:if test="${item.MAKE_TYPE eq '1' }"> 
							<spring:message code="pa.salary.title.manual"/><!--手动-->
						</c:if>
						<c:if test="${item.MAKE_TYPE eq '0' }"> 
							<spring:message code="pa.salary.title.automatic"/><!--自动-->
						</c:if>
					</td>
					<td width="13%" align="center">${item.CREATED_DATE }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="10%" align="center">
						<c:if test="${item.MAKE_RESULT eq '1' }"> 
							<spring:message code="pa.salary.title.success"/><!--成功-->
						</c:if>
						<c:if test="${item.MAKE_RESULT eq '2' }"> 
							<spring:message code="pa.salary.title.fail"/><!--失败-->
						</c:if>
					</td>
					<td width="25%" align="center">${item.REMARK_MESSAGE }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewPortalSap" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>