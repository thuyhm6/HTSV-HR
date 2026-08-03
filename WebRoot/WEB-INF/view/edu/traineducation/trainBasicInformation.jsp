<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanBasnoBas="";
	var quanBas="";
	function xiugaiBas(){
			if(quanBasnoBas!=""){
				$('#updateBas').attr('href','/edu/traineducation/trainBasicInformationInfo?BASIC_NO='+quanBasnoBas);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongBas(no,nono){
		var count="${trainBasicInformationListCount}";
		for(var i=1;i<=count;i++){
			$('#listBas_'+i).attr('style','');
		}
		$('#listBas_'+no).attr('style','background:#aaccf6');
		$('#updateBas').attr('href','/edu/traineducation/trainBasicInformationInfo?BASIC_NO='+nono);
		$('#yinBas').attr('style','display:none');
		$('#xianBas').attr('style','');
		quanBas=no;
		quanBasnoBas=nono;
	}
	function shanchuBas(){
		if(quanBasnoBas!=""){
				alert("<spring:message code='edu.trainBasicInformation.SHANCHUJIBENXINXI.a'/>");//删除基本信息,与之对应的学生考评、讲师评价、培训结果、费用管理都会删除！
				$('#deleteBas').attr('href','/edu/traineducation/deleteTrainBasicInformation?BASIC_NO='+quanBasnoBas);
	}else{
		$('#deleteBas').attr('href','/edu/traineducation/deleteTrainBasicInformation?{Bas}');
	}
  }
	function sousuoBas(){
		$('#trainBasicInformation').submit();
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
	    "scrollY": $(document.body).height() - 270,
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

function changeURL_trainBasic(basicNo){
	var href = "/edu/traineducation/queryBasicInformation?BASIC_NO=" + basicNo;
	$.pdialog.open(href,"edu0201", "<spring:message code='pa.salary.title.fullInfo' />", {width:1000,height:200,mask:true});//查看课程表
}

</script>
<form id="trainBasicInformation" onsubmit="return navTabSearch(this);" action="/edu/traineducation/trainBasicInformation" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="coursename" id="coursename" value="${coursename }">
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="startdate"  id="startdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${startdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="enddate"  id="enddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${enddate}" class="Wdate"/>
		</td>
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 250px;">
		            <li>
						<a class="buttonActive" href="#" onclick="sousuoBas()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/edu/traineducation/addTrainBasicInformation" target="dialog" mask="true" width="1000" height="600" >
							<span><spring:message code="button.add"/><!--添加--></span>
						</a>
					</li>
					<li id="yinBas" style="">
						<a class="buttonActive"  onclick="xiugaiBas()" href="#"  width="800" height="250">
							<span><spring:message code="button.update"/><!--修改--></span>
						</a>
					</li>
					<li id="xianBas" style="display:none">
						<a class="buttonActive" id="updateBas" onclick="xiugaiBas()" href="#" target="dialog" mask="true"  width="1000" height="600">
							<span><spring:message code="button.update"/><!--修改--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/><!--确定是否删除?-->"   id="deleteBas" onclick="shanchuBas()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
							<span><spring:message code="button.delete"/><!--删除--></span>
						</a>
					</li>
				</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
	<thead>
		<tr >
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="5%"><spring:message code="hrm.empinfo.TRAIN_curriculum"/><!--培训课程--></th>
			<th  width="5%"><spring:message code="edu.trainBasicInformation.PEIXUNFANGSHI.a"/><!--培训方式--></th>
			<th  width="5%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></th>
			<th  width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${trainBasicInformation}" var="s" varStatus="i">
		<tr target="Bas" id="listBas_${i.count }" onclick="xuanzhongBas('${i.count }','${s.BASIC_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_trainBasic(${s.BASIC_NO });'>
           <span style="color: blue">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</span>
           </td>
           <td class="td_type" width="5%">${s.TRAIN_FORM_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td class="td_type" width="5%">
           		<c:if test="${s.IMPLE_CLASS_UNIT=='0' }">
					<span>${s.IMPLE_CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
				</c:if>
				<c:if test="${s.IMPLE_CLASS_UNIT=='1' }">
					<span>${s.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
				</c:if>
				<c:if test="${s.IMPLE_CLASS_UNIT=='2' }">
					<span>${s.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
				</c:if>
           </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>