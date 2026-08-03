<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function navTabSearch_viewPaResult(form) {

	var $form = $("#savePaResult");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;

}

function savePaResult() {
	$("#savePaResult").attr("action", "/pa/workManagement/savePaResult");
	$("#savePaResult").attr("onsubmit", "return  navTabSearch_viewPaResult(this)");
	$("#savePaResult").attr("class", "j-ajax");

	var chk_value_name = [];//定义一个数组    

	$('input[name="paArItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
				var orderno = $('#paSort' + id_value).attr('value');
				chk_value_name.push($(this).val() + "-" + $(this).attr("alt")
						+ "-" + orderno);//将选中的值添加到数组chk_value中    

			});

	$('input[name="paInputItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
				var orderno = $('#paSort' + id_value).attr('value');
				chk_value_name.push($(this).val() + "-" + $(this).attr("alt")
						+ "-" + orderno);//将选中的值添加到数组chk_value中    

			});

	$('input[name="paComputeItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数    
				var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
				var orderno = $('#paSort' + id_value).attr('value');
				chk_value_name.push($(this).val() + "-" + $(this).attr("alt")
						+ "-" + orderno);//将选中的值添加到数组chk_value中    

			});

	$("#CHECK_PARAM_ITEM_NAME").val(chk_value_name);

	$("#savePaResult").submit();

}



function checkViewPaResult() {
	$('input[name="paArItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
		var id = $(this).attr("id");
		this.checked = false ;
		var id_value = id.substring(2, id.length);
		$('#paSort' + id_value).attr('value','');
		$('#paSort' + id_value).removeClass("required");
	});
	$('input[name="paInputItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
		var id = $(this).attr("id");
		this.checked = false ;
		var id_value = id.substring(2, id.length);
		$('#paSort' + id_value).attr('value','');
		$('#paSort' + id_value).removeClass("required");
	});
	$('input[name="paComputeItem"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
		var id = $(this).attr("id");
		this.checked = false ;
		var id_value = id.substring(2, id.length);
		$('#paSort' + id_value).attr('value','');
		$('#paSort' + id_value).removeClass("required");
	});
	
	
	$("#checkViewPaResult").attr("action", "/pa/workManagement/checkViewPaResult?ITEM_TYPE2="+$("#ITEM_TYPE2").attr("value")+"&IS_USE2="+$("#IS_USE").attr("value"));
	$("#checkViewPaResult").attr("onsubmit", "return  navTabSearch_checkViewPaResult(this)");
	$("#checkViewPaResult").attr("class", "j-ajax");
	 
	 
    
	$("#checkViewPaResult").submit();

}

function backCheckPaResult(date){

	$('input[name="paInputItem"]').removeAttr("checked"); //清除选中
	$('input[name="paComputeItem"]').removeAttr("checked");
	$('input[name="paArItem"]').removeAttr("checked");
	 
	var items=date.checkViewPaResult;
	if(items.length>0){
	for(var i=0;i<items.length;i++){
	var compear2=items[i].ITEM_NO+"-"+items[i].ITEM_ID;
 	$('input[name="paArItem"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
 		  
				var compear = $(this).attr("alt");  //获取这个值进行对比
				 
	           if(compear==compear2){
	        	  $(this).attr("checked","checked");//符合条件的选中
	        	var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
			    $('#paSort' + id_value).attr('value',items[i].ORDERNO);  //order也改掉
	        	  
	        	  
	           }
	 
   }); 
	 
 	$('input[name="paInputItem"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
 		  
				var compear = $(this).attr("alt");  //获取这个值进行对比
				 
	           if(compear==compear2){
	        	  $(this).attr("checked","checked");//符合条件的选中
	        	var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
			    $('#paSort' + id_value).attr('value',items[i].ORDERNO);  //order也改掉
	        	  
	        	  
	           }
	 
   }); 
 	
 		$('input[name="paComputeItem"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数    
 		 
					var compear = $(this).attr("alt");  //获取这个值进行对比
				 
	           if(compear==compear2){
	        	  $(this).attr("checked","checked");
	        	var id = $(this).attr("id");
				var id_value = id.substring(2, id.length);
			    $('#paSort' + id_value).attr('value',items[i].ORDERNO);
	        	  
	        	  
	           }

			});
 	
 	
 	}
   }
}



function navTabSearch_checkViewPaResult(form) {
    
    
	var $form = $("#checkViewPaResult");
 
 
 
	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"), 
		data:"text", 
		dataType : "json",
		cache : false ,
		success :function(date){
	backCheckPaResult(date);
		},
		error : DWZ.ajaxError
	});

	return false;

}




function f_checkAll_pa(checkBoxFlag) {
	$("#paResultCenter :checkbox").each(function() {
		this.checked = checkBoxFlag;
	});
	if (checkBoxFlag) {
		$("#paResultCenter :text").addClass("required");
	} else {
		$("#paResultCenter :text").removeClass("required");
	}
}
function f_checkAntiAll_pa(checkBoxFlag) {
	$("#paResultCenter :checkbox").each(function() {
		if (this.checked) {
			this.checked = false;
		} else {
			this.checked = true;
		}
	});
	if (checkBoxFlag) {
		$("#paResultCenter :text").addClass("required");
	} else {
		$("#paResultCenter :text").removeClass("required");
	}
}

var parameterPa = {};
var hasParamPa = 0;
function saveExcelPaResult(obj) {
	var exportId = obj.id;
	hasParamPa = 1;//初始化
	//人事项目
<c:forEach items="${hrItemList}" var="item">
		 addUrlParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y","",1);
	</c:forEach>
	
	//考勤项目  
	<c:forEach items="${arItemList}" var="item"> 
		addUrlParam("${item.COLUMN_NAME}","${item.ITEM_NAME}","N","",2);
	</c:forEach> 

	//输入项目
	<c:forEach items="${paInputItemList}" var="item">
 		addUrlParam("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1",3);
	</c:forEach>  

	//计算项目
	<c:forEach items="${paComputeItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1",3);
	</c:forEach>  
	  
	//基础项目
	/* <c:forEach items="${paBasicInputItemList}" var="item" >
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1",3);
	</c:forEach>  */

	//保险输入项目
	/* <c:forEach items="${insuranceInputItemList}" var="item">
		addUrlParam("${item.PARAM_ID}","${item.ALIAS_NAME}","N","2",4);
	</c:forEach> */
	 
	//保险项目
	/* <c:forEach items="${insuranceComputeItemList}" var="item">
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","2",4);
	</c:forEach>  */

	   

	//奖金项目
	//<c:forEach items="${bonusComputeItemList}" var="item">
	//	addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
	//</c:forEach>
	//alert(hasParamPa);
	parameterPa["paramNum"] = hasParamPa;

	//parameterPa["tableNamePart"] = "T_PA_";
	parameterPa["tableNamePart"] = "PA_SUMMARY_";
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
				    alertMsg.info('<spring:message code="alert.message.save_success"/>');//保存成功
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
	 	 document.getElementById("paSort"+"${item.DISTINCT_FIELD}").value='';
		 addUrlParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>
	
	//考勤项目  
	<c:forEach items="${arItemList}" var="item"> 
	 	document.getElementById("paSort"+"${item.COLUMN_NAME}").value='';
		addUrlParam("${item.COLUMN_NAME}","${item.ITEM_NAME}","N");
	</c:forEach> 

	//输入项目
	<c:forEach items="${paInputItemList}" var="item">
	 	document.getElementById("paSort"+"${item.PARAM_ITEM_ID}").value='';
 		addUrlParam("${item.PARAM_ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  

	//计算项目
	<c:forEach items="${paComputeItemList}" var="item" >
	 	document.getElementById("paSort"+"${item.ITEM_ID}").value='';
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  
	  
	//基础项目
	/* <c:forEach items="${paBasicInputItemList}" var="item" >
	 	document.getElementById("paSort"+"${item.ITEM_ID}").value='';
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  */

	//保险输入项目
	/* <c:forEach items="${insuranceInputItemList}" var="item">
	 	document.getElementById("paSort"+"${item.PARAM_ID}").value='';
		addUrlParam("${item.PARAM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach> */
	 
	//保险项目
	/* <c:forEach items="${insuranceComputeItemList}" var="item">
	 	document.getElementById("paSort"+"${item.ITEM_ID}").value='';
		addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N","1");
	</c:forEach>  */

	//奖金项目
	//<c:forEach items="${bonusComputeItemList}" var="item">
	//	addUrlParam("${item.ITEM_ID}","${item.ALIAS_NAME}","N");
	//</c:forEach>
	//alert(hasParamPa);
	parameterPa["paramNum"] = hasParamPa;

	//parameterPa["tableNamePart"] = "T_PA_";
	parameterPa["tableNamePart"] = "PA_SUMMARY_";
	var paMonth = $("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).val();
	//var empTypeGroup = $("#result_EMP_TYPE_GROUP",navTab.getCurrentPanel()).val();
	//var statNo = $("#result_STATNO",navTab.getCurrentPanel()).val();
	//var deptNo = $("#result_DEPTNO",navTab.getCurrentPanel()).val();
	var deptNo = $(":input[sysLong='seachBankDept']").val();
	//alertMsg.info(paMonth);
	//alertMsg.info(empTypeGroup+":::::"+deptNo);
	parameterPa["PAY_SCHEDULE_NO"] = paMonth;
	//parameterPa["empTypeGroup"] = empTypeGroup;
	/* parameterPa["statNo"] = statNo;
	parameterPa["deptNo"] = deptNo;
	parameterPa["saveYn"] ="N"; */
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
function addUrlParam(name,value,flag,exFlag,dataType){
	var hrItem=document.getElementById("pa"+name);
	var paSort=document.getElementById("paSort"+name);
	if(hrItem.checked==true){
		parameterPa["alias"+hasParamPa] = name;
		parameterPa["aliasName"+hasParamPa] = value;
		parameterPa["aliasType"+hasParamPa] = flag;
		parameterPa["aliasSort"+hasParamPa] = paSort.value;
		parameterPa["aliasExpFlag"+hasParamPa] = exFlag;
		if(dataType){
			parameterPa["aliasDataType"+hasParamPa] = dataType;
		}
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
<form method="post" action="/pa/salary/paBalance" name="excelExport"
	class="pageForm required-validate" id="savePaResult"
	onsubmit="return validateCallback(this)">

	<input name="CHECK_PARAM_ITEM_NO" id="CHECK_PARAM_ITEM_NO"
		type="hidden" value="">
	<input name="CHECK_PARAM_ITEM_NAME" id="CHECK_PARAM_ITEM_NAME"
		type="hidden" value="">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<%-- <td><spring:message code="pa.insurance.title.salaryMonth"/><!--工资月--></td>
				<td><ait:date yearName="result_year" monthName="result_month"/></td>
				<td>区间</td>
				<td>
					<select id="result_STATNO" name="result_STATNO">
						<c:forEach items="${statList}" var="stat">
							<option value="${stat.STAT_NO}">${stat.STAT_NAME}</option>
						</c:forEach>
					</select>
				</td> --%>
				<td>
					<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:
				</td>
				<td>
					<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
						<c:forEach items="${paPayScheduleList}" var="paySchedule"
							varStatus="i">
							<c:choose>
								<c:when
									test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
									<option value="${paySchedule.PAY_SCHEDULE_NO }"
										selected="selected">
										${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }
									</option>
								</c:when>
								<c:otherwise>
									<option value="${paySchedule.PAY_SCHEDULE_NO }">
										${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }
									</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
				<td>
					<spring:message code="public.title.deptName" />
					<!--部门-->
					：
				</td>
				<td>
					<ait:deptList name="result_DEPTNO" id="seachBankDept" limit="pa" />
					<ait:deptTreeIcon name="result_DEPTNO" limit="pa"
						id="seachBankDept" selected="${DEPTNO}" />
				</td>
				
			</tr>
			<tr>
			    <td>
					<!--勾选项目--><spring:message code="pa.viewPaResult.GOUXUANXIANGMU.b" />
				</td>
				<td>
					<select name="IS_USE" id="IS_USE">
						<option value="1">
							<!--月环比项目明细--><spring:message code="pa.viewPaResult.YUEHUANBIXIANGMUMINGXI.b" />
						</option>
						<!-- <option value="2">
							工资差异报表
						</option>
						<option value="3">
							给予-扣除-保险项目
						</option> -->
						<option value="4">
							<!--项目别核对--><spring:message code="pa.viewPaResult.XIANGMUBIEHEDUI.b" /> | <!--结果确认--><spring:message code="pa.viewPaResult.JIEGUOQUEREN.b" />-<!--支付对比--><spring:message code="pa.viewPaResult.ZHIFUDUIBI.b" />&<!--保险对比--><spring:message code="pa.viewPaResult.BAOXIANDUIBI.b" />
						</option>
						<option value="5">
							<!--月/年工资明细--><spring:message code="pa.viewPaResult.YUENIANGONGZIMINGXI.b" />
						</option>
						<option value="6">
							<!--工资条--><spring:message code="pa.viewPaMain.GONGZITIAO.C" />
						</option>
					</select>

				</td>
			</tr>
			<tr>
			    <td>
					<!--项目区分--><spring:message code="liang.public.title.ItemDistinguish" />
				</td>
				<td>

					<select name="ITEM_TYPE" id="ITEM_TYPE2">

						<option value="1">
							<!--给予项目(工资条中的出勤明细)--><spring:message code="pa.viewPaResult.JIYUXIANGMUGONGZITIAOCHUQINMINGXI.b" />
						</option>
						<option value="2">
							<!--扣除项目(工资条中的工资明细)--><spring:message code="pa.viewPaResult.KOUCHUXIANGMUGONGZITIAOGONGZIMINGXI.b" />
						</option>
						<option value="3">
							<!--保险项目(工资条中的扣除明细)--><spring:message code="pa.viewPaResult.BAOXIANXIANGMUGONGZITIAOKOUCHUMINGXI.b" />
						</option>
						<option value="4">
							<!--标准项目(工资条中的标准项目)-->标准项目(工资条中的标准项目)
						</option>
					</select>

				</td>
			</tr>
		</table>
		<div class="subBar">
			<c:if
				test="${LoginUser.adminID eq '11111111' or LoginUser.adminID eq '11111112'
				or LoginUser.adminID eq '11111113'or LoginUser.adminID eq '11111114'
				or LoginUser.adminID eq '11111115'or LoginUser.adminID eq '11111116'}">
				<ul>
					<li>
						<a class="buttonActive" onclick="checkViewPaResult(this);"><span>
								<!--查询--><spring:message code="public.title.search" /></span> </a>
						<!--	<a class="buttonActive" id="saveExcelPaResult"
					style="display: none">&nbsp;</a></li> -->
				</ul>

				<ul>
					<li>
						<a class="buttonActive" onclick="savePaResult(this);"><span>
								<!--保存--><spring:message code="ess.message.save" /></span> </a>
						<!--	<a class="buttonActive" id="saveExcelPaResult"
					style="display: none">&nbsp;</a></li> -->
				</ul>
			</c:if>


			<ul>
				<li>
					<a class="buttonActive" onclick="excelExportPaResult(this);"><span>
							<spring:message code="pa.insurance.title.excelExport" /> <!--EXCEL导出-->
					</span> </a>
					<a class="buttonActive" id="excelExportPaResult"
						style="display: none">&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="formBar">
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAll_pa(this.checked)" />
			<spring:message code="pa.salary.title.allChecked" />
			<!--全选-->
		</label>
		<label style="float: left;padding-left:10px;">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAntiAll_pa(this.checked)" />
			<spring:message code="pa.salary.title.opsiteChecked" />
			<!--反选-->
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
				<spring:message code="pa.salary.title.humanItem" />
				<!--人事项目-->
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
									<input type="checkBox" value="${item.FIELD_NAME}"
										name="pahrItem" id="pa${item.DISTINCT_FIELD}"
										checked="checked" group="" />
									<input type="text" id="paSort${item.DISTINCT_FIELD}"
										name="pahrItem" style="width: 15px;" maxlength="2"
										class="number textInput required" value="1" />
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
									<input type="checkBox" value="${item.FIELD_NAME}"
										name="pahrItem" id="pa${item.DISTINCT_FIELD}"
										${item.ISCHECKED } group=""
										<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if> />
									<input type="text" id="paSort${item.DISTINCT_FIELD}"
										name="pahrItem" style="width: 15px;" maxlength="2"
										<c:choose>
												<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
													class="number textInput required"
												</c:when>
												<c:otherwise>
													class="number textInput"
												</c:otherwise>
											</c:choose>
										<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if> />
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
				<spring:message code="pa.salary.title.attendanceItem" />
				<!--考勤项目-->
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
								alt="${item.ITEM_NO}-${item.ITEM_ID}"
								id="pa${item.COLUMN_NAME}" ${item.ISCHECKED } group=""
								<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if> />
							<input type="text" id="paSort${item.COLUMN_NAME}" name="paArItem"
								style="width: 15px;" maxlength="2"
								<c:choose>
											<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
												class="number textInput required"
											</c:when>
											<c:otherwise>
												class="number textInput"
											</c:otherwise>
										</c:choose>
								<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if> />
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
				<spring:message code="pa.salary.title.inputItem" />
				<!--输入项目-->
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
								alt="${item.PARAM_ITEM_NO}-${item.PARAM_ITEM_ID}"
								value="${item.ALIAS_NAME}" id="pa${item.PARAM_ITEM_ID}"
								${item.ISCHECKED } group=""
								<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if> />
							<input type="text" id="paSort${item.PARAM_ITEM_ID}"
								name="paInputItem" style="width: 15px;" maxlength="2"
								<c:choose>
											<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
												class="number textInput required"
											</c:when>
											<c:otherwise>
												class="number textInput"
											</c:otherwise>
										</c:choose>
								<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if> />
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
				<spring:message code="pa.salary.title.caculateItem" />
				<!--计算项目-->
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
								value="${item.ALIAS_NAME}" alt="${item.ITEM_NO}-${item.ITEM_ID}"
								id="pa${item.ITEM_ID}" ${item.ISCHECKED } group=""
								<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if> />
							<input type="text" id="paSort${item.ITEM_ID}"
								name="paComputeItem" style="width: 15px;" maxlength="2"
								<c:choose>
											<c:when test="${item.ORDERNO ne null && item.ORDERNO ne ''}">
												class="number textInput required"
											</c:when>
											<c:otherwise>
												class="number textInput"
											</c:otherwise>
										</c:choose>
								<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if> />
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
		<%-- <div class="panel">
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
								<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if>/>
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
										<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if> />
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
								<c:if test="${item.ITEM_FLAG eq '1' }"> checked="checked" </c:if>/>
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
										<c:if test="${item.ITEM_NUMBER ne '0'}"> value="${item.ITEM_NUMBER }" </c:if>/>
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
		</div> --%>
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

<div style="display: none">
	<form id="checkViewPaResult">

	</form>
</div>