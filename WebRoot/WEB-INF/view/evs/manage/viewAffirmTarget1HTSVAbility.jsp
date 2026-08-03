<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewAffirmTarget1_search",navTab.getCurrentPanel()).click(function(){
		$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
	});
	
	$("#viewAffirmTarget1ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
	});

	$("#viewaffirmTarget_evsList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": true,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"searching": true,//本地搜索
		"bSort": true,   //关闭排序功能
		"bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 410,
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
	
	//保存
	$("#viewAffirmTarget1_save",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var jsonData = '[';
		$("input:[name='EVS_SEQ']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(obj).val() != ''){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				jsonData += ' "SEQ": "' + $(obj).val() + '" ,';
				jsonData += ' "EVS_POINT": "0" ,';
				jsonData += ' "EVS_GRADE": "' + $("#evsGrade_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).find("option:selected").text() + '" ,';
				jsonData += ' "AFFIRM_CONTENT": "' + $("#affirmContent_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA'/>");//没有需要保存的数据
			return;
		}

		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/affirmTarget',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
		affirmTargetFun();
	});
	affirmTargetFun();
});

function changeURL_person(personId,resumeSEQ,seq,activity,localName){ 
	var href = "/evs/manage/viewConfirmTargetInfoHTSVAbility?APPLY_PERSON_ID="+ personId + "&EVS_PERSON_ID="+ personId +"&RESUME_SEQ="+ resumeSEQ +"&EVS_OBJECT_SEQ="+ seq +"&ACTIVITY="+ 14015358 +"";
	var localName = localName;
	$.pdialog.open(href,"ess0517", localName, {width:1000,height:600,mask:true});
}
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewAffirmTargetForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewAffirmTarget1HTSVAbility" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewAffirmTarget1ResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
						<input type="hidden" name="AFFIRM_LEVEL" value="${AFFIRM_LEVEL }">
						<input type="hidden" name="seach_LIMIT" value="${LIMIT }">
						<input type="hidden" name="seach_EVS_CYCLE" value="${EVS_CYCLE }">
					</td>
					<td><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
					<td>
						<%-- <ait:evsCodeMulti id="seach_EVS_DEPT" name="seach_EVS_DEPT_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_DEPT1" selected="${EVS_DEPT}" selectedNm="${EVS_DEPT_NAME}"/> --%>
						<ait:deptList name="seach_DEPTNO" limit="hr" id="viewAffirmTarget1HTSVAbility_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewAffirmTarget1HTSVAbility_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
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
						<a class="buttonActive" id="viewAffirmTarget1_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" id="viewAffirmTarget1_save">
							<span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span>
						</a>
					</li>
					<li id="viewAffirmTargetConfirm" style="display:none;">
						<a class="buttonActive" onclick="viewAffirmTarget1_confirm()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<%@ include file="/WEB-INF/view/evs/manage/viewEvsAffirmRatAbility.jsp"%>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员-->：${viewConfirmTargetSize}&nbsp;&nbsp;/&nbsp;&nbsp;<spring:message code="evs.viewAffirmTarget1.PINGJIADUIXIANGRENYUAN.a"/><!--评价对象人员-->：${viewConfirmTargetCnt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<table class="evsList" id="viewaffirmTarget_evsList" width="1200px;">
			<thead>
				<tr>
					<th width="30px">No.</th>
					<th width="120px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
					<th width="90px"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
					<th width="120px"><spring:message code="org.title.dept"/><!--部门--></th>
					<th width="90px"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></th>
					<th width="130px"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></th>
					<th width="80px"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
					<th width="120px"><spring:message code="evs.viewAffirmTarget11HTSV.PINGJIADENGJIYICI.a"/><!--评价等级1次--></th>
					<th width="120px"><spring:message code="evs.viewConfirmTargetInfoSST.YICIKAOHEYIJIAN.a"/><!--1次考核意见--></th>
					<th width="70px"><spring:message code="ess.empInfo.conduct_state"/><!--进行状态--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${viewConfirmTarget}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td class="td_type" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${item.PERSON_ID },"${RESUME_SEQ }","${item.SEQ}", "14015357", "${item.LOCAL_NAME }");'>
           			<span style="color: blue">${item.LOCAL_NAME }</span></td>
					<%-- <td style="text-align:center"><a href="/evs/manage/viewConfirmTargetInfoHTSVAbility?APPLY_PERSON_ID=${item.PERSON_ID }&EVS_PERSON_ID=${item.PERSON_ID }&RESUME_SEQ=${RESUME_SEQ }&EVS_OBJECT_SEQ=${item.SEQ}&ACTIVITY=14015357" 
					target="dialog" width="800" height="600" mask="true" style="color:blue;">${item.LOCAL_NAME }</a></td> --%>
					<td style="text-align:center">${item.EMPID }</td>
					<td>${item.DEPTNAME }</td>
					<td>${item.POST_GRADE_NAME }</td>
					<td>${item.MAIN_BUSINESS_NAME }</td>
					<td>${item.DATE_STARTED }</td>
					<td style="text-align:center">
						<select id="evsGrade_${i.index}" name="EVS_GRADE" style="width:70px" <c:if test="${item.ACTIVITY ne '14015357'}">disabled="disabled"</c:if>>
							<option value=""></option>
							<c:forEach items="${viewGradeList}" var="item2" varStatus="j">
								<option value="${item2.START_SCORE }" <c:if test="${item2.EVS_GRADE_NAME eq item.EVS_GRADE1}">selected="selected"</c:if>>${item2.EVS_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align:center">
						<c:if test="${item.ACTIVITY eq '14015357' and empty item.AFFIRM_CONTENT1}">
							<img src="/resources/images/newImages/Add_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015357',${i.index});"></img>
							<input type="hidden" name="EVS_SEQ" sysIndex="${i.index}" value="${item.SEQ1 }"/>
							<input type="hidden" name="EVS_OBJECT_SEQ" sysIndex="${i.index}" value="${item.SEQ }"/>
						</c:if>
						<c:if test="${item.ACTIVITY eq '14015357' and not empty item.AFFIRM_CONTENT1}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015357',${i.index});"></img>
							<input type="hidden" name="EVS_SEQ" sysIndex="${i.index}" value="${item.SEQ1 }"/>
							<input type="hidden" name="EVS_OBJECT_SEQ" sysIndex="${i.index}" value="${item.SEQ }"/>
						</c:if>
						<c:if test="${item.ACTIVITY ne '14015357' and not empty item.AFFIRM_CONTENT1}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015357',${i.index},'${item.AFFIRM_CONTENT1 }');"></img>
						</c:if>
						<input type="hidden" id="affirmContent_${i.index}" value="${item.AFFIRM_CONTENT1 }"/>
					</td>
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
