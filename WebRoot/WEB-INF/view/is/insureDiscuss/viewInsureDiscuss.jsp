<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link href="/resources/css/default.css" rel="stylesheet" type="text/css">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div>


<form name="searchForm" id="searchForm" method="post" action="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			
				
			</tr>
			<tr>
				<td  width="11">
					&nbsp;
				</td>
				<td valign="TOP" align="CENTER">
					<br>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
							<tr>
			    				<td class="title1">
			    				<!-- 查询条件 -->
								<spring:message code="display.mutual.search_criteria"></spring:message>
								</td>
			  				</tr>
				  			<tr align="center">
				  				<td>
					  				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1" class="dr_d">
						  				<tr>
											<td height="30" width="20%" align="center" class="info_title_01">
												<!-- 保险年月 --><spring:message  code="display.emp.ben.or.benhs48"/>
											</td>
											<td height="30" width="30%" align="center"  class="info_content_01_o" colspan="3">
											<!-- 
											<input type="text" name="seach_InsureDiscuss_DATE" class="date" readonly="true" value="${ENDp_CONTRACT_DATE }"/>
											 -->
											</td>
						  				</tr>
					  				</table>
				  				</td>
							</tr>
						</table>
						<br>
						<br>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
							<tr>
								<td align="left" class="title1" colspan="10">
									<!-- 社会保险汇缴 --><spring:message  code="display.emp.ben.or.benhs65"/>
								</td>
							</tr>
							<tr></tr>
							<tr>
								<td class="info_title_000" colspan="10">
									<!-- 汇总信息 --><spring:message  code="display.emp.ben.or.benhs66"/>：
								</td>
							</tr>
						</table>
						 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
						 	<tr>
								<td class="info_title_01" rowspan="2">
									区分
								</td>
								<td class="info_title_01" colspan="3">
									 前月 
								</td>
								<td class="info_title_01" colspan="3">
									本月 
								</td>
			  				</tr>
			  				<tr>
								<td class="info_title_01">
									在保 
								</td>
								<td class="info_title_01">
									 参保
								</td>
								<td class="info_title_01">
									停保 
								</td>
								<td class="info_title_01">
									 在保 
								</td>
								<td class="info_title_01">
									参保 
								</td>
								<td class="info_title_01">
									停保 
								</td>
			  				</tr>
			  				<c:set value="0" var="v"/>
							<c:set value="0" var="v2"/>
							<c:set value="0" var="v3"/>
							<c:set value="0" var="v4"/>
							<c:set value="0" var="v5"/>
							<c:set value="0" var="v6"/>	
			  				<c:forEach items="${changeList}" var="l">
				  				<tr>
									<td class="info_content_01">社会保险</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.LAST_LASTCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.LAST_LASTCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.LAST_JOINCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.LAST_JOINCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.LAST_STOPCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.LAST_STOPCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.CURR_LASTCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.CURR_LASTCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.CURR_JOINCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.CURR_JOINCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="info_content_01">
										<c:choose>
											<c:when test="${empty l.CURR_STOPCOUNT}">&nbsp;</c:when>
											<c:otherwise>
												<fmt:formatNumber type="number" value="${l.CURR_STOPCOUNT}" maxFractionDigits="2"/>
											</c:otherwise>
										</c:choose>
									</td>
				  				</tr>
					  				<c:set value="${l.LAST_LASTCOUNT + v}" var="v"></c:set>
							    	<c:set value="${l.LAST_JOINCOUNT + v2}" var="v2"></c:set>
							    	<c:set value="${l.LAST_STOPCOUNT + v3}" var="v3"></c:set>
							    	<c:set value="${l.CURR_LASTCOUNT + v4}" var="v4"></c:set>
							    	<c:set value="${l.CURR_JOINCOUNT + v5}" var="v5"></c:set>
						    		<c:set value="${l.CURR_STOPCOUNT + v6}" var="v6"></c:set>
			  				</c:forEach>
				  				<tr>
							      <td class="info_content_01">合计 </td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v}" maxFractionDigits="2"/></td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v2}" maxFractionDigits="2"/></td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v3}" maxFractionDigits="2"/></td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v4}" maxFractionDigits="2"/></td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v5}" maxFractionDigits="2"/></td>
							      <td class="info_content_01"><fmt:formatNumber type="number" value="${v6}" maxFractionDigits="2"/></td>
							    </tr>
						 </table>
						 <br>
						 <br>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
						<tr>
							<td class="info_title_000" colspan="15">
								缴纳信息 ：
							</td>
						</tr>
						<tr>
							<td class="info_title_01" rowspan="2">
								区分 
							</td>
							<td class="info_title_01" rowspan="2">
								险种 
							</td>
							<td class="info_title_01" colspan="2">
								本月应缴 
							</td>
							<td class="info_title_01" colspan="2">
							本月补缴 
							</td>
							<td class="info_title_01" colspan="2">
							利息
							</td>
							<td class="info_title_01" colspan="2">
								滞纳金
							</td>
							<td class="info_title_01" colspan="2">
							 返还 
							</td>
							<td class="info_title_01" rowspan="2">
								 合计 
							</td>
						</tr>
						<tr bgcolor="#F5F5F5">
							<td class="info_title_01">
								人数
							</td>
							<td class="info_title_01">
								金额
							</td>
							<td class="info_title_01">
								 人数 
							</td>
							<td class="info_title_01">
								金额 
							</td>
							<td class="info_title_01">
								人数
							</td>
							<td class="info_title_01">
								金额 
							</td>
							<td class="info_title_01">
								人数 
							</td>
							<td class="info_title_01">
								金额 
							</td>
							<td class="info_title_01">
								人数
							</td>
							<td class="info_title_01">
								金额 
							</td>
						</tr>
						<c:if test="${not empty payInfoList}">
						<c:set value="0" var="s"/>
						<c:set value="0" var="s2"/>
						<c:set value="0" var="s3"/>
						<c:set value="0" var="s4"/>
						<c:set value="0" var="s5"/>
						<c:set value="0" var="s6"/>						
						<c:forEach items="${payInfoList}" var="pl" varStatus="i">
						<tr>
						  <c:if test="${i.first}">
					      	<td class="info_content_01" rowspan="8">公司 </td>
					      </c:if>
					      <td class="info_content_01">${pl.INSURANCE_CODE_NAME}</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_PAY_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_PAY_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_MEND_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_MEND_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_INTEREST_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_INTEREST_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_OVERDUE_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_OVERDUE_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_BACK_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_BACK_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.COR_TOTAL}" maxFractionDigits="2"/></td>
					    </tr>
					    	<c:set value="${pl.COR_PAY_VALUE + s}" var="s"></c:set>
					    	<c:set value="${pl.COR_MEND_VALUE + s2}" var="s2"></c:set>
					    	<c:set value="${pl.COR_INTEREST_VALUE + s3}" var="s3"></c:set>
					    	<c:set value="${pl.COR_OVERDUE_VALUE + s4}" var="s4"></c:set>
					    	<c:set value="${pl.COR_BACK_VALUE + s5}" var="s5"></c:set>
					    	<c:set value="${pl.COR_TOTAL + s6}" var="s6"></c:set>
					    </c:forEach>
					    <tr>
					      <td class="info_content_01"> 调整金额 </td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01">&nbsp;</td>
					      <c:if test="${empty payInfoList}">
					      	<td class="info_content_01">　</td>
					      </c:if>
					      <td class="info_content_01">${adjustValue}</td>
					    </tr>
					    <tr>
					      <td class="info_content_01">合计 </td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s2}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s3}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s4}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s5}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">
					      	<fmt:formatNumber type="number" value="${s6 + adjustValue}" maxFractionDigits="2"/>&nbsp;
					      </td>
					      <c:if test="${empty payInfoList}">
					      	<td class="info_content_01">&nbsp;</td>
					      </c:if>
					    </tr>
					    <c:set value="0" var="p"/>
						<c:set value="0" var="p2"/>
						<c:set value="0" var="p3"/>
						<c:set value="0" var="p4"/>
						<c:set value="0" var="p5"/>
						<c:set value="0" var="p6"/>
					    <c:forEach items="${payInfoList}" var="pl" varStatus="i">
						<tr>
						  <c:if test="${i.first}">
					      	<td class="info_content_01" rowspan="7"> 个人 </td>
					      </c:if>
					      <td class="info_content_01">${pl.INSURANCE_CODE_NAME}</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_PAY_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_PAY_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_MEND_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_MEND_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_INTEREST_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_INTEREST_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_OVERDUE_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_OVERDUE_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_BACK_COUNT}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_BACK_VALUE}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${pl.PER_TOTAL}" maxFractionDigits="2"/></td>
					    </tr>
					    	<c:set value="${pl.PER_PAY_VALUE + p}" var="p"></c:set>
					    	<c:set value="${pl.PER_MEND_VALUE + p2}" var="p2"></c:set>
					    	<c:set value="${pl.PER_INTEREST_VALUE + p3}" var="p3"></c:set>
					    	<c:set value="${pl.PER_OVERDUE_VALUE + p4}" var="p4"></c:set>
					    	<c:set value="${pl.PER_BACK_VALUE + p5}" var="p5"></c:set>
					    	<c:set value="${pl.PER_TOTAL + p6}" var="p6"></c:set>
					    </c:forEach>
					    <tr>
					      <td class="info_content_01">合计 </td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p2}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p3}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p4}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p5}" maxFractionDigits="2"/></td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${p6}" maxFractionDigits="2"/></td>
					      <c:if test="${empty payInfoList}">
					      	<td class="info_content_01">&nbsp;</td>
					      </c:if>
					    </tr>
					    <tr>
					      <td class="info_content_01" colspan="2">总计</td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s + p}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s2 + p2}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s3 + p3}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s4 + p4}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">&nbsp;</td>
					      <td class="info_content_01"><fmt:formatNumber type="number" value="${s5 + p5}" maxFractionDigits="2"/></td>
					      <td class="info_content_01">
					      	<fmt:formatNumber type="number" value="${s6 + adjustValue + p6}" maxFractionDigits="2"/>
					      </td>
					    </tr>
					    <tr>
					      <td class="info_content_01" colspan="3">调整金额产生原因</td>
					      <td class="info_content_01" colspan="10">${reason}&nbsp;</td>
					    </tr>
					      </c:if>
					</table>
					</form>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="15">
						<tr><td>&nbsp;</td></tr>
					</table>
				</td>
				<td background="/img/tablbk01_r4_c26.gif" width="10">&nbsp;</td>
			</tr>
			
		</table>
		<DIV class=text id=popupcalendar style="top: 0px; left: 0px; z-index: 0"></DIV>



</div>