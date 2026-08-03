<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<!-- jjy maskedinput -->
<script src="/resources/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
<script type="text/javascript">
function checkNumPot2(value) {
	if (value.indexOf('.') > -1 && value.length - value.indexOf('.') - 1 > 2)
		return false;
	else
		return true;
}

//工作地区级联
$(document).ready(function() {
	var stateCd=$('#viewHire_STATENM').val();
	var cityCd0=$('#hCITYNM').val();
	var region0=$('#hREGION').val();
	changeState(stateCd,cityCd0);
	
	$('#viewHire_STATENM').live('change',function(){
		var st=$('#viewHire_STATENM').val();
		changeState(st,cityCd0);
		changeCity(null, null);
		//var workAreatext = $("#viewHire_STATENM option:selected").text();
		//$("#viewHire_WORK_AREA").val(workAreatext);
		$("#viewHire_WORK_AREA").val(st);
	});
	
	var cityCd=$('#viewHire_CITYNM select').val();
	changeCity(cityCd0, region0);
	
	$('#viewHire_CITYNM select').live('change',function(){
		var st=$('#viewHire_CITYNM select').val();
		changeCity(st, region0);
	});
});

function changeState(stateCd, cityCd){
	$.ajaxSettings.global = false;
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#viewHire_CITYNM").html(data);
			$("#viewHire_CITYNM #seach_CITYNM").bind("change",function(){
					var workArea = $("#viewHire_CITYNM #seach_CITYNM").val();
					//var workAreatext = $("#viewHire_CITYNM #seach_CITYNM option:selected").text();
					//$("#viewHire_WORK_AREA").val($("#viewHire_WORK_AREA").val()+workAreatext);
					$("#viewHire_WORK_AREA").val(workArea);
				});
		}
	});
	$.ajaxSettings.global = true;
}

function changeCity(cityCd, region){
	$.ajaxSettings.global = false;
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=REGION&parentNo='+cityCd+"&selected="+region+'&name=seach_REGION',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#viewHire_REGION").html(data);
			$("#viewHire_REGION #seach_REGION").bind("change",function(){
				var workArea = $("#viewHire_REGION #seach_REGION").val();
				//var workAreatext = $("#viewHire_REGION #seach_REGION option:selected").text();
				//$("#viewHire_WORK_AREA").val($("#viewHire_WORK_AREA").val()+workAreatext);
				$("#viewHire_WORK_AREA").val(workArea);
			});
		}
	});
	$.ajaxSettings.global = true;
}
/*
//通过职等 关联职级
function getZhiDengAndZhiJi(GRADE_LEVEL){
	
	if(GRADE_LEVEL == null || GRADE_LEVEL.length == 0){
		var sel_duty_no = $("#viewHire_DUTY_NO");//职级
		var sel_post_grade_no = $("#viewHire_POST_GRADE_NO");//职级
		var sel_post_no = $("#viewHire_POST_NO");//职级
		sel_duty_no.empty();
		sel_post_grade_no.empty();
		sel_post_no.empty();
		sel_duty_no.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
		sel_post_grade_no.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
		sel_post_no.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
		return ;
	}
	var sel = $("#viewHire_POST_GRADE_NO");//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiDengAndZhiJi?",
		 data: 'GRADE_LEVEL=' + GRADE_LEVEL,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	getZhiJiAndZeToMing($("#viewHire_POST_GRADE_NO").val());
}

//查询职责和职级名称
function getZhiJiAndZeToMing(POST_GRADE_NO){
	if(POST_GRADE_NO == null || POST_GRADE_NO.length == 0){
		return ;
	}
	var sel = $("#viewHire_DUTY_NO");//职责
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiZe?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	
	var sel = $("#viewHire_POST_NO");//职级名称
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/transferOrder/getZhiJiAndZhiJiMing?",
		 data: 'POST_GRADE_NO=' + POST_GRADE_NO,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
*/
/*
//雇佣类型下控制契约类型
function getGuYongAndQiYue(CODE_ONE){
	
	
	if(CODE_ONE == null || CODE_ONE.length == 0){
		return ;
	}
	var sel = $("#viewHire_CONTRACT_TYPE");//契约类型
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/hrm/empinfo/getRelevance?",
		 data: 'CODE_ONE=' + CODE_ONE,
		 dataType:"json",
		 success: function(data) {
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	getQiYueAndGongShi($("#viewHire_CONTRACT_TYPE").val());
}

//契约类型 控制工时制
function getQiYueAndGongShi(CODE_TWO){
	
	
	if(CODE_TWO == null || CODE_TWO.length == 0){
		
		return ;
	}
	 var CODE_ONE = $("#viewHire_EMPLOYMENT_TYPE").val();
	 if(CODE_TWO == null || CODE_TWO.length == 0){
	 		alert("请先选择雇佣类型");
	 		$("#viewHire_EMPLOYMENT_TYPE").focus();
			return ;
		}
	var sel = $("#viewHire_WORKING_TIME");//工时制
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		  async:false,
		 url: "/hrm/empinfo/getRelevance?",
		 data: 'CODE_ONE=' + CODE_ONE+'&CODE_TWO='+CODE_TWO,
		 dataType:"json",
		 success: function(data) {
			$.each(data, function(key,value){
					if($(data).size() > 0){
							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
	
		getGongShiAndRenLi($("#viewHire_WORKING_TIME").val());
	

}

//工时制控制详细人力区分
function getGongShiAndRenLi(CODE_THREE){
	
	
	if(CODE_THREE == null || CODE_THREE.length == 0){
		return ;
	}
	 var CODE_ONE = $("#viewHire_EMPLOYMENT_TYPE").val();
	 if(CODE_ONE == null || CODE_ONE.length == 0){
	 		alert("请先选择雇佣类型");
	 		$("#viewHire_EMPLOYMENT_TYPE").focus();
			return ;
		}
	 var CODE_TWO = $("#viewHire_CONTRACT_TYPE").val();
	 if(CODE_TWO == null || CODE_TWO.length == 0){
	 		alert("请先选择契约类型");
	 		$("#viewHire_CONTRACT_TYPE").focus();
			return ;
		}
	var sel = $("#viewHire_EMP_TYPE_CODE");//详细人力区分
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 url: "/hrm/empinfo/getRelevance?",
		 data: 'CODE_ONE=' + CODE_ONE+'&CODE_TWO='+CODE_TWO+'&CODE_THREE='+CODE_THREE,
		 dataType:"json",
		 success: function(data) {
			$.each(data, function(key,value){
					if($(data).size() > 0){
						sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
*/

//根据传入的不同名称查询子idgetEducationNameHire
	function getEducationNameHire(id,sourceId,name,toname){        
	id=id.replace(name,"");
	if(sourceId == null || sourceId.length == 0){
		return ;
	}
	var sel = $("#"+toname+id);
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 url: "/hrm/transferOrder/getRecSourceDetailByRecSource?",
		 data: 'PARENT_CODE_NO=' + sourceId,
		 dataType:"json",
		 success: function(data) {
			$.each(data, function(key,value){
					if($(data).size() > 0){
						
							sel.append('<option value='+value+'>'+key+'</option>'); 
						
					}
			});
		 }
	});
}

function validateCallbackViewHire(form,callback) {
	var $form = $("#viewHire");
	
	if (!$form.valid()) {
		return false;
	}
	if(true == CheckForm()){		
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data) {			
				if(200==data.statusCode){
					//alert("入职成功");
					alertMsg.correct('<spring:message code="hr.alert.message.viewHire.hireSuccess"/>');
					if(data.popMark == 1){
						document.getElementById("pophref").href=encodeURI(encodeURI("/hrm/transferOrder/viewPopMarkToHire?IDCARD_NO="+data.IDCARD_NO+"&&FOREIGNER_IDCARD_NO="+data.FOREIGNER_IDCARD_NO+"&&PASSPORT_NO="+data.PASSPORT_NO+"&&JOIN_COMPANY_DATE="+data.JOIN_COMPANY_DATE));
						document.getElementById("pophref").click();
						return false;
					}else{
						navTab.reloadFlag("hr0504");
					}
				}else{
					alertMsg.error(data.message);
					return false;
				}				
			  }
		});
		return false;
	}else{
		return false;
	}
}

//页面验证
function CheckForm() {
	if($("#viewHire_DEPTNO").val()==''){
		//alert("部门不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullDeptNo"/>');
		$("#viewHire_DEPTNO").focus();
		return false;
	}else if($("#PROD_TP").val()=='' && $("#viewHire_CPNY_ID").val()=='TSTO'){
		alertMsg.error('主责产品不可以为空！');
		$("#PROD_TP").focus();
		return false;
	}else if($("#viewHire_JOIN_COMPANY_DATE").val()==''){
		//alert("入司日期不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.joinCompanyDateNotNull"/>');
		$("#viewHire_JOIN_COMPANY_DATE").focus();
		return false;
	}else if($("#viewHire_LOCAL_NAME").val()==''){
		alertMsg.error('中文姓名不可以为空！');
		$("#viewHire_LOCAL_NAME").focus();
		return false;
	}else if($("#viewHire_CHINESE_PINYIN").val()==''){
		alertMsg.error('英文姓名不可以为空！');
		$("#viewHire_CHINESE_PINYIN").focus();
		return false;
	}else if($("#viewHire_IDCARD_NO").val()==''){
		//alert("身份证号不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullIdcardNo"/>');
		$("#viewHire_IDCARD_NO").focus();
		return false;
	}else if(!isIdCardNo($("#viewHire_IDCARD_NO").val())){
		return false;
	}else if($("#viewHire_BIRTHDAY").val() == ''){
		//alert("出生日期不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.dobNotNull"/>');
		$("#viewHire_BIRTHDAY").focus();
		return false;
	}else if($("#viewHire_BASE_PAY").val() == ''){
		alertMsg.error('基本工资不能为空');
		$("#viewHire_BASE_PAY").focus();
		return false;
	}else if($("#viewHire_INSRAREA_ID").val()==''){
		alertMsg.error('福利地区不能为空.');
		$("#viewHire_INSRAREA_ID").focus();
		return false;
	}else if($("#viewHire_EMP_TYPE_CODE").val()==''){
		alertMsg.error('人员类型不可以为空！');
		$("#viewHire_EMP_TYPE_CODE").focus();
		return false;
	}else if($("#viewHire_SHIFT_NO").val()==''){
		alertMsg.error('班号不可以为空！');
		$("#viewHire_SHIFT_NO").focus();
		return false;
	}else if ($("#viewHire_PROB_PAY_RAT").val() =='' ||$("#viewHire_PROB_PAY_RAT").val() < 0 || $("#viewHire_PROB_PAY_RAT").val() > 100) {
        alert("试用期比例应为0 ~ 100之间的值.");
        $("#viewHire_PROB_PAY_RAT").focus();
        return false;
    }/*else  if($("#WHETHER_FOREIGNERS").val()=='123214' && $("#NATIONALITY_CODE").val()==''){
		//是否外国人为N时 国籍为必选
		//alert("国籍不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullNationality"/>');
		$("#NATIONALITY_CODE").focus();
		return false;
	}else  if($("#NATIONALITY_CODE").val()=='871' && $("#NATION_CODE").val()==''){
		//alert("国籍是中国时民族不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.nationCodeNotNull"/>');
		$("#NATION_CODE").focus();
		return false;
	}else if($("#EMAIL").val() == ''){
		//alert("E-MAIL不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.emailNotNull"/>');
		$("#EMAIL").focus();
		return false;
	}else if($("#JOB_TYPE").val() == ''){
		//alert("职种不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.jobTypeNotNull"/>');
		$("#JOB_TYPE").focus();
		return false;
	}else if($("#POSITION_NO").val()==''){
		//alert("职(岗)位不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullPositionNo"/>');
		$("#POSITION_NO").focus();
		return false;
	}else if($("#JOIN_TYPE_CODE").val()==''){
		//alert("入职类型不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullJoinTypeCode"/>');
		$("#JOIN_TYPE_CODE").focus();
		return false;
	}else if($("#IN_THE_DIFFERENCE").val()==''){
		//alert("是否试用不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.inTheDifferenceNotNull"/>');
		$("#IN_THE_DIFFERENCE").focus();
		return false;
	}else if($("#WORK_AREA").val()==''){
		//alert("工作地不能为空");
		alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullWorkArea"/>');
		$("#WORK_AREA").focus();
		return false;
	}else if($("#SOCIAL_SECURITY_AREA").val()==''){
		//alert("社会保险地不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.socialSecurityAreaNotNull"/>');
		$("#SOCIAL_SECURITY_AREA").focus();
		return false;
	}else if($("#INSURANCE_TYPE_CODE").val()==''){
		//alert("保险类型不能为空");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.insuranceTypeCodeNotNull"/>');
		$("#INSURANCE_TYPE_CODE").focus();
		return false;
	}else {
		//韩国人或者其它国籍的人，护照号和身份证号其中之一不为空即可！
		if($("#NATIONALITY_CODE").val() == '871' && isIdCardNoForChina($("#IDCARD_NO_VAL"))==true){
			return true;
		}
	}*/
	return true;
}

//通过身份证号来判断类型onblur="getIdCard(this.value);"
function getIdCard(idCard){

	var $form = $("#viewHire");
	
	if(idCard == null || idCard.length == 0){
		return ;
	}
	
	$.ajax({
		 cache: false,
		 type: 'post',
		 url: "/hrm/transferOrder/getIdCard?",
		 data: 'IDCARD_NO=' + idCard,
		 dataType:"json",
		 success: function(data) {
			if($(data).size() > 0){
				$.each(data, function(key,value){
					$form.find("#" + key).children("option").each(function(index, obj){
					      if($(obj).val() == value){
					    	  obj.selected = true ;
					      }      
					}); 
				});
			}
		 }
		 
		});
}


//提交的时候验证身份证信息
function isIdCardNoForChina(num){
	//if($("#NATIONALITY_CODE").val()== '871'|| $("#NATIONALITY_CODE").val() == '873' || $("#NATIONALITY_CODE").val()== '874'){
		var idnum = $(num).val();
		return isIdCardNo(idnum);
	//}
}
/*
//选择国籍是触发
function isIdCardNoForChina2(str){
	var $form = $("#viewHire");
	if(str== '871' || str== '873' || str== '874'){
    	$form.find("#viewHire_FSE_N").attr("checked","checked");
	}
    if(str== '872' || str== '875'){        
    	$form.find("#viewHire_FSE_Y").attr("checked","checked");
    }
	if(str=='') {
		$("#viewHire_FSE_N").attr("checked", "checked");
	} 	
	if(str== '871'){
		var idnum = $("#viewHire_IDCARD_NO").val();
		return isIdCardNo(idnum);
	}
}
*/
//验证身份证的信息
function isIdCardNo(num){
	var len = num.length, re;
	if (len == 15){
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	}else if (len == 18){
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d|X|x)$/);
	}else if (len == 0){
		//alert("输入的身份证号不能为空！"); 
		alertMsg.error('请输入身份证号！');
		$("#vireHire_IDCARD_NO").focus();
		return false;
	}else {
		//alert("输入的数字位数不对！"); 
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkDigitalDigits"/>');
		$("#vireHire_IDCARD_NO").focus();
		return false;
	}
	var a = num.match(re);
	var B = null;
	var D = null;
	
	if (a != null){
		if (len==15){
			D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
			B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}
		else{
			D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
			B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}
		
		if (!B){
			//alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitA"/>'+ a[0] +'<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitB"/>');
			$("#vireHire_IDCARD_NO").focus();
			return false;
		}else{
			$("#viewHire_BIRTHDAY").val(D.getFullYear()+"-"+((D.getMonth()+1) < 10 ? "0"+(D.getMonth()+1) : (D.getMonth()+1))+"-"+((D.getDate()) < 10 ? "0"+(D.getDate()) : (D.getDate())));
			return true;
		}
	}else{
		//您输入的身份证号不正确
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNo"/>');
		$("#vireHire_IDCARD_NO").focus();
		return false;
	}
}

$(function() {
    $("#viewHire_CELLPHONE").mask("999-9999-9999");
});
</script>
<div class="pageContent">
	<form id="viewHire" method="post"
		action="/hrm/transferOrder/saveTransferOrderHire"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="80">
		<div class="panel collapse" >
			<h1>
				<spring:message code="hr.viewHire.title.COMPANYINFORMATIONIN" />
				<!--司内信息  --><!-- 직원기초정보 -->
			</h1>
			<div>
				<table id="info" border='0' width="100%" cellspacing="0"
						cellpadding="0" class="user_table">
						<tr>
							<td class="td_title">
								法人代码 
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_CPNY_ID" name="CPNY_ID" value="${personInfo.cpnyId}" readonly/>
							</td>
							<td class="td_title">
								<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
								<!--部门--><!-- 부서 -->
							</td>
							<td class="td_type">
								<c:if test="${searchMap.authority eq '1'}">
									<ait:deptList name="DEPTNO" cpnyId="${personInfo.cpnyId}" 
										limit="super" id="viewHire_DEPTNO"/>
									<ait:deptTreeIcon name="DEPTNO" cpnyId="${personInfo.cpnyId}" 
										limit="super" id="viewHire_DEPTNO" selected="${personInfo.DEPTNO}"/>
								</c:if>
								<c:if test="${searchMap.authority ne '1'}">
									<ait:deptList name="DEPTNO" cpnyId="${personInfo.cpnyId}" 
										limit="hr" id="viewHire_DEPTNO"/>
									<ait:deptTreeIcon name="DEPTNO" cpnyId="${personInfo.cpnyId}" 
										limit="hr" id="viewHire_DEPTNO" selected="${personInfo.DEPTNO}"/>
								</c:if>
								<font color="red">*</font>
							</td>
							<td class="td_title">
								法人入职日期
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_JOIN_COMPANY_DATE" 
									name="JOIN_COMPANY_DATE" class="required date" readonly
									size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
								<a class="inputDateButton" href="javascript:;">
									<spring:message
										code="public.title.choose" />
								</a>
							</td>
                            <td class="td_title">
                            	入职类型
                            </td>
                            <td class="td_type"> 
                            	<!-- ait:SelectSyCodeByCpnyID id="viewHire_JOIN_TYPE_CODE" name="JOIN_TYPE_CODE" 
                            		parentNo="1359" selected="${JOIN_TYPE_CODE}" 
                            		cnpyID="${personInfo.cpnyId}" limit="all"/> -->
                            	<select  name="JOIN_TYPE_CODE" id="viewHire_JOIN_TYPE_CODE">
                                    <option value="12280" selected>新入职</option>
                                    <option value="12281">再入职</option>
                                </select>                            		
                            </td>
							<!-- td class="td_title">
                                                                                职群
                            </td>
                            <td class="td_type">     
                            	<ait:SelectSyCodeByCpnyID id="viewHire_JOB_FAM" name="JOB_FAM" 
                            		parentNo="13955" selected="${personInfo.JOB_FAM}" 
                            		cnpyID="${personInfo.cpnyId}" limit="all"/>                          
                            </td>	
                            <td class="td_title">
                               	 职位
                            </td>
                            <td class="td_type">                 	
                            	<ait:SelectSyCodeByCpnyID id="viewHire_DUTY_NO" name="DUTY_NO" 
                            		parentNo="211586" selected="${personInfo.DUTY_NO}" 
                            		cnpyID="${personInfo.cpnyId}" limit="all"/>
                            </td> -->
						</tr>
						<tr>
							<td class="td_title">
								社编 
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_EMPID" name="EMPID" 
									value="${personInfo.EMPID}" readonly/>
							</td>
							<td class="td_title">
								中文姓名
							</td>
							<td class="td_type">
								<input id="viewHire_LOCAL_NAME" name="LOCAL_NAME" class="required" 
									type="text" size="20" />
							</td>
                             <!-- td class="td_title">
                                <spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME" />
                            </td>
                            <td class="td_type" colspan=3>
                                <select  name="POST_GRADE_NO" id="viewHire_POST_GRADE_NO" 
                                	onchange="getZhiJiAndZeToMing(this.value);">
                                    <option value="">
                                        <spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
                                    </option>
                                </select>
                            </td>
						</tr>
						<tr> -->
							<!-- td class="td_title">
								LGE集团入职日期
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_JOIN_BLOC_DATE" 
									name="JOIN_BLOC_DATE" class="required date" readonly
									size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
								<a class="inputDateButton" href="javascript:;">
									<spring:message
										code="public.title.choose" />
								</a>
							</td> -->
                            <td class="td_title">
								英文姓名
							</td>
							<td class="td_type">
								<input id="viewHire_CHINESE_PINYIN" name="CHINESE_PINYIN"  
									class="required" type="text" size="20" />
							</td>
                            
                            <td class="td_title">
								试用期开始日期
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_PROB_STRT_DATE" 
									name="PROB_STRT_DATE" readonly class="date"
									size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
								<a class="inputDateButton" href="javascript:;">
									<spring:message code="public.title.choose" />
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_title">
								试用期结束日期
							</td>
							<td class="td_type">
								<input type="text" id="viewHire_END_PROBATION_DATE" 
									name="END_PROBATION_DATE" readonly class="date"
									size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
								<a class="inputDateButton" href="javascript:;">
									<spring:message code="public.title.choose" />
								</a>
							</td>
                            <!-- td class="td_title">
                                                                               任期类型
                            </td>
                            <td class="td_type">   
                            	<c:if test="${personInfo.cpnyId eq 'AIT01'}">
                            		<ait:SelectSyCodeByCpnyID name="viewHire_CONTRACT_TYPE" 
                            			parentNo="125075"cnpyID="${personInfo.cpnyId}" limit="all" />
									<font color="red" size="20">*</font>
                            	</c:if>
                            	
                            	<c:if test="${personInfo.cpnyId ne 'AIT01'}">
                            		 <select name="CONTRACT_TYPE" id="viewHire_CONTRACT_TYPE" 
                            		 	onChange="getQiYueAndGongShi(this.value);" >
                                    <option value="">
                                        <spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
                                    </option>
                                </select>
                            	</c:if>
                            </td> -->
                            <td class="td_title">
                                	试用期比例 
                            </td>
                            <td class="td_type">   
                            	<input type="text" id="viewHire_PROB_PAY_RAT" 
									name="PROB_PAY_RAT"	size="5" maxlength="5"
									onkeyup="if(isNaN(value))execCommand('undo')"
									onafterpaste="if(isNaN(value))execCommand('undo')" />  %              	
                            </td>
                            <td class="td_title">
                                	职务
                            </td>
                            <td class="td_type">                               	
								<ait:ComboSyCodeDescByCpnyID name="JOB_TITLE_CD"
									id="JOB_TITLE_CD" parentNo="14013573" limit="all"
									cnpyID="${personInfo.cpnyId}"/> 								
                            </td>
                            <!-- td class="td_title">
                                <spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
                            </td>
                            <td class="td_type">
                                <ait:ComboJobPositionByCpnyIDTag id="viewHire_POSITION_NO"
									name="POSITION_NO" parentNo="215918"
									selected="215919" cnpyID="${personInfo.cpnyId}" limit="all" />
                             </td> -->
						</tr>
				</table>
			</div>
		</div>
		<div class="clear"></div>
		<div class="panel collapse" >
			<h1>
				<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION_DETAIL" />
				<!--个人基础信息--><!-- 개인기초정보 -->
			</h1>
			<div>
				<table id="info" cellspacing="0" cellpadding="2" border="0"
					width="100%" class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME" />
							<!--国籍-->
						</td>
						<td class="td_type">					
							<ait:ComboSyCodeDescByCpnyID name="viewHire_NATIONALITY_CODE"
							id="viewHire_NATIONALITY_CODE" parentNo="870"
							cnpyID="${personInfo.cpnyId}" disabled="true"/>
							<input type="hidden" name="NATIONALITY_CODE" value="CHI" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.SEX" />
							<!--性别--><!-- 성별 -->
						</td>
						<td class="td_type">
							<input type="radio" name="SEXCODE" value="M" />男
							<input type="radio" name="SEXCODE" value="F" checked/>女
							<font color="red" size="20">*</font>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" />
							<!--身份证号--><!-- 신분증번호 -->
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_IDCARD_NO" name="IDCARD_NO" class="required" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.NATION_NAME" />
							<!--民族 -->
						</td>
						<td class="td_type">							
							<ait:SelectSyCodeByCpnyID name="NATION_CODE" parentNo="210942" 
								cnpyID="${personInfo.cpnyId}" limit="all" />
						</td>				
					</tr>
					<tr>
						<td class="td_title">
							最终学历
						</td>
						<td class="td_type">
							<ait:selectSyCode name="FINAL_DEGREE_CODE" selected="211780" parentNo="211777" />
							<!-- ait:SelectSyCodeByCpnyID id="viewHire_FINAL_DEGREE_CODE" 
								name="FINAL_DEGREE_CODE"parentNo="13769" 
								cnpyID="${personInfo.cpnyId}" limit="all"/> -->
						</td>
						<td class="td_title">
							生日
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_BIRTHDAY" 
								name="BIRTHDAY" class="required date" readonly
								size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
							<a class="inputDateButton" href="javascript:;">
								<spring:message code="public.title.choose" />
							</a>
						</td>
						<td class="td_title">
							ID卡号
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_ID_CARD_NO_VAL" name="ID_CARD_NO" />
						</td>
						<td class="td_title">
							<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
							<!--手机号码-->
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_CELLPHONE" name="CELLPHONE" size="13"/>
						</td>			
					</tr>
					<tr>
						<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.email.chinese" />
						<!--邮箱-->
						</td>
						<td class="td_type">
						<input type="text" id="viewHire_EMAIL" name="EMAIL"  size="20"/>
						</td>
					<!-- /tr>
					<tr>
						<td class="td_title">
							<spring:message
								code="hr.viewPersonalInfo.title.BORNPLACE_NAME" />
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="BORNPLACE_CODE" parentNo="774" 
								cnpyID="${personInfo.cpnyId}" limit="all" 
								selected="${personInfo.BORNPLACE_CODE}"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
						</td>
						<td class="td_type" colspan="5">
							<ait:SelectSyCodeByCpnyID name="REG_PLACE_PROVINCE_CODE" 
								id="viewHire_REG_PLACE_PROVINCE_CODE" 
                                parentNo="4602" cnpyID="${personInfo.cpnyId}" limit="all" />							
							<input type="text" id="viewHire_REG_PLACE" name="REG_PLACE" size="60"/>
						</td>
						
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewHire.title.HOME_PHONE" />
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_HOME_PHONE" name="HOME_PHONE"  size="10"/>
						</td>
						<td class="td_title">
							家庭住址
						</td>
						<td class="td_type" colspan="5">
							<ait:SelectSyCodeByCpnyID name="IDCARD_ADDR_PROVINCE_CODE" 
								id="viewHire_IDCARD_ADDR_PROVINCE_CODE" 
                                parentNo="4602" cnpyID="${personInfo.cpnyId}" limit="all" />							
							<input type="text" id="viewHire_IDCARD_ADDR" name="IDCARD_ADDR" size="60"/>
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message
								code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
						</td>
						<td class="td_type" colspan="3">							
							<ait:SelectSyCodeByCpnyID name="HOME_ADDRESS_PROVINCE_CODE" 
								id="HOME_ADDRESS_PROVINCE_CODE" 
                                parentNo="4602" cnpyID="${personInfo.cpnyId}" limit="all" />							
							<input type="text" id="HOME_ADDRESS" name="HOME_ADDRESS"  size="30" 
								class="required textInput"/>
						</td>
						<td class="td_title">
							邮编
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_POSTALCODE" name="POSTALCODE"  size="10"/>
						</td>
					</tr>
					<tr>
						<td class="td_title">
							办公电话
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_OFFICE_PHONE" name="OFFICE_PHONE"  size="10"/>
						</td>
						<td class="td_title">
							<spring:message code="liang.hr.viewPersonalInfo.title.EMAIL" />
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_EMAIL" name="EMAIL" class="required"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" />
						</td>
						<td class="td_type" width="15%">
							<ait:SelectSyCodeByCpnyID name="POLITY_CODE" parentNo="210938" 
							cnpyID="${personInfo.cpnyId}" 
							selected="${personInfo.POLITY_CODE }" limit="all" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST" />
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="WHETHER_COMMUNIST" id="viewHire_WHETHER_COMMUNIST" 
                                 parentNo="123224" cnpyID="${personInfo.cpnyId}" 
                                 selected="${personInfo.WHETHER_COMMUNIST }" limit="all" />
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message
								code="hr.viewPersonalInfo.title.shengao" />
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_HEIGHT" name="HEIGHT"  size="10" 
								value="${personInfo.HEIGHT }"/>cm
						</td>
						<td class="td_title">
							<spring:message
								code="hr.viewPersonalInfo.title.tizhong" />
						</td>
						<td class="td_type" width="15%">
							<input type="text" id="viewHire_WEIGHT" name="WEIGHT"  size="10" 
								value="${personInfo.WEIGHT }"/>kg
						</td>
						<td class="td_title">
							<spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME" />
						</td>
						<td class="td_type" width="15%">
							<ait:SelectSyCodeByCpnyID name="BLOOD_TYPE" id="viewHire_BLOOD_TYPE" 
                                 parentNo="4573" cnpyID="${personInfo.cpnyId}" 
                                 selected="${personInfo.BLOOD_TYPE }" limit="all" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewCondSql.titleSHIFOUCANJI" />
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="DISABILITY_YN" id="viewHire_DISABILITY_YN" 
                                 parentNo="123224" cnpyID="${personInfo.cpnyId}" 
                                 selected="${personInfo.DISABILITY_YN }" limit="all" />
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.zhaopinlaiyuan" />
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="RECRUITMENT_SOURCE_TYPE" 
								id="viewHire_RECRUITMENT_SOURCE_TYPE" 
                                parentNo="3306" cnpyID="${personInfo.cpnyId}" 
                                selected="${personInfo.RECRUITMENT_SOURCE_TYPE }" limit="all" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPromote.title.RESIGN_REASON" />
						</td>
						<td class="td_type" width="15%">
							<input type="text" id="viewHire_LEAVE_REASON" name="LEAVE_REASON"  size="20" 
								value="${personInfo.LEAVE_REASON }"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.jiangchengbeizhu" />
						</td>
						<td class="td_type" width="15%">
							<input type="text" id="viewHire_REMARK" name="REMARK"  
								size="20" value="${personInfo.REMARK }"/>
						</td>
						<td class="td_title">
							结婚纪念日
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_WEDDING_DATE" 
								name="WEDDING_DATE" class="required date" readonly
								size="10" format="yyyy-MM-dd" yearstart="-50" yearend="10"/>
							<a class="inputDateButton" href="javascript:;">
								<spring:message code="public.title.choose" />
							</a>
						</td> -->
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
							<!--户口性质-->
						</td>
						<td class="td_type" width="15%">
							<ait:SelectNonSyCodeByCpnyID name="REG_TYPE_CODE" id="REG_TYPE_CODE"
    								codeType="REG_TYPE_CODE" selected="${personInfo.REG_TYPE_CODE }"
    								cnpyID="${personInfo.cpnyId}" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.REG_PLACE" />
							<!--户口所在地-->
						</td>
						<td class="td_type" colspan="3">
						    <input type="text" id="REG_PLACE" name="REG_PLACE"  size="60" value="${personInfo.REG_PLACE }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>		
	
	<div style="clear: both;"></div>
		<div class="panel collapse">
			<h1>
				<spring:message code="hr.viewPersonalInfo.title.payinfo" />
				<!--工资信息 -->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="hr.empinfo.pay.type.num" />
							<!--工资级号-->
						</td>
						<td class="td_type">
							<ait:SelectNonSyCodeByCpnyID codeType="PAY_GRADE"
								id="viewHire_PAY_GRADE" name="PAY_GRADE" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.PAY_GRADE }" />
						</td>
						<td class="td_title">
							<spring:message code="hr.empinfo.pay.type.num.leave"/>
							<!--工资级号等级-->
						</td>
						<td class="td_type">
							 <ait:SelectNonSyCodeByCpnyID name="PAY_STEP" id="viewHire_PAY_STEP" 
	                            codeType="PAY_STEP" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.PAY_STEP }"/>
						</td>
						<td class="td_title">
							<spring:message code="ess.viewpersonalpainfo.jibengongzi"/>
							<!--基本工资-->
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_BASE_PAY" name="BASE_PAY" class="required number"
								size="10" value="${personInfo.BASE_PAY }"/>
						</td>
						<td class="td_title">
						<spring:message code="hr.empinfo.pay.VARIABLE_SALARY_MONTH"/>
						<!--变动工资-->
						</td>
						<td class="td_type">
						<input type="text" id="VARB_PAY" name="VARB_PAY" class="number" size="10" value="${personInfo.VARB_PAY }"/>
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="pa.wagebase.title.openAccountBanks"/>
							<!--开户行-->
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_CARD_NAME" name="CARD_NAME"  
								size="20" value="${personInfo.CARD_NAME }"/>
						</td>
						<td class="td_title">
							<spring:message code="rp.report.title.bankcardno"/>
							<!-- 银行账号 -->
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_CARD_NO" name="CARD_NO"  
								size="20" value="${personInfo.CARD_NO }"/>
						</td>
						<!-- td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.nyinhangdaima" />
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_BANK_ID" name="BANK_ID"  
								size="20" value="${personInfo.BANK_ID }"/>
						</td> -->
						<!-- td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.nianxin" />
						</td>
						<td class="td_type" width="15%">
							<input type="text" id="viewHire_ANSAL" name="ANSAL"  
								size="10" value="${personInfo.ANSAL }"/>
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.feiyongnyinhangdaima"/>
						</td>
						<td class="td_type">
							 <input type="text" id="viewHire_EXPNS_BANK_CD" name="EXPNS_BANK_CD"  
								size="20" value="${personInfo.EXPNS_BANK_CD }"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.feiyongkaihuhang" />
						</td>
						<td class="td_type">
							<input type="text" id="viewHire_EXPNS_BANK_BRNCH_NM" name="EXPNS_BANK_BRNCH_NM"  
								size="20" value="${personInfo.EXPNS_BANK_BRNCH_NM }"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.feiyongyinhangzhanghao" />
						</td>
						<td class="td_type" width="8%">
							<input type="text" id="viewHire_EXPNS_BANK_ACCT_NO" name="EXPNS_BANK_ACCT_NO"  
								size="20" value="${personInfo.EXPNS_BANK_ACCT_NO }"/>
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.fudanfangzubiaozhi" />
						</td>
						<td class="td_type" width="15%">
							<select name="IF_PAYMENT_RENT">
								<option value="E" <c:if test="${personInfo.IF_PAYMENT_RENT eq 'E' }">selected</c:if>>个人</option>
								<option value="C" <c:if test="${personInfo.IF_PAYMENT_RENT eq 'C' }">selected</c:if>>公司</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.aixinjijinzhifufangshi" />
						</td>
						<td class="td_type"> 
							<ait:SelectSyCodeByCpnyID name="LOVE_FUND_PAYMENT_TYPE" 
								id="viewHire_LOVE_FUND_PAYMENT_TYPE" 
	                            parentNo="211654" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.LOVE_FUND_PAYMENT_TYPE }" limit="all" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.shifouzhifuaixinjijin" />
						</td>
						<td class="td_type" >
							<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_LOVE_FUND" 
								id="viewHire_IF_PAYMENT_LOVE_FUND" 
	                            parentNo="123224" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.IF_PAYMENT_LOVE_FUND }" limit="all" />
						</td>
						<td class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.fudanjiaoyufeibiaozhi" />
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_EDUCATION" 
								id="viewHire_IF_PAYMENT_EDUCATION" 
	                            parentNo="123224" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.IF_PAYMENT_MEDICAL }" limit="all" />
						</td>
						<td class=td_title><spring:message
								code="hr.viewPersonalInfo.title.fudanyiliaofeibiaozhi" />
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="IF_PAYMENT_MEDICAL" 
								id="viewHire_IF_PAYMENT_MEDICAL" 
	                            parentNo="123224" cnpyID="${personInfo.cpnyId}" 
	                            selected="${personInfo.IF_PAYMENT_MEDICAL }" limit="all" />
						 </td> -->
					</tr>
				</table>
			</div>
		</div>
	
		<div style="clear: both;"></div>
		<div class="panel collapse">
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.workinfo" />
			<!--工作信息 -->
		</h1>

		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<tr>
					<td class=td_title>福利地区</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID id="viewHire_INSRAREA_ID" name="INSRAREA_ID" 
							parentNo="216736" selected="${personInfo.INSRAREA_ID}" 
							cnpyID="${personInfo.cpnyId}" limit="all"/>
						<font color="red" size="20">*</font>							
					</td>
					<!-- td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.shifouFSE" />
					</td>
					<td class="td_type" width="15%">
						<input type="radio" id="viewHire_FSE_Y" name="FSE_YN" value="Y" />Y
						<input type="radio" id="viewHire_FSE_N" name="FSE_YN" value="N" checked/>N
					</td> -->
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.renyuanleixing.chr" />
						<!--人员类型(CHR)-->
					</td>
					<td class="td_type" width="20%">
						<!--  select name="EMP_TYPE_CODE"><option value="">请选择</option>
						  <c:forEach items="${empTypeList}" var="position">
						    <option value="${position.TEMP_EMPTYPE}"<c:if test="${position.TEMP_EMPTYPE eq '211804'}">selected</c:if>>${position.EMPTYPE_NAME}
						  </c:forEach>
						</select>-->
						<ait:SelectNonSyCodeByCpnyID id="viewHire_EMP_TYPE_CODE" 
							name="EMP_TYPE_CODE" 
					     	codeType="TEMP_EMP_TYPE" 
					     	limit="all"
					     	cnpyID="${personInfo.cpnyId}" 
					     	selected="211804"
					     	/>
						
						<font color="red" size="20">*</font>
					</td>
					<!-- td class="td_title">
						人员类型生效日期
					</td>
					<td class="td_type">
						<input type="text" id="viewHire_EMP_TYPE_START_DATE" 
							name="EMP_TYPE_START_DATE" readonly
							class="required date" format="yyyy-MM-dd" 
							yearstart="-100" yearend="10" 
							size="10" value="${personInfo.EMP_TYPE_START_DATE }"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /></a>
					</td>					
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.sheneigongling" />
					</td>
					<td class="td_type" >
						<input type="text" id="viewHire_INNER_WORK_YEAR" 
							name="INNER_WORK_YEAR"  size="10" 
							value="${personInfo.INNER_WORK_YEAR }"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.shewaigongling" />
					</td>
					<td class="td_type" width="15%">
						<input type="text" id="viewHire_OUTER_WORK_YEAR" name="OUTER_WORK_YEAR"  
							size="20" value="${personInfo.OUTER_WORK_YEAR }"/>
						<spring:message	code="hr.viewPersonalInfo.title.tishixinxi.zaiwaigongling" />
					</td>
					
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.laodongshoucebianhao" />
					</td>
					<td class="td_type">
						<input type="text" id="viewHire_MANUAL_NUM" 
							name="MANUAL_NUM"  size="10" value="${personInfo.MANUAL_NUM }"/>
					</td>					
				</tr>
				<tr> -->
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.gongzuodiqu" />
						<!--工作地区-->
					</td>
					<td class="td_type" colspan="3">
						<input type="hidden" id="viewHire_WORK_AREA" 
							name="WORK_AREA" 
							value="${personInfo.WORK_AREA}"/>
						<ait:SelectState id="viewHire_STATENM" 
							name="STATENM" type="STATE" 
							parentNo="" selected="${STATENM}" 
							limit="all"/>
						<span id="viewHire_CITYNM"><select></select></span>
						<span id="viewHire_REGION"><select></select></span>
					</td>	
					<!-- td class=td_title>福利地区(公积金)</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID id="viewHire_INSRAREA_ID_INS" 
							name="INSRAREA_ID_INS" parentNo="216736" 
							selected="${personInfo.INSRAREA_ID_INS}" 
							cnpyID="${personInfo.cpnyId}" limit="all"/>
					 </td> -->
				</tr>
				<tr>
					<td class="td_title" width="10%">
						<spring:message code="hr.viewPersonalInfo.title.gongzuoleixing.chr" />
						<!--工作类型(CHR)-->
					</td>
					<td class="td_type" width="20%">
						<ait:SelectSyCodeByCpnyID id="viewHire_PROMTR_WORK_TP" 
							name="PROMTR_WORK_TP" parentNo="211554" 
							selected="${personInfo.PROMTR_WORK_TP}" 
							cnpyID="${personInfo.cpnyId}" limit="all"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.banhao"/>
						<!--班号-->
					</td>
					<td class="td_type">
						<ait:SelectNonSyCodeByCpnyID name="SHIFT_NO" id="viewHire_SHIFT_NO"
    						codeType="SHIFT_NO" selected="${personInfo.SHIFT_NO }" cnpyID="${personInfo.cpnyId}" />
    					<font color="red" size="20">*</font>
					</td>
					<!-- td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.baoxiangongsi" />
					</td>
					<td class="td_type">
						<select name="INSURANCE_COMPANY">
							<option value="">请选择</option> 
							<c:forEach items="${corpList}" var="item" varStatus="i">
								<option value="${item.CP_NO }" <c:if test="${item.CP_NO eq personInfo.INSURANCE_COMPANY }">selected</c:if>>${item.CP_NAME }</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME" />
					</td>
					<td class="td_type" >
						<ait:SelectSyCodeByCpnyID id="viewHire_INSURANCE_TYPE_CODE" 
							name="INSURANCE_TYPE_CODE" parentNo="483" 
							selected="${personInfo.INSURANCE_TYPE_CODE}" 
							cnpyID="${personInfo.cpnyId}" limit="all"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.nianjiajizhun" />
					</td>
					<td class="td_type" width="15%">
						<input type="text" id="viewHire_YY_VAC_STD_DATE" 
							name="YY_VAC_STD_DATE" readonly
							class="date" format="yyyy-MM-dd" yearstart="-100" yearend="10" 
							size="10" value="${personInfo.YY_VAC_STD_DATE }"/>
						<a class="inputDateButton">
							<spring:message code="public.title.choose" />
						</a>
					</td>
					<td class=td_title><spring:message code="hr.viewPersonalInfo.title.fengongsi.daqu" /></td>
					<td class="td_type">
						<ait:SelectState id="viewHire_PAY_AREA_CD" name="PAY_AREA_CD" 
							type="PAYAREA" parentNo="" 
							selected="${personInfo.PAY_AREA_CD}" limit="all"/>
					 </td>
				</tr>
				<tr> -->
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu" />
						<!--促销员所属-->
					</td>
					<td class="td_type" >
							<ait:SelectSyCodeByCpnyID id="PROMTR_TP" name="PROMTR_TP" parentNo="211837" selected="${personInfo.PROMTR_TP }" cnpyID="${personInfo.cpnyId}" limit="all"/>
					</td>
					<td class="td_title">
						<spring:message
							code="hr.viewPersonalInfo.title.xingjijibie" />
					</td>
					<td class="td_type" width="15%">
							<ait:SelectSyCodeByCpnyID id="STAR_TP" name="STAR_TP" parentNo="215954" selected="${personInfo.STAR_TP }" cnpyID="${personInfo.cpnyId}" limit="all"/>
					</td>
					<!-- td class="td_title">
						评价类型
					</td>
					<td class="td_type" >
					<ait:ComboSyCodeDescByCpnyID id="EVS_TYPE_CODE"
							name="EVS_TYPE_CODE" parentNo="14895"
							selected="${personInfo.EVS_TYPE_CODE }" cnpyID="${personInfo.cpnyId}" limit="all"/>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.shifougongjiancuxiaoyuan" />
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="COMM_YN" id="COMM_YN" 
                              parentNo="123224" cnpyID="${personInfo.cpnyId}" selected="${personInfo.COMM_YN }" limit="all" onChangeName="displayProduct()"/>
					</td>
					<td class="td_title"><spring:message
							code="hr.viewPersonalInfo.title.shifoujianmai" />
						</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="PART_TIME_YN" id="PART_TIME_YN" 
                              parentNo="123224" cnpyID="${personInfo.cpnyId}" selected="${personInfo.PART_TIME_YN }" limit="all" />
					 </td>
					
					<td class="td_title">
					</td>
					<td class="td_type">
					</td> -->
				</tr>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.chanpin" />
					</td>
					<td class="td_type">
						<ait:ComboSyCodeDescByCpnyID id="PROD_TP"
								name="PROD_TP" parentNo="211424"
								selected="${personInfo.PROD_TP }" cnpyID="${personInfo.cpnyId}" limit="all"/>
						<c:if test="${personInfo.cpnyId eq 'TSTO'}"><font color="red" size="20">*</font></c:if>
					</td>
					<td class="td_title">兼卖产品</td>
					<td class="td_type" colspan=3>
						<table class="table" width="100%">
						  <c:forEach items="${codeList}" var="item" varStatus="i">
						    <c:if test="${i.count%4 eq 1}">
						      <tr><td width="80"><input type="checkbox" name="PRODUCT_NO" value="${item.CODE_NO}"/>&nbsp;&nbsp;${item.CODENAME}</td>
						    </c:if>
						    <c:if test="${i.count%4 eq 0}">
						      <td width="80"><input type="checkbox" name="PRODUCT_NO" value="${item.CODE_NO}"/>&nbsp;&nbsp;${item.CODENAME }</td></tr>
						    </c:if>
						    <c:if test="${i.count%4 ne 1 and i.count%4 ne 0}">
						      <td width="80"><input type="checkbox" name="PRODUCT_NO" value="${item.CODE_NO}"/>&nbsp;&nbsp;${item.CODENAME }</td>
						    </c:if>
						  </c:forEach>
						</table>
					</td>
			</table>
		</div>
	</div>
	
<div style="clear: both;"></div>	
			
			<div id="createTableA" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
	<div style="padding: 5px;">
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" onClick="return CheckForm();">
									<spring:message code="public.title.submit" />
									<!-- 保存 -->
								</button>
							</div>
						</div>
					</li>
					
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<spring:message code="public.title.cancle" />
									<!-- 取消 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
