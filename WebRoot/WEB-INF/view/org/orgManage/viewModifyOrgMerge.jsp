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
	var t = $("#deptTree_viewModifyOrgMerge");
	t = $.fn.zTree.init(t, setting, zNodes);
});

var selectedObj = null;
//左右移动
$(document).ready(function(){
	var upIndex = 0;
	var dowIndex = 0;
	$("#layout3",$.pdialog.getCurrent()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("deptTree_viewModifyOrgMerge");
		var nodes = treeObj.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			var flag = true;
			$("input[name='upDept']").each(function(){
				if($(this).val() == nodes[i].DEPTNO){
					flag = false;
				}
			});
			if(flag){
				upIndex = upIndex + 1;
	           	trStr = '<tr style="cursor:pointer;" onclick="changeColor(this);"><td style="text-align:center;">' + upIndex + '</td><td style="text-align:center;">' + nodes[i].DEPTNAME + '<input type="hidden" name="upDept" value="' + nodes[i].DEPTNO + '"></td></tr>';
				$("#upDeptTable").append(trStr);
			}
       	}
	});
	$("#layout2",$.pdialog.getCurrent()).click(function(){
		selectedObj.remove();
		var tb2 = document.getElementById("upDeptTable");
		var rowCount = tb2.rows.length;
		if(rowCount == 1 ){
			upIndex = 0;
		}else{
			for(var m=1;m<=rowCount;m++){
				tb2.rows[m].cells[0].innerHTML = m;
				upIndex = m;
			}
		}
	});
	
	$("#layout5",$.pdialog.getCurrent()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("deptTree_viewModifyOrgMerge");
		var nodes = treeObj.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			if(dowIndex == 0){
				dowIndex = 1;
	           	trStr = '<tr style="cursor:pointer;background-color:#eaffc0;"><td style="text-align:center;">' + dowIndex + '</td><td style="text-align:center;">' + nodes[i].DEPTNAME + '<input type="hidden" name="downDept" value="' + nodes[i].DEPTNO + '"></td></tr>';
				$("#downDeptTable").append(trStr);
			}
       	}
	});
	$("#layout4",$.pdialog.getCurrent()).click(function(){
		$("#downDeptTable tr:gt(0)").remove();
		dowIndex = 0;
	});
	$("tr[sysLong='upDeptTr']",$.pdialog.getCurrent()).each(function(){
		$(this).bind("click",function(){
			$(this).css("background-color","#eaffc0");
		});
	});
});
function changeColor(tr){
	$("#upDeptTable tr",$.pdialog.getCurrent()).css("background-color","rgb(241, 241, 241)");
	$(tr).css("background-color","#eaffc0");
	selectedObj = $(tr);
}


function modifyOrgMerge(){
	var $form = $("#modifyOrgMergeForm");	

	if($("input[name='upDept']").size() < 2 ){
		alertMsg.info('<spring:message code="org.title.SELECT_TWODEPT" />');
		return false;
	}

	if($("input[name='downDept']").size() < 1 ){
		alertMsg.info('<spring:message code="org.title.SELECT_TARGETDEPT" />');
		return false;
	}
	
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
  		{okCall:function(){
		  	$.ajax({
  				type: $form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
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
	<form id="modifyOrgMergeForm" method="post" action="/org/orgManage/modifyOrgMerge" class="pageForm required-validate" >
		<div style="width:230px;height:410px;line-height:410px;overflow-x:hidden;float:left;">
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<input type="hidden" name="RESUME_NO" value="${RESUME_NO}"/>
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.DEPT_CATALOG" /><!-- 部门目录 --></div>
			</div>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;height:20px;line-height:20px;">Total:${fn:length(modifyOrgOrderNoList)}</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:20px;line-height:20px;text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);height:320px;line-height:320px;overflow:auto;">
				<ul id="deptTree_viewModifyOrgMerge" class="ztree"></ul>
			</div>
		</div>
		<div style="width:30px;margin-left:15px;height:410px;line-height:410px;overflow-x:hidden;float:left;">
		
			<div id="layout3" class="w-layout-collapse-left" style="margin-top:100px;"></div>
			<div id="layout2" class="w-layout-collapse-right" style="margin-top:1px;"></div>
			
			<div id="layout5" class="w-layout-collapse-left" style="margin-top:140px;"></div>
			<div id="layout4" class="w-layout-collapse-right" style="margin-top:1px;"></div>
			
		</div>
		<div style="width:200px;height:410px;line-height:410px;overflow-x:hidden;float:right;">
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.SELECT_DEPT" /><!-- 选择部门 --></div>
			</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:20px;line-height:20px;">Total:${fn:length(modifyOrgOrderNoList)}</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:150px;line-height:150px;">
				<table class="user_table"  width="80%" border="1" cellpadding="2" cellspacing="1" id="upDeptTable">
					<tr>
						<td class="tb_title" width="40px" style="text-align:center;">No.</td>
						<td class="tb_title" width="150px" style="text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					</tr>
				</table>
			</div>
			<div style="display:block;height:20px;line-height:20px;margin-top:5px;">
				<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="org.title.TARGETDEPT" /><!-- 目标部门 --></div>
			</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:20px;line-height:20px;">Total:${fn:length(modifyOrgOrderNoList)}</div>
			<div class="user_table" style="background-color:rgb(241, 241, 241);font:bold 12px/20px arial,sans-serif;height:150px;line-height:150px;">
				<table class="user_table"  width="80%" border="1" cellpadding="2" cellspacing="1" id="downDeptTable">
					<tr>
						<td class="tb_title" width="40px" style="text-align:center;">No.</td>
						<td class="tb_title" width="150px" style="text-align:center;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					</tr>
				</table>
			</div>
		</div>
			<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent"><!--保存-->
									<button type="button" onclick="modifyOrgMerge();">
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