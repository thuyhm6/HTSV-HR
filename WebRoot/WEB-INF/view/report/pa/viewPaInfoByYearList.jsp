<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function  openEmpProveSearch(){
	 var $form = $("#viewPaInfoByYearList",navTab.getCurrentPanel());
    var empid = $form.find("#seach_EMPID").val();
    var deptno = $form.find("#seach_DEPT_NO").val();
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
	 var $form = $("#viewPaInfoByYearList",navTab.getCurrentPanel());
    var deptno = $form.find("#seach_DEPT_NO").val();
    var year = $form.find("#seach_YEAR").val();
    var month = $form.find("#seach_MONTH").val();
    if(deptno=='' || deptno==null){
   	//alert("部门为必选项，请选择部门！");
		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
		$form.find("#seach_DEPT_NO").focus();
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
     var $from = $("#viewPaInfoByYearList");  
                      
	 var url ="/report/pa/exportViewPaInfoByYearExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
   
}
function openPaInfoByYearExecl1(a){
	//var title = $this.attr("title"); 
	var cpnyid=$("#combox_CPNY_ID",navTab.getCurrentPanel()).val();
	var empid=$("#seach_EMPID",navTab.getCurrentPanel()).val();
	var deptid=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var year=$("#seach_PA_YEAR",navTab.getCurrentPanel()).val();
	$.ajax({ 
	 		async: false,
	 		type: "POST",         
	 		url: "/pa/excelExport/exportViewPaInfoByYearExcel", 
	 		data: "seach_CPNY_ID="+cpnyid+"&seach_EMPID="+empid+"&seach_DEPT_NO="+deptid+"&seach_PA_YEAR="+year,
	 		dataType: "json",
	 		success: function(resp){
				//alert(resp);
	 			document.getElementById("excelExportPaResult_pa0107").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr;
	 			document.getElementById("excelExportPaResult_pa0107").click();
	 		} 
	 	});
	
}
function pageFromSea(a){
	//$("#id",navTab.getCurrent())    根据id 获取当前navTab的dom节点
	
	var seach_EMPID=$("#seach_EMPID",navTab.getCurrentPanel()).val();
	var seach_DEPT_NAME=$("#seach_DEPT_NAME",navTab.getCurrentPanel()).val();
	var combox_CPNY_ID=$("#combox_CPNY_ID",navTab.getCurrentPanel()).val();
	//$("#id",navTab.getCurrent())   
	//$("#pagerForm",navTab.getCurrentPanel());
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/report/pa/viewPaInfoByYearList?seach_EMPID="+seach_EMPID+"&seach_DEPT_NAME="+seach_DEPT_NAME+"&seach_CPNY_ID="+combox_CPNY_ID);
}
function cpnyChange(){
	//alert(1);
	//$('#seach_DEPT_NAME').val("aaaaa");
	//$('#seach_DEPT_NAME').trigger("click");
	//$("#seach_DEPT_NAME").click();
	//var deptName=$("#seach_DEPT_NAME1").val();
	//var deptNo=$("#seach_DEPT_NO1").val();
	//$("#seach_DEPT_NAME option[text='测试部门']").attr("selected", true); 
	//$("#seach_DEPT_NAME").val(deptName);
	//$("#seach_DEPT_NO").val(deptNo);
	
}
</script>
<div class="pageHeader" >
	<form id="viewPaInfoByYearList" onsubmit="return navTabSearch(this);" action="/report/pa/viewPaInfoByYearList" method="post" rel="pagerForm">
	<div class="searchBar">
		<a class="buttonActive" id="excelExportPaResult_pa0107"
					style="display: none">&nbsp;</a>
		
		<table class="searchContent">
			<tr>
				<td width="5%"><spring:message code="sys.basic.title.companyName"/><!-- 公司名称 -->：</td>
				<td width="20%" style="text-align:left">
					<select class="combox" id="combox_CPNY_ID" name="seach_CPNY_ID" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<c:forEach items="${companyList}" var="company" varStatus="i">
							<option value="${company.CPNY_ID}" <c:if test="${seach_CPNY_ID eq company.CPNY_ID }">selected</c:if>>${company.COMPANY_NAME}</option>
					    </c:forEach>
					</select>
				</td>
				<td width="5%" style="text-align:right"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="8%" style="text-align:left">
				    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${seach_EMPID }">
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="5%" style="text-align:center"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>：
				</td>
				<td width="20%" style="text-align:left">
					<ait:deptTreeForCombox deptId="seach_DEPT_NO"  deptName="seach_DEPT_NAME" formName="viewPaInfoByYearList" 
						selectedId="${seach_DEPT_NO}" selectedName="${seach_DEPT_NAME}"/>
				</td>
				
				<td width="5%" style="text-align:right"><!-- 年份 -->
					<spring:message code="pa.payear.title.payear"/>：
				</td>
				<td width="8%" style="text-align:left">
								
					<select id="seach_PA_YEAR" name="seach_PA_YEAR" style="width:75px">
						<c:forEach var="i" begin="2011" end="2025" step="1"> 
					    	<option value="${i}" <c:if test="${seach_PA_YEAR eq i }">selected</c:if> >${i}</option>
					    </c:forEach> 
					 </select>
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
				  <a class="edit" onclick="openPaInfoByYearExecl1(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th><spring:message code="ar.viewcycle.title.xuhao" /><!-- 序号 --></th>
				<th><spring:message code="liang.hr.viewWorkInfo.title.farenmingcheng"/><!-- 法人名称 --></th>
				<th><spring:message code="pa.payear.title.payear"/><!-- 年份 --></th>
				<th><spring:message code="public.title.empId"/><!-- 工号 --></th>
				<th><spring:message code="public.title.empName"/><!-- 姓名 --></th>
				<th><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/> <!-- 部门 --></th>
				<th><spring:message code="hr.viewPersonalInfo.title.POST_NAME"/><!--职级名称 --></th>
				<th><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE"/><!-- 职责 --></th>
				<th><spring:message code="main.home.message.ruzhiriqi"/><!-- 入职日期 --></th>
				
				<th>01<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>02<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>03<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>04<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>05<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>06<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				
				<th>07<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>08<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>09<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>10<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>11<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th>12<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				
				<th><spring:message code="liang.hr.viewWorkInfo.title.zongji"/><!-- 总计 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paInfoByYearList}" var="pa" varStatus="i">
				<tr target="EMPID" rel="${pa.EMPID}">
					<td style="text-align: center">${i.index+1 }</td>
					<td>${pa.COMPANY_NAME}</td>
					
					<td style="text-align: center">${pa.PA_YEAR}</td>
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td>${pa.DEPT_NAME}</td>
					<td>${pa.POST_NAME}</td>
					<td>${pa.DUTY_NAME}</td>
					<td>${pa.DATE_STARTED}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M01_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M02_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M03_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M04_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M05_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M06_SALARY}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M07_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M08_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M09_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M10_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M11_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M12_SALARY}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.SUM_SALARY}</td>
				</tr>
			</c:forEach>    
		</tbody>
	</table>
	
	<c:set value="/report/pa/viewPaInfoByYearList" var="pageUrl"  />
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>