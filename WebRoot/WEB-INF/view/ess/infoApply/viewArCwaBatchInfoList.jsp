<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
function delarCwaApplyCallbackBatch(OP_FLAG,form,callback) {
	$("#BATCH_AR_CWA_OP_FLAG").val(OP_FLAG);
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
    var checked=false;
	var ids= document.getElementsByName("BATCH_CWA_BATCH");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApply/delarCwaApplyInBatchForBatch");
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
						alertMsg.correct(data.message);
						navTabSearch(document.viewApplyCwaBatchInfoList);
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

function downloadImportTemplate_viweapplyAbsenteeismbatchess0307(){
	var url = "/ess/infoApply/exportBatchAbsenteeismModule?navTabId=ess0307";
	document.getElementById("exportExcel_viweapplyAbsenteeismbatchess0307").href=encodeURI(url);
}
function excelimport_viewapplyAbsenteeismbatchess0307(){
	$("#importExcelDialog_ess0307").attr('href','/pa/excelImport/importExcelData?importFunName=/importAbsenteeismTempess0307');
	$("#importExcelDialog_ess0307").click();
}
//-->
</script>

<a id="importExcelDialog_ess0307"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess0307" href="#" target="navTab" mask="true"><span style="display:none;">旷工申请信息导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewArCwaBatchInfoList" rel="pagerForm" method="post"
		id="viewApplyCwaBatchInfoList" name="viewApplyCwaBatchInfoList">
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
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar"  id="viewApplyArMacInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"   limit="ar" id="viewApplyArMacInfoList_seachDept" selected="${DEPT_NO}"/></td>
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
					<li>
						<a class="buttonActive" id ="exportExcel_viweapplyAbsenteeismbatchess0307" onclick="downloadImportTemplate_viweapplyAbsenteeismbatchess0307();" href="#">
							<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  onclick="excelimport_viewapplyAbsenteeismbatchess0307();"><span>批量导入</span></a>
					</li>
		<li><a class="buttonActive" onclick="delarCwaApplyCallbackBatch(0,'delarCwaApplyAffirmFormBatch',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="delarCwaApplyCallbackBatch(1,'delarCwaApplyAffirmFormBatch',DWZ.ajaxDone)"><span>提交</span></a></li>
	</ul>
</div>
	<form name="delLeaveApplyAffirmForm" id="delarCwaApplyAffirmFormBatch" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delarCwaApplyCallbackBatch(this, navTabAjaxDone);">
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
			<thead>
				<tr>
					<th width="20">
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_CWA_BATCH" />
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
				<c:forEach items="${cwaInfoList}" var="arCwaApply" varStatus="i">			
					<tr target="sid" rel="${admin.personId}">
					    <td style="text-align: center">
					    	<c:if test="${arCwaApply.AFFIRM_FLAG eq '0' || arCwaApply.AFFIRM_FLAG eq '-1'}">
					        	<input type="checkbox" id="BATCH_CWA_BATCH" name="BATCH_CWA_BATCH" value="${arCwaApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center">${arCwaApply.BATCH_APPLY_NO}</td>
					    <td style="text-align: center">${arCwaApply.EMPID}</td>
					    <td style="text-align: center">
					    	<c:if test="${arCwaApply.AFFIRM_FLAG eq '-1'}">
								<a rel="arCwaApplyAffirm" href="/ess/infoApply/viewFullCwaApplyAffirmInfo1?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${arCwaApply.APPLY_NO }&pageNum=1" title="详细查看"
						          target="navTab" ><font color="red">${arCwaApply.LOCAL_NAME}</font></a>
					    	</c:if>
					    	<c:if test="${arCwaApply.AFFIRM_FLAG ne '-1'}">
					    		${arCwaApply.LOCAL_NAME}
					    	</c:if>
					    </td>
					    <td style="text-align: center">${arCwaApply.APPLY_DATE}</td>
					    <td style="text-align: center">${arCwaApply.BATCH_APPLY_DEPT_NAME}</td>
						<td style="text-align: center">
					    	<c:if test="${arCwaApply.AFFIRM_FLAG eq '-1'}">
								<a rel="arCwaApplyAffirm" href="/ess/infoApply/viewFullCwaApplyAffirmInfo1?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${arCwaApply.APPLY_NO}&pageNum=1" title="详细查看"
						          target="navTab" >查看</a>
					    	</c:if>
					    	<c:if test="${arCwaApply.AFFIRM_FLAG ne '-1'}">
								<a rel="arCwaApplyAffirm" href="/ess/infoApply/viewFullCwaApplyAffirmInfo?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${arCwaApply.APPLY_NO}&pageNum=1" title="详细查看"
						          target="navTab" >查看</a>
					    	</c:if>
						</td>
						<td style="text-align: center">
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '-1'}">
								<font color="blue">暂存</font>
							</c:if>
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '0'}">
								<font color="back">提交</font>
							</c:if>
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '1'}">
								<font color="green">通过</font>
							</c:if>
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '2'}">
								<font color="red">否决</font>
							</c:if>
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '3'}">
								<font color="grey">取消</font>
							</c:if>
							<c:if test="${arCwaApply.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_AR_CWA_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
    <c:set value="/ess/infoApply/viewArCwaBatchInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>