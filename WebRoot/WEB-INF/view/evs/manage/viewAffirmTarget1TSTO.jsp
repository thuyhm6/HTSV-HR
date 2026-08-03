<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewAffirmTarget1_search",navTab.getCurrentPanel()).click(function(){
		$("#viewAffirmTarget1Form",navTab.getCurrentPanel()).submit();
	});
	
	$("#viewAffirmTarget1ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewAffirmTarget1Form",navTab.getCurrentPanel()).submit();
	});

	$(".evsList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 370,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
		 "sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
         "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",//查询不到相关数据！
         "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",//表中无数据存在！
         "sSearch": "<spring:message code='ess.message.rapid_screening'/>"//快速筛选
        } //多语言配置
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
	});

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
});
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewAffirmTarget1Form" onsubmit="return navTabSearch(this);" action="/evs/manage/viewAffirmTarget1TSTO" method="post" >
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
						<a class="buttonActive" id="viewAffirmTarget1_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" id="viewAffirmTarget1_save">
							<span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="viewAffirmTarget1_confirm()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1TSTO.PINGJIADENGJIFENBU.a"/><!--评价 等级 分布--></div>
		
		<table class="user_table" width="100%">	
			<tr>
				<td class="td_title" style="text-align:center;" width="14%"><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="ess.viewpersonalpainfo.heji"/><!--合计--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewAffirmTarget1TSTO.WEISHURU.a"/><!--未输入--></td>
				<c:forEach items="${viewGradeList}" var="item" varStatus="i">
					<td class="td_title" style="text-align:center;" width="10%">${item.EVS_GRADE_NAME}</td>
				</c:forEach>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center"><spring:message code="evs.viewEvsResult.BIAOZHUNFENPEILV.a"/><!--标准分配率--></td>
			    <td class="td_type" style="text-align:center;">100</td>
			    <td class="td_type" style="text-align:center;">-</td>
				<c:forEach items="${viewGradeList}" var="item" varStatus="i">
			    	<td class="td_type" style="text-align:center;">${scoreList[0][item.EVS_GRADE_NAME]}</td>
				</c:forEach>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center"><spring:message code="evs.viewAffirmTarget1TSTO.RENYUANBIAOZHUN.a"/><!--人员 标准--></td>
			    <td class="td_type" style="text-align:center;">${viewConfirmTargetSize}</td>
			    <td class="td_type" style="text-align:center;">-</td>
				<c:forEach items="${viewGradeList}" var="item" varStatus="i">
			    	<td class="td_type" style="text-align:center;"><fmt:formatNumber value="${scoreList[0][item.EVS_GRADE_NAME] * viewConfirmTargetSize * 0.01}" pattern="0.0"/></td>
				</c:forEach>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员--></td>
			    <td class="td_type" style="text-align:center;">${viewConfirmTargetSize}</td>
			    <td class="td_type" style="text-align:center;" syslong="rat" sysGrade="empty"></td>
				<c:forEach items="${viewGradeList}" var="item" varStatus="i">
					<td class="td_type" style="text-align:center;" syslong="rat" sysGrade="${item.EVS_GRADE_NAME}">${item.EVS_GRADE_NAME}</td>
				</c:forEach>
			</tr>
		</table>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员-->：${viewConfirmTargetSize}&nbsp;&nbsp;/&nbsp;&nbsp;<spring:message code="evs.viewAffirmTarget1.PINGJIADUIXIANGRENYUAN.a"/><!--评价对象人员-->：${viewConfirmTargetCnt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<table class="evsList" width="1100px;">
			<thead>
				<tr>
					<th width="30px">No.</th>
					<th width="70px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
					<th width="70px"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
					<th width="100px"><spring:message code="org.title.dept"/><!--部门--></th>
					<th width="70px"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></th>
					<th width="80px"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></th>
					<th width="70px"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
					<th width="70px"><spring:message code="evs.viewAffirmTarget11HTSV.PINGJIADENGJIYICI.a"/><!--评价等级1次--></th>
					<th width="70px"><spring:message code="evs.viewConfirmTargetInfoSST.YICIKAOHEYIJIAN.a"/><!--1次考核意见--></th>
					<th width="70px"><spring:message code="ess.empInfo.conduct_state"/><!--进行状态--></th>
					<th width="50px"><spring:message code="ar.monthwork.title.kuanggong"/><!--旷工--></th>
					<th width="50px"><spring:message code="evs.viewAffirmTarget11HTSV.BINGSHIJIA.a"/><!--病/事假--></th>
					<th width="50px"><spring:message code="evs.viewAffirmTarget11HTSV.CHANBUJISHENG.a"/><!--产/哺/计生--></th>
					<th width="50px"><spring:message code="evs.viewAffirmTarget11HTSV.TINGGONG.a"/><!--停工--></th>
					<th width="50px"><spring:message code="evs.viewAffirmTarget11HTSV.HUNSANG.a"/><!--婚丧--></th>
					<th width="50px"><spring:message code="ar.viewArAnnualStandard.title.ninjia"/><!--年假--></th>
					<th width="50px"><spring:message code="ess.infoApply.LATE_TIMES"/><!--迟到次数--></th>
					<th width="50px"><spring:message code="ess.infoApply.leave_early"/><!--早退--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${viewConfirmTarget}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td><a href="/evs/manage/viewConfirmTargetInfoTSTO?EVS_PERSON_ID=${item.PERSON_ID }&RESUME_SEQ=${RESUME_SEQ }&EVS_OBJECT_SEQ=${item.SEQ}&ACTIVITY=14015357" target="dialog" width="800" height="600" mask="true" style="color:blue;">${item.LOCAL_NAME }</a></td>
					<td>${item.EMPID }</td>
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
						<c:if test="${item.ACTIVITY eq '14015357'}">
							<img src="/resources/images/newImages/Add_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015358',${i.index});"></img>
							<input type="hidden" name="EVS_SEQ" sysIndex="${i.index}" value="${item.SEQ1 }"/>
							<input type="hidden" name="EVS_OBJECT_SEQ" sysIndex="${i.index}" value="${item.SEQ }"/>
						</c:if>
						<c:if test="${item.ACTIVITY ne '14015357'}">
							<img src="/resources/images/newImages/view_en.gif" style="vertical-align:middle;cursor:pointer;" title="${item.AFFIRM_CONTENT1 }" onclick="openEvsCommentWindow('${item.ACTIVITY}','14015358',${i.index});"></img>
						</c:if>
						<input type="hidden" id="affirmContent_${i.index}" value="${item.AFFIRM_CONTENT1 }"/>
					</td>
					<td style="text-align:center">${item.ACTIVITY_NAME}</td>
					<td>${item.KUANGGONG }</td>
					<td>${item.BINGJIA }</td>
					<td>${item.CHANJIA }</td>
					<td>${item.TINGGONG }</td>
					<td>${item.HUNSANG }</td>
					<td>${item.NIANJIA }</td>
					<td>${item.CHIDAO }</td>
					<td>${item.ZAOTUI }</td>
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
