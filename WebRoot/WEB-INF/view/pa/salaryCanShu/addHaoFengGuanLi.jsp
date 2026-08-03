<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addHaoFengInfo(form, callback){
	var $form = $("#addHaoFengInfoForm",$.pdialog.getCurrent());
	if (!$form.valid()) {
		return false;
	}
	var startMonth = document.getElementById("START_MONTH").value;
	var startMonthFormat = startMonth.substr(2,4)+startMonth.substr(0,2);
	if (!/^(?:0[1-9]|1[0-2])(?:19[7-9]\d|2\d{3,3})$/.test(startMonth)){ 
         alertMsg.warn('<spring:message code="alert.message.pa.salary.startMonthIsNotCorrect"/>');
         return false;
    }
	var endMonth = document.getElementById("END_MONTH").value;
	var endMonthFormat = endMonth.substr(2,4)+endMonth.substr(0,2);
	if(endMonth!=null && endMonth !=""){
		if (!/^(?:0[1-9]|1[0-2])(?:19[7-9]\d|2\d{3,3})$/.test(endMonth)){
	        alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
	        return false;
	    }
		//如果结束月份比开始月早， 请重新填写结束月！
	    if(endMonthFormat < startMonthFormat){
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	        return false;
	    }
	}

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	$.pdialog.closeCurrent();
    alertMsg.correct('<spring:message code="liang.alert.message.org.add_success"/>!');//操作成功！
	return false;
}

function checkDayMon(){
	 
	   
	 var CPNY_ID=$("#CPNY_ID",$.pdialog.getCurrent()).val();

	 var JIQUN = $("#JIQUN",$.pdialog.getCurrent()).val();
	 var HAOFENG=$("#HAOFENG",$.pdialog.getCurrent()).val();
	 $.ajax({
			type : 'post',
			cache : false,
			contentType :'application/json',
			url : '/pa/salaryCanShu/updateHaoFengDayMonAjax?CPNY_ID='+CPNY_ID+'&JIQUN='+JIQUN+'&HAOFENG'+HAOFENG,
			dateType : 'json',
			success : function(responseStr){
			alert(responseStr);
				if(responseStr==1){
				 
					alertMsg.error("<spring:message code='pa.salary.canShu.riyueshenjiao'/>");
				}
			 }
		
		 });
}

$(function(){
	$("#JIQUN").change(function(){ 
		var a="";
		var POST_GRADE_NO=$("#JIQUN",$.pdialog.getCurrent()).val();
		$.ajax({
			type : 'post',
			dateType : 'json',
			url : '/pa/salaryCanShu/viewHaoFeng?POST_GRADE_NO='+POST_GRADE_NO,
			data:{a:a},
			success : function(data){
			var list = data.viewHaoFeng;
			if(list!=""){
				var s='';
				for(var i=0;i<list.length;i++){
					var PAY_STEP=list[i]['PAY_STEP'];
					var PAY_STEP_NAME=list[i]['PAY_STEP_NAME'];
					s=s+'<option value="'+PAY_STEP+'">'+PAY_STEP_NAME+'</option>';
				}
				$('#HAOFENG',$.pdialog.getCurrent()).html(s);
			}
			}
		 });
});
});
</script>



<div class="pageContent">
     <form method="post" id="addHaoFengInfoForm" action="/pa/salaryCanShu/addHaoFengGuanLiInfo" class="pageForm required-validate" 
     	onsubmit="return addHaoFengInfo(this, submitFormViewPaHaoFengList);">
		<div class="pageFormContent nowrap"> 
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
				<input type="hidden" id="CPNY_ID"  name="CPNY_ID" value="${CPNY_ID}" />			
					${CPNY_ID}	
				</dd>
			</dl>
			
			
			<!--<dl>
				<dt>职群 <spring:message code="ess.empInfo.zhiqun" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="ZHIQUN" id="ZHIQUN" parentNo="14015812" selected="" limit="ALL"/>	 		
				</dd>
			</dl>-->
			
			<dl>
				<dt><!--职级 --><spring:message code="ess.infoApply.Rank" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="JIQUN" id="JIQUN" parentNo="14015815"  limit="all"/> 				
				</dd>
			</dl>
			
			<dl>
				<dt><!--年资等级 --><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z" /></dt>
				<dd>
					<select name="HAOFENG" id="HAOFENG"></select> 				
				</dd>
			</dl>
			
			 <dl>
				<dt><!-- 开始年月 --> <spring:message code="hrm.empinfo.START_YEAR_MONTH" /></dt>
				<dd>
					<input  name="START_MONTH" id="START_MONTH" value=""  class="required"/><span style="color:red"><spring:message code="pa.insurance.title.exampleMonth"/></span>				
				</dd>
			</dl>
			
			 <dl>
				<dt><!-- 结束年月 --> <spring:message code="hrm.empinfo.END_YEAR_MONTH" /></dt>
				<dd>
					<input id="END_MONTH" name="END_MONTH" value="" /><span style="color:red"><spring:message code="pa.insurance.title.exampleMonth"/></span>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.jibengongzi"/><!--基本工资--></dt>
				<dd>
					<input  name="BASE_PAY" value="" />					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="ableStatus_pa0801">
						<option value="1" selected>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
				</dd>
			</dl>
			<input type="hidden" name="CREATED_IP" value="${adminID }"/>
			<input type="hidden" name="CREATED_BY" value="${username }"/>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
 </div>
