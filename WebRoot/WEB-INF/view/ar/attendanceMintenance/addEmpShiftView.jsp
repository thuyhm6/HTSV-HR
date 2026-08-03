<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	function addempshiftview_sub(form,callback){
		if(document.addempshiftview.FROM_DATE.value == '' || document.addempshiftview.TO_DATE.value == ''){
			//开始或结束时间不能为空！
			alert("<spring:message code='ar.alert.message.viewCompanyCalendar.datenull'/>");
			return false;
		}
		var str = "";
		$("#addempshiftview").find(":radio[name='group']").each(function() {
		//$("#layout1 input:radio").each(function() {
			if (this.checked) {
				str = this.value;
			}
		});
		
		var strBan = "";
		var length = document.addempshiftview.shift_s.length;
		for (var i = 0; i < length; i++){
			if (i == length - 1){
				strBan += document.addempshiftview.shift_s.options[i].value;
			}else{
				strBan += document.addempshiftview.shift_s.options[i].value + "-";
			}
		}
		var fromDate = document.addempshiftview.FROM_DATE.value;
		var toDate = document.addempshiftview.TO_DATE.value;
        var formatFormDate = fromDate.substring(6,10)+fromDate.substring(3,5)+fromDate.substring(0,2);
        var formatToDate = toDate.substring(6,10)+toDate.substring(3,5)+toDate.substring(0,2);
		if (str == "1"){
			if (formatFormDate > formatToDate){
				//结束时间应大于等于起始时间
				alert("<spring:message code='ar.alert.message.viewCompanyCalendar.endafterstart'/>");
				return false;
			}
			if (document.addempshiftview.temp_personId.value == ""){
				//选择员工
				alert("<spring:message code='ar.alert.message.addempshift.chooseperson'/>");
				return false;
			}
			var options = {
				url: '/ar/attendanceMintenance/addEmpShift?shift_no=' + strBan + '&empids=' + document.addempshiftview.temp_personId.value,
				type: 'POST',
				success: function(responseText) {
					if (responseText.returnString == "Y"){
						//保存成功!
						alert("<spring:message code='ar.alert.message.addempshift.success'/>"+"<spring:message code='ar.addEmpShiftView.YICHENGGONGPAIBAN.b'/>"+responseText.count+"<spring:message code='hrm.empinfo.people'/>。");				
						new function(){
							var $form = $("#addempshiftview");
							var params = $("#addempshiftview").serializeArray();
							
							navTab.reload($form.attr('action'), {data: params, navTabId:'navTabId'});
							return false;
						};
					}else{
						//员工安排班次失败
						alert("<spring:message code='ar.alert.message.addempshift.fail'/>");
					}
				}
			};
			$('#addempshiftview').ajaxSubmit(options);
			return false;
		} else if (str == "2"){
			if (formatFormDate > formatToDate){
				//结束时间应大于等于起始时间
				alert("<spring:message code='ar.alert.message.viewCompanyCalendar.endafterstart'/>");
				return false;
			}
			if (document.addempshiftview.DEPTID.value == ""){
				//请选择部门
				alert("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
				return false;
			}
			var sonDeptFlag = "";
			if(document.getElementById('isIncludeSonDept_SHIFT').checked){
				sonDeptFlag = "YES";
			}else{
				sonDeptFlag = "NO";
			}
			// alert(emp_type_code);
			var options = {
				url: '/ar/attendanceMintenance/addEmpShiftByDeptId?shift_no=' + strBan + '&dept=' 
					+ document.addempshiftview.DEPTID.value+'&sonDeptFlag='+sonDeptFlag,
				type: 'POST',
				success: function(responseText) {
					if (responseText.returnString == "Y"){
						//保存成功!
						alert("<spring:message code='ar.alert.message.addempshift.success'/>"+"<spring:message code='ar.addEmpShiftView.YICHENGGONGPAIBAN.b'/>"+responseText.count+"<spring:message code='hrm.empinfo.people'/>。");
						new function(){
							var $form = $("#addempshiftview");
							var params = $("#addempshiftview").serializeArray();
							navTab.reload($form.attr('action'), {data: params, navTabId:'navTabId'});
							return false;
						};
					}else{
						//部门安排班次失败
						alert("<spring:message code='ar.alert.message.addempshift.deptfail'/>");
					}
				}
			};
			$('#addempshiftview').ajaxSubmit(options);
			return false;
		} else if (str == "3"){
			var dynamicGroupNo = "";
			for (var i = 0; i < document.addempshiftview.dynamicGroup.length; i++){
				if (document.addempshiftview.dynamicGroup.options[i].selected){
					dynamicGroupNo = document.addempshiftview.dynamicGroup.options[i].value;
				}
			}
			if (formatFormDate > formatToDate){
				//结束时间应大于等于起始时间
				alert("<spring:message code='ar.alert.message.viewCompanyCalendar.endafterstart'/>");
				return false;
			}
			if (dynamicGroupNo == ""){
				//请选择动态组
				alert("<spring:message code='ar.alert.message.addempshift.choosedynamicgroup'/>");
				return false;
			}
			var options = {
				url: '/ar/attendanceMintenance/addEmpShiftBydynamicGroup?shift_no=' + strBan + '&dynamicGroupNo=' + dynamicGroupNo,
				type: 'POST',
				success: function(responseText) {
					if (responseText.returnString == "Y"){
						//保存成功!
						alert("<spring:message code='ar.alert.message.addempshift.success'/>"+"<spring:message code='ar.addEmpShiftView.YICHENGGONGPAIBAN.b'/>"+responseText.count+"<spring:message code='hrm.empinfo.people'/>。");
						new function(){
							var $form = $("#addempshiftview");
							var params = $("#addempshiftview").serializeArray();
							navTab.reload($form.attr('action'), {data: params, navTabId:'navTabId'});
							return false;
						};
					}else{
						//动态组安排班次失败
						alert("<spring:message code='ar.alert.message.addempshift.dynamicgroupfail'/>");
					}
				}
			};
			$('#addempshiftview').ajaxSubmit(options);
			return false;
		}else if (str == "4"){ 
			/*for (var i = 0; i < document.addempshiftview.dynamicGroup.length; i++){
				if (document.addempshiftview.dynamicGroup.options[i].selected){
					dynamicGroupNo = document.addempshiftview.dynamicGroup.options[i].value;
				}
			}*/
			var calssShiftNo =$('#calssShiftNo').val();
			if (formatFormDate > formatToDate){
				//结束时间应大于等于起始时间
				alert("<spring:message code='ar.alert.message.viewCompanyCalendar.endafterstart'/>");
				return false;
			}
			if (calssShiftNo == ""){
				//请选择班组
				alert("<spring:message code='ar.addEmpShiftView.QINGXUANZEBANZU.b'/>");
				return false;
			}
		 	var options = {
				url: '/ar/attendanceMintenance/addEmpShiftByClassNum?shift_no=' + strBan + '&calssShiftNo=' + calssShiftNo,
				type: 'POST',
				success: function(responseText) {
					if (responseText.returnString == "Y"){
						//保存成功!已排班*人
						alert("<spring:message code='ar.alert.message.addempshift.success'/>");
						new function(){
							var $form = $("#addempshiftview");
							var params = $("#addempshiftview").serializeArray();
							navTab.reload($form.attr('action'), {data: params, navTabId:'navTabId'});
							return false;
						};
					}else{
						//动态组安排班次失败
						alert("<spring:message code='ar.alert.message.addempshift.dynamicgroupfail'/>");
					}
				}
			};
			$('#addempshiftview').ajaxSubmit(options); 
			return false;
		}
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
		var temp_input_personId = $("#temp_personId").val()!=""?$("#temp_personId").val().split(","):new Array();
		//回传值
		var oldpersonId = $("#personId").val()!=""?$("#personId").val().split(","):new Array();
		var oldempId = $("#empId").val()!=""?$("#empId").val().split(","):new Array();
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
						$("#temp_personId").attr("value",$("#temp_personId").val()+","+oldpersonId[k]);
						temp_div_name+="<div id='"+oldpersonId[k]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[k]+"("+oldempId[k]+")</div>";
					}
				} 
				$("#addempshift_name").append(temp_div_name);
			}
			
		}else{
			if(oldpersonId.length > 0){
				for(i = 0 ; i < oldempName.length ; i++){
					temp_div_name+="<div id='"+oldpersonId[i]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[i]+"("+oldempId[i]+")</div>";
				} 
				$("#temp_personId").attr("value",$("#personId").val());
				$("#addempshift_name").append(temp_div_name);
			} 
		}
		
	}

	function removetd(data) {
		$("#" + data.id).remove();
		var oldemp = $("#temp_personId").val().split(",");
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
		$("#temp_personId").attr("value",tempId);
	}

	function up(){
		var temp;
		var text1;
		var value1;
		for(var i=0;i<document.addempshiftview.shift_s.length;i++){
			if(document.addempshiftview.shift_s.length==1){ 
				//无法执行上移操作
				alert("<spring:message code='ar.alert.message.addempshift.noup'/>");
				return false;
			}
			if(document.addempshiftview.shift_s.options[i].selected){
				if(i==0)
				{ 
				//无法执行上移操作
				alert("<spring:message code='ar.alert.message.addempshift.noup'/>");
				return false;
				}
				temp=i;
				text1=document.addempshiftview.shift_s.options[i-1].text;
				value1=document.addempshiftview.shift_s.options[i-1].value;
				document.addempshiftview.shift_s.options[temp-1]=new Option(document.addempshiftview.shift_s.options[i].text,document.addempshiftview.shift_s.options[i].value);
				document.addempshiftview.shift_s.options[i]=new Option(text1,value1);
	
			}
		}
	}
	function down(){
		var temp;
		var text1;
		var value1;
		for(var i=0;i<document.addempshiftview.shift_s.length;i++){
			if(document.addempshiftview.shift_s.length==1){
				//无法执行下移操作
				alert("<spring:message code='ar.alert.message.addempshift.nodown'/>");
				return false;
			}
			if(document.addempshiftview.shift_s.options[i].selected){
				if(i==document.addempshiftview.shift_s.length-1){ 
					//无法执行下移操作
					alert("<spring:message code='ar.alert.message.addempshift.nodown'/>");
					return false;
				}
				temp=i;
				text1=document.addempshiftview.shift_s.options[i+1].text;
				value1=document.addempshiftview.shift_s.options[i+1].value;
				document.addempshiftview.shift_s.options[temp+1]=new Option(document.addempshiftview.shift_s.options[i].text,document.addempshiftview.shift_s.options[i].value);
				document.addempshiftview.shift_s.options[i]=new Option(text1,value1);
			}
		}
	}
	function add(){
		var length=document.addempshiftview.shift_s.length;
		for(var i=0;i<document.addempshiftview.shifts.length;i++){
			if(document.addempshiftview.shifts.options[i].selected){
				document.addempshiftview.shift_s.options[length]=new Option(document.addempshiftview.shifts.options[i].text,document.addempshiftview.shifts.options[i].value);
			}
		}
	}
	function del(){
		var length = document.addempshiftview.shift_s.length;
		for(var i=0;i<length;i++){
			if(document.addempshiftview.shift_s.options[i].selected){
				document.addempshiftview.shift_s.options[i]=null;
				if(length != 1){
					document.addempshiftview.shift_s.options[i-1].selected=true;
				}
			}
		}
	}
	function check_this(rValue){
		if(rValue == '1'){
			document.getElementById("shift_d1").style.display = 'block';
			document.getElementById("shift_d2").style.display = 'none';
			document.getElementById("shift_d3").style.display = 'none';
			document.getElementById("shift_d4").style.display = 'none';
		}
		if(rValue == '2'){
			document.getElementById("shift_d2").style.display = 'block';
			document.getElementById("shift_d1").style.display = 'none';
			document.getElementById("shift_d3").style.display = 'none';
			document.getElementById("shift_d4").style.display = 'none';
		}
		if(rValue == '3'){
			document.getElementById("shift_d3").style.display = 'block';
			document.getElementById("shift_d1").style.display = 'none';
			document.getElementById("shift_d2").style.display = 'none';
			document.getElementById("shift_d4").style.display = 'none';
		}
		if(rValue == '4'){
			document.getElementById("shift_d4").style.display = 'block';
			document.getElementById("shift_d1").style.display = 'none';
			document.getElementById("shift_d2").style.display = 'none';
			document.getElementById("shift_d3").style.display = 'none';
		}
	}
	function exportArShiftExcel(obj){
		var FROM_DATE = document.addempshiftview.FROM_DATE.value;
		var TO_DATE = document.addempshiftview.TO_DATE.value;
	    if(FROM_DATE == '' || TO_DATE == ''){
		    //请选择开始结束时间
			alert("<spring:message code='ar.addEmpShiftView.QINGXUANZEKAISHIJIESHUSHIJIAN.b'/>");
			return false;
	    }
		document.getElementById("exportArShiftExcel").href="/pa/excelExport/exportArShiftExcel?FROM_DATE="+FROM_DATE+"&TO_DATE="+TO_DATE;
		document.getElementById("exportArShiftExcel").click();
	
	}
 	function  getofvalues(){
 		var $form = $("#addempshiftview");
 		var shiftYear = $form.find("#shiftYear").val();
 		var shiftMonth = $form.find("#shiftMonth").val();

   		var a = document.getElementsByName("group");
		for(var i=0;i<a.length;i++){
		   if(a[i].checked){
			   a = a[i].value;
		   }
	   	}
   		var s = document.getElementsByName("dynamicGroup");
   		for(var j=0;j<s.length;j++){
		   	if(s[j].checked){
			   s = s[j].value;
		   	}
   		}
	   	var url="/pa/excelExport/exportArShiftExcelModel";
	   	if(0==s.length){
	   	   	
	   	}else{
		   url=url +(url.indexOf('?') == -1 ? "?" : "&") ;
		   for(var i = 0;i<s.length;i++){
				url+="dynamicGroup="+s[i].value+"&";
			}
	   	}
		window.location =url+"group="+a+"&shiftYear="+shiftYear+"&shiftMonth="+shiftMonth;
	 }
 	
 	function excelimport_ar0602(){
 		$("#importExcelDialog_ar0602").attr('href','/pa/excelImport/importExcelData?importFunName=/importEmpCalendarExcel');
 		$("#importExcelDialog_ar0602").click();
 	}
</script>
<a id="importExcelDialog_ar0602"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ar0602" href="#" target="navTab" mask="true"><span style="display:none;"><!-- 导入结果 --><spring:message code="ess.title.DAORUJIEGUO" /></span></a>
<div class="pageContent">
	<form id="addempshiftview" name="addempshiftview" method="post" action="/ar/attendanceMintenance/addEmpShiftView">
	<div class="panel">
		<h1><!-- 班次信息 --><spring:message code="ar.addempshift.title.banciinfo"/></h1>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
				<tr>
				    <td class="td_title"><!-- 选择类型 --><spring:message code="ar.addempshift.title.choosetype"/></td>
				    <td class="td_type">
					    <input type="radio" value="1" name="group" id="group" checked="checked" onclick="check_this(this.value)"/>&nbsp;<!-- 按员工排班 --><spring:message code="ar.addempshift.title.byperson"/>
				     	<input type="radio" value="2" name="group" id="group" onclick="check_this(this.value)"/>&nbsp;<!-- 按部门排班 --><spring:message code="ar.addempshift.title.bydept"/>
				     	<input type="radio" value="3" name="group" id="group" onclick="check_this(this.value)"/>&nbsp;<!-- 按动态组排班 --><spring:message code="ar.addempshift.title.bygroup"/>
				     	<input type="radio" value="4" name="group" id="group" onclick="check_this(this.value)"/>&nbsp;<!-- 按班组排班 --><spring:message code="ar.addempshift.title.byclass"/>
				     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				     	<spring:message code="ar.addEmpShiftView.MOBANPAIBANYUEFEN.b"/><!-- 模板排班月份 -->：<ait:date yearName="shiftYear" monthName="shiftMonth" />
				    </td>
				    <td width="30%" style="padding:4px;">
		      			<input type="hidden" id="empids" name="empids" value=""/>
				        <input type="hidden" id="shift_no" name="shift_no" value=""/>
				        <c:if test="${toolbarInfo.INSERTR == '1'}">
					    <li>
							<a class="buttonActive"
								onclick="addempshiftview_sub('addempshiftview','navTabAjaxDone');"><span><!-- 提交 --><spring:message code="submit"/></span>
							</a>
						</li>
						<li>
							<%-- <a class="buttonActive" onclick="getofvalues()"
								><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
							</a> --%>
							<a class="buttonActive" href="/pa/excelExport/downloadExcelEmpCalendarTemplate?file=EmpCalendar_Temp">
								<span><spring:message code="ar.addempshift.title.downloadmodule" /><!-- 下载导入模板 --></span>
							</a>
						</li>
						<li>
							<%-- <a class="buttonActive"
								href="/pa/excelImport/importInsuranceInputItemData?
								&importFunName=importArShiftExcel" target="dialog" mask="true" width="400" height="200" ><span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span>
							</a> --%>
							<a class="buttonActive" onclick="excelimport_ar0602()"><span><!--Excel导入--><spring:message code="ess.infoApply.EXCEL_IN" /></span></a>
						</li>	
					    <li>
							<a id="exportArShiftExcel" class="buttonActive"
								onclick="exportArShiftExcel(this);"><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
							</a>
						</li>
						</c:if>
		      		</td>
				  </tr>
				  <tr>
				    <td class="td_title"><!-- 排班时间 --><spring:message code="ar.addempshift.title.paibantime"/></td>
				    <td class="td_type">
				    	<table width="65%">
					      	<tr>
					      		<td width="10%" style="padding:4px;">
					      			<!-- 开始 --><spring:message code="ar.viewshift.title.start"/>：
					      		</td>
					      		<td width="40%" style="padding:4px;">
					      		    <input id="FROM_DATE" type="text" name="FROM_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					      			<!--<input id="FROM_DATE" type="text" name="FROM_DATE" class="date required" readonly="true"/>-->
					      		</td>
					      		<td width="10%" style="padding:4px;">
					      			<!-- 结束 --><spring:message code="ar.viewshift.title.end"/>：
					      		</td>
					      		<td width="40%" style="padding:4px;">
					      		    <input id="TO_DATE" type="text" name="TO_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					      			<!--<input id="TO_DATE" type="text" name="TO_DATE" class="date required" readonly="true"/>-->
					      		</td>
					      	</tr>
					      </table>
				    </td>
				  </tr>
			 </table>
		</div>
	</div>
	<div style="clear:both;"></div>
	<div id="shift_d1" class="panel" style="display:block;">
		<h1><!-- 排班方式 --><spring:message code="ar.addempshift.title.paibantype"/></h1>
		<div>
			 <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				 <tr>
					<td class="td_title" width="30%">
						<!-- 排班人员 --><spring:message code="ar.addempshift.title.paibanperson"/>
					</td> 
					<td class="td_type" width="70%">
						<div id="addempshift_name" name="addempshift_name" ></div>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<!-- 工号 --><spring:message code="public.title.empId"/>
					</td>
					<td class="td_type">
						<input id="jsonData" name="jsonData" value="" type="hidden"/>
						<input id="temp_personId" name="temp_personId" value="" type="hidden"/>
						  
						<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
						<input id="empId" name="dwz.person.empId" type="hidden" class="required"  readOnly lookupGroup="person"/>
						<input id="empName" name="dwz.person.empName" value="" type="hidden" lookupGroup="person"/>
						<a class="btnLook" href="/ar/attendanceMintenance/viewShiftEmpList?pageNum=1&supervisor=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="shift_d2" class="panel" style="display:none;">
		<h1><!-- 排班方式 --><spring:message code="ar.addempshift.title.paibantype"/></h1>
		<div>
			 <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				 <tr>
					<td class="td_title">
						<!-- 部门 --><spring:message code="public.title.deptName"/>
					</td> 
					<td class="td_type">
						<ait:deptTree name="DEPTID" limit="ar"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="isIncludeSonDept_SHIFT" name="isIncludeSonDept_SHIFT"  
	            			title="<spring:message code='hr.viewCondSql.title.BAOHANZIBUMEN'/>"/><!-- 包含子部门  -->
	            			<spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/> 
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="shift_d3" class="panel" style="display:none;">
		<h1><!-- 排班方式 --><spring:message code="ar.addempshift.title.paibantype"/></h1>
		<div>
			 <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				 <tr>
					<td class="td_title">
						<!-- 动态组 --><spring:message code="ar.addempshift.title.dynamicgroup"/>
					</td> 
					<td class="td_type">
			           <select name="dynamicGroup" id="dynamicGroup" size="10" style="width:18% ">
				          	<c:forEach items="${dynamicGroupList}" var="Group">
				          		<option value="${Group.GROUP_NO}">
				          			${Group.GROUP_NAME}
				          		</option>
				          	</c:forEach>
				       </select>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="shift_d4" class="panel" style="display:none;">
		<h1><!-- 排班方式 --><spring:message code="ar.addempshift.title.paibantype"/></h1>
		<div>
			 <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				 <tr>
					<td class="td_title" >
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</td> 
					<td class="td_type">
			            
				         <ait:SelectSyCodeByCpnyID  name="calssShiftNo" id="calssShiftNo" parentNo="400223"
							cnpyID="${LoginUser.cpnyId}"  />
				        
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="clear:both;"></div>
	<div class="panel">
		<h1><!-- 班次列表 --><spring:message code="ar.viewshift.title.banciliebiao"/></h1>
		<div>
			 <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="user_table">
				 <tr>
					<td class="td_title">
						<!-- 班次名称 --><spring:message code="ar.addempshift.title.banciname"/>
					</td> 
					<td class="td_type" width="45%">
						<select name="shifts" size="10" style="width:100% ">
				          	<c:forEach items="${shiftsList}" var="shifts">
				          		<option value="${shifts.SHIFT_NO}">
				          			${shifts.SHIFT_NAME}
				          		</option>
				          	</c:forEach>
				        </select>
					</td>
					<td class="td_type" width="5%">
						<input class="l-button" style="float:left; margin-left:10px; margin-right:10px;" type="button" value="<spring:message code='button.add'/>--＞" id="Button3" onClick="add()"/><br/>
	          	  		<input class="l-button" style="float:left; margin-left:10px; margin-right:10px;" type="button" value="＜--<spring:message code='button.delete'/>" id="Button4" onClick="del()"/>
					</td>
					<td class="td_type" width="45%">
						<select name="shift_s" size="10" style="width:100% "></select>
					</td>
					<td class="td_type" width="5%">
						<input class="l-button" style="float:left; margin-left:10px; margin-right:10px;" type="button" value="<spring:message code='ar.addempshift.title.up'/>" id="Button2" onClick="up()"/>
	          	  		<input class="l-button" style="float:left; margin-left:10px; margin-right:10px;" type="button" value="<spring:message code='ar.addempshift.title.down'/>" id="Button5" onClick="down()"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</form>
</div>