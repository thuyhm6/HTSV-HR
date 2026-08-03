<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//关闭、打开所有节点
$(document).ready(function(){
	$("#orgCloseOpen",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewComposeOrgTree"); 
		treeObj.expandAll(true);
	});
	$("#orgCloseClose",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewComposeOrgTree"); 
		treeObj.expandAll(false);
	});
	$("#viewComposeOrgResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewComposeOrgForm",navTab.getCurrentPanel()).submit();
	});
});
//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewModifyOrgInfoTree?DEPTNO=&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgInfo_tree_unit');
	openOnRight('/org/orgManage/viewModifyOrgInfo?SON_FLAG=1&DEPTNO=&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgInfo_right_unit');
});
//点击部门刷新右边页面
function onClick_viewComposeOrgTree(event, treeId, treeNode){
	openOnRight('/org/orgManage/viewModifyOrgInfo?SON_FLAG=1&DEPTNO=' + treeNode.DEPTNO + '&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgInfo_right_unit');
}
//模糊搜索部门树，并打开节点
function srachOrg(){
	if($("#viewComposeOrgResumeNo_deptName").val() != ""){
		var treeObj = $.fn.zTree.getZTreeObj("viewComposeOrgTree"); 
		var treeNodes = treeObj.getNodesByParamFuzzy("DEPTNAME",$("#viewComposeOrgResumeNo_deptName").val()); 
		for(var i=0;i<treeNodes.length;i++){
			treeObj.expandNode(treeNodes[i].getParentNode());
		}
	}else{
		alertMsg.info('<spring:message code="org.title.INPUT_SELECTINFO" />');
	}
}
</script>
<div id="orgTree" style="width:330px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;">
	<div style=" float:left; display:block; margin:10px; overflow:hidden;width:300px; height:60px; border:solid 1px #CCC; background:#FFF;">
		<form id="viewComposeOrgForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewComposeOrg" method="post" >
			<table class="searchContent">
				<tr style="height:30px;line-height:30px;">
					<td width="100px;" class="td_center" style="overflow:hidden; white-space:pre;"><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
					<td width="200px;">
						<select id="viewComposeOrgResumeNo" name="RESUME_NO">
							<c:forEach items="${orgResumeList}" var="result">
								<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr style="height:30px;line-height:30px;">
					<td class="td_center" style="overflow:hidden; white-space:pre;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					<td>
						<input type="text" id="viewComposeOrgResumeNo_deptName" name="deptName" size="15"/>
						<a class="w_button" onclick="srachOrg();"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style=" float:left; display:block; margin-left:10px; overflow:hidden;width:300px;background:#FFF;">
		<input type="radio" id="orgCloseOpen" name="orgClose" value="open"/>&nbsp;&nbsp;<spring:message code="org.title.OPEN_ALL" /><!-- 全部打开 -->&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgCloseClose" name="orgClose" value="close"/>&nbsp;&nbsp;<spring:message code="org.title.CLOSE_ALL" /><!-- 全部关闭 -->
	</div>
	<div id="viewModifyOrgInfo_tree_unit">
	</div>
</div>
<div style="display:block; margin:10px;margin-left:330px;">
	<div id="viewModifyOrgInfo_right_unit">
	</div>
	<div id="viewModifyOrgEmpInfo_unit">
	</div>
</div>
