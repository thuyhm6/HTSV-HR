<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!--班组的月别列表查询-->
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewSickLeaveConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewSickLeaveConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	 $('#viewLeaveConfirmListTable',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_ar0903();
		} );
	$(".list",navTab.getCurrentPanel()).dataTable({
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
	initEditFun_ar0903();
});

function sickLeaveProofConfirmSingle(flag, index){
	$("input[name='SickLeave']",navTab.getCurrentPanel()).each(function(i, obj){
		$(obj).removeAttr("checked");
	});
	$("#SickLeave_" + index).attr("checked","checked");
	leaveConfirmBatch(flag);
}

function leaveConfirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='SickLeave']",navTab.getCurrentPanel()).each(function(i, obj){
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
		//请先勾选要操作的数据
		alertMsg.info("<spring:message code='ar.viewPOtApplyInfoConfirmList.QINGXIANGOUXUANCAOZUOSHUJU.b' />");
		return;
	}
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation' />",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/arConfirm/sickLeaveProofConfirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
function initEditFun_ar0903(){
	var ids= document.getElementsByName("SickLeave");
	for(var i=0;i<ids.length;i++){
		var index = ids[i].id.substring(6);
		var applyLength = $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val();
		var DAY_HOURS = $("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val();
		var lengthText = '';
		var length = Math.floor(applyLength/DAY_HOURS) ;
		if( length > 0 ){
			lengthText += length + "<spring:message code='ar.viewsummaryparameteritem.title.day'/>";//天
		}
		var length = applyLength % DAY_HOURS ;
		if(length > 0 ){
			lengthText += length + "<spring:message code='ar.viewsummaryparameteritem.title.hour'/>";//小时
		}
		if(lengthText == ""){
			$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html("0<spring:message code='ar.viewsummaryparameteritem.title.hour'/>");//小时
		}else{
			$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html(lengthText);
		}
	}
}
function changeURL_ar0903(applyNo,applyType){
	var href = "/ess/infoApply/viewApprovaledLeave?seach_APPLY_NO=" + applyNo+"&seach_APPLY_TYPE="+applyType;
	$.pdialog.open(href,"ar0903", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b'/>", {width:1000,height:600,mask:true});//明细查看
}
</script>
<div class="pageHeader">
<form id="viewSickLeaveConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/arConfirm/viewSickLeaveProofConfirmList" method="post">
<div class="searchBar">
<table class="searchContent">
	       <tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门： --> 
				</td>
				<td>
					<ait:deptList name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar" id="viewArShiftRecordCheckList_seachDept_ATT" /> 
					<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar" id="viewArShiftRecordCheckList_seachDept_ATT" selected="${DEPTNO}" />
				</td>
				<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
				<td>
					<input type="text" name="seach_KEY" id="seach_KEY_ATT" value="${KEY}"/>
				</td>
				<td><!-- 期间 --><spring:message code="ess.infoApply.Period"/></td>
				<td>
					<input type="text" id="seach_START_DATE_ATT" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${START_DATE}"/>~
					<input type="text" id="seach_END_DATE_ATT" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${END_DATE}"/>
				</td>
			</tr>
			<tr>
			    <td><!-- 员工类型 --><spring:message code="ess.infoApply.employee_type"/></td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE" selected="${EMP_TYPE_CODE}" parentNo="13864" limit="all"/>
				</td>
				<td><!-- 提交状态--><spring:message code="ess.empInfo.submitted_status"/></td>
				<td>
					<select name="seach_PROOF_YN">
						<option value=""<c:if test="${PROOF_YN == null}">selected</c:if>> <!-- 全部 --><spring:message code="ess.infoApply.whole"/> </option>
						<option value="0"<c:if test="${PROOF_YN == '0'}">selected</c:if>> <!-- 未提交 --><spring:message code="ess.affirmApply.title.remark.weitijiao"/> </option>
						<option value="1"<c:if test="${PROOF_YN == '1'}">selected</c:if>> <!-- 已提交 --><spring:message code="ess.affirmApply.title.remark.yitijiao"/> </option>
					</select>
				</td>
			    <!--<td>
					考勤类型 <spring:message code="ess.infoApply.attendance_type"/>
				</td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="21" selected="${APPLY_TYPE_CODE }" limit="all"/>
				</td>
				<td>
					 确认状态 <spring:message code="ess.infoApply.confirm_status"/>
				</td>
				<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>> 全部  <spring:message code="hrm.empinfo.ALL"/></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>> 未确认  <spring:message code="ess.title.WEIQUEREN"/></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>> 通过  <spring:message code="ess.infoApply.adopt"/></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>> 否决  <spring:message code="ess.infoApply.veto"/></option> 
					</select>
				</td>-->
			</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewSickLeaveConfirmList_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
		<li><a class="buttonActive" href="#" onclick="leaveConfirmBatch(1)"><span><!-- 确认提交 --><spring:message code="ar.viewSickLeaveProof.QUERENTIJIAO.b"/></span></a></li>
    </ul>
</div>
<div class="pageContent">
				<input type="hidden" id="viewLeaveConfirmListCnt" value="${viewSickLeaveProofConfirmListCnt}">
				<table class="list" width="140%" id="viewLeaveConfirmListTable">
					<thead>
						<th>NO.</th>
					    <th>
					       <input type="checkbox" class="checkboxCtrl" group="SickLeave" />
					    </th>
						<th>
							<!--社号--><spring:message code="hr.viewPersonalInfo.title.EMPID" />
						</th>
						<th>
							<!--申请者--><spring:message code="ess.viewApply.title.applyName" />
						</th>
						<th>
						    <!--部门--><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
						</th>
						<th>
							<!--职群--><spring:message code="hrm.empinfo.POST_FAMILY" />
						</th>
						<th>
							<!--职级--><spring:message code="sys.postManage.title.postGrade" />
						</th>
						<th>
							<!--申请日期--><spring:message code="ess.viewApply.title.applyDate" />
						</th>
						<th>
							<!--距休假结束时间--><spring:message code="ar.viewLeaveConfirmList.JUXIUJIAJIESHUSHIJIAN.b" />
						</th>
						<th>
							<!--休假类型--><spring:message code="ess.viewApply.title.leaveApplyType" />
						</th>
						<th>
							<!--申请时段--><spring:message code="ar.viewLeaveConfirmList.SHENQINGSHIDUAN.b" />
						</th>
						<th>
							<!--休假时长--><spring:message code="ar.viewLeaveConfirmList.XIUJIASHICHANG.b" />
						</th>
						<th>
							<!--申请原因(休假原因)--><spring:message code="ar.viewLeaveConfirmList.SHENQINGXIUJIAYUANYIN.b" />
						</th>
						<th>
							<!--代申请人--><spring:message code="ar.viewLeaveConfirmList.DAISHENQINGREN.b" />
						</th>
						<!--<th>
							决裁情况<spring:message code="ess.viewApply.title.affirmCondition" />
						</th>
						<th>
							人事确认<spring:message code="ess.viewApply.title.humanAffirm" />
						</th>-->
						<th>
							<!-- 提交状态--><spring:message code="ess.empInfo.submitted_status"/>
						</th>
					</thead>
					<tbody>
					<c:forEach items="${viewSickLeaveProofConfirmList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
							    <td class='td_center'>
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <c:if test="${item.PROOF_YN eq 0 or item.PROOF_YN eq null}">
								    <input type="checkbox" id="SickLeave_${i.index}" name="SickLeave" value="${item.APPLY_NO }" />
								    </c:if>
								     <c:if test="${item.PROOF_YN eq 1}">
								    <input type="hidden" id="SickLeave_${i.index}" name="SickLeave" value="${item.APPLY_NO }" />
								    </c:if>
							    </td>
							    <td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME }</td>
								<td class='td_center'>${item.DEPT_NAME }</td>
								<td class='td_center'>${item.POST_FAMILY_NAME }</td>
								<td class='td_center'>${item.POST_GRADE_NAME }</td>
								<td class='td_center'>${item.APPLY_TIME }</td>
								<td class='td_center'><!-- 距离休假结束时间 -->
									<c:if test="${item.OVER_DAY <= 0 and item.OVER_HOUR <= 0 }">0<!-- 天--><spring:message code="ar.viewsummaryparameteritem.title.day"/></c:if>
									<c:if test="${item.OVER_DAY gt 0 or item.OVER_HOUR gt 0 }">${item.OVER_DAY }<!-- 天--><spring:message code="ar.viewsummaryparameteritem.title.day"/>${item.OVER_HOUR}<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></c:if>
								</td>
								<td class='td_center'>${item.LEAVE_TYPE_CODE_NAME }</td>
								<td class='td_center'>${item.LEAVE_FROM_TIME } ~ ${item.LEAVE_TO_TIME }</td>
								<td class='td_center' id ="APPLY_LENGTH_TEXT_${i.index}">
								    <c:if test="${item.APPLY_LENGTH ge item.DAY_HOURS }"><fmt:formatNumber type="number"  value="${item.APPLY_LENGTH/item.DAY_HOURS + (item.APPLY_LENGTH%item.DAY_HOURS == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/><spring:message code="ar.viewsummaryparameteritem.title.day" /><!--天--></c:if>${item.APPLY_LENGTH%item.DAY_HOURS}<spring:message code="ar.viewsummaryparameteritem.title.hour" /><!--小时-->
								</td>
								<input type="hidden" id="DAY_HOURS_${i.index}" value="${item.DAY_HOURS }">
								<input type="hidden" id="APPLY_LENGTH_${i.index}" value="${item.APPLY_LENGTH }">
								<td>${item.LEAVE_REASON }</td>
								<td class='td_center'><c:if test="${item.PERSON_ID ne item.CREATED_BY}">${item.CREATED_NAME}</c:if></td>
							    <!--<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_ar0903(${item.APPLY_NO },${item.LEAVE_TYPE_CODE });'>
										<span style="color: blue"> 通过  <spring:message code="ess.infoApply.adopt"/></span>
								</td>						
								<td class='td_center'>
									<c:if test="${item.CONFIRM_FLAG eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp; 通过  <spring:message code="ess.infoApply.adopt"/></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 0 or item.CONFIRM_FLAG eq null}"><a href="#" onclick="leaveConfirmSingle(1,${i.index})"> 通过  <spring:message code="ess.infoApply.adopt"/></a>|<a href="#" onclick="leaveConfirmSingle(2,${i.index})"> 否决  <spring:message code="ess.infoApply.veto"/></a></c:if>
									<c:if test="${item.CONFIRM_FLAG eq 2}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp; 否决  <spring:message code="ess.infoApply.veto"/></c:if>
								</td>-->
								<td class='td_center'>
									<c:if test="${item.PROOF_YN eq 1}">${item.CONFIRM_BY }&nbsp;&nbsp;&nbsp;&nbsp; <!-- 已提交 --><spring:message code="ess.affirmApply.title.remark.yitijiao"/> </c:if>
									<c:if test="${item.PROOF_YN eq 0 or item.CONFIRM_FLAG eq null}"><a href="#" onclick="sickLeaveProofConfirmSingle(1,${i.index})"> <!-- 确认提交 --><spring:message code="ar.viewSickLeaveProof.QUERENTIJIAO.b"/>  </a></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
