<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmCwaApplyCallbackEss0802(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("cwa_c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}

    $form.attr("action","/ess/affirmApply/approveCwaApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=CWA_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchLeaveApplyAffirmForm");
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
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewCwaAbnormalAffirmList" method="post" 
	      name="searchLeaveApplyAffirmForm" id="searchLeaveApplyAffirmForm" rel="pagerForm">
	<input type="hidden" id="menuNo" name="menuNo" value="2449"/>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->:
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
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->
                </td>			
			    <td><!--seach_FROM_TIME-->
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->
                </td>			     
				<td><!--seach_TO_TIME-->
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 	 
			</tr>
		</table>
		<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!-- 查询 -->
									<spring:message code="button.search" />
								</button>
							</div>
						</div>
					</li>

				</ul>
			</div>
	</div>
	</form>
		
</div>
	<div class="formBar">
				<ul>
				<li>
					<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmCwaApplyCallbackEss0802('updateCwaApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmCwaApplyCallbackEss0802('updateCwaApplyAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/></span></a>
                    </div>									
				</div>	
				</li>
		</ul>
	</div>
<div class="pageContent" > 
<form name="updateCwaApplyAffirmForm" id="updateCwaApplyAffirmForm" method="post" action="/ess/affirmApply/approveCwaApplyInBatch" 
	  onsubmit="return validateAffirmCwaApplyCallbackEss0802(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="235" nowrapTD="false">
		<thead>
			<tr>
			    <th width="30"><input type="checkbox" class="checkboxCtrl" group="cwa_c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="180"><spring:message code="public.title.deptName"/><!--部门--></th>
                <th width="80">申请人</th>
				<th width="60">是否批量</th>
				<th width="120">考勤日期<!-- 是否批量 --></th>
				<th width="160">异常开始时间</th>
				<th width="160">异常结束时间</th>				
				<th width="80">申请类型</th>				
				<th width="160">申请事由</th>	
				<th width="80"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="80" style="text-align: center"><!--决裁-->
					决裁
				</th>							
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${leaveApplyAffirmList}" var="leaveApplyAffirm" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">
				    	<c:if test="${leaveApplyAffirm.CURRENT_AFFIRM_ID eq leaveApplyAffirm.AFFIRMOR_ID}">
				        	<input type="checkbox" name="cwa_c1" value="${leaveApplyAffirm.ESS_AFFIRM_NO}" />
				        </c:if>
				    </td>
					<td style="text-align: center">${leaveApplyAffirm.EMPID}</td>
					<td style="text-align: center">${leaveApplyAffirm.LOCAL_NAME}</td>
					<td style="text-align: center">${leaveApplyAffirm.DEPT_NAME}</td>
					<td style="text-align: center">${leaveApplyAffirm.CREATE_NAME}</td>
					<td style="text-align: center">
						<c:if test="${leaveApplyAffirm.BATCH_YN eq 'N' }">
							个人
						</c:if>
						<c:if test="${leaveApplyAffirm.BATCH_YN eq 'Y' }">
							批量
						</c:if>
					</td>
					<td style="text-align: center">
					${leaveApplyAffirm.AR_DATE_STR }
					</td>
					<td style="text-align: center"><fmt:formatDate value="${leaveApplyAffirm.FROM_TIME}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
                    <td style="text-align: center"><fmt:formatDate value="${leaveApplyAffirm.TO_TIME}" pattern="yyyy/MM/dd/ HH:mm:ss" /></td>
                    <td style="text-align: center">${leaveApplyAffirm.YICHANGTYPENAME}</td>
					<td style="text-align: center">${leaveApplyAffirm.APPLY_REASON}</td>
					<td style="text-align: center">
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==-1}" >
						    <font color="grey">未提交</font>
						</c:if>
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==0}" >
						    <font color="blue">未决裁</font>
						</c:if>	
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>	
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==3}" >
						    <font color="back">已取消</font>
						</c:if>	
						<c:if test="${leaveApplyAffirm.AFFIRM_PROGRESS_FLAG==4}" >
						    <font color="green">决裁中</font>
						</c:if>		
					</td>
					<td style="text-align: center">
					    <c:if test="${leaveApplyAffirm.CURRENT_AFFIRM_ID eq leaveApplyAffirm.AFFIRMOR_ID}">
					    	<a class="add" href="/ess/infoApply/viewCwaApplyAffirmorList?seach_APPLY_TYPE_NO=218197&seach_ID=${leaveApplyAffirm.ID }&seach_YICHANGTYPE=${leaveApplyAffirm.YICHANGTYPE }&seach_GERENORPILIANG=${leaveApplyAffirm.GERENORPILIANG }&seach_APPLY_NO=${leaveApplyAffirm.APPLY_NO}" title="审批"
							 rel="ess0305_affirm"	target="navTab"><font color="red">审批</font></a>
						</c:if>
						
						<c:if test="${leaveApplyAffirm.CURRENT_AFFIRM_ID ne leaveApplyAffirm.AFFIRMOR_ID}" >
						    <a rel="leaveAffirmRemark" href="/ess/infoApply/viewFullCwaApplyAffirmInfo?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${leaveApplyAffirm.APPLY_NO}" title="决裁详情"
				          		target="dialog" mask="true" width="1250" height="450" id="leaveAffirmRemarkHref"><font color="red">审批查看</font></a>
						</c:if>
					</td>                 														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<div id="CwaAbnormalAffirmList" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/infoApply/viewCwaAbnormalAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	