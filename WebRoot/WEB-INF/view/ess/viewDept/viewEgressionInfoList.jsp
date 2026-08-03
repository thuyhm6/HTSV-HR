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
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/viewApply/viewEgressionInfoList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_JUECAI_ZHUANGTAI="+seach_JUECAI_ZHUANGTAI+"&seach_QUEREN_ZHUANGTAI="+seach_QUEREN_ZHUANGTAI);
}
	
	
	function exportExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewArVacationMonth");  
     
     var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/viewApply/viewEgressionInfoList"
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
				       <a class="inputDateButton">
                          <spring:message code="public.title.choose"/><!-- 选择 -->
                       </a>
				</td>
				<td>
				     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
				</td>				
				<td>
					<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
				      <a class="inputDateButton">
                          <spring:message code="public.title.choose"/><!-- 选择 -->
                      </a>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="ess.viewApply.title.egressionApplyType"/><!-- 外出类型 -->:
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="16201" cnpyID="${defaultCpny}" selected="${APPLY_TYPE_CODE}" limit="all"/>       
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
				<li><div class="buttonActive"><div class="buttonContent">
				    <button type="submit">
				     <spring:message code="public.title.search"/><!-- 检索 -->
				    </button>
				    </div></div>
				</li>
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
				<th width="120"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="140"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th>
				<th width="80"><spring:message code="ess.viewApply.title.egressionApplyType"/><!--外出类型--></th>
				<th width="180"><spring:message code="ess.viewApply.title.egressionShift"/><!--外出时段--></th>
				<th width="60"><spring:message code="ess.viewApply.title.length"/><!--长度--></th>
				<th width="80"><spring:message code="ess.viewApply.title.egressionReason"/><!--外出原由--></th>
				<th width="200"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="100"><spring:message code="ess.viewApply.title.humanAffirm"/><!--人事确认--></th>
				<th width="100"><spring:message code="button.delete"/><!--删除--></th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personEgressionApplyList}" var="personEgressionApply" varStatus="i">			
				<tr target="sid" rel="${personEgressionApply.PERSON_ID}">
				    <td>${personEgressionApply.EMPID}</td>	
					<td>${personEgressionApply.LOCAL_NAME}</td>
					<td>${personEgressionApply.DEPT_NAME}</td>
					<td>${personEgressionApply.POST_NAME}</td>
					<td>${personEgressionApply.POSITION_NAME}</td>
					<td>${personEgressionApply.EGRESSION_TYPE_NAME}</td>
					<td>
					    ${personEgressionApply.LEAVE_FROM_TIME}<br>
					    ${personEgressionApply.LEAVE_TO_TIME}                    
                    </td>
					<td><fmt:formatNumber value="${personEgressionApply.LEAVE_LENGTH}" pattern="#,##0.00"/></td>
					<td>
				       <a rel="egressionApplyFullDescpView" href="/ess/viewApply/viewFullApplyInfo?APPLY_REMARK=${personEgressionApply.LEAVE_REASON}"  
				          target="dialog" mask="true" width="300" height="300" id="egressionApplyFullDescpViewHref" >${personEgressionApply.INTRO}</a>				    										    
					</td>
					<td>
                        <c:forEach items="${personEgressionApply.affirmerList}" var="affirmer" varStatus="i">					  					           
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
                   		 <c:if test="${personEgressionApply.ACTIVITY==1}" >			                            
	                           &nbsp;<spring:message code="ess.viewApply.title.confirmedPass"/><!--人事确认已通过--> 
	                     </c:if> 
	                     <c:if test="${personEgressionApply.ACTIVITY==2}" >         
	                           &nbsp;<spring:message code="ess.viewApply.title.confirmedReject"/><!--人事确认未通过-->
						 </c:if>
	                     <c:if test="${personEgressionApply.ACTIVITY==0}" >         
	                           &nbsp;<spring:message code="ess.viewApply.title.notConfirmed"/><!--人事未确认-->
						 </c:if>	
                    </td>		
                    <td>
                        <c:if test="${personEgressionApply.ACTIVITY eq 0 && personEgressionApply.AFFIRM_COUNT eq 0}">
	                        <a href="/ess/viewApply/delViewEgressionview?APPLY_NO=${personEgressionApply.APPLY_NO}" target="ajaxTodo">
	                           <span><img src="/resources/images/button/Delete_little.gif"></span>
							</a>
						</c:if>
                    </td>	
				</tr>			
			</c:forEach>
		</tbody>
    <c:set value="/ess/viewApply/viewEgressionInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</table>
	<div id="egressionApplyFullDescpView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
</div>