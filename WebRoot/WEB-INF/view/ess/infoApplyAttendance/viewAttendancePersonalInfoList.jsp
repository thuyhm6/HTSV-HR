<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
       /*$("#viewAttendancePersonalInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewAttendancePersonalInfoList",navTab.getCurrentPanel()).submit();
	   });*/
     
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
		     "scrollY": $(document.body).height() - 350,
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
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewAttendancePersonalInfoList"  method="post"
		id="viewAttendancePersonalInfoList" name="viewAttendancePersonalInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration"/>
					</td>
					<td>
					     <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${FROM_DATE}"/>
						~
					    <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${TO_DATE}"/>
					</td>
					<td><spring:message code="ess.infoApply.attendState"/><!-- 考勤状态 --></td>	 
					<td>
					     <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" ><!-- 请选择 --><spring:message code="pa.salary.canShu.qingXuanZe" /></option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
							    <button type="submit">
							       <spring:message code="org.title.SELECT"/><!-- 查询 -->
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
<!--<div class="formBar">
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewAttendancePersonalInfoList_Serch" href="#" ><span><spring:message code="org.title.SELECT"/> 查询 </span></a></li>
	</ul>
</div>-->
		<table class="orderList" width="100%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
				    	<spring:message code="org.title.LOCAL_NAME"/><!-- 姓名 -->
				    </th>
					<th>
						<spring:message code="org.title.EMPID"/><!-- 社号 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.DEPT_NAME"/><!-- 部门名 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.attendState"/><!-- 考勤状态 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.attendance_time"/><!-- 考勤时长 -->
					</th>
					<th>
						<spring:message code="org.title.DATE"/><!-- 日期 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.title.startTime"/><!-- 开始时间 -->
					</th>
					<th>
						<spring:message code="ess.infoApply.end_time"/><!-- 结束时间-->
					</th>
					<!--<th>
						<spring:message code="ess.infoApply.LOCK_STATUS.Z"/> 锁定状态
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveCoordList}" var="CoordleaveApply" varStatus="i">	
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${CoordleaveApply.LOCAL_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.EMPID}</td>
						<td style="text-align: center">${CoordleaveApply.DEPT_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.ITEM_NAME}</td>
						<td style="text-align: center">
							<c:if test="${CoordleaveApply.UNIT eq 'DAY'}">
								${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.day" />
							</c:if>
							<c:if test="${CoordleaveApply.UNIT eq 'HOUR'}">
								${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.hour" />
							</c:if>
							<c:if test="${CoordleaveApply.UNIT eq 'MINUTES'}">
								${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.minite" />
							</c:if>
							<c:if test="${CoordleaveApply.UNIT eq 'TIME'}">
								${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewitemparameter.title.timeofunit" />
							</c:if>
						</td>
						<td style="text-align: center">${CoordleaveApply.AR_DATE_STR}</td>
						<td style="text-align: center">${CoordleaveApply.FROM_DATE}</td>
						<td style="text-align: center">${CoordleaveApply.TO_DATE}</td>
						<!--<td style="text-align: center">
							<c:if test="${CoordleaveApply.LOCK_YN eq 'N'}">
							  	<spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b"/> 未锁定							    
							</c:if>
							<c:if test="${CoordleaveApply.LOCK_YN ne 'N'}">
							    <spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b"/> 已锁定
							</c:if>
						</td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>