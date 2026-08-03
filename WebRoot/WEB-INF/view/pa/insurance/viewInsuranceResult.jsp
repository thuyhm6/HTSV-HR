<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_insurance(checkBoxFlag){
   	$("#InsuranceResultCenter :checkbox").each(function ()
   	{	
       	
       	this.checked = checkBoxFlag ;  
    });
   	if(checkBoxFlag){
    	$("#InsuranceResultCenter :text").addClass("required");
    }else{
    	$("#InsuranceResultCenter :text").removeClass("required");
    }
}
function f_checkAntiAll_insurance(checkBoxFlag){
	$("#InsuranceResultCenter :checkbox").each(function ()
	{	
		if(this.checked){
			this.checked = false;
			}
		else{
			this.checked = true;
			}  
    });
	if(checkBoxFlag){
    	$("#InsuranceResultCenter :text").addClass("required");
    }else{
    	$("#InsuranceResultCenter :text").removeClass("required");
    } 
}


var parameter = {}; 
var hasParam=0;
function excelExportInsuranceResult(obj){
	var exportId=obj.id;
	hasParam=1;//初始化
	//人事项目hrItemList item.DISTINCT_FIELD item.FIELD_NAME
	<c:forEach items="${hrItemList}" var="item">
		addUrlInsuranceParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>

	//输入项目insuranceInputItemList item.PARAM_ID item.ALIAS_NAME
	<c:forEach items="${insuranceInputItemList}" var="item">
		addUrlInsuranceParam("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>

	//计算项目 insuranceComputeItemList item.ITEM_ID item.ALIAS_NAME
	<c:forEach items="${insuranceComputeItemList}" var="item" >
		addUrlInsuranceParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  

	parameter["paramNum"] = hasParam;
	parameter["functionFlag"] = "3";
	var menuNo = '${menuNo}';
	
	parameter["distinguish"] = menuNo;
	parameter["tableNamePart"] = "T_IS_";
 	if(hasParam>1){
 		$.ajax({ 
 			async: false,
 			type: "POST",
 			url: "/pa/excelExport/exportResult", 
 			data: parameter,
 			dataType: "json",
 			success: function(resp){ 
 				if(resp.pathStr=="N"){
					alertMsg.error('<spring:message code="pa.insurance.title.exportFaild"/>');
	 			}else{
	 				document.getElementById("excelExportInsuranceResult").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr;
	 				document.getElementById("excelExportInsuranceResult").click();
	 	 		} 
 			} 
 			});
 	}else{
 		alertMsg.info('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');
 	}
}
function addUrlInsuranceParam(name,value,flag,exFlag){
	var hrItem=document.getElementById("in31"+name);
	var isSort=document.getElementById("isSort31"+name);
	if(hrItem!=null&&name!=null){
		if(hrItem.checked==true){
			parameter["alias"+hasParam] = name;
			parameter["aliasName"+hasParam] = value;
			parameter["aliasType"+hasParam] = flag;
			parameter["aliasSort"+hasParam] = isSort.value;
			parameter["aliasExpFlag"+hasParam] = exFlag;
			hasParam++;
		}
	}
} 

$(document).ready(function(){
	
	$(":checkbox").click(function(){
		
		//选中checkbox时改变text框样式
		var group=$(this).attr("group");//页面遍历输出的checkbox标签的group属性不能删除
		if(group != ''){
			if(this.checked == true){
				$("input[type='text'][name='" + group + "']").addClass("required");
			}else{
				$("input[type='text'][name='" + group + "']").each(function(index){
					if(group=="pahrItem"){
						if(index!=0 || index!=1){
							$(this).removeClass("required");
						}
					}else{
						$(this).removeClass("required");
					}
				});
			}
		}else{
			var idStr=this.id.substring(2,this.id.length);//获得DISTINCT_FIELD
			if(this.checked == true){
				$("input[type='text'][id='isSort" + idStr + "']").addClass("required");
				
			}else{
				$("input[type='text'][id='isSort" + idStr + "']").removeClass("required");
			}
		}
	});
});
</script>

<form method="post" action="/pa/insurance/insuranceBalance"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this,navTabAjaxDone)">
	<div class="formBar">
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAll_insurance(this.checked)" />
			<spring:message code="pa.salary.title.allChecked"/><!--全选-->
		</label>
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAntiAll_insurance(this.checked)" />
			<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
		</label>
		<ul>
			<!--  -->
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">
							<spring:message code="pa.insurance.title.insuranceSettlement"/><!-- 保险结算 -->
						</button>
					</div>
				</div>
			</li>
			
			<li>
				<a class="buttonActive" onclick="excelExportInsuranceResult(this);">
				<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="excelExportInsuranceResult"
					style="display: none">&nbsp;</a>
			</li>
		</ul>
	</div>
	<div class="pageContent" id="InsuranceResultCenter">
		<div class="panel">
		<h1>
			<spring:message code="pa.salary.title.humanItem"/><!--人事项目-->
			<input type="checkbox" class="checkboxCtrl" group="ishrItem"/>
		</h1>
		<div>
		<table>
			
			<c:forEach items="${hrItemList}" var="item" varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>

				<td>
				<!-- '工资月' 默认选中 -->
							<c:choose>
								<c:when test="${item.DISTINCT_FIELD eq 'IS_MONTH'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="ishrItem" 
											id="in31${item.DISTINCT_FIELD}"checked="checked" group=""/>
									<input type="text" id="isSort31${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="1" />
								</c:when>
								<c:when test="${item.DISTINCT_FIELD eq 'GIVE_DATE'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="ishrItem" 
											id="in31${item.DISTINCT_FIELD}"checked="checked" group=""/>
									<input type="text" id="isSort31${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="2" />
								</c:when>
								<c:otherwise>
									<input type="checkBox" value="${item.FIELD_NAME}" name="ishrItem" 
											id="in31${item.DISTINCT_FIELD}" ${item.ISCHECKED } group=""/>
									<input type="text" id="isSort31${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
											maxlength="2"
											<c:choose>
												<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
													class="number textInput required"
												</c:when>
												<c:otherwise>
													class="number textInput"
												</c:otherwise>
											</c:choose>
											value="${item.ORDERNO }"/>
								</c:otherwise>
							</c:choose>
					&nbsp;&nbsp;${item.FIELD_NAME}&nbsp;&nbsp;
				</td>

				<c:if test="${(i.index + 1) mod 4 == 0}">
					</tr>
					<tr>
				</c:if>

				<c:if test="${i.index + 1 == fn:length(hrItemList)}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
		</div>
	</div>
	<div class="panel">
		<h1>
			<spring:message code="pa.salary.title.inputItem"/><!--输入项目--> 
			<input type="checkbox" class="checkboxCtrl" group="isParamItem"/>
		</h1>
		<div>
		<table>
			<c:forEach items="${insuranceInputItemList}" var="item" varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>

				<td>
					<input type="checkBox" name="isParamItem" value="${item.ALIAS_NAME}" id="in31${item.PARAM_ID}" ${item.ISCHECKED} group=""/>
					<input type="text" id="isSort31${item.PARAM_ID}" name="isParamItem" style="width:15px;" 
									maxlength="2"
									<c:choose>
										<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
											class="number textInput required"
										</c:when>
										<c:otherwise>
											class="number textInput"
										</c:otherwise>
									</c:choose>
									value="${item.ORDERNO }"/>
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
			<spring:message code="pa.salary.title.caculateItem"/><!--计算项目-->
			 <input type="checkbox" class="checkboxCtrl" group="isItem"/>
		</h1>
		<div>
		<table>
			<c:forEach items="${insuranceComputeItemList}" var="item"
				varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>

				<td>
					<input type="checkBox" name="isItem" value="${item.ALIAS_NAME}" id="in31${item.ITEM_ID}" ${item.ISCHECKED} group=""/>
					<input type="text" id="isSort31${item.ITEM_ID}" name="isItem" style="width:15px;" 
									maxlength="2"
									<c:choose>
										<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
											class="number textInput required"
										</c:when>
										<c:otherwise>
											class="number textInput"
										</c:otherwise>
									</c:choose>
									value="${item.ORDERNO }"/>
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
</div>
</form>