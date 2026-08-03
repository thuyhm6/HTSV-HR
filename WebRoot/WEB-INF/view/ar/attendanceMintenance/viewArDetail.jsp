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
			jsonData += ' "LOCK_YN": "' + $("#LOCK_YN_"+i).val()  + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+i).val()  + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_"+i).val()  + '" ,';
			jsonData += ' "EMP_ID": "' + $("#EMP_ID_"+i).val()  + '" ,';
			jsonData += ' "QUANTITY": "' + $("#QUANTITY_"+i).val()  + '",';
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
</script>
<a id="importExcelDialog_ar0201"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ar0201" href="#" target="navTab" mask="true"><span style="display:none;">部门导入结果</span></a>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="/ar/attendanceMintenance/viewArDetail" method="post" name="searchArDetailForm" id="searchArDetailForm"
			rel="pagerForm">
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
						<td>
						<input type="text" name="seach_sDate" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${sDate }"/>
						</td>
							
						<td>
							<!-- 结束日期 --><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/></td>
						<td style="padding-left: 1px">
						<input type="text" name="seach_eDate" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${eDate }"/>
						</td>
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

	<!--<c:set value="0" var="add_target_exit"/>
		<c:set value="javascript:f_add(this, navTabAjaxDone);" var="add_Url" />
		<c:set value="0" var="delete_target_exit"/>
		<c:set value="javascript:f_delete(this, navTabAjaxDone);" var="delete_Url" />
		<c:set value="0" var="edit_target_exit"/>
		<c:set value="javascript:f_update(this, navTabAjaxDone);" var="edit_Url" />-->
		
		<div class="formBar">
		<ul class="toolBar">
			<li>
										<a id="exportArDetailExcel" class="buttonActive"
											onclick="exportArDetailExcel(this);"><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
										</a>
									</li>
									<c:if test="${toolbarInfo.INSERTR eq '1'}">
									<!--<li>
										<a class="buttonActive"
											href="/pa/excelImport/importInsuranceInputItemData?
											&importFunName=importArDetailExcel" target="dialog" mask="true" width="400" height="200" ><span> EXCEL导入 <spring:message code="ar.addempshift.title.excelimport"/>(旧模板)</span>
										</a>
									</li>	
									-->
									<li>
										<a class="buttonActive" onclick="excelimport_ar0201();">
											<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 -->(带验证)</span>
										</a>
									</li>
									</c:if>
									<c:if test="${toolbarInfo.INSERTR eq '1'}">
									<li>
										<a class="buttonActive"
											href="/pa/excelExport/exportArDetailDataExcelModel?CPNY_ID=${CPNY_ID}"><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
										</a>
									</li>
									</c:if>
			<c:if test="${toolbarInfo.INSERTR eq '1'}"><li><a class="add" onclick="f_viewardetail_add('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 添加 --><spring:message code="button.add" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.DELETER eq '1'}"><li><a class="delete" onclick="f_viewardetail_delete('updateArDetailForm',DWZ.ajaxDone);"><span><!-- 删除 --><spring:message code="button.delete" /></span></a></li></c:if>
			<c:if test="${toolbarInfo.UPDATER eq '1'}"><li><a class="edit" onclick="f_viewardetail_update('updateArDetailForm',DWZ.ajaxDone);" ><span><!-- 修改<spring:message code="button.update" /> -->保存</span></a></li></c:if>
			
		</ul>
		</div>                              
		<form name="updateArDetailForm" id="updateArDetailForm" method="post" action="/ar/attendanceMintenance/updateOrAddArDetail"
	 		 onsubmit="return cancelAgentTransValidateCallback(this, navTabAjaxDone);">
			<table class="table" width="100%" layoutH="231">
				<thead>
					<tr>
						<th width="10" align="center" >
							<input type="checkbox" class="checkboxCtrl" group="c1">
						</th>
						<th width="7%">
							<!-- 日期 --><spring:message code="ar.viewardetail.title.date"/>
						</th width="15%">
						
				<%--	<th width="12%">
							<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>
						</th>
				 --%>	
				        <th width="8%">
							<!-- 工号  --><spring:message code="public.title.empId"/>
						</th>	
					    <th width="5%">
							<!-- 姓名 --><spring:message code="public.title.name"/>
						</th>	
						<th width="15%">
							<!-- 部门 --><spring:message code="public.title.deptName"/>
						</th>
						<th width="5%">
							<!-- 班次 --><spring:message code="ar.viewCompanyCalendar.title.banci"/>
						</th>
						<c:if test="${LoginUser.cpnyId ne 'TSTO'}">
						<th width="5%">
						            是否夜班
						</th>
						</c:if>
						<th width="15%">
							<!-- 状态 --><spring:message code="ar.viewcycle.title.zhuangtai"/>
						</th>
						<th width="28%">
							<!-- 时间段 --><spring:message code="ar.viewardetail.title.dateduan"/>
						</th>
						<th width="3%">
							<!-- 长度 --><spring:message code="ess.viewApply.title.length"/>
						</th>
						<th width="8%">
							<!-- 锁定 --><spring:message code="ar.viewardetail.title.lock"/>
						</th>
						<th width="8%">
							修改理由
						</th>
						<th width="20">
							最终修改人
						</th>
						<th width="20">
							修改时间
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${arDetailList}" var="list" varStatus="i">
	
						<tr>
						  
							<td>
								<c:if test="${list.STATUS eq '0'}"><input type="checkbox" id="c1_${i.index}" name="c1" value="${list.PK_NO}"></c:if>
								<c:if test="${list.STATUS ne '0'}">&nbsp;</c:if>
							</td>
							<td  >
								<input type="hidden" id="AR_DATE_STR_${i.index}" name="AR_DATE_STR_${i.index}" value="${list.AR_DATE_STR}"/>
								${list.AR_DATE_STR}
							</td>
				<%-- 	    <td  >
								<input type="hidden" id="PERSON_ID_${i.index}" name="PERSON_ID_${i.index}" value="${list.PERSON_ID}"/>
								<input type="hidden" id="EMP_ID_${i.index}" name="EMP_ID_${i.index}" value="${list.EMPID}"/>
								(${list.EMPID})${list.LOCAL_NAME}
							</td>
				--%>		
				            <td>
								<input type="hidden" id="PERSON_ID_${i.index}" name="PERSON_ID_${i.index}" value="${list.PERSON_ID}"/>
							    ${list.EMPID} 
							</td>
						    <td>
							   <input type="hidden" id="EMP_ID_${i.index}" name="EMP_ID_${i.index}" value="${list.EMPID}"/>
							    ${list.LOCAL_NAME}
							</td>	
							<td  >
								${list.DEPTNAME}
							</td>
							
							<td  >
							 <c:if test="${fn:contains(list.SHIFT_NAME,'休息')}"><font color="red"></c:if>	${list.SHIFT_NAME}
							 <c:if test="${fn:contains(list.SHIFT_NAME,'休息')}"></font></c:if>
							</td>
							<c:if test="${LoginUser.cpnyId ne 'TSTO'}">
								<td  >
									${list.ISYEBAN}  
								 
								</td>
							 </c:if>
							<td  > 
								 
									<select name="ITEM_NO_${i.index}" id="ITEM_NO_${i.index}"  onchange="javascript:locked('${i.index}');">
										<c:forEach items="${getItemList}" var="item">
											<option value="${item.ITEM_NO}" 
												<c:if test="${item.ITEM_NO eq list.ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
											</option>
										</c:forEach>
									</select>
							 	<input name="OLD_ITEM_NO_${i.index}" id="OLD_ITEM_NO_${i.index}" type="hidden" value="${list.ITEM_NO}"  />
							</td>
							<td  >
								${list.FROMTIME}~~
								${list.TOTIME}
							</td>
							<td  >
								
									<input name="QUANTITY_${i.index}" id="QUANTITY_${i.index}" type="text" value="${list.QUANTITY}" size="3"
										onchange="javascript:locked('${i.index}');" onblur="checkNum(this.value)"/>
								
							</td>
							<td  >
								
									<select name="LOCK_YN_${i.index}" id="LOCK_YN_${i.index}" style="width:60px;" onchange="javascript:locked2('${i.index}',this);">
										<option value="N" <c:if test="${list.LOCK_YN eq 'N'}">selected</c:if>>
												<!-- 开放 --><spring:message code="ar.viewardetail.title.release"/>
										</option>
										<option value="Y" <c:if test="${list.LOCK_YN eq '' || list.LOCK_YN eq null || list.LOCK_YN eq 'Y'}">selected</c:if>>
												<!-- 锁定 --><spring:message code="ar.viewardetail.title.lock"/>
										</option>
									</select>
								
							</td>
							<td>
							 	<input name="REMARK_${i.index}" id="REMARK_${i.index}" type="text" value="${list.REMARK}" size="20" maxlength="15" />
							</td>
							<td> 
							     <c:choose> 
								     <c:when test="${list.UPDATED_BY eq ''|| list.UPDATED_BY == null}"> &nbsp;&nbsp;&nbsp;&nbsp; 
	                                 </c:when>
	                                 <c:otherwise> ${list.UPDATED_BY}
	                                 </c:otherwise>
	                             </c:choose>
							</td>
							<td>  
							  <c:choose> 
								     <c:when test="${list.UPDATE_DATE eq '' || list.UPDATE_DATE == null }"> &nbsp;&nbsp;&nbsp;&nbsp; 
	                              </c:when>
	                                 <c:otherwise> ${list.UPDATE_DATE}
	                                 </c:otherwise>
	                             </c:choose>
							 
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<c:set value="/ar/attendanceMintenance/viewArDetail" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
	
	
