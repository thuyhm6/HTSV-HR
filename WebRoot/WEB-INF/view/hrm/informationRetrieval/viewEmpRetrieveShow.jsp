<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <script type="text/javascript">
 function navTabSearch_hrExecl(from)
 {
	 var $this = $(from);
	 var title = "Result";//$this.attr("title") || $this.text();
	 var tabid = "" ;
	 var fresh = eval($this.attr("fresh") || "true");
	 var external = eval($this.attr("external") || "false");
     var data = $this.serializeArray() ;
     var url = $this.attr("action") ;
	 
	 navTab.openTab(tabid, url, {title:title, fresh:fresh, external:external, data:data});

	 return false ;
 }
 function SaveSql()
 {
	 var $this = $("#hr040401");
	 var title = "Save";//$this.attr("title") || $this.text();
	 var tabid = "" ;
	 var fresh = eval($this.attr("fresh") || "true");
	 var external = eval($this.attr("external") || "false");
     var data = $this.serializeArray() ;
     var url = "/hrm/informationRetrieval/viewAddEmpRetrieve";
	 
	 navTab.openTab(tabid, url, {title:title, fresh:fresh, external:external, data:data});

	 return false ;
 }
 
 
 </script>
<div class="pageHeader">
	<form id="hr040401" action="/hrm/informationRetrieval/viewEmpRetrieveShowExecl" method="post" rel="pagerForm">
			<input type="hidden"  id="SqlKey" name="SqlKey" value="${SqlKey}"/>	 
		    <input type="hidden"  id="ColName" name="ColName" value="${ColName}"/>	 
		    <input type="hidden"  id="CondSql" name="CondSql" value="${CondSql}" />
		    <input type="hidden"  id="CondSqlCnt" name="CondSqlCnt" value="${CondSqlCnt}" />
		     <input type="hidden"  id="tablename" name="tablename" value="${tablename}" />
		    <div class="searchBar">
      		<div class="subBar">
      		${tablename}
      		<c:if test="${errorFlag ne '1'}">
				<ul>		
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="SaveSql()"><!-- 保存 --><spring:message code='ar.viewempcalender.title.save'/> </button></div></div></li>			
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></button></div></div></li>
					
				
				</ul>
			</c:if>	
			</div>
			</div>     	
	</form>
</div>
<div class="pageContent">
	 
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			<th width="100"><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
			<th width="100"><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
			<th width="100"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
	  	<c:forEach items="${ColTitle}" var="titleName" varStatus="i">
	  	   <c:if test="${i.count>3}">
			 <th width="100">${titleName}</th>
			 </c:if>
			</c:forEach>
	     
			</tr>
		</thead>
	 	      
        <tbody>
		 	<c:forEach items="${getEmpRetrieveShowList}" var="map" varStatus="y">
				 	<tr>
				<c:forEach items="${newKeys}" var="keys" varStatus="k">
				 <td nowrap="nowrap">${map[keys]}</td>
				</c:forEach>
				    
				</tr>
			</c:forEach>	
	 
	
		</tbody>
	</table>
	
		<c:set value="/hrm/informationRetrieval/viewEmpRetrieveShow" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
