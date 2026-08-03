<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

	<%--<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		--%><table class="table" width="100%" nowrapTD="false">
			<thead>
				<tr>
					<th>
				    	NO
				    </th>
				    <th>
						<!-- 社号 --><spring:message code="public.title.empId" />
					</th>
				    <th>
						<!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
					</th>
					<th>
						<!-- 班次 --><spring:message code="ess.message.work_shift" />
					</th>
					<th>
						<!-- 工作时间 --><spring:message code="ess.infoApply.working_hours" />
					</th>
					<th>
						<!-- 日期 --><spring:message code="ess.infoApply.date" />
					</th>
					<th>
						<!-- 考勤类型 --><spring:message code="ess.infoApply.attendance_type" />
					</th>
					<th>
						<!-- 进门时间 --><spring:message code="ess.infoApply.in_door_time" />
					</th>
					<th>
						<!-- 出门时间 --><spring:message code="ess.infoApply.out_door_time" />
					</th>
					<th>
						<!-- 开始时间--><spring:message code="ess.infoApply.title.startTime" />
					</th>
				   <th>
						<!-- 结束时间--><spring:message code="ess.infoApply.end_time" />
					</th>
					<th>
						<!-- 时长--><spring:message code="ess.infoApply.duration" />
					</th>
					<!--<th>
						 备注 <spring:message code="ess.empInfo.remarks" />
					</th>-->
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${viewArPersonalSingleList}" var="item" varStatus="i">			
					<tr target="sid" rel="">
					    <td style="text-align: center">
					    	${i.count}
					    </td>
					    <td style="text-align: center">
					    ${item.EMPID}
					    </td>
					    <td style="text-align: center">
					   ${item.LOCAL_NAME}
					    </td>
					    <td style="text-align: center">
					    ${item.SHIFT_NAME}
					    </td>
					      <td style="text-align: center">
					    ${item.SHIFT_START_TIME} - ${item.SHIFT_END_TIME}
					    </td>
					    <td style="text-align: center">
					    ${item.AR_DATE_STR}
					    </td>
					     <td style="text-align: center">
					    ${item.ITEM_NAME}
					  </td>
					  <td style="text-align: center">
					    ${item.INDOOR_TIME}
					  </td>
					  <td style="text-align: center">
					    ${item.OUTDOOR_TIME}
					  </td>
					  <td style="text-align: center">
					    ${item.FROM_TIME}
					  </td>
					  <td style="text-align: center">
					    ${item.TO_TIME}
					  </td>
					  <td style="text-align: center">
					    ${item.APPLY_LENGTH}
					  </td>
					  <!--<td style="text-align: center">
					    ${item.REMARK}
					  </td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table><%--
	</form>
    <c:set value="/ess/viewDept/viewArPersonalSingleList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>--%>
</div>