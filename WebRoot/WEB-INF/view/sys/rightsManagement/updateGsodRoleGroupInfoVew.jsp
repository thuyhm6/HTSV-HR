<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

  function searchGroupName()
	{  
		var SY_ROLE_GROUP_NAME = encodeURI($("#SY_ROLE_GROUP_NAME").val());
		    SY_ROLE_GROUP_NAME = encodeURI(SY_ROLE_GROUP_NAME);
		var ROLE_GSOD_NAME = encodeURI($("#role_GSOD_NAME").val());
		    ROLE_GSOD_NAME = encodeURI(ROLE_GSOD_NAME);
		var href="/sys/rightsManagement/updateGsodRoleGroupInfoVew";
		href+="?SY_ROLE_GROUP_NAME="+SY_ROLE_GROUP_NAME;
		href+="&role_GSOD_NAME="+ROLE_GSOD_NAME;
		$("#updateGsodRoleGroupInfoVew_update").attr("href",href);
		$("#updateGsodRoleGroupInfoVew_update").click();
		//navTabNum(href,'addGsodRoleGroupInfoView','添加');
		//alert();
	}
	
	function validateCallback_sys_gsod(form, callback) {
	var $form = $("#updateGsodRoleGroupInfo");
	if (!$form.valid()) {
		return false;
	}
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}

/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}
</script>
<a id="updateGsodRoleGroupInfoVew_update" href="" target="navTab" rel="updateGsodRoleGroupInfoVew" style="display:none;">修改</a>
<div class="pageContent">
<form method="post" id="updateGsodRoleGroupInfo" action="/sys/rightsManagement/updateGsodRoleGroupInfo" onsubmit="return validateCallback_sys_gsod(this,navTabAjaxDone);">
<input type="hidden" name="ACTIVITY" id="ACTIVITY" value="1"/>
<input type="hidden" name="GROUPNO" value="${GROUPNO}"/>
<input type="hidden" name="flag" value="1"/>
		<div class="pageFormContent nowrap" layoutH="67">		 
			<dl>
				<dt><spring:message code="sys.gsodrole.title.gsodname"/>:</dt>
				<dd>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="role_GSOD_NAME" id="role_GSOD_NAME" value="${ROLE_GSOD_NAME }" readonly="readonly"/>
				</select>
				</dd>
			</dl>	
			<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.rights.title.privilegeGroupName"/><!--权限组名称-->：
				</td>
				<td>
				    <input type="text" id="SY_ROLE_GROUP_NAME" name="SY_ROLE_GROUP_NAME" value="${SY_ROLE_GROUP_NAME}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="searchGroupName();">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>		 
			<dl>
	            <table class="table" width="50%" layoutH="206">
		       <thead>
			      <tr>
			      <th width="50"><input type="checkbox" name="check_ROLE_GROUP_NO"
					id="check_ROLE_GROUP_NO" class="checkboxCtrl" group="check_gsod">
				</th>
				<th width="150"><spring:message code="sys.rights.title.privilegeGroupName"/><!--权限组名称--></th>
				<th width="150">法人</th>
			     </tr>
		      </thead>
		      <tbody>
			    <c:forEach items="${rolesGroupList}" var="item" varStatus="i">
				<tr target="ROLE_GROUP_NO" rel="${item.ROLE_GROUP_NO}&CPNY_ID=${item.CPNY_ID}">
					<td>
					    <input type="checkbox" name="check_gsod" value="${item.ROLE_GROUP_NO},${item.CPNY_ID }"
					    <c:forEach items="${gsodRoleGroupList}" var="itemlist" varStatus="j">
											<c:if test="${item.ROLE_GROUP_NO eq itemlist.SY_ROLE_GROUP_NO}">checked=true</c:if>
											</c:forEach>
					    />&nbsp;&nbsp;
					   </td>
					   <td>${item.GROUP_NAME}</td>
					</td>
					<td>${item.CPNY_ID}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
			</dl> 		
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>