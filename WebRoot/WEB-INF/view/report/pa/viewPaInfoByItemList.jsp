<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function  openEmpProveSearch(){
	 var $form = $("#viewPaInfoByItemList");
    var empid = $form.find("#seach_EMPID").val();
    var deptno = $form.find("#seach_DEPTNO").val();
    var year = $form.find("#seach_YEAR").val();
    var month = $form.find("#seach_MONTH").val();
    
    if(checkEmpPaMonth()==true){
	     window.document.getElementById("onOfficeProve").href="/report/pa/viewEmpProveList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno
	     		+"&seach_YEAR="+year+"&seach_MONTH="+month;
    }else{
   	 	window.document.getElementById("onOfficeProve").href="/report/pa/viewSapPaError";
    }
}
function checkEmpPaMonth(){
	 var $form = $("#viewPaInfoByItemList");
    var deptno = $form.find("#seach_DEPTNO").val();
    var year = $form.find("#seach_YEAR").val();
    var month = $form.find("#seach_MONTH").val();
    if(deptno=='' || deptno==null){
   	//alert("部门为必选项，请选择部门！");
		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
		$form.find("#seach_DEPTNO").focus();
		return false;
    }
    if(year=='' || year==null){
   	//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_YEAR").focus();
		return false;
    }
    if(month=='' || month==null){
   	//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_MONTH").focus();
		return false;
    }
    return true;
}
function openPaInfoByYearExecl(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewPaInfoByItemList");  
     
	 var url ="/report/pa/viewPaInfoByItemExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
   
}
</script>
<div class="pageHeader" >
	<form id="viewPaInfoByItemList" onsubmit="return navTabSearch(this);" action="/report/pa/viewPaInfoByItemList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="5%">公司名称：</td>
				<td width="20%" style="text-align:left">
					<select class="combox" id="combox_CPNY_ID" name="seach_CPNY_ID" 
						ref="combox_ITEM_NO" refUrl="/sys/ajax/getPaItemInfoList?seach_CPNY_ID={value}"
						ref2="combox_PA_MONTH" refUrl2="/sys/ajax/getPaMonthList?seach_CPNY_ID={value}">
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach items="${companyList}" var="company" varStatus="i">
							<option value="${company.CPNY_ID}" <c:if test="${CPNY_ID eq company.CPNY_ID }">selected</c:if>>${company.COMPANY_NAME}</option>
					    </c:forEach>
					</select>
				</td>
				<td width="5%" style="text-align:right"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>：
				</td>
				<td width="15%" style="text-align:left">
					<select class="combox" name="seach_PA_MONTH" id="combox_PA_MONTH" refId="combox_CPNY_ID" ref="combox_GIVE_DATE" 
						refUrl="/sys/ajax/getPaGiveDateList?seach_PA_MONTH={value}">
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach items="${getPaMonthList}" var="month" varStatus="i">
							<option value="${month.PA_MONTH}" <c:if test="${PA_MONTH eq month.PA_MONTH }">selected</c:if> >${month.PA_MONTH}</option>
					    </c:forEach>
					</select>
				</td>
				<td width="5%" style="text-align:right">工资发放日：</td>
				<td width="8%" style="text-align:left">
				    <select class="combox" name="seach_GIVE_DATE" id="combox_GIVE_DATE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach items="${getPaGiveDateList}" var="give" varStatus="i">
							<option value="${give.GIVE_DATE}" <c:if test="${GIVE_DATE eq give.GIVE_DATE }">selected</c:if> >${give.GIVE_DATE}</option>
					    </c:forEach>
					</select>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="5%" style="text-align:center"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>：
				</td>
				<td width="20%" style="text-align:left">
					<ait:deptTreeForCombox deptId="seach_DEPT_NO" deptName="seach_DEPT_NAME" formName="viewPaInfoByItemList" 
						selectedId="${DEPT_NO}" selectedName="${DEPT_NAME}"/>
				</td>
				<td width="5%" style="text-align:right"><!--项目名称-->
					<spring:message code="pa.insurance.title.projectName"/>：
				</td>
				<td width="8%" style="text-align:left">
					<select class="combox" name="seach_ITEM_NO" id="combox_ITEM_NO" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach items="${getPaItemInfoList}" var="item" varStatus="i">
							<option value="${item.ITEM_ID}" <c:if test="${ITEM_NO eq item.ITEM_ID }">selected</c:if> >${item.ITEM_NAME}</option>
					    </c:forEach>
					</select>
				</td>
				<td width="5%" style="text-align:right"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="8%" style="text-align:left">
				    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
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
	<div class="formBar">
		<ul class="toolBar">
			 <li>
				  <a class="edit" onclick="openPaInfoByYearExecl(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">公司</th>
				<th style="text-align: center">工资月</th>
				<th style="text-align: center">工资发放日</th>
				<th style="text-align: center">项目名称</th>
				
				<th style="text-align: center">工号</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">开始月</th>
				<th style="text-align: center">结束月</th>
				
				<th style="text-align: center">值</th>
				<th style="text-align: center">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paInfoByItemList}" var="pa" varStatus="i">
				<tr target="EMPID" rel="${pa.EMPID}">
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${pa.COMPANY_NAME}</td>
					<td style="text-align: center">${pa.PA_MONTH}</td>
					<td style="text-align: center">${pa.GIVE_DATE}</td>
					<td style="text-align: center">${pa.ITEM_NAME}</td>
					
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td style="text-align: center">${pa.DEPT_NAME}</td>
					<td style="text-align: center">${pa.START_MONTH}</td>
					<td style="text-align: center">${pa.END_MONTH}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.SALARY}</td>
					<td style="text-align: center">${pa.MARK}</td>
				</tr>
			</c:forEach>    
		</tbody>
	</table>
	<c:set value="/report/pa/viewPaInfoByItemList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>