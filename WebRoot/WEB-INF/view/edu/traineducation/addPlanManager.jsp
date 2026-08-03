<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
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
function zhiding() {
	var deptno = $('#deptNosEdu').val();
	var empid = $('#empidEmployee').val();
	if (deptno != '') {
		$('#zhiding').attr(
				'href',
				'/edu/traineducation/desEmployee?DEPTNO=' + deptno + '&EMPID='
						+ empid);
	} else {
		$('#zhiding').attr('href',
				'/edu/traineducation/desEmployee?EMPID=' + empid);
	}
}
function budgetShow() {
	var ischeck = $('#BUDGET_SHOW').prop('checked');
	if (ischeck == true) {
		$('#BUDGET_SHOW').attr('value', 'Y');
	} else {
		$('#BUDGET_SHOW').attr('value', 'N');
	}
}
function teacher() {
	var teachername = $('#TEACHER_NAME').val();
	var teacherempid = $('#TEACHER_EMPID').val();
	if (teachername != '') {
		$('#teacher').attr(
				'href',
				'/edu/traineducation/teacherSearch?TEACHER_NAME=' + teachername
						+ '&teacherEmpid' + teacherempid);
	} else {
		$('#teacher').attr('href', '/edu/traineducation/teacherSearch');
	}
}
function pingjia() {
	var array = "";
	$('input[name="EVALUATE"]').each(function() {
		if ($(this).attr("checked")) {
			array += $(this).val() + ",";
		}
	});
	array = array.substring(0, array.length - 1);
	$('#ISNOT_EVALUATE').attr('value', array);
}
/**
 * 删除附件(新增)
 */
function deleteAttListInsertPlan() {
	var flag = false;
	$("input[name='FILE_NO']").each(function() {
		if ($(this).attr("checked") == "checked") {
			$(this).parent().parent().remove();
			flag = true;
		}
	});
	if (flag == false) {
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}
}
function addPlanImport() {
	$("#importExcelDialogAddPlan")
			.attr(
					'href',
					'/pa/excelImport/importExcelData?importFunName=/importTrainPlan?plan_no=${planno }');
	$("#importExcelDialogAddPlan").click();
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
</script>
<div class="pageContent" layoutH="10">
	<form method="post" action="/edu/traineducation/addPlanManagerInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="PLAN_NO" id="PLAN_NO" value=${planno }>
		<a id="importExcelDialogAddPlan" href="" target="dialog"
			rel="addplancourse" mask="true" width="500" height="200"></a>
		<div class="pageFormContent nowrap">
			<table id="eduTable" class="user_table" width="100%" border="1"
				cellpadding="2" cellspacing="1">
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="empsubject.subjectNm"/><!--课程名称
		-->
					</td>
					<td class="td_type" width="20%">
						<select id="TRAIN_TYPE_CODE" name="TRAIN_TYPE_CODE">
							<c:forEach items="${courseManagerList}" var="c" varStatus="i">
								<option value="${c.COURSE_NO }">
									${c.TRAIN_TYPE_CODE_NAME }&nbsp&nbsp${c.COURSE_NUMBER
									}&nbsp&nbsp${c.COURSE_NAME_CODE }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.JIHUAKAISHISHIJIAN.a"/><!--计划开始时间-->
					</td>
					<td class="td_type" width="20%">
						<input name="PLAN_STARTDATE" class="required"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" id="PLAN_STARTDATE"
							type="text" value="" />
					</td>
				</tr>

				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.JIHUAJIESHUSHIJIAN.a"/><!--计划结束时间-->
					</td>
					<td class="td_type" width="20%">
						<input name="PLAN_ENDDATE" class="required"
							onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" id="PLAN_ENDDATE"
							type="text" value="" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" name="CLASS_HOUR" id="CLASS_HOUR" value=""
							class="required" min="0" />
						<select name="CLASS_UNIT" id="CLASS_UNIT">
							<option value="2">
								<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时-->
							</option>
							<option value="0">
								<spring:message code="display.mutual.month"/><!--月-->
							</option>
							<option value="1">
								<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天-->
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="hrm.empinfo.Training_form"/><!--培训形式
			-->
					</td>
					<td class="td_type" width="20%">
						<ait:SelectSyCodeByCpnyID name="TRAIN_FORM_CODE"
							id="TRAIN_FORM_CODE" parentNo="14014493" cnpyID="${defaultCpny}"
							selected="" />
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
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.SHIFOUKESHENQING.a"/><!--是否可申请
			-->
					</td>
					<td class="td_type" width="20%">
						<select name="ISNOT_APPLY" id="ISNOT_APPLY">
							<option value="Y">
								<spring:message code="ar.viewcycle.content.yes"/><!--是
			-->
							</option>
							<option value="N">
								<spring:message code="ar.viewcycle.content.no"/><!--否
			-->
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.ZHIDINGBUMEN.a"/><!--指定部门
			-->
					</td>
					<td class="td_type" width="20%">
						<ul id="deptTree1_updateattendancekeeperdepttree" class="ztree"></ul>
						<input type="hidden" name="DES_DEPARTMENT" id="deptNosEdu"
							value="" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员
			-->
					</td>
					<td class="td_type" width="98%">
						<a class="buttonActive" href="/edu/traineducation/desEmployee"
							id="zhiding" onclick="zhiding()" rel="zhiding" target="dialog"
							mask="true" width="600" height="400"> <span><spring:message code="edu.planManager.TIANJIAZHIDINGRENYUAN.a"/><!--添加指定人员
			--></span> </a>
						<span  id="zhEmployee"></span>
						<input type="hidden" name="empidEmployeeName"
							id="empidEmployeeName" value="">
						<input type="hidden" name="empidEmployee" id="empidEmployee"
							value="">
					<%-- 
					   <img  style="padding-left: 200px;" alt="全部清除" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
							onclick="$('#empidEmployeeName').attr('value','');$('#empidEmployee').attr('value','');$('#zhEmployee').html('');">
				    --%>
				</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.YUSUANFEIYONG.a"/><!--预算费用
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" name="BUDGET" id="BUDGET" value="" />
						<input type="checkbox" name="BUDGET_SHOW" id="BUDGET_SHOW"
							value="N" onclick="budgetShow()" />
						<spring:message code="edu.planManager.SHIFOUKEJIAN.a"/><!--是否可见
			-->
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.ZHUGUANBUMEN.a"/><!--主管部门
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" id="DEPART_MANA_CODE"
							name="DEPART_MANA_CODE_NAME" value="">
						<input name="DEPART_MANA_CODE" type="hidden" value=""
							syslong="DEPART_MANA_CODE">
						<ait:deptTreeIcon name="DEPART_MANA_CODE" cpnyId="${defaultCpny}"
							limit="super" id="DEPART_MANA_CODE" selected="" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.JIANGSHI.a"/><!--讲师
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" name="TEACHER_NAME" id="TEACHER_NAME" value="">
						<a class="buttonActive" href="/edu/traineducation/teacherSearch"
							id="teacher" onclick="teacher()" rel="sou" target="dialog"
							mask="true" width="600" height="400"> <span><spring:message code="ar.viewempcalender.title.search"/><!--搜索
			--></span> </a>
						<input type="hidden" name="TEACHER_EMPID" id="TEACHER_EMPID"
							value="">
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="empsubject.eduRm"/><!--培训地点
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" name="TRAIN_ADDRESS" id="TRAIN_ADDRESS"
							value="">
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.PEIXUNRENSHU.a"/><!--培训人数
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="text" class="required" name="TRAIN_PERSON_COUNT"
							id="TRAIN_PERSON_COUNT" value="" min="0">
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注
			--></td>
					<td class="td_type"  width="20%" >
					<input type="text"  name="TRAIN_PERSON_REMARK" id="TRAIN_PERSON_REMARK"  >
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.SHIFOUXUYAOPINGJIA.a"/><!--是否需要评价
			-->
					</td>
					<td class="td_type" width="20%">
						<input type="checkbox" name="EVALUATE" value="1"
							onclick="pingjia()">
						<spring:message code="edu.planManager.XUEYUANPINGJIA.a"/><!--学员评价
			-->
						<input type="checkbox" name="EVALUATE" value="2"
							onclick="pingjia()">
						<spring:message code="edu.planManager.JIANGSHIPINGJIA.a"/><!--讲师评价
			-->
						<input type="checkbox" name="EVALUATE" value="3"
							onclick="pingjia()">
						<spring:message code="edu.planManager.PEIXUNPINGJIA.a"/><!--培训评价
			-->
						<input type="hidden" name="ISNOT_EVALUATE" id="ISNOT_EVALUATE"
							value="0">
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
					<td class="td_title" width="1%">
						<spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件
			-->
					</td>
					<td class="td_type" width="20%">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="button.add"/><!--添加
			--></span>
						</a>
						<a class="w_button" href="#" onclick="deleteAttListInsertPlan();"><span><spring:message code="button.delete"/><!--删除
			--></span>
						</a>
						<table id="fileTable" class="list" width="100%">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="1%">
						<spring:message code="edu.planManager.DAORUKECHENGBIAO.a"/><!--导入课程表
			-->
					</td>
					<td class="td_type" width="20%">
						<a class="buttonActive" href="#" onclick="addPlanImport()"> <span><spring:message code="ar.addempshift.title.excelimport"/><!--Excel导入
			--></span>
						</a>
						<a class="buttonActive"
							href="/edu/traineducation/planImportDemoLoad?flag=load"> <span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载
			--></span>
						</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<!-- 提交 -->
								<spring:message code="public.title.submit" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<!-- 取消 -->
								<spring:message code="public.title.cancle" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
