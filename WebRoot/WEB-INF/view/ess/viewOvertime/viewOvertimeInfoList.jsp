<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
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
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/viewApply/viewOvertimeInfoList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_JUECAI_ZHUANGTAI="+seach_JUECAI_ZHUANGTAI+"&seach_QUEREN_ZHUANGTAI="+seach_QUEREN_ZHUANGTAI);
}




	function doAddOvertime(){
		//window.location="/ess/viewOvertime/addOvertimeInfo";
		var form = document.getElementById("pageForm");
		form.action="/ess/viewOvertime/addOvertimeInfo";
		form.submit();
		
	
	
	
	}





</script>
<div class="pageHeader">
	<form id="pageForm" onsubmit="return navTabSearch(this);" action="/ess/viewOvertime/viewOvertimeInfoList" rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>
				</td>
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
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
			</tr>
			<tr>
				<td><!-- 加班类型 -->
					<spring:message code="ess.viewApply.title.overtimeApplyType"/>:
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" selected="${APPLY_TYPE_CODE}" limit="all"/>       
				</td>				               			
				<td><!-- 决裁状态 -->
					 <spring:message code="ess.viewApply.title.affirmStatus"/>:
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
				
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					
				    
				    
				    <a class="button" href="/ess/viewOvertime/addOvertimeInfo" target="dialog" rel="dlg_page8">
				    <span><spring:message code="ess.viewOvertimeInfo.shenqingjiaban"/><!-- 申请 --></span>
				    </a>
			    </li>
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
	<table class="table" width="100%" layoutH="144" nowrapTD="false">
		<thead>
			<tr>
				<th width="60" style="text-align: center"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="100" style="text-align: center"><!--姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="140" style="text-align: center"><!--部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="140" style="text-align: center"><!--职岗位-->
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="140" style="text-align: center"><!--职级名称（职务）-->
					<spring:message code="public.title.postName"/>
				</th>
				
				<th width="100" style="text-align: center"><!--加班类型-->
					<spring:message code="ess.viewApply.title.overtimeApplyType"/>
				</th>
				<th width="200" style="text-align: center"><!--加班时段-->
					<spring:message code="ess.viewApply.title.overtimeShift"/>
				</th>
				<th width="80" style="text-align: center"><!--扣除时间-->
					<spring:message code="ar.viewshift.title.kouchushijian"/>
				</th>
				<th width="80" style="text-align: center"><!--长度(小时)-->
					<spring:message code="ess.viewApply.title.lengthHough"/>
				</th>
				<th width="140" style="text-align: center"><!--加班内容-->
					<spring:message code="ess.viewApply.title.overtimeContent"/>
				</th>
				
				<th width="140" style="text-align: center"><!--决裁情况-->
					<spring:message code="ess.viewApply.title.affirmCondition"/>
				</th>
				<th width="100" style="text-align: center"><!--人事确认-->
					<spring:message code="ess.viewApply.title.humanAffirm"/>
				</th>
				<th width="80" style="text-align: center"><!--删除-->
					<spring:message code="button.delete"/>
				</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personOtApplyList}" var="personOtApply" varStatus="i">			
				<tr target="sid" rel="${personOtApply.PERSON_ID}">
				    <td style="text-align: center">${personOtApply.EMPID}</td>	
					<td style="text-align: center">${personOtApply.LOCAL_NAME}</td>
					<td style="text-align: center">${personOtApply.DEPT_NAME}</td>
					<td style="text-align: center">${personOtApply.POST_NAME}</td>
					<td style="text-align: center">${personOtApply.POSITION_NAME}</td>
					
					<td style="text-align: center">${personOtApply.APPLY_OT_TYPE_NAME}</td>
					<td style="text-align: center">
					    ${personOtApply.OT_FROM_TIME}<br>
					    ${personOtApply.OT_TO_TIME}
					</td>
					<td style="text-align: center">${personOtApply.OT_DEDUCT_TIME}</td>
					<td style="text-align: center">
						<fmt:formatNumber value="${personOtApply.OT_LENGTH}" pattern="#,##0.00"/>
					</td>
					<td style="text-align: center">
						
				       <a rel="overtimeApplyFullDescpView" href="/ess/viewApply/viewFullApplyInfo?APPLY_REMARK=${abcd}"  
				          target="dialog" mask="true" width="300" height="300" id="overtimeApplyFullDescpViewHref" >${personOtApply.INTRO}</a>				    
					</td>
					
					<td style="text-align: center">
                        <c:forEach items="${personOtApply.affirmerList}" var="affirmer" varStatus="i">					  					           
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
					<td style="text-align: center">
						 <c:if test="${personOtApply.ACTIVITY==1}" >			                            
	                        &nbsp;<spring:message code="ess.viewApply.title.confirmedPass"/><!--人事确认已通过--> 
	                     </c:if> 
	                     <c:if test="${personOtApply.ACTIVITY==2}" >         
	                        &nbsp;<spring:message code="ess.viewApply.title.confirmedReject"/><!--人事确认未通过-->
						 </c:if>
	                     <c:if test="${personOtApply.ACTIVITY==0}" >         
							&nbsp;<spring:message code="ess.viewApply.title.notConfirmed"/><!--人事未确认--> 
						 </c:if>					     
					</td>
					<td style="text-align: center">
					    <c:if test="${personOtApply.ACTIVITY eq 0 && personOtApply.AFFIRM_COUNT eq 0 }">
	                        <a href="/ess/viewApply/delViewOtviewApply?APPLY_NO=${personOtApply.APPLY_NO}" target="ajaxTodo">
	                           <span><img src="/resources/images/button/Delete_little.gif"></span>
							</a>
						</c:if>
                    </td>				
				</tr>			
			</c:forEach>
		</tbody>
	</table>
    <div id="overtimeApplyFullDescpView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <c:set value="/ess/viewApply/viewOvertimeInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>