<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导入数据
function importExcelTempEmpResign() {
	$("#importExcelDialog_hr0206").attr('href',
					'/pa/excelImport/importExcelData?importFunName=/importExcelTempEmpResignData');
	$("#importExcelDialog_hr0206").attr('height', "200");
	$("#importExcelDialog_hr0206").attr('width', "400");
	$("#importExcelDialog_hr0206").click();
}
//查看离职
function f_inquiryResign() {
	$("#hr0206Link").html("查看离职");
	$("#importExcelDialog_hr0206").attr('href','/hrm/transferOrder/viewResignInquiryList?pageNum=1&{sid}');
	$("#importExcelDialog_hr0206").attr('height', "500");
	$("#importExcelDialog_hr0206").attr('width', "900");
	$("#importExcelDialog_hr0206").click();
}
//新增离职
function f_addResign() {
	$("#hr0206Link").html("添加");
	$("#importExcelDialog_hr0206").attr('href','/hrm/transferOrder/viewResignAddList?pageNum=1');
	$("#importExcelDialog_hr0206").attr('height', "500");
	$("#importExcelDialog_hr0206").attr('width', "900");
	$("#importExcelDialog_hr0206").click();
}
//修改离职
function f_editResign() {
	$("#hr0206Link").html("修改");
	$("#importExcelDialog_hr0206").attr('href','/hrm/transferOrder/viewResignEditList?pageNum=1&{sid}');
	$("#importExcelDialog_hr0206").attr('height', "500");
	$("#importExcelDialog_hr0206").attr('width', "900");
	$("#importExcelDialog_hr0206").click();
}
//删除离职
function f_delResign() {
	var $form = $("#tempEmpResignForm");	
	var validFlag = false ;
	$form.find(":checkbox[id='resignReqCKB']").each(function(index, checkBoxObj){	    
	    if(checkBoxObj.checked){
	      validFlag = true ;
	    }	    
	});
	if(!validFlag){
		alertMsg.error('请选择信息再进行操作!');
		return false;
	}
	alertMsg.confirm("确认删除？",{
		okCall:function(){
			$("#tempEmpResignForm").attr("action","/hrm/transferOrder/deleteResign?pageNum=1&menuNo=278687&navTabId=hr0206");
			$("#tempEmpResignForm").submit();
		}
	});
}
//申请离职发令
function f_reqResign() {
	var $form = $("#tempEmpResignForm");	
	var validFlag = false ;
	$form.find(":checkbox[id='resignReqCKB']").each(function(index, checkBoxObj){	    
	    if(checkBoxObj.checked){
	      validFlag = true ;      
	    }	    
	});
	if(!validFlag){
		alertMsg.error('请选择信息再进行操作!');
		return false;
	}
	var params   = $("#tempEmpResignForm").serialize();
	$("#hr0206Link").html("离职发令申请");
	$("#importExcelDialog_hr0206").attr('href','/hrm/transferOrder/viewResignReqList?pageNum=1&'+params);
	$("#importExcelDialog_hr0206").attr('height', "500");
	$("#importExcelDialog_hr0206").attr('width', "900");
	$("#importExcelDialog_hr0206").click();
}
//撤销选中离职 (通过选中的人员/通过申请号)
function f_revokeResign(){
	var $form = $("#tempEmpResignForm");	
	var validFlag = false ;
	var chkObj = $form.find(":checkbox[id='revokeResignCKB']:checked");
	chkObj.each(function(index, checkBoxObj){ 
	      validFlag = true ; 
	});
	if(!validFlag){
		alertMsg.error('请选择信息再进行操作!');
		return false;
	}
	var chkedState = $("#STATE_"+chkObj[0].value).attr("value");
	chkObj.each(function(index, checkBoxObj){
		var temp = $("#STATE_"+checkBoxObj.value).attr("value");
		if(temp!=chkedState){ validFlag = false;}   
	});
	if(!validFlag){
		alertMsg.error('请选择相同状态行再进行操作!');
		return false;
	}
	var params   = $("#tempEmpResignForm").serialize();
	$("#hr0206Link").html("撤销离职发令申请");
	$("#importExcelDialog_hr0206").attr('href','/hrm/transferOrder/viewResignRevokeReqList?pageNum=1&STATE='+chkedState+'&'+params);
	$("#importExcelDialog_hr0206").attr('height', "500");
	$("#importExcelDialog_hr0206").attr('width', "900");
	$("#importExcelDialog_hr0206").click();
}
//修改按钮
function checkDelBtn(STATE){
	if(STATE==20){
		$("#btnDelResign").css("display","block");//将按钮可用
		$("#btnInqResign").css("display","none");		
	}else{
		$("#btnDelResign").css("display","none");
		$("#btnInqResign").css("display","block");//将按钮可用
	}
}
//撤消离职
function revokeResignCallback(form, callback) {	
	$form = $("#tempEmpResignForm");
	
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchResign");
				alertMsg.correct(data.message);			
			}else{
				alertMsg.info(data.message);				
			}
   	 	}  ,
		error: DWZ.ajaxError
	});	
	return false;
}
</script>
<a id="importExcelDialog_hr0206" href="#" width="400" height="200" target="dialog" mask="true"><span
		id="hr0206Link" style="display: none"></span></a> 
<a id="importExcel_hr0206" href="#" target="dialog" width="800" height="420" mask="true"><span
	style="display: none;">临时职离职导入结果</span></a>
<div class="pageHeader">
<form id="searchResign" name="searchResign" 
	action="/hrm/transferOrder/viewResign" 
	method="post" 
	onsubmit="return navTabSearch(this);" 
	rel="pagerForm">
<div class="searchBar">
<table class="searchContent">
		<tr>
				<td>法人
				</td>
				<td>				
					<input type="text" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${searchMap.CPNY_ID}" readonly/>
					<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
			    </td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResign_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResign_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResign_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResign_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input id="seach_EMPID" name="dwz.person.empId" type="text" 
						value="${searchMap.EMPID}" lookupGroup="person"
						onkeyup="this.value=this.value.toLocaleUpperCase().replace(/(^\s*)|(\s*$)/g, '')" 
						/>
					<input id="seach_PERSON_ID" name="dwz.person.personId" 
						type="hidden" value="" readOnly lookupGroup="person"
						/>
					<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				</td>
			</tr>
			<tr>
				<td>
					发令类型
				</td>					
				<td> 
                    <select name="seach_TRANS_CODE" id="seach_TRANS_CODE">
                        <option value="">请选择</option>
                        <option value="RESIGN" <c:if test="${searchMap.TRANS_CODE == 'RESIGN'}">selected</c:if>>离职</option>
                        <option value="RESIGNREVOKE" <c:if test="${searchMap.TRANS_CODE == 'RESIGNREVOKE'}">selected</c:if>>撤销离职 </option>
                    </select>
				</td>
				<td>离职日期：</td>
				<td>
					<input type="text" id="seach_ORDERDATEF" name="seach_ORDERDATEF" value="${searchMap.ORDERDATEF}" class="date" readonly="true" />
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				<td>
					<span class="span_left">~</span>
				</td>
				<td>
					<input type="text" id="seach_ORDERDATET" name="seach_ORDERDATET" value="${searchMap.ORDERDATET}" class="date" readonly="true" />
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="is.joininstance.title.statement"/>
				</td>
				<td> 
                    <select name="seach_STATE" id="seach_STATE">
                        <option value="">请选择</option>
                        <!-- option value="10" <c:if test="${searchMap.STATE == '10'}">selected</c:if>>暂存</option -->
                        <option value="20" <c:if test="${searchMap.STATE == '20'}">selected</c:if>>保存</option>
                        <option value="30" <c:if test="${searchMap.STATE == '30'}">selected</c:if>>提交</option>
                        <option value="40" <c:if test="${searchMap.STATE == '40'}">selected</c:if>>审批中</option>
                        <option value="50" <c:if test="${searchMap.STATE == '50'}">selected</c:if>>通过</option>
                        <option value="60" <c:if test="${searchMap.STATE == '60'}">selected</c:if>>否决</option>
                        <option value="70" <c:if test="${searchMap.STATE == '70'}">selected</c:if>>撤销中</option>
                        <option value="80" <c:if test="${searchMap.STATE == '80'}">selected</c:if>>已生效</option>
                        <option value="90" <c:if test="${searchMap.STATE == '90'}">selected</c:if>>已撤销</option>
                    </select>
				 </td>
			</tr>
</table>
<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
</div>
</div>
</form>
</div>

<div class="pageContent" style="padding:5px;">
	<div class="formBar">
			<ul>
				<li id="btnInqResign"><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.SELECTR == '1'}">
							<button type="button" onclick="f_inquiryResign()" >
								查看申请
							</button>
						</c:if>
					</div>
				</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.INSERTR == '1'}">
							<button type="button" onclick="f_addResign()">
								添加
							</button>
						</c:if>
					</div>
				</div></li>
				<li id="btnDelResign"><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.DELETER == '1'}">
							<button type="button" onclick="f_delResign()" >
								 删除
							</button>
						</c:if>
					</div>
				</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.INSERTR == '1'}">
							<button type="button" onclick="f_reqResign()" id="btnReqResign">
								离职发令申请
							</button>
						</c:if>
					</div>
				</div></li>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<c:if test="${toolbarInfo.INSERTR == '1'}">
								<a class="downloadExel" href="/hrm/transferOrder/downloadResignationTemplate?CPNY_ID=${searchMap.CPNY_ID}">						
								<span>
									<spring:message code="inct.salesman.downloadExcelTemplate" />
								</span>
								</a>
							</c:if>
						</div>
					</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.INSERTR == '1'}">
							<button type="button" onclick="importExcelTempEmpResign()">
								<spring:message code="inct.salesman.uploadExcel" />
								<!--上传excel-->
							</button>
						</c:if>
					</div>
				</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.INSERTR == '1'}">
							<button type="button" onclick="f_revokeResign()" id="btnRevokeResign">
								撤销
							</button>
						</c:if>
					</div>
				</div></li>
			</ul>
	</div>
	<form name="tempEmpResignForm" 
		id="tempEmpResignForm" 
		method="post" 
		action="/hrm/transferOrder/cancelResign"
	  	onsubmit="return revokeResignCallback(this, navTabAjaxDone);" 
	  	rel="pagerForm" >
		<table width="100%" class="table" layoutH="258">
		<thead>
			<tr >
				<th width="2%">
					<input type="checkbox" class="checkboxCtrl" group="resignReqCKB" />
				</th>
				<th width="15%" orderField="EMPID" class="${orderDirection}">
					[<spring:message code="hr.viewPersonalInfo.title.EMPID"/>]<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--社号-->
				</th>
				<th width="10%" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="8%" orderField="nlssort(EMP_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					人员类型
				</th>
				<th width="7%" orderField="nlssort(TRANS_CODE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令类型
				</th>
				<th width="7%" orderField="nlssort(RESIGN_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<!--离职日期-->
				</th>
				<th width="12%" orderField="nlssort(RESIGN_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<!--离职类型-->
				</th>
				<th width="12%" orderField="nlssort(RESIGN_REASON_DESC,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<!--离职原因-->
				</th>
				<th width="6%" orderField="nlssort(BLACKLIST_YN,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewResign.title.BLACKYN"/>
					<!--能否再入职-->
				</th>
				<th width="6%" orderField="nlssort(UPDATED_BY,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					操作者
				</th>
				<th width="6%" orderField="nlssort(CURRENT_AFFIRM_ID,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					审批人
				</th>
				<th width="4%" orderField="nlssort(STATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="is.joininstance.title.statement"/><!-- 状态  -->
				</th>
				<th width="5%">
					<input type="checkbox" class="checkboxCtrl" group="revokeResignCKB" />撤销
				</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resignList}" var="resign">
				<tr target="sid" 
					rel="REQ_ID=${resign.EXP_INSIDE_NO}&RESIGN_NO=${resign.RESIGN_NO}&STATE=${resign.STATE}&ACTIVITY=${resign.ACTIVITY}" 
					onclick="checkDelBtn('${resign.STATE}')">
					<td class='td_center'>	
						<c:if test="${resign.STATE eq 10 || resign.STATE eq 20}">
						<input type="checkbox" id="resignReqCKB" name="resignReqCKB" value="${resign.RESIGN_NO}" />
						</c:if>					
					</td>	
					<td>
						[${resign.EMPID}]${resign.LOCAL_NAME}
					</td>									
					<td>
						${resign.DEPT_NAME}
					</td>					
					<td>
						${resign.EMP_TYPE_NAME}
					</td>					
					<td class='td_center'>
						<c:if test="${resign.TRANS_CODE eq 'RESIGN'}"><font >离职</font></c:if>
						<c:if test="${resign.TRANS_CODE eq 'RESIGNREVOKE'}"><font >离职撤销</font></c:if>
					</td>									
					<td class='td_center'>
						${resign.RESIGN_DATE}
					</td>						
					<td>
						${resign.RESIGN_TYPE_NAME}
					</td>
					<td>
						${resign.RESIGN_REASON_DESC}
					</td>					
					<td class='td_center'>			
						${resign.BLACKLIST_YN_DESC}			
					</td>
					<td class='td_center'>
						${resign.UPDATED_BY_NM}
					</td>					
					<td class='td_center'>
						${resign.CURRENT_AFFIRM_NM}
					</td>						
					<td class='td_center'>
						 <c:if test="${resign.STATE eq '10'}"><font >暂存</font></c:if>
						 <c:if test="${resign.STATE eq '20'}"><font >保存</font></c:if>
						 <c:if test="${resign.STATE eq '30'}"><font >提交</font></c:if>						 
						 <c:if test="${resign.STATE eq '40'}"><font >审批中</font></c:if>
						 <c:if test="${resign.STATE eq '50'}"><font >通过</font></c:if>
						 <c:if test="${resign.STATE eq '60'}"><font >否决</font></c:if>
						 <c:if test="${resign.STATE eq '70'}"><font >撤销中</font></c:if>	
						 <c:if test="${resign.STATE eq '80'}"><font >已生效</font></c:if>	
						 <c:if test="${resign.STATE eq '90'}"><font >已撤销</font></c:if>	
						 <input type="hidden" id="STATE_${resign.RESIGN_NO}" name="STATE_${resign.RESIGN_NO}" value="${resign.STATE}"/>
					</td>
					<td class='td_center'>	
						<c:if test="${resign.REVOKE_FLAG eq 'Y'}">
							<input type="checkbox" id="revokeResignCKB" name="revokeResignCKB" value="${resign.RESIGN_NO}"/>
						</c:if>					
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>	
			<c:set value="/hrm/transferOrder/viewResign" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
