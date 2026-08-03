<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<SCRIPT type='text/javascript'>	
 
</SCRIPT>
<div>
	<div  layoutH="10">			
			<dl>
				<dd>
					<input type="hidden" id="CodeNo" name="CODE_NOS"  class="required textInput"   />
						<table width="300" height="30" border="1" cellspacing="1" cellpadding="0" style="background-color:white"> 
								<tr onclick="hiddenDIVAdd('');">
									<td style="text-align: center"><spring:message code="public.title.empId"/><!--工号--></td>
									<td style="text-align: center"><spring:message code="public.title.name"/><!--姓名--></td>
									<td style="text-align: center"><spring:message code="public.title.deptName"/><!--部门--></td>
								 </tr>
					         <c:if test="${fn:length(empList)==0}">
								 <tr onclick="hiddenDIVAdd('');" id="noEmp">
									<td colspan="3"  align="center"><spring:message code="sys.affirm.title.noSearchPerson"/><!--没有你要查询的人员！--></td>
								 </tr>
					         </c:if>
								
							<c:forEach items="${empList}" var="employee" varStatus="i">
								<tr onclick="hiddenDIVAdd('${employee.PERSON_ID}','${employee.EMPID}','${employee.LOCAL_NAME}','${employee.DEPTNAME}','${employee.CHINESE_PINYIN}');">
									<td style="text-align: center">${employee.EMPID}</td>
									<td style="text-align: center">${employee.LOCAL_NAME}</td>
									<td style="text-align: center">${employee.DEPTNAME}</td>
								 </tr>
							</c:forEach>
						</table> 
				</dd>
			</dl>
	</div>
							 
</div>
