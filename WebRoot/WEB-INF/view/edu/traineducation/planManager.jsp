<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanPlanno="";
	var quanPlan="";
	var xiu=0;
	function xiugaiPlan(){
			if(quanPlanno!=""){
				$('#updatePlan').attr('href','/edu/traineducation/planManagerInfo?PLAN_NO='+quanPlanno);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function addPlan(){
	    var TRAIN_DIFF_CODE = $('#TRAIN_DIFF_CODE').val();
	    var TRAIN_TYPE_CODE = $('#TRAIN_TYPE_CODE').val();
	    if(TRAIN_TYPE_CODE== null)
	    TRAIN_TYPE_CODE='';
		$('#addPlanManger').attr('href','/edu/traineducation/addPlanManager?TRAIN_DIFF_CODE='+TRAIN_DIFF_CODE+'&TRAIN_TYPE_CODE='+TRAIN_TYPE_CODE);	
	}

	function xuanzhongPlan(no,nono){
		var count="${planManagerListCount}";
		for(var i=1;i<=count;i++){
			$('#listPlan_'+i).attr('style','');
		}
		$('#listPlan_'+no).attr('style','background:#aaccf6');
		$('#updatePlan').attr('href','/edu/traineducation/planManagerInfo?PLAN_NO='+nono);
		$('#yinPlan').attr('style','display:none');
		$('#xianPlan').attr('style','');
		quanPlan=no;
		quanPlanno=nono;
	}
	function shanchuPlan(){
		if(quanPlanno!=""){
			$('#deletePlan').attr('href','/edu/traineducation/deletePlanManager?PLAN_NO='+quanPlanno);
	    }else{
		$('#deletePlan').attr('href','/edu/traineducation/deletePlanManager?{Plan}');
	}
  }
	function sousuo1(){
		$('#planManager').submit();
	}
	
$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
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
    "scrollY": $(document.body).height() - 290,
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
	
function panDiff(value){
	codeRelation(value,'TRAIN_TYPE_CODE','${TRAIN_TYPE_CODE}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
function changeURL_planManager_clickForDetail(planNo){
	var href = "/edu/traineducation/singlePlanManagerInfo?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0103", "<spring:message code='pa.ins.alert.message.title.clickForDetail' />", {width:800,height:600,mask:true});//明细查看
}
function changeURL_planManager_queryCourse(planNo){
	var href = "/edu/traineducation/queryCourseSyllabus2?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0103", "<spring:message code='edu.planManager.CHAKANKECHENGBIAO.a' />", {width:600,height:400,mask:true});//查看课程表
}

</script>
<form id="planManager" onsubmit="return navTabSearch(this);" action="/edu/traineducation/planManager" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
    <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="hrm.empinfo.training_distinction"/></td><!--培训区分-->
		<td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TRAIN_DIFF_CODE" id="TRAIN_DIFF_CODE"
                    parentNo="14014478" cnpyID="${defaultCpny}" selected="${TRAIN_DIFF_CODE }" limit="all" onChangeName="panDiff(this.value)"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/></td><!--培训类型-->
		<td class="td_type" width="4%">
	        <select name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE" ></select>
	    </td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/></td><!--课程名称-->
		<td class="td_type" width="4%">
		<input type="text" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE" value="${COURSE_NAME_CODE }">
		</td>
		</tr>
		</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 250px;">
		            <li>
						<a class="buttonActive" href="#" onclick="sousuo1()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/></span><!--搜索
						--></a>
					</li>
					<li>
						<a  id="addPlanManger" class="buttonActive" href="#" onclick="addPlan();" target="dialog" mask="true" width="800" height="600" >
							<span><spring:message code="button.add"/></span><!--添加
						--></a>
					</li>
					<li id="yinPlan" style="">
						<a class="buttonActive"  onclick="xiugaiPlan()" href="#"  width="800" height="250">
							<span><spring:message code="button.update"/></span><!--修改
						--></a>
					</li>
					<li id="xianPlan" style="display:none">
						<a class="buttonActive" id="updatePlan" onclick="xiugaiPlan()" href="#" target="dialog" mask="true"  width="800" height="600">
							<span><spring:message code="button.update"/></span><!--修改
						--></a>
					</li>
					<li>
						<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/><!--确定是否删除?
						-->" id="deletePlan" onclick="shanchuPlan()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
							<span><spring:message code="button.delete"/></span><!--删除
						--></a>
					</li>
				</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
	<thead>
		<tr >
		    <th  width="1%">NO.</th>
			<th  width="4%"><spring:message code="hrm.empinfo.training_distinction"/></th><!--培训区分
			--><th  width="6%"><spring:message code="edu.courseManager.KECHENGBIANHAO.a"/></th><!--课程编号
			--><th  width="10%"><spring:message code="empsubject.subjectNm"/></th><!--课程名称
			--><th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/></th><!--培训类型
			--><th  width="6%"><spring:message code="edu.planManager.ZHUGUANBUMEN.a"/></th><!--主管部门
			--><th  width="5%"><spring:message code="ess.trans.title.typeName"/></th><!--培训形式
			--><th  width="5%"><spring:message code="edu.planManager.SHISHIRIQI.a"/></th><!--实施日期
			--><th  width="3%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/></th><!--培训课时
			--><th  width="3%"><spring:message code="edu.planManager.KECHENGBIAO.a"/></th><!--课程表
			--><th  width="3%"><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/></th><!--培训内容
		--></tr>
		</thead>
		<tbody>
		<c:forEach items="${planManagerList}" var="s" varStatus="i">
		<tr target="Plan" id="listPlan_${i.count }" onclick="xuanzhongPlan('${i.count }','${s.PLAN_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }</td>
           <td class="td_type" width="4%" style="text-align:center;">${s.TRAIN_DIFF_CODE}</td>
           <td class="td_type" width="6%" style="text-align:center;">${s.COURSE_NUMBER }</td>
           <td class="td_type" width="10%" style="text-align:center;">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/>&nbsp<!--期--><spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })
           <td class="td_type" width="5%" style="text-align:center;">${s.TRAIN_TYPE_CODE_NAME }</td>
           <c:choose>
           <c:when test="${s.DEPART_MANA_CODE_NAME==null}">
           <td class="td_type" width="5%" style="text-align:center;">${s.DEPART_MANA_CODE }</td>
           </c:when>
            <c:otherwise> 
            <td class="td_type" width="6%" style="text-align:center;">${s.DEPART_MANA_CODE_NAME }</td>
            </c:otherwise>
           </c:choose>
           <td class="td_type" width="5%" style="text-align:center;">${s.TRAIN_FORM_CODE_NAME }</td>
           <td class="td_type" width="5%" style="text-align:center;">${s.PLAN_STARTDATE }~${s.PLAN_ENDDATE }</td>
           <td class="td_type" width="3%" style="text-align:center;">
              <c:if test="${s.CLASS_UNIT=='0' }">
				<span>${s.CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
				</c:if>
				<c:if test="${s.CLASS_UNIT=='1' }">
					<span>${s.CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
				</c:if>
				<c:if test="${s.CLASS_UNIT=='2' }">
					<span>${s.CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
				</c:if>
           </td>
           
           <td class="td_type" width="3%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_planManager_queryCourse(${s.PLAN_NO });'>
           <span style="color: blue"><spring:message code="edu.planManager.CHAKANKECHENGBIAO.a"/><!--查看课程表--></span>
           </td>
           <td class="td_type" width="3%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_planManager_clickForDetail(${s.PLAN_NO });'>
		   <span style="color: blue"><spring:message code="pa.ins.alert.message.title.clickForDetail"/></span><!--点击查看-->
		   </td>
		   </tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>