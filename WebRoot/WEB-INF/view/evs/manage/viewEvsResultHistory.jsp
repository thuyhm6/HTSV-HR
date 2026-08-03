<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsResultHistory_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsResultHistoryForm",navTab.getCurrentPanel()).submit();
	});

	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResultHistory&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResultHistory&seach_KEY='+name);
   });

	$(".evsList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 310,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
		"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",//表中无数据存在！
        "sSearch": "<spring:message code='ess.message.rapid_screening'/>"//快速筛选
        } //多语言配置
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsResultHistoryForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsResultHistory" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><spring:message code="pa.payear.title.payear"/><!--年份--></td>
					<td>
						<input type="text" name="seach_START_YEAR" class="Wdate" value="${START_YEAR }" onClick="WdatePicker({dateFmt:'yyyy'})"/>~
						<input type="text" name="seach_END_YEAR" class="Wdate" value="${END_YEAR }" onClick="WdatePicker({dateFmt:'yyyy'})"/>
					</td>
					<td><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></td>
					<td>
						<input type="text" name="seach_START_DATE_STARTED" class="Wdate" value="${START_DATE_STARTED }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>~
						<input type="text" name="seach_END_DATE_STARTED" class="Wdate" value="${END_DATE_STARTED }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>
					</td>
					<td><spring:message code="evs.viewEvsResultHistory.TUIZHIRI.a"/><!--退职日--></td>
					<td>
						<input type="text" name="seach_START_DATE_LEFT" class="Wdate" value="${START_DATE_LEFT }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>~
						<input type="text" name="seach_END_DATE_LEFT" class="Wdate" value="${END_DATE_LEFT }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>
					</td>
				</tr>
				<tr>
					<td><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="hr" id="viewEvsResultHistory_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewEvsResultHistory_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
					</td>
					<td><spring:message code="ess.infoApply.renzhizhuangtai"/><!--任职状态--></td>
					<td>
						<ait:selectCodeMulti id="seach_EMP_OFFICE" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE}" selectedNm="${EMP_OFFICE_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsResultHistory_Serch">
							<span><spring:message code="org.title.SELECT"/><!--查询--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExcel('viewEvsResultHistoryForm','/evs/manage/viewEvsResultHistoryExport','/evs/manage/viewEvsResultHistory')">
							<span><spring:message code="ess.infoApply.export_to_Excel"/><!--导出到Excel--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${objectListSize}</div>
				<table  class="evsList" width="1100px;">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="70px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
							<th width="70px"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
							<th width="100px"><spring:message code="ess.infoApply.DEPT"/><!--部门--></th>
							<th width="80px"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></th>
							<th width="80px"><spring:message code="hrm.empinfo.Evaluation_year"/><!--评价年度--></th>
							<th width="50px">1<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">2<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">3<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">4<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">5<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">6<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">7<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">8<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">9<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">10<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">11<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px">12<spring:message code="display.mutual.month"/><!--月--></th>
							<th width="50px"><spring:message code="hr.viewEvaluate.title.EV_ABIL"/><!--能力--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${objectList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;">${item.LOCAL_NAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;">${item.EMPID}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;">${item.DEPTNAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;">${item.POST_GRADE_NAME}</td>
								<td style="text-align:center">${item.EVS_YEAR}</td>
								<td style="text-align:center">${item.EVS_MONTH1}</td>
								<td style="text-align:center">${item.EVS_MONTH2 }</td>
								<td style="text-align:center">${item.EVS_MONTH3 }</td>
								<td style="text-align:center">${item.EVS_MONTH4 }</td>
								<td style="text-align:center">${item.EVS_MONTH5 }</td>
								<td style="text-align:center">${item.EVS_MONTH6 }</td>
								<td style="text-align:center">${item.EVS_MONTH7 }</td>
								<td style="text-align:center">${item.EVS_MONTH8 }</td>
								<td style="text-align:center">${item.EVS_MONTH9}</td>
								<td style="text-align:center">${item.EVS_MONTH10}</td>
								<td style="text-align:center">${item.EVS_MONTH11}</td>
								<td style="text-align:center">${item.EVS_MONTH12}</td>
								<td style="text-align:center">${item.EVS_MONTH13}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
