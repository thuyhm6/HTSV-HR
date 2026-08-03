<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_paHistoryTwo(checkBoxFlag){
   	$("#paHistoryResultCenter :checkbox").each(function ()
   	{
       	this.checked = checkBoxFlag ;  
    });
   	if(checkBoxFlag){
    	$("#paHistoryResultCenter :text").addClass("required");
    }else{
    	$("#paHistoryResultCenter :text").removeClass("required");
    }
}
function f_checkAntiAll_paHistoryTwo(checkBoxFlag){
	$("#paHistoryResultCenter :checkbox").each(function ()
	{	
		if(this.checked){
			this.checked = false;
		}else{
			this.checked = true;
		}  
    });
   	if(checkBoxFlag){
    	$("#paHistoryResultCenter :text").addClass("required");
    }else{
    	$("#paHistoryResultCenter :text").removeClass("required");
    } 
}

var parameterPa = {}; 
var hasParamPa=0;
function excelExportPaHistoryResultTwo(obj){
	var $form = $("#view_paHistory",navTab.getCurrentPanel());
	var paBeginMonth = $form.find("#paBeginYearTwo",navTab.getCurrentPanel()).val() + $form.find("#paBeginMonthTwo",navTab.getCurrentPanel()).val();
	var paEndMonth = $form.find("#paEndYearTwo",navTab.getCurrentPanel()).val() + $form.find("#paEndMonthTwo",navTab.getCurrentPanel()).val();
	if(paEndMonth < paBeginMonth){
		//薪资区间的结束月份不得晚于开始月份！请重新选择结束月！
		alertMsg.info('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	}else{
		var key = $form.find("#seach_KEY",navTab.getCurrentPanel()).val();
		var deptNo = $form.find("#seach_DEPTNO",navTab.getCurrentPanel()).val();
		var exportId=obj.id;
		hasParamPa=1;//初始化
		//人事项目
		<c:forEach items="${hrItemListHistory}" var="item">
			addUrlParamHistoryTwo("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
		</c:forEach>
		
		//考勤项目  
		<c:forEach items="${arItemListHistory}" var="item"> 
			addUrlParamHistoryTwo("${item.COLUMN_NAME}","${item.ITEM_NAME}","N");
		</c:forEach> 
	
		//输入项目
		<c:forEach items="${paInputItemListHistory}" var="item">
			addUrlParamHistoryTwo("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1");
		</c:forEach>  
	
		//计算项目
		<c:forEach items="${paComputeItemListHistory}" var="item" >
			addUrlParamHistoryTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
		</c:forEach>  
		  
		//基础项目
		<c:forEach items="${paBasicInputItemListHistory}" var="item" >
			addUrlParamHistoryTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
		</c:forEach> 
	
		//保险输入项目
		<c:forEach items="${insuranceInputItemListHistory}" var="item">
			addUrlParamHistoryTwo("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
		</c:forEach>
		 
		//保险项目
		<c:forEach items="${insuranceComputeItemListHistory}" var="item">
			addUrlParamHistoryTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
		</c:forEach> 
	
		//奖金项目
		//<c:forEach items="${bonusComputeItemListHistory}" var="item">
		//	addUrlParamHistoryTwo("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
		//</c:forEach>
		//alert(hasParamPa);
		parameterPa["paramNum"] = hasParamPa;
		parameterPa["tableNamePart"] = "PA_SUMMARY_";
		//"4"表示为部门工资
		parameterPa["functionFlag"] = "4";
		//parameterPa["distinguish"] = "2";
		var menuNo = '${menuNo}';
		parameterPa["distinguish"] = menuNo;

		if($("#paBEGIN_GIVE_DATETwo").val() == null || $("#paBEGIN_GIVE_DATETwo").val() == ""){
			alertMsg.error('<spring:message code="zxc.pa.history.PLEASE_CHOOSE_BEGIN_GIVE_DATE"/>');
			return;
		}
		if($("#paEND_GIVE_DATETwo").val() == null || $("#paEND_GIVE_DATETwo").val() == ""){
			alertMsg.error('<spring:message code="zxc.pa.history.PLEASE_CHOOSE_END_GIVE_DATE"/>');
			return;
		}

		parameterPa["paBeginGiveDate"] = $form.find("#paBEGIN_GIVE_DATETwo").val();
		parameterPa["paEndGiveDate"] = $form.find("#paEND_GIVE_DATETwo").val();
		
	 	if(hasParamPa>1){
	 		$.ajax({ 
	 			async: false,
	 			type: "POST",
	 			url: encodeURI("/pa/excelExport/exportPaHistoryResult?paBeginMonth="+paBeginMonth+"&paEndMonth="+paEndMonth+"&key="+key+"&deptNo="+deptNo), 
	 			data: parameterPa,
	 			dataType: "json",
	 			success: function(resp){
	 				if(resp.pathStr=="N"){
	 					alertMsg.error('<spring:message code="pa.insurance.title.exportFaild"/>');
	 	 			}else if(resp.pathStr=="K"){
	 	 	 			alertMsg.error('<spring:message code="pa.insurance.title.exportExceling"/>');
	 				}else{
	 	 				document.getElementById("excelExportPaHistoryResult").href="/pa/excelExport/downloadPaHistoryResult?pathstr="+resp.pathStr;
	 	 				document.getElementById("excelExportPaHistoryResult").click();
	 	 	 		} 
	 			} 
	 			});
	 	}else{
	 		alertMsg.info('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');
	 	}
	}
}
function addUrlParamHistoryTwo(name,value,flag,exFlag){
	var hrItem=document.getElementById("his42"+name);
	var paHisSort=document.getElementById("paHisSort42"+name);
	if(hrItem!=null&&name!=null){
		if(hrItem.checked==true){
			parameterPa["alias"+hasParamPa] = name;
			parameterPa["aliasName"+hasParamPa] = value;
			parameterPa["aliasType"+hasParamPa] = flag;
			parameterPa["aliasSort"+hasParamPa] = paHisSort.value;
			parameterPa["aliasExpFlag"+hasParamPa] = exFlag;
			hasParamPa++;
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
			var idStr=this.id.substring(3,this.id.length);//获得DISTINCT_FIELD
			if(this.checked == true){
				$("input[type='text'][id='paHisSort" + idStr + "']").addClass("required");
				
			}else{
				$("input[type='text'][id='paHisSort" + idStr + "']").removeClass("required");
			}
		}
	});
});

function getGiveDateOfCurrentPaMonthTwo(obj){
	var parameterPaHis = {};
	var idStr = obj.id.substring(2,3);
	if(idStr == 'B'){
		parameterPaHis["PA_MONTH"] = $("#paBeginYearTwo",navTab.getCurrentPanel()).val() + $("#paBeginMonthTwo",navTab.getCurrentPanel()).val();
	}
	if(idStr =='E'){
		parameterPaHis["PA_MONTH"] = $("#paEndYearTwo",navTab.getCurrentPanel()).val() + $("#paEndMonthTwo",navTab.getCurrentPanel()).val();
	}
	parameterPaHis["TABLE_NAME"] = "PA_CALC_OBJECT";
	$.ajax({
		type: 'POST',
		url: "/pa/salary/getGiveDateOfCurrentPaMonth",
		data: parameterPaHis,
		dataType: "json",
		cache: false,
		success: function(data){
			var options="";
			$.each(data.giveDateList,function(){
				if($(data.giveDateList).size()>0){
					options+='<option value="'+this.GIVE_DATE+'">'+this.GIVE_DATE+'</option>';
				}
			});
			if(idStr == 'B'){
				$("#paBEGIN_GIVE_DATETwo").empty();
				$("#paBEGIN_GIVE_DATETwo").append(options);
			}
			if(idStr =='E'){
				$("#paEND_GIVE_DATETwo").empty();
				$("#paEND_GIVE_DATETwo").append(options);
			}
		},
		error: DWZ.ajaxError
	});
}
</script>
<form id="view_paHistory" method="post" action="/pa/salary/viewPaHistoryResult" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent" width="100%">
			<tr>
				<td style="text-align:center" width="6%"> 
					<spring:message code="liang.pa.insurance.title.xinziqujian"/><!--薪资区间-->:
				</td>
				<td style="text-align:center" width="10%">
					<ait:date yearName="paBeginYearTwo" monthName="paBeginMonthTwo" onChange="getGiveDateOfCurrentPaMonthTwo(this)"/>
					<select id="paBEGIN_GIVE_DATETwo" name="paBEGIN_GIVE_DATETwo" style="width:100px;">
						<c:forEach items="${giveDateList}" var="date">
							<option value="${date.GIVE_DATE }">${date.GIVE_DATE }</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align:center" width="1%">
					~
				</td>
				<td style="text-align:center" width="10%">
					<ait:date yearName="paEndYearTwo" monthName="paEndMonthTwo" onChange="getGiveDateOfCurrentPaMonthTwo(this)"/>
					<select id="paEND_GIVE_DATETwo" name="paEND_GIVE_DATE" style="width:100px;">
						<c:forEach items="${giveDateList}" var="date">
							<option value="${date.GIVE_DATE }">${date.GIVE_DATE }</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align:right" width="6%"> 
					<spring:message code="public.title.name"/><!--姓名-->/
					<spring:message code="public.title.empId"/><!--工号-->:
				</td>
				<td style="text-align:left" width="6%">
					<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY }"/>
				</td>
				<td style="text-align:right" width="8%"> 
					<spring:message code="public.title.deptName"/><!--部门-->:
				</td>
				<td style="text-align:left" width="18%">
					<c:if test="${loginName ne 'IT'}">
						<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
					</c:if>
					<c:if test="${loginName eq 'IT'}">
						<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
					</c:if>
				</td>
				<td>&nbsp;&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
			<label style="float: left">
				<input type="checkbox" name="checkbox" onclick="f_checkAll_paHistoryTwo(this.checked)" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
			</label>
			<label style="float: left">
				<input type="checkbox" name="checkbox" onclick="f_checkAntiAll_paHistoryTwoTwo(this.checked)" />
				<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
			</label>
		 	<ul>
				<li>
					<a class="buttonActive" onclick="excelExportPaHistoryResultTwo(this);"><span>
						<spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
					</a>
					<a class="buttonActive" id="excelExportPaHistoryResult" style="display: none">&nbsp;</a>
				</li>
			</ul>
		</div> 	
	</div>
	<div class="pageContent" id="paHistoryResultCenter">
		<div class="panel">
			<h1>
				<spring:message code="pa.salary.title.humanItem"/><!--人事项目-->
				<input type="checkbox" class="checkboxCtrl" group="pahrItem" />
			</h1>
			<div>
				<table>
					<c:forEach items="${hrItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
						<c:choose>
								<c:when test="${item.DISTINCT_FIELD eq 'PA_MONTH'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="his42${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paHisSort42${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="1" />
								</c:when>
								<c:when test="${item.DISTINCT_FIELD eq 'GIVE_DATE'}">
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="his42${item.DISTINCT_FIELD}" checked="checked" group="" />
									<input type="text" id="paHisSort42${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="2" />
								</c:when>
								<c:otherwise>
									<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" 
										id="his42${item.DISTINCT_FIELD}" 
										${item.ISCHECKED } group=""
										/>
									<input type="text" id="paHisSort42${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
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
							<%--<input type="checkBox" value="${item.FIELD_NAME}" name="pahrItem" id="his42${item.DISTINCT_FIELD}" group=""/>
							<input type="text" id="paHisSort42${item.DISTINCT_FIELD}" name="pahrItem" style="width:15px;" 
											maxlength="2"
											<c:choose>
												<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
													class="number textInput required"
												</c:when>
												<c:otherwise>
													class="number textInput"
												</c:otherwise>
											</c:choose>
											value="${item.ORDERNO }"/>--%>
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
					<c:forEach items="${arItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" value="${item.ITEM_NAME}" name="paArItem" id="his42${item.COLUMN_NAME}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.COLUMN_NAME}" name="paArItem" style="width:15px;" 
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
					<c:forEach items="${paBasicInputItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paBasicInputItem" value="${item.ALIAS_NAME}" id="his42${item.ITEM_ID}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.ITEM_ID}" name="paBasicInputItem" style="width:15px;" 
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
					<c:forEach items="${paInputItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paInputItem" value="${item.ALIAS_NAME}" id="his42${item.PARAM_ITEM_ID}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.PARAM_ITEM_ID}" name="paInputItem" style="width:15px;" 
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
					<c:forEach items="${paComputeItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="paComputeItem" value="${item.ALIAS_NAME}" id="his42${item.ITEM_ID}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.ITEM_ID}" name="paComputeItem" style="width:15px;" 
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
					<c:forEach items="${insuranceInputItemListHistory}" var="item"
						varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="insuranceInputItem" value="${item.ALIAS_NAME}" id="his42${item.PARAM_ID}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.PARAM_ID}" name="insuranceInputItem" style="width:15px;" 
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
					<c:forEach items="${insuranceComputeItemListHistory}" var="item" varStatus="i">
						<c:if test="${i.index == 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkBox" name="insuranceItem" value="${item.ALIAS_NAME}" id="his42${item.ITEM_ID}" ${item.ISCHECKED } group="" />
							<input type="text" id="paHisSort42${item.ITEM_ID}" name="insuranceItem" style="width:15px;" 
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
           		<c:forEach items="${bonusComputeItemListHistory}" var="item" varStatus="i">
           			<c:if test="${i.index == 0}">
           				<tr>
           			</c:if>
           			<td ><input type="checkBox" name="${item.ITEM_ID}" value="${item.ALIAS_NAME}" id="his42${item.ITEM_ID}"/>
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
           	</table>-->
	</div>
</form>