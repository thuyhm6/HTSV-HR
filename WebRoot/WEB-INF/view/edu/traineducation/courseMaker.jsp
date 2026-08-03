<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#makerTable select[name=APPLY_FLAG]').each(function(){
		var applyflag=$(this).attr('flag');
		var makerno=$(this).attr('makerno');
		$('#APPLY_FLAG_'+makerno+' option[value='+applyflag+']').attr('selected','selected');
	});
	
});
function xuanzhongMAK(no){
	var count="${courseMakerListCount}";
	for(var i=1;i<=count;i++){
		$('#listMAK_'+i).attr('style','');
	}
	$('#listMAK_'+no).attr('style','background:#aaccf6');
	var checkno=$('#makerCheck_'+no).prop('checked');
	if(checkno==true){
		$('#makerCheck_'+no).removeAttr('checked','checked');
	}else{
		$('#makerCheck_'+no).attr('checked','checked');
	}
	
}
function makertongguo(){
	//遍历学生对应课程的信息
	var arraymakerno="";
	$('#makerTable input[name=makerCheck]').each(function(){
		  var checkno=$(this).prop('checked');
		   if(checkno==true){
			   arraymakerno=arraymakerno+$(this).val()+",";
		   }
	});
	arraymakerno=arraymakerno.substring(0,arraymakerno.length-1);
	if(arraymakerno!=''){
		$('#tongguo').attr('href','/edu/traineducation/updateCourseMaker?arraymakerno='+arraymakerno+'&APPLY_FLAG=2');
	}else{
		$('#tongguo').attr('href','/edu/traineducation/updateCourseMaker?{MAK}');
	}
}
function sousuoMAK(){
	$('#courseMaker').submit();
}
function changeMaker(makerno,applyflag,applyno){
	var changeflag=$('#APPLY_FLAG_'+makerno).val();
	$('#tongguo').attr('href','/edu/traineducation/updateCourseMaker?arraymakerno='+applyno+'&APPLY_FLAG='+changeflag);
	$('#tongguo').click();
}
$('#makerAllCheck').click(function(){  
    $('input[name="makerCheck"]').prop("checked",this.checked);  
});

$("#makerTable",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":true,
    "bLengthChange": true,  //关闭按多少条记录显示下拉框
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

function changeURL_courseMaker_queryCourse(planNo){
	var href = "/edu/traineducation/queryCourseSyllabus?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0402", "<spring:message code='edu.planManager.CHAKANKECHENGBIAO.a' />", {width:600,height:400,mask:true});//查看课程表
}

</script>
<form id="courseMaker" onsubmit="return navTabSearch(this);" action="/edu/traineducation/courseMaker" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="0" cellpadding="2" cellspacing="1">
        <tr>
        <td class="td_title" width="4%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></td>
		<td class="td_type" width="4%">
		 <ait:deptList name="makDepartno" cpnyId="${defaultCpny}" limit="super" id="makDepartnoId"  />
		 <ait:deptTreeIcon name="makDepartno" limit="super" id="makDepartnoId" selected="${makDepartno}"/>
		</td>
        <td class="td_title" width="4%"><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
		<td class="td_type" width="4%">
		<input type="text" name="makEmpidName" id="makEmpidName" value="${makEmpidName }"  >
		</td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="makcoursename" id="makcoursename" value="${makcoursename }"  >
		</td>
		</tr>
		<tr>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="makstartdate" id="makstartdate"  onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${makstartdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="makenddate" id="makenddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${makenddate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></td>
		<td class="td_type" width="4%">
		<select name="maker_flag" id="maker_flag" >
		<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
		<option value="1"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></option>
		<option value="2"><spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!--已通过--></option>
		<option value="0"><spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!--已否决--></option>
		</select>
		</td>
		</tr>
		
		
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float:right; padding-right:20px">
	<ul style="width:150px;">        
        <li>
			<a class="buttonActive" href="#" onclick="sousuoMAK()" width="800" height="250" >
				<span><spring:message code="hrm.empinfo.SEARCH"/><!--搜索--></span>
			</a>
		</li>
		<li>
			<a class="buttonActive" id="tongguo" onclick="makertongguo()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
				<span><spring:message code="hrm.contractInfo.ADOPT"/><!--通过--></span>
			</a>
		</li>
		
	</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table class="list" id="makerTable">
	<thead>
	    
		<tr >
		    <td  width="1%"><input type="checkbox" name="makerAllCheck" id="makerAllCheck" ></td>
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="ess.infoApply.EMPID"/><!--社号--></th>
			<th  width="5%"><spring:message code="ess.viewApply.title.applyName"/><!--申请者--></th>
			<th  width="5%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></th>
			<th  width="5%"><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--职级--></th>
			<th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="5%"><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></th>
			<th  width="5%"><spring:message code="edu.planManager.SHISHIRIQI.a"/><!--实施日期--></th>
			<th  width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
			<th  width="5%"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--课程表--></td>	
			<th  width="5%"><spring:message code="ess.empInfo.date_application"/><!--申请日期--></th>
			<th  width="5%"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由--></th>
			<th  width="5%"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${courseMakerList}" var="s" varStatus="i">
		<tr target="MAK" id="listMAK_${i.count }" onclick="xuanzhongMAK('${i.count }')" >
		   <td class="td_type" width="1%" style="text-align:center;">
		   <c:if test="${s.CONFIRM_FLAG=='1' }">
		   <input type="checkbox" name="makerCheck" id="makerCheck_${i.count }" value="${s.APPLY_NO }" onclick="xuanzhongMAK('${i.count }')">
		   </c:if>
		   <c:if test="${s.CONFIRM_FLAG!='1' }">
		   <input type="checkbox" disabled="true">
		   </c:if>
		   </td>
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }</td>
           <td class="td_type" width="5%">${s.EMPID }</td>
           <td class="td_type" width="5%">${s.STU_LOCAL_NAME }</td>
           <td class="td_type" width="5%">${s.DEPTNAME }</td>
           <td class="td_type" width="5%">${s.POST_GRADE_NO_NAME }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
           <td class="td_type" width="5%" nowrap="nowrap">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td class="td_type" width="5%">${s.IMPLE_CLASS_HOUR }&nbsp
			<c:if test="${s.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
           </td>
           <c:if test="${s.SYLLABUSCOUNT=='0' }">
           <td class="td_type" width="5%"></td>
           </c:if>
           <c:if test="${s.SYLLABUSCOUNT!='0' }">
           <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_courseMaker_queryCourse(${s.PLAN_NO });'>
           <span style = "color:blue;"><spring:message code="edu.planManager.CHAKANKECHENGBIAO.a"/><!--查看课程表--></span></td>
           </c:if>
           <td class="td_type" width="5%">${s.CREATE_DATE }</td>
           <td class="td_type" width="5%">${s.APPLY_TASK }</td>
           <td class="td_type" width="5%" nowrap="nowrap">
           <c:if test="${s.CONFIRM_FLAG=='1' }">
           <select name="APPLY_FLAG" id="APPLY_FLAG_${s.MAKER_NO }" flag="${s.APPLY_FLAG}" makerno="${s.MAKER_NO }" onchange="changeMaker('${s.MAKER_NO }','${s.APPLY_FLAG}','${s.APPLY_NO }')">
           <option value="1"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></option>
           <option value="2"><spring:message code="ess.trans.title.pass"/><!--通过--></option>
           <option value="0"><spring:message code="ess.trans.title.reject"/><!--否决--></option>
           </select>
           </c:if>
           <c:if test="${s.CONFIRM_FLAG!='1' }">
           <select disabled="true" name="APPLY_FLAG" id="APPLY_FLAG_${s.MAKER_NO }" flag="${s.APPLY_FLAG}" makerno="${s.MAKER_NO }" onchange="changeMaker('${s.MAKER_NO }','${s.APPLY_FLAG}','${s.APPLY_NO }')">
           <option value="1"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></option>
           <option value="2"><spring:message code="ess.trans.title.pass"/><!--通过--></option>
           <option value="0"><spring:message code="ess.trans.title.reject"/><!--否决--></option>
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