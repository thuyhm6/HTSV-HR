<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
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

<div class="pageContent"  style="height:350px; overflow:auto">

<%--	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		--%><table class="table" width="100%" nowrapTD="false">
			<thead>
				<tr>
					<th>
				    	NO
				    </th>
				    <th><!--姓名 -->
						姓名 
					</th>
					<th><!--部门 -->
						社号
					</th>
					<th><!--等级名-->
						班组
					</th>
					<th><!--GEN -->
						班次
					</th>
					<th><!--主要业务 -->
						工作时间
					</th>
					<th><!--主要业务 -->
						日期
					</th>
					<th><!--部门长姓名 -->
						考勤类型
					</th>
					<th><!--标准职务 -->
						开始时间
					</th>
				   <th><!--标准职务 -->
						结束时间
					</th>
					<th><!--标准职务 -->
						时长
					</th>
					<th><!--标准职务 -->
						原因
					</th>
					<th><!--标准职务 -->
						其他原因 
					</th>
					<th><!--标准职务 -->
					审批状态
					</th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${viewArPersonalSingleList}" var="item" varStatus="i">			
					<tr target="sid" rel="">
					    <td style="text-align: center">
				
					    	${i.count}
					    </td>
					    <td style="text-align: center">
					 ${item.LOCAL_NAME}
					    </td>
					    <td style="text-align: center">
					    ${item.EMPID}
					    </td>
					      <td style="text-align: center">
					    ${item.GROUP_ID}
					    </td>
					    <td style="text-align: center">
					    ${item.SHIFT_NO}
					    </td>
					      <td style="text-align: center">
					    ${item.LEAVE_FROM_TIME}
					    </td>
					    <td style="text-align: center">
					    ${item.AR_DATE_STR}
					    </td>
					     <td style="text-align: center">
					    ${item.ITEM_NO}
					  </td>
					  <td style="text-align: center">
					    ${item.FROM_TIME}
					  </td>
					  <td style="text-align: center">
					    ${item.TO_TIME}
					  </td>
					  <td style="text-align: center">
					    
					    ${item.QUANTITY}
					    
					  </td>
					   <td style="text-align: center">
					    
					    ${item.REASON}
					    
					  </td>
					   <td style="text-align: center">
					    
					    ${item.REASON_OTHER}
					    
					  </td>
					   <td style="text-align: center">
					    
					    ${item.AFFIRM_FLAG}
					    
					  </td>
					 
					</tr>
				</c:forEach>
				
			</tbody>
		</table><%--
	</form>
    <c:set value="/ess/viewDept/viewArPersonalSingleList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>--%>
</div>