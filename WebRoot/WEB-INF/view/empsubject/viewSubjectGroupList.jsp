<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">

<form onsubmit="return navTabSearch(this);" action="/empsubject/viewSubjectGroupList" method="post" rel="pagerForm">
<div class="searchBar">
<table class="searchContent">
	<tr>
  		<td><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 -->
		</td>
		<td>
		    <!--<ait:ComboDeptByCpnyIDTag id="seach_SUBJT_GR_NM" name="seach_SUBJT_GR_NM" parentNo="198659" selected="${SUBJT_GR_NM}" cnpyID="${defaultCpny}" limit="all"/> -->
		    <select name="seach_SUBJT_GR_ID" class="input_select_short" value="" >
                    <option value = "">请选择</option>
                    <c:forEach items="${groupList}" var="GRCDResult">
                        <option value="<c:out value='${GRCDResult.SUBJT_GR_ID}'/>" <c:if test="${SUBJT_GR_ID==GRCDResult.SUBJT_GR_ID}"> selected</c:if> > 
                          <c:out value='${GRCDResult.SUBJT_GR_ID}'/>&nbsp;|&nbsp;<c:out value='${GRCDResult.SUBJT_GR_NM}'/>
                        </option>
                    </c:forEach>
            </select>
		</td>
        <td><spring:message code="empsubject.useYn"/><!-- 状态 -->
		</td>
		<td style="width:100px">
			<select name="USE_YN">
				<option value="Y" <c:if test="${USE_YN eq 'Y'}">selected</c:if>>
				<spring:message code="empsubject.useY"/><!--使用--></option>
				<option value="N" <c:if test="${USE_YN eq 'N'}">selected</c:if>>
				<spring:message code="empsubject.useN"/><!--不使用--></option>
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
	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="500" var="add_height"/>
	<c:set value="/empsubject/addSubjectGroupView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/empsubject/updateSubjectGroupView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
<table class="table" width="100%" layoutH="232">
	<thead>
		<tr>
			<th width="14%"><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --></th>
			<th width="14%"><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --></th>
			<th width="15%"><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 --></th>
			<th width="15%"><spring:message code="empsubject.regTime"/><!-- 注册时间--></th>
			<th width="15%"><spring:message code="empsubject.updateTime"/><!-- 更新时间--></th>
			<th width="14%"><spring:message code="empsubject.updateUser"/><!-- 更新人 --></th>
			<th width="13%"><spring:message code="empsubject.useYn"/><!-- 状态 --></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${subjectGroup}" var="item" varStatus="i">

			<tr target="sid" rel="SUBSD_CD=${item.SUBSD_CD}&SUBJT_GR_ID=${item.SUBJT_GR_ID}">
				<td class='td_center' >${item.SUBSD_CD}</td>
				<td class='td_center' >${item.SUBJT_GR_ID}</td>
				<td class='td_center'>${item.SUBJT_GR_NM}</td>
				<td class='td_center'>${item.RGST_DTIME}</td>
				<td class='td_center'>${item.UPDT_DTIME}</td>
				<td class='td_center'>${item.UPDT_USER}</td>
				<td class='td_center'>${item.USE_YN}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/empsubject/viewSubjectGroupList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
