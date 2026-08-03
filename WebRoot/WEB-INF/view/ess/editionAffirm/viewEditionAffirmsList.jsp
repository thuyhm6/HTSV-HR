<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDPa0901_affirm(currentRowID){
	var count = parseInt($("#affirmCount_affirm").val());
    var htm  ='<tr id="rowIdPa0901_affirm'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDPa0901_affirm' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOPa0901_affirm' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor_affirm(this,' + count + ',event)" class="required"/>';
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td style="text-align: center">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0901_affirm(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listPa0901_affirm.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevelPa0901_affirm();"/></td>';
		htm +='<td></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdPa0901_affirm" + currentRowID).after(htm);
   	changeAffirmLevelPa0901_affirm();
  	$("#affirmCount_affirm").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevelPa0901_affirm(){
	var tb2 = document.getElementById("addAffirm_listPa0901_affirm");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
var keyCodeInit=0;
function submitKeyClick_affirmor_affirm(obj,index,event){
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
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdList?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId_sy0482='+empIdStr
									+'&personId_sy0482='+personIdStr  
									+'&empName_sy0482='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOPa0901_affirm" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDPa0901_affirm" + index).val( jsonObject.personId);
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
}
	
    function validateCallbackViewUpdatePaTempSales(form, callback) {
		var $form = $("#affirmDimissionApplyInfo");
		if (!$form.valid()) {
			return false;
		}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	}

	function affirmDimission(flag){
		$("#affirmDimissionApplyInfoFLAG_affirm").val(flag);
		$("#affirmDimissionApplyInfo").submit();
	}
	function addRowByIDess2017_Check(){
		$.pdialog.open("/ess/editionAffirm/viewAddCheckEssDimission?AFFIRM_NO=${affirm_no}", "addRowByIDess2017_Check", "添加Check人",  {width:600,height:350,mask:true});
	}
</script>
<div class="pageContent">
	<table class="table" width="100%" layoutH="350" nowrapTD="false">
		<thead>
			<tr>
				<th width="5%">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="5%">
					<!-- 姓名 -->
					<spring:message code="inct.salesman.Name" />
				</th>
				<th width="10%">
					<!-- 人员类型 -->
					<spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th width="10%"><spring:message
						code="ess.infoApply.title.essApplyTime" />
					<!-- 申请日期 -->
				</th>
				<th width="10%"><spring:message
						code="display.pa.ecc.expectresigndate" />
					<!-- 预离职日期 -->
				</th>
				<th width="21%"><spring:message
						code="ess.trans.title.resignReason" />
					<!-- 离职原因-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionAffirmList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left">${item.EMPID }
					<input type="hidden" name="APPLY_NO" value="${item.APPLY_NO}"/>
					<input type="hidden" name="PERSON_ID" value="${item.PERSON_ID}" />
					<input type="hidden" name="DEPTNO" value="${item.DEPTNO}" />
					</td>
					<td style="text-align:left">${item.LOCAL_NAME }</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME }</td>
					<td style="text-align:left">${item.APPLY_TIME }</td>
					<td>${item.APPLY_LEAVE_TIME }</td>
					<td>${item.APPLY_REASON}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="pageContent">
	<form id="affirmDimissionApplyInfo" method="post" action="/ess/editionAffirm/affirmDimissionApplyInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewUpdatePaTempSales(this, navTabAjaxDone)">
	<input type="hidden" id="APPLY_NO" name="APPLY_NO" value="${APPLY_NO }" />
	<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${applyorInfo.PERSON_ID}" />
		<div class="pageFormContent">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				<tr>
					<td class="td_title" style="text-align: center">
						申请对象
					</td>
					<td class="td_title">
						${applyorInfo.LOCAL_NAME}
					</td>
				</tr>
				<tr>
					<td class="td_title" style="text-align: center">
						审批线
					</td>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listPa0901_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center" width="30%">
									审批等级
								</td>
								<td class="td_title" style="text-align: center" width="40%">
									审批者
								</td>
								<td class="td_title" style="text-align: center" width="30%">
									审批情况
								</td>
								<td class="td_title" style="text-align: center" width="40%">
									审批时间
								</td>
								<td class="td_title" style="text-align: center" width="30%">
									审批批注
								</td>
								<td class="td_title" style="text-align: center" width="40%">
									审批者(+/-)
								</td>
								<td class="td_title" style="text-align: center" width="30%">
									check(+)
								</td>
							</tr>
							<c:forEach items="${affirmList}" var="affirmor" varStatus="j">	
								<tr id="rowIdPa0901_affirm${j.index}">
									<td class="td_type" style="text-align: center" width="33%">
										${j.count}
									</td>
									<td class="td_type" style="text-align: center" width="33%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<c:if test="${affirmor.AFFIRM_FLAG eq '0' }">
										<input type="hidden" name=AFFIRMOR_ID value="${affirmor.AFFIRMOR_ID }"/>
										</c:if>
									</td>
									<td class="td_type" style="text-align: center" width="33%">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未审批</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
									</td>
									<td class="td_type" style="text-align: center" width="33%">
										${affirmor.UPDATE_DATE}
									</td>
									<td class="td_type" style="text-align: center" width="33%">
										<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
										   <c:if test="${affirmor.UPDATE_DATE ne ''}">
											<input type="text" name="AFFIRM_CONTENT" />
											<input type="hidden" name="ESS_AFFIRM_NO" value="${affirmor.ESS_AFFIRM_NO}"/>
										</c:if>
										</c:if>
										<c:if test="${affirmor.ESS_AFFIRM_NO ne affirm_no}">
											${affirmor.AFFIRM_CONTENT}
										</c:if>
									</td>
									<td class="td_type" style="text-align: center" width="33%">
									<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0901_affirm(${j.index})"/>
									</c:if>
									</td>
									<td class="td_type" style="text-align: center" width="33%">
									<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDess2017_Check()"/>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" style="text-align: center">
						Check
					</td>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addACheck_listPa0901_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center">
									Type
								</td>
								<td class="td_title" style="text-align: center">
									Requests
								</td>
								<td class="td_title" style="text-align: center">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkList}" var="check" varStatus="j">
								<tr>
									<td class="td_type" style="text-align: center" width="10%">
										public
									</td>
									<td class="td_type" width="45%">
										[${check.EMPID_R}]-${check.LOCAL_NAME_R }&nbsp;&nbsp;${check.POSITION_NO_R }&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br/>
										[Request]${check.CHECK_REASON}
									</td>
									<td class="td_type" width="45%">
										[${check.EMPID_C}]-${check.LOCAL_NAME_C }&nbsp;&nbsp;${check.POSITION_NO_C }&nbsp;&nbsp;(${check.DEPTNAME_C })
										<c:if test="${check.CHECK_FLAG eq '0'}">
											/未Check
										</c:if>
										<c:if test="${check.CHECK_FLAG ne '0'}">
										/${check.DATE_C }
										</c:if>
										<br/>
										<c:if test="${check.CHECK_FLAG eq '1'}">
											[Check]${check.CHECK_CONTENT}
										</c:if>
										<c:if test="${check.ESS_CHECK_NO eq check_no}">
											[Check]<br/><input type="text" name="CHECK_CONTENT" />
											<input type="hidden" name="ESS_CHECK_NO" value="${check.ESS_CHECK_NO}"/>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		    <input type="hidden" name="affirmCount" id="affirmCount_affirm" value="${affirmorListCnt }">
		    <input type="hidden" id="affirmDimissionApplyInfoFLAG_affirm" name="FLAG" value="1" />
		    <input type="hidden" id="dept_level" name="dept_level" value="${dept_level}" />
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmDimission(1);">通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmDimission(2);">否决</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>