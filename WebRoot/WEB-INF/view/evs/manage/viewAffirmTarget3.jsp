<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewAffirmTarget2_search",navTab.getCurrentPanel()).click(function(){
		$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewAffirmTarget2ResumeNo",navTab.getCurrentPanel()).change(function(){
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
		$("input[name='EVS_POINT']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(obj).val() != ''){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				jsonData += ' "SEQ": "' + $("#evsSeq_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "EVS_POINT": "' + $(obj).val() + '" ,';
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

	$('td:[syslong="rat"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$("select[name='EVS_GRADE']",navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($(object).find("option:selected").text() == ''){
					cnt = cnt + 1;
				}
			}else{
				if($(object).find("option:selected").text() == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);
	});

	//复制考评等级
	$("#viewAffirmTarget1_CopyEvsGrade",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewAffirmTarget2.QUEDINGYAOFUZHIPINGJIADENGJIMA.a'/>",//确定要复制评价等级吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/copyEvsGrade',
					data: { SEQ: '${RESUME_SEQ}',EVS_STEP:'14015071' },
	  				dataType:"json",
	  				cache: false,
	  				success: function(json){
						DWZ.ajaxDone(json);
						$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
		  			},
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
});

function changeEvsGrade2(index){
	var total = $("#evsPoint_" + index,navTab.getCurrentPanel()).val();
	if(total != ''){
		$('#evsGrade_' + index + ' option',navTab.getCurrentPanel()).each(function(i, obj){
			if(i>0){
				if(total > parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
	}
}

function changeURL_person(personId,resumeSEQ,seq,activity,localName){ 
	var href = "/evs/manage/viewConfirmTargetInfoSST?APPLY_PERSON_ID="+ personId + "&EVS_PERSON_ID="+ personId +"&RESUME_SEQ="+ resumeSEQ +"&EVS_OBJECT_SEQ="+ seq +"&ACTIVITY="+ 14015359 +"";
	var localName = localName;
	$.pdialog.open(href,"ess0506", localName, {width:1000,height:600,mask:true});
}
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewAffirmTargetForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewAffirmTarget3" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewAffirmTarget2ResumeNo" name="RESUME_SEQ">
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
						<ait:evsCodeMulti id="seach_EVS_DEPT" name="seach_EVS_DEPT_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_DEPT2" selected="${EVS_DEPT}" selectedNm="${EVS_DEPT_NAME}"/>
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
						<a class="buttonActive" id="viewAffirmTarget2_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
					<c:if test="${currentEvsCnt ne 0}">
					<li>
						<a class="buttonActive" id="viewAffirmTarget1_CopyEvsGrade">
							<span><spring:message code="evs.viewAffirmTarget2.FUZHIPINGJIADENGJI.a"/><!--复制评价等级--></span>
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
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>
<%@ include file="/WEB-INF/view/evs/manage/viewEvsAffirmRatAbility.jsp"%>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员-->：${viewConfirmTargetSize}&nbsp;&nbsp;/&nbsp;&nbsp;<spring:message code="evs.viewAffirmTarget1.PINGJIADUIXIANGRENYUAN.a"/><!--评价对象人员-->：${viewConfirmTargetCnt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<table class="evsList" id="viewaffirmTarget_evsList" width="1540px;">
			<thead>
				<tr>
					<th width="3%" rowspan="2">No.</th>
					<th width="5%" rowspan="2"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
					<th width="5%" rowspan="2"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
					<th width="9%" rowspan="2"><spring:message code="org.title.dept"/><!--部门--></th>
					<th width="3%" rowspan="2"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></th>
					<th width="6%" rowspan="2"><spring:message code="evs.viewEvsParamInfoList.DUIXIANGLEIXING.a"/><!--对象类型--></th>
					<th width="5%" rowspan="2"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
					<th  width="9%" colspan="2"><spring:message code="evs.viewEvsResult.BENREN.a"/><!--本人--></th>
					<th  width="15%" colspan="3"><spring:message code="evs.viewAffirmTarget1.YICI.a"/><!--1次--></th>
					<th  width="18%" colspan="3"><spring:message code="evs.viewAffirmTarget1.ERCI.a"/><!--2次--></th>
					<th  width="18%" colspan="3"><spring:message code="evs.viewEvsResult.SANCI.a"/><!--3次--></th>
					<th width="6%" rowspan="2"><spring:message code="ess.empInfo.conduct_state"/><!--进行状态--></th>
				</tr>
				<tr>
					<th width="4%"><spring:message code="inct.salesman.evalPoint"/><!-- Point --></th>
					<th width="5%"><spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/><!--评价等级--></th>
					<th width="4%"><spring:message code="inct.salesman.evalPoint"/><!-- Point --></th>
					<th width="5%"><spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/><!--评价等级--></th>
					<th width="6%"><spring:message code="evs.viewAffirmTarget1.KAOHEYIJIAN.a"/><!--考核意见--></th>
					<th width="6%"><spring:message code="inct.salesman.evalPoint"/><!-- Point --></th>
					<th width="6%"><spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/><!--评价等级--></th>
					<th width="6%"><spring:message code="evs.viewAffirmTarget1.KAOHEYIJIAN.a"/><!--考核意见--></th>
					<th width="6%"><spring:message code="inct.salesman.evalPoint"/><!-- Point --></th>
					<th width="6%"><spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/><!--评价等级--></th>
					<th width="6%"><spring:message code="evs.viewAffirmTarget1.KAOHEYIJIAN.a"/><!--考核意见--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${viewConfirmTarget}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td class="td_type" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_person(${item.PERSON_ID },"${item.RESUME_SEQ }","${item.SEQ}", "14015357", "${item.LOCAL_NAME }");'>
           			<span style="color: blue">${item.LOCAL_NAME }</span></td>
					<%-- <td><a href="/evs/manage/viewConfirmTargetInfoSST?APPLY_PERSON_ID=${item.PERSON_ID }&EVS_PERSON_ID=${item.PERSON_ID }&RESUME_SEQ=${RESUME_SEQ }&EVS_OBJECT_SEQ=${item.SEQ}&ACTIVITY=14015359" 
					target="dialog" width="800" height="600" mask="true" style="color:blue;">${item.LOCAL_NAME }</a></td> --%>
					<td>${item.EMPID }</td>
					<td>${item.DEPTNAME }</td>
					<td>${item.POST_GRADE_NAME }</td>
					<td>${item.OBJECT_TYPE_NAME }</td>
					<td>${item.DATE_STARTED }</td>
					<td style="text-align:center">${item.EVS_POINT0 }</td>
					<td style="text-align:center">${item.EVS_GRADE0 }</td>
					<td style="text-align:center">${item.EVS_POINT1 }</td>
					<td style="text-align:center">${item.EVS_GRADE1 }</td>
					<td style="text-align:center">
						<c:if test="${not empty item.AFFIRM_CONTENT1}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','9999999',${i.index},'${item.AFFIRM_CONTENT1 }');"></img>
						</c:if>
					</td>
					<td style="text-align:center">
						<input type="text" id="evsPoint_${i.index}" sysIndex="${i.index}" name="EVS_POINT" size="7" onblur="changeEvsGrade2('${i.index}')" value="${item.EVS_POINT2 }" <c:if test="${item.ACTIVITY ne '14015359'}">disabled="disabled"</c:if>/>
						<input type="hidden" id="evsSeq_${i.index}" value="${item.SEQ2 }"/>
					</td>
					<td style="text-align:center">
						<select id="evsGrade_${i.index}" name="EVS_GRADE" style="width:70px" disabled="disabled">
							<option value=""></option>
							<c:forEach items="${viewGradeList}" var="item2" varStatus="j">
								<option value="${item2.START_SCORE }" <c:if test="${item2.EVS_GRADE_NAME eq item.EVS_GRADE2}">selected="selected"</c:if>>${item2.EVS_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>
					
					<td style="text-align:center">
						<c:if test="${not empty item.AFFIRM_CONTENT2}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT2 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','9999999',${i.index},'${item.AFFIRM_CONTENT2 }');"></img>
						</c:if>
					</td>
					<td style="text-align:center">
						<input type="text" id="evsPoint_${i.index}" sysIndex="${i.index}" name="EVS_POINT" size="7" onblur="changeEvsGrade2('${i.index}')" value="${item.EVS_POINT2 }" <c:if test="${item.ACTIVITY ne '14015359'}">disabled="disabled"</c:if>/>
						<input type="hidden" id="evsSeq_${i.index}" value="${item.SEQ2 }"/>
					</td>
					<td style="text-align:center">
						<select id="evsGrade_${i.index}" name="EVS_GRADE" style="width:70px" disabled="disabled">
							<option value=""></option>
							<c:forEach items="${viewGradeList}" var="item2" varStatus="j">
								<option value="${item2.START_SCORE }" <c:if test="${item2.EVS_GRADE_NAME eq item.EVS_GRADE2}">selected="selected"</c:if>>${item2.EVS_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>
					
					<td style="text-align:center">
						<c:if test="${item.ACTIVITY eq '14015359' and empty item.AFFIRM_CONTENT3}">
							<img src="/resources/images/newImages/Add_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT3 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015359',${i.index});"></img>
							<input type="hidden" name="EVS_SEQ" sysIndex="${i.index}" value="${item.SEQ3 }"/>
							<input type="hidden" name="EVS_OBJECT_SEQ" sysIndex="${i.index}" value="${item.SEQ }"/>
						</c:if>
						<c:if test="${item.ACTIVITY eq '14015359' and not empty item.AFFIRM_CONTENT3}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT3 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015359',${i.index});"></img>
							<input type="hidden" name="EVS_SEQ" sysIndex="${i.index}" value="${item.SEQ3 }"/>
							<input type="hidden" name="EVS_OBJECT_SEQ" sysIndex="${i.index}" value="${item.SEQ }"/>
						</c:if>
						<c:if test="${item.ACTIVITY ne '14015359' and not empty item.AFFIRM_CONTENT3}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT3 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015359',${i.index},'${item.AFFIRM_CONTENT3 }');"></img>
						</c:if>
						<input type="hidden" id="affirmContent_${i.index}" value="${item.AFFIRM_CONTENT3 }"/>
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
