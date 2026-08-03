<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<SCRIPT type='text/javascript'>	
 
</SCRIPT>
<div>
			<dl>
			 	<dt><spring:message code="sys.affirm.title.affirmLevel"/><!--决裁等级-->:</dt>
				<dd>
					<input type="hidden" id="CodeNo" name="CODE_NOS"  class="required textInput"   />
						<table width="600" border="1" cellspacing="1" cellpadding="0" class="user_table" style="background-color:white" id="empTb"> 
								<tr height="25">
									<td class="td_title" style="text-align: center"><spring:message code="public.title.empId"/><!--工号--></td>
									<td class="td_title" style="text-align: center"><spring:message code="public.title.name"/><!--姓名--></td>
									<!--<td class="td_title" style="text-align: center">拼音</td>  -->
									<td class="td_title" style="text-align: center"><spring:message code="public.title.deptName"/><!--部门--></td>
									<td class="td_title" style="text-align: center"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--> </td>
									<td class="td_title" style="text-align: center"><spring:message code="sys.affirm.title.affirmOperation"/><!--操作--> </td>
							    </tr>
					         <c:if test="${fn:length(empList)==0}">
								 <tr onclick="hiddenDIV('');"  height="25" id="noEmp">
									<td colspan="5"  align="center"><spring:message code="sys.affirm.title.noSearchPerson"/><!--没有你要查询的人员！--></td>
								 </tr>
					         </c:if>
								
							<c:forEach items="${empList}" var="employee" varStatus="i">
								<tr  height="25">
									<td style="text-align: center">${employee.EMPID}
										<input type="hidden" name="PERSON_IDS" value="${employee.PERSON_ID}"/>
									</td>
									<td style="text-align: center">${employee.LOCAL_NAME}</td>
									<!--<td style="text-align: center">${employee.CHINESE_PINYIN}</td>-->
									<td style="text-align: center">${employee.DEPTNAME}</td>
									<td style="text-align: center">${employee.AFFIRM_LEVEL}</td>
									<td style="text-align: center">
										<span onclick="upper(this);"><img src="/resources/images/button/up.gif" style="cursor:hand"/></span>  &nbsp;&nbsp;&nbsp;&nbsp;
										<span onclick="moveDown(this);"><img src="/resources/images/button/down.gif" style="cursor:hand"/></span> &nbsp;&nbsp;&nbsp;&nbsp;
										<span onclick="delRow(this);"><img src="/resources/images/0.gif" style="cursor:hand"/></span> 
									</td>
								 </tr>
							</c:forEach>
						</table> 
				</dd>
			</dl>
							 
</div>
