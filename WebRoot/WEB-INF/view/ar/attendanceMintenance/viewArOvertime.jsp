<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">

function locked(seq){
 
      document.getElementById("LOCK_YN_"+seq).value='Y';
	  document.getElementById("c1_"+seq).checked=true;
	 
}
function locked2(seq,obj)
{
  document.getElementById("c1_"+seq).checked=true;
 }
function checkNum(value){
	if(value == ''){
		//值不能为空!
		alert("<spring:message code='ar.alert.message.viewardetail.valuenotnull'/>");
	}
	if(isNaN(value)){
		//值必须为数字!
		alert("<spring:message code='ar.alert.message.viewardetail.numbervalue'/>");
	}
}

//未完
function f_viewardetail_add(form, callback) {

	//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
		    
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
		return;
	}
    if(checked){
         var issame = 0;
		 	$.each($("input[name='c1']"),function(i, obj) {
		    if (obj.checked) {
		         var newitemno =  $("#ITEM_NO_"+i).val();
		         var olditemno =  $("#OLD_ITEM_NO_"+i).val();
		        // alert(newitemno);
		        // alert(olditemno);
		         if(newitemno == olditemno)
		         {
		         issame++;
		         }
		     
		       }
		    });
		// alert(issame);    
	   if (issame != 0  ){   
		alert("请添加区别于原项目的考勤项目!");
		return;
		}
	}
	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+i).val() + '" ,';
			jsonData += ' "LOCK_YN": "' + $("#LOCK_YN_"+i).val()  + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+i).val()  + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+i).val()  + '" ,';
			jsonData += ' "EMP_ID": "' + $("#EMP_ID_"+i).val()  + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val() + '",';
		    jsonData += ' "REMARK": "' + $("#REMARK_"+i).val()  + '"';
			jsonData += '}';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要添加的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
		return;
	}
	var $form = $(form);

//	if (!$form.valid()) {
//		return;
//	}

	//确定要提交吗？
	
 	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
 		$.ajax({
 			type: 'POST',
 			url:'/ar/attendanceMintenance/addArDetailInfo',
 			data:[{ name: 'jsonData', value: jsonData }],
 			dataType:"json",
 			cache: false,
 			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					//navTabSearch("searchArDetailForm");
					//alertMsg.correct(data.message);
					  $("#seach_condition").val(data.condition);
					   
					  $("#submitClick_ar0201").click();
					// navTabSearch("searchArDetailForm");
					alertMsg.correct(data.message);
				}else{
					navTabSearch("searchArDetailForm");
					alertMsg.error(data.message);
				}   
	   	 	}  ,
 			error: DWZ.ajaxError
 		});
		
 	}
}

function f_viewardetail_update(form, callback) {
  
	//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行修改操作!
		alert("<spring:message code='ar.alert.message.viewardetail.choosetoupdate'/>");
		return;
	}

	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+i).val()  + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val()  + '" ,';
			//jsonData += ' "LOCK_YN": "' + $("#LOCK_YN_"+i).val()  + '" ,';
			jsonData += ' "SHIFT_NO": "' + $("#shiftNO_"+i).val()  + '" ,';
			jsonData += ' "FROM_TIME": "' + $("#FROMTIME_"+i).val()  + '" ,';
			jsonData += ' "TO_TIME": "' + $("#TOTIME_"+i).val()  + '" ,';
			jsonData += ' "REASON": "' + $("#REASON_"+i).val()  + '" ,';
			jsonData += ' "ALLOWANCE": "' + $("#ALLOWANCE_"+i).val()  + '" ,';
			jsonData += ' "REASON_OTHER": "' + $("#REASON_OTHER_"+i).val()  + '" ,';
			jsonData += ' "AFFIRM_FLAG": "' + $("#AFFIRM_FLAG_"+i).val()  + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+i).val()  + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+i).val()  + '" ,';
			jsonData += ' "LOCAL_NAME": "' + $("#LOCAL_NAME_"+i).val()  + '" ,';
			jsonData += ' "OT_TYPE_CODE": "' + $("#AR_APPLY_TYPE_"+i).val()  + '" ,';
			jsonData += ' "AFFIRM_FLAG_DEFAULT": "' + $("#affirmFlagDefault_"+i).val()  + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val()  + '",';
			jsonData += ' "CREATED_IP": "' + $("#CREATED_IP_"+i).val()  + '" ,';
			jsonData += ' "CREATE_DATE": "' + $("#CREATE_DATE_"+i).val()  + '" ,';
			jsonData += ' "CREATED_BY": "' + $("#CREATED_BY_"+i).val()  + '" ,';
			jsonData += ' "APPLY_NO": "' + $("#APPLY_NO_"+i).val()  + '" ,';
			var defaultShiftNO=$("#shifitDefault_"+i).val();
			if(defaultShiftNO != $("#shiftNO_"+i).val()){
				jsonData += ' "DEFAULTSHIFTNO": "1",';
			}else{
				jsonData += ' "DEFAULTSHIFTNO": "0",';
			}
			jsonData += ' "REMARK": "' + $("#REMARK_"+i).val()  + '"';				
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要修改的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetomodify'/>");
		return;
	}
	
	var $form = $(form);

//	if (!$form.valid()) {
//		return;
//	}

	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url:'/ar/attendanceMintenance/updateArDetailInfo',
			data:[{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					// navTabSearch("searchArDetailForm");
					$("input[name='c1']").each(function(){ if(this.checked){ this.checked=false; } });
					alertMsg.correct(data.message);
				}else{
					alertMsg.error(data.message);
				}   
   	 		}  ,
			error: DWZ.ajaxError
		});
		
	}
}

function f_viewardetail_delete(form, callback) {

	//选中检查
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.chooseperson'/>");
		return;
	}

	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+i).val()  + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val()  + '"';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要删除的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/>");
		return;
	}
	
	var $form = $(form);

//	if (!$form.valid()) {
//		return;
//	}

	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url:'/ar/attendanceMintenance/deleteArDetailInfo',
			data:[{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
			   	//navTabSearch("searchArDetailForm");
			   		document.getElementById("submitClick_ar0201").click();
					$("input[name='c1']").each(function(){ if(this.checked){ this.checked=false; } });
				alertMsg.correct(data.message);
			}else{
				alertMsg.error(data.message);
			}   
	 		}  ,
		error: DWZ.ajaxError
		});
		
	}
}

function exportArDetailExcel(obj){

	var condition = document.searchArDetailForm.seach_condition.value;
	var deptID = document.searchArDetailForm.seach_deptID.value;
	var sDate = document.searchArDetailForm.seach_sDate.value;
	var eDate = document.searchArDetailForm.seach_eDate.value;
	var itemNo = document.searchArDetailForm.seach_itemNo.value;
	var status = document.searchArDetailForm.seach_status.value;

	document.getElementById("exportArDetailExcel").href="/pa/excelExport/exportArDetailExcel?condition="+condition
				+"&deptID="+deptID+"&sDate="+sDate+"&eDate="+eDate+"&itemNo="+itemNo+"&status="+status;
	document.getElementById("exportArDetailExcel").click();

}


function excelimport_ar0201(){
	$("#importExcelDialog_ar0201").attr('href','/pa/excelImport/importExcelData?importFunName=/importArDetailTemp');
	$("#importExcelDialog_ar0201").click();
}

function calength(form,index){
	var $form=null;
	  if($('#'+form).length>0)
	    $form=$('#'+form);
	  else
	     $form = $(form);
     	var changeFlag=0;
	    var FROM_TIME =$("#FROMTIME_"+index).val();
	    var TO_TIME=$("#TOTIME_"+index).val();
	    var AR_DATE_STR= $("#AR_DATE_STR_"+index).val();
	    var firstTime=$("#firstTime_"+index).val();
	    var lastTime=$("#lastTime_"+index).val();
	    var shiftNo= $("#shiftNO_"+index).val();
	  //json传值
		var jsonData = '[';		
		jsonData += '{';
		jsonData += ' "FROM_TIME": "' + FROM_TIME + '" ,';
		jsonData += ' "TO_TIME": "' + TO_TIME + '" ,';
		jsonData += ' "AR_DATE_STR": "' + AR_DATE_STR  + '" ,';
		jsonData += ' "SHIFT_NO": "' + shiftNo  + '" ,';
		jsonData += ' "FIRST_TIME": "' + firstTime  + '" ,';
		jsonData += ' "LAST_TIME": "' +lastTime + '",';
	    jsonData += ' "REMARK": "' + $("#REMARK_"+index).val()  + '"';
		jsonData += '}'; 
		jsonData += ']';
	 //   $form.attr("action","url:'/ar/attendanceMintenance/countArDetailLengthInfo");
	    	$.ajax({
	    	    type: form.method || 'POST',
	    	    url:'/ar/attendanceMintenance/countArDetailLengthInfo',
	    	    data:[{ name: 'jsonData', value: jsonData }],
	    	    dataType:"json",
	    	    cache: false,
	    	    success: function(data){ //请求成功后处理函数。
	    			if(data.statusCode=="200"){
	    				// navTabSearch("searchArDetailForm");
	    				//$("input[name='c1']").each(function(){ if(this.checked){ this.checked=false; } });
	    				//alertMsg.correct(data.message);
	    				$("#QUANTITY_"+index).val(data.message);
	    			}else{
	    				alertMsg.error(data.message);
	    			}   
	    	 		}  ,
	    		error: DWZ.ajaxError
	    	});
	    
	    
}
</script>
<a id="importExcelDialog_ar0201"  href="#" target="dialog" mask="true"></a>
<%--<a id="importExcel_ar0201" href="#" target="navTab" mask="true"><span style="display:none;">明细维护导入结果</span></a>--%>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="/ar/attendanceMintenance/viewArOvertime" method="post" name="searchArOverTimeForm" id="searchArOverTimeForm"
			rel="pagerForm">
			<input type="hidden" name="seach_MANAGER_TYPE" value="ATTOVERTIME"/>
			<div class="searchBar">
				<table class="searchContent" border="0" width="100%">
					<tr>
						<td>
							<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>
						</td>
						<td>
							<input name="seach_condition" type="text" value="${condition}" id="seach_condition"/>
							${seach_condition}
							<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
						</td>
						<td>
							<!-- 部门 --><spring:message code="public.title.deptName"/>
						</td>
						<td>
							<ait:deptList name="seach_deptID" limit="ar"  id="viewArDetail_seachDept"/>
							<ait:deptTreeIcon name="seach_deptID" limit="ar" id="viewArDetail_seachDept" selected="${deptID}"/>
						</td>
						<td>
							<!-- 开始日期 --><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/></td>
						<td><input type="text" name="seach_ar_date_str" class="date" 
									yearstart="-20" yearend="20" readonly="true" value="${ar_date_str}"/><a class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
					</tr>	
					<tr>
						
						<td>
							<!-- 考勤区分 --><spring:message code="ar.viewardetail.title.kaoqinqufen"/>
						</td>
						<td>
							<select name="seach_itemNo" class="select">
									<option value="">
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<c:forEach items="${getItemList}" var="item">
										<option value="${item.ITEM_NO}" 
											<c:if test="${item.ITEM_NO eq itemNo}">selected</c:if>>
											${item.ITEM_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
					<td>
								<!-- 状态 --><spring:message code="ar.viewcycle.title.zhuangtai"/>
						</td>
						<td>
								<select name="seach_status" class="select">
									<option value="" <c:if test="${status ne 'Y' && status ne 'N'}">selected</c:if>>
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<option value="Y" <c:if test="${status eq 'Y'}">selected</c:if>>
										<!-- 锁定 --><spring:message code="ar.viewardetail.title.lock"/>
									</option>
									<option value="N" <c:if test="${status eq 'N'}">selected</c:if>>
										<!-- 开放 --><spring:message code="ar.viewardetail.title.release"/>
									</option>
								</select>
						</td>
						<td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					</tr>
					
				</table>
				<div class="subBar">
								<ul>
									<li>
										<div class="buttonActive">
											<div class="buttonContent">
												<button type="submit" id="submitClick_ar0201">
													<!-- 查询 --><spring:message code="button.search"/>
												</button>
											</div>
										</div>
									</li>
									   	
								</ul>
							</div>
			</div>
		</form>
	</div>
	<div class="pageContent">

	<!----><c:set value="0" var="add_target_exit"/>
		<c:set value="javascript:f_add(this, navTabAjaxDone);" var="add_Url" />
		<c:set value="0" var="delete_target_exit"/>
		<c:set value="javascript:f_delete(this, navTabAjaxDone);" var="delete_Url" />
		<c:set value="0" var="edit_target_exit"/>
		<c:set value="javascript:f_update(this, navTabAjaxDone);" var="edit_Url" />
		
		<div class="formBar">
		<ul class="toolBar">
			<li>
										<a id="exportArDetailExcel" class="buttonActive"
											onclick="exportArDetailExcel(this);"><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
										</a>
									</li>
									<%-- <c:if test="${toolbarInfo.INSERTR eq '1'}">
									<li>
										<a class="buttonActive"
											href="/pa/excelImport/importInsuranceInputItemData?
											&importFunName=importArDetailExcel" target="dialog" mask="true" width="400" height="200" ><span> EXCEL导入 <spring:message code="ar.addempshift.title.excelimport"/>(旧模板)</span>
										</a>
									</li>	
									
									<li>
										<a class="buttonActive" onclick="excelimport_ar0201();">
											<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 -->(带验证)</span>
										</a>
									</li>
									</c:if> --%> 
									<c:if test="${toolbarInfo.INSERTR eq '1'}">
									<li>
										<a class="buttonActive"
											href="/pa/excelExport/exportArDetailDataExcelModel?CPNY_ID=${CPNY_ID}"><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
										</a>
									</li>
									</c:if>
			<c:if test="${toolbarInfo.INSERTR eq '1'}"><li><a class="add" onclick="f_viewardetail_add('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 添加 --><spring:message code="button.add" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.DELETER eq '1'}"><li><a class="delete" onclick="f_viewardetail_delete('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 删除 --><spring:message code="button.delete" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.UPDATER eq '1'}"><li><a href="#" class="edit" onclick="f_viewardetail_update('updateArDetailForm',DWZ.ajaxDone);" ><span><!-- 修改<spring:message code="button.update" /> -->保存</span></a></li></c:if>
			
		</ul>
		</div>                              
		<form name="updateArDetailForm" id="updateArDetailForm" method="post" action="/ar/attendanceMintenance/updateOrAddArDetail"
	 		 onsubmit="return cancelAgentTransValidateCallback(this, navTabAjaxDone);">
			<table    class="table" width="180%" layoutH="231">
				<thead>
					<tr>
					<th width="5%" align="center" >
							No
						</th>
						<th width="5%" align="center" >
							<input type="checkbox" class="checkboxCtrl" group="c1">
						</th>
						 <th width="5%">
							<!-- 工号  --><spring:message code="public.title.empId"/>
						</th>	
					    <th width="5%">
							<!-- 姓名 --><spring:message code="public.title.name"/>
						</th>	
						<th>
							<!-- 部门 --><spring:message code="public.title.deptName"/>
						</th>
						<th  >
							职级
						</th>						 
						<th width="5%">
							<!-- 日期 --><spring:message code="ar.viewardetail.title.date"/>
						</th>
						 <th width="3%">
							星期
						</th>
						<th width="7%">
							考勤
						</th>
				       
						<th width="8%">
							<!-- 班次 --><spring:message code="ar.viewCompanyCalendar.title.banci"/>
						</th>
						<th width="8%">
							工作时间
						</th>
						<th width="6%">
							进门
						</th>
						<th width="6%">
							出门
						</th>
						<th width="6%" >
							开始
						</th>
						<th width="6%" >
							结束
						</th>
						 <th width="5%">
							加班时间
						</th>
						 <th width="10%">
							原因
						</th>
						 <th width="10%">
							其他原因
						</th>
						 <th   nowrap>
							中夜班津贴
						</th>
						<th   nowrap>
							审批状态
						</th>
						<th   width="5%">
							加班合计
						</th>
						<th   width="8%">
							平时
						</th>
						<th   width="8%">
							周末
						</th>
						<th   width="8%">
							法定节假日
						</th>
						<th   nowrap>
							综合加班
						</th>
						<th   nowrap>
							月平均
						</th>
						<th   nowrap>
							输入者
						</th>
						<th   nowrap>
							入力时间
						</th>
						 
						<th  nowrap>
							最终修正者
						</th>
						<th  nowrap>
							修正时间
						</th>
						 
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${arDetailList}" var="list" varStatus="i">
	
						<tr>
						  <td nowrap>
								${i.count}
							</td>
							<td nowrap>
								 <input type="checkbox" id="c1_${i.index}" name="c1" value="${list.PK_NO}">  
								 <input type="hidden" id="CREATE_DATE_${i.index}" name="CREATE_DATE_${i.index}" value="${list.CREATE_DATE}"/>
								 <input type="hidden" id="CREATED_IP_${i.index}" name="CREATED_IP_${i.index}" value="${list.PERSON_ID}"/>
								 <input type="hidden" id="CREATED_BY_${i.index}" name="CREATED_IP_${i.index}" value="${list.CREATED_BY}"/>
								  <input type="hidden" id="APPLY_NO_${i.index}" name="APPLY_NO_${i.index}" value="${list.APPLY_NO}"/>
							</td>
							<td nowrap>
								<input type="hidden" id="PERSON_ID_${i.index}" name="PERSON_ID_${i.index}" value="${list.PERSON_ID}"/>
							    ${list.EMPID} 
							</td>
						    <td nowrap>
							   <input type="hidden" id="EMP_ID_${i.index}" name="EMP_ID_${i.index}" value="${list.EMPID}"/>
							   <input type="hidden" id="LOCAL_NAME_${i.index}" name="LOCAL_NAME_${i.index}" value="${list.LOCAL_NAME}"/>
							    ${list.LOCAL_NAME}
							</td>	
							<td  nowrap>
								${list.DEPTNAME}
								<input type="hidden" id="dateType_${i.index}" name="dateType_${i.index}" value="${list.DATE_TYPE}"/>
							</td>
							<td  nowrap>
								${list.POST_GRADE_NAME}
							</td>
							<td nowrap >
								<input type="hidden" id="AR_DATE_STR_${i.index}" name="AR_DATE_STR_${i.index}" value="${list.AR_DATE_STR}"/>
								${list.AR_DATE_STR}
							</td>
							<td  nowrap>
								 
								${list.IWEEK}
							</td>
							<td  nowrap>
								 
								<select name="ITEM_NO_${i.index}" id="ITEM_NO_${i.index}"  onchange="javascript:shiftChange('${i.index}');">
										<c:forEach items="${getItemList}" var="item">
											<option value="${item.ITEM_NO}" 
												<c:if test="${item.ITEM_NO eq list.ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
											</option>
										</c:forEach>
									</select>
									<input type="hidden" id="AR_APPLY_TYPE_${i.index}" name="AR_APPLY_TYPE_${i.index}" value="${list.AR_APPLY_TYPE}"/>
								
							</td>
				 			
							<td nowrap>
							 <select id="shiftNO_${i.index}" name="shiftNO_${i.index}">
								<c:forEach items="${shiftList}" var="empShift" >
								<option value="${empShift.SHIFT_NO}"<c:if test="${empShift.SHIFT_NO == list.SHIFT_NO  }">selected</c:if> >${empShift.SHIFT_NAME}</option>
								</c:forEach>
								</</select>
								<input type="hidden" name="shifitDefault_${i.index}" id="shifitDefault_${i.index}" value="${list.SHIFT_NO}">
							</td> 
							 <td nowrap>
							 工作时间
							</td>
							<td  nowrap> 
								 
									${list.FIRST_TIME }
									<input type="hidden" name="firstTime_${i.index}" id="firstTime_${i.index}" value="${list.FIRST_TIME}">
							 	 
							</td>
							<td  nowrap> 
								 
									${list.LAST_TIME }
									<input type="hidden" name="lastTime_${i.index}" id="lastTime_${i.index}" value="${list.LAST_TIME}">
							 	 
							</td>
							<td  nowrap> 
								 
								<input name="FROMTIME_${i.index}" id="FROMTIME_${i.index}" type="text" value="${list.FROMTIME}"  
										  /><%-- onblur="checkNum(this.value)" --%>
							 	 
							</td>
							<td  nowrap> 
								 <input name="TOTIME_${i.index}" id="TOTIME_${i.index}" type="text" value="${list.TOTIME}"  
										 onChange="javascript:calength('updateArDetailForm','${i.index}');" />
									
							 	 
							</td>
							 
							<td  nowrap>
								
									<input name="QUANTITY_${i.index}" id="QUANTITY_${i.index}" type="text" value="${list.QUANTITY}"  readOnly/>
								
							</td>
							<td  nowrap>
								
									<input name="REASON_${i.index}" id="REASON_${i.index}" type="text" value="${list.REASON}"  />
								
							</td>
							<td  nowrap>
								
									<input name="REASON_OTHER_${i.index}" id="REASON_OTHER_${i.index}" type="text" value="${list.REASON_OTHER}"  />
								
							</td>
							<td nowrap >
								
									<input name="ALLOWANCE_${i.index}" id="ALLOWANCE_${i.index}" type="text" value="${list.ALLOWANCE}"  />
								
							</td>
							<td nowrap>
								
										<ait:SelectSyCodeByCpnyID name="AFFIRM_FLAG_${i.index}" id="AFFIRM_FLAG_${i.index}" parentNo="14014304"
							cnpyID="${LoginUser.cpnyId}" selected="${list.AFFIRM_FLAG}" />
							<input type="hidden" name="affirmFlagDefault_${i.index}" id="affirmFlagDefault_${i.index}" value="${list.AFFIRM_FLAG}">
								
							</td>
							<td nowrap>
							${list.OT_TOTAIL }
						</td>
						<td nowrap>
							${list.WEEKDAY_OT_TOTAIL }
						</td>
						<td nowrap>
							${list.WEEKEND_OT_TOTAIL }
						</td>
						<td nowrap>
							${list.HOILDAY_OT_TOTAIL}
						</td>
						<td>
							${list.COMPRE_OT_TOTAIL}
						</td>
						<td nowrap>
							月平局
						</td>
						<td nowrap>
							${list.CREATED_BY}
						</td>
						<td nowrap>
							${list.CREATE_DATE}
						</td>
						 
						<td nowrap>
							${list.UPDATED_BY}
						</td>
						<td nowrap>
							${list.UPDATE_DATE}
						<td>
							
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<c:set value="/ar/attendanceMintenance/viewArOvertime" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>