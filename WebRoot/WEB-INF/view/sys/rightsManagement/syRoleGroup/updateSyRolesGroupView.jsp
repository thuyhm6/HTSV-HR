<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <script type="text/javascript">
var cpnyInitId="${roleGroup.CPNY_ID}";
$.ajax({  
    async : false,  
    cache:false,  
    type: 'POST',  
    dataType : "json",  
    url: "/sys/rightsManagement/getRolesIfChecked?cpnyInitId="+cpnyInitId+"&NO=${roleGroup.ROLE_GROUP_NO}"+"&SYS_TYPE="+document.getElementById("SYS_TYPE").value,//请求的action路径  
    error: function () {//请求失败处理函数  
        alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
    },  
    success:function(data){ //请求成功后处理函数。    
    	//alert(data);   //把后台封装好的简单Json格式赋给treeNodes
    	var str="";
    	for(var i=0;i<data.length;i++){
			if(i%5==0){
				str+="<br/>";
			}
			var flagStr='';
			if(data[i].CHECKED=='1'){
				flagStr='checked="true" ';
			}
			//alert(flagStr);
			str+='<input type="checkbox" name="ROLE_NOS"  value="'+data[i].ROLE_NO+'" '+flagStr+'/>'+data[i].ROLENAME+"&nbsp;&nbsp;";
        }
        document.getElementById("roles").innerHTML=str;
    }  
});
function addCpnyIdRoles(obj){
	$.ajax({  
	    async : false,  
	    cache:false,  
	    type: 'POST',  
	    dataType : "json",  
	    url: "/sys/rightsManagement/getRolesIfChecked?cpnyInitId="+obj.value+"&NO=${roleGroup.ROLE_GROUP_NO}"+"&SYS_TYPE="+document.getElementById("SYS_TYPE").value,//请求的action路径  
	    error: function () {//请求失败处理函数  
	        alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
	    },  
	    success:function(data){ //请求成功后处理函数。    
	    	var str="";
	    	for(var i=0;i<data.length;i++){
				if(i%5==0){
					str+="<br/>";
				}
				var flagStr='';
				if(data[i].CHECKED=='1'){
					flagStr='checked="true" ';
				}
				str+='<input type="checkbox" name="ROLE_NOS"  value="'+data[i].ROLE_NO+'" '+flagStr+'/>'+data[i].ROLENAME+"&nbsp;&nbsp;";
	        }
	        document.getElementById("roles").innerHTML=str;
	    }  
	});
}
 
</script>
<div class="pageContent">
	<form id="RolesForm" method="post" action="/sys/rightsManagement/syRoleGroup/saveOrUpdateSyRolesGroupInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" id="SYS_TYPE" name="SYS_TYPE" value="${SYS_TYPE }"/>
		<div class="pageFormContent nowrap" layoutH="67">
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
				<dd>
				<input  type="hidden" id="CPNY_ID" NAME="CPNY_ID" value="${CPNY_ID }"></input>
				${CPNY_ID }
				<!--<select name="CPNY_ID" id="CPNY_ID"  onchange="addCpnyIdRoles(this);">
					<c:forEach items="${cpnyList}" var="cpny">
						<option value="${cpny.CPNY_ID}" <c:if test="${cpny.CPNY_ID eq roleGroup.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
					</c:forEach>
				</select>
				--></dd>
				<input type="hidden" name="ROLE_GROUP_NO" value="${roleGroup.ROLE_GROUP_NO}">
			 <input type="hidden" name="NO" value="${roleGroup.ROLE_GROUP_NO}">
			</dl>
			 
		    <ait:SyLanguage languageNo="${roleGroup.ROLE_GROUP_NO}"/>
			<dl>
				<dt><spring:message code="sys.rights.title.defaltPrivilegeGroup"/><!--默认权限组-->:</dt>
				<dd>
					<select name="JOIN_DEFAULT" id="JOIN_DEFAULT" >
						<option value="1" <c:if test="${roleGroup.JOIN_DEFAULT eq '1'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="0" <c:if test="${roleGroup.JOIN_DEFAULT eq '0'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
			<dl style="height:auto">
				<dt style="height:auto"><spring:message code="sys.rights.title.privilege"/><!--权限-->: </dt>
				<dd id="roles" style="height:auto">
					<!--<c:forEach items="${roleList}" var="roles">
                		<input type="checkbox" name="ROLE_NOS"  value="${roles.ROLE_NO}"
                			<c:if test="${ roles.CHECKED == 1}">checked=true</c:if> 
                		/>${roles.ROLENAME}&nbsp;&nbsp;
                	</c:forEach>-->
	            </dd>
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