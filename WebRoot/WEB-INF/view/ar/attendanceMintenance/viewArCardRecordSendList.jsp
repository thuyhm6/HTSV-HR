<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function readCardRecord(navTabId) {
	var $form = $("#viewArCardRecordSendList");
	
	var key = $form.find("#seach_KEY").val();
	var deptNo = $form.find("#seach_DEPT_NO").val();
	var rDate = $form.find("#seach_R_DATE").val();
	var dateType = $form.find("#seach_DATE_TYPE").val();
	var selDate = $form.find("#seach_SELECT_DATE").val();
	var doorType = $form.find("#seach_DOOR_TYPE").val();
	var arMacNo = $form.find("#seach_AR_MAC_NO").val();
	
	alertMsg.confirm("确认读取刷卡信息?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : encodeURI('/ar/attendanceMintenance/addCardRecordInfo?KEY='+key+'&DEPT_NO='+deptNo+'&R_DATE='+rDate+'&DATE_TYPE='+dateType
							+'&SELECT_DATE='+selDate+'&DOOR_TYPE='+doorType+'&AR_MAC_NO='+arMacNo),
						success : function(responseText) {
							if (responseText == "Y"){
								alert("刷卡数据读取成功！");
								//页面重载
								//navTabSearch(document.viewPortal);
								var $form = $("#viewArCardRecordSendList");
								var params = $("#viewArCardRecordSendList").serializeArray();
								navTab.reload($form.attr('action')+'?pageNum=1', {data: params, navTabId:navTabId});
							}else if(responseText == "E"){
								alert("读取刷卡数据后插入接口日志失败！");
							}else{
								alert("刷卡数据读取失败！");
							}
						}
					});
				}
			});
}
function CheckFormCardRecord(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doCardRecordExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceMintenance/viewArCardRecordExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expCardRecord(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewArCardRecordSendList");
  	if(CheckFormCardRecord($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doCardRecordExport($form);}});
    } 
}

</script>
<div class="pageHeader" >
	<form id="viewArCardRecordSendList" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArCardRecordSendList" method="post">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td style="text-align: right">法人名：</td>
				<td>${COMPANY_NAME}</td>
				 <td style="text-align: right"><!--工号/姓名：-->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>：
				</td>
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="text-align: right"><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>：
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
				<td style="text-align: right">刷卡日期：</td>
			    <td>
			    	<input type="text" id="seach_R_DATE" name="seach_R_DATE" class="date" readonly="true" value="${R_DATE }"/>
				</td>
				<td style="text-align: center">
					<select id="seach_DATE_TYPE" name="seach_DATE_TYPE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="SEND_DATE" <c:if test="${DATE_TYPE eq 'SEND_DATE' }">selected</c:if>>传送日</option>
						<option value="CREATE_DATE" <c:if test="${DATE_TYPE eq 'CREATE_DATE' }">selected</c:if>>生成日</option>
						<option value="UPDATE_DATE" <c:if test="${DATE_TYPE eq 'UPDATE_DATE' }">selected</c:if>>修改日</option>
					</select>
				</td>
			    <td>
			    	<input type="text" id="seach_SELECT_DATE" name="seach_SELECT_DATE" class="date" readonly="true" value="${SELECT_DATE }"/>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>			
				<td style="text-align: right">上下班区分：</td>
				<td>
					<select id="seach_DOOR_TYPE" name="seach_DOOR_TYPE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="IN" <c:if test="${DOOR_TYPE eq 'IN' }">selected</c:if>>进门</option>
						<option value="OUT" <c:if test="${DOOR_TYPE eq 'OUT' }">selected</c:if>>出门</option>
						<option value="EXCEP" <c:if test="${DOOR_TYPE eq 'PASS' }">selected</c:if>>异常</option>
					</select>
				</td>
				<td style="text-align: right">考勤机号：</td>
				<td>
					<select name="seach_AR_MAC_NO" id="seach_AR_MAC_NO">
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
			          	<c:forEach items="${cardMacList}" var="mac">
			          		<option value="${mac.INTERFACE_RECORD_ID}" <c:if test="${AR_MAC_NO eq mac.INTERFACE_RECORD_ID}">selected</c:if>> 
			          			${mac.INTERFACE_RECORD_ID}
			          		</option>
			          	</c:forEach>
			        </select>
				</td>
				<td style="text-align: right">读取类型：</td>
				<td>
					<select id="seach_READ_TYPE" name="seach_READ_TYPE" >
						<option value="N" <c:if test="${READ_TYPE eq 'N' }">selected</c:if>>
							未读取
						</option>
						<option value="Y" <c:if test="${READ_TYPE eq 'Y' }">selected</c:if>>
							已读取
						</option>
						<option value="ALL" <c:if test="${READ_TYPE eq 'ALL' }">selected</c:if>>
							全部
						</option>
					</select>
				</td>
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
		<ul class="toolBar">
			<li>
				<a class="add" onclick="readCardRecord()" title="读取刷卡数据？">
					<span>读取刷卡数据</span></a>
			</li>
			<li>
				<a class="delete" onclick="expCardRecord()" title="确认导出？">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="120%" layoutH="205">
		<thead>
			<tr>
				<th width="3%" style="text-align: center">序号</th>
				
				<th width="15%" style="text-align: center">法人名</th>
				<th width="5%" style="text-align: center">工号</th>
				<th width="5%" style="text-align: center">姓名</th>
				<th width="10%" style="text-align: center">部门</th>
				<th width="10%" style="text-align: center">分店名</th>
				
				<th width="5%" style="text-align: center">上/下班</th>
				<th width="5%" style="text-align: center">刷卡日期</th>
				<th width="5%" style="text-align: center">刷卡时间</th>
				<th width="5%" style="text-align: center">考勤机号</th>
				<th width="5%" style="text-align: center">考勤卡号</th>
				
				<th width="5%" style="text-align: center">传送状态</th>
				<th width="5%" style="text-align: center">传送日</th>
				<th width="5%" style="text-align: center">传送者</th>
				<th width="5%" style="text-align: center">修改日</th>
				<th width="5%" style="text-align: center">修改者</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cardRecordList}" var="card" varStatus="i">
				<tr target="sid" rel="${card.AR_MAC_DATA_NO}">
					<td style="text-align: center">${i.index+1}</td>
					
					<td style="text-align: center">${card.COMPANY_NAME }</td>
					<td style="text-align: center">${card.EMPID }</td>
					<td style="text-align: center">${card.LOCAL_NAME }</td>
					<td style="text-align: center">${card.DEPT_NAME }</td>
					<td style="text-align: center">${card.STORE_NAME }</td>
					
					<td style="text-align: center">
						<c:if test="${card.DOOR_TYPE eq 'IN'}">
							<b><font color="green">进门</font></b>
						</c:if>
						<c:if test="${card.DOOR_TYPE eq 'OUT'}">
							<b><font color="blue">出门</font></b>
						</c:if>
						<c:if test="${card.DOOR_TYPE ne 'IN' && card.DOOR_TYPE ne 'OUT'}">
							<b><font color="red">异常</font></b>
						</c:if>
					</td>
					<td style="text-align: center">${card.R_DATE }</td>
					<td style="text-align: center">${card.R_TIME }</td>
					<td style="text-align: center">${card.INTERFACE_RECORD_ID }</td>
					<td style="text-align: center">${card.CARD_NO }</td>
					
					<td style="text-align: center">
						<c:if test="${card.SEND_STATUS eq 'S'}">
							<b><font color="green">已读取</font></b>
						</c:if>
						<c:if test="${card.SEND_STATUS ne 'S'}">
							<b><font color="red">未读取</font></b>
						</c:if>
					</td>
					<td style="text-align: center">${card.SEND_DATE }</td>
					<td style="text-align: center">${card.SEND_BY }</td>
					<td style="text-align: center">${card.UPDATE_DATE }</td>
					<td style="text-align: center">${card.UPDATED_BY }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArCardRecordSendList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>