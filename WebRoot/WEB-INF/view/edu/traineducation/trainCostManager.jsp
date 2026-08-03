<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	costImport();
});
	var quanCosnoCos="";
	var quanCos="";
	function xiugaiCos(){
			if(quanCosnoCos!=""){
				$('#updateCos').attr('href','/edu/traineducation/trainCostManagerInfo?COST_NO='+quanCosnoCos);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongCos(no,nono){
		var count="${trainCostManagerListCount}";
		for(var i=1;i<=count;i++){
			$('#listCos_'+i).attr('style','');
		}
		$('#listCos_'+no).attr('style','background:#aaccf6');
		$('#updateCos').attr('href','/edu/traineducation/trainCostManagerInfo?COST_NO='+nono);
		$('#yinCos').attr('style','display:none');
		$('#xianCos').attr('style','');
		quanCos=no;
		quanCosnoCos=nono;
	}
	function shanchuCos(){
		if(quanCosnoCos!=""){
		$('#deleteCos').attr('href','/edu/traineducation/deleteTrainCostManager?COST_NO='+quanCosnoCos);
	}else{
		$('#deleteCos').attr('href','/edu/traineducation/deleteTrainCostManager?{Cos}');
	}
  }
	function sousuoCos(){
		$('#trainCostManager').submit();
	}
function costImport(){
	var costname=$('#costname').val();
	var coststartdate=$('#coststartdate').val();
	var costenddate=$('#costenddate').val();
	$('#costImport').attr('href','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=162&coursename='+costname+'&startdate='+coststartdate+'&enddate='+costenddate);
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
</script>
<form id="trainCostManager" onsubmit="return navTabSearch(this);" action="/edu/traineducation/trainCostManager" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="coursename" id="costname" value="${coursename }" onchange="costImport()" >
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="startdate" id="coststartdate" onchange="costImport()"  onClick="WdatePicker({dateFmt:'dd-MM-yyyy'})" value="${startdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="enddate" id="costenddate" onchange="costImport()" onClick="WdatePicker({dateFmt:'dd-MM-yyyy'})" value="${enddate}" class="Wdate"/>
		</td>
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 200px;">       
		            
		            <li>
						<a class="buttonActive" href="#" onclick="sousuoCos()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索--></span>
						</a>
					</li>
					<li id="yinCos" style="">
						<a class="buttonActive"  onclick="xiugaiCos()" href="#"  width="1000" height="250">
							<span><spring:message code="button.update"/><!--修改--></span>
						</a>
					</li>
					<li id="xianCos" style="display:none">
						<a class="buttonActive" id="updateCos" onclick="xiugaiCos()" href="#" target="dialog" mask="true"  width="1000" height="600">
							<span><spring:message code="button.update"/><!--修改--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" id="costImport" href="#">
							<span><spring:message code="pa.insurance.title.excelExport"/><!--Excel导出--></span>
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
			<th  width="5%"><spring:message code="edu.trainCostMANAGER.PEIXUNMINGCHENGQICI.a"/><!--培训名称(期次)--></th>
			<c:if test="${CPNY_ID=='HAE' }">
			<th  width="5%"><spring:message code="edu.trainCostMANAGER.YUJIFEIYONG.a"/><!--预计费用--></th>
			</c:if>
			<th  width="5%"><spring:message code="edu.trainCostMANAGER.FEIYONGHEJI.a"/><!--费用合计--></th>
			<th  width="5%"><spring:message code="edu.trainCostMANAGER.RENJUNFEIYONG.a"/><!--人均费用--></th>
			<th  width="5%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${trainCostManagerList}" var="s" varStatus="i">
		<tr target="Cos" id="listCos_${i.count }" onclick="xuanzhongCos('${i.count }','${s.COST_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/>&nbsp<!--期--><spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
           <c:if test="${CPNY_ID=='HAE' }">
           <td class="td_type" width="5%">${s.BUDGET }</td>
           </c:if>
           <td class="td_type" width="5%">${s.ALL_COST }</td>
           <td class="td_type" width="5%">${s.AVG_COST }</td>
           <td class="td_type" width="5%">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>