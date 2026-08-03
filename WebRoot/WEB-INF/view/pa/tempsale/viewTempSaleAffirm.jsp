<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDPa0901_affirm(currentRowID){
	var count = parseInt($("#affirmCount_affirm").val());
    var htm  ='<tr id="rowIdPa0901_affirm'+ count +'"><td class="td_type" style="text-align: center"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center">';
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
			url: encodeURI('/sys/affirm/getPersonCnt?limit=affirm&navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt!=1){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
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
		var $form = $("#viewAffirmPaTempSales");
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

	function affirmTempSalary(flag){
		$("#viewAffirmPaTempSalesFLAG_affirm").val(flag);
		$("#viewAffirmPaTempSales").submit();
	}
	function addRowByIDPa0901_Check(){
		$.pdialog.open("/pa/tempsale/viewAddCheckPaTempSales?AFFIRM_NO=${affirm_no}&APPLY_NO=${EVENT_ID}", "addRowByIDPa0901_check", "添加Check人", {width:600,height:300,mask:true});
	}
</script>
<c:if test="${tempSalInfo.ACCRUAL_FLAG eq 'N'}">
<div class="pageContent" style="padding-bottom:18px;">
<table class="table" width="100%">
	<thead>
		<tr>
			<th>Event名称</th>
			<th>EventID</th>
			<th>Event部门</th>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>总人数</th>
			<th>总金额</th>
			<th>工资支付月</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
			<tr>
				<td style="text-align:left">
					${tempSalInfo.EVENT_NAME }
				</td>
				<td class="td_center">${tempSalInfo.EVENT_ID }</td>
				<td class="td_center">${tempSalInfo.EVENT_DEPTNO_NAME }</td>
				<td class="td_center">${tempSalInfo.START_DATE }</td>
				<td class="td_center">${tempSalInfo.END_DATE }</td>
				<td class="td_center">${tempSalInfo.TOTAL_NUM }</td>
				<td class="td_center">${tempSalInfo.TOTAL_SALARY }</td>
				<td class="td_center">${tempSalInfo.PAY_DATE }</td>
				<td>${tempSalInfo.REMARK }</td>
			</tr>
			<tr>
				<td class="td_center">
					附件查看
				</td>
				<td style="text-align: left" colspan="8">
							<c:forEach items="${tempSalInfo.fileList}" var="file" varStatus="j">	
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME }</a></div>
							</c:forEach>
			   </td>
			</tr>
	</tbody>
</table>
</div>
<div class="pageContent">
<table class="table" width="100%" layoutH="270">
	<thead>
		<tr>
			<th>序号</th>
			<th>姓名</th>
			<th>身份证号</th>
			<th>银行账号</th>
			<th>开户行</th>
			<th>联系方式</th>
			<th>产品类型</th>
			<th>评价等级</th>
			<th>工作天数</th>
			<th>应发工资</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesEmpInfoList}" var="item" varStatus="i">
			<tr target="INFO_NO" rel="${item.INFO_NO}">
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${item.EMP_NAME }</td>
				<td style="text-align: center">${item.IDCARD_NO }</td>
				<td style="text-align: center">${item.IBANK_NO }</td>
				<td style="text-align: center">${item.BANK_NAME }</td>
				<td style="text-align: center">${item.CELLPHONE }</td>
				<td style="text-align: center">${item.PROD_NAME }</td>
				<td style="text-align:center">${item.EVS_GRADE_NAME }</td>
				<td style="text-align: center">${item.WORK_DAYS }</td>
		   	   	<td style="text-align: center">${item.EVENT_SALARY }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleAffirm?EVENT_ID=${EVENT_ID }&affirmOrCheck=${affirmOrCheck}" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<c:if test="${tempSalInfo.ACCRUAL_FLAG eq 'CONFIRM_Y'}">
<div class="pageContent">
<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align:right;width:10%;">申请人</td>
			<td colspan="7" class="td_type" style="text-align:left;width:90%;">[${tempSalInfo.EMPID}]${tempSalInfo.LOCAL_NAME}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请时间</td>
			<td colspan="7" class="td_type" style="text-align: left;">${tempSalInfo.APPLY_DATE}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请内容</td>
			<td colspan="7">
				<table width="100%">
					<tr>
						<td colspan="2" class="td_title" style="text-align: center;width:25%;">大区</td>
						<td colspan="2" class="td_type" style="text-align: center;width:25%;">${tempSalInfo.PAY_AREA_NAME}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:25%;">支付月份</td>
						<td colspan="2" class="td_type" style="text-align: center;width:25%;">${tempSalInfo.PAY_DATE}</td>
					</tr>
					<c:forEach items="${paTempSalesAccuralInfoList}" var="item" varStatus="i">
						<tr target="INFO_NO" rel="${item.INFO_NO}">
							<td class="td_title" style="text-align: center;">支社</td>
							<td class="td_type" style="text-align: center;">${item.BRANCH_NAME}</td>
							<td class="td_title" style="text-align: center;">对应共同社编</td>
							<td class="td_type" style="text-align: center;">${item.COMMON_EMPID}</td>
							<td class="td_title" style="text-align: center">产品类型</td>
							<td class="td_type" style="text-align: center">${item.PROD_NAME}</td>
							<td class="td_title" style="text-align: center">金额</td>
							<td class="td_type" style="text-align: center">${item.TOTAL_PAY}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" class="td_type"></td>
						<td class="td_title" style="text-align:center;">合计</td>
						<td class="td_type" style="text-align:center;">${paTempSalesSum.TOTAL_PAY}</td>
					</tr>
				</table>
			</td>
		</tr>
</table>
</div>
</c:if>
<c:if test="${tempSalInfo.ACCRUAL_FLAG eq 'CONFIRM_N'}">
<div class="pageContent">
<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align:right;width:10%;">申请人</td>
			<td colspan="7" class="td_type" style="text-align:left;width:90%;">[${tempSalInfo.EMPID}]${tempSalInfo.LOCAL_NAME}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请时间</td>
			<td colspan="7" class="td_type" style="text-align: left;">${tempSalInfo.APPLY_DATE}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请内容</td>
			<td colspan="7">
				<table width="100%">
					<tr>
						<td colspan="3" class="td_title" style="text-align: center;width:25%;">大区</td>
						<td colspan="3" class="td_title" style="text-align: center;width:25%;">${tempSalInfo.PAY_AREA_NAME}</td>
						<td colspan="3" class="td_title" style="text-align: center;width:25%;">支付月份</td>
						<td colspan="3" class="td_title" style="text-align: center;width:25%;">${tempSalInfo.PAY_DATE}</td>
					</tr>
					<c:forEach items="${paTempSalesAccuralInfoList}" var="item" varStatus="i">
						<tr>
							<td class="td_title" style="text-align: center;width:8%;">支社</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.BRANCH_NAME}</td>
							<td class="td_title" style="text-align: center;width:8%;">对应共同社编</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.COMMON_EMPID}</td>
							<td class="td_title" style="text-align: center;width:8%;">产品类型</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.PROD_NAME}</td>
							<td class="td_title" style="text-align: center;width:8%;">应发金额</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.TOTAL_PAY}</td>
							<td class="td_title" style="text-align: center;width:8%;">税金</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.TAX_PAY}</td>
							<td class="td_title" style="text-align: center;width:8%;">实发金额</td>
							<td class="td_type" style="text-align: center;width:8%;">${item.NET_PAY}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" class="td_type"></td>
						<td class="td_title" style="text-align:center;">合计</td>
						<td class="td_type" style="text-align:center;">${paTempSalesSum.TOTAL_PAY}</td>
						<td class="td_type"></td>
						<td class="td_type" style="text-align:center;">${paTempSalesSum.TAX_PAY}</td>
						<td class="td_type"></td>
						<td class="td_type" style="text-align:center;">${paTempSalesSum.NET_PAY}</td>
					</tr>
				</table>
			</td>
		</tr>
</table>
</div>
</c:if>
<div class="pageContent">
	<form id="viewAffirmPaTempSales" method="post" action="/pa/tempsale/affirmPaTempSales" class="pageForm required-validate" onsubmit="return validateCallbackViewUpdatePaTempSales(this, navTabAjaxDone)">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				<tr>
					<td class="td_title" style="text-align: right;width:10%;">
						决裁线
					</td>
					<td style="text-align: center;width:90%;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listPa0901_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center" width="8%">
									决裁等级
								</td>
								<td class="td_title" style="text-align: center" width="15%">
									决裁者
								</td>
								<td class="td_title" style="text-align: center" width="12%">
									决裁情况
								</td>
								<td class="td_title" style="text-align: center" width="15%">
									审批时间
								</td>
								<td class="td_title" style="text-align: center" width="34%">
									决裁批注
								</td>
								<td class="td_title" style="text-align: center" width="8%">
									决裁者(+/-)
								</td>
								<td class="td_title" style="text-align: center" width="8%">
									check(+)
								</td>
							</tr>
							<c:forEach items="${affirmList}" var="affirmor" varStatus="j">	
								<tr id="rowIdPa0901_affirm${j.index}">
									<td class="td_type" style="text-align: center">
										${j.count}
									</td>
									<td class="td_type" style="text-align: center">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<c:if test="${affirmor.AFFIRM_FLAG eq '0' }">
										<input type="hidden" name=AFFIRMOR_ID value="${affirmor.AFFIRMOR_ID }"/>
										</c:if>
									</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
									</td>
									<td class="td_type" style="text-align: center">
										${affirmor.UPDATE_DATE}
									</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
											<input type="text" name="AFFIRM_CONTENT"  size="55" maxlength="200"/>
											<input type="hidden" name="ESS_AFFIRM_NO" value="${affirmor.ESS_AFFIRM_NO}"/>
										</c:if>
										<c:if test="${affirmor.ESS_AFFIRM_NO ne affirm_no}">
											${affirmor.AFFIRM_CONTENT}
										</c:if>
									</td>
									<td class="td_type" style="text-align: center">
									<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no && j.count<affirmorListCnt}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0901_affirm(${j.index})"/>
									</c:if>
									</td>
									<td class="td_type" style="text-align: center">
									<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0901_Check()"/>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" style="text-align: right">
						Check
					</td>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addACheck_listPa0901_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center;width:8%;">
									Type
								</td>
								<td class="td_title" style="text-align: center;width:42%;">
									Requests
								</td>
								<td class="td_title" style="text-align: center;width:50%;">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkList}" var="check" varStatus="j">
								<tr>
									<td class="td_type" style="text-align: center">
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
											[Check]<br/><input type="text" name="CHECK_CONTENT"  size="55" maxlength="200"/>
											<input type="hidden" name="ESS_CHECK_NO" value="${check.ESS_CHECK_NO}"/>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${checkListCnt == 0}">
								<tr>
									<td class="td_type" style="text-align: center;">Public</td>
									<td class="td_type">
										无
									</td>
									<td class="td_type">
										无
									</td>
								</tr>		
							</c:if>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		    <input type="hidden" name="affirmCount" id="affirmCount_affirm" value="${affirmorListCnt }">
		    <input type="hidden" id="viewAffirmPaTempSalesFLAG_affirm" name="FLAG" value="1" />
		    <input type="hidden" id="EVENT_ID" name="EVENT_ID" value="${EVENT_ID}" />
		    <input type="hidden" id="affirmOrCheck" name="affirmOrCheck" value="${affirmOrCheck}" />
		    <input type="hidden" id="dept_level" name="dept_level" value="${dept_level}" />
		    <input type="hidden" id="accrualFlag" name="accrualFlag" value="${tempSalInfo.ACCRUAL_FLAG}" />
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmTempSalary(1);">通过</button></div></div></li>
				<c:if test="${affirmOrCheck eq '1'}">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmTempSalary(2);">否决</button></div></div></li>
				</c:if>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>