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

<div class="pageContent"  style="height:450px; overflow:auto">

<%--	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		--%><table class="table" width="100%"> <!-- layoutH="100" nowrapTD="false" -->
			<thead>
				<tr>
					<th>
						工号
					</th>
					<th>
						姓名
					</th>
					<th>
						部门
					</th>
					<th>
				    	日期
				    </th>
				    <th>
						考勤状态
					</th>
				    <th>
						上班时间
					</th>
					<th>
						下班时间
					</th>
					<th>
						开始时间
					</th>
					<th>
						结束时间
					</th>
					<th>
						时长
					</th>
					<th>
						备注
					</th>
				</tr>
			</thead>
			<tbody >
			<c:forEach items="${viewArSummarySingleList}" var="item" varStatus="i">			
					<tr target="sid" rel="">
						<td style="text-align: center">
					    ${item.EMPID}
					    </td>
					    <td style="text-align: center">
					  	 ${item.LOCAL_NAME}
					    </td>
					    <td style="text-align: center">
					  	 ${item.DEPTNAME}
					    </td>
					    <td style="text-align: center">
					    ${item.AR_DATE_STR}
					    </td>
					     <td style="text-align: center">
					    ${item.ITEM_NAME}
					    </td>
					    <td style="text-align: center">
					    ${item.FROM_TIME}
					    </td>
					    <td style="text-align: center">
					    ${item.TO_TIME} 
					    </td>
					    <td style="text-align: center">
					    ${item.START_TIME} 
					    </td>
					    <td style="text-align: center">
					    ${item.END_TIME}
					    </td>
					    <td style="text-align: center">
					    ${item.QUANTITY}
					    </td>
					    <td style="text-align: center">
					    ${item.REMARK}
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table><%--
	</form>
    <c:set value="/ess/viewDept/viewArPersonalSingleList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>--%>
</div>