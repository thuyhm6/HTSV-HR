<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsResult_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsResultForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResult&seach_KEY='+name+'&seach_RESUME_SEQ=${RESUME_SEQ}' + '&seach_evsType=${evsType }');
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResult&seach_KEY='+name+'&seach_RESUME_SEQ=${RESUME_SEQ}' + '&seach_evsType=${evsType }');
    });
	//打印
	$("#viewEvsResult_print",navTab.getCurrentPanel()).click(function(){
		var empIdsStr="";
		var flag=false;
		$("input[name='viewEvsResult_checkbox']").each(function(){
			if($(this).attr("checked") == "checked"){
				empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
				flag = true;
			}
		});
		empIdsStr = empIdsStr + "'empty'";
		if(flag == false){
			alertMsg.error("<spring:message code='evs.viewEvsResult.QINGXIANGOUXUANYAODAYINDEDUIXIANG.a'/>");//请先勾选要打印的对象
			return false;
		}
		var activity = $("#seach_EVS_ACTIVITY",navTab.getCurrentPanel()).val();
		if(activity == ''){
			activity = '14015359';
		}
		window.open('/evs/manage/viewConfirmTargetInfoPrint?EVS_PERSON_ID_BATCH=' + empIdsStr + '&RESUME_SEQ=${RESUME_SEQ}&ACTIVITY=' + activity,'newwindow','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=595,height=600');
	});
	$('.evsList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
    		$(this).html(val);
			var index = $(this).attr("sysIndex");
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.evsList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
    		$(this).html(val);
			var index = $(this).attr("sysIndex");
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	
	$("#evsListResult2",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"searching": true,//本地搜索
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 470,
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

	//状态变更
	$("#viewEvsResult_changeActivity",navTab.getCurrentPanel()).click(function(){
		var activity = $("#seach_EVS_ACTIVITY",navTab.getCurrentPanel()).val();
		if(activity == ''){
			alertMsg.error("<spring:message code='evs.viewEvsResult.QINGXIANXUANZEZHUANGTAI.a'/>");//请先选择状态
			return false;
		}

		var empIdsStr="";
		var flag=false;
		$("input[name='viewEvsResult_checkbox']").each(function(){
			if($(this).attr("checked") == "checked"){
				empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
				flag = true;
			}
		});
		empIdsStr = empIdsStr + "'empty'";
		if(flag == false){
			alertMsg.error("<spring:message code='evs.viewEvsResult.QINGXIANXUANZEYAOBIANGENGDEDUIXIANG.a'/>");//请先选择要变更的对象
			return false;
		}
		alertMsg.confirm("<spring:message code='evs.viewEvsResult.QUEDINGYAOBIANGENGMA.a'/>",//确定要变更吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/evs/manage/changeEvsActivity',
	  				data:{empIds:empIdsStr,evsActivity:activity},
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  		});
	  	}});
		return false;
	});

	//确定等级反映
	$("#confirmEvsFinalGrade",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewEvsResult.QUEDINGYAOQUEDINGDENGJIFANYINGMA.a'/>",//确定要确定等级反映吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/excuteEvsPro',
					data: { SEQ: '${RESUME_SEQ}',PRO_NAME:'PR_CONFIRM_FINAL_GRADE' },
	  				dataType:"json",
	  				cache: false,
	  				success: function(json){
						DWZ.ajaxDone(json);
						$("#viewEvsResultForm",navTab.getCurrentPanel()).submit();
		  			},
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});

	//保存
	$("#viewEvsResult_Save",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");
				jsonData += ' "FINAL_GRADE": "' + $("#FINAL_GRADE_" + index,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "FINAL_AFFIRM_CONTENT": "' + $("#FINAL_AFFIRM_CONTENT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SEQ": "' + $("#viewEvsResult_checkbox_" + index,navTab.getCurrentPanel()).val() + '" ,';
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
					url: '/evs/manage/saveConfirmFinalGrade',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	
	//结束评价
	$("#evsEnd",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewEvsResult.QUEDINGYAOJIESHUMA.a'/>",//确定要结束吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/excuteEvsPro',
					data: { SEQ: '${RESUME_SEQ}',PRO_NAME:'PR_EVS_END' },
	  				dataType:"json",
	  				cache: false,
	  				success: function(json){
						DWZ.ajaxDone(json);
						if(json.statusCode == DWZ.statusCode.ok){
							$("#viewEvsResultForm",navTab.getCurrentPanel()).submit();
						}
		  			},
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	autoCalEvsGradeRat();

	$("#viewEvsResult_ratDisplay",navTab.getCurrentPanel()).click(function(){
		if($("#evsRatPanel",navTab.getCurrentPanel()).css("display") == 'none'){
			$("#evsRatPanel",navTab.getCurrentPanel()).show();
		}else{
			$("#evsRatPanel",navTab.getCurrentPanel()).hide();
		}
	});
	
});

function autoCalEvsGradeRat(){
	//分配率人事
	$('td:[syslong="ratHr"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$('td:[sysEvsGrade="evsGradeHr"]',navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($('#FINAL_GRADE_'+$(object).attr("sysIndex")).val() != undefined){
					cnt = cnt + 1;
				}
			}else{
				if($('#FINAL_GRADE_'+$(object).attr("sysIndex")).val() == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);
		if($(obj).attr("sysGrade") != "empty" && $("#viewEvsResult_ratHr",navTab.getCurrentPanel()).html() != '0'){
			$(obj).parent().find('td:[syslong="cntHr_' + $(obj).attr("sysGrade") + '"]').html(parseInt(cnt * 100/$("#viewEvsResult_ratHr",navTab.getCurrentPanel()).html()));
		}else{
			$(obj).parent().find('td:[syslong="cntHr_' + $(obj).attr("sysGrade") + '"]').html(0);
		}
	});

	//分配率3次
	$('td:[syslong="rat3"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$('td:[sysEvsGrade="evsGrade3"]',navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($(object).attr("spVal") != ''){
					cnt = cnt + 1;
				}
			}else{
				if($(object).attr("spVal") == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);
		if($(obj).attr("sysGrade") != "empty" && $("#viewEvsResult_rat3",navTab.getCurrentPanel()).html() != '0'){
			$(obj).parent().find('td:[syslong="cnt3_' + $(obj).attr("sysGrade") + '"]').html(parseInt(cnt * 100/$("#viewEvsResult_rat3",navTab.getCurrentPanel()).html()));
		}else{
			$(obj).parent().find('td:[syslong="cnt3_' + $(obj).attr("sysGrade") + '"]').html(0);
		}
	});

	//分配率2次
	$('td:[syslong="rat2"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$('td:[sysEvsGrade="evsGrade2"]',navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($(object).attr("spVal") != ''){
					cnt = cnt + 1;
				}
			}else{
				if($(object).attr("spVal") == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);
		if($(obj).attr("sysGrade") != "empty" && $("#viewEvsResult_rat2",navTab.getCurrentPanel()).html() != '0'){
			$(obj).parent().find('td:[syslong="cnt2_' + $(obj).attr("sysGrade") + '"]').html(parseInt(cnt * 100/$("#viewEvsResult_rat2",navTab.getCurrentPanel()).html()));
		}else{
			$(obj).parent().find('td:[syslong="cnt2_' + $(obj).attr("sysGrade") + '"]').html(0);
		}
	});

	//分配率1次
	$('td:[syslong="rat1"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$('td:[sysEvsGrade="evsGrade1"]',navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($(object).attr("spVal") != ''){
					cnt = cnt + 1;
				}
			}else{
				if($(object).attr("spVal") == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);
		if($(obj).attr("sysGrade") != "empty" && $("#viewEvsResult_rat1",navTab.getCurrentPanel()).html() != '0'){
			$(obj).parent().find('td:[syslong="cnt1_' + $(obj).attr("sysGrade") + '"]').html(parseInt(cnt * 100/$("#viewEvsResult_rat1",navTab.getCurrentPanel()).html()));
		}else{
			$(obj).parent().find('td:[syslong="cnt1_' + $(obj).attr("sysGrade") + '"]').html(0);
		}
	});

	var evsAvtivityCnt_00 = 0;
	var evsAvtivityCnt_01 = 0;
	var evsAvtivityCnt_02 = 0;
	
	var evsAvtivityCnt01 = 0;
	var evsAvtivityCnt11 = 0;
	var evsAvtivityCnt21 = 0;
	var evsAvtivityCnt31 = 0;
	var evsAvtivityCnt41 = 0;
	var evsAvtivityCnt51 = 0;
	$('td:[tdType="activity"]',navTab.getCurrentPanel()).each(function(i, obj){
		var val = $(obj).attr("sysActivity");
		if(val == '14015356'){
			evsAvtivityCnt01++;
		}else if(val == '14015357'){
			evsAvtivityCnt11++;
		}else if(val == '14015358'){
			evsAvtivityCnt21++;
		}else if(val == '14015359'){
			evsAvtivityCnt31++;
		}else if(val == '14015363'){
			evsAvtivityCnt41++;
		}else if(val == '14015364'){
			evsAvtivityCnt51++;
		}else if(val == '14015354'){
			evsAvtivityCnt_00++;
		}else if(val == '14015364'){
			evsAvtivityCnt_01++;
		}else if(val == '14015365'){
			evsAvtivityCnt_02++;
		}
	});
	<c:if test="${LoginUser.cpnyId eq 'SST'}">
	$("#evsAvtivityCnt_0",navTab.getCurrentPanel()).html(evsAvtivityCnt_00);
	$("#evsAvtivityCnt_1",navTab.getCurrentPanel()).html(evsAvtivityCnt_01);
	$("#evsAvtivityCnt_2",navTab.getCurrentPanel()).html(evsAvtivityCnt_02);
	</c:if>
	$("#evsAvtivityCnt0",navTab.getCurrentPanel()).html(evsAvtivityCnt01);
	$("#evsAvtivityCnt1",navTab.getCurrentPanel()).html(evsAvtivityCnt11);
	$("#evsAvtivityCnt2",navTab.getCurrentPanel()).html(evsAvtivityCnt21);
	$("#evsAvtivityCnt3",navTab.getCurrentPanel()).html(evsAvtivityCnt31);
	$("#evsAvtivityCnt4",navTab.getCurrentPanel()).html(evsAvtivityCnt41);
	$("#evsAvtivityCnt5",navTab.getCurrentPanel()).html(evsAvtivityCnt51);
}

function openEvsInfoWindow(empIdsStr,personId){
	window.open('/evs/manage/viewConfirmTargetInfoPrint?APPLY_PERSON_ID='+personId+'&EVS_PERSON_ID_BATCH=' + empIdsStr + '&RESUME_SEQ=${RESUME_SEQ}&ACTIVITY=14015359','newwindow','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=595,height=600');
}
</script>
<div class="pageHeader">
	<form id="viewEvsResultForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsResult" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsResultResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
				<tr>
					<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td><spring:message code="ess.infoApply.DEPT"/><!--部门--></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="hr" id="viewEvsResult_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewEvsResult_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
					</td>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_GROUP" name="seach_EVS_GROUP_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_GROUP" selected="${EVS_GROUP}" selectedNm="${EVS_GROUP_NAME}"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent" id="evsRatPanel">
	<div style="width:63%;float:left;">
		<div style="font:bold 14px/20px arial,sans-serif;width:100%;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsResult.JIBENBIAOZHUN.a"/><!--基本 标准--></div>
		<table class="evsList" id="evsListResult1" width="100%">
			<thead>
				<tr>
					<th width="17%" rowspan="2">-</th>
					<th width="8%" rowspan="2"><spring:message code="empsubject.totalCnt"/><!--总人数--></th>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
						<th width="14%" colspan="2">${item.EVS_GRADE_NAME}
							<%-- <c:choose>
								<c:when test="${item.EVS_GRADE_NAME == 'A'}">EX</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'B'}">VG</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'C'}">GD</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'D'}">NI</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'E'}">UX</c:when>
							</c:choose> --%>
						</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
						<th width="7%"><spring:message code="hrm.empinfo.PERSON_NUMBER"/><!--人数--></th>
						<th width="7%"><spring:message code="evs.viewEvsResult.BILI.a"/><!--比例--></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.BIAOZHUNFENPEILV.a"/><!--标准分配率--></td>
					<td style="text-align:center">${objectListSize }</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;"><fmt:formatNumber value="${scoreList[0][item.EVS_GRADE_ORIGINAL] * objectListSize * 0.01}" pattern="0.0"/></td>
				    	<td style="text-align:center;">${scoreList[0][item.EVS_GRADE_ORIGINAL]}</td>
					</c:forEach>
				</tr>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.FENPEILVRENSHI.a"/><!--分配率 - 人事--></td>
					<td style="text-align:center" syslong="ratHr" sysGrade="empty" id="viewEvsResult_ratHr">0</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;" syslong="ratHr" sysGrade="${item.EVS_GRADE_ORIGINAL}"></td>
				    	<td style="text-align:center;" syslong="cntHr_${item.EVS_GRADE_ORIGINAL}"></td>
					</c:forEach>
				</tr>
				<%-- <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.FENPEILVSANCI.a"/><!--分配率 - 3次--></td>
					<td style="text-align:center" syslong="rat3" sysGrade="empty" id="viewEvsResult_rat3">0</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;" syslong="rat3" sysGrade="${item.EVS_GRADE_NAME}"></td>
				    	<td style="text-align:center;" syslong="cnt3_${item.EVS_GRADE_NAME}"></td>
					</c:forEach>
				</tr>
				</c:if> --%>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.FENPEILVLIANGCI.a"/><!--分配率 - 2次--></td>
					<td style="text-align:center" syslong="rat2" sysGrade="empty" id="viewEvsResult_rat2">0</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;" syslong="rat2" sysGrade="${item.EVS_GRADE_ORIGINAL}"></td>
				    	<td style="text-align:center;" syslong="cnt2_${item.EVS_GRADE_ORIGINAL}"></td>
					</c:forEach>
				</tr>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.FENPEILVYICI.a"/><!--分配率 - 1次--></td>
					<td style="text-align:center" syslong="rat1" sysGrade="empty" id="viewEvsResult_rat1">0</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;" syslong="rat1" sysGrade="${item.EVS_GRADE_NAME}"></td>
				    	<td style="text-align:center;" syslong="cnt1_${item.EVS_GRADE_NAME}"></td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
	<div style="width:35%;float:right; margin-left:10px; ">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsResult.GUANLIJINXINGZHUANTAI.a"/><!--管理进行状态--> </div>
		<c:if test="${ACTIVITY ne '4'}">
		<div style="float:right;height:20px;line-height:20px;">
			<a class="w_button" onclick="saveObjectTarget(0)"><span><spring:message code="evs.viewEvsAffirmorSetup.YOUJIAN.a"/><!--邮件--></span></a>
			<a class="w_button" id="evsEnd"><span><spring:message code="evs.viewEvsResult.JIESHUPINGJIA.a"/><!--结束评价--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%">	
			<tr>
				<td class="td_title" style="text-align:right;" width="30%"><spring:message code="evs.viewEvsResult.PINGJIAZHUANGTAI.a"/><!--评价状态--></td>
				<td class="td_type" style="text-align:left;" width="70%">
		 			<ait:SelectSyCodeByCpnyID id="seach_EVS_ACTIVITY" name="seach_EVS_ACTIVITY" parentNo="14015351" limit="all"/>
		 		</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:center" colspan="2">
					<a class="w_button" id="viewEvsResult_print"><span><spring:message code="evs.viewEvsResult.PINGJIABIAOCHAXUNJIDAYIN.a"/><!--评价表查询及打印--></span></a>
					<c:if test="${ACTIVITY ne '4'}">
					<a class="w_button" id="viewEvsResult_changeActivity"><span><spring:message code="evs.viewEvsResult.BIANGENGJINGXINGZHUANGTAI.a"/><!--变更进行状态--></span></a>
					</c:if>
				</td>
			</tr>
		</table>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsResult.PINGJIAJINXINGJIEDUANXIANZHUANG.a"/><!--评价进行阶段现况--></div>
		<table class="evsList" id="evsListResult1" width="100%">
			<thead>
				<tr>
					<th><spring:message code="empsubject.totalCnt"/><!--总人数--></th>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></th>
					<th><spring:message code="evs.viewEvsResult.YICIQUEREN.a"/><!--一次确认--></th>
					<th><spring:message code="evs.viewEvsResult.ERCIQUEREN.a"/><!--二次确认--></th>
					</c:if>
					<th><spring:message code="evs.viewEvsResult.ZIWO.a"/><!--自我--></th>
					<th><spring:message code="evs.viewEvsResult.YICI.a"/><!--一次--></th>
					<th><spring:message code="evs.viewEvsResult.ERCI.a"/><!--二次--></th>
					<%-- <c:if test="${evsType eq 'performance'}">
					<th><spring:message code="evs.viewEvsResult.SANCI.a"/><!--三次--></th>
					</c:if> --%>
					<th><spring:message code="evs.viewEvsResult.WANCHENG.a"/><!--完成--></th>
					<th><spring:message code="ar.viewshift.title.end"/><!--结束--></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="text-align:center" id="evsAvtivityCntSum">${objectListSize }</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td style="text-align:center" id="evsAvtivityCnt_0">0</td>
					<td style="text-align:center" id="evsAvtivityCnt_1">0</td>
					<td style="text-align:center" id="evsAvtivityCnt_2">0</td>
					</c:if>
					<td style="text-align:center" id="evsAvtivityCnt0">0</td>
					<td style="text-align:center" id="evsAvtivityCnt1">0</td>
					<td style="text-align:center" id="evsAvtivityCnt2">0</td>
					<%-- <c:if test="${evsType eq 'performance'}">
					<td style="text-align:center" id="evsAvtivityCnt3">0</td>
					</c:if> --%>
					<td style="text-align:center" id="evsAvtivityCnt4">0</td>
					<td style="text-align:center" id="evsAvtivityCnt5">0</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="pageContent">
		<div style="float:right;height:20px;line-height:20px;">
			<a class="w_button" id="viewEvsResult_Serch"><span><spring:message code="button.search"/><!--查询--></span></a>
			<a class="w_button" href="/evs/manage/evsAffirmTargetImportDemo"> 
				<span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
			</a>
			<a class="w_button" href="/pa/excelImport/importExcelData?&importFunName=/importEvsAffirmTargetExcel?resumeSEQ=${RESUME_SEQ}"  target="dialog" mask="true" width="400" height="200" >
				<span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span>
			</a>
			<c:if test="${ACTIVITY ne '4'}">
			<a class="w_button" id="confirmEvsFinalGrade"><span><spring:message code="evs.viewEvsResult.QUEDINGDENGJIFANYING.a"/><!--确定等级反映--></span></a>
			</c:if>
			<a class="w_button" id="viewEvsResult_Save"><span><spring:message code="ess.viewOvertimeInfo.baocun"/><!--保存--></span></a>
			<a class="w_button" onclick="downloadExcel('viewEvsResultForm','/evs/manage/viewEvsResultExport','/evs/manage/viewEvsResult')"><span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到Excel--></span></a>
		</div>
				<div style="font:bold 12px/20px arial,sans-serif;;float:left;height:20px;line-height:20px;">Total:${objectListSize}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<table class="evsList" id="evsListResult2" width="2000px;">
					<thead>
						<tr>
							<th width="30px" rowspan="2">No.</th>
							<th width="30px" rowspan="2"><input type="checkbox" class="checkboxCtrl" group="viewEvsResult_checkbox"></th>
							<th width="80px" rowspan="2"><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
							<th width="80px" rowspan="2"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
							<th width="120px" rowspan="2"><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG"/><!--部门名--></th>
							<th width="100px" rowspan="2"><spring:message code="hr.viewCondSql.title.ZHIJI"/><!--职级--></th>
							<th width="80px" rowspan="2"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
							<th width="80px" rowspan="2"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="150px" colspan="3"><spring:message code="evs.viewEvsResult.BENREN.a"/><!--本人--></th>
							<th width="260px" colspan="5"><spring:message code="evs.viewEvsAffirmorSetup.YICIPINGJIAREN.a"/><!--1次评价人--></th>
							<th width="220px" colspan="5"><spring:message code="evs.viewEvsAffirmorSetup.LAINGCIPINGJIAREN.a"/><!--2次评价人--></th>
							<%-- <c:if test="${evsType eq 'performance'}">
							<th width="220px" colspan="4"><spring:message code="evs.viewEvsAffirmorSetup.SANCIPINGJIAREN.a"/><!--3次评价人--></th>
							</c:if> --%>
							<th width="100px" rowspan="2"><spring:message code="evs.viewEvsResult.KAOHEDENGJIRENSHI.a"/><!--考核等级(人事)--></th>
							<th width="80px" rowspan="2"><spring:message code="evs.viewEvsResult.TIAOZHENGYIJIAN.a"/><!--调整意见--></th>
							<th width="230px" rowspan="2"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px" rowspan="2"><spring:message code="org.title.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
						<tr>
							<th width="70px"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="70px"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
							<th width="50px"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></th>
							
							<th width="60px"><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
							<th width="90px"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></th>
							<th width="70px"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="70px"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
							<th width="40px"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></th>
							
							<th width="60px"><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
							<th width="90px"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></th>
							<th width="70px"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="70px"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
							<th width="40px"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></th>
							<%-- <c:if test="${evsType eq 'performance'}">
							<th width="60px"><spring:message code="hrm.empinfo.name"/><!--姓名--></th>
							<th width="90px"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></th>
							<th width="70px"><spring:message code="org.title.status"/><!--状态--></th>
							<th width="40px"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></th>
							</c:if> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${objectList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsResult_checkbox_${i.index}" name="viewEvsResult_checkbox" value="${item.SEQ}"/></td>
								<td><a href="#" style="color:blue;" onclick="openEvsInfoWindow(${item.SEQ},${item.PERSON_ID })">${item.LOCAL_NAME}</a></td>
								<td>${item.EMPID}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center' tdType="activity" sysActivity="${item.ACTIVITY}">${item.ACTIVITY_NAME}</td>
								<td class='td_center'>${item.AFFIRM_FLAG_NAME0}</td>
								<td class='td_center'>${item.EVS_POINT0}</td>
								<td class='td_center'>${item.EVS_GRADE0}
									<%-- <c:choose>
										<c:when test="${item.EVS_GRADE0 == 'A'}">EX</c:when>
										<c:when test="${item.EVS_GRADE0 == 'B'}">VG</c:when>
										<c:when test="${item.EVS_GRADE0 == 'C'}">GD</c:when>
										<c:when test="${item.EVS_GRADE0 == 'D'}">NI</c:when>
										<c:when test="${item.EVS_GRADE0 == 'E'}">UX</c:when>
									</c:choose> --%>
								</td>
								<td>${item.LOCAL_NAME1}</td>
								<td>${item.POST_GRADE_NAME1}</td>
								<td class='td_center'>${item.AFFIRM_FLAG_NAME1}</td>
								<td class='td_center'>${item.EVS_POINT1}</td>
								<td class='td_center' sysEvsGrade="evsGrade1" spVal="${item.EVS_GRADE1}">${item.EVS_GRADE1}
									<%-- <c:choose>
										<c:when test="${item.EVS_GRADE1 == 'A'}">EX</c:when>
										<c:when test="${item.EVS_GRADE1 == 'B'}">VG</c:when>
										<c:when test="${item.EVS_GRADE1 == 'C'}">GD</c:when>
										<c:when test="${item.EVS_GRADE1 == 'D'}">NI</c:when>
										<c:when test="${item.EVS_GRADE1 == 'E'}">UX</c:when>
									</c:choose> --%>
								</td>
								<td>${item.LOCAL_NAME2}</td>
								<td>${item.POST_GRADE_NAME2}</td>
								<td class='td_center'>${item.AFFIRM_FLAG_NAME2}</td>
								<td class='td_center'>${item.EVS_POINT2}</td>
								<td class='td_center' sysEvsGrade="evsGrade2" spVal="${item.EVS_GRADE2}">${item.EVS_GRADE2}
									<%-- <c:choose>
										<c:when test="${item.EVS_GRADE2 == 'A'}">EX</c:when>
										<c:when test="${item.EVS_GRADE2 == 'B'}">VG</c:when>
										<c:when test="${item.EVS_GRADE2 == 'C'}">GD</c:when>
										<c:when test="${item.EVS_GRADE2 == 'D'}">NI</c:when>
										<c:when test="${item.EVS_GRADE2 == 'E'}">UX</c:when>
									</c:choose> --%>
								</td>
								<%-- <c:if test="${evsType eq 'performance'}">
								<td>${item.LOCAL_NAME3}</td>
								<td>${item.POST_GRADE_NAME3}</td>
								<td class='td_center'>${item.AFFIRM_FLAG_NAME3}</td>
								<td class='td_center' sysEvsGrade="evsGrade3" spVal="${item.EVS_GRADE3}">
									<c:choose>
										<c:when test="${item.EVS_GRADE3 == 'A'}">EX</c:when>
										<c:when test="${item.EVS_GRADE3 == 'B'}">VG</c:when>
										<c:when test="${item.EVS_GRADE3 == 'C'}">GD</c:when>
										<c:when test="${item.EVS_GRADE3 == 'D'}">NI</c:when>
										<c:when test="${item.EVS_GRADE3 == 'E'}">UX</c:when>
									</c:choose>
								</td>
								</c:if> --%>
								<%-- <td class='td_center' sysEvsGrade="evsGradeHr" <c:if test="${item.ACTIVITY eq '14015363' }"> id="FINAL_GRADE_${i.index}" sysIndex="${i.index}" sysLog="select" sysValue='${evsGradeSelect }'</c:if> spVal="${item.FINAL_GRADE}"> --%>
								<td class='td_center'sysEvsGrade="evsGradeHr" sysIndex="${i.index}">
									<c:if test="${item.ACTIVITY eq '14015363' }">
										<select id="FINAL_GRADE_${i.index}" onchange='$("#modifyFlag_${i.index}").html("modify");' style="width:70px" >
											<option value=""></option>
											<c:forEach items="${evsGradeSelect}" var="item2" varStatus="j">
												<option value="${item2.CODENAME }" <c:if test="${item2.CODENAME eq item.FINAL_GRADE_OR}">selected="selected"</c:if>>${item2.DES_PAGE}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${item.ACTIVITY ne '14015363' }"> ${item.FINAL_GRADE }
										<%-- <c:choose>
											<c:when test="${item.FINAL_GRADE == 'A' }">EX</c:when>
											<c:when test="${item.FINAL_GRADE == 'B' }">VG</c:when>
											<c:when test="${item.FINAL_GRADE == 'C' }">GD</c:when>
											<c:when test="${item.FINAL_GRADE == 'D' }">NI</c:when>
											<c:when test="${item.FINAL_GRADE == 'E' }">UX</c:when>
										</c:choose> --%>
									</c:if>
								</td>
								<td<c:if test="${item.ACTIVITY eq '14015363' }"> id="FINAL_AFFIRM_CONTENT_${i.index}" sysIndex="${i.index}" sysLog="text"</c:if>>${item.FINAL_AFFIRM_CONTENT}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<c:if test="${item.ACTIVITY eq '14015363' }"><div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
