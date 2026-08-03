<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var zTree;
var demoIframe;
var setting = {
		
	view: {
		dblClickExpand: true,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	check:{
		autoCheckTrigger:false,
		chkboxType:{"Y":"s","N":"s"},
		chkStyle:"checkbox",
		enable:true,
		nocheckInherit:true,
		radionType:"level"
	},
	data: {
		key: {
			checked:"CHECKED",
			name: "DEPTNAME",
			open:"true"
		},
		simpleData: {
			enable:true,
			idKey: "DEPTNO",
			pIdKey: "PARENT_DEPT_NO",
			rootPId: ""
		}
	},
	callback: {
		onCheck: function(treeId, treeNode) { 
		var t =$.fn.zTree.getZTreeObj("deptTree1_updateattendancekeeperdepttree");
		var nodes = t.getCheckedNodes(); 
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				if(i==0){
					 document.getElementById("deptNosEdu").value=nodes[i].DEPTNO;
				}else{
					 document.getElementById("deptNosEdu").value+=","+nodes[i].DEPTNO;
				}
			}
		}else{
			document.getElementById("deptNosEdu").value="";
		}
		
	  }
	}
};

var zNodes;
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",
        url: "/edu/traineducation/getDeptTree?DES_DEPARTMENT=${planManagerInfo.DES_DEPARTMENT}",//请求的action路径  
        error: function () {//请求失败处理函数  
            //请求失败
            alert('<spring:message code="ar.alert.message.viewattendencekeeper.error"/>');  
        },  
        success:function(data){ //请求成功后处理函数。    
       	zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
        }  
   }); 
// 初始调用
$(document).ready(function(){
    //布局
    $("#layout1").ligerLayout({ leftWidth: 180});
	var t = $("#deptTree1_updateattendancekeeperdepttree");
	t = $.fn.zTree.init(t, setting, zNodes);
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("deptNosEdu").value=nodes[i].DEPTNO;
			}else{
				 document.getElementById("deptNosEdu").value+=","+nodes[i].DEPTNO;
			}
		}
	}else{
		document.getElementById("deptNosEdu").value="";
	}
	//课时单位
	var classunit="${planManagerInfo.CLASS_UNIT}";
	if(classunit!=''){
		$('#CLASS_UNIT option[value='+classunit+']').attr('selected','selected');
	}
	//是否需要考试
	/* var isnottest="${planManagerInfo.ISNOT_TEST}";
	if(isnottest!=''){
		$('#ISNOT_TEST option[value='+isnottest+']').attr('selected','selected');
	} */
	//是否可申请
	var isnotapply="${planManagerInfo.ISNOT_APPLY}";
	if(isnotapply!=''){
		$('#ISNOT_APPLY option[value='+isnotapply+']').attr('selected','selected');
	}
	//是否可见
	var budgetshow="${planManagerInfo.BUDGET_SHOW}";
	if(budgetshow=='Y'){
		$('#BUDGET_SHOW').attr('value','Y');
		$('#BUDGET_SHOW').attr('checked','checked');
	}else{
		$('#BUDGET_SHOW').attr('value','N');
		$('#BUDGET_SHOW').removeAttr('checked');
	}
	//是否需要评价
	var isnot_evaluate="${planManagerInfo.ISNOT_EVALUATE}";
	if(isnot_evaluate!='0' && isnot_evaluate!=''){
		var array=isnot_evaluate.split(",");
		for(var i=0;i<array.length;i++){
			$('input[name="EVALUATE"]').each(function(){
				   if($(this).val()==array[i]){
					$(this).attr('checked','checked');
				}
			});
		}
		$('#ISNOT_EVALUATE').attr('value',isnot_evaluate);
	}
	//是否需要培训报告
	/* var isnot_report="${planManagerInfo.ISNOT_REPORT}";
	if(isnot_report=='Y'){
		$('#ISNOT_REPORT option[value="Y"]').attr('selected','selected');
	}else{
		$('#ISNOT_REPORT option[value="N"]').attr('selected','selected');
	}
	//是否需要培训协议
	var isnot_agressment="${planManagerInfo.ISNOT_AGREEMENT}";
	if(isnot_agressment=='Y'){
		$('#ISNOT_AGREEMENT option[value="Y"]').attr('selected','selected');
	}else{
		$('#ISNOT_AGREEMENT option[value="N"]').attr('selected','selected');
	} */
	
	var zhEmployee=  $('#zhEmployee_hidden').val();
	//var empidEmployeeName=  $('#empidEmployeeName').val();
	//var empidEmployee=  $('#empidEmployee').val();
	var arr1 = zhEmployee.split(",");
	var trss2 = "";
	if(zhEmployee != "" && zhEmployee != null)
	$.each(arr1,function(n,value) { 
       trss2 += "<span id='empid_"+n+"'>"+ value +
	        	    "<img  src='/resources/images/train003.gif' alt='<spring:message code="edu.planManager.QINGCHU.a"/>' onclick='deleteLocalNameU("+n+")'></span>";//清除
    });
    $('#zhEmployee').html(trss2);
});

function deleteLocalNameU(id){
  document.getElementById('empid_'+id).innerHTML=''; 
  var empidEmployee =  $('#empidEmployee');
  var empidEmployeeName =  $('#empidEmployeeName');
  var arr1 = empidEmployeeName.val().split(",");
  arr1.splice($.inArray(name,arr1[id]),1);
  var trs1="";
  var  trss1 = "";
  for(var i=0;i<arr1.length;i++){
       trss1 += arr1[i]+','
  }
  trs1 = trss1.substr(0,trss1.length-1);
  $('#empidEmployeeName').val(trs1);
  //empid
  var arr2 = empidEmployee.val().split(",");
  arr2.splice($.inArray(name,arr2[id]),1);
  var trs2="";
  var  trss2 = "";
  for(var i=0;i<arr2.length;i++){
       trss2 += arr2[i]+','
  }
  trs2 = trss2.substr(0,trss2.length-1);
  $('#empidEmployee').val(trs2);
}

function deleteLocalName(id){
  var LOCAL_NAME='';
  //通过empid获取local_name
  if(id != null){
   $.ajax({
		cache: false,
		type: 'post',
		async:false,
		url: "/ess/infoApplyAttendance/getTrainLocalName",
		data: [{ name: 'EMPID', value: id }],
		dataType:"json",
		success: function(data) {
		 LOCAL_NAME = data.LOCAL_NAME;
		}
	}); 
   }
  var zhEmployee = $('#zhEmployee');
  var empidEmployee =  $('#empidEmployee');
  var empidEmployeeName =  $('#empidEmployeeName');
  document.getElementById('empid_'+id).innerHTML='';  
  var arr1 = empidEmployeeName.val().split(",");
  arr1.splice($.inArray(name,arr1),1);
  var trs1="";
  var  trss1 = "";
  for(var i=0;i<arr1.length;i++){
       trss1 += arr1[i]+','
  }
  trs1 = trss1.substr(0,trss1.length-1);
  $('#empidEmployeeName').val(trs1);
  var arr2 = empidEmployee.val().split(",");
  arr2.splice($.inArray(id,arr2),1);
  var trs2="";
  var  trss2 = "";
  $.each(arr2,function(n,value) { 
       trss2 += value+','
  }); 
  trs2 = trss2.substr(0,trss2.length-1);
  $('#empidEmployee').val(trs2);
}


function zhiding(){
	var deptno=$('#deptNosEdu').val();
	var empid=$('#empidEmployee').val();
	if(deptno!=''){
		$('#zhiding').attr('href','/edu/traineducation/desEmployee?DEPTNO='+deptno+'&EMPID='+empid);
	}else{
		$('#zhiding').attr('href','/edu/traineducation/desEmployee?EMPID='+empid);
	}
} 
function budgetShow(){
	var ischeck=$('#BUDGET_SHOW').prop('checked');
	if(ischeck==true){
		$('#BUDGET_SHOW').attr('value','Y');
	}else{
		$('#BUDGET_SHOW').attr('value','N');
	}
}
function teacher(){
	var teachername=$('#TEACHER_NAME').val();
	var teacherempid=$('#TEACHER_EMPID').val();
	if(teachername!=''){
		$('#teacher').attr('href','/edu/traineducation/teacherSearch?TEACHER_NAME='+teachername+'&teacherEmpid'+teacherempid);
	}else{
		$('#teacher').attr('href','/edu/traineducation/teacherSearch');
	}
}
function pingjia1(){
	var array="";
	$('input[name="EVALUATE"]').each(function(){
		   if($(this).attr("checked")){
			array+=$(this).val()+",";
		}
	});
	array=array.substring(0,array.length-1);
	$('#ISNOT_EVALUATE').attr('value',array);
}

/**
 * 删除附件
 */
function deleteAttListPlan(id,val,callback){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']").each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}

	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
function planImport(){
	$("#importExcelDialogPlan").attr('href','/pa/excelImport/importExcelData?importFunName=/importTrainPlan?plan_no=${planManagerInfo.PLAN_NO }');
	$("#importExcelDialogPlan").click();
}
</script>
<div class="pageContent" layoutH="10" id="planManagerInfo">
	<form method="post" action="/edu/traineducation/updatePlanManager" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="PLAN_NO" id="PLAN_NO" value="${planManagerInfo.PLAN_NO }">
		<input type="hidden" name="PERIOD_TIME" id="PERIOD_TIME" value="${planManagerInfo.PERIOD_TIME }">
		<a id="importExcelDialogPlan"  href="" target="dialog" rel="plancourse" mask="true" width="500" height="200"></a>
		<div class="pageFormContent nowrap">
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称
		--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_TYPE_CODE_NAME }&nbsp&nbsp${planManagerInfo.COURSE_NUMBER }&nbsp&nbsp${planManagerInfo.COURSE_NAME_CODE }</span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIHUAKAISHISHIJIAN.a"/><!--计划开始时间--></td>
		<td class="td_type"  width="20%" >
		<input name="PLAN_STARTDATE" class="required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" id="PLAN_STARTDATE" type="text" value="${planManagerInfo.PLAN_STARTDATE }"/>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIHUAJIESHUSHIJIAN.a"/><!--计划结束时间--></td>
		<td class="td_type"  width="20%" >
		<input name="PLAN_ENDDATE" class="required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" id="PLAN_ENDDATE" type="text" value="${planManagerInfo.PLAN_ENDDATE }"/>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="CLASS_HOUR" id="CLASS_HOUR" value="${planManagerInfo.CLASS_HOUR }" class="required" min="0"/>
					<select name="CLASS_UNIT" id="CLASS_UNIT">
					<option value="2"><spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></option>
					<option value="0"><spring:message code="display.mutual.month"/><!--月--></option>
					<option value="1"><spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></option>
					</select>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="hrm.empinfo.Training_form"/><!--培训形式
			--></td>
		<td class="td_type"  width="20%" >
		<ait:SelectSyCodeByCpnyID name="TRAIN_FORM_CODE" id="TRAIN_FORM_CODE"
                    parentNo="14014493" cnpyID="${defaultCpny}" selected="${planManagerInfo.TRAIN_FORM_CODE }" />
		</td>
		</tr>
		
		<!-- <tr>
		<td class="td_title" width="1%">是否需要考试</td>
		<td class="td_type"  width="20%" >
		<select name="ISNOT_TEST" id="ISNOT_TEST">
					<option value="Y">是</option>
					<option value="N">否</option>
					</select>
		</td>
		</tr> -->
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUKESHENQING.a"/><!--是否可申请
			--></td>
		<td class="td_type"  width="20%" >
		<select name="ISNOT_APPLY" id="ISNOT_APPLY">
					<option value="Y"><spring:message code="ar.viewcycle.content.yes"/><!--是
			--></option>
					<option value="N"><spring:message code="ar.viewcycle.content.no"/><!--否
			--></option>
					</select>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.ZHIDINGBUMEN.a"/><!--指定部门
			--></td>
		<td class="td_type"  width="20%" >
		<ul id="deptTree1_updateattendancekeeperdepttree" class="ztree"></ul>
		         <input type="hidden" name="DES_DEPARTMENT" id="deptNosEdu" value="${planManagerInfo.DES_DEPARTMENT}" />
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员
			--></td>
		<td class="td_type"  width="1%" >
		<a class="buttonActive" href="/edu/traineducation/desEmployee" id="zhiding" onclick="zhiding()" rel="zhiding" target="dialog" mask="true" width="600" height="400" >
		<span><spring:message code="edu.planManager.TIANJIAZHIDINGRENYUAN.a"/><!--添加指定人员
			--></span></a>
		<span id="zhEmployee"></span>
		<input type="hidden" id="zhEmployee_hidden" value="${DES_EMPLOYEE_NAME}">
		<input type="hidden" name="empidEmployeeName" id="empidEmployeeName" value="${DES_EMPLOYEE_NAME }">
		<input type="hidden" name="empidEmployee" id="empidEmployee" value="${DES_EMPLOYEE}">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.YUSUANFEIYONG.a"/><!--预算费用
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="BUDGET" id="BUDGET" value="${planManagerInfo.BUDGET }" min="0"/>
				<input type="checkbox" name="BUDGET_SHOW" id="BUDGET_SHOW" value="N" onclick="budgetShow()"/><spring:message code="edu.planManager.SHIFOUKEJIAN.a"/><!--是否可见
			-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.ZHUGUANBUMEN.a"/><!--主管部门
			--></td>
		<td class="td_type"  width="20%" >
		<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME==null }">
		<input type="text"  name="DEPART_MANA_CODE_NAME" id="DEPART_MANA_CODE_PLAN" value="${planManagerInfo.DEPART_MANA_CODE }"> 
		<input  name="DEPART_MANA_CODE" type="hidden" value="" syslong="DEPART_MANA_CODE">
		</c:if>
		<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME!=null }">
		<input type="text" id="DEPART_MANA_CODE" name="DEPART_MANA_CODE_NAME" value="${planManagerInfo.DEPART_MANA_CODE_NAME }">
		<input name="DEPART_MANA_CODE" type="hidden" value="" syslong="DEPART_MANA_CODE">
		</c:if>
	    <ait:deptTreeIcon name="DEPART_MANA_CODE" cpnyId="${defaultCpny}" limit="super" id="DEPART_MANA_CODE" selected="" />
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIANGSHI.a"/><!--讲师
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="TEACHER_NAME" id="TEACHER_NAME" value="${TEACHER_NAME }">
		<a class="buttonActive" href="/edu/traineducation/teacherSearch" id="teacher" onclick="teacher()" rel="sou" target="dialog" mask="true" width="600" height="400" >
		<span><spring:message code="ar.viewempcalender.title.search"/><!--搜索
			--></span></a>
		<input type="hidden" name="TEACHER_EMPID" id="TEACHER_EMPID" value="${TEACHER_EMPID }">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.eduRm"/><!--培训地点
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="TRAIN_ADDRESS" id="TRAIN_ADDRESS" value="${planManagerInfo.TRAIN_ADDRESS }">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNRENSHU.a"/><!--培训人数
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text" class="required" name="TRAIN_PERSON_COUNT" id="TRAIN_PERSON_COUNT" value="${planManagerInfo.TRAIN_PERSON_COUNT }" min="0">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注
			--></td>
		<td class="td_type"  width="20%" >
		<input type="text"  name="TRAIN_PERSON_REMARK" id="TRAIN_PERSON_REMARK" value="${planManagerInfo.TRAIN_PERSON_REMARK }">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUXUYAOPINGJIA.a"/><!--是否需要评价
			--></td>
		<td class="td_type"  width="20%" >
		<input type="checkbox" name="EVALUATE" value="1" onclick="pingjia1()"><spring:message code="edu.planManager.XUEYUANPINGJIA.a"/><!--学员评价
			-->
		<input type="checkbox" name="EVALUATE" value="2" onclick="pingjia1()"><spring:message code="edu.planManager.JIANGSHIPINGJIA.a"/><!--讲师评价
			-->
		<input type="checkbox" name="EVALUATE" value="3" onclick="pingjia1()"><spring:message code="edu.planManager.PEIXUNPINGJIA.a"/><!--培训评价
			-->
		<input type="hidden" name="ISNOT_EVALUATE" id="ISNOT_EVALUATE" value="0">
		</td>
		</tr>
		<!-- <tr>
		<td class="td_title" width="1%">是否需要培训报告</td>
		<td class="td_type"  width="20%" >
		<select id="ISNOT_REPORT" name="ISNOT_REPORT">
		<option value="Y">是</option>
		<option value="N">否</option>
		</select>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%">是否需要培训协议</td>
		<td class="td_type"  width="20%" >
		<select id="ISNOT_AGREEMENT" name="ISNOT_AGREEMENT">
		<option value="Y">是</option>
		<option value="N">否</option>
		</select>
		</td>
		</tr> -->
		<tr>
		<td class="td_title" width="1%"><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件
			--></td>
		<td class="td_type"  width="20%" >
		<a class="w_button" href="#" onclick="uploadAttDialog_new('planManagerInfo','/edu/traineducation/planManagerInfo?PERSON_ID=${PERSON_ID}$PLAN_NO=${planManagerInfo.PLAN_NO }','${planManagerInfo.PLAN_NO }','eduPlanManager','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="button.add"/><!--添加
			--></span></a>
		<a class="w_button" href="#" onclick="deleteAttListPlan('planManagerInfo','/edu/traineducation/planManagerInfo?PERSON_ID=${PERSON_ID}&PLAN_NO=${planManagerInfo.PLAN_NO }',divAjaxDone)"><span><spring:message code="button.delete"/><!--删除
			--></span></a>
		<c:forEach items="${planManagerInfo.fileList}" var="item" varStatus="i">
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a>
	    </c:forEach>
	    
		</td>
		
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.DAORUKECHENGBIAO.a"/><!--导入课程表
			--></td>
		<td class="td_type"  width="20%" >
		<a class="buttonActive" href="#" onclick="planImport()">
							<span><spring:message code="ar.addempshift.title.excelimport"/><!--Excel导入
			--></span>
		</a>
		<a class="buttonActive" href="/edu/traineducation/planImportDemoLoad?flag=load" >
							<span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载
			--></span>
		</a>
		<a  style="padding-left: 200px;color:blue;"  id="course_id_u"  href="/edu/traineducation/queryCourseSyllabus?PLAN_NO=${planManagerInfo.PLAN_NO }" target="dialog" mask="true"  rel="course_id_u" width="600" height="400"><spring:message code="edu.planManager.KECHENGBIAODAORUQINGKUANG.a"/><!--课程表导入情况
			--></a>
		</td>
		</tr>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
