<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<SCRIPT type='text/javascript'>		
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
		chkboxType:{"Y":"ps","N":"ps"},
		chkStyle:"checkbox",
		enable:true,
		nocheckInherit:true,
		radionType:"level"
	},
	data: {
		key: {
		  checked:"CHECKED",
	     // children:"children",
	      name:"CONTENT",
	     // title:"",
	      open:true
	   },
		simpleData: {
			enable:true,
			//checked : true,
			//checkable : true,
			idKey: "MENU_NO",
			pIdKey: "MENU_PARENT_NO",
			rootPId: ""
		}
	}, 
	callback: {
		onCheck: function(treeId, treeNode) { 
			var t =$.fn.zTree.getZTreeObj("tree1");
			var nodes = t.getCheckedNodes(); 
			if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					if(i==0){
						 document.getElementById("MenuNo").value=nodes[i].MENU_NO;
					}else{
						 document.getElementById("MenuNo").value+=","+nodes[i].MENU_NO;
					}
				}
			}else{
				document.getElementById("MenuNo").value="";
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
        url: "/sys/menu/getMenuTreeListByParentMenu?CPNY_ID=${CPNY_ID}&MENU_NO=${MENU_NO}",//请求的action路径  
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
	var t = $("#tree1");
	t = $.fn.zTree.init(t, setting, zNodes);
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("MenuNo").value=nodes[i].MENU_NO;
			}else{
				 document.getElementById("MenuNo").value+=","+nodes[i].MENU_NO;
			}
		}
	}else{
		document.getElementById("MenuNo").value="";
	}
	//$('#MENU_NO').attr('value',nodes);
});
 
function addCpnyIdSy0460(cpnyId){
	/**
	$.each($('a',$('#parentMenuTree')),function(index,value){
		newHref=value.href.split("CPNY_ID="); 
		backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
		$(value).attr("href",backHref);
	});
	*/
	 
	var hrefUrl=document.getElementById("childLink_sy0460").href;
	var hrefArr=hrefUrl.split("CPNY_ID");
	var newUrl=hrefArr[0]+"CPNY_ID="+cpnyId.value;
	document.getElementById("childLink_sy0460").href=newUrl;
	
 	document.getElementById("childLink_sy0460").click();
}

</SCRIPT>
<div class="pageContent">
	<form method="post" action="/sys/menu/saveMenuParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">			
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
				<dd>
					<select  class="combox"  name="CPNY_ID" id="CPNY_ID" onchange="addCpnyIdSy0460(this)">
						<c:forEach items="${companyList}" var="company">
						<option <c:if test="${CPNY_ID eq company.CPNY_ID}">selected</c:if>  value="${company.CPNY_ID}">${company.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.menu.title.menu"/><!--菜单-->:</dt>
				<dd>
					<input type="hidden" id="MenuNo" name="MENU_NOS"  class="required textInput"   />
					<input type="hidden" id="PARENT_MENU_NO" name="PARENT_MENU_NO"  value="${MENU_NO}" class="required textInput"   />
					<div id="treeDiv"  style=" float:left; display:block;  overflow:auto; width:600px; height:330px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
											<ul id="tree1" class="ztree"></ul>
					</div>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
