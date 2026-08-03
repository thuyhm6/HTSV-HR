<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewConfirmTarget1_search",navTab.getCurrentPanel()).click(function(){
		$("#viewConfirmTarget1Form",navTab.getCurrentPanel()).submit();
	});
	$("#viewConfirmTarget1ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewConfirmTarget1Form",navTab.getCurrentPanel()).submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": true,  //关闭按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"searching": true,//本地搜索
		"bSort": true,   //关闭排序功能
		"bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 270,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    } 
	});
});
function changeURL_person(personId,resumeSEQ,seq,activity,localName){ 
	var href = "/evs/manage/viewConfirmTargetInfo?APPLY_PERSON_ID="+ personId + "&EVS_PERSON_ID="+ personId +"&RESUME_SEQ="+ resumeSEQ +"&EVS_OBJECT_SEQ="+ seq +"&ACTIVITY="+ 14015364 +"&LEVEL="+ 1 +"";
	var localName = localName;
	$.pdialog.open(href,"ess0502", localName, {width:1000,height:600,mask:true});
}
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewConfirmTarget1Form" onsubmit="return navTabSearch(this);" action="/evs/manage/viewConfirmTarget1" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewConfirmTarget1ResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
						<input type="hidden" name="AFFIRM_LEVEL" value="${AFFIRM_LEVEL }">
						<input type="hidden" name="seach_LIMIT" value="${LIMIT }">
					</td>
					<td><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_DEPT" name="seach_EVS_DEPT_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_DEPT1" selected="${EVS_DEPT}" selectedNm="${EVS_DEPT_NAME}"/>
					</td>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_GROUP" name="seach_EVS_GROUP_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_GROUP" selected="${EVS_GROUP}" selectedNm="${EVS_GROUP_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewConfirmTarget1_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
					<%-- <li>
						<a class="buttonActive" id="viewConfirmTarget1_init">
							<span><spring:message code="button.init"/><!--初始化--></span>
						</a>
					</li> --%>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员-->：${viewConfirmTargetSize}&nbsp;&nbsp;/&nbsp;&nbsp;<spring:message code="evs.viewAffirmTarget1.PINGJIADUIXIANGRENYUAN.a"/><!--评价对象人员-->：${viewConfirmTargetCnt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<table class="list" width="1150px;">
			<thead>
				<tr>
					<th width="30px">No.</th>
					<th width="30px"><input type="checkbox" class="checkboxCtrl" group="viewConfirmTarget_checkbox"></th>
					<th width="100px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
					<th width="50px"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
					<th width="50px"><spring:message code="org.title.dept"/><!--部门--></th>
					<th width="50px"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></th>
					<th width="50px"><spring:message code="evs.viewEvsParamInfoList.DUIXIANGLEIXING.a"/><!--对象类型--></th>
					<th width="50px"><spring:message code="ess.empInfo.conduct_state"/><!--进行状态--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${viewConfirmTarget}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td style="text-align:center"><input type="checkbox" name="viewConfirmTarget_checkbox" value= "1"/></td>
					<c:if test="${item.ACTIVITY ne '14015354'}">
					<%-- <a href="/evs/manage/viewConfirmTargetInfo?RESUME_SEQ=${RESUME_SEQ }&EVS_PERSON_ID=${item.PERSON_ID }&EVS_OBJECT_SEQ=${item.SEQ}&ACTIVITY=14015364" 
					target="dialog" width="800" height="600" mask="true" style="color:blue;">${item.LOCAL_NAME }</a> --%>
					<td class="td_type" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${item.PERSON_ID },"${RESUME_SEQ }","${item.SEQ}", "14015364", "${item.LOCAL_NAME }");'>
           			<span style="color: blue">${item.LOCAL_NAME }</span></td>
					</c:if>
					<c:if test="${item.ACTIVITY eq '14015354'}">
					<td style="text-align:center">${item.LOCAL_NAME}</td>
					</c:if>
					<td>${item.EMPID }</td>
					<td>${item.DEPTNAME }</td>
					<td>${item.POST_GRADE_NAME }</td>
					<td>${item.OBJECT_TYPE_NAME }</td>
					<td style="text-align:center">${item.ACTIVITY_NAME}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
