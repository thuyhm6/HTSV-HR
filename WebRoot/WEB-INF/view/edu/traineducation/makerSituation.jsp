<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function xuanzhongSIT(no){
	var count="${makerSituationListCount}";
	for(var i=1;i<=count;i++){
		$('#listSIT_'+i).attr('style','');
	}
	$('#listSIT_'+no).attr('style','background:#aaccf6');
	var checkno=$('#SITCheck_'+no).prop('checked');
}

function sousuoFIR2(){
	$('#makerSituation').submit();
}

$(".list",navTab.getCurrentPanel()).dataTable({
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
     "scrollY": $(document.body).height() - 330,
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

function changeURL_makerSituation_queryCourse(planNo){
	var href = "/edu/traineducation/queryCourseSyllabus?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0404", "<spring:message code='edu.planManager.CHAKANKECHENGBIAO.a' />", {width:600,height:400,mask:true});//查看课程表
}

</script>
<form id="makerSituation" onsubmit="return navTabSearch(this);" action="/edu/traineducation/makerSituation" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="0" cellpadding="2" cellspacing="1">
        <tr>
        <c:if test="${adminPersonid=='2000560'||adminPersonid=='2000889' }">
        <td class="td_title" width="4%"><spring:message code="org.title.dept"/><!--部门--></td>
		<td class="td_type" width="4%">
		 <ait:deptList name="situationDepartno" cpnyId="${defaultCpny}" limit="super" id="situationDepartnoId"  />
		 <ait:deptTreeIcon name="situationDepartno" limit="super" id="situationDepartnoId" selected="${situationDepartno}"/>
		</td>
        <td class="td_title" width="4%"><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
		<td class="td_type" width="4%">
		<input type="text" name="situationEmpidName" id="situationEmpidName" value="${situationEmpidName }"  >
		</td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="situationcoursename" id="situationcoursename" value="${situationcoursename }"  >
		</td>
		</c:if>
		<c:if test="${adminPersonid!='2000560'&&adminPersonid!='2000889' }">
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%" colspan='5'>
		<input type="text" name="situationcoursename" id="situationcoursename" value="${situationcoursename }"  >
		</td>
		</c:if>
		</tr>
		<tr>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="situationstartdate" id="situationstartdate"  onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${situationstartdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="situationenddate" id="situationenddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${situationenddate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="ess.infoApply.confirm_status"/><!--确认状态--></td>
		<td class="td_type" width="4%">
		<select name="situation_flag" id="situation_flag" >
		<option value=""><spring:message code="org.title.PLEASE_SELECT"/><!--请选择--></option>
		<option value="1"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></option>
		<option value="2"><spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!--已通过--></option>
		<option value="0"><spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!--已否决--></option>
		</select>
		</td>
		</tr>
		
		
</table>
</div>
</div>
<div class="subBar" style="margin-top: 20px; float: right; padding-right: 20px;">
	<ul>        
           <li>
			<a class="buttonActive" href="#" onclick="sousuoFIR2()" width="800" height="250" >
				<span><spring:message code="hrm.empinfo.SEARCH"/><!--搜索--></span>
			</a>
		</li>
	</ul>
</div>
<div style="width: 100%; padding-top: 20px;">
   <table class="list" width="100%">
	<thead>
		<tr >
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="ess.infoApply.EMPID"/><!--社号--></th>
			<th  width="5%"><spring:message code="ess.viewApply.title.applyName"/><!--申请者--></th>
			<th  width="5%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></th>
			<th  width="5%"><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--职级--></th>
			<th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="8%"><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></th>
			<th  width="5%"><spring:message code="edu.planManager.SHISHIRIQI.a"/><!--实施日期--></th>
			<th  width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
			<th  width="5%"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--课程表--></td>	
			<th  width="5%"><spring:message code="ess.empInfo.date_application"/><!--申请日期--></th>
			<th  width="5%"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由--></th>
			<th  width="5%"><spring:message code="hrm.empinfo.PERSON_NUMBER"/><!--人数--></th>
			<th  width="5%"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
			<th  width="5%"><spring:message code="edu.courseConfirm.QUERENQINGKUANG.a"/><!--确认情况--></th>
			<th  width="5%"><spring:message code="ess.affirmApply.title.quxiaoshenqing"/><!--取消申请--></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${makerSituationList}" var="s" varStatus="i">
		<tr target="SIT" id="listSIT_${i.count }" onclick="xuanzhongSIT('${i.count }')">
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
           <td class="td_type" width="5%">${s.EMPID }</td>
           <td class="td_type" width="5%">${s.LOCAL_NAME }</td>
           <%-- <td class="td_type" width="5%" title="${s.DEPTNAME }">${fn:substring(s.DEPTNAME,0,5) }..</td> --%>
           <td class="td_type" width="5%" >${s.DEPTNAME }</td>
           <td class="td_type" width="5%">${s.POST_GRADE_NO_NAME }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="8%" >
           <span>${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</span>
           </td>
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
           <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_makerSituation_queryCourse(${s.PLAN_NO });'>
           <span style = "color:blue;"><spring:message code="edu.planManager.CHAKANKECHENGBIAO.a"/><!--查看课程表--></span></td>
           </c:if>
           <td class="td_type" width="5%" nowrap="nowrap">${s.CREATE_DATE }</td>
           <td class="td_type" width="5%">${s.APPLY_TASK }</td>
           <td class="td_type" width="5%">${s.alreadycountnum }</td>
           <td class="td_type" width="5%" nowrap="nowrap">
           <c:if test="${s.APPLY_FLAG=='1' }">
            ${s.MAKER_LOCAL_NAME }[<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁-->]
           </c:if>
           <c:if test="${s.APPLY_FLAG=='2' }">
            ${s.MAKER_LOCAL_NAME }<span style="color:blue">[<spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!--已通过-->]</span>
           </c:if>
           <c:if test="${s.APPLY_FLAG=='0' }">
            ${s.MAKER_LOCAL_NAME }<span style="color:red">[<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!--已否决-->]</span>
           </c:if>
           </td>
           <td class="td_type" width="5%" nowrap="nowrap">
           <c:if test="${s.APPLY_FLAG=='2' }">
           <c:if test="${s.CONFIRM_FLAG=='1' }">
           <span style="color:blue;">${s.TRAIN_LOCAL_NAME }[<spring:message code="main.home.message.unconfirm"/><!--待确认-->]</span>
           </c:if>
           <c:if test="${s.CONFIRM_FLAG=='2' }">
           <span>${s.TRAIN_LOCAL_NAME }[<spring:message code="ar.viewsummaryyiqueren"/><!--已确认-->]</span>
           </c:if>
           </c:if>
           </td>
            <td class="td_type" width="5%" nowrap="nowrap">
            <c:if test="${s.APPLY_FLAG=='1' }">
            <a title="<spring:message code="edu.makerSituationHUB.QUEDINGQUXIAO.a"/><!--确定取消?-->" href="/edu/traineducation/cancelApply?APPLY_NO=${s.APPLY_NO }" style="color:red;" callback="doAjaxDoneWithForm" target="ajaxTodo"><spring:message code="inct.salesman.button.cancelImport"/><!--取消--></a>
            </c:if>
            </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>