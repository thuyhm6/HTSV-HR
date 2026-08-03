<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_checkAll_bonus(checkBoxFlag){
   	$("#BonusResultCenter :checkbox").each(function ()
   	{	
       	this.checked = checkBoxFlag ;  
    });
   	if(checkBoxFlag){
    	$("#BonusResultCenter :text").addClass("required");
    }else{
    	$("#BonusResultCenter :text").removeClass("required");
    }
}
function f_checkAntiAll_bonus(checkBoxFlag){
	$("#BonusResultCenter :checkbox").each(function ()
	{	
		if(this.checked){
			this.checked = false;
			}
		else{
			this.checked = true;
			}  
    });
	if(checkBoxFlag){
    	$("#BonusResultCenter :text").addClass("required");
    }else{
    	$("#BonusResultCenter :text").removeClass("required");
    }
}


var parameter = {}; 
var hasParam=0;
function excelExportBonusResult(obj){
	var exportId=obj.id;
	hasParam=1;//初始化
	//人事项目hrItemList item.DISTINCT_FIELD item.FIELD_NAME
	<c:forEach items="${hrItemList}" var="item">
		addUrlBonusParam("${item.DISTINCT_FIELD}","${item.FIELD_NAME}","Y");
	</c:forEach>

	//输入项目insuranceInputItemList item.PARAM_ID item.ALIAS_NAME
	<c:forEach items="${bonusInputItemList}" var="item">
		addUrlBonusParam("${item.PARAM_ITEM_ID}","${item.PARAM_NAME}","N","1");
	</c:forEach>

	//计算项目 insuranceComputeItemList item.ITEM_ID item.ALIAS_NAME
	<c:forEach items="${bonusComputeItemList}" var="item" >
		addUrlBonusParam("${item.ITEM_ID}","${item.ITEM_NAME}","N","1");
	</c:forEach>  

	parameter["paramNum"] = hasParam;
	parameter["functionFlag"] = "2";
	var menuNo = '${menuNo}';
	parameter["distinguish"] = menuNo;
	parameter["tableNamePart"] = "T_BN_";
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
	 				document.getElementById("excelExportBonusResult").href="/pa/excelExport/downloadResult?pathstr="+resp.pathStr;
	 				document.getElementById("excelExportBonusResult").click();
	 	 		} 
 			} 
 			});
 	}else{
 	      alertMsg.error('<spring:message code="pa.insurance.title.pleaseChooseExportItem"/>');  
 	}
}
function addUrlBonusParam(name,value,flag,exFlag){
	var hrItem=document.getElementById("in"+name);
	var bnItem=document.getElementById("bnSort"+name);
	if(hrItem!=null&&name!=null){
		if(hrItem.checked==true){
			parameter["alias"+hasParam] = name;
			parameter["aliasName"+hasParam] = value;
			parameter["aliasType"+hasParam] = flag;
			parameter["aliasSort"+hasParam] = bnItem.value;
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
				$("input[type='text'][id='bnSort" + idStr + "']").addClass("required");
				
			}else{
				$("input[type='text'][id='bnSort" + idStr + "']").removeClass("required");
			}
		}
	});
});
</script>

<form method="post" action="/pa/bonus/bonusBalance"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this,navTabAjaxDone)">
	<div class="formBar">
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAll_bonus(this.checked)" />
			<spring:message code="pa.salary.title.allChecked"/><!--全选-->
		</label>
		<label style="float: left">
			<input type="checkbox" name="checkbox"
				onclick="f_checkAntiAll_bonus(this.checked)" />
		    <spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
		</label>
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">
							<spring:message code="pa.bonus.title.bonusSettlemented"/><!--奖金结算-->
						</button>
					</div>
				</div>
			</li>
			<li>
				<a class="buttonActive" onclick="excelExportBonusResult(this);"><span>
				<spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
				</a>
				<a class="buttonActive" id="excelExportBonusResult"
					style="display: none">&nbsp;</a>
			</li>
		</ul>
	</div>
	<div class="pageContent" id="BonusResultCenter">
		<div class="panel">
		<h1>
			<spring:message code="pa.insurance.title.personnelItem"/><!--人事项目-->
			<input type="checkbox" class="checkboxCtrl" group="ishrItem"/>
		</h1>
		<div>
		<table>
			
			<c:forEach items="${hrItemList}" var="item" varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>
				<td>
					<!-- 奖金月默认选中 -->
					<c:choose>
						<c:when test="${item.DISTINCT_FIELD eq 'BN_MONTH'}">
							<input type="checkBox" value="${item.FIELD_NAME}"
								name="ishrItem" id="in${item.DISTINCT_FIELD}" 
								checked="checked" group=""
								/>
							<input type="text" id="bnSort${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="1" />
						</c:when>
						<c:when test="${item.DISTINCT_FIELD eq 'GIVE_DATE'}">
							<input type="checkBox" value="${item.FIELD_NAME}"
								name="ishrItem" id="in${item.DISTINCT_FIELD}" 
								checked="checked" group=""
								/>
							<input type="text" id="bnSort${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
											maxlength="2" class="number textInput required" value="2" />
						</c:when>
						<c:otherwise>
							<input type="checkBox" value="${item.FIELD_NAME}"
								name="ishrItem" id="in${item.DISTINCT_FIELD}" ${item.ISCHECKED } group=""/>
							<input type="text" id="bnSort${item.DISTINCT_FIELD}" name="ishrItem" style="width:15px;" 
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
			<spring:message code="pa.insurance.title.inputItem"/><!--输入项目-->
			<input type="checkbox" class="checkboxCtrl" group="bnParamItem"/>
		</h1>
		<div>
		<table>
			<c:forEach items="${bonusInputItemList}" var="item" varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>
				<td>
					<input type="checkBox" name="bnParamItem"
						value="${item.PARAM_NAME}" id="in${item.PARAM_ITEM_ID}" 
						 ${item.ISCHECKED } group=""
						/>
					<input type="text" id="bnSort${item.PARAM_ITEM_ID}" name="bnParamItem" style="width:15px;" 
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
					&nbsp;&nbsp;${item.PARAM_NAME}&nbsp;&nbsp;
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
			<spring:message code="pa.insurance.title.computeItem"/><!--计算项目-->
			<input type="checkbox" class="checkboxCtrl" group="bnItem"/>
		</h1>
		<div>
		<table>
			<c:forEach items="${bonusComputeItemList}" var="item"
				varStatus="i">
				<c:if test="${i.index == 0}">
					<tr>
				</c:if>
				<td>
					<input type="checkBox" name="bnItem"
						value="${item.ITEM_NAME}"
						id="in${item.ITEM_ID}" 
						 ${item.ISCHECKED } group=""
						/>
					<input type="text" id="bnSort${item.ITEM_ID}" name="bnItem" style="width:15px;" 
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

				<c:if test="${i.index + 1 == fn:length(bonusComputeItemList)}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</div>
</div>
</form>