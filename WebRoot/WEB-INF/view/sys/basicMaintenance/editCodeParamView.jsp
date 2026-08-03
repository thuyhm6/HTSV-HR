<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>

<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var zTree;
var demoIframe;

var setting_sy0430 = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	
	data: {
		key: {
			name: "CONTENT",
			open:"true"
		},
		simpleData: {
			enable:true,
			idKey: "CODE_NO",
			pIdKey: "PARENT_CODE_NO",
			rootPId: ""
		}
	}, 
	callback: {
		beforeClick: function(treeId, treeNode) { 
			
			var hrefUrl="/sys/basicMaintenance/getEditCodeParamView?";
		 	var cpnyId=document.getElementById("CPNY_ID").value;
		 	var backHref=hrefUrl+"CODE_NO="+treeNode.CODE_NO+"&CPNY_ID="+cpnyId;
		 	document.getElementById("childLink_sy0430").href=backHref;
		 	document.getElementById("childLink_sy0430").click();
		}
	}
};

 var zNodes;
 
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",  
        url: "/sys/basicMaintenance/getAllParentCodeList",//请求的action路径  
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
	var t = $("#parentTree_sy0430");
	t = $.fn.zTree.init(t, setting_sy0430, zNodes);
});
   
function addCpnyIdEditCodeParamView(cpnyId){
	/**
		$.each($('a',$('#parentTree')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
			$(value).attr("href",backHref);
		});*/
	var hrefUrl=document.getElementById("childLink_sy0430").href;
	var hrefArr=hrefUrl.split("CPNY_ID");
	var newUrl=hrefArr[0]+"CPNY_ID="+cpnyId.value;
	
	document.getElementById("childLink_sy0430").href=newUrl;
	
 	document.getElementById("childLink_sy0430").click();
	
	}
</script>
<!-- 页面主容器开始 -->
<div class="pageContent" style="padding:5px">
	<!-- 页签主容器开始 -->
	<div class="tabs">
		<!-- 单个页签主体开始 -->
		<div class="tabsContent">
			<!-- 主体内容开始 -->
			<div >
				<!-- 树框开始 -->
				<div layoutH="10" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff" title="<spring:message code='sys.arAffirmPost.title.commonCode'/>">
		            <!--不带复选框-->
				    <ul id="parentTree_sy0430" class="ztree"></ul>
				    <a id="childLink_sy0430" href="/sys/basicMaintenance/getEditCodeParamView?CODE_NO=${code.CODE_NO}&CPNY_ID=${CPNY_ID}?" target="ajax" rel="CodeManagejbsxBox"></a>
				</div>
				<!-- 树框结束 -->
				<!-- 工作区容器开始 -->
				<div id="CodeManagejbsxBox" class="unitBox" style="margin-left:240px;">
 					 <div class="pageContent">
						<form method="post" action="/sys/basicMaintenance/editCodeParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
							<div class="pageFormContent nowrap" layoutH="214">			
								<dl>
									<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
									<dd>
										<select  class="combox"  name="CPNY_ID"  id="CPNY_ID" onchange="addCpnyIdEditCodeParamView(this)">
											<c:forEach items="${companyList}" var="company">
											<option value="${company.CPNY_ID}" <c:if test="${company.CPNY_ID eq codePams.CPNY_ID}">selected</c:if> >${company.CONTENT}</option>
											</c:forEach>
										</select>
									</dd>
								</dl>
								<dl>
									<dt><spring:message code="sys.arAffirmPost.title.code"/><!--代码-->:</dt>
									<dd>
										<input type="hidden" id="CodeNo" name="CODE_NOS"  class="required textInput"   />
										<div id="treeDiv"  style=" float:left; display:block;overflow:auto;width:600px;border:solid 1px #CCC; line-height:21px; background:#FFF;" layoutH="214">
											<ul id="tree123">
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
												            <span >${code.CONTENT} </span>
												            <ul>
										    		</c:if>
										    		<c:if test="${code.DEPTH != 1}" >
										    			<li id="${code.CODE_NO}" pid="${PARENT_CODE_NO}"  url="${code.CODE_NO}">
															<span ><a>${code.CONTENT}</a>
										    				</span>
										    			</li>
										    		</c:if>
										    	</c:forEach>
									    </ul>
									 </li>
											</ul>
									 
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
				</div>
				<!-- 工作区容器结束 -->
				</div>
			<!-- 主体内容结束-->
		</div>
		<!-- 单个页签主体结束-->
		<!-- 单个页签页脚开始-->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
		<!-- 单个页签页脚开始结束-->
	</div>
	<!-- 页签主容器结束 -->
</div>
<!-- 页面主容器结束-->