<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function pageFromSea(a){  
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?""
			:$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/ess/infoApply/viewCwaCheckLis?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewCwaCheckList" method="post" 
	      rel="pagerForm" name="viewLeaveCheckList" id="viewLeaveCheckList">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->
				</td>
				<td>
					<ait:deptList name="seach_DEPT_NO" limit="ar" id="viewLeaveCheckList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewLeaveCheckList_seachDept" selected="${DEPT_NO}"/>
				</td>
				<td width="10%"><!-- 决裁状态 -->
					<spring:message code="ess.viewApply.title.affirmStatus"/>
				</td>				
				<td width="15%">  
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="">全部</option>
						 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>未提交</option>
						 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未决裁</option>
						 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
						 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
						 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>已取消</option>
						 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>决裁中</option>
					 </select>       
				</td>			
			</tr>
			<tr>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->
                </td>			
			    <td><!--seach_FROM_TIME-->
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->
                </td>			     
				<td><!--seach_TO_TIME-->
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
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
	<table class="table" width="100%" height="80%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="180"><spring:message code="public.title.deptName"/><!--部门--></th>
                <th width="80">申请人</th>
				<th width="120">考勤日期 <!-- 是否批量 --></th>
				<th width="160">进门打卡时间</th>
				<th width="160">出门打卡时间</th>				
				<th width="80">申请类型</th>				
				<th width="160">申请事由</th>	
				<th width="80"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="80" style="text-align: center"><!--Type-->
					Type
				</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${leaveCheckList}" var="leaveCheck" varStatus="i">			
				<tr target="sid">
					<td style="text-align: center">${leaveCheck.EMPID}</td>
					<td style="text-align: center">${leaveCheck.LOCAL_NAME}</td>
					<td style="text-align: center">${leaveCheck.DEPT_NAME}</td>
					<td style="text-align: center">${leaveCheck.CREATE_NAME}</td>
					<td style="text-align: center">
					${leaveCheck.AR_DATE_STR}
					<!-- 
						<c:if test="${leaveCheck.GERENORPILIANG eq 'GEREN' }">
							个人
						</c:if>
						<c:if test="${leaveCheck.GERENORPILIANG eq 'PILIANG' }">
							批量
						</c:if>
						 -->
					</td>
                    <td style="text-align: center"><fmt:formatDate value="${leaveCheck.FROM_TIME}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
                    <td style="text-align: center"><fmt:formatDate value="${leaveCheck.TO_TIME}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
                    <td style="text-align: center">${leaveCheck.YICHANGTYPENAME}</td>
					<td style="text-align: center">${leaveCheck.APPLY_REASON}</td>
					<td style="text-align: center">
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==-1}" >
						    <font color="grey">未提交</font>
						</c:if>
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==0}" >
						    <font color="blue">未决裁</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==3}" >
						    <font color="back">已取消</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==4}" >
						    <font color="green">决裁中</font>
						</c:if>		
					</td>
					<td style="text-align: center">
					    <c:if test="${leaveCheck.AFFIRM_FLAG_PERSON eq '1'}">
					    	<a class="add" href="/ess/infoApply/checkCwaApplyCheckInfo?seach_APPLY_TYPE_NO=218197&seach_ID=${leaveCheck.ID }&seach_GERENORPILIANG=${leaveCheck.GERENORPILIANG }&seach_APPLY_NO=${leaveCheck.APPLY_NO}&seach_ESS_AFFIRM_NO=${leaveCheck.ESS_AFFIRM_NO}&seach_IS_CHECK=1"  
					    		title="CHECK" target="dialog" mask="true" width="1200" height="450"><font color="red">check</font></a>
						</c:if>
						<c:if test="${leaveCheck.AFFIRM_FLAG_PERSON ne '1'}">
						    <a rel="leaveAffirmRemark" href="/ess/infoApply/checkCwaApplyCheckInfo?seach_APPLY_TYPE_NO=218197&seach_ID=${leaveCheck.ID }&seach_GERENORPILIANG=${leaveCheck.GERENORPILIANG }&seach_APPLY_NO=${leaveCheck.APPLY_NO}&seach_ESS_AFFIRM_NO=${leaveCheck.ESS_AFFIRM_NO}" title="审批详情"
				          		target="dialog" mask="true" width="1200" height="450" rel="ess0242_affirm_info" id="leaveAffirmRemarkHref"><font color="red">已check</font></a>
						</c:if>
					</td>	                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
	<div id="viewCwaCheckLis" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/infoApply/viewCwaCheckList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	