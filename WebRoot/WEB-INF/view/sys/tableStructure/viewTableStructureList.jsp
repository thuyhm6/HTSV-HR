<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>

var zTree;
var demoIframe;

var setting_sy0460 = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	data: {
		key: {
			name: "TABLE_NAME",
			open:"true"
		},
		simpleData: {
			enable:true,
			idKey: "TABLE_NO",
			pIdKey: "PARENT_TABLE_NO",
			rootPId: ""
		}
	}, 
	callback: {
		beforeClick: function(treeId, treeNode) { 
			var hrefUrl="/sys/tableStructure/viewTableStructureList?";
		 	var backHref=hrefUrl+"TABLE_NO="+treeNode.TABLE_NO;
		 	document.getElementById("childLink_sy0460").href=backHref;
		 	document.getElementById("childLink_sy0460").click();
		}
	}
};

 var zNodes;
 
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",  
        url: "/sys/tableStructure/getParentTreeData",//请求的action路径  
        error: function () {//请求失败处理函数  
            alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
        },  
        success:function(data){ //请求成功后处理函数。    
        	zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
        }  
    });
  
// 初始调用
$(document).ready(function(){
    //布局
	var t = $("#parentMenuTree_sy0460");
	t = $.fn.zTree.init(t, setting_sy0460, zNodes);
});
</SCRIPT>
 
 
<div class="pageContent">
	<a id="operateAliasButton"  href="/sys/pageStructure/addNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">操作列</a>
	<a id="addAliasButton"  href="/sys/pageStructure/addNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">添加列</a>
	<a id="editAliasButton"  href="/sys/pageStructure/updateNewAliasView"  target="dialog" mask="true" width="500" height="500" style="display:none">修改列</a>
	<form method="post" action="/sys/pageStructure/saveAliasInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	
		<input type="hidden" name="del_rt_no" id="del_rt_no" value="" />
		<input type="hidden" name="update_rt_no" id="update_rt_no" value="" />
		<input type="hidden" name="add_table_index" id="add_table_index" value="" />
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<div class="formBar">
		<select onchange="startRequestPS(this.value);" id="MENU_NO" name="MENU_NO">
			<option value="">--请选择--4433322</option>
			<c:forEach items="${tableStructureList}" var="menu">
				<option value="${menu.TABLE_NAME}">${menu.TABLE_NO}</option>
			</c:forEach>
		</select>
		</div>		
		
		<!-- 树框开始 -->
		<div layoutH="23" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff" title="<spring:message code='sys.arAffirmPost.title.commonCode'/>">
            <ul id="parentMenuTree_sy0460" class="ztree">
		    </ul>
		    <a id="childLink_sy0460" href="/sys/tableStructure/viewTableStructureList?" target="ajax" rel="MenuEditjbsxBox"></a>
		
           
           <c:set var="parentSize" value="${fn:length(tableStructureList)}" />
         
		</div>
		<!-- 树框结束 -->
	
		
	</form>
	
</div>
