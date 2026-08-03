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
	var seach_BATCH_APPLY_NO=$("#seach_BATCH_APPLY_NO",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_BATCH_APPLY_NO",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApplyLeave/viewApplyLeaveBatchInfoList?seach_PERSON_ID="+seach_PERSON_ID
			+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_BATCH_APPLY_NO="+seach_BATCH_APPLY_NO);
}


function delLeaveApplyCallbackBatch(OP_FLAG,form,callback) {
	$("#BATCH_LEAVE_OP_FLAG").val(OP_FLAG);
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("BATCH_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApplyLeave/delLeaveApplyInBatchForBatch");
    var msg = "确定要删除吗？";
    if(OP_FLAG == 1){
        var msg = "确定要提交吗？";
    }
    alertMsg.confirm(msg,{okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"), 
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("viewApplyLeaveBatchInfoList");
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
        }});
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
		  url: '/ess/infoApplyLeave/delLeaveApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveBatchInfoList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
function cancelLeaveApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	if (confirm ("确定要取消吗?")){	  
		$.ajax({
		  url: '/ess/infoApplyLeave/cancelLeaveApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveBatchInfoList);
			}else{
				alert("取消失败！");
			}
		  }
		});
	}
}

function xiaojiaLeaveApplyNoAffirm(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{
		name: 'APPLY_TYPE',
		value: 'BATCH'
	});
	alertMsg.confirm ("确定要销假吗?",{
		okCall:function(){
		$.ajax({
			  url: '/ess/infoApplyLeave/addXiaojiaLeaveApplyNoAffirm',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					alertMsg.correct("销假成功!");
					//页面重载
					navTabSearch(document.viewApplyLeaveBatchInfoList);
				}else{
					alert("销假失败！");
				}
			  }
			});
		}});	  
}
function xiaojiaLeaveApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{
		name: 'APPLY_TYPE',
		value: 'BATCH'
	});
	if (confirm ("确定要申请销假吗?")){	  
		$.ajax({
		  url: '/ess/infoApplyLeave/addXiaojiaLeaveApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				alertMsg.correct("销假申请成功!");
				//页面重载
				navTabSearch(document.viewApplyLeaveBatchInfoList);
			}else{
				alert("取消失败！");
			}
		  }
		});
	}
}

function downloadImportTemplate_viweapplyleavebatch(){
	var url = "/ess/infoApplyLeave/exportBatchLeaveModule?navTabId=ess0246";
	document.getElementById("exportExcel_viweapplyleavebatch").href=encodeURI(url);
}

function excelimport_viewapplyleavebatch(){
	$("#importExcelDialog_ess0246").attr('href','/pa/excelImport/importExcelData?importFunName=/importLeaveTemp&LEAVE_TYPE=ess0246');
	$("#importExcelDialog_ess0246").click();
}
//-->
</script>

<a id="importExcelDialog_ess0246"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess0246" href="#" target="navTab" mask="true"><span style="display:none;">Leave信息导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyLeave/viewApplyLeaveBatchInfoList" rel="pagerForm" method="post"
		id="viewApplyLeaveBatchInfoList" name="viewApplyLeaveBatchInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<c:if test="${authority ne '1'}">
					<td>社号/姓名</td>
					<td>
						${admin.empID }/${admin.localName }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${admin.personId }"/>
					</td>
					<td>
						部门
					</td>
					<td>
						${admin.content }
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${admin.empID}" />
						</c:if>
						 <input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar"  id="viewApplyLeaveInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}"   limit="ar" id="viewApplyLeaveInfoList_seachDept" selected="${DEPTNO}"/></td>
					</c:if>
					<td>
						申请日期
					</td>
					<td>
						<input type="text" id="seach_APPLY_TIME" name="seach_APPLY_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${APPLY_TIME}" />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td><!-- 决裁状态 -->
						 审批状态
					</td>					
					<td>
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>撤销</option>
						 </select>
					</td>
				</tr>
				<tr>
					<td>批次号</td>
					<td>
						<input type="text" id="seach_BATCH_APPLY_NO" name="seach_BATCH_APPLY_NO" value="${BATCH_APPLY_NO }"/>
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

<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id ="exportExcel_viweapplyleavebatch" onclick="downloadImportTemplate_viweapplyleavebatch();" href="#">
			<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
			</a></li>
		<li><a class="buttonActive" onclick="excelimport_viewapplyleavebatch();">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(0,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(1,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span>提交</span></a></li>
	</ul>
</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmFormBatch" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallbackBatch(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
			<thead>
				<tr>
					<th width="20">
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_LEAVE" />
				    </th>
				    <th width="40" style="text-align: center"><!--工号-->
						批次号
					</th>
				    <th width="40" style="text-align: center"><!--工号-->
						社号
					</th>
				    <th width="40" style="text-align: center"><!--申请人-->
						申请人
					</th>
					<th width="60" style="text-align: center"><!--申请日期-->
						申请日期
					</th>
					<th width="60" style="text-align: center"><!--申请日期-->
						申请部门
					</th>
					<th width="100" style="text-align: center"><!--详细查看-->
						审批详细
					</th>
					<th width="60" style="text-align: center"><!--决裁情况-->
						审批状态
					</th>
					<th width="60" style="text-align: center"><!--是否取消-->
						是否取消
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">			
					<tr target="sid" rel="${admin.personId}">
					    <td style="text-align: center">
					    	<c:if test="${leaveApply.AFFIRM_FLAG eq '0' || leaveApply.AFFIRM_FLAG eq '-1'}">
					        	<input type="checkbox" id="BATCH_LEAVE" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">${leaveApply.BATCH_APPLY_NO}</td>
					    <td style="text-align: center">${leaveApply.EMPID}</td>
					    <td style="text-align: center">
					    	<c:if test="${leaveApply.AFFIRM_FLAG eq '-1'}">
								<a rel="leaveApplyAffirm" href="/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo1?APPLY_NO=${leaveApply.APPLY_NO}&pageNum=1&unDoApplyNo=${leaveApply.UNDO_APPLY_NO}" title="决裁详情"
						          target="navTab" rel="viewFullApplyBatchAffirmInfo"><font color="red">${leaveApply.LOCAL_NAME}</font></a>
					    	</c:if>
					    	<c:if test="${leaveApply.AFFIRM_FLAG ne '-1'}">
					    		${leaveApply.LOCAL_NAME}
					    	</c:if>
					    </td>
					    <td style="text-align: center">${leaveApply.APPLY_LEAVE_DATE}</td>
					    <td style="text-align: center">${leaveApply.BATCH_APPLY_DEPT_NAME}</td>
						<td style="text-align: center">
					    	<c:if test="${leaveApply.AFFIRM_FLAG eq '-1'}">
								<a rel="leaveApplyAffirm" href="/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo1?APPLY_NO=${leaveApply.APPLY_NO}&pageNum=1&unDoApplyNo=${leaveApply.UNDO_APPLY_NO}" title="决裁详情"
						          target="navTab" rel="viewFullApplyBatchAffirmInfo">查看</a>
					    	</c:if>
					    	<c:if test="${leaveApply.AFFIRM_FLAG ne '-1'}">
							<a rel="leaveApplyAffirm" href="/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo?APPLY_NO=${leaveApply.APPLY_NO}&pageNum=1&unDoApplyNo=${leaveApply.UNDO_APPLY_NO}" title="决裁详情"
					          target="navTab" rel="viewFullApplyBatchAffirmInfo">查看</a>
					    	</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${leaveApply.AFFIRM_FLAG eq '-1'}">
								<font color="blue">暂存</font>
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '0'}">
								<font color="back">提交</font>
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '1'}">
								<font color="green">通过</font>
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '2'}">
								<font color="red">否决</font>
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '3'}">
								<font color="grey">撤销</font>
							</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${leaveApply.LASTMONTH_YN eq 'N'}">
								<c:if test="${LoginUser.cpnyId eq 'LGEND' or LoginUser.cpnyId eq 'LGEPN'}">
									<c:if test="${leaveApply.AFFIRM_FLAG eq '1' and leaveApply.LEAVE_TYPE_CODE ne '218112' and leaveApply.CAL_FLAG eq '0'}">
										<a href="#" title="申请销假" onclick="xiaojiaLeaveApplyNoAffirm('${leaveApply.APPLY_NO}')" style="cursor: hand">
											<font color="red">申请销假</font>
										</a>
									</c:if>
								</c:if>
								<c:if test="${LoginUser.cpnyId ne 'LGEND' and LoginUser.cpnyId ne 'LGEPN'}">
									<c:if test="${leaveApply.AFFIRM_FLAG eq '1' and leaveApply.LEAVE_TYPE_CODE ne '218112' and leaveApply.CAL_FLAG eq '0'}">
										<a href="#" title="申请销假" onclick="xiaojiaLeaveApply('${leaveApply.APPLY_NO}')" style="cursor: hand">
											<font color="red">申请销假</font>
										</a>
									</c:if>
								</c:if>
							</c:if>
							<c:if test="${leaveApply.LEAVE_TYPE_CODE eq '218112' }">
								销假
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_LEAVE_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
    <c:set value="/ess/infoApplyLeave/viewApplyLeaveBatchInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>