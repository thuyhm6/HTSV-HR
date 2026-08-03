<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function makePortalC01Sap(navTabId) {
	var sendType = $("#seach_HRM_SEND_TYPE").val();
	if(sendType == ''){
		alert('<spring:message code="pa.alert.message.sap.sendTypeIsMust"/>');
	}else{
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.shengcheng'/>"+""+"<spring:message code='pa.viewsapcalculate.title.monthsap'/>"+"?",
				{
					okCall : function() {
							$.ajax( {
							type : 'post',
							cache : false,
							url : '/pa/salary/addPortalInfo?SapType='+sendType,
							success : function(responseText) {
								if (responseText == "Y"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewPortalC01Sap");
									var params = $("#viewPortalC01Sap").serializeArray();
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
function makePortalC01SapPart(navTabId) {
	var sendType = $("#seach_HRM_SEND_TYPE").val();
	if(sendType == ''){
		alert('<spring:message code="pa.alert.message.sap.sendTypeIsMust"/>');
	}else{
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.shengcheng'/>"+""+"<spring:message code='pa.viewsapcalculate.title.monthsap'/>"+"?",
				{
					okCall : function() {
							$.ajax( {
							type : 'post',
							cache : false,//选择性发送
							url : '/pa/salary/addPortalInfo?SEND_TYPE=SEND_PART&SapType='+sendType,
							success : function(responseText) {
								if (responseText == "Y"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewPortalC01Sap");
									var params = $("#viewPortalC01Sap").serializeArray();
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
	<form id="viewPortalC01Sap" onsubmit="return navTabSearch(this);" action="/pa/salary/viewPortalC01Sap" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td style="text-align:center"> 
					<spring:message code="pa.salary.title.date"/><!--日期-->:
				</td>
				<td style="text-align:left">
					<input type="text" id="seach_FIRST_DATE" name="seach_FIRST_DATE" class="date" readonly="true" 
						<c:if test="${FIRST_DATE eq '' || FIRST_DATE == null}">value="${dateParam.FIRST_DATE }"</c:if> 
						<c:if test="${FIRST_DATE ne '' }">value="${FIRST_DATE }"</c:if> />
					<a class="inputDateButton" href="javascript:;">
						<spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:-->
					</a>
				</td>
				<td style="text-align:right">
					<spring:message code="pa.insurance.title.status"/>:<!--状态-->
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
				<td style="text-align: center"><!--发送类型-->
					<spring:message code="pa.title.message.sendType"/>:
				</td>
				<td style="text-align: left">
					<select id="seach_HRM_SEND_TYPE" name="seach_HRM_SEND_TYPE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="SAP_HRM" <c:if test="${HRM_SEND_TYPE eq 'SAP_HRM' }">selected</c:if>><%--SAP人事信息--%>
							<spring:message code="pa.alert.message.sapTypeHrmInof"/>
						</option>
						<option value="SAP_DEPARTMENT" <c:if test="${HRM_SEND_TYPE eq 'SAP_DEPARTMENT' }">selected</c:if>><%--SAP部门信息--%>
							<spring:message code="pa.title.message.sapTypeDepartment"/>
						</option>
						<option value="GMD_HRM" <c:if test="${HRM_SEND_TYPE eq 'GMD_HRM' }">selected</c:if>><%--GMD人事信息--%>
							<spring:message code="pa.title.message.gmdTypeHrmInfo"/>
						</option>
					</select><%--注：发送类型仅在发送接口数据时使用。--%>
					<font size="2" color="red">(<spring:message code="pa.title.message.sendTypeTitleInfo"/>)</font>
				</td>
				<td>&nbsp;</td>
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
			<!--<li>
				<a class="l-button" style="width: 60px; float: left; margin-left: 10px;" onclick="makePortalC01Sap()">
					<spring:message code="pa.salary.title.generateSAP"/>生成SAP
				</a>
			</li> -->
			<li>
				<a class="add" onclick="makePortalC01Sap()"  title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span><%-- 整体性生成SAP --%>整体性<spring:message code="pa.salary.title.generateSAP"/></span></a>
			</li>
			<li>
				<a class="edit" onclick="makePortalC01SapPart()"  title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span><%-- 选择性生成SAP --%>选择性生成SAP</span></a>
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
	<c:set value="/pa/salary/viewPortalC01Sap" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>