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
			"/ess/affirmApply/viewOtCheckList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmLeaveApply/viewLeaveCheckList" method="post" 
	      rel="pagerForm" name="viewLeaveCheckList" id="viewLeaveCheckList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" limit="super" id="viewLeaveCheckList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" limit="super" id="viewLeaveCheckList_seachDept" selected="${DEPT_NO}"/>
					</td>		
					<td>社号/姓名
					</td>						
					<td>
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}"/>
					</td>
	                <td><!-- 审批状态 -->
						审批状态
					</td>				
					<td>  
					     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>撤销</option>
						 </select>       
					</td>
					<td>
						Leave类型
					</td>				
					<td>
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="21" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
					</td> 	
				</tr>
				<tr>
	                <td><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
	                </td>			
				    <td>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
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
	<table class="table" width="100%" layoutH="200" nowrapTD="false">
		<thead>
			<tr>
			    <th><!--NO.-->
					NO
				</th>
				<th><!--申请人-->
					申请人
				</th>
				<th><!--是否批量-->
					是否批量
				</th>
				<th><!-- 加班类型 -->
					开始时间
				</th>	
				<th><!-- 加班类型 -->
					结束时间
				</th>	
				<th><!-- 加班类型 -->
					申请时长
				</th>				
				<th><!-- 加班类型 -->
					申请类型
				</th>	
				<th><!--加班事由-->
					申请事由
				</th>
				<th><!--审批情况-->
					审批情况
				</th>
				<th><!--Type-->
					Type
				</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${leaveCheckList}" var="leaveCheck" varStatus="i">			
				<tr>
				    <td style="text-align: center">${leaveCheck.NO}</td>
					<td style="text-align: center">[${leaveCheck.EMPID}]${leaveCheck.LOCAL_NAME}</td>
					<td style="text-align: center">
						<c:if test="${leaveCheck.APPLY_TYPE eq 'PERSON' }">
							个人
						</c:if>
						<c:if test="${leaveCheck.APPLY_TYPE eq 'BATCH' }">
							批量
						</c:if>
					</td>
					<td style="text-align: center">${leaveCheck.LEAVE_FROM_TIME}</td>
					<td style="text-align: center">${leaveCheck.LEAVE_TO_TIME}</td>
					<td style="text-align: center">${leaveCheck.APPLY_LENGTH}</td>
					<td style="text-align: center">
							${leaveCheck.APPLY_TYPE_NAME}
					</td>
					<td style="text-align: center">${leaveCheck.LEAVE_REASON}</td>
					<td style="text-align: center">
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==-1}" >
						    <font color="grey">暂存</font>
						</c:if>
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==0}" >
						    <font color="blue">提交</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==1}" >
						    <font color="green">通过</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==2}" >
						    <font color="red">否决</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==3}" >
						    <font color="back">撤销</font>
						</c:if>	
						<c:if test="${leaveCheck.AFFIRM_PROGRESS_FLAG==4}" >
						    <font color="green">审批中</font>
						</c:if>		
					</td>
					<td style="text-align: center">
						<c:if test="${leaveCheck.AFFIRM_FLAG_PERSON eq '1'}">
							<a class="add" href="/ess/affirmLeaveApply/checkApplyCheckInfo?pageNum=1&APPLY_NO=${leaveCheck.APPLY_NO}&seach_ESS_AFFIRM_NO=${leaveCheck.ESS_AFFIRM_NO}&seach_IS_CHECK=1&unDoApplyNo=${leaveCheck.UNDO_APPLY_NO}"  
					    		title="CHECK" target="navTab"  rel="ess0242_affirm"><font color="red">Check</font></a>
						</c:if>
						<c:if test="${leaveCheck.AFFIRM_FLAG_PERSON ne '1'}">
						    <a rel="leaveAffirmRemark" href="/ess/affirmLeaveApply/viewLeaveApplyCheckInfo?pageNum=1&APPLY_NO=${leaveCheck.APPLY_NO}&unDoApplyNo=${leaveCheck.UNDO_APPLY_NO}" title="审批详情"
				          		target="navTab" rel="ess0242_affirm_info"><font color="red">已check</font></a>
						</c:if>
					</td>  														
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ess/affirmLeaveApply/viewLeaveCheckList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	