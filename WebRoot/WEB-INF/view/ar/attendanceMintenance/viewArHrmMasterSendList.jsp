<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function makeHrmMaster(navTabId) {
	var $form = $("#viewArHrmMasterSendList");
	
	var key = $form.find("#seach_KEY").val();
	var deptNo = $form.find("#seach_DEPT_NO").val();
	var sendDate = $form.find("#seach_MAS_SEND_DATE").val();
	var resignDate = $form.find("#seach_RESIGN_DATE").val();
	var empOffice = $form.find("#seach_EMP_OFFICE").val();
	var sendType = $form.find("#seach_MAS_SEND_TYPE").val();
	
	alertMsg.confirm("确认传送考勤机人事Master信息?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : encodeURI('/ar/attendanceMintenance/addHrmMasterInfo?KEY='+key+'&DEPT_NO='+deptNo+'&MAS_SEND_TYPE='+sendType),
						success : function(responseText) {
							if (responseText == "Y"){
								alert("人事数据传送成功！");
								//页面重载
								//navTabSearch(document.viewPortal);
								var $form = $("#viewArHrmMasterSendList");
								var params = $("#viewArHrmMasterSendList").serializeArray();
								navTab.reload($form.attr('action')+'?pageNum=1', {data: params, navTabId:navTabId});
							}else if(responseText == "E"){
								alert("考勤机接口更新日志插入失败！");
							}else{
								alert("人事数据传送失败！");
							}
						}
					});
				}
			});
}
function CheckFormHrmMaster(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doHrmMasterExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceMintenance/viewArHrmMasterExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expHrmMaster(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewArHrmMasterSendList");
  	if(CheckFormHrmMaster($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doHrmMasterExport($form);}});
    } 
}

</script>
<div class="pageHeader" >
	<form id="viewArHrmMasterSendList" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArHrmMasterSendList" method="post">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td width="5%" style="text-align: right">法人名：</td>
				<td width="10%" style="text-align: left">${COMPANY_NAME}</td>
				<td width="5%" style="text-align: right"><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>：
				</td>	
				<td width="10%" style="text-align: left">
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
				 <td width="5%" style="text-align: right"><!--工号/姓名：-->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>：
				</td>
				<td width="5%" style="text-align: left">
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td width="5%" style="text-align: right">同步Y/N：</td>
				<td width="5%" style="text-align: left">
					<select id="seach_MAS_SEND_TYPE" name="seach_MAS_SEND_TYPE" ><!-- 未发送过 -->
						<option value="N" <c:if test="${MAS_SEND_TYPE eq 'N' }">selected</c:if>><%--未发送的人员或者有变化的人--%>
							未发送
						</option>
						<option value="Y" <c:if test="${MAS_SEND_TYPE eq 'Y' }">selected</c:if>><%--已发送的人员重新发送--%>
							已发送
						</option>
						<option value="ALL" <c:if test="${MAS_SEND_TYPE eq 'ALL' }">selected</c:if>><%--所有人（无论是否发送过）都发送--%>
							全部
						</option>
					</select>
				</td>
				<td>&nbsp;</td>
				<td style="text-align: center">
					<a class="button" onclick="makeHrmMaster()" title="传送人事数据">
					<span>传送人事数据</span></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: right">修改日期：</td>
			    <td style="text-align: left">
			    	<input type="text" id="seach_MAS_SEND_DATE" name="seach_MAS_SEND_DATE" class="date" readonly="true" value="${MAS_SEND_DATE }"/>
				</td>
				<td style="text-align: right">离职日期：</td>
			    <td style="text-align: left">
			    	<input type="text" id="seach_RESIGN_DATE" name="seach_RESIGN_DATE" class="date" readonly="true" value="${RESIGN_DATE }"/>
				</td>
				<td style="text-align: right">在职区分：</td>
				<td style="text-align: left">
					<select id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="15119" <c:if test="${EMP_OFFICE eq '15119' }">selected</c:if>>在职</option>
						<option value="15120" <c:if test="${EMP_OFFICE eq '15120' }">selected</c:if>>离职</option>
					</select>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul>
			 	<li>
			 		<div class="buttonActive"><div class="buttonContent"><button type="submit">
			 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div>
		 		</li>
		 	</ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar"><%--
			<li>
				<a class="add" onclick="makeHrmMaster()" title="<spring:message code='pa.salary.title.generateSAP'/>">
					<span> HrmMaster发送 <spring:message code="pa.salary.title.generateSAP"/></span></a>
			</li>--%>
			<li>
				<a class="delete" onclick="expHrmMaster()" title="是否导出？">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
				<th width="5%" style="text-align: center">序号</th>
				<th width="15%" style="text-align: center">法人名</th>
				<th width="8%" style="text-align: center">工号</th>
				<th width="10%" style="text-align: center">姓名</th>
				<th width="12%" style="text-align: center">部门</th>
				<th width="12%" style="text-align: center">分店名</th>
				
				<th width="6%" style="text-align: center">离职日</th>
				<th width="5%" style="text-align: center">在职区分</th>
				<th width="5%" style="text-align: center">同步区分</th>
				<th width="6%" style="text-align: center">生成日</th>
				<th width="5%" style="text-align: center">生成者</th>
				
				<th width="6%" style="text-align: center">修改日</th>
				<th width="5%" style="text-align: center">修改人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hrmMasterList}" var="master" varStatus="i">
				<tr target="sid" rel="${master.PERSON_ID}">
					<td style="text-align: center">${i.index+1}</td>
					<td style="text-align: center">${master.COMPANY_NAME }</td>
					<td style="text-align: center">${master.EMPID }</td>
					<td style="text-align: center">${master.LOCAL_NAME }</td>
					<td style="text-align: center">${master.DEPT_NAME }</td>
					<td style="text-align: center">${master.STORE_NAME }</td>
					
					<td style="text-align: center">${master.RESIGN_DATE }</td>
					<td style="text-align: center">${master.EMP_OFFICE_NAME }</td>
					<td style="text-align: center">${master.READ_FLAG }</td>
					<td style="text-align: center">${master.CREATE_DATE}</td>
					<td style="text-align: center">${master.CREATED_BY}</td>
					
					<td style="text-align: center">${master.UPDATE_DATE}</td>
					<td style="text-align: center">${master.UPDATED_BY}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArHrmMasterSendList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>