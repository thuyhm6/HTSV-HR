<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function f_search_viewDailyDetail(){	
	var person_id = $("#person_id1_dailydetail").val();
	$("#person_id_dailydetail").attr("value",person_id);
	navTabSearch(document.viewDailyDetail);
}

function f_save_viewDailyDetail(navTabId){
	var jsonData = '[';
	$(":checkbox").each(function()
	 {
		if (this.checked) {

			var tempdate = $("#year").val() + "-" + $("#month").val() + "-";

			if (jsonData.length > 1) {
				jsonData += ',{';
			}
			 else {
				jsonData += '{';
			}

			jsonData += ' "DDATE_STR": "' + tempdate + this.value + '",';
			jsonData += ' "PERSON_ID": "' + $("#person_id_dailydetail").val() + '",';
			jsonData += ' "CPNY_ID": "' + $("#cpny_id_dailydetail").val() + '",';
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

	$.post("/ar/attendanceMintenance/updateEmpCalendarInfo",
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
				var $form = $("#viewDailyDetail");
				var params = $("#viewDailyDetail").serializeArray();
				
				navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
				return false;
			};
		}else{
			 //更新个人日历失败！
			 alertMsg.error("<spring:message code='ar.viewempcalender.title.updatefail'/>");
		}
	});
}

function prev_viewDailyDetail(){
	var year = $("#viewDailyDetail").find("#year").val() ;
	var month = $("#viewDailyDetail").find("#month").val() ;
	var empid = $("#viewDailyDetail").find("#empid").val() ;
    var person_id = $("#person_id1_dailydetail").val();
    var cpny_id = $("#cpny_id_dailydetail").val();
    var STAT_NO = $("#STAT_NO_dailydetail").val();
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
	
	$("#viewDailyDetail").find("#year").attr("value",year);
	$("#viewDailyDetail").find("#month").attr("value",month);
	$("#viewDailyDetail").find("#empid").attr("value",empid);
	$("#person_id_dailydetail").attr("value",person_id);
	$("#cpny_id_dailydetail").attr("value",cpny_id);
	$("#STAT_NO_dailydetail").attr("value",STAT_NO);
	navTabSearch(document.viewDailyDetail);
}

function next_viewDailyDetail(){
	var year = $("#viewDailyDetail").find("#year").val() ;
	var month = $("#viewDailyDetail").find("#month").val() ;
	var empid = $("#viewDailyDetail").find("#empid").val() ;
    var person_id = $("#person_id1_dailydetail").val();
    var cpny_id = $("#cpny_id_dailydetail").val();
    var STAT_NO = $("#STAT_NO_dailydetail").val();
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
	$("#viewDailyDetail").find("#year").attr("value",year);
	$("#viewDailyDetail").find("#month").attr("value",month);
	$("#viewDailyDetail").find("#empid").attr("value",empid);
	$("#person_id_dailydetail").attr("value",person_id);
	$("#cpny_id_dailydetail").attr("value",cpny_id);
	$("#STAT_NO_dailydetail").attr("value",STAT_NO);
	navTabSearch(document.viewDailyDetail);
}
    
</script>

<div class="pageContent">
	<div class="day">
		<div class="day_right_border">
		<div class="day_bottom_border">
		<div class="day_right_top_corner">
		<div class="day_left_bottom_corner">
		<div class="day_right_bottom_corner">
		<form id="viewDailyDetail" action="/ar/attendanceMintenance/viewDailyDetail" method="post" name="viewDailyDetail">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
		<tr>
			<th class="day_table_left_th">
				 <!-- onclick="searchEmp()" --> 
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<a href="javascript:prev_viewDailyDetail()" title="previous" class="day_arrow_left"></a>
					</td>
					<td>
					<ait:date yearName="year" yearMinus="10" yearPlus="10" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/>
					</td>
					<td>
						<a href="javascript:next_viewDailyDetail()" title="next"  class="day_arrow_right"></a>
					</td>
					<td width="20">&nbsp;</td>
					<td>
						<!-- 工号 --><spring:message code="public.title.empId"/>：
					</td>
					<td>
						<input name="dwz.person.empId_dailydetail" type="text" value="${empid}" readOnly lookupGroup="person"/>
					</td>
					<td>
						<input type="hidden" name="dwz.person.person_id_dailydetail" id="person_id1_dailydetail" value="${person_id}" readOnly lookupGroup="person"/>
			  			<input type="hidden" name="person_id" id="person_id_dailydetail" value="${person_id}"/>
						<input type="hidden" name="cpny_id" id="cpny_id_dailydetail" value="${cpny_id}"/>
					    <input type="hidden" name="STAT_NO" id="STAT_NO_dailydetail" value="${STAT_NO}"/>
			 			<a class="btnLook" href="/ar/attendanceMintenance/viewDailyDetailList?pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
					<td>
						<span id="name" >${name}( ${deptname} )</span>
					</td>
				</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_viewDailyDetail()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 搜索 --><spring:message code="ar.viewempcalender.title.search"/></span></a>
				</td>
				<%--
				<td class="day_table_right_line">
					<c:if test="${toolbarInfo.UPDATER == '1'}">
						<a class="bottona_a" onclick="f_save_viewDailyDetail()"><span class="icon edit_a"></span><span class="bottona_a_r"><!-- 保存 --><spring:message code="ar.viewempcalender.title.save"/></span></a>
					</c:if>
				</td>
				 --%>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
			</form>
		<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="day_table">			
		<tr>
			<th><b>Sun</b></th>
			<th>Mon</th>
			<th>Tues</th>
			<th>Wed</th>
			<th>Thur</th>
			<th>Fri</th>
			<th><b>Sat</b></th>
		</tr>
		${calendarHtml} 
	</table>
   </div>
	</div>
	</div>
	</div>
	</div>
	</div>
</div>         
