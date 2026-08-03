<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_pa(checkBoxFlag){
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
function f_checkAntiAll_pa(checkBoxFlag){
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
function saveExcelPaResult(obj){
	var exportId=obj.id;
	hasParamPa=1;//初始化
	//人事项目
	<c:forEach items="${hrItemList}" var="item">
		 addUrlParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>
	
	//考勤项目  
	<c:forEach items="${arItemList}" var="item"> 
		addUrlParam("${item.COLUMN_NAME}","${item.ITEM_NAME}","N");
	</c:forEach> 

	//输入项目
	<c:forEach items="${paInputItemList}" var="item">
 		addUrlParam("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  

	//计算项目
	<c:forEach items="${paComputeItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  
	  
	//基础项目
	<c:forEach items="${paBasicInputItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	//保险输入项目
	<c:forEach items="${insuranceInputItemList}" var="item">
		addUrlParam("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>
	 
	//保险项目
	<c:forEach items="${insuranceComputeItemList}" var="item">
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	   

	//奖金项目
	//<c:forEach items="${bonusComputeItemList}" var="item">
	//	addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
	//</c:forEach>
	//alert(hasParamPa);
	parameterPa["paramNum"] = hasParamPa;

	//parameterPa["tableNamePart"] = "T_PA_";
	parameterPa["tableNamePart"] = "PA_SUMMARY_WITHHOLD_";
	var paMonth = $("#result_year",navTab.getCurrentPanel()).val() + $("#result_month",navTab.getCurrentPanel()).val();
	//var empTypeGroup = $("#result_EMP_TYPE_GROUP",navTab.getCurrentPanel()).val();
	var statNo = $("#result_STATNO",navTab.getCurrentPanel()).val();
	//var deptNo = $("#result_DEPTNO",navTab.getCurrentPanel()).val();
	var deptNo = $(":input[sysLong='seachBankDept']").val();
	//alertMsg.info(paMonth);
	//alertMsg.info(empTypeGroup+":::::"+deptNo);
	parameterPa["paMonth"] = paMonth;
	//parameterPa["empTypeGroup"] = empTypeGroup;
	parameterPa["statNo"] = statNo;
	parameterPa["deptNo"] = deptNo;
	parameterPa["saveYn"] ="Y";
	//return false;

	//"1"表示为工资计算结果
	parameterPa["functionFlag"] = "1";
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
				    alertMsg.info('保存成功');
	  	 		} 
	 		} 
	 	});
	 }else{
	 	alertMsg.info('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');
	 }
}

function excelExportPaResult(obj){
	var exportId=obj.id;
	hasParamPa=1;//初始化
	//人事项目
	<c:forEach items="${hrItemList}" var="item">
		 addUrlParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>
	
	//考勤项目  
	<c:forEach items="${arItemList}" var="item"> 
		addUrlParam("${item.COLUMN_NAME}","${item.ITEM_NAME}","N");
	</c:forEach> 

	//输入项目
	<c:forEach items="${paInputItemList}" var="item">
 		addUrlParam("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  

	//计算项目
	<c:forEach items="${paComputeItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  
	  
	//基础项目
	<c:forEach items="${paBasicInputItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	//保险输入项目
	<c:forEach items="${insuranceInputItemList}" var="item">
		addUrlParam("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>
	 
	//保险项目
	<c:forEach items="${insuranceComputeItemList}" var="item">
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> 

	   

	//奖金项目
	//<c:forEach items="${bonusComputeItemList}" var="item">
	//	addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
	//</c:forEach>
	//alert(hasParamPa);
	parameterPa["paramNum"] = hasParamPa;

	//parameterPa["tableNamePart"] = "T_PA_";
	parameterPa["tableNamePart"] = "PA_SUMMARY_WITHHOLD_";
	var paMonth = $("#result_year",navTab.getCurrentPanel()).val() + $("#result_month",navTab.getCurrentPanel()).val();
	//var empTypeGroup = $("#result_EMP_TYPE_GROUP",navTab.getCurrentPanel()).val();
	var statNo = $("#result_STATNO",navTab.getCurrentPanel()).val();
	//var deptNo = $("#result_DEPTNO",navTab.getCurrentPanel()).val();
	var deptNo = $(":input[sysLong='seachBankDept']").val();
	//alertMsg.info(paMonth);
	//alertMsg.info(empTypeGroup+":::::"+deptNo);
	parameterPa["paMonth"] = paMonth;
	//parameterPa["empTypeGroup"] = empTypeGroup;
	parameterPa["statNo"] = statNo;
	parameterPa["deptNo"] = deptNo;
	parameterPa["saveYn"] ="N";
	//return false;

	//"1"表示为工资计算结果
	parameterPa["functionFlag"] = "1";
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
	  				document.getElementById("excelExportPaResult").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr+"";
	 				document.getElementById("excelExportPaResult").click();
	  	 		} 
	 		} 
	 	});
	 }else{
	 	alertMsg.info('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');
	 }
}
function addUrlParam(name,value,flag,exFlag){
	var hrItem=document.getElementById("pa"+name);
	var paSort=document.getElementById("paSort"+name);
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
<form method="post" action="/pa/salary/paBalance" name ="excelExport"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this)">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><spring:message code="pa.insurance.title.salaryMonth"/><!--工资月--></td>
				<td><ait:date yearName="result_year" monthName="result_month"/></td>
				<td>区间</td>
				<td>
					<select id="result_STATNO" name="result_STATNO">
						<c:forEach items="${statList}" var="stat">
							<option value="${stat.STAT_NO}">${stat.STAT_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td><spring:message code="public.title.deptName"/><!--部门-->：</td>
				<td>
					<ait:deptList name="result_DEPTNO" id="seachBankDept" limit="pa"/>
					<ait:deptTreeIcon name="result_DEPTNO" limit="pa" id="seachBankDept" selected="${DEPTNO}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><a class="buttonActive" onclick="saveExcelPaResult(this);"><span>
					保存<!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="saveExcelPaResult"
					style="display: none">&nbsp;</a></li>
			</ul>
			<ul>
				<li><a class="buttonActive" onclick="excelExportPaResult(this);"><span>
					<spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="excelExportPaResult"
					style="display: none">&nbsp;</a></li>
			</ul>
		</div>
	</div>
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
		<%-- 
		<ul>
			<li>
				<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->：
					<ait:date yearName="result_year" monthName="result_month"/>
				人员类型组：
					<ait:SelectSyCodeByCpnyID id="result_EMP_TYPE_GROUP" name="result_EMP_TYPE_GROUP" parentNo="211807" cnpyID="${defaultCpny}" limit="all"/>
				<spring:message code="public.title.deptName"/><!--部门-->：
					<ait:deptTree name="result_DEPTNO" limit="pa" selected="${DEPTNO}"/>
			</li>
			<li>
				<a class="buttonActive" onclick="excelExportPaResult(this);"><span>
					<spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="excelExportPaResult"
					style="display: none">&nbsp;</a>
			</li>
		</ul>
		--%>
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
										id="pa${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paSort${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="1" />
								</c:when>
								<%-- 
								<c:when test="${item.DISTINCT_FIELD eq 'GIVE_DATE'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="pa${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paSort${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="2" />
								</c:when>
								 --%>
								<c:otherwise>
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="pa${item.DISTINCT_FIELD}" 
										${item.ISCHECKED } group=""
										/>
									<input type="text" id="paSort${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
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
								id="pa${item.COLUMN_NAME}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort${item.COLUMN_NAME}" name="paArItem" style="width:15px;" 
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
								value="${item.ALIAS_NAME}" id="pa${item.ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort${item.ITEM_ID}" name="paBasicInputItem" style="width:15px;" 
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
								value="${item.ALIAS_NAME}" id="pa${item.PARAM_ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort${item.PARAM_ITEM_ID}" name="paInputItem" style="width:15px;" 
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
								id="pa${item.ITEM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort${item.ITEM_ID}" name="paComputeItem" style="width:15px;" 
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
								id="pa${item.PARAM_ID}" 
								${item.ISCHECKED } group=""
								/>
							<input type="text" id="paSort${item.PARAM_ID}" name="insuranceInputItem" style="width:15px;" 
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
								id="pa${item.ITEM_ID}" 
								${item.ISCHECKED } group="" 
								/>
							<input type="text" id="paSort${item.ITEM_ID}" name="insuranceItem" style="width:15px;" 
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