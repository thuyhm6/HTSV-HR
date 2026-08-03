<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//关闭、打开所有节点
$(document).ready(function(){
	$("#orgCloseOpen",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("${targetRel}"); 
		treeObj.expandAll(true);
	});
	$("#orgCloseClose",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("${targetRel}"); 
		treeObj.expandAll(false);
	});
	$("#${targetRel}ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#${targetRel}Form",navTab.getCurrentPanel()).submit();
	});
	//模糊搜索部门树，并打开节点
	$("#viewCommonOrgTreeSrachOrg",navTab.getCurrentPanel()).click(function(){
		if($("#viewCommonOrgTreeResumeNo_deptName",navTab.getCurrentPanel()).val() != ""){
			var treeObj = $.fn.zTree.getZTreeObj("${targetRel}"); 
			var treeNodes = treeObj.getNodesByParamFuzzy("DEPTNAME",$("#viewCommonOrgTreeResumeNo_deptName",navTab.getCurrentPanel()).val()); 
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
	openOnRight('/org/orgManage/${targetRel}?DEPTNO=&RESUME_NO=' + $("#${targetRel}ResumeNo").val(),'${targetRel}_right_unit');
});
</script>
<div id="orgTree" style="width:340px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;">
	<div style=" float:left; display:block; margin:10px; overflow:hidden;width:310px; height:60px; border:solid 1px #CCC; background:#FFF;">
		<form id="${targetRel}Form" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewCommonOrgTreeInfo" method="post" >
			<table class="searchContent">
				<tr style="height:30px;line-height:30px;">
					<td width="100px;" class="td_center" style="overflow:hidden; white-space:pre;"><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
					<td width="200px;">
						<select id="${targetRel}ResumeNo" name="RESUME_NO">
							<c:forEach items="${orgResumeList}" var="result">
								<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr style="height:30px;line-height:30px;">
					<td class="td_center" style="overflow:hidden; white-space:pre;"><spring:message code="org.title.dept" /><!-- 部门 --></td>
					<td>
						<input type="text" id="viewCommonOrgTreeResumeNo_deptName" name="deptName" size="15"/>
						<a class="w_button" id="viewCommonOrgTreeSrachOrg" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a>
						<input type="hidden" name="targetRel" value="${targetRel}"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style=" float:left; display:block; margin-left:10px; overflow:hidden;width:300px;background:#FFF;">
		<input type="radio" id="orgCloseOpen" name="orgClose" value="open"/>&nbsp;&nbsp;<spring:message code="org.title.OPEN_ALL" /><!-- 全部打开 -->&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgCloseClose" name="orgClose" value="close"/>&nbsp;&nbsp;<spring:message code="org.title.CLOSE_ALL" /><!-- 全部关闭 -->
	</div>
	<div id="${targetRel}_tree_unit">
		<ait:deptTreeResume name="TREE_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="${targetRel}" 
		resumeNo="${RESUME_NO}"
		style="float:left; display:block; margin:10px; overflow:auto;width:310px; height:500px; border:solid 1px #CCC; line-height:21px; background:#FFF;" selected="${DEPTNO}"/>
	</div>
</div>
<div id="${targetRel}_right_unit" style="display:block;margin-top:5px;">
</div>
