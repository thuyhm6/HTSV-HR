<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 200,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});

function validateAffirmLeaveApplyCallbackEss0802(form,callback,flag) {
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
	
/* 	if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    	  var CONFIRM_FLAG  = document.getElementById("CONFIRM_FLAG"+j).value;
		    	  if(CONFIRM_FLAG=="Y"){
					alertMsg.error('考勤审批已锁定！');
					return false;
				  }
				}
			}		  
	} */
	  

    $form.attr("action","/ess/affirmLeaveApply/approveLeaveApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				//navTabSearch("searchLeaveApplyAffirmForm_leave");
				navTabSearch(document.viewAttendanceAffirmList);
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
    
 //文本fill
 function textMuli(name,value){
  var ids = document.getElementsByName("c1");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    	 document.getElementById(name+j).value=value;
				}
			}		  
		}
}

function fillItem(){
    var checked=false;
    var j = 0;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				j++;
			}
		}
		if(!checked){
			alertMsg.error('请选择反应记录'); 
			return false;
		}
	var fillAffirmFlag = $("#viewAttendanceAffirm select[id='FILLAFFIRMFLAG']").val();
	 if(fillAffirmFlag != "" && fillAffirmFlag != null){
		 for(var i=0;i<j;i++){
		 	var applyNo = $("#applyNo"+i).val();
		 	$("#AFFIRM_FLAG"+applyNo).val(fillAffirmFlag);
		 }
	  }else{
	    return;
	 }
}
</script>
         
<div id="viewAttendanceAffirmList"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmLeaveApply/viewAttendanceAffirmList?firstFlag=N" rel="pagerForm" method="post"
		id="viewAttendanceAffirmList" name="viewAttendanceAffirmList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewAttendanceAffirmList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewAttendanceAffirmList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td>
						班组
					</td>
					<td>
					  <ait:SelectSyCodeByCpnyID id="seach_GROUOP_ID" name="seach_GROUOP_ID" parentNo="400223"  cnpyID="${LoginUser.cpnyId}" limit="all" selected="${GROUOP_ID}"/>
					</td>
			   </tr>
			   <tr>
	                <td><!-- 开始日期 -->
	                    <spring:message code="public.title.startDate"/>
	                </td>			
				    <td>
				        <input type="text" name="seach_FROM_TIME" id="seach_FROM_TIME"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_TIME}"/>
				    </td>
	                <td><!-- 结束日期 -->
	                    <spring:message code="public.title.endDate"/>
	                </td>			     
					<td>
					     <input type="text" name="seach_TO_TIME" id="seach_TO_TIME"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_TIME}"/>
					</td> 
					<td><!-- 审批状态： --> 
						  审批状态
					</td>
					<td > 
					     <ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  exclude="14014306" cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
					<td>考勤类型</td>
					<td>
						 <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					     	<option value="" >请选择</option>
							<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>>
									       ${item.ITEM_NAME}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/> 
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>
 <div id="viewAttendanceAffirm" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent" >
				<tr >
					<td  align="right">
					审批状态&nbsp;&nbsp;&nbsp;<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" exclude="14014306,14014310" cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem();"><span>全部反应</span></a>
	             </li>
				</ul>
			</div>
	</div>
</div> 
<div class="pageContent" > 
<div class="formBar">
           <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(leaveApplyAffirmList)}</div>
			<ul>
				<li>
					<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLeaveApplyCallbackEss0802('updateLeaveApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span>保存</span></a>
                            </div>
					</div>	
				</li>
			</ul>
		</div>
<form name="updateLeaveApplyAffirmForm" id="updateLeaveApplyAffirmForm" method="post" action="/ess/affirmApply/approveLeaveApplyInBatch" 
	  onsubmit="return validateAffirmLeaveApplyCallbackEss0802(this, navTabAjaxDone);">    
	<table class="orderList" width="100%">    
		<thead>
				<tr>
				    <th width="30px" ><!--NO-->
						NO
					</th>
					<th width="30px" >
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
					<th width="80px" style="text-align: center"><!--申请人-->
						姓名
					</th>
				    <th  width="50px" style="text-align: center"><!--社号-->
						工号
					</th>
					<th  width="80px" style="text-align: center"><!--部门-->
						部门
					</th>
					<th width="80px"  style="text-align: center"><!--考勤状态-->
						考勤类型
					</th>
					<th width="80px" style="text-align: center"><!--班次-->
						开始日期
					</th>
					<th width="50px"  style="text-align: center"><!--开始时间-->
						开始时间
					</th>
					<th width="80px"  style="text-align: center"><!--结束日期-->
						结束日期
					</th>
					<th width="50px"  style="text-align: center"><!--结束时间-->
						结束时间
					</th>
					<th width="50px"  style="text-align: center"><!--结束时间-->
						时长
					</th>
					<th width="190px"  style="text-align: center"><!--原因-->
						原因
					</th>
					<th width="100px"  style="text-align: center"><!--审批状态-->
						审批状态
					</th>
					<th width="200px"  style="text-align: center"><!--原因-->
						否决原因
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveApplyAffirmList}" var="leaveAffrim" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" >
					    <td width="30px"   style="text-align: center">${i.count}</td>${leaveAffrim.AFFIRM_NAME}
					    <td width="30px" style="text-align: center">
					        <input type="checkbox" id="c1${leaveAffrim.APPLY_NO}" name="c1" value="${leaveAffrim.APPLY_NO}" />
					        <input type="hidden" id="applyNo${i.index}" value="${leaveAffrim.APPLY_NO}">
					        <input type="hidden" id="PERSON_ID" value="${admin.personId}">
					    </td>
					    <td width="80px" style="text-align: center">
                            ${leaveAffrim.LOCAL_NAME}
					    </td>
					    <td width="50px"  style="text-align: center">
					        ${leaveAffrim.EMPID}
					    </td>
					    <td width="80px"  style="text-align: center">
					   		${leaveAffrim.DEPTNO}
					    </td>
					    <td width="80px" style="text-align: center">
					     	${leaveAffrim.ITEM_NAME}
					     <input type="hidden" name="ITEM_NO${leaveAffrim.APPLY_NO}" name="ITEM_NO${leaveAffrim.APPLY_NO}" value="${leaveAffrim.ITEM_NO}"/>
					    </td>
					    <td width="80px" style="text-align: center">
					      	${leaveAffrim.FROM_DATE}
					      <input type="hidden"  id="FROM_DATE${leaveAffrim.APPLY_NO}" name="FROM_DATE${leaveAffrim.APPLY_NO}" value="${leaveAffrim.FROM_DATE}"/>
					    </td>
					    <td width="50px" style="text-align: center">
					      	${leaveAffrim.FROM_TIME}
					    </td>
					    <td width="80px" style="text-align: center">
					      	${leaveAffrim.TO_DATE}
					       <input type="hidden"  id="TO_DATE${leaveAffrim.APPLY_NO}" name="TO_DATE${leaveAffrim.APPLY_NO}" value="${leaveAffrim.TO_DATE}"/>
					    </td>
					    <td width="50px" style="text-align: center">
					       	${leaveAffrim.TO_TIME}
					    </td>
					    <td width="50px" style="text-align: center">
					       	${leaveAffrim.APPLY_LENGTH}
					    </td>
					    <td width="190px" style="text-align: center">
					      ${leaveAffrim.LEAVE_REASON}
					    </td>
					    <td  width="100px"  style="text-align: center">
					    	<ait:SelectSyCodeCombinByCpnyID id="AFFIRM_FLAG${leaveAffrim.APPLY_NO}" name="AFFIRM_FLAG${leaveAffrim.APPLY_NO}" combinParentNo="14014304" selected="${leaveAffrim.AFFIRM_FLAG}"  exclude="14014306,14014310" cnpyID="${LoginUser.cpnyId}"/>
					    </td>
					    <td width="200px" style="text-align: center">
					       <input type="text" id="AFFIRM_CONTENT${leaveAffrim.APPLY_NO}" name="AFFIRM_CONTENT${leaveAffrim.APPLY_NO}">
					    </td> 
					</tr>
				 </c:forEach> 
			</tbody>
	</table>
</form>
<div  style="visibility: hidden;">
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>