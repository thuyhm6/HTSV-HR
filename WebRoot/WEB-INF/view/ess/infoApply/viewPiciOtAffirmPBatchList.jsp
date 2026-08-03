<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
//<!--

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
		$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApply/viewPiciOtAffirmPBatchList?seach_PERSON_ID="+seach_PERSON_ID
				+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG+"&seach_BATCH_APPLY_NO="+seach_BATCH_APPLY_NO);
	}
	
	
	function delOtApplyCallback(OP_FLAG,form,callback) {
		$("#BATCH_POT_OP_FLAG").val(OP_FLAG);
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
	    $form.attr("action","/ess/infoApply/delLOvertimeApplyInBatch");
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
							navTabSearch("viewPiciOtAffirmPBatchList");
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
	 
 
	
	function cancelLOvertimeApply(apply_no){
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
					navTabSearch(document.viewPiciOtAffirmPBatchList);
				}else{
					alert(" 本月考勤已锁定或此加班已明细中锁定，取消失败！");
				}
			  }
			});
		}
	}
	
	function downloadImportTemplate_viweapplyleavebatch(){
		var url = "/ess/infoApplyLeave/exportBatchLeaveModule?navTabId=ess0246";
		document.getElementById("exportExcel_viweapplyleavebatch").href=encodeURI(url);
	}
	
 
//-->
</script>

<script type="text/javascript">
function exportPBatchModel(){
	var url = "/ess/infoApply/exportBatchOtModuleP?navTabId=ess0213";
	document.getElementById("exportPBatchExcel").href=encodeURI(url);
}

function importPOtBatch(){
	$("#importExcelDialogP_ess0213").attr('href','/pa/excelImport/importExcelData?importFunName=/importPOtApplyTemp');
	$("#importExcelDialogP_ess0213").click();
}
//-->
</script>

<a id="importExcelDialogP_ess0213" href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess0213" href="#" target="navTab" mask="true">
	<span style="display:none;">加班申请P导入</span>
</a>
 
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPiciOtAffirmPBatchList" rel="pagerForm" method="post"
		id="viewPiciOtAffirmPBatchList" name="viewPiciOtAffirmPBatchList">
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
					<td><input
						type="text" name="seach_KEY" value="${KEY}" /></td>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar"  id="viewApplyLeaveInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"   limit="ar" id="viewApplyLeaveInfoList_seachDept" selected="${DEPTNO}"/></td>
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
					    <input type="hidden" id="seach_DEFAULTT" name="seach_DEFAULTT" value="default"/>
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
		<!--<li><a class="buttonActive" id ="exportExcel_viweapplyleavebatch" onclick="downloadImportTemplate_viweapplyleavebatch();" href="#">
			<span><spring:message code="pa.insurance.title.downloadImportTemplate"/>下载导入模板</span>
			</a></li>
		<li><a class="buttonActive" onclick="excelimport_viewapplyleavebatch();">
			<span><spring:message code="ar.addempshift.title.excelimport"/> EXCEL导入 </span>
				</a></li>
		-->
			<li>
					<a class="buttonActive" id ="exportPBatchExcel" onclick="exportPBatchModel();" href="#">
						<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
					</a>
				</li>
				<li>
					<a class="buttonActive" onclick="importPOtBatch();">
						<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
					</a>
				</li>
		<li>
				<a class="add" href="/ess/infoApply/viewOtAffirmPBatchList" 
					target="navTab"><span>批量申请加班</span></a>
			</li>
		<li><a class="buttonActive" onclick="delOtApplyCallback(0,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="delOtApplyCallback(1,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>提交</span></a></li>
	</ul>
</div>
	<form name="delOtApplyAffirmForm" id="delOtApplyAffirmForm" method="post" action="/ess/infoApply/delLOvertimeApplyInBatch" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
			<thead>
				<tr>
					<th width="20">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
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
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">			
					<tr target="sid" rel="${admin.personId}">
					    <td style="text-align: center">
					    	<c:if test="${otApply.AFFIRM_FLAG eq '0' || otApply.AFFIRM_FLAG eq '-1'}">
					        	<input type="checkbox" id="c1" name="c1" value="${otApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">

	    <c:if test="${otApply.AFFIRM_FLAG eq '-1'}"><!--
					    		<a class="update" href="/ess/infoApply/viewOtAffirmPBatchUpdateList?seach_APPLY_TYPE_WQ=31&APPLY_NO=${otApply.APPLY_NO}" title="修改加班"
									target="navTab"> 
									-->
									
										<a rel="leaveApplyAffirm" href="/ess/infoApply/viewFullApplyOtBatchAffirmInfo1?APPLY_NO=${otApply.APPLY_NO}&pageNum=1" title="决裁详情"
						          target="navTab" rel="viewFullApplyOtBatchAffirmInfo">
								 	<font color="blue">	 ${otApply.BATCH_APPLY_NO}</font></a>
							</c:if>
					    <c:if test="${otApply.AFFIRM_FLAG ne '-1'}">
								 ${otApply.BATCH_APPLY_NO}
							</c:if>
</td>
					    <td style="text-align: center">${otApply.EMPID}</td>
					    <td style="text-align: center">
					    	<c:if test="${otApply.AFFIRM_FLAG eq '-1'}">
								<a rel="leaveApplyAffirm" href="/ess/infoApply/viewFullApplyOtBatchAffirmInfo1?APPLY_NO=${otApply.APPLY_NO}&pageNum=1" title="决裁详情"
						          target="navTab" rel="viewFullApplyOtBatchAffirmInfo"><font color="red">${otApply.LOCAL_NAME}</font></a>
					    	</c:if>
					    	<c:if test="${otApply.AFFIRM_FLAG ne '-1'}">
					    		${otApply.LOCAL_NAME}
					    	</c:if>
					    </td>
					    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>
					    <td style="text-align: center">${otApply.BATCH_APPLY_DEPT_NAME}</td>
						<td style="text-align: center">
					    	<c:if test="${otApply.AFFIRM_FLAG eq '-1'}">
								<a rel="leaveApplyAffirm" href="/ess/infoApply/viewFullApplyOtBatchAffirmInfo1?APPLY_NO=${otApply.APPLY_NO}&pageNum=1" title="决裁详情"
						          target="navTab" rel="viewFullApplyOtBatchAffirmInfo">查看</a>
					    	</c:if>
					    	<c:if test="${otApply.AFFIRM_FLAG ne '-1'}">
								<a rel="leaveApplyAffirm" href="/ess/infoApply/viewFullApplyOtBatchAffirmInfo?APPLY_NO=${otApply.APPLY_NO}&pageNum=1" title="决裁详情"
						          target="navTab" rel="viewFullApplyOtBatchAffirmInfo">查看</a>
					    	</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${otApply.AFFIRM_FLAG eq '-1'}">
								<font color="blue">暂存</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '0'}">
								<font color="back">提交</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '1'}">
								<font color="green">通过</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '2'}">
								<font color="red">否决</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '3'}">
								<font color="grey">撤销</font>
							</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>
						<td style="text-align: center">
						 
							
					<c:if test="${otApply.AFFIRM_FLAG eq '1'}">
								<a href="#" title="取消" onclick="cancelLOvertimeApply('${otApply.APPLY_NO}')" style="cursor: hand">
									<font color="red">取消</font>
								</a>
							</c:if>		
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_POT_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
    <c:set value="/ess/infoApply/viewPiciOtAffirmPBatchList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>