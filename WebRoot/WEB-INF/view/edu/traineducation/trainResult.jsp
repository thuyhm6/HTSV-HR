<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var resubasicno="${resubasicno}";
	
	if(resubasicno!=""){
		var array=resubasicno.split(",");
		var basnum=0;
		$('#resuEva td[name=isnotjinxingRESU]').each(function(){
			var resubas=$(this).attr('resubas');
			if(resubas==array[basnum]){
				$(this).attr('style','color:blue;');
			}
			basnum=basnum+1;
		});
		
	}
	
	
	
});
	var quanRESUno="";
	var quanRESU="";
	function xiugaiRESU(){
			if(quanRESUno!=""){
				$('#updateRESU').attr('href','/edu/traineducation/trainResultInfo?BASIC_NO='+quanRESUno);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongRESU(no,nono){
		var count="${trainResultListCount}";
		for(var i=1;i<=count;i++){
			$('#listRESU_'+i).attr('style','');
		}
		$('#listRESU_'+no).attr('style','background:#aaccf6');
		$('#updateRESU').attr('href','/edu/traineducation/trainResultInfo?BASIC_NO='+nono);
		$('#yinRESU').attr('style','display:none');
		$('#xianRESU').attr('style','');
		quanRESU=no;
		quanRESUno=nono;
	}
	function shanchuRESU(){
		if(quanRESUno!=""){
			$('#deleteRESU').attr('href','/edu/traineducation/deleteTrainResult?BASIC_NO='+quanRESUno);
	}else{
		$('#deleteRESU').attr('href','/edu/traineducation/deleteTrainResult?{RESU}');
	}
  }
	function sousuoRESU(){
		$('#trainResult').submit();
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
	
function changeURL_resultHAE(basicNo,resultNo){
	var href = "/edu/traineducation/alreadyTrainResultInfoSingle?BASIC_NO=" + basicNo + "&RESULT_NO=" + resultNo;
	$.pdialog.open(href,"edu0204", "<spring:message code='pa.salary.title.fullInfo' />", {width:1000,height:600,mask:true});//查看
}

function changeURL_resultHTSV(basicNo,resultNo){
	var href = "/edu/traineducation/alreadyTrainResultTSTOInfoSingle?BASIC_NO=" + basicNo + "&RESULT_NO=" + resultNo;
	$.pdialog.open(href,"edu0204", "<spring:message code='pa.salary.title.fullInfo' />", {width:1000,height:600,mask:true});//查看
}
</script>
<form id="trainResult" onsubmit="return navTabSearch(this);" action="/edu/traineducation/trainResult" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="startdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${startdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="enddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${enddate}" class="Wdate"/>
		</td>
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 100px;">
            <li>
				<a class="buttonActive" href="#" onclick="sousuoRESU()" width="800" height="250" >
					<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
				</a>
			</li>
		</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table class="list" id="viewInfoTable">
	<thead>
		<tr >
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="5%"><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></th>
			<th  width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
			<th  width="5%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></th>
			<th  width="5%"><spring:message code="inct.salesman.eval.result"/><!--评价结果--></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${trainResultList}" var="s" varStatus="i">
		<tr target="RESU" id="listRESU_${i.count }" onclick="xuanzhongRESU('${i.count }','${s.BASIC_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%" style="" name="isnotjinxingRESU" resubas="${s.BASIC_NO }">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/>&nbsp<!--期--><spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
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
           <td class="td_type" width="5%">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td class="td_type" width="5%">
		<c:if test="${ALLTEACHEREMPID!='11111111'&&ALLTEACHEREMPID!='11111112'&&s.flag=='1' }">
		<c:if test="${s.TRAINRESULTCOUNT=='0' }">
		  <%-- <c:if test="${LoginUser.cpnyId eq 'HAE'}">
		     <a  id="resuweikao_${s.BASIC_NO }" href="/edu/traineducation/trainResultInfo?RESULT_NO=${s.RESULT_NO }&BASIC_NO=${s.BASIC_NO }&stuempid=${ALLTEACHEREMPID}" target="dialog" mask="true"  width="900" height="300">
		     <span id="resuweikaoping_${s.BASIC_NO }"><spring:message code="edu.teacherEvaluate.WEIKAOPING.a"/><!--未考评--></span></a>
		  </c:if> --%>
		  <%-- <c:if test="${LoginUser.cpnyId eq 'HTSV'}"> --%>
		     <a  id="resuweikao_${s.BASIC_NO }" href="/edu/traineducation/trainResultTSTOInfo?RESULT_NO=${s.RESULT_NO }&BASIC_NO=${s.BASIC_NO }&stuempid=${ALLTEACHEREMPID}" target="dialog" mask="true"  width="900" height="400">
		     <span id="resuweikaoping_${s.BASIC_NO }"><spring:message code="edu.teacherEvaluate.WEIKAOPING.a"/><!--未考评--></span></a>
		  <%-- </c:if> --%>
		</c:if>
		<c:if test="${s.TRAINRESULTCOUNT!='0' }">
		  <%-- c:if test="${LoginUser.cpnyId eq 'HTSV'}"> --%>
			<a style="color:red;" id="resuweikao_${s.BASIC_NO }" href="/edu/traineducation/trainResultTSTOInfo?RESULT_NO=${s.RESULT_NO }&BASIC_NO=${s.BASIC_NO }&stuempid=${ALLTEACHEREMPID}" target="dialog" mask="true"  width="900" height="400">
		     <span id="resuweikaoping_${s.BASIC_NO }"><spring:message code="edu.teacherEvaluate.YIKAOPING.a"/><!--已考评--></span></a>
		 <%--  </c:if> --%>
		 <%--  <c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<a  style="color:red;" href="/edu/traineducation/alreadyTrainResultInfoSingle?RESULT_NO=${s.RESULT_NO }&BASIC_NO=${s.BASIC_NO }&stuempid=${ALLTEACHEREMPID}" target="dialog" mask="true"  width="900" height="300">
			<span><spring:message code="edu.teacherEvaluate.YIKAOPING.a"/><!--已考评--></span></a>
		  </c:if> --%>
		</c:if>
		</c:if>
		   <%-- <c:if test="${ALLTEACHEREMPID=='11111111'||ALLTEACHEREMPID=='11111112'||ALLTEACHEREMPID=='40110008'||ALLTEACHEREMPID=='30100104' }"> --%>
			<%-- <c:if test="${LoginUser.cpnyId eq 'HAE'}"> --%>
				<a class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_resultHAE("${s.BASIC_NO }","${s.RESULT_NO }");'>
	            <span style="color: blue"><spring:message code="button.sys.view"/><!--查看--></span>
	            </a>
		  <%--  </c:if> --%>
			<%-- <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<a class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_resultHTSV("${s.BASIC_NO }","${s.RESULT_NO }");'>
	            <span style="color: blue"><spring:message code="button.sys.view"/><!--查看--></span>
	            </a>
		   </c:if> --%>
		 <%-- </c:if>   --%>
           </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>