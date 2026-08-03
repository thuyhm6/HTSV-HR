<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateOvertimeApplyConfirmCallback(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);

	if (!$form.valid()) {
		return false;
	}

//    var checked = true ;
//	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
//	if(checkBoxObj.checked){
//	   checked = true ;      
//	  }	    
//	  });

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	  
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.humanConfirm.choosePersonToConfirm"/>');
		return false;
	}

    $form.attr("action","/ess/humanAffirm/confirmOvertimeApplyInBatch?CONFIRM_FLAG="+flag);

    if(document.getElementById("CPNY_ID").value == "C01"||document.getElementById("CPNY_ID").value == "C11"){
    	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;
	      var applyNo = $(checkBoxObj).val() ;
          if($form.find("select[name='"+applyNo+"_CONVERT_CODE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.humanConfirm.overtimeConvertIsMust"/>');
				$form.find("select[name='"+applyNo+"_CONVERT_CODE']").focus();
				checked=false;
				return false;
			}	 
	    }
	  });
    }else{
    	$form.attr("action","/ess/humanAffirm/confirmOvertimeApplyInBatch?CONFIRM_FLAG="+flag+"&&CONVERT_CODE=''");
    }
    
    if(!checked){
      return false;
    }

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchOvertimeConfirmForm");
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

function passOvertimeApplyConfirmValue(applyNo,flag)
{  
	var convertTypeCode;
    if(document.getElementById("CPNY_ID").value == "C01"||document.getElementById("CPNY_ID").value == "C11"){ 
    	convertTypeCode = document.getElementById(applyNo+"_CONVERT_CODE").value;
		if(convertTypeCode==""){
	       alertMsg.error('<spring:message code="alert.message.ess.humanConfirm.overtimeConvertIsMust"/>');
	       return false;
		}
    }
    document.getElementById(applyNo+"_viewOvertimeApplyBatchConfirmHref").href = 
    document.getElementById(applyNo+"_viewOvertimeApplyBatchConfirmHref").href
    	+"&&CONVERT_CODE="+convertTypeCode+"&&CONFIRM_FLAG="+flag;
    document.getElementById(applyNo+"_viewOvertimeApplyBatchConfirmHref").click();			
}

function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_OT_FROM_TIME=$("#seach_OT_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_OT_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_OT_TO_TIME=$("#seach_OT_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_OT_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_OT_APPLY_CODE=$("#seach_OT_APPLY_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_OT_APPLY_CODE",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/humanAffirm/viewOvertimeApplyConfirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_OT_FROM_TIME="+seach_OT_FROM_TIME+"&seach_OT_TO_TIME="+seach_OT_TO_TIME+"&seach_OT_APPLY_CODE="+seach_OT_APPLY_CODE+"&seach_STATUS_CODE="+seach_STATUS_CODE);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/humanAffirm/viewOvertimeApplyConfirmList" method="post" 
	      name="searchOvertimeConfirmForm" id="searchOvertimeConfirmForm" rel="pagerForm" >
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->:
				</td>
				<td>
					 <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>		
				<td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
					<spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_OT_APPLY_CODE" parentNo="31" cnpyID="${defaultCpny}"  selected="${OT_APPLY_CODE}" limit="all"/>       
				</td> 				
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!--决裁状态-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>			
			</tr>
			<tr>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
                </td>			
			    <td>
			        <input type="text" id="seach_OT_FROM_TIME" name="seach_OT_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${OT_FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" id="seach_OT_TO_TIME" name="seach_OT_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${OT_TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				
								 
			</tr>
		</table>
		<div class="subBar">
				<ul>
					<li>
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                 <spring:message code="public.title.search"/><!-- 检索 -->
                            </button></div></div>
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
					<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateOvertimeApplyConfirmCallback('updateOvertimeApplyConfirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateOvertimeApplyConfirmCallback('updateOvertimeApplyConfirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</div> 
<form name="updateOvertimeApplyConfirmForm" id="updateOvertimeApplyConfirmForm" method="post" 
      action="/ess/humanAffirm/confirmOvertimeApplyInBatch" class="pageForm required-validate" 
	  onsubmit="return validateOvertimeApplyConfirmCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="231" nowrapTD="false">
		<thead>
			<tr>
			    <th width="50" style="text-align: center">
			    	<input type="checkbox" class="checkboxCtrl" group="c1" />
			    </th>
				<th width="80" style="text-align: center"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="80" style="text-align: center"><!--姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="120" style="text-align: center"><!--部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="130" style="text-align: center"><!--职岗位-->
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="150" style="text-align: center"><!--职级名称（职务）-->
					<spring:message code="public.title.postName"/>
				</th>
				
				<th width="140" style="text-align: center"><!--申请日期-->
					<spring:message code="ess.viewApply.title.applyDate"/>
				</th>		
				<th width="140" style="text-align: center"><!--加班类型-->
					<spring:message code="ess.viewApply.title.overtimeApplyType"/>
				</th>				
				<th width="180" style="text-align: center"><!--加班时段-->
					<spring:message code="ess.viewApply.title.overtimeShift"/>
				</th>						
				<th width="80" style="text-align: center"><!--扣除时间-->
					<spring:message code="ar.viewshift.title.kouchushijian"/>
				</th>	
				<th width="80" style="text-align: center"><!--长度(小时)-->
					<spring:message code="ess.viewApply.title.lengthHough"/>
				</th>
				
				<%-- <th width="80" style="text-align: center"><!--剩余调休-->
					<spring:message code="ess.infoApply.title.surAdjustRest"/>
				</th> --%>
				<th width="120" style="text-align: center"><!--加班原因-->
					<spring:message code="ess.viewApply.title.overtimeReasono"/>
				</th>	
				<th width="200" style="text-align: center"><!--决裁情况-->
					<spring:message code="ess.viewApply.title.affirmCondition"/>
				</th>
				<th width="140" style="text-align: center"><!--人事确认-->
					<spring:message code="ess.viewApply.title.humanAffirm"/>
				</th>	
				<%-- <c:if test="${IF_CONVERT_APPLY eq 1}" >
					<th width="120" style="text-align: center"><!--加班转换-->
						<spring:message code="ess.viewApply.title.overtimeTranslate"/>
					</th>	
				</c:if>		 --%>				
			
				<c:if test="${IF_CONVERT_APPLY eq 1}" >
					<th width="120"><spring:message code="ess.viewApply.title.overtimeTranslate"/><!--加班转换--></th>	
				</c:if>							
		</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${ovetimeApplyPersonList}" var="ovetimeApplyPerson" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">
				        <input type="checkbox" id="c1" name="c1" value="${ovetimeApplyPerson.APPLY_NO}" />
				        <input type="hidden" id="CPNY_ID" name="CPNY_ID" value="${CPNY_ID}" />
				        <input type="hidden" id="OT_TYPE_CODE" name="CPNY_ID" value="${ovetimeApplyPerson.OT_TYPE_CODE}" />
				    </td>
					<td style="text-align: center">${ovetimeApplyPerson.EMPID}</td>
					<td style="text-align: center">${ovetimeApplyPerson.LOCAL_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.DEPT_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.POSITION_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.CONTENT}</td>
					
					<td style="text-align: center"><%--${fn:substring(ovetimeApplyPerson.APPLY_OT_DATE,0,10)}--%>
						${fn:substring(ovetimeApplyPerson.CREATE_DATE,0,10)}
					</td>
					<td style="text-align: center">${ovetimeApplyPerson.OT_TYPE_NAME}</td>
					<td style="text-align: center">
			    	    <dt style="padding: 1px;">
                            ${fn:substring(ovetimeApplyPerson.OT_FROM_TIME,0,10)}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${fn:substring(ovetimeApplyPerson.OT_TO_TIME,0,10)}
						</dt>						
					</td>
					<td style="text-align: center">
						${ovetimeApplyPerson.OT_DEDUCT_TIME}
					</td>
					<td style="text-align: center">
						<fmt:formatNumber value="${ovetimeApplyPerson.OT_LENGTH }" pattern="#,##0.00"/>
					</td>
					
					<%--
					<td style="text-align: center">${ovetimeApplyPerson.ADJUST_REST}&nbsp;H</td>
					--%>
					<td>${ovetimeApplyPerson.APPLY_OT_REMARK}</td>
					<td style="text-align: center">              
                        <c:forEach items="${ovetimeApplyPerson.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME} 
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                        <span style="color:green;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>  
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                        <span style="color:red;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                        <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁 --></span> 
								 </c:if>								 
							</dt>
						</c:forEach>						
					</td>
					<td style="text-align: center">
						<dt style="padding: 1px;">
							<c:if test="${ovetimeApplyPerson.ACTIVITY == 0}" >
								<a rel="${ovetimeApplyPerson.APPLY_NO}_overtimeApplyConfirmBatchView" 
										onclick="passOvertimeApplyConfirmValue(${ovetimeApplyPerson.APPLY_NO},'1');">
								   <span style="cursor:pointer;"><spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
								</a>
								<a rel="${ovetimeApplyPerson.APPLY_NO}_overtimeApplyConfirmBatchView" 
										onclick="passOvertimeApplyConfirmValue(${ovetimeApplyPerson.APPLY_NO},'2');">
								   <span style="cursor:pointer;"><spring:message code="ess.viewApply.title.reject"/><!--否决 --></span>
								</a>
								<a rel="${ovetimeApplyPerson.APPLY_NO}_overtimeApplyConfirmBatchView" 
								   		href="/ess/humanAffirm/confirmOvertimeInfoApply?APPLY_NO=${ovetimeApplyPerson.APPLY_NO}&&PERSON_ID=${ovetimeApplyPerson.PERSON_ID}"  
								   		target="ajaxTodo" id="${ovetimeApplyPerson.APPLY_NO}_viewOvertimeApplyBatchConfirmHref" >
								</a>													
							</c:if>
							<c:if test="${ovetimeApplyPerson.ACTIVITY==1}" >
								<span style="color:green;">&nbsp;${ovetimeApplyPerson.LOCAL_NAME}</span>
							    <span style="color:green;">&nbsp;
                                      <spring:message code="ess.viewApply.title.pass"/><!--通过-->
                                </span>
							</c:if>	
							<c:if test="${ovetimeApplyPerson.ACTIVITY==2}" >
							    <span style="color:red;">&nbsp;${ovetimeApplyPerson.LOCAL_NAME}</span>
							    <span style="color:red;">&nbsp;
                                      <spring:message code="ess.viewApply.title.reject"/><!--否决 -->
                                </span>
							</c:if>								
						</dt>	                    
                    </td>
                    <c:if test="${IF_CONVERT_APPLY eq 1}" >
                        <td style="text-align: center">
                        	<select name="${ovetimeApplyPerson.APPLY_NO}_CONVERT_CODE" id="${ovetimeApplyPerson.APPLY_NO}_CONVERT_CODE">     
                        		<option value=""><!--请选择-->
                        			--<spring:message code="sys.affirm.title.choose"/>--
                        		</option>
                        		<c:forEach items="${defaultDaoXiuList}" var="daoXiu" varStatus="j">	
	                            	<option value="${daoXiu.PARAM_VALUE }" <c:if test="${ovetimeApplyPerson.DEFAULTTYPE eq daoXiu.PARAM_VALUE}">selected</c:if>>${daoXiu.PARAM_VALUE_NAME }</option>
	                            </c:forEach>
                            </select>                     
                        </td>	                    			
                    </c:if>											
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<div id="overtimeApplyConfirmBatchView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	<c:set value="/ess/humanAffirm/viewOvertimeApplyConfirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	