<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function checkApply(form){
	var essCheckNo = $('#ESS_CHECK_NO').attr("value");
	var checkContent = $('#CHECK_CONTENT').attr("value");
	if(essCheckNo == ''){
		//Check信息出错，不能进行Check操作，请联系管理员!
		alertMsg.error("Check信息出错，不能进行Check操作，请联系管理员!");
		return false;
	}
	if(checkContent == ''){
		//Check内容不能为空，请填写Check内容!
		alertMsg.error("Check内容不能为空，请填写Check内容!");
		return false;
	}
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: navTabAjaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
		
<div class="pageContent">
	<div style="overflow-y:auto; height:150px;">
		<table width="100%" class="table" layoutH="300">
			<thead>
				<tr>	
					<th width="3%" style="text-align:center">序号</th>
					<th width="10%" style="text-align:center">社号/姓名</th>
					<th width="20%" style="text-align:center">部门</th>
					<th width="8%" style="text-align:center">发放月份</th>
					<th width="8%" style="text-align:center">补发月份</th>
					
					<th width="6%" style="text-align:center">补发类别</th>
					<!-- <th width="12%" style="text-align:center">补发项目</th>
					<th width="8%" style="text-align:center">金额</th> -->
					<th width="20%" style="text-align:center">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paForLeftDetailList}" var="detail" varStatus="i">			
					<tr>
						<td class='td_center'>${i.index+1 }</td>
						<td class='td_center'>(${detail.EMPID})${detail.LOCAL_NAME}</td>
						<td class='td_left'>${detail.DEPARTMENT }</td>
						<td class='td_center'>${detail.PA_MONTH }</td>
						<td class='td_center'>${detail.PA_MONTH_FOR }</td>
						
						<td class='td_center'>
							<c:if test="${detail.ITEM_TYPE eq 'PA' }">薪资</c:if>
							<c:if test="${detail.ITEM_TYPE eq 'IS' }">保险</c:if>
						</td>
						<%-- <td class='td_center'>${detail.ITEM_NAME }</td>
						<td class='td_right'>${detail.ITEM_DATA }</td> --%>
						<td class='td_left'>${detail.REMARK }</td>
					</tr>			
				</c:forEach>			
			</tbody>
		</table>
	</div>
</div>

<div class="pageContent" >
	<div style="overflow-y:auto; height:285px;">
		<table class="user_table" width="100%">
			<tr><td class="td_title" style="text-align: left" colspan="6"><font><b>&nbsp;申请信息&nbsp;</b></font></td></tr>
			<tr>
				<td class="td_title" style="text-align: center">申请人</td>
				<td class="td_type"  style="text-align: center">(${paForLeftMap.EMPID})${paForLeftMap.LOCAL_NAME}</td>
				<td class="td_title" style="text-align: center">申请时间</td>
				<td class="td_type"  style="text-align: center">${paForLeftMap.CREATE_DATE }</td>
				<td class="td_title" style="text-align: center">申请内容</td>
				<td class="td_type">${paForLeftMap.APPLY_CONTENT }</td>
			</tr>
			<tr>
				<c:if test="${affirmorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<c:if test="${affirmorListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
					决裁等级
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
					决裁者
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
					决裁情况
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
					决裁批注
				</td>
			</tr>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
					<td class="td_type" width="15%" style="text-align: center">
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
					<td class="td_type" width="15%" style="text-align: center">	<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">${affirmor.AFFIRM_DATE}</c:if></td>
					<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			
			<tr>
				<td class="td_title" colspan="6"><br/></td>
			</tr>
			<tr>
				<c:if test="${checkorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkorListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${3 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" width="30%" style="text-align: center" colspan="2"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" width="45%" style="text-align: center" colspan="2"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
						<c:if test="${checkor.AFFIRM_FLAG == 0}">
							未决裁
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 1}">
							已通过
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 2}">
							已否决
						</c:if>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">
							未Check
						</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">
							已Check
						</c:if>
					</td>
				</tr>	
				<tr>
					<td class="td_type" width="30%" colspan="2">
						<textarea name="affirmRemark" cols="75" rows="2" disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
					</td>
					<td class="td_type" width="45%" colspan="2">
						<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
						<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }"/>
						<textarea name="checkRemark" cols="75" rows="2" disabled="disabled">[Check]：${checkor.CHECK_CONTENT}</textarea>
					</td>
				</tr>			
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
				<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<spring:message code="public.title.close"/><!-- 关闭 -->
						</button>
					</div>
				</div>
			</li>
			<li>
				&nbsp;&nbsp;&nbsp;&nbsp;
			</li>
		</ul>
	</div>
</div>