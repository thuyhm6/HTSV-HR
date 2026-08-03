<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<link rel="stylesheet" href="../csstree/demo.css" type="text/css">
<link rel="stylesheet" href="../csstree/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="../ztree/jquery-1.4.4.min.js">
</script>
<script type="text/javascript" src="../ztree/jquery.ztree.core-3.5.js">
</script>
<!--  <script type="text/javascript" src="../../../js/jquery.ztree.excheck-3.5.js"></script>
	  <script type="text/javascript" src="../../../js/jquery.ztree.exedit-3.5.js"></script>-->
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type="text/javascript">
		function operAll(){
			
			 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			 treeObj.expandAll(true);

		}	
		function zTreeOnClick(event, treeId, treeNode) {
			
						//param_date.location.href = "/hrm/informationRetrieval/orgViewPersonalInfo?PERSON_ID="+treeNode.id;
   			 //alert(treeNode.tId + ", " + treeNode.name);
		};
		
		function closeAll(){
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.expandAll(false);
			
		}
		function zTreeOnNodeCreated(event, treeId, treeNode) {
    		//alert(treeNode.id + ", " + alert(treeNode.cid);
    		$('#'+treeNode.tId).append('<a href="/hrm/informationRetrieval/orgViewPersonalInfo?PERSON_ID='+treeNode.id+'" target="ajax" rel="jbsxBox">'+treeNode.name+'</a>');
    		                            //<a href="/hrm/informationRetrieval/orgViewPersonalInfo" target="ajax" rel="jbsxBox">载入</a>
			
    		                            
    		                            
    		                          
		};
		var setting = {
			data: {
			key:{
				id:"id",
				pid:"pid",
				cpnyid:"cpnyid",
				empid:"empid",
				cid:"cid"
				
			},
			simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pid",
			empid:"empid",
			cpnyid:"cpnyid",
			rootPId: "cid"
				}
			},
			callback: {
				onClick: zTreeOnClick,
				beforeClick: function(treeId, treeNode) { 
				
					
					var hrefUrl="/hrm/informationRetrieval/orgViewPersonalInfo?PERSON_ID="+treeNode.id;
		 			//var cpnyId=document.getElementById("CPNY_ID").value;
		 			//var backHref=hrefUrl+"MENU_NO="+treeNode.MENU_NO+"&CPNY_ID="+cpnyId;
		 			
		 			
		 			document.getElementById("viewZuzhijiegou").href=hrefUrl;
		 			document.getElementById("viewZuzhijiegou").click();
				}
				
			}
			
		};
		
		var zNodes =${trees}
		$(document).ready(function(){
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
	</SCRIPT>



<table id="abc">
	<tr>
		<td class="info_content_00" width="10%" valign="top" height="800"
			style="border-left-color: black">
			<div class="content_wrap">
				<br />
				<a href="#" onclick="operAll()" style="cursor: hand">open All</a> |
				<a href="#" onclick="closeAll()" style="cursor: hand">close All</a>
				<br />
				<div layoutH="23" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<a id="viewZuzhijiegou"
					href="/hrm/informationRetrieval/orgViewPersonalInfo?" target="ajax"
					rel="jbsxBox"></a>
			</div>
		</td>
		<td class="info_content_00" valign="top" height="1000" nowrap>
			<%--<iframe width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" name="param_date" src="/hrm/informationRetrieval/orgViewPersonalInfo">
			</iframe>
			
		--%>
			<div id="jbsxBox" class="unitBox">
				
			</div>
		</td>
	</tr>
</table>

