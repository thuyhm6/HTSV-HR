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

function search_viewempcalendar_noEmp(){	
    var newHref = '/ar/attendanceMintenance/viewEmpCalendar?NO_EMP=Y';
    var bakHref = '/ar/attendanceMintenance/viewEmpCalendar';
	document.viewempcalendar.action = newHref;
	navTabSearch(document.viewempcalendar);
}

function f_save_viewempcalendar(navTabId){
	var jsonData = '[';
	$("input[name='day']:checkbox:checked",navTab.getCurrentPanel()).each(function()
	 {
		if (this.checked) {

			var tempdate = $("#year",navTab.getCurrentPanel()).val() + "-" + $("#month",navTab.getCurrentPanel()).val() + "-";

			var persons = $("#person_id",navTab.getCurrentPanel()).val().split(",");
            for(var i = 0;i<persons.length;i++){
            	if (jsonData.length > 1) {
    				jsonData += ',{';
    			}
    			 else {
    				jsonData += '{';
    			}
                
    			// jsonData += ' "DDATE_STR": "' + tempdate + this.value + '",';
    			 jsonData += ' "DDATE_STR": "' +   this.title + '",';
    			jsonData += ' "PERSON_ID": "' + persons[i] + '",';
    			jsonData += ' "CPNY_ID": "' + $("#cpny_id",navTab.getCurrentPanel()).val() + '",';
    			jsonData += ' "REMARK": "' + $("#REMARK",navTab.getCurrentPanel()).val() + '",';
    			jsonData += ' "SHIFT_NO": "' + $("#SHIFT_NO_" + this.value).val() + '",';
    			jsonData += ' "DATE_TYPE": "' + $("#DATE_TYPE_" + this.value).val() + '" ';

    			jsonData += '}';
            }
			
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
	if('Y' == '${NO_EMP}'){
		var newHref = '/ar/attendanceMintenance/viewEmpCalendar?NO_EMP=Y';
		document.viewempcalendar.action = newHref;
    }
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
	if('Y' == '${NO_EMP}'){
		var newHref = '/ar/attendanceMintenance/viewEmpCalendar?NO_EMP=Y';
		document.viewempcalendar.action = newHref;
    }
	navTabSearch(document.viewempcalendar);
}

function change_name(){

	var thisvalue = $("#empName",navTab.getCurrentPanel()).val();
	//拼接字符串
	var personId_str="";
	var empId_str="";
	var empName_str="";
	//显示DIV
	var temp_div_name="";
	//隐藏值
	var temp_input_personId = $("#temp_personId",navTab.getCurrentPanel()).val()!=""?$("#temp_personId",navTab.getCurrentPanel()).val().split(","):new Array();
	//回传值
	var oldpersonId = $("#personId",navTab.getCurrentPanel()).val()!=""?$("#personId",navTab.getCurrentPanel()).val().split(","):new Array();
	var oldempId = $("#empId",navTab.getCurrentPanel()).val()!=""?$("#empId",navTab.getCurrentPanel()).val().split(","):new Array();
	var oldempName = thisvalue != "" ? thisvalue.split(",") : new Array();

	//根据隐藏值 嵌套回传值 循环
	if(temp_input_personId.length > 0){
		if(oldpersonId.length > 0){
			for(k = 0 ; k < oldpersonId.length ; k++){
				//boolean
				var flag = true ;
				for(j = 0 ; j < temp_input_personId.length ; j++){
					if(temp_input_personId[j] == oldpersonId[k]){
						flag=false;
						break;
					}
				} 
				if(flag){
					$("#temp_personId",navTab.getCurrentPanel()).attr("value",$("#temp_personId",navTab.getCurrentPanel()).val()+","+oldpersonId[k]);
					$("#person_id",navTab.getCurrentPanel()).attr("value",$("#temp_personId",navTab.getCurrentPanel()).val()+","+oldpersonId[k]);
					temp_div_name+="<div id='"+oldpersonId[k]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[k]+"("+oldempId[k]+")</div>";
				}
			} 
			$("#addempshift_name",navTab.getCurrentPanel()).append(temp_div_name);
		}
		
	}else{
		if(oldpersonId.length > 0){
			for(i = 0 ; i < oldempName.length ; i++){
				temp_div_name+="<div id='"+oldpersonId[i]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[i]+"("+oldempId[i]+")</div>";
			} 
			$("#temp_personId",navTab.getCurrentPanel()).attr("value",$("#personId").val());
			$("#person_id",navTab.getCurrentPanel()).attr("value",$("#personId").val());
			$("#addempshift_name",navTab.getCurrentPanel()).append(temp_div_name);
		} 
	}
	
}

function removetd(data) {
	$("#" + data.id).remove();
	var oldemp = $("#temp_personId",navTab.getCurrentPanel()).val().split(",");
	var newemp = new Array();
	var tempId = ""
	for (var i = 0; i < oldemp.length; i++) {
		if (oldemp[i] != data.id){
			newemp.push(oldemp[i]);
		}
	}
	for (var i = 0; i < newemp.length; i++) {
		if (i == newemp.length - 1) {
			tempId += newemp[i];
		}else{
			tempId += newemp[i] + ",";
		}
	}
	$("#temp_personId",navTab.getCurrentPanel()).attr("value",tempId);
	$("#person_id",navTab.getCurrentPanel()).attr("value",tempId);
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
		<form id="viewempcalendar" action="/ar/attendanceMintenance/viewEmpCalendar" method="post" name="viewempcalendar">
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
					<c:if test="${NO_EMP ne 'Y'}">
					<td>
						<!-- 工号 --><spring:message code="public.title.empId"/>：
					</td>
					<td>
						<input name="dwz.person.empId" type="text" value="${empid}" readOnly lookupGroup="person"/>
					</td>
					</c:if>
					<td>
					    <input id="temp_personId" name="temp_personId" value="" type="hidden"/>
					    <input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
					    <input id="empId" name="dwz.person.empId" type="hidden" class="required"  readOnly lookupGroup="person"/>
						<input id="empName" name="dwz.person.empName" value="" type="hidden" lookupGroup="person"/>
						
						<input type="hidden" name="dwz.person.person_id" id="person_id1" value="${person_id}" readOnly lookupGroup="person"/>
			  			<input type="hidden" name="person_id" id="person_id" value="${person_id}"/>
						<input type="hidden" name="cpny_id" id="cpny_id" value="${cpny_id}"/>
						<input type="hidden" name="dwz.person.stat_no" id="stat_no1" value="${stat_no}" readOnly lookupGroup="person"/> 
					    <input type="hidden" name="STAT_NO" id="STAT_NO" value="${STAT_NO}"/>
					    <c:if test="${NO_EMP eq 'Y'}">
					    	<a class="btnLook" href="/ar/attendanceMintenance/viewShiftEmpList?pageNum=1&supervisor=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					    </c:if>
					    <c:if test="${NO_EMP ne 'Y'}">
			 				<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=ar&pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					    </c:if>
					</td>
					<td>
					    <c:if test="${NO_EMP ne 'Y'}">
					    	<span id="name" >${name }( ${deptname } )</span>
					    </c:if>
					</td>
					<td>/
					<!-- Remark--><spring:message code="hrm.empinfo.REMARK"/>:
					</td>
					<td>
						<input type="text" name="REMARK" id="REMARK" size="50"/>
					</td>
				</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_search_viewempcalendar()"><span class="icon search_a"></span><span class="bottona_a_r"><!-- 搜索 --><spring:message code="ar.viewempcalender.title.search"/></span></a>
				</td>
				<input type="checkbox" class="checkboxCtrl" group="day" />&nbsp;&nbsp;&nbsp;<!-- 全选 --><spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<td class="day_table_right_line">
					<c:if test="${toolbarInfo.UPDATER eq '1'}">
						<a class="bottona_a" onclick="f_save_viewempcalendar()"><span class="icon edit_a"></span><span class="bottona_a_r"><!-- 保存 --><spring:message code="ar.viewempcalender.title.save"/></span></a>
					</c:if>
				</td>
				<td class="day_table_right_line">
					<c:if test="${toolbarInfo.UPDATER eq '1'}">
						<a class="bottona_a" onclick="search_viewempcalendar_noEmp()"><span class="icon edit_a"></span><span class="bottona_a_r"><!-- 员工批量修改 --><spring:message code="ar.viewEmpCalendar.Update_For_Batch_By_Persons.b"/></span></a>
					</c:if>
				</td>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
			</form>
	    <c:if test="${NO_EMP eq 'Y'}">
			<div id="addempshift_name" name="addempshift_name" ></div>
		</c:if>
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
