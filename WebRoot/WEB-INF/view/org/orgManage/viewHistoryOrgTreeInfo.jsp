<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//关闭、打开所有节点
$(document).ready(function(){
	$("#orgCloseOpen",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewHistoryOrgInfo"); 
		treeObj.expandAll(true);
	});
	$("#orgCloseClose",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewHistoryOrgInfo"); 
		treeObj.expandAll(false);
	});
	//模糊搜索部门树，并打开节点
	$("#viewHistoryOrgInfoSrachOrg",navTab.getCurrentPanel()).click(function(){
		if($("#viewHistoryOrgInfo_deptName",navTab.getCurrentPanel()).val() != ""){
			var treeObj = $.fn.zTree.getZTreeObj("viewHistoryOrgInfo"); 
			var treeNodes = treeObj.getNodesByParamFuzzy("DEPTNAME",$("#viewHistoryOrgInfo_deptName",navTab.getCurrentPanel()).val()); 
			for(var i=0;i<treeNodes.length;i++){
				treeObj.expandNode(treeNodes[i].getParentNode());
			}
		}else{
			alertMsg.info('<spring:message code="org.title.INPUT_SELECTINFO" />');
		}
	});
	//
	$("#orgDeptSelf",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewHistoryOrgInfo"); 
		var nodes = treeObj.getSelectedNodes();
		var deptNo = "";
		if(nodes.length > 0){
			deptNo = nodes[0].DEPTNO;
		}
		openOnRight('/org/orgManage/viewHistoryOrgPanel?DEPTNO=' + deptNo + '&RESUME_NO=${RESUME_NO}&currentIndex=' 
				+ $("#viewHistoryOrgPanel_currentIndex").val()
				+ '&SON_FLAG=' + 1,'viewHistoryOrgDetailInfo_right_unit');
	});
	$("#orgDeptSon",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewHistoryOrgInfo"); 
		var nodes = treeObj.getSelectedNodes();
		var deptNo = "";
		if(nodes.length > 0){
			deptNo = nodes[0].DEPTNO;
		}
		openOnRight('/org/orgManage/viewHistoryOrgPanel?DEPTNO=' + deptNo + '&RESUME_NO=${RESUME_NO}&currentIndex=' 
				+ $("#viewHistoryOrgPanel_currentIndex").val()
				+ '&SON_FLAG=' + 0,'viewHistoryOrgDetailInfo_right_unit');
	});
});
//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewHistoryOrgPanel?DEPTNO=&RESUME_NO=${RESUME_NO}&SON_FLAG=1&currentIndex=0','viewHistoryOrgDetailInfo_right_unit');
});
function onClick_viewHistoryOrgInfo(event, treeId, treeNode){
	var SON_FLAG = 1;
	if($("#orgDeptSon",navTab.getCurrentPanel()).attr("checked") == "checked"){
		SON_FLAG = 0;
	}
	openOnRight('/org/orgManage/viewHistoryOrgPanel?DEPTNO=' + treeNode.DEPTNO + '&RESUME_NO=${RESUME_NO}&currentIndex=' 
			+ $("#viewHistoryOrgPanel_currentIndex").val()
			+ '&SON_FLAG=' + SON_FLAG,'viewHistoryOrgDetailInfo_right_unit');
}
</script>
	<div style=" float:left; display:block; margin:10px; overflow:auto;width:310px; height:40px; border:solid 1px #CCC; background:#FFF;">
			<table class="searchContent" style="height:30px;line-height:30px;margin-top:5px;">
				<tr>
					<td width="70px;"  class="td_center"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					<td width="230px;">
						<input type="text" id="viewHistoryOrgInfo_deptName" name="deptName" size="20"/>
						<a class="w_button" id="viewHistoryOrgInfoSrachOrg" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a>
					</td>
				</tr>
			</table>
	</div>
	<div style=" float:left; display:block; margin-left:10px; overflow:hidden;width:300px;background:#FFF;">
		<input type="radio" id="orgDeptSelf" name="SON_FLAG" value="1" checked="checked"/>&nbsp;&nbsp;<spring:message code="org.title.NOW_DEPT" /><!-- 当前部门 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgDeptSon" name="SON_FLAG" value="0"/>&nbsp;&nbsp;<spring:message code="org.title.INCLUDE_CHILDDEPT" /><!-- 包含子部门 -->
	</div>
	<div style=" float:left; display:block; margin-left:10px;margin-top:5px; overflow:hidden;width:300px;background:#FFF;">
		<input type="radio" id="orgCloseOpen" name="orgClose" value="open"/>&nbsp;&nbsp;<spring:message code="org.title.OPEN_ALL" /><!-- 全部打开 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgCloseClose" name="orgClose" value="close"/>&nbsp;&nbsp;<spring:message code="org.title.CLOSE_ALL" /><!-- 全部关闭 -->
	</div>
	<div id="viewHistoryOrgInfo_tree_unit">
		<ait:deptTreeResume name="TREE_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewHistoryOrgInfo" 
		clickFun="1" resumeNo="${RESUME_NO}"
		style="float:left; display:block; margin:10px; overflow:auto;width:310px; height:500px; border:solid 1px #CCC; line-height:21px; background:#FFF;" selected="${DEPTNO}"/>
	</div>
