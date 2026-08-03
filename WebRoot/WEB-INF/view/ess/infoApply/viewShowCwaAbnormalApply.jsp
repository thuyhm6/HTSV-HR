<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	var arDetailListCnt = $("#arDetailListCnt",navTab.getCurrentPanel()).val();
	for(var i=0;i<arDetailListCnt;i++){
		getAffirmor_Ex(i);
	}
	$(".list",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":false,
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
	     "scrollY": $(document.body).height() - 420,
	     "scrollX": false,
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
$("#check_all").click(function(){
	//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1_cwa"); 
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_OPERTE.Z' />");//请选中要操作的对象
		return false;
	}
	var from_date = $("#fromTime_date",navTab.getCurrentPanel()).val();
	var fromTime_shi = $("#fromTime_shi",navTab.getCurrentPanel()).val();
	var fromTime_fen = $("#fromTime_fen",navTab.getCurrentPanel()).val();
	var toTime_date = $("#toTime_date",navTab.getCurrentPanel()).val();
	var toTime_shi = $("#toTime_shi",navTab.getCurrentPanel()).val();
	var toTime_fen = $("#toTime_fen",navTab.getCurrentPanel()).val();
	$.each($("input[name='c1_cwa']"),function(i, obj) {
		if (obj.checked) {
			var index = obj.id.substring(7);
			$("#IN_DATE_"+index,navTab.getCurrentPanel()).val(from_date);
			$("#IN_TIME_"+index,navTab.getCurrentPanel()).val(fromTime_shi);
			$("#IN_MI_"+index,navTab.getCurrentPanel()).val(fromTime_fen);
			$("#OUT_DATE_"+index,navTab.getCurrentPanel()).val(toTime_date);
			$("#OUT_TIME_"+index,navTab.getCurrentPanel()).val(toTime_shi);
			$("#OUT_MI_"+index,navTab.getCurrentPanel()).val(toTime_fen);
		}
	});
	
});

function f_viewardetail_add(form, callback) {
	
	//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1_cwa"); 
	var affirmorCnt = $("#affirmorCnt").val();
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(7);
			if($("#IN_DATE_"+index,navTab.getCurrentPanel()).val()=='' || $("#IN_TIME_"+index,navTab.getCurrentPanel()).val()==''){
				alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_CORRECT_TIME.Z' />");//请选择正确时间
				return false;
			}
			if($("#OUT_DATE_"+index,navTab.getCurrentPanel()).val()=='' || $("#OUT_TIME_"+index,navTab.getCurrentPanel()).val()==''){
				alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_CORRECT_TIME.Z' />");//请选择正确时间
				return false;
			}
			var ar_date_str = $("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val();
			var date = new Date(ar_date_str.substring(6,10), ar_date_str.substring(3,5), ar_date_str.substring(0,2));
			var d = new Date();
			var prDate = new Date(d.getFullYear(),d.getMonth() + 1, d.getDate()-1);
			var persin_id = $("#APPLY_PERSON_ID_"+index,navTab.getCurrentPanel()).val();
			var ar_date_str_format = ar_date_str.substring(6,10) + '/' + ar_date_str.substring(3,5) + '/' + ar_date_str.substring(0,2);
			$.ajax({
				 cache: false,
				 type: 'post',
				 url: '/hrm/recruitManage/doSql',
				 data:{sql:"select AR_GET_ATT_EX_CLASH('"+persin_id+"','"+ar_date_str_format+"','${LoginUser.cpnyId}') FLAG from dual"},
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
	if(!checked){
		//请选择人员再进行添加操作!
		alertMsg.error("<spring:message code='ess.infoApply.PLEASE_SELECT_OPERTE.Z' />");//请选中要操作的对象
		return false;
	}
	//json传值
	var jsonData = '[';
	
	$.each($("input[name='c1_cwa']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var APPLY_DATE = new Date();
			APPLY_DATE = APPLY_DATE.getFullYear()+"-"+(APPLY_DATE.getMonth()+1)+"-"+APPLY_DATE.getDate();
			var index = obj.id.substring(7);
			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_PERSON_ID": "' + $("#APPLY_PERSON_ID_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_EMP_ID": "' + $("#APPLY_EMP_ID_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "REASON": "' + $("#REASON_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "FROM_TIME": "'+$("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val() + " " + $("#FROM_TIME_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "TO_TIME": "' + $("#AR_DATE_STR_"+index,navTab.getCurrentPanel()).val() + " " + $("#TO_TIME_"+index,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_FLAG":"1",';
			jsonData += ' "APPLY_AFFIRM_FLAG":"14014306",';
			jsonData += ' "APPLY_DATE": "' +  APPLY_DATE + '" ,';
			jsonData += ' "APPLY_TYPE_NO": "218197",';//考勤类型
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
			jsonData += ' "IN_TIME":"'+ $("#IN_DATE_"+index,navTab.getCurrentPanel()).val()+" " + $("#IN_TIME_"+index,navTab.getCurrentPanel()).val() +":"+ $("#IN_MI_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OUT_TIME":"'+ $("#OUT_DATE_"+index,navTab.getCurrentPanel()).val()+" "+ $("#OUT_TIME_"+index,navTab.getCurrentPanel()).val() +":"+ $("#OUT_MI_"+index,navTab.getCurrentPanel()).val()  +'"';
			jsonData += '}';
		}
	});
	jsonData += ']';
	if (jsonData.length == 2) {
		//请选择要添加的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
		return false;
	}
	var $form = $(form);
	
	alertMsg.confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>",{
		 okCall:function(){
 		$.ajax({
 			type:form.method || 'POST',
 			url:$form.attr("action"),
 			data:[{ name: 'jsonData', value: jsonData }],
 			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
 		});
	}})
 	return false;
}
function getAffirmor_Ex(index){
	var applyTypeCode = $("#ITEM_NO_"+index,navTab.getCurrentPanel()).val();
	var personId = $("#APPLY_PERSON_ID_"+index,navTab.getCurrentPanel()).val();
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
<div><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%></div>
<div class="pageHeader">
	<form id="viewShowCwaAbnormalApply"  onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewShowCwaAbnormalApply?firstFlag=N" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				   <td>
						<!--开始日期--><spring:message code="ar.viewcycleparameter.title.kaishiriqi" />
					</td>
					<td>
						<input type="text" name="seach_sDate" id="seach_sDate" value="${sDate}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
					<td >
						<spring:message code="ar.viewcycleparameter.title.jieshuriqi" />
					</td>
					<td>
						<input type="text" name="seach_eDate" id="seach_eDate" value="${eDate}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!-- 查询 -->
									<spring:message code="button.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				   <td >
						<spring:message code="ess.infoApply.in_door_card" /><!-- 进门卡 -->
					</td>
					<td>
						<input type="text" name="fromTime_date" id="fromTime_date" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"/>
						<select id="fromTime_shi" name="fromTime_shi">
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
						<select id="fromTime_fen" name="fromTime_fen">
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
					<td >
						<spring:message code="ess.infoApply.out_door_card" /><!-- 出门卡 -->
					</td>
					<td>
						<input type="text" name="toTime_date" id="toTime_date" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
						<select id="toTime_shi" name="toTime_shi">
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
						<select id="toTime_fen" name="toTime_fen">
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
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="check_all">
									<spring:message code="ess.message.all_reaction"/><!--全部反应-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</div>
<form id="" method="post" action="/ess/infoApply/addCwaAbnormalApply" class="required-validate" onsubmit="return f_viewardetail_add(this,navTabAjaxDone);">
		<div class="formBar">
			<ul  class="toolBar">
				
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="ess.infoApply.title.apply"/><!--申请-->
								</button>
							</div>
						</div>
					</li>
			</ul>
		</div>
			<table class="list" width="99%">
				<thead>
					<tr>
						<th>
						    NO.
						</th>
						<th align="center">
							<input type="checkbox" class="checkboxCtrl" group="c1_cwa">
						</th>
						<th>
						          <spring:message code="ess.infoApply.yichangleixing"/><!--异常类型-->
						</th>
						<th>
						  	 <spring:message code="ess.infoApply.attendance_date"/><!--考勤日期-->
						</th>
						<th>
							<spring:message code="ess.infoApply.card_clock_time"/><!--打卡时间-->
						</th>
						<th>
						         <spring:message code="ess.infoApply.time_quantum"/><!--时间段-->
						</th>
						<th>
							<spring:message code="ess.infoApply.Reason"/><!--原因描述-->
						</th>
						<th>
							<spring:message code="ess.infoApply.juecaizheliebiao"/><!--决裁者列表-->
						</th>
						<!--<th>
						    锁定状态 <spring:message code='ess.infoApply.LOCK_STATUS.Z' />
						</th>-->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${arDetailList}" var="list" varStatus="i">
						<tr> 
							<td style="text-align: center">
					    		${i.index+1 }
					   		 </td>
							<td style="text-align: center" >
								<c:if test="${list.LOCK_YN eq 'N'}">
									<input type="checkbox" id="c1_cwa_${i.index}" name="c1_cwa"  value="${list.PK_NO}">
								</c:if>
							</td>
							<td style="text-align: center">
								${list.ITEM_NAME }
								<input type="hidden" name="APPLY_PERSON_ID_${i.index}" id="APPLY_PERSON_ID_${i.index}" value="${list.PERSON_ID }">
								<input type="hidden" name="ITEM_NO_${i.index}" id="ITEM_NO_${i.index}" value="${list.ITEM_NO }">
								<input type="hidden" name="FROM_TIME_${i.index}" id="FROM_TIME_${i.index}"<c:if test="${list.INDOOR_TIME ne '**:**'}"> value="${list.INDOOR_TIME }"</c:if>>
								<input type="hidden" name="TO_TIME_${i.index}" id="TO_TIME_${i.index}"<c:if test="${list.OUTDOOR_TIME ne '**:**'}"> value="${list.OUTDOOR_TIME }"</c:if>>
								<input type="hidden" name="AR_DATE_STR_${i.index}" id="AR_DATE_STR_${i.index}" value="${list.AR_DATE_STR }"> 
								<input type="hidden" name="APPLY_EMP_ID_${i.index}" id="APPLY_EMP_ID_${i.index}" value="${list.EMPID }">
							</td>
							<td style="text-align: center">
								${list.AR_DATE_STR }
							</td>
							<td style="text-align: center">
								<spring:message code="ess.infoApply.in_door_card" /><!-- 进门卡 -->${list.INDOOR_TIME } <br>
								<spring:message code="ess.infoApply.out_door_card" /><!-- 出门卡 -->${list.OUTDOOR_TIME }
							</td>
							<td style="text-align: center" width="30%">
								<spring:message code="ess.infoApply.in_door_card" /><!-- 进门卡 --><input type="text" name="IN_DATE_${i.index}" id="IN_DATE_${i.index}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${list.SHIFT_START_YYYY}"/>
										<%-- <select id="IN_TIME_${i.index}" name="IN_TIME_${i.index}">
											<option value="00" <c:if test="${list.SHIFT_START_HH eq '00'}">selected="selected"</c:if>>00</option>
											<option value="01" <c:if test="${list.SHIFT_START_HH eq '01'}">selected="selected"</c:if>>01</option>
											<option value="02" <c:if test="${list.SHIFT_START_HH eq '02'}">selected="selected"</c:if>>02</option>
											<option value="03" <c:if test="${list.SHIFT_START_HH eq '03'}">selected="selected"</c:if>>03</option>
											<option value="04" <c:if test="${list.SHIFT_START_HH eq '04'}">selected="selected"</c:if>>04</option>
											<option value="05" <c:if test="${list.SHIFT_START_HH eq '05'}">selected="selected"</c:if>>05</option>
											<option value="06" <c:if test="${list.SHIFT_START_HH eq '06'}">selected="selected"</c:if>>06</option>
											<option value="07" <c:if test="${list.SHIFT_START_HH eq '07'}">selected="selected"</c:if>>07</option>
											<option value="08" <c:if test="${list.SHIFT_START_HH eq '08'}">selected="selected"</c:if>>08</option>
											<option value="09" <c:if test="${list.SHIFT_START_HH eq '09'}">selected="selected"</c:if>>09</option>
											<option value="10" <c:if test="${list.SHIFT_START_HH eq '10'}">selected="selected"</c:if>>10</option>
											<option value="11" <c:if test="${list.SHIFT_START_HH eq '11'}">selected="selected"</c:if>>11</option>
											<option value="12" <c:if test="${list.SHIFT_START_HH eq '12'}">selected="selected"</c:if>>12</option>
											<option value="13" <c:if test="${list.SHIFT_START_HH eq '13'}">selected="selected"</c:if>>13</option>
											<option value="14" <c:if test="${list.SHIFT_START_HH eq '14'}">selected="selected"</c:if>>14</option>
											<option value="15" <c:if test="${list.SHIFT_START_HH eq '15'}">selected="selected"</c:if>>15</option>
											<option value="16" <c:if test="${list.SHIFT_START_HH eq '16'}">selected="selected"</c:if>>16</option>
											<option value="17" <c:if test="${list.SHIFT_START_HH eq '17'}">selected="selected"</c:if>>17</option>
											<option value="18" <c:if test="${list.SHIFT_START_HH eq '18'}">selected="selected"</c:if>>18</option>
											<option value="19" <c:if test="${list.SHIFT_START_HH eq '19'}">selected="selected"</c:if>>19</option>
											<option value="20" <c:if test="${list.SHIFT_START_HH eq '20'}">selected="selected"</c:if>>20</option>
											<option value="21" <c:if test="${list.SHIFT_START_HH eq '21'}">selected="selected"</c:if>>21</option>
											<option value="22" <c:if test="${list.SHIFT_START_HH eq '22'}">selected="selected"</c:if>>22</option>
											<option value="23" <c:if test="${list.SHIFT_START_HH eq '23'}">selected="selected"</c:if>>23</option>
										</select>&nbsp;:
										<select id="IN_MI_${i.index}" name="IN_MI_${i.index}">
											<option value="00" <c:if test="${list.SHIFT_START_MI eq '00'}">selected="selected"</c:if>>00</option>
											<option value="15" <c:if test="${list.SHIFT_START_MI eq '15'}">selected="selected"</c:if>>15</option>
											<option value="30" <c:if test="${list.SHIFT_START_MI eq '30'}">selected="selected"</c:if>>30</option>
											<option value="45" <c:if test="${list.SHIFT_START_MI eq '45'}">selected="selected"</c:if>>45</option>
										</select> --%>
										<input id="IN_TIME_${i.index}" name="IN_TIME_${i.index}" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" />&nbsp;&nbsp;
										<input id="IN_MI_${i.index}" name="IN_MI_${i.index}" type="text" class="digits required" size="3" maxlength="2" min="0" max="59" />&nbsp;&nbsp;
										<br>
								<spring:message code="ess.infoApply.out_door_card" /><!-- 出门卡 --><input type="text" name="OUT_DATE_${i.index}" id="OUT_DATE_${i.index}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"  value="${list.SHIFT_END_YYYY}"/>
										<%-- <select id="OUT_TIME_${i.index}" name="OUT_TIME_${i.index}">
											<option value="00" <c:if test="${list.SHIFT_END_HH eq '00'}">selected="selected"</c:if>>00</option>
											<option value="01" <c:if test="${list.SHIFT_END_HH eq '01'}">selected="selected"</c:if>>01</option>
											<option value="02" <c:if test="${list.SHIFT_END_HH eq '02'}">selected="selected"</c:if>>02</option>
											<option value="03" <c:if test="${list.SHIFT_END_HH eq '03'}">selected="selected"</c:if>>03</option>
											<option value="04" <c:if test="${list.SHIFT_END_HH eq '04'}">selected="selected"</c:if>>04</option>
											<option value="05" <c:if test="${list.SHIFT_END_HH eq '05'}">selected="selected"</c:if>>05</option>
											<option value="06" <c:if test="${list.SHIFT_END_HH eq '06'}">selected="selected"</c:if>>06</option>
											<option value="07" <c:if test="${list.SHIFT_END_HH eq '07'}">selected="selected"</c:if>>07</option>
											<option value="08" <c:if test="${list.SHIFT_END_HH eq '08'}">selected="selected"</c:if>>08</option>
											<option value="09" <c:if test="${list.SHIFT_END_HH eq '09'}">selected="selected"</c:if>>09</option>
											<option value="10" <c:if test="${list.SHIFT_END_HH eq '10'}">selected="selected"</c:if>>10</option>
											<option value="11" <c:if test="${list.SHIFT_END_HH eq '11'}">selected="selected"</c:if>>11</option>
											<option value="12" <c:if test="${list.SHIFT_END_HH eq '12'}">selected="selected"</c:if>>12</option>
											<option value="13" <c:if test="${list.SHIFT_END_HH eq '13'}">selected="selected"</c:if>>13</option>
											<option value="14" <c:if test="${list.SHIFT_END_HH eq '14'}">selected="selected"</c:if>>14</option>
											<option value="15" <c:if test="${list.SHIFT_END_HH eq '15'}">selected="selected"</c:if>>15</option>
											<option value="16" <c:if test="${list.SHIFT_END_HH eq '16'}">selected="selected"</c:if>>16</option>
											<option value="17" <c:if test="${list.SHIFT_END_HH eq '17'}">selected="selected"</c:if>>17</option>
											<option value="18" <c:if test="${list.SHIFT_END_HH eq '18'}">selected="selected"</c:if>>18</option>
											<option value="19" <c:if test="${list.SHIFT_END_HH eq '19'}">selected="selected"</c:if>>19</option>
											<option value="20" <c:if test="${list.SHIFT_END_HH eq '20'}">selected="selected"</c:if>>20</option>
											<option value="21" <c:if test="${list.SHIFT_END_HH eq '21'}">selected="selected"</c:if>>21</option>
											<option value="22" <c:if test="${list.SHIFT_END_HH eq '22'}">selected="selected"</c:if>>22</option>
											<option value="23" <c:if test="${list.SHIFT_END_HH eq '23'}">selected="selected"</c:if>>23</option>
										</select>&nbsp;:
										<select id="OUT_MI_${i.index}" name="OUT_MI_${i.index}">
											<option value="00" <c:if test="${list.SHIFT_END_MI eq '00'}">selected="selected"</c:if>>00</option>
											<option value="15" <c:if test="${list.SHIFT_END_MI eq '15'}">selected="selected"</c:if>>15</option>
											<option value="30" <c:if test="${list.SHIFT_END_MI eq '30'}">selected="selected"</c:if>>30</option>
											<option value="45" <c:if test="${list.SHIFT_END_MI eq '45'}">selected="selected"</c:if>>45</option>
										</select> --%>
								<input id="OUT_TIME_${i.index}" name="OUT_TIME_${i.index}" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" />&nbsp;&nbsp;
								<input id="OUT_MI_${i.index}" name="OUT_MI_${i.index}" type="text" class="digits required" size="3" maxlength="2" min="0" max="59" />&nbsp;&nbsp;
							</td>
							<td style="text-align: center">
								<input style="width:90%"  name="REASON_${i.index}" id="REASON_${i.index}" class="textInput" type="text"/>
							</td>
							<td style="text-align: left ;width: 200px" id="AFFIRMOR_TD_${i.index}">
							</td> 
							<!--<td style="text-align: center">
							<c:if test="${list.LOCK_YN eq 'Y'}">
								 已锁定 <spring:message code='ar.viewCoordApplyAttendanceInfoList.YISUODING.b' />
							</c:if>
							<c:if test="${list.LOCK_YN eq 'N'}">
								 未锁定 <spring:message code='ar.viewCoordApplyAttendanceInfoList.WEISUODING.b' />
							</c:if>
						</td>-->
						</tr>
					</c:forEach>
					<input type="hidden" id = "arDetailListCnt" value="${fn:length(arDetailList)}">
					<span style="display:none"><input type="text" id = "affirmorCnt" value="0"></span>
				</tbody>
			</table>
	</form>
</div>
