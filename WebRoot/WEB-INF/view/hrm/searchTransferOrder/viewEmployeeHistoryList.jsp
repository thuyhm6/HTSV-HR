<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">

 	<table class="table" width="120%" layoutH="30" nowrapTD="false">      
		<thead>
			<tr>
				<th width="80"><spring:message code="hrm.empinfo.empid" /></th>
				<th width="80">姓名</th>
				<th width="100">原部门</th>
				<th width="80">原职(岗)位</th>
				<th width="140">原职级名称(职务)</th>
			    <th width="40">原职级</th>
			    <th width="100">原职责</th>
			    <th width="80">原工作地</th>
				<th width="100">部门</th>
				<th width="80">职(岗)位</th>
				<th width="120">职级名称(职务)</th>
			    <th width="40">职级</th>		
			    <th width="100">职责</th>		    
				<th width="80">工作地</th>
				<th width="160">调动类型</th>	
				<th width="140">生效日期</th>	
				<th width="180">决裁情况</th>
				<th width="80">操作者</th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrExperienceInsideList}" var="hrExpInside" varStatus="i">			
				<tr target="sid" rel="${hrExpInside.EXP_INSIDE_NO}">
					<td>${hrExpInside.EMPID}</td>
					<td>${hrExpInside.LOCAL_NAME}</td>
					<td>${hrExpInside.EMP_DEPT_NAME}</td>
					<td>${hrExpInside.EMP_POSITION_NAME}</td>
					<td>${hrExpInside.EMP_POST_NAME}</td>
					<td>${hrExpInside.EMP_POST_GRADE_NAME}</td>
					<td>${hrExpInside.EMP_DUTY_NAME}</td>
					<td>${hrExpInside.EMP_WORK_AR_NAME}</td>
					<td>${hrExpInside.NEW_DEPT_NAME}</td>
					<td>${hrExpInside.NEW_POSITION_NAME}</td>
					<td>${hrExpInside.NEW_POST_NAME}</td>
					<td>${hrExpInside.NEW_POST_GRADE_NAME}</td>
					<td>${hrExpInside.NEW_DUTY_NAME}</td>
					<td>${hrExpInside.NEW_WORK_AR_NAME}</td>
					<td>${hrExpInside.TRANS_TYPE_NAME}</td>
					<td>${hrExpInside.START_DATE}</td>
					<td>
					    <c:forEach items="${hrExpInside.affirmerList}" var="affirmer" varStatus="i">					  					           
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
					<td>${hrExpInside.CREATED_BY_NAME}</td>														                                          
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</div>
