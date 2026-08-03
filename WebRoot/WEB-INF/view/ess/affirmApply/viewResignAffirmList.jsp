<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewResignAffirmList" method="post" 
	      name="searchResignAffirmListForm" id="searchResignAffirmListForm" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResignAffirmListForm_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResignAffirmListForm_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResignAffirmListForm_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResignAffirmListForm_seachDept" selected="${searchMap.DEPTNO}"/>
					</c:if>
				</td>
				<td>申请人社号/姓名
				</td>						
				<td>
					<input type="text" id="seach_REQ_BY" name="seach_REQ_BY" value="${searchMap.REQ_BY}" />
				</td>
				<td>
					申请类型
				</td>					
				<td> 
                    <select name="seach_TRANS_CODE" id="seach_TRANS_CODE">
                        <option value="">请选择</option>
                        <option value="RESIGN" <c:if test="${searchMap.TRANS_CODE == 'RESIGN'}">selected</c:if>>离职</option>
                        <option value="RESIGNREVOKE" <c:if test="${searchMap.TRANS_CODE == 'RESIGNREVOKE'}">selected</c:if>>撤销离职 </option>
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
				<td><!-- 审批状态 -->
					审批状态
				</td>				
				<td>  
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="0" <c:if test="${searchMap.AFFIRM_FLAG eq '0'}">selected</c:if>>等待审批</option>
						 <option value="1" <c:if test="${searchMap.AFFIRM_FLAG eq '1'}">selected</c:if>>审批完成</option>
					 </select>       
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
<form name="resignAffirmListForm" id="resignAffirmListForm" method="post" action="" 
	  onsubmit="return validateCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" layoutH="235" nowrapTD="false">
		<thead>
			<tr>
			    <th>申请类型</th>
			    <th>申请内容</th>
				<th>申请人</th>
				<th>申请日期</th>
				<th>申请部门</th>
				<th>审批情况</th>
				<th>操作</th>
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${resignAffirmList}" var="resignList" varStatus="i">			
				<tr target="sid">
					<td>
						<c:if test="${resignList.TRANS_CODE eq 'RESIGN'}"><font >离职</font></c:if>
						<c:if test="${resignList.TRANS_CODE eq 'RESIGNREVOKE'}"><font >离职撤销</font></c:if>
					</td>
				    <td>临时职离职发令审批申请</td>
					<td class='td_center'>[${resignList.REQ_EMPID}]${resignList.REQ_EMPNM}</td>
					<td class='td_center'>${resignList.REQ_DATE}</td>
					<td>${resignList.DEPT_NAME}</td>
					<td class='td_center'>
						<c:if test="${resignList.MY_AFFIRM_FLAG eq '-1'}" >
						    <font color="blue">提交</font>
						</c:if>
						<c:if test="${resignList.MY_AFFIRM_FLAG eq '0'}" >
						    <font color="green">审批中</font>
						</c:if>
						<c:if test="${resignList.MY_AFFIRM_FLAG eq '1'}" >
						    <font color="green">通过</font>
						</c:if>	
						<c:if test="${resignList.MY_AFFIRM_FLAG eq '2'}" >
						    <font color="red">否决</font>
						</c:if>
					</td>
					<td class='td_center'>
					    <c:if test="${(resignList.AFFIRM_FLAG eq '-1' or resignList.AFFIRM_FLAG eq '4') and resignList.MY_AFFIRM_FLAG eq '0'}">
					    	<a class="add" href="/ess/affirmApply/viewResignAffirmDoList?REQ_ID=${resignList.EXP_INSIDE_NO}" title="审批"
								target="dialog" height=500 width=900 rel="ess0253_affirm"><font color="red">审批</font></a>
						</c:if>
					    <c:if test="${(resignList.AFFIRM_FLAG ne '-1' and resignList.AFFIRM_FLAG ne '4') or resignList.MY_AFFIRM_FLAG ne '0'}">
						    <a rel="leaveAffirmRemark" href="/ess/affirmApply/viewResignAffirmDoList?pageNum=1&REQ_ID=${resignList.EXP_INSIDE_NO}" title="查看"
				          		target="dialog" height=500 width=900 rel="ess0253_affirm_info" id="resignAffirmRemark"><font color="red">查看</font></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
<c:set value="/ess/affirmApply/viewResignAffirmList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>