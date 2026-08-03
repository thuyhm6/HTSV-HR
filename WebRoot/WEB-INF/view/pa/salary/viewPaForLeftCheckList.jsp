<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function pageFromSea(a){  
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_DATE=$("#seach_FROM_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_DATE",navTab.getCurrentPanel()).val();
	var seach_TO_DATE=$("#seach_TO_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_DATE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/pa/salary/viewPaForLeftCheckList?seach_KEY="+seach_KEY+"&seach_FROM_DATE="+seach_FROM_DATE+"&seach_TO_DATE="+seach_TO_DATE
			+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaForLeftCheckList" method="post" 
	      rel="pagerForm" name="viewPaForLeftCheckList" id="viewPaForLeftCheckList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="6%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>:
					</td> 
					<td width="10%">						
						<input id="seach_KEY" name="seach_KEY" type="text" value="${KEY}"/>
					</td>
					<td width="6%"><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>:
					</td> 
					<td>	                    
				        <input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td width="6%"><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>:
					</td> 
					<td>	                    
					    <input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td>
					<td width="6%"><!-- 决裁状态 -->
						审批状态:
					</td> 						
					<td width="10%">
					    <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="">全部</option>	 
							<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未审批</option>
							<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							<option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						</select>       
					</td>
					<td>&nbsp;</td>
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
				<th width="120" style="text-align: center"><!--申请人-->
					申请人
				</th>
				<th width="140" style="text-align: center"><!--部门-->
					部门
				</th>
				<th width="140" style="text-align:center"><!-- 申请内容 -->
					申请内容
				</th>
				
				<th width="80" style="text-align:center"><!--申请时间-->
					申请时间
				</th>
				<th width="100" style="text-align: center"><!-- 审批情况 -->
					审批情况
				</th>
				<th width="80" style="text-align: center"><!--Type-->
					Type
				</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paForLeftCheckList}" var="paForLeftCheck" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">${i.index+1}</td>
					<td style="text-align: center">[${paForLeftCheck.EMPID}]${paForLeftCheck.LOCAL_NAME}</td>
					<td style="text-align: center">${paForLeftCheck.DEPT_NAME }</td>
					<td style="text-align: center">
				        <a id="paForLeftRemarkLHref" rel="paForLeftCheckRemarkL" target="dialog" mask="true" width="300" height="300" title="详细内容"
							href="/pa/salary/viewPaForLeftApplyContentInfo?BATCH_NO=${paForLeftCheck.APPLY_NO}">
					        <font color="blue">${paForLeftCheck.INTRO}..</font>
					    </a>
					</td>
					
					<td style="text-align: center">${paForLeftCheck.CREATE_DATE}</td>
					<td style="text-align: center">
						<c:if test="${paForLeftCheck.AFFIRM_PROGRESS_FLAG==0}" >
							未审批
						</c:if>	
						<c:if test="${paForLeftCheck.AFFIRM_PROGRESS_FLAG==1}" >
							已通过
						</c:if>	
						<c:if test="${paForLeftCheck.AFFIRM_PROGRESS_FLAG==2}" >
							已否决
						</c:if>	
						<c:if test="${paForLeftCheck.AFFIRM_PROGRESS_FLAG==4}" >
							审批中
						</c:if>		
					</td>
					<td style="text-align: center">
						<c:if test="${paForLeftCheck.CURRENT_CHECKOR_ID eq PERSON_ID}">
					    	<a class="add" href="/pa/salary/checkPaForLeftApplyInfo?APPLY_TYPE_NO=224&APPLY_NO=${paForLeftCheck.APPLY_NO}&seach_IS_CHECK=1"  
					    		title="CHECK" target="dialog" mask="true" width="1210" height="550">
					    		<font color="red">Check</font>
					    	</a>
						</c:if>
						<c:if test="${paForLeftCheck.CURRENT_CHECKOR_ID ne PERSON_ID}">
					    	<a href="/pa/salary/checkPaForLeftApplyInfo?APPLY_TYPE_NO=224&APPLY_NO=${paForLeftCheck.APPLY_NO}"  
					    		class="add" title="CHECK" target="dialog" mask="true" width="1210" height="550">
					    		<font color="red">Check查看</font>
					    	</a>
						</c:if>
					</td>	                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
	<div id="paForLeftCheckRemarkL" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/pa/salary/viewPaForLeftCheckList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	