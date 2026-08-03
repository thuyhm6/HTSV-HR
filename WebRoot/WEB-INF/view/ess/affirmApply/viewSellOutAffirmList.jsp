<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"  action="/ess/affirmApply/viewSellOutAffirmList" 
	  method="post"   rel="pagerForm" name="viewSellOutAffirmInfo" id="viewSellOutAffirmInfo">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>
				</td>
				<td> 
					 <input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>
				</td>	
				<td>
				  <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
               <td width="10%"><!-- 决裁状态 -->
					<spring:message code="ess.viewApply.title.affirmStatus"/>:
				</td>				
				<td width="15%">
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="">全部</option>
						 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未决裁</option>
						 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
						 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
					 </select>       
				</td>	
			</tr>
			<tr>
				<td><!-- 开始日期 -->
				     <spring:message code="public.title.startDate"/>:
				</td>
				<td>
					<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
					<a class="inputDateButton"><!-- 选择 -->
				      	<spring:message code="public.title.choose"/>
					</a>
				</td>
				<td><!-- 结束日期 -->
				     <spring:message code="public.title.endDate"/>:
				</td>				
				<td>
					<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
		           	<a class="inputDateButton"><!-- 选择 -->
                    	<spring:message code="public.title.choose"/>
					</a>
				</td>  				               			
				<td>
				</td>					
				<td>
				</td>
				
			</tr>
		</table>
		<div class="subBar">
			 <ul>
			 	<li>
			 		<div class="buttonActive">
						<div class="buttonContent">
						    <button type="submit">
						       <spring:message code="public.title.search"/><!-- 检索 -->
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
<form name="sellOutAffirmListForm" id="sellOutAffirmListForm">    
	<table class="table" width="100%" height="80%" layoutH="231" nowrapTD="false">
		<thead>
			<tr>
			    <th>申请类容</th>
			    <th>大区</th>
			    <th>支社</th>
			    <th>月份</th>
				<th>申请人</th>
				<th>申请日期</th>
				<th>申请部门</th>
				<th>审批情况</th>
				<th>决裁</th>				
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${itemList}" var="itemList" varStatus="i">			
				 <tr target="sid" rel="${itemList.REQ_ID}">
				    <td class="td_center">${itemList.REQ_TITLE}</td>
				    <td class="td_center">${itemList.PAY_AREA_NM}</td>
				    <td class="td_center">${itemList.BRANCH_NM}</td>
				    <td class="td_center">${itemList.REQ_DATE}</td>
					<td class="td_center">${itemList.LOCAL_NAME}</td>
					<td class="td_center">${itemList.REQ_DAY}</td>
					<td class="td_center">${itemList.DEPT_NAME}</td>
					<td class="td_center">
						<c:if test="${itemList.MY_AFFIRM_FLAG==0}" >
						    <font color="blue">未决裁</font>
						</c:if>	
						<c:if test="${itemList.MY_AFFIRM_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${itemList.MY_AFFIRM_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>
					</td>
					<td class="td_center">
					  <c:if test="${itemList.MY_AFFIRM_FLAG eq 0}">
						<a class="add" href="/ess/affirmApply/viewSellOutAffirmInfo?REQ_ID=${itemList.REQ_ID}"  title="审批"
								rel="ess0520_affirm"  target="navTab"><font color="red">审批</font></a>
					  </c:if>
					  <c:if test="${itemList.MY_AFFIRM_FLAG ne 0}" >
						<a class="add" href="/ess/affirmApply/viewSellOutAffirmInfo?REQ_ID=${itemList.REQ_ID}"  title="审批查看"
								rel="ess0520_affirm"  target="navTab"><font color="red">审批查看</font></a>
					  </c:if>
				     </td>
				 </tr>
			</c:forEach>			
		</tbody>
	</table>	
	</form>
    <div id="viewAnnualadjustmentInfo" style="border:10;overflow:auto;bottom:10;right:500;width:400;z-index:1;"></div>
    <c:set value="/ess/affirmApply/viewSellOutAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>



