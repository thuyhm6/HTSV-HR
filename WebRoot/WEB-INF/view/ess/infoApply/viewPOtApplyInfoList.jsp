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
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	     "fixedColumns":false,
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
	    "buttons": [] 
	});
});
function delPOtApplyCallback(OP_FLAG,form,callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
 
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			//HTSV验证考勤是否锁定
			var j = ids[i].value;
			  var LOCKYN = $('#AR_FLAG'+j).val();
			  if(LOCKYN=="-2" || LOCKYN=="-1"){
			       alertMsg.info('<spring:message code="ess.infoApplyAttendance.CURRENT_APPLICATION_ATTENDANCE_LOCKED.Z" />');//当前考勤申请已锁定！
			       return false;
	   	      }
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
	

    $form.attr("action","/ess/infoApply/delPOvertimeApplyInBatch");
    alertMsg.confirm("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b' />",//确定要批量取消吗?
	  		{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"),
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch(document.viewPOtApplyInfoList);
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	} ,
					error: DWZ.ajaxError
				});
	}});
	return false;
}

/*function delPOvertimeApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要删除吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/delOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewPOtApplyInfoList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
function cancelPOvertimeApply(apply_no){

	var   adust_yn= document.getElementById("ADJUST_YN_CANCEL").value;
	if(adust_yn==1){
		alert("调休加班不可取消");
		return  false;
     }
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/cancelOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewPOtApplyInfoList);
			}else{
				alert(" 本月考勤已锁定或此加班已明细中锁定，取消失败！");
			}
		  }
		});
	}
}
 function cancelPOvertimeApply_Batch(apply_no,flag){

	var   adust_yn= document.getElementById("ADJUST_YN_CANCEL").value;
	 
	if(adust_yn==1){
		alert("调休加班不可取消");
		return  false;
     }
	var params = [];
	params.push({
		
		name: 'APPLY_NO',
		value: apply_no
	},{name:'FLAG',
	  value : flag});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/cancelOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewPOtApplyInfoList);
			}else{
				alert(" 本月考勤已锁定或此加班已明细中锁定，取消失败！");
			}
		  }
		});
	}
}
 */
 function changeURL_ess3204(applyNo){
		var href = "/ess/infoApply/viewApprovaledOtInfo?seach_APPLY_NO=" + applyNo;
		$.pdialog.open(href,"ess3204", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
	}
</script>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageHeader">                                
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPOtApplyInfoList?pageNum=1&firstFlag=N" method="post"
		id="viewPOtApplyInfoList" name="viewPOtApplyInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
					<td><spring:message code="ess.infoApply.overtime_type" /><!--加班类型--></td>
					<td>
					<ait:SelectSyCodeByCpnyID name="seach_OT_TYPE_CODE" parentNo="31" selected="${OT_TYPE_CODE}" limit="ALL"/>						
					</td>
					<td><spring:message code="ess.infoApply.approval_status" /><!--审批状态--> </td>
					<td>
						<ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td>
						<spring:message code="ess.workgroup.title.duration"/>
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
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
							    <button type="submit" class="button">
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
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="delPOtApplyCallback(3,'delPOvertimeApplyAffirmForm',DWZ.ajaxDone)"><span><spring:message code="ess.affirmApply.title.quxiaoshenqing" /><!--取消申请--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="navTabNum('/ess/infoApply/viewSSTOtApplyInfo','pageNum=1&menuNo=526&navTabId=ess0234','ess0234','<spring:message code="ess.infoApply.title.overtimeApply" />');"><span><spring:message code="ess.infoApply.title.overtimeApply" /><!--加班申请--></span></a>
			</li>
			
		</ul>
	</div> 
	<form name="delPOvertimeApplyAffirmForm" id="delPOvertimeApplyAffirmForm" method="post" action="/ess/infoApply/delPOvertimeApplyInBatch" 
	  onsubmit="return delPOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="list" width="99%" nowrapTD="false">
			<thead>
				<tr>
				    <th width="3%">
				    	NO
				    </th>
					<th width="3%">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th width="10%" style="text-align: center"><!--加班-->
						<spring:message code="ess.infoApply.overtime_type" /><!--加班类型-->
					</th>
					<th width="10%" style="text-align: center"><!--日期-->
						<spring:message code="ess.infoApply.title.overtimeTime" /><!--加班日期-->
					</th>
					<th width="8%" style="text-align: center"><!--申请开始时间-->
						<spring:message code="ess.infoApply.application_start_date" /><!--申请开始时间-->
					</th>
					<th width="8%" style="text-align: center"><!--申请结束时间-->
						<spring:message code="ess.infoApply.application_end_date" /><!--申请结束时间-->
					</th>
					<th width="5%" style="text-align: center"><!--加班时间-->
						<spring:message code="ess.infoApply.overtime_hours" /><!--加班时长-->
					</th>
					<th width="10%" style="text-align: center"><!--加班时间-->
						<!--是否扣除吃饭时间--><spring:message code="ess.viewSSTOtApplyInfo.DEDUCT_MEAL_TIME_YN.b"/> / <spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri"/> (30')
					</th>
					<th width="25%" style="text-align: center"><!--加班-->
					         <spring:message code="hrm.empinfo.reason" /><!--原因-->
					</th>
					<th width="5%" style="text-align: center"><!--决裁状态-->
						<spring:message code="ar.attendanceView.viewNoSwipingCard.status" /><!--审批状态-->
					</th>
					<th width="5%" style="text-align: center"><spring:message code="ess.infoApply.LOCK_STATUS.Z" /><!-- 锁定状态 --></th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					     <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					         <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
					         <c:if test="${otApply.AFFIRM_FLAG eq '14014306' or otApply.AFFIRM_FLAG eq '14014307' or otApply.AFFIRM_FLAG eq '14014308'}">
						    	<input type="checkbox" id="c1" name="c1" value="${otApply.APPLY_NO}" />
						    </c:if>
					    </td>
					     <td style="text-align: center;cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ess3204(${otApply.APPLY_NO });'>
								<span style="color: blue">${otApply.OT_TYPE_CODE_NAME}</span>
						</td>
					    <%-- <td style="text-align: center">${otApply.OT_TYPE_CODE_NAME}
 					    	<a href="/ess/infoApply/viewApprovaledOtInfo?seach_APPLY_NO=${otApply.APPLY_NO }" target="dialog" mask="true" width="800" height="600">${otApply.OT_TYPE_CODE_NAME}</a>
					    </td> --%>
					    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>
					    <td style="text-align: center">${otApply.OT_FROM_TIME}</td>
					    <td style="text-align: center">${otApply.OT_TO_TIME}</td>
						<td style="text-align: center">${otApply.OT_APPLY_HOUR} <spring:message code="ar.viewitemparameter.title.xiaoshi" /><!-- 小时 --></td>
						<td style="text-align: center"><c:if test="${otApply.DEDUCT_YN eq 0}"><!--否--><spring:message code="ess.infoApply.no"/></c:if>
						                               <c:if test="${otApply.DEDUCT_YN eq 1}"><!--是--><spring:message code="ess.infoApply.yes"/></c:if></td>
						<td style="text-align: center">${otApply.APPLY_OT_REMARK}
							<%-- <a rel="otApplyRemark" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_NO=${otApply.APPLY_NO}" title="原因"
					          target="dialog" mask="true" width="300" height="300" id="otApplyRemarkHref" >${otApply.APPLY_OT_REMARK}...</a> --%>
						</td>
						<td style="text-align: center">${otApply.AFFIRM_FLAG_NAME}
						<%-- &nbsp;&nbsp;<c:if test="${otApply.CONFIRM_FLAG eq 0}"><spring:message code="ess.viewApply.title.notConfirmed" /><!-- 人事未确认 --></c:if><c:if test="${otApply.CONFIRM_FLAG ne 0}"><spring:message code="ar.viewAttendanceManagentForSerchInfo.RENSHIYIQUEREN.b" /><!-- 人事已确认 --></c:if> --%>
						</td>	
						<td style="text-align: center">
							<input name="AR_FLAG${otApply.APPLY_NO}" id="AR_FLAG${otApply.APPLY_NO}" type="hidden" value="${otApply.AR_FLAG}"/>
							<c:if test="${otApply.AR_FLAG eq -2 || otApply.AR_FLAG eq -1}">
								Locked
							</c:if>
							<c:if test="${otApply.AR_FLAG ne -2 && otApply.AR_FLAG ne -1}">
								No lock
							</c:if>
						</td> 
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
    <div id="otApplyRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <div style="visibility: hidden">
    <%-- <c:set value="/ess/infoApply/viewPOtApplyInfoList?firstFlag=N" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%> --%>
	</div>
</div>