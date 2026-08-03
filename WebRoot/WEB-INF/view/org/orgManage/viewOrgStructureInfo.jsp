<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function checkParentDept(form,navTabId){
	var $form=$(form);
	var parent_deptno = $form.find("#seach_PARENT_DEPT_NO").val();
	if(parent_deptno == ''){
		//alert("部门为必选项，请选择部门！");
		alertMsg.error('<spring:message code="org.title.DEPT_IS_MUST" />');
		$form.find("#seach_PARENT_DEPT_NO").focus();
		return false;
	}
    return true;
}
function doOrgStructureExport(from){
  	var $from =$(from);
  	var url ="/org/orgManage/viewOrgStructureInfoExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expOrgStructureExcel(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewOrgStructureInfo");
  	if(checkParentDept($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doOrgStructureExport($from);}});
    } 
}
function changeDeptStatusByFlag(deptno,flag,navTabId) {
	var $form = $("#viewOrgStructureInfo");
	var message = '<spring:message code="org.title.IS_CLOSE_DEPT" />';
	if(flag==1){
		message = '<spring:message code="org.title.IS_OPEN_DEPT" />';
	}
	alertMsg.confirm(
			""+message,
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : '/org/orgManage/manageOrgStructureInfo?DEPTNO='+deptno+'&FLAG='+flag,
						success : function(responseText) {
							if (responseText == "Y"){
								if(flag == 1){
									alert('<spring:message code="org.title.CLOSE_DEPT_SUCCESS" />');
								}else{
									alert('<spring:message code="org.title.OPEN_DEPT_SUCCESS/>');
								}
								//页面重载
								navTabSearch(document.viewOrgStructureInfo);
							}else if(responseText == "N"){
								if(flag == 1){
									alert('<spring:message code="org.title.CLOSE_DEPT_FILE" />');
								}else{
									alert('<spring:message code="org.title.OPEN_DEPT_FILE/>');
								}
							}else if(responseText == "E"){
								alert('<spring:message code="org.title.OPENDEPT_EXISTEMP/>');
							}
						}
					});
				}
			});
}
</script>
<div class="pageHeader">
	<form id="viewOrgStructureInfo" name="viewOrgStructureInfo" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewOrgStructureInfo" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!--部门-->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:
					</td>
					<td>
						<ait:deptTree name="seach_PARENT_DEPT_NO" limit="hr" selected="${PARENT_DEPT_NO}"/>
						<font color="red">*</font>
					</td>
					<td><!--启用状态-->
						<spring:message code="sys.arAffirmPost.title.ableStatus"/>:
					</td>
					<td>
						<select class="combox" name="seach_ACTIVITY" id="seach_ACTIVITY">
							<option value=""><spring:message code="org.title.PLEASE_SELECT"/><!-- 请选择 --></option>
							<option value="1" <c:if test="${ACTIVITY eq '1' }">selected</c:if>><!--启用-->
								<spring:message code="sys.arAffirmPost.title.able"/>
							</option>
							<option value="0" <c:if test="${ACTIVITY eq '0' }">selected</c:if>><!--不启用-->
								<spring:message code="sys.arAffirmPost.title.enable"/>
							</option>
						</select>
					</td>
					<td>&nbsp;&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive"><div class="buttonContent"><button type="submit">
						<spring:message code="button.search"/><!--查询--></button></div></div>
					</li>
					<li>
					  <a class="button" onclick="expOrgStructureExcel()" title='<spring:message code="org.title.IS_INPUT"/>'>
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           	</li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="500" var="add_height"/>
	<c:set value="/org/orgManage/addOrgStructureView" var="add_Url"/>
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/org/orgManage/updateOrgStructureView?DEPTNO={paramno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th style="text-align: center" width="3%"><spring:message code="org.title.NO"/><!-- 序号 --></th>
				<th style="text-align: left" width="5%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>NO<!--部门NO--></th>
				<th width="12%"><spring:message code="org.orgManage.title.deptName"/><!--部门名称--></th>
				
				<th style="text-align: center" width="5%"><spring:message code="org.title.DEPT_LV"/><!-- 部门等级 --></th>
				<th style="text-align: center" width="8%"><spring:message code="org.orgManage.title.parentDept"/><!--上级部门--></th>
				<th style="text-align: center" width="8%"><spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></th>
				<th style="text-align: center" width="10%"><spring:message code="org.orgManage.title.deptEndTime"/><!--部门结束时间--></th>
				
				<th style="text-align: center" width="5%"><spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分--></th>
				<th style="text-align: center" width="5%"><spring:message code="org.orgManage.title.deptType"/><!--部门类型--></th>
				<th style="text-align: center" width="5%"><spring:message code="org.orgManage.title.ownArea"/><!--所属地区--></th>
				
				<th style="text-align: center" width="5%"><spring:message code="org.title.IS_USE"/><!-- 是否使用 --></th>
				<th style="text-align: center" width="5%"><spring:message code="org.title.OPERATION"/><!-- 操作 --></th>
				
				<th style="text-align: center" width="5%"><spring:message code="org.orgManage.title.createdBy"/><!--添加者--></th>
				<th style="text-align: center" width="7%"><spring:message code="org.title.ADD_DATE"/><!-- 添加日期 --></th>
				<th style="text-align: center" width="5%"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
				<th style="text-align: center" width="7%"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orgStructureInfoList}" var="org" varStatus="i">
				<tr target="paramno" rel="${org.DEPTNO}">
					<td style="text-align: center">${i.index+1}</td>
					<td style="text-align: left">${org.DEPTNO}</td>
					<td>
						<c:if test="${org.DEPT_LEVEL==1}">
								${org.DEPATMENT}
						</c:if>
						<c:if test="${org.DEPT_LEVEL>1}">
							<c:forEach begin="2" end="${org.DEPT_LEVEL}" step="1" >
								&nbsp;&nbsp;&nbsp;
							</c:forEach>${org.DEPATMENT}
						</c:if>
					</td>
					<td style="text-align: center">${org.DEPT_LEVEL}</td>
					<td style="text-align: center">${org.PARENT_DEPT_NAME}</td>
					<td style="text-align: center">${org.DATE_CREATED}</td>
					<td style="text-align: center">${org.DATE_ENDED}</td>
					
					<td style="text-align: center">${org.DEPT_DISTINGUISH_NAME}</td>
					<td style="text-align: center">${org.DEPT_TYPE_NAME}</td>
					<td style="text-align: center">${org.WORK_AREA}</td>
					
					<td style="text-align: center">
						<c:if test="${org.ACTIVITY==1}"><!-- 使用 中-->
							<font color="green"><spring:message code="org.title.IN_USE" /><!-- 使用中 --></font>
						</c:if>
						<c:if test="${org.ACTIVITY==0}"><!-- 已停用 -->
							<font color="red"><spring:message code="org.title.USED" /><!-- 已使用 --></font>
						</c:if>
					</td>
					<td style="text-align: center">
						<c:if test="${org.ACTIVITY==1}"><!-- 结束 -->
							<input type="button" id="turnEnd" name="turnEnd" value='<spring:message code="org.title.END" />' onclick="changeDeptStatusByFlag('${org.DEPTNO}',0,this)"/>
						</c:if>
						<c:if test="${org.ACTIVITY==0}"><!-- 启用 -->
							<input type="button" id="turnUse" name="turnUse" value='<spring:message code="org.title.START" />' onclick="changeDeptStatusByFlag('${org.DEPTNO}',1,this)"/>
						</c:if>
					</td>
					<td style="text-align: center">${org.CREATED_BY}</td>
					<td style="text-align: center">${org.CREATE_DATE}</td>
					<td style="text-align: center">${org.UPDATED_BY}</td>
					<td style="text-align: center">${org.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>