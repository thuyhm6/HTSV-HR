<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 260,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
            //正在加载中......
        	"sProcessing": "<spring:message code='ess.message.loading' />",
            //查询不到相关数据！
            "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
            //表中无数据存在！
            "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
            //快速筛选
            "sSearch": "<spring:message code='ess.message.rapid_screening' />",
            //每页 _MENU_ 条记录
            "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
            //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
            "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
            //(从 _MAX_ 条记录过滤)
            "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
            "oPaginate": {
                //上一页
                "sPrevious": "<spring:message code='ess.message.previous_page' />",
                //下一页
                "sNext": "<spring:message code='ess.message.next_page' />"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [
              ] 
	});
});
function changeURL_ess3302(person_id){
	var href = "/ess/viewDept/ManageEmpPositionSinglList?PERSON_ID=" + person_id;
	$.pdialog.open(href,"ess3302", "<spring:message code='ess.viewDept.HR_XINXI_SEARCH.Z' />", {width:1000,height:600,mask:true});//明细查看
}
function changeURL_ess3306(empid,local_name){
	var href = "/ess/viewDept/viewDeptPersonalInfo?EMPID=" + empid + "&LOCAL_NAME" + local_name;
	$.pdialog.open(href,"ess3306", "<spring:message code='ess.viewDept.HR_XINXI_SEARCH.Z' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewDeptPersonalInfoManageList" 
		method="post" id="viewDeptPersonalInfoManageList"
		name="viewDeptPersonalInfoManageList">
		<input type="hidden" name="defaultRoleGroupName"
			value="${defaultRoleGroupName }">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门： --> <spring:message code="ess.infoApply.DEPT" />
					</td>
					<td><ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewDeptPersonalInfoManageList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewDeptPersonalInfoManageList_seachDept"
							selected="${DEPTNO}" /></td>
					<td>
						<!-- 社号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
					<td>
						<!-- 员工类型 -->
						<spring:message code="ess.infoApply.employee_type" />
					</td>
					<td><ait:SelectSyCodeByCpnyID
							id="viewDeptPersonalInfoManageList_seach_EMP_TYPE_CODE"
							name="seach_EMP_TYPE_CODE" parentNo="13864"
							selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}"
							limit="all" /></td>
					
					<td class="text"><spring:message code="hrm.empinfo.EMP_OFFICE_NAME" /> <!-- 员工状态 --> </td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" id="viewDeptPersonalInfoManageList_seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}" limit="all" /></td>
					<!-- <input type="hidden" name="seach_EMP_OFFICE" id="viewDeptPersonalInfoManageList_seach_EMP_OFFICE" value="15119"/> -->
				</tr>
				<tr>
					<td><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY_CODE" id="NATIONALITY_CODE" parentNo="870"
						cnpyID="${defaultCpny}" selected="${NATIONALITY_CODE}" limit="all" /></td>
				</tr>
			</table>
			<div class="subBar" style="padding-top: 15px">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" >
									<!--查询 -->
									<spring:message code="ess.infoApply.SELECT" />
								</button>
							</div>
						</div>
					</li>
					<li><a class="buttonActive"
						onclick="downloadExcel('viewDeptPersonalInfoManageList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=141','/ess/viewDept/viewDeptPersonalInfoManageList')"
						href="#"> <span>
								<!-- 导出到Excel -->
								<spring:message code="ess.infoApply.export_to_Excel" />
						</span>
					</a></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">


	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="viewDeptPersonalInfoManageList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);">
		<table class="orderList"border="1" width="99%"
			nowrapTD="false">
			<thead>
				<tr>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${personList}" var="personList" varStatus="i">
					<tr target="sid" rel="${personList.PERSON_ID_ID}">
						<td width="100%">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table margin_b">
								<tr>

									<td rowspan="6" width="10%" class="td_type"><img
										src="${personList.PHOTO_PATH}" width="116px" height="149px" />

									</td>

									<td class="td_title" width='15%'>
										<!-- 姓名 -->
										<spring:message code="org.title.LOCAL_NAME" />

									</td>
										<td style=" cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ess3302(${personList.PERSON_ID_ID });'>
											<span style="color: blue">${personList.LOCAL_NAME}</span>
										</td>
									<td class="td_title" width='15%'>
										<!-- 职责 -->
										<spring:message code="ess.infoApply.title.dutyName" />
									</td>
									<td class="td_type" width='30%'>
										${personList.POSITION_NO}</td>
									<!--<td class="td_title" width='15%'>
										 部门 
										<spring:message code="org.title.dept" />
									</td>
									<td class="td_type" width='30%'>
										${personList.DEPT_NAME}</td>
								--></tr>
								<tr>

									<td class="td_title" width='15%'>
										<!-- 主要业务 -->
										<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
									</td>
									<td class="td_type" width='30%'>
										${personList.MAIN_BUSINESS}</td>
									<td class="td_title" width='15%'>
										<!-- 员工类型 -->
										<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME" />
									</td>
									<td class="td_type" width='30%'>${personList.EMP_TYPE_CODE}
									</td>
								</tr>
								<tr>
									<td class="td_title" width='15%'>
										<!-- 成本中心 -->
										<spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" />
									</td>
									<td class="td_type" width='30%'>${personList.COST_CENTER}
									</td>
									<td class="td_title" width='15%'>
										<!-- 部门长 -->
										<spring:message code="org.title.MINISTER" />
									</td>
									<td class="td_type" width='30%'>
										${personList.HEAD_DEPARTMENT}</td>
								</tr>
								<tr>

									<td class="td_title" width='15%'>
										<!-- 在职期间 -->
										<spring:message code="rp.report.title.officepersoid" />
									</td>
									<td class="td_type" width='30%'>
										${personList.WORK_AGE}</td>
									<td class="td_title" width='15%'>
										<!-- 入社日期 -->
										<spring:message code="hrm.empinfo.DATE_STARTED" />
									</td>
									<td class="td_type" width='30%'>${personList.DATE_STARTED}</td>
								</tr>
								<tr>
									<td class="td_title" width='15%'>
										<!-- 最终学校 -->
										<spring:message code="hr.viewCondSql.title.ZUIZHONGXUEXIAO" />
									</td>
									<td class="td_type" width='30%'>
										${personList.DEGREE_CODE}</td>
									<td class="td_title" width='15%'></td>
									<td class="td_type" width='30%'></td>
								</tr>

							</table>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	
</div>