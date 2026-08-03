<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
 	<table class="table" width="120%" layoutH="30" nowrapTD="false">      
		<thead>
			<tr>
				<th width="80"><spring:message code="hrm.empinfo.empid" /></th>
				<th width="80">姓名</th>
				<th width="100">部门</th>
				<th width="80">职(岗)位</th>
				<th width="100">职级名称(职务)</th>
			    <th width="40">职级</th>
			    <th width="100">职责</th>
			    <th width="80">员工状态</th>
				<th width="100">生效日期</th>
				<th width="80">停职事由</th>
				<th width="80">调动类型</th>
				<th width="80">决裁情况</th>	
				<th width="80">操作者</th>	
				<th width="80">是否生效</th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrSuspendHistoryList}" var="suspend" varStatus="i">			
				<tr target="sid" rel="${suspend.EXP_INSIDE_NO}">
					<td>${suspend.EMPID}</td>
					<td>${suspend.LOCAL_NAME}</td>
					<td>${suspend.DEPTNAME}</td>
					<td>${suspend.POSITION_NAME}</td>
					<td>${suspend.POST_NAME}</td>
					<td>${suspend.POST_GRADE_NAME}</td>
					<td>${suspend.DUTYNAME}</td>
					<td>${suspend.STATUS_NAME}</td>
					<td>${suspend.START_DATE}</td>
					<td>${suspend.SUSPEND_REASON}</td>
					<td>${suspend.TRANS_CODE_NAME}</td>
					<td>
					    <c:forEach items="${suspend.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME}
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                              &nbsp;通过  
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                              &nbsp;否决 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                              &nbsp;未决裁  
								 </c:if>								 
							</dt>
						</c:forEach>	
					</td>			
					<td>${suspend.CREATE_NAME}</td>
					<td>	    
			            <c:if test="${suspend.ACTIVITY_FLAG == 1 }" >
			             <img src="/resources/images/a_1.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${suspend.ACTIVITY_FLAG == 0 }" >
			             <img src="/resources/images/0.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${suspend.ACTIVITY_FLAG == 2 }" >
			                 &nbsp;已取消&nbsp;
			            </c:if>			            
			        </td> 						
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
	<c:set value="/hrm/searchTransferOrder/searchSuspend" var="pageUrl"/>
</div>
