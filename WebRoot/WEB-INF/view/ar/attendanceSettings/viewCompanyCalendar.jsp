<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	function f_search_viewcompanycalendar(){	
		var year = $("#year").val() ;
		var month = $("#month").val() ;
        document.viewcompanycalendar.action = '/ar/attendanceSettings/viewCompanyCalendar?year=' + year + "&month=" + month ;
		navTabSearch(document.viewcompanycalendar);
	}
    function f_update_viewcompanycalendar()
    {
    	var year = $("#year").val() ;
		var month = $("#month").val() ;
        document.viewcompanycalendar.action = '/ar/attendanceSettings/updateCompanyCalendarView?actionType=edit&year=' + year + "&month=" + month ;
        navTabSearch(document.viewcompanycalendar);
        //location.href = '/ar/attendanceSettings/updateCompanyCalendarView?actionType=edit&year=' + year + "&month=" + month ; 
    }

    function prev_viewcompanycalendar(){
    	var year = $("#year").val() ;
    	var month = $("#month").val() ;
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
    	
    	$("#year").attr("value",year);
    	$("#month").attr("value",month);
    	var year = $("#year").val() ;
		var month = $("#month").val() ;
        document.viewcompanycalendar.action = '/ar/attendanceSettings/viewCompanyCalendar?year=' + year + "&month=" + month ;
    	navTabSearch(document.viewcompanycalendar);
    }

    function next_viewcompanycalendar(){
    	var year = $("#year").val() ;
    	var month = $("#month").val() ;

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
    	$("#year").attr("value",year);
    	$("#month").attr("value",month);
    	var year = $("#year").val() ;
		var month = $("#month").val() ;
        document.viewcompanycalendar.action = '/ar/attendanceSettings/viewCompanyCalendar?year=' + year + "&month=" + month ;
		navTabSearch(document.viewcompanycalendar);
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
		<form id="viewcompanycalendar" action="/ar/attendanceSettings/viewCompanyCalendar?year=${year}&month=${month}" method="post" name="viewcompanycalendar">

		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
			<tr>
			<th class="day_table_left_th">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<a href="javascript:prev_viewcompanycalendar()" title="previous" class="day_arrow_left"></a>
						</td>
						<td>
							<ait:date yearName="year" yearMinus="10" yearPlus="10" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/>
						</td>
						<td>
							<a href="javascript:next_viewcompanycalendar()" title="next"  class="day_arrow_right"></a>
						</td>
					</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_viewcompanycalendar()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 查询 --><spring:message code="button.search"/></span></a>
				</td>
				<td>
					<c:if test="${toolbarInfo.INSERTR == '1'}">
						<a class="bottona_a bottona_b" href="/ar/attendanceSettings/addCompanyCalendarView" target="dialog" mask="true"><span class="icon add_a"></span><span class="bottona_a_r"><!-- 添加 --><spring:message code="button.add"/></span></a>
					</c:if>
				</td>
				<td>
					<c:if test="${toolbarInfo.UPDATER == '1'}">
						<a class="bottona_a" onclick="f_update_viewcompanycalendar()"><span class="icon edit_a"></span><span class="bottona_a_r"><!-- 修改 --><spring:message code="button.update"/></span></a>
					</c:if>
				</td>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
	
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
	</form>
	</div>
	</div>
</div>