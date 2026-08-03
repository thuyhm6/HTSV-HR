<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackAddEmpTypeTr(form,callback,trCd){
	var $form = $("#addEmpTypeTrForm");	
	if (!$form.valid()) {
		return false;
	}
	var validFlag = false ;
	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){	    
	    if(checkBoxObj.checked){
	      validFlag = true ;      
	    }	    
	});
	if(!validFlag){
	 	//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      validFlag = true ;
	      var empid = $(checkBoxObj).val() ;
	      if(trCd=="278706"){//人员类型变更 
	    	  if($form.find("[name='NEW_EMP_TYPE_CODE_" + empid + "']").val()==$form.find("[name='EMP_TYPE_CODE_" + empid + "']").val()){
					alertMsg.error('人员类型未变更！');
					$form.find("[name='NEW_EMP_TYPE_CODE_" + empid + "']").focus();
					validFlag=false;
					return false;
			  }
	      }
		  if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				alertMsg.error('发令日期不可以为空！');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				validFlag=false;
				return false;
		  }
		  if($form.find("[name='TRANSFER_ORDER_REASON_" + empid + "']").val()==''){
				alertMsg.error('发令原因不可以为空！');
				$form.find("[name='TRANSFER_ORDER_REASON_" + empid + "']").focus();
				validFlag=false;
				return false;
		  }
	    }
	});
	if(validFlag){		
		//确定要提交吗？
		if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){				
		  	$.ajax({
				type: form.method || 'POST',
				url: $form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(result) {
					if (result.statusCode == 200) {
						$.pdialog.closeCurrent();
						navTabSearch("searchAddEmpTypeTrForm");
					}
					alert(result.message);
				},
				error: DWZ.ajaxError
			});				
			return false;
		}
	}	
	return false ;
}
function fillItemEmpTypeReq(){
	var $form = $("#addEmpTypeTrForm");
	if (!$form.valid()) {
		return false;
	}
	var checked = false ;
	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){	    
		if(checkBoxObj.checked){
	   		checked = true ;      
	  	}	    
	});
	if(!checked){
	 	alertMsg.error('请选择人员后再进行批量填充！');
		return false;
	}
 	var empTypeChgToBe	= $("#BATCH_NEW_EMP_TYPE_CODE_hr0516").val();
 	var empTypeChgReason= $("#BATCH_TRANSFER_ORDER_REASON_hr0516").val();
 	var empTypeChgDate  = $("#BATCH_START_DATE_hr0516").val(); 	
    if(empTypeChgToBe==null||empTypeChgToBe==""){
    	alertMsg.error('请选择现人员类型！');     
    } 
    if(empTypeChgReason==null||empTypeChgReason==""){
    	alertMsg.error('请输入发令原因！');     
    } 

  	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
  		if(checkBoxObj.checked){
  			var empid = $(checkBoxObj).val() ;
      	 	$form.find("select[name='NEW_EMP_TYPE_CODE_" + empid + "']").attr("value",empTypeChgToBe);
      	 	$form.find("[name='TRANSFER_ORDER_REASON_"+ empid +"']").attr("value",empTypeChgReason);
      	 	if(empTypeChgDate!=null && empTypeChgDate!=""){
      	 		$form.find("[name='START_DATE_" + empid + "']").attr("value",empTypeChgDate);
  			}
  		}
  	});
}
</script>
<div class="pageHeader">
	<form method="post" id="searchAddEmpTypeTrForm" name="searchAddEmpTypeTrForm" 
	action="/hrm/transferOrder/viewReguEmpTransferOrderAddList" 
	onsubmit="return dwzSearch(this,'dialog')" 
	rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="trCd" value="${trCd}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>法人:
				</td>
				<td>				
					<input type="text" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${interCpnyID}" disabled/>
			    </td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:
					<!-- 部门： -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewTrAdd_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewTrAdd_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewTrAdd_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewTrAdd_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>:
					<!--社号/姓名：-->
				</td>
				<td>
					<input id="seach_EMPID" name="seach_EMPID" 
						type="text" value="${searchMap.EMPID}"
						onkeyup="this.value=this.value.toLocaleUpperCase().replace(/(^\s*)|(\s*$)/g, '')" 
						/>					
				</td>
				<td>
					人员类型
				</td>
				<td>
					<ait:SelectSyCodeByCpnyID 
						name="seach_EMP_TYPE_CODE"  parentNo="1368" 
						selected="${searchMap.EMP_TYPE_CODE}"
						empTypeNonTempAct="Y"
						nullable="Y"
						limit="all"
						cnpyID="${searchMap.defaultCpny}"
					/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!-- 查询 -->
							<spring:message code="button.search"/>
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
	<form style="margin:0px;padding:0px;" id="addEmpTypeTrForm" name="addEmpTypeTrForm" 
	onsubmit="return validateCallbackAddEmpTypeTr(this,navTabAjaxDone,${searchMap.trCd});" 
	action="/hrm/transferOrder/saveEmpTypeTransferOrderUpgrade?TRANS_CODE=${searchMap.trCd}" 
	method="post" 
	class="pageForm required-validate">
	<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!--点击填充-->
							<button type="button" onclick="fillItemEmpTypeReq();">
								点击填充
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="hr.viewUpgrade.title.SAVE"/>
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" id="btnClose" name="btnClose" class="close">
							<spring:message code="public.title.cancle"/><!--取消--></button>
						</div>
					</div>
				</li>
		</ul>
	</div>
	<table class="user_table" width="100%" layoutH="130" cellspacing="0" cellpadding="0" >
		<thead>
			<tr >
				<td width="2%" class="td_title" style="text-align:center">
					<input type="checkbox" class="checkboxCtrl" group="trCKB" />
				</td>
				<td width="8%" class="td_title" style="text-align:center"
					orderField="EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</td>
				<td width="8%" class="td_title" style="text-align:center"
					orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</td>				
				<td width="30%" class="td_title" style="text-align:center">原人员类型</td>
				<td width="30%" class="td_title" style="text-align:center">现人员类型<font color="red">*</font></td>
				<td width="12%" class="td_title"  style="text-align:center"
					orderField="nlssort(TRANS_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令原因<font color="red">*</font>
				</td>
				<td width="10%" class="td_title"  style="text-align:center"
					orderField="nlssort(START_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令日期<font color="red">*</font>
				</td>
			</tr>
		</thead>
				<tr>					
					<td colspan="4">
					</td>					
					<td>
						<ait:SelectSyCodeByCpnyID 
							name="BATCH_NEW_EMP_TYPE_CODE_hr0516"  parentNo="1368" 
							selected="${trEmp.EMP_TYPE_CODE}"
							empTypeNonTempAct="Y"
							cnpyID="${searchMap.defaultCpny}"
						/>
					</td>
					<td>
						<input type="text" id="BATCH_TRANSFER_ORDER_REASON_hr0516" 
							name="BATCH_TRANSFER_ORDER_REASON_hr0516" 
							size="18" class="textInput"
							maxlength='30'/>
					</td>					
					<td>
						<input type="text" id="BATCH_START_DATE_hr0516" 
							name="BATCH_START_DATE_hr0516" 
							size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>								
				</tr>
		<tbody>
			<c:forEach items="${tempEmpList}" var="trEmp">
				<tr target="sid" rel="${resign.EMPID}">
					
					<td class='td_center'>
						<input type="checkbox" id="trCKB" name="trCKB" value="${trEmp.EMPID}" />
						<input type="hidden" name="PERSON_ID_${trEmp.EMPID}" value="${trEmp.PERSON_ID}"/>
					</td>					
					<td class='td_center'>
						${trEmp.EMPID}
					</td>					
					<td class='td_center'>
						${trEmp.LOCAL_NAME}
					</td>					
					<td>
						<input type="hidden" id="EMP_TYPE_CODE_${trEmp.EMPID}" 
						name="EMP_TYPE_CODE_${trEmp.EMPID}" 
						value="${trEmp.EMP_TYPE_CODE}"/>
						${trEmp.EMP_TYPE_NAME}	
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID 
							name="NEW_EMP_TYPE_CODE_${trEmp.EMPID}"  parentNo="1368" 
							selected="${trEmp.EMP_TYPE_CODE}"
							empTypeNonTempAct="Y"
							cnpyID="${searchMap.defaultCpny}"
						/>
					</td>
					<td>
						<input type="text" id="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							name="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							size="18" class="textInput"
							maxlength='30'/>
					</td>					
					<td>
						<input type="text" id="START_DATE_${trEmp.EMPID}" 
							name="START_DATE_${trEmp.EMPID}" 
							value="${trEmp.DEFAULT_START_DATE}"
							size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>								
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	</form>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewReguEmpTransferOrderAddList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>