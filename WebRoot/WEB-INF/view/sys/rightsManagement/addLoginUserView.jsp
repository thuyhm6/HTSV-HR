<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var flagStr;
var flagUserNameStr;
var zTree;
var demoIframe;

var setting = {
		
	view: {
		dblClickExpand: true,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	check:{
		autoCheckTrigger:false,
		chkboxType:{"Y":"s","N":"ps"},
		chkStyle:"checkbox",
		enable:true,
		nocheckInherit:true,
		radionType:"level"
	},
	data: {
		key: {
			checked:"CHECKED",
			name: "DEPTNAME",
			open:"true"
		},
		simpleData: {
			enable:true,
			idKey: "DEPTNO",
			pIdKey: "PARENT_DEPT_NO",
			rootPId: ""
		}
	},
	callback: {
		onCheck: function(treeId, treeNode) { 
		var t =$.fn.zTree.getZTreeObj("deptTree_sy0120");
		var nodes = t.getCheckedNodes(); 
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				if(i==0){
					 document.getElementById("deptNos").value=nodes[i].DEPTNO;
				}else{
					 document.getElementById("deptNos").value+=","+nodes[i].DEPTNO;
				}
			}
		}else{
			document.getElementById("deptNos").value="";
		}
	}
	}
};

var zNodes_sy0130_add;
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",  
        url: "/sys/rightsManagement/getLoginUserDeptList?userNo=${loginUserInfo.USER_NO}",//请求的action路径  
        error: function () {//请求失败处理函数  
            alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
        },  
        success:function(data){ //请求成功后处理函数。    
        	zNodes_sy0130_add = data;   //把后台封装好的简单Json格式赋给treeNodes
        }  
    }); 
// 初始调用
$(document).ready(function(){
    //布局
    $("#layout1").ligerLayout({ leftWidth: 180});
	var t = $("#deptTree_sy0120");
	t = $.fn.zTree.init(t, setting, zNodes_sy0130_add);
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("deptNos").value=nodes[i].DEPTNO;
			}else{
				 document.getElementById("deptNos").value+=","+nodes[i].DEPTNO;
			}
		}
	}else{
		document.getElementById("deptNos").value="";
	}
});
function addCpnyIdEditCodeParamView(cpnyId){
	/**
		$.each($('a',$('#parentTree')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
			$(value).attr("href",backHref);
		});*/
	var hrefUrl=document.getElementById("childLink").href;
	var hrefArr=hrefUrl.split("CPNY_ID");
	var newUrl=hrefArr[0]+"CPNY_ID="+cpnyId.value;
	document.getElementById("childLink").href=newUrl;
	
 	document.getElementById("childLink").click();
	
	}

/**
决裁者模糊查询开始
*/
var keyCodeInit=0;
function submitKeyClick_sy0130_add(obj,localName,idcardNo,navTabId,event){
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(5);
		var empNameStr="affirmorName"+empIdStr.substring(5);

   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
							//document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value="";//empName deptName
							document.getElementById("PERSON_ID_EXSIT").innerHTML="<spring:message code='hrm.empinfo.nobody'/>";//empName deptName 查无此人
						}
						if(jsonObject.perCnt>1 ){
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/affirm/viewAffirmorsList?pageNum=1&navTabId=" + navTabId + "&seach_EMPID="
									+empid+'&seach_LOCAL_NAME='
									+localName+'&seach_IDCARD_NO='
									+idcardNo
									+'&empId_sy0130_add='+empIdStr
									+'&personId_sy0130_add='+personIdStr
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
							document.getElementById(empNameStr).innerHTML=jsonObject.empName;//empName deptName
							//addTdAdd_sy0130_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
							$.ajax({  
							    async : false,  
							    cache:false,  
							    type: 'POST',  
							    dataType : "json",  
							    url: "/sys/rightsManagement/validatePersonIdExist?PERSON_ID="+jsonObject.personId,//请求的action路径  
							    error: function () {//请求失败处理函数  
								    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>'); 
							    },  
							    success:function(data){ //请求成功后处理函数。  
							    	flagStr=data.flagYn; //把后台封装好的简单Json格式赋给treeNodes
							    	if(flagStr=="Y"){
							    		document.getElementById("PERSON_ID_EXSIT").innerHTML="<spring:message code='sys.addLoginUserView.YUANGONGYIYOUZHANGHAOBUNENGCHONGFU.b'/>";//该员工已经有账号了，不能再次创建账号!
								    }else{
								    	document.getElementById("PERSON_ID_EXSIT").innerHTML="&nbsp;";
									}
							    }  
							});
						}
					},
			error: DWZ.ajaxError
		});
    }
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
}; 
function checkAffirmor(personId,empId,empName,deptName,letters){
			document.getElementById("EMPID").value=empId;
			document.getElementById("personId").value=personId;
			document.getElementById("affirmorName").innerHTML=empName;
			if(personId!=""){
				$.ajax({  
				    async : false,  
				    cache:false,  
				    type: 'POST',  
				    dataType : "json",  
				    url: "/sys/rightsManagement/validatePersonIdExist?PERSON_ID="+personId,//请求的action路径  
				    error: function () {//请求失败处理函数  
					    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>'); 
				    },  
				    success:function(data){ //请求成功后处理函数。  
				    	flagStr=data.flagYn; //把后台封装好的简单Json格式赋给treeNodes
				    	if(flagStr=="Y"){
				    		document.getElementById("PERSON_ID_EXSIT").innerHTML="<spring:message code='sys.addLoginUserView.YUANGONGYIYOUZHANGHAOBUNENGCHONGFU.b'/>";//该员工已经有账号了，不能再次创建账号!
					    }else{
					    	document.getElementById("PERSON_ID_EXSIT").innerHTML="&nbsp;";
						}
				    }  
				});
			}
}

function validateUserName(){
	var userName=document.getElementById("USER_NAME").value;
	if(userName!=""){
		$.ajax({  
		    async : false,  
		    cache:false,  
		    type: 'POST',  
		    dataType : "json",  
		    url: "/sys/rightsManagement/validatePersonIdExist?USER_NAME="+userName,//请求的action路径  
		    error: function () {//请求失败处理函数  
			    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>'); 
		    },  
		    success:function(data){ //请求成功后处理函数。  
		    	flagUserNameStr=data.flagYn; //把后台封装好的简单Json格式赋给treeNodes
		    	if(flagUserNameStr=="Y"){
		    		document.getElementById("USER_NAME_EXSIT").innerHTML="<spring:message code='sys.addLoginUserView.GAIZHANGHAOCUNZAIQINGGENGHUAN.b'/>";//该账号已存在了,请更换一个账号!
			    }else{
			    	document.getElementById("USER_NAME_EXSIT").innerHTML="&nbsp;";
				}
		    }  
		});
	}
}
function validateCallbackSy0120(form, callback) {
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	if(document.getElementById("SPECIAL_PARAM")&&document.getElementById("SPECIAL_PARAM").value!="special"){
		
		if(document.getElementById("personId")){
			if(document.getElementById("personId").value==""){
				document.getElementById("PERSON_ID_EXSIT").innerHTML="<spring:message code='sys.addLoginUserView.QINGSHEZHIYUANGONG.b'/>";//请设置员工!
				return false;
			}
		}
		if(flagStr=="Y"){
			return false;
		}
	}
	
	if(flagUserNameStr=="Y"){
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
</script>
<div class="pageContent">
	<form method="post" action="/sys/rightsManagement/addLoginUserInfo" class="pageForm required-validate" onsubmit=" createTreeJsonData(); return validateCallbackSy0120(this, navTabAjaxDone)">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
		
		<div class="pageFormContent nowrap" layoutH="80">
			
			<dl>
				<dt><spring:message code="sys.rights.title.userName"/><!--用户名-->: </dt>
				<dd>
					<input type="text" id="USER_NAME" name="USER_NAME" value=""  onblur="validateUserName();"  class="required"/><span id="USER_NAME_EXSIT" style="color:red">&nbsp;</span>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="sys.rights.title.password"/><!--密码-->:</dt>
				<dd><input type="text" id="PASSWORD" name="PASSWORD" value=""  class="required"/>
				</dd>
			</dl>
			<dl id="loginUserEmp" >
				<dt><spring:message code="sys.rights.title.employee"/><!--员工-->: </dt>
				<dd>
				 	<input id="EMPID"  name="EMP_ID" type="text" value="" class="required" size="10" onkeydown="submitKeyClick_sy0130_add(this,'','','${request_navTabId}',event)" />
					<span id="affirmorName" >&nbsp;</span>
					<span id="PERSON_ID_EXSIT" style="color: red">&nbsp;</span>
					<input type="hidden" id="personId" name="PERSON_ID" value="" >
					<a id="onck" name="onck"  href="" lookupGroup="person"></a>
	            </dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.rights.title.specialParam"/><!--特殊参数-->:</dt>
				<dd>
				<script>
				function changeCombox(value){
					if(value!='special'){
						document.getElementById("loginUserDeptTree").style.display="none";
						document.getElementById("EMPID").className="required";
						//document.getElementById("loginUserEmp").style.display="";
						//document.getElementById("PERSON_ID_EXSIT").innerHTML="&nbsp;";
					}else{
						document.getElementById("loginUserDeptTree").style.display="";
						document.getElementById("EMPID").className="";
						//document.getElementById("loginUserEmp").style.display="none";
						//document.getElementById("personId").value="";
						//document.getElementById("affirmorName").innerHTML="&nbsp;";
						//document.getElementById("PERSON_ID_EXSIT").innerHTML="&nbsp;";
					}
				}
				</script>
					<select  class="combox"  name="SPECIAL_PARAM" class="required" id="SPECIAL_PARAM" onchange="changeCombox(this.value)">
						<option value="general" <c:if test="${loginUserInfo.SPECIAL_PARAM eq 'general'}">selected</c:if> >
						<spring:message code="sys.rights.title.commonEmployee"/><!--普通用户--></option>
						<option value="manager" <c:if test="${loginUserInfo.SPECIAL_PARAM eq 'manager'}">selected</c:if>>
						<spring:message code="sys.rights.title.adminstrator"/><!--管理者--></option>
						<option value="special" <c:if test="${loginUserInfo.SPECIAL_PARAM eq 'special'}">selected</c:if>>
						<spring:message code="sys.rights.title.specialEmployee"/><!--特殊用户--></option>
					</select>
					<input type="hidden" name="deptNos" id="deptNos" value=""/>
				</dd>
			</dl>
			
			<dl style="height:120px">
				<dt><spring:message code="sys.rights.title.privilegeGroup"/><!--权限组-->: </dt>
				<dd>
					<c:forEach items="${loginUserInfoRolesGroupList}" var="rolesGroup">
                		<input type="checkbox" name="SCREEN_GRANT_NO"  value="${rolesGroup.ROLE_GROUP_NO}"
                			<c:if test="${ rolesGroup.CHECKED == 1}">checked=true</c:if> 
                		/>${rolesGroup.GROUPNAME}&nbsp;&nbsp;
                	</c:forEach>
	            </dd>
			</dl>
			
			<dl id="loginUserDeptTree" <c:if test="${loginUserInfo.SPECIAL_PARAM !='special'}"> style="display:none"</c:if>>
				<dt><spring:message code="sys.rights.title.deptTree"/><!--部门树-->: </dt>
				<dd>
				<ul id="deptTree_sy0120" class="ztree"></ul>
	            </dd>
			</dl>
		</div>
	
<input id="jsonData" name="jsonData" type="hidden" value=""/>

<script type="text/javascript">
	function createTreeJsonData()
    {   
		var jsonData = '[' ;
	    
		$("#loginUserDept a").each(function(index){

			if($(this).parent().find("div.checked").size() > 0){
				if (jsonData.length > 1){
	            	jsonData += ',{'
	            }
	            else{
	            	jsonData += '{'
	            }
	            jsonData += ' "DEPTNO": "' + $(this).attr("tvalue") + '", ' ;
	            jsonData += ' "USERNO": ' + '${loginUserInfo.USER_NO}' ;
	            jsonData += '}' ;
				
			}
		});

		jsonData += ']' ;

        $('#jsonData').attr('value' , jsonData) ;
    }
</script>
	</form>
</div>
