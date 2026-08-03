<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//新增调动发令
function f_addTransferOrder() {
	var trCd=$('#searchTransferOrderForm_TRANS_CODE').val();
	if(trCd == ''){
		alertMsg.info('请选择发令类型！');
		return false;
	}
	$("#hr0515Link").html("添加");
	$("#importExcelDialog_hr0515").attr('href','/hrm/transferOrder/viewTempEmpTransferOrderAddList?pageNum=1&trCd='+trCd);
	$("#importExcelDialog_hr0515").attr('height', "500");
	$("#importExcelDialog_hr0515").attr('width', "1100");
	$("#importExcelDialog_hr0515").click();
}
//修改调动发令
function f_editTransferOrder() {
	$("#hr0515Link").html("修改");
	$("#importExcelDialog_hr0515").attr('href','/hrm/transferOrder/viewTempEmpTransferOrderEditList?pageNum=1&{sid}');
	$("#importExcelDialog_hr0515").attr('height', "500");
	$("#importExcelDialog_hr0515").attr('width', "1100");
	$("#importExcelDialog_hr0515").click();
}
//删除未生效发令
function f_delTransferOrderInBatch(){
	$form = $("#updateTransForm");	
	var validFlag = false ;
	var chkObj = $form.find(":checkbox[id='hr0515DelTempTROCKB']:checked");
	chkObj.each(function(index, checkBoxObj){ 
	      validFlag = true ; 
	});
	if(!validFlag){
		alertMsg.error('请选择信息再进行操作!');
		return false;
	}
	$form.submit();
}
//删除未生效发令
function cancelTransferOrderCallback(form, callback) {	
	$form = $("#updateTransForm");
	
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
				alertMsg.info(data.message);			
			}else{
				alertMsg.info(data.message);				
			}
			navTabSearch("searchTransferOrderForm");
   	 	}  ,
		error: DWZ.ajaxError
	});	
	return false;
}
//导入数据
function importExcelTempEmpTransferOrder() {
	$("#importExcelDialog_hr0515").attr('href',
					'/pa/excelImport/importExcelData?importFunName=/importExcelTempEmpTransferOrderData');
	$("#importExcelDialog_hr0515").attr('height', "200");
	$("#importExcelDialog_hr0515").attr('width', "400");
	$("#importExcelDialog_hr0515").click();
}
//修改按钮
function checkTempEmpTransferOrderEditBtn(activity){
	if(activity==0){
		$("#btnEditTempEmpTransferOrder").removeAttr("disabled");//将按钮可用
	}else{
		$("#btnEditTempEmpTransferOrder").attr("disabled","true");
	}
}
//查询明细
function f_InquiryTempEmpTransferOrder(){
	$("#hr0515Link").html("查看详细");
	$("#importExcelDialog_hr0515").attr('href','/hrm/transferOrder/viewTempEmpTransferOrderDetail?{sid}');
	$("#importExcelDialog_hr0515").attr('height', "330");
	$("#importExcelDialog_hr0515").attr('width', "500");
	$("#importExcelDialog_hr0515").click();
}
</script>
<a id="importExcelDialog_hr0515" href="#" width="400" height="200" target="dialog" mask="true"><span
		id="hr0515Link" style="display: none"></span></a> 
<a id="importExcel_hr0515" href="#" target="dialog" width="800" height="420" mask="true"><span
	style="display: none;">临时职发令导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" 
		action="/hrm/transferOrder/viewTempEmpTransferOrderList"
		rel="pagerForm" method="post" 
		id="searchTransferOrderForm" 
		name="searchTransferOrderForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td>法人
				</td>
				<td>				
					<input type="text" id="searchTransferOrderForm_CPNY_ID" 
						name="seach_CPNY_ID" value="${searchMap.defaultCpny}" readonly/>
					<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
			    </td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" 
						limit="super" id="searchTransferOrderForm_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" 
						limit="super" id="searchTransferOrderForm_seachDept" 
						selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" 
						limit="hr" id="searchTransferOrderForm_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" 
						limit="hr" id="searchTransferOrderForm_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
				</td>	
                <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input id="searchTransferOrderForm_EMPID" name="dwz.person.empId" type="text" 
						value="${searchMap.EMPID}" lookupGroup="person"						
						onkeyup="this.value=this.value.toLocaleUpperCase().replace(/(^\s*)|(\s*$)/g, '')" 
						/>
					<input id="searchTransferOrderForm_PERSON_ID" name="dwz.person.personId" 
						type="hidden" value="" readOnly lookupGroup="person"
						/>
					<a class="btnLook" style="float:right;" 
						href="/ar/attendanceSettings/viewKeeperList?pageNum=1" 
						width="900" height="400" lookupGroup="person">
						<!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/>
						</a>
				</td>
			</tr>
			<tr>
				<td>
					发令类型
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" 
				     	id="searchTransferOrderForm_TRANS_CODE"
				     	parentNo="1365" 
				     	cnpyID="${searchMap.defaultCpny}" selected="${searchMap.TRANS_CODE}" 
				     	limit="all"/>       
				</td>	
                <td>发令日期：</td>
				<td>
					<input type="text" id="searchTransferOrderForm_ORDERDATEF" 
						name="seach_ORDERDATEF" value="${searchMap.ORDERDATEF}" 
						class="date" readonly="true" />
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				<td>
					<span class="span_left">~</span>
				</td>
				<td>
					<input type="text" id="searchTransferOrderForm_ORDERDATET" 
						name="seach_ORDERDATET" value="${searchMap.ORDERDATET}" class="date" readonly="true" />
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>			 
			</tr>
			<tr>
				<td>
					是否生效
				</td>					
				<td> 
                    <select name="seach_ACTIVITY" id="searchTransferOrderForm_ACTIVITY">
                        <option value="">请选择</option>
                        <option value="0" <c:if test="${searchMap.ACTIVITY == '0'}">selected</c:if>>未生效</option>
                        <option value="1" <c:if test="${searchMap.ACTIVITY == '1'}">selected</c:if>>已生效 </option>
                        <option value="2" <c:if test="${searchMap.ACTIVITY == '2'}">selected</c:if>>已删除</option>
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

<div class="pageContent">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="f_InquiryTempEmpTransferOrder()">
							查看详细
						</button>
					</div>
				</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.INSERTR == '1'}">
							<button type="button" onclick="f_addTransferOrder()">
								添加
							</button>
						</c:if>
					</div>
				</div></li>
				<!-- li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<button type="button" onclick="f_editTransferOrder()" id="btnEditTempEmpTransferOrder">
								修改
							</button>
						</c:if>
					</div>
				</div></li-->
				<li><div class="buttonActive">
						<div class="buttonContent">
							<c:if test="${toolbarInfo.INSERTR == '1'}">
								<a class="downloadExel" href="/hrm/transferOrder/downloadTempEmpTransferOrderTemplate">						
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
							<button type="button" onclick="importExcelTempEmpTransferOrder()">
								<spring:message code="inct.salesman.uploadExcel" />
								<!--上传excel-->
							</button>
						</c:if>
					</div>
				</div></li>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<button type="button" onclick="f_delTransferOrderInBatch()" id="btnDelTransferOrderInBatch">
								删除
							</button>
						</c:if>
					</div>
				</div></li>
		</div>
<form name="updateTransForm" id="updateTransForm" method="post" 
	action="/hrm/transferOrder/cancelTransferOrderInBatch"
	  onsubmit="return cancelTransferOrderCallback(this, navTabAjaxDone);" rel="pagerForm">
 	<table class="table" width="100%" layoutH="260" nowrapTD="false">      
		<thead>
			<tr>
				<th width="8%" 
					orderField="EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="8%"
					orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="15%"
					orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					部门
				</th>
				<th width="10%"
					orderField="nlssort(POSITION_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					职责
				</th>
				<th width="8%" 
					orderField="nlssort(TRANS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令类型
				</th>
				<th width="10%" 
					orderField="nlssort(START_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令日期
				</th>
				<th width="13%" 
					orderField="nlssort(TRANSFER_ORDER_REASON,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令事由
				</th>	
				<th width="9%" 
					orderField="nlssort(UPDATED_BY,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
					<!--操作者-->
				</th>
				<th width="8%" 
					orderField="nlssort(UPDATE_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					操作日期
				</th>	
				<th width="6%"
					orderField="nlssort(ACTIVITY,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
					<!--是否生效-->
				</th>
				<th width="5%">
					<input type="checkbox" class="checkboxCtrl" group="hr0515DelTempTROCKB" />删除
				</th>
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${transList}" var="hrExpInside" varStatus="i">			
				<tr target="sid" rel="PERSON_ID=${hrExpInside.PERSON_ID}
										&EMPID=${hrExpInside.EMPID}
										&TRANS_CODE=${hrExpInside.TRANS_CODE}
										&DEPTMENTNO=${hrExpInside.DEPTNO}
										&EXP_INSIDE_NO=${hrExpInside.EXP_INSIDE_NO}
										&ACTIVITY=${hrExpInside.ACTIVITY}" 
								onclick="checkTempEmpTransferOrderEditBtn('${hrExpInside.ACTIVITY}')"
									>
					<td class='td_center'>${hrExpInside.EMPID}</td>
					<td class='td_center'>${hrExpInside.LOCAL_NAME}</td>
					<td>${hrExpInside.DEPT_NAME}</td>
					<td>${hrExpInside.POSITION_NAME}</td>
					<td class='td_center'>${hrExpInside.TRANS_NAME}</td>
					<td class='td_center'>${hrExpInside.START_DATE}</td>
					<td>${hrExpInside.TRANSFER_ORDER_REASON}</td>
					<td class='td_center'>${hrExpInside.UPDATED_BY}</td>
					<td class='td_center'>${hrExpInside.UPDATE_DATE}</td>																		                                          
			        <td class='td_center'>	
			        	<c:if test="${hrExpInside.ACTIVITY eq '0'}"><font color="red">未生效</font></c:if>		    
			            <c:if test="${hrExpInside.ACTIVITY eq '1'}"><font color="green">已生效</font></c:if>
						<c:if test="${hrExpInside.ACTIVITY eq '2'}"><font color="black">已删除</font></c:if>
			        </td>
			        <td class='td_center'>
			        	<c:if test="${hrExpInside.DEL eq 'Y'}">
			        		<input type="checkbox" id="hr0515DelTempTROCKB" name="hr0515DelTempTROCKB" value="${hrExpInside.EXP_INSIDE_NO}"/>
			        	</c:if>
			        </td>		
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</form>	
	<c:set value="/hrm/transferOrder/viewTempEmpTransferOrderList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
