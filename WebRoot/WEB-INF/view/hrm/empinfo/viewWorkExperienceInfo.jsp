<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/><!--开始时间--></td>';
	   		htm+='<td class="td_type"><input type="text" id="START_DATE' + i + '"  name="START_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/><!--结束时间--></td>';
	   		htm+='<td class="td_type"><input type="text" id="END_DATE' + i + '"  name="END_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/><!--工作单位--></td>';
	   		htm+='<td class="td_type" colspan="3"><input type="text" name="CPNY_NAME' + i + '" class="textInput required" maxlength="30"/></td>';
	   		
	   		
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title"><spring:message code="hr.viewWorkInfo.title.DEPT_NAME"/><!--负责业务--></td>';
	   		htm+='<td class="td_type"><input type="text" name="DEPT_NAME' + i + '" class="textInput" maxlength="30"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewWorkInfo.title.POSITION"/><!--职位--></td>';
	   		htm+='<td class="td_type"><input type="text" name="POSITION' + i + '" class="textInput" maxlength="15"/></td>';
	   		htm+='<td  class="td_title"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></td>';
	   		htm+='<td class="td_type"><input type="text" name="DUTY' + i + '" class="textInput" maxlength="15"/></td>';
	   		htm+='<td  class="td_title"><spring:message code="hr.viewPersonalInfo.title.gongzidaiyu"/><!--工资待遇--></td>';
	   		htm+='<td class="td_type"><input type="text" name="PAYROLL' + i + '" class="textInput" maxlength="15"/></td>';
	   		
	   		htm+='</tr>';
	   		htm+='</table>';


	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewWorkExperienceInfo(form, callback) {
	
		var $form = $("#viewWorkExperienceInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		
		var count = parseInt($("#count").val());
		
		for (i=0;i<count;i++){
			
			if(document.getElementById("START_DATE"+i) != null ){
				
				var sd=document.getElementById("START_DATE"+i).value;
				var ed=document.getElementById("END_DATE"+i).value;
				
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				
				if (date1 - date2 > 0) {
					activity="1";
					alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//开始时间不能晚于结束时间
					document.getElementById("START_DATE"+i).focus();
					return false;
				}
				
				<c:forEach items="${workExperienceList}" var="item" >
	   				var startdate ="${item.START_DATE}";
	   				var enddate ="${item.END_DATE}";
	   				
	   				var datestart = startdate.replaceAll("-","");
					var dateend = enddate.replaceAll("-","");
					
					if(date1-datestart>=0 && date1-dateend<=0 ){
						//开始时间存在于已有的开始和结束时间之间 
						alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
						document.getElementById("START_DATE"+i).focus();
						return false;
					}
					if(date1-datestart<0 && date2-dateend>0){
						//开始结束时间包含了现有的开始结束时间
						alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
						document.getElementById("START_DATE"+i).focus();
						return false;
					}
					if(date2-datestart>0 && date2-dateend<0){
						//结束时间存在于现有的开始和结束时间之间
						alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
						document.getElementById("END_DATE"+i).focus();
						return false;
					}
				</c:forEach>
			
				for(j=0;j<count;j++){
					if(j!=i){
						if(document.getElementById("START_DATE"+j)!=null){
						var sdj=document.getElementById("START_DATE"+j).value;
						var edj=document.getElementById("END_DATE"+j).value;
				
						var sddatej = sdj.replaceAll("-","");
						var eddatej = edj.replaceAll("-","");
						
						if(date1-sddatej>=0 && date1-eddatej<=0 ){
							//开始时间存在于已有的开始和结束时间之间 
						alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("START_DATE"+i).focus();
							return false;
						}
						if(date1-sddatej<0 && date2-eddatej>0 ){
							//开始结束时间包含了现有的开始结束时间
							alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("START_DATE"+i).focus();
							return false;
						}
						if(date2-sddatej>0 && date2-eddatej<0 ){
							//结束时间存在于现有的开始和结束时间之间
							alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("END_DATE"+i).focus();
							return false;
						}
					}
				}
					}
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
</script>



<div class="pageContent">
	<form id="viewWorkExperienceInfo" method="post" action="/hrm/empinfo/addWorkExperienceInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewWorkExperienceInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
						<!--开始时间-->
					</td>
					<td class="td_type">
						<input type="text" id="START_DATE0"  name="START_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
						<!--结束时间-->
					</td>
					<td class="td_type">
						<input type="text" id="END_DATE0" name="END_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/>
						<!--工作单位-->
					</td>
					<td class="td_type" colspan="3">
						<input type="text" name="CPNY_NAME0" class="textInput required"  maxlength="30" >
					</td>
					
					<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewWorkInfo.title.DEPT_NAME"/>
						<!--负责业务-->
					</td>
					<td class="td_type">
						<input type="text" name="DEPT_NAME0" class="textInput"  maxlength="30" >
					</td>
					<td class="td_title">
						<spring:message code="hr.viewWorkInfo.title.POSITION"/>
						<!--职位-->
					</td>
					<td class="td_type">
						<input type="text" name="POSITION0" class="textInput"  maxlength="15" >
					</td>
					<td class="td_title">
						<spring:message code="sys.postManage.title.postGrade"/>
						<!--职级-->
					</td>
					<td class="td_type">
						<input type="text" name="DUTY0" class="textInput"  maxlength="15" >
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.gongzidaiyu"/>
						<!--工资待遇-->
					</td>
					<td class="td_type">
						<input type="text" name="PAYROLL0" class="textInput"  maxlength="15" >
					</td>
					
				</tr>
			</table>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<div>
<table >
<c:forEach items="${workExperienceList}" var="item" varStatus="i">
	<tr target="personId" rel="${item.HEALTH_NO}">
		<td>
			${item.START_DATE}
		</td>
		<td>
			${item.END_DATE}
		</td>
	</tr>
</c:forEach>
</table>
</div>