<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

  function searchGroupName()
	{  
		var SY_ROLE_GROUP_NAME = encodeURI($("#SY_ROLE_GROUP_NAME").val());
		    SY_ROLE_GROUP_NAME = encodeURI(SY_ROLE_GROUP_NAME);
		var role_GSOD_NAME = encodeURI($("#role_GSOD_NAME").val());
		    role_GSOD_NAME = encodeURI(role_GSOD_NAME);
		var href="/sys/rightsManagement/addGsodRoleGroupInfoView";
		href+="?SY_ROLE_GROUP_NAME="+SY_ROLE_GROUP_NAME;
		href+="&role_GSOD_NAME="+role_GSOD_NAME;
		$("#addGsodRoleGroupInfoView_add").attr("href",href);
		$("#addGsodRoleGroupInfoView_add").click();
	}
	
	function validateCallback_sys2014gsod(form, callback) {
		var $form = $("#addGsodRoleGroupInfo");
		if (!$form.valid()) {
			return false;
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
<a id="addGsodRoleGroupInfoView_add" href="" target="navTab"
	rel="addGsodRoleGroupInfoView" style="display: none;">添加</a>
<div class="pageContent">
<form method="post" id="addGsodRoleGroupInfo" action="/sys/rightsManagement/addGsodRoleGroupInfo" onsubmit="return validateCallback_sys2014gsod(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap">		 
			<dl>
				<dt>&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="sys.gsodrole.title.gsodname"/>:</dt>
				<dd>
					<input type="text" name="role_GSOD_NAME" id="role_GSOD_NAME" value="${role_GSOD_NAME}" class="required"/>
					<input type="hidden" name="ACTIVITY" id="ACTIVITY" value="1"/>
					<input type="hidden" name="flag" value="1"/>
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
	</dl>
			<dl>
				<table class="table" width="50%" layoutH="206">
					<thead>
						<tr>
							<th width="50"><input type="checkbox"
								name="check_ROLE_GROUP_NO" id="check_ROLE_GROUP_NO"
								class="checkboxCtrl" group="check_gsod"></th>
							<th width="150"><spring:message
								code="sys.rights.title.privilegeGroupName" /><!--权限组名称--></th>
							<th width="150">法人</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${rolesGroupList}" var="item" varStatus="i">
							<tr target="ROLE_GROUP_NO"
								rel="${item.ROLE_GROUP_NO}&CPNY_ID=${item.CPNY_ID}">
								<td class="td_center" style="white-space: nowrap"><input
									type="checkbox" id="check_gsod" name="check_gsod"
									value="${item.ROLE_GROUP_NO},${item.CPNY_ID }" /></td>
								<td>${item.GROUP_NAME}</td>
								<td>${item.CPNY_ID}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</dl>
		</div>
		<div class="formBar">
		<ul>
			<li>
			<div class="buttonActive">
			<div class="buttonContent">
			<button type="submit"><spring:message
				code="public.title.submit" /><!--提交--></button>
			</div></div>
			</li>
			<li>
			<div class="button">
			<div class="buttonContent">
			<button type="button" class="close"><spring:message
				code="public.title.cancle" /><!--取消--></button>
			</div></div>
			</li>
		</ul>
		</div>
</form>
</div>