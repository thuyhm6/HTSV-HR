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
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 35, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 270,
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

function changeURL_ess3211(person_id){
	var href = "/ess/viewDept/ManageEmpPositionSinglList?PERSON_ID=" + person_id;
	$.pdialog.open(href,"ess3211", "<spring:message code='ess.viewDept.ZHIWU_JINGLI.Z' />", {width:800,height:500,mask:true});//明细查看
}

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/ManageEmpPositionInfoList" method="post"
		id="ManageEmpPositionInfoList" name="ManageEmpPositionInfoList">
		<input type="hidden" name='CODE_NO' />
		<input type="hidden" name='ADMIN_ID' value="${LoginUser.adminID }"/>
		<input type="hidden" name="defaultRoleGroupName" value="${defaultRoleGroupName }">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门： --> <spring:message
							code="ess.infoApply.DEPT" />
					</td>
					<td><ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="manageEmpPositionInfoList_seachDept" limit="manager" /> <ait:deptTreeIcon
							name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="manager"
							id="manageEmpPositionInfoList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!-- 社号/姓名 -->
					</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
					<td><spring:message code="ess.infoApply.employee_type" /><!-- 员工类型 --></td>
						<td>
				 			<ait:SelectSyCodeByCpnyID id="manageEmpPositionInfoList_seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
						
						<td class="text"><spring:message code="hrm.empinfo.EMP_OFFICE_NAME" /> <!-- 员工状态 --> </td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" id="manageEmpPositionInfoList_seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}" limit="all" /></td>
                                    		
					<td>
						<spring:message code="ess.empInfo.date_of_agency" /> <!-- 入社日期  -->
					</td>
					<td class="td_type" colspan="6">
						<input name="DATE_FROM_STARTED" id="manageEmpPositionInfoList_DATE_FROM_STARTED" class="Wdate" 
						onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${DATE_FROM_STARTED}"/>~
						<input name="DATE_TO_STARTED" id="manageEmpPositionInfoList_DATE_TO_STARTED" class="Wdate" 
						onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${DATE_TO_STARTED}"/>
					</td>
				</tr>
                <tr>
					<td><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY_CODE" id="NATIONALITY_CODE" parentNo="870"
						cnpyID="${defaultCpny}" selected="${NATIONALITY_CODE}" limit="all" /></td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td><spring:message
						code="hrm.empinfo.personSupplier" /><!-- 供人公司 --></td>
					<td >
						<select name="COMPANY_NAME" id="COMPANY_NAME">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${supplierList}" var="result">
								<option value="${result.COMPANY_NAME}" name="${result.COMPANY_NAME}" >${result.COMPANY_NAME}</option>
							</c:forEach>
						</select>
					</td>
					</c:if>
                    <%-- <spring:message code="ess.empInfo.Nation" />民族
                    </td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="NATION_CODE" id="manageEmpPositionInfoList_NATION_CODE"
								parentNo="210942" cnpyID="${defaultCpny}" selected="${NATION_CODE}" limit="all" />
					</td>
					<td>
						<spring:message code="hrm.empinfo.SEXCODE" />性别
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="SEXCODE" id="manageEmpPositionInfoList_SEXCODE" parentNo="1324"
								cnpyID="${defaultCpny}" selected="${SEXCODE }" limit="all" />
					</td>
					<td>
						<spring:message code="ess.empInfo.age" />年龄
					</td>
					<td class="td_type">
						<select name="AGE" id="manageEmpPositionInfoList_AGE">
							<option value="NULL"><spring:message code="org.title.PLEASE_SELECT" />请选择</option>
							<option value="~19"   <c:if test="${AGE eq '~19'}">selected="selected"</c:if>>~19</option>
							<option value="20~24" <c:if test="${AGE eq '20~24'}">selected="selected"</c:if>>20~24</option>
							<option value="25~29" <c:if test="${AGE eq '25~29'}">selected="selected"</c:if>>25~29</option>
							<option value="30~34" <c:if test="${AGE eq '30~34'}">selected="selected"</c:if>>30~34</option>
							<option value="35~39" <c:if test="${AGE eq '35~39'}">selected="selected"</c:if>>35~39</option>
							<option value="40~44" <c:if test="${AGE eq '40~44'}">selected="selected"</c:if>>40~44</option>
							<option value="45~49" <c:if test="${AGE eq '45~49'}">selected="selected"</c:if>>45~49</option>
							<option value="50~"   <c:if test="${AGE eq '50~'}">selected="selected"</c:if>>50~</option>
						</select>
					</td> --%>                   		
                 </tr>
             </table>
			<div class="subBar" style="padding-top: 15px">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" >
									<!-- 查询 --><spring:message code="ess.infoApply.SELECT" />
								</button>
							</div>
						</div>
					</li>
	<li><a class="buttonActive" onclick="downloadExcel('ManageEmpPositionInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=143','/ess/viewDept/ManageEmpPositionInfoList')"><span><!--导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="ManageEmpPositionInfoList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
		target="dialog">
		<table class="orderList"border="1" width="99%"
			nowrapTD="false">
			<thead>
				<tr>
					<th>NO</th>
					<th>
						<!--社号 --> <spring:message code="ess.infoApply.EMPID" />
					</th>
					<th>
						<!--姓名 --> <spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--入职日期--> <spring:message code="org.title.DATE_STARTED" />
					</th>
					<th>
						<!--部门--> <spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--部门长 --> <spring:message code="org.title.MINISTER" />
					</th>
					<th><!--职级 --> <spring:message code="org.title.POST_GRADE_NAME" /></th>
					<th>
						<!--职责 --> <spring:message code="hrm.contract.POSITION_NO" />
					</th>
					<th>
						<!--主要业务 --> <spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ManageEmpPositionInfoList}" var="personList"
					varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">${i.count}</td>
						<td style="text-align: center">${personList.EMPID_ID}</td>
						<td style="text-align: center" onclick='javascript:changeURL_ess3211(${personList.PERSON_ID});'>
								<span style="color: blue">${personList.LOCAL_NAME}</span>
						</td>
						<td style="text-align: center">${personList.DATE_STARTED}</td>
						<td style="text-align: center">${personList.DEPTNAME}</td>
						<td style="text-align: center">${personList.MANAGER_EMP_NAME}</td>
						<td style="text-align: center">${personList.POST_GRADE_NO}</td>
						<td style="text-align: center">${personList.POSITION_NAME}</td>
						<td style="text-align: center">${personList.MAIN_BUSINESS}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<%-- <c:set value="/ess/viewDept/ManageEmpPositionInfoList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> --%>
</div>