<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackEditTransferOrder(form,callback,trCd){
	var $form = $("#editReguEmpTrForm");	
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
	    	  if($form.find("[name='NEW_DEPTMENTNO_" + empid + "']").val()==''){
					alertMsg.error('部门信息未变更！');
					$form.find("[name='NEW_DEPTMENTNO_" + empid + "']").focus();
					validFlag=false;
			  }
	      }else if(trCd=="278706"){//人员类型变更 
	    	  if($form.find("[name='NEW_EMP_TYPE_CODE_" + empid + "']").val()==''){
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
	      }else if(trCd=="278704"){//级号变更
	    	  var newAnsal = $form.find("[name='NEW_ANSAL_" + empid + "']").val().replace(/(^\s*)|(\s*$)/g, '');
	    	  var newBasePay = $form.find("[name='NEW_BASE_PAY_" + empid + "']").val().replace(/(^\s*)|(\s*$)/g, '');
	    	  var newVarbPay = $form.find("[name='NEW_VARB_PAY_" + empid + "']").val().replace(/(^\s*)|(\s*$)/g, '');
	    	  if(newAnsal     == $form.find("[name='ANSAL_" + empid + "']").val()
	    		&& newBasePay == $form.find("[name='BASE_PAY_" + empid + "']").val()
	    		&& newVarbPay == $form.find("[name='VARB_PAY_" + empid + "']").val()
	    		&& $form.find("[name='NEW_PAY_GRADE_" + empid + "']").val()==$form.find("[name='PAY_GRADE_" + empid + "']").val()
	    		&& $form.find("[name='NEW_PAY_STEP_" + empid + "']").val()==$form.find("[name='PAY_STEP_" + empid + "']").val()
	    			  ){
					alertMsg.error('级号信息未变更！');
					$form.find("[name='NEW_ANSAL_" + empid + "']").focus();
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
						navTabSearch("searchReguEmpTrEditForm");
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

</script>
<div class="pageHeader">
	<form method="post" id="searchReguEmpTrEditForm" name="searchReguEmpTrEditForm" 
	action="/hrm/transferOrder/viewReguEmpTransferOrderEditList" 
	onsubmit="return dwzSearch(this,'dialog')" 
	rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="TRANS_CODE" value="${TRANS_CODE}" />
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
	<form style="margin:0px;padding:0px;" id="editReguEmpTrForm" name="editReguEmpTrForm" 
	onsubmit="return validateCallbackEditTransferOrder(this,navTabAjaxDone,${searchMap.TRANS_CODE});" 
	action="/hrm/transferOrder/saveTransferOrderUpgrade?SAVETYPE=EDIT&MGT_TYPE=REGUEMP&TRANS_CODE=${searchMap.TRANS_CODE}" 
	method="post" 
	class="pageForm required-validate">
	<div class="formBar">
			<ul>
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
				<td width="2%" class="td_title">
					<input type="checkbox" class="checkboxCtrl" group="trCKB" />
				</td>
				<td width="8%" class="td_title"
					orderField="EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</td>
				<td width="8%" class="td_title"
					orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</td>
				<c:choose>
					<c:when test="${searchMap.TRANS_CODE == 15861 }">
						<td width="30%" class="td_title">原部门</td>
						<td width="30%" class="td_title">现部门<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278706 }">
						<td width="30%" class="td_title">原人员类型</td>
						<td width="30%" class="td_title">现人员类型<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278705 }">
						<td width="30%" class="td_title">原班号</td>
					    <td width="30%" >现班号<font color="red">*</font></td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278704 }">
						<td width="6%" class="td_title">原年薪</td>
					    <td width="6%" class="td_title">原基本工资</td>
					    <td width="6%" class="td_title">原变动工资</td>
					    <td width="6%" class="td_title">原级号</td>
					    <td width="6%" class="td_title">原级号等级</td>
					    <td width="6%" class="td_title">现年薪<font color="red">*</font></td>
					    <td width="6%" class="td_title">现基本工资<font color="red">*</font></td>
					    <td width="6%" class="td_title">现变动工资<font color="red">*</font></td>
					    <td width="6%" class="td_title">现级号<font color="red">*</font></td>
					    <td width="6%" class="td_title">现级号等级<font color="red">*</font></td>
					</c:when>
				</c:choose>
				<td width="12%" class="td_title"
					orderField="nlssort(TRANS_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令原因<font color="red">*</font>
				</td>
				<td width="10%" class="td_title"
					orderField="nlssort(START_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					发令日期<font color="red">*</font>
				</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editTrList}" var="trEmp">
				<tr target="sid" rel="${trEmp.EMPID}">
					
					<td class="td_center">
						<input type="checkbox" id="trCKB" name="trCKB" value="${trEmp.EMPID}" />
						<input type="hidden" name="PERSON_ID_${trEmp.EMPID}" value="${trEmp.PERSON_ID}"/>
						<input type="hidden" name="POSITION_NO_${trEmp.EMPID}" value="${trEmp.POSITION_NO}"/>
						<input type="hidden" name="POST_NO_${trEmp.EMPID}" value="${trEmp.POST_NO}"/>
						<input type="hidden" name="EXP_INSIDE_NO_${trEmp.EMPID}" value="${trEmp.EXP_INSIDE_NO}"/>
					</td>					
					<td class='td_center'>
						${trEmp.EMPID}
					</td>					
					<td class='td_center'>
						${trEmp.LOCAL_NAME}
					</td>
					<c:choose>
					<c:when test="${searchMap.TRANS_CODE == 15861 }">
						<td>
							<input type="hidden" id="DEPTMENTNO_${trEmp.EMPID}" 
							name="DEPTMENTNO_${trEmp.EMPID}" 
							value="${trEmp.OLD_DEPTNO}"/>
							[${trEmp.OLD_DEPTNO}]${trEmp.OLD_DEPTNM}							
						</td>
						<td>
							<ait:deptListInTable name="NEW_DEPTMENTNO_${trEmp.EMPID}"  cpnyId="${searchMap.defaultCpny}" limit="hr" id="NEW_DEPTMENTNM_${trEmp.EMPID}"/>
							<ait:deptTreeIcon name="NEW_DEPTMENTNO_${trEmp.EMPID}"  cpnyId="${searchMap.defaultCpny}" limit="hr" 
								id="NEW_DEPTMENTNM_${trEmp.EMPID}"
								selected="${trEmp.CUR_DEPTNO}"/>
						</td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278706 }">
						<td>
							<input type="hidden" id="EMP_TYPE_CODE_${trEmp.EMPID}" 
							name="EMP_TYPE_CODE_${trEmp.EMPID}" 
							value="${trEmp.OLD_EMP_TYPE_CODE}"/>
							${trEmp.OLD_EMP_TYPE_NAME}	
						</td>
						<td>
							<ait:SelectSyCodeByCpnyID 
								name="NEW_EMP_TYPE_CODE_${trEmp.EMPID}"  parentNo="1368" 
								empTypeNonTempAct="Y"
								cnpyID="${searchMap.defaultCpny}" limit="all"
								selected="${trEmp.CUR_EMP_TYPE_CODE}"/>
						</td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278705 }">
						<td>
							<input type="hidden" id="SHIFT_NO_${trEmp.EMPID}" 
							name="SHIFT_NO_${trEmp.EMPID}" 
							value="${trEmp.OLD_SHIFT_NO}"/>
							${trEmp.OLD_SHIFT_NO}
						</td>
					    <td>
					    	<ait:SelectNonSyCodeByCpnyID name="NEW_SHIFT_NO_${trEmp.EMPID}" 
					     	codeType="SHIFT_NO" 
					     	selected="${trEmp.CUR_SHIFT_NO}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"/>   
						</td>
					</c:when>
					<c:when test="${searchMap.TRANS_CODE == 278704 }">
						<td class='td_right'>
							<input type="hidden" id="ANSAL_${trEmp.EMPID}" 
							name="ANSAL_${trEmp.EMPID}" 
							value="${trEmp.OLD_ANSAL}"/>
							${trEmp.OLD_ANSAL}
						</td>
					    <td class='td_right'>
					    	<input type="hidden" id="BASE_PAY_${trEmp.EMPID}" 
							name="BASE_PAY_${trEmp.EMPID}" 
							value="${trEmp.OLD_BASE_PAY}"/>
							${trEmp.OLD_BASE_PAY}
						</td>
					    <td class='td_right'>
					    	<input type="hidden" id="VARB_PAY_${trEmp.EMPID}" 
							name="VARB_PAY_${trEmp.EMPID}" 
							value="${trEmp.OLD_VARB_PAY}"/>
							${trEmp.OLD_VARB_PAY}
						</td>
					    <td>
					    	<input type="hidden" id="PAY_GRADE_${trEmp.EMPID}" 
							name="PAY_GRADE_${trEmp.EMPID}" 
							value="${trEmp.OLD_PAY_GRADE}"/>
							${trEmp.OLD_PAY_GRADE}
						</td>
					    <td>
					    	<input type="hidden" id="PAY_STEP_${trEmp.EMPID}" 
							name="PAY_STEP_${trEmp.EMPID}" 
							value="${trEmp.OLD_PAY_STEP}"/>
							${trEmp.OLD_PAY_STEP}
						</td>
					    <td>
							<input type="text" id="NEW_ANSAL_${trEmp.EMPID}" 
							name="NEW_ANSAL_${trEmp.EMPID}" 
							value="${trEmp.CUR_ANSAL}"
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')" 
							onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='10'/>
						</td>
						<td>
							<input type="text" id="NEW_BASE_PAY_${trEmp.EMPID}" 
							name="NEW_BASE_PAY_${trEmp.EMPID}"
							value="${trEmp.CUR_BASE_PAY}" 
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='10'/>
						</td>
						<td>
							<input type="text" id="NEW_VARB_PAY_${trEmp.EMPID}" 
							name="NEW_VARB_PAY_${trEmp.EMPID}"
							value="${trEmp.CUR_VARB_PAY}"  
							size="10" class="textInput"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')" 
							maxlength='10'/>
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="NEW_PAY_GRADE_${trEmp.EMPID}" 
					     	codeType="PAY_GRADE" 
					     	selected="${trEmp.CUR_PAY_GRADE}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"/>   
						</td>
						<td>
							<ait:SelectNonSyCodeByCpnyID name="NEW_PAY_STEP_${trEmp.EMPID}" 
					     	codeType="PAY_STEP" 
					     	selected="${trEmp.CUR_PAY_STEP}"
					     	cnpyID="${searchMap.defaultCpny}" 
					     	limit="all"/>   
						</td>
					</c:when>
					</c:choose>	
					<td>
						<input type="text" id="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							name="TRANSFER_ORDER_REASON_${trEmp.EMPID}" 
							value="${trEmp.TRANSFER_ORDER_REASON}"  
							size="18" class="textInput"
							maxlength='30'/>
					</td>					
					<td>
						<input type="text" id="START_DATE_${trEmp.EMPID}" 
							name="START_DATE_${trEmp.EMPID}" 
							value="${trEmp.START_DATE}"  
							size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>								
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	</form>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewReguEmpTransferOrderEditList">
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