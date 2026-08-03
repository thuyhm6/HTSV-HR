<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">
<!--
function validateCallbackUpdateWorkExperienceInfo(form, callback) {


	var $form = $("#editTradeunionInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("TID");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	var workExpListnum= document.getElementById("getTradeunionList").value;
	var activity ="0";
	for (i=1;i<=workExpListnum;i++){
		
		if(document.getElementById("TID_"+i).checked){
			if(document.getElementById("QUITDATE_"+i).value != null && document.getElementById("QUITDATE_"+i).value !="" ){
			var sd=document.getElementById("ADDDATE_"+i).value;
			var ed=document.getElementById("QUITDATE_"+i).value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
					
				activity="1";
				document.getElementById("ADDDATE_"+i).focus();
				}
			}
		}
	}		
	
	if(activity>0){
		//开始时间不能晚于结束时间
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');
		return false;
	}
	
	
	
	
	
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}

function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="TID_"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}

//-->
</script>


<div class="pageContent">
	<form id="editTradeunionInfo" method="post" action="/hrm/empinfo/editTradeunionInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateWorkExperienceInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="getTradeunionList" name="getTradeunionList" value="${fn:length(tradeUnionList)}" />
	<table class="table" width="101.8%" layoutH="150">
		<thead>
			<tr>
				<th width="10">
					<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
				</th>
				
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
													<!--序号-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ZHIZE" />
													<!--职责-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" />
													<!--加入日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" />
													<!--退出日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG" />
													<!--参加 工会与否-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG" />
													<!--会费支付状态-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE" />
													<!--支付方式-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" />
													<!--描述-->
												</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${tradeUnionList}" var="item" varStatus="i">
				<tr target="TID" rel="${item.TID}">
					<td width="10">
						
						<input type="checkbox" id="TID_${i.count}" name="TID" value="${item.TID}"  />
					</td>
					<td width="80">
						${i.count}
					</td>
					<td width="80">		
								
						<ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})"    name="PHYSICAL_TYPE_CODE0_${item.TID}" parentNo="123251" cnpyID="${defaultCpny}" limit="all" selected="${item.RESID}"  />
					</td>
					<td width="80">
						<input  type="text"   id="ADDDATE_${i.count}" name="ADDDATE_${item.TID}" size="13" value="${fn:substring(item.ADDDATE,0, 10)}" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5"    onClick="setdate(this);setCheckboxChecked(${i.count})"/>
					</td>
					<td width="80">
							<input type="text" id="QUITDATE_${i.count}"
								name="QUITDATE_${item.TID}" size="13"
								value="${fn:substring(item.QUITDATE,0, 10)}" class="date"
								readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5"
								onClick="setdate(this);setCheckboxChecked(${i.count})"  />
						</td>
						<td class="td_type">
					${item.JOIN_FLAG}
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="PAY_FLAG_${item.TID}" parentNo="123224" cnpyID="${defaultCpny}" limit="all" selected="${item.PAY_FLAG_ID}"/>
				</td>
				<td class="td_type">
					<ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="PAY_TYPE_${item.TID}" parentNo="211654" cnpyID="${defaultCpny}" limit="all" selected="${item.PAY_TYPE_ID}"/>
				</td>
					<td width="80">
						<input type="text" id="REMARK_${i.count}" name="REMARK_${item.TID}" value="${item.REMARK}" onclick="setCheckboxChecked(${i.count})"/>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	
	
	
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
	</div>
	</form>
</div>