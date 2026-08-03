<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
function validateAffirmOtApplyCallback(form,callback,flag) {
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
    $form.attr("action","/ess/affirmApply/appAnnualadjustmentApplyInBatch?AFFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("viewAnnualadjustmentAffirmList");
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
			"/ess/annualadjustment/viewAnnualadjustmentInfo?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"  action="/ess/affirmApply/viewAnnualadjustmentAffirmList" 
	  method="post"   rel="pagerForm" name="viewAnnualadjustmentInfo" id="viewAnnualadjustmentInfo">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>
				</td>
				<td> 
					 <input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>
				</td>	
				<td>
				  <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
               <td width="10%"><!-- 决裁状态 -->
					<spring:message code="ess.viewApply.title.affirmStatus"/>:
				</td>				
				<td width="15%">  
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="-5">全部</option>
						 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未审批</option>
						 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
						 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
						 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						 <option value="10" <c:if test="${AFFIRM_FLAG eq '10'}">selected</c:if>>本人审批</option>
						 <option value="11" <c:if test="${AFFIRM_FLAG eq '11'}">selected</c:if>>未到审批</option>
					 </select>       
				</td>	
				<td><!-- 审批状态 -->
					批量/个人
				</td>				
				<td>  
				     <select id="seach_APPLY_TYPE_FLAG" name="seach_APPLY_TYPE_FLAG">
						 <option value="">全部</option>
						 <option value="N" <c:if test="${APPLY_TYPE_FLAG eq 'N'}">selected</c:if>>个人</option>
						 <option value="Y" <c:if test="${APPLY_TYPE_FLAG eq 'Y'}">selected</c:if>>批量</option>
					 </select>       
				</td>	
			</tr>
			<tr>
				<td><!-- 开始日期 -->
				     <spring:message code="public.title.startDate"/>:
				</td>
				<td>
					<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
					<a class="inputDateButton"><!-- 选择 -->
				      	<spring:message code="public.title.choose"/>
					</a>
				</td>
				<td><!-- 结束日期 -->
				     <spring:message code="public.title.endDate"/>:
				</td>				
				<td>
					<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
		           	<a class="inputDateButton"><!-- 选择 -->
                    	<spring:message code="public.title.choose"/>
					</a>
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
					         <a class="update" onclick="validateAffirmOtApplyCallback('updateAnnualadjustmentAffirmForm',DWZ.ajaxDone,'1')" href="#" >
					         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
						</div>
			</li>
			<li>
					    <div class="buttonActive">
					         <a class="update" onclick="validateAffirmOtApplyCallback('updateAnnualadjustmentAffirmForm',DWZ.ajaxDone,'2')" href="#" >
					         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    	</div>
		    </li>
		</ul>	
		</div>
<form name="updateAnnualadjustmentAffirmForm" id="updateAnnualadjustmentAffirmForm" method="post" action="/ess/affirmApply/appAnnualadjustmentApplyInBatch" 
		onsubmit="return validateAffirmOtApplyCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="231" nowrapTD="false">
		<thead>
			<tr>
			    <th width="60"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="160"><spring:message code="public.title.deptName"/><!--部门--></th>			
				<th width="100">申请类别</th>	
				<th width="100">调整天数</th>	
				<th width="160">调整原因</th>					
				<th width="120">审批情况</th>
				<th width="120">决裁</th>				
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${annualadjustmentInfoList}" var="annulist" varStatus="i">			
				 <tr target="sid" rel="${annulist.PERSON_ID}">
				 	<td style="text-align: center">
					 	<c:if test="${annulist.CURRENT_AFFIRM_ID eq annulist.AFFIRMOR_ID}">
					        <input type="checkbox" id="c1" name="c1" value="${annulist.ESS_AFFIRM_NO}" />
					    </c:if>
				    </td>
				    <td style="text-align: center">${annulist.EMPID}</td>
					<td style="text-align: center">${annulist.LOCAL_NAME}</td>
					<td style="text-align: center">
						<c:if test="${annulist.BATCH_YN eq 'Y'}" >
						    ${annulist.BATCH_APPLY_DEPT_NAME}
						</c:if>
						<c:if test="${annulist.BATCH_YN eq 'N'}" >
						    ${annulist.DEPT_NAME}
						</c:if>
					</td>
					<td style="text-align: center">
						<c:if test="${annulist.BATCH_YN eq 'Y'}" >
						    批量
						</c:if>
						<c:if test="${annulist.BATCH_YN eq 'N'}" >
						    个人
						</c:if>
					</td>
					<td style="text-align: center">${annulist.APPLY_TANSHU}</td>
					<td style="text-align: center">${annulist.ANNUAL_LEAVE_REASON}</td>
					<td style="text-align: center">
						<c:if test="${annulist.AFFIRM_FLAG==0}" >
						    <font color="blue">提交</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==1}" >
						    <font color="green">通过</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==2}" >
						    <font color="red">否决</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==3}" >
						    <font color="back">取消</font>
						</c:if>	
						<c:if test="${annulist.AFFIRM_FLAG==4}" >
						    <font color="green">审批中</font>
						</c:if>		
					</td>
					<td style="text-align: center">
					  <c:if test="${annulist.CURRENT_AFFIRM_ID eq annulist.AFFIRMOR_ID}">
						<a class="add" href="/ess/affirmApply/viewFullAnnuApplyAffirmorList?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${annulist.APPLY_NO}"  title="审批"
								rel="ess0304_affirm"  target="navTab"><font color="red">审批</font></a>
					 </c:if>
					 <c:if test="${annulist.CURRENT_AFFIRM_ID ne annulist.AFFIRMOR_ID}" >
						<a rel="otAffirmRemark" href="/ess/annualadjustment/viewFullAnnuAffirmInfo?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${annulist.APPLY_NO}" title="审批查看"
						        target="dialog" mask="true" width="1200" height="450" id="otAffirmRemarkHref"><font color="red">审批查看</font></a>
				     </c:if>
				     </tr>
			</c:forEach>			
		</tbody>
	</table>	
	</form>
    <c:set value="/ess/affirmApply/viewAnnualadjustmentAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>



