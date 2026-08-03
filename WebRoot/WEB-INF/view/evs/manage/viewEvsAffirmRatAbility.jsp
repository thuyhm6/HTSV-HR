<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;width:100%;height:20px;line-height:20px;"><spring:message code="evs.viewAffirmTarget1TSTO.PINGJIADENGJIFENBU.a"/><!--评价 等级 分布--></div>
		<table class="evsList" width="100%">
			<thead>
				<tr>
					<th width="12%" rowspan="2"></th>
					<th width="8%" rowspan="2"><spring:message code="ess.viewpersonalpainfo.heji"/><!--合计--></th>
					<th width="8%" rowspan="2"><spring:message code="evs.viewAffirmTarget1TSTO.WEISHURU.a"/><!--未输入--></th>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
						<th class="td_title" style="text-align:center;" width="14%" colspan="2">${item.EVS_GRADE_NAME}</th> 
						<%-- <th width="14%" colspan="2">
							<c:choose>
								<c:when test="${item.EVS_GRADE_NAME == 'A'}">EX</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'B'}">VG</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'C'}">GD</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'D'}">NI</c:when>
								<c:when test="${item.EVS_GRADE_NAME == 'E'}">UX</c:when>
							</c:choose>
						</th> --%>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<th width="7%"><spring:message code="pa.title.pa.excel.thecountnumberofemployee"/><!--人数--></th>
							<th width="7%"><spring:message code="evs.viewEvsResult.BILI.a"/><!--比例--></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsResult.BIAOZHUNFENPEILV.a"/><!--标准分配率--></td>
				    <td style="text-align:center;">100</td>
				    <td style="text-align:center;">-</td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;"><fmt:formatNumber value="${scoreList[0][item.EVS_GRADE_ORIGINAL] * viewConfirmTargetSize * 0.01}" pattern="0.0"/></td>
				    	<td style="text-align:center;">${scoreList[0][item.EVS_GRADE_ORIGINAL]}</td>
					</c:forEach>
				</tr>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewAffirmTarget1.XIANZAIRENYUAN.a"/><!--现在人员--></td>
				    <td style="text-align:center;" id="viewAffirmTarget_cnt">${viewConfirmTargetSize}</td>
				    <td style="text-align:center;" syslong="rat" sysGrade="empty"></td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
						<td style="text-align:center;" syslong="rat" sysGrade="${item.EVS_GRADE_ORIGINAL}"></td>
						<td style="text-align:center;" syslong="cnt_${item.EVS_GRADE_ORIGINAL}"></td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<table class="evsList" width="100%">
			<thead>
				<tr>
					<th width="28%" rowspan="2"></th>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
						<th class="td_title" style="text-align:center;" width="14%" colspan="2">${item.EVS_GRADE_NAME}</th> 
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<th width="7%"><spring:message code="evs.viewEvsParamInfoList.BAOKUOFANWEIMOFENSHU.a"/><!--包括范围末分数--></th>
							<th width="7%"><spring:message code="evs.viewEvsParamInfoList.BAOKUOFANWEIKAISHIFENSHU.a"/><!--包括范围开始分数--></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="text-align:center"><spring:message code="evs.viewEvsParamPanel.KAOHEDENGJISHUXINGDINGYI.a"/><!--考核等级 属性 定义--></td>
					<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				    	<td style="text-align:center;">${item.END_SCORE}</td>
				    	<td style="text-align:center;">${item.START_SCORE}</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</div>

