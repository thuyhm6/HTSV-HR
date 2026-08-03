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
			    		roleIdStr+='<td colspan='+(5-(i%5)+1)+' style="padding:5px"><input name="ROLE_IDS" checked="true"  value="'+data[i].ROLE_ID+'"  type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr>";
				    }else{
				    	if((i+1)%5==0){
				    		roleIdStr+='<td style="padding:5px"><input name="ROLE_IDS"  value="'+data[i].ROLE_ID+'" checked="true"   type="checkbox" style="border:0px"/>'+data[i].ROLENAME+"</td></tr><tr>";
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
$(document).ready(function(){
	changeEmpTypeCode0206("${CPNY_ID}"); 
});
function changeEmpTypeCode0206(cpnyId){
	$.ajax({  
	    async : false,  
	    cache:false,  
	    type: 'POST',  
	    dataType : "json",  
	    url: "/sys/homeParam/getEmpTypeListByCpnyId?CPNY_ID="+cpnyId+"&PARAM_NO=${homeParam.PARAM_NO}",//请求的action路径  
	    error: function () {//请求失败处理函数  
	       alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');  
	    },  
	    success:function(empTypeList){ //请求成功后处理函数。   
	    	var empTypeStr="<table><tr>";
	    	for(var i=0;i<empTypeList.length;i++){
	    		var check=empTypeList[i].CHECKED;
		    	if(check=="true"){
		    		if(i==empTypeList.length-1){
		    			empTypeStr+='<td colspan='+(5-(i%5)+1)+' style="padding:5px"><input name="EMP_TYPE_CODES" checked="true" value="'+empTypeList[i].EMP_TYPE_CODE+'" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td></tr>";
				    }else{
				    	if((i+1)%5==0){
				    		empTypeStr+='<td style="padding:5px"><input name="EMP_TYPE_CODES" value="'+empTypeList[i].EMP_TYPE_CODE+'" checked="true" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td></tr><tr>";
				    	}else{
				    		empTypeStr+='<td style="padding:5px"><input name="EMP_TYPE_CODES" value="'+empTypeList[i].EMP_TYPE_CODE+'" checked="true" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td>";
					    }
					}
			    }else{
			    	if(i==empTypeList.length-1){
			    		empTypeStr+='<td colspan='+(5-(i%5)+1)+' style="padding:5px"><input name="EMP_TYPE_CODES"  value="'+empTypeList[i].EMP_TYPE_CODE+'" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td></tr>";
				    }else{
				    	if((i+1)%5==0){
				    		empTypeStr+='<td style="padding:5px"><input name="EMP_TYPE_CODES" value="'+empTypeList[i].EMP_TYPE_CODE+'" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td></tr><tr>";
				    	}else{
				    		empTypeStr+='<td style="padding:5px"><input name="EMP_TYPE_CODES" value="'+empTypeList[i].EMP_TYPE_CODE+'" type="checkbox" style="border:0px"/>'+empTypeList[i].EMP_TYPE_NAME+"</td>";
					    }
					}
				}
		    	
		    }
	    	document.getElementById("EMP_TYPE_CODE_DD").innerHTML=empTypeStr;
	    }  
	}); 
}
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/sys/homeParam/updateHomeParamCpnyInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			 <input type="hidden" name="PARAM_NO" value="${homeParam.PARAM_NO}"/>
			 <input type="hidden" name="NO" value="${homeParam.PARAM_NO}"/>
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
			 <dl>
			 	<dt><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/><!--员工类型-->
			 		<font size="2" color="red">(*注：目前只用于生日列表。)</font>
			 	</dt>
			 	<dd id="EMP_TYPE_CODE_DD">
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
