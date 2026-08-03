<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!--班组的月别列表查询-->
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewAttendanceExConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewAttendanceExConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	$(".list",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		//"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 300,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
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

function AttendanceExConfirmSingle(flag, index){
	$("input[name='attendanceEx']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#attendanceEx_" + index).attr("checked","checked");
	AttendanceExConfirmBatch(flag);
}

$('.list tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		if(!$("#attendanceEx_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#attendanceEx_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$(this).html(val);
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});

function AttendanceExConfirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='attendanceEx']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(13);
			jsonData += ' "SEQ": "' + obj.value + '" ,';
			jsonData += ' "FLAG": "' + flag + '" ,';
			jsonData += ' "HR_COMMENT":"'+ $("#HR_COMMENT_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	
	if (jsonData.length == 2) {
		alertMsg.info("<spring:message code='ar.viewPOtApplyInfoConfirmList.QINGXIANGOUXUANCAOZUOSHUJU.b' />");//请先勾选要操作的数据
		return;
	}
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation' />",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/arConfirm/attendanceExConfirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
</script>
<div class="pageHeader">
<form id="viewAttendanceExConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/arConfirm/viewAttendanceExConfirm?firstFlag=N" method="post">
<div class="searchBar">
<table class="searchContent">
	       <tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门： --> 
				</td>
				<td><ait:deptList name="seach_DEPTNO"
						cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArShiftRecordCheckList_seachDept_EX" /> <ait:deptTreeIcon
						name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArShiftRecordCheckList_seachDept_EX" selected="${DEPTNO}" />
				</td>
				<td><!--社号/姓名--><spring:message code="public.title.empIdAndName" /></td>
				<td>
					<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY_EX" value="${KEY}"/></div>
				</td>
				<td><!--期间 --><spring:message code="ess.infoApply.Period" /></td>
				<td>
					<input type="text" id="seach_START_DATE_EX" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${START_DATE}"/>~
					<input type="text" id="seach_END_DATE_EX" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${END_DATE}"/>
				</td>
			</tr>
			<tr>
			    <td>
					<!--职群--><spring:message code="ess.empInfo.zhiqun" />
				</td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_POST_FAMILY" parentNo="14015812" selected="${POST_FAMILY}" limit="ALL"/>
				</td>
				<td><!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/></td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE" selected="${EMP_TYPE_CODE}" parentNo="13864" limit="all"/>
				</td>
				<td>
					<!--确认状态--><spring:message code="ess.infoApply.confirm_status" />
				</td>
				<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>><!--全部--><spring:message code="empsubject.all" /></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>><!--未裁决--><spring:message code="liang.pa.insuranceApply.title.weicaijue" /></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>><!--通过--><spring:message code="ess.infoApply.adopt" /></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>><!--否决--><spring:message code="ess.infoApply.veto" /></option> 
					</select>
				</td>
			</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewAttendanceExConfirmList_Serch" href="#"><span><!--查询--><spring:message code="public.title.search" /></span></a></li>
		<li><a class="buttonActive" href="#" onclick="AttendanceExConfirmBatch(1)"><span><!--批量通过--><spring:message code="ess.title.passInBatch" /></span></a></li>
		<li><a class="buttonActive" href="#" onclick="AttendanceExConfirmBatch(2)"><span><!--批量否决--><spring:message code="ess.title.rejectInBatch" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
<%-- 				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewAttendanceExListCnt}</div>
 --%>				<table class="list" width="99%">
					<thead>
						<th>NO.</th>
					    <th>
					       <input type="checkbox" class="checkboxCtrl" group="attendanceEx" />
					    </th>
						<th>
							<!--社号--><spring:message code="hr.viewPersonalInfo.title.EMPID" />
						</th>
						<th>
							<!--申请者--><spring:message code="ess.viewApply.title.applyName" />
						</th>
						<th>
							<!--部门--><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" />
						</th>
						<%-- <th>
							<!--职群--><spring:message code="ess.empInfo.zhiqun" />
						</th> --%>
						<th>
							<!--职级--><spring:message code="ess.infoApply.Rank" />
						</th>
						<th>
							<!--异常类型--><spring:message code="ess.infoApply.yichangleixing" />
						</th>
						<th>
							<!--考勤日期--><spring:message code="ess.infoApply.attendance_date" />
						</th>
						<th  width="170px">
							<!--打卡时间--><spring:message code="ess.recordTest.title.RECORD_TEST_DATE" />
						</th>
						<th>
							<!--班次--><spring:message code="ess.infoApply.title.shift" />
						</th>
						<th width="170px">
							<!--时间段--><spring:message code="ar.viewardetail.title.dateduan" />
						</th>
						<th>
							<!--原因描述--><spring:message code="ar.viewAttendanceExConfirm.YUANYINMIAOSHU.b" />
						</th>
						<th  class="titleColor">
							<!--决裁情况--><spring:message code="ess.viewApply.title.affirmCondition" />
						</th>
						<th>
							<!--人事记录--><spring:message code="evs.affirm.personnel_record.e" />
						</th>
						<th>
							<!--人事确认--><spring:message code="ess.viewApply.title.humanAffirm" />
						</th>
					</thead>
					<tbody>
					<c:forEach items="${viewAttendanceExList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
							    <td class='td_center'>
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}">
								    	<input type="checkbox" id="attendanceEx_${i.index}" name="attendanceEx" value="${item.APPLY_NO }" />
								    </c:if>
							    </td>
							    <td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME }</td>
								<td class='td_center'>${item.DEPT_NAME }</td>
								<%-- <td class='td_center'>${item.POST_FAMILY_NAME }</td> --%>
								<td class='td_center'>${item.POST_GRADE_NAME }</td>
								<td class='td_center'>${item.ITEM_NAME }</td>
								<td class='td_center'>${item.AR_DATE_STR}</td>
								<td class='td_center'><!--进门卡:--><spring:message code="ess.infoApply.in_door_card" />${item.INDOOR_TIME }</br><!--出门卡:--><spring:message code="ess.infoApply.out_door_card" />${item.OUTDOOR_TIME }</td>
								<td class='td_center'>${item.SHIFT_NAME }</td>
								<td class='td_center'><!--进门卡:--><spring:message code="ess.infoApply.in_door_card" />${item.IN_TIME }</br><!--出门卡:--><spring:message code="ess.infoApply.out_door_card" />${item.OUT_TIME }</td>
								<td>${item.APPLY_REASON }</td>
								<td class='td_center'><a href="/ess/infoApply/viewAttendanceEx?seach_APPLY_NO=${item.APPLY_NO }&seach_APPLY_TYPE=${item.ITEM_NO}&APPLY_FLAG=1&seach_ACTIVITY=1" target="dialog" mask="true" width="800" height="600" style="color: #000000"><!--通过--><spring:message code="ess.infoApply.adopt" /></a></td>
								<td sysLog="text" sysIndex="${i.index}" id="HR_COMMENT_${i.index}">${item.HR_COMMENT}</td>
								<td class='td_center'>
									<c:if test="${item.CONFIRM_FLAG eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!--通过--><spring:message code="ess.infoApply.adopt" /></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}"><a href="#" onclick="AttendanceExConfirmSingle(1,${i.index})"><!--通过--><spring:message code="ess.infoApply.adopt" /></a>|<a href="#" onclick="AttendanceExConfirmSingle(2,${i.index})"><!--否决--><spring:message code="ess.infoApply.veto" /></a></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!--否决--><spring:message code="ess.infoApply.veto" /></c:if>
								</td>	
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
