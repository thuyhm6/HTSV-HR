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
			var t = $.fn.zTree.getZTreeObj("deptTree1_updateattendancekeeperdepttree");
			var nodes = t.getCheckedNodes();
			if (nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					if (i == 0) {
						document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
					} else {
						document.getElementById("deptNosEdu").value += ","+nodes[i].DEPTNO;
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
			url : "/edu/traineducation/getDeptTree?DES_DEPARTMENT=${trainBasicInformationInfo.DES_DEPARTMENT}",//请求的action路径  
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
				$("#layout1").ligerLayout( {leftWidth : 180});
				var t = $("#deptTree1_updateattendancekeeperdepttree");
				t = $.fn.zTree.init(t, setting, zNodes);
				var nodes = t.getCheckedNodes();
				if (nodes.length > 0) {
					for ( var i = 0; i < nodes.length; i++) {
						if (i == 0) {
							document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
						} else {
							document.getElementById("deptNosEdu").value += ","+ nodes[i].DEPTNO;
						}
					}
				} else {
					document.getElementById("deptNosEdu").value = "";
				}
			});

$(function(){
	var unit="${trainBasicInformationInfo.IMPLE_CLASS_UNIT }";
	if(unit!=''){
		$('#IMPLE_CLASS_UNIT option[value='+unit+']').attr('selected','selected');
	}
	//$('#plancount').html(splitMethod("${trainBasicInformationInfo.planempid }"));
	$('#actcount').html(splitMethod("${freeempid}")+splitMethod("${trainBasicInformationInfo.planempid }"));
	$('#finalcount').html(splitMethod("${finalempid}"));
});
//分割字符串方法,返回数量
function splitMethod(str){
	if(str==''||str==null){
		return 0;
	}else{
		var array=str.split(",");
		return array.length;
	}
}
function upingjia(){
	var alempid=$('#EVA_TEACHER_EMPID').val();
	var teacherempid=$('#COM_TEACHER_EMPID').val();
    $('#upingjiajiangshi').attr('href','/edu/traineducation/commonTeacher?teacherempid='+teacherempid+'&alempid='+alempid);
	
}
function uzhiding(){
	var checkplanempid=$('#ACT_EMPLOYEE_EMPID').val();
	var checkotherplanempid=$('#FREE_EMPLOYEE_EMPID').val();
	var planempid=$('#PLAN_EMPLOYEE_EMPID').val();
	$('#uzhidingrenyuan').attr('href','/edu/traineducation/planEmployee?planempid='+planempid+'&checkplanempid='+checkplanempid+'&checkotherplanempid='+checkotherplanempid);
	
}

//查询所有的自选人员
function uzixuan(){
	var checkotherplanempid=$('#FREE_EMPLOYEE_EMPID').val();
	var checkplanempid=$('#ACT_EMPLOYEE_EMPID').val();
	var planempid=$('#PLAN_EMPLOYEE_EMPID').val();
	var deptno = $('#deptNosEdu').val();
	$('#uzixuanrenyuan').attr('href','/edu/traineducation/otherPlanEmployee?planempid='+planempid+'&checkotherplanempid='+checkotherplanempid+'&checkplanempid='+checkplanempid+'&DEPTNO='+deptno);
}
function changeStudentInfo(){
	var finalempid1=$('#FREE_EMPLOYEE_EMPID').val()+","+$('#ACT_EMPLOYEE_EMPID').val()+","+$('#PLAN_EMPLOYEE_EMPID').val();
	var finalempid2=$('#FINAL_STUDENT_EMPID').val();
	$('#finalrenyuaninfo').attr('href','/edu/traineducation/finalstudent?finalempid1='+finalempid1+"&finalempid2="+finalempid2+"&flag=2");
}
</script>
<div class="pageContent" layoutH="10">
	<form method="post" id="trainBasicInformationInfo" action="/edu/traineducation/updateTrainBasicInformationInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${trainBasicInformationInfo.BASIC_NO }">
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" >
		<span id="trainname">${trainBasicInformationInfo.COURSE_NAME_CODE }</span>
		<input type="hidden" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE" value="${trainBasicInformationInfo.COURSE_NAME_CODE }">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNFANGSHI.a"/><!--培训方式--></td>
		<td class="td_type"  width="20%" >
		<span id="traintype">${trainBasicInformationInfo.TRAIN_FORM_CODE_NAME }</span>
		<input type="hidden" name="TRAIN_FORM_CODE" id="TRAIN_FORM_CODE" value="${trainBasicInformationInfo.TRAIN_FORM_CODE}">
		</td>
		<td class="td_title" width="1%"><spring:message code="empsubject.eduRm"/><!--培训地点--></td>
		<td class="td_type"  width="20%" >
		<span id="trainaddress">${trainBasicInformationInfo.TRAIN_ADDRESS }</span>
		<input type="hidden" name="TRAIN_ADDRESS" id="TRAIN_ADDRESS" value="${trainBasicInformationInfo.TRAIN_ADDRESS }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIKAISHIRIQI.a"/><!--实施开始日期--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="IMPLE_START_DATE" id="IMPLE_START_DATE" value="${trainBasicInformationInfo.IMPLE_START_DATE }" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" class="Wdate">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIJIESHURIQI.a"/><!--实施结束日期--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="IMPLE_END_DATE" id="IMPLE_END_DATE" value="${trainBasicInformationInfo.IMPLE_END_DATE }" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" class="Wdate">
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.SHISHIKESHI.a"/><!--实施课时--></td>
		<td class="td_type"  width="20%" >
		<input type="text" name="IMPLE_CLASS_HOUR" id="IMPLE_CLASS_HOUR" value="${trainBasicInformationInfo.IMPLE_CLASS_HOUR }" style="width:30px;">
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
		<input name="APPLY_END_DATE"  id="APPLY_END_DATE" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${trainBasicInformationInfo.APPLY_END_DATE }" class="Wdate"/>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" colspan="6" style="text-align: center;"><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		</tr>
		<tr>
		<td class="td_type" width="1%" colspan="6" >
		<input type="text" name="TRAIN_CONTENT" id="TRAIN_CONTENT" value="${trainBasicInformationInfo.TRAIN_CONTENT }" style="width:800px; margin-left: 50px" >
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" colspan="6" style="text-align: center;"><spring:message code="edu.trainBasicInformation.YIBANJIANGSHI.a"/><!--一般讲师--></td>
		</tr>
		
		<tr>
		<td class="td_type" width="1%" colspan="6" style="text-align: center;">
		<span id="yibanjiangshi">${trainBasicInformationInfo.commonTeachername }</span>
		<input type="hidden" name="COM_TEACHER_NAME" id="COM_TEACHER_NAME" value="${trainBasicInformationInfo.commonTeachername }">
		<input type="hidden" name="COM_TEACHER_EMPID" id="COM_TEACHER_EMPID" value="${trainBasicInformationInfo.commonTeacherempid }">
		</td>
		</tr>
		
		<tr id="zhen" >
		<td class="td_title" width="1%" colspan="6" style="text-align: center;">
        <a class="buttonActive" href="#" onclick="upingjia()" id="upingjiajiangshi" rel="ping" target="dialog" mask="true" width="600" height="400" style="margin-left: 420px;" >
		<span><spring:message code="edu.trainBasicInformation.PINGJIAJIANGSHI.a"/><!--评价讲师--></span></a>
		</td>
		</td>
		</tr>
		
		<tr>
		<td class="td_type" width="1%" colspan="6" style="text-align: center;">
		<span id="pingjiateacher">${trainBasicInformationInfo.evateachername }</span>
		<input type="hidden" name="EVA_TEACHER_NAME" id="EVA_TEACHER_NAME" value="${trainBasicInformationInfo.evateachername }">
		<input type="hidden" name="EVA_TEACHER_EMPID" id="EVA_TEACHER_EMPID" value="${trainBasicInformationInfo.evateacherempid }">
		</td>
		</tr>
		
		<tr>
		<!-- <td class="td_title" width="1%" colspan="3" style="text-align: center;">计划培训对象(<span id="plancount">0</span>)</td> -->
		<td class="td_title" width="1%" colspan="3" style="text-align: center;"><spring:message code="edu.trainBasicInformation.JIHUAPEIXUNDUIXIANG.a"/><!--计划培训对象-->(<span id="actcount">0</span>)</td>
		<td class="td_title" width="1%" colspan="3" style="text-align: center;"><spring:message code="edu.trainBasicInformation.SHIJIPEIXUNDUIXIANG.a"/><!--实际培训对象-->(<span id="finalcount">0</span>)</td>
		</tr>
		<tr> 
		   <td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.ZIXUANRENYUANBUMENZHIDING.a"/><!--自选人员部门指定--></td>
			  <td  class="td_type" width="20%" colspan="5"><ul id="deptTree1_updateattendancekeeperdepttree" class="ztree"></ul>
			    <input type="hidden" name="DES_DEPARTMENT" id="deptNosEdu" value="${trainBasicInformationInfo.des_department}" />
	       </td>
		</tr>
		<tr>
		<%-- <td class="td_title" width="1%" rowspan='3'>指定人员</td>
		<td class="td_type"  width="20%" colspan="2" rowspan='3'>
		<span id="planname">${trainBasicInformationInfo.planname }</span>
		<input type="hidden" name="PLAN_EMPLOYEE_NAME" id="PLAN_EMPLOYEE_NAME" value="${trainBasicInformationInfo.planname }">
		<input type="hidden" name="PLAN_EMPLOYEE_EMPID" id="PLAN_EMPLOYEE_EMPID" value="${trainBasicInformationInfo.planempid }">
		</td> --%>
		<td class="td_title" width="1%">
		<a class="buttonActive" href="#" onclick="uzixuan()" id="uzixuanrenyuan" rel="xuanzi" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
		<span><spring:message code="edu.trainBasicInformation.ZIXUANRENYUAN.a"/><!--自选人员--></span></a>
		</td>
		</td>
		<td class="td_type"  width="20%" colspan="2">
		<span id="zixuanname">${freename }</span>
		<input type="hidden" name="FREE_EMPLOYEE_NAME" id="FREE_EMPLOYEE_NAME" value="${freename }">
		<input type="hidden" name="FREE_EMPLOYEE_EMPID" id="FREE_EMPLOYEE_EMPID" value="${freeempid }">
		<input type="hidden" name="OLD_FREE_EMPLOYEE_NAME" id="OLD_FREE_EMPLOYEE_NAME" value="${freename }">
		<input type="hidden" name="OLD_FREE_EMPLOYEE_EMPID" id="OLD_FREE_EMPLOYEE_EMPID" value="${freeempid }">
		</td>
		
		<td  class="td_title" width="1%" rowspan="3">
		<a class="buttonActive" href="#" onclick="changeStudentInfo()" id="finalrenyuaninfo" rel="final" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
		<span><spring:message code="edu.trainBasicInformation.SHIJIRENYUAN.a"/><!--实际人员--></span></a>
		</td>
		<td class="td_type"  width="20%" colspan="3" rowspan="3">
		<span id="finalstudent">${finalname }</span>
		<input type="hidden" name="FINAL_STUDENT_NAME" id="FINAL_STUDENT_NAME" value="${finalname }">
		<input type="hidden" name="FINAL_STUDENT_EMPID" id="FINAL_STUDENT_EMPID" value="${finalempid }">
		</td>
		</tr>
		 <tr>
		<td class="td_title" width="1%" ><spring:message code="edu.trainBasicInformation.CAIJUETONGGUORENYUAN.a"/><!--决裁通过人员--></td>
		<td class="td_type"  width="20%" colspan="2">
		<span>${applyname }</span>
		</td>
		</tr> 
		<tr>
		<td id="zhidingzhen" class="td_title" width="1%" >
		<a class="buttonActive" href="#" onclick="uzhiding()" id="uzhidingrenyuan" rel="zhi" target="dialog" mask="true" width="600" height="400" style="margin-left: 100px;" >
		<span><spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员--></span></a>
		</td>
		<td class="td_type"  width="20%" colspan="2">
		<span id="actname">${actname }</span>
		<input type="hidden" name="ACT_EMPLOYEE_NAME" id="ACT_EMPLOYEE_NAME" value="${actname}">
		<input type="hidden" name="ACT_EMPLOYEE_EMPID" id="ACT_EMPLOYEE_EMPID" value="${actempid }">
		<input type="hidden" name="OLD_ACT_EMPLOYEE_NAME" id="OLD_ACT_EMPLOYEE_NAME" value="${actname}">
		<input type="hidden" name="OLD_ACT_EMPLOYEE_EMPID" id="OLD_ACT_EMPLOYEE_EMPID" value="${actempid }">
		
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
