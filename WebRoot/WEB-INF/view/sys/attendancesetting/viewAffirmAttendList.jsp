<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script> 
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/sys/attendancesetting/viewAffirmAttendList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value="">
						</option>
						<option value="1" <c:if test="${TYPE eq 1}">selected</c:if>>
							<spring:message code="ar.attenditem.title.mingxixiangmu"/>
						</option>
						<option value="2" <c:if test="${TYPE eq 2}">selected</c:if>>
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/>
						</option>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
					<td>
<!-- 						<spring:message code="ess.trans.title.affirmStatus"/> -->审批状态
						<select id="seach_KEYVALUE" name="seach_KEYVALUE" value="${KEYVALUE }"> 
						<option value="">
						</option>
						<option value="0" <c:if test="${KEYVALUE eq '0'}">selected</c:if>>
<!-- 							<spring:message code="ess.trans.title.notAffirmed"/> -->未审批
						</option>
						<option value="1" <c:if test="${KEYVALUE eq '1'}">selected</c:if>>
<!-- 							<spring:message code="ess.trans.title.affirmed"/> -->已通过
						</option>
						<option value="2" <c:if test="${KEYVALUE eq '2'}">selected</c:if>>
							<spring:message code="hr.viewTransactionTransViewList.title.FLAGVOTEDOWN"/>
						</option>
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
									<spring:message code="public.title.search"/><!--检索-->
								</button>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	
<div class="pageContent">
	<table class="table" width="100%" layoutH="173">
		<thead>
			<tr>
			    <th width="5%"><spring:message code="sys.affirm.indexNum" /><!--序号--></th>
				<th width="8%"> 
					申请类型
				</th>
				<th width="7%"> 
					申请法人
				</th>
			    <th width="7%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th width="13%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="20%">
					<spring:message code="pa.salarycode.affirm.reason"/><!--申请事由-->
				</th>
				<th width="15%">
					<spring:message code="hr.viewSuggestion.title.Suggestion"/><!--决裁意见-->
				</th>
				<th width="15%"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请时间-->
					</th>
				<th width="15%" style="text-align: center">
					<spring:message code="ar.viewcycle.title.zhuangtai"/>
				</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${attendList}" var="item" varStatus="i">
				<tr target="AFFIRM_ITEM_NO" rel="${item.AFFIRM_ITEM_NO}">
				    <td>${i.index + 1}&nbsp;</td>
				    <td >
						${item.ACTIVITY_TYPE eq '1' ? '新项目申请' : '申请启用'}
					</td>
					 <td >
						${item.AFFIRM_CPNY_ID}
					</td>
				    <td >
						${item.PROJECT_TYPE eq 1 ? '明细项目' : '汇总项目'}
					</td>
					
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						${item.AFFIRM_REASON}
					</td>
					<td>
					   ${item.AFFIRM_DESCR }
					</td>
					<td>
						<fmt:formatDate value="${item.CREATE_DATE }" pattern="yyyy-MM-dd HH:mm:ss" /> 
					</td>
					<td style="text-align: center">
						        <dt style="padding: 1px;">
									<c:if test="${item.ACTIVITY==0}" >
									   <a href="/sys/attendancesetting/viewAffirmArItemInfo?ITEM_NO=${item.AFFIRM_ITEM_NO}&CPNY_ID=${item.AFFIRM_CPNY_ID}"
							                target="navTab" 
								       rel="ar_affirm"><span style="color:blue;">&nbsp;审批</a></span>
									</c:if>	
								</dt>
						        <dt style="padding: 1px;">
									<c:if test="${item.ACTIVITY==1}" >
									    <span style="color:green;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
									</c:if>	
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${item.ACTIVITY==2}" >
									    <span style="color:red;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:red;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span>
									</c:if>								
								</dt>	
                    </td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
</div>
	<c:set value="/sys/attendancesetting/viewAffirmAttendList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>