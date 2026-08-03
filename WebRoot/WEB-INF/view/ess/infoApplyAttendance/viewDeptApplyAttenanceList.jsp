<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 310,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
        	"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
            "sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！
            "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
            "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
            "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
            "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
            "sInfoFiltered": "(<spring:message code='hrm.alert.contractInfo.Record_filter'/>)",//从 _MAX_ 条记录过滤
            "oPaginate": {
                "sPrevious": '<spring:message code="hrm.alert.contractInfo.Previous_page"/>',
                "sNext": '<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>'
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [
              ] 
		});
});

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewDeptApplyAttenanceList?firstFlag=N" method="post"
		id="viewDeptApplyAttenanceList" name="viewDeptApplyAttenanceList">
		<input type="hidden" name='ADMIN_ID' value="${LoginUser.adminID }"/>
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration"/>
					</td>
					<td>
					    <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${FROM_DATE}"/>
					~
					     <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${TO_DATE}"/>
					</td>
					<td><!-- 考勤状态 --><spring:message code="ess.infoApply.attendState" /></td>	 
					<td>
					     <!--<select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					     <option value="" > 请选择 <spring:message code="org.title.PLEASE_SELECT" /></option>
							 <c:forEach items="${itemList}" var="item">
								 <option value="${item.ITEM_NO}" <c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>>
							       ${item.ITEM_NAME}
								 </option>
							 </c:forEach>
						</select>-->
						<ait:selectCodeMultiArDetail id="seach_AR_DETAIL_ITEM" name="seach_AR_DETAIL_ITEM_NAME"  selected="${AR_DETAIL_ITEM}" selectedNm="${AR_DETAIL_ITEM_NAME}"/>
						<img alt="clear" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
						onclick="$('input[name=seach_AR_DETAIL_ITEM_NAME]',navTab.getCurrentPanel()).attr('value','');$('input[name=seach_AR_DETAIL_ITEM]',navTab.getCurrentPanel()).attr('value','');">	
					</td>
					<td ><!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/> </td>
					<td >
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_SHIFT_NO" parentNo="400223" selected="${SHIFT_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
					</tr><tr>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<!--<ait:deptList name="seach_DEPTNO"  cpnyId="${defaultCpny}"  limit="manager" id="viewDeptApplyAttenanceList_seachDept"  selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO"  cpnyId="${defaultCpny}"  limit="manager" id="viewDeptApplyAttenanceList_seachDept"  selected="${DEPTNO}"/>
					-->
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPT_NAME" limit="manager" selectedNm="${DEPT_NAME}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
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
				    <li><a class="buttonActive" onclick="downloadExcel('viewDeptApplyAttenanceList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=138','/ess/infoApplyAttendance/viewDeptApplyAttenanceList?firstFlag=N')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel"/></span></a></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
<!--<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(leaveDeptList)}</div>-->
</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		<table class="orderList" width="100%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMPID"/> 
					</th>
					<th>
				    	<!--姓名--><spring:message code="ess.infoApply.NAME"/> 
				    </th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT"/> 
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank"/> 
					</th>
					<th>
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/> 
					</th>
					<th>
						<!--日期--><spring:message code="ess.infoApply.date"/> 
					</th>
					<th>
						<!--考勤状态--><spring:message code="ess.infoApply.attendState"/> 
					</th>
					<th>
						<!--进门时间--><spring:message code="ess.infoApply.in_door_time"/> 
					</th>
					<th>
						<!--出门时间--><spring:message code="ess.infoApply.out_door_time"/> 
					</th>
					<th>
						<!--开始时间--><spring:message code="ess.infoApply.title.startTime"/> 
					</th>
					<th>
						<!--结束时间--><spring:message code="ess.infoApply.title.endTime"/> 
					</th>
					<th>
						<!--时长--><spring:message code="ess.infoApply.duration"/> 
					</th>
					<!--<th>
						锁定状态<spring:message code="ess.infoApply.LOCK_STATUS.Z"/> 
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveDeptList}" var="leaveApply" varStatus="i">	
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">${i.count}</td>
						<td style="text-align: center">${leaveApply.EMPID}</td>
					    <td style="text-align: center">${leaveApply.LOCAL_NAME}</td>
						<td style="text-align: center">${leaveApply.DEPT_NAME}</td>
						<td style="text-align: center">${leaveApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${leaveApply.SHIFT_NAME}</td>
						<td style="text-align: center">${leaveApply.AR_DATE_STR}</td>
						<td style="text-align: center">${leaveApply.ITEM_NAME}</td>
						<td style="text-align: center">${leaveApply.INDOOR_TIME}</td>
						<td style="text-align: center">${leaveApply.OUTDOOR_TIME}</td>
						<td style="text-align: center">${leaveApply.FROM_DATE}</td>
						<td style="text-align: center">${leaveApply.TO_DATE}</td>
						<td style="text-align: center">${leaveApply.QUANTITY} ${leaveApply.UNIT}</td>
						<!--<td style="text-align: center">
                            <c:if test="${leaveApply.LOCK_YN ne 'N' }">
								锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b"/>
							 </c:if>
							
							 <c:if test="${leaveApply.LOCK_YN eq 'N' }">
								未锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b"/>
							</c:if>
                        </td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<div style="visibility: hidden">
    <%-- <c:set value="/ess/infoApplyAttendance/viewApplyAttendanceInfoList?firstFlag=N" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%> --%>
	</div>
</div>