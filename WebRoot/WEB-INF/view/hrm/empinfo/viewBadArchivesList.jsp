<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function deleteBadAr(){
	alertMsg.confirm("确定要删除么?", {
		okCall : function() {
			$('#deleteBad').click();
		}
	});
	}
</script>
<div class="pageHeader">
	<form action="/hrm/empinfo/viewBadArchivesList" method="post" rel="pagerForm" id="postName"
	 		onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>：</td>
					<td>						
						<ait:deptList name="DEPTNO" limit="hr" id="viewBadAr"/>
						<ait:deptTreeIcon name="DEPTNO" limit="hr" id="viewBadAr" selected="${DEPTNO}"/>
					</td>
					<td>社号/姓名：</td>
					<td>
						<input type="text" name="EMPID" value="${EMPID}"/>
					</td>
					<td>惩罚类型：</td>
					<td>	
						<select name="ARCHIVES_TYPE">
							<option value="">
								<!-- 请选择 --><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							</option>
							<c:forEach items="${codeList}" var="recsource">
								<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO eq ARCHIVES_TYPE}">selected</c:if>>${recsource.CODENAME}
							</c:forEach>
						</select>
					</td>
					</tr>
					<tr>
					<td>开始时间：</td>
					<td><input type="text" name="START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td>结束时间：</td>
					<td><input type="text" name="END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--检索--><spring:message code="public.title.search"/>
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
				<li id="addLi">
					<a class="add" href="/hrm/empinfo/viewAddBadArchivesInfo"
							target="dialog" mask="true" rel="viewPaData" width="600" height="500">
						<span>添加</span>
					</a>
				</li>
			</c:if>
			<c:if test="${toolbarInfo.UPDATER == '1'}">
				<li id="editLi">
					<a class="edit"
						href="/hrm/empinfo/viewUpdateBadArchivesInfo?ARCH_ID={ARCH_ID}"
							target="dialog"	mask="true"	width="600"	height="500"rel="${edit_tab}"><span>修改</span>
					</a>
				</li>
			</c:if>
			<c:if test="${toolbarInfo.DELETER == '1'}">
				<li id="deleteLi">
					<a class="delete" onClick="deleteBadAr()" href="#">
						<span>删除</span>
					</a>
				</li>
				<a id="deleteBad" href="/hrm/empinfo/deleteBadArchivesInfo?ARCH_ID={ARCH_ID}"
							target="ajaxTodo" mask="true" width="500" height="400"></a>
			</c:if>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th width="6%"><%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="15%"><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></th>
				<th width="11%">社号</th>
				<th width="11%">姓名</th>
				<th width="11%">发生日期</th>
				<th width="11%">奖罚类型</th>
				<th width="12%">内容描述</th>
				<th width="11%"><!--附件查看-->
						附件查看
			    </th>
				<th width="12%">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${archiveList}" var="arch" varStatus="i">
				<tr target="ARCH_ID" rel="${arch.ID}">
					<td>${i.count}</td>
					<td>${arch.DEPTNAME}</td>
					<td>${arch.EMPID}</td>
					<td>${arch.LOCAL_NAME}</td>
					<td>${arch.HAPPEN_DATE}</td>
					<td>${arch.ARCHIVES_NAME}</td>
					<td>${arch.DETAIL_DESCRIPT}</td>
					<td style="text-align: center">
							<c:forEach items="${arch.fileList}" var="file" varStatus="j">	
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME }</a></div>
							</c:forEach>
					</td>
					<td>${arch.REMARK}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/hrm/empinfo/viewBadArchivesList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>