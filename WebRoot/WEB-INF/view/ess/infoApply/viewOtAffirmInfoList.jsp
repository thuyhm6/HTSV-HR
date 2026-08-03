<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
function pageFromSea(a){
	var seach_PERSON_ID=$("#seach_PERSON_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_PERSON_ID",navTab.getCurrentPanel()).val();
	
	var seach_AR_MONTH=$("#seach_AR_MONTH",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AR_MONTH",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApply/viewOtAffirmInfoList?seach_PERSON_ID="+seach_PERSON_ID
			+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}


function delOtApplyCallback(form,callback) {
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
    $form.attr("action","/ess/infoApply/delOvertimeApplyInBatch");
    if (confirm ("确定要批量删除吗?")){	  
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("viewOtAffirmInfoList");
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
    }
	return false;
}

function delOvertimeApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要删除吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/delOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewOtAffirmInfoList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
function cancelOvertimeApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApply/cancelOvertimeApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewOtAffirmInfoList);
			}else{
				alert("取消失败！");
			}
		  }
		});
	}
}


<%--
function CheckFormEatMealCount(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doEatMealCountExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceSettings/viewArEatCountInfoListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expEatMealCount(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewArEatCountInfoList");
  	if(CheckFormEatMealCount($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doEatMealCountExport($form);}});
    } 
}
--%>
//-->
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOtAffirmInfoList" rel="pagerForm" method="post"
		id="viewOtAffirmInfoList" name="viewOtAffirmInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<%-- 
				<tr>
					<td style="text-align:center" width="10%"><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>:
					</td>
					<td width="20%">
						 <ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
					</td>		
					<td style="text-align:center" width="10%"><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>：
					</td>						
					<td width="20%">
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" size="12"/>
					</td>
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
					<td>&nbsp;</td>
				</tr>
				--%>
				<tr>
					<td style="text-align:left" width="10%"><!-- 部门 -->
						部门:
					</td>
					<td style="text-align:left" width="20%">
						${personInfo.DEPARTMENT }
					</td>
	                <td style="text-align:left" width="10%"><!-- 姓名 -->
						姓名:
					</td>
					<td style="text-align:left" width="20%">
						${personInfo.LOCAL_NAME }
					</td>
					<td style="text-align:left" width="10%"><!-- 社号 -->
						社号:
					</td>
					<td style="text-align:left" width="20%">
						${personInfo.EMPID }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${personInfo.PERSON_ID }"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="text-align:left" width="10%"><!-- 考勤月 -->
						考勤月:
					</td>
					<td width="20%">
						<select id="seach_AR_MONTH" name="seach_AR_MONTH">
							 <option value="">-全部-</option>
							 <c:forEach items="${arMonthList}" var="arMonth" varStatus="i">	
								 <option value="${arMonth.AR_MONTH }" <c:if test="${AR_MONTH eq arMonth.AR_MONTH}">selected</c:if>>${arMonth.AR_MONTH_STR }</option>
							 </c:forEach>
						 </select>
					</td>
					<td style="text-align:left" width="10%"><!-- 加班类型 -->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>:
					</td>					 
					<td width="20%">
					     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
					     	selected="${APPLY_TYPE_CODE}" limit="all"/>       
					</td>				               			
					<td style="text-align:left" width="10%"><!-- 审批状态 -->
						 审批状态:
					</td>					
					<td width="20%">
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>取消</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
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
		<ul class="toolBar">
			<li>
				<a class="add" href="/ess/infoApply/viewOtApplyInfo?APPLY_TYPE_NO=31" 
					target="navTab"><span>申请加班</span></a>
			</li>
			 
			<li>
				<a class="delete" onclick="delOtApplyCallback('delOvertimeApplyAffirmForm',DWZ.ajaxDone)" href="#" 
					target="ajaxTodo" title="确定要批量删除吗?"><span>批量删除</span></a>					
			</li>
			<!-- 
			<li>
				<a class="edit" href="/ar/attendanceSettings/updateArEatCountView?seach_AR_EAT_COUNT_NO={meal_no}" 
					target="dialog"><span>修改</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="icon" onclick="expEatMealCount()" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
			-->
		</ul>
	</div> 
	<form name="delOvertimeApplyAffirmForm" id="delOvertimeApplyAffirmForm" method="post" action="/ess/infoApply/delOvertimeApplyInBatch" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
					<th width="40">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
					<th width="60" style="text-align: center"><!--申请日期-->
						申请日期
					</th>
					<th width="60" style="text-align: center"><!--申请时长-->
						申请时长
					</th>
					<th width="60" style="text-align: center"><!--加班类型-->
						加班类型
					</th>
					<th width="160" style="text-align: center"><!--加班事由-->
						加班事由
					</th>
					<th width="60" style="text-align: center"><!--详细查看-->
						详细查看
					</th>
					<th width="60" style="text-align: center"><!--审批状态-->
						审批状态
					</th>
					<th width="60" style="text-align: center"><!--是否取消-->
						是否取消
					</th>
					<th width="60" style="text-align: center"><!--是否删除-->
						删除
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">
					    	<c:if test="${otApply.AFFIRM_FLAG eq '0' || otApply.AFFIRM_FLAG eq '-1'}">
					        	<input type="checkbox" id="c1" name="c1" value="${otApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>	
						<td style="text-align: center">${otApply.OT_APPLY_HOUR}时${otApply.OT_APPLY_MINUTE}分</td>
						<td style="text-align: center">${otApply.OT_TYPE_NAME}</td>
						
						<td style="text-align: center">
							<a rel="otApplyRemark" href="/ess/infoApply/viewApplyContentInfo?seach_APPLY_NO=${otApply.APPLY_NO}" title="加班事由"
					          target="dialog" mask="true" width="300" height="300" id="otApplyRemarkHref" >${otApply.APPLY_OT_REMARK}...</a>
						</td>
						<td style="text-align: center">
							<a rel="otApplyAffirm" href="/ess/infoApply/viewFullApplyAffirmInfo?seach_APPLY_NO=${otApply.APPLY_NO}" title="审批详情"
					          target="dialog" mask="true" width="950" height="450" id="otApplyRemarkHref" >查看</a>
						</td>
						
						<td style="text-align: center">
							<c:if test="${otApply.AFFIRM_FLAG eq '-1'}">
								<font color="blue">未提交</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '0'}">
								<font color="back">未审批</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '1'}">
								<font color="green">已通过</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '2'}">
								<font color="red">已否决</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '3'}">
								<font color="grey">已取消</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>	
						<td style="text-align: center">
							<c:if test="${otApply.AFFIRM_FLAG eq '1' || otApply.AFFIRM_FLAG eq '0'}">
								<a href="#" title="取消" onclick="cancelOvertimeApply('${otApply.APPLY_NO}')" style="cursor: hand">
									<font color="red">取消</font>
								</a>
							</c:if>
						</td>
						<td style="text-align: center"><!-- 申请信息为未提交、或者未审批时才允许删除 -->
							<c:if test="${otApply.AFFIRM_FLAG eq '0' || otApply.AFFIRM_FLAG eq '-1'}">
								<img src="/resources/images/button/Delete_little.gif" onclick="delOvertimeApply('${otApply.APPLY_NO}')"
						 			style="cursor: hand" />
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
    <div id="otApplyRemark" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <c:set value="/ess/infoApply/viewOtAffirmInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>