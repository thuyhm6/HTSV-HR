<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">
<!--
function validateCallbackUpdateWorkExperienceInfo(form, callback) {


	var $form = $("#updateWorkExperienceInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("WNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	
	
	
	var workExpListnum= document.getElementById("workExperienceList1Size").value;
	var activity ="0";
	
	
	for (i=0;i<workExpListnum;i++){
		if(document.getElementById("WNO"+i).checked){
			var sd=document.getElementById("START_DATE_"+i).value;
			var ed=document.getElementById("END_DATE_"+i).value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				//开始时间不能晚于结束时间
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');
				document.getElementById("START_DATE_"+i).focus();
				return false;
			}
			for(j=0;j<workExpListnum;j++){
					if(j!=i){
						var sdj=document.getElementById("START_DATE_"+j).value;
						var edj=document.getElementById("END_DATE_"+j).value;
				
						var sddatej = sdj.replaceAll("-","");
						var eddatej = edj.replaceAll("-","");
						
						if(date1-sddatej>=0 && date1-eddatej<=0 ){
							//开始时间存在于已有的开始和结束时间之间 
						alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("START_DATE_"+i).focus();
							return false;
						}
						if(date1-sddatej<0 && date2-eddatej>0 ){
							//开始结束时间包含了现有的开始结束时间
							alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("START_DATE_"+i).focus();
							return false;
						}
						if(date2-sddatej>=0 && date2-eddatej<=0 ){
							//结束时间存在于现有的开始和结束时间之间
							alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.checkStartEndDate_Conflict"/>');
							document.getElementById("END_DATE_"+i).focus();
							return false;
						}
					}
				}
		}
	}
	

	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="WNO"+index;
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>


<div class="pageContent">
	<form id="updateWorkExperienceInfo" method="post" action="/hrm/empinfo/editWorkExperienceInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateWorkExperienceInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="workExperienceList1Size" name="educationListSize" value="${fn:length(workExperienceList1)}" />
	<table class="table" width="101.8%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
					<!--结束时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/>
					<!--工作单位-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.DEPT_NAME"/>
					<!--负责业务-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.POSITION"/>
					<!--职位-->
				</th>
												<th width="80">
													<spring:message code="sys.postManage.title.postGrade" />
													<!--职级-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.gongzidaiyu" />
													<!--工资待遇-->
												</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${workExperienceList1}" var="item" varStatus="i">
				<tr target="workExperNo" rel="${item.WORK_EXPER_NO}">
					
					<td><input type="checkbox" id="WNO${i.index}" name="WNO" value="${item.WORK_EXPER_NO}" /></td>
					
					<td>
						<input type="text" id="START_DATE_${i.index}" name="START_DATE_${item.WORK_EXPER_NO}" size="13" value="${item.START_DATE}" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.index})"/>
					</td>
					
					<td>
						<input type="text" id="END_DATE_${i.index}" name="END_DATE_${item.WORK_EXPER_NO}" size="13" value="${item.END_DATE}" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.index})"/>
					</td>
					
					<td><input onclick="setCheckboxChecked(${i.index})" name="CPNY_NAME_${item.WORK_EXPER_NO}" type="text" maxlength="30" size="15" value="${item.CPNY_NAME}" /></td>
					
					<td><input onclick="setCheckboxChecked(${i.index})" name="DEPT_NAME_${item.WORK_EXPER_NO}" type="text" maxlength="30" size="15" value="${item.DEPT_NAME}" /></td>
					
					<td><input onclick="setCheckboxChecked(${i.index})" name="POSITION_${item.WORK_EXPER_NO}" type="text" maxlength="15" size="15" value="${item.POSITION}" /></td>
					
					<td><input onclick="setCheckboxChecked(${i.index})" name="DUTY_${item.WORK_EXPER_NO}" type="text" maxlength="15" size="15" value="${item.DUTY}" /></td>
					<td><input onclick="setCheckboxChecked(${i.index})" name="PAYROLL_${item.WORK_EXPER_NO}" type="text" maxlength="15" size="15" value="${item.PAYROLL}" /></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
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