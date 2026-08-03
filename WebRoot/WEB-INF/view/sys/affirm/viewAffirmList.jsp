<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
	var redId="";
	var clickBand="";
	function band(obj,codeNo,personId){
		clickBand="band";
		if(redId!=""){
			document.getElementById(redId).style.backgroundColor="white";
		}
		obj.style.backgroundColor="#F0F1F4";
		redId=obj.id;
		document.getElementById("editButton").href="/sys/affirm/updateAffirmView?AFFIRM_TYPE_NO="+codeNo+"&AFFIRM_OBJECT="+personId;
		document.getElementById("deleteButton").href="/sys/affirm/deleteAffirmInfo?AFFIRM_TYPE_NO="+codeNo+"&AFFIRM_OBJECT="+personId;
	}
	function bandBlank(){
		clickBand="";
		if(redId!=""){
			document.getElementById(redId).style.backgroundColor="white";
			redId="";
		}
		document.getElementById("editButton").href="";
	}
	function editAction(){
		if(clickBand=="band"){
			document.getElementById("editButton").click();
		}
	}
	function deleteAction(){
		if(clickBand=="band"){
			document.getElementById("deleteButton").click();
		}
	}
	var keyCodeInit=0;
	function submitKeyClick_sy0130_view(obj,localName,idcardNo,navTabId,event){
	 	var e= event ? event : window.event; 
	 	var keyCode = e.which ? e.which : e.keyCode;
	   	if(keyCode==13){
	   		keyCodeInit=keyCode;
			//var empid=obj.value;
			//  seach_EMPID  seach_AFFIRMOR_ID seach_LOCAL_NAME
			var empid=document.getElementById("seach_EMPID").value;
			localName=document.getElementById("seach_LOCAL_NAME").value;
			var empIdStr="seach_EMPID";
			var personIdStr="seach_AFFIRMOR_ID";

	   		$.ajax({
				type: 'GET',
				url:encodeURI(encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo)),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
							if (jsonObject.perCnt==0){
								alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
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
								document.getElementById("seach_EMPID").value=jsonObject.empId;
								document.getElementById("seach_AFFIRMOR_ID").value=jsonObject.personId;//empName deptName
								document.getElementById("seach_LOCAL_NAME").value=jsonObject.empName;
							     //addTdAdd_sy0130_add(jsonObject.personId,jsonObject.empId,jsonObject.empName,jsonObject.deptName,'');
							}
						},
				error: DWZ.ajaxError
			});
	    }
	 }
	function checkAffirmor(personId,empId,empName,deptName,letters){
		if(document.getElementById("EMPID")&&document.getElementById("personId")&&document.getElementById("affirmorName")){
			addTdAdd_sy0130_add(personId,empId,empName,deptName,letters);
			document.getElementById("EMPID").value=empId;
			document.getElementById("personId").value=personId;
			document.getElementById("affirmorName").innerHTML=empName;
		}else if(document.getElementById("seach_AFFIRMOR_ID")&&document.getElementById("seach_EMPID")){//seach_AFFIRMOR_ID seach_EMPID
			document.getElementById("seach_AFFIRMOR_ID").value=personId;
			document.getElementById("seach_EMPID").value=empId;
		}
		
	}
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
	
	
//	excel导出
// 	function exportAffirmFinalList() {
// 		var sform = document.getElementById("viewShowAffirmFinalApply");
// 		alertMsg.confirm("确定要导出数据吗?", {
// 			okCall : function() {
//				用于excel导出的表单参数处理
// 				var eForm = document.getElementById("excelExportForm_AffirmFinal"); 
// 				document.getElementById("orgLink").innerHTML = "EXCEL密码设置";
// 				eForm.AFFIRMOR_NAME.value 		= sform.seach_AFFIRMOR_NAME.value;
// 				eForm.EMP_ID.value 		= sform.seach_EMP_ID.value;
// 				$("#importExcelDialog_AffirmFinal").attr('href', "/sys/encryptExcel"
// 						+"?exportFunName=/sys/affirm/viewAffirmFinalExcel"
// 						+"&navTabId=ess0307"
// 						+"&formId=excelExportForm_AffirmFinal");
// 				$("#importExcelDialog_AffirmFinal").attr('width', "300");
// 				$("#importExcelDialog_AffirmFinal").attr('height', "150");
// 				$("#importExcelDialog_AffirmFinal").click();
// 			}
// 		});
// 	}
	
	function reportExcel(a){
	var $this=$(a);
    var title = $this.attr("title"); 
    var $form = $("#viewShowAffirmFinalApply");  
    
	  var url ="/sys/affirm/exportBatchLAffirm";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
					}});
}


	function downloadImportTemplate_viweapplyAffirmbatch(){
		var url = "/sys/affirm/exportBatchLAffirmModule?navTabId=sy0130";
		document.getElementById("exportExcel_viweapplyAffirmbatch").href=encodeURI(url);
	}

	function downloadImportTemplate_viweapplyAffirmbatchData(){
		var url = "/sys/affirm/exportBatchLAffirm?navTabId=sy0130";
		document.getElementById("exportExcel_viweapplyAffirmbatchData").href=encodeURI(url);
	}
	function excelimport_viewapplyAffirmbatch(){
		$("#importExcelDialog_sy0130").attr('href','/pa/excelImport/importExcelData?importFunName=/importAffirmTemp');
		$("#importExcelDialog_sy0130").click();
	}
</script> 
<a id="importExcelDialog_sy0130"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_sy0130" href="#" target="navTab" mask="true"><span style="display:none;">漏刷卡信息导入结果</span></a>
<div class="pageHeader">
 <a id="importExcelDialog_AffirmFinal" href="#" target="dialog" mask="true"><span
	id="orgLink" style="display: none"></span></a> 
	<form onsubmit="return navTabSearch(this);" id="viewShowAffirmFinalApply" action="/sys/affirm/viewAffirmList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
					<spring:message code="sys.affirm.title.affirmPerson"/><!--决裁者-->&nbsp;&nbsp;    
				  </td>
				  <td>
					<input type="text" name="seach_AFFIRMOR_NAME" id="seach_AFFIRMOR_NAME" value="${AFFIRMOR_NAME}" /> 
				  </td>
				  <td>
				          <spring:message code="sys.affirm.title.affirmedDeptName"/><!--被决裁部门-->&nbsp;&nbsp;
				  </td>
				  <td>
				          <input type="text" name="seach_EMP_ID" id="seach_EMP_ID" value="${EMP_ID}"/> 
				          <input type="hidden" name="seach_FLAG" id="seach_FLAG" value="1"/> 
	              </td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit"><spring:message code="public.title.search"/><!--检索--></button>
					    </div></div>
					</li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
		<li><a class="buttonActive" id ="exportExcel_viweapplyAffirmbatch" onclick="downloadImportTemplate_viweapplyAffirmbatch();" href="#">
			<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
			</a></li>
		<li><a class="buttonActive" onclick="excelimport_viewapplyAffirmbatch();">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
		<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="reportExcel(this)"
							title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%>
							<spring:message code="ar.addempshift.title.excelexport" />
						</button>
					</div>
				</div>
			</li>
			<c:if test="${toolbarInfo.INSERTR eq '1'}"><li>
			<a class="add" href="/sys/affirm/addAffirmView" target="navTab" mask="true" width="500" height="400" >
			<span>设置</span></a></li></c:if>
			<c:if test="${toolbarInfo.DELETER eq '1'}"><li><a class="delete" onclick="deleteAction();">
			<span><spring:message code="button.delete"/><!--删除--></span></a></li></c:if>
			<c:if test="${toolbarInfo.UPDATER eq '1'}"><li><a class="edit" onclick="editAction();" >
			<span><spring:message code="button.update"/><!--修改--></span></a></li></c:if>
			
			<li class="line">line</li>
		</ul>
		<a id="editButton"  href=""  target="navTab" mask="true" width="500" height="400" style="display:none">
		<spring:message code="button.update"/><!--修改--></a>
		<a id="deleteButton" href="" target="ajaxTodo" 
		   title="<spring:message code='button.delete.sure'/>" style="display:none">
		<span><spring:message code="button.delete"/><!--删除--></span></a>
	</div>
	<table class="table" style="overflow: auto" width="200%"  layoutH="206" nowrapTD="false">
			<thead>
			<tr>
				<th width="40"><spring:message code="sys.affirm.title.indexNum"/><!--序号--></th>
				<th width="120"><spring:message code="public.title.deptName"/><!--部门-->/<spring:message code="sys.affirm.title.personName"/><!--人员名称--></th>
				<c:forEach items="${typeList}" var="type" varStatus="in">
				 <th width="120">${type.CONTENT}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${affirmList}" var="affirm" varStatus="i">
			
				<tr height="30" >
					<td style="background-color:white" onclick="bandBlank();">${i.index+1}</td>
					<td style="background-color:white" onclick="bandBlank();">
					    <span id="${affirm.EMPID}">${affirm.LOCAL_NAME}</span>
					</td>
					<c:forEach items="${typeList}" var="type" varStatus="var">
						<td id="${i.index+1}${type.CODE_NO}"  onclick="band(this,'${type.CODE_NO}','${affirm.PERSON_ID}')" 
						    style="background-color:white;nowrapTD:false;" >
						<c:forEach items="${affirm.detailList}" var="detail" varStatus="d">
							<c:if test="${detail.AFFIRM_TYPE_ID eq type.CODE_NO}">
								<dt style="padding: 2px;">${detail.AFFIRM_LEVEL}.${detail.NAME}</dt>
							</c:if>
						</c:forEach>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/sys/affirm/viewAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_AffirmFinal" name="excelExportForm_AffirmFinal" method="post">
	<input type="hidden" id="password" name="password" value="" />
	<input type="hidden" id="AFFIRMOR_NAME" name="AFFIRMOR_NAME" value="" />
	<input type="hidden" id="EMP_ID" name="EMP_ID" value="" />
</form>
</div>
