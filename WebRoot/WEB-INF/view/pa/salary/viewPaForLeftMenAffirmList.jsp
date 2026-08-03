<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmPaForLeftApplyCallback(form,callback,flag) {
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

    $form.attr("action","/pa/salary/approvePaForLeftApplyInBatch?AFFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("viewPaForLeftMenAffirmList");
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
	var seach_EMP_KEY     = $("#seach_EMP_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_EMP_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_DATE   = $("#seach_FROM_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_DATE",navTab.getCurrentPanel()).val();
	var seach_TO_DATE     = $("#seach_TO_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_DATE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG = $("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/pa/salary/viewPaForLeftMenAffirmList?seach_EMP_KEY="+seach_EMP_KEY+"&seach_FROM_DATE="+seach_FROM_DATE+"&seach_TO_DATE="+seach_TO_DATE
			+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaForLeftMenAffirmList" method="post" 
	      rel="pagerForm" name="viewPaForLeftMenAffirmList" id="viewPaForLeftMenAffirmList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="6%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>:
					</td> 
					<td width="10%">						
						<input id="seach_EMP_KEY" name="seach_EMP_KEY" type="text" value="${EMP_KEY}"/>
					</td>
					<td width="6%"><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>:
					</td> 
					<td>	                    
				        <input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="date required" 
				        	format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>			   
				    </td>
	                <td width="6%"><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>:
					</td> 
					<td>	                    
					    <input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="date required" 
					    	format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"></a>
					</td>
					<td width="6%"><!-- 决裁状态 -->
						审批状态:
					</td> 						
					<td width="10%">
					    <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="">全部</option>	 
							<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未审批</option>
							<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							<option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						</select>       
					</td>
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
					<a class="update" onclick="validateAffirmPaForLeftApplyCallback('updatePaForLeftAffirmForm',DWZ.ajaxDone,'1')" href="#" >
			         	<span><spring:message code="ess.title.passInBatch"/> </span></a>
				</div>
			    <div class="buttonActive">
					<a class="update" onclick="validateAffirmPaForLeftApplyCallback('updatePaForLeftAffirmForm',DWZ.ajaxDone,'2')" href="#" >
			        	<span><spring:message code="ess.title.rejectInBatch"/> </span></a>
				</div>	
			</li>
		</ul>
	</div>
	 <form name="updatePaForLeftAffirmForm" id="updatePaForLeftAffirmForm" method="post" action="/ess/affirmApply/approveLOvertimeApplyInBatch" 
		onsubmit="return validateAffirmPaForLeftApplyCallback(this, navTabAjaxDone);">    
		<table class="table" width="100%" height="80%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
				    <th width="30" style="text-align: center">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th width="45" style="text-align: center"><!--NO.-->
						NO.
					</th>
					<th width="120" style="text-align: center"><!--申请人-->
						申请人
					</th>
					<th width="140" style="text-align: center"><!--部门-->
						部门
					</th>
					
					<th width="140" style="text-align:center"><!-- 申请内容 -->
						申请内容
					</th>
					<th width="100" style="text-align: center"><!-- 审批情况 -->
						审批情况
					</th>
					 <th width="80" style="text-align: center"><!--审批-->
						审批
					</th>
					<th width="80" style="text-align:center"><!--申请时间-->
						申请时间
					</th>								
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paForLeftAffirmList}" var="affirm" varStatus="i">			
					<tr target="sid" rel="${affirm.BATCH_NO }">
					    <td style="text-align: center">
					    	<c:if test="${affirm.CURRENT_AFFIRM_ID eq affirm.AFFIRMOR_ID}">
					        	<input type="checkbox" id="c1" name="c1" value="${affirm.ESS_AFFIRM_NO}"/>
					        </c:if>
					    </td>
					    <td style="text-align: center">${i.index+1 }</td>
						<td style="text-align: center">[${affirm.EMPID}]${affirm.LOCAL_NAME}</td>
						<td style="text-align: left">${affirm.DEPT_NAME}</td>
						
						<td style="text-align: left">
							<a rel="paForLeftRemark" href="/pa/salary/viewPaForLeftApplyContentInfo?BATCH_NO=${affirm.BATCH_NO}" title="详细内容"
					          target="dialog" mask="true" width="300" height="300" id="paForLeftRemarkLHref" >
					          <font color="blue">${affirm.APPLY_INFO}..</font>
							</a>
						</td>
						<td style="text-align: center">
							<c:if test="${affirm.AFFIRM_PROGRESS_FLAG == 0}">
								未审批
							</c:if>	
							<c:if test="${affirm.AFFIRM_PROGRESS_FLAG == 1}">
								通过
							</c:if>	
							<c:if test="${affirm.AFFIRM_PROGRESS_FLAG == 2}">
								否决
							</c:if>
							<c:if test="${affirm.AFFIRM_PROGRESS_FLAG == 4}">
								审批中
							</c:if>		
						</td>
						<td style="text-align: center">
						    <c:if test="${affirm.CURRENT_AFFIRM_ID eq affirm.AFFIRMOR_ID}">
						    	<a href="/pa/salary/viewFullPaForLeftApplyAffirmorList?APPLY_TYPE_NO=${affirm.APPLY_TYPE_NO}&BATCH_NO=${affirm.BATCH_NO}" 
						    		title="审批" class="add" target="navTab" rel="pa0708_affirm">
						    		<font color="red">审批</font>
						    	</a>
							</c:if>
							<c:if test="${affirm.CURRENT_AFFIRM_ID ne affirm.AFFIRMOR_ID}" >
							    <a href="/pa/salary/viewFullPaForLeftApplyCheckInfo?APPLY_TYPE_NO=${affirm.APPLY_TYPE_NO}&BATCH_NO=${affirm.BATCH_NO}" title="审批详情" 
							    	rel="paForLeftAffirmRemarkL" target="dialog" mask="true" width="1250" height="500" id="paForLeftAffirmRemarkLHref">
					          		<font color="red">审批查看</font>
					          	</a>
							</c:if>
						</td>	     
						<td style="text-align:center">
							${affirm.CREATE_DATE }
						</td>               														
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
	<c:set value="/pa/salary/viewPaForLeftMenAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	