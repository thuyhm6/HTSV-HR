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
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewOtCheckList" method="post" 
	      rel="pagerForm" name="viewOtCheckList" id="viewOtCheckList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="text-align:center" width="10%"><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>:
					</td>
					<td width="20%">
						 <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
					</td>		
					<td style="text-align:center" width="10%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>：
					</td>						
					<td width="20%">
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}"/>
					</td>
	                <td style="text-align:center" width="10%"><!-- 审批状态 -->
						<spring:message code="ess.viewApply.title.affirmStatus"/>:
					</td>				
					<td width="20%">  
						<!--<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="3528" 
					     cnpyID="${defaultCpny}" selected="${AFFIRM_FLAG}" limit="all"/>-->
					     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						 </select>       
					</td>
					<td></td>
				</tr>
				<tr>
	                <td style="text-align:center"><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>:
	                </td>			
				    <td>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td style="text-align:center"><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>:
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td> 
					<td style="text-align:center"><!-- 加班类型 -->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>:
					</td>				
					<td>
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
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
	<table class="table" width="100%" height="80%" layoutH="196" nowrapTD="false">
		<thead>
			<tr>
			    <th width="45" style="text-align: center"><!--NO.-->
					NO.
				</th>
				<th width="100" style="text-align: center"><!--申请人-->
					申请人
				</th>
				<th width="80" style="text-align: center"><!--是否批量-->
					是否批量
				</th>
				<th width="160" style="text-align: center"><!--部门-->
					部门
				</th>
				<th width="100" style="text-align: center"><!--加班日期-->
					加班日期
				</th>		
				
				<th width="100" style="text-align: center"><!-- 加班类型 -->
					加班类型
				</th>			
				<c:if test="${defaultCpny ne 'AIT01' }">	
					<th width="160" style="text-align: center"><!--加班时间段-->
						加班时间段
					</th>
					<th width="50" style="text-align: center"><!--扣除时间-->
						扣除时间
					</th>
					<th width="50" style="text-align: center"><!--申请时长-->
						申请时长
					</th>
				</c:if>
				<c:if test="${defaultCpny eq 'AIT01' }">	
					<th width="80" style="text-align: center"><!--申请时长-->
						申请时长
					</th>
				</c:if>
				
				<th width="120" style="text-align: center"><!--加班事由-->
					加班事由
				</th>
				<th width="100" style="text-align: center"><!--审批情况-->
					审批情况
				</th>
				<th width="80" style="text-align: center"><!--Type-->
					Type
				</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${otCheckList}" var="otCheck" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">${otCheck.APPLY_NO}</td>
					<td style="text-align: center">[${otCheck.EMPID}]${otCheck.LOCAL_NAME}</td>
					<td style="text-align: center">
						<c:if test="${otCheck.APPLY_TYPE eq 'PERSON' }">
							个人
						</c:if>
						<c:if test="${otCheck.APPLY_TYPE eq 'BATCH' }">
							批量
						</c:if>
					</td>
					<td style="text-align: center">${otCheck.DEPT_NAME}</td>
					<td style="text-align: center">${otCheck.APPLY_OT_DATE}</td>
					
					<td style="text-align: center">${otCheck.APPLY_TYPE_NAME}</td>
					<c:if test="${defaultCpny ne 'AIT01' }">
						<td style="text-align: center">
				    	    <dt style="padding: 1px;">${otCheck.OT_FROM_TIME}</dt>
				    	    <dt style="padding: 1px;">${otCheck.OT_TO_TIME}</dt>						
						</td>
						<td style="text-align: center">${otCheck.OT_DEDUCT_TIME}</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${otCheck.OT_LENGTH }" pattern="#,##0.00"/>
						</td>
					</c:if>
					<c:if test="${defaultCpny eq 'AIT01' }">
						<td style="text-align: center">
							${otCheck.OT_APPLY_HOUR}小时${otCheck.OT_APPLY_MINUTE}分
						</td>
					</c:if>
					
					<td style="text-align: center">
						<a rel="otAffirmRemark" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_NO=${otCheck.APPLY_NO}" title="加班事由"
				          target="dialog" mask="true" width="300" height="300" id="otAffirmRemarkHref" >${otCheck.INTRO}</a>
					</td>
					<td style="text-align: center">
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==-1}" >
						    <font color="grey">未提交</font>
						</c:if>
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==0}" >
						    <font color="blue">未审批</font>
						</c:if>	
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>	
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==3}" >
						    <font color="back">已取消</font>
						</c:if>	
						<c:if test="${otCheck.AFFIRM_PROGRESS_FLAG==4}" >
						    <font color="green">审批中</font>
						</c:if>		
					</td>
					<td style="text-align: center">
					    <c:if test="${otCheck.CURRENT_CHECKOR_ID ne otCheck.CHECKOR_ID}">
					    	<a class="add" href="/ess/affirmApply/checkApplyCheckInfo?seach_APPLY_NO=${otCheck.APPLY_NO}&seach_ESS_AFFIRM_NO=${otCheck.ESS_AFFIRM_NO}"  
					    		title="CHECK" target="dialog" mask="true" width="1200" height="450"><font color="red">check</font></a>
						</c:if>
					</td>	                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
	<div id="otCheckRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/affirmApply/viewOtCheckList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	