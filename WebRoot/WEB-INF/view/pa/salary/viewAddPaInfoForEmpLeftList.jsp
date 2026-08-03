<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addRowByIDForLeft(currentRowID){
    //遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('table:last tbody tr'), function(i, tr){
        if($(this).attr('id')==currentRowID){
            //获取当前行
            var currentRow=$('table:last tbody tr:eq('+i+')');
            //要添加的行的id
            var addRowID=i+2;
            str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
            		+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmorForLeft(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<c:if test="${affirmorListCnt != 1}">'
            			+'   <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDForLeft(this.id);"/>'
            			+'</c:if>&nbsp;&nbsp;&nbsp;'
            			+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			//先删除，再排序
            			//+' onclick="javaScript:document.all.LaddAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delLRowByID();"/>'
            			+' onclick="deleteRowByIDForLeft(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   	var tb2 = document.getElementById("addAffirm_listPa0708");
 	//如果决裁者只有一个时，添加一个决裁者之后需要取消此按钮
 	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
 	if(affirmorListCnt == 1){
 		tb2.rows[0].cells[2].innerHTML = '';
	}
   	var rowCount = tb2.rows.length;
   	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
   	}
}

function deleteRowByIDForLeft(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('addAffirm_listPa0708').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listPa0708");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	//var affirmorIdOnly = document.getElementById("affirmorIdOnly").value;
   	//if(affirmorListCnt == 1){
   		//var addStr = '<img id="'+affirmorIdOnly+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDForLeft('+affirmorIdOnly+');"/>';
   		//tb2.rows[0].cells[2].innerHTML = addStr;
	//}
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function delLRowByID(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listPa0708");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}


var keyCodeInit=0;
function submitKeyClick_affirmorForLeft(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt>1 ){
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdListNew?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId='+empIdStr
									+'&personId='+personIdStr  
									+'&empName='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
						}
					},
			error: DWZ.ajaxError
		});
    }
 }
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
};  
//-->
</script>

<script type="text/javascript">
<!--
function savePaInfoForEmpLeft(){
	$("#addPaInfoForEmpLeft").submit();
}

function validateCallbackViewPaInfoForEmpLeft(form, callback) {
	var $form = $("#addPaInfoForEmpLeft");
	if(!$form.valid()) {
		return false;
	}
	//进行申请之前先验证是否有导入数据
	var importDataCnt = $("#paInfoTempListCnt").val();
	if(importDataCnt=="" || importDataCnt=="0"){
		alertMsg.error("薪资补发信息为空，不允许申请，请导入数据!");
		return false;
	}
	if(confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){		
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200" && data.result=="0"){
					navTab.closeCurrentTab();
					alertMsg.correct(data.message);
					navTab.reloadFlag(data.navTabId);
				}else if(data.statusCode=="200" && data.result=="1"){
					navTabSearch("viewAddPaInfoForEmpLeftList");
					alertMsg.correct(data.message);
					navTab.reloadFlag(data.navTabId);
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
    	return false;
	}
	return false ;
}
//-->
</script>
<a id="importExcel_pa0708"  href="#" target="navTab" mask="true"><span style="display:none;">导入结果</span></a>

<div class="pageContent">
	<form name="viewAddPaInfoForEmpLeftList" id="viewAddPaInfoForEmpLeftList" action="/pa/salary/viewAddPaInfoForEmpLeftList"
		onsubmit="return navTabSearch(this);" method="post">
		<div class="formBar">
			<ul class="toolBar">
				<li><a id="exportExcel" href="/pa/salary/exportPaForLeftMenModle">
						<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
					</a>
				</li>
				<li>
					<a class="buttonActive" href="/pa/excelImport/importExcelData?importFunName=/importPaForLeftMen"
						target="dialog" mask="true" width="500" height="200"> 
						<span><!--EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span> 
					</a>
			 	</li><%-- 
			 	<li><a id="exportExcel" href="/pa/excelExport/exportPaiQianDiGuanLiInfoList?seach_faren=${faren}&seach_chengshidengji=${csdj}&seach_diqumingcheng=${dqmc}">
						<span><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></span>
					</a>
				</li>--%>
			</ul>
		</div>
	</div>
	<div class="pageContent">
		<div style="overflow-y:auto; height:180px;">
			<table width="100%" class="table" layoutH="250">
			<thead>
				<tr>
					<th width="8%" style="text-align:center">社号</th>
					<th width="8%" style="text-align:center">姓名</th>
					<th width="8%" style="text-align:center">补发月份</th>
					<th width="8%" style="text-align:center">发放月份</th>
					
					<th width="8%" style="text-align:center">补发类别</th>
					<!-- <th width="15%" style="text-align:center">补发项目</th>
					<th width="5%" style="text-align:center">金额</th> -->
					<th width="15%" style="text-align:center">备注[不超过100字]</th>
					
					<th width="5%" style="text-align:center">正/异常</th>
					<th width="15%" style="text-align:center">错误提示</th>
					<%-- <th width="5%" style="text-align:center">删除</th>--%>
				</tr>
				</thead>
			<tbody>
				<c:forEach items="${paInfoTempList}" var="paInfo" varStatus="j">
					<tr target="DATA_NO" rel="${paInfo.APPLY_NO}">
					<input type="hidden" name="APPLY_NO" value="${paInfo.APPLY_NO}">
						<td width="8%" style="text-align:center">${paInfo.EMPID }</td>
						<td width="8%" style="text-align:center">${paInfo.LOCAL_NAME }</td>
						<td width="8%" style="text-align:center">${paInfo.PA_MONTH_FOR }</td>
						<td width="8%" style="text-align:center">${paInfo.PA_MONTH }</td>
						
						<td width="8%" style="text-align:center">${paInfo.ITEM_TYPE }</td>
						<%-- <td width="15%" style="text-align:center">${paInfo.ITEM_NO }</td>
						<td width="5%" style="text-align:right">${paInfo.ITEM_DATA }</td> --%>
						<td width="15%" style="text-align:center">${paInfo.REMARK }</td>
						
						<td width="5%" style="text-align:center">
							<c:if test="${paInfo.CHECK_FLAG eq '1' }">
								<font color="red">异常</font>
							</c:if>
							<c:if test="${paInfo.CHECK_FLAG ne '1' }">
								<font color="green">正常</font>
							</c:if>
						</td>
						<td width="15%" style="text-align:center">${paInfo.CHECK_ERROR }</td>
						<%--<td width="5%" style="text-align:center">删除</td>--%>
					</tr>
				</c:forEach>
				<c:if test="${paInfoTempListCnt == 0}">
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>						
				</c:if>
				</tbody>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<form id="addPaInfoForEmpLeft" method="post" action="/pa/salary/savePaInfoForEmpLeft" class="pageForm required-validate" 
		onsubmit="return validateCallbackViewPaInfoForEmpLeft(this, navTabAjaxDone)">
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" style="text-align: center" width="25%">申请内容：</td>
					<td class="td_type" width="75%" colspan="3">
						<input type="hidden" id="applyContent1" name="TITLE1" value="离职员工薪资补发申请"/>
						<textarea type="text" id="applyContent" name="TITLE" class="required" cols="80" rows="3" maxlength="300"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_title" style="text-align: center" width="25%" rowspan="2">决裁线</td>
					<td class="td_title" style="text-align: center" width="25%">决裁等级</td>
					<td class="td_title" style="text-align: center" width="25%">决裁者</td>
					<td class="td_title" style="text-align: center" width="25%">是否新增</td>
				</tr>
				<tr id="123">
					<td colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listPa0708">
							<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
									<input type="hidden" id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" value="224"/>
									<input type="hidden" id="paInfoTempListCnt" name="paInfoTempListCnt" value="${paInfoTempListCnt }"/>
									<input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
									<c:if test="${affirmorListCnt == 1}">
										<input type="hidden" id="affirmorIdOnly" name="affirmorIdOnly" value="${affirmor.AFFIRMOR_ID }"/>	
									</c:if>
									<c:if test="${affirmorListCnt != 1}">
										<input type="hidden" id="affirmorIdOnly" name="affirmorIdOnly" value="0"/>
									</c:if>	
									<tr id="${affirmor.AFFIRMOR_ID }">
										<td class="td_type" style="text-align: center" width="33%">${j.count}</td>
										<td class="td_type" style="text-align: center" width="33%">
											[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }<input
													type="hidden" name="AFFIRMOR_ID"
													value="${affirmor.AFFIRMOR_ID }" />
										</td>
										<td class="td_type" style="text-align: center" width="33%">
												<img src="/resources/images/+.gif" title="添加" border="0"
													align="absmiddle" style="cursor: hand" onclick="addRowByIDForLeft(${affirmor.AFFIRMOR_ID })" />
										</td>
									</tr>
								</c:forEach>
								<a id="onck" name="onck"  href="" lookupGroup="person"></a>
								<%-- <input type="hidden" id="FLAG" name="FLAG" value="1"/>--%>
<!-- 								<input type="hidden" name="affirmCountDimi" id="affirmCountDimi" -->
<!-- 				value="${affirmorListCnt }"> -->
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="savePaInfoForEmpLeft()"><!-- 提交 --><spring:message code="public.title.submit"/></button>
						</div>
					</div>
				</li><%-- 
				<li>
					<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
						href="/pa/salary/savePaInfoForEmpLeft" title="确定要提交吗?"><span>提交</span>
					</a>
				</li>--%>
				<li>
					<a class="buttonActive" target="ajaxTodo" href="/pa/salary/deletePaForLeftApplyImport" title="确定要全部取消吗?">
						<span>取消</span>
					</a>
				</li>
			</ul>
		</div>
	</form>
</div>