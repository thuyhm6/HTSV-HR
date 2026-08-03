<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewApprovaledEmail_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewApprovaledEmailForm",navTab.getCurrentPanel()).submit();
	});
	//删除
	$("#viewApprovaledEmail_delete",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var SEQ = "";
		$("input[name='viewApprovaledEmail_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				SEQ += ",'" + obj.value + "'";
			}
		});

		if (SEQ.length < 1) {
			//请先选择要删除的数据
			alertMsg.info("<spring:message code='ess.infoApply.select_data_delete'/>");
			return;
		}
        //确定要删除吗？
		alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/infoApply/deleteAffirmBatch',
					data: [{ name: 'SEQ', value: SEQ.substr(1) },
					       { name: 'FORM_ID', value: "viewApprovaledEmailForm" }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	//标记为已读
	$("#viewApprovaledEmail_readed",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var SEQ = "";
		$("input[name='viewApprovaledEmail_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				SEQ += ",'" + obj.value + "'";
			}
		});

		if (SEQ.length < 1) {
			//请先选择要标记为已读的数据
			alertMsg.info("<spring:message code='ess.infoApply.select_data_want_mark_read'/>");
			return;
		}
        //确定要标记为已读吗？
		alertMsg.confirm("<spring:message code='ess.infoApply.confirm_mark_read'/>",
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/infoApply/readedAffirmBatch',
					data: [{ name: 'SEQ', value: SEQ.substr(1) },
					       { name: 'FORM_ID', value: "viewApprovaledEmailForm" }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
});
function changeColor(obj){
	$(obj).css("color","black").css("font-weight","normal");
}
</script>

<div class="pageHeader">
<form id="viewApprovaledEmailForm" onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewApprovaledEmail" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!--标题 --><spring:message code="ess.title.Title"/></td>
		<td><input type="text" name="seach_TITLE" value="${TITLE}" /></td>
		<td><!--考勤区分 --><spring:message code="ess.title.attendance_distinction"/></td>
		<td>
			<select name="seach_APPLY_TYPE_CODE">
				<option value=""><!--请选择 --><spring:message code="org.title.PLEASE_SELECT"/></option>
				<option value="31" <c:if test="${APPLY_TYPE_CODE eq '31' }">selected</c:if>><!--加班 --><spring:message code="ess.attendance.ot"/></option>
				<option value="21" <c:if test="${APPLY_TYPE_CODE eq '21' }">selected</c:if>><!--考勤 --><spring:message code="ess.attendance.state"/></option>
				<option value="218197" <c:if test="${APPLY_TYPE_CODE eq '218197' }">selected</c:if>><!--考勤异常 --><spring:message code="ess.title.attendance_anomaly"/></option>
			</select>
		</td>
		<td><!--已读/未读 --><spring:message code="ess.infoApply.read_notRead"/></td>
		<td>
			<select name="seach_READ_FLAG">
				<option value="2" <c:if test="${READ_FLAG eq '2' }">selected</c:if>><!--全部 --><spring:message code="ess.infoApply.whole"/></option>
				<option value="0" <c:if test="${READ_FLAG eq '0' }">selected</c:if>><!--未读 --><spring:message code="hrm.ess.apply.weidu"/></option>
				<option value="1" <c:if test="${READ_FLAG eq '1' }">selected</c:if>><!--已读 --><spring:message code="hrm.ess.apply.yidu"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><!--期间 --><spring:message code="ess.infoApply.Period"/></td>
		<td colspan="3">
			<input type="text" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${START_DATE }"/>~
			<input type="text" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${END_DATE }"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewApprovaledEmail_Serch" href="#">
					<span><!--查询 --><spring:message code="ess.infoApply.SELECT"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" id="viewApprovaledEmail_delete" href="#">
					<span><!--删除 --><spring:message code="org.title.DELETE"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" id="viewApprovaledEmail_readed" href="#">
					<span><!--标记为已读 --><spring:message code="hr.viewCondSql.title.BIAOJIYIDU"/></span>
				</a>
			</li>
	</ul>
</div>
<div class="pageContent" style="padding-left:10px;padding-right:10px;">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewApprovaledEmailSize}</div>
		<table class="table" width="100%" layoutH="200">
			<thead>
				<tr>
				    <th width="20">
				    	<!--NO--><spring:message code="sys.homeParam.title.NO"/>
				    </th>
					<th width="30">
				    	<input type="checkbox" class="checkboxCtrl" group="viewApprovaledEmail_checkbox" />
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
				<c:forEach items="${viewApprovaledEmail}" var="item" varStatus="i">			
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" name="viewApprovaledEmail_checkbox" value="${item.SEQ}" />
					    </td>
					    <td style="text-align:left"><a href="${item.AFFIRM_URL}?seach_SEQ=${item.SEQ}&READ_FLAG=1&seach_APPLY_NO=${item.APPLY_NO}&seach_APPLY_TYPE=${item.APPLY_TYPE}&APPLY_FLAG=${item.APPLY_FLAG}&seach_ACTIVITY=${item.ACTIVITY}" target="dialog" mask="true" width="1000" height="600" <c:if test="${item.READ_FLAG eq '0'}">style="color:blue;font-weight:bold;"</c:if> onclick="changeColor(this)">${item.TITLE}</a></td>
					    <td style="text-align: center">${item.APPLY_PERSON_INFO}</td>
					    <td style="text-align: center">${item.UPDATE_DATE}</td>
					    <td style="text-align: center">${item.APPLY_AFFIRM_FLAG}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>