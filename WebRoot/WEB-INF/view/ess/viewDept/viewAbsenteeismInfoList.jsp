<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script type="text/javascript">
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
	     "scrollY": $(document.body).height() - 320,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
	    "oLanguage": {//多语言配置
	        //正在加载中......
	    	"sProcessing": "<spring:message code='ess.message.loading' />",
	    	//查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
	        "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
	        "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
	        "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
	        //(从 _MAX_ 条记录过滤)
	        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
	        "oPaginate": {
	            "sPrevious": '<spring:message code="hrm.alert.contractInfo.Previous_page"/>',
	            "sNext": '<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>'
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [
	          ] 
	});
});
</script>
<div class="panel">
	<!--<h1>
	    旷工查询<spring:message code="ar.viewAbsenteeismInfoList.KUANGGONGCHAXUN.b" />
	</h1>-->
	</div>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="/ess/viewDept/viewAbsenteeismInfoList" method="post"
			id="viewAbsenteeismInfoList" name="viewAbsenteeismInfoList">
			<input type="hidden" name='CODE_NO' />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<!-- 部门： -->
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
						</td>
						<td>
							<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
								limit="ar" id="viewAbsenteeismInfoList_seachDept" />
							<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
								limit="ar" id="viewAbsenteeismInfoList_seachDept"
								selected="${DEPT_NO}" />
						</td>
						<td>
							<!-- 社号/姓名： -->
							<spring:message
								code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
						</td>
						<td>
							<input type="text" name="seach_KEY" value="${KEY}" />
						</td>
						<td>
						    <!--员工状态--><spring:message code="org.title.EMP_OFFICE_NAME" />
						</td>
						<td>
							<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE"
								id="seach_EMP_OFFICE" parentNo="15118"
								cnpyID="${LoginUser.cpnyId}" limit="ALL"
								selected="${EMP_OFFICE }" />
						</td>
						<td>
							<c:choose>
								<c:when test="${withAllatt == 'Y'}">
									<input type="checkbox" name="seach_withAllatt" value="Y"
										checked="checked" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="seach_withAllatt" value="Y" />
								</c:otherwise>
							</c:choose>
							<!--包括迟到早退--><spring:message code="ar.viewAbsenteeismInfoList.BAOKUOCHIDAOZAOTUI.b" />
						</td>
					</tr>
					<tr>
						<td>
							<!--期间 -->
							<spring:message code="ess.infoApply.Period" />
						</td>
						<td>
							<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE"
								class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
								value="${FROM_DATE}" />
							-
							<input type="text" id="seach_TO_DATE" name="seach_TO_DATE"
								class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
								value="${TO_DATE}" />
						</td>
						<td>
							<!--班组 -->
							<spring:message code="hr.viewPersonalInfo.title.banzu" />
						</td>
						<td>
							<ait:SelectSyCodeByCpnyID name="seach_HRGROUP_NO"
								id="seach_HRGROUP_NO" parentNo="400223"
								cnpyID="${LoginUser.cpnyId}" limit="ALL"
								selected="${HRGROUP_NO }" />
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
					</ul>
				</div>
			</div>
		</form>
	</div>

	<div class="pageContent">
		<div class="formBar">
			<ul class="toolBar">
				<li>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<a class="buttonActive"
						onclick="downloadExcel('viewAbsenteeismInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=140','/ess/viewDept/viewAbsenteeismInfoList?firstFlag=N')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span>
					</a>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<a class="buttonActive"
						onclick="downloadExcel('viewAbsenteeismInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=310','/ess/viewDept/viewAbsenteeismInfoList?firstFlag=N')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span>
					</a>
				</c:if>
				</li>
			</ul>
		</div>
		<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
			method="post" action="/ess/viewDept/viewAbsenteeismInfoList"
			onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
			target="dialog">
			<table class="orderList" border="1" width="99%">
				<thead>
					<tr>
						<th>
							NO
						</th>
						<th>
							<!--工号--><spring:message code="public.title.empId" />
						</th>
						<th>
							<!--姓名--><spring:message code="public.title.empName" />
						</th>
						<th>
							<!--部门名--><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG" />
						</th>
						<th>
							<!--职级--><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME" />
						</th>
						<th>
							<!--旷工--><spring:message code="ar.monthwork.title.kuanggong" />/<!--早退--><spring:message code="ar.monthwork.title.EarlyLeave" />-<!--日期--><spring:message code="ar.monthwork.title.Attendanceday" />
						</th>
						<th>
							<!--工作时间--><spring:message code="ess.infoApply.working_hours" />
						</th>
						<th>
							<!--进门--><spring:message code="ar.viewarcardrecord.title.jinmen" />
						</th>
						<th>
							<!--出门--><spring:message code="ar.viewarcardrecord.title.chumen" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${viewAbsenteeismInfoList}" var="personList"
						varStatus="i">
						<tr target="sid" rel="">
							<td style="text-align: center">

								${i.count}
							</td>
							<td style="text-align: center">

								${personList.EMPID_ID}
							</td>
							<td style="text-align: center">

								${personList.LOCAL_NAME}
							</td>
							<td style="text-align: center">

								${personList.DEPT_NAME}
							</td>
							<td style="text-align: center">

								${personList.POST_GRADE_NO}
							</td>
							<td style="text-align: center">

								${personList.ITEM_NAME}/${personList.AR_DATE_STR}
							</td>
							<td style="text-align: center">
								${personList.SHIFT_SHORTNAME}

							</td>
							<td style="text-align: center">
								${personList.IN_TIME}

							</td>
							<td style="text-align: center">

								${personList.OUT_TIME}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
