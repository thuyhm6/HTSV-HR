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
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPersonOtApplyInfoList?pageNum=1&firstFlag=N" method="post"
		id="viewPOtApplyInfoList" name="viewPOtApplyInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr><td>
						<spring:message code="ess.workgroup.title.duration"/>
					</td>
					<td>
						<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"value="${FROM_DATE}" />
					</td>
					<td>~</td>
					<td>
						<input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"value="${TO_DATE}" />
					</td>
					<td><!--加班时长--><spring:message code="ess.infoApply.overtime_hours"/>(>=) </td>
					<td>
					     <input type="text" id="seach_OT_LENGTH" name="seach_OT_LENGTH" size="10" value="${OT_LENGTH }" />
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
	<!--<div class="formBar">
	</div>-->
	</div> 
		<table class="orderList" width="100%">
			<thead>
				<tr>
				    <th>
				    	NO
				    </th>
				    <th style="text-align: center">
						<!--日期--><spring:message code="ess.infoApply.date"/>
					</th>
				    <th style="text-align: center">
					    <!--星期--><spring:message code="ess.infoApply.week"/>
					</th>
					<th style="text-align: center">
						<!--加班类型--><spring:message code="ess.infoApply.overtime_type"/>
					</th>
					<th style="text-align: center"><!--班组-->
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th style="text-align: center"><!--工作时间-->
						<!--工作时间--><spring:message code="ess.infoApply.working_hours"/>
					</th>
					<th style="text-align: center"><!--进门-->
						<!--进门--><spring:message code="ar.viewarcardrecord.title.jinmen"/>
					</th>
					<th style="text-align: center"><!--出门-->
						<!--出门--><spring:message code="ar.viewarcardrecord.title.chumen"/>
					</th>
					<th style="text-align: center"><!--申请开始时间-->
						<!--加班开始时间--><spring:message code="ess.infoApply.overtime_start_time"/>
					</th>
					<th style="text-align: center"><!--申请结束时间-->
						<!--加班结束时间--><spring:message code="ess.infoApply.end_time"/>
					</th>
					<th style="text-align: center"><!--加班时数-->
						<!--加班时数--><spring:message code="ar.viewSearchOtInfo.JIABANSHISHU.b"/>
					</th>
					<!--<th style="text-align: center">
						锁定状态<spring:message code="ess.infoApply.LOCK_STATUS.Z"/>
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					     <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${otApply.AR_DATE_STR}</td>
					    <td style="text-align: center">
						    <c:if test="${otApply.IWEEK eq '0'}"><!-- 星期日 --><spring:message code="ar.week.XINGQIRI.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '1'}"><!-- 星期一 --><spring:message code="ar.week.XINGQIYI.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '2'}"><!-- 星期二 --><spring:message code="ar.week.XINGQIER.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '3'}"><!-- 星期三 --><spring:message code="ar.week.XINGQISAN.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '4'}"><!-- 星期四 --><spring:message code="ar.week.XINGQISI.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '5'}"><!-- 星期五 --><spring:message code="ar.week.XINGQIWU.b"/></c:if>
	                        <c:if test="${otApply.IWEEK eq '6'}"><!-- 星期六 --><spring:message code="ar.week.XINGQILIU.b"/></c:if>
					    </td>
					    <td style="text-align: center">
					    	<font color="red">${otApply.ITEM_NAME}</font>
					    </td>
					    <td style="text-align: center">${otApply.SHIFT_NAME}</td>
					    <td style="text-align: center">${otApply.SHIFT_START_TIME}-${otApply.SHIFT_END_TIME}</td>
					    <td style="text-align: center">${otApply.INDOOR_TIME}</td>
					    <td style="text-align: center">${otApply.OUTDOOR_TIME}</td>
					    <td style="text-align: center">${otApply.FROM_DATE}</td>
					    <td style="text-align: center">${otApply.TO_DATE}</td>
						<td style="text-align: center" >${otApply.QUANTITY}<!--小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
						<!--<td style="text-align: center">
							<a rel="otApplyRemark" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_NO=${otApply.APPLY_NO}" title="原因"
					          target="dialog" mask="true" width="300" height="300" id="otApplyRemarkHref" >${otApply.REASONNAME}...</a>
						</td>-->	
						<!--<td style="text-align: center">
							 <c:if test="${otApply.LOCK_YN ne 'N' }">
								锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b"/>
							 </c:if>
							
							 <c:if test="${otApply.LOCK_YN eq 'N' }">
								未锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b"/>
							</c:if>
						</td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
    <div id="otApplyRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <div style="visibility: hidden">
</div>