<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsParamPanel_currentIndex").val('${currentIndex}');

	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var format = $(this).attr("format");
    		if( typeof(format) != "undefined" && format != ""){
    			if(isNaN(val)){
    	        	$(this).html(0);
    			}else{
    	        	$(this).html(val);
    			}
    			this.editing   = false;
    		}else{
	        	$(this).html(val);
				this.editing = false;
    		}
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
		}
	});
	$('.orderList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$("#viewEvsParamInfoList_table_${currentIndex }",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
		"bLengthChange": true,  //按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 340,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "fixedColumns":false,
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
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
	});
	//保存1
	$("#viewEvsParamInfoList_save${currentIndex }",navTab.getCurrentPanel()).click(function(){
		var currentIndex = '${currentIndex }';
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag${currentIndex }']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");
				if(currentIndex == '0'){
					
					var IS_INCLUDE = 0;
					if($("#viewEvsParamPanel_checkbox${currentIndex }_" + index ,navTab.getCurrentPanel()).attr("checked") == "checked" ){
						var IS_INCLUDE = 1;
					}
					jsonData += ' "EVS_TYPE_NAME": "' + $("#EVS_TYPE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "EVS_GRADE_NAME": "' + $("#EVS_GRADE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).val() + '" ,';
					jsonData += ' "START_SCORE": "' + $("#START_SCORE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "END_SCORE": "' + $("#END_SCORE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "SCORE": "' + $("#SCORE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "REMARK": "' + $("#REMARK_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "IS_INCLUDE": "' + IS_INCLUDE + '" ,';
				}else if(currentIndex == '1'){
					jsonData += ' "CODE_NO": "' + $("#CODE_NO_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "CODE_NAME": "' + $("#CODE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "EVS_SCORE": "' + $("#EVS_SCORE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				}else if(currentIndex == '2'){
					var IS_INCLUDE = 0;
					if($("#viewEvsParamPanel_checkbox${currentIndex }_" + index ).attr("checked") == "checked" ){
						var IS_INCLUDE = 1;
					}
					jsonData += ' "CODE_NO": "' + $("#CODE_NO_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "CODE_NAME": "' + $("#CODE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "FORMULA_NAME": "' + $("#FORMULA_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "EVS_TYPE_NAME": "' + $("#EVS_TYPE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "EVS_GRADE_NAME": "' + $("#EVS_GRADE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).val() + '" ,';
					jsonData += ' "IS_INCLUDE": "' + IS_INCLUDE + '" ,';
				}else if(currentIndex == '3'){
					var IS_INCLUDE = 0;
					if($("#viewEvsParamPanel_checkbox${currentIndex }_" + index ).attr("checked") == "checked" ){
						var IS_INCLUDE = 1;
					}
					jsonData += ' "CODE_NO": "' + $("#CODE_NO_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "CODE_NAME": "' + $("#CODE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "FORMULA_NAME": "' + $("#FORMULA_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "START_STEP_NAME": "' + $("#START_STEP_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "LIST_TYPE": "' + IS_INCLUDE + '" ,';
				}else if(currentIndex == '7'){
					jsonData += ' "EVS_STEP_NAME": "' + $("#EVS_STEP_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "EVS_GROUP_NAME": "' + $("#EVS_GROUP_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "RULE_ID_NAME": "' + $("#RULE_ID_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				}else if(currentIndex == '4' || currentIndex == '5' || currentIndex == '6'){
					jsonData += ' "CODE_NO": "' + $("#CODE_NO_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "CODE_NAME": "' + $("#CODE_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "FORMULA_NAME": "' + $("#FORMULA_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				}
				jsonData += ' "SEQ": "' + $("#SEQ_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
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
					url: '/evs/manage/addParamInfo?currentIndex=${currentIndex }',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});

function deleteParamInfo(currentIndex){
	var currentIndex = '${currentIndex }';
	//获取页面的值
	var jsonData = '[';
	$("input[name='evsParamsbox']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "SEQ": "' + obj.value + '" ,';
			jsonData += ' "currentIndex": "' + currentIndex + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	
	if (jsonData.length == 2) {
		alertMsg.info("<spring:message code='org.title.SELECT_DELETEDATA'/>");//请选择需要删除的数据
		return;
	}
	alertMsg.confirm("<spring:message code='button.delete.sure'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/evs/manage/deleteParamInfo?currentIndex=${currentIndex }',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}

</script>
<c:if test="${currentIndex eq '0'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${gradeListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }" class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="120px"><spring:message code="inct.salesman.classify"/><!--分类--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.XIANGMUPINGJIADENGJIMING.a"/><!--项目评级等级名--></th>
							<th width="120px"><spring:message code="evs.viewEvsParamInfoList.FENBIANLVBAOKUOYUFOU.a"/><!--分辨率包括与否--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.BAOKUOFANWEIKAISHIFENSHU.a"/><!--包括范围开始分数--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.BAOKUOFANWEIMOFENSHU.a"/><!--包括范围末分数--></th>
							<th width="50px"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
							<th width="120px"><spring:message code="hr.viewCompetence.title.QUAL_REMARK"/><!--备注--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${gradeList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsTypeSelect}' id="EVS_TYPE_NAME_${currentIndex }_${i.index}">${item.EVS_TYPE_NAME}</td>
								<%-- <td sysLog="select" sysIndex="${i.index}" sysValue='${evsGradeSelect}' id="EVS_GRADE_NAME_${currentIndex }_${i.index}">${item.EVS_GRADE_NAME} --%>
								<td class='td_center'>
									<select id="EVS_GRADE_NAME_${currentIndex }_${i.index}"  onchange='$("#modifyFlag_${currentIndex }_${i.index}").html("modify");'
									name="EVS_GRADE_NAME_${currentIndex }_${i.index}" style="width:70px" >
										<c:forEach items="${evsGradeSelect}" var="item2" varStatus="j">
											<option value="${item2.CODENAME }" <c:if test="${item2.DES_PAGE eq item.EVS_GRADE_NAME}">selected="selected"</c:if>>${item2.DES_PAGE}</option>
										</c:forEach>
									</select>
								</td>
								<td class='td_center'><input type="checkbox" id="viewEvsParamPanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.IS_INCLUDE eq '1' }">checked="checked"</c:if> onclick='$("#modifyFlag_${currentIndex }_${i.index}").html("modify");'/></td>
								<td sysLog="text" sysIndex="${i.index}" format="number" id="START_SCORE_${currentIndex }_${i.index}" >${item.START_SCORE }</td>
								<td sysLog="text" sysIndex="${i.index}" format="number" id="END_SCORE_${currentIndex }_${i.index}" >${item.END_SCORE}</td>
								<td sysLog="text" sysIndex="${i.index}" format="number" id="SCORE_${currentIndex }_${i.index}" >${item.SCORE}</td>
								<td sysLog="text" sysIndex="${i.index}" id="REMARK_${currentIndex }_${i.index}" >${item.REMARK}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '1'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1000px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.PINGJIADENGJIDAIMA.a"/><!--评价等级代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.XIANGMUPINGJIADENGJIMING.a"/><!--项目评价等级名--></th>
							<th width="80px"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							    <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td sysLog="text" sysIndex="${i.index}" id="EVS_SCORE_${currentIndex }_${i.index}">${item.EVS_SCORE}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '2'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.DUIXIANGLEIXING.a"/><!--对象类型--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.FENPEILVBAOKUOYUFOU.a"/><!--分配率包括与否--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.MORENPINGJIADENGJI.a"/><!--默认评价等级--></th>
							<th width="180px"><spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							    <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsParamPanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.IS_INCLUDE eq '1' }">checked="checked"</c:if> onclick='$("#modifyFlag_${currentIndex }_${i.index}").html("modify");'/></td>
								<%-- <td sysLog="select" sysIndex="${i.index}" sysValue='${evsGradeSelect}' id="EVS_GRADE_NAME_${currentIndex }_${i.index}">${item.EVS_GRADE_NAME} --%>
								<td class='td_center'>
									<select id="EVS_GRADE_NAME_${currentIndex }_${i.index}"  onchange='$("#modifyFlag_${currentIndex }_${i.index}").html("modify");'
										name="EVS_GRADE_NAME_${currentIndex }_${i.index}" style="width:70px" >
										<option value=""></option>
										<c:forEach items="${evsGradeSelect}" var="item2" varStatus="j">
											<option value="${item2.CODENAME }" <c:if test="${item2.CODENAME eq item.EVS_GRADE_NAME}">selected="selected"</c:if>>${item2.DES_PAGE}</option>
										</c:forEach>
									</select>
								</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${formulaSelect}' id="FORMULA_NAME_${currentIndex }_${i.index}">${item.FORMULA_NAME}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '3'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.PINGJIABIAOLEIXING.a"/><!--评价表类型--></th>
							<th width="180px"><spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.CHUSHIZHUANGTAI.a"/><!--初始状态--></th>
							<!-- <th width="80px">List Type</th> -->
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							     <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${formulaSelect}' id="FORMULA_NAME_${currentIndex }_${i.index}">${item.FORMULA_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsStepSelect}' id="START_STEP_NAME_${currentIndex }_${i.index}">${item.START_STEP_NAME}</td>
								<%-- <td class='td_center'><input type="checkbox" id="viewEvsParamPanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.LIST_TYPE eq '1' }">checked="checked"</c:if> onclick='$("#modifyFlag_${currentIndex }_${i.index}").html("modify");'/></td> --%>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '4'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
							<th width="100px"><spring:message code="evs.viewEvsParamInfoList.DUIXIANGFENLEI.a"/><!--对象分类--></th>
							<th width="180px"><spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							    <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${formulaSelect}' id="FORMULA_NAME_${currentIndex }_${i.index}">${item.FORMULA_NAME}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '5'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.PINGJIAZHIYEQUN.a"/><!--评价职业群--></th>
							<th width="180px"><spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							    <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${formulaSelect}' id="FORMULA_NAME_${currentIndex }_${i.index}">${item.FORMULA_NAME}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '6'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${paramListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.ZHIBIAOMING.a"/><!--指标名--></th>
							<th width="180px"><spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paramList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
							    <td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NO_${currentIndex }_${i.index}">${item.CODE_NO}</td>
								<td sysLog="text" sysIndex="${i.index}" id="CODE_NAME_${currentIndex }_${i.index}">${item.CODE_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${formulaSelect}' id="FORMULA_NAME_${currentIndex }_${i.index}">${item.FORMULA_NAME}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '7'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsParamInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&PARAM_TYPE=${PARAM_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsParamInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteParamInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsParamInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${affirmRuleListSize}</div>
				<table id="viewEvsParamInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsParamsbox" /></th>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="evs.viewEvsParamInfoList.PINGJIARENLEIXING.a"/><!--评价人类型--></th>
							<th width="100px"><spring:message code="evs.viewEvsParamInfoList.PINGJIAQUNQUFEN.a"/><!--评价群区分--></th>
							<th width="100px"><spring:message code="evs.viewEvsParamInfoList.GUIZEID.a"/><!--规则ID--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${affirmRuleList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsParamsbox_${i.index}" name="evsParamsbox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsStepSelect}' id="EVS_STEP_NAME_${currentIndex }_${i.index}">${item.EVS_STEP_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsGroupSelect}' id="EVS_GROUP_NAME_${currentIndex }_${i.index}">${item.EVS_GROUP_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${ruleIdSelect}' id="RULE_ID_NAME_${currentIndex }_${i.index}">${item.RULE_ID_NAME}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>