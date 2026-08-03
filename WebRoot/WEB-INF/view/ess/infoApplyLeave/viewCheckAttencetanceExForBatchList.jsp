<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	$("#viewCheckAttencetanceExForBatchList_serch",navTab.getCurrentPanel()).click(function(){
		$("#viewCheckAttencetanceExForBatchList",navTab.getCurrentPanel()).submit();
   });
	$("#delAttencetanceExList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": false,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
		//正在加载中......
    	"sProcessing": "<spring:message code='ess.message.loading' />",
        //查询不到相关数据！
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
        //表中无数据存在！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
        //快速筛选
        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
        //每页 _MENU_ 条记录
        "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
        //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
        //(从 _MAX_ 条记录过滤)
        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
        "oPaginate": {
            //上一页
            "sPrevious": "<spring:message code='ess.message.previous_page' />",
            //下一页
            "sNext": "<spring:message code='ess.message.next_page' />"
            }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
	});
});
function delAttencetanceExCallback(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApplyLeave/delAttencetanceEx");
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
						navTabSearch($("#viewCheckAttencetanceExForBatchList"));
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
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
};
function changeURL_ess3466(applyNo,applyType){
	var href = "/ess/infoApply/viewAttendanceEx?seach_APPLY_NO=" + applyNo+"&APPLY_TYPE="+applyType;
	$.pdialog.open(href,"ess3466", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<div>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyLeave/viewCheckAttencetanceExForBatchList?firstFlag=N" method="post"
		id="viewCheckAttencetanceExForBatchList" name="viewCheckAttencetanceExForBatchList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewCheckAttencetanceExForBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewCheckAttencetanceExForBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td>
						<spring:message code="public.title.startDate"/>
					</td>
					<td>
						<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"value="${FROM_DATE}" />
					</td>
					<td>
						<spring:message code="public.title.endDate"/>
					</td>
					<td>
						<input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"value="${TO_DATE}" />
					</td>
				    <td><!-- 审批状态 --><spring:message code="ess.infoApply.approval_status" /> </td>
					<td>
						<ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
		<li>
		<a class="buttonActive" id="viewCheckAttencetanceExForBatchList_serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.SEARCH" /></span></a>
		</li>
		<li>
		<a class="buttonActive" onclick="delAttencetanceExCallback(3,'delApplyLeaveInfoForm',DWZ.ajaxDone)" href="#"><span><!-- 取消申请 --><spring:message code="ess.affirmApply.title.quxiaoshenqing" /></span></a>					
		</li>
	</ul>
</div>
	<form name="delAttencetanceExForm" id="delApplyLeaveInfoForm" method="post" action="/ess/infoApplyLeave/delAttencetanceEx" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		<table class="list" width="100%"  id ="delAttencetanceExList">
			<thead>
				<tr>
					<th>
				    	NO.
				    </th>
				    <th>
				       <input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th>
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--考勤日期--><spring:message code="ess.infoApply.attendance_date" />
					</th>
					<th>
						<!--异常类型--><spring:message code="ess.infoApply.yichangleixing" />
					</th>
					<th width="110px">
						<!--打卡时间--><spring:message code="ess.infoApply.card_clock_time" />
					</th>
					<th  width="150px">
						<!--时间段--><spring:message code="ess.infoApply.time_quantum" />
					</th>
					<th>
						<!--班次--><spring:message code="ess.message.work_shift" />
					</th>
					<th>
						<!--工作时间--><spring:message code="ess.infoApply.working_hours" />
					</th>
					<th  width="180px">
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<th>
						<!--审批状态--><spring:message code="ess.infoApply.approval_status" />
					</th>
					<th>
							<!--人事记录--><spring:message code="evs.affirm.personnel_record.e" />
						</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">
					    	${i.index+1 }
					    </td>
					    <td style="text-align: center">
					        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
					        <c:if test="${leaveApply.AFFIRM_FLAG eq '14014306' or leaveApply.AFFIRM_FLAG eq '14014307' or leaveApply.AFFIRM_FLAG eq '14014308'}">
						    	<input type="checkbox" id="c1" name="c1" value="${leaveApply.APPLY_NO}" />
						    </c:if>
					    </td>
					    <td style="text-align: center">${leaveApply.EMPID}</td>
					    <td style="text-align: center">${leaveApply.LOCAL_NAME}</td>
					    <td style="text-align: center">${leaveApply.DEPTNAME}</td>
					    <td style="text-align: center">${leaveApply.AR_DATE_STR}</td>
						<td style="text-align: center;cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ess3466(${leaveApply.APPLY_NO },${leaveApply.ITEM_NO });'>
							<span style="color: blue">${leaveApply.ITEM_NAME}</span>
						</td>
						<td style="text-align: center"><!--进门卡--><spring:message code="ess.infoApply.in_door_card" />${leaveApply.INDOOR_TIME}<br><!--出门卡--><spring:message code="ess.infoApply.out_door_card" />${leaveApply.OUTDOOR_TIME}</td>
						<td style="text-align: center"><!--进门卡--><spring:message code="ess.infoApply.in_door_card" />${leaveApply.IN_TIME}<br><!--出门卡--><spring:message code="ess.infoApply.out_door_card" />${leaveApply.OUT_TIME}</td>
						<td style="text-align: center">${leaveApply.AR_SHIFTNO_NAME}</td>
						<td style="text-align: center">${leaveApply.SHIFT_START_TIME}<br/>-${leaveApply.SHIFT_END_TIME}</td>
						<td>${leaveApply.APPLY_REASON}</td>
						<td style="text-align: center">${leaveApply.AFFIRM_FLAG_NAME} / 
							<c:if test="${leaveApply.CONFIRM_FLAG eq '1'}">
								<spring:message code="ess.title.RENSHITONGGUO" />
							</c:if>
							<c:if test="${leaveApply.CONFIRM_FLAG eq '2'}">
								<spring:message code="ess.title.RENSHIFOUJUE" />
							</c:if>
							<input type="hidden" id="AFFIRM_FLAG_${i.index}" name="AFFIRM_FLAG_${i.index}" value="${leaveApply.AFFIRM_FLAG}">
						</td>	
						<td>${leaveApply.HR_COMMENT}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>