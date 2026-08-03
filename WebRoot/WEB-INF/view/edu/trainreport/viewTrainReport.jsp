<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<title></title>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	</head>
	<div class="pageContent" style="padding: 5px">
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li>
							<a href="javascript:;"><span> <%--报表类型--%> <spring:message
										code="rp.report.title.reporttype" /> </span> </a>
						</li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div layoutH="51"
						style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
						<c:forEach var="codeInfo" items="${codeInfoTreeList}"
							varStatus="i">
							<ul class="tree treeFolder">
								<c:forEach items="${reportList}" var="report">
									<c:if test="${report.REPORT_TYPE_NO eq codeInfo.CODE_NO}">
										<li>
											<a href="${report.URL_JSP}" target="ajax" rel="jbsxBoxAr">${codeInfo.CONTENT}</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</c:forEach>

					</div>
					<div id="jbsxBoxAr" class="unitBox" style="margin-left: 246px;">
					</div>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>