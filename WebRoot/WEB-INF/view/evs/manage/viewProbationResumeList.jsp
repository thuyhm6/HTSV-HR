<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//考核对象生成
	$("#viewEvsAffirmorSetup_Create",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewProbationResumeList.QUEDINGYAOSHENGCHENGKAOHEDUIXIANGMA.a'/>",//确定要生成考核对象吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/createEvsTargetPro',
					data: { SEQ: '${RESUME_SEQ}' },
	  				dataType:"json",
	  				cache: false,
	  				success: function(){
		  				alertMsg.info("<spring:message code='evs.viewProbationResumeList.SHENGCHENGGONGGONG.a'/>");//生成成功
		  				navTab.reload('/evs/manage/viewProbationResumeList?evsType=${evsType }', { navTabId:'${navTabId}'});
					},
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
});

$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": $(document.body).height() - 200,
    "scrollX": true,
    "orderClasses": false
});
</script>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewEvsAffirmorSetup_Create"><span>Create Target</span></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="navTab.reload('/evs/manage/viewProbationResumeList?evsType=${evsType }', { navTabId:'${navTabId}'});"><span><spring:message code="button.search"/><!--查询--></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=60&EVS_TYPE=${evsType}"><span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到Excel--></span></a>					
			</li>
	</ul>
</div>
		<div style="font:bold 12px/20px arial,sans-serif;;float:left;height:20px;line-height:20px;">Total:${resumeSize}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="100px"><spring:message code="hrm.empinfo.empid"/><!--社号--></th>
					<th width="100px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
					<th width="200px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!--部门--></th>
					<th width="150px"><spring:message code="hrm.empinfo.DATE_STARTED"/><!--入社日期--></th>
					<th width="150px"><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE"/><!--试用期结束日期--></th>
					<th width="120px"><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/><!--员工类型--></th>
					<th width="120px"><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/><!--职级--></th>
					<th width="120px"><spring:message code="org.title.MAIN_BUSINESS"/><!--主要业务--></th>
					<th width="120px"><spring:message code="org.title.PRODUCT_TYPE"/><!--产品类型--></th>
					<th width="120px"><spring:message code="evs.viewResumeList.GONGZUOJINGXINGZHUANGTAI.a"/><!--工作进行状态--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resumeList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>${i.count}</td>
						<td>${item.EMPID}</td>
						<td>${item.LOCAL_NAME}</td>
						<td>${item.DEPTNAME}</td>
						<td>${item.DATE_STARTED}</td>
						<td>${item.PROBATION_END_DATE}</td>
						<td>${item.EMP_TYPE_NAME}</td>
						<td>${item.POST_GRADE_NAME}</td>
						<td>${item.MAIN_BUSINESS_NAME}</td>
						<td>${item.PRODUCT_CODE_NAME}</td>
						<td>
							<c:if test="${item.ACTIVITY eq 0}"><spring:message code="hrm.approve.RETURN"/><!--退回--></c:if>
							<c:if test="${item.ACTIVITY eq 1}"><spring:message code="evs.viewProbationResumeList.ZIWOPINGJIA.a"/><!--自我评价--></c:if>
							<c:if test="${item.ACTIVITY eq 2}"><spring:message code="evs.viewProbationResumeList.KAOHEZHEJINXINGZHONG.a"/><!--考核者进行中--></c:if>
							<c:if test="${item.ACTIVITY eq 3}"><spring:message code="evs.viewEvsResult.WANCHENG.a"/><!--完成--></c:if>
							<c:if test="${item.ACTIVITY eq 4}"><spring:message code="org.title.END"/><!--结束--></c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>