<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
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
		}
	}
};

var zNodes = ${deptTree };

// 初始调用
$(document).ready(function(){
    //布局
	var t = $("#deptTree_viewModifyOrgSplit");
	t = $.fn.zTree.init(t, setting, zNodes);
});

var selectedObj = null;
//左右移动
$(document).ready(function(){
	var upIndex = 0;
	var dowIndex = 0;
	//down移入
	$("#layout3",$.pdialog.getCurrent()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("deptTree_viewModifyOrgSplit");
		var nodes = treeObj.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			var flag = true;
			$("input[name='upDept']").each(function(){
				if($(this).val() == nodes[i].DEPTNO){
					flag = false;
				}
			});
			if($("#viewModifyOrgSplit_up table").attr("sysLong") == nodes[i].DEPTNO){
				flag = false;
			}
			if(flag){
				upIndex = upIndex + 1;
	           	trStr = '<tr style="cursor:pointer;" onclick="changeColor(this);" sysLong="' + nodes[i].DEPTNO + '"><td style="text-align:center;">' + upIndex + '</td><td style="text-align:center;">' + nodes[i].DEPTNAME + '<input type="hidden" name="upDept" value="' + nodes[i].DEPTNO + '"></td></tr>';
				$("#upDeptTable").append(trStr);
				//初始目标部门人员信息
			  	$.ajax({
	  				type: 'POST',
	  				url: '/org/orgManage/modifyOrgSplit',
					data: [{ name: 'DEPTNO', value: nodes[i].DEPTNO },
					       { name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
					       { name: 'TYPE', value: 'ADD' }],
	  				dataType:"json",
	  				cache: false,
	  				success: function(data){
			  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=' + data.DEPTNO + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
			  		},
	  				error: DWZ.ajaxError
	  			});
			}
       	}
	});
	//down移除
	$("#layout2",$.pdialog.getCurrent()).click(function(){
		var removeDeptno = selectedObj.attr("sysLong");
		selectedObj.remove();
		var tb2 = document.getElementById("upDeptTable");
		var rowCount = tb2.rows.length;
		if(rowCount == 1 ){
			upIndex = 0;
		}else{
			for(var m=1;m<rowCount;m++){
				tb2.rows[m].cells[0].innerHTML = m;
				upIndex = m;
			}
		}
		//初始目标部门人员信息
	  	$.ajax({
			type: 'POST',
			url: '/org/orgManage/modifyOrgSplit',
			data: [{ name: 'DEPTNO', value: removeDeptno },
			       { name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
			       { name: 'TYPE', value: 'DELETE' }],
			dataType:"json",
			cache: false,
			success: function(data){
	  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=' + data.DEPTNO + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
	  		},
			error: DWZ.ajaxError
		});
	});
	//up移入
	$("#layout5",$.pdialog.getCurrent()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("deptTree_viewModifyOrgSplit");
		var nodes = treeObj.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			if(dowIndex == 0){
				dowIndex = 1;
	           	trStr = '<tr style="cursor:pointer;background-color:#eaffc0;"><td style="text-align:center;">' + dowIndex + '</td><td style="text-align:center;">' + nodes[i].DEPTNAME + '<input type="hidden" name="downDept" value="' + nodes[i].DEPTNO + '"></td></tr>';
				$("#downDeptTable").append(trStr);
				//初始化分割人员信息
			  	$.ajax({
	  				type: 'POST',
	  				url: '/org/orgManage/modifyOrgSplit',
					data: [{ name: 'DEPTNO', value: nodes[i].DEPTNO },
					       { name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
					       { name: 'TYPE', value: 'INIT' }],
	  				dataType:"json",
	  				cache: false,
	  				success: function(data){
			  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=' + data.DEPTNO + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_up');
			  		},
	  				error: DWZ.ajaxError
	  			});
			}
       	}
       	//init 清楚目标部门
		var tb2 = document.getElementById("upDeptTable");
		var rowCount = tb2.rows.length;
		for(var m=1;m<rowCount;m++){
			$(tb2.rows[1]).remove();
		}
		upIndex = 0;
		openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
	});
	//up移除
	$("#layout4",$.pdialog.getCurrent()).click(function(){
		$("#downDeptTable tr:gt(0)").remove();
		dowIndex = 0;
	});
	$("tr[sysLong='upDeptTr']",$.pdialog.getCurrent()).each(function(){
		$(this).bind("click",function(){
			$(this).css("background-color","#eaffc0");
		});
	});
	//向下
	$("#layout6",$.pdialog.getCurrent()).click(function(){
		var empids = "";
		$("input[name='viewOrgSplitTemp_empid_UP']").each(function(i,obj){
			if(obj.checked){
				empids += ",'" + obj.value + "'";
			}
		});
		if(empids.length < 1 ){
			alertMsg.info('<spring:message code="org.title.SELECT_ROMOVE_EMP" />');
			return false;
		}else if(selectedObj == null){
			alertMsg.info('<spring:message code="org.title.SELECT_TARGETDEPT" />');
			return false;
		}else{
		  	$.ajax({
  				type: 'POST',
  				url: '/org/orgManage/modifyOrgSplit',
				data: [{ name: 'DEPTNO', value: selectedObj.attr("sysLong") },
				       { name: 'EMPIDS', value: empids.substr(1) },
				       { name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
				       { name: 'TYPE', value: 'MOVE' }],
  				dataType:"json",
  				cache: false,
  				success: function(data){
		  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=' + $("#viewModifyOrgSplit_up table").attr("sysLong") + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_up');
		  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=DOWN&DEPTNO=' + $("#viewModifyOrgSplit_down table").attr("sysLong") + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
		  		},
  				error: DWZ.ajaxError
  			});
		}
	});
	//向上
	$("#layout7",$.pdialog.getCurrent()).click(function(){
		var empids = "";
		$("input[name='viewOrgSplitTemp_empid_DOWN']").each(function(i,obj){
			if(obj.checked){
				empids += ",'" + obj.value + "'";
			}
		});
		if(empids.length < 1 && selectedObj != null){
			return false;
		}else{
		  	$.ajax({
  				type: 'POST',
  				url: '/org/orgManage/modifyOrgSplit',
				data: [{ name: 'DEPTNO', value: $("#viewModifyOrgSplit_up table").attr("sysLong") },
				       { name: 'EMPIDS', value: empids.substr(1) },
				       { name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
				       { name: 'TYPE', value: 'MOVE' }],
  				dataType:"json",
  				cache: false,
  				success: function(data){
		  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=UP&DEPTNO=' + $("#viewModifyOrgSplit_up table").attr("sysLong") + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_up');
		  			openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=DOWN&DEPTNO=' + $("#viewModifyOrgSplit_down table").attr("sysLong") + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
		  		},
  				error: DWZ.ajaxError
  			});
		}
	});
});

function changeColor(tr){
	$("#upDeptTable tr",$.pdialog.getCurrent()).css("background-color","rgb(241, 241, 241)");
	$(tr).css("background-color","#eaffc0");
	selectedObj = $(tr);
	openOnRight('/org/orgManage/viewOrgSplitTemp?FLAG=DOWN&DEPTNO=' + $(tr).attr("sysLong") + '&RESUME_NO=' + $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val(),'viewModifyOrgSplit_down');
}

function changeColorEmp(tr){
	$("#viewModifyOrgSplit_up table tr",$.pdialog.getCurrent()).css("background-color","rgb(241, 241, 241)");
	$(tr).css("background-color","#eaffc0");
}

function modifyOrgSplit(){
	var $form = $("#modifyOrgSplitForm");	

	'<spring:message code="org.title.SELECT_TARGETDEPT" />'
	
	if($("input[name='upDept']",$.pdialog.getCurrent()).size() < 1 ){
		alertMsg.info('<spring:message code="org.title.SELECT_LEASTONE_DEPT" />');
		return false;
	}

	if($("input[name='downDept']",$.pdialog.getCurrent()).size() < 1 ){
		alertMsg.info('<spring:message code="org.title.SELECT_ONEDEPT_SPLIT" />');
		return false;
	}
	if($("#viewModifyOrgSplit_up table",$.pdialog.getCurrent()).find("tr").length > 1 ){
		alertMsg.info('<spring:message code="org.title.NOTSAVE_EXISTEMP" />');
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/org/orgManage/modifyOrgSplit',
				data: [{ name: 'RESUME_NO', value: $("input[name='RESUME_NO']",$.pdialog.getCurrent()).val() },
				       { name: 'DEPTNO', value: $("#viewModifyOrgSplit_up table").attr("sysLong") },
				       { name: 'NEW_DEPTNO', value: $("#viewModifyOrgSplit_down table").attr("sysLong") },
				       { name: 'TYPE', value: 'SAVE' }],
  				dataType:"json",
  				cache: false,
  				success: dialogDivAjaxDone,
  				error: DWZ.ajaxError
  			});
  		}});
	return false;
}
</script>
<div class="pageContent">
	<form id="modifyOrgSplitForm" method="post" action="/org/orgManage/modifyOrgSplit" class="pageForm required-validate" >
		<div style="width:220px;height:440px;line-height:440px;overflow-x:hidden;float:left;">
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<input type="hidden" name="RESUME_NO" value="${RESUME_NO}"/>
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.DEPT_CATALOG" /><!-- 部门目录 --></div>
			</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:20px;line-height:20px;text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);height:375px;line-height:375px;overflow:auto;">
				<ul id="deptTree_viewModifyOrgSplit" class="ztree"></ul>
			</div>
		</div>
		<div style="width:30px;margin-left:5px;height:440px;line-height:440px;overflow-x:hidden;float:left;">
			
			<div id="layout5" class="w-layout-collapse-left" style="margin-top:100px;"></div>
			<div id="layout4" class="w-layout-collapse-right" style="margin-top:1px;"></div>
			
			<div id="layout3" class="w-layout-collapse-left" style="margin-top:140px;"></div>
			<div id="layout2" class="w-layout-collapse-right" style="margin-top:1px;"></div>
			
		</div>
		<div style="width:180px;height:440px;line-height:440px;overflow-x:hidden;float:left;">
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.SELECT_DEPT" /><!-- 选择部门 --></div>
			</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:170px;line-height:170px;">
				<table class="user_table"  width="80%" border="1" cellpadding="2" cellspacing="1" id="downDeptTable">
					<tr>
						<td class="tb_title" width="40px" style="text-align:center;">No.</td>
						<td class="tb_title" width="150px" style="text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					</tr>
				</table>
			</div>
			<div style="height:30px;line-height:30px;"></div>
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.TARGETDEPT" /><!-- 目标部门 --></div>
			</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:170px;line-height:170px;">
				<table class="user_table"  width="80%" border="1" cellpadding="2" cellspacing="1" id="upDeptTable">
					<tr>
						<td class="tb_title" width="40px" style="text-align:center;">No.</td>
						<td class="tb_title" width="150px" style="text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					</tr>
				</table>
			</div>
		</div>
		
		<div style="width:240px;height:440px;line-height:440px;overflow-x:hidden;float:left;margin-left:10px;">
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.SELECT_DEPT_EMP" /><!-- 选择部门  任职员 --></div>
			</div>
			<div id="viewModifyOrgSplit_up" class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:170px;line-height:170px;overflow:auto;">
				<table class="user_table"  width="400px;" border="1" cellpadding="2" cellspacing="1" sysLong="">
					<tr>
						<td class="tb_title" width="30px" style="text-align:center;">No.</td>
						<td class="tb_title" width="30px" style="text-align:center;"><input type="checkbox" class="checkboxCtrl" group="viewOrgSplitTemp_empid_${FLAG }" /></td>
						<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></td>
						<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.EMPID" /><!-- 工号 --></td>
						<td class="tb_title" width="100px" style="text-align:center;"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></td>
						<!--<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.DUTY_NO" /> 岗位 </td>-->
						<td class="tb_title" width="80px" style="text-align:center;"><spring:message code="ess.infoApply.renzhizhuangtai" /><!-- 任职状态 --></td>
					</tr>
				</table>
			</div>
			<div style="height:30px;line-height:30px;">
				<div id="layout6" class="w-layout-collapse-down" style="margin-left:100px;margin-top:10px;float:left;"></div>
				<div id="layout7" class="w-layout-collapse-up" style="margin-left:1px;margin-top:10px;float:left;"></div>
			</div>
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.SELECT_DEPT_EMP" /><!-- 选择部门  任职员 --></div>
			</div>
			<div id="viewModifyOrgSplit_down" class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:170px;line-height:170px;overflow:auto;">
				<table class="user_table"  width="400px;" border="1" cellpadding="2" cellspacing="1" sysLong="">
					<tr>
						<td class="tb_title" width="30px" style="text-align:center;">No.</td>
						<td class="tb_title" width="30px" style="text-align:center;"><input type="checkbox" class="checkboxCtrl" group="viewOrgSplitTemp_empid_${FLAG }" /></td>
						<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></td>
						<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.EMPID" /><!-- 工号 --></td>
						<td class="tb_title" width="100px" style="text-align:center;"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></td>
						<!--<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.DUTY_NO" /> 岗位 </td>-->
						<td class="tb_title" width="80px" style="text-align:center;"><spring:message code="ess.infoApply.renzhizhuangtai" /><!-- 任职状态 --></td>
					</tr>
				</table>
			</div>
		</div>
		
			<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent"><!--保存-->
									<button type="button" onclick="modifyOrgSplit();">
										<spring:message code="org.title.SAVE" /><!-- 保存 -->
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent"><!--提交-->
									<button type="button" class="close">
										<spring:message code="org.title.CLOSE" /><!-- 关闭 -->
									</button>
								</div>
							</div>
						</li>
					</ul>
			</div>
	</form>
</div>