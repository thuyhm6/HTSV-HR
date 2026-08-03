<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewApprovalEmail_seach",navTab.getCurrentPanel()).click(function(){
		$("#viewApprovalEmailForm",navTab.getCurrentPanel()).submit();
	});
});

function executeAffirmBatch(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='viewApprovalEmail_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(9);
			var person_id = $("#PERSON_ID_"+index,navTab.getCurrentPanel()).val();
			jsonData += obj.value;
			jsonData += ' "AFFIRM_FLAG": "' + flag + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
			jsonData += ' "PERSON_ID": "' + person_id + '" ';
			jsonData += '}';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请先选择要操作的数据
		alertMsg.info("<spring:message code='ess.infoApply.select_data_want_operate'/>");
		return;
	}
    //确定要执行此操作吗？
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/infoApply/executeAffirmBatch',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
}
function changeColor_ess0601(obj){
	$(obj).css("color","black").css("font-weight","normal");
}
</script>

<div class="pageHeader">
<form id="viewApprovalEmailForm" onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewApprovalEmail" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!--标题 --><spring:message code="ess.title.Title"/></td>
		<td><input type="text" name="seach_TITLE" value="${TITLE}" /></td>
		<td><!--考勤区分 --><spring:message code="ess.title.attendance_distinction"/></td>
		<td>
			<select name="seach_APPLY_TYPE_CODE">
				<option value=""><!--请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
				<option value="31" <c:if test="${APPLY_TYPE_CODE eq '31' }">selected</c:if>><!--加班 --><spring:message code="ess.attendance.ot"/></option>
				<option value="21" <c:if test="${APPLY_TYPE_CODE eq '21' }">selected</c:if>><!--考勤 --><spring:message code="ess.attendance.state"/></option>
				<option value="218197" <c:if test="${APPLY_TYPE_CODE eq '218197' }">selected</c:if>><!--考勤异常 --><spring:message code="ess.title.attendance_anomaly"/></option>
			</select>
		</td>
		<td><spring:message code="hrm.contract.Rank" /><!-- 职级 --></td>
		<td class="td_type">
			<select name="seach_POST_GRADE_NO">
				<option value=""><!--请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
				<option value="staff" <c:if test="${POST_GRADE_NO eq 'staff' }">selected</c:if>><!--Đã duyệt -->Staff</option>
				<option value="worker" <c:if test="${POST_GRADE_NO eq 'worker' }">selected</c:if>><!--Chưa duyệt -->Worker</option>
			</select>
		</td>
		<td>1st Approve Status</td>
		<td>
			<select name="seach_AFFIRMED">
				<option value=""><!--请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
				<option value="1" <c:if test="${AFFIRMED eq '1' }">selected</c:if>><!--Đã duyệt --><spring:message code="ess.trans.title.affirmed"/></option>
				<option value="0" <c:if test="${AFFIRMED eq '0' }">selected</c:if>><!--Chưa duyệt --><spring:message code="ess.trans.title.notAffirmed"/></option>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewApprovalEmail_seach" href="#">
					<span style="margin-left:-0px"><!--查询 --><spring:message code="ess.infoApply.SELECT"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="executeAffirmBatch(1)" href="#"><span><!--统一通过 --><spring:message code="ess.attendance.toyitongguo"/></span></a>
			</li>
			<li>
			    <a class="buttonActive" onclick="executeAffirmBatch(2)" href="#"><span><!--统一否决 --><spring:message code="ess.attendance.toyifoujue"/></span></a>
			</li>
	</ul>
</div>
<div class="pageContent" style="padding-left:10px;padding-right:10px;">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewApprovalEmailSize}</div>
		<table class="table" width="100%" layoutH="200">
			<thead>
				<tr>
				    <th width="20">
				    <!--NO--><spring:message code="sys.homeParam.title.NO"/>
				    </th>
					<th width="30">
				    	<input type="checkbox" class="checkboxCtrl" group="viewApprovalEmail_checkbox" />
				    </th>
				    <th width="380" style="text-align: center"><!--日期-->
				    <!--标题--><spring:message code="ess.title.Title"/>
					</th>
				    <th width="160" style="text-align: center"><!--星期-->
				    <!-- 期案者--><spring:message code="public.title.qianzhe"/>
					</th>
					<th width="80" style="text-align: center"><!--加班类型-->
					<!--期末结算日--><spring:message code="public.title.qianjiesuanriqi"/>
					</th>
					<th width="80" style="text-align: center"><!--开始时间-->
					<!--审批状态--><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewApprovalEmail}" var="item" varStatus="i">			
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" name="viewApprovalEmail_checkbox" id="checkbox_${i.index }" value=' "APPLY_NO":"${item.APPLY_NO}","APPLY_TYPE":"${item.APPLY_TYPE}","APPLY_FLAG":"${item.APPLY_FLAG}","AFFIRM_LEVEL":"${item.AFFIRM_LEVEL}", ' />
					    </td>
					    <td style="text-align:left"><a href="${item.AFFIRM_URL}?seach_SEQ=${item.SEQ}&seach_APPLY_NO=${item.APPLY_NO}&READ_FLAG=1&seach_APPLY_TYPE=${item.APPLY_TYPE}&APPLY_FLAG=${item.APPLY_FLAG}&seach_ACTIVITY=${item.ACTIVITY}" target="dialog" mask="true" width="1000" height="600" rel="applyInfo"<c:if test="${item.READ_FLAG eq '0'}">style="color:blue;font-weight:bold;"</c:if> onclick="changeColor_ess0601(this)">${item.TITLE}</a></td>
					    <td style="text-align: center">${item.APPLY_PERSON_INFO}</td>
					    <td style="text-align: center">${item.UPDATE_DATE}</td>
					    <td style="text-align: center">${item.APPLY_AFFIRM_FLAG}</td>
					    <input type="hidden" name="PERSON_ID" id="PERSON_ID_${i.index }" value="${item.APPLY_PERSON_ID }">
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>