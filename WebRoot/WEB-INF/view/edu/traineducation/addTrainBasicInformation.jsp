<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

var zTree;
var demoIframe;
var setting = {

	view : {
		dblClickExpand : true,
		showLine : true,
		selectedMulti : false,
		expandSpeed : "fast"
	},
	check : {
		autoCheckTrigger : false,
		chkboxType : {
			"Y" : "s",
			"N" : "s"
		},
		chkStyle : "checkbox",
		enable : true,
		nocheckInherit : true,
		radionType : "level"
	},
	data : {
		key : {
			checked : "CHECKED",
			name : "DEPTNAME",
			open : "true"
		},
		simpleData : {
			enable : true,
			idKey : "DEPTNO",
			pIdKey : "PARENT_DEPT_NO",
			rootPId : ""
		}
	},
	callback : {
		onCheck : function(treeId, treeNode) {
			var t = $.fn.zTree
					.getZTreeObj("deptTree1_updateattendancekeeperdepttree");
			var nodes = t.getCheckedNodes();
			if (nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					if (i == 0) {
						document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
					} else {
						document.getElementById("deptNosEdu").value += ","
								+ nodes[i].DEPTNO;
					}
				}
			} else {
				document.getElementById("deptNosEdu").value = "";
			}

		}
	}
};

var zNodes;
$
		.ajax( {
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "/ar/attendanceSettings/getDeptTree?AR_SUPERVISOR_ID=${AR_SUPERVISOR_ID}",//请求的action路径  
			error : function() {//请求失败处理函数  
				//请求失败
				alert('<spring:message code="ar.alert.message.viewattendencekeeper.error"/>');
			},
			success : function(data) { //请求成功后处理函数。    
				zNodes = data; //把后台封装好的简单Json格式赋给treeNodes
			}
		});
// 初始调用
$(document)
		.ready(function() {
			//布局
				$("#layout1").ligerLayout( {
					leftWidth : 180
				});
				var t = $("#deptTree1_updateattendancekeeperdepttree");
				t = $.fn.zTree.init(t, setting, zNodes);
				var nodes = t.getCheckedNodes();
				if (nodes.length > 0) {
					for ( var i = 0; i < nodes.length; i++) {
						if (i == 0) {
							document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
						} else {
							document.getElementById("deptNosEdu").value += ","
									+ nodes[i].DEPTNO;
						}
					}
				} else {
					document.getElementById("deptNosEdu").value = "";
				}
			});

function basicSubmit(){
	var allcourse=$('#ALL_PLAN').val();
	if(allcourse=="0"){
		alert("<spring:message code='edu.trainBasicInformation.XINGWEIBUTIANXIANG.a'/>");//*为必填项!
	}else{
		$('#addTrainBasicInformationInfo').submit();
	}
}
var teacherempid="";
var planempid="";
//查询计划管理	
function queryAllPlan(){
	var PLAN_NO=$('#ALL_PLAN').val();
	if(PLAN_NO!='0'){
		$.ajax({
			type:'post',
			dataType:'json',
			url:'/edu/traineducation/queryAllPlan',
			data:{PLAN_NO:PLAN_NO},
			success:function(data){
				$('#PLAN_NO').attr('value',data.PLAN_NO);
				//培训期次
				$('#PERIOD_TIME').attr('value',data.period_time);
				//培训类型
				$('#TRAIN_TYPE_CODE_BASIC').attr('value',data.train_type_code);
				//课程名称
				$('#trainname').html(data.course_name_code);
				$('#COURSE_NAME_CODE_BASIC').attr('value',data.course_name_code);
				//培训方式
				$('#traintype').html(data.train_form_code_name);
				$('#TRAIN_FORM_CODE_BASIC').attr('value',data.train_form_code);
				//培训地点
				$('#trainaddress').html(data.train_address);
				$('#TRAIN_ADDRESS').attr('value',data.train_address);
				//实施开始日期,结束日期
				$('#IMPLE_START_DATE').attr('value',data.plan_startdate);
				$('#IMPLE_END_DATE').attr('value',data.plan_enddate);
				//实施课时,单位
				$('#IMPLE_CLASS_HOUR').attr('value',data.class_hour);
				if(data.class_unit!=''&&data.class_unit!=null){
					$('#IMPLE_CLASS_UNIT option[value='+data.class_unit+']').attr('selected','selected');
				}
				$('#yibanjiangshi').html(data.teacher_name);
				$('#COM_TEACHER_NAME').attr('value',data.teacher_name);
				$('#COM_TEACHER_EMPID').attr('value',data.teacher_name_empid);
				$('#pingjiateacher').html(data.teacher_name);
				$('#EVA_TEACHER_NAME').attr('value',data.teacher_name);
				$('#EVA_TEACHER_EMPID').attr('value',data.teacher_name_empid);
				//$('#planname').html(data.des_employee_name);
				$('#PLAN_EMPLOYEE_NAME').attr('value',data.des_employee_name);
				$('#PLAN_EMPLOYEE_EMPID').attr('value',data.des_employee);
				/* $('#actname').html(data.des_employee_name);
				$('#ACT_EMPLOYEE_NAME').attr('value',data.des_employee_name);
				$('#ACT_EMPLOYEE_EMPID').attr('value',data.des_employee); */
				$('#plancount').html(splitMethod(data.des_employee));
				$('#actcount').html(splitMethod(data.des_employee));
				if(data.teacher_name_empid!=''){
					zhenjia(1);
					teacherempid=data.teacher_name_empid;
				}
				if(data.des_employee!=''){
					zhidingzhenjia(1);
					planempid=data.des_employee;
				}
				
				
			}
		});
	}else if(PLAN_NO=='0'){
		$('#tianjiabasic').attr('style','');
		$('#tbasic').click();
	}
}
function pingjia(){
	var alempid=$('#EVA_TEACHER_EMPID').val();
	if(teacherempid!=''){
		zhenjia(1);
		$('#pingjiajiangshi').attr('href','/edu/traineducation/commonTeacher?teacherempid='+teacherempid+'&alempid='+alempid);
	}else{
		zhenjia(0);
		alert("<spring:message code='edu.trainBasicInformation.MEIYOUYIBANJIANGSHI.a'/>");//没有一般讲师!
	}
}
var num=0;
function zhiding(){
	var checkplanempid="";
	if(num==0){
		checkplanempid=$('#PLAN_EMPLOYEE_EMPID').val();
	}else{
		checkplanempid=$('#ACT_EMPLOYEE_EMPID').val();
	}
	var checkotherplanempid=$('#FREE_EMPLOYEE_EMPID').val();
	if(planempid!=''){
		zhidingzhenjia(1);
		$('#zhidingrenyuan').attr('href','/edu/traineducation/planEmployee?planempid='+planempid+'&checkplanempid='+checkplanempid+'&checkotherplanempid='+checkotherplanempid);
	}else{
		zhidingzhenjia(0);
		alert("<spring:message code='edu.trainBasicInformation.MEIYOUJIHUAZHIDINGRENYUAN.a'/>");//没有计划指定人员!
	}
	num=num+1;
}
function zhenjia(a){
	if(a=="1"){
		$('#zhen').attr('style','');
		$('#jia').attr('style','display:none');
	}else if(a=="0"){
		$('#zhen').attr('style','display:none');
		$('#jia').attr('style','');
	}
	
}
function zhidingzhenjia(a){
	if(a=="1"){
		$('#zhidingzhen').attr('style','');
		$('#zhidingjia').attr('style','display:none');
	}else if(a=="0"){
		$('#zhidingzhen').attr('style','display:none');
		$('#zhidingjia').attr('style','');
	}
	
}
//分割字符串方法,返回数量
function splitMethod(str){
	if(str==''||str==null){
		return 0;
	}else{
		var array=str.split(",");
		return array.length;
	}
}
//查询所有的自选人员
function zixuan(){
	var checkotherplanempid=$('#FREE_EMPLOYEE_EMPID').val();
	var checkplanempid=$('#ACT_EMPLOYEE_EMPID').val();
	var deptno = $('#deptNosEdu').val();
	$('#zixuanrenyuan').attr('href','/edu/traineducation/otherPlanEmployee?planempid='+planempid+'&checkotherplanempid='+checkotherplanempid+'&checkplanempid='+checkplanempid+'&DEPTNO='+deptno);
}
function changeStudent(){
	var finalempid1=$('#FREE_EMPLOYEE_EMPID').val()+","+$('#ACT_EMPLOYEE_EMPID').val();
	$('#finalrenyuan').attr('href','/edu/traineducation/finalstudent?finalempid1='+finalempid1);
	
}
</script>
<div class="pageContent" layoutH="10">
	<form method="post" id="addTrainBasicInformationInfo" action="/edu/traineducation/addTrainBasicInformationInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<input type="hidden" name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE_BASIC" value="">
		<input type="hidden" name="PERIOD_TIME" id="PERIOD_TIME" value="">
		<input type="hidden" name=PLAN_NO id="PLAN_NO" value="">
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.SUOYOUKECHENG.a"/><!--所有课程--></td>
		<td class="td_type"  width="20%" colspan='5'>
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<select id="ALL_PLAN" name="ALL_PLAN" onchange="queryAllPlan()">
			<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
			<c:forEach items="${planManagerList}" var="c" varStatus="i">
			<option value="${c.PLAN_NO }">${c.TRAIN_TYPE_CODE_NAME }&nbsp&nbsp${c.COURSE_NUMBER }&nbsp&nbsp${c.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期--> <spring:message code="ar.alert.message.excelimport.title.di"/><!--第--> ${c.PERIOD_TIME })</option>
			</c:forEach>
			</select>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<select id="ALL_PLAN" name="ALL_PLAN" onchange="queryAllPlan()">
			<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
			<c:forEach items="${planManagerList}" var="c" varStatus="i">
			<option value="${c.PLAN_NO }">${c.COURSE_NAME_CODE }</option>
			</c:forEach>
			</select>
		</c:if>
		<span style="color:red;">*</span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" >
		<span id="trainname"></span>
		<input type="hidden" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE_BASIC" value="">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNFANGSHI.a"/><!--培训方式--></td>
		<td class="td_type"  width="20%" >
		<span id="traintype"></span>
		<input type="hidden" name="TRAIN_FORM_CODE" id="TRAIN_FORM_CODE_BASIC" value="">
		</td>
		<td class="td_title" width="1%"><spring:message code="empsubject.eduRm"/><!--培训地点--></td>
		<td class="td_type"  width="20%" >
		<span id="trainaddress"></span>
		<input type="hidden" name="TRAIN_ADDRESS" id="TRAIN_ADDRESS" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIKAISHIRIQI.a"/><!--实施开始日期--></td>
		<td class="td_type"  width="20%" >
		<input name="IMPLE_START_DATE"  id="IMPLE_START_DATE" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="" class="Wdate"/>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIJIESHURIQI.a"/><!--实施结束日期--></td>
		<td class="td_type"  width="20%" >
		<input name="IMPLE_END_DATE"  id="IMPLE_END_DATE" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="" class="Wdate"/>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.SHISHIKESHI.a"/><!--实施课时--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="IMPLE_CLASS_HOUR" id="IMPLE_CLASS_HOUR" value="" style="width:30px;" min="0">
		<select name="IMPLE_CLASS_UNIT" id="IMPLE_CLASS_UNIT">
		            <option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
					<option value="2"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></option>
					<option value="0"><spring:message code="display.mutual.month"/><!--月--></option>
					<option value="1"><spring:message code="display.mutual.day"/><!--天--></option>
					</select>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.BAOMINGJIEZHIRIQII.a"/><!--报名截止日期--></td>
		<td class="td_type"  width="20%" >
		<input name="APPLY_END_DATE"  id="APPLY_END_DATE" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="" class="Wdate"/>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" colspan="6" style="text-align: center;"><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		</tr>
		<tr>
		<td class="td_type" width="1%" colspan="6" >
		<input type="text" name="TRAIN_CONTENT" id="TRAIN_CONTENT" value="" style="width:800px; margin-left: 50px">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" colspan="6" style="text-align: center;"><spring:message code="edu.trainBasicInformation.YIBANJIANGSHI.a"/><!--一般讲师--></td>
		</tr>
		
		<tr>
		<td class="td_type" width="1%" colspan="6" style="text-align: center;">
		<span id="yibanjiangshi"></span>
		<input type="hidden" name="COM_TEACHER_NAME" id="COM_TEACHER_NAME" value="">
		<input type="hidden" name="COM_TEACHER_EMPID" id="COM_TEACHER_EMPID" value="">
		</td>
		</tr>
		
		<tr id="zhen" style="display:none">
		<td class="td_title" width="1%" colspan="6" style="text-align: center;">
        <a class="buttonActive" href="#" onclick="pingjia()" id="pingjiajiangshi" rel="ping" target="dialog" mask="true" width="600" height="400" style="margin-left: 420px;" >
		<span><spring:message code="edu.trainBasicInformation.PINGJIAJIANGSHI.a"/><!--评价讲师--></span></a>
		</td>
		</td>
		</tr>
		
		<tr id="jia" style="">
		<td class="td_title" width="1%" colspan="6" style="text-align: center;">
        <a class="buttonActive" href="#" onclick="pingjia()" style="margin-left: 346px;">
		<span><spring:message code="edu.trainBasicInformation.PINGJIAJIANGSHI.a"/><!--评价讲师--></span></a>
		</td>
		</td>
		</tr>
		
		<tr>
		<td class="td_type" width="1%" colspan="6" style="text-align: center;">
		<span id="pingjiateacher"></span>
		<input type="hidden" name="EVA_TEACHER_NAME" id="EVA_TEACHER_NAME" value="">
		<input type="hidden" name="EVA_TEACHER_EMPID" id="EVA_TEACHER_EMPID" value="">
		</td>
		</tr>
		<tr>
		<!-- <td class="td_title" width="1%" colspan="3" style="text-align: center;">计划培训对象(<span id="plancount">0</span>)</td>
		</td> -->
		<td class="td_title" width="1%" colspan="3" style="text-align: center;"><spring:message code="edu.trainBasicInformation.JIHUAPEIXUNDUIXIANG.a"/><!--计划培训对象-->(<span id="actcount">0</span>)</td>
		<td class="td_title" width="1%" colspan="3" style="text-align: center;"><spring:message code="edu.trainBasicInformation.SHIJIPEIXUNDUIXIANG.a"/><!--实际培训对象--> (<span id="finalcount">0</span>)</td>
		</tr>
		<tr> 
		   <td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.ZIXUANRENYUANBUMENZHIDING.a"/><!--自选人员部门指定--></td>
			  <td  class="td_type" width="20%" colspan="5"><ul id="deptTree1_updateattendancekeeperdepttree" class="ztree"></ul>
			    <input type="hidden" name="DES_DEPARTMENT" id="deptNosEdu" value="" />
	       </td>
		</tr>
		<tr>
		 <input type="hidden" name="PLAN_EMPLOYEE_NAME" id="PLAN_EMPLOYEE_NAME" value="">
			<input type="hidden" name="PLAN_EMPLOYEE_EMPID" id="PLAN_EMPLOYEE_EMPID" value="">
			
	   <td class="td_title" width="1%">
			<a class="buttonActive" href="#" onclick="zixuan()" id="zixuanrenyuan" rel="xuanzi" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
			<span><spring:message code="edu.trainBasicInformation.ZIXUANRENYUAN.a"/><!--自选人员--></span></a>
			</td>
			</td>
			<td class="td_type"  width="20%" colspan="2">
			<span id="zixuanname"></span>
			<input type="hidden" name="FREE_EMPLOYEE_NAME" id="FREE_EMPLOYEE_NAME" value="">
			<input type="hidden" name="FREE_EMPLOYEE_EMPID" id="FREE_EMPLOYEE_EMPID" value="">
		</td>
		<td  class="td_title" width="1%" rowspan="2">
			<a class="buttonActive" href="#" onclick="changeStudent()" id="finalrenyuan" rel="final" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
			<span><spring:message code="edu.trainBasicInformation.SHIJIRENYUAN.a"/><!--实际人员--></span></a>
		</td>
		<td class="td_type"  width="20%" colspan="2" rowspan="2">
		<span id="finalstudent"></span>
		<input type="hidden" name="FINAL_STUDENT_NAME" id="FINAL_STUDENT_NAME" value="">
		<input type="hidden" name="FINAL_STUDENT_EMPID" id="FINAL_STUDENT_EMPID" value="">
		</td>
		</tr>
		<tr>
		<td id="zhidingzhen" class="td_title" width="1%" style="display:none">
		<a class="buttonActive" href="#" onclick="zhiding()" id="zhidingrenyuan" rel="zhi" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
		<span><spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员--></span></a>
		</td>
		<td id="zhidingjia" class="td_title" width="1%" style="">
		<a class="buttonActive" href="#" onclick="zhiding()" style="margin-left: 100px;" >
		<span><spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员--></span></a>
		</td>
		<td class="td_type"  width="20%" colspan="2">
		<span id="actname"></span>
		<input type="hidden" name="ACT_EMPLOYEE_NAME" id="ACT_EMPLOYEE_NAME" value="">
		<input type="hidden" name="ACT_EMPLOYEE_EMPID" id="ACT_EMPLOYEE_EMPID" value="">
		</td>
		</tr>
		</table>
		</div>
		<div id="tianjiabasic" style="display:none">
		  <a class="buttonActive" id="tbasic" href="/edu/traineducation/addTrainBasicInformation" target="dialog" mask="true" width="800" height="600" >
			<span><spring:message code="button.add"/><!--添加--></span>
		</a>
		</div>
		<div class="formBar">
			<ul>
				<li onclick="basicSubmit()"><div class="buttonActive"><div class="buttonContent"><spring:message code="public.title.submit"/></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
