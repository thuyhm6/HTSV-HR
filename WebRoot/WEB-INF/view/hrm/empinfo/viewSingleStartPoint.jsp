<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(function(){
	codeRelation('${expInfo.TRANS_CODE}','TRANS_REASON','${expInfo.TRANS_REASON}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	codeRelation('${expInfo.POST_FAMILY}','POST_GRADE_NO','${expInfo.POST_GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	var cpnyid="${LoginUser.cpnyId}";

	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	$("#POST_GRADE_NO",navTab.getCurrentPanel()).change(function(){ 
			var a="";
			var POST_GRADE_NO=$("#POST_GRADE_NO",navTab.getCurrentPanel()).attr("value");
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
					$('#PAY_STEP_NO').html(s);
				}
				}
			 });
	});
	</c:if>
	

	transCodeChange(1);
	/**********************************
		123314	试用期转正
		123315	实习期转正
		123316	晋升
		123317	主动离职
		123318	转入第三方
		123346	第三方转入
		123347	任职
		123348	解除任职
		123350	复职
		123524	降级
		123525	辞退
		123526	休职
		14015867	入职
		218038	部门调动
	************************************/
	$("#TRANS_CODE",navTab.getCurrentPanel()).click(function(){
		transCodeChange(2);
	});
});

function transCodeChange(flag){
    var transCode = $("#TRANS_CODE",navTab.getCurrentPanel()).val();
   

    changeDisabled();
    //2017.06.08 添加试用期判断  试用期结束日 > 发令日期  员工状态为  试用 或者 实习 （根据员工类型判断） 并加入 flag 第一次进入页面时 走原来的，有改动走2
    var endProDateStr =  $('#END_PROBATION_DATE',navTab.getCurrentPanel()).val();
    var endProDate = new Date(endProDateStr);
    var startDateStr =  $('#START_DATE',navTab.getCurrentPanel()).val();
    var startDate = new Date(startDateStr);
    var endLeftStr =  $('#DATE_LEFT_STR',navTab.getCurrentPanel()).val();
    var endLeft = new Date(endLeftStr);
	if(transCode == '400414'){//应届生
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		addDisabled("END_PROBATION_DATE");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			////changeEmpOffice("14891");
		}
	}else if(transCode == '400435'){//经验入社
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		addDisabled("EMPLOYEE_BELONG");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			////changeEmpOffice("14891");
		}
	}else if(transCode == '400429'){//招聘专家
		addDisabled("DEPTNO");
		addDisabled("EMPLOYEE_BELONG");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '218038'){//部门调动
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123524'){//降级
		addDisabled("EMPLOYEE_BELONG");
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123317'){//主动离职
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		if (flag == '1'){
			changeEmpOffice("OLD");
		}else{
			changeEmpOffice("1375");
		}
	}else if(transCode == '123525'){//辞退
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		if (flag == '1'){
			changeEmpOffice("OLD");
		}else{
			changeEmpOffice("1375");
		}
	}else if(transCode == '123318'){//转入第三方
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("EMPLOYEE_BELONG");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endProDate || startDate <= endLeft){
				//changeEmpOffice("1374");
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123346'){//第三方转入
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("EMPLOYEE_BELONG");
		<c:if test = "${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");		
		</c:if>
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123347'){//任职
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123348'){//解除任职
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endProDate || startDate <= endLeft){
				//changeEmpOffice("1374");
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123526'){//休职
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					////changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == '123350'||transCode == '80000265'){//复职
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE"); 
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
				//	//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == "80000030"){//号俸变更
		addDisabled("POST_GRADE_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endProDate || startDate <= endLeft){
				//changeEmpOffice("1374");
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == "80000137" || transCode=="80000206"){//信息调整 兼职转正式后加
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		addDisabled("EMPLOYEE_BELONG");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					////changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == "14015867"){//信息调整
		addDisabled("EMP_TYPE_CODE");
		addDisabled("EMP_OFFICE");
		addDisabled("DEPTNO");
		addDisabled("EMPLOYEE_BELONG");
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		addDisabled("POSITION_NO");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
					//changeEmpOffice("1374");
				}else{
					////changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}else if(transCode == "80000136"){//岗位调动
		addDisabled("POST_GRADE_NO");
		addDisabled("JOB_TYPE");
		addDisabled("DUTY_NO");
		addDisabled("POST_FAMILY");
		addDisabled("WORK_HOUR_TYPE");
		addDisabled("WAGE_TYPE");
		addDisabled("MAIN_BUSINESS");
		if(flag == '1'){
			changeEmpOffice("OLD");
		}else{
			if (startDate <= endLeft){
				if (startDate <= endProDate){
				//	changeEmpOffice("1374");
				}else{
					//changeEmpOffice("14891");
				}
			}else{
				changeEmpOffice("OLD");
			}
		}
	}
}
//全部信息的disabled增加方法
function addDisabled(type){
	if(type == 'TRANS_CODE'){
		$('#TRANS_CODE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'DEPTNO'){
		$('#deptDiv',navTab.getCurrentPanel()).css('display','none');
		$('#deptTag',navTab.getCurrentPanel()).css('display','');
	}else if(type == 'EMP_TYPE_CODE'){
		$('#EMP_TYPE_CODE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'EMPLOYEE_BELONG'){
		$('#EMPLOYEE_BELONG',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'EMP_OFFICE'){
		$('#EMP_OFFICE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'END_PROBATION_DATE'){
		$('#END_PROBATION_DATE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'POST_GRADE_NO'){
		$('#POST_GRADE_NO',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'JOB_TYPE'){
		$('#JOB_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'DUTY_NO'){
		$('#DUTY_NO',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'POST_FAMILY'){
		$('#POST_FAMILY',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'WORK_HOUR_TYPE'){
		$('#WORK_HOUR_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'PAY_STEP_NO'){
		$('#PAY_STEP_NO',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'WAGE_TYPE'){
		$('#WAGE_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'MAIN_BUSINESS'){
		$('#MAIN_BUSINESS',navTab.getCurrentPanel()).attr('disabled',false);
	}else if(type == 'POSITION_NO'){
		$('#POSITION_NO',navTab.getCurrentPanel()).attr('disabled',false);
	}
}

function changeEmpOffice1(value){
	if(value == '1375'){
		$("#EMP_OFFICE",navTab.getCurrentPanel()).val("1375");
	}else if(value == '1374'){
		var empTypeCode = $("#EMP_TYPE_CODE",navTab.getCurrentPanel()).val();
		if(empTypeCode =='10416'){
			$("#EMP_OFFICE",navTab.getCurrentPanel()).val("1373");
		}else{
			$("#EMP_OFFICE",navTab.getCurrentPanel()).val("1374");
		}
	}else if(value == '14891'){
		$("#EMP_OFFICE",navTab.getCurrentPanel()).val("14891");
	}else{
		$("#EMP_OFFICE",navTab.getCurrentPanel()).val($("#empOfficeOld",navTab.getCurrentPanel()).val());
	}
}
function validateAddPointInfoCallback(form,callback) {
    //查询发令
    var transCode=$('#TRANS_CODE',navTab.getCurrentPanel()).val();	
	var flag = $("#flag",navTab.getCurrentPanel()).val();

	/*if(flag == '0'){
		alertMsg.warn("发令不可修改，请先点击添加");
		return false;
	}

	var joinFlag = $("#joinFlag",navTab.getCurrentPanel()).val();
	if(joinFlag == '1'){
		alertMsg.warn("入职发令不可修改");
		return false;
	}*/
/* 	 var transCode = $("#TRANS_CODE",navTab.getCurrentPanel()).val();
	 var personId = $("#PERSON_ID",navTab.getCurrentPanel()).val();
	 var personId1 = $("#MANAGER_EMP_ID",navTab.getCurrentPanel()).val();
		<c:if test = "${LoginUser.cpnyId eq 'HTSV'}">
			if(personId == personId1){
				if(transCode == '123317' || transCode == '123348'){
					alert("此人为部门长");
				}
			}
		</c:if> */
	var $form = $("#" + form,navTab.getCurrentPanel());
	if (!$form.valid()) {
		return false;
	}
	
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>",//确定要保存吗？
  		{okCall:function(){
  		  	delDisabled();
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  		});
  	}});
	return false;
}

function validateDeleteResumeInfoCallback(form,callback) {
	var cpnyid="${LoginUser.cpnyId}";

	var currentSeq = $("#SEQ",navTab.getCurrentPanel()).val();
	var firstSeq = $(".list tbody tr[sysIndex='0']",navTab.getCurrentPanel()).attr("sysSeq");

	if(currentSeq != firstSeq && cpnyid == 'SPC_SH'){
		alertMsg.warn("<spring:message code='hrm.empinfo.ONLY_DELETE_NEW_ORDER.Z' />");//只能删除最新的发令
		return false;
	}
	var joinFlag = $("#joinFlag",navTab.getCurrentPanel()).val();
	if(joinFlag == '1'){
		alertMsg.warn("<spring:message code='hrm.empinfo.RUZHI_ORDER_NOT_DELETE.Z' />");//入职发令不可删除
		return false;
	}
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>",//确定要删除吗？
		{okCall:function(){
			$.ajax({
		  		type: form.method || 'POST',
		  		url:'/hrm/empinfo/deleteStartPoint',
		  		data:$form.serializeArray(),
		  		dataType:"json",
		  		cache: false,
		  		success: callback || DWZ.ajaxDone,
		  		error: DWZ.ajaxError
		  	});
	}});
	return false;
}

//全部信息的disabled移除方法
function delDisabled(){
	$('#EMPLOYEE_BELONG',navTab.getCurrentPanel()).attr('disabled',false);
	$('#EMP_OFFICE',navTab.getCurrentPanel()).attr('disabled',false);
	$('#END_PROBATION_DATE',navTab.getCurrentPanel()).attr('disabled',false);
	$('#JOB_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
	$('#DUTY_NO',navTab.getCurrentPanel()).attr('disabled',false);
	$('#WORK_HOUR_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
	$('#WAGE_TYPE',navTab.getCurrentPanel()).attr('disabled',false);
}

function changeDisabled(){
	$('#EMPLOYEE_BELONG',navTab.getCurrentPanel()).attr('disabled',true);
	$('#EMP_OFFICE',navTab.getCurrentPanel()).attr('disabled',true);
	$('#END_PROBATION_DATE',navTab.getCurrentPanel()).attr('disabled',true);
	$('#JOB_TYPE',navTab.getCurrentPanel()).attr('disabled',true);
	$('#DUTY_NO',navTab.getCurrentPanel()).attr('disabled',true);
	$('#WORK_HOUR_TYPE',navTab.getCurrentPanel()).attr('disabled',true);
	$('#WAGE_TYPE',navTab.getCurrentPanel()).attr('disabled',true);
}
function finaldegree() {
	var check = $('#final',navTab.getCurrentPanel()).prop('checked');
	if (check == true) {
		$('#NEVER_EMPLOY',navTab.getCurrentPanel()).attr('value', 'Y');
	} else {
		$('#NEVER_EMPLOY',navTab.getCurrentPanel()).attr('value', 'N');
	}
}
function isEndProDate(){
	transCodeChange(2);
}
function falingdiff(value,num){
	codeRelation(value,'TRANS_REASON','${expInfo.TRANS_REASON}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	falingpanduan(value,num);
}
function falingzhiqun(value,num){
	codeRelation(value,'POST_GRADE_NO','${expInfo.POST_GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
function falingpanduan(value,num){
	var cpnyid="${defaultCpny}";

	if(value=="14013966" || value=="14013965" || value=="400440" || value=="400441"){//主动离职/被动退职/合同结束/解除劳动合同
		$("#EMP_OFFICE",navTab.getCurrentPanel()).val("15120");
	}else{
		$("#EMP_OFFICE",navTab.getCurrentPanel()).val("15119");
	}
}
//附加信息全部隐藏
function delDiv(){
	$('#div2').attr('style','display:none');
	$('#div3').attr('style','display:none');
	$('#div4').attr('style','display:none');
	$('#div5').attr('style','display:none');
	
	
}
</script>
	<div>
		<form id="editStartPoint" method="post" action="/hrm/empinfo/editStartPoint" class="pageForm required-validate" 
			onsubmit="return validateAddPointInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							 <table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="td_title" width="10%"><spring:message
										code="hrm.empinfo.starter_START_DATE" /><!-- 发令日期 --></td>
									<td class="td_type"  width="90%" colspan='3' id="minglingriqi">
										<input type="hidden" id="SEQ" name="SEQ" value="${expInfo.SEQ }">
										<input type="hidden" name="PERSON_ID" value="${expInfo.PERSON_ID}">
										<input type="hidden" name="DATE_LEFT_STR" value="${expInfo.DATE_LEFT}" id="DATE_LEFT_STR">
										<input name="START_DATE"  id="START_DATE" class="required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en',onpicked:isEndProDate})"  value="${expInfo.START_DATE}"/>
										<c:if test="${empty expInfo.SEQ}">
											<input type="hidden" id="flag" value="1">
										</c:if>
										<c:if test="${not empty expInfo.SEQ}">
											<input type="hidden" id="flag" value="0">
										</c:if>
										
										<c:if test="${expInfo.TRANS_CODE eq '14015867'}">
											<input type="hidden" id="joinFlag" value="1">
										</c:if>
										<c:if test="${expInfo.TRANS_CODE ne '14015867'}">
											<input type="hidden" id="joinFlag" value="0">
										</c:if>
									</td>
				               </tr>
				               <tr>
				               		<td class="td_title" width="10%"><spring:message
										code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 --></td>
									<td class="td_type"  width="40%">
										<c:if test="${expInfo.TRANS_CODE eq '400414' || expInfo.TRANS_CODE eq '400429' || expInfo.TRANS_CODE eq '400435'}">
											<input type="hidden" name="TRANS_CODE" value="${expInfo.TRANS_CODE}"></input>${expInfo.TRANS_CODE_NAME}
										</c:if>
										<c:if test="${expInfo.TRANS_CODE ne '400414' && expInfo.TRANS_CODE ne '400429' && expInfo.TRANS_CODE ne '400435'}">
											<ait:SelectSyCodeByCpnyID name="TRANS_CODE" selected="${expInfo.TRANS_CODE}" parentNo="14013956" onChangeName="falingdiff(this.value,'1');"
									   	 		limit="all" />
										</c:if>
									</td>
									<td class="td_title" width="10%"><spring:message
										code="hrm.empinfo.TRANS_REASON" /><!-- 发令原因 --></td>
									<td class="td_type"  width="40%">
										<!-- <ait:SelectSyCodeByCpnyID name="TRANS_RESOURCE" selected="${expInfo.TRANS_RESOURCE}" parentNo="210464" limit="all"/> -->
										<select name="TRANS_REASON" id="TRANS_REASON" onchange="yuanyin()" />
										<input type="hidden" id="EMP_OFFICE" name="EMP_OFFICE" value="${expInfo.EMP_OFFICE}" />
									</td>
				               </tr>
				               <tr>
				               		<td class="td_title" width="10%"><spring:message
										code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
									<td id="canwrite" class="td_type"  width="40%" >
										<div id="deptTag">
											<ait:deptList name="DEPTNO" limit="super" id="viewSingleStartPoint_deptList" parameter="WORK_AREA" onClickName='${expInfo.MAIN_BUSINESS}'/>
											<ait:deptTreeIcon name="DEPTNO" limit="super" id="viewSingleStartPoint_deptList" selected="${expInfo.DEPTNO}" parameter="WORK_AREA" onClickName='${expInfo.MAIN_BUSINESS}'/>
										</div>
										<div id="deptDiv" style="display:none">
											${expInfo.DEPTNO_NAME}
										</div>
									</td>
									<td class="td_title"><spring:message code="sys.postManage.title.position" /><!-- Vị trí --></td>
									<td class="td_type">
										<ait:SelectSyCodeByCpnyID name="POSITION" selected="${expInfo.POSITION}" parentNo="14014049"  limit="all"/>
									</td>
							   </tr><!--
							   <tr>
									<td class="td_title"><spring:message
										code="hrm.empinfo.EMP_OFFICE_NAME" /> 员工状态 </td>
									<td class="td_type"><ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    		parentNo="1372" selected="${expInfo.EMP_OFFICE}" limit="all" />
                                    		<input type="hidden" id="empOfficeOld" value="${expInfo.EMP_OFFICE}"/>
                                    </td>
									<td class="td_title"><spring:message
										code="hrm.empinfo.END_PROBATION_DATE" /> 试用结束日期</td>
									<td class="td_type">
										<input type="text" id="END_PROBATION_DATE" name="END_PROBATION_DATE" class="Wdate"
											onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${expInfo.END_PROBATION_DATE }" />
									</td>
							   </tr>
				               -->
				               <tr>
				               		<td class="td_title"><spring:message
										code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
									<td class="td_type">
										<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${expInfo.POST_FAMILY}" onChangeName="falingzhiqun(this.value,'1');"
										parentNo="14015812"  limit="all"/>
									</td>
									<td class="td_title"><spring:message
										code="hrm.contract.Rank" /><!-- 职级 --></td>
									<td class="td_type">
										<select name="POST_GRADE_NO" id="POST_GRADE_NO"></select>
									</td>
							   </tr>
				               <tr>
									<td class="td_title"><spring:message
										code="hrm.contract.POSITION_NO" /><!-- 职责 --></td>
									<td class="td_type">
										<ait:SelectSyCodeByCpnyID 
										name="POSITION_NO" selected="${expInfo.POSITION_NO}"
										parentNo="14014036"
										limit="all" />
									</td>
									 <td class="td_title" width="10%"><spring:message
										code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 --></td>
									<td class="td_type" width="40%">
								   	 	<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    		parentNo="13864" selected="${expInfo.EMP_TYPE_CODE}" limit="all" />
									</td>
							   </tr>
							   <tr>
									<td class="td_title"><spring:message
										code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 --></td>
									<td class="td_type">
										<ait:SelectSyCodeByCpnyID
										name="MAIN_BUSINESS" selected="${expInfo.MAIN_BUSINESS}"
										parentNo="14016056"
										limit="all" />
									</td>
									<td class="td_title"><spring:message
										code="hrm.empinfo.COST_CENTER_NAME_LOCAL" /><!-- 成本中心 --></td>
									<td class="td_type">
										<ait:SelectOrgInfo name="COST_CENTER" orgType="COST_CENTER" selected="${expInfo.COST_CENTER}"/>
									</td>
							  </tr>
							  <c:if test="${LoginUser.cpnyId eq 'HAE'}">
							   <tr>
									<td class="td_title"><spring:message
										code="hrm.recruitManage.NIANZI_DENGJI.Z" /><!-- 年薪等级 --></td>
									<td class="td_type">
										<select id="PAY_STEP_NO" name="PAY_STEP_NO" >
											<option>${expInfo.PAY_STEP_NAME}</option>
										</select>
									</td>
									<td class="td_title"></td>
									<td class="td_type"></td>
							  </tr>
							 </c:if> 
							 </table>
							 <table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
							   <tr>
							   
							  	<td id="REMARK1" class="td_title" width="10%"><spring:message code="hrm.empinfo.REMARK" /><!-- 备注 --></td>
								<td width="90%" class="td_type">
									<textarea id="REMARK" name="REMARK" style="width:600px;height:80px">${expInfo.REMARK}</textarea>
								</td>
							  </tr>
							</table>
							 <table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
							    	<td class="td_title" width="10%"><spring:message
										code="org.title.UPDATED_IP" /><!-- 变更者 --></td>
									<td class="td_type"  width="40%" >
									${expInfo.UPDATED_BY }
									</td>
									<td class="td_title" width="10%"><spring:message
										code="org.title.UPDATE_DATE" /><!-- 变更时间 --></td>
									<td class="td_type"  width="40%" >
									${expInfo.UPDATE_DATE }
									</td>
								</tr>	
							</table>	
						</td>
					</tr>
				</table>	
			</div>
	  	</form>	
	</div>