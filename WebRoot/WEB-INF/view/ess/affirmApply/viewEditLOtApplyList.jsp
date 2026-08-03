<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateEditLOtApplyCallback(form,callback,flag) {	
	var $form = $("#updateLOtApplyForm");
	if (!$form.valid()) {
		return false;
	}
	$("#AFFIRM_FLAG").val(flag);
	
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
			if($form.find("[name='"+applyNo+"_APPLY_OT_DATE']").val()==''){
				alertMsg.error("加班日期为必选项，请选择加班日期！");
				$form.find("[name='"+applyNo+"_APPLY_OT_DATE']").focus();
				checked=false;
		   	}  
		   	
		  	if($form.find("[name='"+applyNo+"_OT_APPLY_HOUR']").val()=='' && $form.find("[name='"+personId+"_OT_APPLY_MINUTE']").val()==''){
				alertMsg.error("加班时长为必选项，不允许小时、分钟全为空！");
				$form.find("[name='"+applyNo+"_OT_APPLY_HOUR']").focus();
				checked=false;
		   	}
		   	
		   	if($form.find("select[name='"+applyNo+"_ADJUST_YN']").val()==''){
				alertMsg.error("是否转调休为必选项，请选择！");
				$form.find("select[name='"+applyNo+"_ADJUST_YN']").focus();
				checked=false;
			}
		}
	});

	if(checked){		
		if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){		
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("searchLOvertimeApplyBatchForm");
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

function pageFromSea(a){  
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", 
			"/ess/affirmApply/viewEditLOtApplyList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME
			+"&seach_TO_TIME="+seach_TO_TIME+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>
  
<script type="text/javascript">
<!--
//获取该日期的加班类型
function getLOtApplyTypeEdit(apply_no){
	var $form = $("#updateLOtApplyForm");
	var fromDate = $("#updateLOtApplyForm input[id='"+apply_no+"_APPLY_OT_DATE']").val();
	var adjustYn = $("#updateLOtApplyForm select[id='"+apply_no+"_ADJUST_YN']").val();
    var personid = $("#updateLOtApplyForm input[id='"+apply_no+"_PERSON_ID']").val();
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
//-->
</script>         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewEditLOtApplyList" method="post" 
	      rel="pagerForm" name="searchLOvertimeApplyBatchForm" id="searchLOvertimeApplyBatchForm">
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
					<a class="update" onclick="validateEditLOtApplyCallback('updateLOtApplyForm',DWZ.ajaxDone,'-1')" href="#" >
			        <span>批量暂存<!--批量保存--></span></a>
				</div>
			    <div class="buttonActive">
			        <a class="update" onclick="validateEditLOtApplyCallback('updateLOtApplyForm',DWZ.ajaxDone,'0')" href="#" >
			        <span>批量提交<!--批量提交--></span></a>
				</div>	
			</li>
		</ul>
	</div>
	<form name="updateLOtApplyForm" id="updateLOtApplyForm" method="post" action="/ess/affirmApply/updateLOtApplyInBatch" 
		onsubmit="return validateEditLOtApplyCallback(this, navTabAjaxDone);">   
		 <input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="L"/>
		 <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
		<table class="table" width="115%" height="80%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
				    <th width="10" style="text-align: center">
				    	<input type="checkbox" class="checkboxCtrl" group="c1"/>
				    </th><!--
				    <th width="15" style="text-align: center">NO.
						NO.
					</th>
					--><th width="40" style="text-align: center"><!--个人/批量-->
						是否批量
					</th>
					<th width="50" style="text-align: center"><!--部门-->
						部门
					</th>
					<th width="60" style="text-align: center"><!--申请人-->
						申请人
					</th>
					
					<th width="45" style="text-align: center"><!--加班日期-->
						加班日期
					</th>
					<th width="100" style="text-align: center"><!--申请时长-->
						申请时长
					</th>
					<th width="110" style="text-align: center"><!-- 加班类型 -->
						加班类型
					</th>	
					<th width="40" style="text-align: center"><!--是否调休-->
						是否调休
					</th>
					<th width="30" style="text-align: center"><!--加班事由-->
						加班事由
					</th>
					<th width="20" style="text-align: center"><!--修改-->
						修改
					</th>
					<th width="35" style="text-align: center"><!--详细查看-->
						详细查看
					</th>								
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otApplyList}" var="otPerson" varStatus="i">			
					<tr target="sid">
					    <td style="text-align: center">
					        <input type="checkbox" id="c1" name="c1" value="${otPerson.APPLY_NO}"/>
					        <input type="hidden" id="${otPerson.APPLY_NO}_PERSON_ID" name="${otPerson.APPLY_NO}_PERSON_ID" value="${otPerson.PERSON_ID}" />
					    </td><!--
					    <td style="text-align: center">${otPerson.APPLY_NO}</td>
					    --><td style="text-align: center">
							<c:if test="${otPerson.APPLY_TYPE eq 'PERSON' }">
								个人
							</c:if>
							<c:if test="${otPerson.APPLY_TYPE eq 'BATCH' }">
								批量
							</c:if>
						</td>
						<td style="text-align: center">${otPerson.DEPT_NAME}</td>
						<td style="text-align: center">[${otPerson.EMPID}]${otPerson.LOCAL_NAME}</td>
						
						<td style="text-align: center">
							<input type="text" id="${otPerson.APPLY_NO}_APPLY_OT_DATE" name="${otPerson.APPLY_NO}_APPLY_OT_DATE" class="date"  size="11"
								format="yyyy-MM-dd" readonly="true" value="${otPerson.APPLY_OT_DATE}" onpropertychange="getLOtApplyTypeEdit(${otPerson.APPLY_NO});"/>
							<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						</td>
						<td style="text-align: center">
							<select name="${otPerson.APPLY_NO}_OT_APPLY_HOUR" id="${otPerson.APPLY_NO}_OT_APPLY_HOUR">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="1" end="8" step="1">
									<option value="${h}" <c:if test="${h eq otPerson.OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
							</select>
							<select name="${otPerson.APPLY_NO}_OT_APPLY_MINUTE" id="${otPerson.APPLY_NO}_OT_APPLY_MINUTE">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="m" begin="0" end="59" step="30">
									<option value="${m}" <c:if test="${m eq otPerson.OT_APPLY_MINUTE}">selected</c:if>>
										${m}分
									</option>
								</c:forEach>
							</select>
						</td>
						<td style="text-align: center">
							<ait:SelectSyCodeByCpnyID name="${otPerson.APPLY_NO}_OT_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
								selected="${otPerson.OT_TYPE_CODE}" limit="all"/>
							&nbsp;
						    <select id="${otPerson.APPLY_NO}_OT_PLACE_TYPE" name="${otPerson.APPLY_NO}_OT_PLACE_TYPE">
						    	<option value="INSIDE" <c:if test="${otPerson.OT_PLACE_TYPE eq 'INSIDE'}">selected</c:if>>社内</option>
						    	<option value="OUTSIDE" <c:if test="${otPerson.OT_PLACE_TYPE eq 'OUTSIDE'}">selected</c:if>>社外</option>
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
								value="${otPerson.APPLY_OT_REMARK }" size="28"/>
						</td>
						<td style="text-align: center">
							<a class="update" href="/ess/affirmApply/viewEditLOtApplyInfo?APPLY_NO=${otPerson.APPLY_NO}" title="修改"
								target="navTab"><font color="red">修改</font></a>
						</td>
						<td style="text-align: center">
						    <a rel="otLEditRemark" href="/ess/affirmApply/viewFullApplyCheckInfo?seach_APPLY_NO=${otPerson.APPLY_NO}" title="详细查看"
				          		target="dialog" mask="true" width="1250" height="450" id="otLEditRemarkHref"><font color="red">详细查看</font></a>
						</td>         														
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
	<div id="otLEditRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ess/affirmApply/viewEditLOtApplyList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	