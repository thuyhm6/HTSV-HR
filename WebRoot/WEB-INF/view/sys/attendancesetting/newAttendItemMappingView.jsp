<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function aa(){
    var ids=document.getElementsByName("CPNY_ID"); 
}

function submitobj(){
	var $form = $("#updateAttendItemMapping");
}
function expContractInfo(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#updateAttendItemMapping");
  	if(CheckFormContractSearch($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doContractInfoExport($from);}});
    } 
}

function submitForm(){
  	var $from = $("#updateAttendItemMapping");
  	$from.submit();
}




function validateCallback(form, callback) {


	var $form = $("#updateAttendItemMappingInfo");
	 
	if (!$form.valid()) {
		return false;
	}

	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
</script>
<div class="searchBar">
<form id="updateAttendItemMapping" method="post" action="/sys/attendancesetting/newAttendItemMappingView?&navTabId=ar0317_newAttendItemMappingView&pageNum=0"
		class="pageForm required-validate" onsubmit="return navTabSearch(this);">
			<table class="searchContent">
				<tr>
					<th><spring:message code="pa.insurance.title.projectType" />
						<!--项目类型-->： 
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE}"> 
						<option value="明细项目" <c:if test="${TYPE eq '明细项目'}">selected</c:if> >
							<spring:message code="ar.attenditem.title.mingxixiangmu"/>
						</option>
						<option value="汇总项目" <c:if test="${TYPE eq '汇总项目'}">selected</c:if>>
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/>
						</option>
						</select>
					</th>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" onsubmit="submitobj();">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>

							</div>
						</div>
					</li>
				</ul>
			</div>
			</form>
		</div>
<div class="pageHeader">
	<form id="updateAttendItemMappingInfo" method="post" action="/sys/attendancesetting/updateAttendItemMappingInfo"
		class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		
	
		<table class="table" width="100%" layoutH="100" class="user_table">
			<thead>
			   
				<tr width="100%">
					<th width="3%"><spring:message
							code="sys.affirm.indexNum" /> <!--序号-->
					</th>
					<th width="6%"><spring:message
							code="pa.insurance.title.projectType" /> <!--项目类型-->
					</th>
					<th width="12%"><spring:message
							code="pa.insurance.title.projectName" /> <!--项目名称-->
					</th>
					<th width="13%"><spring:message
							code="pa.insurance.title.description" /> <!--描述-->
					</th>
					<th >TSTO</th>
					<th >SST</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${attendItemList}" var="item" varStatus="i">
				<tr>
					<td>${i.count}&nbsp;</td>
					<td>${item.PROJECT_TYPE} <input type="hidden"
						name="PROJECT_TYPE" id="PROJECT_TYPE"
						value="${item.PROJECT_TYPE eq '明细项目' ? 1 : 2}" />
						<input type="hidden" name="ITEM_NO" id="ITEM_NO"
						value="${item.ITEM_NO}" />
					</td>
					<td>${item.ITEM_NAME}</td>
					<td>${item.DESCRIPTION}</td>

					<td ><c:if test="${item.TSTO==1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID${i.index}"
								checked="checked" value='TSTO' />
						</c:if> <c:if test="${item.TSTO!=1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID${i.index}" value='TSTO' />
						</c:if>
					</td>
					<td ><c:if test="${item.SST==1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID${i.index}"
								checked="checked" value="SST" />
						</c:if> <c:if test="${item.SST!=1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID${i.index}" value="SST" />
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="aa();">
								<spring:message code="public.title.submit" />
								<!-- 提交 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
