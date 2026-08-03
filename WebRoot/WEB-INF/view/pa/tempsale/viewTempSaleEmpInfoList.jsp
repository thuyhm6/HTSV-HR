<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateBatchTempSaleSalesAffirmCallback(form,callback) {	
	var $form = $(form);	
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
//注意input的id和tr的id要一样
function addRowByIDBatchTempSaleSalesTwoBatch(currentRowID){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchTempSaleSales'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.batspersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.batsempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_BatchTempSaleSales(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDBatchTempSaleSalesTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addApplyBatchTempSaleSalesAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchTempSaleSalesLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyBatchTempSaleSales" + currentRowID).after(str);
	$("[id='dwz.person.batsempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchTempSaleSalesLevel();
	$("#affirmListCnt").val(++count) ;
}


function addRowByIDApplyBatchTempSaleSalesFirst(){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchTempSaleSales'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.batspersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.batsempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_BatchTempSaleSales(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDBatchTempSaleSalesTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addApplyBatchTempSaleSalesAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchTempSaleSalesLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addApplyBatchTempSaleSalesAffirm_list");

   	if(tb2.rows.length == 1){
   		$("#addApplyBatchTempSaleSalesAffirm_list").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.batsempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchTempSaleSalesLevel();
	$("#affirmListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyBatchTempSaleSalesLevel(){
	var tb2 = document.getElementById("addApplyBatchTempSaleSalesAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_BatchTempSaleSales(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="batspersonId"+empIdStr.substring(11);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onck").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.batsempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.batspersonId" + index + "']").val( jsonObject.personId);
					}
				},
				error: DWZ.ajaxError
			});
		}
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

//导入数据
function importExcelTempSalesEmpData(){
	$("#importExcelDialog_pa0701_EMPINFO").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelTempSalesEmpData?EVENT_ID=${EVENT_ID }');
	$("#importExcelDialog_pa0701_EMPINFO").click();
}
</script>
<a id="importExcelDialog_pa0701_EMPINFO"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0701_EMPINFO" href="#" target="navTab" mask="true"><span style="display:none;">临促工资人员信息导入结果</span></a>
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
				<td class="td_center">
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
<c:if test="${SUBMIT_STATUS eq '0'}">
<div class="formBar">
	<ul class="toolBar">
		<!--<li><a class="buttonActive"
			href="/pa/tempsale/viewAddPaTempSalesEmpInfo?EVENT_ID=${EVENT_ID }"
			target="navTab" mask="true"
			rel="viewAddPaTempSalesEmpInfo"><span> <spring:message
			code="button.add" />添加</span></a></li>
		--><li><a class="buttonActive" width="800" height="280"
			href="/pa/tempsale/viewUpdatePaTempSalesEmpInfo?EVENT_ID=${EVENT_ID }&INFO_NO={INFO_NO}"
			target="dialog" mask="true"
			rel="viewAddPaTempSalesEmpInfo"><span><spring:message 
			code="button.update" /><!--修改--></span></a></li>
		<li><a class="buttonActive" 
			href="/pa/tempsale/deletePaTempSalesEmpInfo?EVENT_ID=${EVENT_ID }&INFO_NO={INFO_NO}"
			target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		<li><a class="buttonActive"
			href="/pa/tempsale/downloadExcelTemplate?type=2"><span>
		<spring:message code="pa.insurance.title.downloadImportTemplate" /><!--下载导入模板--></span>
		</a></li>
		<li><a class="buttonActive" onclick="importExcelTempSalesEmpData()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
	</ul>
</div>
</c:if>
<div class="pageContent">
<table class="table" width="100%" layoutH="270">
	<thead>
		<tr>
			<th>序号</th>
			<th>姓名</th>
			<th>Event门店</th>
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
				<td style="text-align:center">${i.count}</td>
				<td style="text-align:center">${item.EMP_NAME }</td>
				<td style="text-align:center">${item.EVENT_STORE_CODE}</td>
				<td style="text-align:center">${item.IDCARD_NO }</td>
				<td style="text-align:center">${item.BANK_NO }</td>
				<td style="text-align:center">${item.BANK_NAME }</td>
				<td style="text-align:center">${item.CELLPHONE }</td>
				<td style="text-align:center">${item.PROD_NAME }</td>
				<td style="text-align:center">${item.EVS_GRADE_NAME }</td>
				<td style="text-align:center">${item.WORK_DAYS }</td>
		   	   	<td style="text-align:center">${item.EVENT_SALARY }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleEmpInfoList?EVENT_ID=${EVENT_ID }&SUBMIT_STATUS=${SUBMIT_STATUS }" var="pageUrl"/>
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
						<tr>
							<td class="td_title" style="text-align: center;width:13%;">支社</td>
							<td class="td_type" style="text-align: center;width:13%;">${item.BRANCH_NAME}</td>
							<td class="td_title" style="text-align: center;width:13%;">对应共同社编</td>
							<td class="td_type" style="text-align: center;width:13%;">${item.COMMON_EMPID}</td>
							<td class="td_title" style="text-align: center;width:12%;">产品类型</td>
							<td class="td_type" style="text-align: center;width:12%;">${item.PROD_NAME}</td>
							<td class="td_title" style="text-align: center;width:12%;">金额</td>
							<td class="td_type" style="text-align: center;width:12%;">${item.TOTAL_PAY}</td>
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
							<td class="td_title" style="text-align: center;width:10%;">支社</td>
							<td class="td_type" style="text-align: center;width:10%;">${item.BRANCH_NAME}</td>
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

<c:if test="${SUBMIT_STATUS ne '0'}">
<div class="pageContent" >
	<table class="user_table" width="100%" border="0">
		<tr> 
			<td class="td_title" width="10%" style="text-align: right" rowspan="${affirmListCnt + 1 }"><!-- 审批线 -->
					审批线
			</td>
			<td class="td_title" width="7%" style="text-align: center"><!-- 审批等级 -->
				审批等级
			</td>
			<td class="td_title" width="20%" style="text-align: center"><!-- 审批者 -->
				审批者
			</td>
			<td class="td_title" width="18%" style="text-align: center"><!-- 审批情况 -->
				审批情况
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" width="30%" style="text-align: center"><!-- 审批批注 -->
				审批批注
			</td>
		</tr>
		<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未审批</font>-->
						未审批
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						否决
					</c:if>
				</td>
				<td class="td_type"  style="text-align: center">${affirmor.UPDATE_DATE}</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmListCnt == 0}">
								<tr>
									<td class="td_title" >&nbsp;</td>
									<td class="td_type" >&nbsp;</td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
								</tr>		
							</c:if>
		<tr>
					<td class="td_title" style="text-align:right" <c:if test="${checkListCnt eq '0'}">rowspan="2"</c:if> <c:if test="${checkListCnt ne '0'}">rowspan="${checkListCnt + 1}"</c:if>>
						Check
					</td>
								<td class="td_title" style="text-align: center">
									Type
								</td>
								<td class="td_title" style="text-align: center" colspan="2">
									Requests
								</td>
								<td class="td_title" style="text-align: center" colspan="2">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkList}" var="check" varStatus="i">
								<tr>
									<td class="td_type" style="text-align: center;" >
										public
									</td>
									<td class="td_type" colspan="2">
										[${check.EMPID_R}]-${check.LOCAL_NAME_R }&nbsp;&nbsp;${check.POSITION_NO_R }&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br/>
										[Request]${check.CHECK_REASON}
									</td>
									<td class="td_type" colspan="2">
										[${check.EMPID_C}]-${check.LOCAL_NAME_C }&nbsp;&nbsp;${check.POSITION_NO_C }&nbsp;&nbsp;(${check.DEPTNAME_C })
										<c:if test="${check.CHECK_FLAG eq '0'}">
											/未Check
										</c:if>
										<c:if test="${check.CHECK_FLAG ne '0'}">
										/${check.DATE_C }
										</c:if>
										<br/>
										[Check]${check.CHECK_CONTENT}
									
								</tr>
							</c:forEach>
							<c:if test="${checkListCnt == 0}">
								<tr>
									<td class="td_type" style="text-align: center;">Public</td>
									<td class="td_type" colspan="2">
										无
									</td>
									<td class="td_type" colspan="2">
										无
									</td>
								</tr>		
							</c:if>
	</table>
</div>
</c:if>

<c:if test="${SUBMIT_STATUS eq '0'}">
<div class="pageContent" >
	<form id="modifyAffirmorForBatchTempSaleSales" method="post" action="/pa/tempsale/modifyAffirmorForBatchTempSale" class="pageForm required-validate" 
		onsubmit="return validateBatchTempSaleSalesAffirmCallback(this,navTabAjaxDone);">
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁线 -->
					决裁线
			</td>
			<td colspan="3" style="text-align: center;width:90%;">
				<table class="user_table" width="100%" id="addApplyBatchTempSaleSalesAffirm_list">
					<tr>
									<td class="td_title" style="text-align:center;" width="11%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="44%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="44%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyBatchTempSaleSalesFirst()"/>)</td>
					</tr>
								<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
									<tr id="rowIdApplyBatchTempSaleSales${i.index }">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/></td>
										<td class="td_type" style="text-align: center"><img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDBatchTempSaleSalesTwoBatch(${i.index })"/></td>
									</tr>			
								</c:forEach>
				</table>
							<input type="hidden" id="affirmListCnt" name="affirmListCnt" value="${affirmListCnt }"/>
							<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${EVENT_ID }">
							<input type="hidden" name="APPLY_TYPE" id="APPLY_TYPE" value="218064">
							<a id="onck" name="onck"  href="" lookupGroup="person"></a>
			</td>
		</tr>
	</table>
	
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>   
	</form>
</div>
</c:if>
