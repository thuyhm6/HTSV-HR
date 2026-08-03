<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>

<script type="text/javascript">
	var xmlHttp;
	var time=null;
	function searchEmp(obj){
		 if(time!=null){
				clearTimeout(time);
				time=null;  
			}
			time=setTimeout(function(){
				var me=document.getElementById("emp_list");
				if(me.style.visibility="hidden"){
					  me.style.visibility="";
				}
				if(obj.value!=''){
						var url='/sys/affirm/getAffirmorsByAffirmId?AFFIRMOR_ID='+obj.value+"&time="+time;
						if(window.ActiveXObject){
							xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
						}else{
							xmlHttp=new XMLHttpRequest();
						}
						xmlHttp.onreadystatechange = buildEmpSelect;
						xmlHttp.open("POST",url,false);
						xmlHttp.setRequestHeader("If-Modified-Since","0");                                                        
						xmlHttp.send(null); 
				}else{
						hiddenDIV('');
				} 
			},300);  
	}
	function buildEmpSelect(){
		if(xmlHttp.readyState ==4){
			if(xmlHttp.status ==200){
				document.getElementById('emp_list').innerHTML = xmlHttp.responseText;
			}
		}
	}
	function hiddenDIV(empid,personId){	
	   document.getElementById("seach_EMPID").value=empid;
	   var me=document.getElementById("employee_list");
	 	   me.style.visibility="hidden";
	}
	
</script> 

<div class="pageHeader">
 
	<form onsubmit="return navTabSearch(this);" action="${actionUrl }" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
					<!-- 姓名(拼音)： --><spring:message code="hrm.empinfo.NAME_ENGLISH_SPELLING" />
				    <input type="text" name="seach_LOCAL_NAME" id="seach_EMPID" value="${EMPID}" onKeyUp="searchEmp(this)"/>
					<div id="emp_list" style="position:absolute;border:10;overflow:auto;top:20;left:20;width:300;z-index:2;" ></div>
					<spring:message code="hrm.empinfo.empid" />：<input type="text" name="seach_EMP_ID" id="seach_EMP_ID" value="${EMP_ID}"/> 
				        <div id="emp_list" style="position:absolute;border:10;overflow:auto;top:20;left:20;width:300;z-index:2;" ></div>
					<!-- 部门： --><spring:message code="hrm.empinfo.DEPARTMENT" /><input type="text" name="deptno123" id="seach_PERSON_ID" />
	              </td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="hrm.empinfo.QUERY" /></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
