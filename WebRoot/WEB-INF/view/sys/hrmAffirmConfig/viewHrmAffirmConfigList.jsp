<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
	.configTable{width:100%; border:1px solid #DEEDF8; cellpadding:0; cellspacing:0; height:100%;
				 border-collapse: collapse ;
	}
	.configTable1{width:100%; border:1px solid #DEEDF8; cellpadding:0; cellspacing:0; height:100%;
				 border-collapse: collapse ;
	}
	.navDiv{ position:fixed; width:84%; z-index:999;}
	.navTr{ position:fixed; width:84%; z-index:999;}
	.navCenter{margin:0 auto;}
	.emptyDiv{height:45px;width:84%;}
	.emptyDiv1{height:110px;width:84%;}
	.li{margin-left:20px;margin-bottom:10px;}
</style>
<script type="text/javascript">
function saveHrmAffirmConfig(){
	
	var parameterHrm = {}; 
	var hasParamHrm = 0;
	var size = 0;
	
	if( $("#selectedCpny").val() !== null){
		if(confirm ('<spring:message code="zxc.hr.hrmAffirmConfig.submit"/>')){
			size=$("#distinctListSize").val();
			$("tr[id^='type_']").each(function(){
				var id=this.id.substring(this.id.indexOf("_")+1,this.id.length);
				
				var looks = new Array(size);
				var updates=new Array(size);
				var searches=new Array(size);
				for(var i = 1;i < size;i++){
					looks[i]=document.getElementById("config_look_"+id+"_"+(i));
					if(looks[i].checked == true){
						parameterHrm["look_"+id+"_"+(i)]=looks[i].value;
					}
					updates[i]=document.getElementById("config_update_"+id+"_"+(i));
					if(updates[i].checked == true){
						parameterHrm["update_"+id+"_"+(i)]=updates[i].value;
					}
					searches[i]=document.getElementById("config_search_"+id+"_"+(i));
					if(searches[i].checked == true){
						parameterHrm["search_"+id+"_"+(i)]=searches[i].value;
					}
				}
				
				
			/*	var looks=new Array(size);
				for(var i = 0;i < size;i++){
					looks[i]=document.getElementById("config_look_"+id+"_"+(i + 1));
					if(i == (size - 1)){
						for(var j = 0; j < looks.length; j++){
							if(looks[j].checked == true){
								parameterHrm["look_"+id+"_"+(j + 1)]=looks[j].value;
							}
						}
					}
				}
				var updates=new Array(size);
				for(var a = 0; a < size; a++){
					updates[a]=document.getElementById("config_update_"+id+"_"+(a + 1));
					if(a == (size - 1)){
						for(var b = 0; b < updates.length; b++){
							if(updates[b].checked == true){
								parameterHrm["update_"+id+"_"+(b + 1)]=updates[b].value;
							}
						}
					}
				}
				var searches=new Array(size);
				for(var x = 0; x < size; x++){
					searches[x]=document.getElementById("config_search_"+id+"_"+(x + 1));
					if(x == (size - 1)){
						for(var y = 0; y < searches.length; y++){
							if(searches[y].checked == true){
								parameterHrm["search_"+id+"_"+(y + 1)]=searches[y].value;
							}
						}
					}
				}*/
			});

			parameterHrm["paramNum"]=size;
			parameterHrm["COMPANY_ID"]=$("#selectedCpny").val();
			
			$.ajax({ 
	 			type: "POST",
	 			url: "/sys/hrmAffirmConfig/saveHrmAffirmConfig", 
	 			data: parameterHrm,
	 			dataType: "json",
	 			cache:	false,
	 			success:  function(data){
	 				if(data.statusCode == 200){
						alertMsg.correct(data.message);
	 				}else{
						alertMsg.error(data.message);
	 				}
	 			},
	 			error: DWZ.ajaxError
	 			});
		}
	}else{
		alert("<spring:message code='zxc.hr.hrmAffirmConfig.warn'/>");//请先选择法人
	}
}


function getSyCompany(value){
	$("#selectedCpny").val(value);
	document.getElementById("FLAG").value="onChangeEvent";
	$("#searchForm_sy0484").submit();
}

//隔行变色
$(document).ready(function(){
//	$("#configTable tr:odd").css("background-color","#999");
	$("#configTable tr:even").css("background-color","#F1F1F1");
	$("#configTable1 tr:even").css("background-color","#FFFFFF");
	$("#configTable1 tr:odd").css("background-color","#A7C0DC");
});

//选择"修改"时同时选中"查看"
$(document).ready(function(){

	$("input[id^='config_look_']").click(function(){
		if(this.checked == false){
			$($(this).closest("li")).siblings().next("li").children("input[type='checkbox']").removeAttr("checked");
		}
	});
	
	$("input[id^='config_update_']").click(function(){
		if(this.checked == true){
			$($(this).closest("li")).siblings().filter(":first").children("input[type='checkbox']").attr("checked","checked");
		}else{
			$($(this).closest("li")).siblings().filter(":first").children("input[type='checkbox']").removeAttr("checked");
		}
	});
});

//$(document).ready(function(){

//	$("#head").addClass("navDiv").addClass("navCenter");
	
//});

function saveCurrentRow(id){
	
	var parameterHrm = {}; 
	var hasParamHrm = 0;
	var size = 0;
	
	if( $("#selectedCpny").val() !== null){
		if(confirm ('<spring:message code="zxc.hr.hrmAffirmConfig.submit"/>')){
			size=$("#distinctListSize").val();
				/*var looks=new Array(size);
				for(var i = 0;i < size;i++){
					looks[i]=document.getElementById("config_look_"+id+"_"+(i + 1));
					if(i == (size - 1)){
						for(var j = 0; j < looks.length; j++){
							if(looks[j].checked == true){
								parameterHrm["look_"+id+"_"+(j + 1)]=looks[j].value;
							}
						}
					}
				}
				var updates=new Array(size);
				for(var a = 0; a < size; a++){
					updates[a]=document.getElementById("config_update_"+id+"_"+(a + 1));
					if(a == (size - 1)){
						for(var b = 0; b < updates.length; b++){
							if(updates[b].checked == true){
								parameterHrm["update_"+id+"_"+(b + 1)]=updates[b].value;
							}
						}
					}
				}
				var searches=new Array(size);
				for(var x = 0; x < size; x++){
					searches[x]=document.getElementById("config_search_"+id+"_"+(x + 1));
					if(x == (size - 1)){
						for(var y = 0; y < searches.length; y++){
							if(searches[y].checked == true){
								parameterHrm["search_"+id+"_"+(y + 1)]=searches[y].value;
							}
						}
					}
				}*/

			var looks = new Array(size);
				var updates=new Array(size);
				var searches=new Array(size);
				for(var i = 1;i < size;i++){
					looks[i]=document.getElementById("config_look_"+id+"_"+(i));
					if(looks[i].checked == true){
						parameterHrm["look_"+id+"_"+(i)]=looks[i].value;
					}
					updates[i]=document.getElementById("config_update_"+id+"_"+(i));
					if(updates[i].checked == true){
						parameterHrm["update_"+id+"_"+(i)]=updates[i].value;
					}
					searches[i]=document.getElementById("config_search_"+id+"_"+(i));
					if(searches[i].checked == true){
						parameterHrm["search_"+id+"_"+(i)]=searches[i].value;
					}
				}
			
			parameterHrm["paramNum"]=size;
			parameterHrm["TRANS_CODE"]=id;
			parameterHrm["COMPANY_ID"]=$("#selectedCpny").val();
			
			$.ajax({ 
	 			type: "POST",
	 			url: "/sys/hrmAffirmConfig/saveHrmAffirmConfigByRow", 
	 			data: parameterHrm,
	 			dataType: "json",
	 			cache:	false,
	 			success:  function(data){
	 				if(data.statusCode == 200){
						alertMsg.correct(data.message);
						getSyCompany($("#COMPANY_ID").val());
	 				}else{
						alertMsg.error(data.message);
	 				}
	 			},
	 			error: DWZ.ajaxError
	 		});
		}
	}else{
		alert("<spring:message code='zxc.hr.hrmAffirmConfig.warn'/>");//请先选择法人
	}
}

function lookByupdate(updateName,lookName,no,index){
	var updateName1 = updateName+"_"+no+"_"+index;
	var lookName1 = lookName+"_"+no+"_"+index;
	var updateName1Val = document.getElementById(updateName1).checked;
	//var lookName1Val = document.getElementById(lookName1).checked;
	document.getElementById(lookName1).checked = updateName1Val;
}
</script>

<div class="pageHeader" id="head" >

	<div class="formBar">
	
		<form id="searchForm_sy0484" onsubmit="return navTabSearch(this);" action="/sys/hrmAffirmConfig/viewHrmAffirmConfigList" method="post">
			<label style="float:left;">
				法人:&nbsp;&nbsp;<ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="COMPANY_ID" language="zh" 
												limit="ALL" activity="1" onChangeName="getSyCompany(this.value)" selected="${COMPANY_ID}"/>
			</label>
			<input type="hidden" id="selectedCpny" name="selectedCpny" value="${COMPANY_ID }"/>
			<input type="hidden" id="FLAG" name="FLAG"/>
			<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }"/>
			<input type="hidden" id="numPerPage" name="numPerPage" value="${numPerPage }"/>
		</form>
		<c:if test="${sessionScope.LoginUser.cpnyId eq 'C02'}">
			<ul>
				<li>
					<div class="buttonActive">
						<a onclick="saveHrmAffirmConfig()">
							<span>
								<spring:message code="zxc.hr.hrmAffirmConfig.save"/><!-- 保存 -->
							</span>
						</a>
					</div>
				</li>
			</ul>
		</c:if>
	</div>
</div>
<!--<div class="emptyDiv">&nbsp;</div>  -->

<div class="pageContent" id="checkboxList">
	<c:set value="0" var="count"></c:set>
	<div style="width:1088px;height:110px;overflow-x:hidden;overflow-y:hidden;position:fixed;">  
	<table class="configTable user_table"  id="configTable">
		<tr>
			<td width="30px">&nbsp;</td>
			<td width="53px" >&nbsp;</td>
			<input type="hidden" id="distinctListSize" value="${fn:length(hrmAffirmConfigDistinctList)}"/>
			<c:forEach items="${hrmAffirmConfigDistinctList}" var="item">
				<td class="td_type td_center " width="5px" >
					${item.TITLE_NAME }  
				</td>
				<c:set value="${count + 1}" var="count"></c:set>
			</c:forEach>
			<td class="td_type td_center" width="45px" >
				<spring:message code="sys.affirm.title.affirmOperation"/><!-- 操作 -->
			</td>
		</tr>
	</table>
	</div>
	
	<div layoutH="100" style="OVERFLOW-Y: auto; OVERFLOW-X: auto; WIDTH: 1088px; HEIGHT: auto;">
	
<div class="emptyDiv1">&nbsp;</div>
	
	<table class="configTable1 user_table" id="configTable1">
		<c:forEach items="${hrmAffirmConfigTransCodeList}" var="transCode" varStatus="status">
			<tr id="type_${transCode.CODE_NO }">
				<td  width="30px">
					${transCode.TRANSFER_TYPE }
					<!--<div class="buttonActive">
						<div class="buttonContent">
							<button type="button"onclick="saveCurrentRow('${transCode.CODE_NO }')">
								save<spring:message code="zxc.hr.hrmAffirmConfig.save"/> 保存 
							</button>
						</div>
					</div>
				--></td>
				<td class="td_type td_center"  nowrap="nowrap"  width="50px">
					<spring:message code="zxc.hrm.affirmConfig.search"/>
				<br>
					<spring:message code="zxc.hrm.affirmConfig.update"/>
				<br>
					<spring:message code="zxc.hrm.affirmConfig.searchShow"/>
				</td>
				<c:if test="${fn:length(preHrmAffirmConfigRecordList[transCode.CODE_NO]) > 0}">
					<c:forEach items="${preHrmAffirmConfigRecordList[transCode.CODE_NO]}" var="record" varStatus="var">
						<td class="td_type td_center" width="5px" >
								<c:choose>
									<c:when test="${record.TRANS_CONFIG_FLAG eq '0'}">
										<c:choose>
											<c:when test="${record.DISTINCT_FIELD eq 'TRANS_ORDER_DATE' || record.DISTINCT_FIELD eq 'TRANS_ORDER_TYPE'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
															checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled" />
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" />
												
											</c:when>
											<c:when test="${record.DISTINCT_FIELD eq 'EMPID' || record.DISTINCT_FIELD eq 'LOCAL_NAME'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
														disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												
											</c:when>
											<c:otherwise>
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }"/>
												
												<br>
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
													onclick="lookByupdate('config_update','config_look',${transCode.CODE_NO },${var.index+1 })"/>
												
												<br>
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
														${record.ISCHECKED }/>
												
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:when test="${record.TRANS_CONFIG_FLAG eq '1'}">
										<c:choose>
											<c:when test="${record.DISTINCT_FIELD eq 'TRANS_ORDER_DATE' || record.DISTINCT_FIELD eq 'TRANS_ORDER_TYPE'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" />
												
											</c:when>
											<c:when test="${record.DISTINCT_FIELD eq 'EMPID' || record.DISTINCT_FIELD eq 'LOCAL_NAME'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
														disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												
											</c:when>
											<c:otherwise>
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" checked="checked"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }"
													onclick="lookByupdate('config_update','config_look',${transCode.CODE_NO },${var.index+1 })"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
														${record.ISCHECKED }/>
												
											</c:otherwise>
										</c:choose>
										</c:when>
										<c:otherwise>
										<c:choose>
											<c:when test="${record.DISTINCT_FIELD eq 'TRANS_ORDER_DATE' || record.DISTINCT_FIELD eq 'TRANS_ORDER_TYPE'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" />
												
											</c:when>
											<c:when test="${record.DISTINCT_FIELD eq 'EMPID' || record.DISTINCT_FIELD eq 'LOCAL_NAME'}">
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
														checked="checked" disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
															disabled="disabled"/>
												<br>
												
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
															checked="checked" disabled="disabled"/>
												
											</c:when>
											<c:otherwise>
												
													<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" checked="checked"/>
												
												<br>
													<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" checked="checked" 
													onclick="lookByupdate('config_update','config_look',${transCode.CODE_NO },${var.index+1 })"/>
												
												<br>
													<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
														${record.ISCHECKED }/>
												
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</td>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(preHrmAffirmConfigRecordList[transCode.CODE_NO]) == 0}">
					<c:forEach begin="0" end="${fn:length(hrmAffirmConfigDistinctList)-1}" var="item" step="1" varStatus="var">
						<td class="td_type td_center" width="5px" >

								<c:choose>
									<c:when test="${var.index == 0 || var.index == 1}">
										
											<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
													checked="checked" disabled="disabled"/>
										
										<br>
											<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
													checked="checked" disabled="disabled"/>
										
										<br>
											<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" />										
										
									</c:when>
									<c:when test="${var.index == 3 || var.index == 4}">
										
											<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" 
													checked="checked" disabled="disabled"/>
										
										<br>
											<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
													disabled="disabled"/>
										
										<br>
											<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" 
													checked="checked" disabled="disabled"/>
										
									</c:when>
									<c:otherwise>
											<input type="checkbox" id="config_look_${transCode.CODE_NO }_${var.index+1 }" />
										<br>
											<input type="checkbox" id="config_update_${transCode.CODE_NO }_${var.index+1 }" 
													onclick="lookByupdate('config_update','config_look',${transCode.CODE_NO },${var.index+1 })"/>
										<br>
											<input type="checkbox" id="config_search_${transCode.CODE_NO }_${var.index+1 }" />
										
									</c:otherwise>
								</c:choose>
							
						</td>
					</c:forEach>
				</c:if>
				<td class="td_type td_center" nowrap="nowrap" width="35px" >
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button"onclick="saveCurrentRow('${transCode.CODE_NO }')">
								<!--save保存 --><spring:message code="zxc.hr.hrmAffirmConfig.save"/> 
							</button>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
</div>