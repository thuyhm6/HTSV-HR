<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmPOtApplyCallback(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}

    $form.attr("action","/ess/affirmApply/approveOvertimeApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=OT_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearchWithParam("viewPOtAffirmList",$("#viewPOtAffirmList").serializeArray());
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

function pageFromSea(a){  
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?""
			:$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/ess/affirmApply/viewPOtAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}

function goToHrefUrl(obj,url){
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?""
			:$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_FLAG=$("#seach_APPLY_TYPE_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_FLAG",navTab.getCurrentPanel()).val();
	
	url = url + "&parentParam=OT_TIME_TYPE=P@DEPT_NO="+seach_DEPT_NO+"@KEY="+seach_KEY+"@FROM_TIME="+seach_FROM_TIME+"@TO_TIME="
			+seach_TO_TIME+"@AFFIRM_FLAG="+seach_AFFIRM_FLAG+"@APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+ "@APPLY_TYPE_FLAG="+seach_APPLY_TYPE_FLAG;
	
	obj.target="navTab";
	obj.rel="ess0238_affirm";
	obj.href=url ;
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewPOtAffirmList" method="post" 
	      rel="pagerForm" name="viewPOtAffirmList" id="viewPOtAffirmList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>
					</td>
					<td>
						 <ait:deptList name="seach_DEPT_NO" limit="super"  id="viewPotAffirmList_seachDept"  selected="${DEPT_NO }"/>
					 <ait:deptTreeIcon name="seach_DEPT_NO" limit="super" id="viewPotAffirmList_seachDept" selected="${DEPT_NO}"/>
					</td>		
					<td><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>
					</td>						
					<td>
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}"/>
					</td>
	                <td><!-- 审批状态 -->
						<spring:message code="ess.viewApply.title.affirmStatus"/>
					</td>				
					<td>  
					     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option> 
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>已取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
							 <option value="10" <c:if test="${AFFIRM_FLAG eq '10'}">selected</c:if>>本人审批</option>
							 <option value="11" <c:if test="${AFFIRM_FLAG eq '11'}">selected</c:if>>未到审批</option>
						 </select>       
						 	   <input type="hidden" id="seach_DEFAULTT" name="seach_DEFAULTT" value="default"/>
					</td>
					<td><!-- 加班类型 -->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>
					</td>				
					<td>
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
					</td> 
				</tr>
				<tr>
	                <td><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
	                </td>			
				    <td>
				        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
	                </td>			     
					<td>
					    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td> 
					<td><!-- 审批状态 -->
						批量/个人
					</td>				
					<td>  
					     <select id="seach_APPLY_TYPE_FLAG" name="seach_APPLY_TYPE_FLAG">
							 <option value="">全部</option>
							 <option value="PERSON" <c:if test="${APPLY_TYPE_FLAG eq 'PERSON'}">selected</c:if>>个人</option>
							 <option value="BATCH" <c:if test="${APPLY_TYPE_FLAG eq 'BATCH'}">selected</c:if>>批量</option>
						 </select>       
					</td>
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
			         <a class="update" onclick="validateAffirmPOtApplyCallback('updatePOtApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
			         <span><spring:message code="ess.title.passInBatch"/></span></a>
				</div>
			    <div class="buttonActive">
			         <a class="update" onclick="validateAffirmPOtApplyCallback('updatePOtApplyAffirmForm',DWZ.ajaxDone,'2')" href="#" >
			         <span><spring:message code="ess.title.rejectInBatch"/></span></a>
                  	</div>	
			</li>
		</ul>
	</div>
	 <form name="updatePOtApplyAffirmForm" id="updatePOtApplyAffirmForm" method="post" action="/ess/affirmApply/approvePOvertimeApplyInBatch" 
		onsubmit="return validateAffirmPOtApplyCallback(this, navTabAjaxDone);">    
		<table class="table" width="100%" height="80%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
				    <th width="30" style="text-align: center">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th  style="text-align: center"><!--NO.-->
						NO.
					</th>
					<th   style="text-align: center"><!--申请人-->
						申请人
					</th>
					<th   style="text-align: center"><!--是否批量-->
						是否批量
					</th>
					<th   style="text-align: center"><!--部门-->
						部门
					</th>
					<th   style="text-align: center"><!--加班日期-->
						加班日期
					</th>		
					
					<th   style="text-align: center"><!-- 加班类型 -->
						加班类型
					</th>
								
					<th   style="text-align: center"><!--加班时间段-->
						加班时间段
					</th>
					<th   style="text-align: center"><!--扣除时间-->
						扣除时间
					</th>	<c:if test="${defaultCpny ne 'LGEQH'}">
					<th   style="text-align: center"><!--申请时长-->
						申请时长
					</th>
					</c:if>
					<th   style="text-align: center"><!--加班事由-->
						加班事由
					</th>
					<th   style="text-align: center"><!--审批情况-->
						审批情况
					</th>
					<th   style="text-align: center"><!--审批-->
						审批
					</th>									
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otApplyList}" var="otPerson" varStatus="i">			
					<tr target="sid">
					    <td style="text-align: center">
					     <input type="hidden" id="AFFIRM_LEVEL" name="AFFIRM_LEVEL" value="${otPerson.AFFIRM_LEVEL_T }"/>
					    	<c:if test="${otPerson.CURRENT_AFFIRM_ID eq otPerson.AFFIRMOR_ID}">
					        	<input type="checkbox" id="c1" name="c1" value="${otPerson.ESS_AFFIRM_NO}" />
					        </c:if>
					        <input type="hidden" id="" name="" value=""/>
					    </td>
					    <td style="text-align: center">${otPerson.NO}</td>
						<td style="text-align: center">[${otPerson.EMPID}]${otPerson.LOCAL_NAME}</td>
						<td style="text-align: center">
						
							<c:if test="${otPerson.APPLY_TYPE eq 'PERSON' }">
								个人
							</c:if>
							<c:if test="${otPerson.APPLY_TYPE eq 'BATCH' }">
								批量
							</c:if>
						</td>
						<td style="text-align: center">${otPerson.DEPT_NAME}</td>
						<td style="text-align: center">${otPerson.APPLY_OT_DATE}</td>
						
						<td style="text-align: center">${otPerson.APPLY_TYPE_NAME}</td>
						
						<td style="text-align: center">
				    	    <dt style="padding: 1px;">${otPerson.OT_FROM_TIME}</dt>
				    	    <dt style="padding: 1px;">${otPerson.OT_TO_TIME}</dt>						
						</td>
						<td style="text-align: center">${otPerson.OT_DEDUCT_TIME}</td>
						<c:if test="${defaultCpny ne 'LGEQH'}">
						<td style="text-align: center">
						
						<c:if test="${otPerson.APPLY_TYPE eq 'PERSON' }">
								<fmt:formatNumber value="${otPerson.OT_LENGTH }" pattern="#,##0.00"/>
							</c:if>
							
						</td>
						</c:if>
						<td style="text-align: center">
							<a rel="otAffirmRemarkP" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_NO=${otPerson.APPLY_NO}" title="加班事由"
					          target="dialog" mask="true" width="300" height="300" id="otAffirmRemarkPHref" >${otPerson.INTRO}</a>
						</td>
						<td style="text-align: center">
							 <c:forEach items="${otPerson.affirmorList}" var="affirmList" varStatus="index">	
							<div style="display:block;line-height:15px;font-size:10px;">
								[${affirmList.EMPID }]${affirmList.LOCAL_NAME }&nbsp;
								<c:if test="${affirmList.AFFIRM_FLAG==0}" >
								    未审批
								</c:if>	
								<c:if test="${affirmList.AFFIRM_FLAG==1}" >
								    通过
								</c:if>	
								<c:if test="${affirmList.AFFIRM_FLAG==2}" >
								    否决
								</c:if>	
							</div>
						</c:forEach>
							 
							 		
						</td>
						<td style="text-align: center">
						    <c:if test="${otPerson.CURRENT_AFFIRM_ID eq otPerson.AFFIRMOR_ID}">
						    	<a class="add"  onclick="goToHrefUrl(this,'/ess/affirmApply/viewFullApplyAffirmorList?seach_APPLY_TYPE_NO=31&seach_APPLY_TYPE_WQ=31&search_page_flag_p=${otPerson.OT_TIME_TYPE}&seach_APPLY_NO=${otPerson.APPLY_NO}');"
						    	href="#"  title="审批加班"
									target="navTab"   rel="ess0238_affirm"><font color="red">审批</font></a>
							    <%-- 
							    <a rel="otAffirmRemarkP" href="/ess/affirmApply/viewFullApplyAffirmorList?seach_APPLY_NO=${otPerson.APPLY_NO}" title="审批"
					          		target="navTab" mask="true" width="1050" height="450" id="otAffirmRemarkPHref"><font color="red">审批</font></a>
					          	--%>
							</c:if>
							
							<c:if test="${otPerson.CURRENT_AFFIRM_ID ne otPerson.AFFIRMOR_ID}" >
							    <a rel="otAffirmRemarkP" href="/ess/affirmApply/viewFullApplyCheckInfoList?pageNum=1&seach_APPLY_TYPE_WQ=31&seach_APPLY_NO=${otPerson.APPLY_NO}" title="审批详情"
					          		target="navTab" mask="true" width="1250" height="450" id="otAffirmRemarkPHref"><font color="red">审批查看</font></a>
							</c:if>
						</td>	                    														
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
	<div id="otAffirmRemarkP" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/affirmApply/viewPOtAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	