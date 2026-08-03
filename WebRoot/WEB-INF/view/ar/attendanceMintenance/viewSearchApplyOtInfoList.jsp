<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script> -->
<script>
$(document).ready(function(){
      $("#viewSearchApplyOtInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewSearchApplyOtInfoList",navTab.getCurrentPanel()).submit();
	   });
      $('input[type=text]').on('keydown', function(e) {
  	    if (e.which == 13) {
  	    	$("#viewSearchApplyOtInfoList",navTab.getCurrentPanel()).submit();
  	    }
  	});
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": $(document.body).width() - 30,
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
<div class="panel"><h1><!--加班搜索--><spring:message code="ar.viewSearchApplyOtInfoList.JIABANSOUSUO.b" /></h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewSearchApplyOtInfoList?firstFlag=N" method="post"
		id="viewSearchApplyOtInfoList" name="viewSearchApplyOtInfoList">
		<div class="searchBar">
				<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<!--<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="seach_ApplyOt_DEPTNO" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="seach_ApplyOt_DEPTNO" selected="${DEPTNO}"/>
					-->
					    <ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPT_NAME" limit="ar" selectedNm="${DEPT_NAME}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
					<td><!-- 日期  --><spring:message code="pa.salary.title.date"/> </td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/>~
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"/>
					</td>
					<td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
					<td>
						<%-- <ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all" selected="${SHIFT_NO }" /> --%>
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${shiftList}" var="result">
								<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<!--<td> 加班类型  <spring:message code="ess.infoApply.overtime_type"/></td>
					<td>
					<ait:SelectSyCodeByCpnyID name="seach_OT_TYPE_CODE" parentNo="31" selected="${OT_TYPE_CODE}" limit="ALL"/>						
					</td>-->
				    <td><!--加班时长--><spring:message code="ess.infoApply.overtime_hours"/>(>=) </td>
					<td>
					     <input type="text" id="seach_OT_LENGTH" name="seach_OT_LENGTH" size="10" value="${OT_LENGTH }" />
					</td>
				   </tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	<li><a class="buttonActive" id="viewSearchApplyOtInfoList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
	<li>
		<a class="buttonActive" onclick="downloadExcel('viewSearchApplyOtInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=132&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewSearchApplyOtInfoList?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a>

		<!--<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<a class="buttonActive" onclick="downloadExcel('viewSearchApplyOtInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=307&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewSearchApplyOtInfoList?firstFlag=N')"><span> 导出到Excel <spring:message code="org.title.exportLOtImportExcel"/></span></a>
		</c:if>-->
	</li>
	 </ul>
</div>
 <input type="hidden" id="nullOTTSTOAffirmListCnt" value="${otCoordListCnt}">
	 <%--  <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${otCoordListCnt}</div>   --%>   
		<table class="orderList" width=100%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
						<!-- 社号 --><spring:message code="ess.infoApply.EMPID"/>
					</th>
					<th>
				    	<!-- 姓名 --><spring:message code="org.title.LOCAL_NAME"/>
				    </th>
					<th>
						<!-- 部门名 --><spring:message code="ess.infoApply.DEPT_NAME"/>
					</th>
					<th>
						<!-- 职级 --><spring:message code="sys.postManage.title.postGrade"/>
					</th>
					<th>
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th>
						<!-- 加班日期 --><spring:message code="ess.infoApply.title.overtimeTime"/>
					</th>
					<th>
						<!-- 星期 --><spring:message code="ess.infoApply.week"/>
					</th>
					<th>
						<!-- 考勤 --><spring:message code="ar.viewArNavigationPage.KAOQIN.b"/>
					</th>
<!-- 					<th>班次
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
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otCoordList}" var="CoordOtApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
<%-- 						<a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordOtApply.EMPID}&LOCAL_NAME= ${CoordOtApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300">
							</a>
 --%>						${CoordOtApply.EMPID}
						</td>
					    <td style="text-align: center">
<%-- 					    <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordOtApply.EMPID}&LOCAL_NAME= ${CoordOtApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300"> 
							</a>
 --%>					    ${CoordOtApply.LOCAL_NAME}
					    </td>
						<td style="text-align: center">${CoordOtApply.DEPT_NAME}</td>
						<td style="text-align: center">${CoordOtApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordOtApply.SHIFT_NAME}</td>
						<td style="text-align: center">${CoordOtApply.AR_DATE_STR}</td>
						<td style="text-align: center"><c:if test="${CoordOtApply.IWEEK eq 1}"><!-- 星期日--><spring:message code="ar.week.XINGQIRI.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 2}"><!-- 星期一--><spring:message code="ar.week.XINGQIYI.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 3}"><!-- 星期二--><spring:message code="ar.week.XINGQIER.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 4}"><!-- 星期三--><spring:message code="ar.week.XINGQISAN.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 5}"><!-- 星期四--><spring:message code="ar.week.XINGQISI.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 6}"><!-- 星期五--><spring:message code="ar.week.XINGQIWU.b"/></c:if>
							<c:if test="${CoordOtApply.IWEEK eq 7}"><!-- 星期六--><spring:message code="ar.week.XINGQILIU.b"/></c:if></td>
						<td style="text-align: center">${CoordOtApply.ITEM_NAME}</td>
						<%-- <td style="text-align: center">${CoordOtApply.SHIFT_NAME}</td> --%>
						<td style="text-align: center">${CoordOtApply.SHIFT_START_TIME}-${CoordOtApply.SHIFT_END_TIME}</td>
						<td style="text-align: center">${CoordOtApply.INDOOR_TIME}</td>
						<td style="text-align: center">${CoordOtApply.OUTDOOR_TIME}</td>
						<td style="text-align: center">${CoordOtApply.FROM_TIME}</td>
						<td style="text-align: center">${CoordOtApply.TO_TIME}</td>
						<td style="text-align: center">${CoordOtApply.QUANTITY}<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>