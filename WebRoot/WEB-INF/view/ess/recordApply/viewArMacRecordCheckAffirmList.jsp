<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function affirmArMacRecordApply(form,callback,flag) {
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

    $form.attr("action","/ess/recordApply/approveArMacRecordApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_ESS=ess0209");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				navTabSearch("viewArMacRecordAffirmList");
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
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_STATUS=$("#seach_AFFIRM_STATUS",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_STATUS",navTab.getCurrentPanel()).val();
	var seach_DOOR_TYPE=$("#seach_DOOR_TYPE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DOOR_TYPE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/recordApply/viewArMacRecordAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_AFFIRM_STATUS="+seach_AFFIRM_STATUS+"&seach_DOOR_TYPE="+seach_DOOR_TYPE);
}

//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/recordApply/viewArMacRecordCheckAffirmList" method="post" 
	      rel="pagerForm" name="viewArMacRecordAffirmList" id="viewArMacRecordAffirmList">
	<div class="searchBar">
		<table class="searchContent">
		<tr>
		     <td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->
				</td>
				<td>
					 <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>		
				
				 <td>进/出门</td>				
				<td>
					<select id="seach_DOOR_TYPE" name="seach_DOOR_TYPE">
						<option value=""><!--请选择:-->
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="IN" <c:if test="${DOOR_TYPE eq 'IN' }">selected</c:if>>进门(IN)</option>
						<option value="OUT" <c:if test="${DOOR_TYPE eq 'OUT' }">selected</c:if>>出门(OUT)</option>
					</select>       
				</td>
                <td>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                            <spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
                    </div>	                
                </td>
				<td><!-- 决裁状态 -->
					<spring:message code="ess.viewApply.title.affirmStatus"/>
				</td>				
				<td>  
					<!--<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="3528" 
				     cnpyID="${defaultCpny}" selected="${AFFIRM_FLAG}" limit="all"/>-->
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
			</tr>
			<tr>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->
                </td>			
			    <td>
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->
                </td>			     
				<td>
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent" > 
<form name="updateArMacRecordAffirm" id="updateArMacRecordAffirm" method="post" action="/ess/recordApply/approveOvertimeApplyInBatch" 
	  onsubmit="return affirmArMacRecordApply(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="154" nowrapTD="false">
		<thead>
			<tr>
				<th style="text-align: center" >NO.</th>
				<th style="text-align: center" >申请人</th>
				<th style="text-align: center" >是否批量</th>
				<th style="text-align: center" >部门</th>
				<th style="text-align: center" >漏刷卡日期</th>
				<th style="text-align: center" >打卡时间</th>		
				<th style="text-align: center" >进/出门类型</th>				
				<th style="text-align: center" >申请事由</th>	
				<th style="text-align: center" >决裁	情况</th>						
				<th style="text-align: center" > Type</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arMacRecordList}" var="arMacRecord" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">${arMacRecord.NO }</td>
					<td style="text-align: center">[${arMacRecord.EMPID}]${arMacRecord.LOCAL_NAME}</td>
					<td style="text-align: center">
						<c:if test="${arMacRecord.BATCH_YN eq 'N' }">
							个人
						</c:if>
						<c:if test="${arMacRecord.BATCH_YN eq 'Y' }">
							批量
						</c:if>
					</td>
					<td style="text-align: center">${arMacRecord.DEPT_NAME}</td>
					<td style="text-align: center">${arMacRecord.R_TIME_LD}</td>
					<td style="text-align: center">${arMacRecord.R_TIME_LT}</td>
					
					<td style="text-align: center">${arMacRecord.DOOR_TYPE}</td>
					<td style="text-align: center"><a rel="otApplyRemark" href="/ess/recordApply/viewMACContentInfo?seach_APPLY_NO=${arMacRecord.APPLY_NO }" title="申请事由"
					          target="dialog" mask="true" width="300" height="300" id="otApplyRemarkHref" >${arMacRecord.INTRO}</a></td>
					<td style="text-align: center"> 
					
					<c:if test="${arMacRecord.AFFIRM_FLAG==-1}" >
						    <font color="grey">未提交</font>
						</c:if>
						<c:if test="${arMacRecord.AFFIRM_FLAG==0}" >
						    <font color="blue">未决裁</font>
						</c:if>	
						<c:if test="${arMacRecord.AFFIRM_FLAG==1}" >
						    <font color="green">已通过</font>
						</c:if>	
						<c:if test="${arMacRecord.AFFIRM_FLAG==2}" >
						    <font color="red">已否决</font>
						</c:if>	
						<c:if test="${arMacRecord.AFFIRM_FLAG==3}" >
						    <font color="back">已取消</font>
						</c:if>	
						<c:if test="${arMacRecord.AFFIRM_FLAG==4}" >
						    <font color="green">决裁中</font>
						</c:if>		
					</td>
					 
					<td style="text-align: center">
					<c:if test="${arMacRecord.AFFIRM_FLAG_PERSON eq '1'}">
							<a class="add" href="/ess/recordApply/checkApplyCheckInfo?pageNum=1&seach_APPLY_TYPE_NO=218294&seach_APPLY_NO=${arMacRecord.APPLY_NO}&seach_ESS_AFFIRM_NO=${leaveCheck.ESS_AFFIRM_NO}&seach_IS_CHECK=1"  
					    		title="CHECK" target="navTab" el="ess0242_affirm_info"><font color="red">Check</font></a>
						</c:if>
						<c:if test="${arMacRecord.AFFIRM_FLAG_PERSON ne '1'}">
						    <a rel="leaveAffirmRemark" href="/ess/recordApply/viewFullApplyAffirmInfo?pageNum=1&seach_APPLY_TYPE_NO=218294&seach_APPLY_NO=${arMacRecord.APPLY_NO}" title="审批详情"
				          		target="navTab" rel="ess0242_affirm_info"><font color="red">已check</font></a>
						</c:if>
					</td>              
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/recordApply/viewArMacRecordCheckAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	