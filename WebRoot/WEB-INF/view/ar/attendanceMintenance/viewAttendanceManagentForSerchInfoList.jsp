<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!-- <script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script> -->
<script>
$(document).ready(function(){
	   $("#viewAttendanceManagentForSerchInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewAttendanceManagentForSerchInfoList",navTab.getCurrentPanel()).submit();
	   });
	   $('input[type=text]').on('keydown', function(e) {
		    if (e.which == 13) {
		    	$("#viewAttendanceManagentForSerchInfoList",navTab.getCurrentPanel()).submit();
		    }
		});
	$("#viewAttendanceManagent",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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
	     "scrollY": $(document.body).height() - 330,
	     "scrollX": false,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "columnDefs": [//自定义排序类型
		            { "orderable": false, "targets": "_all" }
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
<div class="panel"><h1><!--考勤查询--><spring:message code="ar.viewAttendanceManagentForSerchInfo.KAOQINCHAXUN.b" /></h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewAttendanceManagentForSerchInfoList?firstFlag=N"  method="post"
		id="viewAttendanceManagentForSerchInfoList" name="viewAttendanceManagentForSerchInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<!--<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="seach_DEPTNO" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="seach_DEPTNO" selected="${DEPTNO}"/>
					    -->
					    <ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPT_NAME" limit="ar" selectedNm="${DEPT_NAME}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
				
					<td><!-- 日期  --><spring:message code="pa.salary.title.date"/> </td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/>~
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"/>
					</td>
				</tr>
				<tr>
				    <td><!-- 职群  --><spring:message code="ess.empInfo.zhiqun"/></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_POST_FAMILY" parentNo="14015812" selected="${POST_FAMILY}" limit="ALL"/>
					</td>
					<td><!-- 班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/></td>
					<td>
						<%-- <ait:SelectSyCodeByCpnyID name="seach_GROUP_SHIFT" parentNo="400223" selected="${GROUP_SHIFT}" limit="ALL"/> --%>
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${shiftList}" var="result">
								<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td><!-- 考勤类型  --><spring:message code="ess.infoApply.attendance_type"/></td>
					<td>
						<!--<ait:SelectSyCodeByCpnyID name="seach_LEAVE_TYPE_CODE" parentNo="21" selected="${LEAVE_TYPE_CODE }" limit="all"/>-->
					    <!--<select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" > 请选择 <spring:message code="pa.salary.canShu.qingXuanZe" /></option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>-->
						<ait:selectCodeMultiArDetail id="seach_AR_DETAIL_ITEM" name="seach_AR_DETAIL_ITEM_NAME"  selected="${AR_DETAIL_ITEM}" selectedNm="${AR_DETAIL_ITEM_NAME}"/>
						<img alt="clear" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
						onclick="$('input[name=seach_AR_DETAIL_ITEM_NAME]',navTab.getCurrentPanel()).attr('value','');$('input[name=seach_AR_DETAIL_ITEM]',navTab.getCurrentPanel()).attr('value','');">
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewAttendanceManagentForSerchInfoList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewAttendanceManagentForSerchInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=131&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewAttendanceManagentForSerchInfoList?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	 </ul>
</div>
 		<input type="hidden" id="leaveCoordListCnt" value="${leaveCoordListCnt }">
	 <%--  <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${leaveCoordListCnt}</div> --%>
		<table class="orderList" id="viewAttendanceManagent" width="99%">   
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
						<!-- 班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th>
						<!-- 考勤日期 --><spring:message code="ess.infoApply.attendance_date"/>
					</th>
					<th>
						<!-- 星期 --><spring:message code="ess.infoApply.week"/>
					</th>
					<th>
						<!-- 考勤状态--><spring:message code="ess.infoApply.localyn"/>
					</th>
					<th>
						<!-- 开始时间--><spring:message code="ess.infoApply.title.startTime"/>
					</th>
					<th>
						<!-- 结束时间--><spring:message code="ess.infoApply.title.endTime"/>
					</th>
					<th>
						<!-- 时长--><spring:message code="ess.infoApply.duration"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveCoordList}" var="CoordleaveApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
						<td style="text-align: center">${CoordleaveApply.EMPID}</td>
					    <td style="text-align: center">${CoordleaveApply.LOCAL_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.DEPT_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.SHIFT_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.AR_DATE_STR}</td>
						<td style="text-align: center">
							<c:if test="${CoordleaveApply.IWEEK eq 1}"><!-- 星期日--><spring:message code="ar.week.XINGQIRI.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 2}"><!-- 星期一--><spring:message code="ar.week.XINGQIYI.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 3}"><!-- 星期二--><spring:message code="ar.week.XINGQIER.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 4}"><!-- 星期三--><spring:message code="ar.week.XINGQISAN.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 5}"><!-- 星期四--><spring:message code="ar.week.XINGQISI.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 6}"><!-- 星期五--><spring:message code="ar.week.XINGQIWU.b"/></c:if>
							<c:if test="${CoordleaveApply.IWEEK eq 7}"><!-- 星期六--><spring:message code="ar.week.XINGQILIU.b"/></c:if>
						</td>
						<td style="text-align: center">${CoordleaveApply.ITEM_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.FROM_TIME}</td>
						<td style="text-align: center">${CoordleaveApply.TO_TIME}</td>
						<td style="text-align: center" id="LENGTH_TEXT_${i.index}">
						<!--<c:if test = "${CoordleaveApply.DAY_HOURS ne 0 }">
						   <c:if test="${CoordleaveApply.APPLY_LENGTH ge CoordleaveApply.DAY_HOURS }">
						         <fmt:formatNumber type="number"  value="${CoordleaveApply.APPLY_LENGTH/CoordleaveApply.DAY_HOURS + (CoordleaveApply.APPLY_LENGTH%CoordleaveApply.DAY_HOURS == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/> 天<spring:message code="ar.viewsummaryparameteritem.title.day"/>
						   </c:if>
						   ${CoordleaveApply.APPLY_LENGTH%CoordleaveApply.DAY_HOURS} 小时<spring:message code="ar.viewsummaryparameteritem.title.hour"/>
						</c:if>
						<c:if test = "${CoordleaveApply.DAY_HOURS eq 0 }">
						   <c:if test="${CoordleaveApply.APPLY_LENGTH ge CoordleaveApply.DAY_HOURS }">
						         <fmt:formatNumber type="number"  value="${CoordleaveApply.APPLY_LENGTH/8 + (CoordleaveApply.APPLY_LENGTH%8 == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/> 天<spring:message code="ar.viewsummaryparameteritem.title.day"/>
						   </c:if>
						   ${CoordleaveApply.APPLY_LENGTH%8} 小时<spring:message code="ar.viewsummaryparameteritem.title.hour"/>
						</c:if>-->
						<c:if test="${CoordleaveApply.UNIT eq 'DAY'}">
							${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.day" />
						</c:if>
						<c:if test="${CoordleaveApply.UNIT eq 'HOUR'}">
							${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.hour" />
						</c:if>
						<c:if test="${CoordleaveApply.UNIT eq 'MINUTE'}">
							${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewsummaryparameteritem.title.minite" />
						</c:if>
						<c:if test="${CoordleaveApply.UNIT eq 'TIME'}">
							${CoordleaveApply.QUANTITY}&nbsp;<spring:message code="ar.viewitemparameter.title.timeofunit" />
						</c:if>
						</td>
						<input type="hidden" id="APPLY_LENGTH_${i.index}" value="${CoordleaveApply.APPLY_LENGTH}">
						<input type="hidden" id="DAY_HOURS_${i.index}" value="${CoordleaveApply.DAY_HOURS}">
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>