<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoView/viewPersonalAttendanceBack" method="post" 
		name="searchArDetailEssForm" id="searchArDetailEssForm" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent" border="0" width="100%">
				<tr>
					<td><!-- 开始日期 -->
						<spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>:
					</td>
					<td>
						<input type="text" name="seach_sDate" class="date" value="${sDate}" format="yyyy/MM/dd" yearstart="-20" yearend="20" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td><!-- 结束日期 -->
						<spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>:
					</td>
					<td style="padding-left: 1px">
						<input type="text" name="seach_eDate" class="date" value="${eDate}"format="yyyy/MM/dd"  yearstart="-20" yearend="20" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<!--  <td>
						<spring:message code="ar.viewardetail.title.kaoqinqufen"/>:
					</td> -->
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><!-- 查询 -->
									<spring:message code="button.search"/>
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
 <table class="table" width="100%" layoutH="208">
		<thead>
			<tr>
						<th width="10" align="center" >
							<spring:message code="display.mutual.no"/>
						</th>
						<th width="7%">
							<!-- 日期 --><spring:message code="ar.viewardetail.title.date"/>
						</th width="15%">
				        <th width="8%">
							<!-- 工号  --><spring:message code="public.title.empId"/>
						</th>	
					    <th width="5%">
							<!-- 姓名 --><spring:message code="public.title.name"/>
						</th>	
						<th width="15%">
							<!-- 部门 --><spring:message code="public.title.deptName"/>
						</th>
						<th width="5%">
							<!-- 班次 --><spring:message code="ar.viewCompanyCalendar.title.banci"/>
						</th>
						<th width="15%">
							<!-- 状态 --><spring:message code="ar.viewcycle.title.zhuangtai"/>
						</th>
						<th width="28%">
							<!-- 时间段 --><spring:message code="ar.viewardetail.title.dateduan"/>
						</th>
						<th width="3%">
							<!-- 长度 --><spring:message code="ess.viewApply.title.length"/>
						</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${PersonalAttendanceList}" var="list" varStatus="i">
				<tr>
						<td>
							${i.index+1}
						</td>
						<td  >
							<input type="hidden" id="AR_DATE_STR_${i.index}" name="AR_DATE_STR_${i.index}" value="${list.AR_DATE_STR}"/>
							${list.AR_DATE_STR}
						</td>
			            <td>
							<input type="hidden" id="PERSON_ID_${i.index}" name="PERSON_ID_${i.index}" value="${list.PERSON_ID}"/>
							   ${list.EMPID} 
						</td>
					    <td>
						   <input type="hidden" id="EMP_ID_${i.index}" name="EMP_ID_${i.index}" value="${list.EMPID}"/>
						    ${list.LOCAL_NAME}
				        </td>	
						<td>
							${list.DEPTNAME}
						</td>
						<td>
							${list.SHIFT_NAME}
						</td>
						<td> 
						<!-- <select name="ITEM_NO_${i.index}" id="ITEM_NO_${i.index}"  onchange="javascript:locked('${i.index}');">
								<c:forEach items="${getItemList}" var="item">
									<option value="${item.ITEM_NO}" 
										<c:if test="${item.ITEM_NO eq list.ITEM_NO}">selected</c:if>
										>
								       ${item.ITEM_NAME}
									</option>
								</c:forEach>
				    		</select> -->
				    		 ${list.ITEM_NAME}	
						</td>
						<td>
							${list.FROMTIME}~~
							${list.TOTIME}
						</td>
						<td>
							${list.QUANTITY}
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ess/infoView/viewPersonalAttendanceBack" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>