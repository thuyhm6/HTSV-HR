<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function CheckFormEatMealCount(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doEatMealCountExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceSettings/viewArEatCountInfoListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expEatMealCount(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewArEatCountInfoList");
  	if(CheckFormEatMealCount($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doEatMealCountExport($form);}});
    } 
}

</script>
<div class="pageHeader" >
	<form id="viewArEatCountInfoList" onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewArEatCountInfoList" method="post">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent">
			<tr>
				<td style="text-align: right"><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>：
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
				<td style="text-align: right"><!--工号/姓名：-->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>：
				</td>
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				 <td><!-- 开始日期 -->
                    <spring:message code="public.title.startDate"/>:
                </td>			
			    <td>
			        <input type="text" name="seach_BEGIN_DATE_STR" class="date" format="yyyy-MM-dd" readonly="true" value="${BEGIN_DATE_STR}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td><!-- 结束日期 -->
					<spring:message code="public.title.endDate"/>:
                </td>                			     
				<td>
				    <input type="text" name="seach_END_DATE_STR" class="date" format="yyyy-MM-dd" readonly="true" value="${END_DATE_STR}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td><!-- 动态组 -->
					<spring:message code="ar.addempshift.title.dynamicgroup"/>:
                </td>                			     
				<td>
				    <select name="seach_DYNAMIC_GROUP_NO" id="seach_DYNAMIC_GROUP_NO">
						<option value=""><!-- 全部 -->
							<spring:message code="ar.viewarcardrecord.title.quanbu"/>
						</option>
						<c:forEach items="${dynamicGroupList}" var="groupList">
							<option value="${groupList.GROUP_NO}" <c:if test="${groupList.GROUP_NO eq DYNAMIC_GROUP_NO}">selected</c:if>>${groupList.GROUP_NAME}</option>
						</c:forEach>
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
				<a class="add" href="/ar/attendanceSettings/viewArEatCountBatchPersonList?seach_DATA_FLAG=N" 
					target="navTab"><span>添加</span></a>
			</li>
			<li>
				<a class="delete" href="/ar/attendanceSettings/deleteArEatCountInfo?seach_AR_EAT_COUNT_NO={meal_no}" 
					target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a>
			</li>
			<li>
				<a class="edit" href="/ar/attendanceSettings/updateArEatCountView?seach_AR_EAT_COUNT_NO={meal_no}" 
					target="dialog"><span>修改</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="icon" onclick="expEatMealCount()" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="98%" layoutH="158">
		<thead>
			<tr>
				<th width="5%" style="text-align: center">序号</th>
				<th width="10%" style="text-align: center">工号</th>
				<th width="10%" style="text-align: center">姓名</th>
				<th width="10%" style="text-align: center">员工状态</th>
				<th width="15%" style="text-align: center">日期</th>
				<th width="10%" style="text-align: center">可刷卡次数</th>
				<th width="10%" style="text-align: center">是否锁定</th>
				<th width="15%" style="text-align: center">部门</th>
				<th width="15%" style="text-align: center">分店名</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${eatMealCountList}" var="meal" varStatus="i">
				<tr target="meal_no" rel="${meal.AR_EAT_COUNT_NO}">
					<td style="text-align: center">${i.index+1}</td>
					<td style="text-align: center">${meal.EMPID }</td>
					<td style="text-align: center">${meal.LOCAL_NAME }</td>
					<td style="text-align: center">${meal.EMP_OFFICE }</td>
					<td style="text-align: center">${meal.AR_DATE_STR }</td>
					
					<td style="text-align: center">${meal.MEAL_NUM }</td>
					<td style="text-align: center">
						<c:if test="${meal.LOCK_FLAG eq 'Y'}">
							<font color="red">已锁定</font>
						</c:if>
						<c:if test="${meal.LOCK_FLAG eq 'N'}">
							<font color="blue">未锁定</font>
						</c:if>
					</td>
					<td style="text-align: center">${meal.DEPARTMENT }</td>
					<td style="text-align: center">${meal.DISTINGUISH_NAME }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewArEatCountInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>