<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
  
 <script>
function validateCallbackhr_040402(form, callback) {
		
		var $form = $("#hr_040402");
		 
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
	
	function selectShare(){
	
	document.getElementById("selectShareHref").href="/hrm/informationRetrieval/viewEmpIdRetrieveList?pageNum=1";
	
	document.getElementById("selectShareHref").click();

	}
	function empid_personel(person_id,empid,local_name,deptname){
		//alert(person_id+"+"+empid+"+"+local_name+"+"+deptname);
		var personids=$("#personids").val();
// 		alert(personids);
// 		alert(person_id);
		
// 		alert(personids.indexOf(person_id));
		if(personids.indexOf(person_id)==-1){
		personids=personids+"|"+person_id
		$("#personids").attr("value",personids);
		var htm="";
		htm="<tr id=\""+person_id+"\"><td>"+empid+"</td><td>"+local_name+"</td><td>"+deptname+"</td><td><img src=\"/resources/css/ligerUI/skins/icons/delete.gif\" onclick=\"empid__delShiftInfo(\'"+person_id+"\')\"> </td></tr>";
		$("#gongxiangren").append(htm);
		}else{
			alertMsg.error('<spring:message code="liang.hr.viewPersonalInfo.title.get_the_nod"/>');
		}
		
	}
	function empid__delShiftInfo(id){
		var personid=$("#personids").val();
		var str=personid.replace("|"+id,"");
		$("#"+id+"").remove();
		$("#personids").attr("value",str);
	}
</script>

<div class="pageContent">
<form method="post" id="hr_040402" action="/hrm/informationRetrieval/SaveEmpRetrieveShow" class="pageForm required-validate" onsubmit="return validateCallbackhr_040402(this,navTabAjaxDone);">
	                     <input type="hidden"    id="SqlKey" name="SqlKey" value="${SqlKey}"/>	 
		                 <input type="hidden"    id="ColName" name="ColName" value="${ColName}"/>	 
		                 <input type="hidden"  id="CondSql" name="CondSql" value="${CondSql}" />
		                  <input type="hidden"  id="CondSqlCnt" name="CondSqlCnt" value="${CondSqlCnt}" />
		<div class="pageFormContent nowrap" layoutH="67">
		 
		 
		    <ait:SyLanguage/>
		 
		 	<a id="selectShareHref" name="selectShareHref"  href="#" target="dialog" mask="true"  title="<spring:message code="liang.hr.viewPersonalInfo.title.partaker"/>" height="450" width="800"/>
			<input type="button" onclick="selectShare();" value="<spring:message code="liang.hr.viewPersonalInfo.title.partaker"/>" />
			<table class="table" width="100%" layoutH="188"> 
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--社号-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.AFFIRM"/>
					<!--确认-->
				</th>
			</tr>
		</thead>
		<tbody id="gongxiangren">
			
		</tbody>
		</table>
		
		</div>
		  <input type="hidden" name="personids" id="personids" value="">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>