<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateEditPOtApplyCallback(form,callback,flag) {	
	var $form = $("#updatePOtApplyForm");
	if (!$form.valid()) {
		return false;
	}
	//$("#AFFIRM_FLAG").val(flag);
	$("#updatePOtApplyForm input[id='AFFIRM_FLAG']").val(flag);
	
	var checked = false ;
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
		if(checkBoxObj.checked){
			checked = true ;      
		}	    
	});
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
		if(checkBoxObj.checked){
	      	checked = true ;
	      	var applyNo = $(checkBoxObj).val() ;	
			if($form.find("[name='"+applyNo+"_FROM_DATE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
				$form.find("[name='"+applyNo+"_FROM_DATE']").focus();
				checked=false;
		   	}  
		  	if($form.find("[name='"+applyNo+"_OT_FROM_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+applyNo+"_FROM_TIME_HOUR']").focus();
				checked=false;
		   	}
		  	if($form.find("[name='"+applyNo+"_OT_TO_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+applyNo+"_FROM_TIME_MINUTE']").focus();
				checked=false;
		   	}
		   
	        if($form.find("select[name='"+applyNo+"_OT_TYPE_CODE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
				$form.find("select[name='"+applyNo+"_OT_TYPE_CODE']").focus();
				checked=false;
			}

	        if($form.find("select[name='"+applyNo+"_OT_PLACE_TYPE']").val()==''){
				alertMsg.error("社内/社外为必选项，请选择！");
				$form.find("select[name='"+applyNo+"_OT_PLACE_TYPE']").focus();
				checked=false;
			}

	        if($form.find("select[name='"+applyNo+"_ADJUST_YN']").val()==''){
				alertMsg.error("是否转调休为必选项，请选择！");
				$form.find("select[name='"+applyNo+"_ADJUST_YN']").focus();
				checked=false;
			}
			
	        var endDayOffset = $form.find("[name='"+applyNo+"_END_DAY_OFFSET']").val();
	      	var fromDate = $form.find("[name='"+applyNo+"_FROM_DATE']").val();
	      	var fromTime = $form.find("[name='"+applyNo+"_OT_FROM_TIME']").val();
	        //如果结束日期为空，默认为当天日期
	      	var	toDate = fromDate;
			var toTime = $form.find("[name='"+applyNo+"_OT_TO_TIME']").val();
	      	
   			var leavefromtime = fromDate + " " + fromTime + ":" + "00";
   			var leavetotime = toDate + " " + toTime +":" + "00";
   			//因为没有结束日期的选择所以只需要在没有跨天时比较时间即可
   			//加班不跨天时，结束时间一定要晚于开始时间
   			if(endDayOffset == '0'){
	   			if(comptime(leavefromtime,leavetotime)!=1){
	    			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime1"/>');
	    			checked=false;
	    			return false;
	   			}
	   			if(comptime2(leavefromtime,leavetotime)!=1){
	   				alertMsg.error("加班时长不得低于一小时，请重新选择加班时间！");
	   				checked=false;
	   				return false;
	   			}
   			}
		}
	});

	if(checked){		
		if (confirm ("确定要修改申请吗？")){		
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("searchPOvertimeApplyBatchForm");
					alertMsg.correct(data.message);
				}else{
					if(data.result=="2"){
						alertMsg.info(data.message);
					}else{
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});			
	    return false;
		}
	}
	return false ;
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	
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

//比较时间 格式 yyyy-mm-dd hh:mi:ss，加班时间
function comptime2(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
	if(a<0){
		return -1;
	}else if (a>=1){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception'
	}
}

function pageFromSea(a){  
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?""
			:$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/ess/affirmApply/viewEditPOtApplyList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>

<script type="text/javascript">
<!--
//获取该日期的加班类型
function getPOtApplyTypeEdit(apply_no){
	var $form = $("#updatePOtApplyForm");
	var fromDate = $("#updatePOtApplyForm input[id='"+apply_no+"_FROM_DATE']").val();
	var adjustYn = $("#updatePOtApplyForm select[id='"+apply_no+"_ADJUST_YN']").val();
    var personid =$("#updatePOtApplyForm input[id='"+apply_no+"_PERSON_ID']").val();
	
	if(fromDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
			 
			data:'DDATE_STR=' + fromDate +'&PERSON_ID='+personid,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				if(dateType == '1440'){  
					$form.find("select[name='"+apply_no+"_OT_APPLY_TYPE_CODE']").attr("value","32");
					$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("value",'0');
			    	$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("disabled",true);
				}else if(dateType == '1441'){
					$form.find("select[name='"+apply_no+"_OT_APPLY_TYPE_CODE']").attr("value","33");
					$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("value",'0');
					$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("disabled",false);
				}else if(dateType == '1442'){
					$form.find("select[name='"+apply_no+"_OT_APPLY_TYPE_CODE']").attr("value","34");
					$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("value",'0');
					$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("disabled",true);
				}else{
			    	$form.find("select[name='"+apply_no+"_OT_APPLY_TYPE_CODE']").attr("value","32");
			    	$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("value",'0');
			    	$form.find("select[name='"+apply_no+"_ADJUST_YN']").attr("disabled",true);
				}
			}
		});
	}
	//setTimeout("getPOtApplyTypeBatch("+apply_no+","+i+")",1000);
}

//计算时长
function calPoTLengthEdit(apply_no){
	var from_date = $("#updatePOtApplyForm input[id='"+apply_no+"_FROM_DATE']").val();
	var fromTime = $("#updatePOtApplyForm select[id='"+apply_no+"_OT_FROM_TIME']").val();
	var toTime = $("#updatePOtApplyForm select[id='"+apply_no+"_OT_TO_TIME']").val();
	var endDayOffset = $("#updatePOtApplyForm select[id='"+apply_no+"_END_DAY_OFFSET']").val();
	
	if(from_date==""){
		alertMsg.error("请先选择加班日期！");
       	return false;
	}
	
	var otfromtime = from_date + " " + fromTime + ":" + "00";
	var ottotime = from_date + " " + toTime + ":" + "00";
	
	if(comptime(otfromtime,ottotime)!=1 && endDayOffset == '0'){
		alertMsg.error("结束时间要不得早于开始时间，请重新选择结束时间！");
	    return false;
	}
	if(comptime(otfromtime,ottotime)==1 || endDayOffset == '1'){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getOtApplyLength",
			 data: [{ name: 'END_DAY_OFFSET', value: endDayOffset },
			        { name: 'OT_FROM_TIME', value: otfromtime },
			        { name: 'OT_TO_TIME', value: ottotime }],
			 dataType:"json",
			 success: function(response) {
				 document.getElementById(apply_no+"_OT_APPLY_LENGTH").innerHTML = response;
			 }
		});
	}
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewEditPOtApplyList" method="post" 
	      rel="pagerForm" name="searchPOvertimeApplyBatchForm" id="searchPOvertimeApplyBatchForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="text-align:center" width="10%"><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>:
					</td>
					<td width="20%">
						 <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
					</td>		
					<td style="text-align:center" width="10%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>：
					</td>						
					<td width="20%">
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}"/>
					</td>
	                <td style="text-align:center"><!-- 加班类型 -->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>:
					</td>				
					<td>
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
					</td> 
					<td>&nbsp;</td>
				</tr>
				<tr>
	                <td style="text-align:center"><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>:
	                </td>			
				    <td>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td style="text-align:center"><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>:
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td> 
					<%-- 
					<td style="text-align:center" width="10%"><!-- 决裁状态 -->
						<spring:message code="ess.viewApply.title.affirmStatus"/>:
					</td>			
					<td width="20%">  
					     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>未提交</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未决裁</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>已取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>决裁中</option>
						 </select>       
					</td>
					--%>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
			<ul>
				<li>
					    <div class="buttonActive">
							<div class="buttonContent">
							    <button type="submit">
							       <spring:message code="public.title.search"/><!-- 检索 -->
							    </button>
					        </div>
				        </div>	
				</li>
			</ul>
	</div>
		</div>
	</form>
	
</div>

<div class="pageContent" > 
	<div class="formBar">
		<ul>
			<li>
			    <div class="buttonActive">
					<a class="update" onclick="validateEditPOtApplyCallback('updatePOtApplyForm',DWZ.ajaxDone,'-1')" href="#" >
			        <span>批量保存<!--批量保存--></span></a>
				</div>
			    <div class="buttonActive">
			        <a class="update" onclick="validateEditPOtApplyCallback('updatePOtApplyForm',DWZ.ajaxDone,'0')" href="#" >
			        <span>批量提交<!--批量提交--></span></a>
				</div>	
			</li>
		</ul>
	</div>
	<form name="updatePOtApplyForm" id="updatePOtApplyForm" method="post" action="/ess/affirmApply/updatePOtApplyInBatch" 
		onsubmit="return validateEditPOtApplyCallback(this, navTabAjaxDone);">    
		<input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="P"/>
		<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
		<table class="table" width="120%" height="80%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
				    <th width="10" style="text-align: center">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th width="15" style="text-align: center"><!--NO.-->
						NO.
					</th>
					<th width="25" style="text-align: center"><!--是否批量-->
						是否批量
					</th>
					<th width="45" style="text-align: center"><!--部门-->
						部门
					</th>
					<th width="40" style="text-align: center"><!--申请人-->
						申请人
					</th>
					
					
					<th width="45" style="text-align: center"><!--开始日期-->
						加班日期
					</th>	
					<th width="40" style="text-align: center"><!--开始时间-->
						开始时间
					</th>
					<th width="40" style="text-align: center"><!--结束时间-->
						结束时间
					</th>
					<th width="30" style="text-align: center"><!-- 是否跨天 -->
						是否跨天
					</th>
					<th width="45" style="text-align: center"><!--申请时长-->
						申请时长
					</th>
					
					<th width="35" style="text-align: center"><!-- 加班类型 -->
						加班类型
					</th>	
					<th width="30" style="text-align: center"><!--是否调休-->
						是否调休
					</th>
					<th width="30" style="text-align: center"><!--加班事由-->
						加班事由
					</th>
					
					<th width="25" style="text-align: center"><!--修改-->
						修改
					</th> 
					<th width="40" style="text-align: center"><!--详细查看-->
						详细查看
					</th>						
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otApplyList}" var="otPerson" varStatus="i">			
					<tr target="sid">
					    <td style="text-align: center">
					        <input type="checkbox" id="c1" name="c1" value="${otPerson.APPLY_NO}" />
					        <input type="hidden" id="${otPerson.APPLY_NO}_PERSON_ID" name="${otPerson.APPLY_NO}_PERSON_ID" value="${otPerson.PERSON_ID}" />
					    </td>
					    <td style="text-align: center">${otPerson.APPLY_NO}</td>
					    <td style="text-align: center">
							<c:if test="${otPerson.APPLY_TYPE eq 'PERSON' }">
								个人
							</c:if>
							<c:if test="${otPerson.APPLY_TYPE eq 'BATCH' }">
								批量
							</c:if>
						</td>
						<td style="text-align: center">${otPerson.DEPT_NAME}</td>
						<td style="text-align: center">[${otPerson.EMPID}]${otPerson.LOCAL_NAME}</td>
						
						<td style="text-align: left">
							<input type="text" id="${otPerson.APPLY_NO}_FROM_DATE" name="${otPerson.APPLY_NO}_FROM_DATE" class="date" size="10"
								format="yyyy-MM-dd" readonly="true" value="${otPerson.FROM_DATE}" onpropertychange="getPOtApplyTypeEdit(${otPerson.APPLY_NO});"/>
						    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						</td>
						<td style="text-align: center">
					     	<ait:time name="${otPerson.APPLY_NO}_OT_FROM_TIME" spacing="30" selected="${otPerson.FROM_TIME}" 
					     		onChange="calPoTLengthEdit(${otPerson.APPLY_NO});"/>
					    </td>
						<td style="text-align: center">
						    <ait:time name="${otPerson.APPLY_NO}_OT_TO_TIME" spacing="30" selected="${otPerson.TO_TIME}" 
						    	onChange="calPoTLengthEdit(${otPerson.APPLY_NO});"/>					
					    </td>
					     <td>
					    	<select id="${otPerson.APPLY_NO}_END_DAY_OFFSET" name="${otPerson.APPLY_NO}_END_DAY_OFFSET" onchange="calPoTLengthEdit(${otPerson.APPLY_NO});">
								<option value="0" <c:if test="${otPerson.END_DAY_OFFSET eq '0' }">selected="selected"</c:if>>
									否
		                   		</option>
		                   		<option value="1" <c:if test="${otPerson.END_DAY_OFFSET eq '1' }">selected="selected"</c:if>>
									是
		                   		</option>
							</select>
					    </td>
						<td>
					    	<div id="${otPerson.APPLY_NO}_OT_APPLY_LENGTH"></div>
						</td>
						
					    <td style="text-align: center">
							<ait:SelectSyCodeByCpnyID name="${otPerson.APPLY_NO}_OT_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
								selected="${otPerson.OT_TYPE_CODE }" limit="all"/>
						    <select id="${otPerson.APPLY_NO}_OT_PLACE_TYPE" name="${otPerson.APPLY_NO}_OT_PLACE_TYPE">
						    	<option value="INSIDE" <c:if test="${otPerson.OT_PLACE_TYPE eq 'INSIDE' }">selected</c:if>>社内</option>
						    	<option value="OUTSIDE" <c:if test="${otPerson.OT_PLACE_TYPE eq 'OUTSIDE' }">selected</c:if>>社外</option>
						    </select>
						</td>
					    <td style="text-align: center">
							<select id="${otPerson.APPLY_NO}_ADJUST_YN" name="${otPerson.APPLY_NO}_ADJUST_YN">
						    	<option value="0" <c:if test="${otPerson.ADJUST_YN eq '0'}">selected</c:if>>否</option>
						    	<option value="1" <c:if test="${otPerson.ADJUST_YN eq '1'}">selected</c:if>>是</option>
						    </select>
					    </td>
					    
						<td style="text-align: left">
							<input type="text" id="${otPerson.APPLY_NO}_APPLY_REMARK" name="${otPerson.APPLY_NO}_APPLY_REMARK" 
								value="${otPerson.APPLY_OT_REMARK }" size="20"/>
						</td>
						<td style="text-align: center">
							<a class="update" href="/ess/affirmApply/viewEditPOtApplyInfo?APPLY_NO=${otPerson.APPLY_NO}" title="修改加班"
								target="navTab"><font color="red">修改</font></a>	
						</td>
						<td style="text-align: center">
						    <a rel="otPEditRemark" href="/ess/affirmApply/viewFullApplyCheckInfo?seach_APPLY_NO=${otPerson.APPLY_NO}" title="详细查看"
				          		target="dialog" mask="true" width="1250" height="450" id="otPEditRemarkHref"><font color="red">详细查看</font></a>
						</td>        														
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
	<div id="otPEditRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/affirmApply/viewEditPOtApplyList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	