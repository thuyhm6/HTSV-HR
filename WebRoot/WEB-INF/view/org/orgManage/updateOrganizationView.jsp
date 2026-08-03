<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/**
 * 开始加载部门树
 */
var setting_org0103_update = { 
		view: {
		  dblClickExpand: false,
		  showLine: true,
		  selectedMulti: false,
		  expandSpeed: "fast"
		 },
		 data: {
		   key: {
		     children:"children",
		     name:"CONTENT",
		     title:""
		   },
		   simpleData: {
		       enable:true,
		       idKey:"DEPTNO",
		       pIdKey:"PARENT_DEPT_NO",
		       rootPId: ""  
		   }
		 },
		 callback: {
			 beforeClick: function(treeId, treeNode) {
			    var deptName = $("#deptName_org0103_update_0").val();
				if(deptName == treeNode.CONTENT){
					 $("#deptName_org0103_update_0").attr("value", '');
					 $("#seach_DEPTNO_0").attr("value",'');
			    }else{
					 $("#deptName_org0103_update_0").attr("value", treeNode.CONTENT);
					 $("#seach_DEPTNO_0").attr("value",treeNode.DEPTNO);
				}
				document.getElementById("DEPT_LEVEL").value=treeNode.DLEVEL+1;
			    //deptChange_org0103_update("",treeNode.DEPTNO);
			 }
		 }
		};
var zNodes_org0103_update;
$.ajax({  
    async : false,  
    cache:false,  
    type: 'POST',  
    dataType : "json",  
    url: "/sys/arAffirmPost/getOrgInfoTreeData",//请求的action路径  
    error: function () {//请求失败处理函数  
        alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');  
    },  
    success:function(data){ //请求成功后处理函数。   
    	zNodes_org0103_update = data;   //把后台封装好的简单Json格式赋给treeNodes
    }  
}); 
var nodeNameId='';
function showTree_org0103_update(obj) {
		nodeNameId=obj.id;
		var cityObj = $("#"+obj.id);
		var cityOffset = $("#"+obj.id).offset();
		$("#deptContent_org0103_update").offset(
				{top:cityOffset.top +30+ "px",
				 left:cityOffset.left + cityObj.outerHeight()+ "px" 
				 }).slideDown("fast");$("body").bind("mousedown", onBodyDown_org0103_update);
}
function hideMenu_org0103_update() {
	 	 $("#deptContent_org0103_update").fadeOut("fast");
	 	 $("body").unbind("mousedown", onBodyDown_org0103_update);
}
function onBodyDown_org0103_update(event) {
	 var str=event.target.id;
	 if(str.indexOf("switch")==-1&&(str.indexOf("_span")>-1||str.indexOf("_ico")>-1)){
		 if(str!="deptContent_org0103_update"){
			 hideMenu_org0103_update();
		 }
	 }
}
$(document).ready(function(){
	$.fn.zTree.init($("#deptTree_org0103_update"), setting_org0103_update, zNodes_org0103_update);
});
// function validateCallbackorg0103(form, callback) {
	
// 	var $form = $("#updateOrganizationInfo");
// 	if (!$form.valid()) {
// 		    return false;
// 	}
// 	$.ajax({
// 			type: form.method || 'POST',
// 			url:$form.attr("action"),
// 			data:$form.serializeArray(),
// 			dataType:"json",
// 			cache: false,
// 			success: callback || DWZ.ajaxDone,
// 			error: DWZ.ajaxError
// 		});
// 		return false;
// }
//加载部门树结束
</script>
<div class="pageContent">
	<form method="post" id="updateOrganizationInfo"
		action="/org/orgManage/updateOrganizationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.deptNo" />
					<!--部门编号-->
				</dt>
				<dd>
					${organizationInfo.DEPTID} <input name="deptId" type="hidden"
						value="${organizationInfo.DEPTID}" /> <input name="DEPTNO"
						type="hidden" value="${organizationInfo.DEPTNO}" />
				</dd>
			</dl>
			<ait:SyLanguage languageNo="${organizationInfo.DEPTNO}"
				deptNameYn="Y" />
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.ownedCompany" />
					<!--所属法人-->
				</dt>
				<dd>
					<select name="parentCpnyId" disabled="disabled">
						<option value="${organizationInfo.CPNY_ID}">${organizationInfo.CPNY_NAME}</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptType" /></dt>
				<dd>
					<select name="DEPT_TYPE">
						<option value=""></option>
						<option value="payarea"
							<c:if test="${organizationInfo.DEPT_TYPE eq 'payarea'}">selected="selected"</c:if>><spring:message code="org.title.BIGAREA" /><!-- 大区 --></option>
						<option value="branch"
							<c:if test="${organizationInfo.DEPT_TYPE eq 'branch'}">selected="selected"</c:if>><spring:message code="org.title.OFFICE_BRANCH" /><!-- 支社 --></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.deptCode" /></dt>
				<dd>
					<c:if test="${organizationInfo.DEPT_TYPE eq 'payarea'}">${organizationInfo.PAY_AREA_CD }</c:if>
					<c:if test="${organizationInfo.DEPT_TYPE eq 'branch'}">${organizationInfo.ACC_ORG_CODE }</c:if>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.FOR_PACAL" /></dt>
				<dd>
					<select name="FOR_PACAL">
						<option value=""></option>		
				          <c:forEach items="${payAreaList}" var="dept">
							<option value="${dept.DEPTNO}"
								<c:if test="${dept.DEPTNAME eq organizationInfo.FOR_PACAL }">selected="selected"</c:if>>
								${dept.DEPTNAME }</option>
				  </c:forEach>	
				  </select>	
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.DEPT_PAYAREA" /></dt>
				<dd>
				<select name="DEPT_PAYAREA">
						<option value=""></option>		
				  <c:forEach items="${payAreaList}" var="dept">
							<option value="${dept.DEPTNO}"
								<c:if test="${dept.DEPTNAME eq organizationInfo.DEPT_PAYAREA }">selected="selected"</c:if>>
								${dept.DEPTNAME }</option>
				  </c:forEach>	
				  </select>	
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.DEPT_BRANCH" /><!-- 支社属性 --></dt>
				<dd>
					<input name="DEPT_BRANCH" id="DEPT_BRANCH"
						value="${organizationInfo.DEPT_BRANCH}" type="text" />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.DEPT_FUNCTION" /><!-- DEPT_FUNCTION --></dt>
				<dd>
					<select name="DEPT_FUNCTION">
						<option value=""></option>
						<c:forEach items="${functionList}" var="dept">
							<option value="${dept.DEPT_FUNCTION}"
								<c:if test="${dept.CODE_NO eq organizationInfo.DEPT_FUNCTION }">selected="selected"</c:if>>
								${dept.CODE_NAME }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.deptBeginTime" />
					<!--部门成立时间-->
				</dt>
				<dd>${organizationInfo.DATE_CREATED}</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.deptEndTime" />
					<!--部门结束时间-->
				</dt>
				<dd>${organizationInfo.DATE_ENDED}</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.parentDept" />
				</dt>
				<dd>
					<!--上级部门-->
					<input type="hidden" name="DEPT_LEVEL" id="DEPT_LEVEL"
						value="${organizationInfo.DEPT_LEVEL}" />
					<c:if test="${organizationInfo.PARENT_DEPT_NO!='0'}">
				 <ait:deptList name="PARENT_DEPT_NO" cpnyId="${organizationInfo.CPNY_ID}" limit="hr" id="viewEmpInfoList_seachDept1"/>
			      <ait:deptTreeIcon name="PARENT_DEPT_NO" cpnyId="${organizationInfo.CPNY_ID}" limit="hr" id="viewEmpInfoList_seachDept1" selected="${organizationInfo.PARENT_DEPT_NO}"/>			
					</c:if>
					<c:if test="${organizationInfo.PARENT_DEPT_NO eq '0'}">
						<input type="hidden" name="PARENT_DEPT_NO" value="0" />
					</c:if>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.orderNo" />
					<!--排序-->
				</dt>
				<dd>
					${organizationInfo.ORDERNO}
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.shifouxianshi" />
					<!--是否显示-->
				</dt>
				<dd>
					<select name="USE_YN" di="USE_YN">
						<option value="Y"
							<c:if test="${organizationInfo.USE_YN eq 'Y'}">selected</c:if>><spring:message code="org.title.display"/></option>
						<option value="N"
							<c:if test="${organizationInfo.USE_YN eq 'N'}">selected</c:if>><spring:message code="org.title.notDisplay"/></option>
					</select>
				</dd>
			</dl>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
