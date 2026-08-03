<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function CheckFormSapPaAfter(form,navTabId){
	var $form=$(form);
	var sapYear = $form.find("#seach_sapYear").val();
	var sapMonth = $form.find("#seach_sapMonth").val();
	var give_date = $form.find("#seach_SAP_GIVE_DATE").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_sapYear").focus();
		return false;
	}
	if(sapMonth == ''){
		//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_sapMonth").focus();
		return false;
	}
	if(give_date == ''){
		//alert("工资发放日为必填项，请选择工资发放日！");
		alertMsg.error("<spring:message code='pa.salary.title.salaryProvideDateIsMust'/>");
		$form.find("#seach_SAP_GIVE_DATE").focus();
		return false;
	}
	
    return true;
}
function doC01PortalSapAfterExport(from){
  	var $from =$(from);
  	var url ="/pa/salary/viewC01PortalSapAfterExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function importC01PortalSapAfter(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewC01PortalSapAfter");
  	if(CheckFormSapPaAfter($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doC01PortalSapAfterExport($from);}});
    } 
}
</script>
<div class="pageHeader" >
	<form id="viewC01PortalSapAfter" onsubmit="return navTabSearch(this);" action="/pa/salary/viewC01PortalSapAfter" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr><!--
				<td style="text-align: center" width="6%">公司名称
					<spring:message code="ar.viewcycleparameter.title.gongsimingcheng"/>:
				</td>
				<td style="text-align: center;color: red" width="8%">
					&nbsp;江苏乐天玛特&nbsp;
				</td>-->
				<td style="text-align: center" width="5%"><!--工资月-->
					<spring:message code="pa.insurance.title.salaryMonth"/>:
				</td>
				<td style="text-align: left" width="12%">
					<%--<ait:date yearName="sapYear" monthName="sapMonth" />--%>
					<select id="seach_sapYear" name="seach_sapYear" style="width:75px">
				    	<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach var="i" begin="2000" end="2020" step="1"> 
					    	<option value="${i}" <c:if test="${sapYear eq i }">selected</c:if> >${i}</option>
					    </c:forEach>
					</select>
			    	<select id="seach_sapMonth" name="seach_sapMonth" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="01" <c:if test="${sapMonth eq '01' }">selected</c:if>>01</option>
						<option value="02" <c:if test="${sapMonth eq '02' }">selected</c:if>>02</option>
						<option value="03" <c:if test="${sapMonth eq '03' }">selected</c:if>>03</option>
						<option value="04" <c:if test="${sapMonth eq '04' }">selected</c:if>>04</option>
						<option value="05" <c:if test="${sapMonth eq '05' }">selected</c:if>>05</option>
						<option value="06" <c:if test="${sapMonth eq '06' }">selected</c:if>>06</option>
						<option value="07" <c:if test="${sapMonth eq '07' }">selected</c:if>>07</option>
						<option value="08" <c:if test="${sapMonth eq '08' }">selected</c:if>>08</option>
						<option value="09" <c:if test="${sapMonth eq '09' }">selected</c:if>>09</option>
						<option value="10" <c:if test="${sapMonth eq '10' }">selected</c:if>>10</option>
						<option value="11" <c:if test="${sapMonth eq '11' }">selected</c:if>>11</option>
						<option value="12" <c:if test="${sapMonth eq '12' }">selected</c:if>>12</option>
					</select>
			    </td>
			    <td style="text-align: center" width="8%"><!-- 工资发放日 -->
					<spring:message code="pa.salary.title.salaryProvideDate"/>:
				</td>
			    <td style="text-align: left" width="15%">
			    	<input type="text" id="seach_SAP_GIVE_DATE" name="seach_SAP_GIVE_DATE" class="date" readonly="true" value="${SAP_GIVE_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				 <td style="text-align: center" width="8%"><!-- 发送日期 -->
					<spring:message code="pa.salary.title.senddate"/>:
				</td>
			    <td style="text-align: left" width="15%">
			    	<input type="text" id="seach_SEND_DATE" name="seach_SEND_DATE" class="date" readonly="true" value="${SEND_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				 <td style="text-align: center" width="5%"><%--工号--%>
					<spring:message code="public.title.empId"/>:
				</td>
			    <td style="text-align: left" width="10%">
					<input type="text" id="seach_EMPID" name="seach_EMPID" value="${EMPID}">
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
			<li>
				<a class="delete" onclick="importC01PortalSapAfter()"  title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li> 
		</ul>
	</div>
	<table class="table" width="120%" layoutH="150">
		<thead>
			<tr>
				<th width="10%" align="center"><%--当日发送次数--%>
					<spring:message code="pa.title.message.sendCount"/>
				</th>
				<th width="5%" align="center"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>
				</th>
				<th width="8%" align="center"><%--工资发放日--%>
					<spring:message code="pa.salary.title.salaryProvideDate"/>
				</th>
				<th width="8%" align="center"><%--发送日期--%>
					<spring:message code="pa.salary.title.senddate"/>
				</th>
				<th width="5%" align="center"><!--发送人-->
					<spring:message code="pa.salary.title.sender"/>
				</th>
				
				<th width="10%" align="center"><%--部门区分名称--%>
					<spring:message code="ess.trans.title.distinctDeptName"/>
				</th>
				<th width="5%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="8%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th width="10%" align="center"><%--实发工资--%>
					<spring:message code="ess.viewpersonalpainfo.shifagongzi"/>
				</th>
				<th width="8%" align="center"><%--银行名称--%>
					<spring:message code="hr.viewCondSql.title.YINHANGMINGCHENG"/>
				</th>
				
				<th width="15%" align="center"><%--银行账号--%>
					<spring:message code="rp.report.title.bankcardno"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sapPaAfterList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="10%" align="center">${item.SEND_COUNT }</td>
					<td width="5%" align="center">${item.PA_MONTH }</td>
					<td width="8%" align="center">${item.GIVE_DATE }</td>
					<td width="8%" align="center">${item.SEND_DATE }</td>
					<td width="5%" align="center">${item.CREATED_BY}</td>
					
					<td width="10%" align="center">${item.DEPT_DISTINGUISH_NAME }</td>
					<td width="5%" align="center">${item.EMPID }</td>
					<td width="8%" align="center">${item.EMP_NAME }</td>
					<td width="10%" align="center">${item.ACTUAL_RELEASE_SALARY }</td>
					<td width="8%" align="center">${item.BANK_NAME }</td>
					<td width="15%" align="center">${item.CARD_NO }</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewC01PortalSapAfter" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>