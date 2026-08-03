<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function makeC01SapSummary(navTabId) {
	var year = $("#seach_sum_sapYear").val();
	var month = $("#seach_sum_sapMonth").val();
	var paMonth = $("#seach_sum_sapYear").val() + $("#seach_sum_sapMonth").val();
	var giveDate = $("#seach_SUM_GIVE_DATE").val();
	var sendDate = $("#seach_SUM_SEND_DATE").val();
	var empid = $("#seach_EMPID").val();
	if(year == ''){
		alert('<spring:message code="pa.message.pa.check.payearmustchoosed"/>');
	}else if(month == '' || paMonth == ''){
		alert('<spring:message code="pa.message.pa.check.pamonthmustchoosed"/>');
	}else if(giveDate == ''){
		alert('<spring:message code="pa.salary.title.salaryProvideDateIsMust"/>');
	}else if(sendDate == ''){
		alert('<spring:message code="pa.alert.message.sap.sendDateIsMust"/>');
	}else{
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.shengcheng'/>"+"[" + paMonth + "]"+"<spring:message code='pa.viewsapcalculate.title.monthsap'/>"+"?",
				{
					okCall : function() {
							$.ajax( {
							type : 'post',
							cache : false,
							url : '/pa/salary/addPortalInfo?SapType=C01SUMMARY&PA_MONTH='+paMonth+'&SAP_GIVE_DATE='+giveDate+'&SAP_SEND_DATE='+sendDate+'&EMPID='+empid
							       +'&YEAR='+year+'&MONTH='+month,
							success : function(responseText) {
								if (responseText == "Y"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewC01SapSummary");
									var params = $("#viewC01SapSummary").serializeArray();
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
function CheckFormSapSummary(form,navTabId){
	var $form=$(form);
	var sapYear = $form.find("#seach_sum_sapYear").val();
	var sapMonth = $form.find("#seach_sum_sapMonth").val();
	var give_date = $form.find("#seach_SUM_GIVE_DATE").val();
	var send_date = $form.find("#seach_SUM_SEND_DATE").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_sum_sapYear").focus();
		return false;
	}
	if(sapMonth == ''){
		//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_sum_sapMonth").focus();
		return false;
	}
	if(give_date == ''){
		//alert("工资发放日为必填项，请选择工资发放日！");
		alertMsg.error("<spring:message code='pa.salary.title.salaryProvideDateIsMust'/>");
		$form.find("#seach_SUM_GIVE_DATE").focus();
		return false;
	}
	if(send_date == ''){
		//alert("SAP发送日期为必填项，请选择发送日期！");
		alertMsg.error("<spring:message code='pa.alert.message.sap.sendDateIsMust'/>");
		$form.find("#seach_SUM_SEND_DATE").focus();
		return false;
	}
	
    return true;
}
function doC01SapSummaryExport(from){
  	var $from =$(from);
  	var url ="/pa/salary/viewC01SapSummaryExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function importC01SapSummary(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewC01SapSummary");
  	if(CheckFormSapSummary($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doC01SapSummaryExport($from);}});
    } 
}
</script>
<div class="pageHeader" >
	<form id="viewC01SapSummary" onsubmit="return navTabSearch(this);" action="/pa/salary/viewC01SapSummary" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td style="text-align: center" width="5%"><!--工资月-->
					<spring:message code="pa.insurance.title.salaryMonth"/>:
				</td>
				<td style="text-align: left" width="12%">
					<%--<ait:date yearName="sapYear" monthName="sapMonth" />--%>
					<select id="seach_sum_sapYear" name="seach_sum_sapYear" style="width:75px">
				    	<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach var="i" begin="2000" end="2020" step="1"> 
					    	<option value="${i}" <c:if test="${sum_sapYear eq i }">selected</c:if> >${i}</option>
					    </c:forEach>
					</select>
			    	<select id="seach_sum_sapMonth" name="seach_sum_sapMonth" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="01" <c:if test="${sum_sapMonth eq '01' }">selected</c:if>>01</option>
						<option value="02" <c:if test="${sum_sapMonth eq '02' }">selected</c:if>>02</option>
						<option value="03" <c:if test="${sum_sapMonth eq '03' }">selected</c:if>>03</option>
						<option value="04" <c:if test="${sum_sapMonth eq '04' }">selected</c:if>>04</option>
						<option value="05" <c:if test="${sum_sapMonth eq '05' }">selected</c:if>>05</option>
						<option value="06" <c:if test="${sum_sapMonth eq '06' }">selected</c:if>>06</option>
						<option value="07" <c:if test="${sum_sapMonth eq '07' }">selected</c:if>>07</option>
						<option value="08" <c:if test="${sum_sapMonth eq '08' }">selected</c:if>>08</option>
						<option value="09" <c:if test="${sum_sapMonth eq '09' }">selected</c:if>>09</option>
						<option value="10" <c:if test="${sum_sapMonth eq '10' }">selected</c:if>>10</option>
						<option value="11" <c:if test="${sum_sapMonth eq '11' }">selected</c:if>>11</option>
						<option value="12" <c:if test="${sum_sapMonth eq '12' }">selected</c:if>>12</option>
					</select>
			    </td>
			    <td style="text-align: center" width="8%"><!-- 工资发放日 -->
					<spring:message code="pa.salary.title.salaryProvideDate"/>:
				</td>
			    <td style="text-align: left" width="15%">
			    	<input type="text" id="seach_SUM_GIVE_DATE" name="seach_SUM_GIVE_DATE" class="date" readonly="true" value="${SUM_GIVE_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				<td style="text-align: center" width="8%"><!--Send日期 -->
					<spring:message code="pa.title.message.sendDate"/>:
				</td>
			    <td style="text-align: left" width="15%">
			    	<input type="text" id="seach_SUM_SEND_DATE" name="seach_SUM_SEND_DATE" class="date" readonly="true" value="${SUM_SEND_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				<td>&nbsp;</td>
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
			
			<li>
				<a class="add" onclick="makeC01SapSummary()"  title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span><%-- 生成SAP --%><spring:message code="pa.salary.title.generateSAP"/></span></a>
			</li>
			<li>
				<a class="delete" onclick="importC01SapSummary()"  title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li> 
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="5%" align="center"><!--序号-->
					<spring:message code="pa.insurance.title.orderNo"/>
				</th>
				<th width="5%" align="center"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>
				</th>
				<th width="8%" align="center"><%--门店名称--%>
					<spring:message code="pa.title.message.mendianName"/>
				</th>
				<th width="8%" align="center"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>
				</th>
				<th width="8%" align="center"><%--工资发放日--%>
					<spring:message code="pa.salary.title.salaryProvideDate"/>
				</th>
				
				<th width="10%" align="center"><%--项目名称--%>
					<spring:message code="ar.viewItem.title.xiangmumingcheng"/>
				</th>
				<th width="8%" align="center"><%--员工类型--%>
					<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
				</th>
				<th width="8%" align="center"><%--金额--%>
					<spring:message code="rp.report.title.amount"/>
				</th>
				<th width="8%" align="center"><%--发送日期--%>
					<spring:message code="pa.salary.title.senddate"/>
				</th>
				<th width="5%" align="center"><%--发送方式--%>
					<spring:message code="pa.title.message.sendType"/>
				</th>
				
				<th width="5%" align="center"><%--发送人--%>
					<spring:message code="pa.salary.title.sender"/>
				</th>
				<th width="8%" align="center"><%--发送时间--%>
					<spring:message code="pa.salary.title.sendtime"/>
				</th>
				<th width="5%" align="center"><%--当日发送次数--%>
					<spring:message code="pa.title.message.sendCount"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sapSummaryList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="5%" align="center">${i.index+1}</td>
					<td width="5%" align="center">${item.YEAR_ID }${item.MONTH_ID }</td>
					<td width="8%" align="center">${item.DISTINGUISH_NAME }</td>
					<td width="8%" align="center">${item.DEPARTMENT }</td>
					
					<td width="8%" align="center">${item.GIVE_DATE }</td>
					<td width="10%" align="center">${item.ITEM_NAME }</td>
					<td width="8%" align="center">${item.EMP_TYPE_NAME }</td>
					<td width="8%" align="center">${item.NUM }</td>
					<td width="8%" align="center">${item.SEND_DATE }</td>
					
					<td width="5%" align="center">${item.MAKE_TYPE }</td>
					<td width="5%" align="center">${item.CREATED_BY }</td>
					<td width="8%" align="center">${item.CREATE_DATE }</td>
					<td width="5%" align="center">${item.SEND_COUNT }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewC01SapSummary" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>