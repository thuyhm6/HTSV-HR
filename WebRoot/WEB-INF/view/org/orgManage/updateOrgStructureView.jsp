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
function validateCallbackorg0103(form, callback) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	var endValue=document.getElementById("endEddate").value;
	if(endValue&&endValue!=""){
		var flagStr="N";
		$.ajax({  
		    async : false,  
		    cache:false,  
		    type: 'POST',  
		    dataType : "json",  
		    url: "/org/orgManage/validateEmpExist?DEPTNO=${organizationInfo.DEPTNO}",//请求的action路径  
		    error: function () {//请求失败处理函数  
			    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');
		    },  
		    success:function(data){ //请求成功后处理函数。  
		    	flagStr=data.flagYn;
		    	return false; //把后台封装好的简单Json格式赋给treeNodes
		    }  
		}); 
		if(flagStr=="N"){
			alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');
			alert('<spring:message code="alert.org.DONT_SET_DEPTENDDATE"/>');
			return false;
		}
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
//加载部门树结束
</script>
<div class="pageContent">
	<form method="post" action="/org/orgManage/updateOrgStructureInfo" class="pageForm required-validate" onsubmit="return validateCallbackorg0103(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><spring:message code="org.orgManage.title.deptNo"/><!--部门编号--></dt>
				<dd>${organizationInfo.DEPTID}
					<input name="deptId" type="hidden" value="${organizationInfo.DEPTID}"/>
					<input name="DEPTNO" type="hidden" value="${organizationInfo.DEPTNO}"/>
				</dd>
			</dl>
		 	<ait:SyLanguage languageNo="${organizationInfo.DEPTNO}" deptNameYn="Y"/>
			<dl>
				<dt><spring:message code="org.orgManage.title.ownedCompany"/><!--所属法人--></dt>
				<dd> 
				<select name="parentCpnyId" disabled="disabled">
					<option value="${organizationInfo.CPNY_ID}">${organizationInfo.CPNY_NAME}</option>
				</select>
				</dd>
			</dl>
		    <dl>
				<dt><spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分--></dt>
				<dd> 
					<select name="DEPT_DISTINGUISH_NO"  class="combox">
					<option value=""><spring:message code="org.title.PLEASE_SELECT"/><!-- 请选择 --></option>
						 <c:forEach items="${distinguishList}" var="distinguish">
						 	<option value="${distinguish.CODE_NO}" <c:if test="${distinguish.CODE_NO eq organizationInfo.DEPT_DISTINGUISH_NO}">selected</c:if>>${distinguish.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptType"/><!--部门类型--></dt>
				<dd> 
					<select name="DEPT_TYPE"  class="combox">
					<option value=""><spring:message code="org.title.PLEASE_SELECT"/><!-- 请选择 --></option>
						 <c:forEach items="${deptTypeList}" var="deptType">
						 	<option value="${deptType.CODE_NO}"  <c:if test="${deptType.CODE_NO eq organizationInfo.DEPT_TYPE}">selected</c:if>>${deptType.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.ownArea"/><!--所属地区--></dt>
				<dd> 
					<select name="WORK_AREA"  class="combox">
					<option value=""><spring:message code="org.title.PLEASE_SELECT"/><!-- 请选择 --></option>
						 <c:forEach items="${areaList}" var="area">
						 	<option value="${area.CODE_NO}" <c:if test="${area.CODE_NO eq organizationInfo.WORK_AREA}">selected</c:if>>${area.CODE_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></dt>
				<dd>${organizationInfo.DATE_CREATED}</dd>
			</dl>
			 <dl>
				<dt><spring:message code="org.orgManage.title.deptEndTime"/><!--部门结束时间--></dt>
				<dd><input id="endEddate" type="text" name="endEddate" class="date" readonly="true" value="${organizationInfo.DATE_ENDED}"/>
				<a class="inputDateButton"><spring:message code="org.orgManage.title.choose"/><!--选择--></a></dd>
			</dl>
			<dl>
				<dt><spring:message code="org.orgManage.title.parentDept"/><!--上级部门-->
				<c:if test="${organizationInfo.PARENT_DEPT_NO!='0'}" >
					<input id="deptName_org0103_update_0" type="text" onclick="showTree_org0103_update(this);" readonly class="required" value="${organizationInfo.PARENT_DEPT_NAME_ZH}"/> 
				    <input id="seach_DEPTNO_0" name="PARENT_DEPT_NO" type="hidden" sysLong="sy0482"  value="${organizationInfo.PARENT_DEPT_NO}"/>	
				</c:if>
				</dt>
				<dd>
				<input type="hidden" name="DEPT_LEVEL" id="DEPT_LEVEL" value="${organizationInfo.DEPT_LEVEL}"/>
				<c:if test="${organizationInfo.PARENT_DEPT_NO eq '0'}" >
					<input type="hidden" name="PARENT_DEPT_NO" value="0"/>
				</c:if>
				<div id="deptContent_org0103_update" style="display:none;position:absolute; z-index:1;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
						<ul id="deptTree_org0103_update" class="ztree" style="margin-top:0; width:300px;">
						</ul>
					</div>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="org.orgManage.title.post"/><!--职务--></dt>
				<dd><table width="100%">
	    			 <tr align="center" width="100%"> 
				       <c:forEach items="${postList}" var="vList" varStatus="i">
				             <c:choose>
				              	<c:when test="${i.count % 5 == 0}">
						           <td width="20%" align="left">
						              <input name="isChecked" id="isChecked_${vList.POST_NO}" value="${vList.POST_NO}"  type="checkbox" style="border:0px"
						              <c:if test="${ vList.ISCHOOSE eq '1'}">checked=true</c:if> 
						              />
						              	${vList.POST_NAME}
						            </td>	
							    </tr>
								</c:when>
				  				<c:otherwise>
							         <td width="20%" align="left">
							           <input name="isChecked" id="isChecked_${vList.POST_NO}" value="${vList.POST_NO}" type="checkbox" style="border:0px"
							           <c:if test="${ vList.ISCHOOSE eq '1'}">checked=true</c:if> 
							           />					              		
							             ${vList.POST_NAME}
							          </td>		  							
				  				</c:otherwise>
					      </c:choose>	
						</c:forEach>
		    	</table></dd>
			</dl> 
			 
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
