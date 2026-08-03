<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
var affirmLevel=1;
function appendRow(postFlag){
	  var existFlag=false;
	  if(postFlag!="blank"){
		  var postSelected=document.getElementById("Post");
		  if(postSelected.value==""){
			  alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChoosePost"/>');
			  return false;
		  }else{
			  var postNos=document.getElementsByName("DUTY_NO");
			  if(postNos&&postNos!=null){
				 for(var i=0;i<postNos.length;i++){
					 if(postSelected.value==postNos[i].value){
						 existFlag=true;
						 break;
					 }
				 }
			  }
		  }
	  }
	  if(existFlag==false){
		  var rowNum=document.getElementById('operateTable').rows.length;
		  var nTr = document.getElementById('operateTable').insertRow(rowNum);
		  
		  var cell0=nTr.insertCell(0); 
		  var cell1=nTr.insertCell(1); 
		  var cell2=nTr.insertCell(2); 
		  cell0.height = "25" ; 
		  cell0.style.textAlign="center";;
		  cell0.width="25%";
		  cell0.className='info_title_01'; 
		  cell0.innerHTML = "<input type='radio' name='rowNum' />" ;
			
		  cell1.colSpan="1";
		  cell1.height = "25" ;
		  cell1.style.textAlign="center";
		  if(postFlag=="blank"){
		  		cell1.innerHTML ="空缺<input type='hidden' name='DUTY_NO' value='vacancy' /> ";
		  }else{
		  		cell1.innerHTML = postSelected.options[postSelected.selectedIndex].text+
						 " <input type='hidden' name='DUTY_NO' value='" + postSelected.value + "' /> ";
		  }	  
		  cell2.width="25%";
		  cell2.height = "25" ; 
		  cell2.style.textAlign="center";
		  cell2.innerHTML =affirmLevel
			
		 affirmLevel++ ;
	  }else{
	   alertMsg.error('<spring:message code="alert.message.sys.affirm.pleaseChoosePost"/>');
	  }
}
function deleteRow(){
	    var deleteNum=0;
      var optionsArr=document.getElementsByName("rowNum");
     
      for(var i=2;i<optionsArr.length+2;i++){
	        //i为行号
			if(optionsArr[i-2].checked==true){
				document.getElementById('operateTable').deleteRow(i-deleteNum);
				affirmLevel--;
				deleteNum++;
				break;//单选框
			}
	    }
	    if(deleteNum!=0){
	    	var operateTb=document.getElementById('operateTable');
	    	var operateTbLength=document.getElementById('operateTable').rows.length;
	    	for(var j=2;j<operateTbLength;j++){
	    		operateTb.rows[j].cells[2].innerHTML=j-1;
		    }
		}
}	 
</SCRIPT>
 <style>
<!--
 
td.info_title_01 {
	background-color:#F0F1F4;
	color:#014D7E;
	font-weight:bold;
	text-align:center;
	font-size: 12px;
	font-family: Arial;
	padding: 8px 8px 4px 8px;
}
td.info_content_01 {

	background-color:white;
	text-align:center;
	font-size: 12px;
	font-family: Arial;
	padding: 8px 8px 4px 8px;
}
-->
</style>
<div >
	<form method="post" action="/sys/hrmAffirm/saveHrmAffirmInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<div>&gt;<span style="font-size: 14px;padding:5px;font-weight: bold;">
			<spring:message code="sys.affirm.title.affirmDuty"/><!--申请者职责--></span></div>
		    <table border="0" width="30%">
				 <tr>
				    <td class="info_content_01">
				     <select name="DUTY_NO">
					       	<option>select</option>
					         <c:forEach items="${dutyList}" var="result" >
					         <option  value="${result.DUTY_NO}">${result.DUTYNAME}</option>
					         </c:forEach>
					         </select>
				    </td>
				 </tr>    
			 </table>
			 <dl></dl>
			<div>&gt;<span style="font-size: 14px;padding:5px;font-weight: bold;">
			     <spring:message code="sys.affirm.title.projectParam"/><!--项目参数--></span></div>
			 <table border="1" width="50%">
				 <tr>
				    <td class="info_title_01"><spring:message code="sys.affirm.title.applyType"/><!--申请类型--></td>
				    <td class="info_content_01">
				     <select name="APPLY_TYPE">
					       	<option>select</option>
					         <c:forEach items="${applyList}" var="result" >
					         <option  value="${result.CODE_NO}">${result.CODENAME}</option>
					         </c:forEach>
					         </select>
				    </td>
				        
				   
				    <td class="info_title_01"><spring:message code="sys.affirm.title.ifReference"/><!--是否参考--></td>
				    <td class="info_content_01"><select name="REFERENCN_FLAG" id="REFERENCN_FLAG">
				      <option value="1" selected><spring:message code="sys.affirm.title.yes"/><!--是--></option>
				      <option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
				    </select>
				    </td>   
				  </tr>
			 </table>
			 <dl></dl>
			 <div>&gt;<span style="font-size: 14px;padding:5px;font-weight: bold;">
			 <spring:message code="sys.affirm.title.affirmLevel"/><!--决裁级别--></span></div>
			 <table id="operateTable" align="left" width="400" border="1" cellspacing="0" cellpadding="5" 
			        bordercolorlight="#E7E7E7"  style="padding: 2px 2px 2px 2px;">
				   <tr>
						 <td align="left" class="info_title_01" width="25%">
						 	 <spring:message code="sys.arAffirm.title.postTypeName"/><!--职务类型-->:
						 </td> 
						 <td align="left" class="info_content_01" width="25%" >
							 <select name="Post" id="Post" >
							 <option value="">select</option>
								<c:forEach items="${postList}" var="post">
									<option value="${post.AFFIRMOR_NO}">${post.POSTNAME}</option>
						        </c:forEach>
						     </select>
						  </td>
						  <td align="left" class="info_title_01" colspan="1" >
							 <span onclick="appendRow('add');"><spring:message code="button.add"/><!--添加--></span> &nbsp;|&nbsp;
			          		 <span onclick="appendRow('blank');">
			          		 <spring:message code="sys.arAffirm.title.vacancy"/><!--空缺--></span> &nbsp;|&nbsp;
			           		 <span onclick="deleteRow();"><spring:message code="button.delete"/><!--删除--></span> 
						 </td>
					</tr>
				    <tr>
					  	<td class="info_title_01" width="25%">
					  	<spring:message code="public.title.choose"/><!--选择--></td>
						<td class="info_title_01" colspan="1" >
						<spring:message code="sys.arAffirm.title.postTypeName"/><!--职务类型--></td>
						<td class="info_title_01" width="25%">
						<spring:message code="sys.affirm.title.affirmLevel"/><!--决裁级别--></td> 
				    </tr>
			</table>
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