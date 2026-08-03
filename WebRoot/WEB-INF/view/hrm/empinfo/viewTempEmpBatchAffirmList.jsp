<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
		<table class="table" width="120%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
		        <th width="8%">中文姓名</th>
		        <th width="10%">英文姓名</th>
		        <th width="10%">身份证号码</th>
		        <th width="8%">入社日期</th>
		        <th width="8%">生日</th>
		        <th width="5%">性别</th>
		        <th width="10%">福利地区</th>
		        <th width="15%">人员类型</th>
		        <th width="20%">部门</th>
		        <th width="6">基本工资</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">			
			<tr>
			    <input type="hidden" name="hr0517Person" value="${item.PERSON_ID}"/>
				<input type="hidden" name="hr0517DeptNo" value="${item.DEPTNO}"/>
				<td class='td_center'>
				    <a rel="batchApplyAffirm" href="/hrm/empinfo/viewTempEmpReqDetail?PERSON_ID=${item.PERSON_ID}&APPLY_NO=${item.REQ_ID}"
					   title="申请详情" target="navTab">${item.LOCAL_NAME}</a>
				</td>
				<td class='td_center'>${item.CHINESE_PINYIN}</td>
				<td class='td_center'>${item.IDCARD_NO}</td>
				<td class='td_center'>${item.JOIN_COMPANY_DATE}</td>
				<td class='td_center'>${item.DOB}</td>
				<td class='td_center'>${item.SEXCODE}</td>
				<td class='td_center'>${item.INSRAREA_NM}</td>
				<td class='td_center'>${item.EMP_TYPE_NM}</td>
				<td>${item.DEPT_NAME}</td>
				<td class='td_right'>${item.BASE_PAY}</td>
			</tr>			
			</c:forEach>			
		</tbody>
	</table>
	 
	<form id="pagerForm" method="post" action="/hrm/empinfo/viewTempEmpBatchAffirmList?BATCH_NO=${BATCH_NO}&APPLY_NO=${APPLY_NO}&navTabId=hr05171">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>

<div class="pageContent">
<div class="pageFormContent">
	<table class="table" width="100%" nowrapTD="false">
		<tbody>
		<tr>
		    <td width="20%" style="text-align:center">
		             附件
		    </td>
		    <td width="80%" class='td_type'>
			<c:forEach items="${fileList}" var="file" varStatus="i">
			    <a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
			    &nbsp;&nbsp;&nbsp;
			</c:forEach>
		    </td>
		</tr>			
		</tbody>
	</table>
</div>
</div>

<div class="pageContent" >
	<table class="user_table" width="100%" border="0">
		<tr>
			<c:if test="${affirmorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<c:if test="${affirmorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- 决裁等级 -->
				决裁等级
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁者 -->
				决裁者
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁情况 -->
				决裁情况
			</td>
			<td class="td_title" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁批注 -->
				决裁批注
			</td>
		</tr>
		<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						未决裁
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						已通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						已否决
					</c:if>
				</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmorListCnt == 0}">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
		</c:if>
		<tr>
			<c:if test="${checkorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<c:if test="${checkorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${3 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- Type -->
				Type
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Requests -->
				Requests
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Reviewed -->
				Reviewed
			</td>
		</tr>
		<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">Public</td>
				<td class="td_type" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br>
					[Request]：${checkor.CHECK_REASON}
				</td>
				<td class="td_type" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;<br>
					[Check]：${checkor.CHECK_CONTENT}
				</td>
			</tr>	
		</c:forEach>	
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_title" style="text-align: center">Public</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
			</tr>	
		</c:if>	
	</table>
</div>