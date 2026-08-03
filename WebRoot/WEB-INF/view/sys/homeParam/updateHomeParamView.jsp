<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
function changeCpnyIDSy0206(cpnyId){
	$.ajax({  
	    async : false,  
	    cache:false,  
	    type: 'POST',  
	    dataType : "json",  
	    url: "/sys/homeParam/getRoleListByCpnyId?CPNY_ID="+cpnyId+"&PARAM_NO=${homeParam.PARAM_NO}",//请求的action路径  
	    error: function () {//请求失败处理函数  
	       alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
	    },  
	    success:function(data){ //请求成功后处理函数。   
	    	//zNodes_org0103_add = data;   //把后台封装好的简单Json格式赋给treeNodes
	    	//alert(data[0].ROLENAME);
	    	var roleIdStr="<table><tr>";
	    	for(var i=0;i<data.length;i++){
		    	var check=data[i].CHECKED;
		    	if(check=="true"){
		    		if(i==data.length-1){
			    		roleIdStr+='<td colspan='+(5-(i%5)+1)+' style="padding:5px"><input name="ROLE_IDS" checked="true" value="'+data[i].ROLE_ID+'"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr>";
				    }else{
				    	if((i+1)%5==0){
				    		roleIdStr+='<td style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'" checked="true"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr><tr>";
				    	}else{
				    		roleIdStr+='<td style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'" checked="true"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td>";
					    }
					}
			    }else{
			    	if(i==data.length-1){
			    		roleIdStr+='<td colspan='+(5-(i%5)+1)+' style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr>";
				    }else{
				    	if((i+1)%5==0){
				    		roleIdStr+='<td style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr><tr>";
				    	}else{
				    		roleIdStr+='<td style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td>";
					    }
					}
				}
		    }
	    	document.getElementById("ROLE_ID_DD").innerHTML=roleIdStr;
	    }  
	}); 
}
$(document).ready(function(){
	changeCpnyIDSy0206("${CPNY_ID}"); 
});
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/sys/homeParam/updateHomeParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			 <input type="hidden" name="PARAM_NO" value="${homeParam.PARAM_NO}"/>
			 <input type="hidden" name="NO" value="${homeParam.PARAM_NO}"/>
			 <dl>
			 	<dt><spring:message code="sys.essParam.title.legalPerson"/><!--法人-->:</dt>
			 	<dd>
			 		<select class="combox" name="PARAM_CPNY_ID" onchange="changeCpnyIDSy0206(this.value)">
						 <c:forEach items="${companyList}" var="company" varStatus="var">
								<option value="${company.CPNY_ID}" <c:if test="${company.CPNY_ID eq CPNY_ID}">selected</c:if>>${company.CONTENT}</option>
						 </c:forEach>
					 </select>
			 	</dd>
			 </dl>
			 <ait:SyLanguage languageNo="${request_PARAM_NO}"/>
			 <dl>
				 <dt><spring:message code="sys.essParam.title.ifEnabled"/><!--是否启用--></dt>
				 <dd>
					  <select class="combox" name="ACTIVITY">
						<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
					 </select>
				 </dd>
			 </dl>
			 <dl>
			 	<dt><spring:message code="sys.homeParam.title.privilegeId"/><!--权限ID--></dt>
			 	<dd id="ROLE_ID_DD">
			 	  &nbsp;
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