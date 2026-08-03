<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
	function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_JUECAI_ZHUANGTAI=$("#seach_JUECAI_ZHUANGTAI",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_JUECAI_ZHUANGTAI",navTab.getCurrentPanel()).val();
	var seach_QUEREN_ZHUANGTAI=$("#seach_QUEREN_ZHUANGTAI",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_QUEREN_ZHUANGTAI",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/viewApply/viewEvectionInfoList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_JUECAI_ZHUANGTAI="+seach_JUECAI_ZHUANGTAI+"&seach_QUEREN_ZHUANGTAI="+seach_QUEREN_ZHUANGTAI);
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/viewApply/viewEvectionInfoList"
	      rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.deptName"/><!-- 部门 -->:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->:
				</td>
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
				     <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
				</td>
				<td>
					<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
				           <a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>
				     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
				</td>				
				<td>
					<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
				           <a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="ess.viewApply.title.evectionApplyType"/><!-- 出差类型 -->:
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="18" cnpyID="${defaultCpny}" selected="${APPLY_TYPE_CODE}" limit="all"/>       
				</td>				               			
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>					
				<td>
					<%--
				    <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
					 --%>
					 <select id="seach_JUECAI_ZHUANGTAI" name="seach_JUECAI_ZHUANGTAI">
						 <option value="">全部</option>
						 <option value="1" <c:if test="${JUECAI_ZHUANGTAI eq '1'}">selected</c:if>>已通过</option>
						 <option value="2" <c:if test="${JUECAI_ZHUANGTAI eq '2'}">selected</c:if>>已否决</option>
						 <option value="0" <c:if test="${JUECAI_ZHUANGTAI eq '0'}">selected</c:if>>未决裁</option>
					 </select>
				</td>
				<td>
					 确认状态:
				</td>					
				<td>
					<%--
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
					 --%>
					 <select id="seach_QUEREN_ZHUANGTAI" name="seach_QUEREN_ZHUANGTAI">
						 <option value="">全部</option>
						 <option value="1" <c:if test="${QUEREN_ZHUANGTAI eq '1'}">selected</c:if>>已通过</option>
						 <option value="2" <c:if test="${QUEREN_ZHUANGTAI eq '2'}">selected</c:if>>已否决</option>
						 <option value="0" <c:if test="${QUEREN_ZHUANGTAI eq '0'}">selected</c:if>>未确认</option>
					 </select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >

	<table class="table" width="100%" layoutH="138" nowrapTD="false">
		<thead>
			<tr>
				<th width="60"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="140"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="140"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="160"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th>
				<th width="100"><spring:message code="ess.viewApply.title.evectionApplyType"/><!--出差类型--></th>
				<th width="220"><spring:message code="ess.viewApply.title.evectionShift"/><!--出差时段--></th>
				<th width="80"><spring:message code="ess.viewApply.title.length"/><!--长度--></th>
				<th width="100"><spring:message code="ess.viewApply.title.evectionReason"/><!--出差原由--></th>
				<th width="200"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="100"><spring:message code="ess.viewApply.title.humanAffirm"/><!--人事确认--></th>
				<th width="100"><spring:message code="button.delete"/><!--删除--></th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personEvectionApplyList}" var="personEvectionApply" varStatus="i">			
				<tr target="sid" rel="${personEvectionApply.PERSON_ID}">
				    <td>${personEvectionApply.EMPID}</td>	
					<td>${personEvectionApply.LOCAL_NAME}</td>
					<td>${personEvectionApply.DEPT_NAME}</td>
					<td>${personEvectionApply.POST_NAME}</td>
					<td>${personEvectionApply.POSITION_NAME}</td>
					<td>${personEvectionApply.EVECTION_TYPE_NAME}</td>
					<td>
					    ${personEvectionApply.LEAVE_FROM_TIME}<br>
					    ${personEvectionApply.LEAVE_TO_TIME}                    
                    </td>
					<td><fmt:formatNumber value="${personEvectionApply.LEAVE_LENGTH}" pattern="#,##0.00"/></td>
					<td>
				       <a rel="evectionApplyFullDescpView" href="/ess/viewApply/viewFullApplyInfo?APPLY_REMARK=${personEvectionApply.LEAVE_REASON}"  
				          target="dialog" mask="true" width="300" height="300" id="evectionApplyFullDescpViewHref" >${personEvectionApply.INTRO}</a>				    								    
					</td>
					<td>
                        <c:forEach items="${personEvectionApply.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME}
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                              &nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过-->  
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                              &nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                              &nbsp;<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁--> 
								 </c:if>								 
							</dt>
						</c:forEach>
					</td>	
                    <td>
                   		 <c:if test="${personEvectionApply.ACTIVITY==1}" >			                            
	                           &nbsp;<spring:message code="ess.viewApply.title.confirmedPass"/><!--人事确认已通过--> 
	                     </c:if> 
	                     <c:if test="${personEvectionApply.ACTIVITY==2}" >         
	                           &nbsp;<spring:message code="ess.viewApply.title.confirmedReject"/><!--人事确认未通过-->
						 </c:if>
	                     <c:if test="${personEvectionApply.ACTIVITY==0}" >         
	                           &nbsp;<spring:message code="ess.viewApply.title.notConfirmed"/><!--人事未确认--> 
						 </c:if>	
                    </td>	
                    <td>
                        <c:if test="${personEvectionApply.ACTIVITY eq 0 && personEvectionApply.AFFIRM_COUNT eq 0 }">
	                        <a href="/ess/viewApply/delViewEvectionview?APPLY_NO=${personEvectionApply.APPLY_NO}" target="ajaxTodo">
	                           <span><img src="/resources/images/button/Delete_little.gif"></span>
							</a>
						</c:if>
                    </td>					
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	  <c:set value="/ess/viewApply/viewEvectionInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	<div id="evectionApplyFullDescpView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
</div>