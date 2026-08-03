<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackAddTransferOrder(form,callback,trCd){
	var $form = $("#addTempEmpTrForm");	
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
	      if(trCd=="15861"){//部门变更 
	    	  if($form.find("[name='NEW_DEPTMENTNO_" + empid + "']").val()==$form.find("[name='DEPTMENTNO_" + empid + "']").val()){
					alertMsg.error('部门信息未变更！');
					$form.find("[name='NEW_DEPTMENTNO_" + empid + "']").focus();
					validFlag=false;
			  }
	    	  if($form.find("[name='NEW_DEPTMENTNO_" + empid + "']").val()==''){
					alertMsg.error('部门信息考贝后请按回车键！');
					$form.find("[name='NEW_DEPTMENTNO_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="278706"){//人员类型变更 
	    	  if($form.find("[name='NEW_EMP_TYPE_CODE_" + empid + "']").val()==$form.find("[name='EMP_TYPE_CODE_" + empid + "']").val()){
					alertMsg.error('人员类型未变更！');
					$form.find("[name='NEW_EMP_TYPE_CODE_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="278705"){//班号变更
	    	  if($form.find("[name='NEW_SHIFT_NO_" + empid + "']").val()==$form.find("[name='SHIFT_NO_" + empid + "']").val()){
					alertMsg.error('班号未变更！');
					$form.find("[name='NEW_SHIFT_NO_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="278720"){//职责变更
	    	  if($form.find("[name='NEW_POSITION_NO_" + empid + "']").val()==$form.find("[name='POSITION_NO_" + empid + "']").val()){
					alertMsg.error('职责未变更！');
					$form.find("[name='NEW_POSITION_NO_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="278704"){//级号变更
	    	  if($form.find("[name='NEW_PAY_GRADE_" + empid + "']").val()==$form.find("[name='PAY_GRADE_" + empid + "']").val()
	    		&& $form.find("[name='NEW_PAY_STEP_" + empid + "']").val()==$form.find("[name='PAY_STEP_" + empid + "']").val()
	    		&& $form.find("[name='NEW_BASE_PAY_" + empid + "']").val()==$form.find("[name='BASE_PAY_" + empid + "']").val()
	    		&& $form.find("[name='NEW_VARB_PAY_" + empid + "']").val()==$form.find("[name='VARB_PAY_" + empid + "']").val()
	    		&& $form.find("[name='NEW_ANSAL_" + empid + "']").val()==$form.find("[name='ANSAL_" + empid + "']").val()
	    			  ){
					alertMsg.error('级号信息未变更！');
					$form.find("[name='NEW_PAY_GRADE_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="14013588"){//ID卡号变更 
	    	  if($form.find("[name='NEW_ID_CARD_NO_" + empid + "']").val()==$form.find("[name='ID_CARD_NO_" + empid + "']").val()){
					alertMsg.error('ID卡号未变更！');
					$form.find("[name='NEW_ID_CARD_NO_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="14013616"){//职务变更 
	    	  if($form.find("[name='NEW_JOB_TITLE_CD_" + empid + "']").val()==$form.find("[name='JOB_TITLE_CD_" + empid + "']").val()){
					alertMsg.error('职务未变更！');
					$form.find("[name='NEW_JOB_TITLE_CD_" + empid + "']").focus();
					validFlag=false;
			  }
	      }
		  if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				alertMsg.error('发令日期不可以为空！');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				validFlag=false;
		  }
		  if($form.find("[name='TRANSFER_ORDER_REASON_" + empid + "']").val()==''){
				alertMsg.error('发令原因不可以为空！');
				$form.find("[name='TRANSFER_ORDER_REASON_" + empid + "']").focus();
				validFlag=false;
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
						navTabSearch("searchTempEmpTrAddForm");
					}
					alertMsg.info(result.message);
				},
				error: DWZ.ajaxError
			});				
			return false;
		}
	}	
	return false ;
}
//点击填充
function fillItemByBatch(){
	var $form = $("#addTempEmpTrForm");
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
	var trCd	= document.searchTempEmpTrAddForm.trCd.value;
	var chgReason= $("#BATCH_TRANSFER_ORDER_REASON_hr0515").val();
 	var chgDate  = $("#BATCH_START_DATE_hr0515").val();
	if(trCd=="15861"){//部门变更 
		var deptNoChgToBe	= $("#BATCH_DEPTMENTNO_hr0515").val();
		var deptNmChgToBe	= $("#BATCH_DEPTMENTNM_hr0515").val();
		if(deptNoChgToBe==null||deptNoChgToBe==""){
	    	alertMsg.error('请选择现部门！'); 
	    	return;
	    } 
	  	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("[id='NEW_DEPTMENTNO_" + empid + "']").attr("value",deptNoChgToBe);
	      	 	$form.find("[id='NEW_DEPTMENTNM_" + empid + "']").attr("value",deptNmChgToBe);
	      	 }
	  	});
    }else if(trCd=="278706"){//人员类型变更 
    	var empTypeChgToBe	= $("#BATCH_EMP_TYPE_CODE_hr0515").val();
    	if(empTypeChgToBe==null||empTypeChgToBe==""){
	    	alertMsg.error('请选择现人员类型！'); 
	    	return;
	    } 
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("select[name='NEW_EMP_TYPE_CODE_" + empid + "']").attr("value",empTypeChgToBe);
	      	 }
	  	});
    }else if(trCd=="278705"){//班号变更
    	var shiftNoChgToBe	= $("#BATCH_SHIFT_NO_hr0515").val();
    	if(shiftNoChgToBe==null||shiftNoChgToBe==""){
	    	alertMsg.error('请选择现班号！');   
	    	return;
	    } 
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("select[name='NEW_SHIFT_NO_" + empid + "']").attr("value",shiftNoChgToBe);
	      	 }
	  	});
    }else if(trCd=="278720"){//职责变更
    	var positionNoChgToBe	= $("#BATCH_POSITION_NO_hr0515").val();
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("select[name='NEW_POSITION_NO_" + empid + "']").attr("value",positionNoChgToBe);
	      	 }
	  	});
    	
    }else if(trCd=="278704"){//级号变更
    	var payGradeChgToBe	= $("#BATCH_PAY_GRADE_hr0515").val();
     	var payStepChgToBe	= $("#BATCH_PAY_STEP_hr0515").val();
     	var basePayChgToBe	= $("#BATCH_BASE_PAY_hr0515").val();
     	var varbPayChgToBe	= $("#BATCH_VARB_PAY_hr0515").val();
     	var ansalChgToBe	= $("#BATCH_ANSAL_hr0515").val();
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("select[name='NEW_PAY_GRADE_" + empid + "']").attr("value",payGradeChgToBe);
	      	 	$form.find("select[name='NEW_PAY_STEP_" + empid + "']").attr("value",payStepChgToBe);
	      	 	$form.find("input[name='NEW_BASE_PAY_" + empid + "']").attr("value",basePayChgToBe);
	      	 	$form.find("input[name='NEW_VARB_PAY_" + empid + "']").attr("value",varbPayChgToBe);
	      	 	$form.find("input[name='NEW_ANSAL_" + empid + "']").attr("value",ansalChgToBe);
	      	 }
	  	});
    } else if(trCd=="14013588"){//ID卡号变更
     	var idCardNoChgToBe	= $("#BATCH_ID_CARD_NO_hr0515").val();
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("input[name='NEW_ID_CARD_NO_" + empid + "']").attr("value",idCardNoChgToBe);
	      	 }
	  	});
    }  else if(trCd=="14013616"){//职务变更
     	var jobTitleCdChgToBe	= $("#BATCH_JOB_TITLE_CD_hr0515").val();
    	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
	  		if(checkBoxObj.checked){
	  			var empid = $(checkBoxObj).val() ;
	      	 	$form.find("select[name='NEW_JOB_TITLE_CD_" + empid + "']").attr("value",jobTitleCdChgToBe);
	      	 }
	  	});
    }   
  	$form.find(":checkbox[id='trCKB']").each(function(index, checkBoxObj){
  		if(checkBoxObj.checked){
  			var empid = $(checkBoxObj).val() ;
      	 	$form.find("[name='TRANSFER_ORDER_REASON_"+ empid +"']").attr("value",chgReason);
      	 	$form.find("[name='START_DATE_" + empid + "']").attr("value",chgDate);
  		}
  	});
}
</script>
<div class="pageHeader">
	<form method="post" id="searchTempEmpTrAddForm" name="searchTempEmpTrAddForm" 
	action="/hrm/transferOrder/viewTempEmpTransferOrderAddList" 
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
	<form style="margin:0px;padding:0px;" id="addTempEmpTrForm" name="addTempEmpTrForm" 
	onsubmit="return validateCallbackAddTransferOrder(this,navTabAjaxDone,${searchMap.trCd});" 
	action="/hrm/transferOrder/saveTransferOrderUpgrade?SAVETYPE=ADD&MGT_TYPE=TEMPEMP&TRANS_CODE=${searchMap.trCd}" 
	method="post" 
	class="pageForm required-validate">
	<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!--点击填充-->
							<button type="button" onclick="fillItemByBatch();">
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
				<c:choose>
					<c:when test="${searchMap.trCd == 15861 }">
						<td width="30%" class="td_title" style="text-align:center">原部门</td>
						<td width="30%" class="td_title" style="text-align:center">现部门<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 278706 }">
						<td width="30%" class="td_title" style="text-align:center">原人员类型</td>
						<td width="30%" class="td_title" style="text-align:center">现人员类型<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 278705 }">
						<td width="30%" class="td_title" style="text-align:center">原班号</td>
					    <td width="30%" class="td_title" style="text-align:center">现班号<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 278720 }">
						<td width="30%" class="td_title" style="text-align:center">原职责</td>
					    <td width="30%" class="td_title" style="text-align:center">现职责<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 278704 }">
					    <td width="6%" class="td_title" style="text-align:center">原级号</td>
					    <td width="6%" class="td_title" style="text-align:center">原级号等级</td>
					    <td width="6%" class="td_title" style="text-align:center">原基本工资</td>
					    <td width="6%" class="td_title" style="text-align:center">原变动工资</td>
					    <td width="6%" class="td_title" style="text-align:center">原年薪</td>
					    <td width="6%" class="td_title" style="text-align:center">现级号<font color="red">*</font></td>
					    <td width="6%" class="td_title" style="text-align:center">现级号等级<font color="red">*</font></td>
					    <td width="6%" class="td_title" style="text-align:center">现基本工资<font color="red">*</font></td>
					    <td width="6%" class="td_title" style="text-align:center">现变动工资<font color="red">*</font></td>
					    <td width="6%" class="td_title" style="text-align:center">现年薪<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013588 }">
						<td width="30%" class="td_title" style="text-align:center">原ID卡号</td>
						<td width="30%" class="td_title" style="text-align:center">现ID卡号<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013616 }">
						<td width="30%" class="td_title" style="text-align:center">原职务</td>
					    <td width="30%" class="td_title" style="text-align:center">现职务<font color="red">*</font></td>
					</c:when>
				</c:choose>
				<td width="12%" class="td_title" style="text-align:center"
					orderField="nlssort(TRANS_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令原因<font color="red">*</font>
				</td>
				<td width="10%" class="td_title" style="text-align:center"
					orderField="nlssort(START_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令日期<font color="red">*</font>
				</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4">
				</td>
					<c:choose>
					<c:when test="${searchMap.trCd == 15861 }">	
						<td>						
							<ait:deptListInTable name="BATCH_DEPTMENTNO_hr0515"  
								cpnyId="${searchMap.defaultCpny}" limit="hr" 
								id="BATCH_DEPTMENTNM_hr0515"/>
							<ait:deptTreeIcon name="BATCH_DEPTMENTNO_hr0515"  
								cpnyId="${searchMap.defaultCpny}" limit="hr" 
								selected="${trEmp.DEPTNO}"
								id="BATCH_DEPTMENTNM_hr0515"/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 278706 }">	
						<td>						
							<ait:SelectNonSyCodeByCpnyID name="BATCH_EMP_TYPE_CODE_hr0515" 
					     	codeType="TEMP_EMP_TYPE" 
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.EMP_TYPE_CODE}"
					     	/>
					     </td>
					</c:when>
					<c:when test="${searchMap.trCd == 278705 }">
						<td>	
					    	<ait:SelectNonSyCodeByCpnyID name="BATCH_SHIFT_NO_hr0515" 
					     	codeType="SHIFT_NO" 
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.SHIFT_NO}"
					     	/>   
					     </td>
					</c:when>
					<c:when test="${searchMap.trCd == 278720 }">
						<td>	
					    	<ait:SelectNonSyCodeByCpnyID name="BATCH_POSITION_NO_hr0515" 
					     	codeType="POSITION_NO" 
					     	limit="all"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.POSITION_NO}"
					     	/>   
					     </td>
					</c:when>
					<c:when test="${searchMap.trCd == 278704 }">
						<td colspan="4">
						</td>
						<td>	
							<ait:SelectNonSyCodeByCpnyID name="BATCH_PAY_GRADE_hr0515" 
					     	codeType="PAY_GRADE" 
					     	selected="${trEmp.PAY_GRADE}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"
					     	/>   
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="BATCH_PAY_STEP_hr0515" 
					     	codeType="PAY_STEP" 
					     	selected="${trEmp.PAY_STEP}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"
					     	/>   
						</td>
						<td>
							<input type="text" id="BATCH_BASE_PAY_hr0515" 
							name="BATCH_BASE_PAY_hr0515" 
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='30'/>
						</td>
						<td>
							<input type="text" id="BATCH_VARB_PAY_hr0515" 
							name="BATCH_VARB_PAY_hr0515" 
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='30'/>
						</td>
						<td>
							<input type="text" id="BATCH_ANSAL_hr0515" 
							name="BATCH_ANSAL_hr0515" 
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='30'/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013588 }">	
						<td>
							<input type="text" id="BATCH_ID_CARD_NO_hr0515" 
							name="BATCH_ID_CARD_NO_hr0515" 
							size="18" class="textInput"
							maxlength='30'/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013616 }">	
						<td>
							<ait:ComboSyCodeDescByCpnyID name="BATCH_JOB_TITLE_CD_hr0515"
							id="BATCH_JOB_TITLE_CD_hr0515" parentNo="14013573" limit="all"
							cnpyID="${searchMap.defaultCpny}"/> 	
						</td>
					</c:when>
					</c:choose>	
					<td>
						<input type="text" id="BATCH_TRANSFER_ORDER_REASON_hr0515" 
							name="BATCH_TRANSFER_ORDER_REASON_hr0515" 
							size="18" class="textInput"
							maxlength='30'/>
					</td>					
					<td>
						<input type="text" id="BATCH_START_DATE_hr0515" 
							name="BATCH_START_DATE_hr0515" 
							size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>								
				</tr>
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
					<c:choose>
					<c:when test="${searchMap.trCd == 15861 }">
						<td>
							<input type="hidden" id="DEPTMENTNO_${trEmp.EMPID}" 
							name="DEPTMENTNO_${trEmp.EMPID}" 
							value="${trEmp.DEPTNO}"/>
							[${trEmp.DEPTNO}]${trEmp.DEPT_NAME}							
						</td>
						<td>
							<ait:deptListInTable name="NEW_DEPTMENTNO_${trEmp.EMPID}"  
								cpnyId="${searchMap.defaultCpny}" limit="hr" 
								id="NEW_DEPTMENTNM_${trEmp.EMPID}"/>
							<ait:deptTreeIcon name="NEW_DEPTMENTNO_${trEmp.EMPID}"  
								cpnyId="${searchMap.defaultCpny}" limit="hr" 
								selected="${trEmp.DEPTNO}"
								id="NEW_DEPTMENTNM_${trEmp.EMPID}"/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 278706 }">
						<td>
							<input type="hidden" id="EMP_TYPE_CODE_${trEmp.EMPID}" 
							name="EMP_TYPE_CODE_${trEmp.EMPID}" 
							value="${trEmp.EMP_TYPE_CODE}"/>
							${trEmp.EMP_TYPE_NAME}	
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="NEW_EMP_TYPE_CODE_${trEmp.EMPID}" 
					     	codeType="TEMP_EMP_TYPE" 
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.EMP_TYPE_CODE}"
					     	/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 278705 }">
						<td>
							<input type="hidden" id="SHIFT_NO_${trEmp.EMPID}" 
							name="SHIFT_NO_${trEmp.EMPID}" 
							value="${trEmp.SHIFT_NO}"/>
							${trEmp.SHIFT_NO}
						</td>
					    <td>
					    	<ait:SelectNonSyCodeByCpnyID name="NEW_SHIFT_NO_${trEmp.EMPID}" 
					     	codeType="SHIFT_NO" 
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.SHIFT_NO}"
					     	/>   
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 278720 }">
						<td>
							<input type="hidden" id="POSITION_NO_${trEmp.EMPID}" 
							name="POSITION_NO_${trEmp.EMPID}" 
							value="${trEmp.POSITION_NO}"/>
							${trEmp.POSITION_NAME}
						</td>
					    <td>
					    	<ait:SelectNonSyCodeByCpnyID name="NEW_POSITION_NO_${trEmp.EMPID}" 
					     	codeType="POSITION_NO" 
					     	limit="all"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	selected="${trEmp.POSITION_NO}"
					     	/>   
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 278704 }">
					    <td>
					    	<input type="hidden" id="PAY_GRADE_${trEmp.EMPID}" 
							name="PAY_GRADE_${trEmp.EMPID}" 
							value="${trEmp.PAY_GRADE}"/>
							${trEmp.PAY_GRADE}
						</td>
					    <td>
					    	<input type="hidden" id="PAY_STEP_${trEmp.EMPID}" 
							name="PAY_STEP_${trEmp.EMPID}" 
							value="${trEmp.PAY_STEP}"/>
							${trEmp.PAY_STEP}
						</td>
						<td>
							<input type="hidden" id="BASE_PAY_${trEmp.EMPID}" 
							name="BASE_PAY_${trEmp.EMPID}" 
							value="${trEmp.BASE_PAY}"/>
							${trEmp.BASE_PAY}
						</td>
						<td>
							<input type="hidden" id="VARB_PAY_${trEmp.EMPID}" 
							name="VARB_PAY_${trEmp.EMPID}" 
							value="${trEmp.VARB_PAY}"/>
							${trEmp.VARB_PAY}
						</td>
						<td>
							<input type="hidden" id="ANSAL_${trEmp.EMPID}" 
							name="ANSAL_${trEmp.EMPID}" 
							value="${trEmp.ANSAL}"/>
							${trEmp.ANSAL}
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="NEW_PAY_GRADE_${trEmp.EMPID}" 
					     	codeType="PAY_GRADE" 
					     	selected="${trEmp.PAY_GRADE}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"
					     	/>   
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="NEW_PAY_STEP_${trEmp.EMPID}" 
					     	codeType="PAY_STEP" 
					     	selected="${trEmp.PAY_STEP}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"
					     	/>   
						</td>
						<td>
							<input type="text" id="NEW_BASE_PAY_${trEmp.EMPID}" 
							name="NEW_BASE_PAY_${trEmp.EMPID}" 
							value="${trEmp.BASE_PAY}"
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='30'/>
						</td>
						<td>
							<input type="text" id="NEW_VARB_PAY_${trEmp.EMPID}" 
							name="NEW_VARB_PAY_${trEmp.EMPID}" 
							value="${trEmp.VARB_PAY}"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							size="10" class="textInput"
							maxlength='30'/>
						</td>
						<td>
							<input type="text" id="NEW_ANSAL_${trEmp.EMPID}" 
							name="NEW_ANSAL_${trEmp.EMPID}" 
							value="${trEmp.ANSAL}"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							size="10" class="textInput"
							maxlength='30'/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013588 }">
						<td>
							<input type="hidden" id="ID_CARD_NO_${trEmp.EMPID}" 
							name="ID_CARD_NO_${trEmp.EMPID}" 
							value="${trEmp.ID_CARD_NO}"/>
							${trEmp.ID_CARD_NO}						
						</td>
						<td>
							<input type="text" id="NEW_ID_CARD_NO_${trEmp.EMPID}" 
							name="NEW_ID_CARD_NO_${trEmp.EMPID}" 
							value="${trEmp.ID_CARD_NO}"
							size="18" class="textInput"
							maxlength='30'/>
						</td>
					</c:when>
					<c:when test="${searchMap.trCd == 14013616 }">
						<td>
							<input type="hidden" id="JOB_TITLE_CD_${trEmp.EMPID}" 
							name="JOB_TITLE_CD_${trEmp.EMPID}" 
							value="${trEmp.JOB_TITLE_CD}"/>
							${trEmp.JOB_TITLE_CD}						
						</td>
						<td>
							<ait:ComboSyCodeDescByCpnyID name="NEW_JOB_TITLE_CD_${trEmp.EMPID}"
							id="NEW_JOB_TITLE_CD_${trEmp.EMPID}" parentNo="14013573"
							selected="${trEmp.JOB_TITLE_CD}"
							cnpyID="${searchMap.defaultCpny}"/> 	
						</td>
					</c:when>
					</c:choose>	
					<td>
						<input type="text" id="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							name="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							size="18" class="textInput"
							maxlength='30'/>
					</td>					
					<td>
						<input type="text" id="START_DATE_${trEmp.EMPID}" 
							name="START_DATE_${trEmp.EMPID}" 
							size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>								
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	</form>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewTempEmpTransferOrderAddList">
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