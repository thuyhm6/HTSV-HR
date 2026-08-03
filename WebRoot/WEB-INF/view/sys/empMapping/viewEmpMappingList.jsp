<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function sync_emp_info(flag) {
	
    var checked=false;
	var ids= document.getElementsByName("SYNC_EMP");
	var no = "";
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			no = no + ids[i].value + ",";
		}
	}
	if(!checked){
		alertMsg.error('请先选择要同步的信息'); 
		return false;
	}
	var msgInfo = "";
	if(flag == 1){
		msgInfo = "确定要删除吗?";
	}else{
		msgInfo = "确定要同步吗?";
	}
	var params = [];
	params.push({
		name: 'OP_FLAG',
		value: flag
	},{
		name: 'SYNC_EMP',
		value: no
	});
    alertMsg.confirm (msgInfo,{
        okCall:function(){
	    	$.ajax({
				type: 'POST',
				url:'/sys/empMapping/deleteEmpMapping',
				data:params,
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewEmpInfo_sy0490"));
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

function downloadImportTemplate_viweapplyMappingbatch(){
	var url = "/sys/empMapping/exportBatchMappingModule?navTabId=sy0490";
	document.getElementById("exportExcel_viweapplyMappingbatch").href=encodeURI(url);
}
function excelimport_viewapplyMappingbatch(){
	$("#importExcelDialog_sy0490").attr('href','/pa/excelImport/importExcelData?importFunName=/importMappingTemp');
	$("#importExcelDialog_sy0490").click();
}
</script>
<a id="importExcelDialog_sy0490"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_sy0490" href="#" target="navTab" mask="true"><span style="display:none;">新旧社号mapping导入结果</span></a>
<div class="pageHeader">
<form id="viewEmpInfo_sy0490" onsubmit="return navTabSearch(this);" action="/sys/empMapping/viewEmpMappingList" method="post" rel="pagerForm">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 部门： --> <spring:message
			code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/></td>
		<td><!-- 工号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input
			type="text" name="seach_KEY" value="${KEY}" /></td>
		<td><!-- 在职区分： --> <spring:message
			code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /> 
		</td>
		<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE_NAME" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE_NAME}" cnpyID="${defaultCpny}" limit="all"/>
		</td>
		<td>
			年假同步状态
		</td>
		<td>
				     <select id="seach_SYNC_FLAG" name="seach_SYNC_FLAG">
						 <option value="">全部</option>
						 <option value="0" <c:if test="${SYNC_FLAG eq '0'}">selected</c:if>>未同步</option>
						 <option value="1" <c:if test="${SYNC_FLAG eq '1'}">selected</c:if>>已同步</option>
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
<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
			
			<li>
				<a class="delete" onclick="sync_emp_info(2)" href="#"><span>同步年假</span></a>					
			</li>
			<c:if test="${LoginUser.cpnyId ne 'LGEKS'}">
			<li>
				<a class="delete" onclick="sync_emp_info(3)" href="#"><span>同步调休</span></a>					
			</li>
			</c:if>
			
			<li><a class="buttonActive" id ="exportExcel_viweapplyMappingbatch" onclick="downloadImportTemplate_viweapplyMappingbatch();" href="#">
				<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a></li>
			<li><a class="buttonActive" onclick="excelimport_viewapplyMappingbatch();">
				<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
					</a></li>
			<li>
			<li>
				<a class="add" href="/sys/empMapping/viewAddEmpMapping" 
					target="dialog"  width="500" height="220" mask="true" rel="viewAddEmpMapping"><span>添加</span></a>
			</li>
		</c:if>
		<c:if test="${toolbarInfo.DELETER == '1'}">
			<li>
				<a class="delete" onclick="sync_emp_info(1)" href="#"><span>删除</span></a>		
			</li>
		</c:if>
	</ul>
</div>
<table class="table" width="100%" layoutH="215">
	<thead>
		<tr>
			<th width="3%">
				<input type="checkbox" class="checkboxCtrl" group="SYNC_EMP"/>
			</th>
			<th width="8%"><spring:message
				code="hr.enpinfo.title.EMP.EMPNUMBER" /> <!--社号--></th>
			<th width="8%"><spring:message
				code="public.title.name" /> <!--姓名--></th>
			<th width="13%"><spring:message
				code="public.title.deptName" /> <!--部门--></th>
			<th width="13%"><spring:message
				code="hr.viewWorkInfo.title.DUTY" /> <!--职责--></th>
			<th width="10%"><spring:message
				code="hr.enpinfo.title.EMP.TYPE" /> <!--人员类型--></th>
			<th width="10%">旧社号</th> 
			<th width="10%"><!-- 在职区分： --> <spring:message
			code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /></th> 
			<th width="8%">年假</th> 
			<th width="8%">调休</th> 
			<th width="9%">信息同步</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${empInfo}" var="item" varStatus="i">
			<tr target="NO" rel="${item.NO}">
				<td style="text-align: center">
					<c:if test="${item.SYNC_VAC eq '未同步' || item.SYNC_TX eq '未同步'}">
					    <input type="checkbox" id="SYNC_EMP" name="SYNC_EMP" value="${item.NO}" />
					</c:if>
				</td>
				<td style="td_center">${item.NEW_EMPID}</td>
				<td style="td_center">${item.LOCAL_NAME}</td>
				<td style="td_center">${item.DEPT_NAME}</td>
				<td class='td_center' >${item.POSITION}</td>
				<td class='td_center' >${item.EMP_TYPE_NAME}</td>
				<td class='td_center' >${item.OLD_EMPID}</td>
				<td class='td_center' >${item.STATUS_NAME}</td>
				<td class='td_center' >${item.SYNC_VAC}</td>
				<td class='td_center' >${item.SYNC_TX}</td>
				<td class='td_center' >
					<a rel="empMappingSync" href="/sys/empMapping/syncEmpMapping?NO=${item.NO}" title="信息同步"
					          target="dialog" mask="true" width="950" height="450" id="empMappingSync" >信息同步</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/sys/empMapping/viewEmpMappingList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
