<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
// function newCode(){
// 	$("#newCode_001").attr("href","/pa/salarycode/newSalaryCodeAffirmView");
// 	$("#newCode_001").click();
// }
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/sys/attendancesetting/viewAttendItemCheckList" method="post" rel="pagerForm">
		<div class="searchBar">
		
		
			<table class="searchContent">
				<tr>
				     <td>法人
				</td>
			<td>
				<c:if test="${authority eq '1'}">
				
				<select id="seach_CPNY_ID" name="defaultCpny" onchange="reloadPage();">
				    <option value="">全部</option>
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
				${defaultCpny}
					<input type="hidden" id="seach_CPNY_ID" name="seach_defaultCpny" value="${defaultCpny}"/>
						</c:if>
	   			 </td>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value="">
						</option>
						<option value="明细项目" <c:if test="${TYPE eq '明细项目'}">selected</c:if>>
							<spring:message code="ar.attenditem.title.mingxixiangmu"/>
						</option>
						<option value="汇总项目" <c:if test="${TYPE eq '汇总项目'}">selected</c:if>>
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/>
						</option>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
					<td>
						<spring:message code="ar.viewcycle.title.zhuangtai"/>
						<select id="seach_CPNY_FLAG" name="seach_CPNY_FLAG" value="${CPNY_FLAG}"> 
						<option value="">
						</option>
						<option value="1" <c:if test="${CPNY_FLAG==1 || CPNY_FLAG eq '1'}">selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.able"/>
						</option>
						<option value="0" <c:if test="${CPNY_FLAG==0 || CPNY_FLAG eq '0'}">selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.enable"/>
						</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
<!-- 				    <li> -->
<!-- 						<a class="buttonActive" -->
<!-- 							onclick="newCode();"><span>新增代码<spring:message code="pa.salary.title.newdm"/></span> -->
<!-- 						</a> -->
<!-- 						</li> -->
<!-- 				    <li> -->
<!-- 						<a class="buttonActive" -->
<!-- 							onclick="addempshiftview_sub('addempshiftview','navTabAjaxDone');"><span>启用代码<spring:message code="sys.arAffirmPost.title.able"/></span> -->
<!-- 						</a> -->
<!-- 						</li> -->
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		
<div class="pageContent">
	<c:set value="/sys/attendancesetting/addAttendItemAffirmView" var="add_Url" /> 
 	<c:set value="600" var="add_width" /> 
 	<c:set value="500" var="add_height" />
    <c:set value="新增代码申请" var="add_name" />
 	<c:set value="/sys/attendancesetting/addAttendItemAffirmInfoView?NO={ITEM_NO}" 
 		var="edit_Url" /> 
 	<c:set value="600" var="edit_width" /> 
 	<c:set value="500" var="edit_height" /> 
 	<c:set value="代码启用申请" var="edit_name" /> 
 	<%@ include file="/WEB-INF/view/inc/includeButton2.jsp"%>
 	</form>
</div>	

 	 
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="10%"><spring:message
						code="sys.affirm.indexNum" />
					<!--序号--></th>
			    <th width="30%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				
				<th width="40%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				
				<th width="20%">
					<spring:message code="ar.viewcycle.title.zhuangtai" /><!--状态-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${attendItemList}" var="item" varStatus="i">
				<tr target="ITEM_NO" rel="${item.ITEM_NO}&PROJECT_TYPE=${item.PROJECT_TYPE eq '明细项目' ? 1 : 2}">
				    <td>${i.index + 1}&nbsp;</td>
				    <td >
						${item.PROJECT_TYPE}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						    <c:if test="${ item.CPNY_FLAG == 1 || item.CPNY_FLAG > 0}">
						    <spring:message code="sys.arAffirmPost.title.able"/><!--启用--></c:if>
						    <c:if test="${item.CPNY_FLAG == 0}">
						    <spring:message code="sys.arAffirmPost.title.enable"/><!--未启用--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/sys/attendancesetting/viewAttendItemCheckList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>