<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
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

</script>
<div class="pageContent">
	<form method="post" id="updateOrgInfo"
		action="/org/orgManage/updateOrgInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.deptNo" />
					<!--部门编号-->
				</dt>
				<dd>
				${organizationInfo.DEPTNO}
				<input type="hidden" name="DEPTNO" id="DEPTNO" value="${organizationInfo.DEPTNO }"/>
				</dd>
			</dl>
			<ait:SyLanguage1 languageNo="${organizationInfo.DEPTNO}"
				deptNameYn="Y" />
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.ownedCompany" />
					<!--所属法人-->
				</dt>
				<dd>
				  ${organizationInfo.CPNY_ID}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptType" /><!-- 部门类型 --></dt>
				<dd>
				<c:if test="${organizationInfo.DEPT_TYPE eq 'payarea'}"><spring:message code="org.title.BIGAREA" /><!-- 大区 --></c:if>
				<c:if test="${organizationInfo.DEPT_TYPE eq 'branch'}"><spring:message code="org.title.OFFICE_BRANCH" /><!-- 支社 --></c:if>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.deptCode" /><!-- 部门CODE --></dt>
				<dd>
				<c:if test="${organizationInfo.DEPT_TYPE eq 'payarea'}">${organizationInfo.PAY_AREA_CD }</c:if>
					<c:if test="${organizationInfo.DEPT_TYPE eq 'branch'}">${organizationInfo.ACC_ORG_CODE }</c:if>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.FOR_PACAL" /><!-- 工资所属 --></dt>
				<dd>
				    ${organizationInfo.FOR_PACAL}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.DEPT_PAYAREA" /><!-- 大区属性 --></dt>
				<dd>
				${organizationInfo.DEPT_PAYAREA}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.DEPT_BRANCH" /><!-- 支社属性 --></dt>
				
				<dd>
					${organizationInfo.DEPT_BRANCH}
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.orderNo" />
					<!--排序-->
				</dt>
				<dd>
				<input type="text" name="ORDERNO" id="ORDERNO" value="${organizationInfo.ORDERNO }"/>
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
