<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(function(){
	$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 490,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
	});
	
	$('.list tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("input[name='SINGLE_LEAVE']",navTab.getCurrentPanel()).attr("checked",true);
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	
});


function submitKeyClick_recAffirm(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(16);
		var personIdStr="AFFIRMOR_IDApplyLeave"+empIdStr.substring(17);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
							if(jsonObject.perCnt != 1 ){
								document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
										+'&seach_KEY='+empid
										+'&empidStr='+empIdStr
										+'&personidStr='+personIdStr
										));
								document.getElementById("onck").click();
							}
							if(jsonObject.perCnt==1){
							  	$("[id='dwz.person.EMPINFOApplyLeave" + index + "']").val(jsonObject.empId);
							  	$("[id='dwz.person.AFFIRMOR_IDApplyLeave" + index + "']").val( jsonObject.personId);
							  	$("[id='dwz.person.NameEMPINFOApplyLeave" + index + "']").val( jsonObject.empName);
							  	$("[id='dwz.person.DeptEMPINFOApplyLeave" + index + "']").val( jsonObject.deptName);
							  	$("[id='dwz.person.GradeEMPINFOApplyLeave" + index + "']").val( jsonObject.POST_GRADE_NAME);
							  	$("[id='dwz.person.PhoneEMPINFOApplyLeave" + index + "']").val( jsonObject.OFFICE_PHONE);
							}
						},
				error: DWZ.ajaxError
			});
		}
    }
 };

function validateAddRecAffirmCallback(form,callback) {	
	var $form = $("#" + form);
	var checked = false;
	$("input[name='SINGLE_LEAVE']", navTab.getCurrentPanel()).each(function(i, obj) {
		var index = obj.value;
		if (obj.checked) {
			var interviewTime = $("#INTERVIEW_TIME_"+index,navTab.getCurrentPanel()).html();
			var interviewAddress = $("#INTERVIEW_ADDRESS_"+index,navTab.getCurrentPanel()).html();
			
			checked = true;
		}
	});

	if (!checked) {
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}
	
	alertMsg.confirm("<spring:message code='zxc.hr.viewEvaluate.title.SAVE_CONFIRM' />",//确定要保存吗?
  		  	{okCall:function(){	
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});	
	return false;
}

function viewInterviewSchdule_clean() {
	$("[id='INTERVIEW_TIME_ALL']",navTab.getCurrentPanel()).val('');
  	$("[id='INTERVIEW_ADDRESS_ALL']",navTab.getCurrentPanel()).val( '');
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
        hour = '' + d.getHours();
        minute ='' + d.getMinutes();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (hour.length < 2) hour = '0' + hour;
    if (minute.length < 2 ) minute = '0' + minute;
		dformat = [day, month, year].join('/')+' '+
                  [hour, minute].join(':');
    return dformat;
}
function viewInterviewSchdule_feedback(){
	var interviewTimeAll = $("#INTERVIEW_TIME_ALL",navTab.getCurrentPanel()).val();
	var interviewAddressAll = $("#INTERVIEW_ADDRESS_ALL",navTab.getCurrentPanel()).val();
	var year = interviewTimeAll.substring(6,10);
	var month = interviewTimeAll.substring(3,5);
	var day = interviewTimeAll.substring(0,2);
	var hour = interviewTimeAll.substring(11,13);
	var min = interviewTimeAll.substring(14,16);
	var IVTiem = new Date(year,(month-1),day,hour,min)
    d2 = new Date ( IVTiem );
	var checked = false;
	var ids= document.getElementsByName("SINGLE_LEAVE");
	var checked = false;
	for (var i=0; i<ids.length; i++) {
		if (ids[i].checked) {
			checked = true;
			var b = formatDate(d2)
			var index = ids[i].id.substring(13);
			$("#INTERVIEW_TIME_" + index,navTab.getCurrentPanel()).val(b);
			$("#INTERVIEW_ADDRESS_" + index,navTab.getCurrentPanel()).val(interviewAddressAll);
			d2.setMinutes ( d2.getMinutes() + 20 );
		}
	}
	if (!checked) {
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}
}

function changeURL_person(recEmployeeNo, empName){ 
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
		var href = "/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
		var href = "/hrm/recruit/addSingleRecPageHub?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	</c:if>

		var empName = empName;
	$.pdialog.open(href,"hr3704", empName, {width:900,height:400,mask:true});
}
</script>
<div class="pageHeader">
<form id="viewAddRecPageHub_Form" onsubmit="return navTabSearch(this);" action="/hrm/recruit/interviewSchedule" method="post">
<div class="searchBar">
	<table class="searchContent">
		<tr>
			<td><!-- 姓名： --> <spring:message code="alert.pa.pasalarycanshu.xingming" /></td>
			<td><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></td>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<td><!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" /></td>
				<td><ait:SelectSyCodeByCpnyID name="seach_POST_TYPE_CODE" selected="${POST_TYPE_CODE}" parentNo="90000339" limit="all"/></td>
			</c:if>
			<td><!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" /></td>
			<td><ait:SelectSyCodeByCpnyID name="seach_FINAL_EDU_CODE" selected="${FINAL_EDU_CODE}" parentNo="13769" limit="all"/></td>
		</tr>
	</table>
	<div class="subBar">
		<ul>
			<li><div class="buttonActive">
			<div class="buttonContent">
			    <button type="submit"><spring:message code="public.title.search"/></button>
		    </div>
		    </div>
		    </li>
		    
		    <li>
			<a class="buttonActive" onclick="validateAddRecAffirmCallback('interviewEmpScheduleForm',navTabAjaxDone)" href="#">
				<span><!-- 保存面试官 --><spring:message code="hrm.interviewSchedule.saveInterviewer.k" /></span>
		   </a>					
		</li>
		</ul>
	</div>
</div>
</form>
</div>
<div class="pageHeader">
	<form id="interviewEmpScheduleForm2" onsubmit="return navTabSearch(this);" action="/hrm/recruit/interviewSchedule" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="hr.hrm.empinfo.mianshishijian"/><!-- 面试时间 --></td>
					<td style = "text-align: center">
		 				<input type="text" id="INTERVIEW_TIME_ALL" name="INTERVIEW_TIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy HH:mm',lang:'en'})" 
					</td>
					<td><spring:message code="hr.hrm.empinfo.mianshididian"/><!-- 面试地点 --></td>
					<td style = "text-align: center">
						<input type="text" id="INTERVIEW_ADDRESS_ALL" name="INTERVIEW_ADDRESS"  value="" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
					<li>
						<a class="buttonActive" onclick="viewInterviewSchdule_clean();"><span><!--清除--><spring:message code="edu.planManager.QINGCHU.a" /></span></a>
					</li>
		             <li>
		             <a class="buttonActive" onclick="viewInterviewSchdule_feedback();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a>
		             </li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" >
<%-- <div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="buttonActive" onclick="validateAddRecAffirmCallback('interviewEmpScheduleForm',navTabAjaxDone)" href="#">
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<span><!-- 全部反映 --><spring:message code="hrm.recruit.QUANBUFANYING.Z" /></span>
			</c:if>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<span><!-- 保存面试官 --><spring:message code="hrm.interviewSchedule.saveInterviewer.k" /></span>
			</c:if>
		</a>					
		</li>
    </ul>
</div> --%>
	<form name="interviewEmpScheduleForm" id="interviewEmpScheduleForm" method="post" action="/hrm/recruit/addRecAffirmInfo"> 
		<table id="viewInfoTable" class="list" width="100%">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="1%"><input type="checkbox" class="checkboxCtrl" group="SINGLE_LEAVE" /></th>
					<th width="5%"><!-- 姓名 --> <spring:message code="empsubject.userNm" /></th>
					<th width="5%"><!-- 最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" /></th>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<th width="5%"><!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" /></th>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<th width="5%"><!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" /></th>
					</c:if>
					<th width="5%"><!-- 性别 --> <spring:message code="empsubject.sexName" /></th>
					<th width="5%"><!-- 年龄 --> <spring:message code="hrm.empinfo.AGE" /></th>
					<th width="5%"><!-- 国籍 --><spring:message code="hr.viewCondSql.title.GUOJI" /></th>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						<th width="5%"><!-- 电话 --> <spring:message code="empsubject.officePhone" /></th>
						<th width="5%"><!-- 住址  --> <spring:message code="hr.viewRelation.title.FAM_ADDRESS" /></th>
					</c:if>
					<th width="5%"><!--  面试时间 --> <spring:message code="hr.hrm.empinfo.mianshishijian"/></th>
					
					<th width="5%"><!-- 面试地点 --> <spring:message code="hr.hrm.empinfo.mianshididian"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${interviewScheduleList}" var="item" varStatus="i">	
					<tr>
					    <td style="text-align: center" width="1%">${i.index+1}</td>
					    <td style="text-align: center" width="1%">
					        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
					        <input type="checkbox" id="SINGLE_LEAVE_${item.REC_EMPLOYEE_NO}" name="SINGLE_LEAVE" value="${item.REC_EMPLOYEE_NO}" />
					    </td>
					    <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${item.REC_EMPLOYEE_NO }, "${item.EMP_NAME }");'>
           					<span style="color: blue">${item.EMP_NAME }</span></td>
					    <%-- <td style="text-align: center" width="5%">
					       <a style="color:blue;" href="/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${item.REC_EMPLOYEE_NO}" target="dialog" mask="true" width="700" height="250" >
						   <span>${item.EMP_NAME}</span>
						</td> --%>
					    <td style="text-align: center" width="5%">${item.FINAL_EDU_NAME}</td>
					    <c:if test="${LoginUser.cpnyId eq 'HAE'}">
					    	<td style="text-align: center" width="5%">${item.FINAL_SCHOOL}</td>
					    </c:if>
					    <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					    	<td style="text-align: center" width="5%">${item.POST_TYPE_NAME}</td>
					    </c:if>
						<td style="text-align: center" width="5%">${item.SEX_NAME}</td>
						<td style="text-align: center" width="5%">${item.EMP_AGE}</td>
						<td style="text-align: center" width="5%">${item.NATIONALITY_NAME}</td>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							<td style="text-align: center" width="5%">${item.EMP_TELPHONE}</td>
							<td style="text-align: center" width="5%">${item.EMP_ADDRESS}</td> 
						</c:if>
						<td style="text-align: center" width="5%" >
							<input type="text" id="INTERVIEW_TIME_${item.REC_EMPLOYEE_NO}"
							name="INTERVIEW_TIME_${item.REC_EMPLOYEE_NO}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy HH:mm'})" 
							value="${item.INTERVIEW_TIME}" />
						</td>
						<td style="text-align: center" width="5%" >
							<input id="INTERVIEW_ADDRESS_${item.REC_EMPLOYEE_NO}" name="INTERVIEW_ADDRESS_${item.REC_EMPLOYEE_NO}"
							type="text" maxlength="200" style="text-align: left;"
							value="${item.INTERVIEW_ADDRESS}" />
						</td>
						<div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table class="user_table" width="100%">
			<tr height="15px">	
				<td><span>* Select Approval</span></td>
			</tr>
			<tr>
				<td>
					<table class="user_table" width="100%">	
						<tr>
						    <td class="td_title"  style="text-align:center;" width="2%"><spring:message code="ar.viewcycle.title.xuhao"/><!-- 序号 --></td>
							<td class="td_title"  style="text-align:center;" width="18%"><spring:message code="edu.planManager.GONGHAO.a"/><!-- 工号 --></td>
							<td class="td_title" style="text-align:center;" width="20%"><spring:message code="hr.empinfo.name"/><!-- 姓名 --></td>
							<td class="td_title" style="text-align:center;" width="20%"><spring:message code="public.title.deptName"/><!-- 部门 --></td>
							<td class="td_title" style="text-align:center;" width="20%"><spring:message code="ess.trans.title.postGradeName"/><!-- 职级 --></td>
							<td class="td_title" style="text-align:center;" width="20%"><spring:message code="empsubject.officePhone"/><!-- 电话 --></td>
						</tr>
						<tr>
							<td colspan="6">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLeaveAffirm_list">
								    <tr id="rowIdApplyLeave1">
									   <td class="td_type" style="text-align: center" width="2%">
									      1
									   </td>
									   <td class="td_type" style="text-align: center" width="18%">
									      <input id="dwz.person.EmpIdEMPINFOApplyLeave1" name="empid" type="text" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_recAffirm(this,1,event)"/>
									      <input id="dwz.person.AFFIRMOR_IDApplyLeave1" name="AFFIRMOR_ID1" value="" type="hidden"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="empName" id="dwz.person.NameEMPINFOApplyLeave1" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input id="dwz.person.DeptEMPINFOApplyLeave1" name="deptName" type="text" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="postGradeName" id="dwz.person.GradeEMPINFOApplyLeave1" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="officePhone" id="dwz.person.PhoneEMPINFOApplyLeave1" value="" disabled="disabled" size="30"/>
									   </td>
									</tr>
									<tr id="rowIdApplyLeave2">
									   <td class="td_type" style="text-align: center" width="2%">
									      2
									   </td>
									   <td class="td_type" style="text-align: center" width="18%">
									      <input id="dwz.person.EmpIdEMPINFOApplyLeave2" name="empid" type="text" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_recAffirm(this,2,event)"/>
									      <input id="dwz.person.AFFIRMOR_IDApplyLeave2" name="AFFIRMOR_ID2" value="" type="hidden"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="empName" id="dwz.person.NameEMPINFOApplyLeave2" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input id="dwz.person.DeptEMPINFOApplyLeave2" name="deptName" type="text" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="postGradeName" id="dwz.person.GradeEMPINFOApplyLeave2" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="officePhone" id="dwz.person.PhoneEMPINFOApplyLeave2" value="" disabled="disabled" size="30"/>
									   </td>
									</tr>
									<tr id="rowIdApplyLeave3">
									   <td class="td_type" style="text-align: center" width="2%">
									      3
									   </td>
									   <td class="td_type" style="text-align: center" width="18%">
									      <input id="dwz.person.EmpIdEMPINFOApplyLeave3" name="empid" type="text" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_recAffirm(this,3,event)"/>
									      <input id="dwz.person.AFFIRMOR_IDApplyLeave3" name="AFFIRMOR_ID3" value="" type="hidden"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="empName" id="dwz.person.NameEMPINFOApplyLeave3" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input id="dwz.person.DeptEMPINFOApplyLeave3" name="deptName" type="text" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="postGradeName" id="dwz.person.GradeEMPINFOApplyLeave3" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="officePhone" id="dwz.person.PhoneEMPINFOApplyLeave3" value="" disabled="disabled" size="30"/>
									   </td>
									</tr>
									<tr id="rowIdApplyLeave4">
									   <td class="td_type" style="text-align: center" width="2%">
									      4
									   </td>
									   <td class="td_type" style="text-align: center" width="18%">
									      <input id="dwz.person.EmpIdEMPINFOApplyLeave4" name="empid" type="text" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_recAffirm(this,4,event)"/>
									      <input id="dwz.person.AFFIRMOR_IDApplyLeave4" name="AFFIRMOR_ID4" value="" type="hidden"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="empName" id="dwz.person.NameEMPINFOApplyLeave4" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input id="dwz.person.DeptEMPINFOApplyLeave4" name="deptName" type="text" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="postGradeName" id="dwz.person.GradeEMPINFOApplyLeave4" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="officePhone" id="dwz.person.PhoneEMPINFOApplyLeave4" value="" disabled="disabled" size="30"/>
									   </td>
									</tr>
									<tr id="rowIdApplyLeave4">
									   <td class="td_type" style="text-align: center" width="2%">
									      5
									   </td>
									   <td class="td_type" style="text-align: center" width="18%">
									      <input id="dwz.person.EmpIdEMPINFOApplyLeave5" name="empid" type="text" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_recAffirm(this,5,event)"/>
									      <input id="dwz.person.AFFIRMOR_IDApplyLeave5" name="AFFIRMOR_ID5" value="" type="hidden"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="empName" id="dwz.person.NameEMPINFOApplyLeave5" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input id="dwz.person.DeptEMPINFOApplyLeave5" name="deptName" type="text" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="postGradeName" id="dwz.person.GradeEMPINFOApplyLeave5" value="" disabled="disabled" size="30"/>
									   </td>
									   <td class="td_type" style="text-align: center" width="20%">
									      <input type="text" name="officePhone" id="dwz.person.PhoneEMPINFOApplyLeave5" value="" disabled="disabled" size="30"/>
									   </td>
									</tr>
								</table>
								<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_rec_affirm"></a>
							</td>	
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
