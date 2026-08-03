<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//保存当前扇片索引
	$("#viewAddRecruitInfoPanel_currentIndex",navTab.getCurrentPanel()).val('${currentIndex}');
	//基本信息  部门级联下拉框初始化
	<c:if test="${currentIndex eq '0' and not empty recruitInfo}">
	codeRelation('${recruitInfo.JOIN_TYPE}','JOIN_DETAIL_TYPE','${recruitInfo.JOIN_DETAIL_TYPE}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	codeRelation('${recruitInfo.POST_FAMILY}','POST_GRADE_NO','${recruitInfo.POST_GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	</c:if>
	//保存按钮   表单提交
	<c:if test="${currentIndex ne '5'}">
		$("#viewAddrecruitInfo_save${currentIndex}",navTab.getCurrentPanel()).click(function(){
			$("#viewAddrecruitInfoForm${currentIndex}",navTab.getCurrentPanel()).submit();
		});
	</c:if>
	//照片采集
	<c:if test="${currentIndex eq '5'}">
	//图片预览
	$("#viewAddrecruitInfo_uploadPhoto").uploadPreview({ Img: "newImage", Width: 175, Height: 233 });
	//图片上传
	$("#viewAddrecruitInfo_upload").click(function(){
		if($("#viewAddrecruitInfo_uploadPhoto").val() != ''){
			$.ajaxFileUpload({
	        	url: '/hrm/recruitManage/upload?PERSON_ID=${recruitInfo.EMPID}', //用于文件上传的服务器端请求地址
	          	secureuri: false, //是否需要安全协议，一般设置为false
	          	fileElementId: 'viewAddrecruitInfo_uploadPhoto', //文件上传域的ID
	           	dataType: 'text', //返回值类型 一般设置为json
	        	success: function (data, status){  //服务器成功响应处理函数
		        	var repObj = $.parseJSON(data);
	        		$("#orgImage").attr( "src", repObj.photoPath );
	        		alertMsg.info("<spring:message code="hr.alert.message.upload_success" />");//上传成功
	          	},
	           	error: function (data, status, e){//服务器响应失败处理函数
	             	alert("<spring:message code="hr.alert.message.upload_failure" />");//上传失败
	          	}
	        });
		}else{
    		alertMsg.info("<spring:message code="hr.alert.message.checkBoxForChecked_upload_photo" />");//请先选择要上传的照片
		}
	});
	</c:if>
});
$(function(){
	var cpnyid="${LoginUser.cpnyId}";
	//<c:if test="${recruitInfo.EMPID eq '' || recruitInfo.EMPID eq null}">
	//	$("#SHIFT_NO",navTab.getCurrentPanel()).val(400224);
	//</c:if>
	transCodeChange();
	$("#salary_type",navTab.getCurrentPanel()).click(function(){
		transCodeChange();
	});
	$("#IDCARD_EXPIRE_DATE",navTab.getCurrentPanel()).click(function(){
		transCodeChange();
	});
	$("#final",navTab.getCurrentPanel()).click(function(){
		transCodeChange();
	});
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	$("#POST_GRADE_NO",navTab.getCurrentPanel()).change(function(){ 
			var b="";
			var POST_GRADE_NO=$("#POST_GRADE_NO",navTab.getCurrentPanel()).attr("value");
			$.ajax({
				type : 'post',
				dateType : 'json',
				url : '/pa/salaryCanShu/viewHaoFeng?POST_GRADE_NO='+POST_GRADE_NO,
				data:{b:b},
				success : function(data){
				var list = data.viewHaoFeng;
				if(list!=""){
					var s='';
					for(var i=0;i<list.length;i++){
						var PAY_STEP=list[i]['PAY_STEP'];
						var PAY_STEP_NAME=list[i]['PAY_STEP_NAME'];
						s=s+'<option value="'+PAY_STEP+'">'+PAY_STEP_NAME+'</option>';
					}
					$('#PAY_STEP_NO').html(s);
				}
				}
			 });
	});
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
		$("#checkIdCard_button",navTab.getCurrentPanel()).click(function(){
			var checkIdCard = $("#checkIdCard",navTab.getCurrentPanel()).val();
			$.ajax({
				type: 'POST',
				url: '/hrm/recruitManage/doSql',
				data:{sql:"select PKG_RECRUIT_MANAGE.FN_IS_EXISTS_IDCARD('" + checkIdCard + "','${LoginUser.cpnyId}') DATE_STR from dual"},
				dataType:"json",
				cache: false,
				success: function(data){
					alertMsg.warn((data.result[0].DATE_STR));
				},
				error: DWZ.ajaxError
			});
		});
	</c:if>
});

function transCodeChange(){
	var IDCARD_EXPIRE_DATE = $("#IDCARD_EXPIRE_DATE",navTab.getCurrentPanel()).val();
	var LONG_TERM = $("#final",navTab.getCurrentPanel()).val();
	var transCode = $("#salary_type",navTab.getCurrentPanel()).val();	
	changeDisabled();
	if(IDCARD_EXPIRE_DATE != ''){
		addDisabled("final");
	} 
	if(LONG_TERM == "Y"){
		addDisabled("IDCARD_EXPIRE_DATE");
	}
}
function addDisabled(type){
	if(type == 'final'){
		$('#final',navTab.getCurrentPanel()).attr('disabled',true);
	}
	if(type == 'IDCARD_EXPIRE_DATE'){
		$('#IDCARD_EXPIRE_DATE',navTab.getCurrentPanel()).attr('disabled',true);
	}
}
function changeDisabled(){
	$('#IDCARD_EXPIRE_DATE',navTab.getCurrentPanel()).attr('disabled',false);
	$('#final',navTab.getCurrentPanel()).attr('disabled',false);
}
function finaldegree() {
	var check = $('#final',navTab.getCurrentPanel()).prop('checked');
	if (check == true) {
		$('#final',navTab.getCurrentPanel()).attr('value', 'Y');
	} else {
		$('#final',navTab.getCurrentPanel()).attr('value', 'N');
	}
}
function expiredateid(){
	var IDCARD_EXPIRE_DATE = $("#IDCARD_EXPIRE_DATE",navTab.getCurrentPanel()).val();
}


//保存表单数据
function validateAddrecruitInfoCallback${currentIndex}(form,callback){
	var form = $("#viewAddrecruitInfoForm${currentIndex}");
	if($("#employee_type",navTab.getCurrentPanel()).val() != '14015550'){
	if (!form.valid()) {
		return false;
	}
	<c:if test="${currentIndex eq '0'}">
    
     
	  if($("#viewAddRecruitInfo_deptList",navTab.getCurrentPanel()).val() ==='' || $("#viewAddRecruitInfo_deptList").val()===null){
           alertMsg.error("<spring:message code='hrm.empinfo.deptname_notcompleted' />");//部门未进行填写
           return false;  
    	  }
	  if($("#employee_type",navTab.getCurrentPanel()).val() ==='' || $("#employee_type").val()===null){
          alertMsg.error("<spring:message code='hrm.empinfo.EMP_TYPE_CODE_notcompleted' />");//员工类型未进行填写
          return false;  
   	  }
      if($("#JOIN_TYPE",navTab.getCurrentPanel()).val()==='' || $("#JOIN_TYPE",navTab.getCurrentPanel()).val()===null){
           alertMsg.error("<spring:message code="hrm.empinfo.JOIN_TYPE_notcompleted" />");//入社未进行填写
           return false;
          }
	  
      if($("#POST_FAMILY",navTab.getCurrentPanel()).val()==='' || $("#POST_FAMILY",navTab.getCurrentPanel()).val()===null){
          alertMsg.error("<spring:message code="hrm.empinfo.POST_FAMILY_notcompleted" />");//职群未进行填写
          return false;    
          }
   
  </c:if>
	<c:if test="${currentIndex eq '1'}">
	if($("#NATIONALITY_CODE",navTab.getCurrentPanel()).val() == '871'){
		if($("#IDCARD_NO",navTab.getCurrentPanel()).val() != ''){
			var idcheck = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;       
			if(!idcheck.test($("#IDCARD_NO",navTab.getCurrentPanel()).val())){
				alertMsg.error("<spring:message code="hr.alert.message.viewHire.checkNotNullIdcardNo.error" />");
				return false;
			}
		}
	}
    
    if($("#SEXCODE",navTab.getCurrentPanel()).val() ==='' || $("#SEXCODE",navTab.getCurrentPanel()).val()===null){
    	alertMsg.error("<spring:message code="hrm.alert.recruitManage.SEXCODE_notcompleted" />");//性别未进行填写
      	return  false;
    }
    if($("#NATIONALITY_CODE",navTab.getCurrentPanel()).val() ==='' || $("#NATIONALITY_CODE",navTab.getCurrentPanel()).val()===null){
    	alertMsg.error("<spring:message code="hrm.alert.recruitManage.NATIONALITY_CODE_notcompleted" />");//国籍未进行填写
      	return  false;
    }
    if($("#NATION_CODE",navTab.getCurrentPanel()).val() ==='' || $("#NATION_CODE",navTab.getCurrentPanel()).val()===null){
    	alertMsg.error("<spring:message code="hrm.alert.recruitManage.NATION_CODE_notcompleted" />");//民族未进行填写
      	return  false;
    }
    if($("#MARITAL_STATUS_CODE",navTab.getCurrentPanel()).val() ==='' || $("#MARITAL_STATUS_CODE",navTab.getCurrentPanel()).val()===null){
    	alertMsg.error("<spring:message code="hrm.alert.recruitManage.MARITAL_STATUS_notcompleted" />");//婚姻状态未进行填写
      	return  false;
    }   
	</c:if>
	<c:if test="${currentIndex eq '2'}">
	var edu_s_date = $("#START_DATE_${currentIndex}",navTab.getCurrentPanel()).val();
	var edu_e_date = $("#END_DATE_${currentIndex}",navTab.getCurrentPanel()).val();
	var edus_date = edu_s_date.substring(3,7) + edu_s_date.substring(0,2);
	var edue_date = edu_e_date.substring(3,7) + edu_e_date.substring(0,2);
	if(edus_date >= edue_date){
		alertMsg.error("<spring:message code="ar.alert.message.viewCardAssociate.enddatemustbigger" />");
		return false;
	}
	</c:if>
	<c:if test="${currentIndex eq '3'}">
	var work_s_date = $("#START_DATE_${currentIndex}",navTab.getCurrentPanel()).val();
	var work_e_date = $("#END_DATE_${currentIndex}",navTab.getCurrentPanel()).val();
	var works_date = work_s_date.substring(6,10) + work_s_date.substring(3,5) + work_s_date.substring(0,2);
	var worke_date = work_e_date.substring(6,10) + work_e_date.substring(3,5) + work_e_date.substring(0,2);
	if(works_date >= worke_date){
		alertMsg.error("<spring:message code="ar.alert.message.viewCardAssociate.enddatemustbigger" />");
		return false;
	}
	</c:if>
	}
	alertMsg.confirm("<spring:message code="zxc.hr.viewEvaluate.title.SAVE_CONFIRM" />",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url:form.attr("action"),
  				data:form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  		});
  	}});
	return false;
}

function age(){
	var DOB = $("#DOB",navTab.getCurrentPanel()).val();
	if($("#DOB",navTab.getCurrentPanel()).val() == null || $("#DOB",navTab.getCurrentPanel()).val() == ""){
		$("#AGE",navTab.getCurrentPanel()).val("");
	}else{
		var nowdate = new Date();
		var nowyear = nowdate .getFullYear();
		var birth = parseInt(DOB.substring(0,4));
		$("#AGE",navTab.getCurrentPanel()).val(nowyear - birth);
	}
	
}

function card(){
	if($("#DOCUMENT_TYPE",navTab.getCurrentPanel()).val() == '4596'){
		var IDCARD_NO = $("#IDCARD_NO",navTab.getCurrentPanel()).val();

		if($("#IDCARD_NO",navTab.getCurrentPanel()).val().length == 18){
			var birthday = IDCARD_NO.substring(6,14);
			var sex = IDCARD_NO.substring(16,17);
		}else if($("#IDCARD_NO",navTab.getCurrentPanel()).val().length == 15){
			var birthday = "19"+IDCARD_NO.substring(6,12);
			var sex = IDCARD_NO.substring(14,15);
			}
		
		$("#DOB",navTab.getCurrentPanel()).val(birthday);

		if($("#DOB",navTab.getCurrentPanel()).val(birthday) == null || $("#DOB",navTab.getCurrentPanel()).val(birthday) == ""){
			$("#AGE",navTab.getCurrentPanel()).val("");
		}else{
			var nowdate = new Date();
			var nowyear = nowdate .getFullYear();
			var birth = parseInt(birthday.substring(0,4));
			$("#AGE",navTab.getCurrentPanel()).val(nowyear - birth);
		}
		
		
		if(sex%2 == 1){
			$("#SEXCODE",navTab.getCurrentPanel()).val(1326); //男
		}else{
			$("#SEXCODE",navTab.getCurrentPanel()).val(1325);
		}

	}
}

function group(){
	var date_start2 = $("#DATE_STARTED",navTab.getCurrentPanel()).val();
	var post_family2 = $("#POST_FAMILY",navTab.getCurrentPanel()).val();
	if(date_start2 != null && date_start2 != "" && post_family2 != null && post_family2 != ""){
		if(post_family2 == '14015813' || post_family2 == '14015814'){//办公职//一般职
			$.ajax({
    			type: 'POST',
    			url: '/hrm/recruitManage/doSql',
    			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + date_start2 + "','dd/MM/yyyy')-1,2),'dd/MM/yyyy') DATE_STR from dual"},
    			dataType:"json",
    			cache: false,
    			success: function(data){
    				$("#END_PROBATION_DATE",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
    			},
    			error: DWZ.ajaxError
    		});
		}
		if(post_family2 == '14015815'){//生产职
			$.ajax({
    			type: 'POST',
    			url: '/hrm/recruitManage/doSql',
    			data:{sql:"select TO_CHAR(add_months(TO_DATE('" + date_start2 + "','dd/MM/yyyy')-1,1),'dd/MM/yyyy') DATE_STR from dual"},
    			dataType:"json",
    			cache: false,
    			success: function(data){
    				$("#END_PROBATION_DATE",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
    			},
    			error: DWZ.ajaxError
    		});
		}
	}
    }
function changeoffice(){
	var check=$('#emergency_yn').prop('checked');
	if(check==true){
		$('#EMERGENCY_CONTACT_YN').attr('value','Y');
	}else{
		$('#EMERGENCY_CONTACT_YN').attr('value','N');
	}
}
function zhaopinruzhi(value,num){
	codeRelation(value,'JOIN_DETAIL_TYPE','${recruitInfo.JOIN_DETAIL_TYPE}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	var joinType = $("#JOIN_TYPE",navTab.getCurrentPanel()).val();
	var cnpnyId = '${LoginUser.cpnyId}';
	if (cnpnyId == 'HAE') {
		if (joinType == '400414') {
			$("input[name='IS_PROBATION']",navTab.getCurrentPanel()).attr("checked",false);
		}else{
			$("input[name='IS_PROBATION']",navTab.getCurrentPanel()).attr("checked",true);
		}
	}
}
function zhaopinzhiqun(value,num){
	codeRelation(value,'POST_GRADE_NO','${recruitInfo.POST_GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	group();
}

function personSupplier(){
	var empCode = $("#employee_type",navTab.getCurrentPanel()).val();
	if (empCode == '10416') {
		$('#COMPANY_NAME option',navTab.getCurrentPanel()).attr("selected",true);
	}else {
		$('#COMPANY_NAME option',navTab.getCurrentPanel()).attr("selected",false);
	}
}
</script>
<c:if test="${currentIndex eq '0'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,navTabAjaxDoneWithForm);">
	<div class="user_table">
	<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
	<div style="float: left; height: 25px; line-height: 25px;">
		<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" /><!-- 身份证号 --> : <input type="text" id="checkIdCard" name="checkIdCard" value=""  size="25" />
		<a class="w_button" id="checkIdCard_button"><span>check</span></a>
	</div>
	</c:if>
	<c:if test="${recruitInfo.ACTIVITY ne '1'}">	
	<div style="float: right; height: 25px; line-height: 25px;"><a
		class="w_button"
		onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=8888&currentIndex=0','viewRecruitList_unit');">
	<span><spring:message code="button.add" /><!-- 添加 --></span></a> <a
		class="w_button"
		href="/hrm/recruitManage/deleteRecruitInfo?PERSON_ID=${recruitInfo.PERSON_ID}&currentIndex=0"
		target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="button.delete.sure" />"><!-- 确定要删除吗? -->
	<span><spring:message code="button.delete" /><!-- 删除 --></span></a> <a
		class="w_button" id="viewAddrecruitInfo_save${currentIndex}"> <span><spring:message
		code="button.sys.affirm.save" /><!-- 保存 --></span></a></div>
	</c:if>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.name" /><!-- 姓名 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="LOCAL_NAME" name="LOCAL_NAME" class="required"
						value="${recruitInfo.LOCAL_NAME}"  size="25" /> 
						<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${recruitInfo.PERSON_ID}" />
						<input type="hidden" id="currentIndex" name="currentIndex" value="${currentIndex}" />
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.empid" /><!-- 社号 --></td>
					<td width="35%" class="td_type">
						<input type="text" <c:if test="${LoginUser.cpnyId eq 'HAE' && recruitInfo.EMPID eq null}">readonly="readonly"</c:if> id="empid" name="EMPID" value="${recruitInfo.EMPID}" size="25" /> 
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.ENGLISH_NAME" /><!-- 英文姓名 --></td>
					<td width="35%" class="td_type"><input type="text"
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}"> </c:if> id="ENGLISH_NAME" name="ENGLISH_NAME"
						value="${recruitInfo.ENGLISH_NAME}" size="25" /></td>
					<td width="15%" class="td_title">
						<!-- 无试用工资 --><spring:message code="hrm.recruitManage.IS_PROBATION.Z" />
					</td>
					<td width="35%" class="td_type">
						<input type="checkbox" name="IS_PROBATION" <c:if test="${recruitInfo.IS_PROBATION eq '1'}">checked</c:if> value="1">
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title">
						<!-- 入社 --><spring:message code="hrm.recruitManage.JOIN_TYPE.Z" />
					</td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID id="JOIN_TYPE" name="JOIN_TYPE" selected="${recruitInfo.JOIN_TYPE}" parentNo="1359" onChangeName="zhaopinruzhi(this.value,'1');" limit="all"/>
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
					<td width="15%" class="td_title">
						 <!-- 入社细节区分 --> <spring:message code="hrm.recruitManage.JOIN_DETAIL_TYPE.Z" /> 
					</td>
					<td width="35%" class="td_type">
						<select name="JOIN_DETAIL_TYPE" id="JOIN_DETAIL_TYPE"></select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
					<td width="35%" class="td_type">
						<ait:deptList name="DEPTNO" limit="super" id="viewAddRecruitInfo_deptList" parameter="WORK_AREA" onClickName='${recruitInfo.MAIN_BUSINESS}'/>
						<ait:deptTreeIcon name="DEPTNO" limit="super" id="viewAddRecruitInfo_deptList" selected="${recruitInfo.DEPTNO}" parameter="WORK_AREA" onClickName='${recruitInfo.MAIN_BUSINESS}'/>
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 --></td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID id="employee_type" name="EMP_TYPE_CODE" selected="${recruitInfo.EMP_TYPE_CODE}"
							parentNo="13864" onChangeName="personSupplier();" limit="all" />
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></td>
					<td width="35%" class="td_type"><input type="text" class="Wdate required"
						id="DATE_STARTED" name="DATE_STARTED"  onBlur="group()"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${recruitInfo.DATE_STARTED }" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.END_PROBATION_DATE" /><!-- 试用结束日期--></td>
					<td width="35%" class="td_type"><input type="text"
						id="END_PROBATION_DATE" name="END_PROBATION_DATE"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${recruitInfo.END_PROBATION_DATE }" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title">
						<!-- 最初入社日期  --><spring:message code="hrm.recruitManage.TIME_STARTED.Z" />		
					</td>
					<td width="35%" class="td_type"><input type="text"
						id="TIME_STARTED" name="TIME_STARTED" 
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${recruitInfo.TIME_STARTED }" /></td>
					<td width="15%" class="td_title">
						<!-- 现职位认定日期 --><spring:message code="hrm.recruitManage.XIANZHIWEI_RENDING_DATE.Z" /></td>
					<td width="35%" class="td_type"><input type="text"
						id="POSITION_CONFIRMATION_DATE" name="POSITION_CONFIRMATION_DATE"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${recruitInfo.POSITION_CONFIRMATION_DATE }" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 --></td>
					<td width="35%" class="td_type">
						<select name="MAIN_BUSINESS" id="MAIN_BUSINESS" ></select>
					</td>
					<td width="15%" class="td_title">
						<!-- 成本中心 --><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" />
					</td>
					<td width="85%" class="td_type" colspan="3">
						<ait:SelectOrgInfo name="COST_CENTER" orgType="COST_CENTER" selected="${recruitInfo.COST_CENTER}"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID name="POST_FAMILY" id="POST_FAMILY" selected="${recruitInfo.POST_FAMILY}" parentNo="14015812" onChangeName="zhaopinzhiqun(this.value,'1');" limit="all"/>
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.contract.Rank" /><!-- 职级 --></td>
					<td width="35%" class="td_type">
						<select name="POST_GRADE_NO" id="POST_GRADE_NO" onChange="codeRelation(this.value,'S_BAND');"></select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.contract.POSITION_NO" /><!-- 职责 --></td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID  id="duty" name="POSITION_NO" 
							selected="${recruitInfo.POSITION_NO}" parentNo="14014036"  limit="all" />
					</td>
					<td width="15%" class="td_title">
						<!-- 滞留年限 --><spring:message code="hrm.recruitManage.ZHILIU_YEAR.Z" /></td>
					<td width="35%" class="td_type">
						<input type="text" id="DETAIN_LENGTH" name="DETAIN_LENGTH" value="${recruitInfo.DETAIN_LENGTH}"  size="25" /> 
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hr.viewPersonalInfo.title.banzu" /><!-- 班组 --></td>
					<td width="35%" class="td_type">
						<input type="hidden" id="SHIFT_NO" name="SHIFT_NO" value="400224"  size="25" />
						<spring:message  code="ar.viewArShiftMonthCheckList.ZHENGCHANGBAN.b" /><!-- 正常班 --> 
					</td>
				</tr>
				<tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.NIANZI_DENGJI.Z" /><!-- 年资等级 --></td>
					<td width="35%" class="td_type">
						<select name="PAY_STEP_NO" id="PAY_STEP_NO">
							<option>${recruitInfo.PAY_STEP_NAME}</option>
						</select>
					</td>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.personSupplier" /><!-- 供人公司 --></td>
					<td width="35%" class="td_type">
						<select name="COMPANY_NAME" id="COMPANY_NAME">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${supplierList}" var="result">
								<option value="${result.COMPANY_NAME}" name="${result.COMPANY_NAME}" <c:if test="${result.COMPANY_NAME eq recruitInfo.PERSON_SUPPLIER_COMPANY}">selected="selected"</c:if>>${result.COMPANY_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</c:if>
				<td width="15%" class="td_title"></td>
					<td width="35%" class="td_type"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
	<c:if test="${empty recruitInfo.PERSON_ID}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
						<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</c:if>
			<c:if test="${not empty recruitInfo.PERSON_ID}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewHrResumeList_unit','/hrm/empinfo/viewAddResumeInfo?SEQ=${recruitInfo.PERSON_ID}','${recruitInfo.PERSON_ID}','HR_RESUME','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewHrResumeList_unit','/hrm/recruitManage/viewRecruitList?SEQ=${recruitInfo.PERSON_ID}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
						<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${recruitInfo.fileList}" var="item" varStatus="i">
								<tr>
									<td class='td_center'><input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/></td>
									<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
	</c:if>
	</div>
	</form>
</c:if>

<c:if test="${currentIndex eq '1'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,navTabAjaxDoneWithForm);">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<spring:message code="hrm.recruitManage.I_MATTER" /><!-- 本人事项 --></div>
	<c:if test="${recruitInfo.ACTIVITY ne '1'}">
	<div
		style="float: right; height: 20px; line-height: 20px; margin-top: 5px;">
	<a class="w_button" id="viewAddrecruitInfo_save${currentIndex}"> <span><spring:message
		code="button.sys.affirm.save" /><!-- 保存 --></span></a></div>
		</c:if>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr style="display:none">
					<td width="15%" class="td_title" colspan="7">
						<ait:SelectSyCodeByCpnyID
						id="employee_type"
						name="EMP_TYPE_CODE" selected="${recruitInfo.EMP_TYPE_CODE}"
						parentNo="13864"
						limit="all" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="ess.personalinfo.title.IDCardNo" /><!-- 身份证号 --></td>
					<td width="35%" class="td_type"><input type="text" class="required" onBlur="card()"
						id="IDCARD_NO" name="IDCARD_NO" value="${recruitInfo.IDCARD_NO}"
						size="25" /> <input type="hidden" id="PERSON_ID" name="PERSON_ID"
						value="${recruitInfo.PERSON_ID}" /> <input type="hidden"
						id="currentIndex" name="currentIndex" value="${currentIndex}" />
					</td>
					<td class="td_title"><spring:message code="hrm.empinfo.award_date" /><!--获证日期--></td>
					<td class="td_type" width="25%">
						<input name="IDCARD_START_DATE" id="IDCARD_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
							value="${recruitInfo.IDCARD_START_DATE}"/>
					</td>
				</tr>
				<tr>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">	
						<td class="td_title"><spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 --></td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
						<td class="td_title"><!-- 签发地点 --> <spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE" /></td>
					</c:if>
					<td class="td_type" width="25%">
						<input type="text" value="${recruitInfo.ISSUING_AUTHORITY }" id="ISSUING_AUTHORITY" name="ISSUING_AUTHORITY"  size="25">
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.FAM_BORNDATE" /><!-- 出生日期 --></td>
					<td width="35%" class="td_type">
						<input id="DOB" name="DOB" class="Wdate" 
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recruitInfo.DOB }" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.SEXCODE" /><!-- 性别 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						id="SEXCODE" name="SEXCODE" selected="${recruitInfo.SEXCODE}" parentNo="1324"
						limit="all" />
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.MARITAL_STATUS_NAME" /><!-- 婚姻状态 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						id="MARITAL_STATUS_CODE" name="MARITAL_STATUS_CODE"
						selected="${recruitInfo.MARITAL_STATUS_CODE}" parentNo="1709"
						limit="all" />
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
						</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.NATIONALITY_CODE" /><!-- 国籍 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						id="NATIONALITY_CODE" name="NATIONALITY_CODE" selected="${recruitInfo.NATIONALITY_CODE}"
						parentNo="870" limit="all" />
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.NATION_CODE" /><!-- 民族 --></td>
					<td width="35%" class="td_type" colspan="3">
						<ait:SelectSyCodeByCpnyID
						id="NATION_CODE" name="NATION_CODE" selected="${recruitInfo.NATION_CODE}"
						parentNo="210942" limit="all" />
						<input type="text" style="width:5px;border: 0;background-image:url(/resources/css/dwzUI/themes/azure/images/form/input_bg.png);background-repeat:no-repeat;background-position:right center" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="ess.personalinfo.title.familyTelphone" /><!-- 家庭电话 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="HOME_PHONE" name="HOME_PHONE" 
						value="${recruitInfo.HOME_PHONE}" size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.CELLPHONE" /><!-- 手机号 --></td>
					<td width="35%" class="td_type"><input type="text" class="required" 
						id="COMPANY_PHONE" name="COMPANY_PHONE" 
						value="${recruitInfo.COMPANY_PHONE}" size="25" /></td>
				</tr>
				<tr>
					<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
					<td class="td_type" width="25%">
						<ait:SelectSyCodeByCpnyID name="EXIST_SINGLE" id="EXIST_SINGLE"
							parentNo="14013861" cnpyID="${defaultCpny}" selected="${recruitInfo.EXIST_SINGLE}" limit="all" />
					</td>
					<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
					<td class="td_type" width="25%">
						<input type="text" value="${recruitInfo.SING_ID }" id="SING_ID" name="SING_ID" size="25">
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.EMAIL" /><!-- E-Mail --></td>
					<td width="35%" class="td_type"><input type="text" id="EMAIL"
						name="EMAIL" value="${recruitInfo.EMAIL}" class="email" size="25" />
					</td>
					<td class="td_title">
						<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
					</td>
					<td class="td_type" width="25%">
						<ait:SelectSyCodeByCpnyID name="CV_UPDATE_STATUS" id="CV_UPDATE_STATUS" parentNo="90000302"
							cnpyID="${defaultCpny}" selected="${recruitInfo.CV_UPDATE_STATUS}" limit="all" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></td>
					<td class="td_type" width="25%">
						<input type="text" id="RELIGION" name="RELIGION" value="${recruitInfo.RELIGION}">
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.JINGLI_QIJIAN.Z" /><!-- 经历期间 --></td>
					<td width="35%" class="td_type"><input type="text" id="EXPERIENCE"
						name="EXPERIENCE" value="${recruitInfo.EXPERIENCE}" size="25" />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="ess.empInfo.height" /><!-- 身高 --></td>
					<td width="35%" class="td_type"><input type="text" 
						id="HEIGHT" name="HEIGHT" value="${recruitInfo.HEIGHT}"
						size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="ess.empInfo.weight" /><!-- 体重 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="WEIGHT" name="WEIGHT" 
						value="${recruitInfo.WEIGHT}" size="25" /></td>
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.HUJIDIZHI" /><!-- 户口所在地 --></td>
					<td width="35%" class="td_type"><input type="text" 
						id="REG_PLACE" name="REG_PLACE" value="${recruitInfo.REG_PLACE}"
						size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.ORIGIN.Z" /><!-- 出生地 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="ORIGIN" name="ORIGIN" 
						value="${recruitInfo.ORIGIN}" size="25" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!-- 地址 --></div>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr>
					<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" /><!--地址类型--></td>
					<td class="td_type"  width="25%">
						<ait:SelectSyCodeByCpnyID name="ADDRESS_TYPE" id="ADDRESS_TYPE"
                            parentNo="14013840" cnpyID="${defaultCpny}" selected="${recruitInfo.ADDRESS_TYPE}" limit="all"/>
					</td>
				</tr>
				<tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.approve.EFFECTIVE_START_DATE" /><!--有效开始日--></td>
					<td class="td_type"  width="25%" >
					<input name="EFFECTIVE_START_DATE"  id="EFFECTIVE_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recruitInfo.EFFECTIVE_START_DATE}" />
					</td>	
				 </tr>
				 <tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.empinfo.country" /><!--国家--></td>
				   <td class="td_type"  width="25%" >	<ait:SelectSyCodeByCpnyID name="NATIONALITY" id="NATIONALITY"
                                 parentNo="870" cnpyID="${defaultCpny}" selected="${recruitInfo.NATIONALITY}" limit="all" />
				 </td>
				 </tr>
				 <tr>
				 <td class="td_title" width="4%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址--></td>
					<td class="td_type"  width="25%" >
					 <input type="text"  id="ADDRESS_CONTENT" name="ADDRESS_CONTENT" value=${recruitInfo.ADDRESS_CONTENT }>
					</td>	
				 </tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<spring:message code="hrm.contractInfo.HUIJIDIZHI" /><!-- 户籍地址 --></div>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr>
					<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_ADDRESS_TYPE" /><!--地址类型--></td>
					<td class="td_type"  width="25%">
                        <c:if test="${empty recruitInfo.HUJIADDRESS_TYPE}">
								<ait:SelectSyCodeByCpnyID name="HUJIADDRESS_TYPE" selected="14013843"  parentNo="14013840" limit="all"/>
						</c:if>
						<c:if test="${not empty recruitInfo.HUJIADDRESS_TYPE}">
								<ait:SelectSyCodeByCpnyID name="HUJIADDRESS_TYPE" selected="${recruitInfo.HUJIADDRESS_TYPE}" parentNo="14013840" limit="all"/>
						</c:if>
					</td>
				</tr>
				<tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.approve.EFFECTIVE_START_DATE" /><!--有效开始日--></td>
					<td class="td_type"  width="25%" >
					<input name="HUJIYOUXIAO_START_DATE"  id="HUJIYOUXIAO_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${recruitInfo.HUJIYOUXIAO_START_DATE}" />
					</td>	
				 </tr>
				 <tr>
				  <td class="td_title" width="4%"><spring:message code="hrm.empinfo.country" /><!--国家--></td>
				   <td class="td_type"  width="25%" >	<ait:SelectSyCodeByCpnyID name="HUJIGUOJI" id="HUJIGUOJI"
                                 parentNo="870" cnpyID="${defaultCpny}" selected="${recruitInfo.HUJIGUOJI}" limit="all" />
				 </td>
				 </tr>
				 <tr>
				 <td class="td_title" width="4%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址--></td>
					<td class="td_type"  width="25%" >
					 <input type="text"  id="HUJIADDRESS_CONTENT" name="HUJIADDRESS_CONTENT" value=${recruitInfo.HUJIADDRESS_CONTENT }>
					</td>	
				 </tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	</form>
</c:if>

<c:if test="${currentIndex eq '2'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,divAjaxDone);">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
	<spring:message code="hrm.recruitManage.Education_matters" /><!-- 学历事项 --></div>
	<c:if test="${employee_type.ACTIVITY ne '1'}">
	<div style="float: right; height: 25px; line-height: 25px;"><a
		class="w_button"
		onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ2=-1&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');">
	<span><spring:message code="button.add" /><!-- 添加 --></span> </a> <a
		class="w_button"
		href="/hrm/recruitManage/deleteRecruitInfo?SEQ=${recruitInfo.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}"
		target="ajaxTodo" callback="divAjaxDone" title="<spring:message code='hrm.alert.empinfo.Sure.delete' />"><!-- 确定要删除吗？ --><span><spring:message
		code="button.delete" /><!-- 删除 --></span></a> <a class="w_button"
		id="viewAddrecruitInfo_save${currentIndex}"><span><spring:message
		code="button.sys.affirm.save" /><!-- 保存 --></span></a></div>
	</c:if>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr style="display:none">
					<td width="15%" class="td_title" colspan="4">
						<ait:SelectSyCodeByCpnyID
						id="employee_type"
						name="EMP_TYPE_CODE" selected="${employee_type.EMP_TYPE_CODE}"
						parentNo="13864"
						limit="all" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hr.viewPersonalInfo.title.SCHOOL" /><!-- 学校名 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="INSTITUTION_NAME" name="INSTITUTION_NAME"
						value="${recruitInfo.INSTITUTION_NAME}" class="required" size="25" />
					</td>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td width="15%" class="td_title"></td>
					<td width="35%" class="td_type"></td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.XUEXIAO_ADDRESS.Z" /><!-- 学校地址 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="ADDRESS" name="ADDRESS"
						value="${recruitInfo.ADDRESS}" size="25" />
					</td>
					</c:if>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.DEGREE_CODE" /><!-- 学历 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						name="DEGREE_CODE" selected="${recruitInfo.DEGREE_CODE}"
						parentNo="13769" limit="all" /> <input type="hidden" id="SEQ"
						name="SEQ" value="${recruitInfo.SEQ}" /> <input type="hidden"
						id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID}" /> <input
						type="hidden" id="currentIndex" name="currentIndex"
						value="${currentIndex}" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!-- 认定学历与否--></td>
					<td width="35%" class="td_type"><input type="checkbox"
						name="FINAL_DEGREE_WHETHER"
						<c:if test="${recruitInfo.FINAL_DEGREE_WHETHER eq 'Y'}">checked</c:if>
						value="Y"></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.START_DATE" /><!-- 入学日期--></td>
					<td width="35%" class="td_type"><input type="text"
						id="START_DATE_${currentIndex}" name="START_DATE"
						 onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${recruitInfo.START_DATE }" />
						</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.END_DATE" /><!-- 毕业日期 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="END_DATE_${currentIndex}" name="END_DATE"
						 onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${recruitInfo.END_DATE }" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.SUBJECT" /><!-- 专业--></td>
					<td width="35%" class="td_type"><input type="text"
						id="SUBJECT" name="SUBJECT" value="${recruitInfo.SUBJECT}"
						size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!-- 副专业--></td>
					<td width="35%" class="td_type"><input type="text"
						id="SUBJECT_SECOND" name="SUBJECT_SECOND" value="${recruitInfo.SUBJECT_SECOND}"
						size="25" /></td>
				</tr>
				<tr>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.country" /><!-- 国家 --></td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID name="SITE_COUNTRY" id="SITE_COUNTRY"
                           parentNo="870" cnpyID="${defaultCpny}" selected="${recruitInfo.SITE_COUNTRY}" limit="all" />
                    </td>
                    </c:if>
                    <c:if test="${LoginUser.cpnyId eq 'HAE'}">
                    <td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.JIAOYU_XINGSHI.Z" /><!-- 教育形式 --></td>
					<td width="35%" class="td_type">
						<ait:SelectSyCodeByCpnyID name="SCHOOL_LENGTH" id="SCHOOL_LENGTH"
                           parentNo="13704" cnpyID="${defaultCpny}" selected="${recruitInfo.SCHOOL_LENGTH}" limit="all" />
                    </td>
                    </c:if>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.graduation_Certificate_number" /><!-- 毕业证书编号 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="EDU_DEG_NUM" name="EDU_DEG_NUM"
						value="${recruitInfo.EDU_DEG_NUM}" size="25" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.REMARK" /><!-- 备注 --></td>
					<td width="85%" class="td_type" colspan="3"><textarea
						name="REMARK" style="width: 600px; height: 80px">${recruitInfo.REMARK}</textarea>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATED_BY" /><!-- 变更者 --></td>
					<td width="35%" class="td_type">
					[${recruitInfo.EMPID}]-${recruitInfo.LOCAL_NAME}
					${recruitInfo.UPDATED_IP}</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></td>
					<td width="35%" class="td_type">${recruitInfo.UPDATE_DATE}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	<c:if test="${empty recruitInfo.SEQ}">
		<div>
		<c:if test="${employee_type.ACTIVITY ne '1'}">
		<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
		<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message
			code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件 --></span></a> <a
			class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message
			code="button.delete" /><!-- 删除 --></span></a></div>
		</c:if>
		<table id="fileTable" class="list" width="100%">
			<thead>
				<tr>
					<th width="10%">V</th>
					<th width="90%"><spring:message
						code="hrm.recruitManage.ENCLOSURE" /><!-- 附件 --></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
	</c:if> <c:if test="${not empty recruitInfo.SEQ}">
		<div>
		<c:if test="${employee_type.ACTIVITY ne '1'}">
		<div style="font: 10px; float: left; height: 20px; line-height: 20px;">
		<a class="w_button" href="#"
			onclick="uploadAttDialog_new('viewRecruitList_unit','/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ2=${recruitInfo.SEQ}$PERSON_ID=${PERSON_ID}$currentIndex=${currentIndex}','${recruitInfo.SEQ}','EDU_RECRUIT','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
		<span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件 --></span></a>
		<a class="w_button" href="#"
			onclick="deleteAttList_new('viewRecruitList_unit','/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ2=${recruitInfo.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><spring:message
			code="button.delete" /><!-- 删除 --></span></a></div>
		</c:if>
		<table id="fileTable" class="list" width="100%">
			<thead>
				<tr>
					<th width="10%">V</th>
					<th width="90%"><spring:message
						code="hrm.recruitManage.ENCLOSURE" /><!-- 附件 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${recruitInfo.fileList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'><input type="checkbox" name="FILE_NO"
							value="${item.FILE_NO}" /></td>
						<td><a
							href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</c:if>
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">List</div>
	<table class="table" width="100%" layoutH="670">
		<thead>
			<tr>
				<th width="30px">No.</th>
				<th width="80px"><spring:message code="hrm.empinfo.DEGREE_CODE" /><!-- 学历 --></th>
				<th width="80px"><spring:message
					code="hrm.recruitManage.INSTITUTION_NAME" /><!-- 学校 --></th>
				<th width="80px"><spring:message
					code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!-- 认定学历与否 --></th>
				<th width="80px"><spring:message
					code="hrm.recruitManage.START_DATE" /><!-- 入学日期 --></th>
				<th width="80px"><spring:message
					code="hrm.recruitManage.END_DATE" /><!-- 毕业日期 --></th>
				<th width="80px"><spring:message
					code="hrm.recruitManage.SUBJECT" /><!-- 专业 --></th>
				<th width="100px"><spring:message code="hrm.empinfo.UPDATED_BY" /><!-- 变更者--></th>
				<th width="100px"><spring:message
					code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewEducationList}" var="item" varStatus="i">
				<tr
					onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ2=${item.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');">
					<td class='td_center' width="30px">${i.count}</td>
					<td width="80px">${item.DEGREE_CODE_NAME}</td>
					<td width="80px">${item.INSTITUTION_NAME}</td>
					<td width="80px">${item.FINAL_DEGREE_WHETHER}</td>
					<td width="80px">${item.START_DATE}</td>
					<td width="80px">${item.END_DATE}</td>
					<td width="80px">${item.SUBJECT}</td>
					<td width="80px">[${item.EMPID}]-${item.LOCAL_NAME}
					${item.UPDATED_IP}</td>
					<td width="80px">${item.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</c:if>


<c:if test="${currentIndex eq '3'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,divAjaxDone);">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;"><spring:message
		code="hrm.recruitManage.Experience_information" /><!-- 经历信息 --></div>
		<c:if test="${employee_type.ACTIVITY ne '1'}">
	<div style="float: right; height: 25px; line-height: 25px;"><a
		class="w_button"
		onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ3=-1&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');"><span><spring:message
		code="button.add" /><!-- 添加 --></span></a> <a class="w_button"
		href="/hrm/recruitManage/deleteRecruitInfo?SEQ=${recruitInfo.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}"
		target="ajaxTodo" callback="divAjaxDone" title="<spring:message code='hrm.alert.empinfo.Sure.delete' />"><!-- 确定要删除吗？ --><span><spring:message
		code="button.delete" /><!-- 删除 --></span></a> <a class="w_button"
		id="viewAddrecruitInfo_save${currentIndex}"><span><spring:message
		code="button.sys.affirm.save" /><!-- 保存 --></span></a></div>
		</c:if>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr style="display:none">
					<td width="15%" class="td_title" colspan="4">
						<ait:SelectSyCodeByCpnyID
						id="employee_type"
						name="EMP_TYPE_CODE" selected="${employee_type.EMP_TYPE_CODE}"
						parentNo="13864"
						limit="all" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title">
						<spring:message code="hrm.empinfo.COMPANY_NAME" /><!--公司-->
					</td>
					<td width="85%" class="td_type" colspan="3">
						<input type="text" id="CPNY_NAME" name="CPNY_NAME" value="${recruitInfo.CPNY_NAME}" class="required" size="70" /> 
						<input type="hidden" id="SEQ" name="SEQ" value="${recruitInfo.SEQ}" /> 
						<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID}" /> 
						<input type="hidden" id="currentIndex" name="currentIndex" value="${currentIndex}" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title">
						<spring:message code="edu.teacherManager.BUMEN.a" /><!-- 部门 --></td>
					<td width="35%" class="td_type">
						<input type="text" id="DEPT_NAME" name="DEPT_NAME" value="${recruitInfo.DEPT_NAME}" size="25" />
					</td>
					<td width="15%" class="td_title"><spring:message
						code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!-- 月薪 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="PAYROLL" name="PAYROLL" value="${recruitInfo.PAYROLL}"
						size="25" /></td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="rp.report.title.dutyinfo" /><!-- 岗位 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="DUTY" name="DUTY" value="${recruitInfo.DUTY}"
						size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="POSITION" name="POSITION" value="${recruitInfo.POSITION}"
						size="25" /></td>
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="START_DATE_${currentIndex}" name="START_DATE"
						class="Wdate required"
						onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
						value="${recruitInfo.START_DATE }" size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.recruitManage.LEAVE_DATE" /><!-- 离职日期 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="END_DATE_${currentIndex}" name="END_DATE"
						class="Wdate required"
						onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
						value="${recruitInfo.END_DATE }" size="25" /></td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="ess.trans.title.resignReason" /><!-- 离职原因 --></td>
					<td width="85%" class="td_type" colspan="3">
						<textarea name="LEFT_REASON" id="LEFT_REASON" style="width: 600px; height: 80px">${recruitInfo.LEFT_REASON}</textarea>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.REMARK" /><!-- 备注 --></td>
					<td width="85%" class="td_type" colspan="3">
						<textarea name="REMARK" id="REMARK" style="width: 600px; height: 80px">${recruitInfo.REMARK}</textarea>
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATED_BY" /><!-- 变更者 --></td>
					<td width="35%" class="td_type">
					[${recruitInfo.EMPID}]-${recruitInfo.LOCAL_NAME}
					${recruitInfo.UPDATED_IP}</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></td>
					<td width="35%" class="td_type">${recruitInfo.UPDATE_DATE}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">List</div>
	<table class="table" width="100%" layoutH="550">
		<thead>
			<tr>
				<th width="30px">No.</th>
				<th width="80px"><spring:message
					code="hrm.empinfo.COMPANY_NAME" /><!--公司--></th>
				<th width="80px"><spring:message
						code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></th>
				<th width="80px"><spring:message
						code="hrm.recruitManage.LEAVE_DATE" /><!-- 离职日期 --></th>
				<th width="80px"><spring:message
					code="edu.teacherManager.BUMEN.a" /><!-- 部门 --></th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="80px">
					<spring:message code="rp.report.title.dutyinfo" /><!-- 岗位 -->
				</th>
				<th width="80px">
					<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 -->
				</th>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<th width="80px"><spring:message
					code="ess.trans.title.resignReason" /><!-- 离职原因 --></th>
				<th width="80px"><spring:message
					code="hr.viewBadArchives.title.REMARK" /><!-- 备注 --></th>
				</c:if>
				<th width="80px"><spring:message code="hrm.empinfo.UPDATED_BY" /><!-- 变更者 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewWorkExperienceList}" var="item" varStatus="i">
				<tr
					onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ3=${item.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');">
					<td class='td_center' width="30px">${i.count}</td>
					<td width="80px">${item.CPNY_NAME}</td>
					<td width="80px">${item.START_DATE}</td>
					<td width="80px">${item.END_DATE}</td>
					<td width="80px">${item.DEPT_NAME}</td>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td width="80px">${item.DUTY}</td>
					<td width="80px">${item.POSITION}</td>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<td width="80px">${item.LEFT_REASON}</td>
					<td width="80px">${item.REMARK}</td>
					</c:if>
					<td width="80px">[${item.EMPID}]-${item.LOCAL_NAME}
					${item.UPDATED_IP}</td>
					<td width="80px">${item.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</c:if>



<c:if test="${currentIndex eq '4'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,divAjaxDone);">
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;"><spring:message
		code="hrm.recruitManage.Family_information" /><!-- 家庭信息 --></div>
		<c:if test="${employee_type.ACTIVITY ne '1'}">
	<div style="float: right; height: 25px; line-height: 25px;"><a
		class="w_button"
		onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ4=-1&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');"><span><spring:message
		code="button.add" /><!-- 添加 --></span></a> <a class="w_button"
		href="/hrm/recruitManage/deleteRecruitInfo?SEQ=${recruitInfo.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}"
		target="ajaxTodo" callback="divAjaxDone" title="<spring:message code='hrm.alert.empinfo.Sure.delete' />"><!-- 确定要删除吗？ --><span><spring:message
		code="button.delete" /><!-- 删除 --></span></a> <a class="w_button"
		id="viewAddrecruitInfo_save${currentIndex}"><span><spring:message
		code="button.sys.affirm.save" /><!-- 保存--></span></a></div>
		</c:if>
	</div>
	<div>
	<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr>
			<td>
			<table class="user_table" width="100%">
				<tr style="display:none">
					<td width="15%" class="td_title" colspan="4">
						<ait:SelectSyCodeByCpnyID
						id="employee_type"
						name="EMP_TYPE_CODE" selected="${employee_type.EMP_TYPE_CODE}"
						parentNo="13864"
						limit="all" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.name" /><!-- 姓名 --></td>
					<td width="85%" class="td_type" colspan="3"><input type="text"
						id="FAM_NAME" name="FAM_NAME" value="${recruitInfo.FAM_NAME}"
						class="required" size="25" /> <input type="hidden" id="SEQ"
						name="SEQ" value="${recruitInfo.SEQ}" /> <input type="hidden"
						id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID}" /> <input
						type="hidden" id="currentIndex" name="currentIndex"
						value="${currentIndex}" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!-- 关系 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						name="FAM_TYPE_CODE" selected="${recruitInfo.FAM_TYPE_CODE}"
						parentNo="950" limit="all" /></td> 
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.SEXCODE" /><!-- 性别 --></td>
					<td width="35%" class="td_type"><ait:SelectSyCodeByCpnyID
						name="GENDER" id="GENDER" selected="${recruitInfo.GENDER}"
						parentNo="1324" limit="all" /></td> 
				</tr>
				<tr>
					<td width="15%" class="td_title">
						<spring:message code="hrm.empinfo.FAM_BORNDATE" /> <!-- 出生日期 --> 
					</td>
					<td width="35%" class="td_type"><input type="text"
						id="FAM_BORNDATE" name="FAM_BORNDATE" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
						value="${recruitInfo.FAM_BORNDATE }" /></td>
					<td class="td_title" width="4%"><!-- 紧急联络处 -->
						<spring:message code="hrm.recruitManage.EMERGENCY_CONTACT_YN" /></td>
					<td class="td_type"  width="25%">
						<input type="checkbox" <c:if test="${recruitInfo.EMERGENCY_CONTACT_YN eq 'Y'}"> checked='checked' </c:if> id="emergency_yn" onclick="changeoffice()">
						<input type="hidden" id="EMERGENCY_CONTACT_YN" name="EMERGENCY_CONTACT_YN" value="">
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hr.viewRelation.title.FAM_ADDRESS" /><!-- 地址 --></td>
					<td width="85%" class="td_type" colspan="3"><input type="text"
						id="FAM_ADDRESS" name="FAM_ADDRESS" value="${recruitInfo.FAM_ADDRESS}"
						size="100" /></td>
				</tr>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.CELLPHONE" /><!-- 手机号码 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="FAM_PHONE" name="FAM_PHONE" value="${recruitInfo.FAM_PHONE}"
						size="25" /></td>
					<td class="td_title" width="15%"><!-- 家人电子邮箱 -->
						<spring:message code="hrm.recruitManage.FAM_EMAIL" /></td>
					<td class="td_type"  width="35%">
						<input type="text" id="FAM_EMAIL" name="FAM_EMAIL" value="${recruitInfo.FAM_EMAIL}" size="25" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.FAM_FAMILY_PHONE" /><!-- 家庭电话 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="FAM_FAMILY_PHONE" name="FAM_FAMILY_PHONE"
						value="${recruitInfo.FAM_FAMILY_PHONE}" size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="sys.basicMaint.title.companyTelPhoneNo" /><!-- 公司电话 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="WORK_PHONE" name="WORK_PHONE" value="${recruitInfo.WORK_PHONE}"
						size="25" /></td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td width="15%" class="td_title">
						<!-- 工作岗位 -->  <spring:message code="hrm.empinfo.WORK_DUTY.Z" /></td>
					<td width="35%" class="td_type"><input type="text"
						id="OCCUPATION" name="OCCUPATION" value="${recruitInfo.OCCUPATION}"
						size="25" /></td>
					<td width="15%" class="td_title"><spring:message
						code="hr.viewWorkInfo.title.CPNY_NAME" /><!-- 工作单位 --></td>
					<td width="35%" class="td_type"><input type="text"
						id="WORK_UNIT" name="WORK_UNIT" value="${recruitInfo.WORK_UNIT}"
						size="25" /></td>
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.REMARK" /><!-- 备注 --></td>
					<td width="85%" class="td_type" colspan="3"><textarea type="text"
						id="REMARK" name="REMARK" style="width: 650px; height: 80px">${recruitInfo.REMARK}</textarea></td>
				</tr>
				
			</table>
			<table class="user_table" width="100%">
				<tr>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATED_BY" /><!-- 变更者 --></td>
					<td width="35%" class="td_type">
					[${recruitInfo.EMPID}]-${recruitInfo.LOCAL_NAME}
					${recruitInfo.UPDATED_IP}</td>
					<td width="15%" class="td_title"><spring:message
						code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></td>
					<td width="35%" class="td_type">${recruitInfo.UPDATE_DATE}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	<div>
	<div style="font: 10px; float: left; height: 20px; line-height: 20px;">List</div>
	<table class="table" width="100%" layoutH="580">
		<thead>
			<tr>
				<th width="30px">No.</th>
				<th width="80px"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!-- 关系 --></th>
				<th width="80px"><spring:message code="hr.viewPersonalInfo.title.SEX" /><!-- 性别 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.FAM_BORNDATE" /> <!-- 出生日期 --> </th>
				<th width="80px"><spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!-- 地址 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.CELLPHONE" /><!-- 手机号码 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.UPDATED_BY" /><!-- 变更者 --></th>
				<th width="80px"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewFamilyList}" var="item" varStatus="i">
				<tr
					onclick="openOnRight('/hrm/recruitManage/viewAddRecruitInfoPanel?SEQ4=${item.SEQ}&PERSON_ID=${PERSON_ID}&currentIndex=${currentIndex}','viewRecruitList_unit');">
					<td class='td_center' width="30px">${i.count}</td>
					<td width="80px">${item.FAM_NAME}</td>
					<td width="80px">${item.FAM_TYPE_CODE_NAME}</td>
					<td width="80px">${item.GENDER_NAME}</td>
					<td width="80px">${item.FAM_BORNDATE}</td>
					<td width="80px">${item.FAM_ADDRESS}</td>
					<td width="80px">${item.FAM_PHONE}</td>
					<td width="80px">[${item.EMPID}]-${item.LOCAL_NAME}
					${item.UPDATED_IP}</td>
					<td width="80px">${item.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</c:if>

<c:if test="${currentIndex eq '5'}">
	<form id="viewAddrecruitInfoForm${currentIndex}" method="post"
		action="/hrm/recruitManage/addRecruitInfo"
		class="pageForm required-validate"
		onsubmit="return validateAddrecruitInfoCallback${currentIndex}(this,navTabAjaxDoneWithForm);">
	<div class="pageContent">
	<div style='float: left;'><img id='orgImage' name='orgImage'
		src='${recruitInfo.PHOTO_PATH}' border=1
		style='width: 170px; height: 226px;'></div>
	<div style='float: left;'><img id='newImage' name='newImage'
		src='/resources/photo/default.jpg' border=0
		style='width: 175px; height: 233px;'></div>
	</div>
	<div class="searchBar" style='border: 0px; margin-top: 20px;'>
	<table class="searchContent">
	<c:if test="${recruitInfo.ACTIVITY ne '1'}">
		<tr>
			<td><spring:message code="hrm.recruitManage.ENCLOSURE" /><!-- 附件： --></td>
			<td><input type="file" id="viewAddrecruitInfo_uploadPhoto"
				name="file" /> <input type="hidden" id="PERSON_ID" name="PERSON_ID"
				value="${recruitInfo.PERSON_ID}" /> <input type="hidden"
				id="currentIndex" name="currentIndex" value="${currentIndex}" /></td>
			<td><a class="w_button" id="viewAddrecruitInfo_upload"><span><spring:message
				code="button.sys.affirm.save" /><!-- 保存 --></span></a></td>
		</tr>
	</c:if>
	</table>
	</div>
	</form>
</c:if>