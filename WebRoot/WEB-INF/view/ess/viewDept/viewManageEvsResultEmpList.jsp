<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#viewManageEvsResultEmpListTable",navTab.getCurrentPanel()).dataTable({
				"bPaginate": true,    //分页
			    "bAutoWidth":false,//表格宽度不自动变化
			    "bProcessing":true,
			    //"lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
				//"bLengthChange": true,  //按多少条记录显示下拉框
				//"iDisplayLength": 20, //默认每页显示的记录数
				"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		     "searching": true,//本地搜索
				"bSort": true,   //排序功能
				"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
			//"bScrollInfinite":true,
		     "orderClasses": true,
		     "order":[],//初始化不用自动排序
		     "scrollY": $(document.body).height() - 260,
		     "scrollCollapse": false,
		     "deferRender":true,
		     "scroller":true,
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
            "sDom":'<"top"r<"clear">f>t<"bottom"i<"clear">>'
		});
	$("#viewManageEvsResultEmpListTable tbody",navTab.getCurrentPanel()).on( 'click', 'tr', function () {
			$(this).toggleClass('selected');
	 } );
});
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewManageEvsResultEmpList" method="post"
		id="ManageEvsResultEmpList" name="ManageEvsResultEmpList">
		<input type="hidden" name='CODE_NO' />
		<input type="hidden" name='firstFlag' value="N"/>
		<input type="hidden" name='ADMIN_ID' value="${LoginUser.adminID }"/>
		<input type="hidden" name='seach_LIMIT' value="${LIMIT }"/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td><ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="manageEvsResultEmpList_seachDept" limit="manager"/> <ait:deptTreeIcon
							name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="manager"
							id="manageEvsResultEmpList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
					<td width="10%"><!-- 年度 --><spring:message code="pa.salary.canShu.nianDu" /></td>
						<td width="20%">
				 			<input type="text" size="5" name="seach_EVS_YEAR" id="seach_EVS_YEAR" value="${EVS_YEAR }" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy',lang:'en'})" />
						</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
	                <li><a class="buttonActive" onclick="downloadExcel('ManageEvsResultEmpList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=208','/ess/viewDept/viewManageEvsResultEmpList')">
	                    <span><spring:message code="ess.infoApply.export_to_Excel" /><!-- 导出到Excel --></span></a></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" style="overflow: hidden;">
		<table id="viewManageEvsResultEmpListTable" class="orderList" width="100%">    
			<thead>
				<tr>
					<th><!-- 状态 --><spring:message code="hrm.empinfo.Evaluation_year" /></th>
					<th><!-- 社号 --><spring:message code="public.title.empId" /></th>
					<th><!-- 姓名 --><spring:message code="public.title.empName" /></th>
					<th><!-- 部门 --><spring:message code="public.title.deptName" /></th>
					<%-- <th><!-- 1月 --><spring:message code="hrm.empinfo.January" /></th>
					<th><!-- 2月 --><spring:message code="hrm.empinfo.February" /></th>
					<th><!-- 3月 --><spring:message code="hrm.empinfo.March" /></th>
					<th><!-- 4月 --><spring:message code="hrm.empinfo.April" /></th>
					<th><!-- 5月 --><spring:message code="hrm.empinfo.May" /></th>
					<th><!-- 6月 --><spring:message code="hrm.empinfo.June" /></th>
					<th><!-- 7月 --><spring:message code="hrm.empinfo.July" /></th>
					<th><!-- 8月 --><spring:message code="hrm.empinfo.August" /></th>
					<th><!-- 9月 --><spring:message code="hrm.empinfo.September" /></th>
					<th><!-- 10月 --><spring:message code="hrm.empinfo.October" /></th>
					<th><!-- 11月 --><spring:message code="hrm.empinfo.November" /></th>
					<th><!-- 12月 --><spring:message code="hrm.empinfo.December" /></th> --%>
					<th width="30%"><!-- 能力 --><spring:message code="hr.viewEvaluate.title.EV_ACHI" /></th>
					<th width="30%"><!-- 能力 --><spring:message code="hr.viewEvaluate.title.EV_ABIL" /></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${item.EVS_YEAR}</td>
					<td style="text-align:center">
						<a href="/ess/viewDept/viewDeptPersonalInfo?EMPID=${item.EMPID}&LOCAL_NAME=${item.LOCAL_NAME}"
											target="dialog" style="color: blue;" mask="true" width="600"
											height="350"> ${item.EMPID} </a>
					</td>
					<td style="text-align:center">${item.LOCAL_NAME}</td>
					<td style="text-align:center">${item.DEPT_NAME}</td>
					<%-- <td style="text-align:center">${item.EVS_MONTH1}</td>
					<td style="text-align:center">${item.EVS_MONTH2 }</td>
					<td style="text-align:center">${item.EVS_MONTH3 }</td>
					<td style="text-align:center">${item.EVS_MONTH4 }</td>
					<td style="text-align:center">${item.EVS_MONTH5 }</td>
					<td style="text-align:center">${item.EVS_MONTH6 }</td>
					<td style="text-align:center">${item.EVS_MONTH7 }</td>
					<td style="text-align:center">${item.EVS_MONTH8 }</td>
					<td style="text-align:center">${item.EVS_MONTH9}</td>
					<td style="text-align:center">${item.EVS_MONTH10}</td>
					<td style="text-align:center">${item.EVS_MONTH11}</td> --%>
					<td style="text-align:center">${item.EVS_MONTH12}</td>
					<td style="text-align:center">${item.EVS_MONTH13}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	<c:if test="${LoginUser.cpnyId eq 'SST'}">
		<table id="viewManageEvsResultEmpListTable" class="orderList" width="100%">    
			<thead>
				<tr>
					<th >年</th>
					<th>姓名</th>
					<th>社号</th>
					<th>部门</th>
					<th >考核类型</th>
					<th >考核时段 </th>
					<th >等级</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${item.EVS_YEAR}</td>
					<td style="text-align:center">${item.LOCAL_NAME}</td>
					<td style="text-align:center">
						<a href="/ess/viewDept/viewDeptPersonalInfo?EMPID=${item.EMPID}&LOCAL_NAME=${item.LOCAL_NAME}"
											target="dialog" style="color: blue;" mask="true" width="600"
											height="350"> ${item.EMPID} </a></td>
					<td style="text-align:center">${item.DEPT_NAME}</td>
					<td style="text-align:center">${item.EVS_TYPE_NAME}</td>
					<td style="text-align:center">${item.EVS_CYCLE_NAME }</td>
					<td style="text-align:center">${item.FINAL_GRADE }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>