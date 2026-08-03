<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_pa(checkBoxFlag){
    	$("#paPortalCenter :checkbox").each(function ()
    	{
        	this.checked = checkBoxFlag ;  
	    }); 
    }
function f_checkAntiAll_pa(checkBoxFlag){
		$("#paPortalCenter :checkbox").each(function ()
		{	
			if(this.checked){
				this.checked = false;
			}else{
				this.checked = true;
			}  
	    }); 
}
var parameterPa = {}; 
var hasParamPa=0;
function addItemToSAP(){
	var $form = $("#viewPortalSapItem");
	hasParamPa=1;//初始化

	//工资输入项目
	<c:forEach items="${paInputItemListPortal}" var="item">
		addUrlParamSapData("${item.PARAM_ITEM_ID}");
	</c:forEach>  

	//工资计算项目
	<c:forEach items="${paComputeItemListPortal}" var="item" >
		addUrlParamSapData("${item.ITEM_ID}");
	</c:forEach>  
	  
	//工资基础项目
	<c:forEach items="${paBasicInputItemListPortal}" var="item" >
		addUrlParamSapData("${item.ITEM_ID}");
	</c:forEach> 

	//保险输入项目
	<c:forEach items="${insuranceInputItemListPortal}" var="item">
		addUrlParamSapData("${item.PARAM_ID}");
	</c:forEach>
	 
	//保险计算项目
	<c:forEach items="${insuranceComputeItemListPortal}" var="item">
		addUrlParamSapData("${item.ITEM_ID}");
	</c:forEach> 
	<%--
	//奖金输入项目
	<c:forEach items="${bonusInputItemListPortal}" var="item">
		addUrlParamSapData("${item.PARAM_ITEM_ID}");
	</c:forEach>

	//奖金计算项目
	<c:forEach items="${bonusComputeItemListPortal}" var="item">
		addUrlParamSapData("${item.ITEM_ID}");
	</c:forEach>
	--%>
	
	parameterPa["paramNum"] = hasParamPa;
 	if(hasParamPa>1){
 		$.ajax({ 
 			async: false,
 			type: "POST",
 			url: "/pa/salary/addItemToSap", 
 			data: parameterPa,
 			dataType: "json",
 			success: function(resp){
 				if(resp.pathStr=="Y"){
 					alert('<spring:message code="alert.message.add_success"/>');
 	 			}else if(resp.pathStr=="N") {
 	 				alertMsg.error('<spring:message code="alert.message.add_fail"/>');
 	 	 		}else{
 	 	 			alertMsg.error('<spring:message code="alert.message.deletesapfail"/>');
 	 	 	 	}
 			} 
 			});
 			
 	}else{
 		alertMsg.error('<spring:message code="pa.insurance.title.pleaseChooseAddItem"/>');
 	}
}
function addUrlParamSapData(name){ 
	var $form = $("#viewPortalSapItem");
	var hrItem=document.getElementById("por"+name);
	if(hrItem!=null&&name!=null){
		if(hrItem.checked==true){
			parameterPa["alias"+hasParamPa] = name;
			hasParamPa++;
		}
	}
} 
</script>
<form id="viewPortalSapItem" method="post" action="/pa/salary/paBalance" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<div class="formBar">
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAll_pa(this.checked)" />
			<spring:message code="pa.salary.title.allChecked"/><!--全选-->
		</label>
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAntiAll_pa(this.checked)" />
			<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
		</label>
		<ul>
			<li>
				<a class="buttonActive" onclick="addItemToSAP();"><span>
					<spring:message code="pa.insurance.title.submit"/><!--保存--></span>
				</a>
				<a class="buttonActive" id="excelExportPaResult"
					style="display: none">&nbsp;</a>
			</li>
		</ul>
	</div>
	<div class="pageContent" id="paPortalCenter">
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.salarybasicItem"/><!--工资基础项目-->
				<input type="checkbox" class="checkboxCtrl" group="paBasicInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paBasicInputItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paBasicInputItem" value="${item.ALIAS_NAME}" id="por${item.ITEM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>
								/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(paBasicInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.salaryinputItem"/><!--工资输入项目-->
				<input type="checkbox" class="checkboxCtrl" group="paInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paInputItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paInputItem" value="${item.ALIAS_NAME}" id="por${item.PARAM_ITEM_ID}"
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.PARAM_ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach> />
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(paInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.salarycaculateItem"/><!--工资计算项目-->
				<input type="checkbox" class="checkboxCtrl" group="paComputeItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paComputeItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paComputeItem" value="${item.ALIAS_NAME}" id="por${item.ITEM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(paComputeItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.insuranceInputItem"/><!--保险输入项目-->
				<input type="checkbox" class="checkboxCtrl" group="insuranceInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${insuranceInputItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="insuranceInputItem" value="${item.ALIAS_NAME}" id="por${item.PARAM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.PARAM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(insuranceInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.insuranceCaculateItem"/><!--保险计算项目-->
				<input type="checkbox" class="checkboxCtrl" group="insuranceItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${insuranceComputeItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="insuranceItem" value="${item.ALIAS_NAME}" id="por${item.ITEM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>
								/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(insuranceComputeItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<%-- 
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.bonusInputItem"/><!--奖金输入项目  -->
				<input type="checkbox" class="checkboxCtrl" group="bonusInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${bonusInputItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="bonusInputItem" value="${item.ALIAS_NAME}" id="por${item.PARAM_ITEM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.PARAM_ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(bonusInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.bonusCaculateItem"/><!--奖金计算项目  -->
				<input type="checkbox" class="checkboxCtrl" group="bonusItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${bonusComputeItemListPortal}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="bonusItem" value="${item.ALIAS_NAME}" id="por${item.ITEM_ID}" 
								<c:forEach items="${portalLogList}" var="portal">
									<c:if test='${item.ITEM_ID eq portal.FIELD_ID}'>checked</c:if>
								</c:forEach>/>
							&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;
						</td>
						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>
						<c:if test="${i.index + 1 == fn:length(bonusComputeItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		--%>
	</div>
</form>