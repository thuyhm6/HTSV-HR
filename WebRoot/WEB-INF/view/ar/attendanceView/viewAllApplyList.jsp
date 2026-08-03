<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	   $("#viewAttendanceManagentForSerchInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewAttendanceManagentForSerchInfoList",navTab.getCurrentPanel()).submit();
	   });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
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
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": false,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "columnDefs": [//自定义排序类型
		            { "orderable": false, "targets": [2,3,4,5,8] }
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
<script text="java/script">
function exceptMonthWorkExcle(){
	var obj = document.getElementById('dataForm');
	obj.action = "/ar/attendanceView/viewDetailWorkControlExcel";
	obj.submit();
	obj.action = "/ar/attendanceView/viewDetailWorkControl";
}


function ajaxItemForApplyList() {
	var obj=document.getElementById("seach_APPLY_TYPE");
	var APPLY_TYPE = obj.value;

    if(APPLY_TYPE == '218197'){
    	var html = '<option value=""><spring:message code="sys.affirm.title.choose"/></option>';//请选择
    	    html += '<option value="141443"><spring:message code="ar.monthwork.title.kuanggong" /></option>';//矿工
    	    html += '<option value="141442"><spring:message code="ar.monthwork.title.EarlyLeave" /></option>';//早退
    	    html += '<option value="141441"><spring:message code="ar.monthwork.title.Lateness" /></option>';//迟到
    	    html += '<option value="14015448"><spring:message code="ess.title.LOUKA" /></option>';//漏卡
    	    $('#seach_TYPE_CODE').html(html);
    }else{
    	$.ajaxSettings.global = false;
		ajaxEmpTypeForGroupToList2 = $.ajax( {
			type : "POST",
			url : "/ar/attendanceView/getItemForApplyList",
			data : { APPLY_TYPE:APPLY_TYPE },
			dataType : "json",
			success : function(data) {
				$('#seach_TYPE_CODE').html("");
				var html = '<option value=""><spring:message code="sys.affirm.title.choose"/></option>';//请选择
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
							html += '<option value="' + comment['ITEM_NO'] + '">' + comment['SHORT_NAME'] + '</option>';
						});
				}
				$('#seach_TYPE_CODE').html(html);
			}
		});
		$.ajaxSettings.global = true;
    }
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ar/attendanceView/viewAllApplyList" method="post" id="dataForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<%--开始日期--%><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
					</td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/>
					</td>
					<td>
						<%--结束日期--%><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
					</td>
					<td>
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"/>
					</td>
					<td>
						<%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>
					</td>
					<td>	
					<ait:deptList name="seach_DEPT_NO" limit="all"  id="viewMonWo_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPT_NO" limit="all" id="viewMonWo_seachDept" selected="${DEPT_NO}"/>
				  </td>
				</tr>
				<tr>
				  <td><!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/> </td>
			      <td>
	 					<ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE" selected="${EMP_TYPE_CODE}" parentNo="13864" limit="all"/>
				  </td>	
				  <td><!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
				  <td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"  limit="all"/>
				  </td>
				  <td>
						<!-- 审批状态 --><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="14014304" selected="${AFFIRM_FLAG}" limit="ALL"/>
					</td>
				</tr>
				<tr>
					<td>
						<!-- 申请类型 --><spring:message code="sys.affirm.title.applyType" />
					</td>
					<td>
					
						<select name="seach_APPLY_TYPE" id="seach_APPLY_TYPE" onChange="ajaxItemForApplyList();" >
								<option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option>
								<option value="21"<c:if test="${APPLY_TYPE eq  '21'}">selected</c:if>><!--休假申请--><spring:message code="ess.infoApply.title.leaveApply" /></option>
								<option value="31"<c:if test="${APPLY_TYPE eq '31'}">selected</c:if>><!--加班申请--><spring:message code="ess.infoApply.title.overtimeApply" /></option>
								<option value="218197"<c:if test="${APPLY_TYPE eq '218197'}">selected</c:if>><!--异常类型--><spring:message code="ess.title.attendance_anomaly" /></option>
						
						</select>
						
					</td>
					<td>
						<!-- 申请项目 --><spring:message code="liang.pa.insuranceApply.title.shenqingxiangmu" />
					</td>
					<td>
						<select name="seach_TYPE_CODE" id="seach_TYPE_CODE" >
						<option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option>
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.ITEM_NO}"<c:if test="${item.ITEM_NO eq TYPE_CODE}">selected</c:if>>${item.SHORT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td><!-- 人事确认状态 --><spring:message code="ess.title.RENSHIQUERENZHUANGTAI" /></td>
					<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>><!-- 全部 --><spring:message code="org.title.ALL" /></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>><!-- 未确认--><spring:message code="ess.title.WEIQUEREN" /></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>><!-- 通过--><spring:message code="ess.infoApply.adopt" /></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>><!-- 否决--><spring:message code="ess.infoApply.veto" /></option> 
					</select>
					</td>
					
				</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--检索--><spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="downloadExcel('dataForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=346&CPNY=${LoginUser.cpnyId}','/ar/attendanceView/viewAllApplyList')">
									<!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/>
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

<!--   <div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="edit" onclick="exceptMonthWorkExcle()" title="<spring:message code='rp.report.title.exportYN'/>">
				<span><%--Excel导出--%>	<spring:message code="ar.addempshift.title.excelexport"/></span>
			</a>
		</li>
	</ul>
</div> -->

<table class="orderList" width="100%">
	<thead>
		<tr>
		    <th width="2%" >NO</th>
			<th><!--工号--><spring:message code="ess.infoApply.EMPID" /></th>
			<th><%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
			<th><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></th>
			<th><!--申请类型--><spring:message code="sys.affirm.title.applyType" /></th>
			<th><!--申请项目--><spring:message code="liang.pa.insuranceApply.title.shenqingxiangmu" /></th>
			<th><!--申请日期--><spring:message code="ess.viewApply.title.applyDate" /></th>
			<th><!--开始时间--><spring:message code="ess.infoApply.title.startTime" /></th>
			<th><!--结束时间--><spring:message code="ess.infoApply.title.endTime" /></th>
			<th><!--申请时长--><spring:message code="ar.viewArAdjustRest.title.APPLYLENGTH" /></th>
			<th width="20%"><!--备注--><spring:message code="ess.affirmApply.title.remark" /></th>
			<th><!--审批状态--><spring:message code="ess.infoApply.approval_status" /></th>			
		</tr>
		
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="item" varStatus="i">
			<tr>
			    <td>${i.index + 1}</td>
				<td>${item.EMPID}</td>
				<td>${item.LOCAL_NAME}</td>
				<td>${item.DEPT_NAME}</td>
				<td>
				   <c:if test="${item.APPLY_TYPE eq '21'}"><!--休假申请--><spring:message code="ess.infoApply.title.leaveApply" /></c:if>
				   <c:if test="${item.APPLY_TYPE eq '31'}"><!--加班申请--><spring:message code="ess.infoApply.title.overtimeApply" /></c:if>
				   <c:if test="${item.APPLY_TYPE eq '218197'}"><!--异常类型--><spring:message code="ess.infoApply.yichangleixing" /></c:if>
				</td>
				<td>${item.TYPE_CODE_NAME}</td>
				<td>${item.APPLY_TIME}</td>
				<td>${item.FROM_TIME}</td>
				<td>${item.TO_TIME}</td>
				<td>
					<c:if test="${item.APPLY_TYPE eq '21'}">
						<c:if test="${item.APPLY_LENGTH ge item.DAY_HOURS }">
							<fmt:formatNumber type="number"  value="${item.APPLY_LENGTH/item.DAY_HOURS + (item.APPLY_LENGTH%item.DAY_HOURS == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/>&nbsp;<!-- 天--><spring:message code="ar.viewsummaryparameteritem.title.day"/>
						</c:if>&nbsp;${item.APPLY_LENGTH % item.DAY_HOURS}&nbsp;<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/>
				    </c:if>
				    <c:if test="${item.APPLY_TYPE eq '31'}">
				        ${item.APPLY_LENGTH}&nbsp;<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/>
				    </c:if>
				    <c:if test="${item.APPLY_TYPE eq '218197'}">
				        
				    </c:if>
				</td>
				
				<td>${item.REMARK}</td>
				<td>${item.AFFIRM_FLAG_NAME} <c:if test="${item.APPLY_TYPE ne '31'}">
													<c:if test="${leaveApply.CONFIRM_FLAG ne '1' and leaveApply.CONFIRM_FLAG ne '2' }">
														<!--人事未确认--><spring:message code="ess.title.WEIQUEREN" />
												    </c:if>
												    <c:if test="${leaveApply.CONFIRM_FLAG eq '1' }">
												    	<!--人事通过--><spring:message code="ess.title.RENSHITONGGUO" />
												    </c:if><c:if test="${leaveApply.CONFIRM_FLAG eq '2' }">
												    	<!--人事否决--><spring:message code="ess.title.RENSHITONGGUO" />
												    </c:if>
											 </c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
	<c:set value="/ar/attendanceView/viewAllApplyList" var="pageUrl" />
	<%--<%@ include file="/WEB-INF/view/inc/initPagination.jsp"--%>
</div>
