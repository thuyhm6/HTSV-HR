<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function makeC01PortalSapPre(navTabId) {
	var year = $("#seach_firm_sapYear").val();
	var month = $("#seach_firm_sapMonth").val();
	var paMonth = $("#seach_firm_sapYear").val() + $("#seach_firm_sapMonth").val();
	var giveDate = $("#seach_FIRM_GIVE_DATE").val();
	var sendDate = $("#seach_FIRM_SEND_DATE").val();
	var sendType = $("#seach_FIRM_SEND_TYPE").val();
	var empid = $("#seach_EMPID").val();
	
	if(year == ''){
		alert('<spring:message code="pa.message.pa.check.payearmustchoosed"/>');
	}else if(month == '' || paMonth == ''){
		alert('<spring:message code="pa.message.pa.check.pamonthmustchoosed"/>');
	}else if(giveDate == ''){
		alert('<spring:message code="pa.salary.title.salaryProvideDateIsMust"/>');
	}else if(sendType == ''){
		alert('<spring:message code="pa.alert.message.sap.sendTypeIsMust"/>');
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
							url : '/pa/salary/addPortalInfo?SapType=C01FIRM&PA_MONTH='+paMonth+'&SEND_TYPE='+sendType+'&SAP_GIVE_DATE='+giveDate+'&SAP_SEND_DATE='+sendDate+'&EMPID='+empid,
							success : function(responseText) {
								if (responseText == "Y"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewC01PortalSapPre");
									var params = $("#viewC01PortalSapPre").serializeArray();
									navTab.reload($form.attr('action')+'?pageNum=1', {data: params, navTabId:navTabId});
								}else if(responseText == "N"){
									alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
								}else if(responseText == "S"){
									alert('<spring:message code="alert.message.pa.salary.title.salaryMonthHasNoData"/>');
								}else{
									alert(responseText+'<spring:message code="pa.alert.message.sendToSapEmpInfoError"/>');
								}
							}
						});
					}
				});
		}
}
function CheckFormSapPaPre(form,navTabId){
	var $form=$(form);
	
	var sapYear = $form.find("#seach_firm_sapYear").val();
	var sapMonth = $form.find("#seach_firm_sapMonth").val();
	var give_date = $form.find("#seach_FIRM_GIVE_DATE").val();
	var send_date = $form.find("#seach_FIRM_SEND_DATE").val();
	var send_type = $form.find("#seach_FIRM_SEND_TYPE").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_firm_sapYear").focus();
		return false;
	}
	if(sapMonth == ''){
		//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_firm_sapMonth").focus();
		return false;
	}
	if(give_date == ''){
		//alert("工资发放日为必填项，请选择工资发放日！");
		alertMsg.error("<spring:message code='pa.salary.title.salaryProvideDateIsMust'/>");
		$form.find("#seach_FIRM_GIVE_DATE").focus();
		return false;
	}
	if(send_type == ''){
		//alert("SAP发送类型为必填项，请选择发送类型！");
		alertMsg.error("<spring:message code='pa.alert.message.sap.sendTypeIsMust'/>");
		$form.find("#seach_FIRM_SEND_TYPE").focus();
		return false;
	}
	if(send_date == ''){
		//alert("SAP发送日期为必填项，请选择发送日期！");
		alertMsg.error("<spring:message code='pa.alert.message.sap.sendDateIsMust'/>");
		$form.find("#seach_FIRM_SEND_DATE").focus();
		return false;
	}
	
    return true;
}
function doC01PortalSapPreExport(from){
  	var $from =$(from);
  	var url ="/pa/salary/viewC01PortalSapPreExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expC01PortalSapPre(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewC01PortalSapPre");
  	if(CheckFormSapPaPre($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doC01PortalSapPreExport($from);}});
    } 
}
function doSapErrorEmpExport(from){
  	var $from =$(from);
  	var url ="/pa/salary/viewSapErrorEmpExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expSapErrorEmp(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewC01PortalSapPre");
  	if(CheckFormSapPaPre($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doSapErrorEmpExport($from);}});
    } 
}
function CheckFormSimple(form,navTabId){
	var $form=$(form);
	
	var sapYear = $form.find("#seach_firm_sapYear").val();
	var sapMonth = $form.find("#seach_firm_sapMonth").val();
	var give_date = $form.find("#seach_FIRM_GIVE_DATE").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_firm_sapYear").focus();
		return false;
	}
	if(sapMonth == ''){
		//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_firm_sapMonth").focus();
		return false;
	}
	if(give_date == ''){
		//alert("工资发放日为必填项，请选择工资发放日！");
		alertMsg.error("<spring:message code='pa.salary.title.salaryProvideDateIsMust'/>");
		$form.find("#seach_FIRM_GIVE_DATE").focus();
		return false;
	}
	
    return true;
}
function makeZhaBeiAndJiaXingCash(navTabId) {
	var year = $("#seach_firm_sapYear").val();
	var month = $("#seach_firm_sapMonth").val();
	var paMonth = $("#seach_firm_sapYear").val() + $("#seach_firm_sapMonth").val();
	var giveDate = $("#seach_FIRM_GIVE_DATE").val();
	
	if(year == ''){
		alert('<spring:message code="pa.message.pa.check.payearmustchoosed"/>');
	}else if(month == '' || paMonth == ''){
		alert('<spring:message code="pa.message.pa.check.pamonthmustchoosed"/>');
	}else if(giveDate == ''){
		alert('<spring:message code="pa.salary.title.salaryProvideDateIsMust"/>');
	}else{//2013-12-13 原来只有闸北、嘉兴店，现在加一个苏州店，一个徐州店
		alertMsg.confirm("确定将"+"[" + paMonth + "]"+"月份门店独立法人的人员转为现金标记？",
				{
					okCall : function() {
							$.ajax( {
							type : 'post',
							cache : false,
							url : '/pa/salary/addZhaBeiAndJiaXingCash?seach_firm_sapYear='+year+'&seach_firm_sapMonth='+month
							+'&seach_FIRM_GIVE_DATE='+giveDate,
							success : function(responseText) {
								if (responseText == "Y"){
									alert("转换成功！");
									//页面重载
									//navTabSearch(document.viewPortal);
									var $form = $("#viewC01PortalSapPre");
									var params = $("#viewC01PortalSapPre").serializeArray();
									navTab.reload($form.attr('action')+'?pageNum=1', {data: params, navTabId:navTabId});
								}else if(responseText == "N"){
									alert("转换成功！");
								}else{
									alert("转换成功！");
								}
							}
						});
					}
				});
		}
}
function doZhaBeiAndJiaXingEmpPaExport(form){
  	var $form =$(form);
  	var url ="/pa/salary/viewZhaBeiAndJiaXingEmpPaExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expZhaBeiAndJiaXingEmpPa(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewC01PortalSapPre");
  	if(CheckFormSimple($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doZhaBeiAndJiaXingEmpPaExport($from);}});
    } 
}
</script>
<div class="pageHeader" >
	<form id="viewC01PortalSapPre" onsubmit="return navTabSearch(this);" action="/pa/salary/viewC01PortalSapPre" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td style="text-align: center" width="8%"><!--工资月-->
					<spring:message code="pa.insurance.title.salaryMonth"/>:
				</td>
				<td style="text-align: left" width="16%">
					<%--<ait:date yearName="sapYear" monthName="sapMonth" />--%>
					<select id="seach_firm_sapYear" name="seach_firm_sapYear" style="width:75px">
				    	<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach var="i" begin="2010" end="2030" step="1"> 
					    	<option value="${i}" <c:if test="${firm_sapYear eq i }">selected</c:if> >${i}</option>
					    </c:forEach>
					</select>
			    	<select id="seach_firm_sapMonth" name="seach_firm_sapMonth" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="01" <c:if test="${firm_sapMonth eq '01' }">selected</c:if>>01</option>
						<option value="02" <c:if test="${firm_sapMonth eq '02' }">selected</c:if>>02</option>
						<option value="03" <c:if test="${firm_sapMonth eq '03' }">selected</c:if>>03</option>
						<option value="04" <c:if test="${firm_sapMonth eq '04' }">selected</c:if>>04</option>
						<option value="05" <c:if test="${firm_sapMonth eq '05' }">selected</c:if>>05</option>
						<option value="06" <c:if test="${firm_sapMonth eq '06' }">selected</c:if>>06</option>
						<option value="07" <c:if test="${firm_sapMonth eq '07' }">selected</c:if>>07</option>
						<option value="08" <c:if test="${firm_sapMonth eq '08' }">selected</c:if>>08</option>
						<option value="09" <c:if test="${firm_sapMonth eq '09' }">selected</c:if>>09</option>
						<option value="10" <c:if test="${firm_sapMonth eq '10' }">selected</c:if>>10</option>
						<option value="11" <c:if test="${firm_sapMonth eq '11' }">selected</c:if>>11</option>
						<option value="12" <c:if test="${firm_sapMonth eq '12' }">selected</c:if>>12</option>
					</select>
			    </td>
			    <td style="text-align: center" width="8%"><!-- 工资发放日 -->
					<spring:message code="pa.salary.title.salaryProvideDate"/>:
				</td>
			    <td style="text-align: left" width="18%">
			    	<input type="text" id="seach_FIRM_GIVE_DATE" name="seach_FIRM_GIVE_DATE" class="date" readonly="true" value="${FIRM_GIVE_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				<td style="text-align: center" width="8%"><%--工号--%>
					<spring:message code="public.title.empId"/>:
				</td>
			    <td style="text-align: left" width="12%">
					<input type="text" id="seach_EMPID" name="seach_EMPID" value="${EMPID}">
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: center" width="8%"><!--发送类型-->
					<spring:message code="pa.title.message.sendType"/>:
				</td>
				<td style="text-align: left" width="16%">
					<select id="seach_FIRM_SEND_TYPE" name="seach_FIRM_SEND_TYPE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="ALL_TYPE" <c:if test="${FIRM_SEND_TYPE eq 'ALL_TYPE' }">selected</c:if>><%--Sap整体发送--%>
							<spring:message code="pa.title.message.sendTypeContentOne"/>
						</option>
						<option value="PART_TYPE" <c:if test="${FIRM_SEND_TYPE eq 'PART_TYPE' }">selected</c:if>><%--Sap选择发送--%>
							<spring:message code="pa.title.message.sendTypeContentTwo"/>
						</option>
					</select>
				</td>
				<td style="text-align: center" width="8%"><!--发送日期 -->
					<spring:message code="pa.salary.title.senddate"/>:
				</td>
			    <td style="text-align: left" width="18%">
			    	<input type="text" id="seach_FIRM_SEND_DATE" name="seach_FIRM_SEND_DATE" class="date" readonly="true" value="${FIRM_SEND_DATE }"/>
					<a class="inputDateButton" href="javascript:;"><%--选择--%>
						<spring:message code="pa.insurance.title.pleaseChoose"/>
					</a>
				</td>
				
				<td style="text-align: center" width="8%"><!--金额范围-->
					<spring:message code="pa.title.message.actualsalary.moneycount"/>:
				</td>
				<td style="text-align: left" width="12%">
					<select id="seach_MORE_FIFTY_THOUSAND" name="seach_MORE_FIFTY_THOUSAND" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="LESS_THAN" <c:if test="${MORE_FIFTY_THOUSAND eq 'LESS_THAN' }">selected</c:if>><%--实发<5W--%>
							<spring:message code="pa.title.message.actualsalary.lessthenfiftythousand"/>
						</option>
						<option value="MORE_THAN" <c:if test="${MORE_FIFTY_THOUSAND eq 'MORE_THAN' }">selected</c:if>><%--实发>=5W--%>
							<spring:message code="pa.title.message.actualsalary.morethenfiftythousand"/>
						</option>
					</select>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul>
			 	<li>
			 		<div class="buttonActive"><div class="buttonContent"><button type="submit">
			 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div>
		 		</li>
		 		<li>
					<a class="buttonActive" href="/pa/excelExport/exportSapSpecialEmpModel"><span><%--特殊人员导入模板下载--%>
						<spring:message code="pa.button.message.specialempimportmodeldown"/></span>
					</a>
				</li>
				<li>
					<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?&importFunName=importSapSpecialEmpInfo" 
						target="dialog" mask="true" width="400" height="200" ><span><%--特殊人员导入 --%>
						<spring:message code="pa.button.message.specialempimport"/></span>
					</a>
				</li>
		 	</ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" onclick="makeC01PortalSapPre()" title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span><%-- 生成SAP --%><spring:message code="pa.salary.title.generateSAP"/></span></a>
			</li>
			<li>
				<a class="delete" onclick="expC01PortalSapPre()" title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
			<li>
				<a class="delete" onclick="expSapErrorEmp()" title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--异常人员导出--%><spring:message code="pa.title.message.sendToSapEmpInfoErrorExport"/></span></a>
			</li>
			<li><%-- 2013-12-13 原来只有闸北、嘉兴店，现在加一个苏州店 --%>
				<a class="add" onclick="makeZhaBeiAndJiaXingCash()" title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span><%-- 独立法人员工转现金 --%>独立法人员工转现金金</span></a>
			</li>
			<li><%-- 2013-12-13 原来只有闸北、嘉兴店，现在加一个苏州店 --%>
				<a class="delete" onclick="expZhaBeiAndJiaXingEmpPa()" title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--独立法人人员导出--%>独立法人人员导出</span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="175">
		<thead>
			<tr>
				<th width="5%" align="center"><!--序号-->
					<spring:message code="pa.insurance.title.orderNo"/>
				</th>
				<th width="5%" align="center"><!--发送与否-->
					<spring:message code="pa.salary.title.sendYN"/>
				</th>
				<th width="5%" align="center"><%--公司ID--%>
					<spring:message code="pa.insurance.title.companyID"/>
				</th>
				<th width="5%" align="center"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>
				</th>
				<th width="8%" align="center"><%--工资发放日--%>
					<spring:message code="pa.salary.title.salaryProvideDate"/>
				</th>
				<th width="8%" align="center"><%--发送日期--%>
					<spring:message code="pa.salary.title.senddate"/>
				</th><%--
				<th width="7%" align="center">部门区分NO
					<spring:message code="ess.trans.title.distinctDeptName"/>NO
				</th>--%>
				<th width="10%" align="center"><%--部门区分名称--%>
					<spring:message code="ess.trans.title.distinctDeptName"/>
				</th>
				<th width="7%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="8%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th width="10%" align="center"><%--实发工资--%>
					<spring:message code="ess.viewpersonalpainfo.shifagongzi"/>
				</th><%--
				<th width="5%" align="center">银行ID
					<spring:message code="rp.report.title.bankname"/>ID
				</th>--%>
				<th width="10%" align="center"><%--银行名称--%>
					<spring:message code="hr.viewCondSql.title.YINHANGMINGCHENG"/>
				</th>
				<th width="15%" align="center"><%--银行账号--%>
					<spring:message code="rp.report.title.bankcardno"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sapPaPreList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="5%" align="center">${i.index+1}</td>
					<td width="5%" align="center"><!--
						<c:if test="${item.SEND_FLAG eq 'Y' }"> 
							<spring:message code="pa.salary.title.issended"/>已发送
						</c:if>--><!--
						<c:if test="${item.SEND_FLAG eq 'N' }"> 
							<spring:message code="pa.salary.title.nosended"/>未发送
						</c:if>-->${item.SEND_FLAG}
					</td>
					<td width="5%" align="center">${item.CPNY_ID }</td>
					<td width="5%" align="center">${item.PA_MONTH }</td>
					<td width="8%" align="center">${item.GIVE_DATE }</td>
					<td width="8%" align="center">${item.SEND_DATE }</td>
					<!--<td width="7%" align="center">${item.DEPT_DISTINGUISH_NO }</td>-->
					
					<td width="10%" align="center">${item.DEPT_DISTINGUISH_NAME }</td>
					<td width="7%" align="center">${item.EMPID }</td>
					<td width="8%" align="center">${item.EMP_NAME }</td>
					<td width="10%" align="center">${item.ACTUAL_RELEASE_SALARY }</td>
					<!--<td width="5%" align="center">${item.BANK_ID }</td>-->
					<td width="10%" align="center">${item.BANK_NAME }</td>
					<td width="15%" align="center">${item.CARD_NO }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewC01PortalSapPre" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>