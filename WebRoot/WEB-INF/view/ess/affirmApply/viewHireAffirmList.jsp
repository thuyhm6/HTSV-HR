<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewHireAffirmList" method="post" 
	      name="searchHireAffirmListForm" id="searchHireAffirmListForm" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewHire_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewHire_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewHire_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewHire_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
				</td>
				<td>申请人社号/姓名
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${searchMap.KEY}" />
				</td>
				<td><!-- 审批状态 -->
					审批状态
				</td>				
				<td>  
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="">全部</option>
						 <option value="-1" <c:if test="${searchMap.AFFIRM_FLAG eq '-1'}">selected</c:if>>提交</option>
						 <option value="0" <c:if test="${searchMap.AFFIRM_FLAG eq '0'}">selected</c:if>>审批中</option>
						 <option value="1" <c:if test="${searchMap.AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
						 <option value="2" <c:if test="${searchMap.AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
					 </select>       
				</td>
			</tr>
			<tr>
                <td>申请日期</td>			
			    <td>
			        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${searchMap.FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>~</td>			     
				<td><!--seach_TO_TIME-->
				    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${searchMap.TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
			</tr>
		</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/>
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
	</div>
	</form>
</div>

<div class="pageContent" >
<form name="hireAffirmListForm" id="hireAffirmListForm" method="post" action="" 
	  onsubmit="return validateCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" layoutH="235" nowrapTD="false">
		<thead>
			<tr>
			    <th>申请类容</th>
				<th>申请人</th>
				<th>申请日期</th>
				<th>申请部门</th>
				<th>审批情况</th>
				<th>操作</th>
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hireAffirmList}" var="hireList" varStatus="i">			
				<tr target="sid">
				    <td>临时职入职审批申请</td>
					<td class='td_center'>[${hireList.EMPID}]${hireList.LOCAL_NAME}</td>
					<td class='td_center'>${hireList.REQ_DATE}</td>
					<td>${hireList.DEPT_NAME}</td>
					<td class='td_center'>
						<c:if test="${hireList.MY_AFFIRM_FLAG eq '-1'}" >
						    <font color="blue">提交</font>
						</c:if>
						<c:if test="${hireList.MY_AFFIRM_FLAG eq '0'}" >
						    <font color="green">审批中</font>
						</c:if>
						<c:if test="${hireList.MY_AFFIRM_FLAG eq '1'}" >
						    <font color="green">通过</font>
						</c:if>	
						<c:if test="${hireList.MY_AFFIRM_FLAG eq '2'}" >
						    <font color="red">否决</font>
						</c:if>
					</td>
					<td class='td_center'>
					    <c:if test="${hireList.AFFIRM_FLAG eq '-1' or hireList.MY_AFFIRM_FLAG eq '0'}">
					    	<a class="add" href="/ess/affirmApply/viewHireAffirmDtlList?pageNum=1&REQ_ID=${hireList.REQ_ID}" title="审批"
								target="navTab" rel="ess0252_affirm"><font color="red">审批</font></a>
						</c:if>
					    <c:if test="${hireList.AFFIRM_FLAG ne '-1' and hireList.MY_AFFIRM_FLAG ne '0'}">
						    <a rel="leaveAffirmRemark" href="/ess/affirmApply/viewHireAffirmDtlList?pageNum=1&REQ_ID=${hireList.REQ_ID}" title="查看"
				          		target="navTab" rel="ess0252_affirm_info" id="hireAffirmRemark"><font color="red">查看</font></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
<c:set value="/ess/affirmApply/viewHireAffirmList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>