<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//关闭、打开所有节点
$(document).ready(function(){
	$("#orgCloseOpen",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewCurrentOrgDetailInfo"); 
		treeObj.expandAll(true);
	});
	$("#orgCloseClose",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewCurrentOrgDetailInfo"); 
		treeObj.expandAll(false);
	});
	//模糊搜索部门树，并打开节点
	$("#viewCurrentOrgInfoSrachOrg",navTab.getCurrentPanel()).click(function(){
		if($("#viewCurrentOrgInfo_deptName",navTab.getCurrentPanel()).val() != ""){
			var treeObj = $.fn.zTree.getZTreeObj("viewCurrentOrgDetailInfo"); 
			var treeNodes = treeObj.getNodesByParamFuzzy("DEPTNAME",$("#viewCurrentOrgInfo_deptName",navTab.getCurrentPanel()).val()); 
			for(var i=0;i<treeNodes.length;i++){
				treeObj.expandNode(treeNodes[i].getParentNode());
			}
		}else{
			alertMsg.info('<spring:message code="org.title.INPUT_SELECTINFO" />');
		}
	});
});
//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewCurrentOrgDetailInfo?DEPTNO=','viewCurrentOrgDetailInfo_right_unit');
});

</script>
<div id="orgTree" style="width:340px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;">
	<div style=" float:left; display:block; margin:10px; overflow:auto;width:310px; height:40px; border:solid 1px #CCC; background:#FFF;">
			<table class="searchContent" style="height:30px;line-height:30px;margin-top:5px;">
				<tr>
					<td width="100px;"  class="td_center"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					<td width="200px;">
						<input type="text" id="viewCurrentOrgInfo_deptName" name="deptName" size="12"/>
						<a class="w_button" id="viewCurrentOrgInfoSrachOrg" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a>
					</td>
				</tr>
			</table>
	</div>
	<div style=" float:left; display:block; margin-left:10px; overflow:hidden;width:300px;background:#FFF;">
		<input type="radio" id="orgCloseOpen" name="orgClose" value="open"/>&nbsp;&nbsp;<spring:message code="org.title.OPEN_ALL" /><!-- 全部打开 -->&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgCloseClose" name="orgClose" value="close"/>&nbsp;&nbsp;<spring:message code="org.title.CLOSE_ALL" /><!-- 全部关闭 -->
	</div>
	<div id="viewCurrentOrgInfo_tree_unit">
		<ait:deptTreeResume name="TREE_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewCurrentOrgDetailInfo" 
		current="1" 
		style="float:left; display:block; margin:10px; overflow:auto;width:310px; height:500px; border:solid 1px #CCC; line-height:21px; background:#FFF;" selected="${DEPTNO}"/>
	</div>
</div>
<div id="viewCurrentOrgDetailInfo_right_unit" style="display:block;margin-top:5px;">
</div>
