<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function f_search_viewempcalendar(){	
	var person_id = $("#person_id1",navTab.getCurrentPanel()).val();
	var stat_no = $("#stat_no1",navTab.getCurrentPanel()).val();
	 
	if (stat_no =='')
	{
	 stat_no = $("#STAT_NO").val();
	}
	 
	$("#person_id",navTab.getCurrentPanel()).attr("value",person_id);
	$("#STAT_NO",navTab.getCurrentPanel()).attr("value",stat_no);
	navTabSearch(document.viewempcalendar);
}

function f_save_viewempcalendar(navTabId){
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
            
           
			// jsonData += ' "DDATE_STR": "' + tempdate + this.value + '",';
			 jsonData += ' "DDATE_STR": "' +   this.title + '",';
			jsonData += ' "PERSON_ID": "' + $("#person_id",navTab.getCurrentPanel()).val() + '",';
			jsonData += ' "CPNY_ID": "' + $("#cpny_id",navTab.getCurrentPanel()).val() + '",';
			jsonData += ' "SHIFT_NO": "' + $("#SHIFT_NO_" + this.value).val() + '" ';


			jsonData += '}';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要修改的日期!
		alertMsg.error('<spring:message code="ar.alert.message.viewCompanyCalendar.choosedate"/>');
		return;
	}

	$.post("/ess/viewDept/updateEmpCalendarInfo",
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
				var $form = $("#viewempcalendar");
				var params = $("#viewempcalendar").serializeArray();
				
				navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
				return false;
			};
		}else{
			 //更新个人日历失败！
			 alertMsg.error("<spring:message code='ar.viewempcalender.title.updatefail'/>");
		}
	});
}

function prev_viewempcalendar(){
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
	navTabSearch(document.viewempcalendar);
}

function next_viewempcalendar(){
	var year = $("#year",navTab.getCurrentPanel()).val() ;
	var month = $("#month",navTab.getCurrentPanel()).val() ;
	var empid = $("#empid",navTab.getCurrentPanel()).val() ;
    var person_id = $("#person_id1",navTab.getCurrentPanel()).val();
    var cpny_id = $("#cpny_id",navTab.getCurrentPanel()).val();
    var STAT_NO = $("#STAT_NO",navTab.getCurrentPanel()).val();
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
	navTabSearch(document.viewempcalendar);
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
		<form id="viewempcalendar" action="/ess/viewDept/viewEmpCalendar" method="post" name="viewempcalendar">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
		<tr>
			<th class="day_table_left_th">
				 <!-- onclick="searchEmp()" --> 
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<a href="javascript:prev_viewempcalendar()" title="previous" class="day_arrow_left"></a>
					</td>
					<td>
						<ait:date yearName="year" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/>
					</td>
					<td>
						<a href="javascript:next_viewempcalendar()" title="next"  class="day_arrow_right"></a>
					</td>
					<td width="20">&nbsp;</td>
					<td>
				<input name="dwz.person.empId" type="hidden" value="${empid}" readOnly lookupGroup="person"/>
					</td>
				</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_viewempcalendar()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 搜索 --><spring:message code="ar.viewempcalender.title.search"/></span></a>
				</td></tr>
				</table>				
			</th>
			</tr>
		</table>
			</form>
		<table width="100%"  height="100%"  border="0"  cellpadding="0" cellspacing="0" class="day_table">			
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
