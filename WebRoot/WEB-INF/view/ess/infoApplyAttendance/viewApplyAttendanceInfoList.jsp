<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(function(){
	$(".list",navTab.getCurrentPanel()).dataTable({
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
	     "scrollY": $(document.body).height() - 380,
	     "scrollX": false,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
	    	"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
	        "sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！查询不到相关数据！",
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
	    "buttons": [] 
	});
});
function delLeaveApplyCallback(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("SINGLE_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
	var CPNY_ID = $("#CPNY_ID").val();
	//HTSV验证考勤是否锁定
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
			  var j = ids[i].value;
			  var LOCKYN = $('#AR_FLAG'+j).val();
			  if(LOCKYN=="-2" || LOCKYN=="-1"){
			       alertMsg.info('<spring:message code="ess.infoApplyAttendance.CURRENT_APPLICATION_ATTENDANCE_LOCKED.Z" />');//当前考勤申请已锁定！
			       return false;
	   	      }
			}
		}	
    $form.attr("action","/ess/infoApplyAttendance/delAttedanceApplyInBatch");
    alertMsg.confirm ("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b' />",{//确定要批量取消吗?
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewApplyLeaveInfoList"));
						alertMsg.correct(data.message);
					}else{
						if(data.result=="2"){
							alertMsg.info(data.message);
						}else{
							alertMsg.error(data.message);
						}
					}   
		   	 	}  ,
				error: DWZ.ajaxError
			});
        }});
	return false;
}
function changeURL_ess3211(applyNo){
	var href = "/ess/infoApply/viewApprovaledLeaveInfo?seach_APPLY_NO=" + applyNo;
	$.pdialog.open(href,"ess3211", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewApplyAttendanceInfoList?firstFlag=N" method="post"
		id="viewApplyLeaveInfoList" name="viewApplyLeaveInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
				       <td><spring:message code="ess.infoApply.attendance_type" /><!--考勤类型--></td>	 
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_ITEM_NO" parentNo="21" limit="all" selected="${ITEM_NO}"/>
					</td>
					<td><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" /><!--审批状态--> </td>
					<td>
					<ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${defaultCpny}"  limit="all"/>
					</td>
					<td>
						<spring:message code="ess.infoApply.attendance_date" /><!--考勤日期-->
					</td>
					<td>
					    <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${FROM_DATE }"/>
					</td>
					<td>~</td>
					<td>
					    <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${TO_DATE}"/>
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
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="delLeaveApplyCallback(3,'delLeaveApplyAffirmForm',DWZ.ajaxDone)" href="#"><span><spring:message code="ess.affirmApply.title.quxiaoshenqing" /><!--取消申请--></span></a>					
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="navTabNum('/ess/infoApplyAttendance/viewSSTApplyAttendance','pageNum=1&menuNo=587&navTabId=ess0214','ess0214','<spring:message code="ess.infoApply.attendance_applicated" />');"><span><spring:message code="ess.infoApply.attendance_applicated" /><!--考勤申请--></span></a>
			</li>
	    </ul>
</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		<table class="list" width="99%" nowrapTD="false">
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
				    	<input type="checkbox" class="checkboxCtrl" group="SINGLE_LEAVE" />
				    </th>
					<th>
						<spring:message code="ess.infoApply.attendance_type" /><!--考勤类型-->
					</th>
					<th>
						<spring:message code="ess.viewApply.title.applyDate" /><!--申请日期-->
					</th>
					<th>
						<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE" /><!--开始日期-->
					</th>
					<th>
						<spring:message code="ess.infoApply.title.startTime" /><!--开始时间-->
					</th>
					<th>
						<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE" /><!--结束日期-->
					</th>
					<th>
						<spring:message code="ess.infoApply.end_time" /><!--结束时间-->
					</th>
					<th>
						<spring:message code="ess.infoApply.duration" /><!--时长-->
					</th>
					<th width="22%">
						<spring:message code="ess.infoApply.Reason" /><!--原因-->
					</th>
					<th>
						<spring:message code="ar.attendanceView.viewNoSwipingCard.status" /><!--审批状态-->
					</th>
					<th>
							<!--人事记录--><spring:message code="evs.affirm.personnel_record.e" />
						</th>
<!-- 				<th>是否取消
						是否取消
					</th> -->
					<th><spring:message code="ess.infoApply.LOCK_STATUS.Z" /><!-- 锁定状态 --></th>
					<th>
						<spring:message code="ess.infoApply.CREATEPERSON" /><!--输入者-->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">	
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					   <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
					        <c:if test="${leaveApply.AFFIRM_FLAG eq '14014306' or leaveApply.AFFIRM_FLAG eq '14014307' or leaveApply.AFFIRM_FLAG eq '14014308'}">
						   		 <input type="checkbox" id="SINGLE_LEAVE" name="SINGLE_LEAVE" value="${leaveApply.APPLY_NO}" />
						    </c:if>
					    </td>
					    <td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ess3211(${leaveApply.APPLY_NO });'>
								<span style="color: blue">${leaveApply.LEAVE_TYPE_CODE_NAME}</span>
						</td>
					    <td style="text-align: center">${leaveApply.APPLY_TIME}</td>
						<td style="text-align: center">${leaveApply.FROM_DATE}</td>
						<td style="text-align: center">${leaveApply.FROM_TIME}</td>
						<td style="text-align: center">${leaveApply.TO_DATE}</td>
						<td style="text-align: center">${leaveApply.TO_TIME}</td>
						<td style="text-align: center"><c:if test="${leaveApply.APPLY_LENGTH ge leaveApply.DAY_HOURS }"><fmt:formatNumber type="number"  value="${leaveApply.APPLY_LENGTH/leaveApply.DAY_HOURS + (leaveApply.APPLY_LENGTH%leaveApply.DAY_HOURS == 0 ? 0 : -1)}" pattern="#" maxFractionDigits="0"/><spring:message code="ar.viewitemparameter.title.dayofunit" /><!-- 天 --></c:if>${leaveApply.APPLY_LENGTH%leaveApply.DAY_HOURS}<spring:message code="ar.viewitemparameter.title.xiaoshi" /><!-- 小时 --></td>
						<td style="text-align: center">${leaveApply.LEAVE_REASON}</td>
						<td style="text-align: center">${leaveApply.AFFIRM_FLAG_NAME}&nbsp;&nbsp;
						                               <c:if test="${leaveApply.CONFIRM_FLAG ne '1' and leaveApply.CONFIRM_FLAG ne '2' }">
						                               <!-- 人事未确认--><spring:message code="ess.title.WEIQUEREN"/></c:if>
						                               <c:if test="${leaveApply.CONFIRM_FLAG eq '1' }">
						                               <!-- 人事通过--><spring:message code="ess.title.RENSHITONGGUO"/></c:if>
						                               <c:if test="${leaveApply.CONFIRM_FLAG eq '2' }">
						                               <!-- 人事否决--><spring:message code="ess.title.RENSHIFOUJUE"/></c:if>
                        </td>	
						<%-- <td style="text-align: center">
							<c:if test="${leaveApply.AFFIRM_FLAG eq '14014310'}">
									<font color="red">已取消</font>
							</c:if>
						</td>--%>
						<td style="text-align: center">${leaveApply.HR_COMMENT} </td>
						<td style="text-align: center">
							<input name="AR_FLAG${leaveApply.APPLY_NO}" id="AR_FLAG${leaveApply.APPLY_NO}" type="hidden" value="${leaveApply.AR_FLAG}"/>
							<c:if test="${leaveApply.AR_FLAG eq -2 || leaveApply.AR_FLAG eq -1}">
								Locked
							</c:if>
							<c:if test="${leaveApply.AR_FLAG ne -2 && leaveApply.AR_FLAG ne -1}">
								No lock
							</c:if>
						</td> 
						<td style="text-align: center">${leaveApply.CREATED_BY} ${leaveApply.CREATED_IP}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>