<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	$("#viewAttenanceExBatchInfoList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewApplyAttenanceExBatchInfoList",navTab.getCurrentPanel()).submit();
   });
	$('input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	$("#viewApplyAttenanceExBatchInfoList",navTab.getCurrentPanel()).submit();
	    }
	});
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
	     "scrollY": $(document.body).height() - 320,
	     "scrollX": $(document.body).width(),
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
    $('#delAttenanceExFormBatch',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3434();
	} );
    var ids= document.getElementsByName("EX_LEAVE");
	for(var i=0;i<ids.length;i++){
			var index = ids[i].id.substring(9);
			getAffirmor_ess3434(index);
	}
});
$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$(this).html(val);
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		$("#EX_LEAVE_" + index,navTab.getCurrentPanel()).attr("checked",true);
		this.editing = false;
	}
});
function initEditFun_ess3434(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			$("#EX_LEAVE_" + index,navTab.getCurrentPanel()).attr("checked",true);
			this.editing = false;
		}
	});
	var ids= document.getElementsByName("EX_LEAVE");
	for(var i=0;i<ids.length;i++){
			var index = ids[i].id.substring(9);
			getAffirmor_ess3434(index);
	}
}
function delAttenanceExBatchInfo(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("EX_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApplyAttendance/delAttendanceExInBatchForBatch");
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
						navTabSearch($("#viewApplyAttenanceExBatchInfoList"));
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
function ex_fillItem(){
	var checked=false;
	var ids= document.getElementsByName("EX_LEAVE"); 
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_OPERTE.Z' />");//请选中要操作的对象
		return false;
	}
	var in_date = $("#IN_DATE",navTab.getCurrentPanel()).val();
	var in_shi = $("#IN_TIME",navTab.getCurrentPanel()).val();
	var in_fen = $("#IN_MI",navTab.getCurrentPanel()).val();
	var out_date = $("#OUT_DATE",navTab.getCurrentPanel()).val();
	var out_shi = $("#OUT_TIME",navTab.getCurrentPanel()).val();
	var out_fen = $("#OUT_MI",navTab.getCurrentPanel()).val();
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(9);
			$("#IN_DATE_"+index,navTab.getCurrentPanel()).val(in_date);
			$("#IN_TIME_"+index,navTab.getCurrentPanel()).val(in_shi);
			$("#IN_MI_"+index,navTab.getCurrentPanel()).val(in_fen);
			$("#OUT_DATE_"+index,navTab.getCurrentPanel()).val(out_date);
			$("#OUT_TIME_"+index,navTab.getCurrentPanel()).val(out_shi);
			$("#OUT_MI_"+index,navTab.getCurrentPanel()).val(out_fen);
		}
	}
}
function saveAttenanceExBatchInfo(){
	var jsonData = '[';
	$.each($("input[name='EX_LEAVE']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(9);
			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "TO_DATE": "' + $("#TO_DATE_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "FROM_DATE": "' + $("#FROM_DATE_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "IN_TIME":"'+ $("#IN_DATE_"+index,navTab.getCurrentPanel()).val() +" "+ $("#IN_TIME_"+index,navTab.getCurrentPanel()).val()+":"+ $("#IN_MI_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OUT_TIME":"'+ $("#OUT_DATE_"+index,navTab.getCurrentPanel()).val() +" "+ $("#OUT_TIME_"+index,navTab.getCurrentPanel()).val()+":"+ $("#OUT_MI_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "APPLY_REASON":"'+ $("#APPLY_REASON_"+index,navTab.getCurrentPanel()).html() +'"';
			jsonData += '}';
		}
	});
	jsonData += ']';
	var ids= document.getElementsByName("EX_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(9);
			if($("#IN_DATE_"+index,navTab.getCurrentPanel()).val()=='' || $("#IN_TIME_"+index,navTab.getCurrentPanel()).val()==''){
				alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_CORRECT_TIME.Z' />");//请选择正确时间
				return false;
			}
			if($("#OUT_DATE_"+index,navTab.getCurrentPanel()).val()=='' || $("#OUT_TIME"+index,navTab.getCurrentPanel()).val()==''){
				alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_CORRECT_TIME.Z' />");//请选择正确时间
				return false;
			}
			var ar_date_str = $("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val();
			var ar_date_str_format = ar_date_str.substring(6,10) + '/' + ar_date_str.substring(3,5) + '/' + ar_date_str.substring(0,2);  
            var person_id = $("#PERSON_ID_"+index,navTab.getCurrentPanel()).val();
            var date = new Date(ar_date_str.substring(6,10), ar_date_str.substring(3,5), ar_date_str.substring(0,2));
			var d = new Date();
			var prDate = new Date(d.getFullYear(),d.getMonth() + 1, d.getDate()-1);
			var adminID = ${LoginUser.adminID};
			if (adminID != 35448253 && adminID != 11111111) {
			$.ajax({
				 cache: false,
				 type: 'post',
				 url: '/hrm/recruitManage/doSql',
				 data:{sql:"select AR_GET_ATT_EX_CLASH('"+person_id+"','"+ar_date_str_format+"','${LoginUser.cpnyId}') FLAG from dual"},
				 dataType:"json",
				 success: function(data) {
					var flag = data.result[0].FLAG;
					if(flag == 1){
						alertMsg.error("<spring:message code='ess.infoApply.ATTENDANCE_CLOSE.Z' />");//考勤已关闭
						return false;
					}else if(flag == -2 && date.getTime() != prDate.getTime()){
						alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含申请关闭的日期
						return false;
					}
				 },
				 error:DWZ.ajaxError
			}); 
			}
		}
	}
	if (jsonData.length == 2) {
		//请选择要添加的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
		return false;
	}
	alertMsg.confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>",{	
		okCall:function(){
	 		$.ajax({
	 			type:'post',
	 			url:'/ess/infoApplyAttendance/saveAttenanceExBatchInfo' ,
	 			data:[{ name: 'jsonData', value: jsonData }],
	 			dataType:"json",
				cache: false,
				success: navTabAjaxDoneWithForm,
				error: DWZ.ajaxError
	 		});
 		}
	})
 	return false;
}
function getAffirmor_ess3434(index){
	var personId = $("#PERSON_ID_"+index,navTab.getCurrentPanel()).val();
	var htm = '';
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/viewAffirmorByPersonIdList',
		data:[{ name: 'applyTypeCode', value: '218197' },
		      { name: 'applyLength', value: '0' },
		      { name: 'applyTypeNo', value: '218197' },
		      { name: 'personId', value:personId}],
		dataType:"json",
		cache: false,
		async:false,
		success: function(data){
			for(var i=0;i<data.affirmorList.length;i++){
				if(i%2==0){
					htm += '<input type="hidden" name="AFFIRMOR_ID'+(i+1)+'_'+index+'" id="AFFIRMOR_ID'+(i+1)+'_'+index+'" value="'+data.affirmorList[i].AFFIRMOR_ID+'">';
					htm += (i+1)+'['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'&nbsp;&nbsp;'; 
				}else{
					htm += '<input type="hidden" name="AFFIRMOR_ID'+(i+1)+'_'+index+'" id="AFFIRMOR_ID'+(i+1)+'_'+index+'" value="'+data.affirmorList[i].AFFIRMOR_ID+'">';
					htm += (i+1)+'['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'<br>'; 
				}
			}
			$("#AFFIRMOR_TD_"+index,navTab.getCurrentPanel()).html(htm);
			$("#affirmorCnt",navTab.getCurrentPanel()).val(data.affirmorList.length);
		},
		error: DWZ.ajaxError
	});
}
</script>
<div id="viewApplyAttenBatch"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewAttendanceExForBatchInfoList?firstFlag=N&deleteYN=Y" method="post"
		id="viewApplyAttenanceExBatchInfoList" name="viewApplyAttenanceExBatchInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<%-- <ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewAttenanceExBatchInfo_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewAttenanceExBatchInfo_seachDept" selected="${DEPTNO}"/> --%>
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
					<td><!--日期--><spring:message code="org.title.DATE" /></td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/>~
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"/>
					</td>
					<td><!--异常类型--><spring:message code="ess.infoApply.yichangleixing" /></td>
					<td>
						<select name="seach_ITEM_NO">
							<option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option>
							<option value="141443" <c:if test="${'141443' eq ITEM_NO}">selected</c:if>><!--旷工--><spring:message code="ar.monthwork.title.kuanggong" /></option>
							<option value="141442" <c:if test="${'141442' eq ITEM_NO}">selected</c:if>><!--早退--><spring:message code="ar.monthwork.title.EarlyLeave" /></option>
							<option value="141441" <c:if test="${'141441' eq ITEM_NO}">selected</c:if>><!--迟到--><spring:message code="ar.monthwork.title.Lateness" /></option>
							<option value="14015448" <c:if test="${'14015448' eq ITEM_NO}">selected</c:if>><!--漏卡--><spring:message code="ess.title.LOUKA" /></option>
						</select>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
				   <td><!--进门卡--><spring:message code="ess.infoApply.in_door_card" /> </td>
				   <td>
				       <input type="text" name="IN_DATE" id="IN_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value=" "/>&nbsp;&nbsp;
						<select id="IN_TIME" name="IN_TIME">
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08" selected="selected">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
						</select>&nbsp;:
						<select id="IN_MI" name="IN_MI">
							<option value="00" selected="selected">00</option>
							<option value="02">02</option>
							<option value="15">15</option>
							<option value="17">17</option>
							<option value="30">30</option>
							<option value="32">32</option>
							<option value="45">45</option>
							<option value="47">47</option>
						</select>
					</td>
				   <td><!--出门卡--><spring:message code="ess.infoApply.out_door_card" /> </td>
				   <td>
				       <input type="text" name="OUT_DATE" id="OUT_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value=" "/>&nbsp;&nbsp;
						<select id="OUT_TIME" name="OUT_TIME">
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17"  selected="selected">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
						</select>&nbsp;:
						<select id="OUT_MI" name="OUT_MI">
							<option value="00" selected="selected">00</option>
							<option value="02">02</option>
							<option value="15">15</option>
							<option value="17">17</option>
							<option value="30">30</option>
							<option value="32">32</option>
							<option value="45">45</option>
							<option value="47">47</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	            	 <a class="buttonActive" onclick="ex_fillItem();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewAttenanceExBatchInfoList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
	   <!--  <li><a class="buttonActive" onclick="addAttenanceExBatchInfo()"><span>添加</span></a></li> -->
<!-- 		<li><a class="buttonActive" onclick="delAttenanceExBatchInfo(0,'delAttenanceExFormBatch',DWZ.ajaxDone)"><span>取消申请</span></a></li>
 -->		<li><a class="buttonActive" onclick="saveAttenanceExBatchInfo()"><span><!--申请--><spring:message code="display.paecc.shenqing" /></span></a></li>
 		<li><a class="buttonActive" onclick="downloadExcel('viewApplyAttenanceExBatchInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=314','/ess/infoApplyAttendance/viewAttendanceExForBatchInfoList?firstFlag=N')">
			<span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
		<!-- <li><a class="buttonActive" onclick="downloadExcel('viewAttenanceExBatchInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=122','/ess/infoApplyAttendance/viewAttenanceExBatchInfoList?firstFlag=N')"><span>导出到Excel</span></a></li>
		<li><a class="buttonActive" href="/pa/excelImport/importExcelData?importFunName=/importApplyAttenance" target="dialog" mask="true"><span>Excel导入</span></a></li> -->
	</ul>
</div >
	<form name="delAttenanceExForm" id="delAttenanceExFormBatch" method="post" action="/ess/infoApplyAttendance/delAttendanceExInBatchForBatch" 
	  onsubmit="return delAttenanceExBatchInfo(this, navTabAjaxDone);">     
		<table class="orderList" width="99%">                                           
			<thead>
				<tr>
				    <th style="text-align: center" width="2%">
						NO
					</th>
					<th style="text-align: center" width="1%">
				    	<input type="checkbox" class="checkboxCtrl" group="EX_LEAVE" />
				    </th>
				    <th style="text-align: center" width="5%">
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th style="text-align: center" width="10%">
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th style="text-align: center" width="14%">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th style="text-align: center" width="6%">
						<!--考勤日期--><spring:message code="ess.infoApply.attendance_date" />
					</th>
					<!-- <th>
						工作时间
					</th> -->
					<th style="text-align: center" width="8%">
						<!--异常类型--><spring:message code="ess.infoApply.yichangleixing" />
					</th>
					<th style="text-align: center;" width="8%">
						<!--打卡时间--><spring:message code="ess.infoApply.card_clock_time" />
					</th>
					<th style="text-align: center;" width="20%" class="titleColor">
						<!--时间段--><spring:message code="ar.viewardetail.title.dateduan" />
					</th>
					<th style="text-align: center" width="14%" class="titleColor">
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<th style="text-align: center" width="12%">
						<!--审批者--><spring:message code="hrm.contractInfo.APPROVAL_PERSON" />
					</th>
					<!--<th style="text-align: center" width="5%">
						锁定状态<spring:message code="ess.infoApply.LOCK_STATUS.Z" />
					</th>-->
				</tr>
			</thead>
			 <tbody id="tbody">
				<c:forEach items="${attendanceExForBatchInfoList}" var="leaveApply" varStatus="i"> 
					<tr target="sid" rel="${admin.personId}" >
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					    <c:if test="${leaveApply.LOCK_YN eq 'N'}">
					        <input type="checkbox" id="EX_LEAVE_${i.index}" name="EX_LEAVE" value="${leaveApply.APPLY_NO}" />
					    </c:if>
					        <input type="hidden" id="PERSON_ID_${i.index}" value="${leaveApply.PERSON_ID}">
					        <input type="hidden" id="FROM_DATE_${i.index}" value="${leaveApply.FROM_DATE}">
					        <input type="hidden" id="TO_DATE_${i.index}" value="${leaveApply.TO_DATE}">
					        <input type="hidden" id="QUANTITY_${i.index}" value="${leaveApply.WORK_HOUR}">
					        <input type="hidden" id="ITEM_NO_${i.index}" value="${leaveApply.ITEM_NO}">
					        <input type="hidden" id="AR_DATE_STR_${i.index}" value="${leaveApply.AR_DATE_STR}">
					    </td>
					    <td style="text-align: center">${leaveApply.EMPID}</td>
					    <td style="text-align: center">${leaveApply.LOCAL_NAME}</td>
					    <td style="text-align: center">${leaveApply.DEPT_NAME}</td>
					   <td style="text-align: center">${leaveApply.AR_DATE_STR}</td>
				 	    <%-- <td style="text-align: center">${leaveApply.WORK_HOUR}小时</td> --%>
				 	    <td style="text-align: center">${leaveApply.ITEM_NO_NAME}</td>
				 	    <td style="text-align: center;"><!--进门卡--><spring:message code="ess.infoApply.in_door_card" />${leaveApply.INDOOR_TIME}<br><!--出门卡--><spring:message code="ess.infoApply.out_door_card" />${leaveApply.OUTDOOR_TIME}</td>
				 	   <td style="text-align: center;"><!--进门卡--><spring:message code="ess.infoApply.in_door_card" />
				 	   		<input type="text" name="IN_DATE_${i.index}" id="IN_DATE_${i.index}" size="12" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${leaveApply.SHIFT_START_YEAR}"/>&nbsp;
								<%-- <select id="IN_TIME_${i.index}" name="IN_TIME_${i.index}">
									<option value="00" <c:if test="${leaveApply.SHIFT_START_HH eq '00'}">selected="selected"</c:if>>00</option>
									<option value="01" <c:if test="${leaveApply.SHIFT_START_HH eq '01'}">selected="selected"</c:if>>01</option>
									<option value="02" <c:if test="${leaveApply.SHIFT_START_HH eq '02'}">selected="selected"</c:if>>02</option>
									<option value="03" <c:if test="${leaveApply.SHIFT_START_HH eq '03'}">selected="selected"</c:if>>03</option>
									<option value="04" <c:if test="${leaveApply.SHIFT_START_HH eq '04'}">selected="selected"</c:if>>04</option>
									<option value="05" <c:if test="${leaveApply.SHIFT_START_HH eq '05'}">selected="selected"</c:if>>05</option>
									<option value="06" <c:if test="${leaveApply.SHIFT_START_HH eq '06'}">selected="selected"</c:if>>06</option>
									<option value="07" <c:if test="${leaveApply.SHIFT_START_HH eq '07'}">selected="selected"</c:if>>07</option>
									<option value="08" <c:if test="${leaveApply.SHIFT_START_HH eq '08'}">selected="selected"</c:if>>08</option>
									<option value="09" <c:if test="${leaveApply.SHIFT_START_HH eq '09'}">selected="selected"</c:if>>09</option>
									<option value="10" <c:if test="${leaveApply.SHIFT_START_HH eq '10'}">selected="selected"</c:if>>10</option>
									<option value="11" <c:if test="${leaveApply.SHIFT_START_HH eq '11'}">selected="selected"</c:if>>11</option>
									<option value="12" <c:if test="${leaveApply.SHIFT_START_HH eq '12'}">selected="selected"</c:if>>12</option>
									<option value="13" <c:if test="${leaveApply.SHIFT_START_HH eq '13'}">selected="selected"</c:if>>13</option>
									<option value="14" <c:if test="${leaveApply.SHIFT_START_HH eq '14'}">selected="selected"</c:if>>14</option>
									<option value="15" <c:if test="${leaveApply.SHIFT_START_HH eq '15'}">selected="selected"</c:if>>15</option>
									<option value="16" <c:if test="${leaveApply.SHIFT_START_HH eq '16'}">selected="selected"</c:if>>16</option>
									<option value="17" <c:if test="${leaveApply.SHIFT_START_HH eq '17'}">selected="selected"</c:if>>17</option>
									<option value="18" <c:if test="${leaveApply.SHIFT_START_HH eq '18'}">selected="selected"</c:if>>18</option>
									<option value="19" <c:if test="${leaveApply.SHIFT_START_HH eq '19'}">selected="selected"</c:if>>19</option>
									<option value="20" <c:if test="${leaveApply.SHIFT_START_HH eq '20'}">selected="selected"</c:if>>20</option>
									<option value="21" <c:if test="${leaveApply.SHIFT_START_HH eq '21'}">selected="selected"</c:if>>21</option>
									<option value="22" <c:if test="${leaveApply.SHIFT_START_HH eq '22'}">selected="selected"</c:if>>22</option>
									<option value="23" <c:if test="${leaveApply.SHIFT_START_HH eq '23'}">selected="selected"</c:if>>23</option>
								</select>&nbsp;:
								<select id="IN_MI_${i.index}" name="IN_MI_${i.index}">
									<option value="00" <c:if test="${leaveApply.SHIFT_START_MI eq '00'}">selected="selected"</c:if>>00</option>
									<option value="15" <c:if test="${leaveApply.SHIFT_START_MI eq '15'}">selected="selected"</c:if>>15</option>
									<option value="30" <c:if test="${leaveApply.SHIFT_START_MI eq '30'}">selected="selected"</c:if>>30</option>
									<option value="45" <c:if test="${leaveApply.SHIFT_START_MI eq '45'}">selected="selected"</c:if>>45</option>
								</select> --%>
								<input id="IN_TIME_${i.index}" name="IN_TIME_${i.index}" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" />&nbsp;&nbsp;
								<input id="IN_MI_${i.index}" name="IN_MI_${i.index}" type="text" class="digits required" size="3" maxlength="2" min="0" max="59" />&nbsp;&nbsp;
								<br>
						<!--出门卡--><spring:message code="ess.infoApply.out_door_card" />&nbsp;<input type="text" name="OUT_DATE_${i.index}" id="OUT_DATE_${i.index}" size="12" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${leaveApply.SHIFT_END_YEAR}"/>&nbsp;
							<%-- <select id="OUT_TIME_${i.index}" name="OUT_TIME_${i.index}">
								<option value="00" <c:if test="${leaveApply.SHIFT_END_HH eq '00'}">selected="selected"</c:if>>00</option>
								<option value="01" <c:if test="${leaveApply.SHIFT_END_HH eq '01'}">selected="selected"</c:if>>01</option>
								<option value="02" <c:if test="${leaveApply.SHIFT_END_HH eq '02'}">selected="selected"</c:if>>02</option>
								<option value="03" <c:if test="${leaveApply.SHIFT_END_HH eq '03'}">selected="selected"</c:if>>03</option>
								<option value="04" <c:if test="${leaveApply.SHIFT_END_HH eq '04'}">selected="selected"</c:if>>04</option>
								<option value="05" <c:if test="${leaveApply.SHIFT_END_HH eq '05'}">selected="selected"</c:if>>05</option>
								<option value="06" <c:if test="${leaveApply.SHIFT_END_HH eq '06'}">selected="selected"</c:if>>06</option>
								<option value="07" <c:if test="${leaveApply.SHIFT_END_HH eq '07'}">selected="selected"</c:if>>07</option>
								<option value="08" <c:if test="${leaveApply.SHIFT_END_HH eq '08'}">selected="selected"</c:if>>08</option>
								<option value="09" <c:if test="${leaveApply.SHIFT_END_HH eq '09'}">selected="selected"</c:if>>09</option>
								<option value="10" <c:if test="${leaveApply.SHIFT_END_HH eq '10'}">selected="selected"</c:if>>10</option>
								<option value="11" <c:if test="${leaveApply.SHIFT_END_HH eq '11'}">selected="selected"</c:if>>11</option>
								<option value="12" <c:if test="${leaveApply.SHIFT_END_HH eq '12'}">selected="selected"</c:if>>12</option>
								<option value="13" <c:if test="${leaveApply.SHIFT_END_HH eq '13'}">selected="selected"</c:if>>13</option>
								<option value="14" <c:if test="${leaveApply.SHIFT_END_HH eq '14'}">selected="selected"</c:if>>14</option>
								<option value="15" <c:if test="${leaveApply.SHIFT_END_HH eq '15'}">selected="selected"</c:if>>15</option>
								<option value="16" <c:if test="${leaveApply.SHIFT_END_HH eq '16'}">selected="selected"</c:if>>16</option>
								<option value="17" <c:if test="${leaveApply.SHIFT_END_HH eq '17'}">selected="selected"</c:if>>17</option>
								<option value="18" <c:if test="${leaveApply.SHIFT_END_HH eq '18'}">selected="selected"</c:if>>18</option>
								<option value="19" <c:if test="${leaveApply.SHIFT_END_HH eq '19'}">selected="selected"</c:if>>19</option>
								<option value="20" <c:if test="${leaveApply.SHIFT_END_HH eq '20'}">selected="selected"</c:if>>20</option>
								<option value="21" <c:if test="${leaveApply.SHIFT_END_HH eq '21'}">selected="selected"</c:if>>21</option>
								<option value="22" <c:if test="${leaveApply.SHIFT_END_HH eq '22'}">selected="selected"</c:if>>22</option>
								<option value="23" <c:if test="${leaveApply.SHIFT_END_HH eq '23'}">selected="selected"</c:if>>23</option>
							</select>&nbsp;:
							<select id="OUT_MI_${i.index}" name="OUT_MI_${i.index}">
								<option value="00" <c:if test="${leaveApply.SHIFT_END_MI eq '00'}">selected="selected"</c:if>>00</option>
								<option value="15" <c:if test="${leaveApply.SHIFT_END_MI eq '15'}">selected="selected"</c:if>>15</option>
								<option value="30" <c:if test="${leaveApply.SHIFT_END_MI eq '30'}">selected="selected"</c:if>>30</option>
								<option value="45" <c:if test="${leaveApply.SHIFT_END_MI eq '45'}">selected="selected"</c:if>>45</option>
							</select> --%>
							<input id="OUT_TIME_${i.index}" name="OUT_TIME_${i.index}" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" />&nbsp;&nbsp;
							<input id="OUT_MI_${i.index}" name="OUT_MI_${i.index}" type="text" class="digits required" size="3" maxlength="2" min="0" max="59" />&nbsp;&nbsp;
						</td>
				 	    <td style="text-align: center ;" sysLog="text" sysIndex="${i.index}" id="APPLY_REASON_${i.index}"></td>
				 	   <td style="text-align: left ;" id="AFFIRMOR_TD_${i.index}">
							</td> 
					    <div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
					    <!--<td style="text-align: center">
							<c:if test="${leaveApply.LOCK_YN eq 'Y'}">
								已锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b" />
							</c:if>
							<c:if test="${leaveApply.LOCK_YN eq 'N'}">
								未锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b" />
							</c:if>
						</td>-->
					</tr>
			</c:forEach>
			</tbody>
		</table>
	</form >
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>