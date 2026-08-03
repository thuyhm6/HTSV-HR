<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
	function checkPersonStatusInfo(){
		 var $form = $("#viewPersonalStatusInfo");
	     var deptno = $form.find("#seach_DEPTNO_STATUS").val();
	     if(deptno=='' || deptno==null){
	    	//alert("部门为必选项，请选择部门！");
	 		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
	 		$form.find("#seach_DEPTNO_STATUS").focus();
	 		return false;
	     }
	     return true;
	 }
	 function  doPersonStatusInfoExport(a){
	      var $this=$(a);
	      var title = $this.attr("title"); 
	      var $from = $("#viewPersonalStatusInfo");  
	      
		  var url ="/report/hrc01/viewPersonalStatusInfoExcel";
		  if(checkPersonStatusInfo(a)==true){
			   alertMsg.confirm(title, {
							okCall: function(){  
							   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
							}});
		  }
	    
	 }
	 function doExportEmpChildDeptCheck(){
		if(!document.getElementById('do_EXPORT_EMP_STATUS').checked){
			document.getElementById('seach_EXPORT_EMP_STATUS').value = 'NO';
		}else if(document.getElementById('do_EXPORT_EMP_STATUS').checked){
			document.getElementById('seach_EXPORT_EMP_STATUS').value = 'YES';
		}
	 }
</script>
<div class="pageHeader" >
	<form id="viewPersonalStatusInfo" onsubmit="return navTabSearch(this);" action="/report/hrc01/viewPersonalStatusInfo" method="post" rel="pagerForm">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td width="5%" style="text-align:right"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>：
				</td>
				<td width="12%" style="text-align:center">
					<ait:deptTree name="seach_DEPTNO_STATUS" limit="hr" selected="${DEPTNO_STATUS }"/>
					<font color="red">*</font>
				</td>
				<td width="8%">
					<input type="checkbox" id="do_EXPORT_EMP_STATUS" name="do_EXPORT_EMP_STATUS"
						<c:if test="${EXPORT_EMP_STATUS eq 'YES'}">checked="checked"</c:if> onclick="doExportEmpChildDeptCheck();"/>
						<spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/>
					<input id="seach_EXPORT_EMP_STATUS" name="seach_EXPORT_EMP_STATUS" type="hidden" value="${EXPORT_EMP_STATUS }">					
				</td>
				<td width="5%" style="text-align:right"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="8%" style="text-align:center">
				    <input type="text" id="seach_KEY_STATUS" name="seach_KEY_STATUS" maxlength="25" size="8" value="${KEY_STATUS }">
				</td>
				<td width="5%" style="text-align:right"><!--在职区分：-->
					<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/>：
				</td>
				<td width="5%" style="text-align:center">	
					<ait:selectSyCode name="seach_EMP_OFFICE_STATUS" parentNo="15118" limit="all" selected="${EMP_OFFICE_STATUS}"/>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul>
			 	<li>
			 		<div class="buttonActive"><div class="buttonContent"><button type="submit">
			 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div>
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
				<a class="add" onclick="doPersonStatusInfoExport(this)" title="<spring:message code='rp.report.title.exportYN'/>">
					<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="145%" layoutH="157">
		<thead>
			<tr>
				<th width="2%"><!--序号-->
					<spring:message code="pa.insurance.title.orderNo"/>
				</th>
				<th width="6%"><%--部门区分--%>
					<spring:message code="ess.trans.title.distinctDeptName"/>
				</th>
				<th width="4%"><%--部门--%>
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="4%"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="4%"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				
				<th width="3%"><%--员工状态--%>
					<spring:message code="ess.trans.title.employeeStatus"/>
				</th>
				<th width="4%"><%--员工类型--%>
					<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
				</th>
				<th width="3%"><%--在职区分--%>
					<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/>
				</th>
				<th width="4%"><%--职级(GGS)--%>
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
				</th>
				<th width="5%"><%--职级名称(职务) --%>
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				
				<th width="4%"><%--职责--%>
					<spring:message code="sys.affirm.title.duty"/>
				</th>
				<th width="4%"><%--职(岗)位--%>
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="8%"><%--身份证号--%>
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="5%"><%--出生日期--%>
					<spring:message code="main.home.message.chushengriqi"/>
				</th>
				<th width="3%"><%--性别--%>
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="5%"><%--集团入职日期--%>
					<spring:message code="hr.viewPersonalInfo.title.JOIN_BLOC_DATE"/>
				</th>
				<th width="6%"><%--子公司入司日期--%>
					<spring:message code="hr.viewPersonalInfo.title.JOIN_COMPANY_DATE"/>
				</th>
				<th width="5%"><%--离职日期--%>
					<spring:message code="hr.viewPromote.title.RESIGN_DATE"/>
				</th>
				<th width="6%"><%--现部门异动日期--%>
					<spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE"/>
				</th>
				<th width="5%"><%--试用结束日--%>
					<spring:message code="hr.viewPersonalInfo.title.PROBATION_FINISH_DATE"/>
				</th>
				
				<th width="5%"><%--采用路径--%>
					<spring:message code="hr.viewPersonalInfo.title.RECRUITMENT_SOURCE_NAME"/>
				</th>
				<th width="5%"><%--详细采用路径--%>
					<spring:message code="hr.viewPersonalInfo.title.REC_SOURCE_DETAIL"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personStatusInfoList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="2%">${i.index+1}</td>
					<td width="6%">${item.DISTINGUISH_DEPT }</td>
					<td width="4%">${item.DEPARTMENT }</td>
					<td width="4%">${item.EMPID }</td>
					<td width="4%">${item.LOCAL_NAME }</td>
					
					<td width="3%">${item.STATUS }</td>
					<td width="4%">${item.EMP_TYPE }</td>
					<td width="3%">${item.EMP_OFFICE }</td>
					<%-- 
					<c:if test="${item.EMP_OFFICE_CODE eq '15119'}">
						<font color="green">
							<td width="3%">${item.EMP_OFFICE }</td>
						</font>
					</c:if>
					<c:if test="${item.EMP_OFFICE_CODE eq '15120'}">
						<font color="red">
							<td width="3%">${item.EMP_OFFICE }</td>
						</font>
					</c:if>
					--%>
					<td width="4%">${item.POST_GRADE }</td>
					<td width="5%">${item.POST }</td>
					
					<td width="4%">${item.DUTY }</td>
					<td width="4%">${item.POSITION }</td>
					<td width="8%">${item.IDCARD_NO }</td>
					<td width="5%">${item.DOB }</td>
					<td width="3%">${item.SEX }</td>
					
					<td width="5%">${item.JOIN_BLOC_DATE }</td>
					<td width="6%">${item.JOIN_COMPANY_DATE }</td>
					<td width="5%">${item.DATE_LEFT }</td>
					<td width="6%">${item.NOW_DEPARTMENT_DATE }</td>
					<td width="5%">${item.BEFORE_END_PROBATION_DATE }</td>
					
					<td width="5%">${item.RECRUITMENT_SOURCE_TYPE }</td>
					<td width="5%">${item.REC_SOURCE_DETAIL_NO }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/report/hrc01/viewPersonalStatusInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
