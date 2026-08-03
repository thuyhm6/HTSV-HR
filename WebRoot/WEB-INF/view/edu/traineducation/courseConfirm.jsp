<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#confirmTable select[name=CONFIRM_FLAG]').each(function(){
		var confirmflag=$(this).attr('flag');
		var makerno=$(this).attr('makerno');
		$('#CONFIRM_FLAG_'+makerno+' option[value='+confirmflag+']').attr('selected','selected');
	});
	
});
function xuanzhongFIR(no){
	var count="${courseConfirmListCount}";
	for(var i=1;i<=count;i++){
		$('#listFIR_'+i).attr('style','');
	}
	$('#listFIR_'+no).attr('style','background:#aaccf6');
	var checkno=$('#confirmCheck_'+no).prop('checked');
	if(checkno==true){
		$('#confirmCheck_'+no).removeAttr('checked','checked');
	}else{
		$('#confirmCheck_'+no).attr('checked','checked');
	}
	
}
function confirmtongguoFIM(){
	//遍历学生对应课程的信息
	var arrayconfirmno="";
	var arraybasicno="";
	var arrayempid="";
	var arrayname="";
	$('#confirmTable input[name=confirmCheck]').each(function(){
		  var checkno=$(this).prop('checked');
		   if(checkno==true){
			   arrayconfirmno=arrayconfirmno+$(this).val()+",";
			   arraybasicno=arraybasicno+$(this).attr('basicvalue')+",";
			   arrayempid=arrayempid+$(this).attr('empidvalue')+",";
			   arrayname=arrayname+$(this).attr('namevalue')+",";
		   }
	});
	arrayconfirmno=arrayconfirmno.substring(0,arrayconfirmno.length-1);
	arraybasicno=arraybasicno.substring(0,arraybasicno.length-1);
	arrayempid=arrayempid.substring(0,arrayempid.length-1);
	arrayname=arrayname.substring(0,arrayname.length-1);
	if(arrayconfirmno!=''&&arraybasicno!=''){
		$('#tongguoFIM').attr('href','/edu/traineducation/updateCourseConfirm?arrayconfirmno='+arrayconfirmno+'&CONFIRM_FLAG=2&arraybasicno='+arraybasicno+'&arrayempid='+arrayempid+'&arrayname='+encodeURI(encodeURI(arrayname)));
	}else{
		$('#tongguoFIM').attr('href','/edu/traineducation/updateCourseConfirm?{FIR}');
	}
}
function changeConfirm(makerno,basicno,applyno,empid,stu_local_name){
	var changeflag=$('#CONFIRM_FLAG_'+makerno).attr('value');
	$('#tongguoFIM').attr('href','/edu/traineducation/updateCourseConfirm?arrayconfirmno='+applyno+'&CONFIRM_FLAG='+changeflag+'&arraybasicno='+basicno+'&arrayempid='+empid+'&arrayname='+encodeURI(encodeURI(stu_local_name)));
	$('#tongguoFIM').click();
}
function sousuoFIR(){
	$('#courseConfirm').submit();
}

$('#confirmAllCheck').click(function(){  
    $('input[name="confirmCheck"]').prop("checked",this.checked);  
});

$("#confirmTable",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":true,
    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
	"bLengthChange": true,  //按多少条记录显示下拉框
	"iDisplayLength": 50, //默认每页显示的记录数
    "bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
    "bSort": true,   //关闭排序功能
    "bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
    "bScrollInfinite":true,
    "scrollY": true,
    "scrollX": true,
    "orderClasses": false,
    "order":[],//初始化不用自动排序
    "scrollY": $(document.body).height() - 300,
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
    }
});

function changeURL_courseConfirm_queryCourse(planNo){
	var href = "/edu/traineducation/queryCourseSyllabus2?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0402", "<spring:message code='edu.planManager.CHAKANKECHENGBIAO.a' />", {width:600,height:400,mask:true});//查看课程表
}

</script>
<form id="courseConfirm" onsubmit="return navTabSearch(this);" action="/edu/traineducation/courseConfirm" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
        <tr>
        <td class="td_title" width="4%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></td>
		<td class="td_type" width="4%">
		 <ait:deptList name="confirmDepartno" cpnyId="${defaultCpny}" limit="super" id="confirmDepartnoId"  />
		 <ait:deptTreeIcon name="confirmDepartno" limit="super" id="confirmDepartnoId" selected="${confirmDepartno}"/>
		</td>
        <td class="td_title" width="4%"><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
		<td class="td_type" width="4%">
		<input type="text" name="confirmEmpidName" id="confirmEmpidName" value="${confirmEmpidName }"  >
		</td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="confirmcoursename" id="confirmcoursename" value="${confirmcoursename }"  >
		</td>
		</tr>
		<tr>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="confirmstartdate" id="confirmstartdate"  onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${confirmstartdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="confirmenddate" id="confirmenddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${confirmenddate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="ess.humanConfirm.title.confirmStatus"/><!--确认状态--></td>
		<td class="td_type" width="4%">
		<select name="confirm_flag" id="confirm_flag" >
		<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
		<option value="1"><spring:message code="ess.title.WEIQUEREN"/><!--未确认--></option>
		<option value="2"><spring:message code="ar.viewsummaryyiqueren"/><!--已确认--></option>
		</select>
		</td>
		</tr>
		
		
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
	<ul style="width: 150px;">              
        <li>
			<a class="buttonActive" href="#" onclick="sousuoFIR()" width="800" height="250" >
				<span><spring:message code="display.paecc.sousuo"/><!--搜索--></span>
			</a>
		</li>
		<li>
			<a class="buttonActive" id="tongguoFIM" onclick="confirmtongguoFIM()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
				<span><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/><!--确认--></span>
			</a>
		</li>
	</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table class="list" id="confirmTable">
	<thead>
		<tr >
		    <td width="1%"><input type="checkbox" name="confirmAllCheck" id="confirmAllCheck" /></td>
		    <td width="1%">NO.</td>
		    <td width="5%"><spring:message code="ess.infoApply.EMPID"/><!--社号--></td>
			<td width="5%"><spring:message code="ess.viewApply.title.applyName"/><!--申请者--></td>
			<td width="5%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></td>
			<td width="5%"><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--职级--></td>
			<td width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
			<td width="5%"><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></td>
			<td width="5%"><spring:message code="edu.planManager.SHISHIRIQI.a"/><!--实施日期--></td>
			<td width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
			<td width="5%"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--课程表--></td>
			<td width="5%"><spring:message code="ess.empInfo.date_application"/><!--申请日期--></td>
			<td width="5%"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由--></td>
			<td width="5%"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></td>
			<td width="5%"><spring:message code="edu.courseConfirm.QUERENQINGKUANG.a"/><!--确认情况--></td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${courseConfirmList}" var="s" varStatus="i">
		<tr target="FIR" id="listFIR_${i.count }" onclick="xuanzhongFIR('${i.count }')" >
		   <td width="1%" style="text-align:center;">
		   <c:if test="${s.APPLY_FLAG=='2'&& s.CONFIRM_FLAG=='1' }">
		   <input type="checkbox" name="confirmCheck" id="confirmCheck_${i.count }" value="${s.APPLY_NO }" basicvalue="${s.BASIC_NO }" empidvalue="${s.EMPID }" namevalue="${s.STU_LOCAL_NAME }" onclick="xuanzhongFIR('${i.count }')">
		   </c:if>
		   
		   </td>
		   <td width="1%">${i.count }</td>
           <td width="5%">${s.EMPID }</td>
           <td width="5%">${s.STU_LOCAL_NAME }</td>
           <td width="5%">${s.DEPTNAME }</td>
           <td width="5%">${s.POST_GRADE_NO_NAME }</td>
           <td width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
           <td width="5%" nowrap="nowrap">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td width="5%">${s.IMPLE_CLASS_HOUR }&nbsp
			<c:if test="${s.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
           </td>
           <c:if test="${s.SYLLABUSCOUNT=='0' }">
           <td width="5%"></td>
           </c:if>
           <c:if test="${s.SYLLABUSCOUNT!='0' }">
           <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_courseConfirm_queryCourse(${s.PLAN_NO });'>
           <span style = "color:blue;"><spring:message code="edu.planManager.CHAKANKECHENGBIAO.a"/><!--查看课程表--></span></td>
           </c:if>
           <td width="5%" nowrap="nowrap">${s.CREATE_DATE }</td>
           <td width="5%">${s.APPLY_TASK }</td>
           <td width="5%" nowrap="nowrap">
           <c:if test="${s.APPLY_FLAG=='1' }">
            ${s.MAKER_LOCAL_NAME }[<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->]
           </c:if>
           <c:if test="${s.APPLY_FLAG=='2' }">
            ${s.MAKER_LOCAL_NAME }<span style="color:blue">[<spring:message code="ess.affirmApply.title.remark.yitongguo"/><!--已通过-->]</span>
           </c:if>
           <c:if test="${s.APPLY_FLAG=='0' }">
            ${s.MAKER_LOCAL_NAME }<span style="color:red">[<spring:message code="ess.affirmApply.title.remark.yifoujue"/><!--已否决-->]</span>
           </c:if>
           </td>
           <td width="5%">
           <c:if test="${s.APPLY_FLAG=='2' }">
           <select name="CONFIRM_FLAG" id="CONFIRM_FLAG_${s.MAKER_NO }" flag="${s.CONFIRM_FLAG }"  makerno="${s.MAKER_NO }"  onchange="changeConfirm('${s.MAKER_NO }','${s.BASIC_NO }','${s.APPLY_NO }','${s.EMPID }','${s.STU_LOCAL_NAME }')">
           <option value="1"><spring:message code="main.home.message.unconfirm"/><!--待确认--></option>
           <option value="2"><spring:message code="pa.insurance.title.confirm"/><!--确认--></option>
           <option value="0"><spring:message code="hrm.contractInfo.VETO"/><!--否决--></option>
           </select>
           </c:if>
           </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>