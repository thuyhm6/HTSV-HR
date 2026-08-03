<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function changeNumber(num){
	/* alert(document.getElementById("BASIC_1").value); */
	var Basic=parseInt($("#BASIC_"+num,navTab.getCurrentPanel()).attr("value"));
	var FullAttend=parseInt($("#FULL_ATTEND_"+num,navTab.getCurrentPanel()).attr("value"));
	var Transportation=parseInt($("#TRANSPORTATION_"+num,navTab.getCurrentPanel()).attr("value"));
	var LongAttendance=parseInt($("#LONG_ATTENDANCE_"+num,navTab.getCurrentPanel()).attr("value"));
	var Gross= parseInt(Basic + FullAttend + Transportation + LongAttendance);
	var TotalAllowance= FullAttend + Transportation + LongAttendance;
	$("#Gross_"+num).html(Gross);
	$("#TotalAllowance_"+num).html(TotalAllowance);
	/* alert(FullAttend+"---"+Transportation+"---"+LongAttendance); */
	
	
}	
	function search(){
		$('#viewPaWorkerSalary').submit();
	}
	
	function checkStartMonthAndEndMonth(obj,num){

		if(obj.value.length == 6){
			var startMonthStr = $("#START_MONTH_"+num,navTab.getCurrentPanel()).val();
			var endMonthStr = obj.value;
			if(endMonthStr.substring(0,2) < startMonthStr.substring(0,2) && endMonthStr.substring(2,6) <= startMonthStr.substring(2,6)){
				alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
			}
			if(endMonthStr.substring(0,2) > startMonthStr.substring(0,2) && endMonthStr.substring(2,6) < startMonthStr.substring(2,6)){
				alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
			}
		}
	}
	
function updateAndSave(){
		
		var $form = $("#updatePaPayObjInfo");
		if (!$form.valid()) {
			return false;
		}
		var flag=false;
		$("input[name='c1']").each(function(){
			if($(this).attr("checked") == "checked"){
				flag = true;
			}
		});
		if(flag == false){
			alertMsg.error("<spring:message code='ar.viewPaArSummaryForManageList.QINGXUANZEBAOCUNSHUJU.b' />");//请选择要保存的数据
			return false;
		}
		
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: doAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}//,cancelCall:allCheckedInfo
	  	});

	}
	
function addAndSave() {
	var flag=true;
	$("td[sysLog='START_MONTH']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(this).attr("sysIndex") ;
		var startMonth = parseInt($("#START_MONTH_" + index,navTab.getCurrentPanel()).val());
		var newStartMonth = parseInt($("#HIDDEN_START_MONTH_" + index,navTab.getCurrentPanel()).val());
		
		if(startMonth == newStartMonth){
			flag = false;
		}
        
	});
	
	if(flag == false){
		alertMsg.error("<spring:message code='pa.viewPaMain.PaWorkerSalaryTable.StartMonthNotChange' />");//Start month not change, change it then save!
		return false;
	}
	
	$("td[sysLog='END_MONTH']",navTab.getCurrentPanel()).each(function(i, obj) {
		var index = $(this).attr("sysIndex");
		var endMonth = parseInt($("#END_MONTH_" + index,navTab.getCurrentPanel()).val());
		var newEndMonth = parseInt($("#HIDDEN_END_MONTH_" + index,navTab.getCurrentPanel()).val());
		
		if(endMonth == newEndMonth){
			flag = false;
		}
	}); 
	
	if(flag == false){
		alertMsg.error("<spring:message code='pa.viewPaMain.PaWorkerSalaryTable.EndMonthNotChange' />");//End month not change, change it then save!
		return false;
	}
	
	alertMsg.confirm("<spring:message code='org.title.IS_SELECT_EXECUTE' />",//确定要执行吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/pa/workManagement/addPaWorkerSalaryTable',
  				data:$('#updatePaPayObjInfo').serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: function(json){
  			  		DWZ.ajaxDone(json);
  			  		},
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
	}
</script>
<div class="pageHeader" onLoad="changeNumber">
	<form id="viewPaWorkerSalary" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaWorkerSalaryTable" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<span style="margin-left:3%;">
							<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
							<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}" limit="all"/>
						</span>
					</td>
					<td>
						<!-- 状态 -->
						<spring:message code="org.title.status" />
					</td>
					<td>
						<select id='seach_ACTIVITY' name='seach_ACTIVITY'>
						<option value = '1' <c:if test="${ACTIVITY eq 1}">selected="selected"</c:if>>
						<spring:message code="ess.affirmApply.title.remark.jinxingzhong" />
						</option>
						<option value = '0' <c:if test="${ACTIVITY eq 0}">selected="selected"</c:if>>
						<spring:message code="ess.affirmApply.title.remark.yiquxiao" />
						</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive"><div class="buttonContent" style = "height: 30px;"><button type="button" onclick="search()">
							<spring:message code="public.title.search"/><!--检索--></button></div></div>
					</li>
					<li>
						<a class="buttonActive"  onclick="updateAndSave();" href="#" >
							<span><!--Update And Save--><spring:message code="button.updateAndSave" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  onclick="addAndSave();" href="#" >
							<span><!--Add And Save--><spring:message code="button.addAndSave" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
<form id="updatePaPayObjInfo" method="post"
	action="/pa/workManagement/updatePaWorkerSalaryTable"
	class="pageForm required-validate">
	<table class="table"  id="daTable" width="100%" layoutH="171">
		<thead>
			<tr>
				<th width="20">
				    <input type="checkbox" class="checkboxCtrl" group="viewCheck" />
				</th>
				<!-- <th>No.</th> -->
				<th><!-- Division--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.Division" /></th>
				<th><!--Gross--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.Gross" /></th>
				<th><!-- Basic --> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.Basic" /></th>
				<th><!-- Total Allowance --> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.TotalAllowance" /></th>
				<th><!-- Full Attend --> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.FullAttend" /></th>
				<th><!-- Transportation--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.Transportation" /></th>
				<th><!-- Long Attendance--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.Long_Attendance" /></th>
				<th><!-- Start Month--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.StartMonth" /></th>
				<th><!-- End Month--> <spring:message code="pa.viewPaMain.PaWorkerSalaryTable.EndMonth" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${paWorkerSalaryTableList}" var="workerSalaryObj" varStatus="i">
			<script type="text/javascript">
				$(function (){
					changeNumber('${i.index+1}');
				});
			</script>
			<tr target=SEQ rel="${workerSalaryObj.SEQ}">
				<td>
					<input type="checkbox" id="c1${workerSalaryObj.SEQ }" name="c1" value="${workerSalaryObj.SEQ}" />
					<input type="hidden" name="SEQ_${workerSalaryObj.SEQ }" value="${workerSalaryObj.SEQ }"/>
					<input type="hidden" name="DIVISION_${workerSalaryObj.SEQ }" value="${workerSalaryObj.DIVISION }"/>
				</td>
				<td style="text-align:center" name="DIVISION_${workerSalaryObj.SEQ}" id="DIVISION_${i.index+1}" value="${workerSalaryObj.DIVISION }">
					<c:choose>
							<c:when test="${workerSalaryObj.DIVISION == '1'}">Probation</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '2'}">2~3 months</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '3'}">4~6 months</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '4'}">7~12 months</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '5'}">1 year ~</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '6'}">2 years ~</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '7'}">3 years ~</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '8'}">4 years ~</c:when>
							<c:when test="${workerSalaryObj.DIVISION == '9'}">5 years ~</c:when>
						</c:choose>
				</td>
				<td >
					<span id="Gross_${i.index+1}" style="color:red;"></span>
				</td>
				<td>
					<input name="BASIC_${workerSalaryObj.SEQ}" type="text"  style="text-align:left;" id="BASIC_${i.index+1}"
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
					maxlength="50" class="textInput" min="-99999999999999" value="${workerSalaryObj.BASIC}" onKeyUp="changeNumber('${i.index+1}')"/>
				</td>
				<td >
					<span id="TotalAllowance_${i.index+1}" style="color:red;"></span>
				</td>
				<td>
					<input name="FULL_ATTEND_${workerSalaryObj.SEQ}" type="text"  style="text-align:left;" id="FULL_ATTEND_${i.index+1}"
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
					maxlength="50" class="textInput" min="-99999999999999" value="${workerSalaryObj.FULL_ATTEND}" onKeyUp="changeNumber('${i.index+1}')"/>
				</td>
				<td>
					<input name="TRANSPORTATION_${workerSalaryObj.SEQ}" type="text"  style="text-align:left;" id="TRANSPORTATION_${i.index+1}"
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
					maxlength="50" class="textInput" min="-99999999999999" value="${workerSalaryObj.TRANSPORTATION}" onKeyUp="changeNumber('${i.index+1}')"/>
				</td>
				<td>
					<input name="LONG_ATTENDANCE_${workerSalaryObj.SEQ}" type="text"  style="text-align:left;" id="LONG_ATTENDANCE_${i.index+1}"
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
					maxlength="50" class="textInput" min="-99999999999999" value="${workerSalaryObj.LONG_ATTENDANCE}" onKeyUp="changeNumber('${i.index+1}')"/>
				</td>
				<td sysLog="START_MONTH" sysIndex="${i.index+1}">
					<ait:inputText name="START_MONTH_${workerSalaryObj.SEQ}" inputType="date" id="START_MONTH_${i.index+1}" 
						value="${workerSalaryObj.START_MONTH}" maxLength="6"  
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
						/>
					<input type="hidden" name="HIDDEN_START_MONTH" id="HIDDEN_START_MONTH_${i.index+1}" value="${workerSalaryObj.START_MONTH }"/>
				</td>
				<td sysLog="END_MONTH" sysIndex="${i.index+1 }">
					<ait:inputText name="END_MONTH_${workerSalaryObj.SEQ}" inputType="date" id="END_MONTH_${i.index+1}"  
						value="${workerSalaryObj.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${i.index+1}')"
						onChange="$('#c1${workerSalaryObj.SEQ }',navTab.getCurrentPanel()).attr('checked','checked');"
						/>
					<input type="hidden" name="HIDDEN_END_MONTH" id="HIDDEN_END_MONTH_${i.index+1}" value="${workerSalaryObj.END_MONTH }"/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCount == 0 }">
			<tr>
				<td style="text-align: left;" colspan="9"><!-- 没有查找的数据--> <spring:message code="pa.viewPaPayObj.MEIYOUCHAZHAODESHUJU.C" /></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	</form>
	<c:set value="/pa/workManagement/viewPaPayObj" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
