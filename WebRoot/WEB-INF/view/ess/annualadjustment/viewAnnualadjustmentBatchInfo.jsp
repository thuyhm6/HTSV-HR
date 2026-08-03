<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
function delArVacApplyCallbackBatch(OP_FLAG,form,callback) {
	$("#BATCH_AR_VAC_OP_FLAG").val(OP_FLAG);
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
    var checked=false;
	var ids= document.getElementsByName("BATCH_VAC");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/annualadjustment/delArVacApplyInBatchForBatch");
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
				alertMsg.info("删除成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveBatchInfoList);
			}else{
				alertMsg.info("删除失败！");
			}
		  }
		});
	}
}

function downloadImportTemplate_viweapplyArVacbatch(){
	var url = "/ess/annualadjustment/exportBatchLArVacModule?navTabId=ess0201";
	document.getElementById("exportExcel_viweapplyArVacbatch").href=encodeURI(url);
}

function excelimport_viewapplyArVacbatch(){
	$("#importExcelDialog_ess0201").attr('href','/pa/excelImport/importExcelData?importFunName=/importArVacTemp');
	$("#importExcelDialog_ess0201").click();
}


function cancelCardApplyBatch(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	alertMsg.confirm("确定要取消吗?",{okCall:function(){
		$.ajax({
		  url: '/ess/recordApply/cancelCardApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				alertMsg.info("取消成功！");
				//页面重载
				navTabSearch(document.viewApplyLeaveBatchInfoList);
			}else{
				alertMsg.info("取消失败！");
			}
		  }
		});
    }});
}
//-->
</script>

<a id="importExcelDialog_ess0201"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess0201" href="#" target="navTab" mask="true"><span style="display:none;">年假调整信息导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/annualadjustment/viewAnnualadjustmentBatchInfo" rel="pagerForm" method="post"
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
					</td>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar"  id="viewApplyArVacInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"   limit="ar" id="viewApplyArVacInfoList_seachDept" selected="${DEPT_NO}"/></td>
					</c:if>
					<td>批次号</td>
					<td>
						<input type="text" id="seach_BATCH_APPLY_NO" name="seach_BATCH_APPLY_NO" value="${BATCH_APPLY_NO }"/>
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
					<td>
						开始日期
					</td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${START_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td>
						结束日期
					</td>
					<td>
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${END_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
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
		<li><a class="buttonActive" id ="exportExcel_viweapplyArVacbatch" onclick="downloadImportTemplate_viweapplyArVacbatch();" href="#">
			<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
			</a></li>
		<li>
			<a class="buttonActive" onclick="excelimport_viewapplyArVacbatch();">
				<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
			</a>
		</li>
		<li><a class="buttonActive" onclick="delArVacApplyCallbackBatch(0,'delArVacApplyAffirmFormBatch',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="delArVacApplyCallbackBatch(1,'delArVacApplyAffirmFormBatch',DWZ.ajaxDone)"><span>提交</span></a></li>
	</ul>
</div>
	<form name="delArVacApplyAffirmFormBatch" id="delArVacApplyAffirmFormBatch" method="post" action="/ess/annualadjustment/delArVacApplyInBatchForBatch" 
	  onsubmit="return delArVacApplyCallbackBatch(this, navTabAjaxDone);">
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
			<thead>
				<tr>
					<th width="20">
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_VAC" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${annualadjustmentInfoList}" var="ArVacApply" varStatus="i">			
					<tr target="sid" rel="${admin.personId}">
					    <td style="text-align: center">
					    	<c:if test="${ArVacApply.AFFIRM_FLAG eq '0' || ArVacApply.AFFIRM_FLAG eq '-1'}">
					        	<input type="checkbox" id="BATCH_VAC" name="BATCH_VAC" value="${ArVacApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">${ArVacApply.BATCH_APPLY_NO}</td>
					    <td style="text-align: center">${ArVacApply.EMPID}</td>
					    <td style="text-align: center">
					    	<c:if test="${ArVacApply.AFFIRM_FLAG eq '-1'}">
								<a rel="ArVacApplyAffirm" href="/ess/annualadjustment/viewFullAnnuAffirmInfo1?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${ArVacApply.APPLY_NO}&pageNum=1" title="详细查看"
						          target="navTab" ><font color="red">${ArVacApply.LOCAL_NAME}</font></a>
					    	</c:if>
					    	<c:if test="${ArVacApply.AFFIRM_FLAG ne '-1'}">
					    		${ArVacApply.LOCAL_NAME}
					    	</c:if>
					    </td>
					    <td style="text-align: center">${ArVacApply.APPLY_DATE}</td>
					    <td style="text-align: center">${ArVacApply.BATCH_APPLY_DEPT_NAME}</td>
						<td style="text-align: center">
					    	<c:if test="${ArVacApply.AFFIRM_FLAG eq '-1'}">
								<a rel="ArVacApplyAffirm" href="/ess/annualadjustment/viewFullAnnuAffirmInfo1?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${ArVacApply.APPLY_NO}&pageNum=1" title="详细查看"
						          target="navTab" >查看</a>
					    	</c:if>
					    	<c:if test="${ArVacApply.AFFIRM_FLAG ne '-1'}">
								<a rel="ArVacApplyAffirm" href="/ess/annualadjustment/viewFullAnnuAffirmInfo?seach_APPLY_TYPE_NO=216691&seach_APPLY_NO=${ArVacApply.APPLY_NO}&pageNum=1" title="详细查看"
						          target="navTab" >查看</a>
					    	</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '-1'}">
								<font color="blue">暂存</font>
							</c:if>
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '0'}">
								<font color="back">提交</font>
							</c:if>
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '1'}">
								<font color="green">通过</font>
							</c:if>
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '2'}">
								<font color="red">否决</font>
							</c:if>
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '3'}">
								<font color="grey">取消</font>
							</c:if>
							<c:if test="${ArVacApply.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_AR_VAC_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
    <c:set value="/ess/annualadjustment/viewAnnualadjustmentBatchInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>