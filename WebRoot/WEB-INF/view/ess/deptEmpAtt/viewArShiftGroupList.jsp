<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
     $("#viewArShiftGroupForm_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewArShiftGroupForm",navTab.getCurrentPanel()).submit();
	   });
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 350,
            "scrollX": false,
            "orderClasses": false,
            "oLanguage": {
                //正在加载中......
                "sProcessing": "<spring:message code='ess.message.loading'/>",
                //查询不到相关数据！
                "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",
                //表中无数据存在！
                "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",
                //快速筛选
                "sSearch": "<spring:message code='ess.message.rapid_screening'/>"
            } //多语言配置
		});
});
	function doAddArShiftGroupInfoAjax() {
		var $form = $("#shiftGroupDataForm", navTab.getCurrentPanel());
		if (!$form.valid()) {
			return false;
		}
		var checked = false;
		$("input[name='c1']", navTab.getCurrentPanel()).each(function(i, obj) {
			if (obj.checked) {
				checked = true;
			}
		});
		//var ids= document.getElementsByName("c1");

		if (!checked) {
			alertMsg
					.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
			return false;
		}
		
		alertMsg.confirm("<spring:message code="alert.message.pa.insurance.confirmSubmit"/>",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
					data : $form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: doAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});

		/* if (confirm('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')) {
			$.ajax({
				type : form.method || 'POST',
				url : $form.attr("action"),
				data : $form.serializeArray(),
				dataType : "json",
				cache : false,
				success : ajaxDoneWithForm,
				error : DWZ.ajaxError
			});
		}
		return false; */
	}
	function searchPop_ess3404(flag) {
		var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
				.val()));
		var refreshUrl = '/ess/deptEmpAtt/viewArShiftGroupList?firstView=1';
		var refreshMenuCode = 'ess3404';
		//班组变更
		var refreshMenuName = encodeURI(encodeURI('<spring:message code="ar.viewArShiftGroupList.BANZUBIANGENG.b"/>'));
		//$('#searchPop',navTab.getCurrent())
		$("#searchPop_ess3404", navTab.getCurrentPanel())
				.attr(
						'href',
						'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
								+ name
								+ '&refreshUrl='
								+ refreshUrl
								+ '&refreshMenuCode='
								+ refreshMenuCode
								+ '&refreshMenuName=' + refreshMenuName);
		if (flag == 'onkeyup')
			$("#searchPop_ess3404", navTab.getCurrentPanel()).click();
	}
	function fillItem_ess3404(){
		if($('#BAT_SHIFT_NO_ess3404', navTab.getCurrentPanel()).val() ==''&& $('#BAT_START_DATE_ess3404', navTab.getCurrentPanel()).val()==''){
			alert('<spring:message code="ar.viewArShiftGroupList.QINGXUANZEFANYINGSHUJU.b"/>');//请选择用什么数据来全部反映
		}else{
			$("input[name='c1']", navTab.getCurrentPanel()).each(function(i, obj) {
				if (obj.checked) {
					if($('#BAT_SHIFT_NO_ess3404', navTab.getCurrentPanel()).val() !=''){
						$("#SHIFT_NO_"+obj.value+" option[value='"+$('#BAT_SHIFT_NO_ess3404', navTab.getCurrentPanel()).val()+"']").attr("selected", true);
					}
					if($('#BAT_START_DATE_ess3404', navTab.getCurrentPanel()).val() !=''){
						$("#START_DATE_"+obj.value, navTab.getCurrentPanel()).val($('#BAT_START_DATE_ess3404', navTab.getCurrentPanel()).val());
					}
				}
			});
		}
	}
	function downloadExl(url){
		$('#viewArShiftGroupForm').attr("action",url) ;
		$('#viewArShiftGroupForm').attr("onsubmit",'') ;
		$('#viewArShiftGroupForm').submit() ;
		$('#viewArShiftGroupForm').attr("action",'/ess/deptEmpAtt/viewArShiftGroupList') ;
		$('#viewArShiftGroupForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
	function batchArShiftGroupChangeColor(obj,indexId){
		$(obj).attr('class').indexOf('selected')!=-1?$(obj).removeClass('selected'):$(obj).addClass('selected');
		if($('#c1xp'+indexId,navTab.getCurrentPanel()).attr('checked')=='checked')
			$(obj).addClass('selected');
	}
	function deleteArShiftGroupInfo(){
		var $form = $("#shiftGroupDataForm", navTab.getCurrentPanel());
		if (!$form.valid()) {
			return false;
		}
		var checked = false;
		$("input[name='c1']", navTab.getCurrentPanel()).each(function(i, obj) {
			if (obj.checked) {
				checked = true;
			}
		});

		if (!checked) {
			alertMsg
					.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>');
			return false;
		}
		
		alertMsg.confirm("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b'/>",//确定要批量取消吗?
	  		  	{okCall:function(){
	  		  	$.ajax({
					type:'post',
					url:'/ess/deptEmpAtt/delArShiftGroupInfo',
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#viewArShiftGroupForm"));
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	}  ,
					error: DWZ.ajaxError
				});
	  	}});
	}
</script>
<div class="pageHeader">
	<form id="viewArShiftGroupForm" onsubmit="return navTabSearch(this);"
		action="/ess/deptEmpAtt/viewArShiftGroupList" method="post">
		<input type="hidden" value="ar" name="limit" />
		<input type="hidden" value="1" name="firstView" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
				</td>
				<td><input type="text" name="seach_KEY" id="seach_KEY"
					value="${KEY}"
					onkeydown="javascript:if(event.keyCode == 13)searchPop_ess3404('onkeyup');" />
				</td>
				<%-- <td><a class="btnLook" id="searchPop_ess3404"
					onclick="searchPop_ess3404()" href="#" lookupGroup="person"> </a></td>
				<td>${empInfoShow }
				</td> --%>
				<td>
					<!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
				</td>
				<td>
					<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
				</td>
				<%-- <td><ait:deptList name="seach_DEPTNO"
						cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewDeptPersonalInfoList_seachDept" /> <ait:deptTreeIcon
						name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewDeptPersonalInfoList_seachDept" selected="${DEPTNO}" /></td> --%>
			</tr>
			<tr>
				<td><!--开始日期--><spring:message code="public.title.startDate" /></td>
				<td><input type="text" id="seach_START_DATE"
					name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" 
					value="${START_DATE}" /></td>
				<td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				<td><%-- <ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO"
						parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all"
						selected="${SHIFT_NO }" /> --%>
					<!--<select name="seach_SHIFT_NO" >
			   			<option value=""></option>
						<c:forEach items="${codeList}" var="item">
							<option value="${item.CODE_NO }"  >
							        ${item.CODE_NAME}
							</option>
						</c:forEach>
					</select>-->		
					<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="seach_SHIFT_NO" parentNo="400223" cnpyID="${LoginUser.cpnyId}" selected="${SHIFT_NO}" limit="ALL" />
				</td>
				<td><input name="includeHistoryYN"  value="Y" type="checkbox" <c:if test="${includeHistoryYN == 'Y' }">checked</c:if>/>ALL</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
			       <td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				   <td class="td_type">
				   		<select id="BAT_SHIFT_NO_ess3404" >
				   			<option value=""><!--请选择--><spring:message code="sys.affirm.title.choose" /></option>
							<c:forEach items="${codeList}" var="item">
								<option value="${item.CODE_NO }"  >
								        ${item.CODE_NAME}
								</option>
							</c:forEach>
						</select>
				   </td>
				   <td><!--开始日期--><spring:message code="public.title.startDate" /></td>
				   <td >
						<input type="text" 
								id="BAT_START_DATE_ess3404" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" 
								/>
				   </td >
			    </tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem_ess3404();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent">
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(arShiftGroupList)}</div>
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewArShiftGroupForm_Serch" href="#" ><span><!--查询--><spring:message code="button.search" /></span></a></li>
	<!--<li><a class="buttonActive" onclick="deleteArShiftGroupInfo();" href="#"> <span>删除<spring:message code="button.delete" /></span></a></li>-->
	<li><a class="buttonActive" onclick="doAddArShiftGroupInfoAjax();" href="#"> <span><!--保存--><spring:message code="button.sys.affirm.save" /></span></a></li>
	<li><a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=28')" href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
	 </ul>
</div>
	<form name="shiftGroupDataForm" id="shiftGroupDataForm" method="post"
		action="/ess/deptEmpAtt/addArShiftGroupInfo">
		<table class="list">  
			<thead>
				<tr>
					<th width="2%">NO.</th>
					<th  width="2%"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
					<th width="6%"><!--工号--><spring:message code="public.title.empId" /></th>
					<th width="12%"><!--姓名--><spring:message code="public.title.name" /></th>
					<th width="12%"><!--部门--><spring:message code="org.title.dept" /></th>
					<th width="8%"><!-- 职级 --><spring:message code="hrm.contract.Rank" /></th>
					<th width="8%"><!--变更前班组--><spring:message code="ar.viewArShiftGroupList.BIANGENGQIANBANZU.b" /></th>
					<th width="8%"><!--变更后班组--><spring:message code="ar.viewArShiftGroupList.BIANGENGHOUBANZU.b" /></th>
					<th width="10%"><!--变更开始日期--><spring:message code="ar.viewArShiftGroupList.BIANGENGKAISHIRIQI.b" /></th>
					<th width="12%"><!--原因--><spring:message code="ess.infoApply.Reason" /></th>
					<th width="10%"><!--变更者--><spring:message code="org.title.UPDATED_IP" /></th>
					<th width="10%"><!--变更时间--><spring:message code="org.title.UPDATE_DATE" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${arShiftGroupList}" var="itemData"
					varStatus="status">
					<tr onclick="batchArShiftGroupChangeColor(this,${status.count});">
						<td  class='td_center'>${status.count}</td>
						<td class='td_center'><input type="checkbox" id="c1xp${status.count}" name="c1" value="${itemData.AR_SHIFTGROUP_MANAGE_NO}" />
							<input type="hidden" value="${itemData.PERSON_ID}" name="PERSON_ID_${itemData.AR_SHIFTGROUP_MANAGE_NO}" /></td>
						<td  class='td_center'>${itemData.EMPID}</td>
						<td  class='td_center'>${itemData.LOCAL_NAME}</td>
						<td  class='td_center'>${itemData.DEPTNAME}</td>
						<td  class='td_center'>${itemData.POST_GRADE}</td>
						<td  class='td_center'>${itemData.BEFOR_SHIFT_NO}</td>
						<td  class='td_center'>
							<select name="SHIFT_NO_${itemData.AR_SHIFTGROUP_MANAGE_NO}" id="SHIFT_NO_${itemData.AR_SHIFTGROUP_MANAGE_NO}" 
								onchange="$('#c1xp${status.count}').attr('checked','checked');
												batchArShiftGroupChangeColor(this.parentElement.parentElement,${status.count});">
								<c:forEach items="${codeList}" var="item">
									<option value="${item.CODE_NO }" 
										<c:if test="${item.CODE_NO eq itemData.SHIFT_NO}">selected</c:if>
											>
									        ${item.CODE_NAME}
									</option>
								</c:forEach>
							</select>
							<%-- <ait:SelectSyCodeByCpnyID name="SHIFT_NO_${itemData.AR_SHIFTGROUP_MANAGE_NO}"
								parentNo="400223" cnpyID="${LoginUser.cpnyId}" 
								selected="${itemData.SHIFT_NO }" /> --%>
							<input type="hidden" value="${itemData.SHIFT_NO}" name="BEFOR_SHIFT_NO_${itemData.AR_SHIFTGROUP_MANAGE_NO}" />
						</td>
						<td  class='td_center'>
							<input type="text" id="START_DATE_${itemData.AR_SHIFTGROUP_MANAGE_NO}"
								name="START_DATE_${itemData.AR_SHIFTGROUP_MANAGE_NO}" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" 
								value="${itemData.START_DATE}" size="16" />
						</td>
						<td  class='td_center'><input name="REMARK_${itemData.AR_SHIFTGROUP_MANAGE_NO}"
							type="text" maxlength="200" style="text-align: left;"
							value="${itemData.REMARK}" /></td>
						<td  class='td_center'>${itemData.CREATED_BY}</td>
						<td  class='td_center'>${itemData.CREATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>