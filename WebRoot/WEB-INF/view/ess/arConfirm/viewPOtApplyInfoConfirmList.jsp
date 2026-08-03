<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!--班组的月别列表查询-->
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewPOtApplyInfoConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewPOtApplyInfoConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	$(".list",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
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
	     "scrollY": $(document.body).height() - 280,
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

function pOtApplyInfoConfirmSingle(flag, index){
	$("input[name='pOtApplyInfo']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#pOtApplyInfo_" + index).attr("checked","checked");
	pOtApplyInfoConfirmBatch(flag);
}

function pOtApplyInfoConfirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='pOtApplyInfo']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "APPLY_NO": "' + obj.value + '" ,';
			jsonData += ' "FLAG": "' + flag + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	
	if (jsonData.length == 2) {
		alertMsg.info("<spring:message code='ar.viewPOtApplyInfoConfirmList.QINGXIANGOUXUANCAOZUOSHUJU.b'/>");//请先勾选要操作的数据
		return;
	}
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/arConfirm/pOtApplyInfoConfirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
function changeURL_ar0902(applyNo,otTypeCode){
	var href = "/ess/infoApply/viewApprovaledOt?seach_APPLY_NO=" + applyNo + "&APPLY_TYPE="+otTypeCode;
	$.pdialog.open(href,"ar0902", "<spring:message code='ess.infoApply.adopt'/>", {width:1000,height:600,mask:true});//通过
}
</script>
<div class="pageHeader">
<form id="viewPOtApplyInfoConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/arConfirm/viewPOtApplyInfoConfirmList?firstFlag=N" method="post">
<div class="searchBar">
<table class="searchContent">
	       <tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门： --> 
				</td>
				<td><ait:deptList name="seach_DEPTNO"
						cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArShiftRecordCheckList_seachDept_OT" /> <ait:deptTreeIcon
						name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArShiftRecordCheckList_seachDept_OT" selected="${DEPTNO}" />
				</td>
				<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
				<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY_OT" value="${KEY}"/></div>
				</td>
				<td><!-- 期间 --><spring:message code="ess.infoApply.Period"/></td>
				<td>
					<input type="text" id="seach_START_DATE_OT" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
					<input type="text" id="seach_END_DATE_OT" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
				</td>
				<td><!-- 员工状态 --><spring:message code="ess.trans.title.employeeStatus"/></td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="1372" limit="all"/>
				</td>
			</tr>
			<tr>
			    <td>
					<!-- 加班类型 --><spring:message code="ess.viewApply.title.overtimeApplyType"/>
				</td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE_NEW" parentNo="31" selected="${APPLY_TYPE_CODE_NEW}" limit="ALL"/>
				</td>
				<td>
					<!-- 确认状态 --><spring:message code="ess.infoApply.confirm_status"/>
				</td>
				<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>><!-- 全部 --><spring:message code="ess.infoApply.whole"/></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>><!-- 未确认  --><spring:message code="ess.title.WEIQUEREN"/></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>><!-- 否决  --><spring:message code="ess.infoApply.veto"/></option> 
					</select>
				</td>
			</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewPOtApplyInfoConfirmList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="pOtApplyInfoConfirmBatch(1)"><span><!-- 批量通过 --><spring:message code="ess.trans.title.passInBatch"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="pOtApplyInfoConfirmBatch(2)"><span><!-- 批量否决 --><spring:message code="ess.title.rejectInBatch"/></span></a></li>
</div>
<div class="pageContent">
<%-- 				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewPOtApplyInfoConfirmListCnt}</div>
 --%>				<table class="list" width="99%" >
					<thead>
						<th>NO.</th>
					    <th>
					       <input type="checkbox" class="checkboxCtrl" group="pOtApplyInfo" />
					    </th>
						<th>
							<!-- 社号 --><spring:message code="public.title.empId"/>
						</th>
						<th>
							<!-- 申请者 --><spring:message code="ess.viewApply.title.applyName"/>
						</th>
						<th width="140px">
							<!-- 部门 --><spring:message code="ess.infoApply.DEPT"/>
						</th>
						<th>
							<!-- 职群  --><spring:message code="ess.empInfo.zhiqun"/>
						</th>
						<th>
							<!-- 职级 --><spring:message code="ess.infoApply.Rank"/>
						</th>
						<th>
							<!-- 申请日期 --><spring:message code="ess.viewApply.title.applyDate"/>
						</th>
						<th>
							<!-- 加班类型 --><spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</th>
						<th>
							<!-- 加班时段 --><spring:message code="ess.viewApply.title.overtimeShift"/>
						</th>
						<th>
							<!-- 加班时长 --><spring:message code="ess.infoApply.overtime_hours"/>
						</th>
						<th>
							<!-- 加班合计 --><spring:message code="ess.title.JIABANHEJI"/>
						</th>
						<th width="250px">
							<!-- 加班原因 --><spring:message code="ess.viewApply.title.overtimeReasono"/>
						</th>
						<th>
							<!-- 决裁情况 --><spring:message code="ess.viewApply.title.affirmCondition"/>
						</th>
						<th>
							<!-- 人事确认 --><spring:message code="ess.viewApply.title.humanAffirm"/>
						</th>
					</thead>
					<tbody>
					<c:forEach items="${viewPOtApplyInfoConfirmList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
							    <td class='td_center'>
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							         <c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}">
								    <input type="checkbox" id="pOtApplyInfo_${i.index}" name="pOtApplyInfo" value="${item.APPLY_NO }" />
								     </c:if>
							    </td>
							    <td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME }</td>
								<td class='td_center'>${item.DEPT_NAME }</td>
								<td class='td_center'>${item.POST_FAMILY_NAME }</td>
								<td class='td_center'>${item.POST_GRADE_NAME }</td>
								<td class='td_center'>${item.APPLY_TIME }</td>
								<td class='td_center'>${item.OT_TYPE_CODE_NAME }</td>
								<td class='td_center'>${item.APPLY_OT_DATE }   &nbsp;&nbsp;&nbsp;${item.OT_FROM_TIME} ~  ${item.OT_TO_TIME}</td>
								<td class='td_center'>${item.OT_APPLY_HOUR }<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td class='td_center'>${item.OT_TOTAIL }<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td>${item.APPLY_OT_REMARK }</td>
								<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ar0902(${item.APPLY_NO },${item.OT_TYPE_CODE});'>
									<span style="color: blue"><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></span>
								</td>
								<td class='td_center'>
									<c:if test="${item.CONFIRM_FLAG eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 通过  --><spring:message code="ess.infoApply.adopt"/></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}"><a href="#" onclick="pOtApplyInfoConfirmSingle(1,${i.index})"><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></a>|<a href="#" onclick="pOtApplyInfoConfirmSingle(2,${i.index})"><!-- 否决  --><spring:message code="ess.infoApply.veto"/></a></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp;<!-- 否决  --><spring:message code="ess.infoApply.veto"/></c:if>
								</td>	
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
