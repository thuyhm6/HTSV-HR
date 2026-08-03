<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
		
		var empIdArr=new Array();
		var xmlHttpTb;
		function changeForAffirmor_update(obj){//类型，部门，人员的onchange事件
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
				xmlHttpTb.onreadystatechange = buildEmpSelect_sy0130_update;
				xmlHttpTb.open("POST",url,false);
				xmlHttpTb.setRequestHeader("If-Modified-Since","0");                                                        
				xmlHttpTb.send(null); 
			}
			 
		}
		}
		function buildEmpSelect_sy0130_update(){
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
		function addTdAdd_sy0010sp_update(personId,empId,empName,deptName,letters){
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
			  // var cell2=m.insertCell(2); 
			  var cell3=m.insertCell(2); 
			  var cell4=m.insertCell(3); 
			  var cell5=m.insertCell(4); 
			  cell0.style.textAlign = "center";
			  cell1.style.textAlign = "center";
			  //cell2.style.textAlign = "center";
			  cell3.style.textAlign = "center";
			  cell4.style.textAlign = "center";
			  cell5.style.textAlign = "center";
			  cell0.className="td_type";
			  cell1.className="td_type";
			  cell3.className="td_type";
			  cell4.className="td_type";
			  cell5.className="td_type";
			  //cell0.style.height='30';
			  var txtNode0=document.createTextNode(empId); 
			  var txtNode1=document.createTextNode(empName); 
			//  var txtNode2=document.createTextNode(letters); 
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
			  cell5.innerHTML='<span onclick="upper(this);"><img src="/resources/images/button/up.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp;'
				  +'<span onclick="moveDown(this);"><img src="/resources/images/button/down.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp;'
				  +'<span onclick="delRow(this);"><img src="/resources/images/0.gif" style="cursor:hand"/></span>';
		}
		function upper(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  if(rowElement1>1){//如果不是第一行
			  var newRow=document.getElementById('empTb').insertRow(rowElement1-1); 
			  
			  for(var i=0;i<5;i++){
				  newRow.insertCell(i).innerHTML=document.getElementById('empTb').rows[rowElement1+1].cells[i].innerHTML;
				  newRow.cells[i].style.textAlign="center";
				  newRow.cells[i].className="td_type";
			  }
			  newRow.cells[3].innerHTML=rowElement1-1;
			  document.getElementById('empTb').deleteRow(rowElement1+1);
			  document.getElementById('empTb').rows[rowElement1].cells[3].innerHTML=rowElement1;
		  }
		}
		function moveDown(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  if(rowElement1<document.getElementById('empTb').rows.length-1){//如果不是第一行
			  var newRow=document.getElementById('empTb').insertRow(rowElement1+2); 
			  
			  for(var i=0;i<5;i++){
				  newRow.insertCell(i).innerHTML=document.getElementById('empTb').rows[rowElement1].cells[i].innerHTML;
				  newRow.cells[i].style.textAlign="center";
				  newRow.cells[i].className="td_type";
			  }
			  newRow.cells[3].innerHTML=rowElement1+1;
			  document.getElementById('empTb').deleteRow(rowElement1);
			  document.getElementById('empTb').rows[rowElement1].cells[3].innerHTML=rowElement1;
		  }
		}
		function delRow(obj){
		  var rowElement1=obj.parentElement.parentElement.rowIndex;//当前的行号
		  document.getElementById('empTb').deleteRow(rowElement1);
		  var rowNum= document.getElementById('empTb').rows.length;
		  for(var i=1;i<rowNum;i++){
			  document.getElementById('empTb').rows(i).cells[3].innerHTML=i;
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
		var keyCodeInit=0;
		function submitKeyClick_sy0010sp_update(obj,localName,idcardNo,navTabId,event){
		 	var e= event ? event : window.event; 
		 	var keyCode = e.which ? e.which : e.keyCode;
		   	if(keyCode==13){
		   		keyCodeInit=keyCode;
				var empid=obj.value.replace(/[ ]/g,"");
				var empIdStr=obj.id;
				var personIdStr="personId"+empIdStr.substring(5);

				if(empid == ''){
					alert("请填写准确社号");
				}else{
			   		$.ajax({
						type: 'POST',
						url: encodeURI('/sys/affirmSpecial/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
						dataType:"json",
						cache: false,
						success: function(jsonObject){
									if(jsonObject.perCnt != 1 ){
										alert("请填写准确社号");
									}
									if(jsonObject.perCnt==1){
										document.getElementById(empIdStr).value=jsonObject.empId;
										document.getElementById(personIdStr).value=jsonObject.personId;
										document.getElementById("affirmorSpecialName").innerHTML=jsonObject.empName;
										addTdAdd_sy0010sp_update(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,"");
									}
								},
						error: DWZ.ajaxError
					});
				}
		    }
		 }
	//	function checkAffirmor(personId,empId,empName,deptName,letters){
	//		addTdAdd_sy0130_update(personId,empId,empName,deptName,letters);
	//		document.getElementById("EMPID").value=empId;
	//		document.getElementById("personId").value=personId;
	//		document.getElementById("affirmorName").innerHTML=empName;
	//	}
	function validateCallbackSy0110sp_update(form, callback) {
			
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
</SCRIPT>
 
<div class="pageContent">
	<form method="post" action="/sys/affirmSpecial/insertAffirmSpecialInfo" class="pageForm required-validate" onsubmit="return validateCallbackSy0110sp_update(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
		
			<dl>
				<dt><spring:message code="sys.affirm.title.affirmTypeNames"/><!--决裁类型--></dt>
				<dd>${APPLY_TYPE_NAME }
					<input type="hidden" name="AFFIRM_TYPE_NO" value="${AFFIRM_TYPE_NO }"/>
				</dd>
			</dl>
			<dl>
				<dt>决裁对象</dt>
				<dd>
				  	<input id="seach_DEPTNO_0" name="deptNos" type="hidden" sysLong="sy0482" value="${DEPTNO}"/>	
				  	${DEPTNAME}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.affirm.title.affirmPerson"/><!--决裁者--></dt>
				<dd> 
					<div>
						
						<input id="EMPID"  name="EMP_ID" type="text" value="" size="30" alt="请输入准确社号按回车检索" onkeydown="submitKeyClick_sy0010sp_update(this,'','','${request_navTabId}',event)" />
				<span id="affirmorSpecialName" >&nbsp;</span>
				<input type="hidden" id="personId" name="PERSON_ID" value="" >
				<a id="onck" name="onck"  href="" lookupGroup="person"></a>
				</div>
				</dd>
			</dl>
			<div id="jbsxBox">
				<dl>
					<dt><spring:message code="sys.affirm.title.affirmLevel"/><!--决裁等级--></dt>
					<dd>
							<table border=1 width="600" id="empTb"  class="user_table">
									<tr height="30">
										<td  class="td_title" style="text-align: center"><spring:message code="public.title.empId"/><!--工号--></td>
										<td  class="td_title" style="text-align: center"><spring:message code="public.title.name"/><!--姓名--></td>
										<!-- <td  class="td_title" style="text-align: center">拼音</td> -->
										<td  class="td_title" style="text-align: center"><spring:message code="public.title.deptName"/><!--部门--></td>
										<td	 class="td_title" style="text-align: center"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></td>
										<td  class="td_title" style="text-align: center"><spring:message code="sys.affirm.title.affirmOperation"/><!--操作--></td>
									</tr>
									<c:forEach items="${affirmorSpecialList}" var="employee" varStatus="i">
										<tr  height="25">
											<td style="text-align: center" class="td_type">${employee.EMPID}
												<input type="hidden" name="PERSON_IDS" value="${employee.PERSON_ID}"/>
											</td>
											<td style="text-align: center" class="td_type">${employee.LOCAL_NAME}</td>
											<!--<td style="text-align: center">${employee.CHINESE_PINYIN}</td>-->
											<td style="text-align: center" class="td_type">${employee.DEPTNAME}</td>
											<td style="text-align: center" class="td_type">${employee.AFFIRM_LEVEL}</td>
											<td style="text-align: center" class="td_type">
												<span onclick="upper(this);" style="cursor:hand"><img src="/resources/images/button/up.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
												<span onclick="moveDown(this);" style="cursor:hand"><img src="/resources/images/button/down.gif" style="cursor:hand"/></span>&nbsp;&nbsp;&nbsp;&nbsp; 
												<span onclick="delRow(this);" style="cursor:hand"><img src="/resources/images/0.gif" style="cursor:hand"/></span> 
											</td>
										 </tr>
									</c:forEach>
							</table>
					</dd>
				</dl>
			</div>
		</div>
	</form>
	
</div>
