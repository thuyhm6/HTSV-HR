<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
    <script type="text/javascript">
    var zTree;
	var demoIframe;

	var setting_sy0420_view = {
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
				document.getElementById("childLink_sy0420").href="/sys/basicMaintenance/getCodeListByParentCode?seach_PARENT_CODE_NO="+treeNode.CODE_NO+"&pageNum=1&menuNo=2469";
				document.getElementById("childLink_sy0420").click();
			}
		}
	};

	 var zNodes;
	 
	 $.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: "/sys/basicMaintenance/getParentTreeData",//请求的action路径  
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
    	var t_sy0420 = $("#parentCodeTreeSy0420");
		t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
    });
    
    </script>
<!-- 页面主容器开始 -->
<div class="pageContent">
	<!-- 页签主容器开始 -->
		<!-- 单个页签主体开始 -->
		<div class="tabsContent">
			<!-- 主体内容开始 -->
			<div>
				<!-- 树框开始 -->
				<div layoutH="10" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff" title="<spring:message code='sys.arAffirmPost.title.commonCode'/>">
		            <!--不带复选框-->
				    <ul id="parentCodeTreeSy0420" class="ztree">
				    </ul>
				    <a id="childLink_sy0420" href="/sys/basicMaintenance/getCodeListByParentCode?seach_PARENT_CODE_NO=${parentCode.CODE_NO}&pageNum=1" target="ajax" rel="jbsxBox_sy0420">${parentCode.CONTENT}</a>
				</div>
				<!-- 树框结束 -->
				<!-- 工作区容器开始 -->
				<div id="jbsxBox_sy0420" class="unitBox" style="margin-left:240px;">
 					<!-- 头部搜索条开始 -->
 					<div class="pageHeader">
						<form onsubmit="return divSearch(this, 'jbsxBox_sy0420');" action="/sys/basicMaintenance/getCodeListByParentCode?pageNum=1" method="post" rel="pagerForm" >
						<div class="searchBar">
							<table class="searchContent">
								<tr>
									<td class="dateRange">
										<spring:message code="sys.basic.title.codeName"/><!--代码名称-->:
										<input type="text" value="${CODE_NAME}"  name="seach_CODE_NAME">
										<input type="hidden" name="seach_PARENT_CODE_NO" value="${PARENT_CODE_NO}" />
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
									<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
								</ul>
							</div>
						</div>
						</form>
					</div>
					<!-- 头部搜索条结束 -->
					<!-- 表格主体开始 -->
					<div class="pageContent">
						<div class="formBar">
							<ul class="toolBar">
								<!--  <li><a class="edit"  href="/sys/basicMaintenance/getEditCodeView?NO={sid_obj}" target="dialog" mask="true"><span>修改</span></a></li>
								<li><a class="delete" href="/sys/basicMaintenance/delCodeByCodeNo?CODE_NO={CodeNO}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>-->
								<li class="line">line</li>
							</ul>
						</div>
						<table class="table" width="99%" layoutH="214">
							<thead>
								<tr>
									<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th>
									<th><spring:message code="sys.affirm.CodeNo"/><!--  代码NO--></th>
									<th><spring:message code="sys.affirm.CodeCode"/><!--  代码CODE--></th>
									<th><spring:message code="sys.basic.title.codeNameCN"/><!--中文代码名称--></th>
									<!--<th><spring:message code="sys.basic.title.codeNameKO"/>  韩文代码名称</th>-->
									<th><spring:message code="sys.basic.title.OrderCode"/><!--  排序字段--></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${codeLists}" var="code" varStatus="i">
								<tr target="sid_obj" rel="${code.CODE_NO}">
									<td>${i.index+1}</td>
									<td>${ code.CODE_NO }</td>
									<td>${ code.DESCRIPTION }</td>
									<td>${ code.CONTENT }</td>
									<!--<td>${code.KO}</td>-->
									<td>${code.ORDERNO}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
                        
                        <script>
                        function pageFromSea(totalCnt){
                        }
                        </script>
                        <c:set value="/sys/basicMaintenance/getCodeListByParentCode" var="pageUrl"/>
                        <c:set value="jbsxBox_sy0420" var="targetInfo"/>
                        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
					</div>
					<!--表格主体开始 -->
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
	<!-- 页签主容器结束 -->
</div>
<!-- 页面主容器结束-->