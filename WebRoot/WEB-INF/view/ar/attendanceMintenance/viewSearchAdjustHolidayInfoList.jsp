<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><!-- 
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script> -->
<script>
$(document).ready(function(){
    
    $("#viewSearchAdjustHolidayInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewSearchAdjustHolidayInfoList",navTab.getCurrentPanel()).submit();
	   });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 310,
	     "scrollX": $(document.body).width() - 40,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":false }
	                     ],
	     "fixedColumns":false,
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
	    "buttons": [] 
		});
});
</script>
<div class="panel"><h1><!--倒休搜索--><spring:message code="ar.viewSearchAdjustHolidayInfoList.DAOXIUSOUSUO.b" /></h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewSearchAdjustHolidayInfoList?firstFlag=N" method="post"
		id="viewSearchAdjustHolidayInfoList" name="viewSearchAdjustHolidayInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			   <tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
					</td>
					 <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewArAdjustHolidayManagent_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewArAdjustHolidayManagent_seachDept" selected="${DEPTNO}"/>
					</td>
					<td >
						<spring:message code="ess.workgroup.title.duration" text="期间"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${FROM_DATE }"/>
				      ~
					  <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${TO_DATE }"/>
					</td>
								
			<!-- 	</tr>
				<tr> -->
					<%-- <td>班组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_ID" name="seach_GROUP_ID" parentNo="400223" selected="${GROUP_ID}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td> --%>
					<%-- <td>审批状态 &nbsp;&nbsp;&nbsp;&nbsp;
						  <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
					</td> --%>
					<td><!-- 员工类型 --><spring:message code="org.title.EMP_TYPE"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	<li><a class="buttonActive" id="viewSearchAdjustHolidayInfoList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('viewSearchAdjustHolidayInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=185&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewSearchAdjustHolidayInfoList?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	 </ul>
</div>
<input type="hidden" id="AdjustHolidayListCnt" value="Total:${fn:length(adjustCoordList)}">
	  <%-- <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(adjustCoordList)}</div> --%> 
		<table class="orderList" width="99%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
				    	<!-- 姓名 --><spring:message code="org.title.LOCAL_NAME"/>
				    </th>
					<th>
						<!-- 社号 --><spring:message code="ess.infoApply.EMPID"/>
					</th>
					<th>
						<!-- 部门名 --><spring:message code="ess.infoApply.DEPT_NAME"/>
					</th>
					<th>
						<!-- 职级 --><spring:message code="sys.postManage.title.postGrade"/>
					</th>
					<th>
						<!-- 日期 --><spring:message code="ess.infoApply.date"/>
					</th>
					<th>
						<!-- 星期 --><spring:message code="ess.infoApply.week"/>
					</th>
					<!-- <th>班次
						班组
					</th> -->
					<th>
						<!-- 工作时间 --><spring:message code="ess.infoApply.working_hours"/>
					</th>
					<th>
						<!-- 进门 --><spring:message code="ar.viewarcardrecord.title.jinmen"/>
					</th>
					<th>
						<!-- 出门 --><spring:message code="ar.viewarcardrecord.title.chumen"/>
					</th>
					<th>
						<!-- 开始时间--><spring:message code="ess.infoApply.title.startTime"/>
					</th>
					<th>
						<!-- 结束时间--><spring:message code="ess.infoApply.title.endTime"/>
					</th>
					<th>
						<!-- 加班时长--><spring:message code="ess.infoApply.overtime_hours"/>
					</th>
					<th>
						<!-- 原因 --><spring:message code="ess.infoApply.Reason"/>
					</th>
					<th>
						<!-- 审批状态--><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${adjustCoordList}" var="CoordAdjustApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					    <%-- <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordAdjustApply.EMPID}&LOCAL_NAME= ${CoordAdjustApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300">  --%>
					    ${CoordAdjustApply.LOCAL_NAME}
					    <!-- </a> -->
					    </td>
						<td style="text-align: center">
						<%-- <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordAdjustApply.EMPID}&LOCAL_NAME= ${CoordAdjustApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300"> --%>
						${CoordAdjustApply.EMPID}
						<!-- </a> -->
						</td>
						<td style="text-align: center">${CoordAdjustApply.DEPTNAME}</td>
						<td style="text-align: center">${CoordAdjustApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordAdjustApply.APPLY_OT_DATE}</td>
						<td style="text-align: center"><c:if test="${CoordAdjustApply.IWEEK eq 1}"><!-- 星期日--><spring:message code="ar.week.XINGQIRI.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 2}"><!-- 星期一--><spring:message code="ar.week.XINGQIYI.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 3}"><!-- 星期二--><spring:message code="ar.week.XINGQIER.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 4}"><!-- 星期三--><spring:message code="ar.week.XINGQISAN.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 5}"><!-- 星期四--><spring:message code="ar.week.XINGQISI.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 6}"><!-- 星期五--><spring:message code="ar.week.XINGQIWU.b"/></c:if>
							<c:if test="${CoordAdjustApply.IWEEK eq 7}"><!-- 星期六--><spring:message code="ar.week.XINGQILIU.b"/></c:if></td>
						<%-- <td style="text-align: center">${CoordAdjustApply.SHIFT_NAME}</td> --%>
						<td style="text-align: center">${CoordAdjustApply.SHIFT_START_TIME}-${CoordAdjustApply.SHIFT_END_TIME}</td>
						<td style="text-align: center">${CoordAdjustApply.INDOOR_TIME}</td>
						<td style="text-align: center">${CoordAdjustApply.OUTDOOR_TIME}</td>
						<td style="text-align: center">${CoordAdjustApply.OT_FROM_TIME}</td>
						<td style="text-align: center">${CoordAdjustApply.OT_TO_TIME}</td>
						<td style="text-align: center">${CoordAdjustApply.OT_APPLY_HOUR}<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
						<td style="text-align: center">${CoordAdjustApply.APPLY_OT_REMARK}</td>
						<td style="text-align: center">${CoordAdjustApply.AFFIRM_FLAG_NAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>