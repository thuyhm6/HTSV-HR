<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_paTwo(checkBoxFlag){
    	$("#paResultCenter :checkbox").each(function ()
    	{
        	this.checked = checkBoxFlag ;
	    });
	    if(checkBoxFlag){
        	$("#paResultCenter :text").addClass("required");
	    }else{
	    	$("#paResultCenter :text").removeClass("required");
	    }
    }
function f_checkAntiAll_paTwo(checkBoxFlag){
		$("#paResultCenter :checkbox").each(function ()
		{	
			if(this.checked){
				this.checked = false;
			}else{
				this.checked = true;
			}
    	});
	    if(checkBoxFlag){
        	$("#paResultCenter :text").addClass("required");
	    }else{
	    	$("#paResultCenter :text").removeClass("required");
	    }
}

var parameterPa = {}; 
var hasParamPa=0;
function excelExportPaResultTwo(obj){
	var exportId=obj.id;
	hasParamPa=1;//初始化
	//人事项目
	<c:forEach items="${hrItemList}" var="item">
		 addUrlParamTwo("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>
	
	//考勤项目  
	<c:forEach items="${arItemList}" var="item"> 
		addUrlParamTwo("${item.COLUMN_NAME}","${item.ITEM_NAME}","N");
	</c:forEach> 

	//输入项目
	<c:forEach items="${paInputItemList}" var="item">
 		addUrlParamTwo("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  

	//计算项目
	<c:forEach items="${paComputeItemList}" var="item" >
		addUrlParamTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  
	  
	//基础项目
	<c:forEach items="${paBasicInputItemList}" var="item" >
		addUrlParamTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	//保险输入项目
	<c:forEach items="${insuranceInputItemList}" var="item">
		addUrlParamTwo("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>
	 
	//保险项目
	<c:forEach items="${insuranceComputeItemList}" var="item">
		addUrlParamTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	   

	//奖金项目
	//<c:forEach items="${bonusComputeItemList}" var="item">
	//	addUrlParamTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
	//</c:forEach>
	//alert(hasParamPa);
	parameterPa["paramNum"] = hasParamPa;

	parameterPa["tableNamePart"] = "T_PA_";

	//"1"表示为工资计算结果
	parameterPa["functionFlag"] = "1";
	//parameterPa["distinguish"] = "2";
	var menuNo = '${menuNo}';
	parameterPa["distinguish"] = menuNo;
	if(hasParamPa>1){
	 	$.ajax({ 
	 		async: false,
	 		type: "POST",
	 		url: "/pa/excelExport/exportResult", 
	 		data: parameterPa,
	 		dataType: "json",
	 		success: function(resp){
	 			if(resp.pathStr=="N"){
	 				alertMsg.error('<spring:message code="pa.insurance.title.exportFaild"/>');
	  			}else if(resp.pathStr=="K"){
	  	 			alertMsg.error('<spring:message code="pa.insurance.title.exportExceling"/>');
	 			}else{
	  				document.getElementById("excelExportPaResult").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr;
	 				document.getElementById("excelExportPaResult").click();
	  	 		} 
	 		} 
	 	});
	 }else{
	 	alertMsg.info('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');
	 }
}
function addUrlParamTwo(name,value,flag,exFlag){
	var hrItem=document.getElementById("pa12"+name);
	var paSort=document.getElementById("paSort12"+name);
	if(hrItem.checked==true){
		parameterPa["alias"+hasParamPa] = name;
		parameterPa["aliasName"+hasParamPa] = value;
		parameterPa["aliasType"+hasParamPa] = flag;
		parameterPa["aliasSort"+hasParamPa] = paSort.value;
		parameterPa["aliasExpFlag"+hasParamPa] = exFlag;
		hasParamPa++;
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
				$("input[type='text'][id='paSort" + idStr + "']").addClass("required");
				
			}else{
				$("input[type='text'][id='paSort" + idStr + "']").removeClass("required");
			}
		}
	});
});
</script>
<form method="post" action="/pa/salary/paBalance"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this)">
	<div class="formBar">
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAll_paTwo(this.checked)" />
			<spring:message code="pa.salary.title.allChecked"/><!--全选-->
		</label>
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAntiAll_paTwo(this.checked)" />
			<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
		</label>
		<ul>
			<!--  -->
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">
							<spring:message code="pa.salary.title.salarySettlement"/><!-- 工资结算-->
						</button>
					</div>
				</div>
			</li>
			
			<!--<li><div class="button" href="" target="ajaxTodo" title="Excel导出"><span>Excel导出</span></div></li>  -->
			<li>
				<a class="buttonActive" onclick="excelExportPaResultTwo(this);"><span>
					<spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="excelExportPaResult"
					style="display: none">&nbsp;</a>
			</li>
		</ul>
	</div>
	<div class="pageContent" id="paResultCenter">
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.humanItem"/><!--人事项目-->
				<input type="checkbox" class="checkboxCtrl" group="pahrItem" />
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
								<c:when test="${item.DISTINCT_FIELD eq 'PA_MONTH'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="pa12${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paSort12${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="1" />
								</c:when>
								<c:when test="${item.DISTINCT_FIELD eq 'GIVE_DATE'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="pa12${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paSort12${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="2" />
								</c:when>
								<c:otherwise>
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="pa12${item.DISTINCT_FIELD}" 
										${item.ISCHECKED } group=""
										/>
									<input type="text" id="paSort12${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
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
				<spring:message code="pa.salary.title.attendanceItem"/><!--考勤项目-->
				<input type="checkbox" class="checkboxCtrl" group="paArItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${arItemList}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" value="${item.ITEM_NAME}" name="paArItem"
								id="pa12${item.COLUMN_NAME}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort12${item.COLUMN_NAME}" name="paArItem" style="width:15px;" 
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
							&nbsp;&nbsp;${item.ITEM_NAME}&nbsp;&nbsp;
						</td>

						<c:if test="${(i.index + 1) mod 4 == 0}">
							</tr>
							<tr>
						</c:if>

						<c:if test="${i.index + 1 == fn:length(arItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.basicItem"/><!--基础项目-->
				<input type="checkbox" class="checkboxCtrl" group="paBasicInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paBasicInputItemList}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>

						<td>
							<input type="checkBox" name="paBasicInputItem"
								value="${item.ALIAS_NAME}" id="pa12${item.ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort12${item.ITEM_ID}" name="paBasicInputItem" style="width:15px;" 
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

						<c:if test="${i.index + 1 == fn:length(paBasicInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.inputItem"/><!--输入项目-->
				<input type="checkbox" class="checkboxCtrl" group="paInputItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paInputItemList}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>

						<td>
							<input type="checkBox" name="paInputItem"
								value="${item.ALIAS_NAME}" id="pa12${item.PARAM_ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort12${item.PARAM_ITEM_ID}" name="paInputItem" style="width:15px;" 
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

						<c:if test="${i.index + 1 == fn:length(paInputItemList)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.caculateItem"/><!--计算项目-->
				<input type="checkbox" class="checkboxCtrl" group="paComputeItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${paComputeItemList}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>

						<td>
							<input type="checkBox" name="paComputeItem"
								value="${item.ALIAS_NAME}"
								id="pa12${item.ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort12${item.ITEM_ID}" name="paComputeItem" style="width:15px;" 
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
					<c:forEach items="${insuranceInputItemList}" var="item"
						varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>

						<td>
							<input type="checkBox" name="insuranceInputItem"
								value="${item.ALIAS_NAME}"
								id="pa12${item.PARAM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort12${item.PARAM_ID}" name="insuranceInputItem" style="width:15px;" 
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
				<spring:message code="pa.salary.title.insuranceCaculateItem"/><!--保险计算项目-->
				<input type="checkbox" class="checkboxCtrl" group="insuranceItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${insuranceComputeItemList}" var="item"
						varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>

						<td>
							<input type="checkBox" name="insuranceItem"
								value="${item.ALIAS_NAME}"
								id="pa12${item.ITEM_ID}" 
								${item.ISCHECKED } group="" 
								/>
							<input type="text" id="paSort12${item.ITEM_ID}" name="insuranceItem" style="width:15px;" 
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
		<!--
            	<table>
            		<tr>
            			<td colspan="4">奖金项目</td>
            		</tr>
	           		<c:forEach items="${bonusComputeItemList}" var="item" varStatus="i">
	           			<c:if test="${i.index == 0}">
	           				<tr>
	           			</c:if>
	           			
	           				<td ><input type="checkBox" name="${item.ITEM_ID}" value="${item.ALIAS_NAME}
	           				" id="pa${item.ITEM_ID}"/>&nbsp;&nbsp;${item.ALIAS_NAME}&nbsp;&nbsp;</td>
	           			
	           			<c:if test="${(i.index + 1) mod 4 == 0}">
	           				</tr>
	           				<tr>
	           			</c:if>
	           				
	           			<c:if test="${i.index + 1 == fn:length(bonusComputeItemList)}">
	           				</tr>
	           			</c:if>
	           		</c:forEach>
            	</table>-->
	</div>
</form>