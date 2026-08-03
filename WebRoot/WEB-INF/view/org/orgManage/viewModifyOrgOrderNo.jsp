<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function up(obj,currentOrder,preOrder) { 
	var preHtml = $("#orderDeptNo_" + preOrder).html();
	$("#orderDeptNo_" + preOrder).html($("#orderDeptNo_" + currentOrder).html());
	$("#orderDeptNo_" + currentOrder).html(preHtml);
} 
function down(obj,currentOrder,nextOrder) { 
	var nextHtml = $("#orderDeptNo_" + nextOrder).html();
	$("#orderDeptNo_" + nextOrder).html($("#orderDeptNo_" + currentOrder).html());
	$("#orderDeptNo_" + currentOrder).html(nextHtml);
} 
function saveOrderDeptNo(){
	var $form = $("#modifyOrgOrderNoForm");	
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
  		{okCall:function(){
		  	$.ajax({
  				type: $form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: dialogDivAjaxDone,
  				error: DWZ.ajaxError
  			});
  		}});
	return false;
}
</script>
<div class="pageContent">
		<form id="modifyOrgOrderNoForm" method="post" action="/org/orgManage/modifyOrgOrderNo" class="pageForm required-validate" >
		<div>
			<div style="display:block;height:30px;line-height:30px;">
				<table  class="user_table" width="100%">
					<tr>
						<td width="40%" class="td_title"><spring:message code="org.title.parentDept" /><!-- 上级部门 -->：</td>
						<td width="60%" class="td_type">
							${modifyOrgOrderNoList[0].PARENT_DEPT_NAME}
							<input type="hidden" name="RESUME_NO" value="${RESUME_NO}"/>
							<input type="hidden" name="PARENT_DEPT_NO" value="${modifyOrgOrderNoList[0].PARENT_DEPT_NO}"/>
						</td>
					</tr>
				</table>
			</div>
			<div style="display:block;font:bold 12px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Total:${fn:length(modifyOrgOrderNoList)}</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th style="width:60px;">No.</th>
					<th style="width:90px;"><spring:message code="org.title.dept" /><!-- 部门 --></th>
					<th style="width:60px;"><spring:message code="org.title.NO" /><!-- 序号 --></th>
					<th style="width:60px;">up</th>
					<th style="width:60px;">down</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${modifyOrgOrderNoList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>${i.count}</td>
						<td style="text-align:left" id="orderDeptNo_${i.count}">
							${item.DEPTNAME}
							<input type="hidden" name="DEPTNO" value="${item.DEPTNO}">
						</td>
						<td class='td_center'>${i.count}</td>
						<td class='td_center'>
							<c:if test="${not i.first}">
								<a href="#" onclick="up(this,${i.count},${i.count - 1})">
									<img src="/resources/images/button/up.gif" style="cursor: hand" />
								</a>
							</c:if>
						</td>
						<td class='td_center'>
							<c:if test="${not i.last}">
								<a href="#" onclick="down(this,${i.count},${i.count + 1})"> 
									<img src="/resources/images/button/down.gif" style="cursor: hand" />
								</a>
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
							<div class="buttonContent"><!--保存-->
								<button type="button" onclick="saveOrderDeptNo();">
									<spring:message code="org.title.SAVE" /><!-- 保存 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" class="close">
									<spring:message code="org.title.CLOSE" /><!-- 关闭 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
		</div>
		</form>
</div>