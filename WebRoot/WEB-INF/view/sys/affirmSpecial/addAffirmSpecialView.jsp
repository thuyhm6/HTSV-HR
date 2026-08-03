<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
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
		chkboxType:{"Y":"s","N":"s"},
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
		var t =$.fn.zTree.getZTreeObj("deptTree_updateattendancekeeperdepttree");
		var nodes = t.getCheckedNodes(); 
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				if(i==0){
					 document.getElementById("deptNos").value= "'" + nodes[i].DEPTNO + "'";
				}else{
					 document.getElementById("deptNos").value+=",'"+nodes[i].DEPTNO + "'";
				}
			}
			//根据deptNos查询包含的人员类型
		    // $.ajax({
					//	type: 'POST',
					//	url: encodeURI('/sys/affirmSpecial/getPersonEmpTypeByDeptNos?deptNos=' + document.getElementById("deptNos").value),
				//		dataType:"json",
				//		cache: false,
				//		success: function(jsonObject){
				//	},
				//		error: DWZ.ajaxError
			   // });
		}else{
			document.getElementById("deptNos").value="";
		}
		
	  }
	}
};

var zNodes;
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",
        url: "/ar/attendanceSettings/getDeptTree?AR_SUPERVISOR_ID=${AR_SUPERVISOR_ID}",//请求的action路径  
        error: function () {//请求失败处理函数  
            //请求失败
            alert('<spring:message code="ar.alert.message.viewattendencekeeper.error"/>');  
        },  
        success:function(data){ //请求成功后处理函数。    
       	zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
        }  
   }); 
// 初始调用
$(document).ready(function(){
    //布局
    $("#layout1").ligerLayout({ leftWidth: 180});
	var t = $("#deptTree_updateattendancekeeperdepttree");
	t = $.fn.zTree.init(t, setting, zNodes);
	var nodes = t.getCheckedNodes(); 
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				 document.getElementById("deptNos").value= "'" + nodes[i].DEPTNO + "'";
			}else{
				 document.getElementById("deptNos").value+=",'"+nodes[i].DEPTNO + "'";
			}
		}
	}else{
		document.getElementById("deptNos").value="";
	}
});
//部门联动人员结束

		var empIdArr=new Array();
		var xmlHttpTb;
		function changeForAffirmor(obj){//类型，部门，人员的onchange事件
		var deptNo=document.getElementById("seach_DEPTNO_0").value;
		var personId=document.getElementById("emp_name").value;
		var typeId=document.getElementById("type").value;
		if(typeId!=""){
		    var url='';
			if(obj.id=='type'&&typeId!=''){//emp为空；不为空
				if(personId==''&&deptNo!=''){
					url='/sys/affirmSpecial/getAffirmorsSpecialByPersonId?AFFIRM_OBJECT='+deptNo+'&AFFIRM_TYPE_NO='+typeId;	
				}else if(personId!=''){
					url='/sys/affirmSpecial/getAffirmorsSpecialByPersonId?AFFIRM_OBJECT='+personId+'&AFFIRM_TYPE_NO='+typeId;	
				}
			}else if((obj.id=='seach_DEPTNO_0'&&deptNo!='')||(obj.id=='emp_name'&&personId=='')){
				if(typeId!=''){
					url='/sys/affirmSpecial/getAffirmorsSpecialByPersonId?AFFIRM_OBJECT='+deptNo+'&AFFIRM_TYPE_NO='+typeId;	
				}
			}else if(obj.id=='emp_name'&&personId!=''){
				url='/sys/affirmSpecial/getAffirmorsSpecialByPersonId?AFFIRM_OBJECT='+personId+'&AFFIRM_TYPE_NO='+typeId;	
			}
		
			if(url!=''){
				if(window.ActiveXObject){
					xmlHttpTb=new ActiveXObject("Microsoft.XMLHTTP");
				}else{
					xmlHttpTb=new XMLHttpRequest();
				}
				xmlHttpTb.onreadystatechange = buildEmpSelect_sy0010sp_add;
				xmlHttpTb.open("POST",url,false);
				xmlHttpTb.setRequestHeader("If-Modified-Since","0");                                                        
				xmlHttpTb.send(null); 
			}
			 
		}
		}
		function buildEmpSelect_sy0010sp_add(){
			if(xmlHttpTb.readyState ==4){
				if(xmlHttpTb.status ==200){
					document.getElementById('jbsxBox').innerHTML = xmlHttpTb.responseText;
					var rowNum=document.getElementById('empTb').rows.length;
					var noEmpExist=document.getElementById("noEmp");
					var pers=document.getElementsByName("PERSON_IDS");
					if(pers){
				    	for(var i=0;i<pers.length;i++){
				    		empIdArr.push(pers[i].value);
				        }
				    }
				}
			}
		}
		function addTdAdd_sy0010sp_add(personId,empId,empName,deptName,letters){
		   var rowNum=document.getElementById('empTb').rows.length;
		  // var cellNum=document.getElementById('empTb').cells.length;
		   var noEmpExist=document.getElementById("noEmp"); 
		   if(noEmpExist){
			   document.getElementById('empTb').deleteRow(rowNum-1);
			   rowNum=rowNum-1;
		   }
			  var m = document.getElementById('empTb').insertRow(rowNum); 
			  var cell0=m.insertCell(0); 
			  var cell1=m.insertCell(1); 
			//  var cell2=m.insertCell(2); 
			  var cell3=m.insertCell(2); 
			  var cell4=m.insertCell(3); 
			  var cell5=m.insertCell(4); 
			  cell0.style.textAlign = "center";
			  cell1.style.textAlign = "center";
			//  cell2.style.textAlign = "center";
			  cell3.style.textAlign = "center";
			  cell4.style.textAlign = "center";
			  cell5.style.textAlign = "center";
			  cell0.className="td_type";
			  cell1.className="td_type";
			  cell3.className="td_type";
			  cell4.className="td_type";
			  cell5.className="td_type";
			 // cell2.className="td_type";
			  cell0.style.height='30';
			  var txtNode0=document.createTextNode(empId); 
			  var txtNode1=document.createTextNode(empName); 
			  var txtNode2=document.createTextNode(letters); 
			  var txtNode3=document.createTextNode(deptName);
			  var txtNode4=document.createTextNode(rowNum); 
			  var hiddenInput=document.createElement("input");
			  hiddenInput.setAttribute("name","PERSON_IDS");
			  hiddenInput.setAttribute("type","hidden");
			  hiddenInput.setAttribute("value",personId);
			  cell0.appendChild(txtNode0);
			  cell0.appendChild(hiddenInput);
			  cell1.appendChild(txtNode1);
			 // cell2.appendChild(txtNode2);
			  cell3.appendChild(txtNode3);
			  cell4.appendChild(txtNode4);
			  cell5.innerHTML='<span onclick="upper(this);" style="cursor: hand"><img src="/resources/images/button/up.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<span onclick="moveDown(this);" style="cursor: hand"><img src="/resources/images/button/down.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<span onclick="delRow(this);" style="cursor: hand"><img src="/resources/images/0.gif" style="cursor:hand"/></span>';
		}
		
		function addTdAdd_sy0011sp_add(personId,empId,empName,deptName,letters){
		  var rowNum=document.getElementById('empTb1').rows.length;
		  // var cellNum=document.getElementById('empTb').cells.length;
		   var noEmpExist=document.getElementById("noEmp1"); 
		   if(noEmpExist){
			   document.getElementById('empTb1').deleteRow(rowNum-1);
			   rowNum=rowNum-1;
		   }
			  var m = document.getElementById('empTb1').insertRow(rowNum); 
			  var cell0=m.insertCell(0); 
			  var cell1=m.insertCell(1); 
			//  var cell2=m.insertCell(2); 
			  var cell3=m.insertCell(2); 
			  var cell4=m.insertCell(3); 
			  cell0.style.textAlign = "center";
			  cell1.style.textAlign = "center";
			//  cell2.style.textAlign = "center";
			  cell3.style.textAlign = "center";
			  cell4.style.textAlign = "center";
			  
			  cell0.className="td_type";
			  cell1.className="td_type";
			  cell3.className="td_type";
			  cell4.className="td_type";
			 // cell2.className="td_type";
			  cell0.style.height='30';
			  var txtNode0=document.createTextNode(empId); 
			  var txtNode1=document.createTextNode(empName); 
			  var txtNode2=document.createTextNode(letters); 
			  var txtNode3=document.createTextNode(deptName);
			  var txtNode4=document.createTextNode(rowNum); 
			  var hiddenInput=document.createElement("input");
			  hiddenInput.setAttribute("name","PERSON_IDSS");
			  hiddenInput.setAttribute("type","hidden");
			  hiddenInput.setAttribute("value",personId);
			  cell0.appendChild(txtNode0);
			  cell0.appendChild(hiddenInput);
			  cell1.appendChild(txtNode1);
			 // cell2.appendChild(txtNode2);
			  cell3.appendChild(txtNode3);
			  cell4.innerHTML='<span onclick="delRow1(this);" style="cursor: hand"><img src="/resources/images/0.gif" style="cursor:hand"/></span>';
		}

		//上移
		function upper(obj){
		  
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  if(rowElement1>1){//如果不是第一行
			  var newRow=document.getElementById('empTb').insertRow(rowElement1-1); 
			  
			  for(var i=0;i<5;i++){
				  newRow.insertCell(i).innerHTML=document.getElementById('empTb').rows[rowElement1+1].cells[i].innerHTML;
				  newRow.cells[i].className="td_type";
				 // newRow.style.height="25";
				  newRow.cells[i].style.textAlign="center";
			  }
			  newRow.cells[3].innerHTML=rowElement1-1;
			  document.getElementById('empTb').deleteRow(rowElement1+1);
			  document.getElementById('empTb').rows[rowElement1].cells[3].innerHTML=rowElement1;
		  }
		}

		//下移
		function moveDown(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  if(rowElement1<document.getElementById('empTb').rows.length-1){//如果不是第一行
			  var newRow=document.getElementById('empTb').insertRow(rowElement1+2); 
			  
			  for(var i=0;i<5;i++){
				  newRow.insertCell(i).innerHTML=document.getElementById('empTb').rows[rowElement1].cells[i].innerHTML;
				  newRow.cells[i].className="td_type";
				  newRow.cells[i].style.textAlign="center";
			  }
			  newRow.cells[3].innerHTML=rowElement1+1;
			  document.getElementById('empTb').deleteRow(rowElement1);
			  document.getElementById('empTb').rows[rowElement1].cells[3].innerHTML=rowElement1;
		  }
		}

		//删除当前行
		function delRow(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  document.getElementById('empTb').deleteRow(rowElement1);
		  var rowNum= document.getElementById('empTb').rows.length;
		  for(var i=1;i<rowNum;i++){
			  document.getElementById('empTb').rows[i].cells[3].innerHTML=i;
		  }
		}
		
		//删除当前行
		function delRow1(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  document.getElementById('empTb1').deleteRow(rowElement1);
// 		  var rowNum= document.getElementById('empTb1').rows.length;
// 		  for(var i=1;i<rowNum;i++){
// 			  document.getElementById('empTb1').rows[i].cells[3].innerHTML=i;
// 		  }
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
		/**
		决裁者模糊查询开始
		*/
		var keyCodeInit=0;
		function submitKeyClick_sy0010sp_add(obj,localName,idcardNo,navTabId,event){
		 	var e= event ? event : window.event; 
		 	var keyCode = e.which ? e.which : e.keyCode;
		   	if(keyCode==13){
		   		keyCodeInit=keyCode;
				var empid=obj.value.replace(/[ ]/g,"");
				var empIdStr=obj.id;
				var personIdStr="personId"+empIdStr.substring(5);
				if(empid == ''){
					alert("<spring:message code='sys.viewAffirmSpecialView.QINGTIANXIEZHUNQUESHEHAO.b' />");//请填写准确社号！
				}else{
			   		$.ajax({
						type: 'POST',
						url: encodeURI('/sys/affirmSpecial/getPersonCntByEmpid?EMPID=' + empid),
						dataType:"json",
						cache: false,
						success: function(jsonObject){
									if(jsonObject.perCnt==1){
										document.getElementById(empIdStr).value=jsonObject.empId;
										document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
										document.getElementById("affirmorSpecialName").innerHTML=jsonObject.empName;
										addTdAdd_sy0010sp_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
									}else{
										alert("<spring:message code='sys.viewAffirmSpecialView.QINGTIANXIEZHUNQUESHEHAO.b' />");//请填写准确社号！
									}
								},
						error: DWZ.ajaxError
					});
				}
		    }
		 }
		function validateCallbackSy0010sp(form, callback) {
			
			var $form = $(form);
			
			if (!$form.valid()) {
				return false;
			}
			if(document.getElementById("type")){
				if(document.getElementById("type").value==""){
                   alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChooseAffirmType"/>');
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
		
		/**
		决裁对象模糊查询开始
		*/
		var keyCodeInit=0;
		function submitKeyClick_sy0011sp_add(obj,localName,idcardNo,navTabId,event){
		 	var e= event ? event : window.event; 
		 	var keyCode = e.which ? e.which : e.keyCode;
		   	if(keyCode==13){
		   		keyCodeInit=keyCode;
				var empid=obj.value.replace(/[ ]/g,"");
				var empIdStr=obj.id;
				var personIdStr="personId"+empIdStr.substring(5);
				if(empid == ''){
					alert("<spring:message code='sys.viewAffirmSpecialView.QINGTIANXIEZHUNQUESHEHAO.b' />");//请填写准确社号！
				}else{
			   		$.ajax({
						type: 'POST',
						url: encodeURI('/sys/affirmSpecial/getPersonCntByEmpid?EMPID=' + empid),
						dataType:"json",
						cache: false,
						success: function(jsonObject){
									if(jsonObject.perCnt==1){
										document.getElementById(empIdStr).value=jsonObject.empId;
										document.getElementById(personIdStr).value=jsonObject.personId;//empName deptName
										document.getElementById("affirmorSpecialName1").innerHTML=jsonObject.empName;
										addTdAdd_sy0011sp_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
									}else{
										alert("<spring:message code='sys.viewAffirmSpecialView.QINGTIANXIEZHUNQUESHEHAO.b' />");//请填写准确社号！
									}
								},
						error: DWZ.ajaxError
					});
				}
		    }
		 }
</SCRIPT>

<div class="pageContent">
<form method="post" action="/sys/affirmSpecial/insertAffirmSpecialInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackSy0010sp(this,navTabAjaxDone);">
<div class="formBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.submit" /><!--提交--></button>
	</div>
	</div>
	</li>
	<li>
	<div class="button">
	<div class="buttonContent">
	<button type="button" class="close"><spring:message
		code="public.title.cancle" /><!--取消--></button>
	</div>
	</div>
	</li>
</ul>
</div>
<div class="pageFormContent nowrap" layoutH="56">
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="user_table margin_b">
	<tr>
		<td class="td_title"><spring:message
			code="sys.affirm.title.affirmTypeNames" /><!--决裁类型-->/<!--全选--><spring:message code="pa.salary.title.allChecked" />：<input type="checkbox" class="checkboxCtrl" group="AFFIRM_TYPE_NO" /></td>
			
		<td class="td_type">
		<table>
			<c:forEach items="${typeList}" var="type" varStatus="i">
				<c:if test="${i.count%4 eq 1}">
					<tr>
						<td width="200"><input type="checkbox" name="AFFIRM_TYPE_NO"
							value="${type.CODE_NO}" />&nbsp;&nbsp;${type.CONTENT }</td>
				</c:if>
				<c:if test="${i.count%4 eq 0}">
					<td width="200"><input type="checkbox" name="AFFIRM_TYPE_NO"
						value="${type.CODE_NO}" />&nbsp;&nbsp;${type.CONTENT }</td>
					</tr>
				</c:if>
				<c:if test="${i.count%4 ne 1 and i.count%4 ne 0}">
					<td width="200"><input type="checkbox" name="AFFIRM_TYPE_NO"
						value="${type.CODE_NO}" />&nbsp;&nbsp;${type.CONTENT }</td>
				</c:if>
			</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
		<td class="td_title"><spring:message
			code="sys.affirm.title.affirmPerson" /><!--决裁者--></td>
		<td class="td_type">
		<div><input id="EMPID" name="EMP_ID" type="text" value="" size="50" alt="Xin nhập đúng mã nhân viên hoặc họ tên sau đó Enter"
	  onkeydown="submitKeyClick_sy0010sp_add(this,'','','${request_navTabId}',event)" />
		   <span id="affirmorSpecialName">&nbsp;</span> 
		    <input type="hidden" id="personId" name="PERSON_ID" value=""> 
			<a id="onck" name="onck" href="" lookupGroup="person"></a>
			</div>
		</td>
		</tr>
		<tr>
		<td class="td_title"><!--决裁对象--><spring:message code="sys.viewAffirmSpecialView.JUECAIDUIXIANG.b" /></td>
		<td class="td_type">
		<div><input id="EMPID1" name="EMP_ID1" type="text" value="" size="50" alt="Xin nhập đúng mã nhân viên hoặc họ tên sau đó Enter"
	  onkeydown="submitKeyClick_sy0011sp_add(this,'','','${request_navTabId}',event)" />
		   <span id="affirmorSpecialName1">&nbsp;</span> 
		    <input type="hidden" id="personId1" name="dwz.person.personId" value=""> 
			<a id="onck1" name="onck1" href="" lookupGroup="person"></a>
			</div>
		</td>
		</tr>
<!-- 		<tr> -->
<!-- 		<td class="td_title"> -->
<!-- 			 决裁对象 -->
<!-- 		</td> -->
<!-- 		<td class="td_type"> -->
<!-- 			   <input id="jsonData" name="jsonData" value="" type="hidden"/> -->
<!-- 	           <input id="empDept" name="dwz.person.empDept" value="" type="hidden" lookupGroup="person"/> -->
<!-- 			   <input id="empId" name="dwz.person.empId" value="" type="hidden" lookupGroup="person"/> -->
<!-- 			   <input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/> -->
<!-- 			   <input name="dwz.person.empId" type="text" readOnly lookupGroup="person"/> -->
<!-- 			   <a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">查找带回 -->
<!-- 			   <spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a> -->
<!-- 		</td> -->
<!-- 	</tr> -->
	<tr>
		<td class="td_title"><spring:message code="public.title.deptName" /><!--部门-->（<!--决裁对象--><spring:message code="sys.viewAffirmSpecialView.JUECAIDUIXIANG.b" />）</td>
		<td class="td_type">
		<ul id="deptTree_updateattendancekeeperdepttree" class="ztree"></ul>
		<input type="hidden" name="deptNos" id="deptNos" value="" /></td>
		
		<td class="td_type">
		<input type="hidden" name="emp_type_code" id="emp_type_code" value="" /></td>
	</tr>
</table>
<div id="jbsxBox" style="height: auto">
<dl style="height: auto">
	<dt style="height: auto"><spring:message
		code="sys.affirm.title.affirmGradeLevel" /><!--决裁等级-->:</dt>
	<dd style="height: auto">
	<table border=0 width="500" id="empTb" class="user_table">
		<tr height="30">
			<td style="text-align: center"><spring:message
				code="public.title.empId" /><!--工号--></td>
			<td style="text-align: center"><spring:message
				code="public.title.name" /><!--姓名--></td>
			<!--<td style="text-align: center">拼音</td>  -->
			<td style="text-align: center"><spring:message
				code="public.title.deptName" /><!--部门--></td>
			<td style="text-align: center"><spring:message
				code="sys.affirm.title.affirmLevel" /><!--等级--></td>
			<td style="text-align: center"><spring:message
				code="sys.affirm.title.affirmOperation" /><!--操作--></td>
		</tr>
		<tr height="25" id="noEmp">
		</tr>
	</table>
	</dd>
	<dt style="height: auto"><!--决裁对象--><spring:message code="sys.viewAffirmSpecialView.JUECAIDUIXIANG.b" />:</dt>
	<dd style="height: auto">
		<table border=0 width="350" id="empTb1" class="user_table">
		<tr height="30">
			<td style="text-align: center"><spring:message
				code="public.title.empId" /><!--工号--></td>
			<td style="text-align: center"><spring:message
				code="public.title.name" /><!--姓名--></td>
			<!--<td style="text-align: center">拼音</td>  -->
			<td style="text-align: center"><spring:message
				code="public.title.deptName" /><!--部门--></td>
			<td style="text-align: center"><spring:message
				code="sys.affirm.title.affirmOperation" /><!--操作--></td>
		</tr>
		<tr height="25" id="noEmp1">
		</tr>
	</table>
	</dd>
	</dl>
</div>
</div>
</form>
</div>
