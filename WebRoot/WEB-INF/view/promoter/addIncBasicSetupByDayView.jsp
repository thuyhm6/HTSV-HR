<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function checkData(){
	var PROD_TP=document.addIncBasicSetupByDay.PROD_TP.value;
	var PROD_ID=document.addIncBasicSetupByDay.PROD_ID.value;
	var att_strt_date = document.addIncBasicSetupByDay.PRC_BEGIN_DAY.value;
    var att_end_date  = document.addIncBasicSetupByDay.PRC_END_DAY.value;
    var att_strt_date_year  = att_strt_date.substring(0,4);
    var att_strt_date_month = att_strt_date.substring(4,6);
    var att_strt_date_day   = att_strt_date.substring(6,8);
    var att_end_date_year  = att_end_date.substring(0,4);
    var att_end_date_month = att_end_date.substring(4,6);
    var att_end_date_day   = att_end_date.substring(6,8);
     
    var strtDate = new Date(att_strt_date_year,att_strt_date_month,att_strt_date_day);
    var endDate = new Date(att_end_date_year,att_end_date_month,att_end_date_day);
	var mm = document.addIncBasicSetupByDay.month.value;

    if(PROD_TP == null||PROD_TP ==""){
	    alert("请选择产品类型！");
	    return false;
	    }
	if(PROD_ID == null||PROD_ID ==""){
	    alert("产品ID不能为空！");
	    return false;
	    }
    
    if(strtDate > endDate || att_strt_date_month != mm || att_end_date_month !=mm){
     	alert("日期选择错误！");
     	return false;
    }
	
	return true;
}
function dataSet(){
	var HEAD_UNIT_PRC = document.addIncBasicSetupByDay.HEAD_UNIT_PRC.value;
	var INC_RATE = document.addIncBasicSetupByDay.INC_RATE.value;	  	
	if(!isNaN(HEAD_UNIT_PRC)&&!isNaN(INC_RATE)){
		document.addIncBasicSetupByDay.SUBSD_INCTV_AMT.value = HEAD_UNIT_PRC*INC_RATE/100;
	}
}
</script>

<div class="pageContent">
	<form name="addIncBasicSetupByDay" method="post" action="/promoter/addIncBasicSetupByDay" class="pageForm required-validate" onsubmit="dataSet();return validateCallback(this,dialogAjaxDone);">
		<input name="SEQ" type="hidden" value="${SEQ}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>产品类型</dt>
				<dd><ait:ComboSyCodeDescByCpnyID id="PROD_TP" name="PROD_TP" 
					parentNo="211424" cnpyID="${defaultCpny}" />
				</dd>
			</dl>
			<dl>
				<dt>产品ID</dt>
				<dd><input name="PROD_ID" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt>实贩卖月份</dt>
				<dd style="width:100px"><ait:date yearName="year" yearSelected="${request_year}" monthName="month" monthSelected="${request_month}"/></dd>
			</dl>
			<dl>
				<dt>开始日期</dt>
				<dd>
				    <input type="text" name="PRC_BEGIN_DAY" class="date required" format="yyyyMMdd" readonly="true" value="${request_PRC_BEGIN_DAY }"/>
				    <a class="inputDateButton"><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			<dl>
				<dt>结束日期</dt>
				<dd>
				    <input type="text" name="PRC_END_DAY" class="date required" format="yyyyMMdd" readonly="true" value = "${request_PRC_END_DAY }"/>
				    <a class="inputDateButton"><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			<dl>
				<dt>总部单价</dt>
				<dd><input name="HEAD_UNIT_PRC" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataSet()"/></dd>
			</dl>
			<dl>
				<dt>提成率</dt>
				<dd><input name="INC_RATE" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataSet()"/>%</dd>
			</dl>
			<dl>
				<dt>总部提成</dt>
				<dd><input name="SUBSD_INCTV_AMT" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>更新人</dt>
				<dd><input name="UPDT_USER" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd>
					<select name="USE_YN">
						<option value="Y" <c:if test="${itemInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${itemInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onClick="return checkData();">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>