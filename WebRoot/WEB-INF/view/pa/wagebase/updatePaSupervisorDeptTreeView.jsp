<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ page import="java.util.*" %>
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
		var t =$.fn.zTree.getZTreeObj("deptTree_updatePaSupervisordepttree");
		var nodes = t.getCheckedNodes(); 
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				if(i==0){
					 document.getElementById("deptNos").value=nodes[i].DEPTNO;
				}else{
					 document.getElementById("deptNos").value+=","+nodes[i].DEPTNO;
				}
			}
		}else{
			document.getElementById("deptNos").value="";
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
        url: "/pa/wagebase/getDeptTree?PA_SUPERVISOR_ID=${PA_SUPERVISOR_ID}",//请求的action路径  
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
	var t = $("#deptTree_updatePaSupervisordepttree");
	t = $.fn.zTree.init(t, setting, zNodes);
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("deptNos").value=nodes[i].DEPTNO;
			}else{
				 document.getElementById("deptNos").value+=","+nodes[i].DEPTNO;
			}
		}
	}else{
		document.getElementById("deptNos").value="";
	}
});
</script>

<div id="wageBearDeptTree"  class="panel" style="display:block;float:right;clear:none; width:48%;min-width:300px;" >	
	<h1><!-- 组织结构 --><spring:message code="ar.alert.message.viewattendencekeeper.zuzhijiegou"/></h1>
	<div class="pageFormContent nowrap" style="overflow:auto" layoutH="90">
		<ul id="deptTree_updatePaSupervisordepttree" class="ztree"></ul>
		<input type="hidden" name="deptNos" id="deptNos" value=""/>
	</div>
</div>
