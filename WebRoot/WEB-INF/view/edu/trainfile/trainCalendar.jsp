<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_search_traincalendar(){	
	navTabSearch(document.traincalendar);
}

function prev_traincalendar(){
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
	navTabSearch(document.traincalendar);
}

function next_traincalendar(){
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
	navTabSearch(document.traincalendar);

}
</script>
<form id="traincalendar" action="/edu/trainfile/trainCalendar" method="post" name="traincalendar" >
<div class="pageHeader" >
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
<div class="searchBar">
        <div style="margin-left:auto;margin-right:auto;width:97%;" > 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
			<tr>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
<%--				<td  class="day_table_right_line">--%>
<%--					<ait:date yearName="year" yearMinus="10" yearPlus="10" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/>--%>
<%--				</td>--%>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_traincalendar()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 查询 --><spring:message code="button.search"/></span></a>
				</td>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
		</div> 
	</div>
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
			<tr>
			<th class="day_table_left_th">
				<table border="0" cellpadding="0" cellspacing="0" align="center">
				<tr>
				<td>
					<a href="javascript:prev_traincalendar()" title="previous"  class="day_arrow_left"></a>
				</td>
				<td>
					<ait:date monthName="month" monthSelected="${request_month}" yearName="year" yearMinus="10" yearPlus="10" yearSelected="${request_year}" />
				</td>
				<td>
					<a href="javascript:next_traincalendar()" title="next"  class="day_arrow_right"></a>
				</td>
				</tr>
				</table>	
			</th>
			</tr>
		</table>
		
<div style="width: 97%; margin-left: auto;padding-top: 20px;">
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><b><spring:message code="ar.week.XINGQIRI.b"/></b></th><!--星期日-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><spring:message code="ar.week.XINGQIYI.b"/></th><!--星期一-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><spring:message code="ar.week.XINGQIER.b"/></th><!--星期二-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><spring:message code="ar.week.XINGQISAN.b"/></th><!--星期三-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><spring:message code="ar.week.XINGQISI.b"/></th><!--星期四-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><spring:message code="ar.week.XINGQIWU.b"/></th><!--星期五-->
			<th  width="14%" height="42" style="background:#EBEBEB; text-align:center;"><b><spring:message code="ar.week.XINGQILIU.b"/></b></th><!--星期六-->
		</tr>
		${calendarHtml} 
	</table>

</div>
</div>
</div>
</form>