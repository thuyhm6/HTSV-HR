<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function f_search_viewempcalendar_class(){	
	var person_id = $("#person_id1",navTab.getCurrentPanel()).val();
	$("#person_id",navTab.getCurrentPanel()).attr("value",person_id);
	navTabSearch(document.viewempcalendar_class);
}

function f_save_viewempcalendar_class(navTabId){
          //$(":checkbox").attr("checked", "checked");
	var jsonData = '[';
	$("input[name='day']:checkbox:checked",navTab.getCurrentPanel()).each(function()
	 {
		if (this.checked) {

			var tempdate = $("#year",navTab.getCurrentPanel()).val() + "-" + $("#month",navTab.getCurrentPanel()).val() + "-";

			if (jsonData.length > 1) {
				jsonData += ',{';
			}
			 else {
				jsonData += '{';
			}
		 
          
             
			jsonData += ' "DDATE_STR": "' + this.title + '",';
			jsonData += ' "DATE": "' + $("#DAY" + this.value).val()  + '",';
			
			jsonData += ' "PERSON_ID": "' + $("#person_id",navTab.getCurrentPanel()).val() + '",';
			jsonData += ' "CPNY_ID": "' + $("#cpny_id",navTab.getCurrentPanel()).val() + '",';
			jsonData += ' "GROUP": "' + $("#GROUP",navTab.getCurrentPanel()).val() + '",';			
			jsonData += ' "SHIFT_NO": "' + $("#SHIFT_NO_" + this.value).val() + '" ,';
			jsonData += ' "DATE_TYPE": "' + $("#DATE_TYPE_" + this.value).val() + '" ';

			jsonData += '}';
		}
	});
	jsonData += ']';
	if (jsonData.length == 2) {
		//请选择要修改的日期!
		alertMsg.error('<spring:message code="ar.alert.message.viewCompanyCalendar.choosedate"/>');
		return;
	}
	/* 直接处理正常班 不用通过公司日历 */
	if ( $('#GROUP',navTab.getCurrentPanel()).val()== '400224') {
		//请选择要修改的日期!
		alertMsg.error('<spring:message code="alert.message.pleaseToCompanyCalendar_toUpdateNormal.b"/>');//请到公司日历设置正常班
		return;
	}
	$.post("/ar/attendanceSettings/updateClassCalendarInfo",
	[
	{
		name: 'jsonData',
		value: jsonData
	}
	]
	,
	function(result)
	 {
		if (result == "Y"){
			//保存成功
			alert("<spring:message code='ar.alert.message.addempshift.success'/>");
			new function(){
				var $form = $("#viewempcalendar_class");
				var params = $("#viewempcalendar_class").serializeArray();
				
				navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
				return false;
			};
		}else{
			 //更新个人日历失败！
			 
			 alertMsg.error("<spring:message code='alert.message.updateGroupCalendar_fail.b'/>");//更新班次日历失败！
		}
	});
}
function f_create_ar_detail_class(form,callback,navTabId){
var $form=null;
if($('#'+form).length>0)
	$form=$('#'+form);
else
		$form = $(form);	
var groupId=$("#GROUP",navTab.getCurrentPanel()).val();

var yearStr=$("#year",navTab.getCurrentPanel()).val() ;
var monthStr=$("#month",navTab.getCurrentPanel()).val() ;
var groupText=$("#GROUP").find("option:selected").text();

//jsonData = ' [{"GROUP": "' + groupId + '"},{"MONTH_STR":"'+yearStr+''+monthStr+'"}]';	
  $form.attr("action","/ar/attendanceSettings/createArDEtailClassCalendarInfo?GROUP="+groupId+"&MONTH_STR="+yearStr+monthStr);

    $.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				//navTabSearch("searchLeaveApplyAffirmForm");
				alertMsg.correct(yearStr+monthStr+"生成 成功");
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
}
function prev_viewempcalendar_class(){
	var year = $("#year",navTab.getCurrentPanel()).val() ;
	var month = $("#month",navTab.getCurrentPanel()).val() ;
	var empid = $("#empid",navTab.getCurrentPanel()).val() ;
    var person_id = $("#person_id1",navTab.getCurrentPanel()).val();
    var cpny_id = $("#cpny_id",navTab.getCurrentPanel()).val();
    var STAT_NO = $("#STAT_NO",navTab.getCurrentPanel()).val();
	var myDate = new Date();
	myDate.setFullYear(year, month-1, 1);
	month = myDate.getMonth() + 1;
	
	if(month == 1){
		year = year - 1;
		month = 12;
	}else if(month > 1 && month <= 12){
		month = month - 1;
	}
	month = month < 10 ? "0" + month : "" + month;
	
	$("#year",navTab.getCurrentPanel()).attr("value",year);
	$("#month",navTab.getCurrentPanel()).attr("value",month);
	$("#empid",navTab.getCurrentPanel()).attr("value",empid);
	$("#person_id",navTab.getCurrentPanel()).attr("value",person_id);
	$("#cpny_id",navTab.getCurrentPanel()).attr("value",cpny_id);
	$("#STAT_NO",navTab.getCurrentPanel()).attr("value",STAT_NO);
	navTabSearch(document.viewempcalendar_class);
}

function next_viewempcalendar_class(){
	var year = $("#year",navTab.getCurrentPanel()).val() ;
	var month = $("#month",navTab.getCurrentPanel()).val() ;
	var empid = $("#empid",navTab.getCurrentPanel()).val() ;
    var person_id = $("#person_id1",navTab.getCurrentPanel()).val();
    var cpny_id = $("#cpny_id",navTab.getCurrentPanel()).val();
    var STAT_NO = $("#STAT_NO",navTab.getCurrentPanel()).val();
	/* var myDate = new Date();
	myDate.setFullYear(year, month-1, 1);
	month = myDate.getMonth() + 1;
	
	if(month == 12) {
		year = year + 1;
		month = 1;
	}else if(month >= 1 && month < 12){
		month = month + 1 ;
	}
	month = month < 10 ? "0" + month : "" + month ; */
	var myDate = new Date();
	myDate.setFullYear(year, month-1, 1);
	month = myDate.getMonth() + 1;
	
	if(month == 12) {
		year = parseInt(year) + 1;
		month = 1;
	}else if(month >= 1 && month < 12){
		month = month + 1 ;
	}
	month = month < 10 ? "0" + month : "" + month ;
	$("#year",navTab.getCurrentPanel()).attr("value",year);
	$("#month",navTab.getCurrentPanel()).attr("value",month);
	$("#empid",navTab.getCurrentPanel()).attr("value",empid);
	$("#person_id",navTab.getCurrentPanel()).attr("value",person_id);
	$("#cpny_id",navTab.getCurrentPanel()).attr("value",cpny_id);
	$("#STAT_NO",navTab.getCurrentPanel()).attr("value",STAT_NO);
	//$("#shifts",navTab.getCurrentPanel()).attr("value",shifts);
	navTabSearch(document.viewempcalendar_class);
}
    
</script>

<div class="pageContent">
	<div class="day">
		<div class="day_border">
		<div class="day_left_top">
		</div>
		<div class="day_left_bottom">
		</div>
		<div class="day_right_top">
		</div>
		<div class="day_right_bottom">
		</div>	
		<form id="viewempcalendar_class" action="/ar/attendanceSettings/viewClassCalendar" method="post" name="viewempcalendar_class">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
		<tr>
			<th class="day_table_left_th">
				 <!-- onclick="searchEmp()" --> 
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<a href="javascript:prev_viewempcalendar_class()" title="previous" class="day_arrow_left"></a>
					</td>
					<td>
					    <ait:date yearName="year" yearMinus="10" yearPlus="10" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/>
					    
					</td>
					<td>
						<a href="javascript:next_viewempcalendar_class()" title="next"  class="day_arrow_right"></a>
					</td>
					<td width="20">&nbsp;</td>

					<td><!-- 选择班次  --><spring:message code="ar.viewClassCalendar.XUANZEBANCI.b"/>：</td>
				    <td>
				  <%-- <select name="GROUP" id="GROUP">
				    <c:forEach items="${shiftNo}" var="item" varStatus="i">
					<option value="${item.SHIFTNO}" <c:if test="${GROUP eq item.SHIFTNO}">selected</c:if> >${item.SHIFTNO}</option>
				    </c:forEach>
				    </select> --%> 
				    <ait:SelectSyCodeByCpnyID name="GROUP" id="GROUP" parentNo="400223" onChangeName="navTabSearch($('#viewempcalendar_class',navTab.getCurrentPanel()));"
							cnpyID="${LoginUser.cpnyId}" selected="${GROUP}" />
				    
				    </td>
					<td>
						<input type="hidden" name="dwz.person.person_id" id="person_id1" value="${person_id}" readOnly lookupGroup="person"/>
			  			<input type="hidden" name="person_id" id="person_id" value="${person_id}"/>
						<input type="hidden" name="cpny_id" id="cpny_id" value="${cpny_id}"/>
					    <input type="hidden" name="STAT_NO" id="STAT_NO" value="${STAT_NO}"/>
					    <input type="hidden" name="MENU_CODE"  value="${MENU_CODE}"/>
					</td>
				</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<!-- <td class="day_table_right_line">
					<a class="bottona_a" onclick="f_create_ar_detail_class('viewempcalendar_class',DWZ.ajaxDone)"><span class="icon search_a"></span><span class="bottona_a_r">生成月历</span></a>
				</td> -->
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_viewempcalendar_class()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 搜索 --><spring:message code="ar.viewempcalender.title.search"/></span></a>
				</td>
				<td class="day_table_right_line">
					<c:if test="${toolbarInfo.UPDATER == '1'}">
						<a class="bottona_a" onclick="f_save_viewempcalendar_class()"><span class="icon edit_a"></span><span class="bottona_a_r"><!-- 保存 --><spring:message code="ar.viewempcalender.title.save"/></span></a>
					</c:if>
				</td>
				<td>
					<c:if test="${toolbarInfo.INSERTR == '1'}">
						<!-- <a class="bottona_a" onclick="f_create_ar_detail_class('viewempcalendar_class',DWZ.ajaxDone)"><span class="icon search_a"></span><span class="bottona_a_r">生成月历</span></a> -->
						<a class="bottona_a bottona_b" href="/ar/attendanceSettings/addCompanyCalendarViewBanCi" target="dialog" mask="true"><span class="icon add_a"></span><span class="bottona_a_r"><!-- 添加 --><spring:message code="button.add"/></span></a>
					</c:if>
				</td>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
			</form>
	<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="day_table">				
		<tr>
			<th ><b>Sun</b></th>
			<th >Mon</th>
			<th >Tues</th>
			<th >Wed</th>
			<th >Thur</th>
			<th >Fri</th>
			<th ><b>Sat</b></th>
		</tr>
		${calendarHtml} 
	</table>
	</div>
	</div>
</div>         
