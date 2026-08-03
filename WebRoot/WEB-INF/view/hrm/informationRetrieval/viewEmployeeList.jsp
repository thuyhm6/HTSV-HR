<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function CheckFormContractSearch(form,navTabId){
	var $form=$(form);
	
	<%--var sapYear = $form.find("#seach_sapYear").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_sapYear").focus();
		return false;
	}--%>
	
    return true;
}
function doContractInfoExport(from){
	var STATUS_1=document.getElementById("STATUS_1").value;
	var CPNYSTATUS=document.getElementById("CPNYSTATUS").value; 
	var status_code_emp=document.getElementById("status_code_emp").value; 
	var seach_DEPTNO=document.getElementById("seach_DEPTNO").value; 
  	var $from =$(from);
  	var url ="/hrm/informationRetrieval/viewEmployeeListExcel?STATUS_1="+STATUS_1+"&CPNYSTATUS="+CPNYSTATUS+"&status_code_emp="+status_code_emp+"&seach_DEPTNO="+seach_DEPTNO;
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expContractInfo(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewEmployeeForm");
  	if(CheckFormContractSearch($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doContractInfoExport($from);}});
    } 
}
function checkBoxSelect(){
	var box=document.getElementById("status_code_emp");
	if(box.checked==true){
		document.getElementById("status_code_emp").value="1";
		document.getElementById("STATUS_1").value="1";
	}else{
		document.getElementById("status_code_emp").value="0";
		document.getElementById("STATUS_1").value="0";
	}
	
}
function checkBoxSelect1(){
	var box=document.getElementById("CPNYSTATUS");
	if(box.checked==true){
		document.getElementById("CPNYSTATUS").value="1";
		
	}else{
		document.getElementById("CPNYSTATUS").value="0";
		
	}
	
}
function openPaInfoByYearExecl1(a){
	//var title = $this.attr("title"); 
	var STATUS_1=document.getElementById("STATUS_1").value;
	var CPNYSTATUS=document.getElementById("CPNYSTATUS").value; 
	var status_code_emp=document.getElementById("status_code_emp").value; 
	var seach_DEPTNO=document.getElementById("seach_DEPTNO").value; 
	$.ajax({ 
	 		async: false,
	 		type: "POST",         
	 		url: "/pa/excelExport/viewEmployeeListExcel", 
	 		data: "STATUS_1="+STATUS_1+"&CPNYSTATUS="+CPNYSTATUS+"&status_code_emp="+status_code_emp+"&seach_DEPTNO="+seach_DEPTNO,
	 		dataType: "json",
	 		success: function(resp){
				//alert(resp);
	 			document.getElementById("viewEmployeeListExcel_zzjg0001").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr;
	 			document.getElementById("viewEmployeeListExcel_zzjg0001").click();
	 		} 
	 	});
	
}
</script>
<div class="pageHeader">
	<form name="viewEmployeeForm" onsubmit="return navTabSearch(this);" action="/hrm/informationRetrieval/viewEmployeeList" method="post" rel="pagerForm" >
	<a class="buttonActive" id="viewEmployeeListExcel_zzjg0001"
					style="display: none">&nbsp;</a>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td ><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</td>
				<td width="150" >
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
				</td>
				<td >
					<spring:message code="heran.informationRetrieval.title.SHIFOUBAOHANXIAJIBUMEN"/><input type="checkbox" 
						<c:if test='${CPNYSTATUS eq "1"}'>
								checked="checked"
							</c:if>
						
						id="CPNYSTATUS" name="CPNYSTATUS" value="${CPNYSTATUS}" onclick="checkBoxSelect1()"/>
				</td>
				<td >
					<spring:message code="heran.informationRetrieval.title.SHIFOUBAOHANLIZHIYUANGONG"/><input type="checkbox" name="status_code_emp" id="status_code_emp"  value="${STATUS}"  onclick="checkBoxSelect()" 
							<c:if test='${STATUS eq "1"}'>
								checked="checked"
							</c:if>
					/>
					<input type=hidden name="STATUS_1" value="${STATUS}" id="STATUS_1"/>
				</td>
			</tr>
		
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 检索 -->
						<button type="submit"><spring:message code="public.title.search"/></button>
					</div></div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent">
						<button type="button" onclick="openPaInfoByYearExecl1(this)" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>						
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	
	<table class="table" width="101.8%" layoutH="140">
		<thead>
											<tr>
												<th>
													<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
													<!--社号  -->
												</th>
												<th>
													<spring:message code="hr.viewHire.title.LOCALNAMEANDPINYIN"/>
													<!--姓名  (拼音)-->
												</th>
												<th>
													<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
													<!--身份证号-->
												</th>
												<th>
													<spring:message code="hr.viewPersonalInfo.title.SEX" />
						<!--性别-->
												</th>
												<th>
													<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
												</th>
												<th>
															<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门-->
									
												</th>
												<th>
										<spring:message
							code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" />
						<!--职等-->
									
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
						<!--职责-->
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
						<!--职级(GGS)-->
												</th>
												<th>
												
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)  -->
					</td>
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.ENTRY_DATE"/>
						<!--入司日期 即子公司入职日期 -->
												</th>
												<th>
											<spring:message code="hr.viewPersonalInfo.title.IN_THE_DIFFERENCE" />
						<!--在职区分-->
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE" />
						<!--现部门异动日期-->
												</th>
												<th>
											<spring:message code="liang.hr.viewPersonalInfo.title.ADVANCEMENT_DATE"/>
						<!--晋升日期-->	
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
						<!--离职日期-->	
												</th>
												<th>
										<spring:message
							code="hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY" />
						<!--司内工作年资-->		
												</th>
												<th>
									<spring:message code="hr.viewPersonalInfo.title.EMPLOYMENT_TYPE"/>
						<!--雇佣类型-->			
												</th>
												<th>
												<spring:message code="hr.viewPersonalInfo.title.WORK_TIME_TYPE"/>
						<!--工作时间类型  -->	
												</th>
												<th>
									<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
						<!--契约类型 （${personInfo.CONTRACT_NAME } 契约名称）-->			
												</th>
											
												<th>
										<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
						<!--详细人力区分 -->		
												</th>
											</tr>
										</thead>
										
										<tbody>
										<c:forEach items="${perinfo}" var="item" varStatus="i">
												<tr >
													<td class='td_left' >
													
														<a href="/hrm/empinfo/viewPersonalInfo?PERSON_ID=${item.PERSON_ID}" target="navTab" title="信息查看">${item.EMPID}</a>
														<input type="hidden" value="${item.PERSON_ID}" />
													</td>
													<td class='td_left'>${item.LOCAL_NAME}(${item.CHINESE_PINYIN})</td>
													<td>${item.IDCARD_NO} &nbsp;</td>
													<td>${item.SEX}</td>
													<td>
														<fmt:formatDate value="${item.DOB}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.DEPTNAME}</td>
													<td>${item.GRADE_LEVEL}</td>
													
													<td>${item.DUTY_NO}</td>
													<td>${item.GRADE_NO}</td>
													<td>${item.POST_NO}</td>
													<td>
														<fmt:formatDate value="${item.JOIN_COMPANY_DATE}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.IN_THE}</td>
													<td>
													<fmt:formatDate value="${item.NOW_DEPARTMENT_DATE}" pattern="yyyy-MM-dd"/></td>
													<td>
														<fmt:formatDate value="${item.PROMOTION_DATE}" pattern="yyyy-MM-dd"/>
													</td>
													<td>${item.DATE_LEFT}</td>
                                                     <td>
                                                     <c:if test="${item.WORK_YEAR ne 0 }">${item.WORK_YEAR}<spring:message code="liang.hr.viewWorkInfo.title.YEAR" /></c:if>
                                                     ${item.WORK_MONTH}<spring:message code="hr.viewPersonalInfo.title.WORKINFO_MONTH" />
                                                      </td>
													<td>${item.EMPLOYMENTTYPE}</td>
													<td>${item.WORKTIMETYPE}</td>
													<td>${item.CONTRACTTYPE}</td>
													<td>${item.TYPECODE}</td>
												</tr>	
											
											</c:forEach>
											</tbody>
	</table>
	
			
			<c:set value="/hrm/informationRetrieval/viewEmployeeList" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
