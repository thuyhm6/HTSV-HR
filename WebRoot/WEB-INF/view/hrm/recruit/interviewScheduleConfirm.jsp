<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(function(){
	//查询
	$("#viewInterviewScheduleConfirm_Serch",navTab.getCurrentPanel()).click(function(){
		$("#interviewScheduleConfirm_Form",navTab.getCurrentPanel()).submit();
	});
	
	
});

$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({
	"bPaginate": true,    //分页
    "bAutoWidth":false,//表格宽度自动变化
    "bProcessing":true,
    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
	"bLengthChange": true,  //按多少条记录显示下拉框
	"iDisplayLength": 50, //默认每页显示的记录数
	"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
 	"searching": true,//本地搜索
	"bSort": true,   //排序功能
	"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
     "orderClasses": false,
     "order":[],//初始化不用自动排序
     "scrollY": $(document.body).height() - 280,
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

function validateAddRecAffirmCallback(form,callback) {	
	var $form = $("#" + form);
	var checked = false;
	$("input[name='SINGLE_LEAVE']", navTab.getCurrentPanel()).each(function(i, obj) {
		if (obj.checked) {
			checked = true;
		}
	});

	if (!checked) {
		alertMsg
				.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
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

function changeURL_person(recEmployeeNo, empName){ 
	var href = "/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	var empName = empName;
	$.pdialog.open(href,"hr3707", empName, {width:900,height:400,mask:true});
}
function changeURL_view(recEmployeeNo){ 
	var href = "/hrm/recruit/viewInterviewAffirmList?REC_EMPLOYEE_NO="+ recEmployeeNo + "";
	$.pdialog.open(href,"hr3707", "<spring:message code='pa.ins.alert.message.title.clickForDetail' />", {width:900,height:400,mask:true});
}
</script>
<div class="pageHeader">
<form id="interviewScheduleConfirm_Form" onsubmit="return navTabSearch(this);" action="/hrm/recruit/interviewScheduleConfirm" method="post">
<div class="searchBar">
	<table class="searchContent">
		<tr>
			<td>
			    <!-- 姓名： --> <spring:message code="alert.pa.pasalarycanshu.xingming" />
			</td>
			<td>
			    <input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
			</td>
			<td>
			    <!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" />
			</td>
			<td>
			    <ait:SelectSyCodeByCpnyID name="seach_POST_TYPE_CODE" selected="${POST_TYPE_CODE}" parentNo="90000339" limit="all"/>
			</td>
			<td>
			    <!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />  
			</td>
			<td>
			    <ait:SelectSyCodeByCpnyID name="seach_FINAL_EDU_CODE" selected="${FINAL_EDU_CODE}" parentNo="13769" limit="all"/>
			</td>
			<td>
			    <!-- 面试计划 --> <spring:message code="hr.main.page.interviewscheduleConfirm"/>
			</td>
			<td>
			    <select id='seach_CONFIRM' name='seach_CONFIRM'>
			    <option value = '0' <c:if test="${CONFIRM == 0 }">selected</c:if>><spring:message code="ess.title.WEIQUEREN" /></option>
			    <option value = '1' <c:if test="${CONFIRM == 1 }">selected</c:if>><spring:message code="ess.title.YIQUEREN" /></option>
			</td>
		</tr>
	</table>
</div>
</form>
</div>

<div class="pageHeader" >
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewInterviewScheduleConfirm_Serch">
		    <span><!-- 查询 --><spring:message code="button.search" /></span>
	        </a>
	    </li>
		<li>
			<a class="buttonActive" onclick="validateAddRecAffirmCallback('interviewEmpScheduleConfirmForm',navTabAjaxDone)" href="#"><span><!-- 确认 --><spring:message code="sys.affirm.title.confirm" /></span></a>					
		</li>
    </ul>
</div>
	<form name="interviewEmpScheduleConfirmForm" id="interviewEmpScheduleConfirmForm" method="post" action="/hrm/recruit/saveInterviewConfirm"> 
		<table id="viewInfoTable" class="list">
			<thead>
				<tr>
					<th width="1%">No.</th>
					<th width="1%"><input type="checkbox" class="checkboxCtrl" group="SINGLE_LEAVE" /></th>
					<th width="5%"><!-- 姓名 --> <spring:message code="empsubject.userNm" /></th>
					<th width="5%"><!-- 最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" /></th>
					<th width="5%"><!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" /></th>
					<th width="3%"><!-- 性别 --> <spring:message code="empsubject.sexName" /></th>
					<th width="5%"><!-- 年龄 --> <spring:message code="hrm.empinfo.AGE" /></th>
					<th width="5%"><!-- 国籍 --><spring:message code="hr.viewCondSql.title.GUOJI" /></th>
					<th width="5%"><!-- 电话 --> <spring:message code="empsubject.officePhone" /></th>
					<th width="5%"><!-- 住址  --> <spring:message code="hr.viewRelation.title.FAM_ADDRESS" /></th>
					<th width="5%"><!--  面试时间 --> <spring:message code="hr.hrm.empinfo.mianshishijian"/></th>
					<th width="5%"><!-- 面试地点 --> <spring:message code="hr.hrm.empinfo.mianshididian"/></th>
					<th width="5%"><!-- 面试官 --> <spring:message code="hrm.interviewSchedule.interviewer.k"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${interviewScheduleConfirmList}" var="item" varStatus="i">	
					<tr>
					    <td style="text-align: center" width="1%">${i.index+1}</td>
					    <td style="text-align: center" width="1%">
					        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
					        <input type="checkbox" id="SINGLE_LEAVE" name="SINGLE_LEAVE" value="${item.REC_EMPLOYEE_NO}" />
					        <input type="hidden" name="CONFIRM_${i.index }" value="${item.CONFIRM }"/>
					    </td>
					    <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${item.REC_EMPLOYEE_NO }, "${item.EMP_NAME }");'>
           					<span style="color: blue">${item.EMP_NAME }</span></td>
					    <%-- <td style="text-align: center" width="5%">
					       <a style="color:blue;" href="/hrm/recruit/singleRecPageHubInfo?flag=1&pageNum=1&menuNo=2540&navTabId=rec0101&REC_EMPLOYEE_NO=${item.REC_EMPLOYEE_NO}" target="dialog" mask="true" width="700" height="250" >
						   <span>${item.EMP_NAME}</span>
						</td> --%>
					    <td style="text-align: center" width="5%">${item.FINAL_EDU_NAME}</td>
					    <td style="text-align: center" width="5%">${item.POST_TYPE_NAME}</td>
						<td style="text-align: center" width="3%">${item.SEX_NAME}</td>
						<td style="text-align: center" width="5%">${item.EMP_AGE}</td>
						<td style="text-align: center" width="5%">${item.NATIONALITY_NAME}</td>
						<td style="text-align: center" width="5%">${item.EMP_TELPHONE}</td>
						<td style="text-align: center" width="5%">${item.EMP_ADDRESS}</td> 
						<td style="text-align: center" width="5%">
							<c:if test="${item.CONFIRM  == 0 }">
								<input type="text" id="INTERVIEW_TIME_${item.REC_EMPLOYEE_NO}"
								name="INTERVIEW_TIME_${item.REC_EMPLOYEE_NO}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy HH:mm'})" 
								value="${item.INTERVIEW_TIME}" />
							</c:if>
							<c:if test="${item.CONFIRM != 0 }">
								${item.INTERVIEW_TIME}
							</c:if>
						</td>
						<td style="text-align: center" width="5%">
							<c:if test="${item.CONFIRM  == 0 }">
								<input name="INTERVIEW_ADDRESS_${item.REC_EMPLOYEE_NO}"
								type="text" maxlength="200" class="textInput""
								value="${item.INTERVIEW_ADDRESS}" />
							</c:if>
							<c:if test="${item.CONFIRM  != 0 }">
								${item.INTERVIEW_ADDRESS}
							</c:if>
							
						</td>
						<td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_view(${item.REC_EMPLOYEE_NO });'>
           					<span style="color: blue"><spring:message code="pa.ins.alert.message.title.clickForDetail" /></span></td>
						<%-- <td style="text-align: center" width="5%">
							<a href="/hrm/recruit/viewInterviewAffirmList?REC_EMPLOYEE_NO=${item.REC_EMPLOYEE_NO }" target="dialog" mask="true" width="600" height="300" style="text-decoration:none ;">
							<spring:message code="pa.ins.alert.message.title.clickForDetail" /></a>
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
