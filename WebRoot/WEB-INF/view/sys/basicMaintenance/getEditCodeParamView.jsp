<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>

<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var zTree;
	var demoIframe;
	
	var setting_sy0430 = {
	view: {
		dblClickExpand: true,
		showLine: true,
		selectedMulti: true,
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
	      name:"CONTENT",
	      open:true
 	   },
		simpleData: {
			enable:true,
			idKey: "CODE_NO",
			pIdKey: "PARENT_CODE_NO",
			rootPId: ""
		}
	}, 
	callback: {
		onCheck: function(treeId, treeNode) { 
			var t =$.fn.zTree.getZTreeObj("tree123");
			var nodes = t.getCheckedNodes(); 
			if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					if(i==0){
						 document.getElementById("CodeNo").value=nodes[i].CODE_NO;
					}else{
						 document.getElementById("CodeNo").value+=","+nodes[i].CODE_NO;
					}
				}
			}else{
				document.getElementById("CodeNo").value="";
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
        url: "/sys/basicMaintenance/getChildCodeListByParentNo?CPNY_ID=${CPNY_ID}&CODE_NO=${CODE_NO}",//请求的action路径  
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
	var t = $("#tree123");
	t = $.fn.zTree.init(t, setting_sy0430, zNodes);
	 
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("CodeNo").value=nodes[i].CODE_NO;
			}else{
				 document.getElementById("CodeNo").value+=","+nodes[i].CODE_NO;
			}
		}
	}else{
		document.getElementById("CodeNo").value="";
	}
});
function addCpnyIdEditCodeParamView(cpnyId){
	 
	var hrefUrl=document.getElementById("childLink_sy0430").href;
	var hrefArr=hrefUrl.split("CPNY_ID");
	var newUrl=hrefArr[0]+"CPNY_ID="+cpnyId.value;
	document.getElementById("childLink_sy0430").href=newUrl;
	
 	document.getElementById("childLink_sy0430").click();
}
</script>
 
 					 <div class="pageContent">
						<form method="post" action="/sys/basicMaintenance/editCodeParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
							<div class="pageFormContent nowrap" layoutH="60">			
								<dl>
									<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
									<dd>
										<select  class="combox"  name="CPNY_ID" id="CPNY_ID" onchange="addCpnyIdEditCodeParamView(this)">
											<c:forEach items="${companyList}" var="company">
											<option value="${company.CPNY_ID}" <c:if test="${company.CPNY_ID eq CPNY_ID}">selected</c:if> >${company.CONTENT}</option>
											</c:forEach>
										</select>
									</dd>
								</dl>
								<dl>
									<dt><spring:message code="sys.arAffirmPost.title.code"/><!--代码-->:</dt>
									<dd>
				     					<input type="hidden" id="PARENT_CODE_NO" name="PARENT_CODE_NO"  value="${CODE_NO}" class="required textInput"   />
										<input type="hidden" id="CodeNo" name="CODE_NOS"  class="required textInput"   />
										
										<div id="treeDiv"  style=" float:left; display:block; overflow:auto; width:600px; height:320px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
											<ul id="tree123" class="ztree"> 
												<c:forEach items="${codeSubLists}" var="code" varStatus="i">
										    		<c:if test="${i.index == 0}" >
										    			<li id="${code.CODE_NO}" url="${code.CODE_NO}" isexpand="false">
												            <span >${code.CONTENT}</span>
												            <ul>
										    		</c:if>
										    		
										    		<c:if test="${i.index != 0 && code.DEPTH == 1}" >
												            </ul>
												        </li> 
										    			<li id="${code.CODE_NO}" url="${code.CODE_NO}" isexpand="false">
												            <span >${code.CONTENT}</span>
												            <ul>
										    		</c:if>
										    		<c:if test="${code.DEPTH != 1}" >
										    			<li id="${code.CODE_NO}" pid="${PARENT_CODE_NO}"  url="${code.CODE_NO}">
										    				<span ><a>${code.CONTENT}</a>
											    				<input id="${code.CODE_NO}Zh" type="hidden" value="${code.ALIAS_ZH }"/>
											    				<input id="${code.CODE_NO}En" type="hidden" value="${code.ALIAS_EN }"/>
											    				<input id="${code.CODE_NO}Kr" type="hidden" value="${code.ALIAS_KR }"/>
										    				</span>
										    			</li>
										    			
										    		</c:if>
										    	</c:forEach>
												    </ul>
												 </li>
											</ul>
										</div>
									</dd>
								</dl>
							</div>
							<div class="formBar">
								 <ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
									<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
								</ul>
							</div>
						</form>
					</div>
			 