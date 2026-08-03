<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
function submitFormPre(flag){
	$("#AFFIRM_FLAG").val(flag);
  	var $from = $("#viewOtApplyInfo");
  	$from.submit();
}
function validateOvertimeApplyCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var otTimeType  = document.getElementById("OT_TIME_TYPE").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var applyTypeNo = document.getElementById("APPLY_TYPE_NO").value;
    var applyTypeCode = document.getElementById("APPLY_TYPE_CODE").value;
    
    if(otTimeType == "P"){//如果是时间点（开始日期、开始时间---结束日期、结束时间）方式申请
    	var fromDate  = document.getElementById("FROM_DATE").value;
        var toDate    = document.getElementById("TO_DATE").value;
        var fromTimeHour   = document.getElementById("FROM_TIME_HOUR").value;
        var fromTimeMinute = document.getElementById("FROM_TIME_MINUTE").value;
        var toTimeHour     = document.getElementById("TO_TIME_HOUR").value;
        var toTimeMinute   = document.getElementById("TO_TIME_MINUTE").value;   
        
    	if(toDate==""){
   	       toDate = fromDate ;
   	    }else{
   		    if(fromDate>toDate){
   			   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
   			   return false;
   			}   
   		} 
   	    if(applyTypeCode==""){
   		       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
   		       return false;
   		}
   	  	//如果是连续申请 则只判断时间；否则日期+时间 判断
   		<%--if(document.getElementById("continueApply").checked == true ){
   			if((fromTimeHour-toTimeHour)>0){
   			   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
   			   return false;
   			}
   		}else{
   			var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
   			var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
   			if(comptime(leavefromtime,leavetotime)!=1){
   				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
   				return false;
   			}
   		}--%>
   		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
    }else{//如果是时间长度（加班长度，如：1小时30分）方式申请
    	var applyOtDate    = document.getElementById("APPLY_OT_DATE").value;
        var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;
        var otApplyMinute = document.getElementById("OT_APPLY_MINUTE").value;
        
    	if(applyOtDate==""){
	    	alertMsg.error("加班日期不为空，请选择加班日期！");
	    	return false;
	    }
	    if(otApplyHour==""){
	    	alertMsg.error("加班申请长度不得少于一小时，请重新选择！");
	    	return false;
	    }
	    if(applyTypeCode==""){
		       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
		       return false;
		}
    }
  	
	if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){	          
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
//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	//alert(beginTime+endTime+beginTime);
	//alert(Date.parse(endTime)+" "+Date.parse(beginTime));
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
	if(a<0){
		return -1;
	}else if (a>0){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception'
	}
}
//-->
</script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addRowByID(currentRowID){
    //遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('table:last tbody tr'), function(i, tr){
        if($(this).attr('id')==currentRowID){
            //获取当前行
            var currentRow=$('table:last tbody tr:eq('+i+')');
            //要添加的行的id
            var addRowID=i+2;
            str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
            		+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmor(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByID(this.id);"/>&nbsp;&nbsp;&nbsp;'
            			+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			//先删除，再排序
            			+'	onclick="javaScript:document.all.addAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowByID();"/>'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   var tb2 = document.getElementById("addAffirm_list");
   var rowCount = tb2.rows.length;
   for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
   }
}
function delRowByID(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_list");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}


var keyCodeInit=0;
function submitKeyClick_affirmor(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt>1 ){
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdList?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId_sy0482='+empIdStr
									+'&personId_sy0482='+personIdStr  
									+'&empName_sy0482='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
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
//-->
</script>

<div class="pageContent">

	<div>
		<form id="viewOtApplyInfo" method="post" action="/ess/infoApply/addOvertimeApply" class="pageForm required-validate" 
			onsubmit="return validateOvertimeApplyCallback(this,navTabAjaxDone);">
		   <div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button type="button" onclick="submitFormPre(-1)">
									保存
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="submitFormPre(0)">
									提交
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div>
				<table class="user_table" width="100%" layoutH="98" border="0" cellpadding="0" cellspacing="0">
					
								<tr>
									<td width="20%" class="td_title">工号/姓名/部门</td>
									<td width="30%" class="td_type">
									    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME} / ${personInfo.DEPARTMENT}  
									    <!-- 隐藏的一些参数 -->
					                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${personInfo.PERSON_ID}" />	
									    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" value="31" />		   
									    <input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="PERSON" />
									    <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
									</td>
									<td width="20%" class="td_title">申请日期</td>
									<td width="30%" class="td_type">
									    ${CREATE_DATE}
									</td>
								</tr>
								<c:if test="${defaultCpny eq 'AIT01'}">
									<input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="P"/>
									<tr>
										<td width="20%" class="td_title"><!-- 开始日期 -->
											<spring:message code="public.title.startDate"/>
										</td>
										<td width="30%" class="td_type">
										    <input type="text" id="FROM_DATE" name="FROM_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}" />
										    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
										    &nbsp;&nbsp;
										    <input type="text" id="FROM_TIME_HOUR" name="FROM_TIME_HOUR" class="required" value="${FROM_TIME_HOUR}" min="0" max="23" size="7">:
										    <input type="text" id="FROM_TIME_MINUTE" name="FROM_TIME_MINUTE" class="required" value="${FROM_TIME_MINUTE}" min="0" max="59" size="7">
										</td>
										<td width="20%" class="td_title"><!--加班类型-->
											<spring:message code="ess.viewApply.title.overtimeApplyType"/>
										</td>
										<td width="30%" class="td_type">
										    <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
										    	selected="${APPLY_TYPE_CODE}" limit="all"/>
										    &nbsp;&nbsp;&nbsp;
										    <select id="OT_PLACE_TYPE" name="OT_PLACE_TYPE">
										    	<option value="INSIDE">社内</option>
										    	<option value="OUTSIDE">社外</option>
										    </select>
										</td>
									</tr>
									<tr>
										<td width="20%" class="td_title"><!-- 结束日期 -->
											<spring:message code="public.title.endDate"/>
										</td>
										<td width="30%" class="td_type">
										    <input type="text" id="TO_DATE" name="TO_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
										    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>	
										    &nbsp;&nbsp;
										    <input type="text" id="TO_TIME_HOUR"   name="TO_TIME_HOUR"   class="required" value="${TO_TIME_HOUR}"   min="0" max="23" size="7">:
										    <input type="text" id="TO_TIME_MINUTE" name="TO_TIME_MINUTE" class="required" value="${TO_TIME_MINUTE}" min="0" max="59" size="7">			    
										</td>    
										<td width="20%" class="td_title"><!--是否转调休-->
									    	是否转调休
									    </td>
									    <td width="30%" class="td_type">
									    	<input type="radio" id="ADJUST_YN" name="ADJUST_YN" value="1" title="是" checked="checked"/>是&nbsp;&nbsp;&nbsp;
									    	<input type="radio" id="ADJUST_YN" name="ADJUST_YN" value="0" title="否"/>否
									    </td>
									</tr>
									<%-- 
										<tr>
											<td width="20%" class="td_title"><!--开始时间-->
												<spring:message code="ess.infoApply.title.startTime"/>
											</td>
											<td width="30%" class="td_type">
											    <input type="text" id="FROM_TIME_HOUR" name="FROM_TIME_HOUR" class="required" value="${FROM_TIME_HOUR}" min="0" max="23" size="7">:
											    <input type="text" id="FROM_TIME_MINUTE" name="FROM_TIME_MINUTE" class="required" value="${FROM_TIME_MINUTE}" min="0" max="59" size="7">	
											</td>
											<td width="20%" class="td_title"><!--结束时间-->
												<spring:message code="ess.infoApply.title.endTime"/>
											</td>
											<td width="30%" class="td_type">
											    <input type="text" id="TO_TIME_HOUR"   name="TO_TIME_HOUR"   class="required" value="${TO_TIME_HOUR}"   min="0" max="23" size="7">:
											    <input type="text" id="TO_TIME_MINUTE" name="TO_TIME_MINUTE" class="required" value="${TO_TIME_MINUTE}" min="0" max="59" size="7">
											</td>    
											
											<td width="20%" class="td_title"><!-- 扣除时间 -->
												<spring:message code="ar.viewshift.title.kouchushijian"/>
											</td>
											<td width="30%" class="td_type">
											    <select name="OT_DEDUCT_TIME" id="OT_DEDUCT_TIME">
													<option value=""><!--请选择-->
							                   			<spring:message code="sys.affirm.title.choose"/>
							                   		</option>
													<c:forEach items="${otDeductTimeList}" var="deductList">
														<option value="${deductList.DEDUCT_TIME}" <c:if test="${deductList.DEDUCT_TIME eq OT_DEDUCT_TIME}">selected</c:if>>
															${deductList.DEDUCT_TIME}
														</option>
													</c:forEach>
												</select>	   
											</td>
										</tr>		
									--%>	
									<tr>
										<td width="20%" class="td_title">
											时间长度
										</td>
										<td width="30%" class="td_type">
										    <select name="OT_APPLY_HOUR" id="OT_APPLY_HOUR" disabled="disabled">
												<option value=""><!--请选择-->
						                   			<spring:message code="sys.affirm.title.choose"/>
						                   		</option>
												<c:forEach var="h" begin="1" end="8" step="1">
													<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
														${h}小时
													</option>
												</c:forEach>
											</select>
											<select name="OT_APPLY_MINUTE" id="OT_APPLY_MINUTE" disabled="disabled">
												<<option value=""><!--请选择-->
						                   			<spring:message code="sys.affirm.title.choose"/>
						                   		</option>-->
												<c:forEach var="m" begin="0" end="59" step="30">
													<option value="${m}" <c:if test="${m eq OT_APPLY_MINUTE}">selected</c:if>>
														${m}分
													</option>
												</c:forEach>
											</select>	   
										</td>
										<td width="20%" class="td_title"><!--是否跨天-->
									    	是否跨天
									    </td>
									    <td width="30%" class="td_type">
									    	<%-- 
									    	<input type="radio" id="BEGIN_DAY_OFFSET" name="BEGIN_DAY_OFFSET" value="1" title="是" checked="checked"/>是&nbsp;&nbsp;&nbsp;
									    	<input type="radio" id="BEGIN_DAY_OFFSET" name="BEGIN_DAY_OFFSET" value="0" title="否"/>否
									    	--%>
									    	<input type="hidden" id="BEGIN_DAY_OFFSET" name="BEGIN_DAY_OFFSET" value="0"/>
									    	<input type="radio" id="END_DAY_OFFSET" name="END_DAY_OFFSET" value="1" title="是" checked="checked"/>是&nbsp;&nbsp;&nbsp;
									    	<input type="radio" id="END_DAY_OFFSET" name="END_DAY_OFFSET" value="0" title="否"/>否
									    </td>
									</tr>
								</c:if>
								<c:if test="${defaultCpny ne 'AIT01'}">
									<input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="L"/>
									<tr>
										<td width="20%" class="td_title"><!-- 开始日期 -->
											<spring:message code="public.title.startDate"/>
										</td>
										<td width="30%" class="td_type">
										    <input type="text" id="APPLY_OT_DATE" name="APPLY_OT_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${APPLY_OT_DATE}" />
										    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
										</td>
										<td width="20%" class="td_title"><!--加班类型-->
											<spring:message code="ess.viewApply.title.overtimeApplyType"/>
										</td>
										<td width="30%" class="td_type">
										    <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
										    	selected="${APPLY_TYPE_CODE}" limit="all"/>
										    &nbsp;&nbsp;
										    <select id="OT_PLACE_TYPE" name="OT_PLACE_TYPE">
										    	<option value="社内">社内</option>
										    	<option value="社外">社外</option>
										    </select>
										</td> 
									</tr>		
									<tr>
										<td width="20%" class="td_title">
											时间长度
										</td>
										<td width="30%" class="td_type">
										    <select name="OT_APPLY_HOUR" id="OT_APPLY_HOUR">
												<option value=""><!--请选择-->
						                   			<spring:message code="sys.affirm.title.choose"/>
						                   		</option>
												<c:forEach var="h" begin="1" end="8" step="1">
													<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
														${h}小时
													</option>
												</c:forEach>
											</select>
											<select name="OT_APPLY_MINUTE" id="OT_APPLY_MINUTE">
												<option value=""><!--请选择-->
						                   			<spring:message code="sys.affirm.title.choose"/>
						                   		</option>
												<c:forEach var="m" begin="0" end="59" step="5">
													<option value="${m}" <c:if test="${m eq OT_APPLY_MINUTE}">selected</c:if>>
														${m}分
													</option>
												</c:forEach>
											</select>	   
										</td>
										<td width="20%" class="td_title"><!--是否转调休-->
									    	是否转调休
									    </td>
									    <td width="30%" class="td_type">
									    	<input type="radio" id="ADJUST_YN" name="ADJUST_YN" value="1" title="是" checked="checked"/>是&nbsp;&nbsp;&nbsp;
									    	<input type="radio" id="ADJUST_YN" name="ADJUST_YN" value="0" title="否"/>否
									    </td>
									</tr>
								</c:if>
								<tr>									
								    <td width="20%" class="td_title"><!--加班事由-->
								    	加班事由
								    </td>
								    <td width="80%" class="td_type" colspan="3">
								    	<input type="text" id="APPLY_REMARK" name="APPLY_REMARK" size="80"/>
								    </td>
								</tr>
								<tr>
								    <td width="20%" class="td_title"><!--人事政策-->
								    	人事政策
								    </td>
								    <td width="80%" class="td_type" colspan="3">
								    	<textarea name="ROLE_INFO_HRM" cols="90" rows="2">默认显示（分别写明各种班次对应的加班有效时间段）</textarea>
								    </td>
								</tr>
								<tr>
									<td class="td_title" rowspan="2" width="25%">决裁线</td>
									<td class="td_title" width="25%">决裁等级</td>
									<td class="td_title" width="25%">决裁者</td>
									<td class="td_title" width="25%">是否新增</td>
								</tr>
								<tr>
									<td colspan="3">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_list">
											<tbody>
												<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
													<tr id="${affirmor.AFFIRMOR_ID}">
														<td class="td_type" style="text-align: center" width="33%">
															${j.index+1}
														</td>
														<td class="td_type" style="text-align: center" width="33%">
															[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
														</td>
														<td class="td_type" style="text-align: center" width="33%">
															<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加"
																border="0" align="absmiddle" style="cursor:hand" onclick="addRowByID(this.id)"/>
														</td>
													</tr>
												</c:forEach>
												<a id="onck" name="onck"  href="" lookupGroup="person"></a>
											</tbody>
										</table>
									</td>	
								</tr>
							</table>
			</div>
	  	</form>	
	</div>
</div>