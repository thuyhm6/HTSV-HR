<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function CheckFormExpiredContract(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doExpiredContractExport(from){
  	var $from =$(from);
  	var url ="/hrm/contractInfo/viewExpiredContractForSearchExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expExpiredContract(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewExpiredContractInfo");
  	if(CheckFormExpiredContract($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doExpiredContractExport($from);}});
    } 
}

$(document).ready(function(){
    var pValue = "${PARTICULARHUMANDISTINGUISH}";
    var dValue = "${CONTRACTTYPECODE}";
    //alert(pValue);
    if(pValue != ""){
    	getRenLiAndQiYue(pValue);
    	if(dValue !=""){
    		//jjy option select;
    		$("#seach_CONTRACTTYPECODE > option[value=${CONTRACTTYPECODE}]").attr("selected", "true");	
    	}
    }
});

function validateCallbackApproveExpiredContract(form, callback) {
	var $form = $("#approveExpiredContract");
		
		if (!$form.valid()) {
			return false;
		}

		var checked=false;
		var ids= document.getElementsByName("hr0306Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.shenpi"/>');
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
function changeFlag(flag){
	$("#FLAG").val(flag);
	$("#approveExpiredContract").submit();
}
function singleApprove(flag, index){
	var ids= document.getElementsByName("hr0306Check");
	for(var i=0;i<ids.length;i++){
		if(i == index){
			ids[i].checked=true;
		}else{
			ids[i].checked=false;
		}
	}
	$("#FLAG").val(flag);
	$("#approveExpiredContract").submit();
}
$(document).ready(function(){
	if($("#hr0306_seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#hr0306_seach_EmpTypeCodeNo").val();
		ajaxEmpTypeForGroupToList(EMP_TYPE,"hr0306_seach_JobTypeGroupNo","hr0306_seach_EmpTypeCodeNo",
				"hr0306_seach_CPNY","hr0306_limit");
		//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
	}
});
</script>
<div class="pageHeader">
	<form id="expiredContractApprove" onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewExpiredContractApproveList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</td>
				<td>
					<ait:deptList name="seach_DEPTNO" id="viewExpiredContractApproveList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewExpiredContractApproveList_seachDept" selected="${DEPTNO}"/></td>
				<td><!-- 次数： -->
					<spring:message code="hr.viewExpiredContract.title.TOTALPERIOD"/>
				</td>
				<td>
					<input type="text" name="seach_TOTALPERIOD" value="${TOTALPERIOD}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</td>
				<td><!-- 合同类型 -->
					<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
				</td>
				<td>
                   <ait:SelectSyCodeByCpnyID name="seach_CONTRACT_TYPE_CODE" parentNo="1649" selected="${CONTRACT_TYPE_CODE }" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 社号/姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
			</tr>
			<tr>
			   	<td><!-- 人员类型组 --><spring:message code="hrm.contract.PERSONNEL_TYPE_GROUP" /></td>
						<td>
						<input type="hidden" id="hr0306_limit" name="limit" value="hr">
						<input type="hidden" id="hr0306_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode id="hr0306_seach_JobTypeGroupNo" name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="hr" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,hr0306_seach_JobTypeGroupNo,hr0306_seach_EmpTypeCodeNo,hr0306_seach_CPNY,hr0306_limit)"/>
						</td>
						<td><!-- 人员类型 --><spring:message code="hrm.contract.PERSONNEL_TYPE" /></td>
						<td>
		 	<ait:SelectEmpTypeCode id="hr0306_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" cnpyID="${defaultCpny}" limit="hr"/>
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
     <form id="approveExpiredContract" method="post" action="/hrm/contractInfo/approveExpiredContract" class="pageForm required-validate" 
     	onsubmit="return validateCallbackApproveExpiredContract(this, navTabAjaxDone)">
     	<input type="hidden" id="FLAG" name="FLAG" value="1" />
	 <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="changeFlag(1)">
								<spring:message code="ess.trans.title.passInBatch"/><!-- 批量通过 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="changeFlag(2)">
								<spring:message code="ess.trans.title.rejectInBatch"/><!-- 批量否决 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	   <table class="table" width="120.8%" layoutH="208">
		<thead>
			<tr>
				<th width="30">
				    	<input type="checkbox" class="checkboxCtrl" group="hr0306Check" />
				</th>
				<th><!--合同次数-->
					<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD"/>
				</th>
				<th>
					<!-- 合同编号 --><spring:message code="hrm.contractInfo.CONTRACT_ID" />
				</th>
				<th><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th><!-- 工作地-->
					<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu"/>
				</th>
				<th><!-- 合同类型-->
					<spring:message code="hr.viewPersonalInfo.title.CONTRACT_TYPE"/>
				</th>
				<th>
					<!-- 合同版本 --><spring:message code="hrm.contract.Contract_version"/>
				</th>
				<th><!-- 起始日期-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE"/>
				</th>
				<th><!--终止日期-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE"/>
				</th>
				<th><!--续签工作地区:-->
					<spring:message code="hr.contract.title.xuqian.gongzuodiqu"/>
				</th>
				<th><!--续签合同类型:-->
					<spring:message code="hr.contract.title.xuqian.hetongleixing"/>
				</th>
				<th><!--续签开始日期:-->
					<spring:message code="hr.contract.title.xuqian.kaishiriqi"/>
				</th>
				<th><!--续签结束日期:-->
					<spring:message code="hr.contract.title.xuqian.jiehsuriqi"/>
				</th>
				<th><!--续签意见:-->
					<spring:message code="hr.contract.title.xuqian.yijian"/>
				</th>
				<th><!--审批-->
					<spring:message code="hr.contract.title.shenpi"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center"  style="text-align: center;padding-top:7px;">
						<input type="checkbox" name="hr0306Check" value="${i.index}"/>
						<input type="hidden" name="CONTRACT_NO_${i.index}" value="${item.CONTRACT_NO}" />
					</td>
					<td class="td_center">${item.TOTAL_PERIOD}</td>
					<td style="text-align:left">${item.CONTRACT_NUMBER}</td>
					<td style="text-align:left">${item.EMPID}</td>
					<td style="text-align:left">${item.LOCAL_NAME}</td>
					<td style="text-align:left">${item.DEPARTMENT_NAME}</td>
					<td style="text-align:left">${item.WORK_AREA_NAME}</td>
					<td style="text-align:left">${item.CONTRACT_TYPE }</td>
					<td style="text-align:left">${item.CONTRACT_VERSION}</td>
					<td class="td_center">${item.CONTRACTSTARTDATE}</td>
					<td class="td_center">${item.CONTRACTENDDATE}</td>
					<td style="text-align:left">${item.WORK_AREA_NAME2}</td>
					<td style="text-align:left">${item.CONTRACT_TYPE2 }</td>
					<td class="td_center">${item.CONTRACTSTARTDATE2}</td>
					<td class="td_center">${item.CONTRACTENDDATE2}</td>
					<td style="text-align:left">${item.REMARK}</td>
					<td class="td_center">
						<a href="/hrm/contractInfo/viewContractAffirm?CONTRACT_NO=${item.CONTRACT_NO}" title="审批详情"
				          	target="dialog" rel="hr0303_affirm_info" mask="true" width="950" height="450" ><font color="red">审批</font></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/hrm/contractInfo/viewExpiredContractApproveList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
