<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function refreshCodeList(){
	 $.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: "/sys/basicMaintenance/refreshData",//请求的action路径  
	        error: function () {//请求失败处理函数  
	            alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
	        },  
	        success:function(data){ //请求成功后处理函数。  
	            alertMsg.error('<spring:message code="alert.message.sys.basicMain.refreshDataSuccess"/>');  
	            //把后台封装好的简单Json格式赋给treeNodes
	        }  
	    }); 
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/basicMaintenance/viewCodePamers" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.basic.title.codeName"/><!--代码名称-->：
					<input type="text" name="seach_CODE_NAME" value="${CODE_NAME}"/>
				</td>
				<td>
					<spring:message code="sys.basic.title.companyName"/><!--公司名称-->：
					<input type="text" name="seach_CPNY_NAME" value="${CPNY_NAME}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
				<button type="button" onclick="refreshCodeList();">
				<spring:message code="sys.basic.title.refreshCode"/><!--刷      新--></button>
				</div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	 
	<c:set value="navTab" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="指定法人" var="add_name"/>
	<c:set value="/sys/basicMaintenance/editCodeParamView" var="add_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="90"><spring:message code="sys.basic.title.codeName"/><!--代码名称--></th>
				<th width="100"><spring:message code="sys.basic.title.companyName"/><!--公司名称--></th>
				<th width="120"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
				<th width="80"><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
				<th width="45"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cParmersLists}" var="codeP" varStatus="i">
			
				<tr target="cdid" rel="${codeP.PARAM_NO}&CPNY_ID=${codeP.CPNY_ID}&CODE_NO=${codeP.CODE_NO}">
					<td>${codeP.CODE_NAME_ZH}</td>
					<td>${codeP.CPNY_NAME_ZH}</td>
					<td>${codeP.CHINESE_NAME}</td>
					<td>${codeP.CREATE_DATE}</td>
					<td>${codeP.ACTIVATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/sys/basicMaintenance/viewCodePamers" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>